/**
 * CBaseDBEntity.java
 * 
 * @author Kasimov Ildar
 */
package production_database;


import production_database.interfaces.IBaseDBEntity;

/**
 * class CBaseDBEntity
 * 
 * The class implements IBaseDBEntity
 * 
 * @author Ildar
 *
 */

public class CBaseDBEntity implements IBaseDBEntity {
	protected String mName;
	
	/**
	 * The main constructor with a single parameter
	 * @param name An entity's name
	 */
	
	public CBaseDBEntity(String name) {
		mName = name;
	}
	
	/**
	 * Default constructor is not used
	 */
	
	protected CBaseDBEntity() {
	}
	
	/**
	 * The method sets up a name of a product
	 * @param name A name of a product
	 */
	
	@Override 
	public void SetName(String name) {
		mName = name;		
	}
	
	/**
	 * The method returns a name of a product
	 * @return A string with a name of a product
	 */
	
	@Override 
	public String GetName() {
		return mName;
	}
}
