/*
 * DarAcceso.java
 *
 * Creado 23 de abril de 2007, 12:49
 *
 * Telefonica I+D Copyright 2006-2007
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.objetivos;

import fr.laas.openrobots.jmorse.components.destination.Coordinate;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import java.util.ArrayList;
/**
 *
 * @author Francisco J Garijo
 */
public class AyudarVariasVictimas extends Objetivo {
	
	public String id;
        public ArrayList<Coordinate> victimCoordinate;
	
/** Crea una nueva instancia de AyudarNuevoHerido */
     public AyudarVariasVictimas() {
        super.setgoalId("AyudarVariasVictimas");
    }
     
     public AyudarVariasVictimas(String id) {
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