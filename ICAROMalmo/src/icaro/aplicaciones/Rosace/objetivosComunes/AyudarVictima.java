/*
 * DarAcceso.java
 *
 * Creado 23 de abril de 2007, 12:49
 *
 * Telefonica I+D Copyright 2006-2007
 */

package icaro.aplicaciones.Rosace.objetivosComunes;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
/**
 *
 * @author Francisco J Garijo
 */
public class AyudarVictima extends Objetivo {
	
	public String id;
	public String  victimId;  // identificador de la victima que se pretende salvar
/** Crea una nueva instancia de AyudarNuevoHerido */
     public AyudarVictima() {
        super.setgoalId("AyudarVictima");
    }
     
     public AyudarVictima(String victmId) {
         super.setgoalId("AyudarVictima");
         this.victimId = victmId;
         super.setobjectReferenceId(victmId);
     }
     public void setvictimId(String victmId){
         this.victimId= victmId;
         super.setobjectReferenceId(victmId);
     }
     public String getvictimId(){
         return victimId;
     }
     
//     public String getID(){
//    	 return super.getID();
//    	 return this.id;
//     }
     
//     public void setID(String id){
//    	 this.id = id;
//     }
     
     
}