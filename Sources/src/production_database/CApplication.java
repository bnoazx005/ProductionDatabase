package production_database;


import java.util.Calendar;
import java.util.Date;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import production_database.interfaces.E_MATERIAL_TYPE;
import production_database.interfaces.IBaseRepository;
import production_database.interfaces.ICompany;
import production_database.interfaces.IMaterial;
import production_database.interfaces.IProduct;
import production_database.interfaces.ISpecification;


public final class CApplication {

	public static void main(String[] args) {

		ObjectContainer dbContext = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "./production.data");
		
		IProduct[] products = _formProductsList();
		
		ICompany[] companies = _formCompaniesList();
		
		IMaterial[] materials = _formMaterialsList();
		
		ISpecification[] specifications = _formSpecificationsList(materials, companies, products);
		
		try {
			CProductRepository       productsRepository       = new CProductRepository(dbContext);
			CCompanyRepository       companiesRepository      = new CCompanyRepository(dbContext);
			CMaterialRepository      materialsRepository      = new CMaterialRepository(dbContext);
			CSpecificationRepository specificationsRepository = new CSpecificationRepository(dbContext);
			
			//CreateIfNotExists creates a new record in db, if it doesn't exist, in other case returns false without throwing an exception
			//CreateIfNotExists создает новую запись в базе данных, если она отсутствует, в противном случае возвращает false, не бросая исключения
			
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
				specificationsRepository.CreateIfNotExists((CSpecification)specification);
			}
			
			//print all the data
			System.out.println("Materials' List: ");
			_printData(materialsRepository.FindAll());
			System.out.println();

			System.out.println("Companies' List: ");
			_printData(companiesRepository.FindAll());
			System.out.println();
			
			System.out.println("Products' List: ");
			_printData(productsRepository.FindAll());
			System.out.println();
			
			System.out.println("Specifications' List: ");
			_printData(specificationsRepository.FindAll());
			System.out.println();
			
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
	
	private static ICompany[] _formCompaniesList() {
		return new CCompany[] {
			new CCompany("Fokin Pickups", "315774600010405", "г. Москва, ул. Грина, д. 32"),
			new CCompany("Lepsky Guitars", "758874643420489", "г. Краснодар, ул Трамвайная, 13", "+79182189877")
		};
	}
	
	private static IMaterial[] _formMaterialsList() {
		return new CMaterial[] {
			new CMaterial("Alluminium", "kg./rub.", 70, E_MATERIAL_TYPE.MT_ALUMINIUM),
			new CMaterial("Copper wire", "kg./rub.", 300, E_MATERIAL_TYPE.MT_COPPER),
			new CMaterial("Humbucker pickup magnet", "element/rub.", 420, E_MATERIAL_TYPE.MT_MAGNET),
			new CMaterial("Single pickup magnet", "element/rub.", 390, E_MATERIAL_TYPE.MT_MAGNET),
			new CMaterial("Ebony board", "40cmх40cmх1000cm/rub.", 2750, E_MATERIAL_TYPE.MT_EBONY),
			new CMaterial("Mahogany board", "m^3/rub.", 65000, E_MATERIAL_TYPE.MT_MAHOGANY),
			new CMaterial("Plastic coil", "element/rub", 500, E_MATERIAL_TYPE.MT_PLASTIC),
			new CMaterial("Rosewood board", "m^3/rub.", 65000, E_MATERIAL_TYPE.MT_ROSEWOOD),
		};
	}
	
	private static IProduct[] _formProductsList() {
		return new CProduct[] {
			new CProduct("Hot Breeze Humbucker pickup", "1235412323", true, 180, null),
			new CProduct("MAJESTIC Humbucker pickup", "426364359", true, 234, null),
			new CProduct("Rocket Queen Humbucker pickup", "453936397", true, 250, null),
			new CProduct("Rout Humbucker pickup", "769473957", true, 189, null),
			new CProduct("Saboteur Humbucker pickup", "354826492", true, 210, null),
			new CProduct("Classic S single pickup", "134520572", true, 120, null),
			new CProduct("Woodstock single pickup", "553590183", true, 135, null),
			new CProduct("S Model guitar", "LepskySModel", true, 5, null),
			new CProduct("T Model guitar", "LepskyTModel", true, 5, null),
			new CProduct("F Model guitar", "LepskyFModel", true, 6, null),
			new CProduct("Dominator 6 Model guitar", "LepskyDominator6Model", true, 6, null),
			new CProduct("Dominator Custom 8 AR guitar", "LepskyDominator8ARModel", false, 2, null),
			new CProduct("Spirit 7 Custom SA guitar", "LepskySpirit7CustomSAModel", false, 2, null),
			new CProduct("S Model Custom PV guitar", "LepskySModelCustomPVModel", false, 2, null),
			new CProduct("Gravity 7 Custom SN guitar", "LepskyGravity7CustomSNModel", false, 2, null),
			new CProduct("S Model Anniversary F guitar", "LepskySModelAnniversaryFModel", false, 1, null),
		};
	}
	
	private static ISpecification[] _formSpecificationsList(IMaterial[] materials, ICompany[] companies, IProduct[] products) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(2011, 0, 1);
		
		Date approvalDate = calendar.getTime();

		calendar.set(2020, 0, 1);
		
		Date cancellationDate = calendar.getTime();

		calendar.set(2017, 0, 1);
		
		Date productionYear = calendar.getTime();
		
		CSpecification hotBreezeHumbPickup = new CSpecification("HotBreezeHumbuckerPickup", products[0], companies[0], approvalDate, 
																cancellationDate, productionYear, 180);
		
		hotBreezeHumbPickup.AddMaterial(materials[2], 12.0f); //magnets
		hotBreezeHumbPickup.AddMaterial(materials[1], 0.3f);  //copper wire
		hotBreezeHumbPickup.AddMaterial(materials[6], 1.0f);  //plastic coil	
		
		calendar.set(2012, 0, 1);
		
		approvalDate = calendar.getTime();

		calendar.set(2019, 0, 1);
		
		cancellationDate = calendar.getTime();

		CSpecification woodstockSinglePickup = new CSpecification("WoodstockSinglePickup", products[6], companies[0], approvalDate, 
																  cancellationDate, productionYear, 135);

		woodstockSinglePickup.AddMaterial(materials[3], 6.0f); //magnets
		woodstockSinglePickup.AddMaterial(materials[1], 0.15f);  //copper wire
		woodstockSinglePickup.AddMaterial(materials[6], 1.0f);  //plastic coil
		
		calendar.set(2014, 0, 1);
		
		approvalDate = calendar.getTime();

		calendar.set(2025, 0, 1);
		
		cancellationDate = calendar.getTime();
		
		CSpecification sModelLepskyGuitar = new CSpecification("Lepsky S Model guitar", products[7], companies[1], approvalDate, 
				  											   cancellationDate, productionYear, 5);

		sModelLepskyGuitar.AddMaterial(materials[5], 0.1f); //mahagony
		sModelLepskyGuitar.AddMaterial(materials[7], 0.07f); //rosewood 
		
		return new CSpecification[] {
				hotBreezeHumbPickup, woodstockSinglePickup, sModelLepskyGuitar
		};
	}
}