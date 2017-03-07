/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.comunicacion;

import java.io.Serializable;

/**
 *
 * @author Francisco J Garijo
 * Esta clase sirve para definir la información que va en el contenido de un mensaje que se envia a un agente reactico
 * Hay que tener en cuenta que un reactivo solo puede recibir inputs y lista de objetos con los valores  para ejecutar las
 * accioones semánticas correspondientes a la transicion
 */
public class InfoContEvtMsgAgteReactivo implements Serializable {


  private  String msg = null;
//    Object valorParametroAccion;
  private  Object[] valoresParametrosAccion = {};

 public InfoContEvtMsgAgteReactivo(String msg){
        this.msg= msg;
        this.valoresParametrosAccion = null;
    }

public  InfoContEvtMsgAgteReactivo(String msg,Object valorParametroAccion){
        this.msg= msg;
//        this.valorParametroAccion = valorParametroAccion;
        if (valorParametroAccion == null)
            this.valoresParametrosAccion = null;
            else {
                valoresParametrosAccion= new Object[1];
                valoresParametrosAccion[0]=valorParametroAccion;
            }
    }

public  InfoContEvtMsgAgteReactivo(String input,Object[] valoresParametrosAccion){
        this.msg= input;
        this.valoresParametrosAccion = valoresParametrosAccion;
    }

    public InfoContEvtMsgAgteReactivo() {

    }

    public String getInput() {
        return this.msg;
    }
    public void  setInput(String input) {
        this.msg = input;
    }
    public String getmsg() {
        return this.msg;
    }
    public void  setmsg(String input) {
        this.msg = input;
    }
    public Object[] getvaloresParametrosAccion() {

        return this.valoresParametrosAccion;
    }
    public void  setvaloresParametrosAccion(Object[] listaValoresParametrosAccion) {
        this.valoresParametrosAccion= listaValoresParametrosAccion;
    }
    
    @Override
     public String toString(){
        if ( valoresParametrosAccion == null )
            return " Mensaje: "+msg+", Parametros: null ";
        else 
            return "                 Mensaje: "+msg+"\n " +toStringParametros();
    }
     public String toStringParametros(){
//        Object parametro = null;
        String msgParametros = null;
        for ( int i = 0; i < valoresParametrosAccion.length ; i++ ) {
            msgParametros = "                 Parametro " + i+1 + " :  ";
//            parametro =  valoresParametrosAccion[i]; 
//                msgParametros =  msgParametros + valoresParametrosAccion[i] + "\n" ;
            msgParametros =  msgParametros + valoresParametrosAccion[i]  ;
                    
                }
        
            return msgParametros;
    }
//     public Object getvalorParametroAccion() {
//        return this.valorParametroAccion;
//    }
//    public void  setvalorParametroAccion(Object valorParametrosAccion) {
//        this.valorParametroAccion= valorParametrosAccion;
//    }
}
