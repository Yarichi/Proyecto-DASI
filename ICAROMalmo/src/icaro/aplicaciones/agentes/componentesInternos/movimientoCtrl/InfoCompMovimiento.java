/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl;

import icaro.aplicaciones.agentes.componentesInternos.InfoCompInterno;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;

/**
 *
 * @author FGarijo
 */
public class InfoCompMovimiento extends InfoCompInterno {
   
    public  ItfUsoMovimientoCtrl itfAccesoComponente;
    public  ItfProcesadorObjetivos itfAccesoControlEntidad;
    
  public  InfoCompMovimiento ( String componenteId){
      super (componenteId);
  }
     public void setitfAccesoComponente( ItfUsoMovimientoCtrl itfAccComponente){
         itfAccesoComponente = itfAccComponente;
     }   
    @Override
     public Object getitfAccesoComponente(){
         return itfAccesoComponente ;
     } 
      public void setitfAccesoControlEntidad( ItfProcesadorObjetivos itfAccEntidad){
         itfAccesoControlEntidad = itfAccEntidad;
     }   
    @Override
     public Object getitfAccesoControlEntidad(){
         return itfAccesoControlEntidad ;
     } 
}
