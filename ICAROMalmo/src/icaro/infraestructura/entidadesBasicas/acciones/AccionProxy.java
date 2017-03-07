/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.acciones;

import icaro.infraestructura.entidadesBasicas.acciones.Accion;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItfUsoAutomataEFE;



/**
 *
 * @author FGarijo
 */
public class AccionProxy extends Accion{
    
    private Accion accion;
    //private TimeoutAccion timeout;
    
    
    public AccionProxy(Accion accion) {
        this.accion = accion;
      //  timeout = new TimeoutAccion();
    //	timeout.setAccion(this);
    }
    
    @Override
    public void setItfAutomata(ItfUsoAutomataEFE itfAutomata){
        accion.setItfAutomata(itfAutomata);
    }
//    @Override
//    public ItfProcesadorObjetivos getEnvioHechos(){
//        return accion.getEnvioHechos();                
//    }
    
//    @Override
//    public void setAgente(AgenteCognitivo agente){
//        accion.setAgente(agente);
//    }
//    
//    @Override
//    public AgenteCognitivo getAgente(){
//        return accion.getAgente();
//    }

    @Override
    public String getIdentAccion(){
          return accion.getIdentAccion();
    }
    @Override
    public void ejecutar(Object... params) {
    	accion.setParams(params);
    //	timeout.start();
    	accion.start();  	
    }
    
    /*public TimeoutAccion getTimeout() {
    	return timeout;
    }
    */
   /*
    @SuppressWarnings("deprecation")
	public void terminarAccion() {
    	if (!accion.terminada()) {
    		System.out.println("Terminaci�n de accion forzosa");
    //		this.timeout.forzarTimeout();
                this.stop();
    	}
    	super.terminar(CausaTerminacionAccion.TERMINACION_AGENTE);
    }
 */
    public Accion getAccion() {
    	return accion;
    }
    

}

