package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import java.util.List;


public class DescInstanciaGestor extends DescInstanciaAgente {
	private List<DescInstancia> componentesGestionados;

	public List<DescInstancia> getComponentesGestionados() {
		return componentesGestionados;
	}

	public void setComponentesGestionados(List<DescInstancia> componentesGestionados) {
		this.componentesGestionados = componentesGestionados;
	}
	
}
