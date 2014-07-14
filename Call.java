// CS290 Project 2 | Call.java
// by Hubert Jay J. Lisas, AdMU, July 2002

public class Call
{
	String number, numberCalled, reference;
	int duration;
	double cost;
	
	public Call( String number, String numberCalled, String duration, 
					String reference, double cost )
	{
		this.number = number;
		this.numberCalled = numberCalled;
		this.duration = new Integer( duration ).intValue();
		this.reference = reference;
		this.cost = cost;
	}
	
	public double getCost()
	{
		return this.cost;
	}
	
	public String getNumber()
	{
		return this.number;
	}
	
	public String getNumberCalled()
	{
		return this.numberCalled;
	}
	
	public int getCallDuration()
	{
		return this.duration;
	}
	
	public String getCallReference()
	{
		return this.reference;
	}

}