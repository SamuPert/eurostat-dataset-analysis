package univpm.oopproject.controllers;

import org.springframework.web.bind.annotation.RestController;

import univpm.oopproject.dataset.Dataset;
import univpm.oopproject.datatypes.Person;
import univpm.oopproject.utils.Utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class MainController {
   
	/**
	 * Metodo che restituisce informazioni sui dati caricati delle persone
	 * @return Informazione sul numero di dati caricati
	 */
	@RequestMapping("/")
    public String index()
    {
		return "Caricati i dati di: " + String.valueOf(Dataset.getDataset().size()) + " persone";
    }
	
	/**
	 * Metodo che restituisce i metadati in formato JSON.
	 * @return Metadati in formato JSON.
	 */
	@RequestMapping("/get/metadata")
    public JSONObject getMetadata()
    {
		JSONObject obj = new JSONObject();
		JSONArray arrayOfAttributes = new JSONArray();
		JSONArray arrayOfMethods = new JSONArray();
		
		Class c;
		try {
			
			c = Person.class;
			Field[] fields = c.getDeclaredFields();
			for(int i=0;i< fields.length;i++)
			{
				JSONObject classAttributesMetadata = new JSONObject();
				Field f = fields[i];
				classAttributesMetadata.put("ModificatoreAccesso", Modifier.toString( f.getModifiers() ) );
				classAttributesMetadata.put("Tipo", f.getType().getName());
				classAttributesMetadata.put("Nome", f.getName());
				arrayOfAttributes.add(classAttributesMetadata);
			}
			
			Method[] methods = c.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++)
            {
            	JSONObject classMethodsMetadata = new JSONObject();
            	JSONArray classMethodParametersMetadata = new JSONArray();
				Method method = methods[i];
				
				classMethodsMetadata.put("ModificatoreAccesso", Modifier.toString( method.getModifiers() ) );
				classMethodsMetadata.put("TipoRitorno", method.getGenericReturnType().getTypeName());
				classMethodsMetadata.put("Nome", method.getName() );

            	for(Type t : method.getGenericParameterTypes() )
            		classMethodParametersMetadata.add( t.getTypeName() );

				classMethodsMetadata.put( "TipiParametri", classMethodParametersMetadata );

            	arrayOfMethods.add(classMethodsMetadata);
            }
			
			obj.put("Classe","Person");
			obj.put("Attributi",arrayOfAttributes);
			obj.put("Metodi", arrayOfMethods);
			
		} catch (IllegalArgumentException e) {
			obj.put("Errore","Argomento non valido: " + e.getMessage());
		}

		   
		
    	return obj;
    }
	
	/**
	 * Metodo che restituisce i dati in formato JSON.
	 * @return Dati in formato JSON.
	 */
	@RequestMapping( value = "/get/dataset/full", method = RequestMethod.GET, produces="application/json" )
	public JSONObject getFullData()
	{
		return Dataset.getJSONDataset();
	}
	
	/**
	 * Metodo che restituisce le analisi sui dati JSON
	 * @return Analisi sui dati JSON.
	 */
	@RequestMapping( value = "/get/analytics", method = RequestMethod.GET, produces="application/json" )
	public JSONObject getAnalytics()
	{
		return Dataset.analyzeDataset( Dataset.getDataset() );
	}
	
	/**
	 * Rotta che mostra i dati recuperati dal CSV, eventulmente filtrati, sotto forma di JSON
	 * @param filter Filtro riportato nel messaggio della richiesta in formato POST
	 * @return Restituisce un JSON contenente i dati, eventualmente filtrati
	 */
	@RequestMapping( value = "/get/analytics", method = RequestMethod.POST, produces="application/json" )
	public JSONObject getFilteredAnalytics( @RequestBody(required = false) String filter )
	{
		if( filter == null )
		{
			return Dataset.analyzeDataset( Dataset.getDataset() );
		}
		
		List<Person> data = new ArrayList<Person>();
		
		JSONParser parser = new JSONParser();
		JSONObject dataFilteredJSON;
		JSONObject filtersJSON;
		
		try {
			filtersJSON = (JSONObject) parser.parse(filter);
		} catch (ClassCastException e) {
			dataFilteredJSON = new JSONObject();
			dataFilteredJSON.put("Errore", "Errore nel parsing della richiesta filtro.");
			return dataFilteredJSON;
		} catch (ParseException e) {
			dataFilteredJSON = new JSONObject();
			dataFilteredJSON.put("Errore", "Errore nel parsing della richiesta filtro.");
			return dataFilteredJSON;
		}

		JSONObject responseValidator = Utils.isFilterValid( filtersJSON );
		
		if( ! responseValidator.containsKey("Successo") )
			return responseValidator;
		
		
		
		
		for( Person p: Dataset.getDataset() )
		{
			if( p.applyFilter( filtersJSON ) )
				data.add( p );
		}
		
		return Dataset.analyzeDataset( data );
	}
    
}
