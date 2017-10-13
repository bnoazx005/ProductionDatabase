/**
 * IProduct.java
 * 
 * @author Kasimov Ildar
 */
package production_database.interfaces;

/**
 * Interface IProduct
 * 
 * The interface describes a single product's functionality
 * 
 * @author Kasimov Ildar
 */
public interface IProduct extends IBaseDBEntity {
    /**
     * The method sets up a code of a product
     * @param code A string that contains code's value
     */ 
    void SetCode(String code);
    
    /**
     * The method specifies is a product standard or not
     * @param value A type of a product (true is a standard one, false is not)
     */ 
    void IsStandard(boolean value);
        
    /**
     * The method sets up a production amount per year
     * @param amountPerYear A positive floating-point value that 
     * describes a production amount of a product per year
     */ 
    void SetAmountPerYear(float amountPerYear);
    
    /**
     * The method sets up an additional notes for a product
     * @param note A string with a comment or special notes about a product
     */ 
    void SetNote(String note);
        
    /**
     * The method returns a code of a product
     * @return A string with a product's code
     */ 
    String GetCode();
    
    /**
     * The method returns a type of a product
     * @return The method returns a true if a product is standard and 
     * false in other case
     */ 
    boolean IsStandard();
        
    /**
     * The method returns a value of production amount per year
     * @return a value of product's production amount per year
     */
    float GetAmountPerYear();
            
    /**
     * The method returns a string with additional notes
     * @return A string with product's notes
     */ 
    String GetNote();
}
