/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.tareasComunes;

import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaAsincrona;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import org.apache.log4j.Logger;

/**
 *
 * @author FGarijo
 */
public class MoverseAsalvarVictima extends TareaSincrona {
    // private Victim victim;
    private Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private int velocidadCruceroPordefecto = 5;// metros por segundo
    private ItfUsoMovimientoCtrl itfcompMov;
    private Victim victima;

    @Override
    public void ejecutar(Object... params) {
    
            MisObjetivos misObjs = (MisObjetivos) params[0];
            Focus focoActual = (Focus) params[1];
             victima = (Victim) params[2];
            InfoCompMovimiento infoComMov = (InfoCompMovimiento) params[3];
            VictimsToRescue victimasArescatar = (VictimsToRescue) params[4];
            Objetivo objConseguido = focoActual.getFoco();
            if (objConseguido!= null){
                objConseguido.setPriority(-1);
                misObjs.cambiarPrioridad(objConseguido);
            }
            Objetivo nuevoObj = misObjs.getobjetivoMasPrioritario();
            trazas.aceptaNuevaTrazaEjecReglas(identAgente, "Se ejecuta la tarea " + identTarea
                            + " Victima salvada  :  " + victima + 
                            "Objetivo conseguido :  " + objConseguido + "Nuevo objetivo a conseguir  :  " + nuevoObj
                            + " Los objetivos en la cola son  :  " + misObjs.getMisObjetivosPriorizados() + "\n");
             log.debug("Se ejecuta la tarea " + identTarea
                            + " Victima salvada  :  " + victima + 
                            "Objetivo conseguido :  " + objConseguido + "Nuevo objetivo a conseguir  :  " + nuevoObj
                            + " Los objetivos en la cola son  :  " + misObjs.getMisObjetivosPriorizados() + "\n"); 
             Thread t = new Thread(){	
				public void run(){
                                itfcompMov.moverAdestino(victima.getName(), victima.getCoordinateVictim(), velocidadCruceroPordefecto); 
				}
			};
            if (nuevoObj.getPriority()>0 ){
//            if (!objActual.getobjectReferenceId().equals(victima.getName()))// no se hace nada y se indica un error pq el objetivo debe ser el que esta como mas prioritario  
//            {
//                trazas.trazar(this.getIdentAgente(), "Se ejecuta la tarea " + this.getIdentTarea()
//                        + " El ident de la victima debe coindidir con el del objetivo mas prioritario :  " + victima + "\n", InfoTraza.NivelTraza.error);
//            } else {
//                 Objetivo    nuevoObj = misObjs.getobjetivoMasPrioritario();
//                 if (nuevoObj != null) { 
                
                victima = victimasArescatar.getVictimToRescue(nuevoObj.getobjectReferenceId());
                 itfcompMov = (ItfUsoMovimientoCtrl) infoComMov.getitfAccesoComponente();
//                itfcompMov.moverAdestino(nuevoObj.getobjectReferenceId(), victima.getCoordinateVictim(), velocidadCruceroPordefecto); // se pondra la verlocidad por defecto 
                t.run();
                 infoComMov.setitfAccesoComponente(itfcompMov);
                    nuevoObj.setSolving();
//                    focoActual.setFoco(nuevoObj);
                    this.getEnvioHechos().actualizarHechoWithoutFireRules(infoComMov);
                    this.getEnvioHechos().actualizarHechoWithoutFireRules(nuevoObj);
//                    trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se elimina el objetivo  " + objActual
//                            + " Los objetivos en la cola son  :  " + misObjs.getMisObjetivosPriorizados() + "\n");
                    trazas.aceptaNuevaTrazaEjecReglas(identAgente, "Se ejecuta la tarea " + identTarea
                            + " Se inicia el movimiento  para salvar a la victima :  " + victima + "\n"+
                            "Objetivo conseguido :  " + objConseguido + "Nuevo objetivo a focalizar  :  " + nuevoObj
                            + " Los objetivos en la cola son  :  " + misObjs.getMisObjetivosPriorizados() + "\n");
                   log.debug("Se ejecuta la tarea " + identTarea
                            + " Se inicia el movimiento  para salvar a la victima :  " + victima + "\n"+
                            "Objetivo conseguido :  " + objConseguido + "Nuevo objetivo a focalizar  :  " + nuevoObj
                            + " Los objetivos en la cola son  :  " + misObjs.getMisObjetivosPriorizados() + "\n"); 
                } // no hay objetivos para salvar victimas el foco se pone a null
            else{
                
                trazas.aceptaNuevaTrazaEjecReglas(identAgente, "Se ejecuta la tarea " + identTarea
                            + " Nuevo objetivo a focalizar  :  " + nuevoObj + " Prioridad del objetivo : " + nuevoObj.getPriority()
                            + " Sin orden de movimiento por no tener victimas en la cola."
                            + " Los objetivos en la cola son  :  " + misObjs.getMisObjetivosPriorizados() + "\n");
                 log.debug("\n" + identAgente + "Se ejecuta la tarea " + getIdentTarea() + " Sin orden de movimiento por no tener victimas en la cola."
                         + "  Foco null. Victima rescatada :  " + victima + "\n\n");
                 nuevoObj = null;
            }    
                focoActual.setFoco(nuevoObj);
                this.getEnvioHechos().actualizarHechoWithoutFireRules(misObjs);
                this.getEnvioHechos().actualizarHecho(focoActual);
        }
}
