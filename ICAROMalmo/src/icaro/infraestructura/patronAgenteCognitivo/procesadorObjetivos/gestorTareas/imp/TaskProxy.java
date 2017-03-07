/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.imp;

import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;

/**
 *
 * @author carf
 */
public class TaskProxy extends Tarea {
	
    private Tarea tarea;
    
    public TaskProxy(Tarea tarea){
        this.tarea = tarea;
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


    public String getIdentTartea(){
        return tarea.getIdentTarea();
    }
    @Override
    public void ejecutar(Object... params) {	
		tarea.setParams(params);
		tarea.start();
        //tarea.ejecutar(params);
    }

}
