package univpm.oopproject;

import java.io.BufferedReader;
import org.json.simple.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Dataset {

	private static List<Person> data = new ArrayList<Person>();
	
	public static void addPerson(Person p) {
		data.add(p);
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject getJSONDataset()
	{
		JSONObject datasetJSONObj = new JSONObject();
		JSONArray datasetJSON = new JSONArray();

		for (Person p : Dataset.getDataset())
		{
			JSONObject personDataObj = new JSONObject();
			
			personDataObj.put("WorkingStatus", p.getWstatus() );
			personDataObj.put("Indic_Il", p.getIndic_il() );
			personDataObj.put("Sex", p.getSex() );
			
			if(p.getEtaMax() >= 0 && p.getEtaMin() >= 0)
			{
				personDataObj.put("AgeRange", p.getEtaMin() + "-" + p.getEtaMax() );				
			}else if( p.getEtaMin() < 0 ) {
				personDataObj.put("AgeRange", "<" + p.getEtaMax() );
			}else if( p.getEtaMax() < 0 ) {
				personDataObj.put("AgeRange", ">" + p.getEtaMin() );
			}
			personDataObj.put("Country", p.getCountry() );
			
			
			JSONObject dataObj = new JSONObject();
			for (TupleData td : p.getIndexes())
			{
				dataObj.put(td.getYear(), td.getValue());				
			}
			
			personDataObj.put("Data", dataObj );
			
			datasetJSON.add(personDataObj);	
		}
		
		
		datasetJSONObj.put("PeopleData", datasetJSON);
		datasetJSONObj.put("Count", Dataset.getDataset().size() );
		datasetJSONObj.put("Success", true);
		
		return datasetJSONObj;
	}
	
	public static void LoadAndParseDataset( String filename )
	{
		System.out.println("PARSING DEL FILE \""+filename+"\".");
		// INIZIO PARSING
		try {
			FileReader datasetFileReader = new FileReader( filename );
			BufferedReader bufferedReader = new BufferedReader( datasetFileReader );
			
			String[] personData;
			String[] headingData;
			String personAgeRange;
			
			// Salto prima riga.
			headingData = bufferedReader.readLine().split("[,\\t]");
			
            for(String lineData; (lineData = bufferedReader.readLine()) != null; )
            {
            	Person p = new Person();
            	
            	// Splitta la linea per "\t" e per ","
            	personData = lineData.split("[,\\t]");

            	personAgeRange = personData[3].trim();

            	p.setWstatus( personData[0].trim() );
            	p.setIndic_il( personData[1].trim() );
            	
            	// Prendi il primo carattere del sesso
            	p.setSex( personData[2].trim().toCharArray()[0] );
            	
            	
            	if( personAgeRange.toCharArray()[1] == '_' )
            	{
            		if( personAgeRange.substring(2,2).equals("GE") )
            		{
            			// Greater or Equal
                    	p.setEtaMin( Integer.parseInt( personAgeRange.substring(4) ) );
                		p.setEtaMax( -1 ); 
                		
            		} else if( personAgeRange.substring(2,2).equals("LE") )
            		{
            			// Lower or Equal
                    	p.setEtaMin( -1 );
                		p.setEtaMax( Integer.parseInt( personAgeRange.substring(4) ) ); 
            		}
               	
            	}else{
            		String[] yearMinMax = personAgeRange.substring(1).split("-");
            		p.setEtaMin( Integer.parseInt( yearMinMax[0].trim() ) ); 
            		p.setEtaMax( Integer.parseInt( yearMinMax[1].trim() ) ); 
            	}

            	p.setCountry( personData[4] );
            	
            	int i;
            	for( i = 5; i < headingData.length && i < personData.length ; i++ )
            	{
            		if( !personData[i].trim().split(" ")[0].equals(":") )
            		{
                		TupleData t;
                		t = new TupleData( Integer.parseInt( headingData[i].trim() ), Double.parseDouble( personData[i].trim().split(" ")[0] ) );
                		p.addIndexes( t );
            		} else {
            			TupleData t;
                		t = new TupleData( Integer.parseInt( headingData[i].trim() ), 0 );
                		p.addIndexes( t );
            		}
            	}
            	
            	for( ; i < headingData.length; i++ )
            	{
            		TupleData t;
            		t = new TupleData( Integer.parseInt( headingData[i].trim() ), 0 );
            		p.addIndexes( t );	
            	}
            	
            	Dataset.addPerson(p);
            }
            
            bufferedReader.close();
            datasetFileReader.close();
            
		} catch (IOException e) {
			System.err.println("Errore Input/Output...");
			e.printStackTrace();			
		}
		System.out.println("PARSING COMPLETATO!");
		// FINE PARSING
	}

	public static List<Person> getDataset() {
		return data;
	}

}
