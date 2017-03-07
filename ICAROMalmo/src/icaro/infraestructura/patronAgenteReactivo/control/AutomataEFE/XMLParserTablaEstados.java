/*
 *  Copyright 2001 Telefnica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Clase que convierte un fichero XML en una tabla de estados vlida para un
 * autmata
 * 
 * @author Jorge Gonzlez
 * @created 5 de septiembre de 2001
 */
public class XMLParserTablaEstados {

	/**
	 * @uml.property  name="document"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Document document;

	/**
	 * Constructor
	 */
	public XMLParserTablaEstados(String nombreFich)throws ExcepcionNoSePudoCrearAutomataEFE {
		// Esta parte es genrica para cualquier parsing XML
		// parsing XML

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		// factory.setNamespaceAware(true);
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(this.getClass().getResourceAsStream(nombreFich));
		} catch (SAXException sxe) {
			// Error generated during parsing
			Exception x = sxe;
			if (sxe.getException() != null) {
				x = sxe.getException();
			}
			System.out.println("Se ha producido un error al procesar el fichero XML: "
							+ x.getMessage());
            throw new ExcepcionNoSePudoCrearAutomataEFE( "XMLParserTablaEstados", "error al procesar el fichero XML"+ nombreFich,
                                                          "document = builder.parse(this.getClass().getResourceAsStream(nombreFich)");
			//x.printStackTrace();

		} catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			System.out
					.println("No se pudo construir un analizador XML con las opciones especificadas referido al fichero XML: "
							+ nombreFich);
			throw new ExcepcionNoSePudoCrearAutomataEFE("XMLParserTablaEstados","no se pudo construir un analizador XML a partir del  fichero XML: "+ nombreFich,
                    "document = builder.parse(this.getClass().getResourceAsStream(nombreFich)");
			//pce.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("Error de lectura en el fichero XML. Est usted seguro de que el fichero '"
							+ nombreFich + "' esta ahi?");
			throw new ExcepcionNoSePudoCrearAutomataEFE("XMLParserTablaEstados","Error de lectura en el fichero XML : "+ nombreFich,
                    "document = builder.parse(this.getClass().getResourceAsStream(nombreFich)");
			//pce.printStackTrace();

		}

	}

	public String extraerDescripcionTablaEstados() {
		return document.getElementsByTagName("tablaEstados").item(0)
				.getAttributes().item(0).getNodeValue();
	}

	/**
	 * Convierte el fichero dado en una tablaEstadosControl
	 * 
	 * @param nombreFich
	 *            Nombre del fichero a convertir
	 * @return Tabla del autmata extrado de ese fichero
	 */
	public TablaEstadosControl extraeTablaEstados() throws ExcepcionNoSePudoCrearAutomataEFE {
		TablaEstadosControl tabla = new TablaEstadosControl();

		// Esta parte es dependiente del tipo del documento que hemos creado

		// ahora tengo el documento XML
		// referencias de ayuda en los recorridos
		org.w3c.dom.Node nodo;
		org.w3c.dom.NamedNodeMap mapaNombreNodo;
		org.w3c.dom.NodeList listaNodos;
		String id;

		// capturo el estado inicial (SE QUE SOLO HAY UNO, LAS COMPROBACIONES SE
		// HACEN DESDE EL EXTERIOR)
		org.w3c.dom.NodeList nlInicial = document
				.getElementsByTagName("estadoInicial");

		nodo = nlInicial.item(0);
		mapaNombreNodo = nodo.getAttributes();
		// este es el identificador del nodo inicial
		id = mapaNombreNodo.getNamedItem("idInicial").getNodeValue();
		tabla.putEstado(id, TablaEstadosControl.TIPO_DE_ESTADO_INICIAL);

		// ahora obtenemos las transiciones desde el estado inicial
		// SE QUE TIENE AL MENOS UNA TRANSICION y que todos los hijos seran
		// transiciones
		listaNodos = nodo.getChildNodes();
		procesarListaNodosTransicion(id, listaNodos, tabla);

		// capturo subestados
		org.w3c.dom.NodeList nlIntermedios = document
				.getElementsByTagName("estado");

		// bucle que recorre estados intermedios
		for (int i = 0; i < nlIntermedios.getLength(); i++) {
			nodo = nlIntermedios.item(i);
			mapaNombreNodo = nodo.getAttributes();
			// este es el identificador del nodo inicial
			id = mapaNombreNodo.getNamedItem("idIntermedio").getNodeValue();
			tabla.putEstado(id, TablaEstadosControl.TIPO_DE_ESTADO_INTERMEDIO);

			// SE QUE TIENE AL MENOS UNA TRANSICION y que todos los hijos seran
			// transiciones
			listaNodos = nodo.getChildNodes();
			procesarListaNodosTransicion(id, listaNodos, tabla);
		}

		// capturo estados finales
		org.w3c.dom.NodeList nlFinales = document
				.getElementsByTagName("estadoFinal");
		// bucle que recorre estados finales
		for (int i = 0; i < nlFinales.getLength(); i++) {
			nodo = nlFinales.item(i);
			mapaNombreNodo = nodo.getAttributes();
			// este es el identificador del nodo inicial
			id = mapaNombreNodo.getNamedItem("idFinal").getNodeValue();
			tabla.putEstado(id, TablaEstadosControl.TIPO_DE_ESTADO_FINAL);

			// SE QUE TIENE AL MENOS UNA TRANSICION y que todos los hijos seran
			// transiciones
			listaNodos = nodo.getChildNodes();
			procesarListaNodosTransicion(id, listaNodos, tabla);
		}

		// capturo ahora transiciones universales
		org.w3c.dom.NodeList nlUniversal = document
				.getElementsByTagName("transicionUniversal");
		// bucle que recorre transiciones universales
                 if (nlUniversal.getLength()>0)
		for (int i = 0; i < nlUniversal.getLength(); i++) {
			nodo = nlUniversal.item(i);
			mapaNombreNodo = nodo.getAttributes();
			// extraemos los atributos
			String input = mapaNombreNodo.getNamedItem("input").getNodeValue();
			String accion = mapaNombreNodo.getNamedItem("accion")
					.getNodeValue();
			String estadoSig = mapaNombreNodo.getNamedItem("estadoSiguiente")
					.getNodeValue();
			String modo = mapaNombreNodo.getNamedItem("modoDeTransicion")
					.getNodeValue();

			tabla.putTransicionUniversal(input, accion, estadoSig, modo);
		}

		return tabla;
	}

	/**
	 * Crea un conjunto con los inputs vlidos para el autmata ledo a partir
	 * del fichero de tabla de estados
	 * 
	 * @param nombreFich
	 *            Nombre del fichero que contiene la tabla de estados
	 * @return Conjunto de inputs vlidos del autmata extrados de ese fichero
	 */
	public Set<String> extraeConjuntoInputs() {

		Set<String> conjuntoInputs = new HashSet<String>();

		// Aadir todos los inputs de los elementos "tranisicion"
		NodeList listaTransiciones = document
				.getElementsByTagName("transicion");

		for (int i = 0; i < listaTransiciones.getLength(); i++) {
			org.w3c.dom.Node transicion = listaTransiciones.item(i);
			Node input = transicion.getAttributes().getNamedItem("input");

			conjuntoInputs.add(input.getNodeValue());

		}

		// Aadir todos los inputs de los elementos "tranisicionUniversal"
		listaTransiciones = document
				.getElementsByTagName("transicionUniversal");

		for (int i = 0; i < listaTransiciones.getLength(); i++) {
			org.w3c.dom.Node transicion = listaTransiciones.item(i);
			Node input = transicion.getAttributes().getNamedItem("input");

			conjuntoInputs.add(input.getNodeValue());

		}

		return conjuntoInputs;

	}

	/**
	 * Mtodo auxiliar que procesa las transiciones de un estado determinado
	 * 
	 * @param idEstado
	 *            Estado al que pertenecen las transiciones
	 * @param listaTransiciones
	 *            Lista con las transiciones de ese estado
	 * @param tablaEstados
	 *            Tabla de estados / transiciones para aadir las nuevas
	 *            transiciones que se detectan
	 */
	private void procesarListaNodosTransicion(String idEstado,
			org.w3c.dom.NodeList listaTransiciones,
			TablaEstadosControl tablaEstados) {
		org.w3c.dom.Node nodo, nodoAux;
		org.w3c.dom.NamedNodeMap mapaNombreNodo;
                String input,accion,estadoSig,modo ;
		for (int i = 0; i < listaTransiciones.getLength(); i++) {
			nodo = listaTransiciones.item(i);
                        String idNodo = nodo.getNodeName();
//			if (nodo.getNodeName().equalsIgnoreCase("transicion")) {
                        if (idNodo.equalsIgnoreCase("transicion")) {
				mapaNombreNodo = nodo.getAttributes();
				 input = mapaNombreNodo.getNamedItem("input")
						.getNodeValue();
                                // consideramos la posiblidad de que las acciones no se especificque y las definimos como vacias
                                nodoAux = mapaNombreNodo.getNamedItem("accion");
                                if (nodoAux == null)accion=NombresPredefinidos.ACCION_VACIA_AUTOMATA_EF;
                                 else accion = mapaNombreNodo.getNamedItem("accion").getNodeValue();
                                estadoSig = mapaNombreNodo.getNamedItem("estadoSiguiente").getNodeValue();
                                // consideramos la posiblidad de que la modalidad sea vacia
                                nodoAux = mapaNombreNodo.getNamedItem("modoDeTransicion");
                                if (nodoAux == null) modo = NombresPredefinidos.NOMBRE_MODO_TRANSICION_AUTOMATA_EF;                           
                                else modo = nodoAux.getNodeValue();
				tablaEstados.putTransicion(idEstado, input, accion, estadoSig,
						modo);
			}
		}

	}
}
