package icaro.aplicaciones.recursos.recursoEstadistica;

import java.util.ArrayList;

import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.utils.ReadXMLTestSequence;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoRecursoEstadistica extends ItfUsoRecursoSimple {
	
   public void setNumeroVictimasDiferentesSimulacion(ReadXMLTestSequence rXMLTSeq) throws Exception;
	public int getNumeroVictimasDiferentesSimulacion() throws Exception;
		
    public void setTiempoinicial(long t) throws Exception;   
    public long getTiempoinicial() throws Exception;
        
    public void crearFicheroXMLTRealLlegadaVictimas(String name) throws Exception;    
    public void cerrarFicheroXMLTRealLlegadaVictimas() throws Exception;
    
    public void crearFicheroTextoPlanoTRealLlegadaVictimas(String name) throws Exception;    
    public void cerrarFicheroTextoPlanoTRealLlegadaVictimas() throws Exception;
    
    public void crearFicheroXMLTRealAsignacionVictimasRobots(String name) throws Exception;
    public void cerrarFicheroXMLTRealAsignacionVictimasRobots() throws Exception;
    
    public void crearFicheroTextoPlanoTRealAsignacionVictimasRobots(String name) throws Exception;
    public void cerrarFicheroTextoPlanoTRealAsignacionVictimasRobots() throws Exception;

    public void crearFicherosRepartoTareasRobotsYTiempoCompletarlas(String filenameXMLV1, String filenameXMLV2, String filenameTXT) throws Exception;
    public void cerrarFicherosRepartoTareasRobotsYTiempoCompletarlas() throws Exception;
        
    public void escribeEstadisticaFicheroXMLTRealLlegadaVictimas(long tiempoActual, Victim victima) throws Exception;
    public void escribeEstadisticaFicheroTextoPlanoTRealLlegadaVictimas(long tiempoActual, Victim victima) throws Exception; 

    //public void escribeEstadisticaFicheroXMLTRealAsignacionVictimasRobots(long tiempoActual, String victima, String robot) throws Exception;
    public void escribeEstadisticaFicheroTextoPlanoTRealAsignacionVictimasRobots(long tiempoActual, String victima, String robot, int evaluacionRobot) throws Exception;

    public void escribeEstadisticaFicherosRepartoTareasRobotsYTiempoCompletarlas(String robot, ArrayList idsVictimasFinalesAsignadas, double tiempoTotalAtenderTodasLasVictimasAsignadas) throws Exception;
        
}
