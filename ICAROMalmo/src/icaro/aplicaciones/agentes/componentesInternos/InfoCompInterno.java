/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.componentesInternos;

/**
 *
 * @author FGarijo
 */
public class InfoCompInterno {
    public String tipoComponente;
    public String idComponente;
    public String estadoComponente;
    public Object itfAccesoComponente;
    public Object itfAccesoControlEntidad;
    
  public  InfoCompInterno ( String componenteId){
      idComponente = componenteId;
  }
     public void setitfAccesoComponente( Object itfAccComponente){
         itfAccesoComponente = itfAccComponente;
     }   
     public Object getitfAccesoComponente(){
         return itfAccesoComponente ;
     } 
      public void setitfAccesoControlEntidad( Object itfAccEntidad){
         itfAccesoControlEntidad = itfAccEntidad;
     }   
     public Object getitfAccesoControlEntidad(){
         return itfAccesoControlEntidad ;
     } 
//     public void setestadoComponente( String estadoComp){
//         estadoComponente = estadoComp;
//     }   
//     public String getestadoComponente(){
//         return estadoComponente ;
//     } 
}
