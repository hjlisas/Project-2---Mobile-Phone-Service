// CS290 Project 2 | TransactionServer.java
// by Hubert Jay J. Lisas, AdMU, July 2002

import java.util.*;
import java.util.Collection;
import java.io.*;

public class TransactionServer
{
	SubscriberList subscriberList;
	Subscriber subscriber;
	Call call;

	public TransactionServer()
	{
		subscriberList = new SubscriberList();
	}

	public void text( String s )
	{
		StringTokenizer token = new StringTokenizer( s, "|" );
		String parameter = token.nextToken();
		
		if ( subscriberList.contains( parameter ) )
		{
			subscriber = (Subscriber)subscriberList.getSubscriber( parameter );
			if ( subscriber.getSubscriptionType().equals( "prepaid" ) )
			{
				if ( subscriber.getBalanceAvailable() >= 1 )
				{
					subscriber.sendText( subscriber.getSubscriptionType() );
					System.out.println( "text message sent by " + 
										subscriber.getSIMNumber() );
				}
				else System.out.println( "text message not sent" );
			}
			else if ( subscriber.getSubscriptionType().equals( "postpaid" ) )
			{
				if ( subscriber.getBalanceDue() <= 1000 )
				{
					subscriber.sendText( subscriber.getSubscriptionType() );
					System.out.println( "text message sent by " + 
										subscriber.getSIMNumber() );
				}
				else System.out.println( "text message not sent" );
			}
		}
	}

	public void call( String s )
	{
		StringTokenizer token = new StringTokenizer( s, "|" );
		String number = token.nextToken();
		String numCalled = token.nextToken();
		String mins = token.nextToken();
		String refNum = token.nextToken();
		int callCost, timeAllowed, timeAvailable, balance, callAllowed;
		int callLength = new Integer( mins ).intValue();
		final int PREPAIDCOST = 8;
		final int POSTPAIDCOST = 6;
		double cost = 0;
		
		if ( subscriberList.contains( number ) )
		{
			Subscriber subscriber = subscriberList.getSubscriber( number );
			
			if ( subscriber.getSubscriptionType().equals( "prepaid" ) )
			{
				callCost = callLength * PREPAIDCOST;
				balance = (int)subscriber.getBalanceAvailable();
				timeAvailable =  balance / PREPAIDCOST;
				
				if ( balance == 0 | balance < PREPAIDCOST )
				{
					System.out.println( "call cannot be made." );
				}
				else if ( callCost > balance )
				{
					if ( timeAvailable < callLength )
					{
						cost = timeAvailable * PREPAIDCOST;
						Call call = new Call( number, numCalled, "" + timeAvailable, refNum, cost );
						showResult( call );
						subscriber.addCall( call );
					}
				}
				else 
				{
					cost = callLength * PREPAIDCOST;
					Call call = new Call( number, numCalled, "" + callLength, refNum, cost );
					showResult( call );
					subscriber.addCall( call );
				}
			}
			else if ( subscriber.getSubscriptionType().equals( "postpaid" ) )
			{
				callCost = callLength * POSTPAIDCOST;
				balance = (int)subscriber.getBalanceDue();
				callAllowed = ( 1000 - balance ) / POSTPAIDCOST;
				timeAvailable =  callAllowed / POSTPAIDCOST;
				
				if ( balance == 0 | balance >= 1000 )
				{
					System.out.println( "call cannot be made." );
				}
				else if ( callCost > callAllowed )
				{
					cost = timeAvailable * POSTPAIDCOST;
					Call call = new Call( number, numCalled, "" + timeAvailable, refNum, cost );
					showResult( call );
					subscriber.addCall( call );
				}
				else 
				{
					cost = callLength * POSTPAIDCOST;
					Call call = new Call( number, numCalled, "" + callLength, refNum, cost );
					showResult( call );
					subscriber.addCall( call );
				}
			}
		}
	}
	
	public void history( String x )
	{
		StringTokenizer token = new StringTokenizer( x, "|" );
		String t = token.nextToken();
		
		if ( !subscriber.empty() && subscriberList.contains( t ) )
		{
			Subscriber subscriber = subscriberList.getSubscriber( t );
			Enumeration callList = subscriber.getCallHistory().elements();
			
			System.out.println( "call history for " + subscriber.getSIMNumber() );
		
			while( callList.hasMoreElements() )
			{
				Call c = (Call)callList.nextElement();
				System.out.print( c.getCallReference() + ": " );
				System.out.print( c.getNumberCalled()  + " " );
				System.out.print( c.getCallDuration() + " minute(s)\n" );
			}
		}
	}

	public void due( String s )
	{
		StringTokenizer token = new StringTokenizer( s, "|" );
		String parameter = token.nextToken();
		Object prepaidList[] = subscriberList.getSubscribersList();

		if ( parameter.equals( "allpostpaid" ) )
		{
			for ( int x = 0; x < prepaidList.length; x++ )
			{
				String SIMNum = (String)prepaidList[ x ];
				Subscriber subscriber = subscriberList.getSubscriber( SIMNum );
				if ( subscriber.getSubscriptionType().equals( "postpaid" ) )
				{
					System.out.println( "balance due for " + subscriber.getSIMNumber() + 
										" is P" + subscriber.getBalanceDue() + "" );
				}
			}
		}
		else 
		{
			Subscriber subscriber = (Subscriber)subscriberList.getSubscriber( parameter );
			if ( subscriber.getSubscriptionType().equals( "postpaid" ) )
			{
				System.out.println( "balance due for " + subscriber.getSIMNumber() + 
								" is P" + subscriber.getBalanceDue() + "" );
			}
			else System.out.println( "not a postpaid subscriber" );
		}
	}

	public void balance( String s )
	{
		StringTokenizer token = new StringTokenizer( s, "|" );
		String parameter = token.nextToken();
		Object prepaidList[] = subscriberList.getSubscribersList();

		if ( parameter.equals( "allprepaid" ) )
		{
			for ( int x = 0; x < prepaidList.length; x++ )
			{
				String SIMNum = (String)prepaidList[ x ];
				Subscriber subscriber = subscriberList.getSubscriber( SIMNum );
				if ( subscriber.getSubscriptionType().equals( "prepaid" ) )
				{
					System.out.println( "balance for " + subscriber.getSIMNumber() + 
										" is P" + subscriber.getBalanceAvailable() + "" );
				}
			}
		}
		else 
		{
			Subscriber subscriber = (Subscriber)subscriberList.getSubscriber( parameter );
			if ( subscriber.getSubscriptionType().equals( "prepaid" ) )
			{
				System.out.println( "balance for " + subscriber.getSIMNumber() + 
								" is P" + subscriber.getBalanceAvailable() + "" );
			}
			else System.out.println( "not a prepaid subscriber" );
		}
	}
	
	public void subscribe( String p )
	{
		StringTokenizer token = new StringTokenizer( p, "|" );
		String s = token.nextToken();
		
		if ( subscriberList.contains( s ) )
		{
			System.out.println( "Subscriber already activated..." );
		}
		else
		{
			while ( token.hasMoreTokens() )
			{
				Subscriber subscriber = new Subscriber( s, token.nextToken(),
										token.nextToken(), token.nextToken() );
				subscriberList.register( subscriber );
				showResult( subscriber );
			}
		}
	}
	
	public void pay( String s )
	{
		StringTokenizer token = new StringTokenizer( s, "|" );
		String num = token.nextToken();
		String amt = token.nextToken();
		
		if ( subscriberList.contains( num ) )
		{
			Subscriber subscriber = subscriberList.getSubscriber( num );
			subscriber.payCall( amt );
			subscriber.clearList();
		}
		System.out.println( amt + " pesos paid by " + subscriber.getSIMNumber() );
	}
	
	public void load( String s )
	{
		StringTokenizer token = new StringTokenizer( s, "|" );
		String num, amt;
		num = token.nextToken();
		amt = token.nextToken();
		
		if ( subscriberList.contains( num ) )
		{
			subscriber = (Subscriber)subscriberList.getSubscriber( num );
			subscriber.addLoad( amt );
			System.out.println( subscriber.getBalanceAvailable() +
								" pesos loaded for " + subscriber.getSIMNumber() );
		}
	}
	
	public void undo( String parameter )
	{
		StringTokenizer token = new StringTokenizer( parameter, "|" );
		String num = token.nextToken();
		String ref = token.nextToken();
		
		subscriber = (Subscriber)subscriberList.getSubscriber( num );
		Call c = (Call)subscriber.getCall( ref );
		
		if ( subscriber.getSubscriptionType().equals( "prepaid" ) && !subscriber.empty() )
		{
			subscriber.setBalanceAvailable( c.getCost() );
			System.out.println( "credited " + c.getCost() + " pesos to " +
								c.getNumber() + " " + c.getCallReference() +
								": " + c.getNumberCalled() + " " +
								c.getCallDuration() + " minute(s) " );
			subscriber.refuteCall( ref );
		}
		else if ( subscriber.getSubscriptionType().equals( "postpaid" ) && !subscriber.empty() )
		{
			subscriber.setBalanceDue( c.getCost() );
			System.out.println( "credited " + c.getCost() + " pesos to " +
								c.getNumber() + " " + c.getCallReference() +
								": " + c.getNumberCalled() + " " +
								c.getCallDuration() + " minute(s) " );
			subscriber.refuteCall( ref );
		}
		else if ( subscriber.empty() )
		{
			System.out.println( "undo not allowed for this call.\n" );
		}
	}
	
	public void showResult( Subscriber s )
	{
		System.out.print( s.getSubscriptionType() );
		System.out.print( " number " + s.getSIMNumber() );
		System.out.print( " for " + s.getFirstName() );
		System.out.print( " " + s.getLastName() + " activated\n" );
	}	

	public void showResult( Call c )
	{
		System.out.print( "" + c.getCallReference() );
		System.out.print( ": " + c.getCallDuration() + "-minute call made" );
		System.out.print( " to " + c.getNumberCalled() );
		System.out.print( " from " + c.getNumber() + "\n" );
	}	
	
}