/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionSubordinadoCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.*;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 *
 * @author Francisco J Garijo
 */

/**
 *
 * @author Francisco J Garijo
 */
public class ObtenerEvaluacionAyudaVictima extends TareaSincrona {
	
    private int mi_eval,mi_eval_nueva;
        
 //   private ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
    
    //private TimeOutRespuestas tiempoSinRecibirRespuesta;  //no usado
    
    private Coordinate robotLocation; //Localizacion del robot
    private double funcionEvaluacion; //Variable para almacenar el resultado de calcular la funcion de evaluacion utilizada
 //   private Integer divisor = 10000;
    
// Introduzco como parametro  la InfoParaDecidrQuienVa y le definimos el valor de la evaluacion. 
    @Override
    public void ejecutar(Object... params) {
	   try {
            Objetivo objetivoEjecutantedeTarea = (Objetivo)params[0];
            Victim victim = (Victim)params[1];            
            Coordinate victimLocation = victim.getCoordinateVictim();            
  //          InfoParaDecidirQuienVa infoDecision = (InfoParaDecidirQuienVa)params[2];
            
            RobotStatus1 robot = (RobotStatus1)params[2];                        
            VictimsToRescue victims2R =(VictimsToRescue)params[3];
            MisObjetivos misObjs = (MisObjetivos)params[4];

            Coste coste = new Coste();
            
            String nombreAgenteEmisor = this.getAgente().getIdentAgente();            
            String identTarea = this.getIdentTarea();
            robotLocation = robot.getRobotCoordinate();
            //Mostrar en ventana de trazas informacion de los costes 
 //           trazas.aceptaNuevaTraza(new InfoTraza("Evaluacion", " ", InfoTraza.NivelTraza.info));       		        		                                                           		        		          		           
            
 

            
            //Las dos sentencias siguientes permiten utilizar la funcion de evaluacion 1 que solo considera la distancia entre el robot y la nueva victima
            //COMENATAR las dos lineas siguientes si se quiere utilizar la funcion de evaluacion 2
	        //double distanciaC1toC2 = coste.distanciaC1toC2(robotLocation, victimLocation);
	        //funcionEvaluacion = coste.FuncionEvaluacion2(distanciaC1toC2, 1.0, robot, victim);

	        
	        //Las dos sentencias siguientes permiten utilizar la funcion de evaluacion 2 que considera el recorrido que tendria que hacer y la engergia
	        //QUITAR EL COMENTARIO de las dos lineas siguientes si se quiere utilizar la funcion de evaluacion 2
	        //SI SE UTILIZA LA funcion de evaluacion 1 entonces las dos lineas siguientes deben estar comentadas
//	        double distanciaCamino = coste.CalculaDistanciaCamino(nombreAgenteEmisor, robotLocation, victim, victims2R, misObjs);
//	        funcionEvaluacion = coste.FuncionEvaluacion2(distanciaCamino, 1.0, robot, victim);


	        //Las sentencias siguientes permiten utilizar la funcion de evaluacion 3 que considera el recorrido que tendria que hacer y la engergia y el tiempo
            double distanciaCamino = coste.CalculaDistanciaCamino(nombreAgenteEmisor, robotLocation, victim, victims2R, misObjs);
            double tiempoAtencionVictimas = coste.CalculaTiempoAtencion(3.0, victim, victims2R, misObjs);
            funcionEvaluacion = coste.FuncionEvaluacion3(distanciaCamino, 10.0, tiempoAtencionVictimas, 3.0, robot, victim);
	        
	        
            mi_eval = (int)funcionEvaluacion;   //convierto de double a int porque la implementación inicial de Paco usaba int                                  
            
            if (mi_eval>=0){            
                mi_eval_nueva = Integer.MAX_VALUE; 
//              mi_eval_nueva = cotaMaxima; 
                //como va el que menor rango tiene, lo inicializamos a la peor                        
            	//Para que gane el que mayor valor tiene de evaluación le resto el valor de la distancia obtenida al valor máximo de Integer
            	//El que este más cercano hará decrecer menos ese valor y por tanto es el MEJOR
            	mi_eval = mi_eval_nueva - mi_eval;
            }
            
            EvaluacionAgente eval = new  EvaluacionAgente (nombreAgenteEmisor, mi_eval);
            eval.setObjectEvaluationId(victim.getName());// Referenciamos la evaluacion con el ident de la victima
//            infoDecision.setMi_eval(mi_eval);
//            infoDecision.setTengoMiEvaluacion(Boolean.TRUE);
            this.getEnvioHechos().actualizarHecho(eval);
//            this.getEnvioHechos().insertarHechoWithoutFireRules(eval);
//            this.getEnvioHechos().actualizarHecho(infoDecision);
       
            //Mostrar en ventana de trazas del agente información de la posición del robot, la victima y distancia entre ellos
            //trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "^^^^^Posicion actual robot -> "    + robotLocation.toString() + 
       		//                                                     " , Posicion actual victima -> "  + victimLocation.toString() +
       		//                                                     " , Funcion distancia -> "  + funcionEvaluacion + " , eval-> " + mi_eval, 
       		//                                 InfoTraza.NivelTraza.debug));       		        		                                                           		        		          		           
       
       } catch (Exception e) {
		   e.printStackTrace();
       }
    }
}