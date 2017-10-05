/**
 * IBaseRepository.java
 * @author Kasimov Ildar 
 */

package production_database.interfaces;

import com.db4o.ObjectSet;

/**
 * interface IBaseRepository
 * 
 * The interface describes a basic repository functionality.
 * The basic repository provides create, delete, search and update methods
 * 
 * @author Kasimov Ildar
 *
 */
public interface IBaseRepository<T extends IBaseDBEntity> {
	/**
	 * The method creates a new entity in the database.
	 * The main difference that this method throws RuntimeException if there is an entity 
	 * with the specified name in the database.
	 * If you want just to update value of an existing entity use Update or CreateIfNotExists
	 * @param entity A reference to an entity
	 */
	
	void Create(T entity);
	
	/**
	 * The method creates a new entity if it hasn't existed yet
	 * If the entity already exists, the method does nothing
	 * @param entity A reference to an entity
	 * @return True if the entity was added into the database, false in other cases
	 */
	
	boolean CreateIfNotExists(T entity);
	
	/**
	 * The method updates an entity if it exists. If the specified entity doesn't exist
	 * the method throws exception.
	 * @param entity A reference to an entity
	 */
	
	void Update(T entity);
	
	/**
	 * The method removes the specified entity if it exists. The method throws an exception
	 * if there is no the specified entity.
	 * @param entity
	 */
	
	void Remove(T entity);
	
	/**
	 * The method returns a reference to an entity. 
	 * @param name
	 * @return A set of entities that corresponds the specified name
	 */
		
	ObjectSet<T> FindByName(String name);
	
	/**
	 * The method returns a set of all stored entities
	 * @return A set of entities of the specified type that are stored in the database
	 */
	
	ObjectSet<T> FindAll();
}
