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
public class OrdenInicioCasoSimulacion extends OrdenAgente implements Serializable {
    // Mensajes validos en las ordenes : Ver vocabulario de la aplicacion
   private RobotStatus1 estusRobot;

  public OrdenInicioCasoSimulacion( ) {
        super.setMensajeOrden(VocabularioRosace.MsgOrdenCCinicioCasoSimulacion);
 }
public OrdenInicioCasoSimulacion(String identCCEmisor) {
    identEmisor= identCCEmisor;
    mensajeOrden = VocabularioRosace.MsgOrdenCCinicioCasoSimulacion;
    justificacion = null;

 }
public OrdenInicioCasoSimulacion(String identCCEmisor, RobotStatus1 estatus) {
    identEmisor= identCCEmisor;
    mensajeOrden = VocabularioRosace.MsgOrdenCCinicioCasoSimulacion;
    justificacion = null;
}
        
public RobotStatus1 getRobotStatus(){
    return estusRobot;
}
public void setRobotStatus(RobotStatus1 estatusRobt){
     estusRobot=estatusRobt ;
}
 
//  @Override
//     public String toString(){
//        if ( justificacion == null )
//            return "Agente Emisor :"+identEmisor+ " MensajeOrden :+" + mensajeOrden+ "  Justificacion: null "+"\n ";
//        else 
//            return "Agente Emisor :"+identEmisor+ " MensajeOrden :+" + mensajeOrden+ "  Justificacion: "+justificacion.toString() +"\n ";
//    }
}
