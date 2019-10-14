package univpm.oopproject.dataset;

import java.io.BufferedReader;
import java.util.*;
import org.json.simple.*;

import univpm.oopproject.datatypes.HashTableCustom;
import univpm.oopproject.datatypes.NumericAnalyticsData;
import univpm.oopproject.datatypes.Person;
import univpm.oopproject.datatypes.TupleData;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe utilizzata per la gestione dei dati letti dal dataset TSV.
 * @author Samuele Perticarari & Martina Rossi
 *
 */
public abstract class Dataset {

	private static List<Person> data = new ArrayList<Person>();

	/**
	 * Metodo per aggiungere i dati di una nuova persona alla lista.
	 * @param p Dati della nuova persona da aggiungere alla lista.
	 */
	public static void addPerson(Person p) {
		data.add(p);
	}
	
	/**
	 * Metodo utilizzato per la restituzione dei dati delle persone
	 * in formato JSON.
	 * @return Dati delle persone in formato JSON.
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject getJSONDataset()
	{
		JSONObject datasetJSONObj = new JSONObject();
		JSONArray datasetJSON = new JSONArray();

		for (Person p : Dataset.getDataset())
		{
			JSONObject personDataObj = new JSONObject();
			
			personDataObj.put("Wstatus", p.getWstatus() );
			personDataObj.put("IndicIl", p.getIndic_il() );
			personDataObj.put("Sex", p.getSex() );
			
			if(p.getEtaMax() >= 0 && p.getEtaMin() >= 0)
			{
				personDataObj.put("EtaRange", p.getEtaMin() + "-" + p.getEtaMax() );				
			}else if( p.getEtaMin() < 0 ) {
				personDataObj.put("EtaRange", "<" + p.getEtaMax() );
			}else if( p.getEtaMax() < 0 ) {
				personDataObj.put("EtaRange", ">" + p.getEtaMin() );
			}
			personDataObj.put("Country", p.getCountry() );
			
			
			JSONObject dataObj = new JSONObject();
			for (TupleData td : p.getIndexes())
			{
				dataObj.put(td.getYear(), td.getValue());				
			}
			
			personDataObj.put("Dati", dataObj );
			
			datasetJSON.add(personDataObj);	
		}
		
		
		datasetJSONObj.put("DatiPersone", datasetJSON);
		datasetJSONObj.put("Conteggio", Dataset.getDataset().size() );
		datasetJSONObj.put("Successo", true);
		
		return datasetJSONObj;
	}
	
	/**
	 * Metodo che carica in memoria e parsa i dati dal file TSV appena scaricato.
	 * @param filename File di cui fare il parsing.
	 */
	public static void loadAndParseDataset( String filename )
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

	/**
	 * Metodo che resituisce la lista di dati delle persone inserite.
	 * @return La Lista di persone nel dataset.
	 */
	public static List<Person> getDataset() {
		return data;
	}
	
	/**
	 * Metodo che restituisce l'anno più piccolo dai metadati 
	 * del dataset principale. 
	 * @return Anno minimo dai metadati.
	 */
	public static int getAnnoMinimo()
	{
		return Dataset.getAnnoMinimo( Dataset.getDataset() );
	}
	
	/**
	 * Metodo che restituisce l'anno più piccolo dai metadati di un 
	 * sottoinsieme del dataset.
	 * @return Anno minimo dai metadati.
	 */
	public static int getAnnoMinimo( List<Person> dataset )
	{
		int annoMinimo = 3000;
		
		// Prendi anno più piccolo dai metadati
		for (TupleData t : dataset.get(0).getIndexes() )
		{
			int year = t.getYear();
			if(annoMinimo > year) annoMinimo = year;
		}
		return annoMinimo;
	}
	
	/**
	 * Metodo che restituisce l'anno più grande dai metadati 
	 * del dataset principale. 
	 * @return Anno massimo dai metadati.
	 */
	public static int getAnnoMassimo()
	{
		return Dataset.getAnnoMassimo( Dataset.getDataset() );
	}
	
	/**
	 * Metodo che restituisce l'anno più grande dai metadati di un 
	 * sottoinsieme del dataset.
	 * @return Anno massimo dai metadati.
	 */
	public static int getAnnoMassimo( List<Person> dataset )
	{
		int annoMassimo = 0;		
		// Prendi anno più grande dai metadati
		for (TupleData t : dataset.get(0).getIndexes() )
		{
			int year = t.getYear();
			if(annoMassimo < year) annoMassimo = year;
		}
		return annoMassimo;
	}
	
	/**
	 * Analizza la lista di persone passatagli come parametro
	 * restituendone un'analisi statistica dei dati e degli 
	 * attributi.
	 * @param dataset Lista di persone da analizzare.
	 * @return JSON contentente la statistica sui dati.
	 */
	public static JSONObject analyzeDataset( List<Person> dataset )
	{
		if( dataset.size() == 0 )
		{
			JSONObject ret = new JSONObject();
			ret.put("Errore", "Nessun dato trovato con i filtri inseriti.");
			return ret;
		}
		
		int annoMinimo = Dataset.getAnnoMinimo( dataset );
		
		NumericAnalyticsData[] nad = new NumericAnalyticsData[ dataset.get(0).getIndexes().size() ];
		for( int i = 0; i < nad.length; i++ )
			nad[i] = new NumericAnalyticsData();
		
		JSONObject analytics = new JSONObject();
		JSONArray analyticsData = new JSONArray();

		HashTableCustom<String, Integer> workingStatusHashTable = new HashTableCustom<String, Integer>();
		HashTableCustom<String, Integer> indicIlHashTable = new HashTableCustom<String, Integer>();
		HashTableCustom<String, Integer> sexHashTable = new HashTableCustom<String, Integer>();
		HashTableCustom<String, Integer> etaRangeHashTable = new HashTableCustom<String, Integer>();
		HashTableCustom<String, Integer> countryHashTable = new HashTableCustom<String, Integer>();

		
		for (Person p : dataset)
		{
			JSONObject personDataObj = new JSONObject();
		
			workingStatusHashTable.setOrInc( p.getWstatus() );
			indicIlHashTable.setOrInc( p.getIndic_il() );
			sexHashTable.setOrInc( String.valueOf( p.getSex() ) );
			etaRangeHashTable.setOrInc( p.getEtaRange() );
			countryHashTable.setOrInc( p.getCountry() );
			
			for (TupleData t : p.getIndexes() )
			{
				int index = t.getYear() - annoMinimo;
				nad[index].addValue( t.getValue() );				
			}
				
		}
		
		
		for (int i=0; i<nad.length; i++)
		{
			nad[i].calculateDevstd();
		}
		
		
		JSONObject wstatusJSONData = new JSONObject();
		JSONObject indicIlJSONData = new JSONObject();
		JSONObject sexJSONData = new JSONObject();
		JSONObject etaRangeJSONData = new JSONObject();
		JSONObject countryJSONData = new JSONObject();
		
		wstatusJSONData.put("Attributo", "Wstatus");
		wstatusJSONData.put("Dati", workingStatusHashTable.getJSONValues() );
		wstatusJSONData.put("TipoDato", "String" );

		indicIlJSONData.put("Attributo", "IndicIl");
		indicIlJSONData.put("Dati", indicIlHashTable.getJSONValues() );
		indicIlJSONData.put("TipoDato", "String" );

		sexJSONData.put("Attributo", "Sex");
		sexJSONData.put("Dati", sexHashTable.getJSONValues() );
		sexJSONData.put("TipoDato", "String" );

		etaRangeJSONData.put("Attributo", "EtaRange");
		etaRangeJSONData.put("Dati", etaRangeHashTable.getJSONValues() );
		etaRangeJSONData.put("TipoDato", "String" );

		countryJSONData.put("Attributo", "Country");
		countryJSONData.put("Dati", countryHashTable.getJSONValues() );
		countryJSONData.put("TipoDato", "String" );

		analyticsData.add( wstatusJSONData );
		analyticsData.add( indicIlJSONData );
		analyticsData.add( sexJSONData );
		analyticsData.add( etaRangeJSONData );
		analyticsData.add( countryJSONData );
		
		
		for (int i=0; i<nad.length; i++)
		{
			JSONObject dataObject = new JSONObject();
			JSONObject jdata = new JSONObject();
			dataObject.put("Attributo", String.valueOf( i + annoMinimo ) );
			jdata.put("Somma", nad[i].getSum() );
			jdata.put("Conteggio", nad[i].getCount() );
			jdata.put("Media", nad[i].getAvg() );
			jdata.put("Minimo", nad[i].getMin() );
			jdata.put("Massimo", nad[i].getMax() );
			jdata.put("DeviazioneStandard", nad[i].getDevstd() );
			
			dataObject.put("Dati", jdata);
			dataObject.put("TipoDato", "Numeric");
			analyticsData.add(dataObject);
		}
					
		
		analytics.put("Dati", analyticsData);
		return analytics;
	
	}

}
