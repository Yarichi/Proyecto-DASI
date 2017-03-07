/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.factoriaEInterfacesPrObj.ItfProcesadorObjetivos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.util.Calendar;

/**
 *
 * @author FGarijo
 */
public class Temporizador extends Thread {

	protected long milis;
        protected boolean trazar = false;

    /**
	 * Indica cuando debe dejar de monitorizar
	 * @uml.property  name="finalizar"
	 */
    protected boolean finalizar;

    
     protected ItfProcesadorObjetivos itfEnvioHechos;

     /**
	 * Informe a producir
	 */
     protected Informe informeAGenerar;

    
     public Temporizador(long milis, ItfProcesadorObjetivos envioHechosItf, Informe informeAGenerar) {
      super("Timeout "+informeAGenerar.getidentEntidadEmisora());
      this.milis= milis;
      this.finalizar= false;
      this.itfEnvioHechos = envioHechosItf;
      this.setDaemon(true);
      this.informeAGenerar = informeAGenerar;
    }
     public Temporizador(long milis, ItfProcesadorObjetivos envioHechosItf, InformeDeTarea informeAGenerar,boolean traza) {
      super("Timeout "+informeAGenerar.getidentEntidadEmisora());
      this.milis= milis;
      this.finalizar= false;
      this.itfEnvioHechos = envioHechosItf;
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
              ItfUsoRecursoTrazas trazas;
              try{
                  trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
          		           NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
        	        	
                  hora = calendario.get(Calendar.HOUR);
                  minutos = calendario.get(Calendar.MINUTE);
                  segundos = calendario.get(Calendar.SECOND);
                  
                  lctm1 = System.currentTimeMillis();
                  
                  trazas.aceptaNuevaTraza(new InfoTraza("RulesDebuger" + informeAGenerar.getidentEntidadEmisora(),
        		           "........................   InformeTimeout (objetivo->" + informeAGenerar.getReferenciaContexto() +
      		               " , conteido ->" + informeAGenerar.getContenidoInforme() +         		           
        		           ")..... antes de hacer el sleep.....Hora->" + hora + 
        		           " , Minuto->" + minutos + " , segundos->" + segundos + 
        		           " ... milisegundos desde 1 de enero de 1970 UTC->" + lctm1,
        		           InfoTraza.NivelTraza.debug));
                 }
               catch (Exception ex) {}
               }
          
          Thread.sleep(this.milis);
                              
        //  if (configDebugging.DepuracionConsola.equals("No")){
            if (trazar){
              calendario = Calendar.getInstance();
        	  //int hora, minutos, segundos;

        	  ItfUsoRecursoTrazas trazas;
          
              try{
                   trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
          		             NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);

                   hora = calendario.get(Calendar.HOUR);
                   minutos = calendario.get(Calendar.MINUTE);
                   segundos = calendario.get(Calendar.SECOND);

                   lctm2 = System.currentTimeMillis();
                   lctm12 = lctm2 - lctm1;  
                   
                   trazas.aceptaNuevaTraza(new InfoTraza("RulesDebuger"+informeAGenerar.getidentEntidadEmisora(),
        		             "........................   InformeTimeout  (ref Contexto->" + informeAGenerar.getReferenciaContexto() + 
        		             " , contenido->" + informeAGenerar.getContenidoInforme() + 
        		             ")..... milisegundos despues de hacer el sleep ->" + this.milis + " milisegundos" + 
        		             ".....Hora->" + hora + " , Minuto->" + minutos + " , segundos->" + segundos +         		             
          		             " ... milisegundos desde 1 de enero de 1970 UTC->" + lctm2 + " . DIFERENCIA->" + lctm12,
        		             InfoTraza.NivelTraza.debug));
                 }
              catch (Exception ex) {}                            
          }
                    
        } catch (InterruptedException ex) {}

        // Genera un nuevo evento de input
        this.itfEnvioHechos.insertarHecho(informeAGenerar);

      }    
      
}
