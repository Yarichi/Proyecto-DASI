package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;


public class InfoTraza {
	
	public static enum NivelTraza {error,info,debug}
//	public static enum TipoEntidad {Cognitivo,ADO,DirigidoPorObjetivos,Reactivo,Recurso}
        
	private String identEntidadEmisora;
        private NombresPredefinidos.TipoEntidad tipoEntidadEmisora;
	private String mensaje;
	private NivelTraza nivel;
	
	public InfoTraza (String entidadEmisora,String contenido,NivelTraza nivel){
		this.identEntidadEmisora = entidadEmisora;
                this.tipoEntidadEmisora = null;
		this.mensaje = contenido;
		this.nivel = nivel;
	}
        public InfoTraza (String idEntidadEmisora,NombresPredefinidos.TipoEntidad tipoEnt,String contenido,NivelTraza nivel){
		this.identEntidadEmisora = idEntidadEmisora;
                this.tipoEntidadEmisora = tipoEnt;
		this.mensaje = contenido;
		this.nivel = nivel;
	}
	
	public InfoTraza() {
	}

	public void setNombre(String n){this.identEntidadEmisora = n;}
	public void setMensaje(String m){this.mensaje = m;}
	public void setNivel(NivelTraza n){this.nivel = n;}
	
	public String getEntidadEmisora(){return this.identEntidadEmisora;}
	public String getMensaje(){return this.mensaje;}
        public NombresPredefinidos.TipoEntidad getTipoEntidadEmisora(){return this.tipoEntidadEmisora;}
	public NivelTraza getNivel(){return this.nivel;}
	
}
