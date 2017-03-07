/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.imp;

import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;


/**
 *
 * @author carf, damiano
 */
public class TareaProxy extends Tarea {
    private Tarea tarea;
    //private TimeoutTarea timeout;
    
    
    public TareaProxy(Tarea tarea) {
        this.tarea = tarea;
      //  timeout = new TimeoutTarea();
    //	timeout.setTarea(this);
    }
    
    @Override
    public void setEnvioHechos(ItfProcesadorObjetivos envioHechos){
        tarea.setEnvioHechos(envioHechos);
    }
    @Override
    public ItfProcesadorObjetivos getEnvioHechos(){
        return tarea.getEnvioHechos();                
    }
    
    @Override
    public void setAgente(AgenteCognitivo agente){
        tarea.setAgente(agente);
    }
    
    @Override
    public AgenteCognitivo getAgente(){
        return tarea.getAgente();
    }

    @Override
    public String getIdentTarea(){
          return tarea.getIdentTarea();
    }
    @Override
    public void ejecutar(Object... params) {
    	tarea.setParams(params);
    //	timeout.start();
    	tarea.start();  	
    }
    
    /*public TimeoutTarea getTimeout() {
    	return timeout;
    }
    */
   /*
    @SuppressWarnings("deprecation")
	public void terminarTarea() {
    	if (!tarea.terminada()) {
    		System.out.println("Terminaci�n de tarea forzosa");
    //		this.timeout.forzarTimeout();
                this.stop();
    	}
    	super.terminar(CausaTerminacionTarea.TERMINACION_AGENTE);
    }
 */
    public Tarea getTarea() {
    	return tarea;
    }
    
    
    
  
    
    
    
    
    
    
    

}
