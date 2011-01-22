
package commandserver;
//Filename: CandCserverMenuUI.java
//21 December, 2010

import java.util.Scanner;

/**
 * Rudimentary command line, console based ui
 * Used for troubleshooting and functionality verification; will
 * likely be replaced with graphical version.  
 * 
 * @author W. Taff and P. Salevski
 *
 */
public class CandCserverMenuUI implements Runnable {
	
	
	/** need access to db to invoke methods */
	private ClientDatabase db; 
	
	
	
	
	
	public CandCserverMenuUI(ClientDatabase dbInput){
		
		this.db = dbInput;
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		uiConsole();
		
		
	}//end run

	
	
	
	
	private void uiConsole() {
		
		//OF FORM: commands, module numbers (if any), and targets
		// e.g. MOD_0:ALL  or maybe PRINT:ALL
		// if no target, assume ALL
		
		Scanner adminInputScanner = new Scanner(System.in);
		
		String inputString = "";
		
		int cmdDelimValue;
		
		String command = null;
		
		String target = null;
		
		int moduleNumber = 999;		
		
		while (inputString.compareTo("QUIT")!=0){
			
			inputString = adminInputScanner.next();
			
			inputString = inputString.toUpperCase();
			
			cmdDelimValue = inputString.length();

			
			try {
				if (inputString.contains(":")){
				
					cmdDelimValue = inputString.indexOf(":"); 
									
					command = inputString.substring(0,
						cmdDelimValue);

					target = inputString.substring(cmdDelimValue
						+ 1);
				
				}
				
				else {
					
					command = inputString;
					
					target = "ALL";
					
				}
				
				
				if (inputString.contains("_")){
					
					int modDelimValue = inputString.indexOf("_") + 1; 
					
							
					moduleNumber = Integer.parseInt(inputString.
							substring(modDelimValue, cmdDelimValue) );
					
					command = command.substring(0, modDelimValue -1 );
					
				
				}
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
				System.out.println("ERROR ON PARSE OF INPUT");

				
			}
			
			
			System.out.println("Command is:" 
					+ command );
			
			
			System.out.println("Target is:" 
					+ target );
			
			
			System.out.println("Module Number is:" 
											+ moduleNumber );
			
			
			//THE COMMANDS
			
			
			if (command.compareTo("PRINT") == 0){
				
				print(target);
				
			}
			
			
			else if (command.compareTo("HALT") == 0){
								
				halt(target);
				
			}
			
			else if (command.compareTo("MOD") == 0){
				
				mod(moduleNumber, target);
				
			}
			
			else {
				
//				db.getRecord(command).getCC().
//							sendMessage2Client(value);
				
			}//end else
			
			
			
		}//end while
		
		System.out.println("Got quit command");
		
		System.exit(0);
		
	}








	private void mod(int moduleNumber, String target) {

		System.out.println("Running MOD_" + moduleNumber);
		
		db.run_module(moduleNumber);
		
	}
	
	
	
	

	private void halt(String target) {
		
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
		
		if (target.compareTo("ALL")==0){

			String printBuffer = db.getAllrecordsFromDB();
			
			System.out.println("Host, Exercise, Inbox, Status");
			
			System.out.println(printBuffer);
		
		}//endif
		
		
	}//END print()

}//end class
