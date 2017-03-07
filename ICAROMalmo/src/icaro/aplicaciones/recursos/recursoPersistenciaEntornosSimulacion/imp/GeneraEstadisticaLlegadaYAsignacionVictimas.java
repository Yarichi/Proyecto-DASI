package icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GeneraEstadisticaLlegadaYAsignacionVictimas {

    private String tipoSimulacion;
    private int numeroRobotsSimulacion;
    private int numeroVictimasSimulacion; 
    private int intervaloFrecuenciaEnvioVictimas;	
	
	//Constructor de la clase
	public GeneraEstadisticaLlegadaYAsignacionVictimas(String tipoSimulacion, int numeroRobotsSimulacion, int numeroVictimasSimulacion, int intervaloFrecuenciaEnvioVictimas){
		this.tipoSimulacion = tipoSimulacion;
		this.numeroRobotsSimulacion = numeroRobotsSimulacion;
		this.numeroVictimasSimulacion = numeroVictimasSimulacion;
		this.intervaloFrecuenciaEnvioVictimas = intervaloFrecuenciaEnvioVictimas;				
	}

	
	//El metodo CrearFicheroXMLEstadisticaLlegadaYAsignacionVictimas es una copia del main
	//Este metodo se ha declarado para automatizar totalmente la generacion de los xml de estadisticas
	//La simulacion creara automaticamente el fichero xml que comina la llegada y asignacion de victimas cuando acaba la simulacion
	//Esto se produce en el metodo cerrarFicherosRepartoTareasRobotsYTiempoCompletarlas de la clase ClaseGeneradoraRecursoEstadistica
	public void CrearFicheroXMLEstadisticaLlegadaYAsignacionVictimas(){
	    String ident1="   ";
	    String ident2="      ";
		
		PrintWriter pwficheroXMLResultado=null;         //Para cada instante de tiempo tomado tendra un item con formato Tiempo, Nro Victimas en el entorno Sistema, Nro Victimas Asignadas a los robots
                                                        //Alguno de los numeros sera vacio
		
		String ficheroXMLLlegadaVictimas = icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaVictimas;		
		String ficheroXMLAsignacionVictimas = icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasAsignacionVictimas;
		
		String ficheroXMLResultado = icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaYAsignacionVictimas;
		
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
    	    	
		Document docficheroXMLLlegadaVictimas=null;				
        try {
			  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			  docficheroXMLLlegadaVictimas = dBuilder.parse(new File(ficheroXMLLlegadaVictimas));
			  docficheroXMLLlegadaVictimas.getDocumentElement().normalize();
        } catch (Exception e) {
             e.printStackTrace();
        }        
        NodeList nodeLstficheroXMLLlegadaVictimas = docficheroXMLLlegadaVictimas.getElementsByTagName("item");        
        int nroItemsficheroXMLLlegadaVictimas = nodeLstficheroXMLLlegadaVictimas.getLength();
         
        
		Document docficheroXMLAsignacionVictimas=null;				
        try {
			  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			  docficheroXMLAsignacionVictimas = dBuilder.parse(new File(ficheroXMLAsignacionVictimas));
			  docficheroXMLAsignacionVictimas.getDocumentElement().normalize();
        } catch (Exception e) {
             e.printStackTrace();
        }
        NodeList nodeLstficheroXMLAsignacionVictimas = docficheroXMLAsignacionVictimas.getElementsByTagName("item");                
        int nroItemsficheroXMLAsignacionVictimas = nodeLstficheroXMLAsignacionVictimas.getLength();

        System.out.println("nroItemsficheroXMLLlegadaVictimas->" + nroItemsficheroXMLLlegadaVictimas);        
        System.out.println("nroItemsficheroXMLAsignacionVictimas->" + nroItemsficheroXMLAsignacionVictimas + "\n\n");
        
        int indexFicheroXMLLlegadaVictimas = 0;        
        Element infofichllegadaVictimas = null;
        String tiempollegada="";
        int nrotiempollegada=0;
        String strnumtiempollegadaEnSegundosUnDecimal="";
        double doblenumtiempollegadaEnSegundosUnDecimal;
        String nrovictimasenentorno;

        int indexFicheroXMLAsignacionVictimas = 0;        
        Element infofichasignacionVictimas = null;
        String tiempoasignacion="";
        int    nrotiempoasignacion=0;
        String strnumtiempoasignacionEnSegundosUnDecimal;
        double doblenumtiempoasignacionEnSegundosUnDecimal=0.0;
        String nrovictimastotalasignadas="";

        boolean bandera = false;
        
        //Obtener el primer elemento del fichero xml de asignacion de victimas
        if (indexFicheroXMLAsignacionVictimas < nroItemsficheroXMLAsignacionVictimas){      	
        	infofichasignacionVictimas = ObtenerElementoItem(nodeLstficheroXMLAsignacionVictimas, indexFicheroXMLAsignacionVictimas);
		    //obtener valor tiempo de llegada en segundos con un decimal        	          	
        	tiempoasignacion = ObtenerElementoItem(infofichasignacionVictimas, "tiempoasignacion");
        	nrotiempoasignacion = Integer.valueOf(tiempoasignacion);           //PARA COMPARAR USO EL VALOR EN MILISEGUNDOS
      	    strnumtiempoasignacionEnSegundosUnDecimal = ObtenerTiempoEnSegundosUnDecimal(tiempoasignacion ,1);        	          	  
      	    doblenumtiempoasignacionEnSegundosUnDecimal = Double.valueOf(strnumtiempoasignacionEnSegundosUnDecimal).doubleValue();
		    //obtener valor del numero de victimas asignadas        	          	
      	    nrovictimastotalasignadas = ObtenerElementoItem(infofichasignacionVictimas, "nrovictimastotalasignadas");        	        	
      	  
      	    System.out.println("Acceso a FicheroXMLAsignacionVictimas : indice ->" + indexFicheroXMLAsignacionVictimas);
        	System.out.println("doblenumtiempoasignacionEnSegundosUnDecimal->" + doblenumtiempoasignacionEnSegundosUnDecimal);        	
        	System.out.println("nrovictimastotalasignadas->" + nrovictimastotalasignadas +"\n\n");     	          	        	
        }
        
        else{
        	//El fichero xml de asignacion de victimas no tiene items
        	System.out.println("El fichero xml de asignacion de victimas no tiene items");
        	System.out.println("Se va a proceder a crear el fichero solo con informacion de las victimas de llegada.....");        	
        	for (int i=0; i < nroItemsficheroXMLLlegadaVictimas; i++){        		
                //Obtendo elemento del fichero xml de llegada de victimas
          	  infofichllegadaVictimas= ObtenerElementoItem(nodeLstficheroXMLLlegadaVictimas, i);        	
          	  tiempollegada = ObtenerElementoItem(infofichllegadaVictimas, "tiempollegada");
          	  nrotiempollegada =  Integer.valueOf(tiempollegada);             //PARA COMPARAR USO EL VALOR EN MILISEGUNDOS        	  
  		      //obtener valor tiempo de llegada en segundos con un decimal        	  
          	  strnumtiempollegadaEnSegundosUnDecimal = ObtenerTiempoEnSegundosUnDecimal(tiempollegada ,1);        	          	  
          	  doblenumtiempollegadaEnSegundosUnDecimal = Double.valueOf(strnumtiempollegadaEnSegundosUnDecimal).doubleValue();
        		
		      //obtener valor nro de victimas que hay en el entorno en ese instante			  
        	  nrovictimasenentorno = ObtenerElementoItem(infofichllegadaVictimas, "nrovictimas");			  			  

			  escribeEstadisticaFicheroXMLLlegadaVictimasYAsignacionVictimasRobot(pwficheroXMLResultado,
					  					doblenumtiempollegadaEnSegundosUnDecimal,
					  					nrovictimasenentorno,
					  					"");  						                                                                     	  
        	}        	
        	return;   //finalizar la ejecucion
        }
                
        while(indexFicheroXMLLlegadaVictimas< nroItemsficheroXMLLlegadaVictimas){
        	
        	  bandera=false;
        	
              //Obtendo elemento del fichero xml de llegada de victimas
        	  infofichllegadaVictimas= ObtenerElementoItem(nodeLstficheroXMLLlegadaVictimas, indexFicheroXMLLlegadaVictimas);        	
        	  tiempollegada = ObtenerElementoItem(infofichllegadaVictimas, "tiempollegada");
        	  nrotiempollegada =  Integer.valueOf(tiempollegada);             //PARA COMPARAR USO EL VALOR EN MILISEGUNDOS        	  
		      //obtener valor tiempo de llegada en segundos con un decimal        	  
        	  strnumtiempollegadaEnSegundosUnDecimal = ObtenerTiempoEnSegundosUnDecimal(tiempollegada ,1);        	          	  
        	  doblenumtiempollegadaEnSegundosUnDecimal = Double.valueOf(strnumtiempollegadaEnSegundosUnDecimal).doubleValue();        	          	  			  			  
		      //obtener valor nro de victimas que hay en el entorno en ese instante			  
        	  nrovictimasenentorno = ObtenerElementoItem(infofichllegadaVictimas, "nrovictimas");			  			  
        	  
        	  System.out.println("Acceso a FicheroXMLLegadaVictimas : indice ->" + indexFicheroXMLLlegadaVictimas);        	  
			  System.out.println("tiempo (doble)->doblenumtiempollegadaEnSegundosUnDecimal->" + doblenumtiempollegadaEnSegundosUnDecimal);        	  
  			  System.out.println("nrovictimasenentorno (string)-> " + nrovictimasenentorno +"\n\n");
  			  
  			  if (nrotiempollegada <= nrotiempoasignacion){
  				escribeEstadisticaFicheroXMLLlegadaVictimasYAsignacionVictimasRobot(pwficheroXMLResultado,
  																					doblenumtiempollegadaEnSegundosUnDecimal, 
  																					nrovictimasenentorno,
  																					"");  						                                                             
  			  }
  			  else{   //nrotiempollegada > nrotiempoasignacion
  				  
  				      if(indexFicheroXMLAsignacionVictimas > (nroItemsficheroXMLAsignacionVictimas-1))  				        				      
  				      {
  				    	  System.out.println("  acabe el recorrido de las asignaciones ......... tengo que acabar aqui de completar las llegadas");
  				    	    				    	  
  		  				  escribeEstadisticaFicheroXMLLlegadaVictimasYAsignacionVictimasRobot(pwficheroXMLResultado,
  		  						  					doblenumtiempollegadaEnSegundosUnDecimal,
  		  						  					nrovictimasenentorno,
  		  						  					"");  						                                                               				    	    				    	  
  				      }
  				      
  				      //ESCRIBIR INFORMACION DEL FICHERO DE ASIGNACION DE VICTIMAS
  				      while((nrotiempoasignacion < nrotiempollegada) &&
  				            (indexFicheroXMLAsignacionVictimas <= (nroItemsficheroXMLAsignacionVictimas-1)))
  				      {
  				    	  
  		  				escribeEstadisticaFicheroXMLLlegadaVictimasYAsignacionVictimasRobot(pwficheroXMLResultado,
  		  					                                                                doblenumtiempoasignacionEnSegundosUnDecimal,
  		  					                                                                "",
  		  					                                                                nrovictimastotalasignadas);
  		  				
  		  				//OBTENER SIGUIENTE ELEMENTO DEL FICHERO DE ASIGNACION DE VICTIMAS
  				    	indexFicheroXMLAsignacionVictimas++;
  				    	
  				    	if (indexFicheroXMLAsignacionVictimas <= (nroItemsficheroXMLAsignacionVictimas-1)){
  			        	  infofichasignacionVictimas = ObtenerElementoItem(nodeLstficheroXMLAsignacionVictimas, indexFicheroXMLAsignacionVictimas);
  					      //obtener valor tiempo de llegada en segundos con un decimal        	          	
  			        	  tiempoasignacion = ObtenerElementoItem(infofichasignacionVictimas, "tiempoasignacion");
  			        	  nrotiempoasignacion = Integer.valueOf(tiempoasignacion);
  			      	      strnumtiempoasignacionEnSegundosUnDecimal = ObtenerTiempoEnSegundosUnDecimal(tiempoasignacion ,1);        	          	  
  			      	      doblenumtiempoasignacionEnSegundosUnDecimal = Double.valueOf(strnumtiempoasignacionEnSegundosUnDecimal).doubleValue();
  					      //obtener valor del numero de victimas asignadas        	          	
  			      	      nrovictimastotalasignadas = ObtenerElementoItem(infofichasignacionVictimas, "nrovictimastotalasignadas");        	        	

  			      	      System.out.println("Acceso a FicheroXMLAsignacionVictimas : indice ->" + indexFicheroXMLAsignacionVictimas);  			      	      
  			        	  System.out.println("doblenumtiempoasignacionEnSegundosUnDecimal->" + doblenumtiempoasignacionEnSegundosUnDecimal);        	
  			        	  System.out.println("nrovictimastotalasignadas->" + nrovictimastotalasignadas + "\n\n");
  			        	  
  			        	  bandera = true;  //no hay que incrementar el contador indexFicheroXMLLlegadaVictimas en el curso de ciclo actual
  				    	}
  				      }  				    				  
  			  } 
  			  
              //PARA OBTENER SIGUIENTE ELEMENTO DEL FICHERO DE LLEGADA DE VICTIMAS
  			  if (bandera==false)  			  
  			      indexFicheroXMLLlegadaVictimas++;        
   	    }//fin while(indexFicheroXMLLlegadaVictimas< nroItemsficheroXMLLlegadaVictimas)
	
        
        //ESCRIBIR EL RESTO DE ANOTACIONES DE ASIGNACIONES QUE QUEDAN POR RECORRER   
        if(indexFicheroXMLAsignacionVictimas <= (nroItemsficheroXMLAsignacionVictimas-1)){    	 
    	    for (int indice = indexFicheroXMLAsignacionVictimas; indice<=(nroItemsficheroXMLAsignacionVictimas-1); indice++){    		 
	        	infofichasignacionVictimas = ObtenerElementoItem(nodeLstficheroXMLAsignacionVictimas, indice);
			    //obtener valor tiempo de llegada en segundos con un decimal        	          	
	        	tiempoasignacion = ObtenerElementoItem(infofichasignacionVictimas, "tiempoasignacion");
	        	nrotiempoasignacion = Integer.valueOf(tiempoasignacion);
	      	    strnumtiempoasignacionEnSegundosUnDecimal = ObtenerTiempoEnSegundosUnDecimal(tiempoasignacion ,1);        	          	  
	      	    doblenumtiempoasignacionEnSegundosUnDecimal = Double.valueOf(strnumtiempoasignacionEnSegundosUnDecimal).doubleValue();
			    //obtener valor del numero de victimas asignadas        	          	
	      	    nrovictimastotalasignadas = ObtenerElementoItem(infofichasignacionVictimas, "nrovictimastotalasignadas");       	        	

	      	    System.out.println("Acceso a FicheroXMLAsignacionVictimas : indice ->" + indexFicheroXMLAsignacionVictimas);  			      	      	      	    
	      	    System.out.println("doblenumtiempoasignacionEnSegundosUnDecimal->" + doblenumtiempoasignacionEnSegundosUnDecimal);        	
	        	System.out.println("nrovictimastotalasignadas->" + nrovictimastotalasignadas + "\n\n");
	      	    
	  			escribeEstadisticaFicheroXMLLlegadaVictimasYAsignacionVictimasRobot(pwficheroXMLResultado,
	  																				doblenumtiempoasignacionEnSegundosUnDecimal,
	  																				"",
	  																				nrovictimastotalasignadas);	      	    	      	    
    	    }    	     	 
        }
     
        //CERRAR EL FICHERO PARA QUE SE PRODUZCA LA ESCRITURA
        pwficheroXMLResultado.print("</sequence>");
        pwficheroXMLResultado.close();
        
	}//FIN DEL METODO CrearFicheroXMLEstadisticaLlegadaYAsignacionVictimas
	
	
	//********************************************************************************
	// METODOS AUXILIARES DE ESTA CLASE
	//********************************************************************************
	
    //Obtendo elemento del NodeList obtenido a partir de un fichero xml
	public static Element ObtenerElementoItem(NodeList nodeLst, int indice){
    	  Element info=null;
	      Node fstNode = nodeLst.item(indice);
	      if (fstNode.getNodeType() == Node.ELEMENT_NODE){
		      info = (Element) fstNode;
	      }
	      return info;	      
	}


    //obtener valor asociado al tag. Por ejemplo <tiempo>12</tiempo> devuelve 12 
	public static String ObtenerElementoItem(Element info, String tag){
		  NodeList idNmElmntLst = info.getElementsByTagName(tag);
		  Element idNmElmnt = (Element) idNmElmntLst.item(0);
		  NodeList idNm = idNmElmnt.getChildNodes();					  
		  String valor = ((Node)idNm.item(0)).getNodeValue();
		  return valor;				
	}
	

    //obtener cadena con valor en segundos con nrodecimales decimales a partir de una cadena con valor en milisegundos 
	public static String ObtenerTiempoEnSegundosUnDecimal(String strMilisegundos, int nrodecimales){
		  int numtiempoEnMiliSegundos = Integer.parseInt(strMilisegundos);		  
		  double numtiempoEnSegundos = ((double)numtiempoEnMiliSegundos / 1000);		  		  
		  String strnumtiempoEnSegundos = "" + numtiempoEnSegundos;		  		  
		  int posPunto = strnumtiempoEnSegundos.indexOf('.');
		  String strnumtiempoEnSegundosUnDecimal = strnumtiempoEnSegundos.substring(0, posPunto+1+nrodecimales);			  
		  return strnumtiempoEnSegundosUnDecimal;
	}
	
    private static void escribeEstadisticaFicheroXMLLlegadaVictimasYAsignacionVictimasRobot( PrintWriter pw,    
    		                                                                                 double tiempo, 
    		                                                                                 String NroVictimasEnEntorno, 
    		                                                                                 String NroVictimasAsignadas) {
	    String ident1="   ";

    	String cadena =        "<item>" + "\n";
    	cadena = cadena + ident1 + "<tiempo>" + tiempo + "</tiempo>" + "\n";
    	cadena = cadena + ident1 + "<nrovictimasenentorno>" +  NroVictimasEnEntorno + "</nrovictimasenentorno>" + "\n";
    	cadena = cadena + ident1 + "<nrovictimastotalasignadas>" +  NroVictimasAsignadas + "</nrovictimastotalasignadas>" + "\n";    	
    	cadena = cadena  +     "</item>" + "\n";
    	pw.print(cadena);    	
}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	    String ident1="   ";
	    String ident2="      ";
		
		PrintWriter pwficheroXMLResultado=null;         //Para cada instante de tiempo tomado tendra un item con formato Tiempo, Nro Victimas en el entorno Sistema, Nro Victimas Asignadas a los robots
                                                        //Alguno de los numeros sera vacio
		
		String ficheroXMLLlegadaVictimas = icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaVictimas;		
		String ficheroXMLAsignacionVictimas = icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasAsignacionVictimas;
		
		String ficheroXMLResultado = icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaYAsignacionVictimas;
		
    	try {
    		     pwficheroXMLResultado = new PrintWriter(new FileOutputStream(ficheroXMLResultado));
   			     pwficheroXMLResultado.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
   			     pwficheroXMLResultado.println("<sequence>");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	    	
		Document docficheroXMLLlegadaVictimas=null;				
        try {
			  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			  docficheroXMLLlegadaVictimas = dBuilder.parse(new File(ficheroXMLLlegadaVictimas));
			  docficheroXMLLlegadaVictimas.getDocumentElement().normalize();
        } catch (Exception e) {
             e.printStackTrace();
        }        
        NodeList nodeLstficheroXMLLlegadaVictimas = docficheroXMLLlegadaVictimas.getElementsByTagName("item");        
        int nroItemsficheroXMLLlegadaVictimas = nodeLstficheroXMLLlegadaVictimas.getLength();
         
        
		Document docficheroXMLAsignacionVictimas=null;				
        try {
			  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			  docficheroXMLAsignacionVictimas = dBuilder.parse(new File(ficheroXMLAsignacionVictimas));
			  docficheroXMLAsignacionVictimas.getDocumentElement().normalize();
        } catch (Exception e) {
             e.printStackTrace();
        }
        NodeList nodeLstficheroXMLAsignacionVictimas = docficheroXMLAsignacionVictimas.getElementsByTagName("item");                
        int nroItemsficheroXMLAsignacionVictimas = nodeLstficheroXMLAsignacionVictimas.getLength();

        System.out.println("nroItemsficheroXMLLlegadaVictimas->" + nroItemsficheroXMLLlegadaVictimas);        
        System.out.println("nroItemsficheroXMLAsignacionVictimas->" + nroItemsficheroXMLAsignacionVictimas + "\n\n");
        
        int indexFicheroXMLLlegadaVictimas = 0;        
        Element infofichllegadaVictimas = null;
        String tiempollegada="";
        int nrotiempollegada=0;
        String strnumtiempollegadaEnSegundosUnDecimal="";
        double doblenumtiempollegadaEnSegundosUnDecimal;
        String nrovictimasenentorno;

        int indexFicheroXMLAsignacionVictimas = 0;        
        Element infofichasignacionVictimas = null;
        String tiempoasignacion="";
        int    nrotiempoasignacion=0;
        String strnumtiempoasignacionEnSegundosUnDecimal;
        double doblenumtiempoasignacionEnSegundosUnDecimal=0.0;
        String nrovictimastotalasignadas="";

        boolean bandera = false;
        
        //Obtener el primer elemento del fichero xml de asignacion de victimas
        if (indexFicheroXMLAsignacionVictimas < nroItemsficheroXMLAsignacionVictimas){      	
        	infofichasignacionVictimas = ObtenerElementoItem(nodeLstficheroXMLAsignacionVictimas, indexFicheroXMLAsignacionVictimas);
		    //obtener valor tiempo de llegada en segundos con un decimal        	          	
        	tiempoasignacion = ObtenerElementoItem(infofichasignacionVictimas, "tiempoasignacion");
        	nrotiempoasignacion = Integer.valueOf(tiempoasignacion);           //PARA COMPARAR USO EL VALOR EN MILISEGUNDOS
      	    strnumtiempoasignacionEnSegundosUnDecimal = ObtenerTiempoEnSegundosUnDecimal(tiempoasignacion ,1);        	          	  
      	    doblenumtiempoasignacionEnSegundosUnDecimal = Double.valueOf(strnumtiempoasignacionEnSegundosUnDecimal).doubleValue();
		    //obtener valor del numero de victimas asignadas        	          	
      	    nrovictimastotalasignadas = ObtenerElementoItem(infofichasignacionVictimas, "nrovictimastotalasignadas");        	        	
      	  
      	    System.out.println("Acceso a FicheroXMLAsignacionVictimas : indice ->" + indexFicheroXMLAsignacionVictimas);
        	System.out.println("doblenumtiempoasignacionEnSegundosUnDecimal->" + doblenumtiempoasignacionEnSegundosUnDecimal);        	
        	System.out.println("nrovictimastotalasignadas->" + nrovictimastotalasignadas +"\n\n");     	          	        	
        }
        
        else{
        	//El fichero xml de asignacion de victimas no tiene items
        	System.out.println("El fichero xml de asignacion de victimas no tiene items");
        	System.out.println("Se va a proceder a crear el fichero solo con informacion de las victimas de llegada.....");        	
        	for (int i=0; i < nroItemsficheroXMLLlegadaVictimas; i++){        		
                //Obtendo elemento del fichero xml de llegada de victimas
          	  infofichllegadaVictimas= ObtenerElementoItem(nodeLstficheroXMLLlegadaVictimas, i);        	
          	  tiempollegada = ObtenerElementoItem(infofichllegadaVictimas, "tiempollegada");
          	  nrotiempollegada =  Integer.valueOf(tiempollegada);             //PARA COMPARAR USO EL VALOR EN MILISEGUNDOS        	  
  		      //obtener valor tiempo de llegada en segundos con un decimal        	  
          	  strnumtiempollegadaEnSegundosUnDecimal = ObtenerTiempoEnSegundosUnDecimal(tiempollegada ,1);        	          	  
          	  doblenumtiempollegadaEnSegundosUnDecimal = Double.valueOf(strnumtiempollegadaEnSegundosUnDecimal).doubleValue();
        		
		      //obtener valor nro de victimas que hay en el entorno en ese instante			  
        	  nrovictimasenentorno = ObtenerElementoItem(infofichllegadaVictimas, "nrovictimas");			  			  

			  escribeEstadisticaFicheroXMLLlegadaVictimasYAsignacionVictimasRobot(pwficheroXMLResultado,
					  					doblenumtiempollegadaEnSegundosUnDecimal,
					  					nrovictimasenentorno,
					  					"");  						                                                                     	  
        	}        	
        	return;   //finalizar la ejecucion
        }

                
        while(indexFicheroXMLLlegadaVictimas< nroItemsficheroXMLLlegadaVictimas){
        	
        	  bandera=false;
        	
              //Obtendo elemento del fichero xml de llegada de victimas
        	  infofichllegadaVictimas= ObtenerElementoItem(nodeLstficheroXMLLlegadaVictimas, indexFicheroXMLLlegadaVictimas);        	
        	  tiempollegada = ObtenerElementoItem(infofichllegadaVictimas, "tiempollegada");
        	  nrotiempollegada =  Integer.valueOf(tiempollegada);             //PARA COMPARAR USO EL VALOR EN MILISEGUNDOS        	  
		      //obtener valor tiempo de llegada en segundos con un decimal        	  
        	  strnumtiempollegadaEnSegundosUnDecimal = ObtenerTiempoEnSegundosUnDecimal(tiempollegada ,1);        	          	  
        	  doblenumtiempollegadaEnSegundosUnDecimal = Double.valueOf(strnumtiempollegadaEnSegundosUnDecimal).doubleValue();        	          	  			  			  
		      //obtener valor nro de victimas que hay en el entorno en ese instante			  
        	  nrovictimasenentorno = ObtenerElementoItem(infofichllegadaVictimas, "nrovictimas");			  			  
        	  
        	  System.out.println("Acceso a FicheroXMLLegadaVictimas : indice ->" + indexFicheroXMLLlegadaVictimas);        	  
			  System.out.println("tiempo (doble)->doblenumtiempollegadaEnSegundosUnDecimal->" + doblenumtiempollegadaEnSegundosUnDecimal);        	  
  			  System.out.println("nrovictimasenentorno (string)-> " + nrovictimasenentorno +"\n\n");
  			  
  			  if (nrotiempollegada <= nrotiempoasignacion){
  				escribeEstadisticaFicheroXMLLlegadaVictimasYAsignacionVictimasRobot(pwficheroXMLResultado,
  																					doblenumtiempollegadaEnSegundosUnDecimal, 
  																					nrovictimasenentorno,
  																					"");  						                                                             
  			  }
  			  else{   //nrotiempollegada > nrotiempoasignacion
  				  
  				      if(indexFicheroXMLAsignacionVictimas > (nroItemsficheroXMLAsignacionVictimas-1))  				        				      
  				      {
  				    	  System.out.println("  acabe el recorrido de las asignaciones ......... tengo que acabar aqui de completar las llegadas");
  				    	    				    	  
  		  				  escribeEstadisticaFicheroXMLLlegadaVictimasYAsignacionVictimasRobot(pwficheroXMLResultado,
  		  						  					doblenumtiempollegadaEnSegundosUnDecimal,
  		  						  					nrovictimasenentorno,
  		  						  					"");  						                                                               				    	    				    	  
  				      }
  				      
  				      //ESCRIBIR INFORMACION DEL FICHERO DE ASIGNACION DE VICTIMAS
  				      while((nrotiempoasignacion < nrotiempollegada) &&
  				            (indexFicheroXMLAsignacionVictimas <= (nroItemsficheroXMLAsignacionVictimas-1)))
  				      {
  				    	  
  		  				escribeEstadisticaFicheroXMLLlegadaVictimasYAsignacionVictimasRobot(pwficheroXMLResultado,
  		  					                                                                doblenumtiempoasignacionEnSegundosUnDecimal,
  		  					                                                                "",
  		  					                                                                nrovictimastotalasignadas);
  		  				
  		  				//OBTENER SIGUIENTE ELEMENTO DEL FICHERO DE ASIGNACION DE VICTIMAS
  				    	indexFicheroXMLAsignacionVictimas++;
  				    	
  				    	if (indexFicheroXMLAsignacionVictimas <= (nroItemsficheroXMLAsignacionVictimas-1)){
  			        	  infofichasignacionVictimas = ObtenerElementoItem(nodeLstficheroXMLAsignacionVictimas, indexFicheroXMLAsignacionVictimas);
  					      //obtener valor tiempo de llegada en segundos con un decimal        	          	
  			        	  tiempoasignacion = ObtenerElementoItem(infofichasignacionVictimas, "tiempoasignacion");
  			        	  nrotiempoasignacion = Integer.valueOf(tiempoasignacion);
  			      	      strnumtiempoasignacionEnSegundosUnDecimal = ObtenerTiempoEnSegundosUnDecimal(tiempoasignacion ,1);        	          	  
  			      	      doblenumtiempoasignacionEnSegundosUnDecimal = Double.valueOf(strnumtiempoasignacionEnSegundosUnDecimal).doubleValue();
  					      //obtener valor del numero de victimas asignadas        	          	
  			      	      nrovictimastotalasignadas = ObtenerElementoItem(infofichasignacionVictimas, "nrovictimastotalasignadas");        	        	

  			      	      System.out.println("Acceso a FicheroXMLAsignacionVictimas : indice ->" + indexFicheroXMLAsignacionVictimas);  			      	      
  			        	  System.out.println("doblenumtiempoasignacionEnSegundosUnDecimal->" + doblenumtiempoasignacionEnSegundosUnDecimal);        	
  			        	  System.out.println("nrovictimastotalasignadas->" + nrovictimastotalasignadas + "\n\n");
  			        	  
  			        	  bandera = true;  //no hay que incrementar el contador indexFicheroXMLLlegadaVictimas en el curso de ciclo actual
  				    	}
  				      }  				    				  
  			  } 
  			  
              //PARA OBTENER SIGUIENTE ELEMENTO DEL FICHERO DE LLEGADA DE VICTIMAS
  			  if (bandera==false)  			  
  			      indexFicheroXMLLlegadaVictimas++;        
   	 }//fin while(indexFicheroXMLLlegadaVictimas< nroItemsficheroXMLLlegadaVictimas)
	
        
     //ESCRIBIR EL RESTO DE ANOTACIONES DE ASIGNACIONES QUE QUEDAN POR RECORRER   
     if(indexFicheroXMLAsignacionVictimas <= (nroItemsficheroXMLAsignacionVictimas-1)){    	 
    	 for (int indice = indexFicheroXMLAsignacionVictimas; indice<=(nroItemsficheroXMLAsignacionVictimas-1); indice++){    		 
	        	infofichasignacionVictimas = ObtenerElementoItem(nodeLstficheroXMLAsignacionVictimas, indice);
			    //obtener valor tiempo de llegada en segundos con un decimal        	          	
	        	tiempoasignacion = ObtenerElementoItem(infofichasignacionVictimas, "tiempoasignacion");
	        	nrotiempoasignacion = Integer.valueOf(tiempoasignacion);
	      	    strnumtiempoasignacionEnSegundosUnDecimal = ObtenerTiempoEnSegundosUnDecimal(tiempoasignacion ,1);        	          	  
	      	    doblenumtiempoasignacionEnSegundosUnDecimal = Double.valueOf(strnumtiempoasignacionEnSegundosUnDecimal).doubleValue();
			    //obtener valor del numero de victimas asignadas        	          	
	      	    nrovictimastotalasignadas = ObtenerElementoItem(infofichasignacionVictimas, "nrovictimastotalasignadas");       	        	

	      	    System.out.println("Acceso a FicheroXMLAsignacionVictimas : indice ->" + indexFicheroXMLAsignacionVictimas);  			      	      	      	    
	      	    System.out.println("doblenumtiempoasignacionEnSegundosUnDecimal->" + doblenumtiempoasignacionEnSegundosUnDecimal);        	
	        	System.out.println("nrovictimastotalasignadas->" + nrovictimastotalasignadas + "\n\n");
	      	    
	  			escribeEstadisticaFicheroXMLLlegadaVictimasYAsignacionVictimasRobot(pwficheroXMLResultado,
	  																				doblenumtiempoasignacionEnSegundosUnDecimal,
	  																				"",
	  																				nrovictimastotalasignadas);	      	    	      	    
    	 }    	     	 
     }
     
     //CERRAR EL FICHERO PARA QUE SE PRODUZCA LA ESCRITURA
     pwficheroXMLResultado.print("</sequence>");
     pwficheroXMLResultado.close();   
		
	}//fin del main
	
}



////////if (nroItemsficheroXMLAsignacionVictimas>0){	  	  
///////}


//System.out.println("numtiempollegada->" + Integer.MAX_VALUE);  596 HORAS DE SIMULACION ....   24 DIAS
