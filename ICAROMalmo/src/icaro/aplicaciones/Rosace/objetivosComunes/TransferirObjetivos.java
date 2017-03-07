/*
 * 
 *
 * 
 *
 * 
 */

package icaro.aplicaciones.Rosace.objetivosComunes;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
/**
 *
 * @author Francisco J Garijo
 */
public class TransferirObjetivos extends Objetivo {
	
	public String id;
	public String  misionId;  // identificador de la victima que se pretende salvar
/** Crea una nueva instancia de AyudarNuevoHerido */
     public TransferirObjetivos() {
        super.setgoalId("TransferirObjetivos");
    }
     
     public TransferirObjetivos(String idMision) {
         super.setgoalId("TransferirObjetivos");
         this.misionId = idMision;
         super.setobjectReferenceId(misionId);
     }
     public void setmisionId(String misionId){
         this.misionId= misionId;
         super.setobjectReferenceId(misionId);
     }
     public String getmisionId(){
         return misionId;
     }
     
//     public String getID(){
//    	 return super.getID();
//    	 return this.id;
//     }
     
//     public void setID(String id){
//    	 this.id = id;
//     }
     
     
}