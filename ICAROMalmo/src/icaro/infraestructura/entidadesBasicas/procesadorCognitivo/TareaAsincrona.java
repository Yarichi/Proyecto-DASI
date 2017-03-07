/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 *
 * @author FGarijo
 */

public abstract class TareaAsincrona extends TareaSincrona implements Runnable {
//    Thread tareaActiva;
   
		
    public TareaAsincrona(){	
                this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
                this.repoInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
               
	}
    
    public TareaAsincrona(ItfProcesadorObjetivos envioHechos, AgenteCognitivo agente) {
    
    	this.itfProcObjetivos = envioHechos;
    	this.agente = agente;
        this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        this.identAgente = agente.getIdentAgente();
        this.repoInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
    }
	
    public boolean terminada() {
    	return terminada;
    }
    public void comenzar() {
//        tareaActiva = new Thread (this);
//        tareaActiva.setDaemon(true);
//        tareaActiva.setName(identTarea);
//    	tareaActiva.start();
    }
    
    @Override
    public void run() {
		this.ejecutar(params);
                terminada = true;
               //			this.terminar(CausaTerminacionTarea.EXITO);
    }
      
}