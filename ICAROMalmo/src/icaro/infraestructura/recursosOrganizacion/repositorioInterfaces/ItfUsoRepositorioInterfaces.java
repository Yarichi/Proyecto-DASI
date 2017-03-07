package icaro.infraestructura.recursosOrganizacion.repositorioInterfaces;

/**
 * Repositorio para acceder de forma centralizada a las interfaces de uso de los
 * componentes
 *
 * @author FGarijo
 * @version
 */
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author FGarijo
 */
public interface ItfUsoRepositorioInterfaces extends ItfUsoRecursoSimple {

    /**
     * Almacena una interfaz en el repositorio En caso de existir el nombre
     * previamente, se actualiza la referencia
     *
     * @param nombre
     * @param interfaz
     * @throws RemoteException
     */
    public void registrarInterfaz(String nombre, Object interfaz) throws RemoteException;

    /**
     * Recupera una interfaz del repositorio
     *
     * @param nombre Nombre de la interfaz a recuperar
     * @return Interfaz asociada a ese nombre o null si no se ha encontrado ese
     * nombre
     * @throws Exception
     */
    public Object obtenerInterfaz(String nombre) throws Exception;

    /**
     * Cancela el registro de una interfaz en el repositorio
     *
     * @param nombre Nombre de la interfaz a eliminar del repositorio
     * @throws Exception
     */
    public void eliminarRegistroInterfaz(String nombre) throws Exception;

    /**
     * Devuelve los nombres de todos los interfaces registrados
     *
     * @return
     * @throws Exception
     */
    public String listarNombresInterfacesRegistradas() throws Exception;

    /**
     * Devuelve los nombres de todos los interfaces registrados en un array de
     * String
     *
     * @return
     * @throws Exception
     */
    public ArrayList nombresInterfacesRegistradas() throws Exception;

    /**
     * Devuelve los nombres de todos las interfaces de los agentes de
     * aplicacionregistrados en un array de String
     *
     * @return
     * @throws Exception
     */
    public ArrayList nombresAgentesAplicacionRegistrados() throws Exception;

    public Object obtenerInterfazUso(String nombre) throws Exception;

    public Object obtenerInterfazGestion(String nombre) throws Exception;

    public Boolean estaRegistradoEsteNombre(String nombreEntidad)throws Exception;;

    public Boolean estaRegistradoEsteRecurso(String nombreRecurso)throws Exception;;

    public ArrayList nombresRecursosRegistrados() throws Exception;

    public Boolean estaRegistradoEsteAgente(String nombreAgente)throws Exception;;
}
