/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.ItfUsoRecursoVisualizadorEntornosSimulacion;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author FGarijo
 */
public abstract class EstadoAbstractoMovRobot  {

//    public static  enum EstadoMovimientoRobot {Indefinido,RobotParado, RobotEnMovimiento, RobotBloqueado,RobotBloqueadoPorObstaculo,RobotavanceImposible,enDestino,  error}
    //Nombres de las clases que implementan estados del recurso interno
    public static  enum EvalEnergiaRobot {sinEnergia,energiaSuficiente,EnergiaJusta, EnergiaInsuficiente }
    public EstadoAbstractoMovRobot estadoActual;
    public MaquinaEstadoMovimientoCtrl maquinaEstados;
    public String identAgente;
    public volatile Coordinate robotposicionActual;
    public volatile Coordinate destinoCoord;
    public double distanciaDestino ;
    protected double velocidadCrucero;
    public ItfProcesadorObjetivos itfProcObjetivos;
//    protected HebraMonitorizacionLlegada monitorizacionLlegadaDestino;
    public ItfUsoRecursoTrazas trazas ;
    public String identComponente ;
    public String identEstadoActual ;
    public String identDestino ;
    public int energiaRobot;
    public ItfUsoRecursoVisualizadorEntornosSimulacion itfusoRecVisSimulador;
    
   public  EstadoAbstractoMovRobot (MaquinaEstadoMovimientoCtrl maquinaEstds,MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot identEstadoAcrear) {
    if (maquinaEstds == null){
       estadoActual = null;
       trazas.trazar(this.getClass().getSimpleName(), " Error al crear el estado  "+ identEstadoAcrear+
                " La maquinaEstds es null   ", InfoTraza.NivelTraza.error);
    }else{
          maquinaEstados = maquinaEstds ;
//          estadoActual = this; 
          identEstadoActual= identEstadoAcrear.name();
    }
}
//    public void inicializar (ItfProcesadorObjetivos itfProcObj){
    public void inicializarDependencias1 (String agteId,ItfUsoRecursoVisualizadorEntornosSimulacion itfVisSimul){
        identAgente = agteId;
//        itfProcObjetivos = itfProcObj;
        identComponente = identAgente+"."+this.getClass().getSimpleName();
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        itfusoRecVisSimulador = itfVisSimul;
 
    }
    public void inicializarDependencias2 (ItfProcesadorObjetivos itfProcObj,ItfUsoRecursoVisualizadorEntornosSimulacion itfVisSimul){
        identAgente = itfProcObj.getAgentId();
        itfProcObjetivos = itfProcObj;
        identComponente = identAgente+"."+this.getClass().getSimpleName();
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        itfusoRecVisSimulador = itfVisSimul;
 
    }
    public  void inicializarInfoMovimiento(int robotEnergia, Coordinate coordInicial, double velocidadInicial){
        robotposicionActual =coordInicial;
        velocidadCrucero = velocidadInicial;
        energiaRobot=robotEnergia;
    } 

        public abstract void moverAdestino(String identDest,Coordinate coordDestino, double velocidadCrucero);
        public abstract void cambiaVelocidad( double nuevaVelocidadCrucero); 
        public abstract void cambiaDestino(String identDest,Coordinate coordDestino) ;
        public abstract void continuar();
        public abstract void parar();
        public abstract  boolean getEstamosEnDestino();
        public abstract  void setEstamosEnDestino();
        public abstract Coordinate getCoordenadasActuales(); 
        public void setEnergiaRobot(int robtEnergia){
            this.energiaRobot = robtEnergia;
            }
        public int getEnergiaRobot(){
            return energiaRobot ;
        }
        public synchronized void setCoordenadasActuales(Coordinate nuevasCoordenadas) {
        if (nuevasCoordenadas != null){
            
         robotposicionActual= nuevasCoordenadas;
        }
        notify();
    }
}
