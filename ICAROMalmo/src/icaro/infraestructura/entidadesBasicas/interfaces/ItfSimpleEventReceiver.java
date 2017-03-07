package icaro.infraestructura.entidadesBasicas.interfaces;

import icaro.infraestructura.entidadesBasicas.comunicacion.SimpleEvent;

/**
 * Interface for components that are able to receive events
 * @author Carlos Celorrio
 *
 */
public interface ItfSimpleEventReceiver {
	
	/**
	 * Receives and deals with a new event
	 * @param event The event to deal with
	 * @throws Exception A exception can be thrown if the receiver wasn't able to deal with the event
	 */
	public void receiveEvent(SimpleEvent event) throws Exception;

}
