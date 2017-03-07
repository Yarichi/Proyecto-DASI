package icaro.aplicaciones.Rosace.informacion;

public class FinSimulacion {
	
	   public boolean flagActivacion; 
	   
       public FinSimulacion(){
    	   this.flagActivacion = false;    	   
       } 
       
                      
       public boolean getflagActivacion(){
    	   return this.flagActivacion;
       }
       
       public void setflagActivation(boolean flag){
    	   this.flagActivacion = flag;
       }              
}
