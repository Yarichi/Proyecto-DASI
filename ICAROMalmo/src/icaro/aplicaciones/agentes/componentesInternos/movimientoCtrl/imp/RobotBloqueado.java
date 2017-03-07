/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.RobotStatus1;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author FGarijo
 */
public class RobotBloqueado extends EstadoAbstractoMovRobot {
  
 //   public MaquinaEstadoMovimientoCtrl maquinaEstados;
    public  RobotBloqueado (MaquinaEstadoMovimientoCtrl maquinaEstados){
       // en este estado se puede simular que el robot no avanza, pero que esta intentando salir del atasco
        // esto puede durar un tiempo. al final o ha salido del atasco para continuar hacia el destino o se queda
        // esperando ordenes
         super (maquinaEstados,MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotBloqueado);
  
    }
//   @Override
//    public  void inicializarInfoMovimiento(Coordinate coordInicial, double velocidadInicial){
//       
//   } 
    @Override
        public void moverAdestino(String identdest,Coordinate coordDestino, double velocidadCrucero) {
//            this.distanciaDestino = this.distanciaEuclidC1toC2(this.robotposicionActual, coordDestino);
//            double tiempoParaAlcanzarDestino = distanciaDestino/velocidadCrucero;
            this.identDestino = identdest;
        }
    @Override
        public void cambiaVelocidad( double nuevaVelocidadCrucero) {
            this.velocidadCrucero = nuevaVelocidadCrucero;
        }
    @Override
        public void cambiaDestino(String identdest,Coordinate coordDestino) {
            this.destinoCoord = coordDestino;
            this.identDestino = identdest;
        }
    @Override
        public void parar(){
//         if (monitorizacionLlegadaDestino!=null ) this.monitorizacionLlegadaDestino.finalizar();
            this.trazas.trazar (this.identAgente +"."+this.getClass().getSimpleName(), " ignoro la operacion porque estoy parado ", InfoTraza.NivelTraza.debug);
        }
   
        public void bloquear(){
//           if (monitorizacionLlegadaDestino!=null ) this.monitorizacionLlegadaDestino.finalizar();
           this.trazas.trazar (this.identAgente +"."+this.getClass().getSimpleName(), " ignoro la operacion porque estoy bloqueado ", InfoTraza.NivelTraza.debug);
    }
    @Override
        public void continuar(){
        // deberia intentar salir del bloqueo durante un tiempo, si no lo consigue notifica al agente
        // lo podria hacer generando una notificacion que va al controlador del agente
            this.trazas.trazar (this.identAgente +"."+this.getClass().getSimpleName(), " ignoro la operacion porque estoy parado ", InfoTraza.NivelTraza.debug); 
        }
   
    public boolean estamosEnDestino(String identDest){
        return (identDestino.equals(identDest));
        // 
        // se podria estar atascado y en destino
//         this.trazas.trazar (this.identAgente +"."+this.getClass().getSimpleName(), " ignoro la operacion porque estoy atascado ", InfoTraza.NivelTraza.debug);
    }
    @Override
    public  Coordinate getCoordenadasActuales(){
//        return this.monitorizacionLlegadaDestino.getCoordRobot();
        return this.robotposicionActual;
    } 
     public  String getIdentEstadoMovRobot(){
         return MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotBloqueado.name();
     }

   
    public EstadoAbstractoMovRobot getEstadoActual() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    return this;
    }

    public boolean paradoEnDestino(String identDestino) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public Coordinate getCoordenasDestino() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public HebraMonitorizacionLlegada getHebraMonitorizacionLlegadaDestino() {
//        return null;
//    }

    @Override
    public boolean getEstamosEnDestino() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setEstamosEnDestino() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    


}
