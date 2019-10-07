package univpm.oopproject;

import java.util.ArrayList;
import java.util.List;

public class Person {
	
	public String wstatus;
	public String indic_il;
	public char sex;
	private int etaMax = 0;
	private int etaMin = 0;
	public String etaRange;
	public String country;
	public List<TupleData> indexes;
	
	Person() {
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
	
}
