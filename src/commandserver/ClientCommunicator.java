// Filename: ClientCommunicator.java
// 21 December, 2010

package commandserver;

import java.net.*;
import java.io.IOException;
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
	
	
	/** Socket passed to CC by the SocketServer  */
	private Socket ccSocket;
	
	/** The output stream we use to push our messages onto the wire  */
	private PrintStream outPrintStream;
	
	
	/** the location of the db, so we can call it's methods */
	private ClientDatabase db;
	
	/** the keyname of the host that the CC relates to */
	private String keyname; 
	
	
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

			e.printStackTrace();
			
		}
		
		
	}//end constructor ClientCommunicator()


	

	
		
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		//@Override
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
				
				Thread listenerThread = new Thread(
						new ClientCommunicatorListener(
								ccSocket.getInputStream(), db, this));
				
				listenerThread.start();
				
				
						
				
			} catch (IOException e) {
			
				e.printStackTrace();
				
			}
			
			
		}//end ccListenerStarter()
		
		
		
		
		
		
		
		/**
		 * Externally callable session terminator - closes socket. 
		 * Also updates the status of the MM-Client in the db.  
		 */
		public void terminateSession(){
			
			sendMessage2Client("Session Terminated");
			
			db.getRecord(keyname).setClientStatus("TERMINATED");
			
			try {
				
				ccSocket.close();
								
			} catch (Exception e) {

				e.printStackTrace();
				db.getRecord(keyname).setClientStatus("LOST");
				
			}
			
			
		}//end terminateSession()

		

		
		/**
		 * Pushes any input string down to MM-Client
		 * @param msg
		 */
		public void sendMessage2Client(String msg){
			
			outPrintStream.println(msg);
			
		}



		/**
		 * @return the keyname
		 */
		public String getKeyname() {
			return keyname;
		}



		/**
		 * @param keyname the keyname to set
		 */
		public void setKeyname(String keyname) {
			this.keyname = keyname;
		}
		

	
}//end class
