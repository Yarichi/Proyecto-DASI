package icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
//import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.imp.FactoriaAgenteCognitivoImp;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.imp.FactoriaAgenteCognitivoImp2;


/**
 * Abstract Factory for Cognitive Agents
 * @author carf
 * @author Carlos Celorrio
 */
public abstract class FactoriaAgenteCognitivo {
	
	/**
	 * System property for alternative factory implementation
	 */
//	private static final String FACTORY_IMP_PROPERTY = "icaro.infrastructure.pattern.cognitiveAgent.factory.imp";
	
    private static FactoriaAgenteCognitivo instance;

    /**
     * Gets the singleton instance of this factory.
     * @return A cognitive agent factory
     */
    public static FactoriaAgenteCognitivo instance() {
        if(instance==null){
//            String c = System.getProperty(FACTORY_IMP_PROPERTY,
//                    FactoriaAgenteCognitivoImp.class.getName());
  //          String c = System.getProperty(NombresPredefinidos.RUTA_FACTORIA_COGNITIVO,FactoriaAgenteCognitivoImp.class.getName());
          String c = System.getProperty(NombresPredefinidos.RUTA_FACTORIA_COGNITIVO,FactoriaAgenteCognitivoImp2.class.getName());
            try{
                instance = (FactoriaAgenteCognitivo) Class.forName(c).newInstance();
            }catch(Exception ex){
                throw new RuntimeException("Implementation not found for: " + c);
            }
        }
        return instance;
    }

    public abstract void createCognitiveAgent(DescInstanciaAgente descInstanciaAgente) throws Exception;
    public abstract void crearAgenteCognitivo(DescInstanciaAgente descInstanciaAgente) throws Exception;
}
