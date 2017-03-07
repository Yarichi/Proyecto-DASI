package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.ListaPropiedades;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Propiedad;

import java.util.Properties;

public class ConstructorProperties {

	public static Properties obtenerProperties(ListaPropiedades listaPropiedades) {
		Properties properties = new Properties();
		if (listaPropiedades != null) {
			for (Propiedad prop : listaPropiedades.getPropiedad())
				properties.put(prop.getAtributo(), prop.getValor());
		}
		return properties;

	}
}
