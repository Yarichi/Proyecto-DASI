/*
 * 
 */
package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

//import icaro.infraestructura.entidadesBasicas.EventoRecAgte;

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;

/**
 *  Clase que traduce objetos de la clase EventoRecAgte en los inputs que tengamos
 *  definidos en el automata
 *
 */
public class ConversionDeEventosyMsgsSimplesEnInputs {

	/**
	 *  Constructor for the ConversionDeEventosEnInputs object
	 */
	public ConversionDeEventosyMsgsSimplesEnInputs() { }


	/**
	 *@param  ev  Evento a traducir
	 *@return     Input procesable por el automata
	 */
	public static String procesarEventoParaProducirInput(EventoSimple ev)
	{
            InfoContEvtMsgAgteReactivo  evContenido = (InfoContEvtMsgAgteReactivo)ev.getContenido();
            return (evContenido.getInput()).trim();
	}
        public static String procesarMsgSimpleParaProducirInput(MensajeSimple msg)
	{

            Object[] contenidoMsg = (Object[]) msg.getContenido();
            return (contenidoMsg[0].toString().trim());
	}
}
