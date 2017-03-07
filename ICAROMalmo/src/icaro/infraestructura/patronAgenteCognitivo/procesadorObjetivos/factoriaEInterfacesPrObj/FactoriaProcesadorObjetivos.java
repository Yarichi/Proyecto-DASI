package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj;

import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.imp.FactoriaProcesadorObjetivosImp1;



/**
 * Factory for Cognitive Control
 * @author Carlos Celorrio
 *
 */
public abstract class FactoriaProcesadorObjetivos {
	
	private static final String FACTORY_IMP_PROPERTY = "icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.imp";
	
	private static FactoriaProcesadorObjetivos instance;

	/**
     * Gets the singleton instance of this factory.
     * @return A cognitive agent control factory
     */
    public static FactoriaProcesadorObjetivos instance() {
        if(instance==null){
            
            String c = System.getProperty(FACTORY_IMP_PROPERTY, FactoriaProcesadorObjetivosImp1.class.getName());
            
            try{
                instance = (FactoriaProcesadorObjetivos) Class.forName(c).newInstance();
                
            }catch(Exception ex){
                throw new RuntimeException("Implementation not found for: " + c);
            }
        }
        return instance;
    }

    public abstract ProcesadorObjetivos crearProcesadorObjetivos(AgenteCognitivo cognitiveAgent,
    		String goalResolutionFile) throws Exception;

}
