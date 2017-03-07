package icaro.aplicaciones.Rosace.utils;

public class ConstantesRutasEstadisticas {
//	public static String rutassrc = "src/main/java";

	public static String rutassrc = "src/";
	
//	public static String rutaFicheroEvaluacionAgentesXML = "src/main/java/icaro/aplicaciones/agentes/agenteAplicacionrobotMasterIAReactivo/comportamiento/EvaluacionAgentes.xml";

	public static String rutaFicheroEvaluacionAgentesXML = "src/icaro/aplicaciones/agentes/agenteAplicacionrobotMasterIAReactivo/comportamiento/EvaluacionAgentes.xml";

		
	//----------------------------------------------		
	//    Constantes para obtener estadisticas
	//----------------------------------------------
	public static String rutaDirectorioEstadisticas = "estadisticas/";  //DEBE ESTAR CREADO MANUALMENTE EL DIRECTORIO estadisticas AL MISMO NIVEL QUE EL DIRECTORIO src
	
	public static String rutaficheroPlanoEstadisticasLlegadaVictimas = rutaDirectorioEstadisticas + "EstadisticasLlegadaVictimas.txt";
	public static String rutaficheroXMLEstadisticasLlegadaVictimas = rutaDirectorioEstadisticas + "EstadisticasLlegadaVictimas.xml";
	
	public static String rutaficheroPlanoEstadisticasAsignacionVictimas = rutaDirectorioEstadisticas + "EstadisticasAsignacionVictimas.txt";
	public static String rutaficheroXMLEstadisticasAsignacionVictimas = rutaDirectorioEstadisticas + "EstadisticasAsignacionVictimas.xml";

	public static String rutaficheroPlanoEstadisticasNroVictimasEnEntorno = rutaDirectorioEstadisticas + "EstadisticasNroVictimasEnEntorno.txt";
	
	public static String rutaficheroXMLEstadisticasLlegadaYAsignacionVictimas = rutaDirectorioEstadisticas + "EstadisticasLlegadaYAsignacionVictimas.xml";

	public static String rutaficheroTextoPlanoRepartoTareasRobotsYTiempoCompletarlas = rutaDirectorioEstadisticas + "EstadisticaFinalSimulacionAsignacionMisionV2.txt";
	public static String rutaficheroXMLRepartoTareasRobotsYTiempoCompletarlasV1 = rutaDirectorioEstadisticas + "EstadisticaFinalSimulacionAsignacionMisionV1.xml";
	public static String rutaficheroXMLRepartoTareasRobotsYTiempoCompletarlasV2 = rutaDirectorioEstadisticas + "EstadisticaFinalSimulacionAsignacionMisionV2.xml";					
	//Fin constantes para obtener estadisticas

	
	
	
	//Cambiar el valor de la variable rutasFicheroRobots para realizar pruebas con diferentes configuraciones de robots. 
	//Basta con cambiar el Test4Robots.xml por otro fichero xml con una configuración diferente de los robots. 
	//Es decir los robots estan colocados en las posiciones iniciales indicadas en el xml 	
//	public static String rutasFicheroRobots = rutassrc + "utils/Test4Robots.xml";
//	public static String rutasFicheroRobots = rutassrc + "utils/Test1_4Robots.xml";
//        public static String rutasFicheroRobots =rutassrc +"utils/Escenario1_5Robots.xml" ;
	
	
	//Estas variables las definio Paco
//	public static String rutasFicheroRobotsJerarquico  = rutassrc + "utils/TestJerarq_4Robots.xml";
//    public static String rutasFicheroRobotsIgualitario = rutassrc + "utils/Escenario1_5Robots.xml" ;


       //LAS SIGUIENTES DOS LINEAS ERAN PARA CONFIGURAR LA RUTA DEL FICHERO DE ROBOTS. AHORA SE LEEN DE LAS PROPIEDADES GLOBALES DE LA DESCRIPCION DE LA ORGANIZACION                
       //public static String rutasFicheroRobots=rutassrc +"utils/TestJerarq_4Robots.xml";
       //public static String rutasFicheroRobots=rutassrc +"utils/Escenario1_5Robots.xml";
	
	//Cambiar el valor de la variable ficheroVictimasTest para realizar pruebas con diferentes configuraciones de victimas
//	public static String ficheroVictimasTest = "TestSequence.xml";
//	public static String ficheroVictimasTest = "Test1_SequenceVictims.xml";

        
//LAS SIGUIENTES TRES LINEAS ERAN PARA CONFIGURAR LA RUTA DEL FICHERO DE VICTIMAS. AHORA SE LEEN DE LAS PROPIEDADES GLOBALES DE LA DESCRIPCION DE LA ORGANIZACION        
//        public static String ficheroVictimasTest = "Escenario1_15Victims.xml"; // Test1 con empates
//        public static String ficheroVictimasTest = "Escenario1_6Victims.xml"; // Test1 con empates        
        //public static String rutaFicheroVictimasTest = "utils/" + ficheroVictimasTest;

}
