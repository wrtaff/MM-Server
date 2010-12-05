// Filename: ClientCommunicator.java
// 06 November 2010

package commandserver;

import java.net.*;
import java.util.SortedMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * A handler that maintains the session between server and client.
 * Runs as a thread that is started by server.  On run, spins off 
 * a threaded Client Listener to accept input, and calls MM-node
 * for Name and Status.  
 * 
 * @author W. Taff and P. Salevski
 */
public class ClientCommunicator implements Runnable{
	
	private Socket ccSocket;
	
	private PrintStream outPrintStream;
	
	private ClientDatabase db;
	
	
	// CONSTRUCTOR
	public ClientCommunicator(Socket passedSocket, 
			ClientDatabase db) {
		
		this.ccSocket = passedSocket; 
		
		this.db = db;
		
		//make the output stream.  input stream made in run()
		try {
			
			this.outPrintStream = new PrintStream( 
					ccSocket.getOutputStream() );
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}//end ClientCommunicator()


	
	
	
		
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			
			ccListenerStarter() ;
			
			outPrintStream.println("#GETNAME");
			
		}//end run()

		
		
		
		
		
		
		/**
		 * Starts the ccListener.
		 * Use the ccListener for input coming in from the MM-node.
		 */
		private void ccListenerStarter(){
			
			try {
				
				//spin off new ccListener
				
				Thread thread = new Thread(
						new ClientCommunicatorListener(
								ccSocket.getInputStream(), db, this));
				thread.start();
				
				System.out.println("Created and started Thread: " + 
						thread.getName() );
						
				
			} catch (IOException e) {
			
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}//end ccListenerStarter()
		
		
		
		
		
		
		
		/**
		 * Externally callable session terminator - closes socket.  
		 */
		public void terminateSession(){
			
			sendMessage2Client("Session Terminated");
			
			try {
				ccSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}//end terminateSession()

		
		
		
		
		
		
		
		
		/**
		 * @param clientName
		 */
		public void createRecordForClient(String clientName){
			
			db.createRecord(clientName, this);
			
		}
		
		
		
		
		/**
		 * @param msg
		 */
		public void sendMessage2Client(String msg){
			
			outPrintStream.println(msg);
			
		}
		

	
}//end class
