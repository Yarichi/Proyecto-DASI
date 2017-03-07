package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.Arrays;





/**
 *  Representa el foco del sistema. Como no se puede acceder al contexto en la
 *  parte condicional de una regla, de momento se inserta en el motor de
 *  inferencias como un objeto para poder acceder al objetivo focalizado
 *
 *@author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class FocusOld {

    private Objetivo foco = null;
    /**
     *  Cola circular que guarda los focos anteriores
     */
    protected Objetivo[] focosAnteriores = null;
    /**
     *  Tamao mximo de la cola circular
     */
    protected final static int TAM_COLA_FOCOS = 5;
    /**
     *  Indice de la cola circular
     */
    protected int indice = 0;
    protected Objetivo objetivoFocalizado;
    
    protected ItfUsoRecursoTrazas trazas= NombresPredefinidos.RECURSO_TRAZAS_OBJ;


    /**
     *  Constructor for the Focus object
     *
     *@param  motor  Description of the Parameter
     */
    public  FocusOld() {
        // Crea cola circular
        this.focosAnteriores = new Objetivo[TAM_COLA_FOCOS];
        Arrays.fill(this.focosAnteriores, null);
        this.indice = 0;
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
        int posobjetivos = 0;
        String claseobjetivo = obj.getClass().getName();
        posobjetivos = claseobjetivo.indexOf("objetivos");
        claseobjetivo = claseobjetivo.substring(posobjetivos);
        
    	//trazas.aceptaNuevaTraza(new InfoTraza("Focalizaciones","Foco: Focalizando el objetivo "+obj.getID() + " , class -> " + claseobjetivo,InfoTraza.NivelTraza.debug));  	
    	// Introduce el foco nuevo en la cola, siempre que no fuera el mismo objetivo que el anterior
             if (obj != this.foco) {
                if (foco != null) foco.setisfocused(false);
                 this.foco = obj;
                 obj.setisfocused(true);
                 this.focosAnteriores[indice] = obj;
                 this.indice = (this.indice + 1) % TAM_COLA_FOCOS;

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

    /**
     *  Devolvemos la referencia al Objetivo al cual apuntaba el foco
     *  anteriormente
     *
     *@return    Refernacia al objetivo en el que estaba antes el foco
     */
    public synchronized Objetivo getFocoAnterior() {
        return this.focosAnteriores[(TAM_COLA_FOCOS + this.indice - 1) % TAM_COLA_FOCOS];
    }

    /**
     *  Refocaliza en el objetivo anterior al actualmente focalizado Solo se
     *  puede refocalizar al objetivo inmediantamente anterior (memoria 1 slo
     *  paso)
     */
    public synchronized void refocus() {
        this.indice = (TAM_COLA_FOCOS + this.indice - 1) % TAM_COLA_FOCOS;
        this.foco = this.focosAnteriores[this.indice];
        trazas.aceptaNuevaTraza(new InfoTraza("","Foco: Focalizando el objetivo "+foco.getgoalId(),InfoTraza.NivelTraza.debug));
        
        
        
    }

    /**
     *  Refocaliza en el objetivo anterior al actualmente focalizado Solo se
     *  puede refocalizar al objetivo inmediantamente anterior (memoria 1 slo
     *  paso) Actualiza el objetivo que acabamos de re-focalizar al estado
     *  pending
     */
    public synchronized void refocusYCambiaAPending() {
        this.indice = (TAM_COLA_FOCOS + this.indice - 1) % TAM_COLA_FOCOS;
        this.foco = this.focosAnteriores[this.indice];
        this.foco.setPending();
        trazas.aceptaNuevaTraza(new InfoTraza("","Foco: Focalizando el objetivo "+foco.getgoalId(),InfoTraza.NivelTraza.debug));
    }
    
    public synchronized void setFocusToObjetivoMasPrioritario(MisObjetivos misObjs){
        Objetivo obj = misObjs.getobjetivoMasPrioritario();
        if (obj == null) this.foco= null;
        else
         if (obj != this.foco) {

            this.foco = obj;

            this.indice = (this.indice + 1) % TAM_COLA_FOCOS;
            this.focosAnteriores[indice] = obj;


        }
    }
    /**
     *  Devuelve el contenido del foco como una cadena de texto
     *
     *@return    Description of the Return Value
     */
    public String toString() {
        return "(FOCO: focoActual= " + this.foco + "  focoAnterior= " + this.focosAnteriores[(this.indice + TAM_COLA_FOCOS - 1) % TAM_COLA_FOCOS] + " )";
    }

    /**
     *  Obtiene el contenido de la cola de focos anteriores
     *
     *@return    Description of the Return Value
     */
    public String toStringCola() {

        String res = "";
        res += "Cola de Objetivos focalizados:\n";
        for (int i = 0; i < TAM_COLA_FOCOS; i++) {
            res += "Posicion " + i + ": " + this.focosAnteriores[i] + '\n';
        }

        res += "Objetivo focalizado en posicion " + this.indice + ": " + this.focosAnteriores[this.indice];

        return res;
    }
}

