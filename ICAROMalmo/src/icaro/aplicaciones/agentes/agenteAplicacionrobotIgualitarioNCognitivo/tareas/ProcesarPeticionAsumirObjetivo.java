/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.PropuestaAgente;
import icaro.aplicaciones.Rosace.informacion.PeticionAsumirObjetivo;
import icaro.aplicaciones.Rosace.informacion.RobotStatus1;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.Rosace.objetivosComunes.AyudarVictima;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.objetivos.DecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author FGarijo
 */
public class ProcesarPeticionAsumirObjetivo extends TareaSincrona {

    private InfoParaDecidirQuienVa infoDecision;
	@Override
	public void ejecutar(Object... params) {
            try {
                  PeticionAsumirObjetivo peticionRecibida = (PeticionAsumirObjetivo)params[0];
                  MisObjetivos misObjs= (MisObjetivos)params[1];
                  InfoEquipo miEquipo =  (InfoEquipo)params[2];
                  Focus foco = (Focus)params[3];
//                  VictimsToRescue victims = (VictimsToRescue)params[4];
            // Se Verifica que el robot que hace la propuesta esta bloqueado
                  RobotStatus1 estatusRobot = (RobotStatus1)peticionRecibida.getJustificacion();
                  if(estatusRobot.getBloqueado()){
            // Actualizo el equipo
                      miEquipo.setTeamMemberStatus( estatusRobot);
            // Se asume que el objetivo es "SalvarVictima" y se trabaja con la referencia a  la victima     
            // Se asume la peticion generando un objetivo para ver quien se hace cargo de salvar a la victima
                 Victim victimaRescate= (Victim) peticionRecibida.getinfoComplementaria();
               
                 if(victimaRescate!=null){
                 String idvictima = victimaRescate.getName();
                 AyudarVictima nuevoObjAyudarVictima= new AyudarVictima(idvictima);
                 nuevoObjAyudarVictima.setPriority(victimaRescate.getPriority());
          //      if((objetivoEjecutantedeTarea == null)) newObjetivo.setSolving(); // se comienza el proceso para intentar conseguirlo                                        
           //       Se genera un objetivo para decidir quien se hace cargo de la ayuda y lo ponemos a solving
                 DecidirQuienVa newDecision = new DecidirQuienVa(idvictima);
                 newDecision.setSolving();
//                 foco.setFoco(newDecision);
                 this.getEnvioHechos().actualizarHechoWithoutFireRules(miEquipo);
                 this.getEnvioHechos().actualizarHechoWithoutFireRules(newDecision);
                 this.getEnvioHechos().actualizarHechoWithoutFireRules(nuevoObjAyudarVictima);
                 this.getEnvioHechos().actualizarHechoWithoutFireRules(foco);
                 this.trazas.aceptaNuevaTrazaEjecReglas(this.getIdentAgente(), 
                        "Se ejecuta la tarea : " + this.getIdentTarea() + " Peticion recibida del robot :  " + peticionRecibida.identAgente +
                        "  idVictima implicada : "+idvictima +" Estado del robot proponente bloqueado? : "+estatusRobot.getBloqueado()+"\n" );  
                  }else
                     this.trazas.aceptaNuevaTrazaEjecReglas(this.getIdentAgente(), 
                        "Se ejecuta la tarea : " + this.getIdentTarea() + " Preticion recibida del robot :  " + peticionRecibida.identAgente +
                        " la victima implicada es null. Estado del robot proponente bloqueado? : "+estatusRobot.getBloqueado()+"\n" );  
            }
                  this.getEnvioHechos().eliminarHecho(peticionRecibida);
            }
            catch(Exception e) {
                  e.printStackTrace();
            }
	 
}
}
