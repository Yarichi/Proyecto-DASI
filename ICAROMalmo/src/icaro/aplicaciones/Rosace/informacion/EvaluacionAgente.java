/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.Rosace.informacion;

import java.io.Serializable;

/**
 *
 * @author Francisco J Garijo
 */
public class EvaluacionAgente implements Serializable{
    public String identAgente;
    public Integer valorEvaluacion;
    public String objectEvaluationId; // identificador de la victima sobre la que se realiza la evaluacion

    public EvaluacionAgente(String nombreAgenteEmisor, Integer evaluacion) {
        identAgente= nombreAgenteEmisor;
    valorEvaluacion =evaluacion;

 }
 public Integer   getEvaluacion(){
      return valorEvaluacion;
 }

 public String   getIdentAgente(){
      return identAgente;
 }
 public String   getObjectEvaluationId(){
      return objectEvaluationId;
 }

 public void   setObjectEvaluationId(String objEvalId){
      objectEvaluationId = objEvalId ;
 }
 @Override
     public String toString(){
        
            return "Agente Emisor :"+identAgente+ "+  Valor Evaluacion :+" + valorEvaluacion + " Ident Obejeto Evaluacion : +" + objectEvaluationId +"\n ";
        
    }
 }