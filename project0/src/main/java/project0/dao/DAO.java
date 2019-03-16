package project0.dao;



public interface DAO<T> {
	/**
	 * 
	 * @param t
	 * @return returns the newly created object back to service.
	 */
	T add(T t);
	/**
	 * 
	 * @param t
	 * @return returns newly updated object back to service layer.
	 */
	T update(T t);
	/**
	 * 
	 * @param t
	 * @return returns true if the object inputed has been removed from the database.
	 */
	boolean delete(T t);
		
}
