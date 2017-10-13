/**
 * CMaterialRepository.java
 * @author Kasimov Ildar
 */
package production_database;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

/**
 * class CMaterialRepository
 * The class inherits CBaseRepository
 * @author Kasimov Ildar
 */
public class CMaterialRepository extends CBaseRepository<CMaterial> {

    public CMaterialRepository(ObjectContainer dbContext) {
        super(dbContext);
    }
    
    protected CMaterialRepository() {
    }

    /**
     * The method returns a set of all stored entities
     * @return A set of entities of the specified type that are stored 
     * in the database
     */
    @Override
    public ObjectSet<CMaterial> FindAll() {
        if (mDatabaseContext == null) {
            throw new IllegalArgumentException(
        	    "A database's context object cannot equal to null");
        }

        return mDatabaseContext.queryByExample(CMaterial.class);
    }
}
