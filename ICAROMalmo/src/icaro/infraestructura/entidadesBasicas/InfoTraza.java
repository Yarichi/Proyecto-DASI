package icaro.infraestructura.entidadesBasicas;


public class InfoTraza {
	
	public static enum NivelTraza {error,info,debug,fatal,masterIA}
	
	private String entidadEmisora;
	private String mensaje;
	private NivelTraza nivel;
	
	public InfoTraza (String entidadEmisora,String contenido,NivelTraza nivel){
		this.entidadEmisora = entidadEmisora;
		this.mensaje = contenido;
		this.nivel = nivel;
	}
	
	public InfoTraza() {
		// TODO Auto-generated constructor stub
	}

	public void setNombre(String n){this.entidadEmisora = n;}
	public void setMensaje(String m){this.mensaje = m;}
	public void setNivel(NivelTraza n){this.nivel = n;}
	
	public String getNombre(){return this.entidadEmisora;}
	public String getMensaje(){return this.mensaje;}
	public NivelTraza getNivel(){return this.nivel;}
	
}
