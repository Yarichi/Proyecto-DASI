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
public class ConsensuarQuienEsElMejor extends Objetivo{
	
	public String id;
	
    public ConsensuarQuienEsElMejor () {
        super.setgoalId("ConsensuarQuienEsElMejor");
    }
    
    public ConsensuarQuienEsElMejor (String id) {
        super.setgoalId(id);
        this.id = super.getgoalId();
    }
    
}
