/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Esta clase  modela los objetivos gestionados por el agente
 * En esta primera version se modelan los objetivos que el agente tiene que realizar y que requiren energía, utilizacion de sensores y 
 * otros recursos del agente. Se excluyen los objetivos internos que implican procesos de razomiento o decision
 * Se utiliza una cola de prioridad donde se insertan los objetivos pendientes y se eliminan cuando estan realizados
 * @author FGarijo
 */
public class MisObjetivos {
    protected PriorityBlockingQueue <Objetivo> misObjetivosPriorizados;
    protected SortedSet<String> setOfIGoalRefIds ; // idetificadores de los objetos a los que se refieren los objetivos ej Identif de vicitmas
    public Objetivo objetivoMasPrioritario;
    protected  Comparator c ;
  
    public MisObjetivos (){ 
            c = new Comparator<Objetivo>() {
           @Override
            public int compare(Objetivo o1, Objetivo o2) {
                if(o1.getPriority() <= (o2.getPriority()))return 1;
//                else if (o1.getPriority() ==  o2.getPriority())return 0;
                else return -1 ;
            }
        };
        misObjetivosPriorizados = new PriorityBlockingQueue <Objetivo> (11,c);
        setOfIGoalRefIds = new TreeSet<String>();
        objetivoMasPrioritario=null;
    }
    public void addObjetivo ( Objetivo obj){
        //if (misObjetivosPriorizados == null) misObjetivosPriorizados = new PriorityBlockingQueue <Objetivo> (11,c);
        //Objetivo o = new Objetivo();
        // verificamos que el objetivo no esta en la cola de objetivos
        String goalRefId = obj.getobjectReferenceId();
        if(goalRefId==null)goalRefId= obj.getgoalId();
        if ( existeObjetivoConEsteIdentRef(goalRefId))this.eliminarObjetivo(obj);
        misObjetivosPriorizados.add((Objetivo)obj);
        setOfIGoalRefIds.add(goalRefId);
        
    }
    public void inicializar(){
        misObjetivosPriorizados = new PriorityBlockingQueue <Objetivo> (11,c);
        setOfIGoalRefIds = new TreeSet<String>();
        objetivoMasPrioritario=null;
    }
    public boolean eliminarObjetivo ( Objetivo obj){
        String goalRefId = obj.getobjectReferenceId();
        if (goalRefId==null)return false;
        if ( existeObjetivoConEsteIdentRef(goalRefId)){
            setOfIGoalRefIds.remove(goalRefId);
           return   misObjetivosPriorizados.remove(obj);
        } else return false;  
    }
    public Objetivo getobjetivoMasPrioritario ( ){
        objetivoMasPrioritario= misObjetivosPriorizados.peek();
        return objetivoMasPrioritario;       
    }
    
    public void  setobjetivoMasPrioritario (Objetivo obj ){
        // Esto es un poco engagnoso pq no garantiza que el objetivo agnadido sea el mas prioritario
        addObjetivo(obj);     
    }
    public Boolean existeObjetivoConEsteIdentRef (String identRef ){
        
        return setOfIGoalRefIds.contains(identRef);
        
    }
    public void cambiarPrioridad(Objetivo obj){
        Objetivo objetivoNuevo=obj; // obj tiene prioridad diferente que el que esta en la cola 
        String goalRefId = obj.getobjectReferenceId();
        if(goalRefId==null)goalRefId= obj.getgoalId();
        if (goalRefId!=null&&setOfIGoalRefIds.contains(goalRefId)){
            this.misObjetivosPriorizados.remove(obj);
            this.misObjetivosPriorizados.add(objetivoNuevo);
        }
    }
    public PriorityBlockingQueue <Objetivo> getMisObjetivosPriorizados ( ){

        return misObjetivosPriorizados;      
    }
    public void deleteObjetivosSolved(){
    	Iterator<Objetivo> it = misObjetivosPriorizados.iterator();    	    	
    	while (it.hasNext()){
  	  	  //Hay al menos un objetivo    		
  	      Objetivo ob = it.next();
              String obrefId = ob.getobjectReferenceId();
  	      if(ob.getState()==Objetivo.SOLVED){
                 if (obrefId != null) setOfIGoalRefIds.remove(obrefId);
                  misObjetivosPriorizados.remove(ob);
              }
        }
    }
    
}
     
