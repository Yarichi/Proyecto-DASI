/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.tareas;
import icaro.aplicaciones.Rosace.informacion.*;
import icaro.aplicaciones.agentes.agenteAplicacionrobotIgualitarioNCognitivo.informacion.InfoParaDecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

/**
 *Se calcula el coste de la realizaciÛn del objetivo
 * @author Francisco J Garijo
 */
public class CalcularEvaluacionRealizarObjetivo extends TareaSincrona {
	
    private int mi_eval, mi_eval_nueva;
    private Coordinate robotLocation; //Localizacion del robot
    private double funcionEvaluacion; //Variable para almacenar el resultado de calcular la funcion de evaluacion utilizada
    private Integer divisor = 10000;
    // Argumentos para calcular el coste 
    private double distanciaPosRobotVictima;
    private int tiempoAtencionVictimas; // para tener en cuenta  el  numero de victimas asignadas
    private int numeroVictimasAsignadas; // numero de victimas a rescatar cuando se hace la evaluacion
    private int energiaDisponibleRobot ;
    // Pesos asociados a los parametros
    private int pesoArgumentoDistancia;
    private int pesoArgumentoEnergia;
    private int pesoArgumentoTiempoAtencion;
    private boolean trazarCalculoCoste;
    // Funciones de evaluacion
// Introduzco como parametro  la InfoParaDecidrQuienVa y le definimos el valor de la evaluacion. 
    @Override
    public void ejecutar(Object... params) {
	   try {
            RobotStatus1 robot = (RobotStatus1)params[0]; 
            Victim victim = (Victim)params[1];                
            InfoParaDecidirQuienVa infoDecision = (InfoParaDecidirQuienVa)params[2];                      
            VictimsToRescue victims2R =(VictimsToRescue)params[3];
            MisObjetivos misObjs = (MisObjetivos)params[4];
            
            Coordinate victimLocation = victim.getCoordinateVictim(); 
            trazarCalculoCoste=true;
            Coste coste = new Coste();
            coste.setTrazar(true);
            // si el robot esta bloqueado le ponemos como coste el valor mayor para que se le excluya del objetivo
            if(!robot.getBloqueado()){
            
                robotLocation = robot.getInfoCompMovt().getCoordenadasActuales();

//            
            //Las dos sentencias siguientes permiten utilizar la funcion de evaluacion 1 que solo considera la distancia entre el robot y la nueva victima
            //COMENATAR las dos lineas siguientes si se quiere utilizar la funcion de evaluacion 2
	        //double distanciaC1toC2 = coste.distanciaC1toC2(robotLocation, victimLocation);
	        //funcionEvaluacion = coste.FuncionEvaluacion2(distanciaC1toC2, 1.0, robot, victim);

	        
	        //Las dos sentencias siguientes permiten utilizar la funcion de evaluacion 2 que considera el recorrido que tendria que hacer y la engergia
	        //QUITAR EL COMENTARIO de las dos lineas siguientes si se quiere utilizar la funcion de evaluacion 2
	        //SI SE UTILIZA LA funcion de evaluacion 1 entonces las dos lineas siguientes deben estar comentadas
//	        double distanciaCamino = coste.CalculaDistanciaCamino(nombreAgenteEmisor, robotLocation, victim, victims2R, misObjs);
//	        funcionEvaluacion = coste.FuncionEvaluacion2(distanciaCamino, 1.0, robot, victim);
//
            System.out.println("Realizando  la evaluacion para el Robot ->" + this.identAgente);
            System.out.println("robotLocation->"+robotLocation);
            System.out.println("para la victima ->"+victim.toString());
            System.out.println("victims2R->"+victims2R.getlastVictimToRescue().toString());
//            System.out.println("misObjs->"+misObjs.getobjetivoMasPrioritario().toString());
            if (misObjs.getobjetivoMasPrioritario()!=null)System.out.println("misObjs->"+misObjs.getobjetivoMasPrioritario().toString());
	        //Las sentencias siguientes permiten utilizar la funcion de evaluacion 3 que considera el recorrido que tendria que hacer y la engergia y el tiempo
            double distanciaCamino = coste.CalculaDistanciaCamino(this.identAgente, robotLocation, victim, victims2R, misObjs);
            double tiempoAtencionVictimas = coste.CalculaTiempoAtencion(3.0, victim, victims2R, misObjs);
            funcionEvaluacion = coste.FuncionEvaluacion3(distanciaCamino, 5.0, tiempoAtencionVictimas, 9.0, robot, victim);
                if(trazarCalculoCoste) {
                    this.trazas.aceptaNuevaTrazaEjecReglas(identAgente, coste.getTrazaCalculoCoste());
                }           
                mi_eval = (int)funcionEvaluacion;   //convierto de double a int porque la implementacion inicial de Paco usaba int                                  
            
                if (mi_eval>=0){            
                    mi_eval_nueva = Integer.MAX_VALUE; 
    //              mi_eval_nueva = cotaMaxima; 
                    //como va el que menor rango tiene, lo inicializamos a la peor                        
                    //Para que gane el que mayor valor tiene de evaluacion le resto el valor de la distancia obtenida al valor m√°ximo de Integer
                    //El que este mas cercano hara° decrecer menos ese valor y por tanto es el MEJOR
                    mi_eval = mi_eval_nueva - mi_eval;
                }
            }else // Robot bloqueado
                mi_eval = Integer.MAX_VALUE; 
            EvaluacionAgente eval = new  EvaluacionAgente (identAgente, mi_eval);
            eval.setObjectEvaluationId(victim.getName());// Referenciamos la evaluacion con el ident de la victima
            infoDecision.setMi_eval(mi_eval);
            infoDecision.setTengoMiEvaluacion(Boolean.TRUE);
            this.getEnvioHechos().insertarHechoWithoutFireRules(eval);
            this.getEnvioHechos().actualizarHecho(infoDecision);
       } catch (Exception e) {
		   e.printStackTrace();
       }
    }
}