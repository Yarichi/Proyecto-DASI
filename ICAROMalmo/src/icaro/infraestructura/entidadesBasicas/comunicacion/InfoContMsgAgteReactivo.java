/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.comunicacion;

/**
 *
 * @author Francisco J Garijo
 * Esta clase sirve para definir la información que va en el contenido de un mensaje que se envia a un agente reactico
 * Hay que tener en cuenta que un reactivo solo puede recibir inputs y lista de objetos con los valores  para ejecutar las
 * accioones semánticas correspondientes a la transicion
 */
public class InfoContMsgAgteReactivo {


    String input = null;
    Object valorParametroAccion;
    Object[] valoresParametrosAccion;

 public  InfoContMsgAgteReactivo(String input){
        this.input= input;
        this.valoresParametrosAccion = null;
    }

 public  InfoContMsgAgteReactivo(String input,Object valorParametroAccion){
        this.input= input;
        this.valorParametroAccion = valorParametroAccion;
    }

 public  InfoContMsgAgteReactivo(String input,Object[] valoresParametrosAccion){
        this.input= input;
        this.valoresParametrosAccion = valoresParametrosAccion;
    }

    public String getInput() {
        return this.input;
    }
    public void  setInput(String input) {
        this.input = input;
    }
    public Object[] getvaloresParametrosAccion() {
        return this.valoresParametrosAccion;
    }
    public void  setvaloresParametrosAccion(Object[] listaValoresParametrosAccion) {
        this.valoresParametrosAccion= listaValoresParametrosAccion;
    }
     public Object getvalorParametroAccion() {
        return this.valorParametroAccion;
    }
    public void  setvalorParametroAccion(Object valorParametrosAccion) {
        this.valorParametroAccion= valorParametrosAccion;
    }
}
