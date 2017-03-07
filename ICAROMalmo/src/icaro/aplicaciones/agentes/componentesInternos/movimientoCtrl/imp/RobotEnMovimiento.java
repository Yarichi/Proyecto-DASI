/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.RobotStatus1;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import org.openide.util.Exceptions;

/**
 *
 * @author FGarijo
 */
// public class RobotEnMovimiento extends MovimientoCtrlImp implements ItfUsoMovimientoCtrl{
  public class RobotEnMovimiento extends EstadoAbstractoMovRobot {
    private RobotStatus1 robotStatus;
    private boolean estamosEnDestino=false;
    private HebraMonitorizacionLlegada monitorizacionLlegadaDestino = null;
    private  Semaphore semaforo;
    private ExecutorService executorService1;
    private Future ejecucionHebra;
    public  RobotEnMovimiento (MaquinaEstadoMovimientoCtrl maquinaEstados){
 //       this.inicializarMovimientoCtrl(robotId);
     super (maquinaEstados,MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotEnMovimiento);
     semaforo=new Semaphore(1);
      executorService1 = Executors.newSingleThreadExecutor();
      
    }
    
    @Override
        public  void moverAdestino(String identdest,Coordinate coordDestino, double velocidadCrucero) { 
//            try {
//			semaforo.acquire();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//           if ( identdest.equals(identDestino)){
              this.trazas.trazar (this.identComponente, " Se esta avanzando hacia el destino : " + this.identDestino, InfoTraza.NivelTraza.debug);
              if (velocidadCrucero<= 0)trazas.trazar(identComponente, "La velocidad debe ser mayor que cero. Se ignora la operacion", InfoTraza.NivelTraza.error);
              else this.velocidadCrucero = velocidadCrucero;
//            }else { 
// cambair destino
               if(monitorizacionLlegadaDestino!=null ){
                   monitorizacionLlegadaDestino.finalizar();
                   this.trazas.trazar (this.identComponente, " Se da orden de finalizar a la hebra de monitorizacion", InfoTraza.NivelTraza.debug);
                       ejecucionHebra.cancel(true);
//                   executorService1.shutdown();
                   
                   
               }
//               this.trazas.trazar(identComponente, "Se ejecuta  una  orden de mover a destino."+ identdest + " El robot esta en el estado : movimiento . "
//				+ " CoordActuales =  "+this.robotposicionActual.toString() + " CoordDestino =  " +this.destinoCoord.toString(), InfoTraza.NivelTraza.debug);
               this.trazas.trazar(this.identComponente, "Se ejecuta  una  orden de mover a destino."+ identdest + " El robot esta en el estado : movimiento . ", InfoTraza.NivelTraza.debug);
               this.velocidadCrucero = velocidadCrucero;
               this.destinoCoord = (Coordinate)coordDestino.clone();
               this.identDestino = identdest;
               monitorizacionLlegadaDestino = new HebraMonitorizacionLlegada(this.identAgente, energiaRobot, this, itfusoRecVisSimulador);
//               this.monitorizacionLlegadaDestino.cambiarDestino(identDestino,  coordDestino, velocidadCrucero);        
               monitorizacionLlegadaDestino.inicializarDestino(identDestino,this.robotposicionActual,destinoCoord,velocidadCrucero);
              ejecucionHebra = executorService1.submit(monitorizacionLlegadaDestino);
//               monitorizacionLlegadaDestino.run();
              
//                  }
//           this.semaforo.release();
         }
    @Override
        public void cambiaVelocidad( double nuevaVelocidadCrucero) {
            this.velocidadCrucero = nuevaVelocidadCrucero;
        }
    @Override
        public synchronized void cambiaDestino(String identdest,Coordinate coordDestino) {
        // Habria que obtener la posicion actual y recalcular la distancia y el tiempo
        // lo dejamos con mover a destino desde la posicion inicial 
            this.destinoCoord = coordDestino;
            this.identDestino = identdest;
            this.monitorizacionLlegadaDestino.finalizar();
            this.moverAdestino(identDestino,destinoCoord,this.velocidadCrucero);
        }
    
    @Override
        public void parar(){
            if (monitorizacionLlegadaDestino!=null ){
                this.monitorizacionLlegadaDestino.finalizar();
                
//                this.trazas.trazar (this.identComponente, " Se ejecuta una orden de parada y se da orden de finalizar a la hebra de monitorizacion", InfoTraza.NivelTraza.debug);
                try{       
                ejecucionHebra.cancel(true);
                } catch (Exception e) {
                    this.trazas.trazar (this.identComponente, " Se ejecuta una orden de parada. La hebra ha finalizado", InfoTraza.NivelTraza.debug);
               }
                this.maquinaEstados.setCoordenadasActuales(monitorizacionLlegadaDestino.getCoordRobot());
            }
            
            this.maquinaEstados.cambiarEstado (MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotParado).parar();
//            this.trazas.trazar (this.identAgente +"."+this.getClass().getSimpleName(), " transito al estado parado ", InfoTraza.NivelTraza.debug);
//            this.maquinaEstados.getEstadoActual().parar();
        }
    @Override
        public void continuar(){
            this.trazas.trazar (this.identAgente +"."+this.getClass().getSimpleName(), " ignoro la operacion porque estoy parado ", InfoTraza.NivelTraza.debug); 
        }
   
        public void bloquear(){
            if (monitorizacionLlegadaDestino!=null ) this.monitorizacionLlegadaDestino.finalizar();
            this.maquinaEstados.cambiarEstado (MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot.RobotBloqueado).parar();
    }
    @Override
    public synchronized Coordinate getCoordenadasActuales(){
//        if(monitorizacionLlegadaDestino==null)return this.robotposicionActual;
//        else return this.monitorizacionLlegadaDestino.getCoordRobot();
            
        return this.robotposicionActual;
       
}
//    @Override
//    public HebraMonitorizacionLlegada getHebraMonitorizacionLlegadaDestino() {
//            // TODO Auto-generated method stub
//            return null;
//    }
    
    public void inicializarInfoMovimiento(Coordinate coordInicial, double velocidadInicial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getEstamosEnDestino() {
      return this.estamosEnDestino;
    }

    @Override
    public void setEstamosEnDestino() {
       this.estamosEnDestino=true;
       this.maquinaEstados.setEnergiaRobot(energiaRobot);
       this.maquinaEstados.enDestino();
    }
    


}
