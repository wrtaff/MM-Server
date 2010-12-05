package commandserver;
// Filename: ClientRecord.java
// 03 November 2010

import java.net.*;
import java.util.SortedMap;

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
	
	
	/**unique identifier of host, based on mac and hostname */
	private String uid_Host;
	
	
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
	public ClientRecord (String hostID, 
						String exerciseID, 
						ClientCommunicator passedCC, 
						ClientDatabase db) {
		
		
		this.uid_Host = hostID;
		
		this.uid_ExerciseNetwork = exerciseID;
		
		this.cc = passedCC;
		
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
		
		status = "Client done reading inbox, received message: " 
			+ clientInbox;
		
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
	 * get the UID of the host.  Useful for printing reports.  
	 * @return uid_Host
	 */
	public String getUID_Host(){
		//TODO - make uid_Host only appear at ClientDatabase?  
		return uid_Host;
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
	// no SET method for uid_Host since that is the key for this node in the treemap
	/////////////////////////////
	
	
	
	
	/**
	 * sets the client inbox to a text message written by server
	 * @param message - set by server at inbox in record of client
	 */
	public void setClientInbox(String message){
		clientInbox = message;
	}
	
	
	
	
	/**
	 * sets the client status
	 * written by the client, read by the server
	 * @param message
	 */
	public void setClientStatus(String message){
		status = message;
	}
	
	
	
	
	/**
	 * sets the ExerciseNetwork 
	 * set by the client, read by the server
	 * @param message
	 */
	public void setExerciseNetwork(String message){
		uid_ExerciseNetwork = message;
	}
} // end of ClientRecord class
