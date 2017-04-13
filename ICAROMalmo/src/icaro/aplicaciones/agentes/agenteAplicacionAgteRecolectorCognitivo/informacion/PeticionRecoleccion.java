package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;

public class PeticionRecoleccion extends MensajeSimple{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String emisor;
	private String mensaje;
	private Manzana manzana;
	
	public PeticionRecoleccion(String e, String m, Manzana mm){
		super();
		this.emisor = e;
		this.mensaje = m;
		this.manzana = mm;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Manzana getManzana() {
		return manzana;
	}

	public void setManzana(Manzana manzana) {
		this.manzana = manzana;
	}
}
