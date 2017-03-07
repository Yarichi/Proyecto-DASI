package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestionPercepcion;
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
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.PercepcionAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ProcesadorInfoReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.FactoriaControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

import java.util.logging.Level;
import org.apache.log4j.Logger;





/**
 * Produce instancias del patr�n
 *
 * @F Garijo
 * @created 20  Mayo  2010
 */

public class FactoriaAgenteReactivoImp1 extends FactoriaAgenteReactivo {

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

    protected ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
    private String nombreInstanciaAgente;
    protected AgenteReactivoAbstracto agente;

    protected ItfUsoRepositorioInterfaces repositorioIntfaces= NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;

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
    protected InterfazGestionPercepcion itfGestionPercepcion;
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

    @Override
    public void crearAgenteReactivo(DescInstanciaAgente descInstanciaAgente)throws ExcepcionEnComponente {

        // Paso 1 Se obtienen los objetos de la descripcion

			String nombreInstanciaAgente = descInstanciaAgente.getId();
			DescComportamientoAgente descagente = descInstanciaAgente.getDescComportamiento();
			String nombreComportamiento = descagente.getNombreComportamiento();
			boolean esGestor = descagente.getRol() == RolAgente.GESTOR;
      try {
             trazas = (ItfUsoRecursoTrazas) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            }
        catch (Exception e) {
            logger.error("No se pudo usar el recurso de trazas", e);
        }
            // Paso 2 se obtienen los parametros necesarios para crear los componentes internos
			AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas = obtenerAcciones(descInstanciaAgente);
	//		String rutaTabla = obtenerRutaTablaTransiciones(descInstanciaAgente);
                        String rutaTabla = descInstanciaAgente.getDescComportamiento().getLocalizacionFicheroAutomata();
         if ((accionesSemanticasEspecificas !=null)&(rutaTabla!=null)){

             try {
// En esta version la percepcion envia la informacion procesada al control
// Se crea en primer lugar el control y postieriormente la percepcion y la clase que implementa
// las interfaces externas    
// Se crea  un objeto que implementa las interfaces de  uso y de gestion del agente
            this.agente = (AgenteReactivoAbstracto)new AgenteReactivoImp2(nombreInstanciaAgente);
// Se crea el control del agente por medio de su factoria
		//	ProcesadorEventosAbstracto control = FactoriaControlAgteReactivo.instancia().crearControlAgteReactivo( accionesSemanticasEspecificas,rutaTabla , nombreInstanciaAgente, itfConsumidorPercepcion, itfProductorPercepcion);
                ProcesadorInfoReactivoAbstracto control = FactoriaControlAgteReactivo.instancia().crearControlAgteReactivo( accionesSemanticasEspecificas,rutaTabla , agente);
                itfControlAgteReactivo = (ItfControlAgteReactivo) control ;
// Se crea la percepcion y se le pasa la interfaz del control
            PercepcionAbstracto percepcion = FactoriaPercepcion.instancia().crearPercepcion(agente,itfControlAgteReactivo);
            itfGestionPercepcion = (InterfazGestionPercepcion) percepcion;
            itfProductorPercepcion = (ItfProductorPercepcion) percepcion;
	// paso 3.3 se crea la instancia que implementa las interfaces
	//	AgenteReactivoAbstracto patron = new AgenteReactivoImp(nombreInstanciaAgente,itfControlAgteReactivo,itfProductorPercepcion,itfConsumidorPercepcion);
          // Se crea el automata del ciclo de vida para implementar la interfaz de Gestion
                 ItfUsoAutomataEFsinAcciones itfAutomata = (ItfUsoAutomataEFsinAcciones) ClaseGeneradoraAutomataEFsinAcciones.instance(NombresPredefinidos.FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE);
                this.agente.setComponentesInternos(nombreInstanciaAgente,itfAutomata,itfControlAgteReactivo,itfProductorPercepcion,itfGestionPercepcion);
        // paso 3. 4  Se define la interfaz de uso del agente creado en las acciones semánticas específicas
//        accionesSemanticasEspecificas.setItfUsoAgenteReactivo(agente);
        // Quedan definidos todos los objetos necesarios para implementar el ejemplar creado
        logger.debug(nombreInstanciaAgente + ":Creacion del Agente ...ok");
   //         trazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente,
    //                nombreInstanciaAgente + ":Creaci�n del Agente ...ok",
    //                NivelTraza.debug));
        // Paso 4 Procedemos a registrar las interfaces de la instancia crada en el repositorio

			repositorioIntfaces.registrarInterfaz(
					NombresPredefinidos.ITF_GESTION + nombreInstanciaAgente, (ItfGestionAgenteReactivo)agente);
			repositorioIntfaces.registrarInterfaz(
					NombresPredefinidos.ITF_USO + nombreInstanciaAgente, (ItfUsoAgenteReactivo)agente);

			// activamos trazas pesadas
			agente.setDebug(false);
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
	
    @Override
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
     String rutaAutomata = obtenerRutaValidaAutomata ( rutaComportamiento );
     Class AccionesSemanticas = obtenerClaseAccionesSemanticas (rutaComportamiento);

// Creamos las acciones semanticas
      AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas = (AccionesSemanticasAgenteReactivo)AccionesSemanticas.newInstance();

     accionesSemanticasEspecificas.setNombreAgente(nombreInstanciaAgente);
           //Paso 3 Se intenta la creacion de los componentes internos
            // Paso 3.1 Creacion de la Percepcion del agente
    this.agente = (AgenteReactivoAbstracto)new AgenteReactivoImp2(nombreInstanciaAgente);
// Se crea el control del agente por medio de su factoria
		//	ProcesadorEventosAbstracto control = FactoriaControlAgteReactivo.instancia().crearControlAgteReactivo( accionesSemanticasEspecificas,rutaTabla , nombreInstanciaAgente, itfConsumidorPercepcion, itfProductorPercepcion);
                ProcesadorInfoReactivoAbstracto control = FactoriaControlAgteReactivo.instancia().crearControlAgteReactivo( accionesSemanticasEspecificas,rutaAutomata , agente);
                        itfControlAgteReactivo = (ItfControlAgteReactivo) control ;
// Se crea la percepcion y se le pasa la interfaz del control

            PercepcionAbstracto percepcion = FactoriaPercepcion.instancia().crearPercepcion(agente,itfControlAgteReactivo);
            itfGestionPercepcion = (InterfazGestionPercepcion) percepcion;
            itfProductorPercepcion = (ItfProductorPercepcion) percepcion;

          // Se crea el automata del ciclo de vida para implementar la interfaz de Gestion
                 ItfUsoAutomataEFsinAcciones itfAutomata = (ItfUsoAutomataEFsinAcciones) ClaseGeneradoraAutomataEFsinAcciones.instance(NombresPredefinidos.FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE);
                 this.agente.setComponentesInternos(nombreInstanciaAgente,itfAutomata,itfControlAgteReactivo,itfProductorPercepcion,itfGestionPercepcion);
        // paso 3. 4  Se define la interfaz de uso del agente creado en las acciones semánticas específicas

        accionesSemanticasEspecificas.setCtrlGlobalAgenteReactivo(agente);
        // Quedan definidos todos los objetos necesarios para implementar el ejemplar creado
        logger.debug(nombreInstanciaAgente + ":Creacion del Agente ...ok");
   //         trazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente,
    //                nombreInstanciaAgente + ":Creaci�n del Agente ...ok",
    //                NivelTraza.debug));

        // Paso 4 Procedemos a registrar las interfaces de la instancia crada en el repositorio

			repositorioIntfaces.registrarInterfaz(
					NombresPredefinidos.ITF_GESTION + nombreInstanciaAgente, (ItfGestionAgenteReactivo)agente);
			repositorioIntfaces.registrarInterfaz(
					NombresPredefinidos.ITF_USO + nombreInstanciaAgente, (ItfUsoAgenteReactivo)agente);

			// activamos trazas pesadas
			agente.setDebug(false);
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
			logger.error("Error AL CREAR LA PERCEPCION. La factoria no puede crear la instancia : " + nombreInstanciaAgente + " La ruta del  clase A Semanticas  es " +rutaComportamiento , ex);
            System.err
					.println(" No se puede crear la Percepcion del agente. La factoria no puede crear la instancia.");
            throw new ExcepcionEnComponente("patronAgenteReactivo.contol", "posible error al crear la percepcion","percepcion",""  );
        }


     }

    private String obtenerRutaValidaAutomata (String rutaComportamiento)throws ExcepcionEnComponente {
    // La ruta del comportamiento no incluye la clase
    // Obtenemos la clase de AS en l aruta
       rutaComportamiento =File.separator+rutaComportamiento.replace(".", File.separator);
   //   String rutaBusqueda = NombresPredefinidos.RUTA_SRC+rutaComportamiento;
       String rutaBusqueda = rutaComportamiento;
 //     String rutaBusqueda = utils.constantes.rutassrc +rutaComportamiento;
        rutaBusqueda = NombresPredefinidos.RUTA_SRC+rutaComportamiento;
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
    // Obtenemos la clase de AS en la ruta
    String rutaClases = rutaComportamiento;
            rutaComportamiento = NombresPredefinidos.RUTA_SRC+File.separator+rutaComportamiento.replace(".", File.separator);
 //           rutaComportamiento = utils.constantes.rutassrc +File.separator+rutaComportamiento.replace(".", File.separator);
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

        TipoAgente tipo = comportamiento.getTipo();
                String nombreCptoAgte = instAgente.getId();
                String msgUsuario = null;
		if (!comportamiento.getTipo().equals(TipoAgente.REACTIVO)) {
                    msgUsuario= "El tipo de agente definido es : " +comportamiento.getTipo() +" Debe ser REACTIVO ";
                        trazas.trazar (nombreCptoAgte,msgUsuario,NivelTraza.error );
                        System.err.println(msgUsuario);
                }
		//	String ruta = comportamiento.getLocalizacionComportamiento()+"."+ NombresPredefinidos.PREFIJO_CLASE_ACCIONES_SEMANTICAS + comportamiento.getNombreComportamiento();
                        String rutaAcciones = comportamiento.getLocalizacionClaseAcciones();
                 //       String rutaAutomata = comportamiento.getLocalizacionFicheroAutomata();
                        if(rutaAcciones==null){
                            trazas.trazar(nombreCptoAgte, "La ruta de la clase de Acciones semanticas del comportamiento : " +nombreCptoAgte +"  no esta definida \n", NivelTraza.error);
                            return null;
                        }

		try {
			Class claseAcciones = Class.forName(rutaAcciones);
			AccionesSemanticasAgenteReactivo acciones = (AccionesSemanticasAgenteReactivo)claseAcciones.newInstance();
			acciones.setNombreAgente(nombre);
			return acciones;
		} catch (InstantiationException ex) {
			msgUsuario= "La clase :"+ rutaAcciones + "que debe implementar las acciones del gestor , no puede instanciarse.";
                        trazas.trazar (nombreCptoAgte,msgUsuario,NivelTraza.error );
                        System.err.println(msgUsuario);
		} catch (IllegalAccessException ex) {
			msgUsuario= "La clase :"+ rutaAcciones + "que debe implementar las acciones del gestor , no tiene un constructor sin parametros.";
                        trazas.trazar (nombreCptoAgte,msgUsuario,NivelTraza.error );
                        System.err.println(msgUsuario);
		} catch (ClassNotFoundException ex) {
			msgUsuario= "La clase :"+ rutaAcciones + "que debe implementar las acciones del gestor , no existe.";
                        trazas.trazar (nombreCptoAgte,msgUsuario,NivelTraza.error );
                        System.err.println(msgUsuario);
                    System.err
					.println("La clase "
							+ rutaAcciones
							+ "que debe implementar las acciones semanticas, no existe.");
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
     *  Introduce un nuevo evento en la percepcion
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