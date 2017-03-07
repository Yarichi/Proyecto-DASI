/*
 *
 * Clase para gestionar las caracteristicas del robot en un escenario de simulación :
 * 
 *    -  idRobot -> Identificador del robot
 *    -  availableEnergy -> Energia disponible
 *    -  robotCoordinate -> Coordenada inicial. Tambiï¿½n se podrï¿½a utilizar para poner la coordenada actual del robot en un instante dado.
 *    -  healRange -> Rango para poder aplicar con ï¿½xito la curaciï¿½n de una victima
 *    -  rangeProximity -> Rango de proximidad para poder ver a otros robots que estan a una distancia dentro del rango especificado.
 *    -  robotCapabilities -> Capacidades. Identifica las habilidades que tiene el robot para curar a una victima. Las capacidades estan relacionadas con los requisitos de la victima. 
 *                            Deben emparejar totalmente con los requisitos de una victima para que el robot pueda curarla totalmente. En caso de no emparejar totalmente
 *                            entonces solo permitirï¿½ eliminar los requisitos que emparejan, y la victima todavï¿½a requiere de otros robots para poder compensar los requisitos
 *                            que no emparejaron con las capacidades del robot. 
 *
 */

package icaro.aplicaciones.Rosace.informacion;

import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.ItfUsoMovimientoCtrl;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;
@Root
public class RobotStatus1 implements Cloneable{
    @Element
    private String idRobot;
    @Element
    private String idRobotRol;
    @Element
    private int availableEnergy;
    @Element
    private Point robotCoordinateActualP;
    private Point robotCoordinateAnteriorP ;
    private Coordinate robotCoordinate;
    private float healRange;  
    //Actualmente en nuestra implementacion no se utilizan los atributos rangeProximity y robotCapabilities.
    //No obstante esta clase ya ofrece metodos para poder considerarlos en el futuro 
    private float rangeProximity;
    private final double limiteDespalzamiento = 0.5;
    @ElementList(entry="robotCapability")
    private  List<RobotCapability> robotCapabilities ;
//    @Element(name="robotCapability")
    private RobotCapability capablity;
//    private InfoCompMovimiento infoCompMovt;
    private ItfUsoMovimientoCtrl itfUsoCompMovimiento;
    private boolean bloqueado;
    private String identDestino;
    private String estadoMovimiento;
    private String causaCambioEstado;

    public RobotStatus1() {
        robotCoordinateAnteriorP = new Point(0,0);
                robotCoordinateActualP = new Point(1,1);
//                idRobot = robotId;
                idRobotRol = "indefinido";
                availableEnergy = 100000;
                robotCapabilities = new ArrayList<RobotCapability>();
//                robotCapabilities.add(robotCapability);
//                capablity = new RobotCapability();
//                if (robotCapabilities.size()==0)robotCapabilities.add(capablity );
//                robotCapabilities.add(20);
    }
   public  RobotStatus1(ArrayList<RobotCapability> initialCapb) {
        robotCoordinateAnteriorP = new Point(0,0);
                robotCoordinateActualP = new Point(1,1);
//                idRobot = robotId;
                idRobotRol = "indefinido";
                availableEnergy = 100000;
                robotCapabilities = new ArrayList<RobotCapability>();
//                robotCapabilities.add("camera");
                robotCapabilities = initialCapb;
//                robotCapabilities.add("camara");
//                robotCapabilities.add(20);
    }  
       	
	public void setIdRobot(String id){
		this.idRobot = id;
	}
        @XmlElement (name = "idRobot")
	public String getIdRobot(){
		return this.idRobot;
	}
        public void setIdRobotRol(String id){
		this.idRobotRol = id;
	}
        @XmlElement (name = "idRobotRol")
	public String getIdRobotRol(){
		return this.idRobotRol;
	}		
	public void setAvailableEnergy(int energy){
		this.availableEnergy = energy;
	}
        @XmlElement (name = "availableEnergy")
	public int getAvailableEnergy(){
		return this.availableEnergy;
	}	
    public  void setRobotCoordinate(Coordinate coord){
       
        this.robotCoordinate = coord; 
                
    }
    
    public  Coordinate getRobotCoordinate(){
        if(robotCoordinate==null)robotCoordinate = new Coordinate(robotCoordinateActualP.x, robotCoordinateActualP.y, 0);  
        return robotCoordinate;
    }
    @XmlElement (name = "robotCoordinateP")
    public Point getLocPoint() {
        return this.robotCoordinateActualP;
    }
    public void setLocPoint(Point punto) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         this.robotCoordinateActualP=punto;
         if ( this.robotCoordinate == null)robotCoordinate = new Coordinate(0,0,0);
         this.robotCoordinateActualP = punto;
         this.robotCoordinate.setX(punto.x);
         this.robotCoordinate.setY(punto.y);
         
    }
    public void setItfCompMovimiento(ItfUsoMovimientoCtrl itfCompMov){
        this.itfUsoCompMovimiento = itfCompMov;    	
    }
    
    public ItfUsoMovimientoCtrl getInfoCompMovt(){
    	return this.itfUsoCompMovimiento;
    }
  
    public void setHealRange(float hr){
    	this.healRange = hr;
    }
//    public void addRobotCapability(String capabilityId){
//    	boolean encontrado = false;
//        int numCapabilities = robotCapabilities.size();
//        if(numCapabilities >0 ){
//            int i=0; 
//            while(i<robotCapabilities.size()& !encontrado){
//                if(capabilityId.equals(robotCapabilities.get(i)) )encontrado =true ;
//                i++;    
//            }
//        }
//         if (!encontrado)this.robotCapabilities.add(capabilityId);
//       
//    }
    public void deleteRobotCapability(String capabilityId){
//    	int i=0; boolean encontrado = false;
//        while(i<robotCapabilities.size()& !encontrado){
//        if(capabilityId ==robotCapabilities.get(i) ){
//            encontrado =true ;
//            robotCapabilities.remove(i);
//        }
//        else i++;    
//        }
        if (robotCapabilities.contains(capabilityId))robotCapabilities.remove(capabilityId);
    }

    public double getHealRange(){
    	return this.healRange;
    }
 
    public void setRangeProximity(float rp){
    	this.rangeProximity = rp;
    }

    public double getRangeProximity(){
    	return this.rangeProximity;
    }
    public boolean getBloqueado(){
		return this.bloqueado;
	}

	public void setBloqueado(boolean b){
		this.bloqueado = b;
	}
    
    public void setRobotCapability(RobotCapability capabilityR){
           if(!robotCapabilities.contains(capabilityR)) robotCapabilities.add(capabilityR);
           
    }
    @XmlElement (name = "robotCapability")
    public RobotCapability getRobotCapability(String identCapab){
         int i=0; boolean encontrado = false;
        while(i<robotCapabilities.size()& !encontrado){
        if(robotCapabilities.get(i).getNombre().equalsIgnoreCase(identCapab) )
            encontrado =true ; 
        else i++;    
        }
           if (encontrado) return robotCapabilities.get(i);
           else return null;
    }
     @XmlElement (name = "robotCapabilities")   
    public List<RobotCapability> getRobotCapabilities(){
    	return this.robotCapabilities;
    }
    public boolean sinMovimientoSignificativo (){
        if (robotCoordinateAnteriorP == null) return false;
        return (limiteDespalzamiento>=Math.abs(robotCoordinateActualP.getY()-robotCoordinateAnteriorP.getY()) && 
                limiteDespalzamiento>=Math.abs(robotCoordinateActualP.getX()-robotCoordinateAnteriorP.getX()) );
    }
    public void setidentDestino(String destinoIdent){
         identDestino=destinoIdent ;
    }
    public String getidentDestino(){
        return identDestino;
    }
    public void setestadoMovimiento(String estadoMvto){
         estadoMovimiento=estadoMvto ;
    }
    public String getestadoMovimiento(){
        return estadoMovimiento;
    }
    public void setcausaCambioEstado(String causaCambio){
         causaCambioEstado=causaCambio ;
    }
    public String getcausaCambioEstado(){
        return causaCambioEstado;
    }
    @Override
    public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
    
    @Override
    public String toString(){    	
    	return "Robot: id->" + this.getIdRobot() + 
                " ; Robot: Rol->" + this.getIdRobotRol() +
    			" ; engergylevel->" + this.getAvailableEnergy() + 
    			" ; coordinate->" + this.getRobotCoordinate() + 
    			" ; healrange->" + this.getHealRange() +
                        " ; identDestino->" + this.getidentDestino() +
                        " ; estadoMovimiento->" + this.getestadoMovimiento ()+
                        " ; esta boqueado ? ->" +  this.getBloqueado() ;
    }
}
