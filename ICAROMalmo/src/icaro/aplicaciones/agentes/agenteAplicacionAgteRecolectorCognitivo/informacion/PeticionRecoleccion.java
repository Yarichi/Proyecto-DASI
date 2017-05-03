package icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion;

import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;

public class PeticionRecoleccion{



	private String mensaje;
	private Manzana manzana;
	private String receptor;
	private String emisor;
	public PeticionRecoleccion(String e, String m, Manzana mm){
		super();
		this.mensaje = m;
		this.manzana = mm;
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
	public String getReceptor() {
		return receptor;
	}

	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
}
