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
public class OrdenCentroControl implements Serializable {
    // Mensajes validos en las propuestas : Ver vocabulario de la aplicacion
    
    
    public String identCC;
    public String mensajeOrden;
    public Object justificacion;

  public OrdenCentroControl( ) {
        
 }
    public OrdenCentroControl(String identCCEmisor) {
        identCC= identCCEmisor;
        mensajeOrden =null;
        justificacion = null;

 }
    public OrdenCentroControl(String identCCEmisor, String msgOrden, Object justificat) {
        identCC= identCCEmisor;
        mensajeOrden = msgOrden;
        justificacion = justificat;

 }
  public void   setMensajeOrden(String msgOrden){
      mensajeOrden =msgOrden;
 }
 public String   getMensajeOrden(){
      return mensajeOrden;
 }
 public void   setIdentCC(String ccId){
      identCC =ccId;
 }

 public String   getIdentCC(){
      return identCC;
 }
public void   setJustificacion(Object contJustificacion){
      justificacion =contJustificacion;
 }

  public Object   getJustificacion(){
      return justificacion;
 }
  @Override
     public String toString(){
        if ( justificacion == null )
            return "Agente Emisor :"+identCC+ " MensajeOrden :+" + mensajeOrden+ "  Justificacion: null "+"\n ";
        else 
            return "Agente Emisor :"+identCC+ " MensajeOrden :+" + mensajeOrden+ "  Justificacion: "+justificacion.toString() +"\n ";
    }
}
