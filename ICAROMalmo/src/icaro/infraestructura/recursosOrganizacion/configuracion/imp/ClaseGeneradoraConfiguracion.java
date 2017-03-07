package icaro.infraestructura.recursosOrganizacion.configuracion.imp;

import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.recursosOrganizacion.configuracion.*;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import java.rmi.RemoteException;





/**
 * @author Damiano Spina
 * @version 1.0
 * @created 19-feb-2008 13:20:43
 */
public abstract class ClaseGeneradoraConfiguracion extends ImplRecursoSimple implements ItfUsoConfiguracion {
	
	
	public ClaseGeneradoraConfiguracion(String idRecurso) throws RemoteException {
		super(idRecurso);
	}

	private static ClaseGeneradoraConfiguracion instance;

	public static ClaseGeneradoraConfiguracion instance() throws ExcepcionEnComponente, RemoteException {
            try {
            if (instance == null)
			instance = new ConfiguracionImp();
		return instance;
	} catch (Exception e) {
 //           logger.fatal("\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en el fichero de descripcion del XML " + xmlDescripcion.getAbsolutePath() + ".",
//							e);      
	throw new ExcepcionEnComponente("\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en el fichero de descripcion del XML " );
        }
        }          
	
	public static ClaseGeneradoraConfiguracion instance(String descripcionXMLAlternativo) throws ExcepcionEnComponente, RemoteException {
		if (instance == null)

                       instance = new ConfiguracionImp(descripcionXMLAlternativo);
		return instance;


                }
	
	
}