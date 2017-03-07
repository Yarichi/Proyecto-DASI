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
public class OrdenParada extends OrdenAgente implements Serializable {
    // Mensajes validos en las ordenes : Ver vocabulario de la aplicacion

  public OrdenParada( ) {
        super.setMensajeOrden(VocabularioRosace.MsgeOrdenParar);
 }
    public OrdenParada(String identCCEmisor) {
        identEmisor= identCCEmisor;
        mensajeOrden = VocabularioRosace.MsgeOrdenParar;
        justificacion = null;

 }
    public OrdenParada(String identCCEmisor, Object justificat) {
        identEmisor= identCCEmisor;
        mensajeOrden = VocabularioRosace.MsgeOrdenParar;
        justificacion = justificat;

 }
 
//  @Override
//     public String toString(){
//        if ( justificacion == null )
//            return "Agente Emisor :"+identEmisor+ " MensajeOrden :+" + mensajeOrden+ "  Justificacion: null "+"\n ";
//        else 
//            return "Agente Emisor :"+identEmisor+ " MensajeOrden :+" + mensajeOrden+ "  Justificacion: "+justificacion.toString() +"\n ";
//    }
}
