/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp;

import com.thoughtworks.xstream.XStream;
import icaro.aplicaciones.Rosace.informacion.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author FGarijo
 */
public class RecursoPersistenciaEntornosSimulacionImp extends ImplRecursoSimple{
   private ReadXMLTestSequence rXMLTSeq; 
   private NodeList nodeLst;      // estructura en memoria con todos los nodos de								   // las victimas que hay en el fichero xml
   private String rutaFicheroVictimasTest;	
   private String rutaFicheroRobotsTest;
    private int numeroVictimasDiferentesSimulacion = 0; //Numero de victimas diferentes que van a intervenir en el proceso de simulacion
    private Map<String, String> victimsDiferentesXML = new HashMap <String, String>();  //Victimas diferentes que hay en el fichero de secuencia tomado como entrada
    private ArrayList<Victim> victimasDefinidas;    //Victimas que hay en el entorno 
    private ArrayList<RobotStatus> robotsDefinidos;    //Victimas que hay en el entorno 
    protected ComunicacionAgentes comunicacion;
    protected InfoEquipo equipo;
    protected ArrayList identsAgtesEquipo;
    protected String identificadorEquipo;
    private String directorioPersistencia;
    private String identFicheroInfoAsigVictimasObj;
    private String identFicheroInfoAsigVictimasXML;
//    private ObjectOutputStream  oPersAsignVictima;
//    private ObjectInputStream  inPersAsignVictima;
    private  ArrayList<InfoAgteAsignacionVictima> infoVictimasAsigandas  ;
    public RecursoPersistenciaEntornosSimulacionImp (String recursoId) throws RemoteException{
        super (recursoId);
    }
     public  void  inicializarRecursoPersistenciaEntornosSimulacion (){
        try {
            ItfUsoConfiguracion   itfconfig = (ItfUsoConfiguracion) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
               rutaFicheroVictimasTest = itfconfig.getValorPropiedadGlobal(VocabularioRosace.identRutaFicheroEscenarioSimulacion);
               rutaFicheroRobotsTest = itfconfig.getValorPropiedadGlobal(VocabularioRosace.rutaFicheroRobotsTest);
               rXMLTSeq = new ReadXMLTestSequence(rutaFicheroVictimasTest);
               Document doc = this.getDocumentVictimas(rXMLTSeq);
               nodeLst    =   this.getListaNodosVictima(rXMLTSeq, doc);
               victimasDefinidas = new ArrayList ();
               if ((rXMLTSeq!=null) && (nodeLst!=null))
                                numeroVictimasDiferentesSimulacion = rXMLTSeq.getNumberOfVictimsInSequence(nodeLst);
                                // se obtienen tambien el conjunto de vicitmas definidas
           //    String nombreFicheroAsignVictim = "asigVictimasObjetos";
                 directorioPersistencia = VocabularioRosace.NombreDirectorioPersistenciaResSimulacion+File.separator;
                identFicheroInfoAsigVictimasObj = directorioPersistencia+VocabularioRosace.NombreFicheroSerieInfoAsignacionVictimas+".tmp";
                identFicheroInfoAsigVictimasXML = directorioPersistencia+VocabularioRosace.NombreFicheroSerieInfoAsignacionVictimas+".xml";
                File dirFicherosPersistencia = new File(directorioPersistencia);
                if(!dirFicherosPersistencia.exists())dirFicherosPersistencia.mkdir();
                infoVictimasAsigandas = new ArrayList<InfoAgteAsignacionVictima>();
                robotsDefinidos = getTeamRobotStatus();
        } catch (Exception ex) {
            Logger.getLogger(RecursoPersistenciaEntornosSimulacionImp.class.getName()).log(Level.SEVERE, null, ex);
 //           return null;
        }
     }
    
    public ReadXMLTestSequence getSecuenciaVictimasClase(String rutaFicheroVictimasTest) throws Exception{    	
    	ReadXMLTestSequence rXMLTSeq;    	    	
    	rXMLTSeq = new ReadXMLTestSequence(rutaFicheroVictimasTest);    	
    	return rXMLTSeq;
    }
   
    public Document getDocumentVictimas(ReadXMLTestSequence rXMLTSeq) throws Exception{        
    	if (rXMLTSeq!=null)    	
    	    return rXMLTSeq.getDocument(rXMLTSeq.gettestFilePaht());
    	else    
    	    return null;
    }
    
   
    public NodeList getListaNodosVictima(ReadXMLTestSequence rXMLTSeq, Document doc) throws Exception{    	
    	if ((rXMLTSeq!=null) && (doc!=null)) 
    	   return rXMLTSeq.getVictimsXMLStructure(doc, "victim"); // Obtain all the victims   	
    	else return null;
    }
        
   
     public int getNumeroTotalVictimasEnLaSecuencia(){
        
    	     return numeroVictimasDiferentesSimulacion;
     }
    
    public void setNumeroVictimasDiferentesSimulacion() throws Exception{
    	Document doc = rXMLTSeq.getDocument(rXMLTSeq.gettestFilePaht());
    	NodeList nodeLst = rXMLTSeq.getVictimsXMLStructure(doc, "victim");  		//Obtain all the victims
    	int numItemsVictim = rXMLTSeq.getNumberOfVictimsInSequence(nodeLst); //El numero de items Victim que hay en el xml
    	Victim victima;
        for (int i=0; i<numItemsVictim; i++){
    		Element info = rXMLTSeq.getVictimElement(nodeLst, i);   //El nodo de la primera victima es 0
    		String valueid = rXMLTSeq.getVictimIDValue(info,"id");  //Obtener el id de la victima
    		            //Si esta repetida no se hara nada 
                 if ( valueid != null && !victimsDiferentesXML.containsKey(valueid)){
                     victimsDiferentesXML.put(valueid, valueid); 
                     victima = createNewVictim( rXMLTSeq, nodeLst, i);
                     victimasDefinidas.add(victima);
                 }
    	}
    	this.numeroVictimasDiferentesSimulacion = victimsDiferentesXML.size();    	
    };
    
    public int getNumeroVictimasDiferentesSimulacion() throws Exception{
    	return this.numeroVictimasDiferentesSimulacion;
    }
    
     public ArrayList<Victim>  getVictimsArescatar (){
         
        return victimasDefinidas;      
     }
    
    private Victim createNewVictim(ReadXMLTestSequence rXMLTSeq,NodeList nodeLst, int numeroVictima) {
	//	int posicionVictimaNodoLst = numeroVictima - 1; // El nodo de la victima

		Element info = rXMLTSeq.getVictimElement(nodeLst,numeroVictima);
		String valueid = rXMLTSeq.getVictimIDValue(info, "id");
		int valueseverity = rXMLTSeq.getVictimSeverity(info, "severity");
		List<Integer> victimRequirements = new ArrayList<Integer>();
		victimRequirements = rXMLTSeq.getVictimRequirements(info);                    
		Coordinate valueCoordinate = rXMLTSeq.getVictimCoordinate(info);
		return new Victim(valueid, valueCoordinate, valueseverity, victimRequirements);  
	}
                              	
    //obtener valor en segundos con nrodecimales decimales a partir de long que expresa el tiempo en milisegundos
	private double ObtenerTiempoEnSegundosUnDecimal(long tiempoEnMilisegundos, int nrodecimales){
//		  int numtiempoEnMiliSegundos = Integer.parseInt(strMilisegundos);		  
		  double numtiempoEnSegundos = ((double)tiempoEnMilisegundos / 1000);		  		  
		  String strnumtiempoEnSegundos = "" + numtiempoEnSegundos;		  		  
		  int posPunto = strnumtiempoEnSegundos.indexOf('.');
		  String strnumtiempoEnSegundosUnDecimal = strnumtiempoEnSegundos.substring(0, posPunto+1+nrodecimales);
		  return Double.parseDouble(strnumtiempoEnSegundosUnDecimal);
//		  return strnumtiempoEnSegundosUnDecimal;
	}

public void guardarInfoCasoSimulacion(InfoCasoSimulacion infoCaso) throws Exception {
 // se guarda la serie asignacion de victimas. Las series graficas se guardan por orden del agente 
    } 
 public void guardarInfoAsignacionVictima (InfoAgteAsignacionVictima infoAsignVictima)throws Exception{
    
     infoVictimasAsigandas.add(infoAsignVictima);
     FileOutputStream fInfoAsignVictima = new FileOutputStream(identFicheroInfoAsigVictimasObj);
     ObjectOutputStream  oPersAsignVictima = new ObjectOutputStream (fInfoAsignVictima);
     oPersAsignVictima.writeObject(infoVictimasAsigandas);
     oPersAsignVictima.close();
    
     
 }
         
  public ArrayList<InfoAgteAsignacionVictima> obtenerInfoAsignacionVictimas ()throws Exception{
   //   String nombreFicheroAsignVictim = "pruebaAsigVictimas";
      if (infoVictimasAsigandas.isEmpty())return null;
                String directorioPersistencia = VocabularioRosace.NombreDirectorioPersistenciaResSimulacion+File.separator;
   //             String identFichero = directorioPersistencia+nombreFicheroAsignVictim;
                File ficheroPersistencia = new File(identFicheroInfoAsigVictimasObj);
                if(ficheroPersistencia.exists()){
    
                FileInputStream fInInfoAsignVictima = new FileInputStream(identFicheroInfoAsigVictimasObj);
                ObjectInputStream   inPersAsignVictima = new ObjectInputStream (fInInfoAsignVictima);
 //               ArrayList<InfoAsignacionVictima> infoAsignVictimas = new ArrayList<InfoAsignacionVictima>();
 //               infoAsignVictimas = 
                return (ArrayList<InfoAgteAsignacionVictima>)inPersAsignVictima.readObject();
                }
                else return null;
  } 
  
  public boolean guardarSerieResultadosXML(InfoSerieResultadosSimulacion serieResultados){
      // Se guarda en un fichero xml
      
     try {
      String identFicheroSerie=directorioPersistencia+ serieResultados.getTituloSerie()+".xml";
 //     FileOutputStream fserieResultados = new FileOutputStream(identFicheroSerie);
      Path path = FileSystems.getDefault().getPath(".", identFicheroSerie);
      OutputStream in = Files.newOutputStream(path);
      BufferedWriter escritorXML = Files.newBufferedWriter(FileSystems.getDefault().getPath(".", identFicheroSerie),
                        Charset.forName("US-ASCII"),
                        StandardOpenOption.CREATE);
 //     PrintWriter xmlWriter = new PrintWriter(new FileOutputStream(identFicheroSerie));
//      Writer buffer = new StringWriter();
    
      XStream xstream = new XStream(); 
      xstream.alias(serieResultados.getTituloSerie(), InfoSerieResultadosSimulacion.class);
      xstream.alias("puntoAsignacionVictima", PuntoEstadistica.class);
      escritorXML.write(xstream.toXML(serieResultados));
//      xmlWriter.print(xstream.toXML(serieResultados));
//      xmlWriter.close();
       System.out.println(xstream.toXML(serieResultados));
       escritorXML.close();
       return true ;
    }catch (Exception e) {
        e.printStackTrace();
         return false;
		} 
  }
public String getRutaEscenarioSimulacion()throws Exception{
    return  rutaFicheroVictimasTest;
}
RobotStatus getRobotStatus ( String robotId)throws Exception{
//    ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotsTest);
//    	Document doc = rXMLTRobots.getDocument(rXMLTRobots.gettestFilePaht());
//		//Obtain all the robots
//		NodeList nodeLst = rXMLTRobots.getRobotsXMLStructure(doc, "robot");
//		
		int numeroRobotsSimulation = robotsDefinidos.size();
		int j=0; boolean encontrado = false;
		while( j<numeroRobotsSimulation&& !encontrado){
  		    //Obtain info about robot located at the test        	
//        	Element info = rXMLTRobots.getRobotElement(nodeLst, j);			        	
//                String	valueid = rXMLTRobots.getRobotIDValue(info, "id");
                  RobotStatus robot = robotsDefinidos.get(j);
                  if (robot.getIdRobot().equals(robotId)) return robot ;       		
                }
                return null;
}
public ArrayList<RobotStatus> getTeamRobotStatus ( )throws Exception{
            ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotsTest);
            Document doc = rXMLTRobots.getDocument(rXMLTRobots.gettestFilePaht());
		//Obtain all the robots
		NodeList nodeLst = rXMLTRobots.getRobotsXMLStructure(doc, "robot");
		ArrayList<RobotStatus> robotsDefinidos = new ArrayList();
		int numeroRobotsSimulation = rXMLTRobots.getNumberOfRobots(nodeLst);
		for( int j=0;j<numeroRobotsSimulation; j++){
  		    //Obtain info about robot located at the test 
                RobotStatus robotStatus = new RobotStatus();
                 
        	Element info = rXMLTRobots.getRobotElement(nodeLst, j);			        	
//                String	valueid = rXMLTRobots.getRobotIDValue(info, "id");
                robotStatus.setIdRobot(rXMLTRobots.getRobotIDValue(info, "id"));
        	       		
        	   int energy = rXMLTRobots.getRobotInitialEnergy(info, "initialenergy");
        	   Coordinate initialCoordinate = rXMLTRobots.getRobotCoordinate(info);
        	   float healRange = rXMLTRobots.getRobotHealRange(info, "healrange");	        	           	   
        	   robotStatus.setAvailableEnergy(energy);        	   
        	   robotStatus.setRobotCoordinate(initialCoordinate);        	   
        	   robotStatus.setHealRange(healRange); 
                  robotsDefinidos.add(robotStatus);
                }
                return robotsDefinidos;
}

}
