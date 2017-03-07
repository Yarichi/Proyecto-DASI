package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj;

/**
 * Abstract Class for Cognitive Control
 * @author Carlos Celorrio
 *
 */
public abstract class ProcesadorObjetivos implements ItfProcesadorObjetivos {

	/**
	 * Recieves an evidence to process
	 * @param ev The evidence
	 * @return Whether the evidence has been processed successfully
	 */
    @Override
    public abstract void insertarHecho(Object fact);
    @Override
    public abstract void eliminarHecho(Object objeto);
    @Override
    public abstract void actualizarHecho(Object objeto);
    
}
