package Tools;

import java.util.HashMap;


public final class TableSymboles {

	private static final HashMap<String, String> _strings = new HashMap<String, String>();
	
	/**
	 * Return the value corresponding to the given key, looking for its value rather than its object
	 * @param hash
	 * @param key
	 * @return
	 */
	public static String get(String strKey){
		for(String key : _strings.keySet()){
			if(key.equals(strKey))
				return _strings.get(key);
		}
		
		_strings.put(strKey, strKey);
		
		return strKey;
	}
	
}
