package commandserver;
// Filename: ClientRecord.java
// 21 December, 2010

/**
 * ClientRecord - the records in the database.
 * Includes all fields associated with a single client, except for 
 * it's uid, which the record is keyed by in the database.  
 * @author W. Taff and P. Salevski
 *
 */
public class ClientRecord {
	
	///////////////////////////////////////////
	//DATA MEMBERS 
	///////////////////////////////////////////
	

	
	/**unique identifier of the exercise network */
	private String uid_ExerciseNetwork;
	
	
	/**status of the client, set by the client, read by server */
	private String status;
	
	
	/**inbox of the client, set by server, read by client */
	private String clientInbox;
	
	/**where the ClientCommunicator lives */
    private ClientCommunicator cc;
	
	
	
	
	
	///////////////////////////////////////////
	//METHODS 
	///////////////////////////////////////////
	

	
	/**
	 * Constructor for a ClientRecord - called by ClientDatabase.
	 * Gets passed hostID, exerciseID and a socket.  Initializes
	 * the class with the passed params, and makes empty for those 
	 * params that it does not yet have.  
	 * @param hostID - uid_host of the host we are creating (used as key)
	 * @param exerciseID - the UID of the exercise
	 * @param passedCC - the ClientCoummincator for the client.
	 * @param db - the database of clients
	 */
	public ClientRecord (ClientCommunicator passedCC,
											ClientDatabase db) {
		
		
		this.cc = passedCC;
		
		this.uid_ExerciseNetwork = "NOT_SET";
		
		this.status = "INITIALIZED";
		
		this.clientInbox = "INITIALIZED";
		
	}
	
	
		
	/////////////////////////////
	// GET METHODS
	/////////////////////////////
	
	
	/**
	 * returns the content of the client's inbox
	 * The client inbox is set by the server, but read by
	 * the client.  Consists of a plain text string value.   
	 * @return clientInbox - the contents of the client's inbox
	 */
	public String getClientInbox(){
		
		return clientInbox;
		
	}
	
	
	public ClientCommunicator getCC(){
		
		return this.cc;
		
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * for the commandServer to get status of the individual client
	 * @return status - the contents of the client's status box
	 */
	public String getClientStatus(){
		return status;
	}

	

	
	
	
	
	/**
	 * return the UID of the exercise network
	 * @return uid_ExerciseNetwork
	 */
	public String getUID_ExerciseNetwork() {
		return uid_ExerciseNetwork;
	}
	
	
	
	
	/////////////////////////////
	// SET METHODS
	/////////////////////////////
	
	
	
	
	/**
	 * Allows server to write message to client inbox. 
	 * Only servers shall write to the client inbox.  
	 * @param hostID - uid_host of the host of interest
	 * @param message - String of message FROM server TO client.  
	 * */
	public void setClientInbox(String message){
		
		clientInbox = message;
		
	}

	
	
	
	
	
	
	
	/**
	 * Allows client to set their status in status box of record. 
	 * Only clients shall write their status to their status box.  
	 * Read by the server to ascertain status of the client.  
	 * @param message - String of status FROM client.  
	 * 
	 * */
	public void setClientStatus(String message){
		
		status = message;
		
	}
	
	
	
	
	
	
	
	/**
	 * Allows client to set exercise in exercise field of record. 
	 * Only clients shall write their exercise to their exercise 
	 * field.  Read by the server to ascertain exercise of the client.  
	 * @param message - String of exercise FROM client.  
	 * 
	 * */
	public void setExercise(String message){
		
		uid_ExerciseNetwork = message;
		
	}
	
	
} // end of ClientRecord class
