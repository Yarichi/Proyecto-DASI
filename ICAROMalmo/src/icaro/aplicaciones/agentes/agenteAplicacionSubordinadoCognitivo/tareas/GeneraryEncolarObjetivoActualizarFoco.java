/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.AceptacionPropuesta;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.objetivosComunes.AyudarVictima;
import icaro.aplicaciones.recursos.recursoEstadistica.ItfUsoRecursoEstadistica;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

/**
 *
 * @author FGarijo
 */
public class GeneraryEncolarObjetivoActualizarFoco extends TareaSincrona{
		
    @Override
   public void ejecutar(Object... params) {
    	
       ItfUsoRecursoEstadistica itfUsoRecursoEstadistica=null;    //Para recoger estadisticas del instante de envio de victimas desde el centro de control
	    	
	   try {
             trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
             MisObjetivos misObjs = (MisObjetivos) params[0];
    //         Objetivo obj1 = (Objetivo)params[1];
  //           InfoParaDecidirQuienVa infoDecision = (InfoParaDecidirQuienVa)params[2];
             Focus focoActual = (Focus)params[1];
             Victim victima = (Victim) params[2];
             AceptacionPropuesta propuestaAceptada = (AceptacionPropuesta) params[3];
             String identTarea = this.getIdentTarea();
             String nombreAgenteEmisor = this.getIdentAgente();    
             //Para anotar en el fichero cuál es mi coste
            // int coste = 0;   //El coste se define como el MAYOR ENTERO - VALOR DE LA FUNCION DE EVALUACION
                              //El que menor coste tiene es el que se hace cargo de la victima o dicho de otra manera
                              //El que mayor función de evaluación tiene es el que se hace cargo de la victima
             String identVictim = propuestaAceptada.getidentObjectRefAcetPropuesta();
      //       int miEvaluacion = infoDecision.getMi_eval(); 
             int miEvaluacion = victima.getEstimatedCost();
             if (miEvaluacion!=-1) miEvaluacion = Integer.MAX_VALUE - miEvaluacion;
             else miEvaluacion = miEvaluacion;    //SI EL COSTE EL -1 INDICARIA QUE SE HA HECHO CARGO PERO QUE NO PUEDE IR (NO TIENE RECURSOS)
                                       
             //ACTUALIZAR ESTADISTICAS
        	    //Inicializar y recuperar la referencia al recurso de estadisticas        	
  //          itfUsoRecursoEstadistica = (ItfUsoRecursoEstadistica)ClaseGeneradoraRepositorioInterfaces.instance().
 //			 		                                                    obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoEstadistica1");
              itfUsoRecursoEstadistica = (ItfUsoRecursoEstadistica)NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(NombresPredefinidos.ITF_USO + "RecursoEstadistica1");
              long tiempoActual = System.currentTimeMillis(); 
              String refVictima = identVictim;
              itfUsoRecursoEstadistica.escribeEstadisticaFicheroTextoPlanoTRealAsignacionVictimasRobots(tiempoActual, refVictima, nombreAgenteEmisor, miEvaluacion);       
           //       Se genera un objetivo para decidir quien se hace cargo de la ayuda y lo ponemos a solving
               AyudarVictima nuevoObj = new AyudarVictima(refVictima);
                nuevoObj.setSolving() ; 
                misObjs.addObjetivo(nuevoObj);
                focoActual.setFocusToObjetivoMasPrioritario(misObjs);
                this.getEnvioHechos().insertarHechoWithoutFireRules(nuevoObj);              
                this.getEnvioHechos().actualizarHechoWithoutFireRules(misObjs);
                this.getEnvioHechos().actualizarHechoWithoutFireRules(focoActual);
                this.getEnvioHechos().eliminarHecho(propuestaAceptada);
                trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se ejecuta la tarea " + this.getIdentTarea()+
                                    " Se actualiza el  objetivo:  "+ nuevoObj, InfoTraza.NivelTraza.debug));
            System.out.println("\n"+nombreAgenteEmisor +"Se ejecuta la tarea " + this.getIdentTarea()+ " Se actualiza el  objetivo:  "+ nuevoObj+"\n\n" );
                          
             
       } catch (Exception e) {
			 e.printStackTrace();
       }
   }

}
