package univpm.oopproject.datatypes;

/**
 * Classe che contiene una coppia anno-valore.
 * @author Samuele Perticarari e Martina Rossi
 *
 */
public class TupleData {
	
	public int year;
	public double value;
	
	/**
	 * Costruttore di TupleData, assegna direttamente la coppia anno-valore.
	 * @param year Anno di riferimento.
	 * @param value Valore relativo all'anno.
	 */
	public TupleData(int year, double value) {
		this.year = year;
		this.value = value;
	}

	/**
	 * Metodo che restituisce l'anno di riferimento.
	 * @return Anno di riferimento.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Metodo che imposta l'anno di riferimento.
	 * @param year Anno da settare.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Metodo che imposta il valore relativo all'anno di riferimento.
	 * @return Valore relativo all'anno di riferimento.
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Metodo che imposta il valore relativo all'anno di riferimento.
	 * @param value Valore da impostare.
	 */
	public void setValue(double value) {
		this.value = value;
	}

	
}
