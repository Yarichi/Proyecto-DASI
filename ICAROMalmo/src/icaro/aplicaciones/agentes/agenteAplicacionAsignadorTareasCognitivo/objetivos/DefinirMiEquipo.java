/*
 * DarAcceso.java
 *
 * Creado 23 de abril de 2007, 12:49
 *
 * Telefonica I+D Copyright 2006-2007
 */

package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
/**
 *
 * @author Francisco J Garijo
 */
public class DefinirMiEquipo extends Objetivo {
	
	public String id;
	public String  misionId;  // identificador de la mision del equipo 
/** Crea una nueva instancia del objetivo */
     public DefinirMiEquipo() {
        super.setgoalId("DefinirMiEquipo");
    }    
     public DefinirMiEquipo(String misionId) {
         super.setgoalId("DefinirMiEquipo");
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
     
//     public String getID(){
//    	 return super.getID();
//    	 return this.id;
//     }
     
//     public void setID(String id){
//    	 this.id = id;
//     }
     
     
}