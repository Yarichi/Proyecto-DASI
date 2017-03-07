package icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.imp;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.FactoriaAgenteCognitivo;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.imp.ClaseGeneradoraAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.FactoriaPercepcionAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.PercepcionAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.imp.PercepcionAgenteCognitivoImp;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.FactoriaProcesadorObjetivos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ProcesadorObjetivos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.io.File;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 * Implementation for Cognitive Agent Factory
 *
 * @author F Garijo
 */
public class FactoriaAgenteCognitivoImp2 extends FactoriaAgenteCognitivo {

    public ItfUsoRepositorioInterfaces repositorioInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
    public ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
    private PercepcionAgenteCognitivo percepcion;
    private ProcesadorObjetivos procesObjetivos;
    private AgenteCognitivo agenteContrl;
    private String identAgente;
    private NombresPredefinidos.TipoEntidad tipoEntidad = NombresPredefinidos.TipoEntidad.Cognitivo ;
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
        
	@Override
	public void createCognitiveAgent(DescInstanciaAgente descInstanciaAgente)throws Exception {

		String agentName = descInstanciaAgente.getId();
		String descName = descInstanciaAgente.getDescComportamiento().getNombreComportamiento();
		String nombreAgenteEnMinusculas = descName.substring(0, 1).toLowerCase()
				+ descName.substring(1);
/*
		String ficheroReglas = NombresPredefinidos.RUTA_PROCESO_RESOLUCION_COGNITIVO;
		ficheroReglas = ficheroReglas.replaceAll(NombresPredefinidos.regex_ruta_automata_package, nombreAgenteEnMinusculas);
		ficheroReglas = ficheroReglas.replaceAll(NombresPredefinidos.regex_ruta_automata_behaviour, descName);
*/

		// Get interface repository
	//	ItfUsoRepositorioInterfaces repositorio = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
	//	ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
	try {
// Validamos el comportamiento que nos dan como entrada
        String rutaComportamiento = descInstanciaAgente.getDescComportamiento().getLocalizacionComportamiento();
        String rutaReglas = rutaComportamiento + "."+ NombresPredefinidos.CARPETA_REGLAS;
                rutaReglas = obtenerRutaValidaReglas ( rutaReglas );

		// Create cognitive agent
		trazas.aceptaNuevaTraza(new InfoTraza(agentName,
                    agentName +": Creating agent "+agentName,
				InfoTraza.NivelTraza.debug));
		AgenteCognitivotImp2 agent = new AgenteCognitivotImp2(agentName);

		// Register cognitive agent
		trazas.aceptaNuevaTraza(new InfoTraza(agentName,
				agentName+": Registering agent "+agentName,
				InfoTraza.NivelTraza.debug));
		repositorioInterfaces.registrarInterfaz(NombresPredefinidos.ITF_USO+agentName, (ItfUsoAgenteCognitivo)agent );
		repositorioInterfaces.registrarInterfaz(NombresPredefinidos.ITF_GESTION+agentName, (InterfazGestion)agent);
        }
	catch ( ExcepcionEnComponente exc) {
//			logger.error("Error al crear El CONTROL del agente. La factoria no puede crear la instancia o no se pueden obtener la interfaces : " + nombreInstanciaAgente, exc);
            System.err
					.println(" No se puede crear el control del agente. La factoria no puede crear la instancia.");
                    exc.putCompDondeEstaContenido("patronAgenteCognitivo.contol");
                    exc.putParteAfectada("FactoriaControlAgenteCognitivoImp");
                    throw exc;
        }
 // Damos orden a las factorias de los componentes internos para que creen los elementos


	}
    @Override
  public void crearAgenteCognitivo(DescInstanciaAgente descInstanciaAgente)throws ExcepcionEnComponente {
	  
        // Paso 1 Se obtienen los objetos de la descripcion

	String nombreInstanciaAgente = descInstanciaAgente.getId();
	DescComportamientoAgente agente = descInstanciaAgente.getDescComportamiento();
	String nombreComportamiento = agente.getNombreComportamiento();
        trazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente,tipoEntidad,
				nombreInstanciaAgente+": Creando el agente  "+nombreInstanciaAgente,
				InfoTraza.NivelTraza.debug));
//                        identAgente = nombreInstanciaAgente.substring(0, 1).toLowerCase()
//				+ nombreInstanciaAgente.substring(1);
	//		boolean esGestor = agente.getRol() == RolAgente.GESTOR;


            // Paso 2 se obtienen los parametros necesarios para crear los componentes internos
			// Validamos el comportamiento que nos dan como entrada
           //     String rutaComportamiento = agente.getLocalizacionComportamiento();
           //     String rutaReglas = rutaComportamiento + "."+ NombresPredefinidos.CARPETA_REGLAS;
            //    rutaReglas = obtenerRutaValidaReglas ( rutaReglas );
                        String rutaReglas = agente.getLocalizacionFicheroReglas();

 //     if ((rutaComportamiento !=null)&(rutaReglas!=null)){
             if ((rutaReglas!=null)){
             try {

	//  Se obtienen los parametros y se crean los subcomponentes utilizando las factorias
           //Paso 3 Se intenta la creacion de los componentes internos
            // Paso 3.1 Creacion de la Percepcion del agente
           // Creamos el automata del ciclo de vida del patron
           // Creamos una instancia de  la clase que implementa  la interfaz del agente
           ItfUsoAutomataEFsinAcciones itfAutomataEstadoAgente = (ItfUsoAutomataEFsinAcciones) ClaseGeneradoraAutomataEFsinAcciones.instance(NombresPredefinidos.FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE);
 //          itfAutomataEstadoAgente.transita(NombresPredefinidos.);
           agenteContrl = (AgenteCognitivo) new AgenteCognitivotImp2(nombreInstanciaAgente);
          
          // Ordenamos a las factorias que creen los componentes internos
             
          // Creamos en primer lugar el Procesador de objetivos porque es el mas critico y porque la percepcion
          // envia las evidencias a al procesador de objetivos => necesata conocer  su interfaz
           procesObjetivos = FactoriaProcesadorObjetivos.instance().crearProcesadorObjetivos(agenteContrl,rutaReglas);
           percepcion = FactoriaPercepcionAgenteCognitivo.instance().crearPercepcion(agenteContrl,procesObjetivos );

        // Definimos los parametros del objeto que implementa las interfaces del agente

            agenteContrl.setComponentesInternos(itfAutomataEstadoAgente, percepcion, procesObjetivos);

        // Paso 4 Procedemos a registrar las interfaces de la instancia crada en el repositorio

			repositorioInterfaces.registrarInterfaz(
					NombresPredefinidos.ITF_GESTION + nombreInstanciaAgente, (InterfazGestion)agenteContrl);
			repositorioInterfaces.registrarInterfaz(
					NombresPredefinidos.ITF_USO + nombreInstanciaAgente, (ItfUsoAgenteCognitivo)agenteContrl);
       // La factoria podria verificar que el estado del automata del ciclo de vida es creado
                          agenteContrl.setEstado(NombresPredefinidos.ESTADO_CREADO);
			// activamos trazas pesadas
	//		agenteContrl.setDebug(false);
        }

        catch ( ExcepcionEnComponente exc) {
			logger.error("Error al crear Procesador de Objetivos del agente. La factoria no puede crear la instancia o no se pueden obtener la interfaces : " + nombreInstanciaAgente, exc);
            System.err.println(" No se puede crear el Procesador de Objetivos del agente. La factoria no puede crear la instancia.");
                    exc.putCompDondeEstaContenido("patronAgenteCognitivo.ProcesadorObjetivos");
                    exc.putParteAfectada("FactoriaControlAgenteCognitivoImp2");
                    agenteContrl.setEstado(NombresPredefinidos.ESTADO_ERROR);
                    throw exc;
        }


	catch (Exception ex) {
			logger.error("Error AL CREAR el Agente Cognitivo. La factoria no puede crear la instancia : " + identAgente, ex);
           agenteContrl.setEstado(NombresPredefinidos.ESTADO_ERROR);
           System.err
					.println(" No se puede crear la Percepcion del agente. La factoria no puede crear la instancia.");
            throw new ExcepcionEnComponente("patronAgenteCognitivo.contol", " error al crear los componentes internos","factoria",""  );
        }

        }
         else {
             agenteContrl.setEstado(NombresPredefinidos.ESTADO_ERROR);
             trazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente,
                    nombreInstanciaAgente + ":No se puede crear el agente. El comportamiento no esta bien definido",
                    NivelTraza.error));
            System.err
					.println(" No se puede crear la Instancia del agente . La descrpcion del comportamiento no es correcta.");
         }
     }


private String obtenerRutaValidaReglas (String rutaComportamiento)throws ExcepcionEnComponente {
    // La ruta del comportamiento no incluye la clase
    // Obtenemos el fichero de reglas en la ruta
            rutaComportamiento =File.separator+rutaComportamiento.replace(".", File.separator);
      String rutaBusqueda = NombresPredefinidos.RUTA_SRC+rutaComportamiento;
//      String rutaBusqueda = utils.constantes.rutassrc +rutaComportamiento;
      File f = new File(rutaBusqueda);
       String[] ficherosRuta = f.list() ;
   // Buscamos la clase de acciones semanticas en el array con los ficheros
        Boolean ficheroEncontrado=false;
        String nombreFichero = "";
        int i = 0;
        while((i<ficherosRuta.length)&&!ficheroEncontrado){
             nombreFichero = ficherosRuta[ i ];
            if (nombreFichero.startsWith(NombresPredefinidos.PREFIJO_REGLAS )  ){
                        ficheroEncontrado = true;
           }
        i++;
        }
        if (ficheroEncontrado)
         return rutaComportamiento.replace(File.separator, "/")+"/"+nombreFichero;
        {

            throw new ExcepcionEnComponente ( "PatronAgenteCognitivo", "No se encuentra el fichero de reglas en la ruta :"+rutaComportamiento,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
                }

    }
}
