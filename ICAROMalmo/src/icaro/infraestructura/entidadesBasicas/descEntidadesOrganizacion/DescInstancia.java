package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Nodo;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.TipoAgente;
import java.io.Serializable;

import java.util.Properties;


public class DescInstancia implements Serializable {
	private String id;
	private Nodo nodo;
	private Properties propiedades;
        private String categoriaComponente; //agente de Aplicacion, recurso
        private String tipoComponente; // Si es agente : cognitivo, reactivo
	private String rolComponente;
     //   private Boolean isReactivo;
   //     private Boolean esDirigidoPorObjetivos;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Nodo getNodo() {
		return nodo;
	}
	public void setNodo(Nodo nodo) {
		this.nodo = nodo;
	}
	public void setPropiedades(Properties propiedades) {
		this.propiedades = propiedades;
	}
	public String getValorPropiedad(String atributo) {
		return propiedades.getProperty(atributo);
	}
        public String getCategoriaComponente() {
		return categoriaComponente;
	}
        public void setCategoriaComponente(String categoriaComp) {
		this.categoriaComponente = categoriaComp;
	}
        public String getTipoComponente() {
		return tipoComponente;
	}
        public void setTipoComponente(String modeloComp) {
		this.tipoComponente = modeloComp;
	}
        public String getRolComponente() {
		return rolComponente;
	}
        public void setRolComponente(String rol) {
		this.rolComponente = rol;
	}
        
        public boolean esDirigidoPorObjetivos() {
            return(tipoComponente.equals(TipoAgente.ADO.value())||tipoComponente.equals(TipoAgente.COGNITIVO.value()))||
                    tipoComponente.equals(TipoAgente.DIRIGIDO_POR_OBJETIVOS.value());
          
	}
          public boolean esReactivo() {
            return(tipoComponente.equals(TipoAgente.REACTIVO.value()));
        }
}
