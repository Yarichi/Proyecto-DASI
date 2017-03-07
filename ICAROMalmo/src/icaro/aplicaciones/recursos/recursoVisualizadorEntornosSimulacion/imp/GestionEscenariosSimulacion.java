/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.ItfUsoRecursoPersistenciaEntornosSimulacion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoPanelEspecifico;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.openide.util.Exceptions;

/**
 *
 * @author FGarijo
 */
public class GestionEscenariosSimulacion {
       private HashMap tablaEscenariosDefinidos;
	//arraylist que contiene los paneles visualizados
    private EscenarioSimulacionRobtsVictms infoEscenario;
//    private ItfUsoRecursoPersistenciaEntornosSimulacion itfPersistencia;
//   private LinkedList<String> listaElementosTrazables  ;
    private HashSet identsEscenarios;
    private String numRobots = "Robts_";
    private String numVictims = "Victs_";
    private String orgModelo = "Org_";
    private String  orgModeloInicial = "SinDefinir";

     public GestionEscenariosSimulacion (){
        tablaEscenariosDefinidos = new HashMap();      
        identsEscenarios = new HashSet();
     }
//     public void setItfUsoPersistencia (ItfUsoRecursoPersistenciaEntornosSimulacion persitenciaItf){
//         itfPersistencia = persitenciaItf;
//     }
//     public void obtenerEscenariosGuardados (){
//           try {
//               identsEscenarios = itfPersistencia.obtenerIdentsEscenarioSimulacion();
//           } catch (Exception ex) {
//               Exceptions.printStackTrace(ex);
//           }
//}
     public synchronized String getIdentEscenario (String orgTipo,int numRobts, int numVictm){
         String identEscenario = orgModelo+orgTipo+numRobots+numRobts+numVictims+numVictm+"_0";
//         if (identsEscenarios.isEmpty())return identEscenario+"_0";
//         int indiceEscenarioRepetido = 0;      
//         while (getIdentsEscenariosSimulacion ( ).contains(identEscenario)){
//             indiceEscenarioRepetido ++;
//             identEscenario =identEscenario+"_"+indiceEscenarioRepetido;
//         }
         return identEscenario;
     }
     public void  setIdentsEscenariosSimulacion ( HashSet setIdentsEscenarios){
         identsEscenarios = setIdentsEscenarios;
     }
      public  HashSet getIdentsEscenariosSimulacion ( ){
          return identsEscenarios;
      }
     public EscenarioSimulacionRobtsVictms crearEscenarioSimulacion(){
         EscenarioSimulacionRobtsVictms escenarioSim = new EscenarioSimulacionRobtsVictms();
         escenarioSim.setGestorEscenarios(this);
         String identEscenario = getIdentEscenario (orgModeloInicial,0, 0);
//         escenarioSim.setIdentEscenario(getIdentEscenario (orgModeloInicial,0, 0));
         escenarioSim.setIdentEscenario (identEscenario);
         return escenarioSim;
    }
    public void addEscenario(EscenarioSimulacionRobtsVictms escenario){
        tablaEscenariosDefinidos.put(escenario.getIdentEscenario(), escenario);
        identsEscenarios.add(escenario.getIdentEscenario());
    }
    public boolean existeEscenario(String identEscenario){
        return identsEscenarios.contains(identEscenario);
    }
     public void eliminarEscenario(String identEscenario){
        tablaEscenariosDefinidos.remove(identEscenario);
        identsEscenarios.remove(identEscenario);
    }

    boolean hayEscenariosCreados() {
     return ! identsEscenarios.isEmpty();
    }
}
