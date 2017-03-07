/*
 * FactoriaAdaptadorMotorReglas.java
 *
 * Creado 18 de abril de 2007, 11:52
 *
 * Telefonica I+D Copyright 2006-2007
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.imp.FactoriaMotorDeReglasDroolsImp1;
import java.io.InputStream;



/**
 * Factory for Rule Engine
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 * @author Carlos Celorrio
 */
public abstract class FactoriaMotorDeReglas {
	
//	private static final String FACTORY_IMP_PROPERTY = "icaro.infraestructura.PatronAgenteCognitivo.procesadorObjetivos.motorDeReglas.imp";
   
    private static FactoriaMotorDeReglas instance;
    
    public static FactoriaMotorDeReglas instance(){
        if(instance==null){
            String c = System.getProperty(NombresPredefinidos.RUTA_FACTORIA_MOTOR_REGLAS,
                    FactoriaMotorDeReglasDroolsImp1.class.getName());
            try{
                instance = (FactoriaMotorDeReglas) Class.forName(c).newInstance();
            }catch(Exception ex){
                throw new RuntimeException("Implementation not found for: " + c);
            }
        }
        return instance;

    }
    public abstract ItfMotorDeReglas crearMotorDeReglas(AgenteCognitivo agent);
//    public abstract ItfMotorDeReglas crearMotorDeReglas(AgenteCognitivo agent, InputStream reglas);
    public abstract ItfMotorDeReglas crearMotorDeReglas(AgenteCognitivo agent, InputStream reglas, String ficheroReglas);
}
