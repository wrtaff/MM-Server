package commandserver;
// Filename: ClientDatabase.java
// 21 December, 2010


import java.util.TreeMap;
import java.util.SortedMap;
import java.util.Collections;

/**
 * The database of ClientRecords.
 * Uses a TreeMap (for now) as the data structure, 
 * and ClientRecords as the nodes.  
 * 
 * @author W. Taff and P. Salevski
 *
 */
public class ClientDatabase {
	
	
	///////////////////////////////////////////
	//DATA MEMBERS 
	///////////////////////////////////////////

	
	/**the database data structure of ClientRecord*/
	private SortedMap< String, ClientRecord > dbase =
		Collections.synchronizedSortedMap( 
				new TreeMap< String, ClientRecord >() );
	
	///////////////////////////////////////////
	//METHODS 
	///////////////////////////////////////////
	
	
	/**
	 * Constructor for ClientDatabase
	 * 
	 * */
	public ClientDatabase () {

	}
	
	
	
	
	
	/**
	 * Creates a record in the database. 
	 * @param hostID - uid_host of the host we are creating (used as key)
	 * @param ccIn  - the calling Client Communicator
	 * @return True if successfully created
	 * */
	public Boolean createRecord(String hostID, 
										ClientCommunicator ccIn) {
		
		String exerciseID = "INIT";
		
		//build the record
		ClientRecord tempRecord = 
				new ClientRecord(hostID, exerciseID, ccIn, this); 
		
		//put the record in the db
		try {
			
			 dbase.put(hostID, tempRecord);
			 
			 System.out.println("Added record for " + hostID);
			 
		}
		
		catch (ClassCastException cce) {
			
			 System.err.println(cce);
		}
		
		catch (NullPointerException npe) {
			
			 System.err.println(npe);
		}		

		return true;
	}
	
	
	
	
	
	/**
	 * Print record in the database to the console.
	 * Prints instance of record to the console, including the
	 * contents of inbox, status, the client UID and the exercise network.
	 * @param hostID - uid_host of the host of interest
	 * */
	public void printRecords(String hostID) {
		ClientRecord tempClientRecord = null;
		
		// gets the record from the TreeMap that has the hostID key
		tempClientRecord = getRecord(hostID);
		
		System.out.println("Printing all information for Record: " +
				hostID);
		System.out.println("Client Inbox " + 
				tempClientRecord.getClientInbox() );
		System.out.println("Client Status " + 
				tempClientRecord.getClientStatus() );
		System.out.println("Client uid_Host " + 
				tempClientRecord.getUID_Host() );
		System.out.println("Client uid_ExerciseNetwork " + 
				tempClientRecord.getUID_ExerciseNetwork() );
	}
	
	
	
	/**
	 * get a client record from the database.
	 * Pulls an instance of ClientRecord from the database, for use as 
	 * a helper function for class functions.   
	 * @param hostID - uid_host of the host of interest
	 * @return ClientRecord
	 * 
	 * */
	public ClientRecord getRecord(String hostID) {
		// gets the record from the TreeMap that has the hostID key
		ClientRecord tempClientRecord = null;
		try {
			tempClientRecord = dbase.get(hostID);
		}
		catch (ClassCastException cce) {
			 System.err.println(cce);
		}
		catch (NullPointerException npe) {
			 System.err.println(npe);
		}	
		return tempClientRecord;
	}
	
	
	
	/**
	 * Returns String of all database inbox and status for all MM-C.
	 * Typically used for console troubleshooting. 
	 * 
	 * @return
	 */
	public String getAllrecordsFromDB(){
		
		String returnString = "";
		
		for (String keyString : dbase.keySet() ) {
			
			returnString += keyString + "," + 
					dbase.get(keyString).getClientInbox() + "," +
					dbase.get(keyString).getClientStatus() +"\n" ;
			
		}//end for-loop
		
				
		return returnString;
		
	}
	
	
	
	
	/**
	 * deletes a client record from the database.
	 * Will attempt to remove a client record from the database, 
	 * based on the host UID provided.   
	 * @param hostID - uid_host of the host of interest
	 * @return True of record and deleted, False if record not found
	 * 
	 * */
	public Boolean deleteRecord(String hostID) {
		
		// tries to delete the record
		Boolean deleteSuccess;
		
		ClientRecord tempClientRecord = null;
		
		try {
			
			tempClientRecord = dbase.remove(hostID);
			
		}
		
		catch (ClassCastException cce) {
			
			 System.err.println(cce);
		}
		
		catch (NullPointerException npe) {
			
			 System.err.println(npe);
			 
		}	
		
		if (tempClientRecord == null)
			
			deleteSuccess = false;
		
		else {
			
			deleteSuccess = true;	
		}
		
		return deleteSuccess;
		
	}
	
	/////////////////////////////
	// UPDATE (SET) METHODS
	/////////////////////////////
	

	
	/**
	 * Halts running module - OVERLOADED METHOD.  
	 * Called without arguments, halts running module in all 
	 * modules.  Simple iteration over dbase, setting client 
	 * inboxes to HALT.  
	 */
	public void halt_module(){
		
		for (String keyString : dbase.keySet() ) {
			
			dbase.get(keyString).setClientInbox( "HALT" );
			
		}//end for-loop
		
		
	}//end halt_running_mods()
	
	
	
	
	
	
	
	/**
	 * 	Starts running module - OVERLOADED METHOD.  
	 * Called without target arguments, starts running module in all 
	 * modules.  Simple iteration over dbase, setting client 
	 * inboxes to MOD_X, where X is the module number. 
	 * 
	 * @param moduleNumber
	 */
	public void run_module(int moduleNumber){
		
		for (String keyString : dbase.keySet() ) {
			
			dbase.get(keyString).
				setClientInbox("MOD_" + moduleNumber);
			
		}//end for-loop

	}// end run_module()
	
	
	

	
}
