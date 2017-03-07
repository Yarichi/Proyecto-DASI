/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import java.util.*;
import java.util.Map.Entry;
import javax.xml.bind.annotation.XmlElement;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

/**
 *
 * @author FGarijo
 */
@Root(name = "CasoSimulacion",strict=false)
public class InfoCasoSimulacion {
    public static  String SerieDatosTiempoPeticion= "puntosTiemposPeticion";
    public static String SerieDatosTiempoAsignacion="puntosTiemposAsignacion";
    public static String SerieDatosTiempoRescate="puntosTiemposRescate";
    @Element
    private String identCaso; // Se podria poner una variable alfanumerica seguida del tiempo inicial del caso
    @Element
    private String identEscenario;
    @Element
    private String modeloOrganizativo;
    @Element
    private String identEquipo;
    @Element
    private long tiempoInicialDeLaSimulacion=0;      //Variable para obtener el tiempo al iniciar la simulacion
    @Element
    private int numeroVictimasEntorno=0;            //Numero de victimas actuales que se han introducido en el entorno    
    @Element
    private int numeroVictimasAsignadas=0;          //Numero de victimas asignadas a robots
    @Element
    private int numeroRobotsSimulacion = 0;         //Numero de robots diferentes que van a intervenir en el proceso de simulacion	
    @Element
    private int numeroRobotsActivos = 0;    //Numero de robots diferentes que han intervenido en la simulacion
    @Element
    private int tiempoMedioEnvioPeticiones = 1; //Intervalo uniforme de envio de la secuencia de victimas
    @ElementMap(entry="InfoVictimasRescatadas", key="key", attribute=true, inline=true)
    private Map<String, InfoRescateVictima> infoRescateVictimas;
    @ElementMap(entry="rescatesRobot", key="key", attribute=true, inline=true)
    private Map<String, VictimasSalvadas> infoRobotVictimasSalvadas;
    @Element
    VictimasSalvadas identsVictimasRescatadas;
    private SortedSet<InfoRescateVictima> conjVictimasRescatadas ;
    private ArrayList<PuntoEstadistica> valoresSeriePuntosElapsed ;
    private InfoEntornoCasoSimulacion infoEntorno;
    private VictimasSalvadas victimasRescatadasPorRobot ;
    private Map<String, Set<String>> infoRobotVictimasAsignadas;
    private boolean victimaNueva = false;
    private ArrayList valoresSeriePuntosAsignacion;
    private int numeroVictimasRescatadas;
    private String idUltimavictimaRescatada;
            //El tiempo del elemento i-esimo de elapsed es igual el tiempo del elemento asignacion(i) - tiempo del elemento llegada(i) 
   public  InfoCasoSimulacion (@Element(name="identCaso")String casoId,@Element(name="identEscenario") String escenarioId){ 
       identCaso= casoId;
       identEscenario= escenarioId;
       infoRescateVictimas = new HashMap<String, InfoRescateVictima>();
       conjVictimasRescatadas= new TreeSet <InfoRescateVictima>() ;
       infoRobotVictimasSalvadas=new HashMap<String, VictimasSalvadas>();
       infoRobotVictimasAsignadas=new HashMap<String, Set<String>>();
       victimasRescatadasPorRobot = new VictimasSalvadas();
       identsVictimasRescatadas= new VictimasSalvadas();
      }
   public void setInfoCasoSimulacion(String equipoId,String modeloOrg, int numeroRobots, int numeroVictimas){
       identEquipo=equipoId;
       modeloOrganizativo= modeloOrg;
       numeroRobotsSimulacion = numeroRobots;
       numeroVictimasEntorno = numeroVictimas;
//       intervaloSecuencia = intervSecuencia;
   }
   
    @XmlElement (name = "identCaso")
    public String  getidentCaso( ){
        return identCaso;
    }
    @XmlElement (name = "numeroVictimasEntorno")
    public synchronized int  getNumeroVictimasEntorno( ){
        return numeroVictimasEntorno;
    }
    
    @XmlElement (name = "numeroRobotsSimulacion")
    public synchronized int  getNumeroRobotsSimulacion( ){
        return numeroRobotsSimulacion;
    }
    public void setmodeloOrganizativo( String modeloOrg){
        modeloOrganizativo=modeloOrg;
    }
    @XmlElement (name = "modeloOrganizativo")
    public String  getmodeloOrganizativo( ){
        return modeloOrganizativo;
    }
    @XmlElement (name = "numeroRobotsActivos")
    public synchronized int  getNumeroRobotsActivos( ){
        return numeroRobotsActivos;
    }
    @XmlElement (name = "numeroVictimasAsignadas")
    public synchronized int  getNumeroVictimasAsignadas( ){
        return numeroVictimasAsignadas;
    }
    @XmlElement (name = "numeroVictimasRescatadas")
    public synchronized int  getNumeroVictimasRescatadas( ){
        return numeroVictimasRescatadas;
    }
    @XmlElement (name = "identEscenario")
    public synchronized String  getIdentEscenario( ){
        return identEscenario;
    }
    @XmlElement (name = "identEquipo")
    public synchronized String  getIdentEquipo( ){
        return identEquipo;
    }
   public void setTiempoInicioEnvioPeticiones(long tiempoInicio){
       tiempoInicialDeLaSimulacion=tiempoInicio;
   }
   @XmlElement (name = "tiempoInicialDeLaSimulacion")
   public long getTiempoInicioEnvioPeticiones(){
       return tiempoInicialDeLaSimulacion;
   }
    public void setTiempoMedioEnvioPeticiones(int tiempoMedio){
       tiempoMedioEnvioPeticiones=tiempoMedio;
   }
   @XmlElement (name = "tiempoMedioEnvioPeticiones")
   public long getTiempoMedioEnvioPeticiones(){
       return tiempoMedioEnvioPeticiones;
   }
   public VictimasSalvadas getIdentsVictimasRescatadas(){
       return identsVictimasRescatadas;
   }
   public synchronized void addInfoAsignacionVictima(InfoRescateVictima infoAsign){
//      if( infoVictims2Rescue.put(infoAsign.getVictima().getName(), infoAsign)!= null) numeroVictimasEntorno ++ ;
   String idVictima= infoAsign.getvictimaId();
   if (idVictima!=null){
    String idRobot = infoAsign.getRobotRescatadorId();
//       InfoRescateVictima infoVictima = infoRescateVictimas.get(idVictima);
        if(!infoRobotVictimasAsignadas.containsKey(idRobot)){
            Set victimasAsignadasAlRobot =new  TreeSet <String>();
            victimasAsignadasAlRobot.add(idVictima);
            infoRobotVictimasAsignadas.put(idRobot,victimasAsignadasAlRobot );
        }else infoRobotVictimasAsignadas.get(idRobot).add(idVictima);
           if(!infoRescateVictimas.containsKey(idVictima))numeroVictimasAsignadas ++;
           conjVictimasRescatadas.add(infoAsign);
           infoRescateVictimas.put(idVictima, infoAsign); 
   }
   }
   public synchronized void addInfoRescateVictima(InfoRescateVictima infoRescate){
       String idVictima= infoRescate.getvictimaId();
       String idRobot= infoRescate.getRobotRescatadorId();
       VictimasSalvadas   victimasRescatadasRobot;
    if (idVictima!=null)infoRescateVictimas.put(idVictima, infoRescate);
    victimasRescatadasPorRobot.addVictima(idVictima);
   numeroVictimasRescatadas ++;
   if(!infoRobotVictimasSalvadas.containsKey(idRobot)){
       numeroRobotsActivos++;
       victimasRescatadasRobot= new VictimasSalvadas();
   }else victimasRescatadasRobot=infoRobotVictimasSalvadas.get(idRobot);
   victimasRescatadasRobot.addVictima(idVictima);
   conjVictimasRescatadas.add(infoRescate);
   if (idVictima.equals(conjVictimasRescatadas.last().getvictimaId()))identsVictimasRescatadas.addVictima(conjVictimasRescatadas.last().getvictimaId());
   else reordenarIdsVictimasRescatadas();
   infoRobotVictimasSalvadas.put(idRobot,victimasRescatadasRobot );
   }
   private void reordenarIdsVictimasRescatadas (){
       Iterator iter = conjVictimasRescatadas.iterator();
       int i=0; InfoRescateVictima infoRescV;
       identsVictimasRescatadas.reiniciarVictimas();
       while ( iter.hasNext()){
           infoRescV= (InfoRescateVictima)iter.next();
           identsVictimasRescatadas.addVictima(infoRescV.getvictimaId());
       }
   }
   public synchronized InfoRescateVictima getInfoRescateVictima(String victimId){
      return infoRescateVictimas.get(victimId);
   }
   public Map<String,VictimasSalvadas> getRobotRescateVictimas(){
       return this.infoRobotVictimasSalvadas;
   }
   public Map<String,InfoRescateVictima> getInfoRescateVictimas(){
       return this.infoRescateVictimas;
   }
//   @XmlElement (name = "victimasRescatadas")
      public List<String> getvictimasRescatadas(String idRobot){
          return infoRobotVictimasSalvadas.get(idRobot).getVictimas();
       }
      public Set<String> getvictimasAsignadas(String idRobot){
          
          return this.infoRobotVictimasAsignadas.get(idRobot);
       }

        public ArrayList<PuntoEstadistica> getSerieAsignacionVictimas(){
          ArrayList<PuntoEstadistica> serieAsignacionVictimas = new ArrayList<PuntoEstadistica>();
          int numVictimasAsignadas=0;
          long tiempoEnviopeticion=0, tiempoAsignacion;
         Collection<InfoRescateVictima> rescateVictimas;
          rescateVictimas=(Collection<InfoRescateVictima>)infoRescateVictimas.values();
          Iterator<InfoRescateVictima> iter = rescateVictimas.iterator();
              int i=0;
              InfoRescateVictima infoRescVict;
    //      for (int i = 0; i < rescateVictimas.size(); i++) {
          while (iter.hasNext()){
              infoRescVict=iter.next();
              tiempoEnviopeticion = infoRescVict.getTiempoPeticion();
              tiempoAsignacion = infoRescVict.getTiempoAsignacion();
              serieAsignacionVictimas.add(i, new PuntoEstadistica(numVictimasAsignadas++,tiempoAsignacion-tiempoEnviopeticion));
              i++;
          }
          return serieAsignacionVictimas;
       }
         public void ordenarInfoRescateVictimas(){    
          int numVictimasRescatadas=0;
          long tiempoEnviopeticion=0, tiempoRescate;
          Iterator<InfoRescateVictima> iter = conjVictimasRescatadas.iterator();
           int i=0;
           InfoRescateVictima infoRescVict,infoVictClasif;
    //      for (int i = 0; i < rescateVictimas.size(); i++) {
          while (iter.hasNext()){
              infoRescVict=iter.next();
    //          rescateSet.add(infoRescVict);
    //          infoVictClasif = rescateSet.last();
    //          if((infoRescVict.compareTo(rescateSet.last()))<0)
              infoRescateVictimas.put(infoRescVict.getvictimaId(),infoRescVict);
            }
       }
     public ArrayList<PuntoEstadistica> getSerieDatosParaVisualizar(String serieDatos){
       ArrayList<PuntoEstadistica> serieRescateVictimas = new ArrayList<PuntoEstadistica>();
      int numVictimas=0;
      long tiempoNotificacion=0, tiempoReportado=0;
      Collection<InfoRescateVictima> rescateVictimas;
      rescateVictimas=(Collection<InfoRescateVictima>)infoRescateVictimas.values();
      Iterator<InfoRescateVictima> iter = rescateVictimas.iterator();
       int i=0;
       InfoRescateVictima infoRescVict;
          while (iter.hasNext()){
          infoRescVict=iter.next();
          numVictimas++;
          if(serieDatos.equalsIgnoreCase(SerieDatosTiempoPeticion))tiempoReportado = infoRescVict.getTiempoPeticion();
          else if (serieDatos.equalsIgnoreCase(SerieDatosTiempoAsignacion) )tiempoReportado = infoRescVict.getTiempoAsignacion();
                else if (serieDatos.equalsIgnoreCase(SerieDatosTiempoRescate))tiempoReportado = infoRescVict.getTiempoRescate();
          serieRescateVictimas.add(i, new PuntoEstadistica(numVictimas,tiempoReportado-tiempoInicialDeLaSimulacion));
          i++;
        }
         return serieRescateVictimas;
     }
       public int getnumeroVictimasEntorno(){
           return numeroVictimasEntorno;
       }
       public InfoEntornoCasoSimulacion getInfoEntornoCasoSimulacion(){
           return infoEntorno;
       }
       public int getnumeroVictimasAsignadas(){
           return numeroVictimasAsignadas;
       }
       public VictimasSalvadas getIdentsVictimasSalvadasRobot(String robotId){
           return infoRobotVictimasSalvadas.get(robotId);
       } 
       public boolean todasLasVictimasAsignadas(){ 
        return numeroVictimasAsignadas == numeroVictimasEntorno ;
       }
       public boolean todasLasVictimasRescatadas(){ 
        return numeroVictimasRescatadas == numeroVictimasEntorno ;
       }
        public boolean victimaNotificada(String idVictima){ 
        return this.infoRescateVictimas.containsKey(idVictima) ;
       }
}