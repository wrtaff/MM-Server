/**
 * 
 */
package commandserver;

import java.io.*;

/**
 * @author W. Taff and P. Salevski
 * Handles input from MM-node.  
 * Parses MM-node messages and makes appropriate system calls.
 *
 */
public class ClientCommunicatorListener implements Runnable{
	
	///////////////////////////////////////////
	//DATA MEMBERS 
	///////////////////////////////////////////
	
	
	/** passed - will bolt on top a BufferedReader */
	private InputStream inStream;
	
	/** use for reading incoming messages from MM-Client */
	private BufferedReader inBufferedReader; 
		
	/** gives ability to call ClientDatabase fns 	 */
	private ClientDatabase db;
	
	/** gives ability to call back to the calling CC */
	private ClientCommunicator parentCC;
	
	
	
	
	///////////////////////////////////////////
	//METHODS 
	///////////////////////////////////////////
	
	
	//CONSTRUCTOR
	public ClientCommunicatorListener(InputStream inputStream,
			ClientDatabase db, ClientCommunicator callingCC) {
		
		this.db = db ;
		
		this.inStream = inputStream;
		
		this.inBufferedReader = 
			new BufferedReader(new InputStreamReader(inStream));
		
		this.parentCC = callingCC;
				
	}
	
	
	public void run() {
		
		listenLoop();
		
				
		
	}//end run()	

	
	
	
	
	

	/**
	 * Main loop of CCListener.
	 * Blocks on readlines from MM-Client.  Calls appropriate db
	 * methods based on input passed up from MM-Client.  
	 * 
	 */
	private void listenLoop() {

		Boolean keepGoing = true;
		
		String textReceived = "";
		
		while ( keepGoing ) {
		
			try {
					
				textReceived = inBufferedReader.readLine();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
		
			
			if ( textReceived.compareTo("QUIT")==0 ){
				
				parentCC.terminateSession();
				
				keepGoing = false;
				
			}
			
			else if ( textReceived.contains("GETINBOX")){
				
				parentCC.sendMessage2Client(db.getRecord(
						parentCC.getKeyname()).getClientInbox() );
				
			}
			
			
			else if ( textReceived.contains("=") ){
				
				setVariableValue(textReceived);
				
				
			}//end if
			
			//RESET THE TEXT OR WE SPIN
			textReceived = "";
						

		} // end while

		
	}


	
	
	
	
	
	/**
	 * Set a db key/value pair based on input from MM-client
	 *  
	 * @param textReceived
	 */
	private void setVariableValue(String textReceived) {
		
		int delimValue = textReceived.indexOf("="); 
		
		String key = textReceived.substring(0, delimValue);
		
		String value = textReceived.substring(delimValue + 1);
		
		
		if (key.compareTo("NAME")==0) {
			
			parentCC.setKeyname(value);
			
			db.createRecord(value, parentCC);
							
		}
		
		else if (key.compareTo("STATUS")==0) {
			
			//with key, set the status
			db.getRecord(parentCC.getKeyname()).
			   								setClientStatus(value);
								
		}
		

		else if (key.compareTo("EXERCISE")==0){
			
			db.getRecord(parentCC.getKeyname()).setExercise(value);
								
		}
		
	}
	
}
