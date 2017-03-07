package icaro.infraestructura.entidadesBasicas.factorias;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.FactoriaAutomatas;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.FactoriaAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.imp.FactoriaAgenteCognitivoImp2;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp.FactoriaControlAgteReactivoInputObjImp0;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.FactoriaAgenteReactivoInputObjImp0;
import icaro.infraestructura.patronRecursoSimple.*;
import icaro.infraestructura.patronRecursoSimple.imp.FactoriaRecursoSimpleImp2;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.ClaseGeneradoraRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

public  class FactoriaComponenteIcaro {

	private static FactoriaComponenteIcaro instanceFactoriaCompIcaro;
        private static FactoriaRecursoSimple instanceFactoriaRecursoSimple;
        private static FactoriaAutomatas instanceFactoriaAutomatas;
        private static FactoriaControlAgteReactivoInputObjImp0 instanceFactoriaControlAgteReactivoInputObjImp0;
        private static FactoriaAgenteReactivoInputObjImp0 instanceFactoriaAgteReactivoInputObjImp0;
        private static FactoriaAgenteCognitivo instanceFactoriaAgteReactivo;
        public ItfUsoRecursoTrazas recursoTrazas ;
        public ItfUsoRepositorioInterfaces repositorioInterfaces;

	public static FactoriaComponenteIcaro instanceFCI() {
		if (instanceFactoriaCompIcaro == null)
			instanceFactoriaCompIcaro = new FactoriaComponenteIcaro();
		return instanceFactoriaCompIcaro;
	}
        public static FactoriaRecursoSimple instanceFRS() {
		if (instanceFactoriaRecursoSimple == null)
			instanceFactoriaRecursoSimple = new FactoriaRecursoSimpleImp2();
		return instanceFactoriaRecursoSimple;
	}
        public static FactoriaAutomatas instanceAtms() {
		if (instanceFactoriaAutomatas == null)
			instanceFactoriaAutomatas = new FactoriaAutomatas();
		return instanceFactoriaAutomatas;
	}
	public static FactoriaControlAgteReactivoInputObjImp0 instanceControlAgteReactInpObj(){
            if (instanceFactoriaControlAgteReactivoInputObjImp0 == null)
			instanceFactoriaControlAgteReactivoInputObjImp0 = new FactoriaControlAgteReactivoInputObjImp0();
		return instanceFactoriaControlAgteReactivoInputObjImp0;
        }
	public  void crearRecursoSimple(DescInstanciaRecursoAplicacion recurso){
            FactoriaComponenteIcaro.instanceFRS().crearRecursoSimple(recurso);
        }
        public static FactoriaAgenteReactivoInputObjImp0 instanceAgteReactInpObj(){
            if (instanceFactoriaAgteReactivoInputObjImp0 == null)
			instanceFactoriaAgteReactivoInputObjImp0 = new FactoriaAgenteReactivoInputObjImp0();
		return instanceFactoriaAgteReactivoInputObjImp0;
        }
        public static FactoriaAgenteCognitivo instanceAgteCognitivoImp2(){
            if (instanceFactoriaAgteReactivo == null)
			instanceFactoriaAgteReactivo = new FactoriaAgenteCognitivoImp2();
		return instanceFactoriaAgteReactivo;
        }
	
      public  void crearRepositorioInterfaces(){ 	
       
            try {
            // Se crea el repositorio de interfaces y el recurso de trazas
                if ( NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ != null)
                                 repositorioInterfaces =NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
                else    {
                 repositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();         
                 NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ = repositorioInterfaces;
                }
                } catch (Exception e) {
                    String msgUsuario = "Error. No se pudo crear el repositorio de interfaces ";
                    System.err.println(msgUsuario);
                    e.printStackTrace();
               }
      }           
      public  void crearRecursoTrazas(){ 
            try {   
                 if (NombresPredefinidos.RECURSO_TRAZAS_OBJ != null)
                                                    recursoTrazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ ;
                 else{
                 recursoTrazas = ClaseGeneradoraRecursoTrazas.instance();
                 if ( NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ == null) this.crearRepositorioInterfaces();
                 repositorioInterfaces.registrarInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS,recursoTrazas);
                 repositorioInterfaces.registrarInterfaz(
                            NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS,recursoTrazas);
                    // Guardamos el recurso de trazas y el repositorio de Itfs en la clase de nombres predefinidos
                 NombresPredefinidos.RECURSO_TRAZAS_OBJ = recursoTrazas;
                 }
            } catch (Exception e) {
                String msgUsuario = "Error. No se pudo crear o registrar el recurso de trazas";
                    System.err.println(msgUsuario);
                    e.printStackTrace();
                //no es error cr�tico
               }
      }
}
