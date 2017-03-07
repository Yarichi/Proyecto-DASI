/*
 * DarAcceso.java
 *
 * Creado 23 de abril de 2007, 12:49
 *
 * Telefonica I+D Copyright 2006-2007
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
/**
 *
 * @author Francisco J Garijo
 */
public class AyudarNuevoHerido extends Objetivo {
	
	public String id;
	
/** Crea una nueva instancia de AyudarNuevoHerido */
     public AyudarNuevoHerido() {
        super.setgoalId("AyudarNuevoHerido");
    }
     
     public AyudarNuevoHerido(String id) {
         super.setgoalId(id);
         this.id = super.getgoalId();
     }
     
//     public String getID(){
//    	 return super.getID();
//    	 return this.id;
//     }
     
//     public void setID(String id){
//    	 this.id = id;
//     }
     
     
}