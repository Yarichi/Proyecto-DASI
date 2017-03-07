package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.RobotStatus1;

public class RobotBloqueadoPorObstaculo extends EstadoAbstractoMovRobot {
	 private RobotStatus1 robotStatus;
    protected HebraMonitorizacionLlegada monitorizacionLlegadaDestino;

	public RobotBloqueadoPorObstaculo(MaquinaEstadoMovimientoCtrl maquinaEstds,
			icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp.MaquinaEstadoMovimientoCtrl.EstadoMovimientoRobot identEstadoAcrear) {
		super(maquinaEstds, identEstadoAcrear);
		
	}

	public void bloqueadoPorObstaculo() {
		// TODO Auto-generated method stub
		
	}

	public void bloquear() {
		
	}

	@Override
	public void moverAdestino(String identDest, Coordinate coordDestino, double velocidadCrucero) {
		// TODO Auto-generated method stub
		
	}


	public boolean estamosEnDestino(String identDest) {
		// TODO Auto-generated method stub
		return false;
	}

	 @Override
    public  Coordinate getCoordenadasActuales(){
        return this.monitorizacionLlegadaDestino.getCoordRobot();
    }
//	@Override
//	public HebraMonitorizacionLlegada getHebraMonitorizacionLlegadaDestino() {
//		return null;
//	}
	
	public void setRobotStatus(RobotStatus1 robotStatus) {
		this.robotStatus = robotStatus;
		
	}
    public void inicializarInfoMovimiento(Coordinate coordInicial, double velocidadInicial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void cambiaVelocidad(double nuevaVelocidadCrucero) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cambiaDestino(String identDest, Coordinate coordDestino) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void continuar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void parar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getEstamosEnDestino() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setEstamosEnDestino() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}

