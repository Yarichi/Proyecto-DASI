package icaro.aplicaciones.recursos.recursoEstadistica.imp;

import icaro.aplicaciones.Rosace.informacion.FinSimulacion;
import icaro.aplicaciones.Rosace.informacion.InfoEquipo;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas;
import icaro.aplicaciones.Rosace.utils.ReadXMLTestRobots;
import icaro.aplicaciones.Rosace.utils.ReadXMLTestSequence;
import icaro.aplicaciones.recursos.recursoEstadistica.ItfUsoRecursoEstadistica;
import icaro.aplicaciones.recursos.recursoEstadistica.imp.visualizacionEstadisticas.VisualizacionJfreechart;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
//This resource class needs the next imports in order to send events towards some agent


public class ClaseGeneradoraRecursoEstadistica extends ImplRecursoSimple implements ItfUsoRecursoEstadistica {
	
	public static int IntervaloFrecuenciaEnvioVictimas;

    private ItfUsoRecursoTrazas trazas; //Se inicializa en el constructor con la referencia al recurso de trazas. Asi ya estara disponible en cualquier metodo.
    private String idRecurso;  //Se inicializara en el constructor con el identificador, dado a la instancia del recurso, en la descripcion de la organizacion

    private long tiempoinicial=0;   //Variable para obtener el tiempo al iniciar la simulacion

    private PrintWriter ficheroXMLTRealLlegadaVictimas;          //Contendra items con elementos: TiempoLLegada, NombreVictima, NroVictimas    
    private PrintWriter ficheroXMLTRealAsignacionVictimasRobots;            //Contendra items con elementos: TiempoAsignacion, NombreVictima, RobotResponsable, NroVictimasAsignadas    
    private PrintWriter ficheroXMLRepartoTareasRobotsYTiempoCompletarlasVersion2;  //Contendra items con elementos: RobotResponsable, Nro total de victimas asignadas, Victimas asignadas, Coste total en atender todas las victimas asignadas 

    private int nroRobotsEnLaSimulacion;
    private int nroRobotsEscritoresEnRepartoTareasRobotsYTiempoCompletarlas=0;
    private boolean flagcreadosFicherosRepartoTareasRobotsYTiempoCompletarlas = false;                              
    
    private int numeroVictimasEntorno=0;    //Numero de victimas actuales que se han introducido en el entorno    
    private int numeroVictimasAsignadas=0;    //Numero de victimas asignadas a robots
    
    private int numeroVictimasDiferentesSimulacion; //Numero de victimas diferentes que van a intervenir en el proceso de simulacion
    
    String ident1="   ";
    String ident2="      ";
    
    private Map<String, String> victimsDiferentesXML = new HashMap <String, String>();  //Victimas diferentes que hay en el fichero de secuencia tomado como entrada
    
    Map<String, Victim> victims2Rescue = new HashMap <String, Victim>();   //Victimas que hay en el entorno    
    Map<String, String> victimasAsignadas = new HashMap <String, String>();   //Victimas que han sido asignadas a algun robot, es decir, algun robot se ha hecho responsable para salvarla

    
    //Variables utilizadas para comunicar a los agentes el final de la simulacion. Se crean e inicializan en el constructor
    protected ComunicacionAgentes comunicacion;
    protected InfoEquipo equipo;
    protected ArrayList identsAgtesEquipo = new ArrayList();
    protected String identificadorEquipo;
    
    //Variables para representar graficas con el jfreechart
    private XYSeries seriesTRLlegadaVictimas ;
    private XYSeries seriesTRAsigancionVictimas ;
    private VisualizacionJfreechart visualizadorJFchart;
    
//    milisegundosactuales = System.currentTimeMillis();//Variable para obtener el tiempo al abrir el programa
    
    public ClaseGeneradoraRecursoEstadistica(String idRecurso) throws Exception{
        super(idRecurso);
        this.idRecurso = idRecurso;
        try {
               trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance()
                                   .obtenerInterfaz(NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
               comunicacion = new ComunicacionAgentes(this.idRecurso);
               
               //Si se intenta acceder al recurso de configuracion da un error y no arranca
//               ItfUsoConfiguracion itfconfig = (ItfUsoConfiguracion)ClaseGeneradoraRepositorioInterfaces.instance()
//                                             .obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
               //itfUsoRepositorioInterfaces = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
               
               ItfUsoConfiguracion itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
              identificadorEquipo = itfconfig.getValorPropiedadGlobal(NombresPredefinidos.NOMBRE_PROPIEDAD_GLOBAL_IDENT_EQUIPO);
               equipo = new InfoEquipo("", identificadorEquipo);
               identsAgtesEquipo = equipo.getTeamMemberIDs();

               setNroRobotsEnLaSimulacion(identsAgtesEquipo);
               
               System.out.println("Nro de robots en la simulacion->" + getNroRobotsEnLaSimulacion());
        } catch (Exception e) {
        	this.itfAutomata.transita("error");
            System.out.println("No se pudo usar el recurso de trazas");
        }
		
        //A continuacion se sigue completando el constructor si es necesario .... 
        //      .................................
		
        //Ultima llamada del constructor seria la siguiente
        trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso, "El constructor de la clase generadora del recurso " + this.id + " ha completado su ejecucion ....",	InfoTraza.NivelTraza.debug));	                    
    }	

    
    //Methods that implement Estadistica resource use interface

    @Override
    public void setNumeroVictimasDiferentesSimulacion(ReadXMLTestSequence rXMLTSeq) throws Exception{
    	Document doc = rXMLTSeq.getDocument(rXMLTSeq.gettestFilePaht());
    	NodeList nodeLst = rXMLTSeq.getVictimsXMLStructure(doc, "victim");  		//Obtain all the victims
    	int numItemsVictim = rXMLTSeq.getNumberOfVictimsInSequence(nodeLst); //El numero de items Victim que hay en el xml
    	for (int i=0; i<numItemsVictim; i++){
    		Element info = rXMLTSeq.getVictimElement(nodeLst, i);   //El nodo de la primera victima es 0
    		String valueid = rXMLTSeq.getVictimIDValue(info,"id");  //Obtener el id de la victima
    		victimsDiferentesXML.put(valueid, valueid);             //Si esta repetida no se hara nada    		
    	}
    	this.numeroVictimasDiferentesSimulacion = victimsDiferentesXML.size();    	
    };
    
    @Override
    public int getNumeroVictimasDiferentesSimulacion() throws Exception{
    	return this.numeroVictimasDiferentesSimulacion;
    }

    
    //Tiempo de llegada de la primera victima
    @Override
    public void setTiempoinicial(long t) throws Exception{
    	this.tiempoinicial = t;
    };
    
    @Override
    public long getTiempoinicial() throws Exception{
    	return this.tiempoinicial;
    };
                
    @Override
    public void crearFicheroXMLTRealAsignacionVictimasRobots(String name) throws Exception{        
        seriesTRAsigancionVictimas = new XYSeries("Assignment Time"); //Leyenda de la serie de asignacion de victimas 
    	try {
			  PrintWriter xml = new PrintWriter(
					            new FileOutputStream(name));			  
			  this.ficheroXMLTRealAsignacionVictimasRobots = xml;
			  
			  this.ficheroXMLTRealAsignacionVictimasRobots.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			  this.ficheroXMLTRealAsignacionVictimasRobots.println("<sequence>");			  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}    	
    }
    
    @Override
    public void crearFicheroTextoPlanoTRealAsignacionVictimasRobots(String name) throws Exception{

    }
        
    public PrintWriter getRefFicheroXMLTRealAsignacionVictimasRobots(){
    	return this.ficheroXMLTRealAsignacionVictimasRobots;
    }

    public PrintWriter getRefFicheroTextoPlanoTRealAsignacionVictimasRobots(){
    	return null;
    }

        
    @Override
    public void cerrarFicheroXMLTRealAsignacionVictimasRobots() throws Exception{
    	  this.ficheroXMLTRealAsignacionVictimasRobots.print("</sequence>");
		  this.ficheroXMLTRealAsignacionVictimasRobots.close();    	
    };

    
    @Override
    public void cerrarFicheroTextoPlanoTRealAsignacionVictimasRobots() throws Exception{
    };
    
    
    private void escribeEstadisticaFicheroXMLTRealAsignacionVictimasRobots(long tiempoActual, String victima, String robot, 
    		                                              int evaluacionRobot, int NroVictimasAsignadas, int NroVictimasEnEntorno) throws Exception{
        long tiempoEstadistico;
        tiempoEstadistico = tiempoActual - this.tiempoinicial;
        String cadena =        "<item>" + "\n";
        cadena = cadena + ident1 + "<victima>" + victima + "</victima>" + "\n";
        cadena = cadena + ident1 + "<robot>" + robot + "</robot>" + "\n";        
        cadena = cadena + ident1 + "<evaluacion>" + evaluacionRobot + "</evaluacion>" + "\n";        
        cadena = cadena + ident1 + "<tiempoasignacion>" + tiempoEstadistico + "</tiempoasignacion>" + "\n";
        cadena = cadena + ident1 + "<nrovictimastotalasignadas>" +  NroVictimasAsignadas + "</nrovictimastotalasignadas>" + "\n";
        cadena = cadena + ident1 + "<nrovictimasenentorno>" +  NroVictimasEnEntorno + "</nrovictimasenentorno>" + "\n";
        cadena = cadena  +     "</item>" + "\n";
        if(ficheroXMLTRealAsignacionVictimasRobots == null)crearFicheroXMLTRealAsignacionVictimasRobots(ConstantesRutasEstadisticas.rutaficheroPlanoEstadisticasLlegadaVictimas);
        this.ficheroXMLTRealAsignacionVictimasRobots.print(cadena);
                
        //Informacion para la grafica del jfreechart
        double tiempoEstadisticoSegundos1Decimal = ObtenerTiempoEnSegundosUnDecimal(tiempoEstadistico,1);        
        seriesTRAsigancionVictimas.add(NroVictimasAsignadas, tiempoEstadisticoSegundos1Decimal);                
        //String str = " ...........NroVictimasAsignadas, ObtenerTiempoEnSegundosUnDecimal(tiempoEstadistico,1) " +
		//           "----->" + NroVictimasAsignadas + ", " + tiempoEstadisticoSegundos1Decimal;        
        //trazas.aceptaNuevaTraza(new InfoTraza(this.id, str, InfoTraza.NivelTraza.info));
    }

    
    //Escribe estadisticas y actualiza el numero de victimas asignadas
    @Override
    public synchronized void escribeEstadisticaFicheroTextoPlanoTRealAsignacionVictimasRobots(long tiempoActual, String victima, 
    		                                                                                  String robot, int evaluacionRobot) throws Exception{
         long tiempoEstadistico;
         tiempoEstadistico = tiempoActual - this.tiempoinicial;

         String valor = victimasAsignadas.put(victima, victima);

         int numeroVictimasEnEntorno = this.numeroVictimasEntorno;
         
         if (valor==null) //no estaba insertado, es una nueva victima asignada
         {
        	 this.incrementarNumeroVictimasAsignadas();
         }
         int nroVictimasAsignadas = this.numeroVictimasAsignadas;
                  
         this.escribeEstadisticaFicheroXMLTRealAsignacionVictimasRobots(tiempoActual, victima, robot, evaluacionRobot, nroVictimasAsignadas, numeroVictimasEnEntorno);
                                            
         //Se han asignado todas las victimas diferentes que hay en la simulacion cerramos los ficheros correspondientes
         if (nroVictimasAsignadas==this.numeroVictimasDiferentesSimulacion){   
        	 this.cerrarFicheroXMLTRealLlegadaVictimas();
        	 this.cerrarFicheroXMLTRealAsignacionVictimasRobots();
        	         	 
        	 //JOptionPane.showMessageDialog(null, "Todas las victimas diferentes de la secuencia han sido asignadas. Puede que todavia queden por enviar algunas victimas repetidas.");
        	 
        	 //Comunicar a todos los agentes el final de la simulacion
        	 FinSimulacion finalSimulacion = new FinSimulacion();        	 
        	 comunicacion.informaraGrupoAgentes(finalSimulacion, identsAgtesEquipo);
        	 
             //Crear y mostrar las series de estadisticas recogidas en graficas del jfreechart                
             visualizadorJFchart = new VisualizacionJfreechart("Graficas estadisticas simulacion");
                          
             visualizadorJFchart.inicializacionJFreeChart(
                			"Victim's Notification and Assignment to Team members ",   //titulo   
                			"Number of Victim's Notifications",                            //etiqueta eje x  
                			"Time in seconds",                    //etiqueta eje y
                			PlotOrientation.VERTICAL,             //horientacion del plot
                			true,                                 //leyenda
                			true,                                 //tooltips
                			false                                 //urls
                		);                       
             
             visualizadorJFchart.setColorChartBackgroundPaint(Color.white);
             visualizadorJFchart.setColorChartPlotBackgroundPaint(Color.lightGray);
             visualizadorJFchart.setColorChartPlotDomainGridlinePaint(Color.white);             
             visualizadorJFchart.setColorChartPlotRangeGridlinePaint(Color.white);
             
             visualizadorJFchart.showJFreeChart(40,40);
                
             visualizadorJFchart.addSerie(1, Color.green, seriesTRLlegadaVictimas);
             visualizadorJFchart.addSerie(2, Color.green, seriesTRAsigancionVictimas);
             
             //Llamadas para que se creen automaticamente otros ficheros de estadisticas (llegadaYAsignacion e intervalo)
             GeneraEstadisticaLlegadaYAsignacionVictimas generaEstadisticaLlegadaYAsignacionVictimas = new GeneraEstadisticaLlegadaYAsignacionVictimas(); 
             generaEstadisticaLlegadaYAsignacionVictimas.CrearFicheroXMLEstadisticaLlegadaYAsignacionVictimas();
             
             GeneraEstadisticaIntervaloLlegadaYAsignacionVictimas generaEstadisticaIntervalo = new GeneraEstadisticaIntervaloLlegadaYAsignacionVictimas();
             
     		String rutaFicheroRobotsSimulacion = getRutaFicheroRobotTest();
    		ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotsSimulacion);
    		Document robotsSimulacion = rXMLTRobots.getDocument(rutaFicheroRobotsSimulacion);
    		NodeList listaNodosRobotsSimulacion = rXMLTRobots.getRobotsXMLStructure(robotsSimulacion, "robot");
    		int nroRobotsSimulacion = rXMLTRobots.getNumberOfRobots(listaNodosRobotsSimulacion);
                                      
             System.out.println("identsAgtesEquipo->" + identsAgtesEquipo + " ; identsAgtesEquipo.size()->" + identsAgtesEquipo.size() + " ; variable nroRobotsSimulacion(acceso al xml)->" + nroRobotsSimulacion);

             generaEstadisticaIntervalo.CrearFicheroXMLIntervaloLlegadaYAsignacionVictimas(equipo.getTeamId(), nroRobotsSimulacion, nroVictimasAsignadas, IntervaloFrecuenciaEnvioVictimas);                                       

//             generaEstadisticaIntervalo.CrearFicheroXMLIntervaloLlegadaYAsignacionVictimasXX(equipo.getTeamId(), identsAgtesEquipo.size(), nroVictimasAsignadas);                                       
//             generaEstadisticaIntervalo.CrearFicheroXMLIntervaloLlegadaYAsignacionVictimasXX(equipo.getTeamId(), equipo.getNumberOfTeamMembers(), nroVictimasAsignadas);                                       
         }
    }
    
      
    @Override
    public void crearFicheroXMLTRealLlegadaVictimas(String name) throws Exception{
    	
    	seriesTRLlegadaVictimas = new XYSeries("Notification Time");
    	
    	try {
			  PrintWriter xml = new PrintWriter(
					            new FileOutputStream(name));			  
			  this.ficheroXMLTRealLlegadaVictimas = xml;
			  
			  this.ficheroXMLTRealLlegadaVictimas.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			  this.ficheroXMLTRealLlegadaVictimas.println("<sequence>");			  			  			  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}    	
    }
    
    @Override
    public void cerrarFicheroXMLTRealLlegadaVictimas() throws Exception{
     	      this.ficheroXMLTRealLlegadaVictimas.println("</sequence>");    	
			  this.ficheroXMLTRealLlegadaVictimas.close();               
    };
    
    
    @Override
    public void crearFicheroTextoPlanoTRealLlegadaVictimas(String name) throws Exception{

    }
        
    @Override
    public void cerrarFicheroTextoPlanoTRealLlegadaVictimas() throws Exception{

    }
    
            
    @Override
    public void escribeEstadisticaFicheroXMLTRealLlegadaVictimas(long tiempoActual, Victim victima) throws Exception{
        long tiempoEstadistico;
        tiempoEstadistico = tiempoActual - this.tiempoinicial;
        String cadena =        "<item>" + "\n";
        cadena = cadena + ident1 + "<victima>" + victima.getName() + "</victima>" + "\n";
        cadena = cadena + ident1 + "<tiempollegada>" + tiempoEstadistico + "</tiempollegada>" + "\n";
        cadena = cadena + ident1 + "<nrovictimas>" +  this.numeroVictimasEntorno + "</nrovictimas>" + "\n";         
        cadena = cadena  +     "</item>" + "\n";
        if(ficheroXMLTRealLlegadaVictimas == null)crearFicheroTextoPlanoTRealLlegadaVictimas(ConstantesRutasEstadisticas.rutaficheroPlanoEstadisticasLlegadaVictimas);
        this.ficheroXMLTRealLlegadaVictimas.print(cadena);
        
        double tiempoEstadisticoSegundos1Decimal = ObtenerTiempoEnSegundosUnDecimal(tiempoEstadistico,1);
        
        seriesTRLlegadaVictimas.add(this.numeroVictimasEntorno, tiempoEstadisticoSegundos1Decimal);
                
//        String str = " ...........NroVictimasEnEntorno, ObtenerTiempoEnSegundosUnDecimal(tiempoEstadistico,1) " +
//		           "----->" + this.numeroVictimasEntorno + ", " + tiempoEstadisticoSegundos1Decimal;        
//        trazas.aceptaNuevaTraza(new InfoTraza(this.id, str, 
//        		                InfoTraza.NivelTraza.info));
        
        
    }

    
    //Escribe estadisticas y actualiza el numero de victimas enviadas
    @Override
    public void escribeEstadisticaFicheroTextoPlanoTRealLlegadaVictimas(long tiempoActual, Victim victima) throws Exception{
         long tiempoEstadistico;
         tiempoEstadistico = tiempoActual - this.tiempoinicial;

         Victim valor = victims2Rescue.put(victima.getName(), victima);
         if (valor==null) //no estaba insertado
         {
             this.incrementarNumeroVictimasActuales();        	 
         }                           
    }
    

    @Override
    public synchronized void crearFicherosRepartoTareasRobotsYTiempoCompletarlas(String filenameXMLV1, String filenameXMLV2, String filenameTXT) throws Exception{
    	try {    		
    		  boolean flag = getFlagcreadosFicherosRepartoTareasRobotsYTiempoCompletarlas();

    		  if (flag==false){    			
    			  		PrintWriter ficheroXMLV2 = new PrintWriter(new FileOutputStream(filenameXMLV2));
			            			  
    			  		this.ficheroXMLRepartoTareasRobotsYTiempoCompletarlasVersion2 = ficheroXMLV2;

    					this.ficheroXMLRepartoTareasRobotsYTiempoCompletarlasVersion2.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    					this.ficheroXMLRepartoTareasRobotsYTiempoCompletarlasVersion2.println("<sequence>");
    					
    			  		setTrueFlagcreadosFicherosRepartoTareasRobotsYTiempoCompletarlas();
			  }			  			  			  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}    	    	    	
    }


    @Override
    public void cerrarFicherosRepartoTareasRobotsYTiempoCompletarlas() throws Exception{
		  
	      this.ficheroXMLRepartoTareasRobotsYTiempoCompletarlasVersion2.println("</sequence>");    	
		  this.ficheroXMLRepartoTareasRobotsYTiempoCompletarlasVersion2.close();
		  		      	  
    	  //FIN DE LA SIMULACION
          String directorioTrabajo = System.getProperty("user.dir");  //Obtener directorio de trabajo      		
          
          String msg = "FIN DE LA SIMULACION !!!.\n";
          msg = msg + "Se ha completado la captura de todas las estadisticas para la simulacion actual.\n";
          msg = msg + "Los ficheros de estadisticas se encuentran en el directorio " + directorioTrabajo + "/" + ConstantesRutasEstadisticas.rutaDirectorioEstadisticas + "\n";
          msg = msg + "Los ficheros de estadisticas son los siguientes:\n";
          msg = msg + directorioTrabajo + "/" + ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaVictimas + "\n";
          msg = msg + directorioTrabajo + "/" + ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasAsignacionVictimas + "\n";
          msg = msg + directorioTrabajo + "/" + ConstantesRutasEstadisticas.rutaficheroXMLRepartoTareasRobotsYTiempoCompletarlasV2 + "\n";              
          msg = msg + directorioTrabajo + "/" + ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaYAsignacionVictimas + "\n";          
          msg = msg + directorioTrabajo + "/" + "EstIntLlegadaYAsignacionVictims" + "FECHA.xml" + "\n";
                            
     	  JOptionPane.showMessageDialog(null, msg);
    }

    
    @Override
    public synchronized void escribeEstadisticaFicherosRepartoTareasRobotsYTiempoCompletarlas(String robot, 
    		                                                                     ArrayList idsVictimasFinalesAsignadas,
    		                                                                     double costeTotalAtenderTodasLasVictimasAsignadas) throws Exception{
    	
    	//Me quedo con la parte entera del double para imprimirla en la estadistica
    	int nroEnterocosteTotalAtenderTodasLasVictimasAsignadas = (int)costeTotalAtenderTodasLasVictimasAsignadas;
    	
    	int nroRobotsEnLaSimulacion = getNroRobotsEnLaSimulacion();
    	
        int nroVictimasFinalesAsignadas = idsVictimasFinalesAsignadas.size();
        
        String cadenaTXTRobot = robot;
        String cadenaTXTnroVictimasFinalesAsignadas = "" + nroVictimasFinalesAsignadas;        
        String cadenaTXTcosteTotalAtenderTodasLasVictimasAsignadas = "";
        String cadenaTXTvictimasAsignadas = "";

        String cadenaXMLV2 =  "";
        
        String cadenaXMLV1 =        "<item>" + "\n";
        cadenaXMLV1 = cadenaXMLV1 + ident1 + "<robot>" + robot + "</robot>" + "\n";

        cadenaXMLV1 = cadenaXMLV1 + ident1 + "<nroTotalVictimasAsignadas>" + nroVictimasFinalesAsignadas + "</nroTotalVictimasAsignadas>" + "\n";
        
        cadenaXMLV2 = cadenaXMLV1; 
                
        if (nroVictimasFinalesAsignadas==0){
        	//cadenaTXTvictimasAsignadas = "NADA";
        	cadenaTXTvictimasAsignadas = " ";
        }
        
        cadenaXMLV1 = cadenaXMLV1 + ident1 + "<victimasAsignadas>" + "\n";
        
        cadenaXMLV2 = cadenaXMLV2 + ident1 + "<victimasAsignadas>";
        
        for (int i=0;i<nroVictimasFinalesAsignadas;i++){
        	String victima = (String)idsVictimasFinalesAsignadas.get(i);        	        	
            cadenaXMLV1 = cadenaXMLV1 + ident2 + "<victima>" + victima + "</victima>" + "\n";                        
            
            cadenaTXTvictimasAsignadas = cadenaTXTvictimasAsignadas + victima + "  ";
        }
        
        cadenaXMLV2 = cadenaXMLV2 + cadenaTXTvictimasAsignadas + "</victimasAsignadas>" + "\n";; 
        
        cadenaXMLV1 = cadenaXMLV1 + ident1 + "</victimasAsignadas>" + "\n";                                
                                    
        cadenaXMLV2 = cadenaXMLV2 + ident1 + "<costeTotalAtenderTodasLasVictimasAsignadas>" + 
                                             nroEnterocosteTotalAtenderTodasLasVictimasAsignadas + 
                                             "</costeTotalAtenderTodasLasVictimasAsignadas>" + "\n";
        
        cadenaXMLV2 = cadenaXMLV2 + "</item>" + "\n";
        		
        cadenaXMLV1 = cadenaXMLV1 + ident1 + "<costeTotalAtenderTodasLasVictimasAsignadas>" + nroEnterocosteTotalAtenderTodasLasVictimasAsignadas + "</costeTotalAtenderTodasLasVictimasAsignadas>" + "\n";
                
        cadenaTXTcosteTotalAtenderTodasLasVictimasAsignadas = "" + nroEnterocosteTotalAtenderTodasLasVictimasAsignadas;
        
        cadenaXMLV1 = cadenaXMLV1  +     "</item>" + "\n";
                
        this.ficheroXMLRepartoTareasRobotsYTiempoCompletarlasVersion2.print(cadenaXMLV2);
                               		                                                                                     
        //Incrementar el numero de robots escritores en este fichero de estadistica final
        nroRobotsEscritoresEnRepartoTareasRobotsYTiempoCompletarlas++;
        
        //Si han escrito todos los robots entonces cerrar los ficheros
        if (nroRobotsEscritoresEnRepartoTareasRobotsYTiempoCompletarlas==nroRobotsEnLaSimulacion)
        	cerrarFicherosRepartoTareasRobotsYTiempoCompletarlas();        
    }
    
    
    private boolean getFlagcreadosFicherosRepartoTareasRobotsYTiempoCompletarlas() throws Exception{
    	return this.flagcreadosFicherosRepartoTareasRobotsYTiempoCompletarlas;
    }
    
    
    private void setTrueFlagcreadosFicherosRepartoTareasRobotsYTiempoCompletarlas(){
    	   this.flagcreadosFicherosRepartoTareasRobotsYTiempoCompletarlas = true;
    }
    
    
    private void incrementarNumeroVictimasActuales(){
         this.numeroVictimasEntorno ++;	
    }
    

    private void incrementarNumeroVictimasAsignadas(){
        this.numeroVictimasAsignadas ++;	
    }
    
 
    private void setNroRobotsEnLaSimulacion(ArrayList identsAgtesEquipo){
    	this.nroRobotsEnLaSimulacion = identsAgtesEquipo.size();    	
    };
 
    
    private int getNroRobotsEnLaSimulacion(){    
    	return this.identsAgtesEquipo.size();    	
    };
    
    

    @Override
    public void termina() {
        trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso, "Terminando recurso" + this.id + " ....",	InfoTraza.NivelTraza.debug));
 	    
        //Si es un recurso de visualizacion es necesaria una llamar a dispose de la ventana de visualizacion. Algo parecido a lo siguiente	
        //this.jvariableLocalReferenciaVisualizador.dispose(); //Destruye los componentes utilizados por este JFrame y devuelve la memoria utilizada al Sistema Operativo 	 
		
        super.termina();
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


	private String getRutaFicheroRobotTest(){
		
		String rutaFicheroRobotTest = "";
		ClaseGeneradoraRepositorioInterfaces itfUsoRepositorioInterfaces;
		ItfUsoConfiguracion itfconfig;
		
		//Recuperar la ruta del fichero de robots del escenario
    	try{    	
    		itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
    		itfconfig = (ItfUsoConfiguracion)itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
    		rutaFicheroRobotTest = itfconfig.getValorPropiedadGlobal(VocabularioRosace.rutaFicheroRobotsTest); 
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}
				
		return rutaFicheroRobotTest;
	}
	
}
