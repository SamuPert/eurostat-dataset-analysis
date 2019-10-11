package univpm.oopproject;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public abstract class Utils {

	public static JSONObject isFilterValid( JSONObject filtersJSON )
	{
		int annoMinimo = Dataset.getAnnoMinimo();
		int annoMassimo = Dataset.getAnnoMassimo();
		
		List<String> validFields = new ArrayList<String>();
		validFields.add("Wstatus");
		validFields.add("Sex");
		validFields.add("IndicIl");
		validFields.add("Age");
		validFields.add("Country");
		validFields.add("Wstatus");
		for(int w1 = annoMinimo; w1 < annoMassimo + 1 ; w1++)
		{
			validFields.add( String.valueOf(w1) );	
		}
		
		JSONObject dataFilteredJSON = new JSONObject();
		
		// Validate JSON input
		Object[] keys = filtersJSON.keySet().toArray();
		for( int i = 0; i < keys.length; i++ )
		{
			Object k = keys[i];
			
			if(! (k instanceof String ) )
			{
				dataFilteredJSON.put("Error", "Almeno una chiave dell'oggetto non è valida.");
				dataFilteredJSON.put("InvalidKey", k);
				return dataFilteredJSON;
			}

			String fieldFilterAttribute = (String)k;			

			// Check if key is between annoMinimo and annoMassimo
			try {
				int number_key = Integer.parseInt( fieldFilterAttribute );
				if( annoMinimo <= number_key && number_key <= annoMassimo )
				{
					// OK, Valid key. Check if argument is valid.
					Object obj = filtersJSON.get(k);
					
					if(!( obj instanceof JSONObject) )
					{
						dataFilteredJSON.put("Error", "Filtro non valido.");
						dataFilteredJSON.put("InvalidKey", obj);
						return dataFilteredJSON;
					}
					
					JSONObject filterJson;
					filterJson = (JSONObject) obj;
					
					Object[] keysInner = filterJson.keySet().toArray();
					for( int j = 0; j < keysInner.length; j++ )
					{
						Object kInner = keysInner[j];
						
						if(! (kInner instanceof String ) )	
						{
							dataFilteredJSON.put("Error", "Almeno una chiave dell'oggetto non è valida.");
							dataFilteredJSON.put("InvalidKey", kInner);
							return dataFilteredJSON;
						}
						String keyInnerString = (String)kInner;
						switch(keyInnerString)
						{
							case "$gt":
							case "$gte":
							case "$lt":
							case "$lte":
								if( !( filterJson.get(kInner) instanceof Double || filterJson.get(kInner) instanceof Long) )
								{
									dataFilteredJSON.put("Error", "Non è possibile applicare il filtro \""+keyInnerString+"\" con valore \""+String.valueOf(filterJson.get(kInner))+"\" al campo \""+fieldFilterAttribute+"\".");
									dataFilteredJSON.put("InvalidFilter", filterJson.get(kInner));
									return dataFilteredJSON;									
								}
								continue;
								
							case "$bt":
								
								if( filterJson.get(kInner) instanceof JSONArray )
								{
									if( ((JSONArray)filterJson.get(kInner)).size() == 2 &&
										(
												((JSONArray)filterJson.get(kInner)).toArray()[0] instanceof Long ||
												((JSONArray)filterJson.get(kInner)).toArray()[0] instanceof Double
										) && (
												((JSONArray)filterJson.get(kInner)).toArray()[1] instanceof Long ||
												((JSONArray)filterJson.get(kInner)).toArray()[1] instanceof Double
										)
									)
									{
										continue;
									}
									dataFilteredJSON.put("Error", "Non è possibile applicare il filtro \""+keyInnerString+"\" con valore \""+String.valueOf(filterJson.get(kInner))+"\" al campo \""+fieldFilterAttribute+"\".");
									dataFilteredJSON.put("InvalidFilter", filterJson.get(kInner));
									return dataFilteredJSON;	
								}

								dataFilteredJSON.put("Error", "Non è possibile applicare il filtro \""+keyInnerString+"\" con valore \""+String.valueOf(filterJson.get(kInner))+"\" al campo \""+fieldFilterAttribute+"\".");
								dataFilteredJSON.put("InvalidFilter", filterJson.get(kInner));
								return dataFilteredJSON;									
								

								
							default:
								dataFilteredJSON.put("Error", "Non è possibile applicare il filtro \""+keyInnerString+"\" al campo \""+fieldFilterAttribute+"\".");
								dataFilteredJSON.put("InvalidFilter", keyInnerString);
								return dataFilteredJSON;
						}
					}
				}
			} catch (NumberFormatException e){
				// OK, let's check if other 
			}
			
			switch( fieldFilterAttribute )
			{
				case "$or":
				case "$and":
					// {"$or": [{"status": "GOLD"}, {"status": "SILVER"}]}
					JSONArray filterJsonArr;
					if(! (filtersJSON.get(k) instanceof JSONArray ) )	
					{
						dataFilteredJSON.put("Error", "Almeno un valore dell'oggetto non è valido.");
						dataFilteredJSON.put("InvalidKey", filtersJSON.get(k));
						return dataFilteredJSON;
					}
					
					
					filterJsonArr = (JSONArray) filtersJSON.get(k);
					
					Object[] elementsInner = filterJsonArr.toArray();
					for( int j = 0; j < elementsInner.length; j++ )
					{
						Object elementInner = elementsInner[j];
						JSONObject innerElement;
						if(! (elementInner instanceof JSONObject ) )	
						{
							dataFilteredJSON.put("Error", "Almeno un valore dell'oggetto non è valido.");
							dataFilteredJSON.put("InvalidKey", elementInner);
							return dataFilteredJSON;
						}
						
						innerElement = (JSONObject)elementInner;
						
						Object[] keysInner = innerElement.keySet().toArray();
						for( int w = 0; w < keysInner.length; w++ )
						{
							if(! (keysInner[w] instanceof String ) )	
							{
								dataFilteredJSON.put("Error", "Almeno una chiave dell'oggetto non è valida.");
								dataFilteredJSON.put("InvalidKey", keysInner);
								return dataFilteredJSON;
							}
							
							
							if( ! validFields.contains(keysInner) )
							{
								dataFilteredJSON.put("Error", "Chiave dell'oggetto non presente tra gli attributi.");
								dataFilteredJSON.put("InvalidKey", keysInner);
								return dataFilteredJSON;
							}
						}
						
					}
					break;
			
				case "Wstatus":
				case "IndicIl":
				case "Sex":
				case "EtaRange":
						// { "wstatus" : {"$not" : "1" } }
						// {"field" : {"$in" : [value1, value2, ...]}}
						// {"field" : {"$nin" : [value1, value2, ...]}}
						// {"field" : {"$or" : [ "A", "B", "C" ]    }}
						// {"field" : {"$and" : [ "A", "B", "C" ]    }}
					
						// OK, Valid key. Check if argument is valid.
						JSONObject filterJson;
						if(! (filtersJSON.get(k) instanceof JSONObject ) )	
						{
							dataFilteredJSON.put("Error", "Almeno un valore dell'oggetto non è valido.");
							dataFilteredJSON.put("InvalidKey", filtersJSON.get(k));
							return dataFilteredJSON;
						}
						
						filterJson = (JSONObject) filtersJSON.get(k);
						
						Object[] keysInner = filterJson.keySet().toArray();
						for( int j = 0; j < keysInner.length; j++ )
						{
							Object kInner = keysInner[j];
							
							if(! (kInner instanceof String ) )	
							{
								dataFilteredJSON.put("Error", "Almeno una chiave dell'oggetto non è valida.");
								dataFilteredJSON.put("InvalidKey", kInner);
								return dataFilteredJSON;
							}
							String keyInnerString = (String)kInner;
							switch(keyInnerString)
							{
								case "$not":
									if( !( filterJson.get(kInner) instanceof String) )
									{
										dataFilteredJSON.put("Error", "Non è possibile applicare il filtro \""+keyInnerString+"\" con valore \""+String.valueOf(filterJson.get(kInner))+"\" al campo \""+fieldFilterAttribute+"\".");
										dataFilteredJSON.put("InvalidFilter", filterJson.get(kInner));
										return dataFilteredJSON;									
									}
									continue;
									
								case "$in":
								case "$nin":
									if( !( filterJson.get(kInner) instanceof JSONArray) )
									{
										dataFilteredJSON.put("Error", "Non è possibile applicare il filtro \""+keyInnerString+"\" con valore \""+String.valueOf(filterJson.get(kInner))+"\" al campo \""+fieldFilterAttribute+"\".");
										dataFilteredJSON.put("InvalidFilter", filterJson.get(kInner));
										return dataFilteredJSON;									
									}
									continue;
								
								default:
									dataFilteredJSON.put("Error", "Non è possibile applicare il filtro \""+keyInnerString+"\" al campo \""+fieldFilterAttribute+"\".");
									dataFilteredJSON.put("InvalidFilter", keyInnerString);
									return dataFilteredJSON;
							}
						}
					
					break;
					
				/*
					// Number here
					
					break;
				*/
			}

		}
				
		dataFilteredJSON.put("Success", true);
		
		return dataFilteredJSON;
	}
}
