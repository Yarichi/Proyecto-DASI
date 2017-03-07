package icaro.aplicaciones.recursos.recursoEstadistica.imp;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.recursos.recursoEstadistica.imp.visualizacionEstadisticas.VisualizacionJfreechart;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GeneraEstadisticaIntervaloLlegadaYAsignacionVictimas {

    //Variables para representar graficas con el jfreechart
    private XYSeries serieIntervaloLlegadaYAsignacionVictimas ;
    private VisualizacionJfreechart visualizadorJFchart;

	
	//Constructor de la clase
	public void GeneraEstadisticaIntervaloLlegadaYAsignacionVictimas(){
				
	}
	
	
	public void CrearFicheroXMLIntervaloLlegadaYAsignacionVictimas(String equipo, int numeroRobotsSimulacion, int numeroVictimasSimulacion, int intervaloFrecuenciaEnvioVictimas){
		
		serieIntervaloLlegadaYAsignacionVictimas = new XYSeries("Elapsed Time");
		
	    String ident1="   ";
	    String ident2="      ";
		
	    int totalVictimasEnviadas;
	    
		PrintWriter pwficheroXMLResultado=null;         
                                                        
		String ficheroXMLLlegadaVictimasYAsignacionVictimas = icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaYAsignacionVictimas;		

//		String ficheroXMLResultado = icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaYAsignacionVictimas;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String fecha = dateFormat.format(Calendar.getInstance().getTime()); 
		String ficheroXMLResultado = "estadisticas/" + "EstIntLlegadaYAsignacionVictims"+fecha +".xml";

//		String rutaFicheroRobotsSimulacion = getRutaFicheroRobotTest();
//		ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotsSimulacion);
//		Document robotsSimulacion = rXMLTRobots.getDocument(rutaFicheroRobotsSimulacion);
//		NodeList listaNodosRobotsSimulacion = rXMLTRobots.getRobotsXMLStructure(robotsSimulacion, "robot");
//		int nro = rXMLTRobots.getNumberOfRobots(listaNodosRobotsSimulacion);
				
    	try {
		     pwficheroXMLResultado = new PrintWriter(new FileOutputStream(ficheroXMLResultado));
		     //  

 		         pwficheroXMLResultado.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		     
		         pwficheroXMLResultado.print("<!-- ");
		         pwficheroXMLResultado.print("simulacion " + equipo + " ; numero robots simulacion = " + numeroRobotsSimulacion + 
		        		                     " ; numero victimas simulacion = " + numeroVictimasSimulacion + 
		        		                     " ; frecuencia de envio = " + intervaloFrecuenciaEnvioVictimas + " milisegundos");
		         pwficheroXMLResultado.println(" -->");

			     pwficheroXMLResultado.println("<sequence>");
	    } catch (FileNotFoundException e) {
		     e.printStackTrace();
	    }
		
		Document docficheroXMLLlegadaYAsignacionVictimas=null;				
        try {
			  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			  docficheroXMLLlegadaYAsignacionVictimas = dBuilder.parse(new File(ficheroXMLLlegadaVictimasYAsignacionVictimas));
			  docficheroXMLLlegadaYAsignacionVictimas.getDocumentElement().normalize();
        } catch (Exception e) {
             e.printStackTrace();
        }        
        
        NodeList nodeLstficheroXMLLlegadaYAsignacionVictimas = docficheroXMLLlegadaYAsignacionVictimas.getElementsByTagName("item");        
        int nroItemsficheroXMLLlegadaYAsignacionVictimas = nodeLstficheroXMLLlegadaYAsignacionVictimas.getLength();

        System.out.println("nroItemsficheroXMLLlegadaYAsignacionVictimas->" + nroItemsficheroXMLLlegadaYAsignacionVictimas);

        totalVictimasEnviadas = buscaTotalVictimasEnviadas(nodeLstficheroXMLLlegadaYAsignacionVictimas);
        
        System.out.println("totalVictimasEnviadas->" + totalVictimasEnviadas);

        
        double tiempoLlegadaVictima;        
        double tiempoAsignacionVictima;
        double intervaloLlegadaAsignacion;
        String strIntervaloLlegadaAsignacion;
        
        for (int i=1;i<=totalVictimasEnviadas;i++){
        	
            String stri = i + "";        	
            tiempoLlegadaVictima = buscaTiempoLlegadaVictimaEnviadaI(nodeLstficheroXMLLlegadaYAsignacionVictimas, stri);
        	
        	System.out.println("tiempoLlegadaVictima " + i + "->" + tiempoLlegadaVictima);

        	tiempoAsignacionVictima = buscaTiempoAsignacionVictimaEnviadaI(nodeLstficheroXMLLlegadaYAsignacionVictimas,stri);

        	System.out.println("tiempoAsignacionVictima " + i + "->" + tiempoAsignacionVictima);

        	intervaloLlegadaAsignacion = tiempoAsignacionVictima - tiempoLlegadaVictima;
        	
        	System.out.println("intervaloLlegadaAsignacion " + i + "->" + intervaloLlegadaAsignacion);
        	
        	strIntervaloLlegadaAsignacion = intervaloLlegadaAsignacion + "";
        	strIntervaloLlegadaAsignacion = ObtenerTiempoEnSegundosNDecimales(strIntervaloLlegadaAsignacion,1);  //1 decimal
        	System.out.println("strIntervaloLlegadaAsignacion " + i + "->" + strIntervaloLlegadaAsignacion + "\n");

        	//escribir en el fichero un item        	
        	escribeEstadisticaFicheroXMLIntervaloLlegadaYAsignacionVictimas(pwficheroXMLResultado, stri, strIntervaloLlegadaAsignacion);        	        	
        }
        
        //CERRAR EL FICHERO PARA QUE SE PRODUZCA LA ESCRITURA
        pwficheroXMLResultado.print("</sequence>");
        pwficheroXMLResultado.close();
        
        
        //Crear y mostrar las series de estadisticas recogidas en graficas del jfreechart
//        pwficheroXMLResultado.print("simulacion " + equipo + " ; numero robots simulacion = " + numeroRobotsSimulacion + 
//                " ; numero victimas simulacion = " + numeroVictimasSimulacion + 
//                " ; frecuencia de envio = " + intervaloFrecuenciaEnvioVictimas + " milisegundos");

        String cadena = equipo + ": " + numeroRobotsSimulacion + " robots" + " ; " + numeroVictimasSimulacion + " victimas" + " ; " + intervaloFrecuenciaEnvioVictimas + " milisegundos";
        
        visualizadorJFchart = new VisualizacionJfreechart("Graficas estadisticas simulacion " + cadena);
        visualizadorJFchart.inicializacionJFreeChart(
    			"Elapsed Time to Assign a New Victim",   //titulo   
    			"Number of Victim's Notifications",    //etiqueta eje x  
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

        visualizadorJFchart.showJFreeChart(100,100);

        visualizadorJFchart.addSerie(1, Color.green, serieIntervaloLlegadaYAsignacionVictimas);

        
        System.out.println("Fichero de estadisticas salvado en " + ficheroXMLResultado);
        //String text = "Fichero de estadisticas salvado en " + ficheroXMLResultado;
        //String title = "Fin Generacion Estadistica IntervaloLlegadaYAsignacion";
        //JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);

        //System.out.println(pwficheroXMLResultado.);
	}
	

	
	private int buscaTotalVictimasEnviadas(NodeList nodeLstficheroXMLLlegadaYAsignacionVictimas){
		int contador = 0;

        int nroItemsficheroXMLLlegadaYAsignacionVictimas = nodeLstficheroXMLLlegadaYAsignacionVictimas.getLength();
        Element infofichaItem = null;
        String nrovictimasenentorno="";

        //System.out.println("nroItemsficheroXMLLlegadaYAsignacionVictimas->" + nroItemsficheroXMLLlegadaYAsignacionVictimas);
        
        for (int i=0; i<nroItemsficheroXMLLlegadaYAsignacionVictimas; i++){
        	infofichaItem = ObtenerElementoItem(nodeLstficheroXMLLlegadaYAsignacionVictimas, i);            
		    //obtener valor tiempo de nrovictimasenentorno        	          	
        	nrovictimasenentorno = ObtenerElementoItem(infofichaItem, "nrovictimasenentorno");
        	        	        	
            if (! nrovictimasenentorno.equals("")){
            	contador++;
//                System.out.println("contador->"+contador);            	
            }
//            else {
//            	contador++;
//                System.out.println("contador->"+contador);
//            }            
        }				
		return contador;
	}
	
	
	private double buscaTiempoLlegadaVictimaEnviadaI(NodeList nodeLstficheroXMLLlegadaYAsignacionVictimas, String nroVictima){

		double tiempoResultado = 0.0 ;
		String strtiempoResultado = "";

        int nroItemsficheroXMLLlegadaYAsignacionVictimas = nodeLstficheroXMLLlegadaYAsignacionVictimas.getLength();
        Element infofichaItem = null;
        String nrovictimasenentorno="";

        for (int i=0; i<nroItemsficheroXMLLlegadaYAsignacionVictimas; i++){
        	infofichaItem = ObtenerElementoItem(nodeLstficheroXMLLlegadaYAsignacionVictimas, i);            
		    //obtener valor tiempo de nrovictimasenentorno        	          	
        	nrovictimasenentorno = ObtenerElementoItem(infofichaItem, "nrovictimasenentorno");
        	
        	if (nrovictimasenentorno.equals(nroVictima)){
        		strtiempoResultado = ObtenerElementoItem(infofichaItem, "tiempo");
        		tiempoResultado = Double.valueOf(strtiempoResultado).doubleValue();
        		i=nroItemsficheroXMLLlegadaYAsignacionVictimas;//salir del for
        	}        	
        }                             
		return tiempoResultado;
	}
	
	
	private double buscaTiempoAsignacionVictimaEnviadaI(NodeList nodeLstficheroXMLLlegadaYAsignacionVictimas, String nroVictima){

		double tiempoResultado = 0.0 ;
		String strtiempoResultado = "";

        int nroItemsficheroXMLLlegadaYAsignacionVictimas = nodeLstficheroXMLLlegadaYAsignacionVictimas.getLength();
        Element infofichaItem = null;
        String nrovictimasenentorno="";

        for (int i=0; i<nroItemsficheroXMLLlegadaYAsignacionVictimas; i++){
        	infofichaItem = ObtenerElementoItem(nodeLstficheroXMLLlegadaYAsignacionVictimas, i);            
		    //obtener valor tiempo de nrovictimasenentorno        	          	
        	nrovictimasenentorno = ObtenerElementoItem(infofichaItem, "nrovictimastotalasignadas");
        	
        	if (nrovictimasenentorno.equals(nroVictima)){
        		strtiempoResultado = ObtenerElementoItem(infofichaItem, "tiempo");
        		tiempoResultado = Double.valueOf(strtiempoResultado).doubleValue();
        		i=nroItemsficheroXMLLlegadaYAsignacionVictimas;//salir del for
        	}        	
        }                             
		return tiempoResultado;
	}
	
	
	
    private void escribeEstadisticaFicheroXMLIntervaloLlegadaYAsignacionVictimas( PrintWriter pw,    
    																					String NroVictima, 
    																					String tiempo) 
    {
    		String ident1="   ";	
    		String cadena =        "<item>" + "\n";
    		cadena = cadena + ident1 + "<victima>" + NroVictima + "</victima>" + "\n";
    		cadena = cadena + ident1 + "<elapsedTime>" +  tiempo + "</elapsedTime>" + "\n";
    		cadena = cadena  +     "</item>" + "\n";
    		pw.print(cadena);
    		
    		//actualizar tambien el punto en la serie de la grafica
    		int nv = Integer.parseInt(NroVictima);
    		double t  = Double.parseDouble(tiempo);    		
    		try {
    		    serieIntervaloLlegadaYAsignacionVictimas.add((double)nv, t);
    		}
    		catch (Exception e){
    			e.printStackTrace();
    		}
    		
    		
    }
	

	//********************************************************************************
	// METODOS AUXILIARES DE ESTA CLASE
	//********************************************************************************
	
    //Obtendo elemento del NodeList obtenido a partir de un fichero xml
	public Element ObtenerElementoItem(NodeList nodeLst, int indice){
    	  Element info=null;
	      Node fstNode = nodeLst.item(indice);
	      if (fstNode.getNodeType() == Node.ELEMENT_NODE){
		      info = (Element) fstNode;
	      }
	      return info;	      
	}
	
	
	public String ObtenerElementoItem(Element info, String tag){
	      String valor = "";
		  try {

		      NodeList idNmElmntLst = info.getElementsByTagName(tag);
		      Element idNmElmnt = (Element) idNmElmntLst.item(0);
		      NodeList idNm = idNmElmnt.getChildNodes();	
		    	    
			  valor = ((Node)idNm.item(0)).getNodeValue();
		  } catch (Exception e) {
			//e.printStackTrace();
		  }		
		  if (valor==null) valor="";
		  return valor;				
	}

    //obtener cadena con valor en segundos con nrodecimales decimales a partir de una cadena con valor en segundos 
	public String ObtenerTiempoEnSegundosNDecimales(String strSegundos, int nrodecimales){
//		  int numtiempoEnMiliSegundos = Integer.parseInt(strMilisegundos);
		  
//		  double numtiempoEnSegundos = Double.valueOf(strSegundos).doubleValue();
		  
//		  String strnumtiempoEnSegundos = "" + numtiempoEnSegundos;		  		  

		  String strnumtiempoEnSegundos = "" + strSegundos;		  		  

		  int posPunto = strnumtiempoEnSegundos.indexOf('.');
		  String strnumtiempoEnSegundosUnDecimal = strnumtiempoEnSegundos.substring(0, posPunto+1+nrodecimales);			  
		  return strnumtiempoEnSegundosUnDecimal;
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
	
	
	public static void main(String[] args) {
		
//		GeneraEstadisticaIntervaloLlegadaYAsignacionVictimas gEstaInter = new GeneraEstadisticaIntervaloLlegadaYAsignacionVictimas();
//		gEstaInter.CrearFicheroXMLIntervaloLlegadaYAsignacionVictimas();
		
	}
	
}
