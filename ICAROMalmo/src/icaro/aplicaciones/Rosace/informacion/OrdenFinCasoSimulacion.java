/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import java.io.Serializable;

/**
 *
 * @author FGarijo
 */
public class OrdenFinCasoSimulacion extends OrdenAgente implements Serializable {
    // Mensajes validos en las ordenes : Ver vocabulario de la aplicacion

  public OrdenFinCasoSimulacion( ) {
        super.setMensajeOrden(VocabularioRosace.MsgOrdenCCFinalizarCasoSimulacion);
 }
    public OrdenFinCasoSimulacion(String identCCEmisor) {
        super.setIdentEmisor(identCCEmisor);
        super.setMensajeOrden(VocabularioRosace.MsgOrdenCCFinalizarCasoSimulacion);
        justificacion = null;

 }
    public OrdenFinCasoSimulacion(String identCCEmisor, Object justificat) {
        super.setIdentEmisor(identCCEmisor);
        super.setMensajeOrden(VocabularioRosace.MsgOrdenCCFinalizarCasoSimulacion);
        super.setJustificacion (justificat);

 }
 
//  @Override
//     public String toString(){
//        if ( justificacion == null )
//            return "Agente Emisor :"+identEmisor+ " MensajeOrden :+" + mensajeOrden+ "  Justificacion: null "+"\n ";
//        else 
//            return "Agente Emisor :"+identEmisor+ " MensajeOrden :+" + mensajeOrden+ "  Justificacion: "+justificacion.toString() +"\n ";
//    }
}
