/*
 *  
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFconGesAcciones.estadosyTransiciones;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;



/**
 *  Description of the Class
 *
 *@author     Francisco J Garijo
 *@created    11 de febrero de 2014
 *@modified   
 */
public class TablaEstadosAutomataEFinputObjts implements Cloneable, Serializable{

	/**
	 * Tabla que almacena si los estados son iniciales / finales / etc, indexa por estado
	 * @uml.property  name="clasificacionEstados"
	 * @uml.associationEnd  qualifier="identificador:java.lang.String java.lang.Integer"
         * Los inputs deben ser objetos de clases conocidas, en los atributos y métodos del input se pueden pasar valores que
         * utilizan las acciones definidas en la tabla
	 */
	private HashMap<String,ParTipoEstadoConjInputs> infoEstados = new HashMap<String,ParTipoEstadoConjInputs>();

	/**
	 * El estado inicial
	 * @uml.property  name="identificadorEstadoInicial"
	 */
//	private String identificadorEstadoInicial = "";

	/**
	 * Tabla de tablas que almacena las tablas input/estado siguiente y las indexa por estado
	 * @uml.property  name="inputsDeEstados"
	 * @uml.associationEnd  qualifier="input:java.lang.String java.lang.String"
	 */
//	private Hashtable<String,Hashtable<String,String>> inputsDeEstados = new Hashtable<String,Hashtable<String,String>>();
        private Hashtable<String,TransicionAutomataEF> tablaTransicionFromEstadoInput = new Hashtable<String,TransicionAutomataEF>();
        private Set<String> identsInputsEstado ;
	/**
	 *  Marca el tipo de estados finales
	 */
	public final static int TIPO_DE_ESTADO_FINAL = 3;

	/**
	 *  Marca el tipo del estado inicial
	 */
	public final static int TIPO_DE_ESTADO_INICIAL = 0;
	/**
	 *  Marca el tipo de estados intermedios
	 */
	public final static int TIPO_DE_ESTADO_INTERMEDIO = 2;
        
        private String InfoErrores = "Errores en la construccion de la tabla";
        private boolean hayErrores = false;
	/**
	 *  Constructor
	 */
	public TablaEstadosAutomataEFinputObjts() { }


	/**
	 *  Devuelve el estado inicial del autmata
	 *
	 *@return    Devuelve el estado inicial del automata
	 */
	public String dameEstadoInicial()
	{
		return NombresPredefinidos.ESTADO_INICIAL;
	}
        public Boolean esEstadoValido(String identEstado)
	{
		return infoEstados.containsKey(identEstado);
	}

	/**
	 *  Devuelve el estado siguiente al estado Qi con el input i PRE: El
	 *  estado actual no es final. El input pertenece a los aceptados por ese
	 *  estado
	 *
	 *@param  estadoActual  Estado en el que se est
	 *@param  input         Input que se ha recibido
	 *@return               estado siguiente
	 */
	public String dameEstadoSiguiente(String estadoActual, String input)
	{
		
		return tablaTransicionFromEstadoInput.get(estadoActual+input).getidentEstadoSiguiente();
	}


	/**
	 *  Comprueba si el estado dado es final
	 *
	 *@param  estado  Estado a evaluar
	 *@return         Devuelve si cuando el estado es final, no e.o.c.
	 */
	public boolean esEstadoFinal(String estado)
	{
		return ( ((infoEstados.get(estado)).getTipoEstado()) == NombresPredefinidos.TIPO_ESTADO_FINAL_AUTOMATA_EF);
	}



	/**
	 *  Determina si el input dado es uno de los inputs que se esperan en el estado
	 *  dado
	 *
	 *@param  estado  Estado a evaluar
	 *@param  input   Input que se desea comprobar
	 *@return         Dice si es vlido o no
	 */
	public boolean esInputValidoDeEstado(String estado, String input)
	{
		
		return (tablaTransicionFromEstadoInput.containsKey(estado+input));
	}

	/**
	 *  Aade un nuevo estado a la tabla
	 *
	 *@param  identificador  Nombre del estado
	 *@param  tipo           Tipo del estado (ver enumerados de esta clase)
	 */
	public void putEstado(String input, int tipoEstado)
	{
		// clasificar el estado
//		infoEstados.put(identificador, new ParTipoEstadoConjEstados(tipo, identsInputsEstado));
                if (infoEstados.containsKey(input) ){
                    
                } else {
                   identsInputsEstado = new TreeSet<>();
//                   identsInputsEstado.add(input);
                   if (tipoEstado<1 || tipoEstado >=3 ){
                    tipoEstado = TIPO_DE_ESTADO_INTERMEDIO; // sacar un mensaje indicando que se ha corregido
                   }
                  infoEstados.put(input, new ParTipoEstadoConjInputs(tipoEstado, identsInputsEstado));
            }
	//	inputsDeEstados.put(identificador, new Hashtable<String,String>());

		// aceleramos la recuperacin del estado inicial
		
	}

	/**
	 *  Aade una nueva transicion de estados a la tabla
	 *
	 *@param  estado           Estado desde el que parte la transicin
	 *@param  input            Input que activa la transicin
	 *@param  estadoSiguiente  Estado al que se pasa tras ejecutar la transicin
	 */
	public Boolean addTransicion(String estadoOrigen,Integer tipoEstado,
                                  TransicionAutomataEF transicion) {

		// incorpora una transicion al estado origen
//            String input = transicion.getInput();
           if ( estadoOrigen==null ){
                // mensaje de error el estado origen no puede ser nulo
                return false; 
            }
            if ( transicion==null ){
                // mensaje de error la transicion asociada a un input no puede ser vacia
                return false; 
            }
            String input= transicion.getInput();
            String claveTablaEstados = estadoOrigen+input;
//            if (infoEstados.containsKey(estadoOrigen)){
//                identsInputsEstado = infoEstados.get(estadoOrigen).getIdentsInputsEstado();
//                
//                if (identsInputsEstado.contains(input) )return false;// sacar un mensaje de error o de aviso 
//                else {
////                    TransicionAutomataEF transicion = new TransicionAutomataEF(estadoOrigen, input, estadoSiguiente, accion, modo);
//                    tablaTransicionFromEstadoInput.put(estadoOrigen+input,new TransicionAutomataEF(estadoOrigen,input,estadoSiguiente,accion,modo));
//                    identsInputsEstado.add(input);
//                }
            if (!tablaTransicionFromEstadoInput.containsKey(claveTablaEstados)){
                identsInputsEstado = infoEstados.get(estadoOrigen).getIdentsInputsEstado();
                identsInputsEstado.add(input);
                tablaTransicionFromEstadoInput.put(estadoOrigen+input,transicion);     
                infoEstados.put(estadoOrigen, new ParTipoEstadoConjInputs(tipoEstado,identsInputsEstado));
            }
            
//            else { // no existe el estado origen => se crea 
//                identsInputsEstado = new TreeSet<>();
//                identsInputsEstado.add(input);
//                infoEstados.put(input, new ParTipoEstadoConjEstados(TIPO_DE_ESTADO_INTERMEDIO, identsInputsEstado));
//                tablaTransicionFromEstadoInput.put(estadoOrigen+input, new TransicionAutomataEF(estadoOrigen,input,estadoSiguiente,accion,modo));
//            }
            return true;
	}
        public TransicionAutomataEF getTransicion (String estadoMasInput){
            return tablaTransicionFromEstadoInput.get(estadoMasInput);
        }
        public Boolean putTransicion(String estadoOrigen, TransicionAutomataEF transicion) {

		// incorpora una transicion al estado origen
            String input = transicion.getInput();
            Integer tipoEstado;
            if (infoEstados.containsKey(estadoOrigen)){
                identsInputsEstado = infoEstados.get(estadoOrigen).getIdentsInputsEstado();
                
                if (identsInputsEstado.contains(input) )return false;// sacar un mensaje de error o de aviso 
                else {
//                    TransicionAutomataEF transicion = new TransicionAutomataEF(estadoOrigen, input, estadoSiguiente, accion, modo);
//                    tablaTransicionFromEstadoInput.put(estadoOrigen+input,transicion);
                    identsInputsEstado.add(input);
                    tipoEstado= infoEstados.get(estadoOrigen).tipoEstado;
                }
            }
            else { // no existe el estado origen => se saca mensaje de error y se crea 
                identsInputsEstado = new TreeSet<>();
                identsInputsEstado.add(input);
                tipoEstado = TIPO_DE_ESTADO_INTERMEDIO;
//                if (tipoEstado<1 || tipoEstado >=3 ){
//                    tipoEstado = TIPO_DE_ESTADO_INTERMEDIO; // sacar un mensaje indicando que se ha corregido
//                }
                
                infoEstados.put(input, new ParTipoEstadoConjInputs(TIPO_DE_ESTADO_INTERMEDIO, identsInputsEstado));
//                transicion.setidentEstadoOrigen(estadoOrigen);
//              transicion.setTipoEstadoOrigen(tipoEstado);
//                tablaTransicionFromEstadoInput.put(estadoOrigen+input, transicion);              
            }
            transicion.setidentEstadoOrigen(estadoOrigen);
            transicion.setTipoEstadoOrigen(tipoEstado);
            tablaTransicionFromEstadoInput.put(estadoOrigen+input, transicion);
            return true;
	}


	/**
	 *  Anyade la transicin indicada como parmetro a todos los estados del
	 *  autmata PRE: El autmata debe estar completamente creado ( todos los
	 *  estados aadidos ) NOTA: En caso de existir inputs repetidos, se dejar
	 *  intacto el input ya existente
	 *
	 *@param  input      input de la transicion universal
	 *@param  estadoSig  estado al que llegamos tras la transicin universal
	 */
	public void putTransicionUniversal( TransicionAutomataEF transicion){
            if (transicion != null){
            String input = transicion.getInput();
            for (String estadoPivote : (infoEstados.keySet())) {
                if ( esEstadoFinal(estadoPivote)){} // no se anyade la transicion y se sigue el proceso
                else if ( esInputValidoDeEstado(estadoPivote, input)){
                              hayErrores = true; // damos preferencia al input definido y no se incluye la transicion
                              InfoErrores+= "\n" + " Input repetido "+ input + " En el estado :"+ estadoPivote ;
                }else{
                    transicion.setidentEstadoOrigen(estadoPivote);
                    tablaTransicionFromEstadoInput.put(estadoPivote+input,transicion );
                }
            }
            }
	}
	/**
	 *  Expresa la tabla en texto
	 *
	 *@return    Texto con la tabla de estados
	 */
        @Override
	public String toString()
	{
		String dev = "LEYENDA:   Estado: input -> estado siguiente";
		dev += "\n------------------------------------------------------";
//		java.util.Collection<String> claves = (java.util.TreeSet)tablaTransicionFromEstadoInput.keySet();
           
                java.util.Iterator claves = ((java.util.TreeSet)tablaTransicionFromEstadoInput.keySet()).iterator();

		String input = "";
		String estsig = "";
		String id = "";
                TransicionAutomataEF transicion =null;
		while (claves.hasNext())
		{
		 id = (String) claves.next();
                 transicion = tablaTransicionFromEstadoInput.get(id);
                if (transicion!=null)
                    dev += "\n" + id + ": Transicion : estado : " +transicion.getidentEstadoOrigen() + " accion : " + transicion.getClaseAccion()+ " -> "+transicion.getidentEstadoSiguiente();
                else dev += "\n" + id + ": Transicion : null";
							
			}

		return dev += "\n------------------------------------------------------";
	}
        @Override
    public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
private class  ParTipoEstadoConjInputs{
    public String identEstadoOrigen;
    public Set<String> identsInputsEstado ;
    public Integer tipoEstado = 2 ; // estado intermedio por defecto
    
    public ParTipoEstadoConjInputs (Integer tipoEstado,Set<String> idsInputEstado){
        this.tipoEstado=tipoEstado;
        this.identsInputsEstado= idsInputEstado;
    }
    public Set<String> getIdentsInputsEstado(){
        return this.identsInputsEstado;
    }
    public boolean addInput (String inputId){
         return( this.identsInputsEstado.add(inputId));
    }
    public boolean setTipoEstado(Integer tipoEstado){
        if(tipoEstado >0 & tipoEstado>=3) return true;
        else return false;
    }
    public Integer getTipoEstado(){
        return this.tipoEstado;
    }
    }
}
