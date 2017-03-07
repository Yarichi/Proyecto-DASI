/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.patronAgenteReactivo.control.acciones;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;

/**
 *
 * @author FGarijo
 */

public abstract class AccionAsincrona extends AccionSincrona implements Runnable {
    Thread tareaActiva;
		
    public AccionAsincrona(){	
                this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
                this.repoInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
               
	}
    
////    public AccionAsincrona(ItfProcesadorObjetivos envioInputs, AgenteCognitivo agente) {
////    
////    	this.itfProcObjetivos = envioHechos;
////    	this.agente = agente;
////        this.trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
////        this.identAgente = agente.getIdentAgente();
////        this.repoInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
////    }
	
    public boolean terminada() {
    	return terminada;
    }
    public void comenzar() {
        tareaActiva = new Thread (this);
        tareaActiva.setDaemon(true);
        tareaActiva.setName(identAccion);
    	tareaActiva.start();
    }
    
    @Override
    public void run() {
		this.ejecutar(params);
                terminada = true;
               //			this.terminar(CausaTerminacionTarea.EXITO);
    }
      
}