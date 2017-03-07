package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.RobotStatus1;

public interface ItfUsoMovimientoCtrl {
	public void inicializarInfoMovimiento(int energiaRobot,Coordinate coordInicial, double velocidadInicial) ;
        public void moverAdestino(String identDest,Coordinate coordDestino, double velocidadCrucero) ;
        public void cambiaVelocidad( double nuevaVelocidadCrucero) ;
        public void cambiaDestino(String identDest,Coordinate coordDestino) ;
        public Coordinate getCoordenasDestino();
 //       public void actualizarCoordenadas(Coordinate nuevasCoordenadas) ;
        public Coordinate getCoordenadasActuales() ;
        public  void setCoordenadasActuales(Coordinate nuevasCoordenadas) ;
        public void parar();
        public void bloquear();
        public void continuar();
        public boolean estamosEnDestino(String identDestino);
        public void imposibleAvanzarADestino();
	public  int getEnergiaRobot();
        public  int getContadorGastoEnergia();
        public void initContadorGastoEnergia();
        public String getIdentEstadoMovRobot();
        
}