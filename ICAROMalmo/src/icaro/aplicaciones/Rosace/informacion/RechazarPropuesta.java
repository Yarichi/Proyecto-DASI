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
public class RechazarPropuesta implements Serializable{

    public String identAgente; // Identificador del agente que emite la confirmacion de la propuesta
    public String msgRechazoPropuesta;
    public PropuestaAgente propuestaRechazada;
    public String identObjectRefPropuesta;
    public Object justificacion;
    public RechazarPropuesta(String nombreAgenteEmisor,String mensajeAceptacionRechazo,PropuestaAgente propRecibida ) {
        identAgente= nombreAgenteEmisor;
        propuestaRechazada = propRecibida;
        msgRechazoPropuesta =mensajeAceptacionRechazo;
        identObjectRefPropuesta= null;
        justificacion = null;
    }
    
    public String   getmsgRechazoPropuesta(){
        return msgRechazoPropuesta;
    }
    public void   setmsgRechazoPropuesta(String msg){
         msgRechazoPropuesta = msg;
    }
    public String   getidentObjectRefPropuesta(){
        return identObjectRefPropuesta;
    }
    public void   setidentObjectRefPropuesta(String identObjRef){
         identObjectRefPropuesta = identObjRef;
    }

    public PropuestaAgente  getpropuestaAceptada(){
        return propuestaRechazada;
    }
    public void  setpropuestaAceptada(PropuestaAgente propuesta){
         propuestaRechazada = propuesta;
    }
    
    public String   getIdentAgente(){
        return identAgente;
    }
    public void   setIdentAgente(String id){
         identAgente = id;
    }
    public Object   getJustificacion(){
        return justificacion;
    }
    public void   setJustificacion(Object justifica){
         justificacion = justifica;
    }
    
//    @Override
//    public String toString(){ 
//            return "Agente Emisor :"+identAgente+ " MensajePropuesta :+" + msgAceptacionPropuesta+ "  Propuesta: "+propuestaAceptada.toString() +"\n ";
//    }

    @Override
    public String toString(){ 
            return "AceptacionPropuesta:-> Agente Emisor:" + identAgente +
                                           "identObjectRefAcetPropuesta :" +identObjectRefPropuesta +
                                           " MensajeAceptacionPropuesta :+" + msgRechazoPropuesta + 
                                           " Propuesta: " + propuestaRechazada.toString() +
                                            "Justificacion: " + justificacion.toString() + "\n ";
    }
    
}
