package icaro.infraestructura.entidadesBasicas.interfaces;

import icaro.infraestructura.entidadesBasicas.comunicacion.ReactiveInputMessage;

/**
 * Interface for components that are able to receive reactive input messages
 * @author Carlos Celorrio
 *
 */
public interface ItfReactiveInputMessageReceiver extends ItfSimpleMessageReceiver {
	
	/**
	 * Receives and deals with a new reactive input message
	 * @param input The reactive input message
	 * @throws Exception A exception can be thrown if the receiver wasn't able to deal with the input
	 */
	public void aceptaEvento(ReactiveInputMessage input) throws Exception;

}
