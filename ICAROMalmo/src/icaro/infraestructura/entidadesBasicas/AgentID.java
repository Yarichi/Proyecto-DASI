package icaro.infraestructura.entidadesBasicas;

// import icaro.infrastructure.resource.communication.CommunicationFactory;

import java.io.Serializable;
/*
/**
 * Represents the identifier of an Agent, composed by a local name and the node id.
 * @author Carlos Celorrio
 * @version 1.0
 */
public class AgentID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8594220966975224304L;

	
	private String localName;
	private String nodeId;
	
	/**
	 * Constructor for AgentID
	 * @param localName Name of the agent instance
	 */
	public AgentID(String localName) {
		this.localName = localName;
		try {
//			this.nodeId = CommunicationFactory.getCommunication().getFrameworkID();
		} catch (Exception e) {		
			// Should not happen
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor for AgentID
	 * @param localName Name of the agent instance
	 * @param nodeId Id of the agent's node
	 */
	public AgentID(String localName, String nodeId) {
		this.localName = localName;
		this.nodeId = nodeId;
	}
	
	
	public String getLocalName() {
		return localName;
	}
	
	
	public String getNodeId() {
		return nodeId;
	}
	
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	@Override
	public String toString() {
		return localName + "@" + nodeId;		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof AgentID))
			return false;
		
		AgentID other = (AgentID) obj;
		return localName.equals( other.localName ) && nodeId.equals( other.nodeId );
	}
	
	
	@Override
	public int hashCode() {
		return localName.hashCode() * 53 + nodeId.hashCode()*21;
	}
	
	public static AgentID parse(String string) {
		String name = string.substring( 0, string.indexOf('@') );
		String nodeId = string.substring( string.indexOf('@') );
		return new AgentID(name,nodeId);
	}
}
