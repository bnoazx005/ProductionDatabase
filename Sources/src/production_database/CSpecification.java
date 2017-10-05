/**
 * CSpecification.java
 * 
 * @author Kasimov Ildar
 */
package production_database;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import production_database.interfaces.*;


/**
 * class CSpecification
 * 
 * The class implements ISpecification and describes a functionality of a production specification;
 * @author Kasimov Ildar
 */

public final class CSpecification extends CBaseDBEntity implements ISpecification {
	private IProduct              mProduct;
	
	private ICompany              mManufacturer;
	
	private Map<IMaterial, Float> mUsedMaterials;
	
	private Date                  mApprovalDate;
	
	private Date                  mCancellationDate;
	
	private Date                  mYearOfProduction;
	
	private float                 mProductionAmount;
	
	/**
	 * The constructor with main parameters
	 * @param name A name of a specification
	 * @param product A reference to a product
	 * @param manufacturer A reference to a manufacturer
	 * @param materials A reference to a map, which contains amounts of used materials per entity
	 * @param approvalDate A date when a production was begun
	 * @param cancellationDate A date when a production was canceled
	 * @param yearOfProduction A year when a production was begun
	 * @param productionAmount An amount of produced product
	 * @throws IllegalArgumentException The method throws the exception when one of the parameters equals to null
	 */

	public CSpecification(String name, IProduct product, ICompany manufacturer, Map<IMaterial, Float> materials,
						  Date approvalDate, Date cancellationDate, Date yearOfProduction,
						  float productionAmount) throws IllegalArgumentException {
		super(name);
		
		if (product == null) {
			throw new IllegalArgumentException("The product reference is not initialized");
		}
		
		if (manufacturer == null) {
			throw new IllegalArgumentException("The manufacturer reference is not initialized");
		}
		
		if (materials == null) {
			throw new IllegalArgumentException("The materials map's reference is not initialized");
		}
		
		mProduct = product;
		
		mManufacturer = manufacturer;
		
		mUsedMaterials = materials;
		
		mApprovalDate = approvalDate;
		
		mCancellationDate = cancellationDate;
		
		mYearOfProduction = yearOfProduction;
		
		mProductionAmount = productionAmount;
	}
	
	/**
	 * The constructor without materials parameter
	 * @param name A name of a specification
	 * @param product A reference to a product
	 * @param manufacturer A reference to a manufacturer
	 * @param approvalDate A date when a production was begun
	 * @param cancellationDate A date when a production was canceled
	 * @param yearOfProduction A year when a production was begun
	 * @param productionAmount An amount of produced product
	 * @throws IllegalArgumentException The method throws the exception when one of the parameters equals to null
	 */
	
	public CSpecification(String name, IProduct product, ICompany manufacturer, Date approvalDate, Date cancellationDate, 
						  Date yearOfProduction, float productionAmount) throws IllegalArgumentException {
		super(name);
		
		if (product == null) {
			throw new IllegalArgumentException("The product reference is not initialized");
		}
		
		if (manufacturer == null) {
			throw new IllegalArgumentException("The manufacturer reference is not initialized");
		}
				
		mProduct = product;
		
		mManufacturer = manufacturer;
		
		mUsedMaterials = new HashMap<IMaterial, Float>();
		
		mApprovalDate = approvalDate;
		
		mCancellationDate = cancellationDate;
		
		mYearOfProduction = yearOfProduction;
		
		mProductionAmount = productionAmount;
	}

	@SuppressWarnings("unused")
	private CSpecification() {
	}
	
	/**
	 * The method sets up a reference to a product
	 * @param product A reference to a product
	 * @throws IllegalArgumentException The method throws the exception when the parameter equals to null
	 */
	
	@Override 
	public void SetProduct(IProduct product) throws IllegalArgumentException {
		if (product == null) {
			throw new IllegalArgumentException("The product reference is not initialized");
		}
		
		mProduct = product;
	}
	
	/**
	 * The method sets up a reference to a manufacturer
	 * @param company A reference to a manufacturer
	 * @throws IllegalArgumentException The method throws the exception when the parameter equals to null
	 */
	
	@Override 
	public void SetManufacturer(ICompany company) throws IllegalArgumentException {
		if (company == null) {
			throw new IllegalArgumentException("The manufacturer reference is not initialized");
		}
		
		mManufacturer = company;
	}
	
	/**
	 * The method sets up a date when a product was approved for production
	 * @param date An approval date
	 */
	
	@Override 
	public void SetApprovalDate(Date date) {
		mApprovalDate = date;
	}
	
	/**
	 * The method sets up a date when a product's production was canceled
	 * @param date A cancellation date
	 */
	
	@Override 
	public void SetCancellationDate(Date date) {
		mCancellationDate = date;
	}
	
	/**
	 * The method sets up a year of a production
	 * @param date A year of a production
	 */
	
	@Override 
	public void SetYearOfProduction(Date date) {
		mYearOfProduction = date;
	}
	
	/**
	 * The method sets up an amount of production
	 * @param amount A positive value that represents an amount of production
	 */
	
	@Override 
	public void SetProductionAmount(float amount) {
		mProductionAmount = amount;
	}
	
	/**
	 * The method adds a new reference to a material into the specification
	 * @param material A reference to a material
	 * @param amount An amount of used material
	 */
		
	@Override 
	public void AddMaterial(IMaterial material, float amount) throws IllegalArgumentException {
		if (material == null) {
			throw new IllegalArgumentException("The material reference is not initialized");
		}
		
		if (mUsedMaterials.containsKey(material)) {
			throw new NoSuchElementException("The specification already contains this material");
		}
		
		mUsedMaterials.put(material, amount);
	}
	
	/**
	 * The method removes the specified material from the specification
	 * @param material A reference to a material
	 */
	
	@Override 
	public void DeleteMaterial(IMaterial material) throws NoSuchElementException, IllegalArgumentException {
		if (material == null) {
			throw new IllegalArgumentException("The material reference is not initialized");
		}
		
		if (!mUsedMaterials.containsKey(material)) {
			throw new NoSuchElementException("There is no a material with the specified parameters in the specification");
		}
		
		mUsedMaterials.remove(material);
	}
	
	/**
	 * The method returns a reference to a product
	 * @return A reference to a product
	 */
	
	@Override 
	public IProduct GetProduct() {
		return mProduct;
	}
	
	/**
	 * The method returns a reference to a company-manufacturer
	 * @return A reference to a company-manufacturer
	 */
	
	@Override 
	public ICompany GetManufacturer() {
		return mManufacturer;
	}
	
	/**
	 * The method returns an approval date
	 * @return An approval date
	 */
	
	@Override 
	public Date GetApprovalDate() {
		return mApprovalDate;
	}
	
	/**
	 * The method returns a cancellation date
	 * @return A cancellation date
	 */
	
	@Override 
	public Date GetCancellationDate() {
		return mCancellationDate;
	}
	
	/**
	 * The method returns a year of production
	 * @return A year of production
	 */
	
	@Override 
	public Date GetYearOfProduction() {
		return mYearOfProduction;
	}
	
	/**
	 * The method returns an amount of a production
	 * @return An amount of a production
	 */
	
	@Override 
	public float GetProductionAmount() {
		return mProductionAmount;
	}
	
	/**
	 * The method returns a reference to a material if a specification contains the specified one.
	 * 
	 * @param name A material's name
	 * @return A reference to a material
	 */
	
	@Override 
	public IMaterial GetMaterialByName(String name) throws NoSuchElementException, IllegalArgumentException {
//		if (name == null) {
//			throw new IllegalArgumentException("The name couldn't equal to null");
//		}
//		
//		IMaterial foundMaterial = mUsedMaterials.keySet().
//										 parallelStream().
//										 filter(material -> material.GetName().equals(name)).findFirst().get();
//		
//		if 
//		
//		if (!mUsedMaterials.containsKey(material)) {
//			throw new NoSuchElementException("There is no a material with the specified parameters in the specification");
//		}
//		
//		return mUsedMaterials.get(material);
		return null; // NOT IMPLEMENTED YET
	}
	
	
	/**
	 * The method returns a used amount of the material with the specified name if the material
	 * is stored in the specification.
	 * @param material A reference to a material
	 * @return A positive value that describes a used amount of the material
	 */
	@Override 
	public float GetMaterialAmount(IMaterial material) throws NoSuchElementException, IllegalArgumentException {
		if (material == null) {
			throw new IllegalArgumentException("The material reference is not initialized");
		}
		
		if (!mUsedMaterials.containsKey(material)) {
			throw new NoSuchElementException("There is no a material with the specified parameters in the specification");
		}
		
		return mUsedMaterials.get(material);
	}
	
	@Override
	public String toString() {
		String resultString = "[";
		
		resultString += "Product : " + mProduct;
		resultString += ",\n Manufacturer : " + mManufacturer;
		resultString += ",\n Approved : " + mApprovalDate;
		resultString += ",\n Cancelled : " + mCancellationDate;
		resultString += ",\n ProductionYear : " + mYearOfProduction;
		resultString += ",\n Materials: {\n";

		for (Map.Entry<IMaterial, Float> entry : mUsedMaterials.entrySet())
		{
			resultString += "\t[ Name: " + entry.getKey();
			resultString += ", Amount: " + entry.getValue();
			resultString += "],\n";
		}

		resultString += "}";
		
		//add additional data here
		
		return resultString + "]";
	}
}
