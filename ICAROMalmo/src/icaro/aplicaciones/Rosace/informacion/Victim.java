package icaro.aplicaciones.Rosace.informacion;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class Victim implements Serializable, Cloneable {
    @Element
       private String name;
     @Element
       private int priority =100; //victim severity
     
       private volatile Coordinate coordinateVictim;
	   //Requirements. Robot abilities should cover these requirements to heal the victim.
        @ElementList(entry="competency")
       public List<Integer> requiredCompetencies ;  
        @Element 
       private Point victimCoordinateP;
    
       private int estimatedCost;
       private boolean isRescued = false;
       private boolean isCostEstimated = false;
       private Point victimCoordinateAnteriorP;
   
       public Victim(){
           victimCoordinateAnteriorP = new Point(0,0);
           victimCoordinateP = new Point(1,1);
           coordinateVictim = new Coordinate(1,1,0);
//                idRobot = robotId;
                name = "indefinido";
                priority = 100;
                requiredCompetencies = new ArrayList<Integer>();
                requiredCompetencies.add( 25);
       } 
	
       //In our first scenario (1 injured group, 1 new injured) this constructor was used.
       public Victim(Coordinate coorVictim){
    	   this.coordinateVictim = coorVictim;
       }
       public Victim(String nombre){
    	   this.name = nombre;
           victimCoordinateAnteriorP = new Point(0,0);
                victimCoordinateP = new Point(1,1);
                coordinateVictim = new Coordinate(1,1,0);
                priority = 100;
                requiredCompetencies = new ArrayList<Integer>();
                requiredCompetencies.add(25);
       } 

       public Victim(String name, Coordinate coorVictim, int priority, List<Integer> requirements){
           this.name = name;
    	   this.coordinateVictim = coorVictim;
    	   this.priority = priority;
   		   for(int i=0;i<requirements.size();i++){
   		      this.requiredCompetencies.add(requirements.get(i));
   		   }
       }
       @XmlElement (name = "name")
       public synchronized String getName(){
    	   return this.name;
       }
       
       public synchronized void setName(String victimName){
    	   this.name = victimName;
       }
      
       public Coordinate getCoordinateVictim(){
           if(coordinateVictim ==null) coordinateVictim = new Coordinate(0, 0, 0);
    	   if (victimCoordinateP!=null){
               coordinateVictim.setX(victimCoordinateP.x);
               coordinateVictim.setY(victimCoordinateP.y);
           }
           return coordinateVictim;
       }
       
       public void setCoordinateVictim(Coordinate coorVictim){
    	   this.coordinateVictim = coorVictim;
       }
       @XmlElement (name = "priority")
       public synchronized int getPriority(){
            return this.priority;
	   }

   	   public synchronized void setPriority(int priority){
		   this.priority = priority;
	   }
        @XmlElement (name = "requiredCompetencies")
   	   public List<Integer> getRequirements(){
		   return this.requiredCompetencies ;
	   }
   	   
   	   public void setRequirements(List<Integer> requirements){
   		   for(int i=0;i<requirements.size();i++){
    		      this.requiredCompetencies.add(requirements.get(i));
    	   }   		   		 
	   }
           public synchronized int getEstimatedCost(){
		   return this.estimatedCost;
	   }

   	   public synchronized void setEstimatedCost(int costEstimated){
		   this.estimatedCost = costEstimated;
                   isCostEstimated = true;
	   }
   	   
           public synchronized boolean isCostEstimated(){
		  return isCostEstimated ;
	   }
            public synchronized boolean getRescued(){
   		   return this.isRescued;
   	   }
   	   
   	   public synchronized void setRescued(){
   		   this.isRescued = true;
   	   }
   	   //Method for debugging
    @Override
   	   public String toString(){
   		   return "Victim: " + " name->" + this.getName() + " ; coordinate->" + this.getCoordinateVictim() + 
   		          " ; priority->" + this.getPriority() + " ; requirements->" + this.getRequirements();
   	   }
 @XmlElement (name = "victimCoordinateP")
    public Point getLocPoint() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(this.coordinateVictim == null)this.coordinateVictim= new Coordinate(victimCoordinateP.x, victimCoordinateP.y, 0);
        return this.victimCoordinateP;
    }
    public void setLocPoint(Point punto) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         this.victimCoordinateP=punto;
         if(this.coordinateVictim == null)this.coordinateVictim= new Coordinate(0, 0, 0);
          coordinateVictim.setX(victimCoordinateP.x);
          coordinateVictim.setY(victimCoordinateP.y);
          
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
   	      	   
}
