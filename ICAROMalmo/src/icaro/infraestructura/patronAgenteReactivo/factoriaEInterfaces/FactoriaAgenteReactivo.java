package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.factorias.FactoriaComponenteIcaro;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.FactoriaAgenteReactivoImp;
import java.io.InputStream;
import java.util.logging.Level;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

/**
 *
 *@author     
 *@created    
 */

public abstract class FactoriaAgenteReactivo  extends FactoriaComponenteIcaro{
	
 private static FactoriaAgenteReactivo instancia;
 protected Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    
    public static FactoriaAgenteReactivo instancia(){
        Log log = LogFactory.getLog(FactoriaAgenteReactivo.class);
        if(instancia==null){
            String clase = System.getProperty("icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp",
                    "icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.FactoriaAgenteReactivoInputObjImp0");
            try{
                instancia = (FactoriaAgenteReactivo)Class.forName(clase).newInstance();
            }catch(Exception ex){
                log.error("Implementacion de la factoria: FactoriaAgenteReactivoImp no encontrada",ex);
            }
            
        }
        return instancia;
    }
    
    public abstract void crearAgenteReactivo (DescInstanciaAgente descInstanciaAgente)throws ExcepcionEnComponente ;
    public abstract void crearAgenteReactivo(String nombreInstanciaAgente, String rutaComportamiento)throws ExcepcionEnComponente;
    //public abstract void crearAgenteReactivoDesdeFichero (String fichConfig);
//    private String obtenerRutaValidaAutomata (String rutaComportamiento){
//    // La ruta del comportamiento no incluye la clase
//    // Obtenemos la clase de AS en l aruta
//    //   rutaComportamiento =File.separator+rutaComportamiento.replace(".", File.separator);
//   //   String rutaBusqueda = NombresPredefinidos.RUTA_SRC+rutaComportamiento;
//        String msgInfoUsuario;
//        rutaComportamiento = "/" +rutaComportamiento.replace(".", "/");
//         String rutaAutomata = rutaComportamiento +"/"+ NombresPredefinidos.FICHERO_AUTOMATA;
//		InputStream input = this.getClass().getResourceAsStream(rutaAutomata);
//		logger.debug(rutaAutomata+"?"+ ((input != null) ? "  OK" : "  null"));
//		if  (input != null) return rutaAutomata;
//                else {
//                    // intentamos otra política de nombrado
//                    String nombreEntidad = rutaComportamiento.substring(rutaComportamiento.lastIndexOf("/")+1);
//                    String primerCaracter= nombreEntidad.substring(0,1);
//                    nombreEntidad = nombreEntidad.replaceFirst(primerCaracter, primerCaracter.toUpperCase());
//                    rutaAutomata = rutaComportamiento +"/automata"+nombreEntidad+".xml";
//                    input = this.getClass().getResourceAsStream(rutaAutomata);
//                    if  (input != null) return rutaAutomata;
//                    else {
//                    // la entidad no se encuentra o no esta definida 
//                    msgInfoUsuario = "Error no se encuentra el fichero especificado \n"+
//                            "Para el comportamiento:" + rutaComportamiento + 
//                            "En la ruta: " + rutaAutomata + "\n" +
//                            "Verifique la existencia del fichero en el directorio src \n";
//                    logger.fatal(msgInfoUsuario);
//      //              throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra el fichero del automata  en la ruta :"+rutaAutomata,"Factoria del Agente Reactivo",this.getClass().getName()  );
//                    return null;
//                    }
//                }
//    }  
//    private Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)throws ExcepcionEnComponente {
//
//    	DescComportamientoAgente comportamiento = instAgente.getDescComportamiento();
//        String ruta = comportamiento.getLocalizacionComportamiento()+"."+ NombresPredefinidos.PREFIJO_CLASE_ACCIONES_SEMANTICAS + comportamiento.getNombreComportamiento();
//        try {
//            Class claseAcciones = Class.forName(ruta);
//            return claseAcciones;
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FactoriaAgenteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
//        throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra la clase de acciones semanticas en la ruta :"+ruta,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
//        }
//    }
//    
//    private Class obtenerClaseAccionesSemanticas(String rutaComportamiento)throws ExcepcionEnComponente {
//        String msgInfoUsuario;
//    //    rutaComportamiento = "/" +rutaComportamiento.replace(".", "/");
//        String nombreEntidad = rutaComportamiento.substring(rutaComportamiento.lastIndexOf(".")+1);
//        String primerCaracter= nombreEntidad.substring(0,1);
//               nombreEntidad = nombreEntidad.replaceFirst(primerCaracter, primerCaracter.toUpperCase());
//        String rutaAccionesSemanticas = rutaComportamiento+ "."+NombresPredefinidos.NOMBRE_ACCIONES_SEMANTICAS+nombreEntidad;
//        try {
//            Class claseAcciones = Class.forName(rutaAccionesSemanticas);
//            return claseAcciones;
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FactoriaAgenteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
//        throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra la clase de acciones semanticas en la ruta :"+rutaComportamiento,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
//        }
//    }
}