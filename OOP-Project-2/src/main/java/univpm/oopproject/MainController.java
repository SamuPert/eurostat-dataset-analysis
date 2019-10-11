package univpm.oopproject;

import org.springframework.web.bind.annotation.RestController;
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
    
	@RequestMapping("/")
    public String index()
    {
    	return "Dataset size: " + Dataset.getDataset().size() + " people.";
    }
	
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
				classAttributesMetadata.put("AccessModifier", Modifier.toString( f.getModifiers() ) );
				classAttributesMetadata.put("Type", f.getType().getName());
				classAttributesMetadata.put("Name", f.getName());
				arrayOfAttributes.add(classAttributesMetadata);
			}
			
			Method[] methods = c.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++)
            {
            	JSONObject classMethodsMetadata = new JSONObject();
            	JSONArray classMethodParametersMetadata = new JSONArray();
				Method method = methods[i];
				
				classMethodsMetadata.put("AccessModifier", Modifier.toString( method.getModifiers() ) );
				classMethodsMetadata.put("ReturnType", method.getGenericReturnType().getTypeName());
				classMethodsMetadata.put("Name", method.getName() );

            	for(Type t : method.getGenericParameterTypes() )
            		classMethodParametersMetadata.add( t.getTypeName() );

				classMethodsMetadata.put( "ParameterTypes", classMethodParametersMetadata );

            	arrayOfMethods.add(classMethodsMetadata);
            }
			
			obj.put("Class","Person");
			obj.put("Attributes",arrayOfAttributes);
			obj.put("Methods", arrayOfMethods);
			
		} catch (IllegalArgumentException e) {
			obj.put("Error","Illegal argument exception: " + e.getMessage());
		}

		   
		
    	return obj;
    }
	
	@RequestMapping( value = "/get/dataset/full", method = RequestMethod.GET, produces="application/json" )
	public JSONObject getFullData()
	{
		return Dataset.getJSONDataset();
}
	
	@RequestMapping( value = "/get/analytics", method = RequestMethod.GET, produces="application/json" )
	public JSONObject getAnalytics()
	{
		return Dataset.analyzeDataset( Dataset.getDataset() );
	}
	
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
			dataFilteredJSON.put("Error", "Errore nel parsing della richiesta filtro.");
			return dataFilteredJSON;
		} catch (ParseException e) {
			dataFilteredJSON = new JSONObject();
			dataFilteredJSON.put("Error", "Errore nel parsing della richiesta filtro.");
			return dataFilteredJSON;
		}

		// { "wstatus" : {"$not" : "1" } }
		// {"field" : {"$in" : [value1, value2, ...]}}
		// {"field" : {"$nin" : [value1, value2, ...]}}
		// {"field" : {"$or" : [ "A", "B", "C" ]    }}
		// {"field" : {"$and" : [ "A", "B", "C" ]    }}

		JSONObject responseValidator = Utils.isFilterValid( filtersJSON );
		
		if( ! responseValidator.containsKey("Success") )
			return responseValidator;
		
		
		
		
		for( Person p: Dataset.getDataset() )
		{
			if( p.applyFilter( filtersJSON ) )
				data.add( p );
		}
		
		
		
		
		System.out.println(data.size());
		
		
		
		
		return Dataset.analyzeDataset( data );
	}
    
}
