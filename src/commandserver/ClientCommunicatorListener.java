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
//TODO ENTIRE CLASS NEEDS DOCUMENTATION
	private InputStream inStream;
	
	private BufferedReader inBufferedReader; 
	
	private ClientDatabase db;
	
	private ClientCommunicator parentCC;

	
	
	
	
	
	///////////////////////////////////////////
	//METHODS 
	///////////////////////////////////////////
	
	

	public ClientCommunicatorListener(InputStream inputStream,
			ClientDatabase db, ClientCommunicator callingCC) {
		
		this.db = db ;
		
		this.inStream = inputStream;
		
		this.inBufferedReader = 
			new BufferedReader(new InputStreamReader(inStream));
		
		this.parentCC = callingCC;
				
	}
	
	
	//@Override
	public void run() {
		 
		Boolean keepGoing = true;
		
		String textReceived = "";
		
		System.out.println( Thread.currentThread().getName() +
										"Listener up and listening");
		db.printUpandListening();

			//TODO - clean this code train wreck for readability - make fn's, including an assignment fn for '='? 
		while ( keepGoing ) {

			try {

				if (inBufferedReader.ready()) {
					textReceived = inBufferedReader.readLine();
					System.out.println(textReceived);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			if ( textReceived.compareTo("QUIT")==0 ){
				System.out.println("quitting....");
				parentCC.terminateSession();
				keepGoing = false;
			}
			
			else if ( textReceived.contains("GETINBOX")){
			
				
				String inbox = db.getRecord(parentCC.getKeyname())
					.getClientInbox();
				
				parentCC.sendMessage2Client(inbox);
				
				
			}
			
			
			else if ( textReceived.contains("=") ){
				
				int delimValue = textReceived.indexOf("="); 
				String key = textReceived.substring(0, delimValue);
				String value = textReceived.substring(delimValue + 1);
				System.out.println("Variable = " + key);
				System.out.println("Value = " + value );

				System.out.println("MM-node reports " + key + 
						"=" + value);
				
				if (key.compareTo("NAME")==0) {
					
					parentCC.setKeyname(value);
					
					db.createRecord(value, parentCC);
									
				}//end inner if
				
				else if (key.compareTo("STATUS")==0) {
					
					//with key, set the status
					db.getRecord(parentCC.getKeyname()).setClientStatus(value);
										
				}// end status if
				

				
				else if (key.compareTo("EXERCISE")==0){
					
					db.getRecord(parentCC.getKeyname()).setExercise(value);
										
				}//end exercise if

				
			}//end outer if
			
			//RESET THE TEXT OR WE SPIN
			textReceived = "";
						
			
			 try {
				 
				 java.lang.Thread.sleep(10);
			 }
			 catch (InterruptedException e) {
				 System.err.println(e);
			 }
			
		} // end while loop
		
		
	}//end run()	
	
}
