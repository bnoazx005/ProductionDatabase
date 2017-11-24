/**
 * CApplication.java
 * @author Kasimov Ildar
 */
package production_database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import production_database.evaluators.CSpecificMaterialEvaluation;
import production_database.evaluators.CSpecificProductionYearEvaluation;
import production_database.interfaces.E_MATERIAL_TYPE;
import production_database.interfaces.IBaseRepository;
import production_database.interfaces.ICompany;
import production_database.interfaces.IMaterial;
import production_database.interfaces.IProduct;
import production_database.interfaces.ISpecification;

public final class CApplication {
    /**
     * The entry point of the program
     * @param args program's arguments list
     */    
    public static void main(String[] args) {

        ObjectContainer dbContext = Db4oEmbedded.openFile(
        	Db4oEmbedded.newConfiguration(), "./production.data");
        
        /* _formProductsList returns a hard-coded array of products */
        IProduct[] products	= _formProductsList();
        
        /* _formCompaniesList returns a hard-coded array of companies */
        ICompany[] companies	= _formCompaniesList();
        
        /* _formMaterialsList returns a hard-coded array of materials */
        IMaterial[] materials	= _formMaterialsList();	
        
        /* _formSpecificationsList returns a hard-coded array of specifications */
        ISpecification[] specifications = _formSpecificationsList(materials,
        	                                                  companies,
        	                                                  products);
        
        try {
            /*
             * Initialize repositories for the specified data
             */
            CProductRepository productsRepository = 
        	    new CProductRepository(dbContext);
            
            CCompanyRepository companiesRepository = 
        	    new CCompanyRepository(dbContext);
            
            CMaterialRepository materialsRepository = 
        	    new CMaterialRepository(dbContext);
            
            CSpecificationRepository specificationsRepository = 
        	    new CSpecificationRepository(dbContext);
            
            /* CreateIfNotExists creates a new record in db, if it doesn't 
               exist, in other case returns false without throwing 
               an exception.
               CreateIfNotExists создает новую запись в БД, если та еще
               не существует, в противном случае возвращается false без
               бросания исключения.
            */
            for (IMaterial material: materials) {
                materialsRepository.CreateIfNotExists((CMaterial)material);
            }
            
            for (ICompany company: companies) {
                companiesRepository.CreateIfNotExists((CCompany)company);
            }
            
            for (IProduct product: products) {
                productsRepository.CreateIfNotExists((CProduct)product);
            }
            
            for (ISpecification specification: specifications) {
                specificationsRepository.CreateIfNotExists(
                	(CSpecification)specification);
            }
            
            //print all the data
            System.out.println("[DATABASE]");
            _printResults("Materials' List: ", materialsRepository.FindAll());
            _printResults("Companies' List: ", companiesRepository.FindAll());
            _printResults("Products' List: ", productsRepository.FindAll());
            _printResults("Specifications' List: ", specificationsRepository.FindAll());
            
            //print out queries results
            System.out.println("[QUERIES]");
            
            /*
             * Most expensive product query (самое дорогое изделие)
             * >===============================================================
             */
            Query mostExpensiveProductQuery = 
        	    _getMostExpensiveProductQuery(specificationsRepository);
            ObjectSet<CSpecification> mostExpensiveProductQueryResult = 
        	    specificationsRepository.Find(mostExpensiveProductQuery);
            
            if (mostExpensiveProductQueryResult.size() >= 1) {
                System.out.println("Most expensive product: " + 
                		   mostExpensiveProductQueryResult.get(0));
            }
            else {
        	System.out.println("Most expensive product: None");        	
            }
            
            /*
             * Most expensive product query (самое дорогое изделие)
             * <===============================================================
             */
            
            /*
             * The Youngest and the oldest products (самый старый и самый молодой продукт)
             * >===============================================================
             */
            Query youngestAndOldestProductsQuery = 
        	    _getYoungestOldestProductsQuery(specificationsRepository);
            ObjectSet<CSpecification> youngestAndOldestProductsQueryResult = 
        	    specificationsRepository.Find(youngestAndOldestProductsQuery);
            
            if (youngestAndOldestProductsQueryResult.size() >= 2) {
        	System.out.println("The youngest and the oldest products by approval date: ");
        	System.out.println("The youngest: " + youngestAndOldestProductsQueryResult.get(0));
        	System.out.println("The oldest: " + 
        		youngestAndOldestProductsQueryResult.get(
        			youngestAndOldestProductsQueryResult.size() - 1));  
            }
            else if (youngestAndOldestProductsQueryResult.size() == 1) {
        	System.out.println("The youngest and the oldest products by approval date: " +
        		youngestAndOldestProductsQueryResult.get(0));         	
            }
            else {
        	System.out.println("The youngest and the oldest products by approval date: None");         	
            }
            
            /*
             * Youngest and oldest products (самый старый и самый молодой продукт)
             * <===============================================================
             */
            
            /*
             * The products that weren't produced in Y year 
             * (список изделий, которые не производились в Y году)
             * >===============================================================
             */
            int year = 2017;
            
            _printResults("The products that weren't produced in " + year + " year: ", 
        	    specificationsRepository.Find(_getProductsWerentProducedInYear(
        		    specificationsRepository, year)));
            
            /*
             * The products that weren't produced in Y year 
             * (список изделий, которые не производились в Y году)
             * <===============================================================
             */
            
            /*
             * Display a product that consists of more amount of materials than others
             * (Вывести изделие в котором использовано больше всего материалов)
             * >===============================================================
             */

            System.out.println("The product that consists of more amount of materials than others: ");
            
            System.out.println(_findFirstProductByMaterialsNumber(specificationsRepository));
            
            /*
             * Display a product that consists of more amount of materials than others
             * (Вывести изделие в котором использовано больше всего материалов)
             * <===============================================================
             */
            
            /*
             * Tthe most used material
             * (Материал, который используется чаще остальных)
             * >===============================================================
             */

            System.out.println("The most used material: ");
            
            IMaterial mostUsedMaterial = _findTheMostUsedMaterial(specificationsRepository, materialsRepository);
            
            System.out.println(mostUsedMaterial != null ? mostUsedMaterial : "None");
            
            /*
             * The most used material
             * (Материал, который используется чаще остальных)
             * <===============================================================
             */
            
            /*
             * The company that has lowest amount of products
             * (Предприятие, которое выпускает меньше всего изделий)
             * >===============================================================
             */

            System.out.println("The company that has lowest amount of products: ");
            
            ICompany companyWithLowestProduction = 
        	    _findCompanyWithLowestProdution(specificationsRepository, companiesRepository);
            
            System.out.println(companyWithLowestProduction != null ? companyWithLowestProduction : "None");
            
            /*
             * The company that has lowest amount of products
             * (Предприятие, которое выпускает меньше всего изделий)
             * <===============================================================
             */
            
            /*
             * Display an average consumption of Nth material in Yth year
             * (Вывести среднемесячный расход материала 'N' в Y году)
             * >===============================================================
             */
            
            System.out.println("Month average consumption of product " +
        	    "[Humbucker pickup magnet] in " +
        	    "2017 year: " +
        	    _getMonthAvgMaterialConsumption(materialsRepository, specificationsRepository, 
        		    "Humbucker pickup magnet", 2017));
            
            /*
             * Display an average consumption of Nth material in Yth year
             * (Вывести среднемесячный расход материала 'N' в Y году)
             * <===============================================================
             */
            
            /*
             * Display a product that the most uses the specified material
             * (Определить изделие, которое больше всего использует указанный материал)
             * >===============================================================
             */
            
            System.out.print("The product that the most uses the specified material: ");
            
            System.out.println(_getProductWhichMostUsesMaterial(
        	    materialsRepository, specificationsRepository, 
		    "Alluminium"));
            
            /*
             * Display a product that the most uses the specified material
             * (Определить изделие, которое больше всего использует указанный материал)
             * <===============================================================
             */
            
            /*
             * Display a list of products for which the cost of materials in Y
             * year decreased compared to the previous year
             * (Вывести список изделий, для которых затраты на материалы в Y году
             *  снизились по сравнению с предыдущим годом)
             * >===============================================================
             */
                        
            _printResults("The specifications with reduced costs: ",
        	    _getSpecificationsWithReducedCost(specificationsRepository, 2017));
            
            /*
             * Display a list of products for which the cost of materials in Y
             * year decreased compared to the previous year
             * (Вывести список изделий, для которых затраты на материалы в Y году
             *  снизились по сравнению с предыдущим годом)
             * <===============================================================
             */
            
            System.out.println("The program has finished its work");
        }
        finally {
            dbContext.close();
        }
    }

    private static <T> void _printData(ObjectSet<T> data) {
        for (T entity : data) {
            System.out.println(entity);
        }
    }
    
    private static <T> void _printData(T[] data) {
        for (T entity : data) {
            System.out.println(entity);
        }
    }
    
    private static <T> void _printResults(String header, ObjectSet<T> data) {
	System.out.println(header);
	
	_printData(data);
	
	System.out.println();
    }
    
    private static <T> void _printResults(String header, T[] data) {
	System.out.println(header);
	
	_printData(data);
	
	System.out.println();
    }
    
    private static ICompany[] _formCompaniesList() {
	/*CCompany's constructor description:
	 * first	- A company's name,
	 * second	- A company's code,
	 * third	- A company's address
	 * fourth	- A company's phone
	 */
        return new CCompany[] {
            new CCompany("Fokin Pickups", "315774600010405", 
        	         "г. Москва, ул. Грина, д. 32"),
            new CCompany("Lepsky Guitars", "758874643420489", 
        	         "г. Краснодар ул Трамвайная, 13", "+79182189877")
        };
    }
    
    private static IMaterial[] _formMaterialsList() {
	/*CMaterial's constructor description:
	 * first	- a name of a material,
	 * second	- measurements units,
	 * third	- a price per single unit,
	 * fourth	- a type of a material
	 */
        return new CMaterial[] {
            new CMaterial("Alluminium", "kg./rub.", 70, 
        	          E_MATERIAL_TYPE.MT_ALUMINIUM),
            new CMaterial("Copper wire", "kg./rub.", 300, 
        	          E_MATERIAL_TYPE.MT_COPPER),
            new CMaterial("Humbucker pickup magnet", "element/rub.", 420, 
        	          E_MATERIAL_TYPE.MT_MAGNET),
            new CMaterial("Single pickup magnet", "element/rub.", 390, 
        	          E_MATERIAL_TYPE.MT_MAGNET),
            new CMaterial("Ebony board", "40cmx40cmx1000cm/rub.", 2750, 
        	          E_MATERIAL_TYPE.MT_EBONY),
            new CMaterial("Mahogany board", "m^3/rub.", 65000, 
        	          E_MATERIAL_TYPE.MT_MAHOGANY),
            new CMaterial("Plastic coil", "element/rub", 500, 
        	          E_MATERIAL_TYPE.MT_PLASTIC),
            new CMaterial("Rosewood board", "m^3/rub.", 55000, 
        	          E_MATERIAL_TYPE.MT_ROSEWOOD),
        };
    }
    
    private static IProduct[] _formProductsList() {
	/*CProduct's constructor description:
	 * first	- A name of a product
	 * second	- A code of a product
	 * third	- Is a product standard
	 * fourth	- An amount of product's production per year
	 * fifth	- An additional notes about product (not used here)
	 */
        return new CProduct[] {
            new CProduct("Hot Breeze Humbucker pickup", "1235412323", true,
        	         180, null),
            new CProduct("MAJESTIC Humbucker pickup", "426364359", true,
        	         234, null),
            new CProduct("Rocket Queen Humbucker pickup", "453936397", true,
        	         250, null),
            new CProduct("Rout Humbucker pickup", "769473957", true,
        	         189, null),
            new CProduct("Saboteur Humbucker pickup", "354826492", true,
        	         210, null),
            new CProduct("Classic S single pickup", "134520572", true,
        	         120, null),
            new CProduct("Woodstock single pickup", "553590183", true,
        	         135, null),
            new CProduct("S Model guitar", "LepskySModel", true,
        	         5, null),
            new CProduct("T Model guitar", "LepskyTModel", true,
        	         5, null),
            new CProduct("F Model guitar", "LepskyFModel", true,
        	         6, null),
            new CProduct("Dominator 6 Model guitar", 
        	         "LepskyDominator6Model", true, 6, null),
            new CProduct("Dominator Custom 8 AR guitar", 
        	         "LepskyDominator8ARModel", false, 2, null),
            new CProduct("Spirit 7 Custom SA guitar", 
        	         "LepskySpirit7CustomSAModel", false, 2, null),
            new CProduct("S Model Custom PV guitar", 
        	         "LepskySModelCustomPVModel", false, 2, null),
            new CProduct("Gravity 7 Custom SN guitar", 
        	         "LepskyGravity7CustomSNModel", false, 2, null),
            new CProduct("S Model Anniversary F guitar", 
        	         "LepskySModelAnniversaryFModel", false, 1, null),
        };
    }
    
    private static ISpecification[] _formSpecificationsList(
	    IMaterial[] materials, ICompany[] companies, IProduct[] products) {
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(2011, 0, 1);
        
        Date approvalDate = calendar.getTime();

        calendar.set(2020, 0, 1);
        
        Date cancellationDate = calendar.getTime();

        calendar.set(2017, 0, 1);
        
        Date productionYear = calendar.getTime();
        
        CSpecification hotBreezeHumbPickup = 
        	new CSpecification("HotBreezeHumbuckerPickup", products[0], 
        		           companies[0], approvalDate,
        		           cancellationDate, productionYear, 180, 3600);
        
        /*Attach materials to the specification's object*/
        hotBreezeHumbPickup.AddMaterial(materials[2], 12.0f); //magnets
        hotBreezeHumbPickup.AddMaterial(materials[1], 0.3f);  //copper wire
        hotBreezeHumbPickup.AddMaterial(materials[6], 1.0f);  //plastic coil    
        
        calendar.set(2012, 0, 1);
        
        approvalDate = calendar.getTime();

        calendar.set(2019, 0, 1);
        
        cancellationDate = calendar.getTime();

        CSpecification woodstockSinglePickup = 
        	new CSpecification("WoodstockSinglePickup2017", products[6],
        		           companies[0], approvalDate,
        		           cancellationDate, productionYear, 135, 2700);

        woodstockSinglePickup.AddMaterial(materials[3], 6.0f); //magnets
        woodstockSinglePickup.AddMaterial(materials[1], 0.15f);  //copper wire
        woodstockSinglePickup.AddMaterial(materials[6], 1.0f);  //plastic coil
        
        calendar.set(2016, 0, 1);

        productionYear = calendar.getTime();

        CSpecification woodstockSinglePickupPrevYear = 
        	new CSpecification("WoodstockSinglePickup2016", products[6],
        		           companies[0], approvalDate,
        		           cancellationDate, productionYear, 135, 2700);

        woodstockSinglePickupPrevYear.AddMaterial(materials[3], 6.0f); //magnets
        woodstockSinglePickupPrevYear.AddMaterial(materials[1], 0.3f);  //copper wire
        woodstockSinglePickupPrevYear.AddMaterial(materials[6], 1.0f);  //plastic coil
        
        calendar.set(2014, 0, 1);
        
        approvalDate = calendar.getTime();

        calendar.set(2025, 0, 1);
        
        cancellationDate = calendar.getTime();
        
        calendar.set(2017, 0, 1);

        productionYear = calendar.getTime();
        
        CSpecification sModelLepskyGuitar = 
        	new CSpecification("Lepsky S Model guitar", products[7],
        			   companies[1], approvalDate,
        			   cancellationDate, productionYear, 5, 66000);

        sModelLepskyGuitar.AddMaterial(materials[5], 0.1f); //mahagony
        sModelLepskyGuitar.AddMaterial(materials[7], 0.07f); //rosewood
        
        calendar.set(2013, 0, 1);
        
        productionYear = calendar.getTime();
        
        calendar.set(2013, 0, 1);
        
        approvalDate = calendar.getTime();

        calendar.set(2013, 0, 1);
        
        cancellationDate = calendar.getTime();	
        
        CSpecification gravityCustomGuitar = 
        	new CSpecification("Gravity 7 Custom SN Model guitar", products[14],
        			   companies[1], approvalDate,
        			   cancellationDate, productionYear, 1, 78000);

        gravityCustomGuitar.AddMaterial(materials[5], 0.1f); //mahagony
        gravityCustomGuitar.AddMaterial(materials[7], 0.07f); //rosewood
        
        /*returns three specifications within the array*/
        return new CSpecification[] {
                hotBreezeHumbPickup, woodstockSinglePickup, woodstockSinglePickupPrevYear,
                sModelLepskyGuitar, gravityCustomGuitar
        };
    }
    
    /**
     * The method creates a query that returns a list of specifications 
     * in descending order by products' prices
     * @param repository A reference to a repository's object
     * @return A list of objects
     */
    private static Query _getMostExpensiveProductQuery(CSpecificationRepository repository) {
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
    private static Query _getYoungestOldestProductsQuery(CSpecificationRepository repository) {
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
    private static Query _getProductsWerentProducedInYear(
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
    private static CSpecification _findFirstProductByMaterialsNumber(
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
    private static CMaterial _findTheMostUsedMaterial(
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
    private static CCompany _findCompanyWithLowestProdution(
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
    private static float _getMonthAvgMaterialConsumption(CMaterialRepository materials,
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
    private static ISpecification _getProductWhichMostUsesMaterial(CMaterialRepository materials,
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
    private static ISpecification[] _getSpecificationsWithReducedCost(
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