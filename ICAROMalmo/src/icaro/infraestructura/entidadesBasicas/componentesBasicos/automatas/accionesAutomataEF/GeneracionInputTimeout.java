/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.accionesAutomataEF;

import icaro.infraestructura.entidadesBasicas.informes.Informe;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItfUsoAutomataEFE;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FGarijo
 */
public class GeneracionInputTimeout extends Thread {

	protected long milis;
        protected boolean trazar = false;

    /**
	 * Indica cuando debe dejar de monitorizar
	 * @uml.property  name="finalizar"
	 */
    protected boolean finalizar;

    /**
	 * Agente reactivo al que se pasan los eventos de monitorizacion
	 * @uml.property  name="agente"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
     protected ItfUsoAgenteReactivo itfUsoAgenteReceptor;

     /**
	 * Evento a producir
	 * @uml.property  name="evento"
	 */
     protected Informe informeAGenerar;
     protected  ItfUsoRecursoTrazas trazas;

    
     public GeneracionInputTimeout(long milis, ItfUsoAgenteReactivo itfusoAfteRecptor, Informe informeAGenerar) {
      super("Timeout "+informeAGenerar.getidentEntidadEmisora());
      this.milis= milis;
      this.finalizar= false;
      this.itfUsoAgenteReceptor = itfusoAfteRecptor;
      this.setDaemon(true);
      this.informeAGenerar = informeAGenerar;
    }
     public GeneracionInputTimeout(long milis, ItfUsoAgenteReactivo itfusoAfteRecptor, Informe informeAGenerar,boolean traza) {
      super("Timeout "+informeAGenerar.getidentEntidadEmisora());
      this.milis= milis;
      this.finalizar= false;
      this.itfUsoAgenteReceptor = itfusoAfteRecptor;
      this.setDaemon(true);
      this.informeAGenerar = informeAGenerar;
      this.trazar = traza;
    }

    /**
     * Termina la monitorizacion
     */
    public void finalizar() {
	this.finalizar= true;
    }

    
    @Override
    public void run() {
        // Duerme lo especificado

    	Calendar calendario = Calendar.getInstance();
    	int hora, minutos, segundos;
    	long lctm1=0, lctm2, lctm12;
    	
        try {
        	
     //     if (configDebugging.DepuracionConsola.equals("No")){
            if (trazar){
               if(trazas == null)trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
//              try{
//                  trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
//          		           NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
        	        	
                  hora = calendario.get(Calendar.HOUR);
                  minutos = calendario.get(Calendar.MINUTE);
                  segundos = calendario.get(Calendar.SECOND);
                  
                  lctm1 = System.currentTimeMillis();
                  
                  trazas.aceptaNuevaTraza(new InfoTraza("InputTimeout" + informeAGenerar.getidentEntidadEmisora(),
        		           "........................   InformeTimeout (objetivo->" + informeAGenerar.getReferenciaContexto() +
      		               " , conteido ->" + informeAGenerar.getContenidoInforme() +         		           
        		           ")..... antes de hacer el sleep.....Hora->" + hora + 
        		           " , Minuto->" + minutos + " , segundos->" + segundos + 
        		           " ... milisegundos desde 1 de enero de 1970 UTC->" + lctm1,
        		           InfoTraza.NivelTraza.debug));
//                 }
//               catch (Exception ex) {}
               }
//          
          Thread.sleep(this.milis);
                              
        //  if (configDebugging.DepuracionConsola.equals("No")){
            if (trazar){
                if(trazas == null)trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
              calendario = Calendar.getInstance();
        	  //int hora, minutos, segundos;

//        	  ItfUsoRecursoTrazas trazas;
          
//              try{
//                   trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
//          		             NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);

                   hora = calendario.get(Calendar.HOUR);
                   minutos = calendario.get(Calendar.MINUTE);
                   segundos = calendario.get(Calendar.SECOND);

                   lctm2 = System.currentTimeMillis();
                   lctm12 = lctm2 - lctm1;  
                   
                   trazas.aceptaNuevaTraza(new InfoTraza("InputTimeout"+informeAGenerar.getidentEntidadEmisora(),
        		             "........................   InformeTimeout  (ref Contexto->" + informeAGenerar.getReferenciaContexto() + 
        		             " , contenido->" + informeAGenerar.getContenidoInforme() + 
        		             ")..... milisegundos despues de hacer el sleep ->" + this.milis + " milisegundos" + 
        		             ".....Hora->" + hora + " , Minuto->" + minutos + " , segundos->" + segundos +         		             
          		             " ... milisegundos desde 1 de enero de 1970 UTC->" + lctm2 + " . DIFERENCIA->" + lctm12,
        		             InfoTraza.NivelTraza.debug));
                try {
                    ////                 }
                    ////              catch (Exception ex) {}    
                                        this.itfUsoAgenteReceptor.aceptaEvento(new EventoRecAgte("inputTimeout",informeAGenerar,this.getClass().getSimpleName(),null));
                } catch (RemoteException ex) {
                    Logger.getLogger(GeneracionInputTimeout.class.getName()).log(Level.SEVERE, null, ex);
                }
          }
//                 EventoRecAgte ev = new EventoRecAgte("inputTimeout",informeAGenerar,this.getClass().getSimpleName(),null);
        
        } catch (InterruptedException ex) {};
           

        // Genera un nuevo evento de input
       

      }    
      
}
