/*
 * FactoriaAdaptadorMotorReglasDrools.java
 *
 * Creado 18 de abril de 2007, 11:56
 *
 * Telefonica I+D Copyright 2006-2007
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.FactoriaMotorDeReglas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas.ItfMotorDeReglas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.io.InputStream;
import java.util.ArrayList;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.PackageBuilder;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;



/**
 * Drools Rule Engine Factory 
 * @author F Garijo
 * 
 */
public class FactoriaMotorDeReglasDroolsImp1 extends FactoriaMotorDeReglas {

    private ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
    private ArrayList <String> ficheroReglasCompilados = new ArrayList();
    private ArrayList<KnowledgeBuilder>  KbuildersObtenidos=new ArrayList(); 
    private KnowledgeBuilder kbuilder;
	/**
	 * Nuevas operaciones para compilar las reglas y evitar recompilarlas cuando se crean clones
	 */
   
    @Override
    public ItfMotorDeReglas crearMotorDeReglas(AgenteCognitivo agent){
   //     return new MotorDeReglasDroolsImp(agent);
   //       return new MotorDeReglasDroolsImp2(agent);
        return new MotorDeReglasDroolsImp5(agent);
    }
    
 
    @Override
    public synchronized ItfMotorDeReglas crearMotorDeReglas(AgenteCognitivo agent, InputStream reglas, String ficheroReglas){
   //     Solo se crea el motor si la compilacion de las reglas es correcta    
    //    MotorDeReglasDroolsImp5 motorDrools = new  MotorDeReglasDroolsImp5(agent);
            kbuilder = compilarReglas( agent.getIdentAgente(), reglas,ficheroReglas);
            if (kbuilder==null){
                trazas.aceptaNuevaTraza(new InfoTraza(agent.getIdentAgente(),"Motor de reglas Drools: ERROR en la compilacion de las reglas al crear el agente "  ,InfoTraza.NivelTraza.error));
                return null;
            }
            MotorDeReglasDroolsImp5 motorDrools = new  MotorDeReglasDroolsImp5(agent,this);
            motorDrools.crearSesionConConfiguracionStandard(kbuilder);
 //           KbuildersObtenidos.add(kbuilder);      
            return  motorDrools;
    }
    
    public synchronized KnowledgeBuilder compilarReglas(String agentId,InputStream fichero,String ficheroReglas) {
        // verifico que no estan ya compiladas sin errores
    //   String ficheroReglas = fichero.toString() ;
       int indiceFicheroEnArray = ficheroReglasCompilados.indexOf(ficheroReglas);
 //       if (!ficheroReglasCompilados.isEmpty()){        
           if (indiceFicheroEnArray>=0) return KbuildersObtenidos.get(indiceFicheroEnArray);
        else{ // se debe compilar
//        PackageBuilder builder = new PackageBuilder();
//      KnowledgeBuilderConfiguration kbuilderConf = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
        try {
              kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
              Resource rsc = ResourceFactory.newInputStreamResource( fichero );
              kbuilder.add( rsc,ResourceType.DRL );
              if ( kbuilder.hasErrors() ) {
                  System.out.println("Problemas con el fichero : "+ ficheroReglas);
                   System.err.println( kbuilder.getErrors().toString() );
                   throw new RuntimeException( "Unable to compile");
              }else{
              //    int ultimoIndiceInsercion = ficheroReglasCompilados.size();
                  ficheroReglasCompilados.add( ficheroReglas);
                  KbuildersObtenidos.add(kbuilder);               
                  return kbuilder;
              }
              } catch (Exception e) {
             trazas.aceptaNuevaTraza(new InfoTraza(agentId,"Motor de Reglas Drools: ERROR al compilar las reglas. " + ficheroReglas,InfoTraza.NivelTraza.error));
             e.printStackTrace(); 
        }
          return null;
           }
    }
}

                
