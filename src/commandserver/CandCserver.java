// Filename: CandCserver.java
// 03 November 2010
package commandserver;

import java.io.IOException;
import java.net.*;

/**
 * The server - top level for program, and listener for connections.
 * Sits and listens for connections, then passes them off to database.  
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
		

		System.out.println ("Server Listening on port " + listenPort);
		
		
		try {
			
			ServerSocket server = new ServerSocket (listenPort);
			
			while (true){
			
				System.out.println ("Waiting");
				
				clntSock = server.accept();
				
				System.out.println ("Connection Accepted from " 
						+ clntSock.getInetAddress() );
				
				Thread thread = new Thread(
						new ClientCommunicator(clntSock, dataBase));
				
				thread.start();
				
				//System.out.println("Created and started Thread: " + 
				//		thread.getName() );
				
				//shouldn't need to ever close the socket...
				//server.close();
			
			
			}//end while
			
		}
		catch (IOException ioe) {
			System.err.println (ioe);
		}
		
		
	}

}
