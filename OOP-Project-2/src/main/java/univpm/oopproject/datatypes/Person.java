package univpm.oopproject.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import univpm.oopproject.dataset.Dataset;
import univpm.oopproject.utils.Utils;

public class Person {
	
	public String wstatus;
	public String indic_il;
	public char sex;
	private int etaMax = 0;
	private int etaMin = 0;
	public String etaRange;
	public String country;
	public List<TupleData> indexes;
	
	public Person() {
		indexes = new ArrayList<TupleData>();
	}
	
	
	public void addIndexes(TupleData t) {
		indexes.add(t);
	}


	public String getWstatus() {
		return wstatus;
	}


	public void setWstatus(String wstatus) {
		this.wstatus = wstatus;
	}


	public String getIndic_il() {
		return indic_il;
	}


	public void setIndic_il(String indic_il) {
		this.indic_il = indic_il;
	}


	public char getSex() {
		return sex;
	}


	public void setSex(char sex) {
		this.sex = sex;
	}


	public int getEtaMax() {
		return etaMax;
	}


	public void setEtaMax(int etaMax) {
		this.etaMax = etaMax;
		this.setEtaRange();
	}


	public int getEtaMin() {
		return etaMin;
	}


	public void setEtaMin(int etaMin) {
		this.etaMin = etaMin;
		this.setEtaRange();
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public List<TupleData> getIndexes() {
		return indexes;
	}


	public void setIndexes(List<TupleData> indexes) {
		this.indexes = indexes;
	}
	
	private void setEtaRange()
	{
	
		if( this.getEtaMax() >= 0 && this.getEtaMin() >= 0)
		{
			this.etaRange = this.getEtaMin() + "-" + this.getEtaMax();				
		}else if( this.getEtaMin() < 0 ) {
			this.etaRange = "<" + this.getEtaMax();
		}else if( this.getEtaMax() < 0 ) {
			this.etaRange = ">" + this.getEtaMin();
		}
	
	}
	
	public String getEtaRange()
	{
		return this.etaRange;
	}

	public boolean applyFilter(JSONObject filtersJSON)
	{
		// For each column apply filter
		for( Object keyFieldObj : filtersJSON.keySet() )
		{
			String field = (String)keyFieldObj;
			
			List<String> validFields = Utils.getValidFilters();
			
			if( !validFields.contains( field ) ) return false;
				
			
			JSONObject filterInfos = (JSONObject)filtersJSON.get(field);
			
			// For each filter apply filter
			for(Object filterType : filterInfos.keySet() )
			{
				
				// JSONObject realFilter = (JSONObject) filterInfos.get( filterType );
				
				// For each 
				//for(Object filterType : realFilter.keySet() )
				//{
				
				String filter = (String)filterType;
					
				JSONArray filterRange, filterArray;
				JSONObject[] filterObjectArr;
				Object objectGeneric;
				Object[] objectArray;
				String[] filterRangeArrStr;
				Double[] filterRangeArrDouble;
				Double filterValue;
				String filterString;
				boolean isOk;
				

				
				// Double filterValue = (Double)filterInfo.get(filter);
								
				switch( filter )
				{
					// {"column" : {"$not" : "1" } }
					case "$not":
						filterString = (String)filterInfos.get(filterType);
						
						isOk = !( this.checkEqualValueByVariableName((String)filter, filterString ) );

						if(!isOk) return false;
						
						break;

					// {"column" : {"$in" : [ "A", "B", "C" ] }}
					case "$in":
						filterRange = (JSONArray)filterInfos.get(filterType);
						objectArray = filterRange.toArray();
						filterRangeArrStr = Arrays.copyOf(objectArray, objectArray.length, String[].class);

						isOk = this.checkContainsValueByVariableName( (String)field, filterRangeArrStr );
						
						if(!isOk) return false;
						
						break;
						
					// {"column" : {"$nin" : [ "A", "B", "C" ] }}
					case "$nin":
						filterRange = (JSONArray)filterInfos.get(filterType);
						objectArray = filterRange.toArray();
						filterRangeArrStr = Arrays.copyOf(objectArray, objectArray.length, String[].class);
						
						isOk = !( this.checkContainsValueByVariableName( (String)field, filterRangeArrStr ) );
						
						if(!isOk) return false;
						
						break;
						
					// {"$or": [{"status": "GOLD"}, {"status": "SILVER"}]}
					case "$or":
						filterArray = (JSONArray)filterInfos.get(filterType);
						filterObjectArr = (JSONObject[]) filterArray.toArray();
						
						isOk = false;
						for(JSONObject js : filterObjectArr)
						{
							Object[] keys = js.keySet().toArray();
							for( Object k : keys )
							{
								isOk = isOk || this.checkEqualValueByVariableName((String)field, js.get(k));
								if(isOk) break;
							}							
						}
						
						if(!isOk) return false;
						
						break;
						
					// {"$and": [{"status": "GOLD"}, {"status": "SILVER"}]}
					case "$and":
						filterArray = (JSONArray)filterInfos.get(filterType);
						filterObjectArr = (JSONObject[]) filterArray.toArray();
						
						isOk = true;
						for(JSONObject js : filterObjectArr)
						{
							Object[] keys = js.keySet().toArray();
							for( Object k : keys )
							{
								isOk = isOk && this.checkEqualValueByVariableName((String)field, js.get(k));
								if(!isOk) break;
							}
						}
						
						if(!isOk) return false;
						
						break;
						
					// {"salary": {"$gt": 10000}}
					case "$gt":
						objectGeneric = filterInfos.get(filterType);
						if( objectGeneric instanceof Long )
						{
							filterValue = ((Long)objectGeneric).doubleValue();
						}else {
							filterValue = (Double)objectGeneric;
						}
						isOk = this.checkGreaterValueByVariableName(field, filterValue);
						if(!isOk) return false;
						break;
						
					// {"salary": {"$gte": 10000}}
					case "$gte":
						objectGeneric = filterInfos.get(filterType);
						if( objectGeneric instanceof Long )
						{
							filterValue = ((Long)objectGeneric).doubleValue();
						}else {
							filterValue = (Double)objectGeneric;
						}
						
						isOk = this.checkGreaterValueByVariableName(field, filterValue);
						if(!isOk) return false;
						isOk = isOk || this.checkEqualValueByVariableName(field, filterValue);
						if(!isOk) return false;
						break;
						
					// {"salary": {"$lt": 10000}}
					case "$lt":
						objectGeneric = filterInfos.get(filterType);
						if( objectGeneric instanceof Long )
						{
							filterValue = ((Long)objectGeneric).doubleValue();
						}else {
							filterValue = (Double)objectGeneric;
						}
						isOk = this.checkLowerValueByVariableName(field, filterValue);
						if(!isOk) return false;
						break;
						
					// {"salary": {"$lte": 10000}}
					case "$lte":
						objectGeneric = filterInfos.get(filterType);
						if( objectGeneric instanceof Long )
						{
							filterValue = ((Long)objectGeneric).doubleValue();
						}else {
							filterValue = (Double)objectGeneric;
						}
						isOk = this.checkLowerValueByVariableName(field, filterValue);
						if(!isOk) return false;
						isOk = isOk || this.checkEqualValueByVariableName(field, filterValue);
						if(!isOk) return false;
						break;
						
					// {"salary": {"$bt": [5000, 7500]}}
					case "$bt":
						filterRange = (JSONArray)filterInfos.get(filterType);
						
						objectArray = filterRange.toArray();
						filterRangeArrDouble = Arrays.copyOf(objectArray, objectArray.length, Double[].class);
						
						isOk = checkValueBetweenByVariableName(field, Math.min(filterRangeArrDouble[0], filterRangeArrDouble[1]), Math.max(filterRangeArrDouble[0], filterRangeArrDouble[1]) );
						if(!isOk) return false;
						break;
					
				}


				//}
				
				
			}
			
				
		}
		
		return true;
	}

	
	private boolean checkGreaterValueByVariableName(String variableName, Object value)
	{
		int annoMinimo = Dataset.getAnnoMinimo();
		int annoMassimo = Dataset.getAnnoMassimo();
		List<String> validFields = new ArrayList<String>();
		
		for(int w1 = annoMinimo; w1 < annoMassimo + 1 ; w1++)
			validFields.add( String.valueOf(w1) );	
		
		
		if( !validFields.contains(variableName) ) return false;
		
		for(TupleData t : this.getIndexes())
		{
			if( t.getYear() == Integer.parseInt((String)variableName) )
			{
				if( value instanceof String )
					return t.getValue() > Double.parseDouble((String)value);
					
				if( value instanceof Long)
					return t.getValue() > (Long)value;
					
				if(value instanceof Double )
					return t.getValue() > (Double)value;
				
				return false;
			}
		}
	
		return false;
	}
	
	private boolean checkLowerValueByVariableName(String variableName, Object value)
	{
		int annoMinimo = Dataset.getAnnoMinimo();
		int annoMassimo = Dataset.getAnnoMassimo();
		List<String> validFields = new ArrayList<String>();
		
		for(int w1 = annoMinimo; w1 < annoMassimo + 1 ; w1++)
			validFields.add( String.valueOf(w1) );	
		
		
		if( !validFields.contains(variableName) ) return false;
		
		for(TupleData t : this.getIndexes())
		{
			if( t.getYear() == Integer.parseInt((String)variableName) )
			{
				if( value instanceof String )
					return t.getValue() < Double.parseDouble((String)value);
					
				if( value instanceof Long)
					return t.getValue() < (Long)value;
					
				if(value instanceof Double )
					return t.getValue() < (Double)value;
				
				return false;
			}
		}
	
		return false;
	}
	
	private boolean checkValueBetweenByVariableName(String variableName, Object min, Object max)
	{
		return     this.checkGreaterValueByVariableName(variableName, min)
				&& this.checkLowerValueByVariableName(variableName, max)
				&& this.checkEqualValueByVariableName(variableName, min)
				&& this.checkEqualValueByVariableName(variableName, max);
	}
	
	private boolean checkContainsValueByVariableName(String variableName, Object values)
	{
		List<String> validFields = new ArrayList<String>();
		
		validFields.add("Wstatus");
		validFields.add("Sex");
		validFields.add("IndicIl");
		validFields.add("EtaRange");
		validFields.add("Country");
		
		if( !validFields.contains(variableName) ) return false;
		
		if( !(values instanceof String[]) ) return false;
		
		List<String> s = Arrays.asList((String[])values);
		
		
		switch(variableName)
		{
			case "Wstatus": return (s.contains( this.getWstatus() ));
			case "Sex": return (s.contains( String.valueOf( this.getSex() ) ));
			case "IndicIl": return (s.contains( this.getIndic_il() ));
			case "EtaRange": return (s.contains( this.getEtaRange() ));
			case "Country": return (s.contains( this.getCountry() ));
		}
		
		return false;
	}
	
	private boolean checkEqualValueByVariableName(String variableName, Object value)
	{
		int annoMinimo = Dataset.getAnnoMinimo();
		int annoMassimo = Dataset.getAnnoMassimo();
		List<String> validFields = new ArrayList<String>();
		
		validFields.add("Wstatus");
		validFields.add("Sex");
		validFields.add("IndicIl");
		validFields.add("EtaRange");
		validFields.add("Country");
		
		for(int w1 = annoMinimo; w1 < annoMassimo + 1 ; w1++)
			validFields.add( String.valueOf(w1) );	
		
		
		if( !validFields.contains(variableName) ) return false;
		
		switch(variableName)
		{
			case "Wstatus": return ( value instanceof String && ((String)value).equals(this.getWstatus()) );
			case "Sex": return ( value instanceof String && ((String)value).equals( String.valueOf(this.getSex()) ) );
			case "IndicIl": return ( value instanceof String && ((String)value).equals( this.getIndic_il() ) );
			case "EtaRange": return ( value instanceof String && ((String)value).equals( this.getEtaRange() ) );
			case "Country": return ( value instanceof String && ((String)value).equals( this.getCountry() ) );
		}
		
		for(TupleData t : this.getIndexes())
		{
			if( t.getYear() == Integer.parseInt((String)variableName) )
			{
				if( value instanceof String )
					return t.getValue() == Double.parseDouble((String)value);
					
				if( value instanceof Long)
					return t.getValue() == (Long)value;
					
				if(value instanceof Double )
					return t.getValue() == (Double)value;
				
				return false;
			}
		}
	
		return false;
	}
	
}
