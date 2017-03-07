package icaro.infraestructura.recursosOrganizacion.directorioOrganizacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgenteAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescRecursoAplicacion;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.infraestructura.entidadesBasicas.excepciones.UsoRecursoException;

/**
 * @author Damiano Spina
 * @version 1.0
 * @created 26-mar-2009 16:15:27
 */
public interface ItfUsoDirectorioOrganizacion extends ItfUsoRecursoSimple {

	/**
	 * 
	 * @param nombre
	 */

	public DescComportamientoAgente getDescComportamientoAgente(String nombre)
	  throws UsoRecursoException;

	/**
	 * 
	 * @param id    id
	 * @exception UsoRecursoException
	 */
	public DescInstanciaAgenteAplicacion getDescInstanciaAgenteAplicacion(String id)
	  throws UsoRecursoException;

	/**
	 * 
	 * @param id    id
	 * @exception UsoRecursoException
	 */
	public DescInstanciaGestor getDescInstanciaGestor(String id)
	  throws UsoRecursoException;

	/**
	 * 
	 * @param id    id
	 * @exception UsoRecursoException
	 */
	public DescInstanciaRecursoAplicacion getDescInstanciaRecursoAplicacion(String id)
	  throws UsoRecursoException;

	/**
	 * 
	 * @param nombre    nombre
	 * @exception UsoRecursoException
	 */
	public DescRecursoAplicacion getDescRecursoAplicacion(String nombre)
	  throws UsoRecursoException;

	/**
	 * 
	 * @param atributo    atributo
	 * @exception UsoRecursoException
	 */
	public String getValorPropiedadGlobal(String atributo)
	  throws UsoRecursoException;

	public String listarNombresInterfacesRegistradas();

	/**
	 * 
	 * @param nombre
	 */
	public Object obtenertInterfaz(String nombre);

	/**
	 * 
	 * @param nombre
	 * @param interfaz
	 */
	public void registrarInterfaz(String nombre, Object interfaz);

    public void eliminarRegistroInterfaz(String nombre);

	/**
	 *
	 * @param nombre    nombre
	 * @exception UsoRecursoException
	 */

}