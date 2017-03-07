/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp;

import com.thoughtworks.xstream.XStream;
import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.EscenarioSimulacionRobtsVictms;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author FGarijo
 */
public class RecursoPersistenciaEntornosSimulacionImp1 extends ImplRecursoSimple{
   private ReadXMLTestSequence rXMLTSeq; 
   private NodeList nodeLst;      // estructura en memoria con todos los nodos de								   // las victimas que hay en el fichero xml
   private String rutaFicheroVictimasTest;	
   private String rutaFicheroRobotsTest;
   private String rutaFicherosPersistencia;
    private int numeroVictimasDiferentesSimulacion = 0; //Numero de victimas diferentes que van a intervenir en el proceso de simulacion
    private Map<String, String> victimsDiferentesXML = new HashMap <String, String>();  //Victimas diferentes que hay en el fichero de secuencia tomado como entrada
    private ArrayList<Victim> victimasDefinidas;    //Victimas que hay en el entorno 
    private ArrayList<RobotStatus> robotsDefinidos;    //Victimas que hay en el entorno 
    private File[] ficherosEscenarios; 
    private File[] ficherosResultadosSimulacion;
    private File directorioFicherosPersistenciaEscenarios;
    private File directorioResultadosSimulacion;
    HashSet identsEscenarioSimulacion ;
    HashSet identsResultadosSimulacion;
    protected ComunicacionAgentes comunicacion;
    protected InfoEquipo equipo;
    protected ArrayList identsAgtesEquipo;
    protected String identificadorEquipo;
    private String directorioPersistencia;
    private String identFicheroInfoAsigVictimasObj;
    private String extFicheroXML= ".xml";
//    private ObjectOutputStream  oPersAsignVictima;
//    private ObjectInputStream  inPersAsignVictima;
    private  ArrayList<InfoAgteAsignacionVictima> infoVictimasAsigandas  ;
    public RecursoPersistenciaEntornosSimulacionImp1 (String recursoId) throws RemoteException{
        super (recursoId);
    }
     public  void  inicializarRecursoPersistenciaEntornosSimulacion (){
        try {
//            ItfUsoConfiguracion   itfconfig = (ItfUsoConfiguracion) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
//               rutaFicheroVictimasTest = itfconfig.getValorPropiedadGlobal(VocabularioRosace.identRutaFicheroEscenarioSimulacion);
//               rutaFicheroRobotsTest = itfconfig.getValorPropiedadGlobal(VocabularioRosace.rutaFicheroRobotsTest);
//               rXMLTSeq = new ReadXMLTestSequence(rutaFicheroVictimasTest);
//               Document doc = this.getDocumentVictimas(rXMLTSeq);
//               nodeLst    =   this.getListaNodosVictima(rXMLTSeq, doc);
               victimasDefinidas = new ArrayList ();
//               if ((rXMLTSeq!=null) && (nodeLst!=null))
//                                numeroVictimasDiferentesSimulacion = rXMLTSeq.getNumberOfVictimsInSequence(nodeLst);
//                                // se obtienen tambien el conjunto de vicitmas definidas
//           //    String nombreFicheroAsignVictim = "asigVictimasObjetos";
//                directorioPersistencia = VocabularioRosace.NombreDirectorioPersistenciaEscenarios;
                identFicheroInfoAsigVictimasObj = VocabularioRosace.NombreFicheroSerieInfoAsignacionVictimas+".tmp";
                directorioFicherosPersistenciaEscenarios = new File(VocabularioRosace.NombreDirectorioPersistenciaEscenarios);
                directorioResultadosSimulacion= new File(VocabularioRosace.NombreDirectorioPersistenciaResSimulacion);
                if(!directorioFicherosPersistenciaEscenarios.exists())directorioFicherosPersistenciaEscenarios.mkdir();
                 if(!directorioResultadosSimulacion.exists())directorioResultadosSimulacion.mkdir();
//                rutaFicherosPersistencia = directorioFicherosPersistenciaEscenarios.getAbsolutePath();
                
                ficherosEscenarios = directorioFicherosPersistenciaEscenarios.listFiles();
                ficherosResultadosSimulacion = directorioResultadosSimulacion.listFiles();
                infoVictimasAsigandas = new ArrayList<InfoAgteAsignacionVictima>();
                identsEscenarioSimulacion = new HashSet<String>();
                identsResultadosSimulacion = new HashSet<String>();
                // pruebas 
//            File f =    obtenerFicheroEscenario("modeloOrg_JerarquicoNumRobts_5NumVicts_5");
//            HashSet set = obtenerIdentsEscenarioSimulacion(directorioPersistencia);
//            EscenarioSimulacionRobtsVictms escenario = obtenerInfoEscenarioSimulacion("modeloOrg_JerarquicoNumRobts_5NumVicts_5");
//            this.guardarInfoEscenarioSimulacion(escenario);
//                robotsDefinidos = getTeamRobotStatus();
        } catch (Exception ex) {
            Logger.getLogger(RecursoPersistenciaEntornosSimulacionImp1.class.getName()).log(Level.SEVERE, null, ex);
 //           return null;
        }
     }
    public boolean guardarInfoEscenarioSimulacion(EscenarioSimulacionRobtsVictms escenario){
         Serializer serializer = new Persister();
//         String identFichero = VocabularioRosace.rutaPersistenciaEscenarios+File.separator+escenario.getIdentEscenario()+".xml";
         try {
             String identFichero = escenario.getIdentEscenario()+extFicheroXML;
             
//             File ficheroEscenario = this.obtenerFicheroEscenario(identFichero);
             if (identsEscenarioSimulacion.contains(identFichero)){// Se renombra el fichero agnadiendo 
                 identFichero = this.renombrarFichero(identFichero);
             }
             identFichero = directorioFicherosPersistenciaEscenarios+File.separator+identFichero;
             File ficheroEscenario = new File (identFichero);
             serializer.write(escenario, ficheroEscenario);
             ArrayList<String> robotNombres = escenario.getListIdentsRobots();
             for (String ideRobot:robotNombres){
//                 String ideRobot = (String)robtIter.next();
              RobotStatus1 infoRobot = (RobotStatus1) escenario.getRobotInfo(ideRobot);
             
              List<RobotCapability> capacidades=infoRobot.getRobotCapabilities();
                 System.out.println(" Desde persistencia Lista de capacidades a guardar del robot  : " + ideRobot+"Capacidades : "+ capacidades.toString() );
             }
              System.out.println("Desde peticion Guardar Numero de Robots  : " + escenario.getNumRobots()+" Numero de victimas : "+ escenario.getNumVictimas());

//          serializer.write(escenario, new File(rutaFicheroInfoPersistencia+identFichero+".xml"));
          
          System.out.println("En el fichero   : "+ identFichero);
          System.out.println("Se va a guardar  : "+ escenario.toString() );
          return true;
          
        
        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
            return false;
        }    
     }
     
     public EscenarioSimulacionRobtsVictms obtenerInfoEscenarioSimulacion(String identFicheroInfoPersistencia){
         try {
              File ficheroEscenario = this.obtenerFicheroEscenario(identFicheroInfoPersistencia);
             
                if(ficheroEscenario== null){
//                    dirFicherosPersistencia.mkdir();
//                    rutaFicheroInfoPersistencia= dirFicherosPersistencia.getAbsolutePath()+"\\";
                System.out.println("El fichero   : "+ identFicheroInfoPersistencia+ " No existe " );
             
                }else {
                   Serializer serializer = new Persister();
//                 EscenarioSimulacionRobtsVictms escenario = serializer.read(EscenarioSimulacionRobtsVictms.class,ficheroEscenario, false);
                return serializer.read(EscenarioSimulacionRobtsVictms.class,ficheroEscenario, false);
//                        serializer.read(EscenarioSimulacionRobtsVictms.class,ficheroEscenario, false);
                    
                }   
                }catch (Exception e) { // catches ANY exception
        e.printStackTrace();
        }
         return null;
     }
 public   InfoCasoSimulacion obtenerInfoCasoSimulacion(String identFicheroCaso) {
       try {
              File ficheroEscenario = this.obtenerFicheroCasoSimulacion(identFicheroCaso);
             
                if(ficheroEscenario== null){
//                    dirFicherosPersistencia.mkdir();
//                    rutaFicheroInfoPersistencia= dirFicherosPersistencia.getAbsolutePath()+"\\";
                System.out.println("El fichero   : "+ identFicheroCaso+ " No existe " );
             
                }else {
                   Serializer serializer = new Persister();
//                 EscenarioSimulacionRobtsVictms escenario = serializer.read(EscenarioSimulacionRobtsVictms.class,ficheroEscenario, false);
                return serializer.read(InfoCasoSimulacion.class,ficheroEscenario, false);

                    
                }   
                }catch (Exception e) { // catches ANY exception
        e.printStackTrace();
    }
         return null;
     }
   
     
      public boolean  eliminarEscenarioSimulacion(String identFicheroInfoPersistencia){
         try {
              File ficheroEscenario = this.obtenerFicheroEscenario(identFicheroInfoPersistencia);
             
                if(ficheroEscenario== null){
//                    dirFicherosPersistencia.mkdir();
//                    rutaFicheroInfoPersistencia= dirFicherosPersistencia.getAbsolutePath()+"\\";
                System.out.println("El fichero   : "+ identFicheroInfoPersistencia+ " No existe " );
                    
                }else{
                    //confirmar eliminacion y si lo confirma eliminar
                    identsEscenarioSimulacion.remove(ficheroEscenario.getName());
                    ficheroEscenario.delete();
                    ficherosEscenarios = directorioFicherosPersistenciaEscenarios.listFiles();
                    return true;
                }
         }catch (Exception e) { // catches ANY exception
                e.printStackTrace();
        }  
         return false;
     }
     public HashSet obtenerIdentsEscenarioSimulacion(String identDirectorioInfoPersistencia){
         
        if(!identDirectorioInfoPersistencia.equals(VocabularioRosace.NombreDirectorioPersistenciaEscenarios)){
           System.out.println(" El directorio especificado  : " +identDirectorioInfoPersistencia + " No es valido ");
           return null;
        }
         if (identsEscenarioSimulacion.isEmpty()){
            try {
              if(ficherosEscenarios == null) return null;
                String identFichero;
                for (int i=0;i<ficherosEscenarios.length;i++){
                     identFichero = ficherosEscenarios[i].getName();
                    System.out.println(" Fichero obtenido : " +identFichero);
                    identsEscenarioSimulacion.add(identFichero);
                    } 
            }catch (Exception e) { // catches ANY exception
                e.printStackTrace();
            } 
        }
    return identsEscenarioSimulacion;
}
     public HashSet obtenerIdentsFicherosResultadosSimulador(String identDirectorioResultados){
          if(!identDirectorioResultados.equals(VocabularioRosace.NombreDirectorioPersistenciaResSimulacion)){
           System.out.println(" El directorio especificado  : " +identDirectorioResultados + " No es valido ");
           return null;
        }
        if (identsResultadosSimulacion.isEmpty()){
            try {
              if(ficherosResultadosSimulacion == null) return null;
                String identFichero;
                for (int i=0;i<ficherosResultadosSimulacion.length;i++){
                     identFichero = ficherosResultadosSimulacion[i].getName();
                    System.out.println(" Fichero obtenido : " +identFichero);
                    identsResultadosSimulacion.add(identFichero);
                    } 
            }catch (Exception e) { // catches ANY exception
                e.printStackTrace();
            } 
        }
    return identsResultadosSimulacion;
}
     private String renombrarFichero( String identfichero){
         String idFicheroSinXml = identfichero.replace(extFicheroXML, "");
         int indiceRepeticion=0;
         for (int i=0;i<ficherosEscenarios.length;i++){
          if (ficherosEscenarios[i].getName().contains(idFicheroSinXml))indiceRepeticion++;   
         }
         return idFicheroSinXml +"_"+indiceRepeticion+extFicheroXML;
     }
     private File obtenerFicheroEscenario (String identEscenario){
         Boolean encontrado = false ;
         if(!identEscenario.endsWith(extFicheroXML))identEscenario=identEscenario+extFicheroXML;
         try {
              if(ficherosEscenarios == null) return null;
                String identFichero;
                 int i=0;
                while (i<ficherosEscenarios.length&&!encontrado){
                     identFichero = ficherosEscenarios[i].getName();
                     if (identFichero.equals(identEscenario))encontrado = true;
                     else i++;
                    System.out.println(" Fichero obtenido : " +identFichero);
                    }
                if (encontrado) return ficherosEscenarios[i];
                else return null;
     }catch (Exception e) { // catches ANY exception
        e.printStackTrace();
         return null;
    }   
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
        Serializer serializer = new Persister();
    //    String identFichero = VocabularioRosace.NombreDirectorioPersistenciaResSimulacion+File.separator+infoCaso.getidentCaso();
        try {
             String identFichero = infoCaso.getidentCaso()+extFicheroXML;
             
//             File ficheroEscenario = this.obtenerFicheroEscenario(identFichero);
             if (identsResultadosSimulacion.contains(identFichero)){// Se renombra el fichero agnadiendo 
                 identFichero = this.renombrarFichero(identFichero);
             }
             identFichero = VocabularioRosace.NombreDirectorioPersistenciaResSimulacion+File.separator+identFichero;
             File ficheroEscenario = new File (identFichero);
             serializer.write(infoCaso, ficheroEscenario);
             Map<String, VictimasSalvadas> victimasSalvadas = infoCaso.getRobotRescateVictimas();
             Set<String> robotNombres= victimasSalvadas.keySet();
             VictimasSalvadas listaVictimas;
             for (String ideRobot:robotNombres){
//                 String ideRobot = (String)robtIter.next();
               listaVictimas = (VictimasSalvadas) victimasSalvadas.get(ideRobot);
                 System.out.println(" Desde persistencia Lista de victimas rescatadas por el robot  : " + ideRobot+" Victimas rescatadas : "+ listaVictimas.toString() );
             }
              System.out.println("Desde peticion Guardar Infocaso Simulacion  : " + infoCaso.getidentCaso()+" Numero de victimas : "+ infoCaso.getNumeroVictimasEntorno());

//          serializer.write(escenario, new File(rutaFicheroInfoPersistencia+identFichero+".xml"));
          
          System.out.println("En el fichero   : "+ identFichero);
          System.out.println("Se va a guardar  : "+ infoCaso.getidentCaso() );
        
        } catch (Exception e) { // catches ANY exception
            e.printStackTrace();
           
        }  
    } 
 public void guardarInfoAsignacionVictima (InfoAgteAsignacionVictima infoAsignVictima)throws Exception{
    
     infoVictimasAsigandas.add(infoAsignVictima);
     String identFichero = VocabularioRosace.NombreDirectorioPersistenciaResSimulacion+File.separator+identFicheroInfoAsigVictimasObj;
     FileOutputStream fInfoAsignVictima = new FileOutputStream(identFichero);
     ObjectOutputStream  oPersAsignVictima = new ObjectOutputStream (fInfoAsignVictima);
     oPersAsignVictima.writeObject(infoVictimasAsigandas);
     oPersAsignVictima.close();
    
     
 }
         
  public ArrayList<InfoAgteAsignacionVictima> obtenerInfoAsignacionVictimas ()throws Exception{
   //   String nombreFicheroAsignVictim = "pruebaAsigVictimas";
      if (infoVictimasAsigandas.isEmpty())return null;
                String directorioPersistencia = VocabularioRosace.NombreDirectorioPersistenciaResSimulacion+File.separator;
                String identFichero = directorioPersistencia+identFicheroInfoAsigVictimasObj;
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

    private File obtenerFicheroCasoSimulacion(String identFicheroCaso) {
       Boolean encontrado = false ;
         if(!identFicheroCaso.endsWith(extFicheroXML))identFicheroCaso=identFicheroCaso+extFicheroXML;
         try {
              if(ficherosResultadosSimulacion == null) return null;
                File ficheroObtenido=null ;
                 int i=0;
                while (i<ficherosResultadosSimulacion.length&&!encontrado){
                     ficheroObtenido = ficherosResultadosSimulacion[i];
                     if (ficheroObtenido.getName().equals(identFicheroCaso))encontrado = true;
                     else i++;
                    System.out.println(" Fichero obtenido : " +ficheroObtenido.getName());
                    }
                if (encontrado) return ficheroObtenido;
                else return null;
     }catch (Exception e) { // catches ANY exception
        e.printStackTrace();
         return null;
    } 
    }

    

}
