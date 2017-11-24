/**
 * CQuery.java
 * @author Kasimov Ildar
 */
package production_database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import production_database.evaluators.CSpecificMaterialEvaluation;
import production_database.evaluators.CSpecificProductionYearEvaluation;
import production_database.interfaces.ICompany;
import production_database.interfaces.IMaterial;
import production_database.interfaces.ISpecification;


/**
 * class CQuery
 * The class represents a storage of all used queries 
 * @author Kasimov Ildar
 */
public class CQuery {
    /**
     * The method creates a query that returns a list of specifications 
     * in descending order by products' prices
     * @param repository A reference to a repository's object
     * @return A list of objects
     */
    public static Query GetMostExpensiveProductQuery(CSpecificationRepository repository) {
	Query query = repository.CreateQuery();
	
	query.constrain(CSpecification.class); /* seeking for specifications */
	/* sort them in descending order by a price of a product */
	query.descend("mProductPrice").orderDescending(); 
	
	return query;
    }
    
    /**
     * The method creates a query that returns a list of specifications 
     * in descending order by production date
     * @param repository A reference to a repository's object
     * @return A list of objects
     */
    public static Query GetYoungestOldestProductsQuery(CSpecificationRepository repository) {
	Query query = repository.CreateQuery();
	
	query.constrain(CSpecification.class); /* seeking for specifications */
	/* sort them in descending order by an approval date */
	query.descend("mApprovalDate").orderDescending();
	
	return query;
    }
    
    /**
     * The method returns a reference to a query, which allows to select
     * products that weren't produced in Y year
     * @return
     */
    public static Query GetProductsWerentProducedInYear(
	    CSpecificationRepository repository, int year) {
        Query query = repository.CreateQuery();
        
        query.constrain(CSpecification.class);
        query.constrain(new CSpecificProductionYearEvaluation(2017, true));
        
        return query;
    }
    
    /**
     * The method returns a reference to a specifications, which contains more materials
     * than others
     * @param specificationsRepository A reference to a repository
     * @return A reference to a specifications, which contains more materials
     * than others
     */
    public static CSpecification FindFirstProductByMaterialsNumber(
	    CSpecificationRepository specificationsRepository) {
	ObjectSet<CSpecification> specifications = specificationsRepository.FindAll();

	CSpecification result   = null;
	CSpecification currSpec = null;
	
	int maxMaterialsCount = -1;
	
	for (int i = 0; i < specifications.size(); ++i) {
	    currSpec = specifications.get(i);
	    
	    int currMaterialsCount = 0;
	    
	    if ((currMaterialsCount = currSpec.GetMaterialsCount()) > maxMaterialsCount) {
		maxMaterialsCount = currMaterialsCount;

		result = currSpec;
	    }
	}
	
	return result;
    }
    
    /**
     * The method returns a reference to the most used material
     * @param specificationsRepository A reference to a specifications' repository
     * @param materialsRepository A reference to a materials' repository
     * @return A reference to the most used material
     */
    public static CMaterial FindTheMostUsedMaterial(
	    CSpecificationRepository specificationsRepository,
	    CMaterialRepository materialsRepository) {
	// first type describes a material's name, second is an amount of its usage
	Map<String, Integer> materialsUsageTable = new HashMap<String, Integer>();
	
	ObjectSet<CSpecification> specifications = specificationsRepository.FindAll();

	CSpecification currSpec = null;

	IMaterial[] currMaterials = null;
	IMaterial currMaterial    = null;
	
	String currMaterialName = null;
	
	//build a usage table
	for (int i = 0; i < specifications.size(); ++i) {
	    currSpec = specifications.get(i);

	    currMaterials = currSpec.GetMaterials();
	    
	    for (int j = 0; j < currMaterials.length; ++j) {
		currMaterial = currMaterials[j];
		
		currMaterialName = currMaterial.GetName();
		
		materialsUsageTable.put(currMaterialName,
			materialsUsageTable.getOrDefault(currMaterialName, 0) + 1);
	    }
	}

	//iterate over it and store a material with max value of usage
	String mostUsedMaterialName = null;
	
	int mostUsedMaterialUsageValue = -1;
	
	for (Map.Entry<String, Integer> entry : materialsUsageTable.entrySet())
	{
	    if (entry.getValue() > mostUsedMaterialUsageValue) {
		mostUsedMaterialUsageValue = entry.getValue();
		
		mostUsedMaterialName = entry.getKey();
	    }
	}
	
	ObjectSet<CMaterial> resultsArray = materialsRepository.FindByName(mostUsedMaterialName);
	
	if (resultsArray.size() < 1) {
	    return null;
	}
	
	return resultsArray.get(0);
    }
    
    /**
     * The method returns a reference to a company with the lowest production's amount
     * @param specificationsRepository A reference to a specifications' repository
     * @param companiesRepository A reference to a companies' repository
     * @return A reference to a company with the lowest production's amount
     */
    public static CCompany FindCompanyWithLowestProdution(
	    CSpecificationRepository specificationsRepository,
	    CCompanyRepository companiesRepository) {
	// first type describes a company's name, second is a production's amount
	Map<String, Integer> productionByCompanies = new HashMap<String, Integer>();
	
	ObjectSet<CSpecification> specifications = specificationsRepository.FindAll();

	CSpecification currSpec = null;

	ICompany currCompany = null;
	
	String currCompanyName = null;
	
	//build a table
	for (int i = 0; i < specifications.size(); ++i) {
	    currSpec = specifications.get(i);

	    currCompany = currSpec.GetManufacturer();
	    
	    currCompanyName = currCompany.GetName();

	    productionByCompanies.put(currCompanyName,
		    productionByCompanies.getOrDefault(currCompanyName, 0) + 1);
	}

	//iterate over it and store a company with min value of production
	String lowestProductionCompanyName = null;
	
	int mostUsedMaterialUsageValue = Integer.MAX_VALUE;
	
	for (Map.Entry<String, Integer> entry : productionByCompanies.entrySet())
	{
	    if (entry.getValue() < mostUsedMaterialUsageValue) {
		mostUsedMaterialUsageValue = entry.getValue();
		
		lowestProductionCompanyName = entry.getKey();
	    }
	}
	
	ObjectSet<CCompany> resultsArray = 
		companiesRepository.FindByName(lowestProductionCompanyName);
	
	if (resultsArray.size() < 1) {
	    return null;
	}
	
	return resultsArray.get(0);
    }
    
    /**
     * The method returns a month average consumption of the material in the specified year
     * @param materials Materials' repository
     * @param specifications Specifications' repository
     * @param materialName A material's name
     * @param year A year of a production
     * @return A month average consumption of the material in the specified year
     */
    public static float GetMonthAvgMaterialConsumption(CMaterialRepository materials,
	    CSpecificationRepository specifications, String materialName,
	    int year) {
	ObjectSet<CMaterial> foundMaterials = materials.FindByName(materialName);
	
	if (foundMaterials.size() < 1) {
	    return 0.0f;
	}
	
	IMaterial specifiedMaterial = foundMaterials.get(0);
	
	Query specsQuery = specifications.CreateQuery();
	
	// select specifications
	specsQuery.constrain(CSpecification.class);
	// only ones that were produced in (year) year
	specsQuery.constrain(new CSpecificProductionYearEvaluation(year, false));
	// select specifications, which uses material (materialName)
	specsQuery.constrain(new CSpecificMaterialEvaluation(specifiedMaterial));
	
	//compute average value per month
	ObjectSet<CSpecification> specs = specifications.Find(specsQuery);

	float avgConsumption = 0.0f;
	
        for (CSpecification entity : specs) {
            avgConsumption += entity.GetMaterialAmount(specifiedMaterial);
        }
        
        avgConsumption /= 12.0;
	
	return avgConsumption;
    }
    
    /**
     * The query returns a specification, which most uses the specified material
     * @param materials Materials' repository
     * @param specifications Specifications' repository
     * @param materialName A material's name
     * @return The method returns a specification, which most uses the specified material
     */
    public static ISpecification GetProductWhichMostUsesMaterial(CMaterialRepository materials,
	    CSpecificationRepository specifications, String materialName) {
	ObjectSet<CMaterial> foundMaterials = materials.FindByName(materialName);
	
	if (foundMaterials.size() < 1) {
	    return null;
	}
	
	IMaterial specifiedMaterial = foundMaterials.get(0);
	
	Query specsQuery = specifications.CreateQuery();

	// select specifications
	specsQuery.constrain(CSpecification.class);
	// select specifications, which uses material (materialName)
	specsQuery.constrain(new CSpecificMaterialEvaluation(specifiedMaterial));

	// execute the query
	ObjectSet<CSpecification> specs = specifications.Find(specsQuery);
	
	// find a specification, which most uses the material
	ISpecification result = null;
	
	float maxUsageValue = -1.0f;
	float currUsageValue = 0.0f;
	
	for (CSpecification entity : specs) {
	    if (maxUsageValue < (currUsageValue = entity.GetMaterialAmount(specifiedMaterial))) {
		maxUsageValue = currUsageValue;
		result = entity;
	    }
        }
	
	return result;
    }
    
    /**
     * The query returns specifications, which reduced their production cost in Y year
     * @param specifications A specifications' repository
     * @param year A year of production
     * @return Returns specifications, which reduced their production cost in Y year
     */
    public static ISpecification[] GetSpecificationsWithReducedCost(
	    CSpecificationRepository specifications, int year) {	
	Query currYearSpecsQuery = specifications.CreateQuery();
	Query prevYearSpecsQuery = specifications.CreateQuery();

	// select specifications
	currYearSpecsQuery.constrain(CSpecification.class);
	// only ones that were produced in (year) year
	currYearSpecsQuery.constrain(new CSpecificProductionYearEvaluation(year, false));

	// select specifications
	prevYearSpecsQuery.constrain(CSpecification.class);
	// only ones that were produced in (year) year
	prevYearSpecsQuery.constrain(new CSpecificProductionYearEvaluation(year - 1, false));
	
	// execute the first query
	ObjectSet<CSpecification> currYearSpecs = specifications.Find(currYearSpecsQuery);
	// execute the second query
	ObjectSet<CSpecification> prevYearSpecs = specifications.Find(prevYearSpecsQuery);
		
	ArrayList<ISpecification> resultArray = new ArrayList<ISpecification>();
	
	for (CSpecification currYearEntity : currYearSpecs) {
	    for (CSpecification prevYearEntity : prevYearSpecs) {
		//check up a manufacturer and a product
		if ((currYearEntity.GetManufacturer() != prevYearEntity.GetManufacturer()) ||
			(currYearEntity.GetProduct() != prevYearEntity.GetProduct()) ||
			(currYearEntity.GetProductionCost() > prevYearEntity.GetProductionCost())) {
		    continue;
		}

		//found previous year specification for current
		resultArray.add(currYearEntity);
		
		break;
	    }
	}

	return resultArray.toArray(new ISpecification[0]);
    }
}
