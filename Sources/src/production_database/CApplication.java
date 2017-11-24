/**
 * CApplication.java
 * @author Kasimov Ildar
 */
package production_database;

import java.util.Calendar;
import java.util.Date;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import production_database.interfaces.E_MATERIAL_TYPE;
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
        	    CQuery.GetMostExpensiveProductQuery(specificationsRepository);
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
        	    CQuery.GetYoungestOldestProductsQuery(specificationsRepository);
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
        	    specificationsRepository.Find(CQuery.GetProductsWerentProducedInYear(
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
            
            System.out.println(CQuery.FindFirstProductByMaterialsNumber(specificationsRepository));
            
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
            
            IMaterial mostUsedMaterial = CQuery.FindTheMostUsedMaterial(specificationsRepository,
        	    materialsRepository);
            
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
        	    CQuery.FindCompanyWithLowestProdution(specificationsRepository, companiesRepository);
            
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
        	    CQuery.GetMonthAvgMaterialConsumption(materialsRepository, specificationsRepository, 
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
            
            System.out.println(CQuery.GetProductWhichMostUsesMaterial(
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
        	    CQuery.GetSpecificationsWithReducedCost(specificationsRepository, 2017));
            
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
}