/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

/**
 *
 * @author Francisco J Garijo
 */
public class InformeDeTarea extends Informe{
	
//    public Object contenidoInforme;
    public String identTarea;
    public String contextGoal;
    public CausaTerminacionTarea estatusTerminacion;
    public String identAgenteEmisor;

    public InformeDeTarea (String  idTarea,String idAgenteEmisor,Object contenido){
        super (idAgenteEmisor,contenido);
        identTarea = idTarea;
        identAgenteEmisor = idAgenteEmisor;
   //     contenidoInforme = contenido;
        estatusTerminacion = CausaTerminacionTarea.EXITO;
    }
    
    public InformeDeTarea (String  idTarea,String contxtGoal,String idAgenteEmisor,Object contenido){
        super (idAgenteEmisor,contxtGoal,contenido);
        identTarea = idTarea;
        contextGoal = contxtGoal;
        identAgenteEmisor = idAgenteEmisor;
   //     contenidoInforme = contenido;
        estatusTerminacion = CausaTerminacionTarea.EXITO;
    }
         
    public InformeDeTarea (String  idTarea,String contxtGoal,String idAgenteEmisor,Object contenido, CausaTerminacionTarea causaTerminacion ){
        super (idAgenteEmisor,contxtGoal,contenido);
        identTarea = idTarea;
        contextGoal = contxtGoal;
        identAgenteEmisor = idAgenteEmisor;
 //       contenidoInforme = contenido;
        estatusTerminacion = causaTerminacion;
    }

    public String getIdentTarea() {
		return identTarea;
	}
    
//    @Override
 //   public Object getContenidoInforme() {
 //       return contenidoInforme;
//	}
    
    
//    public String getcontextGoal() {
//        return contextGoal;
//	}

    public CausaTerminacionTarea getCausaTerminacion() {
        return estatusTerminacion;
	}
    
    public String getidentAgenteEmisor() {
		return identAgenteEmisor;
	}

    /**
     *  JM: Cadena de texto para la depuración
     */    
    @Override
    public String toString(){
    	return "InformeDeTarea: " + "Tarea->" + this.getIdentTarea() + " ; AgenteEmisor->" + this.getidentAgenteEmisor() + 
    	       " ; Contenido->" + this.getContenidoInforme() + " ; contextGoal->" + this.getReferenciaContexto();
    }
    
}
