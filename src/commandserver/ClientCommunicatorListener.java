package commandserver;
//Filename: ClientCommunicator.java
//21 December, 2010


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
		
		//Need try/catch to make severed sessions graceful
		try {
			
			listenLoop();
			
		} catch (NullPointerException e) {
			
			e.printStackTrace();
			
		}
		
		catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		finally {
			
			System.out.println("Connection Lost to " + 
					parentCC.getKeyname());
			
			parentCC.terminateSession();
		    db.getRecord(parentCC.getKeyname()).
		    								setClientStatus("LOST");
			
		}
		
		
	}//end run()	

	
	
	
	
	

	/**
	 * Main loop of CCListener.
	 * Blocks on readlines from MM-Client.  Calls appropriate db
	 * methods based on input passed up from MM-Client.  
	 * @throws Exception 
	 * 
	 */
	private void listenLoop() throws Exception {

		Boolean keepGoing = true;
		
		String textReceived = "";
		
		while ( keepGoing ) {
	
			textReceived = inBufferedReader.readLine();
			
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
