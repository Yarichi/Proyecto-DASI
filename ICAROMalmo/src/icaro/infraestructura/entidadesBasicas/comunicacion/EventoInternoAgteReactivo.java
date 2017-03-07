/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.comunicacion;

/**
 *
 * @author FGarijo
 */
public class EventoInternoAgteReactivo {
    public String emisor ;
    public  Object input = null;
//    Object valorParametroAccion;
  public  Object[] valoresParametrosAccion = {};
  
  public EventoInternoAgteReactivo (String identEmisor,Object input, Object[] paramAccion){
      this.emisor = identEmisor;
        this.input = input;
        valoresParametrosAccion = paramAccion;
//	this.destino = destino;
  }
}
