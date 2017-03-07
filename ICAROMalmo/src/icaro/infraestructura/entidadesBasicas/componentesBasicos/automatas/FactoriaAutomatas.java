/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas;

import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFconGesAcciones.InterpreteAutomataEFconGestAcciones;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFconGesAcciones.estadosyTransiciones.TablaEstadosAutomataEFinputObjts;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFconGesAcciones.estadosyTransiciones.XMLParserTablaEstadosAutomataEFinputObj;
import icaro.infraestructura.patronAgenteReactivo.control.GestorAccionesAgteReactivoImp;
import icaro.infraestructura.entidadesBasicas.factorias.FactoriaComponenteIcaro;
import icaro.gestores.informacionComun.VocabularioGestores;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.gestorAcciones.GestorAccionesAbstr;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItfUsoAutomataEFE;
import icaro.infraestructura.pruebas.InformeArranqueGestor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FGarijo
 */
public class FactoriaAutomatas extends FactoriaComponenteIcaro{
    
//
//	private static FactoriaAutomatas instance;
//
//	public static FactoriaAutomatas instance() {
//		if (instance == null)
//			instance = new FactoriaAutomatas();
//		return instance;
//	}
    
	public synchronized InterpreteAutomataEFconGestAcciones crearInterpreteAutomataEFconGestorAcciones(String identPropietario,
            String rutaFicheroAutomata,String rutaCarpetaAcciones,Boolean trazar ){
            XMLParserTablaEstadosAutomataEFinputObj parser =   new XMLParserTablaEstadosAutomataEFinputObj(identPropietario);     
            TablaEstadosAutomataEFinputObjts tablaEF =  parser.extraeTablaEstadosDesdeFicheroXML(rutaFicheroAutomata, rutaCarpetaAcciones);
            return  new InterpreteAutomataEFconGestAcciones(tablaEF,trazar);
        }
        public synchronized GestorAccionesAbstr crearGestorAcciones ( Class claseGestor){
            try {
                return (GestorAccionesAbstr)claseGestor.newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(FactoriaAutomatas.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(FactoriaAutomatas.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
	public  synchronized InterpreteAutomataEFconGestAcciones crearAutomataParaControlAgteReactivo(
                String identPropietario,String rutaFicheroAutomata,String rutaCarpetaAcciones,Boolean trazar){
            
	XMLParserTablaEstadosAutomataEFinputObj prueba1 =   new XMLParserTablaEstadosAutomataEFinputObj(identPropietario);

         TablaEstadosAutomataEFinputObjts tablaEF =  prueba1.extraeTablaEstadosDesdeFicheroXML(rutaFicheroAutomata, rutaCarpetaAcciones);
         // crear el ejecutor de acciones
         GestorAccionesAbstr gestAcciones = crearGestorAcciones (GestorAccionesAgteReactivoImp.class);
         gestAcciones.setPropietario(identPropietario);
         InterpreteAutomataEFconGestAcciones interpreteAutomata = new InterpreteAutomataEFconGestAcciones(tablaEF,trazar);
         interpreteAutomata.setGestorAcciones(gestAcciones);
         return interpreteAutomata;
        }
         public static void main(String args[]) {
//         XMLParserTablaEstadosAutomataEFinputObj prueba1 =   new XMLParserTablaEstadosAutomataEFinputObj("Iniciador");
//         prueba1.extraeTablaEstadosDesdeFicheroXML("/icaro/infraestructura/entidadesBasicas/componentesBasicos/automatas/clasesImpAutomatas/automataPrueba.xml", null);
//         String rutaFichero = prueba1.obtenerRutaValidaAutomata (NombresPredefinidos.COMPORTAMIENTO_PORDEFECTO_INICIADOR);
//         prueba1.extraeTablaEstadosDesdeFicheroXML("/icaro/gestores/iniciador/automataPrueba.xml", null);
//         prueba1.extraeTablaEstadosDesdeFicheroXML("/icaro/pruebas/automataPruebas.xml", null);
//             String rutaFicheroAutomata = "/icaro/gestores/iniciador/automataPrueba.xml";  // da error
             String rutaFicheroAutomata = "/icaro/infraestructura/pruebas/automataPruebas.xml";
             String rutaCarpetaAcciones = "icaro.infraestructura.pruebas";
             String identPropietario = "Iniciador";
             String estadoActual;
             Boolean esEstadoFinal;
             ItfUsoAutomataEFE interpretePrueba;
            interpretePrueba= FactoriaComponenteIcaro.instanceAtms().
                              crearAutomataParaControlAgteReactivo(identPropietario,rutaFicheroAutomata,rutaCarpetaAcciones,Boolean.TRUE);
             interpretePrueba.volverAEstadoInicial(); // Estado inicial dela utomata
             estadoActual = interpretePrueba.getEstadoAutomata(); // debe dar el estado inicial 
             esEstadoFinal = interpretePrueba.estasEnEstadoFinal(); // debe dar falso
             interpretePrueba.cambiaEstado("creandoRecursosNucleoOrganizacion");
             estadoActual = interpretePrueba.getEstadoAutomata();
             interpretePrueba.cambiaEstado("arrancandoGestorInicial"); // deben salir las trazas
//             interpretePrueba.ejecutarTransicion("existenEntidadesDescripcion");
//              interpretePrueba.procesaInput("existenEntidadesDescripcion", "a", "b");
             InformeArranqueGestor informePrueba = new InformeArranqueGestor ("Prueba Factoria", VocabularioGestores.ResultadoArranqueGestorOK);
//              interpretePrueba.procesaInput(informePrueba, "a", "b");
        }

}
