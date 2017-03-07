/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 *
 * @author FGarijo
 */
public class EncolarObjetivoActualizarFocoIGN extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        //    ItfUsoRecursoEstadistica itfUsoRecursoEstadistica=null;
        int velocidadCruceroPordefecto = 1;// metros por segundo
        //Para recoger estadisticas del instante de envio de victimas desde el centro de control

        try {
//             trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
            MisObjetivos misObjs = (MisObjetivos) params[0];
            Objetivo obj1 = (Objetivo) params[1];
            InfoParaDecidirQuienVa infoDecision = (InfoParaDecidirQuienVa) params[2];
            Focus focoActual = (Focus) params[3];
            InfoCompMovimiento infoComMov = (InfoCompMovimiento) params[4];
            Victim victima = (Victim) params[5];
            VictimsToRescue victimas =(VictimsToRescue) params[6];
            String nombreAgenteEmisor = this.getIdentAgente();

            //Para anotar en el fichero cuál es mi coste
            int coste = 0;   //El coste se define como el MAYOR ENTERO - VALOR DE LA FUNCION DE EVALUACION
            //El que menor coste tiene es el que se hace cargo de la victima o dicho de otra manera
            //El que mayor función de evaluación tiene es el que se hace cargo de la victima
            int miEvaluacion = infoDecision.getMi_eval();
            if (miEvaluacion != -1) {
                coste = Integer.MAX_VALUE - miEvaluacion;
            } else {
                coste = miEvaluacion;    //SI EL COSTE EL -1 INDICARIA QUE SE HA HECHO CARGO PERO QUE NO PUEDE IR (NO TIENE RECURSOS)
            }
            //ACTUALIZAR ESTADISTICAS
            //Inicializar y recuperar la referencia al recurso de estadisticas        	
            long tiempoActual = System.currentTimeMillis();
            String refVictima = obj1.getobjectReferenceId();
            //      	 itfUsoRecursoEstadistica.escribeEstadisticaFicheroTextoPlanoTRealAsignacionVictimasRobots(tiempoActual, refVictima, nombreAgenteEmisor, coste);
            ////////////////////////////////////////////////////////
            //ENVIAR INFORMACION AL AGENTE CONTROLADOR DEL SIMULADOR           
            Object[] valoresParametrosAccion = new Object[4];
            valoresParametrosAccion[0] = tiempoActual;
            valoresParametrosAccion[1] = refVictima;
            valoresParametrosAccion[2] = nombreAgenteEmisor;
            valoresParametrosAccion[3] = miEvaluacion;
            InfoContEvtMsgAgteReactivo msg = new InfoContEvtMsgAgteReactivo("victimaAsignadaARobot", valoresParametrosAccion);
            this.getComunicator().enviarInfoAotroAgente(msg, VocabularioRosace.IdentAgteControladorSimulador);
            // verificamos que no se esta ayudando a esa victima. Comprobamos que el ident no esta en ninguno de los objetivos 
            //      if((objetivoEjecutantedeTarea == null)) newObjetivo.setSolving(); // se comienza el proceso para intentar conseguirlo                                        
            //       Se genera un objetivo para decidir quien se hace cargo de la ayuda y lo ponemos a solving
//            obj1.setSolving();
            misObjs.addObjetivo(obj1);
            Objetivo nuevoObj = misObjs.getobjetivoMasPrioritario();
            if ( obj1.getobjectReferenceId().equalsIgnoreCase(nuevoObj.getobjectReferenceId())){
                obj1.setSolving();
            }
            focoActual.setFoco(nuevoObj);
            victima = victimas.getVictimToRescue(nuevoObj.getobjectReferenceId());          
                ItfUsoMovimientoCtrl itfcompMov = (ItfUsoMovimientoCtrl) infoComMov.getitfAccesoComponente();
//                if (itfcompMov.estamosEnDestino(victima.getName())){
//                    nuevoObj.setSolved();
//                    this.getEnvioHechos().actualizarHechoWithoutFireRules(nuevoObj);
//                } else {             
                itfcompMov.moverAdestino(nuevoObj.getobjectReferenceId(), victima.getCoordinateVictim(), velocidadCruceroPordefecto);
            // se pondra la verlocidad por defecto 
            trazas.aceptaNuevaTrazaEjecReglas(identAgente, "Objetivo1 : "+ obj1.toString()+ "Se ejecuta la tarea : " + identTarea + " Se actualiza el  foco al objetivo:  " + nuevoObj + "\n");
            trazas.aceptaNuevaTrazaEjecReglas(identAgente,"Posicion Robot : "+itfcompMov.getCoordenadasActuales()+ "Se da orden al comp Movimiento  para salvar a la victima :  " + victima + "\n");
            System.out.println("\n" + identAgente + "Se ejecuta la tarea " + identTarea + " Se actualiza el  objetivo:  " + obj1 + "\n\n");
//            }
            if (infoDecision != null) {
                this.getEnvioHechos().eliminarHechoWithoutFireRules(infoDecision);
            }
            this.getEnvioHechos().actualizarHecho(obj1);
            this.getEnvioHechos().actualizarHechoWithoutFireRules(infoComMov);
            this.getEnvioHechos().actualizarHechoWithoutFireRules(misObjs);
            this.getEnvioHechos().actualizarHecho(focoActual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

