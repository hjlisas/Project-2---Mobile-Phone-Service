// CS290 Project 2 | SIMServer.java
// by Hubert Jay J. Lisas, AdMU, July 2002

import java.io.*;
import java.util.*;

public class SIMServer
{
	public static void main( String[] args )
	{
		SIMServer SIM = new SIMServer( "trans.txt" );
	}

	public SIMServer( String fileName )
	{
		TransactionServer transaction = new TransactionServer();
		String tempString, command, parameters;
	
		try
		{
			BufferedReader input = new BufferedReader( new FileReader( fileName ) );
			parameters = "";
			command = "";
			tempString = "";
		
			while( ( tempString = input.readLine() ).length() != 0 )
			{
				StringTokenizer tokens = new StringTokenizer( tempString, " " );
				command = tokens.nextToken();
				while ( tokens.hasMoreTokens() )
				{
					parameters += tokens.nextToken() + "|";
				}
				
				if ( command.equals( "subscribe" ) )
				{
					transaction.subscribe( parameters );
				}
				else if ( command.equals( "call" ) )
				{	
					transaction.call( parameters );
				}
				else if ( command.equals( "history" ) )
				{
					transaction.history( parameters );
				}
				else if ( command.equals( "text" ) )
				{
					transaction.text( parameters );
				}
				else if ( command.equals( "load" ) )
				{
					transaction.load( parameters );
				}
				else if ( command.equals( "balance" ) )
				{
					transaction.balance( parameters );
				}
				else if ( command.equals( "due" ) )
				{
					transaction.due( parameters );
				}
				else if ( command.equals( "pay" ) )
				{
					transaction.pay( parameters );
				}
				else if ( command.equals( "undo" ) )
				{
					transaction.undo( parameters );
				}
				else 
				{
					System.out.println( "* Unrecognized command or invalid input! *\n" );
				}
				parameters = "";
				command = "";
			}
			input.close();
		}
		catch( Exception e ) {}
	}
	
}