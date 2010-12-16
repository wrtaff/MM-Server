/**
 * 
 */
package commandserver;

import java.util.Scanner;

/**
 * A temp class that makes a text based UI for CandCserver.
 * At least, until Paul is finished with the GUI version.
 * 
 * @author will
 *
 */
public class CandCserverGUI implements Runnable {
	
	private ClientDatabase db; 
	
	public CandCserverGUI(ClientDatabase dbInput){
		
		this.db = dbInput;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	//@Override
	public void run() {
		
		Scanner adminInputScanner = new Scanner(System.in);
		String inputString = adminInputScanner.next();
		while (inputString.compareTo("QUIT")!=0){
			
			if (inputString.compareTo("PRINT:ALL") == 0){
				printAll();
			}
			else {
				int delimValue = inputString.indexOf(":"); 
				System.out.println(delimValue);
				String key = inputString.substring(0, delimValue);
				String value = inputString.substring(delimValue + 1);
				
				db.getRecord(key).getCC().sendMessage2Client(value);
			}//end else
			
			inputString = adminInputScanner.next();
			
			
			
			
		}//end while
		
		System.out.println("Got quit command");
		
	}

	/**
	 * Print all records in the database.  
	 */
	private void printAll() {
		// TODO Auto-generated method stub
		String printBuffer = db.getAllrecordsFromDB();
		System.out.println(printBuffer);
	}

}
