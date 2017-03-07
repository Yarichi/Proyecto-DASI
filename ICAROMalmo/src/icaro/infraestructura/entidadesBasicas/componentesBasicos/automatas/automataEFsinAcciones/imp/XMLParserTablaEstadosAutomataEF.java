/*
 *  Copyright 2001 Telefnica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.imp;

import java.io.IOException;

import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 *  Clase que convierte un fichero XML en una tabla de estados vlida para un
 *  autmata
 *
 *@author     lvaro Rodrguez
 *@created    5 de septiembre de 2001
 *@modified   02 de Marzo de 2007
 */
public class XMLParserTablaEstadosAutomataEF {

	/**
	 * @uml.property  name="document"
	 * @uml.associationEnd  
	 */
	Document document;
    private Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());

	/**
	 *  Constructor
	 */
	public XMLParserTablaEstadosAutomataEF() { }


	/**
	 *  Convierte el fichero dado en una tablaEstadosControl
	 *
	 *@param  nombreFich  Nombre del fichero a convertir
	 *@return             Tabla del autmata extrado de ese fichero
	 */
	public TablaEstadosAutomataEF extraeTablaEstadosDesdeFicheroXML(String nombreFich)
	{
		TablaEstadosAutomataEF tabla = new TablaEstadosAutomataEF();

		// Esta parte es genrica para cualquier parsing XML
		// parsing XML

		DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();
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
         System.out.println("Se ha producido un error al procesar el fichero XML: "+nombreFich );
			x.printStackTrace();

		}
		catch (ParserConfigurationException pce)
		{
			// Parser with specified options can't be built
			System.out.println("No se pudo construir un analizador XML con las opciones especificadas referido al fichero XML: "+nombreFich );;
			pce.printStackTrace();
		}
		catch (IOException ioe)
		{
			System.out.println("Error de lectura en el fichero XML. Est usted seguro de que el fichero '"+nombreFich+"' est ah?" );
			ioe.printStackTrace();
		}

		// Esta parte es dependiente del tipo del documento que hemos creado

		// ahora tengo el documento XML
		// referencias de ayuda en los recorridos
		org.w3c.dom.Node nodo;
		org.w3c.dom.NamedNodeMap mapaNombreNodo;
		org.w3c.dom.NodeList listaNodos;
		String id;

		// capturo el estado inicial (SE QUE SOLO HAY UNO, LAS COMPROBACIONES SE HACEN DESDE EL EXTERIOR)
		org.w3c.dom.NodeList nlInicial = document.getElementsByTagName("estadoInicial");

		nodo = nlInicial.item(0);
		mapaNombreNodo = nodo.getAttributes();
		// este es el identificador del nodo inicial
		id = mapaNombreNodo.getNamedItem("idInicial").getNodeValue();
		tabla.putEstado(id, TablaEstadosAutomataEF.TIPO_DE_ESTADO_INICIAL);

		// ahora obtenemos las transiciones desde el estado inicial
		// SE QUE TIENE AL MENOS UNA TRANSICION y que todos los hijos seran transiciones
		listaNodos = nodo.getChildNodes();
		procesarListaNodosTransicion(id, listaNodos, tabla);

		// capturo subestados
		org.w3c.dom.NodeList nlIntermedios = document.getElementsByTagName("estado");

		// bucle que recorre estados intermedios
		for (int i = 0; i < nlIntermedios.getLength(); i++)
		{
			nodo = nlIntermedios.item(i);
			mapaNombreNodo = nodo.getAttributes();
			// este es el identificador del nodo inicial
			id = mapaNombreNodo.getNamedItem("idIntermedio").getNodeValue();
			tabla.putEstado(id, TablaEstadosAutomataEF.TIPO_DE_ESTADO_INTERMEDIO);

			// SE QUE TIENE AL MENOS UNA TRANSICION y que todos los hijos seran transiciones
			listaNodos = nodo.getChildNodes();
			procesarListaNodosTransicion(id, listaNodos, tabla);
		}

		// capturo estados finales
		org.w3c.dom.NodeList nlFinales = document.getElementsByTagName("estadoFinal");
		// bucle que recorre estados finales
		for (int i = 0; i < nlFinales.getLength(); i++)
		{
			nodo = nlFinales.item(i);
			mapaNombreNodo = nodo.getAttributes();
			// este es el identificador del nodo inicial
			id = mapaNombreNodo.getNamedItem("idFinal").getNodeValue();
			tabla.putEstado(id, TablaEstadosAutomataEF.TIPO_DE_ESTADO_FINAL);

			// SE QUE TIENE AL MENOS UNA TRANSICION y que todos los hijos seran transiciones
			listaNodos = nodo.getChildNodes();
			procesarListaNodosTransicion(id, listaNodos, tabla);
		}

      // capturo ahora transiciones universales
  		org.w3c.dom.NodeList nlUniversal = document.getElementsByTagName("transicionUniversal");
		// bucle que recorre transiciones universales
		for (int i = 0; i < nlUniversal.getLength(); i++)
		{
			nodo = nlUniversal.item(i);
			mapaNombreNodo = nodo.getAttributes();
			// extraemos los atributos
			String input = mapaNombreNodo.getNamedItem("input").getNodeValue();
			String estadoSig = mapaNombreNodo.getNamedItem("estadoSiguiente").getNodeValue();

         tabla.putTransicionUniversal(input, estadoSig);
		}

		return tabla;
	}


	/**
	 *  Mtodo auxiliar que procesa las transiciones de un estado determinado
	 *
	 *@param  idEstado           Estado al que pertenecen las transiciones
	 *@param  listaTransiciones  Lista con las transiciones de ese estado
	 *@param  tablaEstados       Tabla de estados / transiciones para aadir las
	 *      nuevas transiciones que se detectan
	 */
	private void procesarListaNodosTransicion(String idEstado, org.w3c.dom.NodeList listaTransiciones, TablaEstadosAutomataEF tablaEstados)
	{
		org.w3c.dom.Node nodo;
		org.w3c.dom.NamedNodeMap mapaNombreNodo;

		for (int i = 0; i < listaTransiciones.getLength(); i++)
		{
			nodo = listaTransiciones.item(i);
			if (nodo.getNodeName().equalsIgnoreCase("transicion"))
			{
				mapaNombreNodo = nodo.getAttributes();
				String input = mapaNombreNodo.getNamedItem("input").getNodeValue();
				String estadoSig = mapaNombreNodo.getNamedItem("estadoSiguiente").getNodeValue();
				tablaEstados.putTransicion(idEstado, input, estadoSig);
			}
		}

	}
}
