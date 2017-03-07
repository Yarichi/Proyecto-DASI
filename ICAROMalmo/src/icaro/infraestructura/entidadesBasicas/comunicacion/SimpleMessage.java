package icaro.infraestructura.entidadesBasicas.comunicacion;

import icaro.infraestructura.entidadesBasicas.*;
import java.io.Serializable;

/**
 * 
 * @author Carlos Celorrio
 *
 */
public class SimpleMessage implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1624889937599726865L;
	
	private Object content;
	private Object sender;
	private AgentID receiver;



	public SimpleMessage() {
        
    }


	public SimpleMessage(Object content, Object sender, Object receiver) {
		this.content = content;
		this.setEmisor(sender);
		this.setReceptor(receiver);		
	}
	
	
	public Object getEmisor() {
		return sender;
	}
	
	
	public void setEmisor(Object emisor) {
		if(emisor instanceof AgentID)
			this.sender = (AgentID) emisor;
		else if(emisor!=null)
			this.sender = new AgentID(emisor.toString(),null );
	}
	
	
	
	public AgentID getReceptor() {
		return receiver;
	}
	
	public void setReceptor(Object receptor) {
		if(receptor instanceof AgentID)
			this.receiver = (AgentID) receptor;
		else if(receptor!=null)
			this.receiver = new AgentID(receptor.toString(),null );
	}
	
	public Object getContenido() {
		return content;
	}
	
	public void setContenido(Object contenido) {
		this.content = contenido;
	}
	
	
    public String toString(){
    	return "Emisor: "+sender+", Receptor: "+receiver;
    }
	
}