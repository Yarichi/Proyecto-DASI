package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.imp.ClaseGeneradoraAutomataEFsinAcciones;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.RolAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.TipoAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.FactoriaPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.PercepcionAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ProcesadorInfoReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.FactoriaControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.rmi.RemoteException;
import java.util.Set;
import java.io.File;

import java.util.logging.Level;
import org.apache.log4j.Logger;





/**
 * Produce instancias del patr�n
 * 
 * @F Garijo
 * @created 14 Enero 2009
 */

public class FactoriaAgenteReactivoImp extends FactoriaAgenteReactivo {

	/*
	 * Crea una instancia del patr�n que crea un agente reactivo a partir de las
	 * acciones sem�nticas, el aut�mata que define el comportamiento y el nombre
	 * del agente.
	 */
     private static final long serialVersionUID = 1L;
    /**
	 * Control del agente
	 * @uml.property  name="control"
	 * @uml.associationEnd
	 */
    protected ItfControlAgteReactivo itfControlAgteReactivo;
    protected ClaseGeneradoraRepositorioInterfaces RepositorioIntfaces;

    /**
	 * @uml.property  name="itfGesControl"
	 * @uml.associationEnd
	 */
  //  protected InterfazGestion itfGesControl; //Control
    /**
	 * Percepcion del agente
	 * @uml.property  name="itfConsumidorPercepcion"
	 * @uml.associationEnd
	 */
    protected ItfConsumidorPercepcion itfConsumidorPercepcion;
    /**
	 * @uml.property  name="itfProductorPercepcion"
	 * @uml.associationEnd
	 */
    protected ItfProductorPercepcion itfProductorPercepcion;
    /**
	 * Nombre del agente a efectos de traza
	 * @uml.property  name="nombre"
	 */
    protected String nombre;
    /**
	 * Estado del agente reactivo
	 * @uml.property  name="estado"
	 */
//    protected int estado = InterfazGestion.ESTADO_OTRO;
    /**
	 * Acciones sem�nticas del agente reactivo
	 * @uml.property  name="accionesSemanticas"
	 * @uml.associationEnd
	 */
//    protected AccionesSemanticasImp accionesSemanticas;
    /**
	 * @uml.property  name="dEBUG"
	 */
    private boolean DEBUG = false;
    /**
	 * Conocimiento del agente reactivo
	 * @uml.property  name="itfUsoGestorAReportar"
	 * @uml.associationEnd
	 */
    protected ItfUsoAgenteReactivo itfUsoGestorAReportar;
    /**
	 * @uml.property  name="logger"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    /**
	 * @uml.property  name="trazas"
	 * @uml.associationEnd
	 */
    protected ItfUsoRecursoTrazas trazas;
    private String nombreInstanciaAgente;




    public void crearAgenteReactivo(DescInstanciaAgente descInstanciaAgente)throws ExcepcionEnComponente {

        // Paso 1 Se obtienen los objetos de la descripcion

			String nombreInstanciaAgente = descInstanciaAgente.getId();
			DescComportamientoAgente agente = descInstanciaAgente.getDescComportamiento();
			String nombreComportamiento = agente.getNombreComportamiento();
			boolean esGestor = agente.getRol() == RolAgente.GESTOR;
      try {
             trazas = (ItfUsoRecursoTrazas) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            }
        catch (Exception e) {
            logger.error("No se pudo usar el recurso de trazas", e);
        }

            // Paso 2 se obtienen los parametros necesarios para crear los componentes internos
			AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas = obtenerAcciones(descInstanciaAgente);
			String rutaTabla = obtenerRutaTablaTransiciones(descInstanciaAgente);
         if ((accionesSemanticasEspecificas !=null)&(rutaTabla!=null)){

             try {

            // Paso 0 se obtienen las interfaces de los recursos externos : trazas e interfaces

             RepositorioIntfaces = ClaseGeneradoraRepositorioInterfaces.instance();


			
			
	//		Se obtienen los parametros y se crean los subcomponentes utilizando las factorias
     //       this.crearPercepcionyControl(accionesSemanticasEspecificas,rutaTabla,nombreInstanciaAgente);
           //Paso 3 Se intenta la creacion de los componentes internos
            // Paso 3.1 Creacion de la Percepcion del agente
            PercepcionAbstracto percepcion = FactoriaPercepcion.instancia().crearPercepcion();
            itfConsumidorPercepcion = (ItfConsumidorPercepcion) percepcion;
            itfProductorPercepcion = (ItfProductorPercepcion) percepcion;
		
         // Paso 3.2 Se intenta la creacion del  control del agente
                
		//	ProcesadorEventosAbstracto control = FactoriaControlAgteReactivo.instancia().crearControlAgteReactivo( accionesSemanticasEspecificas,rutaTabla , nombreInstanciaAgente, itfConsumidorPercepcion, itfProductorPercepcion);
                ProcesadorInfoReactivoAbstracto control = FactoriaControlAgteReactivo.instancia().crearControlAgteReactivo( accionesSemanticasEspecificas,rutaTabla , nombreInstanciaAgente, itfConsumidorPercepcion, itfProductorPercepcion);
                        itfControlAgteReactivo = (ItfControlAgteReactivo) control ;
     //     itfGesControl = (InterfazGestion) control.getProcesadorEventos();


			// paso 3.3 se crea la instancia que implementa las interfaces
	//	AgenteReactivoAbstracto patron = new AgenteReactivoImp(nombreInstanciaAgente,itfControlAgteReactivo,itfProductorPercepcion,itfConsumidorPercepcion);
          // Se crea el automata del ciclo de vida para implementar la interfaz de Gestion
                 ItfUsoAutomataEFsinAcciones itfAutomata = (ItfUsoAutomataEFsinAcciones) ClaseGeneradoraAutomataEFsinAcciones.instance(NombresPredefinidos.FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE);
                 AgenteReactivoAbstracto patron = new AgenteReactivoImp(nombreInstanciaAgente,itfAutomata,itfControlAgteReactivo,itfProductorPercepcion,itfConsumidorPercepcion);
        // paso 3. 4  Se define la interfaz de uso del agente creado en las acciones semánticas específicas

//        accionesSemanticasEspecificas.setItfUsoAgenteReactivo(patron);
        // Quedan definidos todos los objetos necesarios para implementar el ejemplar creado
        logger.debug(nombreInstanciaAgente + ":Creaci�n del Agente ...ok");
   //         trazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente,
    //                nombreInstanciaAgente + ":Creaci�n del Agente ...ok",
    //                NivelTraza.debug));

        // Paso 4 Procedemos a registrar las interfaces de la instancia crada en el repositorio
			
			RepositorioIntfaces.registrarInterfaz(
					NombresPredefinidos.ITF_GESTION + nombreInstanciaAgente, (ItfGestionAgenteReactivo)patron);
			RepositorioIntfaces.registrarInterfaz(
					NombresPredefinidos.ITF_USO + nombreInstanciaAgente, (ItfUsoAgenteReactivo)patron);
			
			// activamos trazas pesadas
			patron.setDebug(false);
        }

        catch ( ExcepcionEnComponente exc) {
			logger.error("Error al crear El CONTROL del agente. La factoria no puede crear la instancia o no se pueden obtener la interfaces : " + nombreInstanciaAgente, exc);
            System.err
					.println(" No se puede crear el control del agente. La factoria no puede crear la instancia.");
                    exc.putCompDondeEstaContenido("patronAgenteReactivo.contol");
                    exc.putParteAfectada("FactoriaControlAgenteReactivoImp");
                    throw exc;
        }

		
		catch (Exception ex) {
			logger.error("Error AL CREAR LA PERCEPCON. La factoria no puede crear la instancia : " + nombreInstanciaAgente, ex);
            System.err
					.println(" No se puede crear la Percepcion del agente. La factoria no puede crear la instancia.");
            throw new ExcepcionEnComponente("patronAgenteReactivo.contol", "posible error al crear l paercepcion","percepcion",""  );
        }

        }
         else {
            trazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente,
                    nombreInstanciaAgente + ":No se puede crear el agente. El comportamiento no esta bien definido",
                    NivelTraza.error));
            System.err
					.println(" No se puede crear la Instancia del agente . La descrpcion del comportamiento no es correcta.");
         }
     }
	/*
	 * Este m�todo crea un agente reactivo a partir de un fichero
	 * donde se especifica su configuraci�n
	 */

//	public void crearAgenteReactivoDesdeFichero(String fichConfig) {
//		try {
//			ConfiguracionGlobal config = leerConfig(fichConfig);
//			
//			AccionesSemanticasAgenteReactivo acciones = obtenerAccionesGlobal(config);
//			String rutaTabla = obtenerRutaTablaTransiciones(config);
//			String nombre = obtenerNombre(config);
//			
//			AgenteReactivoAbstracto patron = new AgenteReactivoImp(
//					acciones, rutaTabla,
//					nombre);
//			
//			RepositorioInterfaces.instance().registrarInterfaz(
//					NombresPredefinidos.ITF_GESTION + nombre, (ItfGestionAgenteReactivo)patron);
//			RepositorioInterfaces.instance().registrarInterfaz(
//					NombresPredefinidos.ITF_USO + nombre, (ItfUsoAgenteReactivo)patron);
//
//			
//			acciones.setConfiguracionGlobal(config);
//			acciones.setItfUsoAgenteReactivo(patron);
//			
//			String archivoLog = "log/log.log";
//		    String nivelLog = "debug";
//			patron.setParametrosLoggerAgReactivo(archivoLog, nivelLog);
//
//			
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//	}
	
	/*************************************** M�todos auxiliares para leer a partir de la configuraci�n ***************************************/
//	
//	private static ConfiguracionGlobal leerConfig(String ficheroConfig) {
//		ConfiguracionGlobal conf = null;
//		try {
//			FileReader file = new FileReader(ficheroConfig);
//			conf = ConfiguracionGlobal.unmarshal(file);
//		} catch (FileNotFoundException ex) {
//			System.err.println("El fichero " + ficheroConfig + " no existe");
//			ex.printStackTrace();
//		} catch (Exception ex2) {
//			System.err
//					.println("El fichero "
//							+ ficheroConfig
//							+ " no es un fichero de configuraci�n v�lido. Verifique que la sintaxis del fichero es correcta y compatible con el fichero XML-SCHEMA que le corresponde.");
//			ex2.printStackTrace();
//		}
//		return conf;
//	}
public void crearAgenteReactivo(String nombreInstanciaAgente, String rutaComportamiento)throws ExcepcionEnComponente {

// Se valida la informacion de entrada y si es valida se procede a crear los componentes internos
        try {
             trazas = (ItfUsoRecursoTrazas) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            }
        catch (Exception e) {
            logger.error("No se pudo usar el recurso de trazas", e);
            }
    try {
// Validamos el comportamiento que nos dan como entrada
     String RutaAutomata = obtenerRutaValidaAutomata ( rutaComportamiento );
     Class AccionesSemanticas = obtenerClaseAccionesSemanticas (rutaComportamiento);

// Creamos las acciones semanticas
      AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas = (AccionesSemanticasAgenteReactivo)AccionesSemanticas.newInstance();

     accionesSemanticasEspecificas.setNombreAgente(nombreInstanciaAgente);


           //Paso 3 Se intenta la creacion de los componentes internos
            // Paso 3.1 Creacion de la Percepcion del agente





     PercepcionAbstracto percepcion = FactoriaPercepcion.instancia().crearPercepcion();
            itfConsumidorPercepcion = (ItfConsumidorPercepcion) percepcion;
            itfProductorPercepcion = (ItfProductorPercepcion) percepcion;

         // Paso 3.2 Se intenta la creacion del  control del agente

            ProcesadorInfoReactivoAbstracto control = FactoriaControlAgteReactivo.instancia().crearControlAgteReactivo( accionesSemanticasEspecificas,RutaAutomata , nombreInstanciaAgente, itfConsumidorPercepcion, itfProductorPercepcion);
           itfControlAgteReactivo = (ItfControlAgteReactivo) control ;
     //     itfGesControl = (InterfazGestion) control.getProcesadorEventos();


	// paso 3.3 se crea la instancia que implementa las interfaces
        // Se crea el automata del ciclo de vida
           ItfUsoAutomataEFsinAcciones itfAutomata = (ItfUsoAutomataEFsinAcciones) ClaseGeneradoraAutomataEFsinAcciones.instance(NombresPredefinidos.FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE);

           AgenteReactivoAbstracto patron = new AgenteReactivoImp(nombreInstanciaAgente,itfAutomata,itfControlAgteReactivo,itfProductorPercepcion,itfConsumidorPercepcion);

        // paso 3. 4  Se define la interfaz de uso del agente creado en las acciones semánticas específicas

//        accionesSemanticasEspecificas.setItfUsoAgenteReactivo(patron);
        // Quedan definidos todos los objetos necesarios para implementar el ejemplar creado
        logger.debug(nombreInstanciaAgente + ":Creacion del Agente ...ok");
   //         trazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente,
    //                nombreInstanciaAgente + ":Creaci�n del Agente ...ok",
    //                NivelTraza.debug));

        // Paso 4 Procedemos a registrar las interfaces de la instancia crada en el repositorio
	  RepositorioIntfaces = ClaseGeneradoraRepositorioInterfaces.instance();
			RepositorioIntfaces.registrarInterfaz(
					NombresPredefinidos.ITF_GESTION + nombreInstanciaAgente, (ItfGestionAgenteReactivo)patron);
			RepositorioIntfaces.registrarInterfaz(
					NombresPredefinidos.ITF_USO + nombreInstanciaAgente, (ItfUsoAgenteReactivo)patron);

			// activamos trazas pesadas
			patron.setDebug(false);
        }

        catch ( ExcepcionEnComponente exc) {
			logger.error("Error al crear El CONTROL del agente. La factoria no puede crear la instancia o no se pueden obtener la interfaces : " + nombreInstanciaAgente, exc);
            System.err
					.println(" No se puede crear el control del agente. La factoria no puede crear la instancia.");
                    exc.putCompDondeEstaContenido("patronAgenteReactivo.contol");
                    exc.putParteAfectada("FactoriaControlAgenteReactivoImp");
                    throw exc;
        }

		catch (Exception ex) {
			logger.error("Error AL CREAR LA PERCEPCON. La factoria no puede crear la instancia : " + nombreInstanciaAgente, ex);
            System.err
					.println(" No se puede crear la Percepcion del agente. La factoria no puede crear la instancia.");
            throw new ExcepcionEnComponente("patronAgenteReactivo.contol", "posible error al crear l paercepcion","percepcion",""  );
        }


     }

    private String obtenerRutaValidaAutomata (String rutaComportamiento)throws ExcepcionEnComponente {
    // La ruta del comportamiento no incluye la clase
    // Obtenemos la clase de AS en l aruta
            rutaComportamiento =File.separator+rutaComportamiento.replace(".", File.separator);
      String rutaBusqueda = "src"+rutaComportamiento;
       File f = new File(rutaBusqueda);
       String[] ficherosRuta = f.list() ;
   // Buscamos la clase de acciones semanticas en el array con los ficheros
        Boolean ficheroEncontrado=false;
        String nombreFichero = "";
        int i = 0;
        while((i<ficherosRuta.length)&&!ficheroEncontrado){
             nombreFichero = ficherosRuta[ i ];
            if (nombreFichero.startsWith(NombresPredefinidos.PREFIJO_AUTOMATA )  ){
                        ficheroEncontrado = true;
           }
        i++;
        }


   /*     for (int j=0; (j<ficherosRuta.length)&&!ficheroEncontrado; j++){
             nombreFichero = ficherosRuta[ i ];
         if (nombreFichero.startsWith(NombresPredefinidos.PREFIJO_AUTOMATA )  ){
                        ficheroEncontrado = true;
                            }
            }
   /*/
        if (ficheroEncontrado)
         return rutaComportamiento.replace(File.separator, "/")+"/"+nombreFichero;
        {

            throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra el fichero del automata en la ruta :"+rutaComportamiento,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
                }

    }

    private String normalizarRuta(String ruta){
	/*Esta funci�n cambia la primera letra del nombre y la pone en min�sculas*/
		String primero = ruta.substring(0,1).toLowerCase(); //obtengo el primer car�cter en min�sculas
		String rutaNormalizada = primero + ruta.substring(1, ruta.length()); 
		
		return rutaNormalizada;
	}
	private Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)throws ExcepcionEnComponente { 
        
    	DescComportamientoAgente comportamiento = instAgente.getDescComportamiento();
        String ruta = comportamiento.getLocalizacionComportamiento()+"."+ NombresPredefinidos.PREFIJO_CLASE_ACCIONES_SEMANTICAS + comportamiento.getNombreComportamiento();
        try {
            Class claseAcciones = Class.forName(ruta);
            return claseAcciones;
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FactoriaAgenteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
        throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra la clase de acciones semanticas en la ruta :"+ruta,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
        }
    }
    private Class obtenerClaseAccionesSemanticas(String rutaComportamiento)throws ExcepcionEnComponente {
    // La ruta del comportamiento no incluye la clase
    // Obtenemos la clase de AS en l aruta
    String rutaClases = rutaComportamiento;
            rutaComportamiento = "src"+File.separator+rutaComportamiento.replace(".", File.separator);
        String[] ficherosRuta = (new File (rutaComportamiento)).list() ;
   // Buscamos la clase de acciones semanticas en el array con los ficheros
        Boolean ClaseASEncontrada=false;
        String nombreClase= "";
        for (int i=0; (i<ficherosRuta.length)&!ClaseASEncontrada; i++){
             nombreClase = ficherosRuta[ i ];
         if (nombreClase.startsWith(NombresPredefinidos.PREFIJO_CLASE_ACCIONES_SEMANTICAS )&
            (nombreClase.lastIndexOf(".java")!= 0)  ){
                        ClaseASEncontrada = true;
                            }
            }
        if (ClaseASEncontrada = true) {
        try {
            
            rutaClases = rutaClases+"."+nombreClase;
            rutaClases =rutaClases.replace(".java", "");
            int i=1;
            Class claseAcciones = Class.forName(rutaClases);
            return claseAcciones;
            }
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FactoriaAgenteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
        throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra la clase de acciones semanticas en la ruta :"+rutaComportamiento,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
                                         }
            }
        else
            throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra la clase de acciones semanticas en la ruta :"+rutaComportamiento,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
   }


    private AccionesSemanticasAgenteReactivo obtenerAcciones(DescInstanciaAgente instAgente) {
	
		DescComportamientoAgente comportamiento = instAgente.getDescComportamiento();
	  
 //       TipoAgente tipo = comportamiento.getTipo();
//		if (comportamiento.getTipo().equals(TipoAgente.REACTIVO)) {
//			DescComportamientoAgenteReactivo comportamientoReactivo = (DescComportamientoAgenteReactivo) comportamiento;
			String ruta = comportamiento.getLocalizacionComportamiento()+"."+ NombresPredefinidos.PREFIJO_CLASE_ACCIONES_SEMANTICAS + comportamiento.getNombreComportamiento();
//		} else
//            logger.debug(nombreInstanciaAgente + ":La descripcion del comportamiento no es la de un agente reactivo");

		String nombre = instAgente.getId();
		
		try {
			Class claseAcciones = Class.forName(ruta);
			AccionesSemanticasAgenteReactivo acciones = (AccionesSemanticasAgenteReactivo)claseAcciones.newInstance();
			acciones.setNombreAgente(nombre);
			return acciones;
		} catch (InstantiationException ex) {
			System.err
					.println("La clase "
							+ ruta
							+ "que debe implementar las acciones del gestor de recursos, no puede instanciarse.");
		} catch (IllegalAccessException ex) {
			System.err
					.println("La clase "
							+ ruta
							+ "que debe implementar las acciones del gestor de recursos, no tiene un constructor sin par�metros.");
		} catch (ClassNotFoundException ex) {
			System.err
					.println("La clase "
							+ ruta
							+ "que debe implementar las acciones sem�nticas, no existe.");
		}
		// si falla algo, devuelvo un null
		return null;
	}

	/**
	 * Lee del fichero de config la ruta de la tabla de transiciones
	 * 
	 * @param ficheroConfig
	 * @return
	 */
	private String obtenerRutaTablaTransiciones(DescInstanciaAgente instAgente) {
		DescComportamientoAgente comportamiento = instAgente.getDescComportamiento();
	//	String ruta =  null;
		if (!comportamiento.getTipo().name().equals(TipoAgente.REACTIVO.name())) {
            logger.debug(nombreInstanciaAgente + ":La descripcion del comportamiento no es la de un agente reactivo");
        }
		String ruta = "/"+comportamiento.getLocalizacionComportamiento();
        return ruta.replace(".", "/")+"/automata.xml";

		/*
		String ruta = "./automatas/TablaEstados"+nombreAgente+".xml";
		return ruta;
		*/
	}
   

    /**
     *  Introduce un nuevo evento en la percepci�n
     *
     *@param  evento  Evento que llega nuevo
     */
    public void setParametrosLoggerAgReactivo(String archivoLog, String nivelLog) {
        new ConfiguracionTrazas(logger, archivoLog, nivelLog);
    }
	
	/**
	 * Lee del fichero de config el nombre del agente
	 * 
	 * @param ficheroConfig
	 * @return
	 */
//	private String obtenerNombre(ConfiguracionGlobal config) {
//		String nombre = config.getNombre();
//		return nombre;
//	}

}