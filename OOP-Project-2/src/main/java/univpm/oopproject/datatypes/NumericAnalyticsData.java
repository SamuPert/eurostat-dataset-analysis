package univpm.oopproject.datatypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilizzata per la gestione dei dati analitici.
 * @author Samuele Perticarari & Martina Rossi
 *
 */
public class NumericAnalyticsData
{
	private double sum = 0;
	private int count = 0;
	private double avg;
	private double devstd = 0;
	private double min;
	private double max;
	
	private List<Double> data;

	/**
	 * Costruttore della Classe NumericAnalyticsData.
	 */
	public NumericAnalyticsData()
	{
		this.data = new ArrayList<Double>();
		this.sum = 0;
		this.count = 0;
		this.avg = 0;
		this.devstd = 0;
	}
	
	/**
	 * Metodo che inserisce un nuovo valore in lista
	 * aggiornandone la somma, il conteggio,
	 * minimo e massimo dei valori iseriti. 
	 * @param value Valore che vogliamo inserire
	 */
	public void addValue( double value )
	{
		this.data.add(value);
		this.sum += value;
		this.count++;

		if( this.count == 1 )
		{
			this.min = value;
			this.max = value;
		}else{
			this.min = Math.min(value, this.min);
			this.max = Math.max(value, this.max);
		}
		
		calculateAvg();
	}
	
	/**
	 * Metodo che calcola la media dei valori inseriti.
	 */
	public void calculateAvg()
	{
		this.avg = this.sum / this.count;
	}
	
	/**
	 * Metodo che calcola la deviazione standard dei valori inseriti.
	 */
	public void calculateDevstd()
	{
		double devstd = 0;
		for( Double value : this.data )
		{
			devstd += Math.pow( (this.avg - value) , 2);
		}
		this.devstd = devstd;
	}
	
	/**
	 * Metodo che ritorna la somma dei valori inseriti.
	 * @return Somma dei valori inseriti.
	 */
	public double getSum() {
		return sum;
	}

	/**
	 * Metodo che ritorna il conteggio dei valori inseriti.
	 * @return Conteggio dei valori inseriti.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Metodo che ritorna la media dei valori inseriti.
	 * @return Media dei valori inseriti.
	 */
	public double getAvg() {
		return avg;
	}

	/**
	 * Metodo che ritorna la deviazione standard dei valori inseriti.
	 * @return Deviazione standard dei valori inseriti.
	 */
	public double getDevstd() {
		return devstd;
	}
	
	/**
	 * Metodo che ritorna il minimo dei valori inseriti.
	 * @return Minimo dei valori inseriti.
	 */
	public double getMin() {
		return min;
	}

	/**
	 * Metodo che ritorna il massimo dei valori inseriti.
	 * @return Massimo dei valori inseriti.
	 */
	public double getMax() {
		return max;
	}
	
}
