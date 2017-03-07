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
public class DecisionAgente implements Serializable{
	
    static public  final String Decision_De_Asumir_Objetivo = "YoVoy";
    static public  final String Decision_De_Rechazar_Objetivo = "YoNoVoy";
    static public  final String Decision_De_Continuar_Objetivo = "SigoConObjetivoOriginal";

    public String identAgente;
    public String decisionMsg;
  //  public String decisionInfo; //creo que actualmente este campo no se utiliza
 //   public String IdDecisionInfo;  // identificador que permite identificar el objeto sobre el que se realiza la decision P ej la victima
    public String identObjectRefDecision; // identificador de un objeto o contexto al que se refiere la propuesta
    public Object justificacion;
    public DecisionAgente() {
        
    }

    public DecisionAgente(String identAgenteEmisor) {
        identAgente= identAgenteEmisor;
        decisionMsg =null;
        identObjectRefDecision = null;
    }
    
    public DecisionAgente(String identAgenteEmisor, String msgDecision) {
        identAgente= identAgenteEmisor;
        this.decisionMsg =msgDecision;
        identObjectRefDecision = null;
    }
    
    public void   setDecisionMsg(String msgDecision){
        this.decisionMsg =msgDecision;
    }
    
    public String   getDecisionMsg(){
        return decisionMsg;
    }

    public void   setIdentAgente(String identAgente){
        this.identAgente = identAgente ;
    }
    
    public String   getIdentAgente(){
        return identAgente;
    }
    
    public void   setidentObjectRefDecision(String idobjDecisionEntity){
        identObjectRefDecision =idobjDecisionEntity;
    }

    public String   getidentObjectRefDecision(){
        return identObjectRefDecision;
    }
     public void   setJustificacion(Object contJustificacion){
        justificacion =contJustificacion;
    }

    public Object   getJustificacion(){
        return justificacion;
    }

//    @Override
//    public String toString(){
//        if ( decisionInfo == null )
//            return "Agente Emisor :" + identAgente + " MensajePropuesta :+" + decisionMsg + "  Justificacion: null " + "\n ";
//        else 
//            return "Agente Emisor :" + identAgente + " MensajePropuesta :+" + decisionMsg + "  Justificacion: " + decisionInfo.toString() +"\n ";
//    }

    @Override
    public String toString(){
        if ( identObjectRefDecision == null )
            return "DecisionAgente:->  Agente Emisor :" + identAgente + " identObjectRefDecision :+" + identObjectRefDecision +" MensajeDecision :+" + decisionMsg + "  Justificacion: null " + "\n ";
        else 
            return "DecisionAgente:->  Agente Emisor :" + identAgente + " identObjectRefDecision :+" + identObjectRefDecision + " MensajeDecision :+" + decisionMsg + "  Justificacion: " + justificacion.toString() +"\n ";
    }
    
}
