package icaro.infraestructura.patronAgenteCognitivo.percepcion.imp;

import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;

/**
 * Agent's Perception Item Processor
 *
 */
public interface ItfProcesadorItems {
	
	public boolean procesarItem(Object item);
    public void SetProcesadorEvidencias(ItfProcesadorObjetivos procesadorExtractedItems);
	public void termina();
	void arranca();
	
	//Nuevos metodos para parar y volver a arrancar el hilo
	public void pararProcesoEnvioInfoExtracted();
	public void continuarProcesoEnvioInfoExtracted();

}
