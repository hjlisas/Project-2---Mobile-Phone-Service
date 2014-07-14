// CS290 Project 2 | SubscriberList.java
// by Hubert Jay J. Lisas, AdMU, July 2002

import java.util.*;

public class SubscriberList
{
	Hashtable subscribers;
	
	public SubscriberList()
	{
		subscribers = new Hashtable();
	}
	
	public void register( Subscriber subscriber )
	{
		subscribers.put( subscriber.getSIMNumber(), subscriber );
	}
	
	public boolean contains( String number )
	{
		return subscribers.containsKey( number );
	}
	
	public Object[] getSubscribersList()
	{
		return subscribers.keySet().toArray();
	}

	public Subscriber getSubscriber( String number )
	{
		return (Subscriber)subscribers.get( number );
	}
	
}