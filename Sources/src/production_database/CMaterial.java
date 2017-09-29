/**
 * CMaterial.java
 * 
 * @author Kasimov Ildar
 */
package production_database;


import production_database.interfaces.*;


/**
 * class CMaterial
 * 
 * The class implements IMaterial and describes a material's functionality.
 * 
 * @author Kasimov Ildar
 */
public final class CMaterial extends CBaseDBEntity implements IMaterial {	
	private String          mUnits;
	
	private float           mPricePerUnit;
	
	private E_MATERIAL_TYPE mType;
	
	public CMaterial(String name, String units, float pricePerUnit, E_MATERIAL_TYPE type) {
		super(name);
		
		mUnits = units;
		
		mPricePerUnit = pricePerUnit;
		
		mType = type;
	}

	@SuppressWarnings("unused")
	private CMaterial() {
	}
		
	/**
	 * The method sets up a string, which defines a units of measurement
	 * @param units 
	 */
	
	@Override
	public void SetUnits(String units) {
		mUnits = units;
	}
	
	/**
	 * The method sets up a price's value per unit
	 * @param pricePerUnit
	 */
	
	@Override
	public void SetPricePerUnit(float pricePerUnit) {
		mPricePerUnit = pricePerUnit;
	}
	
	/**
	 * The method sets up a type of a material
	 * @param type Some value from E_MATERIAL_TYPE enumeration
	 */
	
	@Override
	public void SetType(E_MATERIAL_TYPE type) {
		mType = type;
	}
		
	/**
	 * The method returns a string, which contains units of measurement
	 * @return A string, which contains units of measurement
	 */
	
	@Override
	public String GetUnits() {
		return mUnits;
	}
	
	/**
	 * The method returns a price of a single unit of a material
	 * @return A price of a single unit of a material
	 */
	
	@Override
	public float GetPricePerUnit() {
		return mPricePerUnit;
	}
	
	/**
	 * The method returns a type of a material
	 * @return A value from E_MATERIAL_TYPE enumeration
	 */
	
	@Override
	public E_MATERIAL_TYPE GetType() {
		return mType;
	}
	
	@Override
	public String toString() {
		String resultString = "[" + mName;
		
		resultString += ", Price Per Unit: " + mPricePerUnit + "(" + mUnits + ")";		
		resultString += ", Type: " + mType;
		
		return resultString + "]";
	}
}
