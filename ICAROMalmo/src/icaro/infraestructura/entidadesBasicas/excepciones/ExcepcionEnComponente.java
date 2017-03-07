/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.excepciones;

/**
 *
 * @author Francisco J Garijo
 */
public class ExcepcionEnComponente extends java.lang.Exception {

protected String identComponente = "Componente:";
protected String identSuperComponente = "";

protected String identParteAfectada = "";
protected String causa = "";
protected String contextoExcepcion;


public ExcepcionEnComponente(String causa, String identParteAfectada,String contextoExcepcion ) {
     super(causa) ;
     this.identParteAfectada=identParteAfectada;
     this.contextoExcepcion=contextoExcepcion;
     this.causa= this.causa+causa;
	}
public ExcepcionEnComponente(String identComponente,String causa, String identParteAfectada,String contextoExcepcion ) {
     super(causa) ;
     this.identComponente = this.identComponente+identComponente;
     this.identParteAfectada=identParteAfectada;
     this.contextoExcepcion=contextoExcepcion;
     this.causa= this.causa+causa;
	}
public ExcepcionEnComponente(String mensaje)  {
           super(mensaje);
           this.causa= this.causa+mensaje;
       }

public String getParteAfectada(){
           return this.identParteAfectada;
       }
public void putParteAfectada(String identParteAfectada){
            this.identParteAfectada = this.identParteAfectada+"Parte Afectada:"+identParteAfectada;
       }
public String getCompDondeEstaContenido(){
           return this.identSuperComponente;
       }
public void putCompDondeEstaContenido(String identSuperComponente){
            this.identSuperComponente = this.identSuperComponente+"contenido en:"+identSuperComponente;
       }
public String getContextoExcepcion(){
           return this.contextoExcepcion;
}
public String getCausa(){
           return this.causa;
}
}
