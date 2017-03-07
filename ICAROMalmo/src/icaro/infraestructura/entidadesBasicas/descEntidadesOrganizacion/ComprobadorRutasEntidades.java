/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.log4j.Logger;
import java.util.logging.Level;
/**
 *
 * @author Francisco J Garijo
 */
public class ComprobadorRutasEntidades {

private Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());
private  Class[] clasesUltimoDirectorio;
private String identUltimoDirectorio;

public ComprobadorRutasEntidades() {
	}

 public boolean existeSchemaDescOrganizacion() {
// Se cmprueba la existencia del fichero en la ruta predefinida de la organizacion


     File schema = new File(NombresPredefinidos.DESCRIPCION_SCHEMA);
     if (!schema.exists()) {
			logger.fatal("No se ha encontrado el fichero de descripcion:"
					+ "\n\t\t\t" + schema.getAbsolutePath()
					+ ".\n Compruebe la ruta y el nombre del fichero.");
                    }

				return (schema.exists()) ;

	}
 public boolean existeDescOrganizacion1(String identFicherodescripcion) {
// Se cmprueba la existencia del fichero en la ruta predefinida de la organizacion

	String	descXML = NombresPredefinidos.RUTA_DESCRIPCIONES+identFicherodescripcion+".xml";
    File ficheroDescripcion = new File(descXML);
          if (!ficheroDescripcion.exists()) {
			logger.fatal("No se ha encontrado el fichero de descripcion:"
					+ "\n\t\t\t" + ficheroDescripcion.getAbsolutePath()
					+ ".\n Compruebe la ruta y el nombre del fichero.");
                    }
				return (ficheroDescripcion.exists()) ;

	}
public boolean existeDescOrganizacion(String identFicherodescripcion){
    // La ruta del comportamiento no incluye el fichero 
    //  File.separator + rutaComportamiento.replace(".", File.separator);
    //    identFicherodescripcion = identFicherodescripcion+".xml";
    //   String rutaBusqueda = NombresPredefinidos.RUTA_DESCRIPCIONES.replace(".", File.separator);
//       String rutaBusqueda = utils.Constantes.rutassrc + rutaComportamiento;
    String rutaBusqueda =NombresPredefinidos.RUTA_DESCRIPCIONES ;
    int posSeparadorDirect = identFicherodescripcion.lastIndexOf(".");
    if (posSeparadorDirect>0)rutaBusqueda = rutaBusqueda+identFicherodescripcion.substring(0, posSeparadorDirect);
       Boolean ficheroEncontrado=false;
       Queue<File> ficherosRuta = new LinkedList<File>();
       ficherosRuta.add( new File(rutaBusqueda));
       if (ficherosRuta == null ) return false;
        while(!ficherosRuta.isEmpty() &&!ficheroEncontrado){
          for (File ficheroVisitado : ficherosRuta.poll().listFiles()) {
              if (ficheroVisitado.isDirectory()){
              ficherosRuta.add(ficheroVisitado);
            } else if (ficheroVisitado.isFile()) {
                String nombFicherovisitado = ficheroVisitado.getName();
                if (ficheroVisitado.getName().equals(identFicherodescripcion))ficheroEncontrado = true;
            }
          }  
        }
        return ficheroEncontrado;      
    }
public String buscarDescOrganizacion(String identFicherodescripcion){
       // devuelve la ruta del fichero encontrado o null si no lo encuentra
    //   identFicherodescripcion = identFicherodescripcion+".xml";
    //   String rutaBusqueda = NombresPredefinidos.RUTA_DESCRIPCIONES.replace(".", File.separator);
       String rutaBusqueda =NombresPredefinidos.RUTA_DESCRIPCIONES ;
    int posSeparadorDirect = identFicherodescripcion.lastIndexOf("/");
    if (posSeparadorDirect>0){
        posSeparadorDirect = posSeparadorDirect+1;
        rutaBusqueda = rutaBusqueda+identFicherodescripcion.substring(0, posSeparadorDirect);
        identFicherodescripcion = identFicherodescripcion.substring(posSeparadorDirect);
    }
    rutaBusqueda = rutaBusqueda.replace(".", File.separator);
    identFicherodescripcion = identFicherodescripcion+".xml";
       String rutaFicheroVisitado = null;
//       String rutaBusqueda = utils.Constantes.rutassrc + rutaComportamiento;
       Boolean ficheroEncontrado=false;
       Queue<File> ficherosRuta = new LinkedList<File>();
       ficherosRuta.add( new File(rutaBusqueda));
       if (ficherosRuta == null ) return null;
        while(!ficherosRuta.isEmpty() &&!ficheroEncontrado){
          for (File ficheroVisitado : ficherosRuta.poll().listFiles()) {
              if (ficheroVisitado.isDirectory()){
              ficherosRuta.add(ficheroVisitado);
            } else if (ficheroVisitado.isFile()) {
                String nombFicherovisitado = ficheroVisitado.getName();
                if (ficheroVisitado.getName().equals(identFicherodescripcion)){
                    ficheroEncontrado = true;
                    rutaFicheroVisitado = ficheroVisitado.getAbsolutePath();
                }
            }
          }  
        }
        return rutaFicheroVisitado;      
    }

    private String normalizarRuta(String ruta){
	/*Esta funcin cambia la primera letra del nombre y la pone en minsculas*/
		String primero = ruta.substring(0,1).toLowerCase(); //obtengo el primer carcter en minsculas
		String rutaNormalizada = primero + ruta.substring(1, ruta.length());

		return rutaNormalizada;
	}

//    private Class obtenerClaseAccionesSemanticas(String rutaComportamiento)throws ExcepcionEnComponente {
//    // La ruta del comportamiento no incluye la clase
//    // Obtenemos la clase de AS en l aruta
//    String rutaClases = rutaComportamiento;
////            rutaComportamiento = "src"+File.separator+rutaComportamiento.replace(".", File.separator);
//            rutaComportamiento = NombresPredefinidos.RUTA_Carpeta_CodigoFuente_ICARO + File.separator + rutaComportamiento.replace(".", File.separator);
//            String[] ficherosRuta = (new File (rutaComportamiento)).list() ;
//   // Buscamos la clase de acciones semanticas en el array con los ficheros
//        Boolean ClaseASEncontrada=false;
//        String nombreClase= "";
//        for (int i=0; (i<ficherosRuta.length)&!ClaseASEncontrada; i++){
//             nombreClase = ficherosRuta[ i ];
//         if (nombreClase.startsWith(NombresPredefinidos.PREFIJO_CLASE_ACCIONES_SEMANTICAS )&
//            (nombreClase.lastIndexOf(".java")!= 0)  ){
//                        ClaseASEncontrada = true;
//                            }
//            }
//        if (ClaseASEncontrada = true) {
//        try {
//
//            rutaClases = rutaClases+"."+nombreClase;
//            rutaClases =rutaClases.replace(".java", "");
//            int i=1;
//            Class claseAcciones = Class.forName(rutaClases);
//            return claseAcciones;
//            }
//        catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ComprobadorRutasEntidades.class.getName()).log(Level.SEVERE, null, ex);
//
//
//            throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra la clase de acciones semanticas en la ruta :"+rutaComportamiento,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
//                                         }
//            }
//        else
//            throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra la clase de acciones semanticas en la ruta :"+rutaComportamiento,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
//   }
    public Class obtenerClaseAccionesSemanticas(String rutaComportamiento){
        
    try {
         if (!rutaComportamiento.equals(identUltimoDirectorio)){
            clasesUltimoDirectorio= getClasses( rutaComportamiento);
            identUltimoDirectorio=rutaComportamiento;
         }
            for ( Class clase: clasesUltimoDirectorio){
//                String claseN = clase.getSimpleName();
//                int posicion= claseN.indexOf(NombresPredefinidos.NOMBRE_ACCIONES_SEMANTICAS);
               if ( clase.getSimpleName().indexOf(NombresPredefinidos.NOMBRE_ACCIONES_SEMANTICAS)>=0)return clase;
            }
         
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(ComprobadorRutasEntidades.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        java.util.logging.Logger.getLogger(ComprobadorRutasEntidades.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
    }
     public Class obtenerClaseAccionesReactivo(String identClase,String rutaComportamiento){
        
    try {
         if (!rutaComportamiento.equals(identUltimoDirectorio)){
            clasesUltimoDirectorio= getClasses( rutaComportamiento);
            identUltimoDirectorio=rutaComportamiento;
         }
            for ( Class clase: clasesUltimoDirectorio){
//                String claseN = clase.getSimpleName();
//                int posicion= claseN.indexOf(NombresPredefinidos.NOMBRE_ACCIONES_SEMANTICAS);
               if ( clase.getSimpleName().equalsIgnoreCase(identClase))return clase;
            }
         
    } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(ComprobadorRutasEntidades.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        java.util.logging.Logger.getLogger(ComprobadorRutasEntidades.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
    }
    
	public boolean existeClase(String rutaClase) {
			Class clase;
			try {
				clase = Class.forName(rutaClase);
				return (clase!=null);
			} catch (ClassNotFoundException e) {
				return false;
			}
	}
 public boolean existeFichero(String rutaFicheroBusqueda) {

			 File f = new File(rutaFicheroBusqueda);

				return (f.exists()) ;

	}
public boolean existeRecursoClasspath(String recursoClassPath) {

		InputStream input = this.getClass().getResourceAsStream(
				recursoClassPath);
		logger.debug(recursoClassPath+"?"+ ((input != null) ? "  OK" : "  null"));
		return (input != null);
	}

	private String primeraMinuscula(String nombre) {
		String firstChar = nombre.substring(0, 1);
		return nombre.replaceFirst(firstChar, firstChar.toLowerCase());
	}
        /**
* Todavia sin utilizar ni probar 
* Scans all classes accessible from the context class loader which belong to the given package and subpackages.
*
* @param packageName The base package
* @return The classes
* @throws ClassNotFoundException
* @throws IOException
*/
private static Class[] getClasses(String packageName)throws ClassNotFoundException, IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    assert classLoader != null;
    String path = packageName.replace('.', '/');
    Enumeration<URL> resources = classLoader.getResources(path);
    List<File> dirs = new ArrayList<File>();
    while (resources.hasMoreElements()) {
    URL resource = resources.nextElement();
    dirs.add(new File(resource.getFile()));
    }
    ArrayList<Class> classes = new ArrayList<Class>();
    for (File directory : dirs) {
    classes.addAll(findClasses(directory, packageName));
    }
    return classes.toArray(new Class[classes.size()]);
}

/**
* Recursive method used to find all classes in a given directory and subdirs.
*
* @param directory   The base directory
* @param packageName The package name for classes found inside the base directory
* @return The classes
* @throws ClassNotFoundException
*/
//private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
//    List<Class> classes = new ArrayList<Class>();
//    if (!directory.exists()) {
//    return classes;
//    }
//    File[] files = directory.listFiles();
//        for (File file : files) {
//            if (file.isDirectory()) {
//            assert !file.getName().contains(".");
//            classes.addAll(findClasses(file, packageName + "." + file.getName()));
//            } else if (file.getName().endsWith(".class")) {
//            classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
//            }
//        }
//    return classes;
//    }
public String verificarRutaClasesAccion(String rutaClaseAS, String rutaComportamiento){
        // buscamos las clases existente en la ruta de comportamiento. Puede ser la clase AccionesSemanticas y/o otras clases de tipo Accion
        // Si no se encuentra la clase de AS  se devuelve null, y si se encuentra se devuelve la ruta de la clase
        // lo dejamos para cuando se construya el auntomata 
        // verificamos si la ruta es un directorio o si es el nombre de la clase Acciones semnanticas
        try {
            if (rutaClaseAS.indexOf(NombresPredefinidos.NOMBRE_ACCIONES_SEMANTICAS)>0 &&
            existeClase(rutaClaseAS)) return rutaClaseAS;
        if (rutaComportamiento == null) return null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = rutaComportamiento.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        if (dirs.isEmpty()){
            if (rutaComportamiento.indexOf(NombresPredefinidos.NOMBRE_ACCIONES_SEMANTICAS)>0 &&
            existeClase(rutaComportamiento)) return rutaComportamiento;
            else return null;
        }
              ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, rutaComportamiento));
        }
//            boolean encontradoClaseAccion= false;
            if (classes.isEmpty()) return null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().startsWith(NombresPredefinidos.NOMBRE_ACCIONES_SEMANTICAS))
                    return rutaComportamiento+"."+clazz.getSimpleName() ;              
            }
            return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
           return null;   
         }
        
     /**
     * Load all classes from a package.
     * 
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Class[] getAllClassesFromPackage(final String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Find file in package.
     * 
     * @param directory
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            }
            else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }    
}