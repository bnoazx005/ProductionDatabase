/**
 * IBaseDBEntity.java
 * 
 * @author Kasimov Ildar
 */
package production_database.interfaces;

/**
 * interface IBaseDBEntity
 * 
 * The interface describes a basic database entity
 * @author Kasimov Ildar
 *
 */
public interface IBaseDBEntity {
    /**
     * The method sets up a name of a material
     * @param name A name of a material
     */ 
    void SetName(String name);
    
    /**
     * The method returns a name of an entity
     * @return A string with a name of an entity
     */ 
    String GetName();
}
