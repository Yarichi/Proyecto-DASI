/*
 * Telefonica I+D Copyright 2006-2007
 */
package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import org.drools.runtime.StatefulKnowledgeSession;


/**
 * Interfaz del motorl de reglas
 * @author Carlos Rodriguez Fernandez & F Garijo
 * @author Carlos Celorrio
 */
public interface ItfMotorDeReglas {
	
    public void compileRules(InputStream file) throws Exception;
 //   public void compileRules2(URL fichero) throws Exception ;
	
    public void addGlobalVariable(String name, Object object);
	
    public void fireRules();
    
    public StatefulKnowledgeSession getStatefulKnowledgeSession();
    public ItfConfigMotorDeReglas getItfConfigMotorDeReglas ();
    public void reinicializarSesion(); // conserva las reglas compiladas de la sesion anterior. eliina la sesion actual 
                                          //y crea una nueva con las mismas reglas compiladas 
//    public ItfMotorDeReglas getItfMotorDeReglas ();
    public boolean crearKbSesionConNuevasReglas(InputStream fichero,String identFicheroReglas);
    public void assertFact(Object fact);
    public void retracttFact(Object objeto);
    public void updateFact(Object objeto);
	//The next methods do not call to fireAllRules inside its code.   
    public void assertFactWithoutFireRules(Object objeto);
    public void retractFactWithoutFireRules(Object objeto);
    public void updateFactWithoutFireRules(Object objeto);
    
}
