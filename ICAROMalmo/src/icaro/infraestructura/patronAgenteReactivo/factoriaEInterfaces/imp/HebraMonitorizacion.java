package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp;


import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;


/**
* Clase interna que se encarga de generar eventos de monitorizacin cada cierto tiempo
*
* @author Carlos Delgado
*/
public class HebraMonitorizacion extends Thread {

    /**
	 * Milisegundos que esperar antes de lanzar otra monitorizacin
	 * @uml.property  name="milis"
	 */
    protected long milis;

    /**
	 * Indica cundo debe dejar de monitorizar
	 * @uml.property  name="finalizar"
	 */
    protected boolean finalizar;

    /**
	 * Agente reactivo al que se pasan los eventos de monitorizacin
	 * @uml.property  name="agente"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
     protected ItfUsoAgenteReactivo agente;

     /**
	 * Evento a producir
	 * @uml.property  name="evento"
	 */
     protected String evento="";

    /**
     * Constructor
     *
     * @param milis Milisegundos a esperar entre cada monitorizacin
     * @param eventoAProducir Elemento que se produce en la interfaz cada cierto tiempo
     */
    public HebraMonitorizacion(long milis, ItfUsoAgenteReactivo agente, String eventoAProducir) {
      super("HebraMonitorizacion del agente reactivo "+agente.toString());
      this.milis= milis;
      this.finalizar= false;
      this.agente= agente;
      this.setDaemon(true);
      this.evento = eventoAProducir;
    }


    /**
     * Termina la monitorizacin
     */
    public void finalizar() {
	this.finalizar= true;
    }

    @Override
    public void run() {

      this.finalizar= false;

      while (!this.finalizar) {

	  // Duerme lo especificado
	  try {
	    Thread.sleep(this.milis);
	  } catch (InterruptedException ex) {}

	  // Genera un nuevo evento de input
	  if (!this.finalizar)
		try {
			this.agente.aceptaEvento(new EventoRecAgte(this.evento,null,null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
    }

}
