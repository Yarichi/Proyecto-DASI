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
public class OrdenAgente implements Serializable {
    // Mensajes validos en las propuestas : Ver vocabulario de la aplicacion
    
    
    public String identEmisor;
    public String mensajeOrden;
    public Object justificacion;
    public String refIteracion;

  public OrdenAgente( ) {
        
 }
    public OrdenAgente(String identCCEmisor) {
        identEmisor= identCCEmisor;
        mensajeOrden =null;
        justificacion = null;

 }
    public OrdenAgente(String identCCEmisor, String msgOrden, Object justificat) {
        identEmisor= identCCEmisor;
        mensajeOrden = msgOrden;
        justificacion = justificat;

 }
  public void   setMensajeOrden(String msgOrden){
      mensajeOrden =msgOrden;
 }
 public String   getMensajeOrden(){
      return mensajeOrden;
 }

 public String   getIdentEmisor(){
      return identEmisor;
 }
  public void   setIdentEmisor(String emisorId){
       identEmisor=emisorId;
 }
public void   setJustificacion(Object contJustificacion){
      justificacion =contJustificacion;
 }

  public Object   getJustificacion(){
      return justificacion;
 }
  public String getrefIteracion(){
         return refIteracion ;
    }
    public void setrefIteracion(String iterId){
        refIteracion =iterId;
    }
  @Override
     public String toString(){
        if ( justificacion == null )
            return "Agente Emisor :"+identEmisor+ " MensajeOrden :+" + mensajeOrden+ "  Justificacion: null "+"\n ";
        else 
            return "Agente Emisor :"+identEmisor+ " MensajeOrden :+" + mensajeOrden+ "  Justificacion: "+justificacion.toString() +"\n ";
    }
}
