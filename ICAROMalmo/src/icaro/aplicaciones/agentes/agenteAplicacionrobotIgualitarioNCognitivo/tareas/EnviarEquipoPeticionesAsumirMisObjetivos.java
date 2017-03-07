/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.PeticionAsumirObjetivo;
import icaro.aplicaciones.Rosace.informacion.RobotStatus1;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.Rosace.objetivosComunes.TransferirObjetivos;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoTransimisionObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 *
 * @author FGarijo
 */
public class EnviarEquipoPeticionesAsumirMisObjetivos extends TareaSincrona{
    
   @Override
   public void ejecutar(Object... params) {
	   try {
             InfoEquipo miEquipo = (InfoEquipo)params[0];
             MisObjetivos misObjs = (MisObjetivos)params[1];
             RobotStatus1 miEstatus = (RobotStatus1) params[2];
             VictimsToRescue victimas = (VictimsToRescue)params[3];
             InfoTransimisionObjetivos infoTransmisionObjs;
//             String idVictima=trsnsfObj.getobjectReferenceId();
              
              
             infoTransmisionObjs = new InfoTransimisionObjetivos(identAgente,miEquipo,miEstatus.getcausaCambioEstado());
             // Enviamos las propuestas a los miembros del equipo
             Iterator<Objetivo>  iterObj = misObjs.getMisObjetivosPriorizados().iterator();
            ArrayList<String> idsAgtesMiequipo = miEquipo.getTeamMemberIDs();
            trazas.aceptaNuevaTrazaEjecReglas(identAgente, "  Se ejecuta la tarea : " + identTarea 
                      + "///////////////////////////////////////////////////////////////////////////////// ///////////"+"\n"+
                      "Agentes en mi equipo: " + idsAgtesMiequipo ); 
            while (iterObj.hasNext()){
  	  	  //Hay al menos un objetivo    		
                Objetivo obj = iterObj.next();
//                if(obj.getgoalId().equals(idObjetivoAtrasmitir) ){
                if( obj.getgoalId().equals("AyudarVictima")){
                String obrefId = obj.getobjectReferenceId();
                PeticionAsumirObjetivo petAsumirObj = new PeticionAsumirObjetivo(this.identAgente, obj, miEstatus);
                petAsumirObj.setinfoComplementaria(victimas.getVictimToRescue(obrefId));
                this.getComunicator().informaraGrupoAgentes(petAsumirObj, idsAgtesMiequipo);
                infoTransmisionObjs.addInfoPropuestaEnviada(obrefId);
                trazas.aceptaNuevaTrazaEjecReglas(identAgente, " Se envia una peticion al equipo para salvar a la victima : "+obrefId+ "Se aniade la victima a InfoTransimisionObjetivos . Contenido :  " + infoTransmisionObjs +"\n");
                }
            }
             this.getEnvioHechos().insertarHecho(infoTransmisionObjs);
             // Activo un timeout para la decision. Cuando venza se decidira que hacer en funcion de la situacion del agente
             // Porque se supone que estoy esperando informaciones que no llegan. 
       } catch (Exception e) {
			 e.printStackTrace();
       }
   }

}
