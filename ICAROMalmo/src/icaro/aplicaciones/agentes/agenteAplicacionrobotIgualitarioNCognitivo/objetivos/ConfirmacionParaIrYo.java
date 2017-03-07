/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
/**
 *
 * @author Francisco J Garijo
 */
public class ConfirmacionParaIrYo extends Objetivo {
	
      public String id;
      public String objectConfirmationId;
	
      /** Crea una nueva instancia de AyudarNuevoHerido */
      public ConfirmacionParaIrYo() {
          super.setgoalId("ConfirmacionParaIrYo");
          this.id = "ConfirmacionParaIrYo";
      }
       public ConfirmacionParaIrYo (String decisionObjId) {
        super.setgoalId("ConfirmacionParaIrYo");
        this.objectConfirmationId= decisionObjId;
        super.setobjectReferenceId(decisionObjId);
        this.id = "ConfirmacionParaIrYo";
    }
       public String getId(){
    	return this.id;
    }
  
       public String getobjectConfirmationId(){
    	return this.objectConfirmationId;
    }
    public void setobjectConfirmationId(String decisionObjId){
    	 this.objectConfirmationId= decisionObjId;
         super.setobjectReferenceId(decisionObjId);
         
    }
}
