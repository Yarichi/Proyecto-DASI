/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFconGesAcciones.InterpreteAutomataEFconGestAcciones;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItfUsoAutomataEFE;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp.AutomataEFEImp;
import icaro.infraestructura.patronAgenteReactivo.control.GestorAccionesAgteReactivoImp;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ProcesadorInfoReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author Francisco J Garijo
 */
public class ProcesadorInfoReactivoAutInObjImp0 extends ProcesadorInfoReactivoAbstracto implements Serializable{

	/**
	 * @uml.property  name="dEBUG"
	 */
//	private boolean DEBUG = false;
	/**
	 * autmata que describe el control
	 * @uml.property  name="automataControl"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
//	private InterpreteAutomataEFconGestAcciones automataControl;
    private ItfUsoAutomataEFE automataControl;
    private GestorAccionesAgteReactivoImp gestorAcciones;
    private boolean resultadoProcesoInput;
//
//    private AccionesSemanticasAgenteReactivo accionesSemanticasAgenteCreado;
	/**
	 * estado interno del componente control
	 * @uml.property  name="estado"
	 */
//	private int estado = InterfazGestion.ESTADO_CREADO;


	/**
	 * Nombre del componente a efectos de traza
     * @param interpAutom
     * @param gestAcciones
     * @param implItfsagente
     * @throws java.rmi.RemoteException
	 * @uml.property  name="nombre"
	 */
//	private String nombre;
//        private  AgenteReactivoAbstracto agente;
	
	public ProcesadorInfoReactivoAutInObjImp0( ItfUsoAutomataEFE interpAutom,GestorAccionesAgteReactivoImp gestAcciones,
			 AgenteReactivoAbstracto implItfsagente) throws RemoteException
	{
//            super("Agente reactivo "+implItfsagente.getIdentAgente());
            super("Agente reactivo ");
              identAgte =implItfsagente.getIdentAgente();
            automataControl = interpAutom;
//                accionesSemanticasAgenteCreado = accionesSemanticasEspecificas;
             //   percepcionConsumidor = percConsumidor;
		//percepcionProductor = percProductor;
                gestorAcciones = gestAcciones;
		agente = implItfsagente;
//                this.arranca();
	}

@Override
        public synchronized void procesarInfoControlAgteReactivo (Object infoParaProcesar  ) {
    
     NombresPredefinidos.RECURSO_TRAZAS_OBJ.trazar(identAgte,"Se procesa el  input " + infoParaProcesar, InfoTraza.NivelTraza.debug);
      if(this.estado == InterfazGestion.ESTADO_ACTIVO)
          if( infoParaProcesar instanceof InfoContEvtMsgAgteReactivo){
             InfoContEvtMsgAgteReactivo infoParaAutomata = (InfoContEvtMsgAgteReactivo) infoParaProcesar;
         resultadoProcesoInput =   automataControl.procesaInput(infoParaAutomata.getInput(),infoParaAutomata.getvaloresParametrosAccion());
          }else{
//              System.out.println(nombre + ": El input debe ser de  clase InfoContEvtMsgAgteReactivo  y el objeto es clase" + infoParaProcesar.getClass()
//                      + " Cambiar el contenido del evento");
          resultadoProcesoInput =  automataControl.procesaInput(infoParaProcesar);
          }
      if(!resultadoProcesoInput){
       NombresPredefinidos.RECURSO_TRAZAS_OBJ.trazar(identAgte,"No hay transicion asociada al input " + infoParaProcesar, InfoTraza.NivelTraza.debug);
      
      }
}
        @Override
        public synchronized String getEstadoControlAgenteReactivo ( ){

        return automataControl.getEstadoAutomata();
}
    public void setDebug(boolean d) {
        this.DEBUG = d;
    }

    public boolean getDebug() {
        return this.DEBUG;
    }

    @Override
    public synchronized  void procesarInput (Object input, Object ...paramsAccion  ){
        NombresPredefinidos.RECURSO_TRAZAS_OBJ.trazar(identAgte,"Se procesa el  input " + input, InfoTraza.NivelTraza.debug);
        String inputAutomata ;
            Object paramPosicion0 = null;
            Object[] valoresParametrosAccion;
            int posparametros =0;
//           Object[] parametroAccion = new Object[1];
           if (input==null) {
               NombresPredefinidos.RECURSO_TRAZAS_OBJ
                       .aceptaNuevaTraza(new InfoTraza(this.getClass().getSimpleName(),
					"ERROR: El input a procesar no puede ser null ", InfoTraza.NivelTraza.error));
           }
            if(!(input instanceof String)){
                inputAutomata = input.getClass().getSimpleName();          
                paramPosicion0= input;
                posparametros++;
            }else 
                inputAutomata = (String)input;
//                valoresParametrosAccion [0]= null;
           if ( paramsAccion == null){
                valoresParametrosAccion = new Object[1];
                valoresParametrosAccion [0]= null;
           }
            else if((paramsAccion.length==1 && paramsAccion[0]==null)){
                valoresParametrosAccion = new Object[1];
                valoresParametrosAccion [0]= null;
//                return ejecutarTransicion(inputAutomata, valoresParametrosAccion); 
//               numParametros = 1;
            }else {
                 valoresParametrosAccion= new Object[(paramsAccion).length+posparametros];
                    if(posparametros==1)valoresParametrosAccion[0]=paramPosicion0;
                     for (Object param: paramsAccion){
                        valoresParametrosAccion[posparametros]=param;
                        posparametros++;
                    }
            }
        automataControl.procesaInput(inputAutomata, valoresParametrosAccion);
}
    @Override
 public   void inicializarInfoGestorAcciones(String identAgte,ItfProductorPercepcion itfEvtosInternos ){
     if(gestorAcciones !=null){
         percepcionProductor= itfEvtosInternos;
         gestorAcciones.inicializarInfoAccionesAgteReactivo(identAgte, percepcionProductor, this);
     }
 }
    public synchronized int getEstado() {
        return estado;
    }

    /**
	 * @param e
	 * @uml.property  name="estado"
	 */
    public synchronized void setEstado(int e) {
        this.estado = e;
    }

    /**
	 * @return
	 * @uml.property  name="control"
	 */
       

//    @Override
//    public String toString() {
//        return nombre;
//    }

    //@Override
    /**
     *  Establece el gestor a reportar
     *  @param nombreGestor nombre del gestor a reportar
     *  @param listaEventos lista de posibles eventos que le puede enviar.
     *
     *  El gestionador obtendr las interfaces del gestor a partir del repositorio de interfaces y podr validar la informacin.
     *
     */
}
