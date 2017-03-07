package icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.ItfUsoRecursoPersistenciaEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.EscenarioSimulacionRobtsVictms;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

//Other imports used by this Resource
//#start_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS
 
//#end_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS


    //Methods that implement PersistenciaEntornosSimulacion resource use interface
    
    
    
public  class ClaseGeneradoraRecursoPersistenciaEntornosSimulacion extends ImplRecursoSimple implements ItfUsoRecursoPersistenciaEntornosSimulacion {

//    private ItfUsoRecursoTrazas trazas; //Se inicializa en el constructor con la referencia al recurso de trazas. Asi ya estara disponible en cualquier metodo.
   private String idRecurso;  //Se inicializara en el constructor con el identificador, dado a la instancia del recurso, en la descripcion de la organizacion
   private ReadXMLTestSequence rXMLTSeq; 
   private NodeList nodeLst;      // estructura en memoria con todos los nodos de								   // las victimas que hay en el fichero xml
   private String rutaFicheroVictimasTest;	
   private String rutaFicheroRobotsTest; 
   private Document doc;
   private RecursoPersistenciaEntornosSimulacionImp1 impRecPersistenciaXML ;
   private Map<String, String> victimsDiferentesXML = new HashMap <String, String>();  //Victimas diferentes que hay en el fichero de secuencia tomado como entrada
   private int numeroVictimasDiferentesSimulacion; //Numero de victimas diferentes que van a intervenir en el proceso de simulacion
   private ArrayList<Victim> victimasDefinidas; 
   private RobotStatus statusRobot;
   public ClaseGeneradoraRecursoPersistenciaEntornosSimulacion(String idrecurso) throws Exception {
            super(idrecurso);
            idRecurso=idrecurso;
             try {
           impRecPersistenciaXML = new RecursoPersistenciaEntornosSimulacionImp1(idrecurso);
           impRecPersistenciaXML.inicializarRecursoPersistenciaEntornosSimulacion();
//           impPersistenciaXML.setNumeroVictimasDiferentesSimulacion();
           
        } catch (Exception ex) {
            Logger.getLogger(RecursoPersistenciaEntornosSimulacionImp.class.getName()).log(Level.SEVERE, null, ex);
            
        }
     }
  
        
//    @Override
    
     public int getNumeroTotalVictimasEnLaSecuencia(){
         return this.impRecPersistenciaXML.getNumeroTotalVictimasEnLaSecuencia();
     }
    
 
    @Override
    public ArrayList<Victim> getVictimasArescatar ()throws Exception{
        if (victimasDefinidas == null)
        victimasDefinidas = this.impRecPersistenciaXML.getVictimsArescatar ();
        return victimasDefinidas;
        
    }
    @Override
    public void termina() {
        trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso, "Terminando recurso" + this.id + " ....",	InfoTraza.NivelTraza.debug));
 	    
        //Si es un recurso de visualizacion es necesaria una llamar a dispose de la ventana de visualizacion. Algo parecido a lo siguiente	
        //this.jvariableLocalReferenciaVisualizador.dispose(); //Destruye los componentes utilizados por este JFrame y devuelve la memoria utilizada al Sistema Operativo 	 
		
        super.termina();
    }

    @Override
    public void guardarInfoCasoSimulacion(InfoCasoSimulacion infoCaso) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet.");
        trazas.trazar(this.idRecurso, "Guardo InfoCasoSimulacion " + infoCaso.getidentCaso() + " ....",InfoTraza.NivelTraza.debug	);
        this.impRecPersistenciaXML.guardarInfoCasoSimulacion(infoCaso);
    }

    @Override
    public InfoCasoSimulacion obtenerInfoCasoSimulacion(String identFicheroCaso) throws Exception {
       return this.impRecPersistenciaXML.obtenerInfoCasoSimulacion(identFicheroCaso);
    }

    @Override
    public void guardarInfoAsignacionVictima(InfoAgteAsignacionVictima infoAsignVictima) throws Exception {
        this.impRecPersistenciaXML.guardarInfoAsignacionVictima(infoAsignVictima);
    }

    @Override
    public ArrayList<InfoAgteAsignacionVictima> obtenerInfoAsignacionVictimas() throws Exception {
       return  this.impRecPersistenciaXML.obtenerInfoAsignacionVictimas();
    }

    @Override
    public void finSimulacion() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public boolean guardarSerieResultadosSimulacion(InfoSerieResultadosSimulacion infoSerieResultados)throws Exception{
       return  this.impRecPersistenciaXML.guardarSerieResultadosXML(infoSerieResultados);
    }
    @Override
    public String getIdentEscenarioSimulacion()throws Exception{
    return  impRecPersistenciaXML.getRutaEscenarioSimulacion();
}
    @Override
    public RobotStatus getRobotStatus ( String robotId)throws Exception{
        return impRecPersistenciaXML.getRobotStatus (  robotId);
        
    }

    @Override
    public EscenarioSimulacionRobtsVictms obtenerInfoEscenarioSimulacion(String identFicheroEscenario) throws Exception {
      return this.impRecPersistenciaXML.obtenerInfoEscenarioSimulacion(identFicheroEscenario);
    }

    @Override
    public boolean eliminarEscenarioSimulacion(String rutaFicheroInfoPersistencia) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean guardarInfoEscenarioSimulacion( EscenarioSimulacionRobtsVictms escenario) throws Exception {
        return impRecPersistenciaXML.guardarInfoEscenarioSimulacion(escenario);
    }

    @Override
    public HashSet obtenerIdentsEscenarioSimulacion() throws Exception {
        
       return impRecPersistenciaXML.obtenerIdentsEscenarioSimulacion(VocabularioRosace.identDirectorioPersistenciaEscenarios);
    }
}
            

