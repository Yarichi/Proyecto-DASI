/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.Rosace.utils.ConstantesRutasEstadisticas;
import icaro.aplicaciones.recursos.recursoEstadistica.ItfUsoRecursoEstadistica;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;


/**
 *
 * @author Francisco J Garijo
 */
public class FinalizarSimulacionIG extends TareaSincrona {
	
        
//    private ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;    
    private ItfUsoRecursoEstadistica itfUsoRecursoEstadistica;    //Para escribir estadisticas

    
    private Coordinate robotLocation; //Localizacion del robot
    
    private ArrayList idsVictimasFinalesAsignadas = new ArrayList();

    
    @Override
    public void ejecutar(Object... params) {
	   try {
		   		   		             		             
           RobotStatus robot = (RobotStatus)params[0];                        
           VictimsToRescue victims2R =(VictimsToRescue)params[1];
           MisObjetivos misObjs = (MisObjetivos)params[2];
           FinSimulacion finSimu = (FinSimulacion)params[3];
           
            
           String nombreAgenteEmisor = this.getAgente().getIdentAgente();            
           String identTarea = this.getIdentTarea();
           robotLocation=robot.getRobotCoordinate();

            //Mostrar en ventana de trazas informacion de los costes 
            //trazas.aceptaNuevaTraza(new InfoTraza("Evaluacionadfd", " ", InfoTraza.NivelTraza.info)); 		        		                                                           		        		          		           
            //trazas.aceptaNuevaTraza(new InfoTraza("Evaluacionadfd", nombreAgenteEmisor + " ejecutando " + identTarea, InfoTraza.NivelTraza.info));       		        		                                                           		        		          		           
            
//            try {            	            	
//       	          ItfUsoRepositorioInterfaces ItfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
//       	          try{    		   
//       		           ItfUsoRecursoMorse morseResourceRef;
//       		           morseResourceRef = (ItfUsoRecursoMorse) ItfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + 
//       				                      "RecursoMorse1");
//       		           robotLocation = morseResourceRef.getGPSInfo(nombreAgenteEmisor);
//       		           
//       				   itfUsoRecursoEstadistica = (ItfUsoRecursoEstadistica)ClaseGeneradoraRepositorioInterfaces.instance().
// 		                                          obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoEstadistica1");       		           
//       	          }
//   	              catch (Exception ex){
//       		              ex.printStackTrace();
//       	          }       	          
//   	        }
//            catch (Exception e) {
//   		          e.printStackTrace();
//            }
            
            Coste coste = new Coste();

            //Las sentencias siguientes permiten calcular el tiempo en realizar la mision, es decir, atender a las victimas de las que se ha hecho responsable
            //utilizar la funcion de evaluacion 3 que considera el recorrido que tendria que hacer y el tiempo en curar cada victima

            double tiempoRecorrerCaminoVictimasAsignadas = coste.calculaCosteRecorrerCaminoVictimas(nombreAgenteEmisor, robotLocation, victims2R, misObjs);
            double tiempoAtencionVictimasAsignadas = coste.calculaCosteAtencionVictimasFinalesAsignadas(3.0, victims2R, misObjs);
            double tiempoTotalCompletarMisionAtenderVictimasFinalesAsignadas = coste.calculaCosteTotalCompletarMisionAtenderVictimasFinalesAsignadas(
            		                                                           tiempoRecorrerCaminoVictimasAsignadas, 10.0, tiempoAtencionVictimasAsignadas, 3.0);                         
            
            //Obtener las victimas que tiene asignadas el robot
            this.idsVictimasFinalesAsignadas = getIdsVictimasFinalesAsignadas(misObjs, victims2R);
            
            //Crear los ficheros EstadisticaFinalSimulacionAsignacionMision (.xml y .txt)            
            String filenameXMLV1 = ConstantesRutasEstadisticas.rutaficheroXMLRepartoTareasRobotsYTiempoCompletarlasV1;
            String filenameXMLV2 = ConstantesRutasEstadisticas.rutaficheroXMLRepartoTareasRobotsYTiempoCompletarlasV2;
            String filenameTXT = ConstantesRutasEstadisticas.rutaficheroTextoPlanoRepartoTareasRobotsYTiempoCompletarlas;
            
            itfUsoRecursoEstadistica.crearFicherosRepartoTareasRobotsYTiempoCompletarlas(filenameXMLV1, filenameXMLV2, filenameTXT);
            		
            //Escribir estadisticas en los ficheros creados previamente
            itfUsoRecursoEstadistica.escribeEstadisticaFicherosRepartoTareasRobotsYTiempoCompletarlas(nombreAgenteEmisor,
            																		this.idsVictimasFinalesAsignadas,	
            																		tiempoTotalCompletarMisionAtenderVictimasFinalesAsignadas);                       
            
            System.out.println(nombreAgenteEmisor + " : tiempo para recorrer camino victimas asignadas->" + tiempoRecorrerCaminoVictimasAsignadas);
            System.out.println(nombreAgenteEmisor + " : tiempo para atender victimas asignadas->" + tiempoAtencionVictimasAsignadas);
            System.out.println(nombreAgenteEmisor + " : tiempo total mision ->" + tiempoTotalCompletarMisionAtenderVictimasFinalesAsignadas);
            
            //finSimu.setflagActivation(true);            
            //this.getEnvioHechos().actualizarHecho(finSimu);
                                                
       } catch (Exception e) {
		   e.printStackTrace();
       }
    }//FIN metodo ejecutar
    
    
    
    private ArrayList getIdsVictimasFinalesAsignadas(MisObjetivos misObjs, VictimsToRescue victims2R){
    	ArrayList aux = new ArrayList();

    	PriorityBlockingQueue <Objetivo> colaobjetivos = misObjs.getMisObjetivosPriorizados();
    	int tamaniocola = colaobjetivos.size();

    	Iterator<Objetivo> it = colaobjetivos.iterator();

        if (tamaniocola==0){
        	return aux;
        }
        
    	int index = 0;
    	while (it.hasNext()){    		
    		  //Hay al menos un objetivo
    	      Objetivo ob = it.next();
    	      String referenciaIdObjetivo = ob.getobjectReferenceId();

  	          //Obtener la victima de la cola
  	          Victim victimaActualCola = victims2R.getVictimToRescue(referenciaIdObjetivo);  	          
  	          String nameVictim = victimaActualCola.getName();
  	        
  	          aux.add(index, nameVictim);
  	          index ++;  	        
    	}
    	
    	return aux;
    }
    
}