package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;

public class RespuestaRecoleccion extends MensajeSimple{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String emisor;
	public String objetivoID;
	private String mensaje;
	private Integer coste;
	
	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public String getObjetivoID() {
		return objetivoID;
	}

	public void setObjetivoID(String objectID) {
		this.objetivoID = objectID;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getCoste() {
		return coste;
	}

	public void setCoste(Integer coste) {
		this.coste = coste;
	}

	public RespuestaRecoleccion(String emisor, String mensaje, String id,Integer coste) {
		super();
		this.emisor = emisor;
		this.mensaje = mensaje;
		this.coste = coste;
		this.objetivoID = id;
	}
	
	
}
