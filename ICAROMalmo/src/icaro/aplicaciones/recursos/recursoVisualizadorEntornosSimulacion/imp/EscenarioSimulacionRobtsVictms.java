/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.RobotCapability;
import icaro.aplicaciones.Rosace.informacion.RobotStatus1;
import icaro.aplicaciones.Rosace.informacion.Victim;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

/**
 *
 * @author FGarijo
 */
@Root(name = "escenarioSimulacion",strict=false)
public class EscenarioSimulacionRobtsVictms {
    @Element
    private String identEscenario;
    @Element
    private String modeloOrganizativo;
    @Element
    private int numRobots;
    @Element
    private int numVictimas;
    @ElementMap(entry="robots", key="key", attribute=true, inline=true)
    private Map<String, RobotStatus1> infoRobots;     
    @ElementMap(entry="victimas", key="key", attribute=true, inline=true)
//    private Map<String, Point> victimasLoc;
    private Map<String, Victim> infoVictimas;
    private String robotInicialId = "initRobot";
    private String victimInicialId = "initVictim";
    private String robotCapability = "camera";
//    @Element
//    private  List<String> robotCapabilities ;
    private volatile RobotStatus1 ultimoRobotDefinido ;
    private volatile Victim ultimaVictimaModificada;
    private volatile GestionEscenariosSimulacion migestor;
    private RobotCapability capabilityInicial;
     

public  EscenarioSimulacionRobtsVictms (){
    migestor=null;
    
        infoRobots = new HashMap<String,RobotStatus1 >();
//        infoRobots.put(robotInfo.getIdRobot(),robotInfo);
//        infoRobots.put(robotInicialId, robotInfo);
        
        
//        victimasLoc = new HashMap<String, Point>();
        infoVictimas = new HashMap<String, Victim>();
       
        
        
}
public void  initEscenario(){
    modeloOrganizativo = "SinDefinir";
        numRobots=0;
        numVictimas= 0;
        ultimoRobotDefinido = new RobotStatus1();
        capabilityInicial = new RobotCapability();
        capabilityInicial.setNombre("radar");
        capabilityInicial.setDescripcion("radar del robot"); 
        ultimoRobotDefinido.setRobotCapability(capabilityInicial);
        ultimoRobotDefinido.setIdRobot(robotInicialId);
        ultimaVictimaModificada = new Victim (victimInicialId);
//        ultimaVictimaModificada.setName(victimInicialId);
//        this.addRoboInfo(robotInicialId, ultimoRobotDefinido);
        this.infoRobots.put(robotInicialId, ultimoRobotDefinido);
     
//        victimasLoc.put(victimInicialId, new Point(0,0));
        this.infoVictimas.put(victimInicialId, ultimaVictimaModificada);
        
}
public synchronized void setGestorEscenarios( GestionEscenariosSimulacion gestorEsc){
    migestor=gestorEsc;     
}
public synchronized void setNumVictimas( int numVict){
    numVictimas=numVict;
}

@XmlElement (name = "numVictimas")
public synchronized int  getNumVictimas( ){
    return numVictimas;
}
public synchronized void setNumRobots( int numRobot){
    numRobots=numRobot;
}
@XmlElement (name = "numRobots")
public synchronized int  getNumRobots( ){
    return numRobots;
}
public void setmodeloOrganizativo( String modeloOrg){
    modeloOrganizativo=modeloOrg;
}
@XmlElement (name = "modeloOrganizativo")
public String  getmodeloOrganizativo( ){
    return modeloOrganizativo;
}

public synchronized void addVictimLoc (String idVictima,Point puntoEnEscenario ){
    
    if(ultimaVictimaModificada!=null &&ultimaVictimaModificada.getName().equalsIgnoreCase(idVictima))ultimaVictimaModificada.setLocPoint(puntoEnEscenario);
    else {
        ultimaVictimaModificada = infoVictimas.get(idVictima);
        if(ultimaVictimaModificada == null){
        if(numVictimas==0)infoVictimas.remove(victimInicialId);
        ultimaVictimaModificada = new  Victim(idVictima);
        infoVictimas.put (idVictima,ultimaVictimaModificada );
        numVictimas++;
        }
        ultimaVictimaModificada.setLocPoint(puntoEnEscenario);
    }
    
}
public void eliminaVictim (String idVictima){
    if(infoVictimas.containsKey(idVictima)){
        infoVictimas.remove(idVictima);
        numVictimas--;
    }
}
public synchronized void addRoboInfo (String idRobot,RobotStatus1 infoRobot ){
    if(numRobots==0)
        infoRobots.remove(robotInicialId);
    //remove(robotInicialId);
    this.infoRobots.put(idRobot, infoRobot);
    numRobots++;
}
public void addRoboLoc (String idRobot,Point robotLoc ){

        if (ultimoRobotDefinido!=null &&ultimoRobotDefinido.getIdRobot().equals(idRobot))ultimoRobotDefinido.setLocPoint(robotLoc);
        else{
            ultimoRobotDefinido = infoRobots.get(idRobot);
            if (ultimoRobotDefinido==null){
                if(numRobots==0)infoRobots.remove(robotInicialId);
                ultimoRobotDefinido = new RobotStatus1();
                ultimoRobotDefinido.setIdRobot(idRobot);
                ultimoRobotDefinido.setRobotCapability(capabilityInicial);
                infoRobots.put(idRobot, ultimoRobotDefinido);   
                numRobots++;
            }
            ultimoRobotDefinido.setLocPoint(robotLoc);
            }
               
               
}

public void eliminaRobot (String idRobot){
    if(infoRobots.containsKey(idRobot)){
        infoRobots.remove(idRobot);
        numRobots--;
    }   
}
public void eliminarEntidad (String idEntidad){
    if(idEntidad.contains("Robot")){
           if( infoRobots.containsKey(idEntidad)){
            infoRobots.remove(idEntidad);
            numRobots--;
           }else if(numRobots>0&&idEntidad.startsWith("init"))infoRobots.remove(idEntidad);
            
           }
    else if(idEntidad.contains("Victim")){
           if( infoVictimas.containsKey(idEntidad)){
            infoVictimas.remove(idEntidad);
            numVictimas--;
           }else if(idEntidad.startsWith("init"))infoVictimas.remove(idEntidad);
            if(numVictimas==0)infoVictimas.put(victimInicialId, new Victim(victimInicialId));
        }
}
public Set getSetVictims (){
    if(numVictimas>0)infoVictimas.remove(victimInicialId);
    return this.infoVictimas.entrySet();
}
public synchronized Map<String, Victim> getVictims (){
    if(numVictimas>0)infoVictimas.remove(victimInicialId);
    return this.infoVictimas;
}

public Set getRobots (){
    if(numRobots>0)infoRobots.remove(robotInicialId);
    return this.infoRobots.entrySet();
}

public Point getVictimLoc (String idVictima){
    return this.infoVictimas.get(idVictima).getLocPoint();
}
public RobotStatus1 getRobotInfo (String idRobot){
    return this.infoRobots.get(idRobot);
}
public void setIdentEscenario(String escenarioId) {
    identEscenario = escenarioId;
}
    public synchronized String getIdentEscenario() {
//        throw new UnsupportedOperationException("Not supported yet."); 
    if (identEscenario==null&& migestor!=null)return migestor.getIdentEscenario(modeloOrganizativo, numRobots, numVictimas);
        return this.identEscenario;
    }
   public synchronized void  setIdentificadorNormalizado(){
       // se lo pide al gestor para que verifique posibles conflictos
      this.identEscenario= migestor.getIdentEscenario(modeloOrganizativo, numRobots, numVictimas);
    
} 
   public ArrayList getListIdentsRobots(){
       if (numRobots<=0)return null;
       else{
          infoRobots.remove(robotInicialId);
          ArrayList listaIdents = new ArrayList(); 
          Object[] identRobots= infoRobots.keySet().toArray();
          listaIdents.addAll(Arrays.asList(identRobots));
          return listaIdents;
       }
   }
    public ArrayList getListIdentsVictims(){
       if (numVictimas<=0)return null;
       else{
          infoVictimas.remove(victimInicialId);
          ArrayList listaIdents = new ArrayList(); 
          Object[] identVictimas= infoVictimas.keySet().toArray();
          listaIdents.addAll(Arrays.asList(identVictimas));
          return listaIdents;
       }
   }

    public void renombrarIdentRobts(ArrayList identList) {
        String identNuevo; int i= 0;
        RobotStatus1 robotInfo = null;
        Object[] identRobots= infoRobots.keySet().toArray();
        for( i=0; i<identList.size();i++){
            robotInfo = infoRobots.get ( identRobots[i]);
            infoRobots.remove ( identRobots[i]);  
            identNuevo = (String)identList.get(i);
            robotInfo.setIdRobot(identNuevo);  
            infoRobots.put(identNuevo,robotInfo );           
        }
    }
}