/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.EvaluacionAgente;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import java.util.ArrayList;

/**
 *
 * @author FGarijo
 */
public class DecidirConFaltanEvaluaciones extends TareaSincrona{
    
private final int evalParaExcluir=100000000;
@Override
public void ejecutar(Object... params) {
  Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];    
    InfoParaDecidirQuienVa  infoDecision = (InfoParaDecidirQuienVa)params[1]; 
  try {
     // si no he recibido ninguna evaluacion asumo el objetivo
//      ArrayList evaluacionesRecibidas= infoDecision.getEvaluacionesRecibidas();
    if( infoDecision.getnumeroEvaluacionesRecibidas()==0){ //asumo el objetivo porque los demas no contestan
        infoDecision.tengoLaMejorEvaluacion=true;
        infoDecision.tengoAcuerdoDeTodos=true;  
    }else{// miro las respuestas que faltan
        ArrayList<String> identAgtesEquipo= infoDecision.getAgentesEquipo();
        ArrayList evaluacionesRecibidas= infoDecision.getEvaluacionesRecibidas();
      for(int i = 0; i< evaluacionesRecibidas.size(); i++){
           if((Integer)evaluacionesRecibidas.get(i)==0 ){//si aun no tenemos la evaluacion generamos un valor alto para excluirlo
               String identAgteSinEval= identAgtesEquipo.get(i);
               EvaluacionAgente nuevaEvaluacion= new EvaluacionAgente(identAgteSinEval,evalParaExcluir);
               infoDecision.addNuevaEvaluacion(nuevaEvaluacion);
              this.trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se genera evaluacion de exclusion para el agente : "+ identAgteSinEval);
           }
        }
      }
        //  anyado el infoDecision
        this.getEnvioHechos().actualizarHecho(infoDecision);
        this.trazas.aceptaNuevaTrazaEjecReglas(this.identAgente, "Se modifica InfoDecision con los valores : "+ infoDecision);
  } catch (Exception e) {
       }
   }
}
