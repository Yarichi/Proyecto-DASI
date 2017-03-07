package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.ClasificadorVisual;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.io.Serializable;
import org.apache.log4j.Logger;

public class NotificacionesRecTrazas implements Serializable{

	/**
	 * @uml.property  name="clasificador"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="controlador:icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.ClasificadorVisual"
	 */
  private  ClasificadorVisual clasificador;
 
  private String identGestoraReportar;
  private ItfUsoAgenteReactivo gestoraReportar;
  private transient Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());
 public NotificacionesRecTrazas() {

//        TreeSet ConjIdentifPaneles = new TreeSet();
	}
public synchronized void pedirTerminacionOrganizacion() {
        try {       
            if ( gestoraReportar != null)gestoraReportar.aceptaEvento(new EventoRecAgte("peticion_terminar_todo_usuario", NombresPredefinidos.RECURSO_TRAZAS, gestoraReportar.getIdentAgente()));        
               else logger.fatal( "Error al pedirTerminacionOrganizacion. El gestor a reportar es null ,");     
		} catch (Exception e) {
			e.printStackTrace();
                        logger.fatal( "Error al obtener la interfaz del gestor Inicial . Hay un problema con el recurso de trazas,",e);
		}
	}

public synchronized void confirmarTerminacionOrganizacion() {
	try {
              if(gestoraReportar != null){
                gestoraReportar.aceptaEvento(new EventoRecAgte("terminacion_confirmada", NombresPredefinidos.RECURSO_TRAZAS, gestoraReportar.getIdentAgente()));
                 NombresPredefinidos.RECURSO_TRAZAS_OBJ.aceptaNuevaTraza(new InfoTraza(NombresPredefinidos.RECURSO_TRAZAS,
				 "Se envia el evento con input :"+ "terminacion_confirmada" + " al   agente "+gestoraReportar.getIdentAgente(),
				InfoTraza.NivelTraza.debug));
                            }
                            else
                                logger.fatal( "Error al pedirTerminacionOrganizacion. No se puede obtener la interfaz del gestor a reportar : "+identGestoraReportar );

		} catch (Exception e) {
			e.printStackTrace();
                        logger.fatal( "Error al enviar eventos al  gestor Inicial . Hay un problema con el recurso de trazas,",e);
		}
	}
public void anularTerminacionOrganizacion() {
		try {                   
                   if ( gestoraReportar != null)    gestoraReportar.aceptaEvento(new EventoRecAgte("terminacion_anulada", NombresPredefinidos.RECURSO_TRAZAS, gestoraReportar.getIdentAgente()));
                     else
                        logger.fatal( "Error al pedirTerminacionOrganizacion. No se puede obtener la interfaz del gestor a reportar : "+identGestoraReportar );
                } catch (Exception e) {
			e.printStackTrace();
                        logger.fatal( "Error al enviar eventos  al  gestor Inicial . Hay un problema con el recurso de trazas,",e);
		}
	}
public void setIdentGestoraReportar (String identGestor){     
        try {   
               if ( identGestor != null){
                   identGestoraReportar = identGestor;
                   gestoraReportar = (ItfUsoAgenteReactivo)NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(NombresPredefinidos.ITF_USO+identGestoraReportar);  
                    }
               else logger.fatal( "Error . El gestor a reportar es null ,");
                   
		} catch (Exception e) {
			e.printStackTrace();
                        logger.fatal( "Error al obtener la interfaz del gestor Inicial . Hay un problema con el recurso de trazas,",
								e);

		}
        
}
   
	
}
