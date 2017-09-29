/**
 * ISpecification.java
 * 
 * @author Kasimov Ildar
 */
package production_database.interfaces;

import java.util.*;

/**
 * interface ISpecification
 * 
 * The interface describes a single specification's functionality
 * 
 * @author Kasimov Ildar
 *
 */

public interface ISpecification extends IBaseDBEntity {
	/**
	 * The method sets up a reference to a product
	 * @param product A reference to a product
	 */
	
	void SetProduct(IProduct product) throws IllegalArgumentException;
	
	/**
	 * The method sets up a reference to a manufacturer
	 * @param company A reference to a manufacturer
	 */
	
	void SetManufacturer(ICompany company) throws IllegalArgumentException;
	
	/**
	 * The method sets up a date when a product was approved for production
	 * @param date An approval date
	 */
	
	void SetApprovalDate(Date date);
	
	/**
	 * The method sets up a date when a product's production was canceled
	 * @param date A cancellation date
	 */
	
	void SetCancellationDate(Date date);
	
	/**
	 * The method sets up a year of a production
	 * @param date A year of a production
	 */
	
	void SetYearOfProduction(Date date);
	
	/**
	 * The method sets up an amount of production
	 * @param amount A positive value that represents an amount of production
	 */
	
	void SetProductionAmount(float amount);
	
	/**
	 * The method adds a new reference to a material into the specification
	 * @param material A reference to a material
	 * @param amount An amount of used material
	 */
		
	void AddMaterial(IMaterial material, float amount) throws IllegalArgumentException;
	
	/**
	 * The method removes the specified material from the specification
	 * @param material A reference to a material
	 */
	
	void DeleteMaterial(IMaterial material) throws NoSuchElementException, IllegalArgumentException;
	
	/**
	 * The method returns a reference to a product
	 * @return A reference to a product
	 */
	
	IProduct GetProduct();
	
	/**
	 * The method returns a reference to a company-manufacturer
	 * @return A reference to a company-manufacturer
	 */
	
	ICompany GetManufacturer();
	
	/**
	 * The method returns an approval date
	 * @return An approval date
	 */
	
	Date GetApprovalDate();
	
	/**
	 * The method returns a cancellation date
	 * @return A cancellation date
	 */
	
	Date GetCancellationDate();
	
	/**
	 * The method returns a year of production
	 * @return A year of production
	 */
	
	Date GetYearOfProduction();
	
	/**
	 * The method returns an amount of a production
	 * @return An amount of a production
	 */
	
	float GetProductionAmount();
	
	/**
	 * The method returns a reference to a material if a specification contains the specified one.
	 * 
	 * @param name A material's name
	 * @return A reference to a material
	 */
	
	IMaterial GetMaterialByName(String name) throws NoSuchElementException, IllegalArgumentException;
	
	
	/**
	 * The method returns a used amount of the material with the specified name if the material
	 * is stored in the specification.
	 * @param material A reference to a material
	 * @return A positive value that describes a used amount of the material
	 */
	float GetMaterialAmount(IMaterial material) throws NoSuchElementException, IllegalArgumentException;
}
