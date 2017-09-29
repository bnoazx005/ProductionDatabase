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
public final class CMaterial implements IMaterial {
	private String          mName;
	
	private String          mUnits;
	
	private float           mPricePerUnit;
	
	private E_MATERIAL_TYPE mType;
	
	public CMaterial(String name, String units, float pricePerUnit, E_MATERIAL_TYPE type) {
		mName  = name;
		mUnits = units;
		
		mPricePerUnit = pricePerUnit;
		
		mType = type;
	}

	@SuppressWarnings("unused")
	private CMaterial() {
	}
	
	/**
	 * The method sets up a name of a material
	 * @param name A name of a material
	 */
	
	@Override
	public void SetName(String name) {
		
	}
	
	/**
	 * The method sets up a string, which defines a units of measurement
	 * @param units 
	 */
	
	@Override
	public void SetUnits(String units) {
		
	}
	
	/**
	 * The method sets up a price's value per unit
	 * @param pricePerUnit
	 */
	
	@Override
	public void SetPricePerUnit(float pricePerUnit) {
		
	}
	
	/**
	 * The method sets up a type of a material
	 * @param type Some value from E_MATERIAL_TYPE enumeration
	 */
	
	@Override
	public void SetType(E_MATERIAL_TYPE type) {
		
	}
	
	/**
	 * The method returns a name of a material
	 * @return A string with a name of a material
	 */
	
	@Override
	public String GetName() {
		return null;
	}
	
	/**
	 * The method returns a string, which contains units of measurement
	 * @return A string, which contains units of measurement
	 */
	
	@Override
	public String GetUnits() {
		return null;
	}
	
	/**
	 * The method returns a price of a single unit of a material
	 * @return A price of a single unit of a material
	 */
	
	@Override
	public float GetPricePerUnit() {
		return 0.0f;
	}
	
	/**
	 * The method returns a type of a material
	 * @return A value from E_MATERIAL_TYPE enumeration
	 */
	
	@Override
	public E_MATERIAL_TYPE GetType() {
		return E_MATERIAL_TYPE.MT_MATERIAL1;
	}
}
