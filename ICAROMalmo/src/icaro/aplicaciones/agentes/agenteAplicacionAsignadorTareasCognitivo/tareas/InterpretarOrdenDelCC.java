/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.tareas;

import icaro.aplicaciones.Rosace.informacion.OrdenCentroControl;
import icaro.aplicaciones.Rosace.informacion.Victim;
import icaro.aplicaciones.Rosace.informacion.VictimsToRescue;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.AyudarVictima;
import icaro.aplicaciones.agentes.agenteAplicacionAsignadorTareasCognitivo.objetivos.DecidirQuienVa;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.MisObjetivos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author FGarijo
 */
public class InterpretarOrdenDelCC extends TareaSincrona{
    @Override
   public void ejecutar(Object... params) {
	   try {
             trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
             MisObjetivos misObjs = (MisObjetivos) params[0];
             Objetivo objetivoEjecutantedeTarea = (Objetivo)params[1];
             OrdenCentroControl ccOrdenAyudarVictima = (OrdenCentroControl)params[2];
             VictimsToRescue victims2R = (VictimsToRescue)params[3];
             Victim victim = (Victim)ccOrdenAyudarVictima.getJustificacion();
             String identTarea = this.getIdentTarea();
             String nombreAgenteEmisor = this.getIdentAgente();
             String idVictim = victim.getName();
             this.getEnvioHechos().eliminarHechoWithoutFireRules(ccOrdenAyudarVictima);
           // verificamos que no se esta ayudando a esa victima. Comprobamos que el ident no esta en ninguno de los objetivos 
       //      if ((ccOrdenAyudarVictima.mensajeOrden.equals(VocabularioRosace.MsgOrdenCCAyudarVictima)))
        //          if((objetivoEjecutantedeTarea == null) |
        //             ((objetivoEjecutantedeTarea != null)&&((!idVictim.equals(objetivoEjecutantedeTarea.getobjectReferenceId()))&& (!misObjs.existeObjetivoConEsteIdentRef(idVictim))))){
           // se crea el objetivo y se inserta en el motor
                 if (victims2R.getvictims2Rescue().isEmpty() || victims2R.getVictimToRescue(idVictim) == null )  {   
                 AyudarVictima newAyudarVictima = new AyudarVictima (idVictim);
           //      newObjetivo.setvictimId(idVictim);
                 newAyudarVictima.setPriority(victim.getPriority());
                 victims2R.addVictimToRescue(victim);
          //      if((objetivoEjecutantedeTarea == null)) newObjetivo.setSolving(); // se comienza el proceso para intentar conseguirlo                                        
           //       Se genera un objetivo para decidir quien se hace cargo de la ayuda y lo ponemos a solving
                 DecidirQuienVa newDecision = new DecidirQuienVa(idVictim);
                 newDecision.setSolving();                 
                 this.getEnvioHechos().insertarHechoWithoutFireRules(victim);
                 this.getEnvioHechos().insertarHechoWithoutFireRules(newAyudarVictima);
                 this.getEnvioHechos().actualizarHechoWithoutFireRules(victims2R);
                 this.getEnvioHechos().insertarHecho(newDecision);
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgenteEmisor, "Se ejecuta la tarea " + this.getIdentTarea()+
                                    " Se crea el  objetivo:  "+ newAyudarVictima, InfoTraza.NivelTraza.debug));
            System.out.println("\n"+nombreAgenteEmisor +"Se ejecuta la tarea " + this.getIdentTarea()+ " Se crea el  objetivo:  "+ newAyudarVictima+"\n\n" );
             }else{
              trazas.aceptaNuevaTraza(new InfoTraza("\n" +nombreAgenteEmisor, "Se ejecuta la tarea " + this.getIdentTarea()+
                                    " Pero NO Se crea ningun   objetivo:  ", InfoTraza.NivelTraza.debug));
            System.out.println("\n"+nombreAgenteEmisor +"Se ejecuta la tarea " + this.getIdentTarea()+ " Pero NO Se crea ningun   objetivo:  "+"\n\n" ); 
             
      //       if ((ccOrdenAyudarVictima.mensajeOrden.equals(VocabularioRosace.MsgOrdenCCAyudarVictima)) &&
      //               (!idVictim.equals(objetivoEjecutantedeTarea.getobjectReferenceId()))&& (!misObjs.existeObjetivoConEsteIdentRef(idVictim))  ){
                 
            
             }
                 
             
       } catch (Exception e) {
			 e.printStackTrace();
       }
   }

}
