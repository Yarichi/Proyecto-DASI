package icaro.infraestructura.entidadesBasicas.interfaces;

/**
 * Represents the basic use interface that all ICARO agents must comply
 * @author Carlos Celorrio
 */
public interface ItfUseAgent extends ItfSimpleEventReceiver, ItfSimpleMessageReceiver {

	/**
	 * Gets the name of the agent in the current local node
	 * @return The name of the agent
	 */
	public String getLocalName();
	
}
