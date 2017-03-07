package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.InfoCasoSimulacion;
import icaro.aplicaciones.Rosace.informacion.PuntoEstadistica;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.recursos.recursoEstadistica.imp.visualizacionEstadisticas.VisualizacionJfreechart;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.ItfUsoRecursoPersistenciaEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.ItfUsoRecursoVisualizadorEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.visualizacionResultados.ControladorVisualizResultados;
import icaro.infraestructura.entidadesBasicas.InfoTraza.NivelTraza;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Informe;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Temporizador;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.awt.Color;
import java.io.File;
import static java.rmi.server.LogStream.log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.openide.util.Exceptions;

//Other imports used by this Resource
//#start_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS
//#end_nodespecialImports:specialImports <--specialImports-- DO NOT REMOVE THIS
public class ClaseGeneradoraRecursoVisualizadorEntornosSimulacion extends ImplRecursoSimple implements ItfUsoRecursoVisualizadorEntornosSimulacion {

    private VisorMovimientoEscenario visorEscenarioMov;
    private VisualizacionJfreechart visualizadorJFchart;
    private NotificadorInfoUsuarioSimulador notifEvt;
    private String recursoId;
    private String identAgenteaReportar;
//    private Map<String,HebraMovimiento> tablaHebrasMov;
    private int coordX = 40;
    private int coordY = 40;  // valores iniciales 
//   private int coordX, coordY ; // coordenadas de visualizacion  se le dan valores iniciales y se incrementan para que las ventanas no coincidan
    private ControladorVisualizacionSimulRosace controladorIUSimulador;
    private ControladorVisualizResultados controladorResultados;
 // para prueba de integracion 
    private String directorioPersistencia = VocabularioRosace.NombreDirectorioPersistenciaEscenarios+File.separator;
//    private String identFicheroEscenarioSimulacion=directorioPersistencia+"modeloOrg_JerarquicoNumRobts_4NumVicts_2.xml" ;
    private String identFicheroEscenarioSimulacion;
    private Coordinate coordDestino;
    private String identDestino;
    private boolean escenarioMovAbierto;
    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass().getSimpleName());
//    private VisorMovimientoEscenario visorMovimiento;
    private ControladorGestionEscenariosRosace controladorGestEscenarios;
    private MemComunControladores memoriaComunControladores;

    public ClaseGeneradoraRecursoVisualizadorEntornosSimulacion(String idRecurso) throws Exception {
        //#start_nodeconstructorMethod:constructorMethod <--constructorMethod-- DO NOT REMOVE THIS
        super(idRecurso);
        recursoId = idRecurso;
        try {
            trazas.aceptaNuevaTraza(new InfoTraza(idRecurso, "El constructor de la clase generadora del recurso " + idRecurso + " ha completado su ejecucion ....", InfoTraza.NivelTraza.debug));
            notifEvt = new NotificadorInfoUsuarioSimulador(recursoId, identAgenteaReportar);
            // un agente debe decirle al recurso a quien debe reportar . Se puede poner el agente a reportar fijo
//            visorEscenarios = new VisorEscenariosRosace3();
//            visorEscenarios = new VisorEscenariosRosace();
//            ventanaControlCenterGUI = new ControlCenterGUI4(notifEvt);
            
            controladorIUSimulador = new ControladorVisualizacionSimulRosace(notifEvt);
            controladorGestEscenarios= new ControladorGestionEscenariosRosace(notifEvt);
            VisorControlSimuladorRosace visorControl = new VisorControlSimuladorRosace(controladorIUSimulador);
            visorControl.setControladorGestionEscenarios(controladorGestEscenarios);
            visorControl.setVisible(true);
            memoriaComunControladores = new MemComunControladores();
            controladorIUSimulador.setVisorControlSimulador(visorControl);
            controladorIUSimulador.setMemComunControladores(memoriaComunControladores);
            controladorGestEscenarios.setVisorControlSimulador(visorControl);
            controladorGestEscenarios.setMemComunControladores(memoriaComunControladores);
            controladorResultados = new ControladorVisualizResultados ();
            
        } catch (Exception e) {
            this.trazas.trazar(recursoId, " Se ha producido un error en la creación del recurso : " + e.getMessage(), InfoTraza.NivelTraza.error);
            this.itfAutomata.transita("error");
            throw e;
        }
    }

    @Override
    public void mostrarVentanaControlSimulador(String rutaFicheroEscenario)throws Exception{
//    ventanaControlCenterGUI.setVisible(true);
        // debe devolver un booleano cuando no se pueda abrir el fichero por las causas que sea
        identFicheroEscenarioSimulacion = rutaFicheroEscenario;
      if ( ! controladorIUSimulador.abrirVisorConEscenario( identFicheroEscenarioSimulacion))
        trazas.aceptaNuevaTraza(new InfoTraza(recursoId, "El escenario  : " + identFicheroEscenarioSimulacion + " no existe o no se puede abrir ", InfoTraza.NivelTraza.error));
//      controladorIUSimulador.abrirVisorMovimientoConEscenario(identFicheroEscenarioSimulacion);
}
    @Override
    public void mostrarVentanaControlSimulador(EscenarioSimulacionRobtsVictms escenarioSimulacion)throws Exception{
//    ventanaControlCenterGUI.setVisible(true);
        // debe devolver un booleano cuando no se pueda abrir el fichero por las causas que sea
      if ( escenarioSimulacion !=null)
        trazas.aceptaNuevaTraza(new InfoTraza(recursoId, "Se abre el  escenario  : " + escenarioSimulacion.getIdentEscenario() , InfoTraza.NivelTraza.info));
      this.controladorIUSimulador.abrirVisorControlSimConEscenario(escenarioSimulacion);
}
    @Override
    public boolean escenarioSimulacionDefinido()throws Exception {
       return controladorGestEscenarios.hayEscenarioSimulAbierto();
    }
    @Override
    public void obtenerEscenarioSimulacion (String modOrganizativo, int numRobots )throws Exception {
        this.controladorGestEscenarios.peticionObtenerEscenarioSimulacion( modOrganizativo,  numRobots);
//       EscenarioSimulacionRobtsVictms escenarioActual= null;
//       int numerointentos = 0;int maxIntentos = 2;
//       while ( numerointentos<maxIntentos && escenarioActual==null ){
//         escenarioActual =  controladorIUSimulador.obtenerEscenarioSimulacion(modOrganizativo,numRobots );
//       
//         numerointentos++;
//        }
//       this.notifEvt.informaraOtroAgenteReactivo(new InfoContEvtMsgAgteReactivo("escenarioDefinidoPorUsuario", escenarioActual), identAgenteaReportar);
    }
    @Override
     public void obtenerEscenarioSimulacion (String identFichero,String modOrganizativo, int numRobots  )throws Exception{
         this.controladorGestEscenarios.peticionObtenerEscenarioSimulacion( identFichero, modOrganizativo,  numRobots);
     }
    @Override
    public void notificarRecomendacion (String titulo, String motivo, String recomendacion)throws Exception{
        this.controladorIUSimulador.notifRecomendacionUsuario( titulo,  motivo,  recomendacion);
    }
    @Override
     public boolean peticionConfirmacionInformacion(String preguntaAconfirmar){
         return this.controladorIUSimulador.peticionConfirmacionInformacion( preguntaAconfirmar);
     }
    @Override
    public void crearEInicializarVisorGraficaEstadisticas(String tituloVentanaVisor,
            String tituloLocalGrafico,
            String tituloEjeX,
            String tituloEjeY) throws Exception {
        PlotOrientation orientacionPlot = PlotOrientation.VERTICAL;
        boolean incluyeLeyenda = true;
        boolean incluyeTooltips = true;

        Color colorChartBackgroundPaint = Color.white;
        Color colorChartPlotBackgroundPaint = Color.lightGray;
        Color colorChartPlotDomainGridlinePaint = Color.white;
        Color colorChartPlotRangeGridlinePaint = Color.white;
//		VisualizacionJfreechart visualizadorJFchart = new VisualizacionJfreechart("tituloVenanaVisor");
        visualizadorJFchart = new VisualizacionJfreechart(tituloVentanaVisor);
        visualizadorJFchart.inicializacionJFreeChart(tituloLocalGrafico, tituloEjeX, tituloEjeY, orientacionPlot, incluyeLeyenda, incluyeTooltips, false);
        visualizadorJFchart.setColorChartBackgroundPaint(colorChartBackgroundPaint);
        visualizadorJFchart.setColorChartPlotBackgroundPaint(colorChartPlotBackgroundPaint);
        visualizadorJFchart.setColorChartPlotDomainGridlinePaint(colorChartPlotDomainGridlinePaint);
        visualizadorJFchart.setColorChartPlotRangeGridlinePaint(colorChartPlotRangeGridlinePaint);

    }

    ;
    @Override
    public void crearVisorGraficasLlegadaYasignacionVictimas(int numeroRobotsSimulacion, int numeroVictimasDiferentesSimulacion, int intervaloSecuencia, String identificadorEquipo) throws Exception {
        String tituloVentanaVisor = "Simulacion: " + numeroRobotsSimulacion + " R; " + numeroVictimasDiferentesSimulacion + " Vic; " + intervaloSecuencia + " mseg ; " + " tipo simulacion->" + identificadorEquipo;
        String tituloLocalGrafico = "Victim's Notification and Assignment to Team members ";
        String tituloEjeX = "Number of Victim's Notifications";
        String tituloEjeY = "Time in miliseconds";
        crearEInicializarVisorGraficaEstadisticas(tituloVentanaVisor, tituloLocalGrafico, tituloEjeX, tituloEjeY);
        mostrarVisorGraficaEstadisticas();   
    }

    @Override
    public void crearVisorGraficasTiempoAsignacionVictimas(int numeroRobotsSimulacion, int numeroVictimasDiferentesSimulacion, int intervaloSecuencia, String identificadorEquipo) throws Exception {
        String tituloVentanaVisor = "Simulacion: " + numeroRobotsSimulacion + " R; " + numeroVictimasDiferentesSimulacion + " Vic; " + intervaloSecuencia + " mseg ; " + " tipo simulacion->" + identificadorEquipo;
        String tituloLocalGrafico = "Elapsed Time to Assign a New Victim";
        String tituloEjeX = "Number of Victim's Notifications";
        String tituloEjeY = "Time in miliseconds";
        crearEInicializarVisorGraficaEstadisticas(tituloVentanaVisor, tituloLocalGrafico, tituloEjeX, tituloEjeY);
        mostrarVisorGraficaEstadisticas();
    }

    @Override
    public void visualizarLlegadaYasignacionVictimas(ArrayList<PuntoEstadistica> llegada,
            ArrayList<PuntoEstadistica> asignacion,ArrayList<PuntoEstadistica> rescate ) throws Exception {
        String tituloSerieLlegadaVictimas = "Notification Time";
        int indexSerieLlegadaVictimas = 1;
        String tituloSerieasignacionVictimas = "Assignment Time";
        int indexSerieasignacionVictimas = 2;
        aniadirSerieAVisorGraficaEstadisticas(tituloSerieLlegadaVictimas, indexSerieLlegadaVictimas, llegada);
        aniadirSerieAVisorGraficaEstadisticas(tituloSerieasignacionVictimas, indexSerieasignacionVictimas, asignacion);
        String tituloSerieRescateVictimas = "Rescue Time";
        int indexSerieRescateVictimas = 3;
        aniadirSerieAVisorGraficaEstadisticas(tituloSerieRescateVictimas, indexSerieRescateVictimas, rescate);
    }
    @Override
    public void visualizarLlegadaYasignacionVictimas(InfoCasoSimulacion infocaso)throws Exception{
        this.controladorResultados.visualizarLlegadaYasignacionVictimas2(infocaso);
    }
    @Override
    public void visualizarTiemposRescatePorRobot(InfoCasoSimulacion infocaso)throws Exception{
        this.controladorResultados.visualizarTiemposRescatePorRobot(infocaso);
    }

    @Override
    public void visualizarTiempoAsignacionVictimas(ArrayList<PuntoEstadistica> elapsed) throws Exception {
        String tituloSerieElapasedVictimas = "Elapsed Time";
        int indexSerieElapasedVictimas = 1;
        aniadirSerieAVisorGraficaEstadisticas(tituloSerieElapasedVictimas, indexSerieElapasedVictimas, elapsed);

    }

    @Override
    public void mostrarVisorGraficaEstadisticas() throws Exception {
        visualizadorJFchart.showJFreeChart(coordX, coordY);
        coordX = 10 * coordX; // coordY= 5*coordY;
    }
	
    @Override
    public void aniadirSerieAVisorGraficaEstadisticas(String tituloSerie, int indexSerie,
            ArrayList<PuntoEstadistica> puntosEstadistica) throws Exception {

        XYSeries serie;

        serie = new XYSeries(tituloSerie);

        for (int i = 0; i < puntosEstadistica.size(); i++) {

            serie.add(puntosEstadistica.get(i).getX(), puntosEstadistica.get(i).getY());
        }

        //serie.add(1.0, 1.5);

        visualizadorJFchart.addSerie(indexSerie, Color.green, serie);

    }

    //End methods that implement VisualizadorEstadisticas resource use interface


//#start_nodeterminaMethod:terminaMethod <--terminaMethod-- DO NOT REMOVE THIS        

    @Override
    public void termina() {
        trazas.aceptaNuevaTraza(new InfoTraza(this.id, "Terminando recurso" + this.id + " ....", InfoTraza.NivelTraza.debug));

        //Si es un recurso de visualizacion es necesaria una llamar a dispose de la ventana de visualizacion. Algo parecido a lo siguiente	
        //this.jvariableLocalReferenciaVisualizador.dispose(); //Destruye los componentes utilizados por este JFrame y devuelve la memoria utilizada al Sistema Operativo 	 

        super.termina();
    }

    //Fragmento de codigo para mostrar por la ventana de trazas de este recurso un mensaje	
    //trazas.aceptaNuevaTraza(new InfoTraza(this.idRecurso,"Mensaje mostrado en la ventana de trazas del recurso ....",InfoTraza.NivelTraza.debug));
    @Override
    public void mostrarEscenarioMovimiento(EscenarioSimulacionRobtsVictms infoEscenario) {
        //   throw new UnsupportedOperationException("Not supported yet.");
        // verificar que el agente a reportar esta definido , si no lo esta los eventos no se envian a nadie
        controladorIUSimulador.peticionMostrarEscenarioMovimiento(infoEscenario);
    }
    public void mostrarEscenarioMovimiento(String rutaEscenario) {
        //   throw new UnsupportedOperationException("Not supported yet.");
        // verificar que el agente a reportar esta definido , si no lo esta los eventos no se envian a nadie
        if (visorEscenarioMov == null) {
            try {
              if( controladorIUSimulador.abrirVisorMovimientoConEscenario(rutaEscenario)){
                  visorEscenarioMov.setVisible(true);
                  escenarioMovAbierto=true;
              };
               
            } catch (Exception ex) {
                Logger.getLogger(ClaseGeneradoraRecursoVisualizadorEntornosSimulacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        visorEscenarioMov.setVisible(true);
//        else {
//            trazas.trazar(this.id, "El identificador del agente controlador no esta definido. El agente controlador debe definirlo"
//                    + "o definir el identificador del agente en esta clase", InfoTraza.NivelTraza.error);
//        }
    }

    @Override
    public void setIdentAgenteAReportar(String identAgenteAReportar) {
        super.setIdentAgenteAReportar(identAgenteAReportar);
        notifEvt.setIdentAgenteAReportar(identAgenteAReportar);
    }

    @Override
    public void mostrarEscenario() throws Exception {
//         if (visorEscenarios == null) {
//            try {
//                visorEscenarios = new VisorEscenariosRosace ();
//            } catch (Exception ex) {
//                Logger.getLogger(ClaseGeneradoraRecursoVisualizadorEntornosSimulacion.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } 
//        visorEscenarios.setVisible(true);
    }

    @Override
    public void mostrarResultadosFinSimulacion() throws Exception {
        String directorioTrabajo = System.getProperty("user.dir");  //Obtener directorio de trabajo 
        String nombreFicheroAsignVictim = "asigVictimasObjetos";
        String directorioPersistencia = VocabularioRosace.NombreDirectorioPersistenciaResSimulacion + "/";
        String identFicheroInfoAsigVictimasObj = directorioPersistencia + nombreFicheroAsignVictim + ".tmp";
        //  String identFicheroInfoAsigVictimasXML = directorioPersistencia + nombreFicheroAsignVictim + ".xml";
        String identFicheroInfoAsigVictimasXML = directorioPersistencia + VocabularioRosace.NombreFicheroSerieInfoAsignacionVictimas + "<TimeTag>.xml";
        String identFicheroSerieAsigVictimasXML = directorioPersistencia + VocabularioRosace.NombreFicheroSerieAsignacion + "<TimeTag>.xml";
        String identFicheroLlegyAsigVictimasXML = directorioPersistencia + VocabularioRosace.NombreFicheroSerieLlegadaYasignacion + "<TimeTag>.xml";
        String msg = "FIN DE LA SIMULACION !!!.\n";
        msg = msg + "Se ha completado la captura de todas las estadisticas para la simulacion actual.\n";
        msg = msg + "Los ficheros de estadisticas se encuentran en el directorio " + directorioTrabajo + "/" + directorioPersistencia + "\n";
        msg = msg + "Los ficheros de estadisticas son los siguientes:\n";
        msg = msg + directorioTrabajo + "/" + identFicheroInfoAsigVictimasXML + "\n";
        msg = msg + directorioTrabajo + "/" + identFicheroSerieAsigVictimasXML + "\n";
        msg = msg + directorioTrabajo + "/" + identFicheroLlegyAsigVictimasXML + "\n";
        //      msg = msg + directorioTrabajo + "/" + ConstantesRutasEstadisticas.rutaficheroXMLEstadisticasLlegadaYAsignacionVictimas + "\n";
        //      msg = msg + directorioTrabajo + "/estadisticas/" + "EstIntLlegadaYAsignacionVictims" + "FECHA.xml" + "\n";
        JOptionPane.showMessageDialog(null, msg);
    }
    @Override
    public synchronized void mostrarPosicionRobot(String identRobot, Coordinate coordRobot)throws Exception{
//      public synchronized void mostrarPosicionRobot(String identRobot, Coordinate coordRobot,Coordinate coordDestino,String identDestino)throws Exception{
       Integer coordX = (int) coordRobot.getX();
       Integer coordY = (int) coordRobot.getY();
//        if ( Math.abs (coordX-coordDestino.getX())<0.6 && Math.abs (coordY-coordDestino.getY())<0.6){
// // notificamos llegada a destino
//            this.notifEvt.sendNotificacionLlegadaDestino(identRobot, identDestino);
//            log.debug("Envio notificacion de destino  " + identRobot + " en destino -> ("+coordX + " , " + coordY + ")");
////        Temporizador informeTemp = new Temporizador (500,itfProcObjetivos,informeLlegada);  
//        }
//        else {
//            if(visorEscenarioMov==null){
//                visorEscenarioMov = controladorIUSimulador.getVisorMovimiento();
//                if(visorEscenarioMov==null)controladorGestEscenarios.peticionAbrirEscenario(this.);
//            }else{
//                visorEscenarioMov.setVisible(true);
//                visorEscenarioMov.cambiarPosicionRobot(identRobot, coordX, coordY);
////            controladorIUSimulador.peticionCambiarPosicionRobot(identRobot, coordX, coordY);
//            }
            
//        visorEscenarios.moverRobot(identRobot, coordX, coordX);
        controladorIUSimulador.peticionCambiarPosicionRobot(identRobot, coordX, coordY);
      }
//    @Override
//    public synchronized void mostrarPosicionActualRobot(String identRobot)throws Exception{
//        visorEscenarios.setVisible(true);
//        
//        visorEscenarios.cambiarPosicionRobot(identRobot, coordX, coordY);
//    }
    @Override
    public synchronized void mostrarVictima(String identVictima,String estadoVictima)throws Exception{
        controladorIUSimulador.peticionMostrarVictima( identVictima, estadoVictima);
//        switch (estadoVictima) {
//            case "esperandoRescate":
//                this.visorEscenarioMov.cambiarIconoEntidad(identVictima,VisorMovimientoEscenario.IMAGEmujer);
//                break;
//            case "asignada":
//                this.visorEscenarioMov.cambiarIconoEntidad(identVictima,VisorMovimientoEscenario.IMAGEmujerAsignada);
//                break;
//            case "reAsignada":
//                this.visorEscenarioMov.cambiarIconoEntidad(identVictima,VisorMovimientoEscenario.IMAGEmujerReAsignada);
//                break;
//            case "rescatada":
//                this.visorEscenarioMov.cambiarIconoEntidad(identVictima,VisorMovimientoEscenario.IMAGEmujerRes);
//                break;
//                default:
//             throw new IllegalArgumentException("Estado de la victima no valido : " + estadoVictima);
//        }
    }
    @Override
     public  void inicializarDestinoRobot(String idRobot,Coordinate coordInicial,String destinoId, Coordinate coordDestino, double velocidadInicial){
         this.coordDestino=coordDestino;
         this.identDestino = destinoId;
//        if (idRobot != null )  this.getInstanciaHebraMvto(idRobot).inicializarDestino(idRobot, coordInicial, coordDestino, velocidadInicial);
    } 
    @Override
    public synchronized void mostrarMovimientoAdestino(String idRobot,String identDest,Coordinate coordDestino, double velocidadCrucero) {
//           if (idRobot != null ){
//               this.visorEscenarios.setVisible(true);
//               this.visorEscenarios.cambiarPosicionRobot(idRobot, coordX, coordX);
////               visorEscenarios.moverRobot(idRobot, coordX, coordX);
//           }
    }

    @Override
    public void mostrarIdentsEquipoRobots(ArrayList identList){
//        this.ventanaControlCenterGUI.visualizarIdentsEquipoRobot(identList);
        controladorIUSimulador.peticionVisualizarIdentsRobots(identList);
    }

    @Override
    public void setItfUsoPersistenciaSimulador(ItfUsoRecursoPersistenciaEntornosSimulacion itfUsopersistencia) throws Exception {
        this.controladorGestEscenarios.setIftRecPersistencia(itfUsopersistencia);
        this.controladorIUSimulador.setIftRecPersistencia(itfUsopersistencia);
        this.controladorGestEscenarios.initModelosYvistas();
        this.controladorIUSimulador.initModelosYvistas();
    }

    @Override
    public void visualizarCosteEnergiaRescateVicitimas(InfoCasoSimulacion infoCasoSimul ) throws Exception {
        this.controladorResultados.visualizarCosteEnergiaRescateVictimas( infoCasoSimul );
    }
    @Override
    public void finCasoSimulacion()throws Exception{
        this.controladorIUSimulador.finCasoSimulacion();
    }
}
