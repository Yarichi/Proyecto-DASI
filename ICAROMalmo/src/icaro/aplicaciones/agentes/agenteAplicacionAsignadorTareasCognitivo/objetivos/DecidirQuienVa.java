/*
 * DarAyudaAcceso.java
 *
 * Created on 22 de mayo de 2007, 11:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;




/**
 *
 * @author Fgarijo
 */
public class DecidirQuienVa extends Objetivo{

	public String id;
        public String objectDecisionId; // identificador del objeto sobre el que se esta decidiendo
    
    /** Creates a new instance of DecidirQuienVa */
    public DecidirQuienVa () {
        super.setgoalId("DecidirQuienVa");
    }
    
    public DecidirQuienVa (String objId) {
        super.setgoalId("DecidirQuienVa");
         this.objectDecisionId = objId;
         super.setobjectReferenceId(objId);
    }
    
    public synchronized String getobjectDecisionId(){
    	return this.objectDecisionId;
    }
    public synchronized void setobjectDecisionId(String decisionObjId){
    	 this.objectDecisionId= decisionObjId;
         super.setobjectReferenceId(decisionObjId);
    }
     @Override
    public boolean equals(Object obj){
    	boolean result = false;
    	if (obj instanceof DecidirQuienVa){
    		DecidirQuienVa dqv = (DecidirQuienVa)obj;
    		boolean bb = this.objectDecisionId.equals(dqv.objectDecisionId);    		    		
    		return bb;
    	}
    	return false;	    	
    }
    
    @Override
    public int hashCode() {
    	int hash = 1;
    	hash = hash * 31 + this.objectDecisionId.hashCode();
    	return hash;
    }
    
}

