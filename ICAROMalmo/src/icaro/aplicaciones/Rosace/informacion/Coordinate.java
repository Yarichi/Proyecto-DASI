package icaro.aplicaciones.Rosace.informacion;

import java.io.Serializable;

public class Coordinate implements Serializable, Cloneable{

  public double x, y, z;
  
  public Coordinate(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  public synchronized void setX (double XCoord){
      this.x=XCoord;
  }
  public synchronized double getX (){
      return this.x;
  }
  public synchronized void setY (double YCoord){
      this.y=YCoord;
  }
  public synchronized double getY (){
      return this.y;
  }
  public synchronized void setZ (double ZCoord){
      this.z=ZCoord;
  }
  public synchronized double getZ (){
      return this.z;
  }
  @Override
  public String toString() {
    return "Coordinate: (" + x + "," + y + "," + z +")";
  }
  @Override
  public boolean equals(Object obj){
	  Coordinate c=(Coordinate)obj;
	  return (c.getX() == this.x && c.getY() == this.y);
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
