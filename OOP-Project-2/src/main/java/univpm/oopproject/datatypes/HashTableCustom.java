package univpm.oopproject.datatypes;
import java.util.Hashtable;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe personalizzata per una gestione ottimizzata della HashTable. 
 * @author Samuele Perticarari e Martina Rossi
 *
 * @param <K> Chiave
 * @param <V> Valore
 */
public class HashTableCustom <K, V> extends Hashtable {

	/**
	 * Aggiorna il valore associato ad una chiave
	 * @param key Chiave nella quale si vuole modificare il valore
	 * @param newValue Il valore che assumerà la nuova chiave.
	 */
	public void set (K key, V newValue) {
		this.remove(key);
		this.put(key, newValue);
	}
	
	/**
	 * Metodo che crea la chiave nel caso in cui 
	 * questa non esista, altrimenti la incrementa
	 * nel caso in cui ne esista già una.
	 * @param key Chiave
	 */
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
	
	/**
	 * Metodo che crea un array di oggetti di tipo JSON 
	 * in cui inserisce la coppia Valore-Conteggio della 
	 * chiave.
	 * @return L'array creato
	 */
	public JSONArray getJSONValues()
	{
		JSONArray json = new JSONArray();
		Set<String> keyset = this.keySet();
		
		for (String key : keyset ) {
			JSONObject jobj = new JSONObject ();
			jobj.put("Valore", key);
			jobj.put("Conteggio", this.get(key));
			json.add(jobj);
		}	
		return json;
	}

}
