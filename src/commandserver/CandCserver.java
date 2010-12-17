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
		
		//TODO CLEAN initialization stuff - have to make the database:
		ClientDatabase testDBase = new ClientDatabase();
				
		//TODO - start the UI - will strike this
		Thread GUIthread = new Thread(
				new CandCserverMenuUI(testDBase));
		GUIthread.start();
		

		//we'll need to have a socket listener here
		Integer port = 30000;

		Socket clntSock = null;
		System.out.println ("Server Listening on port " + port);
		
		
		try {
			
			ServerSocket server = new ServerSocket (port);
			
			while (true){
			
				System.out.println ("Waiting");
				
				clntSock = server.accept();
				
				System.out.println ("Accepted from " 
						+ clntSock.getInetAddress() );
				
				Thread thread = new Thread(
						new ClientCommunicator(clntSock, testDBase));
				
				thread.start();
				
				System.out.println("Created and started Thread: " + 
						thread.getName() );
				
				//shouldn't need to ever close the socket...
				//server.close();
				
				//and we'll need to pass the cxn off here
				System.out.println("sending socket off...");
			
			
			}//end while
		}
		catch (IOException ioe) {
			System.err.println (ioe);
		}
		
		
	}

}
