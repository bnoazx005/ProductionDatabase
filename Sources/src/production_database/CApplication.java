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

			_createIfNotExistsManufacturer(databaseInstance, new CCompany("Fokin Pickups", "315774600010405", "г. Москва, ул. Грина, д. 32"));
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
			System.out.println("The company" + company + " is already exists in the database");
			
			return;
		}
		
		database.store(company);
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
}
