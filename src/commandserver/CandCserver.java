// Filename: CandCserver.java
// 21 December, 2010
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
