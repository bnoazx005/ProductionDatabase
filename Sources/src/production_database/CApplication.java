package production_database;


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
		
		try {
			CProductRepository       productsRepository       = new CProductRepository(dbContext);
			CCompanyRepository       companiesRepository      = new CCompanyRepository(dbContext);
			CMaterialRepository      materialsRepository      = new CMaterialRepository(dbContext);
			CSpecificationRepository specificationsRepository = new CSpecificationRepository(dbContext);
			
			//CreateIfNotExists creates a new record in db, if it doesn't exist, in other case returns false without throwing an exception
			//CreateIfNotExists создает новую запись в базе данных, если она отсутствует, в противном случае возвращает false, не бросая исключения
			materialsRepository.CreateIfNotExists(new CMaterial("Alluminium", "kg./rub.", 70, E_MATERIAL_TYPE.MT_ALUMINIUM));
			materialsRepository.CreateIfNotExists(new CMaterial("Copper wire", "kg./rub.", 300, E_MATERIAL_TYPE.MT_COPPER));
			materialsRepository.CreateIfNotExists(new CMaterial("Humbucker pickup magnet", "element/rub.", 420, E_MATERIAL_TYPE.MT_MAGNET));
			materialsRepository.CreateIfNotExists(new CMaterial("Single pickup magnet", "element/rub.", 390, E_MATERIAL_TYPE.MT_MAGNET));
			materialsRepository.CreateIfNotExists(new CMaterial("Ebony board", "40cmх40cmх1000cm/rub.", 2750, E_MATERIAL_TYPE.MT_EBONY));
			materialsRepository.CreateIfNotExists(new CMaterial("Mahogany board", "m^3/rub.", 65000, E_MATERIAL_TYPE.MT_MAHOGANY));
			
			companiesRepository.CreateIfNotExists(new CCompany("Fokin Pickups", "315774600010405", "г. Москва, ул. Грина, д. 32"));
			companiesRepository.CreateIfNotExists(new CCompany("Lepsky Guitars", "758874643420489", "г. Краснодар ул Трамвайная, 13", "+79182189877"));
			
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			productsRepository.CreateIfNotExists(new CProduct("", "46-57-03", true, 180, null));
//			
//			specificationsRepository.CreateIfNotExists(null);
			
			//print all the data
			System.out.println("Materials' List: ");
			_printData(materialsRepository.FindAll());
			System.out.println();

			System.out.println("Companies' List: ");
			_printData(companiesRepository.FindAll());
			
			System.out.println("The program has finished its work");
		}
		finally {
			dbContext.close();
		}
	}
	
	private static <T> void _printData(ObjectSet<T> data) {
		System.out.println(data.size());
		for (T entity : data) {
			System.out.println(entity);
		}
	}
}