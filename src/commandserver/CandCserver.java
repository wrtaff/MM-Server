/****************************************************************/
/*                                                              */
/*      Program:  Malware Mimic Server					        */
/*                                                              */
/*      Top level for the MM-Server.  Interfaces with the user  */
/*      via the UI. Handles incoming TCP connections on all     */
/* 		interfaces on TCP.port == 30000 with remote or local    */
/* 		MM-Clients.  Maintains and closes TCP sessions with     */
/*      MM-Clients.  Translates user instructions to MM-Clients */
/*      and passes the commands to MM-Clients.  Allows input    */
/* 		from MM-Clients to UI.  Maintains state on MM-Clients.  */
/*      FILE:       ClientProgram.java			                */
/*                                                              */
/*      USAGE: ./MM-Server <with no paramters>					*/
/*                                                              */
/*                                                              */
/*			AUTHORS: W. Taff and P. Salevski                    */
/*                                                              */
/*			DATE: 22 January 2011			                    */
/*                                                              */
/****************************************************************/


package commandserver;

import java.io.IOException;
import java.net.*;

/**
 * The server - top level for program, and listener for connections.
 * Initializes the database.  Starts the UI.  
 * Sits and listens for connections, spins off CC-Communicators
 * to handle them and passes off Socket to same, then reset to 
 * listen.   
 * 
 * @author W. Taff and P. Salevski
 *
 */
public class CandCserver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		///////////////////////////////////////////
		//INITIALIZATION
		///////////////////////////////////////////
		
		ClientDatabase dataBase = new ClientDatabase();
		
		Integer listenPort = 30000;

		Socket clntSock = null;		
				
		
		//start the UI
		Thread GUIthread = new Thread(
				new CandCserverMenuUI(dataBase));
		GUIthread.start();
		
		
		
		try {
			
			ServerSocket server = new ServerSocket (listenPort);
		
			System.out.println ("Server Listening on port "
														+ listenPort);
			
			
			while (true){
			
				System.out.println ("Waiting");
				
				clntSock = server.accept();
				
				System.out.println ("Connection Accepted from " 
						+ clntSock.getInetAddress() );
				
				Thread thread = new Thread(
						new ClientCommunicator(clntSock, dataBase));
				
				thread.start();
				
			
			}//end while
			
		}
		catch (IOException ioe) {
			System.err.println (ioe);
		}
		
		
	}

}
