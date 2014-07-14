// CS290 Project 2 | Subscriber.java
// by Hubert Jay J. Lisas, AdMU, July 2002

import java.util.*;

public class Subscriber
{
	Hashtable callHistory;
	String SIMNumber;
	String lastName, firstName, subscriptionType;
	double balanceDue = 0;
	double payments = 0;
	double balanceAvailable = 0;
	
	public Subscriber()
	{
	}
	
	public Subscriber( String number, String firstName, String lastName, String type ) 
	{
		this.firstName = firstName;
		this.lastName = lastName;
		SIMNumber = number;
		subscriptionType = type;
		callHistory = new Hashtable();
	}
	
	public String getSIMNumber()
	{
		return SIMNumber;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setBalanceDue( double amt )
	{
		balanceDue += amt;
	}
	
	public void setBalanceAvailable( double amt )
	{
		balanceAvailable += amt;
	}
	
	public double getBalanceDue()
	{
		return balanceDue;
	}
	
	public double getBalanceAvailable()
	{
		return balanceAvailable;
	}
		
	public String getSubscriptionType()
	{
		return subscriptionType;
	}
	
	public void addCall( Call call )
	{
		callHistory.put( call.getCallReference(), call );
		if ( getSubscriptionType().equals( "prepaid" ) )
		{
			balanceAvailable -= ( call.getCallDuration() * 8 );
		}
		else if (  getSubscriptionType().equals( "postpaid" ) )
		{
			balanceDue += ( call.getCallDuration() * 6 );
		}
	}
	
	public Hashtable getCallHistory()
	{
		
		return this.callHistory;
	}
	
	public void clearList()
	{
		this.callHistory.clear();
	}
	
	public void refuteCall( String ref )
	{
		this.callHistory.remove( ref );
	}
	
	public Call getCall( String ref )
	{
		Call c = (Call)callHistory.get( ref );
		return c;
	}
	
	public void sendText( String type )
	{
		if ( type.equals( "prepaid" ) )
		{
			balanceAvailable -= 1;
		}
		else if ( type.equals( "postpaid" ) )
		{
			balanceDue += 1;
		}
	}
	
	public void payCall( String amount )
	{
		payments = new Double( amount ).doubleValue();
		balanceDue -= payments;
	}
	
	public void addLoad( String amount )
	{
		balanceAvailable += new Double( amount ).doubleValue();
	}
	
	public boolean empty()
	{
		return callHistory.isEmpty();
	}

}