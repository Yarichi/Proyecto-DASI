package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public abstract class FactoriaControlAgteReactivo {
    
    private static FactoriaControlAgteReactivo instancia;
    
    public static FactoriaControlAgteReactivo instancia(){
        Log log = LogFactory.getLog(FactoriaControlAgteReactivo.class);
        if(instancia==null){
            String clase = System.getProperty("icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp",
            		"icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp.FactoriaControlAgteReactivoImp2");
            try{
                instancia = (FactoriaControlAgteReactivo)Class.forName(clase).newInstance();
            }catch(Exception ex){
                log.error("Implementacion del Control no encontrado",ex);
            }
            
        }
        return instancia;
    }
    
//public abstract ProcesadorEventosAbstracto crearControl(ItfConsumidorPercepcion percConsumidor, AutomataEFEAbstracto automata,
//												  ItfProductorPercepcion percProductor, String nombreDelControl);
public abstract  ProcesadorInfoReactivoAbstracto crearControlAgteReactivo(AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas, String nombreFicheroTablaEstados, String nombreDelAgente,ItfConsumidorPercepcion percConsumidor,
		ItfProductorPercepcion percProductor)throws ExcepcionEnComponente;
public abstract  ProcesadorInfoReactivoAbstracto crearControlAgteReactivo(AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas, String nombreFicheroTablaEstados, AgenteReactivoAbstracto agente
	)throws ExcepcionEnComponente;
}
