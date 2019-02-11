package thdl.collections;


import java.util.HashMap;


public class Collection<K1, K2, K3, V>
{

	private HashMap<K1, V>	firstKeyValueHash;
	private HashMap<K2, V>	secondKeyValueHash;
	private HashMap<K3, V>	thirdKeyValueHash;

	public Collection()
	{
		firstKeyValueHash = new HashMap<K1, V>();
		secondKeyValueHash = new HashMap<K2, V>();
		thirdKeyValueHash = new HashMap<K3, V>();
	}

	/**
	 * Adds an Object to the collection, if its not already in it
	 * 
	 * @param value
	 *            the object-value to add to the collection
	 * @param key1
	 *            the first key value
	 * @param key2
	 *            the second key value
	 * @param key3
	 *            the third key value
	 * @return
	 * 		if the object is already in the collections
	 */
	public boolean add(V value, K1 key1, K2 key2, K3 key3)
	{
		/**
		 * True, if newly added to Collection // False, if already in Collection
		 */
		if (firstKeyValueHash.containsValue(value) || secondKeyValueHash.containsValue(value)
				|| thirdKeyValueHash.containsValue(value))
		{
			return false;
		}
		else
		{
			firstKeyValueHash.put(key1, value);
			secondKeyValueHash.put(key2, value);
			thirdKeyValueHash.put(key3, value);
			return true;
		}
	}

	/**
	 * Get an object of the collection by its first key
	 * 
	 * @param key
	 * @return
	 */
	public V getByFirstKey(K1 key)
	{
		V val = null;
		val = firstKeyValueHash.get(key);
		return val;
	}

	/**
	 * Get an object of the collection by its second key
	 * 
	 * @param key
	 * @return
	 */
	public V getBySecondKey(K2 key)
	{
		V val = null;
		val = secondKeyValueHash.get(key);
		return val;
	}

	/**
	 * Get an object of the collection by its third key
	 * 
	 * @param key
	 * @return
	 */
	public V getByThirdKey(K3 key)
	{
		V val = null;
		val = thirdKeyValueHash.get(key);
		return val;
	}

	/**
	 * Returns if there is an Object with this key in the collection
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsFirstKey(K1 key)
	{
		return firstKeyValueHash.containsKey(key);
	}

	/**
	 * Returns if there is an Object with this key in the collection
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsSecondKey(K2 key)
	{
		return secondKeyValueHash.containsKey(key);
	}

	/**
	 * Returns if there is an Object with this key in the collection
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsThirdKey(K3 key)
	{
		return thirdKeyValueHash.containsKey(key);
	}

	/**
	 * Are there any values in the collection?
	 * 
	 * @return
	 */
	public boolean isEmpty()
	{
		if (firstKeyValueHash.isEmpty() || secondKeyValueHash.isEmpty() || thirdKeyValueHash.isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Removes all values from the collection
	 */
	public void clear()
	{
		firstKeyValueHash.clear();
		secondKeyValueHash.clear();
		thirdKeyValueHash.clear();
	}
}
