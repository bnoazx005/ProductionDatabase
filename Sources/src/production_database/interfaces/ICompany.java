/**
 * ICompany.java
 * 
 * @author Kasimov Ildar
 */
package production_database.interfaces;

/**
 * interface ICompany
 * The interface describes a single company's functionality
 * 
 * @author Kasimov Ildar
 *
 */
public interface ICompany extends IBaseDBEntity {   
    /**
     * The method sets up a code of a company
     * @param code A string that contains code's value
     */ 
    void SetCode(String code);
    
    /**
     * The method sets up a string, which contains a phone of a company
     * @param phone A string with a company's phone
     */ 
    void SetPhone(String phone);
    
    /**
     * The method sets up an address of a company
     * @param address A string with a company's address
     */ 
    void SetAddress(String address);
        
    /**
     * The method returns a code of a company
     * @return A string with a company's code
     */ 
    String GetCode();
    
    /**
     * The method returns a string with a company's phone
     * @return A string, which contains a company's phone
     */ 
    String GetPhone();
    
    /**
     * The method returns a string with a company's address
     * @return A string, which contains a company's address
     */ 
    String GetAddress();
}
