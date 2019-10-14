package univpm.oopproject.datatypes;
import java.util.Hashtable;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class HashTableCustom <K, V> extends Hashtable {

	
	public void set (K key, V newValue) {
		this.remove(key);
		this.put(key, newValue);
	}
	
	//metodo per la creazione o l'incremento della chiave
	public void setOrInc (K key) {
		
		if(!(key instanceof String)) return;
		
		if(this.containsKey(key)) {
			Object oldValueGeneric = this.get(key);
			if(!(oldValueGeneric instanceof Integer)) return;
			Integer newValue = (Integer)oldValueGeneric;
			newValue++;
			this.set(key, (V) newValue);
		}
		else {
			this.set(key, (V) new Integer(1));
		}
	}
	
	public JSONArray getJSONValues()
	{
		JSONArray json = new JSONArray();
		Set<String> keyset = this.keySet();
		
		for (String key : keyset ) {
			JSONObject jobj = new JSONObject ();
			jobj.put("Value", key);
			jobj.put("Count", this.get(key));
			json.add(jobj);
		}	
		return json;
	}

}
