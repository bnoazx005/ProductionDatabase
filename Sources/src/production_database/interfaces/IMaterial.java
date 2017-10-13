/**
 * @author Kasimov Ildar
 */
package production_database.interfaces;


/**
 * interface IMaterial
 * 
 * The interface describes a material's functionality
 * @author Kasimov Ildar
 *
 */
public interface IMaterial extends IBaseDBEntity {
    /**
     * The method sets up a string, which defines a units of measurement
     * @param units 
     */
    void SetUnits(String units);
    
    /**
     * The method sets up a price's value per unit
     * @param pricePerUnit
     */
    void SetPricePerUnit(float pricePerUnit);
    
    /**
     * The method sets up a type of a material
     * @param type Some value from E_MATERIAL_TYPE enumeration
     */
    void SetType(E_MATERIAL_TYPE type);
    
    /**
     * The method returns a string, which contains units of measurement
     * @return A string, which contains units of measurement
     */
    String GetUnits();
    
    /**
     * The method returns a price of a single unit of a material
     * @return A price of a single unit of a material
     */
    float GetPricePerUnit();
    
    /**
     * The method returns a type of a material
     * @return A value from E_MATERIAL_TYPE enumeration
     */
    E_MATERIAL_TYPE GetType();
}
