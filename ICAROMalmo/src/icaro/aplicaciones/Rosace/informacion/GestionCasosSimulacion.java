/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp.*;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoPanelEspecifico;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author FGarijo
 */
public class GestionCasosSimulacion {
    private HashMap tablaCasosDefinidos;
	//arraylist que contiene los paneles visualizados
    private InfoCasoSimulacion infoCaso;
//   private LinkedList<String> listaElementosTrazables  ;
    private HashSet identsCasos;
    private HashSet identsSeriesCaso;
    private String identUltimaSerie;
    private final String prefijoSerie="Serie_";
    private final String prefijoCaso="Caso_";
    private final String sufijoInicialCaso="Serie_0Caso_0";
    private int sufijoSerie;
    private int sufijoCaso;
    

     public GestionCasosSimulacion (){
        tablaCasosDefinidos = new HashMap();      
        identsCasos = new HashSet();
        identsSeriesCaso= new HashSet();
        identUltimaSerie=prefijoSerie+"0";
        sufijoSerie=0;
        sufijoCaso=0;
     }
     public synchronized String getIdentCasoSimulacion (String identEscenario){
         // Se crea el identificador del caso
         identEscenario = identEscenario.replaceFirst(".xml", "");
         String identCaso = identEscenario+sufijoInicialCaso;
         if(identsCasos.add(identCaso) ) return identCaso;
         // Cuanod ya hay series creadas  para ese escenario. Se crea un nuevo identificador para el caso, 
         //se incrementa el indice del caso y se aniade a la ultima serie
         sufijoCaso++;
         identCaso = identEscenario+identUltimaSerie+prefijoCaso+sufijoCaso;
         
         return identCaso;
     }
     public synchronized String getIdentCasoEnNuevaSerie (String identEscenario){
         sufijoSerie++;sufijoCaso=0;
         identEscenario = identEscenario.replaceFirst(".xml", "");
         return identEscenario+prefijoSerie+sufijoSerie+prefijoCaso+sufijoCaso;
     }
     
     public void  setIdentsEscenariosSimulacion ( HashSet setIdentsEscenarios){
         identsCasos = setIdentsEscenarios;
     }
      public  HashSet getIdentsEscenariosSimulacion ( ){
          return identsCasos;
      }
     public InfoCasoSimulacion crearCasoSimulacion(String identEscenario){
         String identCaso = this.getIdentCasoSimulacion(identEscenario);
         InfoCasoSimulacion infoCaso = new InfoCasoSimulacion(identCaso, identEscenario);
//         infoCaso.setGestorCasos(this);
         return infoCaso;
    }
     public InfoCasoSimulacion crearCasoEnNuevaSerie(String identEscenario){
         String identCaso = this.getIdentCasoEnNuevaSerie(identEscenario);
         InfoCasoSimulacion infoCaso = new InfoCasoSimulacion(identCaso, identEscenario);
//         infoCaso.setGestorCasos(this);
         return infoCaso;
    }
    public void addInfoCasoSimulacion(InfoCasoSimulacion infoCaso){
        tablaCasosDefinidos.put(infoCaso.getidentCaso(), infoCaso);
        identsCasos.add(infoCaso.getIdentEscenario());
    }
    public boolean existeCaso(String identCaso){
        return identsCasos.contains(identCaso);
    }
     public void eliminarCaso(String identCaso){
        tablaCasosDefinidos.remove(identCaso);
        identsCasos.remove(identCaso);
    }
}
