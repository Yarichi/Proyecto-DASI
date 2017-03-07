/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.visualizacionResultados;

import icaro.aplicaciones.Rosace.informacion.InfoCasoSimulacion;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.ItfUsoRecursoPersistenciaEntornosSimulacion;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.imp.ClaseGeneradoraRecursoPersistenciaEntornosSimulacion;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.awt.Color;

/**
 *
 * @author FGarijo
 */
public class ControladorVisualizResultados {
   private VisualizacionJfreechart visualidaroJf;
   private ConstructorSeriesDatosParaVisualizar constructorSeries;
    private Object itfUsoRepositorio;
   public ControladorVisualizResultados (){
        visualidaroJf = new VisualizacionJfreechart("VisualizacionResultadosSimulacion");
        constructorSeries = new ConstructorSeriesDatosParaVisualizar();
   }
   public void visualizarLlegadaYasignacionVictimas( InfoCasoSimulacion infoCaso){
       visualidaroJf.setTitle(infoCaso.getIdentEscenario());
       visualidaroJf.crearChartNotifAsigResc();
       
       String tituloSerieLlegadaVictimas = "Notification Time";
        int indexSerieLlegadaVictimas = 1;
//        constructorSeries.extraerSerieRescateVictimas(ConstructorSeriesDatosParaVisualizar.SerieDatosTiempoAsignacion, infoCaso);
        visualidaroJf.addSerie(1, Color.GREEN,
                constructorSeries.extraerSerieRescateVictimas(ConstructorSeriesDatosParaVisualizar.SerieDatosTiempoPeticion, infoCaso));
         visualidaroJf.addSerie(2, Color.GREEN,
                constructorSeries.extraerSerieRescateVictimas(ConstructorSeriesDatosParaVisualizar.SerieDatosTiempoAsignacion, infoCaso));
         visualidaroJf.addSerie(3, Color.GREEN,
                constructorSeries.extraerSerieRescateVictimas(ConstructorSeriesDatosParaVisualizar.SerieDatosTiempoRescate, infoCaso));
        String tituloSerieasignacionVictimas = "Assignment Time";
        int indexSerieasignacionVictimas = 2;
        String tituloSerieRescateVictimas = "Rescue Time";
        int indexSerieRescateVictimas = 3;
        
//        aniadirSerieAVisorGraficaEstadisticas(tituloSerieLlegadaVictimas, indexSerieLlegadaVictimas, llegada);
//        aniadirSerieAVisorGraficaEstadisticas(tituloSerieasignacionVictimas, indexSerieasignacionVictimas, asignacion);
        
//        aniadirSerieAVisorGraficaEstadisticas(tituloSerieRescateVictimas, indexSerieRescateVictimas, rescate);
//        ArrayList<PuntoEstadistica> serieRescateVictimas = new ArrayList<PuntoEstadistica>();
   }
   public void visualizarTiemposAsignacion( InfoCasoSimulacion infoCaso){
       
   }
    public void visualizarTiemposRescatePorRobot( InfoCasoSimulacion infoCaso){
        VisualizacionJfreechart visualidaroJf2 = new VisualizacionJfreechart("VisualizacionResultadosSimulacion");
       visualidaroJf2.setTitle(infoCaso.getIdentEscenario());
       visualidaroJf2.visualizarSeriesTiemposRescateVictPorRobots(constructorSeries.
                        extraerSerieRescatesPorRobot(ConstructorSeriesDatosParaVisualizar.SerieTiempoRescate, infoCaso));
    }

  public void visualizarLlegadaYasignacionVictimas2( InfoCasoSimulacion infoCaso){
      VisualizacionJfreechart visualidaroJf2 = new VisualizacionJfreechart("VisualizacionResultadosSimulacion");
       visualidaroJf2.setTitle(infoCaso.getIdentEscenario());
//       visualidaroJf2.crearChartAsignRescateVict(constructorSeries.extraerDataSetRescateVictimas(ConstructorSeriesDatosParaVisualizar.SerieDatosTiempoAsignacion, infoCaso));
//       visualidaroJf2.crearChartAsignRescateVict( constructorSeries.extraerSeriesRescateVictimas( infoCaso));
       visualidaroJf2.crearLineChartAsignRescateVict( constructorSeries.extraerSeriesRescateVictimas( infoCaso));
  }
    
    public void visualizarCosteEnergiaRescateVictimas(InfoCasoSimulacion infoCaso) {
         VisualizacionJfreechart visualidaroJf2 = new VisualizacionJfreechart("VisualizacionResultadosSimulacion");
       visualidaroJf2.setTitle(infoCaso.getIdentEscenario());
       visualidaroJf2.crearBarChartCosteEnergiaRescateVictimas(constructorSeries.extraerDataSetRescateVictimas(ConstructorSeriesDatosParaVisualizar.SerieEnergiaConsumida, infoCaso));
   
    }
    public void visualizarTiemposyCosteEnergiaRescateVictimas(InfoCasoSimulacion infoCaso) {
       VisualizacionJfreechart visualidaroJf2 = new VisualizacionJfreechart("VisualizacionResultadosSimulacion");
       visualidaroJf2.setTitle(infoCaso.getIdentEscenario());
       visualidaroJf2.crearChartCombinadoAsignRescateVict(
               constructorSeries.extraerSeriesRescateVictimas( infoCaso),
               constructorSeries.extraerDataSetRescateVictimas(ConstructorSeriesDatosParaVisualizar.SerieEnergiaConsumida, infoCaso));
   
    }
    
    /**
* Starting point for the demonstration application.
*
* @param args ignored.
*/
    public static void main(String[] args) {
        ItfUsoConfiguracion configuracionExterna = null;
//        ItfUsoRecursoTrazas recursoTrazas = null;
        
            try {
            // Se crea el repositorio de interfaces y el recurso de trazas
              String  identInfocaso = "rescate11466753238475.xml";
           ItfUsoRecursoPersistenciaEntornosSimulacion itfPersistencia=   new  ClaseGeneradoraRecursoPersistenciaEntornosSimulacion("persistencia");
                InfoCasoSimulacion infoCaso = itfPersistencia.obtenerInfoCasoSimulacion(identInfocaso);
                ControladorVisualizResultados controlador = new ControladorVisualizResultados();
//                controlador.visualizarLlegadaYasignacionVictimas(infoCaso);
                controlador.visualizarLlegadaYasignacionVictimas2(infoCaso);
//                controlador.visualizarLlegadaYasignacionVictimas2( infoCaso);
                controlador.visualizarCosteEnergiaRescateVictimas(infoCaso);
                controlador.visualizarTiemposyCosteEnergiaRescateVictimas(infoCaso);
                controlador.visualizarTiemposRescatePorRobot(infoCaso);
                
                } catch (Exception e) {
                    System.err.println("Error. No se pudo crear o registrar el recurso de trazas");
                    e.printStackTrace();
                //no es error critico
               }
    }     
}
