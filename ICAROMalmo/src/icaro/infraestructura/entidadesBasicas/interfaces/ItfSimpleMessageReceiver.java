package icaro.infraestructura.entidadesBasicas.interfaces;

import icaro.infraestructura.entidadesBasicas.comunicacion.SimpleMessage;

/**
 * Interface for components that are able to receive messages
 * @author Carlos Celorrio
 *
 */
public interface ItfSimpleMessageReceiver {
	
	/**
	 * Receives and deals with a new message
	 * @param message The message to deal with
	 * @throws Exception A exception can be thrown if the receiver wasn't able to deal with the message
	 */
	 public void receiveMessage(SimpleMessage message);

}
