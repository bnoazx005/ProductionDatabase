/**
 * E_MATERIAL_TYPE.java
 * 
 * @author Kasimov Ildar
 */
package production_database.interfaces;

/**
 * Enumeration E_MATERIAL_TYPE
 * 
 * The enumeration contains a types of all possible materials
 * @author Kasimov Ildar
 *
 */
public enum E_MATERIAL_TYPE {
    MT_MAPLE("Maple"),
    MT_EBONY("Ebony"),
    MT_MAHOGANY("Mahogany"),
    MT_ROSEWOOD("Rosewood"),
    MT_COPPER("Cooper"),
    MT_PLASTIC("Plastic"),
    MT_ALUMINIUM("Aluminium"),
    MT_STEEL("Steel"),
    MT_MAGNET("Magnet"),
    MT_IVORY("Ivory"),
    MT_PEARL("Pearl");

    private String mMaterialName;
    
    E_MATERIAL_TYPE(String name) {
	mMaterialName = name;
    }
    
    @Override
    public String toString() {
	return mMaterialName;
    }
}
