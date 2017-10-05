/**
 * CBaseRepository.java
 * @author Kasimov Ildar
 */
package production_database;


import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

import production_database.interfaces.IBaseDBEntity;
import production_database.interfaces.IBaseRepository;

/**
 * class CBaseRepository
 * 
 * The generic class that represent a base repository in the project
 * 
 * @author Kasimov Ildar
 *
 */
public abstract class CBaseRepository<T extends IBaseDBEntity> implements IBaseRepository<T>  {
	protected ObjectContainer mDatabaseContext;

	public CBaseRepository(ObjectContainer dbContext) {
		if (dbContext == null) {
			throw new IllegalArgumentException("A database's context object cannot equal to null");
		}
		
		mDatabaseContext = dbContext;
	}
	
	protected CBaseRepository() {
	}
	
	/**
	 * The method creates a new entity in the database.
	 * The main difference that this method throws RuntimeException if there is an entity 
	 * with the specified name in the database.
	 * If you want just to update value of an existing entity use Update or CreateIfNotExists
	 * @param entity A reference to an entity
	 */
	
	public void Create(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("An input argument cannot equal to null");
		}
		
		if (mDatabaseContext == null) {
			throw new IllegalArgumentException("A database's context object cannot equal to null");
		}
		
		ObjectSet<T> existingEntities = FindByName(entity.GetName());
		
		if (existingEntities.size() > 0) {
			throw new RuntimeException("The specified entity already exists in the database");
		}
		
		mDatabaseContext.store(entity);
	}
	
	/**
	 * The method creates a new entity if it hasn't existed yet
	 * If the entity already exists, the method does nothing
	 * @param entity A reference to an entity
	 * @return True if the entity was added into the database, false in other cases
	 */
	
	public boolean CreateIfNotExists(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("An input argument cannot equal to null");
		}
		
		if (mDatabaseContext == null) {
			throw new IllegalArgumentException("A database's context object cannot equal to null");
		}
		
		ObjectSet<T> existingEntities = FindByName(entity.GetName());
		
		if (existingEntities.size() > 0) {
			return false;
		}
		
		mDatabaseContext.store(entity);
		
		return true;
	}
	
	/**
	 * The method updates an entity if it exists. If the specified entity doesn't exist
	 * the method throws exception.
	 * @param entity A reference to an entity
	 */
	
	public void Update(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("An input argument cannot equal to null");
		}
		
		if (mDatabaseContext == null) {
			throw new IllegalArgumentException("A database's context object cannot equal to null");
		}
		
		ObjectSet<T> existingEntities = FindByName(entity.GetName());
		
		if (existingEntities.size() < 1) {
			throw new RuntimeException("The specified entity doesn't exist in the database");
		}
		
		mDatabaseContext.store(entity);
	}
	
	/**
	 * The method removes the specified entity if it exists. The method throws an exception
	 * if there is no the specified entity.
	 * @param entity
	 */
	
	public void Remove(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException("An input argument cannot equal to null");
		}
		
		if (mDatabaseContext == null) {
			throw new IllegalArgumentException("A database's context object cannot equal to null");
		}
		
		mDatabaseContext.delete(entity);
	}
	
	/**
	 * The method returns a reference to an entity. 
	 * @param name
	 * @return A set of entities that corresponds the specified name
	 */
	
	public ObjectSet<T> FindByName(String name) {
		if (mDatabaseContext == null) {
			throw new IllegalArgumentException("A database's context object cannot equal to null");
		}
		
		Predicate<T> queryPredicate = new Predicate<T>() {
			public boolean match(T entity) {
				//compare the name of a current entity with the specified one
				//сравниваем имя текущей сущности с указанным параметром
				return entity.GetName().equals(name); 
			}
		};

		return mDatabaseContext.query(queryPredicate);
	}
	
	/**
	 * The method returns a set of all stored entities
	 * @return A set of entities of the specified type that are stored in the database
	 */
	
	public abstract ObjectSet<T> FindAll();
}
