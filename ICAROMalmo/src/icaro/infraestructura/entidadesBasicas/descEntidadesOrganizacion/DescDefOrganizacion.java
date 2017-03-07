package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.*;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.*;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Nodo;

import java.util.Properties;


public class DescDefOrganizacion {
	private String IdentFicheroDefOrganizacion;
	private Nodo nodo;
	private Properties propiedades;
	
	
	
	public String getIdentFicheroDefOrganizacion() {
		return IdentFicheroDefOrganizacion;
	}
	public void setIdentFicheroDefOrganizacion(String id) {
		this.IdentFicheroDefOrganizacion = id;
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
}
