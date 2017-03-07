/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

/**
 *
 * @author Francisco J Garijo
 */
public class Informe {
	
    public Object contenidoInforme;
    public String identEntidadEmisora;
    public String referenciaContexto;
    

    public Informe (String  identEmisor,Object contenido){
        identEntidadEmisora = identEmisor;
        contenidoInforme = contenido;
    }
    public Informe (String  identEmisor,String refContexto,Object contenido){
        identEntidadEmisora = identEmisor;
        contenidoInforme = contenido;
        referenciaContexto = refContexto;
    }
    
    public String getidentEntidadEmisora() {
		return identEntidadEmisora;
	}
    
    public Object getContenidoInforme() {
        return contenidoInforme;
	}
    public String getReferenciaContexto() {
		return referenciaContexto;
	}
    /**
     *  JM: Cadena de texto para la depuración
     */    
    @Override
    public String toString(){
    return "Informe: " +  " ; EntidadEmisora->" + this.getidentEntidadEmisora() + " ; referenciaContexto->" + this.getReferenciaContexto()+
    	    " ; Contenido->" + this.getContenidoInforme() ;
    }
    
}
