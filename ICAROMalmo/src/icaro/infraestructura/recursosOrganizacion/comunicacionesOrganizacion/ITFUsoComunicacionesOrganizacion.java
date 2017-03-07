package icaro.infraestructura.recursosOrganizacion.comunicacionesOrganizacion;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import java.util.ArrayList;

/**
 * @version 1.0
 * @created 20-dic-2010 16:20:34
 */
public interface ITFUsoComunicacionesOrganizacion extends ItfUsoRecursoSimple {

	/**
	 * 
	 * @param mensaje
	 */
	public void enviarMensaje(MensajeSimple mensaje);

	/**
	 * Crea  el mensaje, añade el contenido y lo envia al receptor
	 * 
	 * @param contenido
	 * @param identAgenteReceptor
	 */
	public void mandarInfoAAgenteId(Object contenido, String identAgenteReceptor);

	/**
	 * 
	 * @param mensaje
	 * @param nombresAgentes
	 */

        public void mandarMensajeaGrupoAgentes(MensajeSimple mensaje, ArrayList<String> nombresAgentes);

	/**
	 * 
	 * @param infoaEnviar
	 * @param grupoDestinatarios
	 */
	public void mandarInfoaGrupoAgentes(Object infoaEnviar, ArrayList<String> grupoDestinatarios);

}