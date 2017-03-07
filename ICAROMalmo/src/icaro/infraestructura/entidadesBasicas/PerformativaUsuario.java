/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Francisco J Garijo
 */
public class PerformativaUsuario {
 public  String PETICION = "peticion";
 public  String INFORMACION = "informacion";
 public  String ORDEN = "comando";
 public  String [] performativas = {PETICION,INFORMACION,ORDEN};

protected String  nombrePerformativa = null;
protected String  nombreOperacion = null;
protected Object parametros = null;


public PerformativaUsuario (String performativa, Object parametros ){
  this.nombrePerformativa = performativa;
  this.parametros= parametros;
}
public PerformativaUsuario (String performativa,String operacion, Object parametros ){
  this.nombrePerformativa = performativa;
  this.nombreOperacion = operacion;
  this.parametros= parametros;
}
 public Object getParametros() {
        return parametros;
    }
public String getnombrePerformativa() {
        return nombrePerformativa;
    }
public String getnombreOperacion() {
        return nombreOperacion;
    }
public void  nombrePerformativa(String nombre) {
        this.nombrePerformativa = nombre;
    }
void  setnombreOperacion(String nombreOperacion) {
        this.nombreOperacion = nombreOperacion;
    }
void  setParametros(Object parametros) {
        this.parametros = parametros;
    }
public boolean esPerformativa ( String potencialPerformativa){

    int i = 0;
    Boolean encontrado = false;
    while( (i < performativas.length)& !encontrado) {
       if (performativas[i] !=  potencialPerformativa){
             i++;
            }
            { encontrado= true;
                                }

    }
    return encontrado;

}


}
