package icaro.infraestructura.recursosOrganizacion.comunicacionesOrganizacion.imp;
import icaro.infraestructura.recursosOrganizacion.comunicacionesOrganizacion.ITFUsoComunicacionesOrganizacion;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.comunicacionesOrganizacion.imp.RMI.ComunicacionesOrganizacionRMI;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author FGarijo
 * @version 1.0
 * @created 20-dic-2010 16:18:02
 */
public abstract class ClaseGeneradoraComunicacionesOrganizacion extends ImplRecursoSimple implements ITFUsoComunicacionesOrganizacion {

    private static final long serialVersionUID = 1L;

    private static ClaseGeneradoraComunicacionesOrganizacion instance;

    public static final String IMP_LOCAL = "icaro.infraestructura.recursosOrganizacion.comunicacionesOrganizacion.imp.ComunicacionesOrganizacionLocal";
    public static final String IMP_RMI = "icaro.infraestructura.recursosOrganizacion.comunicacionesOrganizacion.imp.RMI.ComunicacionesOrganizacionRMI";

    public ClaseGeneradoraComunicacionesOrganizacion(String idRecurso) throws RemoteException {
		super(idRecurso);
	}

    public ClaseGeneradoraComunicacionesOrganizacion instance() {
		if (instance == null)
			try {
	//			Class imp = Class.forName(IMP_CORBA);
				instance = new ComunicacionesOrganizacionRMI ();
			} catch (Exception e) {
				e.printStackTrace();
			}

		return instance;
	}
	/**
	 * 
	 * @param mensaje
	 */
	public abstract void enviarMensaje(MensajeSimple mensaje);

	
	/**
	 * Crea  el mensaje añade el contenido y lo envia al receptor
	 * 
	 * @param contenido
	 * @param identAgenteReceptor
	 */
	public abstract void mandarInfoAAgenteId(Object contenido, String identAgenteReceptor);

	/**
	 * 
	 * @param mensaje
	 * @param nombresAgentes
	 */
	public abstract void mandarMensajeaGrupoAgentes(MensajeSimple mensaje, ArrayList<String> nombresAgentes);

	/**
	 * 
	 * @param infoaEnviar
	 * @param grupoDestinatarios
	 */
	public abstract void mandarInfoaGrupoAgentes(Object infoaEnviar, ArrayList<String> grupoDestinatarios);

	public void termina(){

	}

}