package icaro.infraestructura.entidadesBasicas;

import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

/**
 * Definiciones de los nombres de las interfaces. Se puede heredar de esta clase en cada aplicaciï¿½n
 * @author Francisco J Garijo
 * @version 1.0
 */

public class NombresPredefinidos {

  public static final String TIPO_COGNITIVO = "Cognitivo";
  public static final String TIPO_REACTIVO = "Reactivo";
  public static final String ROL_AGENTE = "Gestor";
  public static final String NOMBRE_ENTIDAD_AGENTE = "Agente";
  public static final String NOMBRE_ENTIDAD_INDEFINIDA = "Entidad No efinida";
  public static final String NOMBRE_ENTIDAD_RECURSO = "Recurso";
  public static String NOMBRE_GESTOR_AGENTES = "GestorAgentes";
  public static String NOMBRE_GESTOR_RECURSOS = "GestorRecursos" ;
  public static String NOMBRE_GESTOR_ORGANIZACION ="GestorOrganizacion" ;
  public static String NOMBRE_GESTOR_NODO ="GestorNodo" ;
  public static String NOMBRE_GESTOR_APLICACION_COMUNICACION = "GestorAplicacionComunicacion";
  public static String NOMBRE_INICIADOR ="Iniciador" ;
  public static String NOMBRE_AGENTE_APLICACION = "AgenteAplicacion";
  public static String ACTIVACION_PANEL_TRAZAS_DEBUG = "true";
  public static final String NOMBRE_ITF_USO_CONFIGURACION = "Itf_Uso_Configuracion";
  public static final String CONFIGURACION = "Configuracion";
  public static final String INTERVALO_MONITORIZACION_ATR_PROPERTY = "intervaloMonitorizacion";
  public static final String NOMBRE_PROPIEDAD_GLOBAL_IDENT_EQUIPO = "identificadorEquipo" ;
  public static final String NOMBRE_PROPIEDAD_GLOBAL_JEFE_EQUIPO = "nombreJefeEquipo" ;
  public static final String NOMBRE_PROPIEDAD_GLOBAL_MODELO_ORGANIZATIVO = "organizacion" ;
  public static final String RECURSO_TRAZAS = "RecursoTrazas";

  public static final String EXPR_REG_NOMBRE_AGENTE = "AgenteAplicacion([0-9a-zA-Z])*";
 // public static final String EXPR_REG_NOMBRE_FICHERO_AS = "AccionesSemanticas([0-9a-zA-Z])*.class";
  public static final String NOMBRE_FICHERO_PDFTO_AUTOMATA = "automata.xml";
  public static final String NOMBRE_FICHERO_PDFTO_REGLAS = "reglas.drl";
  public static final String NOMBRE_FICHERO_PDFTO_ACCIONES_SMT = "AccionesSemanticas.class";
  public static final String NOMBRE_ACCIONES_SEMANTICAS = "AccionesSemanticas";
  public static final String EXPR_REG_ACCIONES_AGTE_REACTIVO = "AccionesSemanticas([0-9a-zA-Z])*.class";
  public static final String NOMBRE_TABLA_AUTOMATA_EF = "automata";
  public static final String NOMBRE_FICHERO_CL_GENER_RECURSO = "ClaseGeneradora.class";
//  public static final String EXPR_REG_NOMBRE_FICHERO_AUTOMATA = "automata*.xml";
//  public static final String EXPR_REG_NOMBRE_FICHERO_REGLAS = "reglas([0-9a-zA-Z])*.drl";
  public static final String EXPR_REG_NOMBRE_GESTOR = "(GestorOrganizacion|GestorAgentes|GestorRecursos)([0-9a-zA-Z])*";
  public static  enum TipoEntidad {Cognitivo,ADO,DirigidoPorObjetivos,Reactivo,Recurso, noDefinido};
  public static final Integer TIPO_ESTADO_FINAL_AUTOMATA_EF = 3;
  public static final Integer AUTOMATA_EF_TIPO_ESTADO_FINAL_ = 3;
  public static final Integer AUTOMATA_EF_TIPO_ESTADO_INTERMEDIO = 2;
  public static final Integer AUTOMATA_EF_TIPO_ESTADO_INICIAL = 1;
  public static final Integer AUTOMATA_EF_TIPO_TRANSICION_SIN_ACCION = 0;
  public static final Integer AUTOMATA_EF_TIPO_TRANSICION_ACCION_BLOQ = 1;
  public static final Integer AUTOMATA_EF_TIPO_TRANSICION_ACCION_THREAD= 2;
  public static final Integer AUTOMATA_EF_TIPO_TRANSICION_METODO_AS_BLOQ = 3;
  public static final Integer AUTOMATA_EF_TIPO_TRANSICION_METODO_AS_CONCURR = 4;
  public static final String AUTOMATA_EF_NOMBRE_MODO_BLOQUEANTE ="bloqueante";
  public static final String AUTOMATA_EF_NOMBRE_MODO_CONCURRENTE ="paralelo";
//  public static final Integer TRANSICION_AUTOMATA_EF_SIN_ACCION = 0;
//  public static final Integer TRANSICION_AUTOMATA_EF_ACCION_BLOQ = 1;
//  public static final Integer TRANSICION_AUTOMATA_EF_ACCION_CONCUR = 2;
  public static final String ITF_GESTION = "Itf_Ges_";
  public static final String ITF_USO = "Itf_Uso_";

  public static final String FACTORIA_AGENTE_REACTIVO = "FactoriaAgenteReactivo";
  public static final String FACTORIA_RECURSO_SIMPLE = "FactoriaRecursoSimple";

//  public static final String FACTORIA_AGENTE_COGNITIVO = "FactoriaAgenteCognitivo";
  public static final String FACTORIA_AGENTE_COGNITIVO = "CognitiveAgentFactory";
  public static final String PAQUETE_GESTORES = "icaro.gestores";
  public static final String COMPORTAMIENTO_PORDEFECTO_GESTOR_ORGANIZACION = "icaro.gestores.gestorOrganizacion.comportamiento";
  public static final String COMPORTAMIENTO_PORDEFECTO_GESTOR_NODO = "icaro.gestores.gestorNodo.comportamiento";
  public static final String COMPORTAMIENTO_PORDEFECTO_INICIADOR = "icaro.gestores.iniciador";
  public static final String PAQUETE_GESTOR_ORGANIZACION = "icaro.gestores.gestorOrganizacion.comportamiento";
  public static final String COMPORTAMIENTO_PORDEFECTO_GESTOR_AGENTES = "icaro.gestores.gestorAgentes.comportamiento";
  public static final String COMPORTAMIENTO_PORDEFECTO_GESTOR_RECURSOS = "icaro.gestores.gestorRecursos.comportamiento";
  public static final String RUTA_AGENTES_APLICACION = "icaro.aplicaciones.agentes";
  public static final String RUTA_RECURSOS_APLICACION = "icaro.aplicaciones.recursos";

  public static final String PAQUETE_CLASES_GENERADORAS_ORGANIZACION = "icaro.infraestructura.clasesGeneradorasOrganizacion";
  public static  String CLASES_GENERADORA_ORGANIZACION_PORDEFECTO = "icaro.infraestructura.clasesGeneradorasOrganizacion.ArranqueSistemaSinAsistente";
  public static final String PREFIJO_CLASE_ACCIONES_SEMANTICAS = "AccionesSemanticas";
  public static final String PREFIJO_CLASE_GENERADORA_RECURSO = "ClaseGeneradora";
  public static final String PREFIJO_AUTOMATA = "automata";
  public static final String FICHERO_AUTOMATA = "automata.xml";
  public static final String PREFIJO_REGLAS = "reglas";
  public static final String PREFIJO_PROPIEDAD_TAREA_TIMEOUT ="timeMsTarea:";
  public static final String PREFIJO_MSG_TIMEOUT = "Expiro timeout";
  public static final String CARPETA_REGLAS = "procesoResolucionObjetivos";
  public static final String FICHERO_REGLAS = "reglas.drl";
  public static final String CARPETA_OBJETIVOS = "objetivos";
  public static final String CARPETA_TAREAS = "tareas";
  public static final String CARPETA_COMPORTAMIENTO = "comportamiento";
  public static final String DESCRIPCION_SCHEMA = "./schemas/DescripcionOrganizacionSchema.xsd";
  public static final String RUTA_DESCRIPCIONES = "config/icaro/aplicaciones/descripcionOrganizaciones/";
//  public static final String RUTA_DESCRIPCIONES = "./config/icaro/aplicaciones/descripcionOrganizaciones/";
  public static final String PAQUETE_JAXB = "icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb";
  public static final String RUTA_LOGS = "./log/";
// Estados e inputs para el automata del ciclo de vida
  public static final String ESTADO_INICIAL = "estadoInicial";
  public static final String ESTADO_FINAL = "estadoFinal";
  public static final String ESTADO_EN_CONSTRUCCION = "creandose";
  public static final String ESTADO_CREADO = "creado";
  public static final String ESTADO_ARRANCADO = "arrancado";
  public static final String ESTADO_ARRANCANDO = "arrancando";
  public static final String ESTADO_FALLO_ARRANQUE = "falloArranque";
  public static final String ESTADO_ERROR = "error";
  public static final String ESTADO_ACTIVO = "activo";
  public static final String ESTADO_FALLO_TEMPORAL = "falloTemporal";
  public static final String ESTADO_PARADO = "parado";
  public static final String ESTADO_TERMINANDO = "terminando";
  public static final String ESTADO_TERMINADO = "terminado";
  public static final String INPUT_ARRANCA = "arrancar";
  public static final String INPUT_FALLO = "fallo";
  public static final String INPUT_ERROR = "error";
  public static final String INPUT_COMENZAR = "comenzar";
  public static final String INPUT_OK = "ok";
  public static final String INPUT_PARAR = "parar";
  public static final String INPUT_CONTINUAR = "continuar";
  public static final String INPUT_ACTIVAR = "activar";
  public static final String INPUT_TERMINAR = "terminar";
  public static final String NOMBRE_TRANSICION_AUTOMATA_EF = "transicion";
  
  public static final String NOMBRE_MODO_BLOQUEANTE_AUTOMATA_EF_SIN_ACCION ="bloqueante";
  public static final String NOMBRE_MODO_CONCURRENTE_AUTOMATA_EF_SIN_ACCION ="paralelo";
  public static final String NOMBRE_TRANSICION_UNIVERSAL_AUTOMATA_EF ="transicionUniversal";
  public static final String NOMBRE_INPUT_AUTOMATA_EF = "input";
  public static final String NOMBRE_ESTADO_AUTOMATA_EF = "estado";
  public static final String NOMBRE_ACCION_AUTOMATA_EF = "accion";
  public static final String NOMBRE_MODO_TRANSICION_AUTOMATA_EF ="modoDeTransicion";
  public static final String NOMBRE_ESTADO_SIGUIENTE_AUTOMATA_EF = "estadoSiguiente";
  public static final String IDENT_NODO_INICIAL_AUTOMATA_EF = "idInicial";
  public static final String IDENT_NODO_INTERMEDIO_AUTOMATA_EF = "idIntermedio";
  public static final String IDENT_NODO_FINAL_AUTOMATA_EF = "idFinal";
  public static final String ACCION_VACIA_AUTOMATA_EF = "vacio";
  public static final String EXPR_REG_SIN_ACCION_EN_AUTOMATA_EF = "(null|vacia|vacio|nada|sinAccion)";
 // public final static String FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE = "/icaro/infraestructura/patronRecursoSimple/TablaEstadosCicloVidaRecursos.xml";
//  public final static String FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE = "./schemas/TablaEstadosCicloVidaRecursos.xml";
  public final static String FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE = "/icaro/infraestructura/entidadesBasicas/componentesBasicos/automatas/automataEFsinAcciones/TablaEstadosCicloVidaEntidades.xml";
  public static  String DESCRIPCION_XML_POR_DEFECTO = "descripcionAcceso.xml";
  public  static  ItfUsoRecursoTrazas RECURSO_TRAZAS_OBJ = null;
  public  static ItfUsoRepositorioInterfaces REPOSITORIO_INTERFACES_OBJ = null;

 // Factoria Agente Cognitivo
  public final static String RUTA_PROCESO_RESOLUCION_COGNITIVO = "/icaro/application/agent/<PAQUETE>/goalResolutionProcess/reglas.drl";
  public final static String RUTA_FACTORIA_COGNITIVO = "icaro.infrastructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.imp";
  public final static String RUTA_FACTORIA_MOTOR_REGLAS = "icaro.infraestructura.PatronAgenteCognitivo.procesadorObjetivos.motorDeReglas.imp";
  public static String RUTA_SRC = "src/";
  public static final String TASK_MANAGER_GLOBAL = "gestorTareas";
  public static final String ITFUSO_RECURSOTRAZAS_GLOBAL = "recursoTrazas";
//  public static final String ITFUSO_RECURSOCONFIGURACION = "configuracion";
  public static final String AGENT_ID_GLOBAL = "agentId";
  public final static String regex_ruta_automata_package = "<PAQUETE>";
  public final static String regex_ruta_automata_behaviour = "<NOMBRE>";
  public final static String DROOLS_Debugging_BEFORE_RuleFired = "BEFORE_ActivationFired";
  public final static String DROOLS_Debugging_AFTER_RuleFired = "AFTER_ActivationFired";
  public final static String PROPERTY_TIME_TIMEOUT_POR_DEFECTO = "timeTimeoutPorDefecto"; 
  // añadir los nombres de cada aplicacion en concreto
  public final static String RUTA_PRUEBAS = "icaro.pruebas";
  public static String RUTA_Carpeta_CodigoFuente_ICARO = "src/";  //cambiar el valor a "src/main/java" si pone el icaro en un proyecto maven
}
