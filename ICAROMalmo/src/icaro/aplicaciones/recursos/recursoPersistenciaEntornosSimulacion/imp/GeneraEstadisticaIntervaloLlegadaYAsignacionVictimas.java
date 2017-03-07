package icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp;

import icaro.aplicaciones.recursos.recursoEstadistica.imp.visualizacionEstadisticas.VisualizacionJfreechart;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.text.SimpleDateFormat;
import java.util.Calendar; 

public class GeneraEstadisticaIntervaloLlegadaYAsignacionVictimas {
	
    private String tipoSimulacion;
    private int numeroRobotsSimulacion;
    private int numeroVictimasSimulacion; 
    private int intervaloFrecuenciaEnvioVictimas;
    
	//Constructor de la clase
	public GeneraEstadisticaIntervaloLlegadaYAsignacionVictimas(String tipoSimulacion, int numeroRobotsSimulacion, int numeroVictimasSimulacion, int intervaloFrecuenciaEnvioVictimas){
		this.tipoSimulacion = tipoSimulacion;
		this.numeroRobotsSimulacion = numeroRobotsSimulacion;
		this.numeroVictimasSimulacion = numeroVictimasSimulacion;
		this.intervaloFrecuenciaEnvioVictimas = intervaloFrecuenciaEnvioVictimas;		
	}
	
	
	public void CrearFicheroXMLIntervaloLlegadaYAsignacionVictimas(){
				
	    String ident1="   ";
	    String ident2="      ";
		
	    int totalVictimasEnviadas;
	    
		PrintWriter pwficheroXMLResultado=null;         
                                                        
		String ficheroXMLLlegadaVictimasYAsignacionVictimas = icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaYAsignacionVictimas;		

//		String ficheroXMLResultado = icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaYAsignacionVictimas;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String fecha = dateFormat.format(Calendar.getInstance().getTime());
        
		String ficheroXMLResultado = "estadisticas/" + "EstIntLlegadaYAsignacionVictims"+fecha +".xml";

    	try {
		         pwficheroXMLResultado = new PrintWriter(new FileOutputStream(ficheroXMLResultado));
			     pwficheroXMLResultado.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			     
		         pwficheroXMLResultado.print("<!-- ");
			     
		         pwficheroXMLResultado.print("simulacion " + this.tipoSimulacion + 
		        		                     " ; numero robots simulacion = " + this.numeroRobotsSimulacion + 
	                                         " ; numero victimas simulacion = " + this.numeroVictimasSimulacion + 
	                                         " ; frecuencia de envio = " + this.intervaloFrecuenciaEnvioVictimas + " milisegundos");
		         
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
                       
        System.out.println("Fichero de estadisticas salvado en " + ficheroXMLResultado);
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
    		cadena = cadena + ident1 + "<elapsed time>" +  tiempo + "</elapsed time>" + "\n";
    		cadena = cadena  +     "</item>" + "\n";
    		pw.print(cadena);    		
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
		  String strnumtiempoEnSegundos = "" + strSegundos;		  		  

		  int posPunto = strnumtiempoEnSegundos.indexOf('.');
		  String strnumtiempoEnSegundosUnDecimal = strnumtiempoEnSegundos.substring(0, posPunto+1+nrodecimales);			  
		  return strnumtiempoEnSegundosUnDecimal;
	}
	
	
	public static void main(String[] args) {		
//		GeneraEstadisticaIntervaloLlegadaYAsignacionVictimas gEstaInter = new GeneraEstadisticaIntervaloLlegadaYAsignacionVictimas();
		GeneraEstadisticaIntervaloLlegadaYAsignacionVictimas gEstaInter = new GeneraEstadisticaIntervaloLlegadaYAsignacionVictimas("tipoSimulacion", 4, 6, 10000);
		gEstaInter.CrearFicheroXMLIntervaloLlegadaYAsignacionVictimas();
		
	}
	
}
