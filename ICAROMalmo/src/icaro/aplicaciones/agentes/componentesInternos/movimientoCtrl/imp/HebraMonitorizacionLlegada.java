
package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp;


import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.ItfUsoRecursoVisualizadorEntornosSimulacion;
import org.apache.log4j.Logger;


/**
 * Clase interna que se encarga de generar eventos de monitorizacin cada cierto tiempo
 *
* 
 */
public class HebraMonitorizacionLlegada implements Runnable {
	/**
	 * Milisegundos que esperar antes de lanzar otra monitorizacin
	 * @uml.property  name="milis"
	 */
	protected long milis;

	/**
	 * Indica cundo debe dejar de monitorizar
	 * @uml.property  name="finalizar"
	 */
//	protected volatile boolean finalizar = false;
        private volatile boolean finalizar = false;
	/**
	 * Agente reactivo al que se pasan los eventos de monitorizacin
	 * @uml.property  name="agente"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private RobotEnMovimiento estadoRobot;
	private Logger log = Logger.getLogger(this.getClass().getSimpleName());
	/**
	 * Evento a producir
	 * @uml.property  name="evento"
	 */
	private String identDestino, identRobot;
	private   volatile Coordinate coordActuales;
	private volatile Coordinate coordDestino;
	private volatile Coordinate coordIncremento;
	private double velocidadRobot; // en metros por segundo 
	//     protected int intervaloEnvioInformacion = 1000; // por defecto en ms . Deberia ser configurable
	private volatile double pendienteRecta;
	private volatile boolean estamosEnDestino;
	private double espacioRecorrido ;
	//     protected double distanciaDestino ;
	private boolean pendienteInfinita = false ;
	private volatile boolean parar = false ;
	private volatile boolean enDestino = false ;
	private float distanciaArecorrer ;
	private float b ; // punto corte recta con eje Y
	private int dirX =0, dirY=0,incrementoDistancia=0;
	private int intervaloEnvioInformesMs ;
	private int distanciaRecorridaEnIntervaloInformes ;
	private long tiempoParaAlcanzarDestino = 3000;
//	private RobotStatus1 robotStatus;
        private int energiaRobot= 0;
        
	public ItfUsoRecursoVisualizadorEntornosSimulacion itfusoRecVisSimulador;

	private int contadorAuxiliar=0;
    private MaquinaEstadoMovimientoCtrl contrMovimiento;
    private int costeEnergeticoPorPaso= 1;
    private int energiaMinimaParaMoverse= 5000; // otra constante a revisar

	//    private int numeroPuntos = 20;
	/**
	 * Constructor
	 *
	 * @param milis Milisegundos a esperar entre cada monitorizacion
	 */
	public HebraMonitorizacionLlegada(String idRobot, int energRobot,RobotEnMovimiento estdoRobot, ItfUsoRecursoVisualizadorEntornosSimulacion itfRecVisSimulador) {
//		super("HebraMonitorizacion "+idRobot);  
		estadoRobot =estdoRobot;
		this.itfusoRecVisSimulador = itfRecVisSimulador;
		identRobot = idRobot;
                energiaRobot= energRobot; // arbitrario : cambiar por algo realista
	}
	public synchronized void inicializarDestino (String idDestino,Coordinate coordRobot,Coordinate coordDest, double velocidad ){    
		//      this.finalizar= false;
		coordActuales =(Coordinate)coordRobot.clone() ;
		coordDestino = (Coordinate)coordDest.clone();
		velocidadRobot = velocidad; // 
		//      intervaloEnvioInformacion= intervEnvioInformacion; 
		espacioRecorrido = 0;
		identDestino = idDestino;
		//      this. pendienteRecta = (float) ((coordDestino.y-coordActuales.y)/(coordDestino.x-coordActuales.x));
		log.debug ("Coord Robot " + identRobot + " iniciales -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");
		log.debug ("Coord Robot " + identRobot + " destino -> ("+this.coordDestino.getX() + " , " + this.coordDestino.getY() + ")");
//		this.setDaemon(true);
		//      coordIncremento = this.calcularIncrementosCoordenadasAvelocidadConstante(intervaloEnvioInformacion);
		//     this.evento = notificacionAProducir;
		this.finalizar= false;
                this.enDestino = false;
		//       distanciaDestino = this.distanciaEuclidC1toC2(coordActuales, coordDestino);
		double incrX=(coordDestino.getX()-coordActuales.getX());
		double incrY=(coordDestino.getY()-coordActuales.getY());
		if (incrX>0)dirX=1 ;
		else dirX=-1;
		if (incrY>0)dirY=1 ;
		else dirY=-1;
		if (incrX==0 &&incrY!=0){pendienteInfinita= true;
		distanciaArecorrer = (float)incrY;
		}else if (incrX==0 &&incrY==0) finalizar=true;
		else { pendienteRecta = (float) Math.abs(incrY/incrX);
		this.distanciaArecorrer =(float) Math.sqrt(incrX*incrX+incrY*incrY);
		this.b = (float) (coordActuales.y -pendienteRecta * coordActuales.x ) ;

		}
		intervaloEnvioInformesMs = (int)velocidadRobot* 20;
		distanciaRecorridaEnIntervaloInformes = 1;
	}
        public synchronized void cambiarDestino (String idDestino,Coordinate coordDest, double velocidad ){ 
		coordDestino = (Coordinate)coordDest.clone();
		velocidadRobot = velocidad; // 
		//      intervaloEnvioInformacion= intervEnvioInformacion; 
		espacioRecorrido = 0;
		identDestino = idDestino;
		//      this. pendienteRecta = (float) ((coordDestino.y-coordActuales.y)/(coordDestino.x-coordActuales.x));
		log.debug ("Coord Robot " + identRobot + " iniciales -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");
		log.debug ("Coord Robot " + identRobot + " destino -> ("+this.coordDestino.getX() + " , " + this.coordDestino.getY() + ")");
//		this.setDaemon(true);
		//      coordIncremento = this.calcularIncrementosCoordenadasAvelocidadConstante(intervaloEnvioInformacion);
		//     this.evento = notificacionAProducir;
		this.finalizar= false;
                this.enDestino = false;
        }

	public void pararRobot(){
		this.finalizar=true;
	}
	
	/**
	 * Termina la monitorizacin
	 */
	public synchronized void finalizar() {
		this.finalizar= true;
		this.notifyAll();

	}
	public synchronized void setCoordRobot(Coordinate robotCoord) {
		this.coordActuales= (Coordinate)robotCoord.clone() ;
	}
	public Coordinate getCoordRobot() {
		return (Coordinate)coordActuales.clone();
	}
//	public synchronized void setCoordDestino(Coordinate destCoord) {
//        try {
//            this.coordDestino= (Coordinate)destCoord.clone();
//            if (itfusoRecVisSimulador != null)
//                this.itfusoRecVisSimulador.inicializarDestinoRobot(identRobot, coordActuales, identDestino,coordDestino, velocidadRobot);
//        } catch (Exception ex) {
//            Exceptions.printStackTrace(ex);
//	}
//    }
//	public synchronized Coordinate getCoordDestino() {
//		return coordDestino;
//	}
//	public synchronized void setVelocidadRobot(double velRobot) {
//		this.velocidadRobot= velRobot;
//	}

	@Override
	public  void run() {
//		int energiaActual=this.robotStatus.getAvailableEnergy();
//           int energiaActual= 500
            Coordinate coordenadaEnviar = this.coordActuales;
		//       double espacioRecorridoEnIntervalo = velocidadRobot*intervaloEnvioInformacion;
		log.debug ("Coord Robot " + identRobot + " iniciales -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");
		log.debug ("Coord Robot " + identRobot + " destino -> ("+this.coordDestino.getX() + " , " + this.coordDestino.getY() + ")");
		//       System.out.println("Coord Robot " + identRobot + " iniciales -> ("+this.coordActuales.x + " , " + this.coordActuales.y + ")");
		//      this.itfusoRecVisSimulador.mostrarMovimientoAdestino(identRobot,identDestino, coordActuales,velocidadRobot);
 log.debug("Inicio ciclo de envio de coordenadas  " + identRobot + " en posicion actual -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");           
		while (!this.finalizar && (!enDestino)) {
			try {
			Thread.sleep(intervaloEnvioInformesMs);
                     calcularNuevasCoordenadas (distanciaRecorridaEnIntervaloInformes);                      
//                     log.debug("Coord Robot " + identRobot + " calculadas -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");  
                    enDestino = ((coordActuales.getX()-coordDestino.getX())*dirX>=0 &&(coordActuales.getY()-coordDestino.getY())*dirY>=0);
                     finalizar = (coordActuales.x<0.5 || coordActuales.y<0.5 ||energiaRobot< energiaMinimaParaMoverse);
                    if (itfusoRecVisSimulador != null)
                         coordenadaEnviar = (Coordinate)coordActuales.clone();
			this.itfusoRecVisSimulador.mostrarPosicionRobot(identRobot, coordenadaEnviar);
                    this.estadoRobot.setCoordenadasActuales(coordenadaEnviar);
			} catch (Exception e) {
				// TODO Auto-generated catch block
                            finalizar = true;
//				 e.printStackTrace();
			}
		}
		if (enDestino ){
			finalizar = true;
            try {
                Thread.sleep(tiempoParaAlcanzarDestino);
                        this.estadoRobot.setEnergiaRobot(energiaRobot);
			this.estadoRobot.setEstamosEnDestino();
			log.debug("Coord Robot En thread  " + identRobot + " en destino -> ("+this.coordActuales.getX() + " , " + this.coordActuales.getY() + ")");
          System.out.println("Coord Robot En thread  " + identRobot + " en destino -> ("+this.coordActuales.x + " , " + this.coordActuales.y + ")");           
            } catch (Exception ex) {
                log.error( ex);
                 finalizar = true;
		}
	}
              }

	private void calcularNuevasCoordenadas (long incrementoDistancia){
		// suponemos avance en linea recta 
		// formula aplicada x1 = xo + sqrt( espacioRecorrido**2 / (1 + pendienteRecta**2))
		//  y1 = y0 +(x1-x0)*pendienteRecta

		if (pendienteInfinita){
			//            constIncrX = 0;
			//            constIncrY= incrementoDistancia;
			this.coordActuales.setY(coordActuales.getY() + incrementoDistancia*dirY);
		}
		else {
			// incremmento de x respecto a distancia recorrida
            this.coordActuales.setY(coordActuales.getY() + pendienteRecta*incrementoDistancia*dirY);
            this.coordActuales.setX(coordActuales.getX() + incrementoDistancia*dirX);
            this.energiaRobot= energiaRobot-costeEnergeticoPorPaso;
			}
			}

}
