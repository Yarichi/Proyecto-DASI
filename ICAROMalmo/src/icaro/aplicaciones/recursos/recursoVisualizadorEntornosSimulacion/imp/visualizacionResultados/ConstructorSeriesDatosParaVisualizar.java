/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.visualizacionResultados;

import icaro.aplicaciones.Rosace.informacion.InfoCasoSimulacion;
import static icaro.aplicaciones.Rosace.informacion.InfoCasoSimulacion.SerieDatosTiempoAsignacion;
import static icaro.aplicaciones.Rosace.informacion.InfoCasoSimulacion.SerieDatosTiempoPeticion;
import static icaro.aplicaciones.Rosace.informacion.InfoCasoSimulacion.SerieDatosTiempoRescate;
import icaro.aplicaciones.Rosace.informacion.InfoRescateVictima;
import icaro.aplicaciones.Rosace.informacion.PuntoEstadistica;
import icaro.aplicaciones.Rosace.informacion.VictimasSalvadas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author FGarijo
 */
public class ConstructorSeriesDatosParaVisualizar {
    public static int SerieDatosTiempoPeticion= 1;
    public static int SerieDatosTiempoAsignacion=2;
    public static int SerieDatosTiempoRescate=3;
    public static int SerieDatosEnergiaConsumida=4;
    public static String SerieTiempoPeticion= "Tiempo de petición";
    public static String SerieTiempoAsignacion="Tiempo de asignación";
    public static String SerieTiempoRescate="Tiempo de rescate";
    public static String SerieEnergiaConsumida="Energía Consumida";
    public ConstructorSeriesDatosParaVisualizar(){
        
    }
    public  XYSeries  extraerSerieRescateVictimas(int tituloSerie,InfoCasoSimulacion infoCaso){
//        String tituloSerieLlegadaVictimas = "Notification Time";
//        int indexSerieLlegadaVictimas = 1;
//        String tituloSerieasignacionVictimas = "Assignment Time";
//        int indexSerieasignacionVictimas = 2;
//        aniadirSerieAVisorGraficaEstadisticas(tituloSerieLlegadaVictimas, indexSerieLlegadaVictimas, llegada);
//        aniadirSerieAVisorGraficaEstadisticas(tituloSerieasignacionVictimas, indexSerieasignacionVictimas, asignacion);
//        String tituloSerieRescateVictimas = "Rescue Time";
//        int indexSerieRescateVictimas = 3;
//        aniadirSerieAVisorGraficaEstadisticas(tituloSerieRescateVictimas, indexSerieRescateVictimas, rescate);
//        ArrayList<PuntoEstadistica> serieRescateVictimas = new ArrayList<PuntoEstadistica>();
      XYSeries serieAconstruir = new XYSeries(tituloSerie);
      int numVictimas=0;
      long tiempoNotificacion=0, tiempoReportado=0;
      long tiempoInicialDeLaSimulacion = infoCaso.getTiempoInicioEnvioPeticiones();
      Collection<InfoRescateVictima> rescateVictimas;
      rescateVictimas=(Collection<InfoRescateVictima>)infoCaso.getInfoRescateVictimas().values();
      Iterator<InfoRescateVictima> iter = rescateVictimas.iterator();
       int i=1;
       InfoRescateVictima infoRescVict;
          while (iter.hasNext()){
          infoRescVict=iter.next();
          numVictimas++;
          switch (tituloSerie){
          case 1:  
              serieAconstruir.add(i,infoRescVict.getcosteRescate());
              break;
          case 2:
            tiempoReportado = infoRescVict.getTiempoPeticion();
            break;
          case 3 :
              tiempoReportado = infoRescVict.getTiempoAsignacion();
              break;
          case 4:
              tiempoReportado = infoRescVict.getTiempoRescate();
              break;
              }
//          serieRescateVictimas.add(i, new PuntoEstadistica(numVictimas,tiempoReportado-tiempoInicialDeLaSimulacion));
          serieAconstruir.add(i, tiempoReportado-tiempoInicialDeLaSimulacion);
          
          i++;
        }
         return serieAconstruir;
    }
    public  XYSeries  extraerSerieRescatesPorRobot1(int tituloSerie, InfoCasoSimulacion infoCaso){
         XYSeries serieAconstruir = new XYSeries(tituloSerie);
      int numRobots=0;
      long tiempoNotificacion=0, tiempoReportado=0;
      long tiempoInicialDeLaSimulacion = infoCaso.getTiempoInicioEnvioPeticiones();
      Collection<InfoRescateVictima> rescateVictimas;
      InfoRescateVictima infoRescVict;
      Collection<VictimasSalvadas> rescateRobots;
      Map<String,InfoRescateVictima> tablaVictimasRescatadas = infoCaso.getInfoRescateVictimas();
      rescateRobots=(Collection<VictimasSalvadas>)infoCaso.getRobotRescateVictimas().values();
      Iterator<VictimasSalvadas> iter = rescateRobots.iterator();
    
       int i=1;
       VictimasSalvadas infoRescRobot;
          while (iter.hasNext()){
          infoRescRobot=iter.next();
          List<String> victimasSalvadasRobot = infoRescRobot.getVictimas();
          numRobots++;
          for ( int j=0;j<victimasSalvadasRobot.size();j++){
           infoRescVict = tablaVictimasRescatadas.get(victimasSalvadasRobot.get(j));
           if(tituloSerie==SerieDatosTiempoPeticion)tiempoReportado = infoRescVict.getTiempoPeticion();
           else if (tituloSerie==SerieDatosTiempoAsignacion )tiempoReportado = infoRescVict.getTiempoAsignacion();
                else if (tituloSerie==SerieDatosTiempoRescate)tiempoReportado = infoRescVict.getTiempoRescate();
//          serieRescateVictimas.add(i, new PuntoEstadistica(numVictimas,tiempoReportado-tiempoInicialDeLaSimulacion));
           serieAconstruir.add(i, tiempoReportado-tiempoInicialDeLaSimulacion);
          }
          i++;
        }
          return serieAconstruir;
    }   
    public  DefaultCategoryDataset  extraerSerieRescatesPorRobotOrdAsigVict(String tituloSerie, InfoCasoSimulacion infoCaso){
         DefaultCategoryDataset serieAconstruir = new DefaultCategoryDataset();
      int numRobots=0;
      long tiempoSerie=0, tiempoReportado=0,tiempoUltimaVictima=0;
      long tiempoInicialDeLaSimulacion = infoCaso.getTiempoInicioEnvioPeticiones();
      Collection<InfoRescateVictima> rescateVictimas;
      rescateVictimas=(Collection<InfoRescateVictima>)infoCaso.getInfoRescateVictimas().values();
      Map<String,VictimasSalvadas> tablaVictimasSalvadas = infoCaso.getRobotRescateVictimas();
      Object[] identsRobots = (Object[])tablaVictimasSalvadas.keySet().toArray();
      long[] ultimosvaloresRobotAlmcenados = new long[identsRobots.length];
      Iterator<InfoRescateVictima> iter = rescateVictimas.iterator();
       int i=1;
       String idRobotRescatador;
       InfoRescateVictima infoRescVict;
          while (iter.hasNext()){
          infoRescVict=iter.next();
          if(tituloSerie.equals(ConstructorSeriesDatosParaVisualizar.SerieTiempoPeticion))tiempoReportado = infoRescVict.getTiempoPeticion();
          else if (tituloSerie.equals(ConstructorSeriesDatosParaVisualizar.SerieTiempoAsignacion))tiempoReportado = infoRescVict.getTiempoAsignacion();
                else if (tituloSerie.equals(ConstructorSeriesDatosParaVisualizar.SerieTiempoRescate))tiempoReportado = infoRescVict.getTiempoRescate();
          idRobotRescatador=infoRescVict.getRobotRescatadorId();
          int j=0; boolean encontrado=false;
            while (j<identsRobots.length&&!encontrado){
                if(identsRobots[j].equals(idRobotRescatador)) encontrado=true;
                else j++;
            }
           tiempoReportado= tiempoReportado-tiempoInicialDeLaSimulacion;
           tiempoSerie=tiempoReportado-ultimosvaloresRobotAlmcenados[j];
          serieAconstruir.addValue((Number)tiempoSerie,idRobotRescatador,infoRescVict.getvictimaId());
          ultimosvaloresRobotAlmcenados[j]=tiempoReportado;
        }
          return serieAconstruir;
    } 
     public  DefaultCategoryDataset  extraerSerieRescatesPorRobot(String tituloSerie, InfoCasoSimulacion infoCaso){
         DefaultCategoryDataset serieAconstruir = new DefaultCategoryDataset();
      int numRobots=0;
      long tiempoSerie=0, tiempoReportado=0,tiempoUltimaVictima=0;
      long tiempoInicialDeLaSimulacion = infoCaso.getTiempoInicioEnvioPeticiones();
      Collection<InfoRescateVictima> rescateVictimas;
      rescateVictimas=(Collection<InfoRescateVictima>)infoCaso.getInfoRescateVictimas().values();
      Map<String,VictimasSalvadas> tablaVictimasSalvadas = infoCaso.getRobotRescateVictimas();
      Object[] identsRobots = (Object[])tablaVictimasSalvadas.keySet().toArray();
      long[] ultimosvaloresRobotAlmcenados = new long[identsRobots.length];
      Iterator<InfoRescateVictima> iter = rescateVictimas.iterator();
       String idRobotRescatador;
       InfoRescateVictima infoRescVict;
       ArrayList<String> identsVictimasRescatadas =(ArrayList<String>) infoCaso.getIdentsVictimasRescatadas().getVictimas();
      for (int i=0; i< identsVictimasRescatadas.size();i++){
          infoRescVict = infoCaso.getInfoRescateVictima(identsVictimasRescatadas.get(i));
          if(tituloSerie.equals(ConstructorSeriesDatosParaVisualizar.SerieTiempoPeticion))tiempoReportado = infoRescVict.getTiempoPeticion();
          else if (tituloSerie.equals(ConstructorSeriesDatosParaVisualizar.SerieTiempoAsignacion))tiempoReportado = infoRescVict.getTiempoAsignacion();
                else if (tituloSerie.equals(ConstructorSeriesDatosParaVisualizar.SerieTiempoRescate))tiempoReportado = infoRescVict.getTiempoRescate();
          idRobotRescatador=infoRescVict.getRobotRescatadorId();
          int j=0; boolean encontrado=false;
            while (j<identsRobots.length&&!encontrado){
                if(identsRobots[j].equals(idRobotRescatador)) encontrado=true;
                else j++;
            }
           tiempoReportado= tiempoReportado-tiempoInicialDeLaSimulacion;
           tiempoSerie=tiempoReportado-ultimosvaloresRobotAlmcenados[j];
          serieAconstruir.addValue((Number)tiempoSerie,idRobotRescatador,infoRescVict.getvictimaId());
          ultimosvaloresRobotAlmcenados[j]=tiempoReportado;
        }
          return serieAconstruir;
    }  
    public  DefaultCategoryDataset  extraerSerieTiemposRescatePorRobot(String tituloSerie, InfoCasoSimulacion infoCaso){
      DefaultCategoryDataset serieAconstruir = new DefaultCategoryDataset();
      long tiempoSerie=0, tiempoReportado=0,tiempoUltimaVictima=0;
      long tiempoInicialDeLaSimulacion = infoCaso.getTiempoInicioEnvioPeticiones();
      Collection<InfoRescateVictima> rescateVictimas;
      rescateVictimas=(Collection<InfoRescateVictima>)infoCaso.getInfoRescateVictimas().values();
      Map<String,InfoRescateVictima> tablaInfoRescateVictimas = infoCaso.getInfoRescateVictimas();
      Map<String,VictimasSalvadas> tablaVictimasSalvadas = infoCaso.getRobotRescateVictimas();
      Object[] identsRobots = (Object[])tablaVictimasSalvadas.keySet().toArray();
      Iterator<InfoRescateVictima> iter = rescateVictimas.iterator();
       String idRobotRescatador;
       InfoRescateVictima infoRescVict;
       for(int i=0;i<identsRobots.length;i++){
           idRobotRescatador=(String)identsRobots[i];
           VictimasSalvadas victimasSalvadasRobot =(VictimasSalvadas ) tablaVictimasSalvadas.get(idRobotRescatador);
           tiempoReportado=0;tiempoUltimaVictima=0;
           for(String idVictim:victimasSalvadasRobot.getVictimas()){
              infoRescVict =(InfoRescateVictima) tablaInfoRescateVictimas.get(idVictim);
               if(tituloSerie.equals(ConstructorSeriesDatosParaVisualizar.SerieTiempoPeticion))tiempoReportado = infoRescVict.getTiempoPeticion();
               else if (tituloSerie.equals(ConstructorSeriesDatosParaVisualizar.SerieTiempoAsignacion))tiempoReportado = infoRescVict.getTiempoAsignacion();
                else if (tituloSerie.equals(ConstructorSeriesDatosParaVisualizar.SerieTiempoRescate))tiempoReportado = infoRescVict.getTiempoRescate();
                   tiempoReportado=tiempoReportado-tiempoInicialDeLaSimulacion;
                   tiempoSerie=tiempoReportado-tiempoUltimaVictima;
                   tiempoUltimaVictima=tiempoReportado;
                   serieAconstruir.addValue((Number)(tiempoSerie),idRobotRescatador,infoRescVict.getvictimaId());
           }
        }
        
      return serieAconstruir;
    } 
//    XYSeries extraerSerieCosteEnergiaRescateVictimas(String SerieDatosTiempoPeticion, InfoCasoSimulacion infoCaso) {
//        
//    }
    public  DefaultCategoryDataset  extraerDataSetCosteRescateVictimas(String tituloSerie,InfoCasoSimulacion infoCaso){
//        String tituloSerieLlegadaVictimas = "Notification Time";
//        int indexSerieLlegadaVictimas = 1;
//        String tituloSerieasignacionVictimas = "Assignment Time";
//        int indexSerieasignacionVictimas = 2;
//        aniadirSerieAVisorGraficaEstadisticas(tituloSerieLlegadaVictimas, indexSerieLlegadaVictimas, llegada);
//        aniadirSerieAVisorGraficaEstadisticas(tituloSerieasignacionVictimas, indexSerieasignacionVictimas, asignacion);
//        String tituloSerieRescateVictimas = "Rescue Time";
//        int indexSerieRescateVictimas = 3;
//        aniadirSerieAVisorGraficaEstadisticas(tituloSerieRescateVictimas, indexSerieRescateVictimas, rescate);
//        ArrayList<PuntoEstadistica> serieRescateVictimas = new ArrayList<PuntoEstadistica>();
      DefaultCategoryDataset serieAconstruir = new DefaultCategoryDataset();
      int numVictimas=0;
      long tiempoNotificacion=0, tiempoReportado=0;
      long tiempoInicialDeLaSimulacion = infoCaso.getTiempoInicioEnvioPeticiones();
      Collection<InfoRescateVictima> rescateVictimas;
      rescateVictimas=(Collection<InfoRescateVictima>)infoCaso.getInfoRescateVictimas().values();
      Iterator<InfoRescateVictima> iter = rescateVictimas.iterator();
       int i=1;
       InfoRescateVictima infoRescVict;
          while (iter.hasNext()){
          infoRescVict=iter.next();
          numVictimas++;
          serieAconstruir.addValue(infoRescVict.getcosteRescate(), infoRescVict.getRobotRescatadorId(),infoRescVict.getvictimaId());
          
        }
         return serieAconstruir;
    }
     public  DefaultCategoryDataset  extraerDataSetRescateVictimas(String tituloSerie, InfoCasoSimulacion infoCaso){
         DefaultCategoryDataset serieAconstruir = new DefaultCategoryDataset();
      int numRobots=0;
      long tiempoNotificacion=0, valorReportado=0;
      long tiempoInicialDeLaSimulacion = infoCaso.getTiempoInicioEnvioPeticiones();
      int numVictimas=0;InfoRescateVictima infoRescVict;
       ArrayList<String> identsVictimasRescatadas =(ArrayList<String>) infoCaso.getIdentsVictimasRescatadas().getVictimas();
      for (int i=0; i< identsVictimasRescatadas.size();i++){
          infoRescVict = infoCaso.getInfoRescateVictima(identsVictimasRescatadas.get(i));
          switch (tituloSerie){
             case "Tiempo de petición":  
              serieAconstruir.addValue(infoRescVict.getTiempoPeticion()-tiempoInicialDeLaSimulacion, tituloSerie,infoRescVict.getvictimaId());
              break;
          case "Tiempo de asignación":
            serieAconstruir.addValue(infoRescVict.getTiempoAsignacion()-tiempoInicialDeLaSimulacion, tituloSerie,infoRescVict.getvictimaId());
            break;
          case "Tiempo de rescate":
              serieAconstruir.addValue(infoRescVict.getTiempoRescate()-tiempoInicialDeLaSimulacion, tituloSerie,infoRescVict.getvictimaId());
              break;
          case "Energía Consumida":
              valorReportado = (long)infoRescVict.getcosteRescate();
              serieAconstruir.addValue((long)infoRescVict.getcosteRescate(), tituloSerie,infoRescVict.getvictimaId());
              break;
              }
        }
         return serieAconstruir;
    }
     public  DefaultCategoryDataset  extraerSeriesRescateVictimas( InfoCasoSimulacion infoCaso){
         // Extreaemos una matriz con  3 series 
         DefaultCategoryDataset serieAconstruir = new DefaultCategoryDataset();
      int numRobots=0;
      long tiempoNotificacion=0;
      Number valorReportado=0;
      long tiempoInicialDeLaSimulacion = infoCaso.getTiempoInicioEnvioPeticiones();
      int numVictimas=0;
      InfoRescateVictima infoRescVict;
       ArrayList<String> identsVictimasRescatadas =(ArrayList<String>) infoCaso.getIdentsVictimasRescatadas().getVictimas();
      for (int i=0; i< identsVictimasRescatadas.size();i++){
          infoRescVict = infoCaso.getInfoRescateVictima(identsVictimasRescatadas.get(i));
          numVictimas++;
           serieAconstruir.addValue((Number)(infoRescVict.getTiempoPeticion()-tiempoInicialDeLaSimulacion),
                   ConstructorSeriesDatosParaVisualizar.SerieTiempoPeticion,infoRescVict.getvictimaId());
           serieAconstruir.addValue((Number)(infoRescVict.getTiempoAsignacion()-tiempoInicialDeLaSimulacion),
                   ConstructorSeriesDatosParaVisualizar.SerieTiempoAsignacion,infoRescVict.getvictimaId());
           serieAconstruir.addValue((Number)(infoRescVict.getTiempoRescate()-tiempoInicialDeLaSimulacion),
                   ConstructorSeriesDatosParaVisualizar.SerieTiempoRescate,infoRescVict.getvictimaId());
//           serieAconstruir.addValue((Number)infoRescVict.getcosteRescate(),
//                   ConstructorSeriesDatosParaVisualizar.SerieEnergiaConsumida,infoRescVict.getvictimaId());
              }
         return serieAconstruir;
    }
    
     public DefaultCategoryDataset addOtroDataset(DefaultCategoryDataset dataSetOrigen,DefaultCategoryDataset dataSetToAdd ){
         List clavesColumnas = dataSetToAdd.getColumnKeys();
         List clavesFilas = dataSetToAdd.getRowKeys();
         Comparable claveFila, claveColumna;
         for (int i=0;i<clavesColumnas.size();i++){
             for (int j=0;j<clavesFilas.size();j++){
                 claveColumna = dataSetToAdd.getColumnKey(j);
                 claveFila = dataSetToAdd.getRowKey(j);
                 dataSetOrigen.addValue(dataSetToAdd.getValue(j, i),claveFila,claveColumna);
             }
          }
         return dataSetOrigen;
     }
     
}
