package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

public class ComprobadorRutas {
	
	public ComprobadorRutas() {
	}
	
	
	public boolean existeClase(String rutaClase) {
			Class clase;
			try {
				clase = Class.forName(rutaClase);
				return (clase!=null);
			} catch (ClassNotFoundException e) {
				return false;
			}		
	}
}
