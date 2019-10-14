package univpm.oopproject.utils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import univpm.oopproject.dataset.Dataset;


/**
 * Classe astratta (non istanziabile) per le funzioni di utilizzo generale
 * @author Samuele Perticarari e Martina Rossi
 *
 */
public abstract class Utils {

	/**
	 * Metodo che controlla la validità di un filtro in JSON.
	 * @param filtroJSON Filtro in formato JSON.
	 * @return Restituisce true se il filtro è valido, altrimenti false.
	 */
	public static JSONObject isFilterValid( JSONObject filtroJSON )
	{
		int annoMinimo = Dataset.getAnnoMinimo();
		int annoMassimo = Dataset.getAnnoMassimo();
		
		List<String> validFields = Utils.getValidFields();
		
		JSONObject dataFilteredJSON = new JSONObject();
		
		// Validate JSON input
		Object[] keys = filtroJSON.keySet().toArray();
		for( int i = 0; i < keys.length; i++ )
		{
			Object k = keys[i];
			
			if(! (k instanceof String ) )
			{
				dataFilteredJSON.put("Errore", "Almeno una chiave dell'oggetto non è valida.");
				dataFilteredJSON.put("ChiaveNonValida", k);
				return dataFilteredJSON;
			}

			String fieldFilterAttribute = (String)k;			

			// Check if key is between annoMinimo and annoMassimo
			try {
				int number_key = Integer.parseInt( fieldFilterAttribute );
				if( annoMinimo <= number_key && number_key <= annoMassimo )
				{
					// OK, Valid key. Check if argument is valid.
					Object obj = filtroJSON.get(k);
					
					if(!( obj instanceof JSONObject) )
					{
						dataFilteredJSON.put("Errore", "Filtro non valido.");
						dataFilteredJSON.put("ChiaveNonValida", obj);
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
							dataFilteredJSON.put("Errore", "Almeno una chiave dell'oggetto non è valida.");
							dataFilteredJSON.put("ChiaveNonValida", kInner);
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
									dataFilteredJSON.put("Errore", "Non è possibile applicare il filtro \""+keyInnerString+"\" con valore \""+String.valueOf(filterJson.get(kInner))+"\" al campo \""+fieldFilterAttribute+"\".");
									dataFilteredJSON.put("ChiaveNonValida", filterJson.get(kInner));
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
									dataFilteredJSON.put("Errore", "Non è possibile applicare il filtro \""+keyInnerString+"\" con valore \""+String.valueOf(filterJson.get(kInner))+"\" al campo \""+fieldFilterAttribute+"\".");
									dataFilteredJSON.put("ChiaveNonValida", filterJson.get(kInner));
									return dataFilteredJSON;	
								}

								dataFilteredJSON.put("Errore", "Non è possibile applicare il filtro \""+keyInnerString+"\" con valore \""+String.valueOf(filterJson.get(kInner))+"\" al campo \""+fieldFilterAttribute+"\".");
								dataFilteredJSON.put("ChiaveNonValida", filterJson.get(kInner));
								return dataFilteredJSON;									
								

								
							default:
								dataFilteredJSON.put("Errore", "Non è possibile applicare il filtro \""+keyInnerString+"\" al campo \""+fieldFilterAttribute+"\".");
								dataFilteredJSON.put("ChiaveNonValida", keyInnerString);
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
					if(! (filtroJSON.get(k) instanceof JSONArray ) )	
					{
						dataFilteredJSON.put("Errore", "Almeno un valore dell'oggetto non è valido.");
						dataFilteredJSON.put("ChiaveNonValida", filtroJSON.get(k));
						return dataFilteredJSON;
					}
					
					
					filterJsonArr = (JSONArray) filtroJSON.get(k);
					
					Object[] elementsInner = filterJsonArr.toArray();
					for( int j = 0; j < elementsInner.length; j++ )
					{
						Object elementInner = elementsInner[j];
						JSONObject innerElement;
						if(! (elementInner instanceof JSONObject ) )	
						{
							dataFilteredJSON.put("Errore", "Almeno un valore dell'oggetto non è valido.");
							dataFilteredJSON.put("ChiaveNonValida", elementInner);
							return dataFilteredJSON;
						}
						
						innerElement = (JSONObject)elementInner;
						
						Object[] keysInner = innerElement.keySet().toArray();
						for( int w = 0; w < keysInner.length; w++ )
						{
							if(! (keysInner[w] instanceof String ) )	
							{
								dataFilteredJSON.put("Errore", "Almeno una chiave dell'oggetto non è valida.");
								dataFilteredJSON.put("InvalidKey", keysInner);
								return dataFilteredJSON;
							}
							
							
							if( ! validFields.contains(keysInner) )
							{
								dataFilteredJSON.put("Errore", "Chiave dell'oggetto non presente tra gli attributi.");
								dataFilteredJSON.put("ChiaveNonValida", keysInner);
								return dataFilteredJSON;
							}
						}
						
					}
					break;
			
				case "Wstatus":
				case "IndicIl":
				case "Sex":
				case "EtaRange":
						JSONObject filterJson;
						if(! (filtroJSON.get(k) instanceof JSONObject ) )	
						{
							dataFilteredJSON.put("Errore", "Almeno un valore dell'oggetto non è valido.");
							dataFilteredJSON.put("ChiaveNonValida", filtroJSON.get(k));
							return dataFilteredJSON;
						}
						
						filterJson = (JSONObject) filtroJSON.get(k);
						
						Object[] keysInner = filterJson.keySet().toArray();
						for( int j = 0; j < keysInner.length; j++ )
						{
							Object kInner = keysInner[j];
							
							if(! (kInner instanceof String ) )	
							{
								dataFilteredJSON.put("Errore", "Almeno una chiave dell'oggetto non è valida.");
								dataFilteredJSON.put("ChiaveNonValida", kInner);
								return dataFilteredJSON;
							}
							String keyInnerString = (String)kInner;
							switch(keyInnerString)
							{
								case "$not":
									if( !( filterJson.get(kInner) instanceof String) )
									{
										dataFilteredJSON.put("Errore", "Non è possibile applicare il filtro \""+keyInnerString+"\" con valore \""+String.valueOf(filterJson.get(kInner))+"\" al campo \""+fieldFilterAttribute+"\".");
										dataFilteredJSON.put("ChiaveNonValida", filterJson.get(kInner));
										return dataFilteredJSON;									
									}
									continue;
									
								case "$in":
								case "$nin":
									if( !( filterJson.get(kInner) instanceof JSONArray) )
									{
										dataFilteredJSON.put("Errore", "Non è possibile applicare il filtro \""+keyInnerString+"\" con valore \""+String.valueOf(filterJson.get(kInner))+"\" al campo \""+fieldFilterAttribute+"\".");
										dataFilteredJSON.put("ChiaveNonValida", filterJson.get(kInner));
										return dataFilteredJSON;									
									}
									continue;
								
								default:
									dataFilteredJSON.put("Errore", "Non è possibile applicare il filtro \""+keyInnerString+"\" al campo \""+fieldFilterAttribute+"\".");
									dataFilteredJSON.put("ChiaveNonValida", keyInnerString);
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
				
		dataFilteredJSON.put("Successo", true);
		
		return dataFilteredJSON;
	}

	/**
	 * Metodo che restuisce una lista di campi validi per il filtraggio.
	 * @return La lista dei campi validi.
	 */
	public static List<String> getValidFields()
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
		return validFields;
	}
	
	/**
	 * Scarica il dataset dall'URL presente nel file Configurations (FILE_URL) 
	 * e salva il dataset in un file (FILE_NAME).
	 */
	public static void downloadDataset()
	{
		try
		{
			byte buffer[] = new byte[1024];
		    int bytesRead;
		    
		    System.out.println("SCARICO IL DATASET..");
			
			InputStream datasetStream = new URL( Configurations.FILE_URL ).openStream();
			BufferedInputStream inputStream = new BufferedInputStream( datasetStream  );
			
			FileOutputStream fileOutputStream = new FileOutputStream( Configurations.FILE_NAME );
			
		    while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
		        fileOutputStream.write(buffer, 0, bytesRead);
		    }
		    
		    datasetStream.close();
		    fileOutputStream.close();
		    inputStream.close();
			
		    System.out.println("DATASET SCARICATO..");
		} catch (IOException e)
		{
			System.err.println("Errore nel download del file...");
			System.exit(-1);
		}
	}
}
