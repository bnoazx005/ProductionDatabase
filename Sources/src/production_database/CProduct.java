/**
 * CProduct.java
 * 
 * @author Kasimov Ildar
 */

package production_database;

import production_database.interfaces.*;


/**
 * class CProduct
 * 
 * The class represents a product and implements IProduct interface.
 * 
 * @author Kasimov Ildar
 */

public final class CProduct extends CBaseDBEntity implements IProduct {	
	private String  mCode;
	
	private boolean mIsStandard;
	
	private float   mAmountPerYear;
	
	private String  mAdditionalNote;
	
	/**
	 * The main constructor 
	 * @param name A name of a product
	 * @param code A code of a product
	 * @param isStandard Is a product standard
	 * @param amountPerYear An amount of product's production per year
	 * @param note An additional notes about product
	 */
	
	public CProduct(String name, String code, boolean isStandard, float amountPerYear, String note) {
		super(name);
		
		mCode = code;
		
		mIsStandard = isStandard;
		
		mAmountPerYear = amountPerYear;
		
		mAdditionalNote = note;
	}
	
	/**
	 * The simplified constructor with two parameters
	 * 
	 * It provides to sets up a name and a code of a product. By default mIsStandard field will set up to false,
	 * and mAmountPerYear will be equal to 0.0f
	 * @param name A name of a product
	 * @param code A code of a product
	 */
	
	public CProduct(String name, String code) {
		super(name);
		
		mCode = code;
		
		mIsStandard = false;
		
		mAmountPerYear = 0.0f;
	}
	
	/**
	 * Default constructor is not used
	 */
	
	@SuppressWarnings("unused")
	private CProduct() {		
	}
	
	/**
	 * The method sets up a code of a product
	 * @param code A string that contains code's value
	 */
	
	@Override 
	public void SetCode(String code) {
		mCode = code;
	}
	
	/**
	 * The method specifies is a product standard or not
	 * @param value A type of a product (true is a standard one, false is not)
	 */
	
	@Override 
	public void IsStandard(boolean value) {
		mIsStandard = value;
	}
		
	/**
	 * The method sets up a production amount per year
	 * @param amountPerYear A positive floating-point value that describes a production
	 *  amount of a product per year
	 */
	
	@Override 
	public void SetAmountPerYear(float amountPerYear) {
		mAmountPerYear = amountPerYear;
	}
	
	/**
	 * The method sets up an additional notes for a product
	 * @param note A string with a comment or special notes about a product
	 */
	
	@Override 
	public void SetNote(String note) {
		mAdditionalNote = note;
	}
	
	/**
	 * The method returns a code of a product
	 * @return A string with a product's code
	 */
	
	@Override 
	public String GetCode() {
		return mCode;
	}
	
	/**
	 * The method returns a type of a product
	 * @return The method returns a true if a product is standard and false in other case
	 */
	
	@Override 
	public boolean IsStandard() {
		return mIsStandard;
	}
	/**
	 * The method returns a value of production amount per year
	 * @return a value of product's production amount per year
	 */
	
	@Override 
	public float GetAmountPerYear() {
		return mAmountPerYear;
	}
			
	/**
	 * The method returns a string with additional notes
	 * @return A string with product's notes
	 */
	
	@Override 
	public String GetNote() {
		return mAdditionalNote;
	}
	
	@Override
	public String toString() {
		String resultString = "[" + mName;
		
		if (mCode != null) {
			resultString += ", Code: (" + mCode + ")";
		}

		resultString += ", Amount Per Year: " + mAmountPerYear;		
		resultString += ", IsStandard: " + (mIsStandard ? "Yes" : "No");
		
		return resultString + "]";
	}
}
