/*
 *  
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFconGesAcciones.estadosyTransiciones;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.ComprobadorRutasEntidades;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 *  Clase que convierte un fichero XML en una tabla de estados valida para un
 *  autmata. Extrae inputs y transiciones para incorporarlos a la tabla de estados
 *
 *@author     Francisco J Garijo
 *@created    20 de septiembre de 2001
 *@modified   20 de Febrero  de 2014
 */
public class XMLParserTablaEstadosAutomataEFinputObj {

	/**
	 * @uml.property  name="document"
	 * @uml.associationEnd  
	 */
	Document document;
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    private TablaEstadosAutomataEFinputObjts tablaEstados;
    private String identFicheroDescAutomata;
    private String rutaCarpetaAcciones;
    private String identClaseAccionesSemanticas;
    private String identAgtePropietarioAutomata;
    private boolean buscadoClaseAccionesSemanticas = false;
    private Class claseAccionesSemanticas;
    private  ComprobadorRutasEntidades comprobadorRutas;
	/**
	 *  Constructor
	 */
	public XMLParserTablaEstadosAutomataEFinputObj() { }
        
        public XMLParserTablaEstadosAutomataEFinputObj(String identAgtePropietario) {
        identAgtePropietarioAutomata =identAgtePropietario;
        }


	/**
	 * Analiza el fichero con la descripcion de la tabla y extrae inputs y transiciones
         * Convierte el fichero dado en una tablaEstados y una tabla de transiciones que utilizara el automata
	 *
	 *@param  nombreFich  Nombre del fichero a convertir
         * @param rutaCarpetaAccs
	 *@return             Tabla del autmata extrado de ese fichero
	 */
	public TablaEstadosAutomataEFinputObjts extraeTablaEstadosDesdeFicheroXML(String nombreFich, String rutaCarpetaAccs){
		 this.tablaEstados = new TablaEstadosAutomataEFinputObjts();
                 this.identFicheroDescAutomata = nombreFich;
                 this.rutaCarpetaAcciones = rutaCarpetaAccs;
                 this.comprobadorRutas= new ComprobadorRutasEntidades();

		// Esta parte es genrica para cualquier parsing XML
		// parsing XML

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		//factory.setNamespaceAware(true);
        URL nombre1;
        InputStream nombre;
		try
		{
		DocumentBuilder builder = factory.newDocumentBuilder();
                Class	clase = this.getClass();
                //nombre1 =  clase.getResource(nombreFich);
                nombre1 =  clase.getResource(nombreFich);
                nombre = clase.getResourceAsStream(nombreFich);
		document = builder.parse(this.getClass().getResourceAsStream(nombreFich));
					//new File(nombreFich));
		}
		catch (SAXException sxe)
		{
			// Error generated during parsing
			Exception x = sxe;
			if (sxe.getException() != null)
			{
				x = sxe.getException();
			}
         System.out.println(" Se ha producido un error al procesar el fichero XML: "+nombreFich );
			x.printStackTrace();

		}
		catch (ParserConfigurationException pce)
		{
			// Parser with specified options can't be built
			System.out.println(" No se pudo construir un analizador XML con las opciones especificadas referido al fichero XML: "+nombreFich );
			pce.printStackTrace();
		}
		catch (IOException ioe)
		{
			System.out.println("Error de lectura en el fichero XML. Esta usted seguro de que el fichero: "+nombreFich+" esta en esa ruta ? " );
			ioe.printStackTrace();
		}

		// Esta parte es dependiente del tipo del documento que hemos creado

		// ahora tengo el documento XML
		// referencias de ayuda en los recorridos
		org.w3c.dom.Node nodo;
		org.w3c.dom.NamedNodeMap mapaNombreNodo;
		org.w3c.dom.NodeList listaInfoTransiciones;
		String idNodo;

		// capturo el estado inicial (SE QUE SOLO HAY UNO, LAS COMPROBACIONES SE HACEN DESDE EL EXTERIOR)
		org.w3c.dom.NodeList nlInicial = document.getElementsByTagName(NombresPredefinidos.ESTADO_INICIAL);

		nodo = nlInicial.item(0);
		mapaNombreNodo = nodo.getAttributes();
		// este es el identificador del nodo inicial
		idNodo = mapaNombreNodo.getNamedItem(NombresPredefinidos.IDENT_NODO_INICIAL_AUTOMATA_EF).getNodeValue();
		tablaEstados.putEstado(idNodo, NombresPredefinidos.AUTOMATA_EF_TIPO_ESTADO_INICIAL);
		// ahora obtenemos las transiciones desde el estado inicial
		// SE QUE TIENE AL MENOS UNA TRANSICION y que todos los hijos seran transiciones
		listaInfoTransiciones = nodo.getChildNodes();
		procesarListaNodosTransicion(idNodo, listaInfoTransiciones);

		// capturo subestados
		org.w3c.dom.NodeList nlIntermedios = document.getElementsByTagName(NombresPredefinidos.NOMBRE_ESTADO_AUTOMATA_EF);

		// bucle que recorre estados intermedios
		for (int i = 0; i < nlIntermedios.getLength(); i++)
		{
			nodo = nlIntermedios.item(i);
			mapaNombreNodo = nodo.getAttributes();
			// este es el identificador del nodo inicial
			idNodo = mapaNombreNodo.getNamedItem(NombresPredefinidos.IDENT_NODO_INTERMEDIO_AUTOMATA_EF).getNodeValue();
			tablaEstados.putEstado(idNodo, NombresPredefinidos.AUTOMATA_EF_TIPO_ESTADO_INTERMEDIO);

			// SE QUE TIENE AL MENOS UNA TRANSICION y que todos los hijos seran transiciones
			listaInfoTransiciones = nodo.getChildNodes();
			procesarListaNodosTransicion(idNodo, listaInfoTransiciones);
		}

		// capturo estados finales
		org.w3c.dom.NodeList nlFinales = document.getElementsByTagName(NombresPredefinidos.IDENT_NODO_FINAL_AUTOMATA_EF);
		// bucle que recorre estados finales
		for (int i = 0; i < nlFinales.getLength(); i++)
		{
			nodo = nlFinales.item(i);
			mapaNombreNodo = nodo.getAttributes();
			// este es el identificador del nodo inicial
			idNodo = mapaNombreNodo.getNamedItem(NombresPredefinidos.IDENT_NODO_FINAL_AUTOMATA_EF).getNodeValue();
			tablaEstados.putEstado(idNodo, NombresPredefinidos.TIPO_ESTADO_FINAL_AUTOMATA_EF);

			// SE QUE TIENE AL MENOS UNA TRANSICION y que todos los hijos seran transiciones
			listaInfoTransiciones = nodo.getChildNodes();
			procesarListaNodosTransicion(idNodo, listaInfoTransiciones);
		}

      // capturo ahora transiciones universales
  		org.w3c.dom.NodeList nlUniversal = document.getElementsByTagName(NombresPredefinidos.NOMBRE_TRANSICION_UNIVERSAL_AUTOMATA_EF);
		// bucle que recorre transiciones universales
                if (nlUniversal.getLength()>0)
//                    transicion = extraerInfoTransicion(nodo);
//                    
		for (int i = 0; i < nlUniversal.getLength(); i++)
		{
			nodo = nlUniversal.item(i);
                        if (nodo !=null){
//			mapaNombreNodo = nodo.getAttributes();
			// extraemos los atributos
//			String input = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_INPUT_AUTOMATA_EF).getNodeValue();
//			String accion = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_ACCION_AUTOMATA_EF).getNodeValue();
//                        String estadoSig = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_ESTADO_SIGUIENTE_AUTOMATA_EF).getNodeValue();
                        TransicionAutomataEF transicion = extraerInfoTransicion(nodo);
                        if (transicion!=null)
                        tablaEstados.putTransicionUniversal(transicion);
                        }
		}

		return tablaEstados;
	}


	/**
	 *  Mtodo auxiliar que procesa las transiciones de un estado determinado
	 *  estado y la anyade a la tabla de estados
	 *@param  idEstado           Estado al que pertenecen las transiciones
	 *@param  listaTransiciones  Lista con las transiciones de ese estado
	 *@param  
	 *      nuevas transiciones que se detectan
	 */
//	private void procesarListaNodosTransicion(String idEstado, org.w3c.dom.NodeList listaTransiciones)
//	{
//		org.w3c.dom.Node nodo;
//		org.w3c.dom.NamedNodeMap mapaNombreNodo;
//
//		for (int i = 0; i < listaTransiciones.getLength(); i++)
//		{
//			nodo = listaTransiciones.item(i);
//			if (nodo.getNodeName().equalsIgnoreCase(NombresPredefinidos.NOMBRE_TRANSICION_AUTOMATA_EF))
//			{
//				mapaNombreNodo = nodo.getAttributes();
//				String input = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_INPUT_AUTOMATA_EF).getNodeValue();
//				String estadoSig = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_ESTADO_SIGUIENTE_AUTOMATA_EF).getNodeValue();
//				tablaEstados.addTransicion(idEstado, input,"accionAquitar", estadoSig);
//			}
//		}
//
//	}
        private void procesarListaNodosTransicion (String idEstado,org.w3c.dom.NodeList listaTransiciones) {
		org.w3c.dom.Node nodoTransicion, nodoAux;
		org.w3c.dom.NamedNodeMap mapaNombreNodo;
                String input,accion,estadoSig,modo ;
                Integer longLista = listaTransiciones.getLength();
		for (int i = 1; i < listaTransiciones.getLength(); i++) {
			nodoTransicion = listaTransiciones.item(i);
                        String identNodo = nodoTransicion.getNodeName();
			if (nodoTransicion.getNodeName().equalsIgnoreCase(NombresPredefinidos.NOMBRE_TRANSICION_AUTOMATA_EF)) {
//				mapaNombreNodo = nodoTransicion.getAttributes();
//				input = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_INPUT_AUTOMATA_EF)
//						.getNodeValue();
//                                // consideramos la posiblidad de que las acciones no se especificque y las definimos como vacias
//                                nodoAux = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_ACCION_AUTOMATA_EF);
//                                if (nodoAux == null)accion=NombresPredefinidos.ACCION_VACIA_AUTOMATA_EF;
//                                 else accion = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_ACCION_AUTOMATA_EF).getNodeValue();
//                                estadoSig = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_ESTADO_SIGUIENTE_AUTOMATA_EF).getNodeValue();
//                                // consideramos la posiblidad de que la modalidad sea vacia
//                                nodoAux = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_MODO_TRANSICION_AUTOMATA_EF);
//                                if (nodoAux == null) modo = NombresPredefinidos.NOMBRE_MODO_TRANSICION_AUTOMATA_EF;                           
//                                else modo = nodoAux.getNodeValue();
//				tablaEstados.addTransicion(idEstado, input, accion, estadoSig,modo);
                               TransicionAutomataEF transicion = extraerInfoTransicion(nodoTransicion);
                               if(transicion !=null)
                                tablaEstados.putTransicion(idEstado,transicion );
			}
                }
        }

        
         private  TransicionAutomataEF extraerInfoTransicion(org.w3c.dom.Node nodoTransicion) {
            org.w3c.dom.Node  nodoAux;
            org.w3c.dom.NamedNodeMap mapaNombreNodo;
            String identNodo,input,accion,estadoSig,modo ;
            identNodo = nodoTransicion.getNodeName();
            Boolean hayErrores = false;
//            Class claseAccion = null;
            // Pocesar el encabezamiento de la transicion y el input
            if (identNodo.equalsIgnoreCase(NombresPredefinidos.NOMBRE_TRANSICION_AUTOMATA_EF)||
               (identNodo.equalsIgnoreCase(NombresPredefinidos.NOMBRE_TRANSICION_UNIVERSAL_AUTOMATA_EF))) {
                mapaNombreNodo = nodoTransicion.getAttributes();
                input = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_INPUT_AUTOMATA_EF)
                                .getNodeValue();
//                if( input==null){// el input no puede ser null
//                                mensajeError(identNodo, "El input no puede ser nulo, deber ser una cadena de caracteres");
//                                hayErrores=true;
//                }
            }else { // no se continua con el analisis
                mensajeError(identNodo, "Una transicion debe comenzar por el identificador del atributo de transición");
                return null;
            }
                //  Se procesan las acciones
//                consideramos la posiblidad de que las acciones no se especificque y las definimos como vacias
                nodoAux = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_ACCION_AUTOMATA_EF);
                
                if (nodoAux == null)accion=NombresPredefinidos.ACCION_VACIA_AUTOMATA_EF;
                else{
                    accion = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_ACCION_AUTOMATA_EF).getNodeValue();
//                   if (!existeClaseAccion(accion))hayErrores=true ;
//                     claseAccion = obtenerClaseAcciones(accion);
//                     if (claseAccion==null)hayErrores=true ;
                }
                // se procesa el estado siguiente
                estadoSig = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_ESTADO_SIGUIENTE_AUTOMATA_EF).getNodeValue();
                // consideramos la posiblidad de que la modalidad sea vacia
//                if (estadoSig == null){
//                    mensajeError(identNodo, "El estado siguiente no puede ser nulo, deber ser un identificador de estado");
//                    hayErrores=true;
//                }
                nodoAux = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_MODO_TRANSICION_AUTOMATA_EF);
                if (nodoAux == null) modo = NombresPredefinidos.NOMBRE_MODO_BLOQUEANTE_AUTOMATA_EF_SIN_ACCION;
                else modo = nodoAux.getNodeValue();
                return validarInfoTransicion(identNodo, input, accion, estadoSig, modo);
//                                           
//                else modo = nodoAux.getNodeValue();
//                if (hayErrores ) {
//                    mensajeError(nodoTransicion.toString(), " No se ha generado la transicion porque hay errores");
//                    return null;
//                }
//                return new TransicionAutomataEF(input, claseAccion, estadoSig,modo);   
            
         }
         private void mensajeError (String identNodo, String msgError){
             
             System.out.println("El nodo donde se debe extraer la informacion "+identNodo + msgError+ " revisar el fichero del automata" );
         }
       
         private Class obtenerClaseAcciones(String identClase) {
//        String msgInfoUsuario;
    //    rutaComportamiento = "/" +rutaComportamiento.replace(".", "/");
//        String nombreEntidad = rutaComportamiento.substring(rutaComportamiento.lastIndexOf(".")+1);
          String primerCaracter= identClase.substring(0,1);
          identClase = identClase.replaceFirst(primerCaracter, primerCaracter.toUpperCase());
//        String rutaAccionesSemanticas = rutaComportamiento+ "."+NombresPredefinidos.NOMBRE_ACCIONES_SEMANTICAS+nombreEntidad;
//          String rutaAccion = "icaro.aplicaciones.agentes.agenteAplicacionAccesoReactivo.comportamientoPrueba";
          if (this.rutaCarpetaAcciones!=null){
            identClase = this.rutaCarpetaAcciones+"."+ identClase;
          }
          try {
            Class claseAcciones = Class.forName(identClase);
            return claseAcciones;
          } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FactoriaAgenteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
//                mensajeError (identClase, " No se encuentra la clase especificada en la descripcion del automata" );
            String msgAviso = "Se esta buscando una clase: " + identClase + " que implemente las acciones del automata en la ruta :"+
             this.rutaCarpetaAcciones + "  pero no se ha encontrado. La accion se considerara un metodo de la clase Acciones Semanticas";
            System.out.println(msgAviso);
            return null;
//        throw new ExcepcionEnComponente ( "PatronAgenteReactivo", " No se encuentra la clase de acciones semanticas en la ruta :"+rutaComportamiento,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
          }
         }
         
       private TransicionAutomataEF validarInfoTransicion (String idEstado, String idInput, String idAccion,String estadoSguiente, String modoAccion) {
           boolean hayErrores = false;
            Class claseAccion = null;
            String metodo= null;
            boolean tipoTransicionEjecutarMetodoAS = false;
            // Pocesar el encabezamiento de la transicion y el input
           
                if( idInput==null){// el input no puede ser null
                                mensajeError(idEstado, "El input no puede ser nulo, deber ser una cadena de caracteres");
                                hayErrores=true;
                }
//            }else { // no se continua con el analisis
//                mensajeError(identNodo, "Una transicion debe comenzar por el identificador del atributo de transición");
//                return null;
            
                //  Se procesan las acciones
//                consideramos la posiblidad de que las acciones no se especificque y las definimos como vacias
//                nodoAux = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_ACCION_AUTOMATA_EF);
                
//                if (idAccion == null)claseAccion=NombresPredefinidos.ACCION_VACIA_AUTOMATA_EF;
                if (estadoSguiente == null){
                    mensajeError(idEstado, "El estado siguiente no puede ser nulo, deber ser un identificador de estado");
                    hayErrores=true;
                }
                  if ( idAccion == null || idAccion.matches(NombresPredefinidos.EXPR_REG_SIN_ACCION_EN_AUTOMATA_EF)){
                  // no se hace nada porque la clase ya es null
                  }else{
                      if (!buscadoClaseAccionesSemanticas){
//                          identClaseAccionesSemanticas = NombresPredefinidos.NOMBRE_ACCIONES_SEMANTICAS+identAgtePropietarioAutomata;
//                          claseAccionesSemanticas = obtenerClaseAcciones(identClaseAccionesSemanticas);
                         claseAccionesSemanticas = comprobadorRutas.obtenerClaseAccionesSemanticas(rutaCarpetaAcciones) ;
//                          if(claseAccionesSemanticas==null)claseAccionesSemanticas = obtenerClaseAcciones(NombresPredefinidos.NOMBRE_ACCIONES_SEMANTICAS);
                          buscadoClaseAccionesSemanticas = true;
                      }
                      // buscamos la clase y si no  la encontramos  suponemos que es un metodo de las acciones semanticas
                         claseAccion = comprobadorRutas.obtenerClaseAccionesReactivo(idAccion,rutaCarpetaAcciones);
                         if (claseAccion!=null ){}  // si la clase se ha encontrado no hace nada 
                         else if (claseAccionesSemanticas ==null){ // No se encuentra la clase y no existe clase acciones semanticas  
                                 hayErrores= true;
                                 String msgError = "Debe existir una clase: " + idAccion + " o una clase : " + identClaseAccionesSemanticas +
                                        " que implemente las acciones del automata en la ruta :"+this.rutaCarpetaAcciones + 
                                        "  pero no se han encontrado. Revisar la ruta definida y el fichero del automata";
                                System.out.println(msgError);
//                                 mensajeError(idEstado, " No se encuentra la clase : "+ idAccion);                        
                               }else {  // Existe la clase AS deberiamos validar que el identAcciones es un metodo de la clase
                                // pero lo dejamos para la ejecución
                                    claseAccion= this.claseAccionesSemanticas;
                                    metodo= idAccion;
                                    tipoTransicionEjecutarMetodoAS=true;
//                                    if (modoAccion == null) modoAccion = NombresPredefinidos.AUTOMATA_EF_NOMBRE_MODO_BLOQUEANTE;
                                    
                                   }
                      }
                // se procesa el estado siguiente
//                estadoSig = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_ESTADO_SIGUIENTE_AUTOMATA_EF).getNodeValue();
                // consideramos la posiblidad de que la modalidad sea vacia
                
////                nodoAux = mapaNombreNodo.getNamedItem(NombresPredefinidos.NOMBRE_MODO_TRANSICION_AUTOMATA_EF);                                 
//                else modo = nodoAux.getNodeValue();
                if (hayErrores ) {
                    return null;
                }
                if (modoAccion == null) modoAccion = NombresPredefinidos.AUTOMATA_EF_NOMBRE_MODO_BLOQUEANTE;
                if (tipoTransicionEjecutarMetodoAS) return new  TransicionAutomataEF(idInput, claseAccion,metodo, estadoSguiente, modoAccion);
                else return new TransicionAutomataEF(idInput, claseAccion, estadoSguiente, modoAccion);
                
       
    }
 
    private String obtenerRutaValidaAutomata (String rutaComportamiento){
    // La ruta del comportamiento no incluye la clase
    // Obtenemos la clase de AS en l aruta
    //   rutaComportamiento =File.separator+rutaComportamiento.replace(".", File.separator);
   //   String rutaBusqueda = NombresPredefinidos.RUTA_SRC+rutaComportamiento;
        String msgInfoUsuario;
        rutaComportamiento = "/" +rutaComportamiento.replace(".", "/");
         String rutaAutomata = rutaComportamiento +"/"+ NombresPredefinidos.FICHERO_AUTOMATA;
		InputStream input = this.getClass().getResourceAsStream(rutaAutomata);
//		logger.debug(rutaAutomata+"?"+ ((input != null) ? "  OK" : "  null"));
		if  (input != null) return rutaAutomata;
                else {
                    // intentamos otra politica de nombrado
                    String nombreEntidad = rutaComportamiento.substring(rutaComportamiento.lastIndexOf("/")+1);
                    String primerCaracter= nombreEntidad.substring(0,1);
                    nombreEntidad = nombreEntidad.replaceFirst(primerCaracter, primerCaracter.toUpperCase());
                    rutaAutomata = rutaComportamiento +"/automata"+nombreEntidad+".xml";
                    input = this.getClass().getResourceAsStream(rutaAutomata);
                    if  (input != null) return rutaAutomata;
                    else {
                    // la entidad no se encuentra o no esta definida 
                    msgInfoUsuario = " Error no se encuentra el fichero especificado  \n"+
                            "Para el comportamiento: " + rutaComportamiento + 
                            "En la ruta: " + rutaAutomata + "\n" +
                            "Verifique la existencia del fichero en el directorio src  \n";
//                    logger.fatal(msgInfoUsuario);
      //              throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra el fichero del automata  en la ruta :"+rutaAutomata,"Factoria del Agente Reactivo",this.getClass().getName()  );
                    return null;
                    }
                }
    }
         
        public static void main(String args[]) {
         XMLParserTablaEstadosAutomataEFinputObj prueba1 =   new XMLParserTablaEstadosAutomataEFinputObj("AccionesSemanticasAgenteAplicacionAcceso");
//         prueba1.extraeTablaEstadosDesdeFicheroXML("/icaro/infraestructura/entidadesBasicas/componentesBasicos/automatas/clasesImpAutomatas/automataPrueba.xml", null);
//         String rutaFichero = prueba1.obtenerRutaValidaAutomata (NombresPredefinidos.COMPORTAMIENTO_PORDEFECTO_INICIADOR);
//         prueba1.extraeTablaEstadosDesdeFicheroXML("/icaro/gestores/iniciador/automataPrueba.xml", null);
         //prueba1.extraeTablaEstadosDesdeFicheroXML("/icaro/pruebas/automataPruebas.xml", null);
         prueba1.extraeTablaEstadosDesdeFicheroXML("/icaro/aplicaciones/agentes/agenteAplicacionAccesoReactivo/comportamiento/automata.xml", "icaro.aplicaciones.agentes.agenteAplicacionAccesoReactivo.comportamiento");
        
        }
}
