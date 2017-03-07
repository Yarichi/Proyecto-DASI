package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.ExtractedInfo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.ItfGestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfConfigMotorDeReglas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfMotorDeReglas;
import java.util.Collection;

/**
 * Interface for Cognitive Control
 * @author F Garijo
 *
 */
public interface ItfProcesadorObjetivos {
	
	/**
	 * Recieves an evidence to process
     * @param extrInfo  informacion extraida de un mensaje o de un evento
	 * @return Whether the evidence has been processed successfully
	 */
    public boolean procesarExtractedInfo(ExtractedInfo extrInfo);   
//    public boolean procesarEvidencia(Evidencia ev);
//    public boolean procesarCreencia(Creencia cre);
                
    public void arranca();
    public void insertarHecho(Object fact);
    public void eliminarHecho(Object objeto);
    public void actualizarHecho(Object objeto);	    
	//JM: Nueva funcionalidad anadida
    public void insertarHechoWithoutFireRules(Object fact);
    public void eliminarHechoWithoutFireRules(Object objeto);
    public void actualizarHechoWithoutFireRules(Object objeto);
 //   public StatefulKnowledgeSession getStatefulKnowledgeSession();
    public ItfConfigMotorDeReglas getItfConfigMotorDeReglas ();
    public ItfMotorDeReglas getItfMotorDeReglas ();
    public ItfGestorTareas GetItfGestorTareas ();
    public boolean cambiarComportamiento(String identFicheroReglasComportamiento);
    public void reinicializarComportamiento(); // elimina la sesion actual y crea otra nueva en el estado inicial 
    public Collection <Object> copiarObjetosDeMiMemoria();
    public void insertarObjetosEnMiMemoria(Collection <Object> objetosAinsertar);
    public String getAgentId();
	
}
