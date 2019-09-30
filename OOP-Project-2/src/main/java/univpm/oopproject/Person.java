package univpm.oopproject;

import java.util.ArrayList;
import java.util.List;

public class Person {
	
	public String wstatus;
	public String indic_il;
	public char sex;
	public int etaMax;
	public int etaMin;
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
	}


	public int getEtaMin() {
		return etaMin;
	}


	public void setEtaMin(int etaMin) {
		this.etaMin = etaMin;
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
	
	
}
