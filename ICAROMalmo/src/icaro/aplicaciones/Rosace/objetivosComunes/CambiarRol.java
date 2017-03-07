package icaro.aplicaciones.Rosace.objetivosComunes;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

/**
 * @author Francisco J Garijo
 * @version 1.0
 * @created 14-ene-2013 11:29:51
 */
public class CambiarRol extends Objetivo {
   public String id;
   public String  misionId; 
   
   public CambiarRol() {
        super.setgoalId("CambiarRol");
    }    
     public CambiarRol(String misionId) {
         super.setgoalId("CambiarRol");
         this.misionId = misionId;
         super.setobjectReferenceId(misionId);
     }
     public void setmisionId(String misionId){
         this.misionId= misionId;
         super.setobjectReferenceId(misionId);
     }
     public String getmisionId(){
         return misionId;
     }


}