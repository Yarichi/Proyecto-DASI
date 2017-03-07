package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces;

import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public abstract class FactoriaPercepcion {
    
    private static FactoriaPercepcion instancia;
    
    public static FactoriaPercepcion instancia(){
        Log log = LogFactory.getLog(FactoriaPercepcion.class);
        if(instancia==null){
        	
            String clase = System.getProperty("icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.imp",
                    "icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.imp.FactoriaPercepcionImp");
            try{
                instancia = (FactoriaPercepcion)Class.forName(clase).newInstance();
            }catch(Exception ex){
                log.error("Implementacion de la Percepcion no encontrado",ex);
            }
            
        }
        return instancia;
    }
    
    public abstract PercepcionAbstracto crearPercepcion();
    public abstract PercepcionAbstracto crearPercepcion(AgenteReactivoAbstracto agente,ItfControlAgteReactivo itfControl);
    
}
