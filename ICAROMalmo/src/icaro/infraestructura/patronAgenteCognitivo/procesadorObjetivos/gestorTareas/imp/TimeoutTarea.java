package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.gestorTareas.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TimeoutTarea extends Thread {
	private static final int TIEMPO_MAX_EJECUCION_POR_DEFECTO = 60 * 1000 * 5; // 5
	// minutos

	private Integer tiempo;
	private TareaProxy proxy;
	
	private Log log = LogFactory.getLog(TimeoutTarea.class);

	public TimeoutTarea() {
		tiempo = TIEMPO_MAX_EJECUCION_POR_DEFECTO;
		this.setDaemon(true);
	}

	public TimeoutTarea(Integer tiempo, TareaProxy proxy) {
		super("TimeoutTarea-"+proxy.getName());
		this.tiempo = tiempo;
		this.proxy = proxy;
		this.setDaemon(true);
	}

	public Integer getTiempo() {
		return tiempo;
	}

	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
	}

	public TareaProxy getTarea() {
		return proxy;
	}

	public void setTarea(TareaProxy proxy) {
		this.proxy = proxy;
	}

	public void run() {
		try {
			sleep(tiempo);
		} catch (InterruptedException e) {
		}

		log.warn("TimeoutTarea: Se ha superado el tiempo maximo de ejecucion de la tarea"+proxy.getName());
		ItfUsoRecursoTrazas trazas = null;
		try {
			trazas = (ItfUsoRecursoTrazas) NombresPredefinidos.RECURSO_TRAZAS_OBJ;
			trazas.aceptaNuevaTraza(new InfoTraza(proxy.getAgente().getIdentAgente(),
					"Gestor de Tareas: TimeoutTarea: Se ha superado el tiempo maximo de ejecucion de la tarea"+proxy.getName(), NivelTraza.info));
		} catch (Exception e) {
			e.printStackTrace();
		}		

 //               if (!proxy.getTarea().terminada()) {
//		}

	}

	public void forzarTimeout() {
		this.interrupt();
	}

}
