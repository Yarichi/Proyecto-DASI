/*
 *  Copyright 2009 ISSIS
 *
 *
 *  All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.comunicacion;

import java.io.Serializable;

/**
 *  
 *
 *@author
 *@created    11 de septiembre de 2001
 *@modified	  25 /03/2011

 */

public class EventoRecAgte extends EventoSimple {

	private String destino;
        protected String justificacion;
        private InfoContEvtMsgAgteReactivo infoControl;
//    private String origen;
public EventoRecAgte(String input, String origen, String destino) {
//		super( msg,  origen);
//	infoControl = new InfoControlAgteReactivo ();
//        infoControl.setInput(msg);
        this.origen = origen;
        this.contenido = new InfoContEvtMsgAgteReactivo (input);
	this.destino = destino;
 //       this.origen = origen;
        
	}
	
public EventoRecAgte(String input, Object msgParametro, String origen, String destino) {
//		super( msg,  msgParametro, origen );
	this.origen = origen;
        this.contenido = new InfoContEvtMsgAgteReactivo (input,msgParametro );
	this.destino = destino;
	}
	
public EventoRecAgte(String input, Object[] msgParametros, String origen, String destino) {
//		super( msg,  msgParametros, origen );
	this.origen = origen;
        this.contenido = new InfoContEvtMsgAgteReactivo (input,msgParametros );
	this.destino = destino;
	}

public String getJustificacion() {
	return justificacion;
	}
public void setJustificacion(String justificacion) {
	this.justificacion = justificacion;
	}
    public String getDestino(){
	if (destino!=null)

	return destino;
	else
	return "destino no especificado";
	}
/*
    public String getOrigen(){
//		if (origen!=null)

			return origen;
		else
			return "origen no especificado";
	}
	/**
	 * @param origen
	 * @uml.property  name="origen"
	 */
	public void setDestino(String destino){
		this.destino = destino;
	}

}
