/**
 * 
 */
package commandserver;

import java.io.*;

import com.sun.org.apache.xpath.internal.operations.Variable;

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

	private InputStream inStream;
	
	private BufferedReader inBufferedReader; 
	
	private ClientDatabase db;
	
	private ClientCommunicator callingCC;

	
	
	
	
	
	///////////////////////////////////////////
	//METHODS 
	///////////////////////////////////////////
	
	

	public ClientCommunicatorListener(InputStream inputStream,
			ClientDatabase db, ClientCommunicator callingCC) {
		
		this.db = db ;
		
		this.inStream = inputStream;
		
		this.inBufferedReader = 
			new BufferedReader(new InputStreamReader(inStream));
		
		this.callingCC = callingCC;
				
	}
	
	
	//@Override
	public void run() {
		 
		Boolean carryOn = true;
		
		String textReceived = "";
		
		System.out.println( Thread.currentThread().getName() +
										"Listener up and listening");
		db.printUpandListening();

		while ( carryOn ) {

			try {

				if (inBufferedReader.ready()) {
					textReceived = inBufferedReader.readLine();
					System.out.println(textReceived);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			if ( textReceived.compareTo("quit")==0 ){
				System.out.println("quitting...");
				callingCC.terminateSession();
				carryOn = false;
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
					
					callingCC.createRecordForClient(value);
					//RESET THE TEXT OR WE SPIN
					textReceived = "";		
					
				}//end inner if
				
			}//end outer if
			

						
			
			 try {
				 
				 java.lang.Thread.sleep(10);
			 }
			 catch (InterruptedException e) {
				 System.err.println(e);
			 }
			
		} // end while loop
		
		
	}//end run()	
	
}
