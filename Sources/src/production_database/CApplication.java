package production_database;


import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

import production_database.interfaces.*;


public final class CApplication {

	public static void main(String[] args) {

		ObjectContainer databaseInstance = null;
				
		try {
			databaseInstance = Db4o.openFile("production.data");

			_createIfNotExistsMaterial(databaseInstance, new CMaterial("Alluminium", "kg./rub.", 70, E_MATERIAL_TYPE.MT_ALUMINIUM));
			_createIfNotExistsMaterial(databaseInstance, new CMaterial("Copper wire", "kg./rub.", 300, E_MATERIAL_TYPE.MT_COPPER));
			_createIfNotExistsMaterial(databaseInstance, new CMaterial("Humbucker pickup magnet", "element/rub.", 420, E_MATERIAL_TYPE.MT_MAGNET));
			_createIfNotExistsMaterial(databaseInstance, new CMaterial("Single pickup magnet", "element/rub.", 390, E_MATERIAL_TYPE.MT_MAGNET));
			_createIfNotExistsMaterial(databaseInstance, new CMaterial("Ebony board", "40cmх40cmх1000cm/rub.", 2750, E_MATERIAL_TYPE.MT_EBONY));
			_createIfNotExistsMaterial(databaseInstance, new CMaterial("Mahogany board", "m^3/rub.", 65000, E_MATERIAL_TYPE.MT_MAHOGANY));
			
			_createIfNotExistsManufacturer(databaseInstance, new CCompany("Fokin Pickups", "315774600010405", "г. Москва, ул. Грина, д. 32"));
			_createIfNotExistsManufacturer(databaseInstance, new CCompany("Lepsky Guitars", "758874643420489", "г. Краснодар ул Трамвайная, 13", "+79182189877"));
		}
		finally {
			if (databaseInstance != null) {
				databaseInstance.close();				
			}
		}
	}
	
	private static void _createIfNotExistsManufacturer(ObjectContainer database, ICompany company) throws IllegalArgumentException {
		if (company == null) {
			throw new IllegalArgumentException("An input parameter cannot equal to null");
		}
		
		ObjectSet existingSameCompanies = _findCompanyByName(database, company.GetName());
		
		if (existingSameCompanies.size() > 1) {
			System.out.println("The company " + company + " is already exists in the database");
			
			return;
		}
		
		database.store(company);

		System.out.println("The company " + company + " was successfully added into the database");
	}
	
	private static void _createIfNotExistsMaterial(ObjectContainer database, IMaterial material) throws IllegalArgumentException {
		if (material == null) {
			throw new IllegalArgumentException("An input parameter cannot equal to null");
		}
		
		ObjectSet existingSameMaterials = _findMaterialByName(database, material.GetName());
		
		if (existingSameMaterials.size() > 1) {
			System.out.println("The material " + material + " is already exists in the database");
			
			return;
		}
		
		database.store(material);

		System.out.println("The company " + material + " was successfully added into the database");
	}

	private static ObjectSet _findCompanyByName(ObjectContainer database, String name) throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("A name parameter cannot equal to null");			
		}
		
		ObjectSet companiesSet = database.query(new Predicate<ICompany>() {
			public boolean match(ICompany company) {
				//search for a match by a company's name
				return company.GetName().equals(name);
			}
		});
		
		return companiesSet;
	}
	
	private static ObjectSet _findMaterialByName(ObjectContainer database, String name) throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("A name parameter cannot equal to null");			
		}
		
		ObjectSet materialsSet = database.query(new Predicate<IMaterial>() {
			public boolean match(IMaterial material) {
				//search for a match by a material's name
				return material.GetName().equals(name);
			}
		});
		
		return materialsSet;
	}
}
