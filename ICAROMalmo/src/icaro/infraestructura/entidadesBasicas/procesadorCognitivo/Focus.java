package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import org.openide.util.Exceptions;





/**
 *  Representa el foco del sistema. Como no se puede acceder al contexto en la
 *  parte condicional de una regla, de momento se inserta en el motor de
 *  inferencias como un objeto para poder acceder al objetivo focalizado
 *
 *@author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class Focus {

    private Objetivo foco = null;
    /**
     *  Cola circular que guarda los focos anteriores
     */
    protected BlockingDeque <Objetivo> focosAnteriores;
    /**
     *  Tamao mximo de la cola circular
     */
    protected String faseProcesoConsecObjetivos;
    /**
     *  Indice de la cola circular
     */
   
    protected Objetivo objetivoFocalizado;
    
    protected ItfUsoRecursoTrazas trazas= NombresPredefinidos.RECURSO_TRAZAS_OBJ;


    /**
     *  Constructor for the Focus object
     *
     *@param  motor  Description of the Parameter
     */
    public  Focus() {
        // Crea cola circular
        this.focosAnteriores = new LinkedBlockingDeque<Objetivo>();
        objetivoFocalizado = null;
        
		}
        
    public void inicializar(){
        this.focosAnteriores = new LinkedBlockingDeque<Objetivo>();
        objetivoFocalizado = null;
    }
    /**
     *  Fija el foco al objetivo obj
     *
     *@param  obj  Objetivo al cual apuntar el foco
     */
    public synchronized void setFoco(Objetivo obj) {
//    	trazas.aceptaNuevaTraza(new InfoTraza("Focalizaciones","Foco: Focalizando el objetivo "+obj.getID(),InfoTraza.NivelTraza.debug));
    	
    	//Añadido para depurar los objetivos: informo del identificador y la clase de objetivo
        if (obj == null)this.foco=null;
        else {
    	//trazas.aceptaNuevaTraza(new InfoTraza("Focalizaciones","Foco: Focalizando el objetivo "+obj.getID() + " , class -> " + claseobjetivo,InfoTraza.NivelTraza.debug));  	
    	// Introduce el foco nuevo en la cola, siempre que no fuera el mismo objetivo que el anterior
             if (obj != this.foco) {
                if (foco != null) foco.setisfocused(false);
                 this.foco = obj;
                 obj.setisfocused(true);
                 this.focosAnteriores.add(obj);

            }
        }
    }

    /**
     *  Devolvemos la referencia al Objetivo al cual apunta el foco
     *
     *@return    Refernacia al objetivo
     */
    public synchronized Objetivo getFoco() {
        return this.foco;
    }
    public synchronized String getfaseProcesoConsecObjetivos() {
        return faseProcesoConsecObjetivos;
    }
    public synchronized void setfaseProcesoConsecObjetivos(String faseProcesoConsec) {
         faseProcesoConsecObjetivos= faseProcesoConsec;
    }
    /**
     *  Devolvemos la referencia al Objetivo al cual apuntaba el foco
     *  anteriormente
     *
     *@return    Refernacia al objetivo en el que estaba antes el foco
     */
    public synchronized Objetivo getFocoAnterior() {
        return this.focosAnteriores.peek();
    }

    /**
     *  Refocaliza en el objetivo anterior al actualmente focalizado 
     *  Elimina de la pila el foco y lo toma como foco actual
     *  
     */
      public synchronized void refocus() {
       if (this.focosAnteriores.peekLast() == null) this.setFoco(null);
       else this.setFoco (this.focosAnteriores.pollLast());
//        trazas.aceptaNuevaTraza(new InfoTraza("","Foco: Focalizando el objetivo "+foco.getgoalId(),InfoTraza.NivelTraza.debug));
        
        
    }

    /**
     *  Refocaliza en el objetivo anterior al actualmente focalizado Solo se
     *  puede refocalizar al objetivo inmediantamente anterior (memoria 1 slo
     *  paso) Actualiza el objetivo que acabamos de re-focalizar al estado
     *  pending
     */
    public synchronized void refocusYCambiaAPending() {
       
        this.foco = this.focosAnteriores.poll();
        if (foco !=null){
            this.foco.setPending();
            trazas.aceptaNuevaTraza(new InfoTraza("","Foco: Focalizando el objetivo "+foco.getgoalId(),InfoTraza.NivelTraza.debug));
        }
        }
    
    public synchronized void setFocusToObjetivoMasPrioritario(MisObjetivos misObjs){
        Objetivo obj = misObjs.getobjetivoMasPrioritario();
        if (obj == null) this.foco= null;
        if (this.foco!=null && obj != this.foco) {
            try {
                this.wait();
                this.focosAnteriores.addFirst(this.foco);
            this.foco = obj;
            } catch (InterruptedException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
    public synchronized void refocusUltimoObjetivoSolving(){
    /**
     *  Devuelve el ultimo objetivo focalizado que sigue en solving . Esto permite refocalizar en el ultimo 
     *  Objetivo solving. De paso eliminamos los objetivos  conseguidos 
     *@return    Description of the Return Value
     */
        
        while (foco!=null && foco.getState()== Objetivo.SOLVED){
            foco=this.focosAnteriores.pollLast();
        }
    }
    @Override
    public String toString() {
        return "(FOCO: focoActual= " + this.foco + "  focosAnteriores= " + this.focosAnteriores + " )";
    }

}

