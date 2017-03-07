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
public class AceptacionPropuesta implements Serializable{

    public String identAgente; // Identificador del agente que emite la confirmacion de la propuesta
    public String msgAceptacionPropuesta;
    public PropuestaAgente propuestaAceptada;
    public String identObjectRefAcetPropuesta;
    public AceptacionPropuesta(String nombreAgenteEmisor,String mensajeAceptacionPropuesta,PropuestaAgente propAConfirmar ) {
        identAgente= nombreAgenteEmisor;
        propuestaAceptada = propAConfirmar;
        msgAceptacionPropuesta =mensajeAceptacionPropuesta;
        identObjectRefAcetPropuesta= null;
    }
    
    public String   getmsgAceptacionPropuesta(){
        return msgAceptacionPropuesta;
    }
    public void   setmsgAceptacionPropuesta(String msg){
         msgAceptacionPropuesta = msg;
    }
    public String   getidentObjectRefAcetPropuesta(){
        return identObjectRefAcetPropuesta;
    }
    public void   setidentObjectRefAcetPropuesta(String identObjRef){
         identObjectRefAcetPropuesta = identObjRef;
    }

    public PropuestaAgente  getpropuestaAceptada(){
        return propuestaAceptada;
    }
    public void  setpropuestaAceptada(PropuestaAgente propuesta){
         propuestaAceptada = propuesta;
    }
    
    public String   getIdentAgente(){
        return identAgente;
    }
    public void   setIdentAgente(String id){
         identAgente = id;
    }
    
//    @Override
//    public String toString(){ 
//            return "Agente Emisor :"+identAgente+ " MensajePropuesta :+" + msgAceptacionPropuesta+ "  Propuesta: "+propuestaAceptada.toString() +"\n ";
//    }

    @Override
    public String toString(){ 
            return "AceptacionPropuesta:-> Agente Emisor:" + identAgente + 
                                           " MensajeAceptacionPropuesta :+" + msgAceptacionPropuesta + 
                                           " Propuesta: " + propuestaAceptada.toString() +
                                           "identObjectRefAcetPropuesta: " + identObjectRefAcetPropuesta +"\n ";
    }
    
}
