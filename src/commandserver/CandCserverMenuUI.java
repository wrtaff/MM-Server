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
public class CandCserverMenuUI implements Runnable {
	
	//TODO - need comment this entire class.
	
	private ClientDatabase db; 
	
	public CandCserverMenuUI(ClientDatabase dbInput){
		
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
			
			
			int delimValue = inputString.indexOf(":"); 
						
			
			String command = inputString.substring(0,
					delimValue);
			
			
			String target = inputString.substring(delimValue
					+ 1);
			
			
			
			if (command.compareTo("PRINT") == 0){
				
				print(target);
			}
			
			
			else if (command.compareTo("HALT") == 0){
								
				halt(target);
			}
			
			else if (command.contains("MOD")){
				
				mod(command);
				
			}
			
			else {
				
				//db.getRecord(command).getCC().sendMessage2Client(value);
				
			}//end else
			
			inputString = adminInputScanner.next();
			
			
		}//end while
		
		System.out.println("Got quit command");
		
		System.exit(0);
	}

	
	
	
	
	private void mod(String command) {
		// TODO need implement running mods by Exercise
		
		int delimModuleNum = command.indexOf("_"); 
		
		System.out.println(command.
				substring(delimModuleNum+ 1));

		int modNumber = Integer.parseInt(command.
				substring(delimModuleNum+ 1));

		System.out.println("Running MOD_" + 
				modNumber);
		
		db.run_module(modNumber);
		
	}
	
	
	
	

	private void halt(String target) {
		// TODO Auto-generated method stub
		
		if (target.compareTo("ALL")==0){
		
		System.out.println("Halting All!");
		
		db.halt_module();
		
		}

	}

	/**
	 * Print records in the database.  
	 * @param target 
	 */
	private void print(String target) {
		// TODO make for specific target
		
		if (target.compareTo("ALL")==0){
		
			String printBuffer = db.getAllrecordsFromDB();
			
			System.out.println(printBuffer);
		
		}//endif
		
		
		
	}//END print()

}//end class
