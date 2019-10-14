package univpm.oopproject.datatypes;

import java.util.ArrayList;
import java.util.List;

public class NumericAnalyticsData
{
	private double sum = 0;
	private int count = 0;
	private double avg;
	private double devstd = 0;
	private double min;
	private double max;
	
	private List<Double> data;

	public NumericAnalyticsData()
	{
		this.data = new ArrayList<Double>();
		this.sum = 0;
		this.count = 0;
		this.avg = 0;
		this.devstd = 0;
	}
	
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
	
	public void calculateAvg()
	{
		this.avg = this.sum / this.count;
	}
	
	public void calculateDevstd()
	{
		double devstd = 0;
		for( Double value : this.data )
		{
			devstd += Math.pow( (this.avg - value) , 2);
		}
		this.devstd = devstd;
	}
	
	public double getSum() {
		return sum;
	}

	public int getCount() {
		return count;
	}

	public double getAvg() {
		return avg;
	}

	public double getDevstd() {
		return devstd;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}
	
}
