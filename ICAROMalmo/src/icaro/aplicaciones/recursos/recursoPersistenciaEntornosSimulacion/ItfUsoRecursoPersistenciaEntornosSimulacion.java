package icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion;

import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.EscenarioSimulacionRobtsVictms;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import java.util.ArrayList;
import java.util.HashSet;

//Other imports used by this Resource
//#start_nodeuseItfSpecialImports:useItfSpecialImports <--useItfSpecialImports-- DO NOT REMOVE THIS
 
//#end_nodeuseItfSpecialImports:useItfSpecialImports <--useItfSpecialImports-- DO NOT REMOVE THIS


   public interface ItfUsoRecursoPersistenciaEntornosSimulacion extends ItfUsoRecursoSimple {

//    public ReadXMLTestSequence getSecuenciaVictimasClase(String rutaFicheroVictimasTest) throws Exception;  
//   public Document getDocumentVictimas(ReadXMLTestSequence rXMLTSeq) throws Exception;    
//    public NodeList getListaNodosVictima(ReadXMLTestSequence rXMLTSeq, Document doc) throws Exception;
       //los dos metodos siguientes se copiaron del recurso de estadisticas
//    public void setNumeroVictimasDiferentesSimulacion(ReadXMLTestSequence rXMLTSeq) throws Exception;       
//    public int getNumeroVictimasDiferentesSimulacion() throws Exception;
 //      public int getNumeroTotalVictimasEnLaSecuencia();       
//    public void setTiempoinicial(long t) throws Exception;       //
//    public long getTiempoinicial() throws Exception;             //NO USADO DESDE FUERA
            
    ////Metodos para gestionar el fichero EstadisticasLlegadaVictimas.xml (crear, cerrar, escribir, leerlo)
    ////----------------------------------------------------------------------------------------------------
 //   public void crearFicheroXMLTRealLlegadaVictimas(String fileName, String tipoSimulacion, int numeroRobotsSimulacion, int numeroVictimasSimulacion, int intervaloFrecuenciaEnvioVictimas) throws Exception;   
 //   public void cerrarFicheroXMLTRealLlegadaVictimas() throws Exception;             
 //   public void escribeEstadisticaFicheroXMLTRealLlegadaVictimas(long tiempoActual, Victim victima) throws Exception;    
 //   public ArrayList<PuntoEstadistica> leerFicheroXMLTRealLlegadaVictimasPuntos(String rutaFichero) throws Exception;
    
    //Metodos para gestionar el fichero EstadisticasAsignacionVictimas.xml (crear, cerrar, escribir, leerlo)
    ////----------------------------------------------------------------------------------------------------
 //   public void crearFicheroXMLTRealAsignacionVictimasRobots(String fileName, String tipoSimulacion, int numeroRobotsSimulacion, int numeroVictimasSimulacion, int intervaloFrecuenciaEnvioVictimas) throws Exception;
 //   public void cerrarFicheroXMLTRealAsignacionVictimasRobots() throws Exception;
//    public void escribeEstadisticaFicheroXMLTRealAsignacionVictimasRobots(long tiempoActual, String victima, String robot, 
//                                                                          int evaluacionRobot, int NroVictimasAsignadas, int NroVictimasEnEntorno) throws Exception;
//    public ArrayList<PuntoEstadistica> leerFicheroXMLTRealAsignacionVictimasRobotsPuntos(String rutaFichero) throws Exception;     
    //Metodos para gestionar el fichero EstadisticasLlegadaYAsignacionVictimas.xml (el metodo genera es responsable de crearlo, escribelo y cerrarlo)
    ////Este fichero se obtiene a partir de la informacion almacenada en los ficheros EstadisticasLlegadaVictimas.xml y EstadisticasAsignacionVictimas.xml
    ////----------------------------------------------------------------------------------------------------
 //   public void generaFicheroXMLEstadisticasLlegadaYAsignacionVictimas(String tipoSimulacion, int numeroRobotsSimulacion, int numeroVictimasSimulacion, int intervaloFrecuenciaEnvioVictimas) throws Exception;
    //Metodos para gestionar el fichero EstIntLlegadaYAsignacionVictimsFECHA.xml
    ////Este fichero se obtiene a partir de la informacion almacenada en el fichero EstadisticasLlegadaYAsignacionVictimas.xml
    ////----------------------------------------------------------------------------------------------------
 //   public void generaFicheroXMLEstadisticasIntLlegadaYAsignacionVictims(String tipoSimulacion, int numeroRobotsSimulacion, int numeroVictimasSimulacion, int intervaloFrecuenciaEnvioVictimas) throws Exception;
    //Metodos para gestionar el fichero EstadisticaFinalSimulacionAsignacionMisionV2.xml (crear, cerrar, escribir)
    //En este fichero se resume que victimas han sido asignadas a cada robot, el coste final asociado, .
    ////----------------------------------------------------------------------------------------------------
//    public void crearFicherosRepartoTareasRobotsYTiempoCompletarlas(String filenameXMLV2, String tipoSimulacion, int numeroRobotsSimulacion, int numeroVictimasSimulacion, int intervaloFrecuenciaEnvioVictimas) throws Exception;
//    public void cerrarFicherosRepartoTareasRobotsYTiempoCompletarlas() throws Exception;        
//    public void escribeEstadisticaFicherosRepartoTareasRobotsYTiempoCompletarlas(String robot, ArrayList idsVictimasFinalesAsignadas, double tiempoTotalAtenderTodasLasVictimasAsignadas) throws Exception;     //  
    public ArrayList<Victim> getVictimasArescatar ()throws Exception;
    public void guardarInfoCasoSimulacion (InfoCasoSimulacion infoCaso)throws Exception;
    public InfoCasoSimulacion obtenerInfoCasoSimulacion (String idCaso)throws Exception;
    public void guardarInfoAsignacionVictima (InfoAgteAsignacionVictima infoAsignVictima)throws Exception;
    public ArrayList<InfoAgteAsignacionVictima> obtenerInfoAsignacionVictimas ()throws Exception;
    public boolean guardarSerieResultadosSimulacion(InfoSerieResultadosSimulacion infoSerieResultados)throws Exception;
    public void finSimulacion()throws Exception;
    public String getIdentEscenarioSimulacion()throws Exception;
    public RobotStatus getRobotStatus ( String robotId)throws Exception;
    public EscenarioSimulacionRobtsVictms obtenerInfoEscenarioSimulacion(String rutaFicheroEscenario)throws Exception;
    public boolean  eliminarEscenarioSimulacion(String rutaFicheroInfoPersistencia)throws Exception;
    public boolean guardarInfoEscenarioSimulacion(EscenarioSimulacionRobtsVictms escenario)throws Exception;
    public HashSet obtenerIdentsEscenarioSimulacion()throws Exception;
}