package icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp;

/**
 * Implementacin de un repositorio de interfaces mediante una tabla hash
 * @author Jorge M. Gonzlez Martn
 * @version 1.0
 */





import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.rmi.RemoteException;
import java.util.ArrayList;



public abstract class ClaseGeneradoraRepositorioInterfaces extends ImplRecursoSimple implements
		ItfUsoRepositorioInterfaces {

	public ClaseGeneradoraRepositorioInterfaces(String idRecurso) throws RemoteException {
		super(idRecurso);
	}

	private static final long serialVersionUID = 1L;

	private static ClaseGeneradoraRepositorioInterfaces instance;
	
//	public static final String IMP_LOCAL = "icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.RepositorioInterfacesImpLocal";
//	public static final String IMP_CORBA = "icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.impCORBA.RepositorioInterfacesCORBA";
	
	public static ClaseGeneradoraRepositorioInterfaces instance() {
		if (instance == null)
			try {
	//			Class imp = Class.forName(IMP_CORBA);
			//	instance = new RepositorioInterfacesImpLocal ();
                            instance = new RepositorioInterfacesImpGen ();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		return instance;
	}
	
	public static ClaseGeneradoraRepositorioInterfaces instance(String implementacion) {
		if (instance == null)
			try {
				Class imp = Class.forName(implementacion);
				instance = (ClaseGeneradoraRepositorioInterfaces)imp.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		return instance;
	}
	
	/**
	 * Almacena una interfaz en el repositorio En caso de existir el nombre
	 * previamente, se actualiza la referencia
	 * 
	 * @param nombre
	 * @param interfaz
	 */
    @Override
	public abstract void registrarInterfaz(String nombre, Object interfaz);

	/**
	 * Recupera una interfaz del repositorio
	 * 
	 * @param nombre
	 *            Nombre de la interfaz a recuperar
	 * @return Interfaz asociada a ese nombre o null si no se ha encontrado ese
	 *         nombre
	 * @throws Exception 
	 */
    @Override
	public abstract Object obtenerInterfaz(String nombre) throws Exception;
    @Override
        public abstract Object obtenerInterfazUso(String nombre) throws Exception;
    @Override
        public abstract Object obtenerInterfazGestion(String nombre) throws Exception;

	/**
	 * Cancela el registro de una interfaz en el repositorio
	 * 
	 * @param nombre
	 *            Nombre de la interfaz a eliminar del repositorio
	 * @throws Exception
	 */

    @Override
	public abstract void eliminarRegistroInterfaz(String nombre)throws Exception;

	/**
	 * Devuelve una lista con los nombres de todos los interfaces registrados
	 * 
	 * @return
	 * @throws Exception
	 */
    @Override
	public abstract String listarNombresInterfacesRegistradas()throws Exception;
    @Override
        public abstract Boolean estaRegistradoEsteNombre(String nombreEntidad)throws Exception;
    @Override
        public abstract Boolean estaRegistradoEsteRecurso(String nombreRecurso)throws Exception;
    @Override
        public abstract ArrayList nombresAgentesAplicacionRegistrados()throws Exception ;
    @Override
        public abstract ArrayList nombresRecursosRegistrados() throws Exception;
    @Override
        public abstract Boolean estaRegistradoEsteAgente(String nombreAgente);
}