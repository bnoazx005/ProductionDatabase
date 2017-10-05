/**
 * CProductRepository.java
 * @author Kasimov Ildar
 */

package production_database;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

/**
 * class CProductRepository
 * 
 * The class inherits CBaseRepository
 * 
 * @author Kasimov Ildar
 *
 */

public class CProductRepository extends CBaseRepository<CProduct> {

	public CProductRepository(ObjectContainer dbContext) {
		super(dbContext);
	}
	
	protected CProductRepository() {
	}

	/**
	 * The method returns a set of all stored entities
	 * @return A set of entities of the specified type that are stored in the database
	 */

	@Override
	public ObjectSet<CProduct> FindAll() {
		if (mDatabaseContext == null) {
			throw new IllegalArgumentException("A database's context object cannot equal to null");
		}

		return mDatabaseContext.queryByExample(CProduct.class);
	}
}
