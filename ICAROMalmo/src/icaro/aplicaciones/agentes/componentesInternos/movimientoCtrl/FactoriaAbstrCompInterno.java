package icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl;

import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.imp.FactoriaRIntMovimientoCtrl;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;

public abstract class FactoriaAbstrCompInterno {
        // cambiar cuando se pueda
	public abstract InfoCompMovimiento crearComponenteInterno(String identClaseQueImplementaInterfaz, ItfProcesadorObjetivos procObj);
        public abstract boolean verificarExisteInterfazUsoComponente(String identClaseQueImplementaInterfaz);
        public static FactoriaAbstrCompInterno instance;
	
       
        
public static FactoriaAbstrCompInterno instance() throws ExcepcionEnComponente {
            try {
            if (instance == null)
			instance = new FactoriaRIntMovimientoCtrl();
		return instance;
	} catch (Exception e) {
 //           logger.fatal("\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en el fichero de descripcion del XML " + xmlDescripcion.getAbsolutePath() + ".",
//							e);      
	throw new ExcepcionEnComponente("\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en el fichero de descripcion del XML " );
        }
        }    
  /*      {
		if (instance == null)
		instance = new RecursoTrazasImp(NombresPredefinidos.RECURSO_TRAZAS);
		return instance;
	}
*/	
}
