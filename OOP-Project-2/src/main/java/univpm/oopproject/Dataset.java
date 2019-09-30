package univpm.oopproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dataset {

	public static List<Person> data = new ArrayList<Person>();
	
	public static void addPerson(Person p) {
		data.add(p);
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
			
			// Salto prima riga.
			headingData = bufferedReader.readLine().split("[,\\t]");
			
            for(String lineData; (lineData = bufferedReader.readLine()) != null; )
            {
            	Person p = new Person();
            	
            	// Splitta la linea per "\t" e per ","
            	personData = lineData.split("[,\\t]");
            	
            	p.setWstatus( personData[0].trim() );
            	p.setIndic_il( personData[1].trim() );
            	
            	// Prendi il primo carattere del sesso
            	p.setSex( personData[2].trim().toCharArray()[0] );
            	
            	if( personData[3].trim().toCharArray()[1] == '_' )
            	{
            		if( personData[3].trim().substring(2,2).equals("GE") )
            		{
            			// Greater or Equal
                    	p.setEtaMin( Integer.parseInt( personData[3].trim().substring(4) ) );
                		p.setEtaMax( -1 ); 
                		
            		} else if( personData[3].trim().substring(2,2).equals("LE") )
            		{
            			// Lower or Equal
                    	p.setEtaMin( -1 );
                		p.setEtaMax( Integer.parseInt( personData[3].trim().substring(4) ) ); 
            		}
               	
            	}else{
            		String[] yearMinMax = personData[3].trim().substring(1).split("-");
            		p.setEtaMin( Integer.parseInt( yearMinMax[0].trim() ) ); 
            		p.setEtaMax( Integer.parseInt( yearMinMax[1].trim() ) ); 
            	}

            	p.setCountry( personData[4] );
            	
            	for( int i = 5; i < headingData.length && i < personData.length ; i++ )
            	{
            		if( !personData[i].trim().split(" ")[0].equals(":") )
            		{
                		TupleData t;
                		t = new TupleData( Integer.parseInt( headingData[i].trim() ), Double.parseDouble( personData[i].trim().split(" ")[0] ) );
                		p.addIndexes( t );            			
            		}
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
}
