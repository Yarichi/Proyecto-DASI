package icaro.infraestructura.recursosOrganizacion.directorioOrganizacion.imp;
import icaro.infraestructura.recursosOrganizacion.directorioOrganizacion.ItfUsoDirectorioOrganizacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescOrganizacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgenteAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgentes;
import icaro.infraestructura.recursosOrganizacion.directorioOrganizacion.imp.ClaseGeneradoraDirectorioOrganizacion;
import icaro.infraestructura.entidadesBasicas.excepciones.UsoRecursoException;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.Enumeration;
import java.util.Hashtable;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;



public class DirectorioOrganizacionImp extends ClaseGeneradoraDirectorioOrganizacion {

	  // Tabla con todos los interfaces
	  private Hashtable<String,Object> repositorio;

	  // Depuracin del componente
	  public final static boolean DEBUG=true;
	  /**
	   * Constructor. Registra el repositorio y lo deja accesible de forma remota.
	 */ 
	  public DirectorioOrganizacionImp() throws RemoteException {
		  super("RepositorioInterfaces");
		  repositorio = new Hashtable<String, Object>();
	  }
	  


	  /**
	   * Almacena una interfaz en el repositorio
	   * En caso de existir el nombre previamente, se actualiza la referencia
	   * @param nombre
	   * @param interfaz
	   */
	  public synchronized void registrarInterfaz(String nombre, Object interfaz) 
	  {
	    this.repositorio.put(nombre,interfaz);
	    if (DEBUG) System.out.println("Registrado en repositorio nombre="+nombre+", interfaz="+interfaz);
	  }

	  /**
	   * Recupera una interfaz del repositorio
	   * @param nombre  Nombre de la interfaz a recuperar
	   * @return  Interfaz asociada a ese nombre o null si no se ha encontrado ese nombre
	   */
	  public Object obtenerInterfaz(String nombre) 
	  {
	    if ( !this.repositorio.containsKey(nombre) ){
	      System.err.println("RepositorioInterfaces: No se pudo recuperar "+nombre+" porque no existe ningn objeto con ese nombre");
	      System.err.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> "+this.listarNombresInterfacesRegistradas());
	      System.out.println("RepositorioInterfaces: No se pudo recuperar "+nombre+" porque no existe ningn objeto con ese nombre");
	      System.out.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> "+this.listarNombresInterfacesRegistradas());
	    }
	    return this.repositorio.get(nombre);
	  }

	
	  /**
	   * Cancela el registro de una interfaz en el repositorio
	   * @param nombre Nombre de la interfaz a eliminar del repositorio
	   * @throws RemoteException
	   */
	  public synchronized void eliminarRegistroInterfaz(String nombre)
	  {

	    if (this.repositorio.containsKey(nombre))
	    {
	      this.repositorio.remove(nombre);
	      if(DEBUG) System.out.println("Se elimin la referencia a "+nombre+" del repositorio de interfaces.");
	    }
	    else
	      if(DEBUG) System.out.println("Se intent eliminar la referencia "+nombre+" del repositorio, pero no estaba definida.");
	  }
	  
	  
	  /**
	   * Devuelve una lista con los nombres de todos los interfaces registrados
	   * @return
	   * @throws RemoteException
	   */
	  public  String listarNombresInterfacesRegistradas()
	  {
	    String ret="";
	    Enumeration enume = this.repositorio.keys();
	    while (enume.hasMoreElements()) {
	      Object item = enume.nextElement();
	      ret += item+" ";
	    }
	    return ret;
	  }
	

	  public String toString()
	  {
	    StringBuffer str= new StringBuffer("Listado de interfaces registrados Nombre/Interfaz");
	    Enumeration enume = this.repositorio.keys();
	    while (enume.hasMoreElements()) {
	      Object key = enume.nextElement();
	      str.append("\n");
	      str.append((String)key);
	      str.append("->");
	      str.append(this.repositorio.get(key));
	    }
	    return str.toString();
	  }

    @Override
public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 *
	 * @param descAlternativa
	 */
	public void DirectorioOrganizacionImp(String descAlternativa){

	}

	public void DirectorioOrganizacionImp(){

	}

	public void creacionConfiguracion(){

	}

	/**
	 *
	 * @param nombre
	 * @exception UsoRecursoException
	 */
	public DescComportamientoAgente getDescComportamientoAgente(String nombre)
	  throws UsoRecursoException{
		return null;
	}

	/**
	 *
	 * @param id
	 * @exception UsoRecursoException
	 */
	public DescInstanciaAgenteAplicacion getDescInstanciaAgenteAplicacion(String id)
	  throws UsoRecursoException{
		return null;
	}

	/**
	 *
	 * @param id
	 * @exception UsoRecursoException
	 */
	public DescInstanciaGestor getDescInstanciaGestor(String id)
	  throws UsoRecursoException{
		return null;
	}

	/**
	 *
	 * @param id
	 * @exception UsoRecursoException
	 */
	public DescInstanciaRecursoAplicacion getDescInstanciaRecursoAplicacion(String id)
	  throws UsoRecursoException{
		return null;
	}

	public DescOrganizacion getDescOrganizacion(){
		return null;
	}

	/**
	 *
	 * @param nombre
	 * @exception UsoRecursoException
	 */
	public DescRecursoAplicacion getDescRecursoAplicacion(String nombre)
	  throws UsoRecursoException{
		return null;
	}

	/**
	 *
	 * @param atributo
	 * @exception UsoRecursoException
	 */
	public String getValorPropiedadGlobal(String atributo)
	  throws UsoRecursoException{
		return "";
	  /**
	   *  Pruebas
	   
	  public static void main(String[] args) {
	    Object obj1 = new Object();
	    Object obj2 = new Object();
	    Object obj3 = new Object();


	    try {
	      ItfUsoRepositorioInterfaces rep = RepositorioInterfaces.instance();
	    	
	      rep.registrarInterfaz("Objeto UNO",obj1);
	      rep.registrarInterfaz("Objeto DOS",obj2);
	      rep.registrarInterfaz("Objeto tRES",obj3);

	      System.out.println("El objeto uno es "+rep.obtenerInterfaz("Objeto UNO"));
	      System.out.println(""+rep.toString());
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }

	  }
		*/
        }

    public Object obtenertInterfaz(String nombre) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    }

