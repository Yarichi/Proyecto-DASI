/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFconGesAcciones.estadosyTransiciones;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;

/**
 *
 * @author FGarijo
 */
public class TransicionAutomataEF {
    private String identEstadoOrigen;
    private String identEstadoSiguiente;
    private Integer tipoEstadoOrigen = 2 ; // estado intermedio por defecto
    private String input;
    private Class identClaseAccion;
    private String identMetodoAccion;
    private Integer tipoTransicion;
    
    /**
     * Crea una transición a partir de los datos extraidos del fichero XML
     * @param idEstadoOrigen 
     * @param input  identificador del input. En el caso de que se procesen objetos input debe indicar el nombre de la clase 
     *               a la que pertenece el input
     * @param idEstadoSiguiente
     * @param idAccion  Nombre de la acción definida en el automata , puede ser null o "vacia"
     * @param modalidadAccion  definida en el automata puede ser ( bloqueante o paralela )
     */
    public  TransicionAutomataEF (String input,
           Class idAccionClass ,String idEstadoSiguiente, String modalidadAccion){
       this.identEstadoOrigen =  null;
       this.tipoEstadoOrigen = 2;
       this.input = input;
       this.identClaseAccion = idAccionClass;
       this.identEstadoSiguiente=idEstadoSiguiente;
       if (identClaseAccion == null )
           this.tipoTransicion =NombresPredefinidos.AUTOMATA_EF_TIPO_TRANSICION_SIN_ACCION;
       else if (modalidadAccion.equals(NombresPredefinidos.NOMBRE_MODO_CONCURRENTE_AUTOMATA_EF_SIN_ACCION))
                this.tipoTransicion =NombresPredefinidos.AUTOMATA_EF_TIPO_TRANSICION_ACCION_THREAD;
            else this.tipoTransicion =NombresPredefinidos.AUTOMATA_EF_TIPO_TRANSICION_ACCION_BLOQ;
}
    public  TransicionAutomataEF (String input,
           Class idAccion,String idMetodoClase,String idEstadoSiguiente, String modalidadAccion){
       this.identEstadoOrigen =  null; 
       this.tipoEstadoOrigen = 2;
       this.input = input;
       this.identClaseAccion = idAccion;
       this.identMetodoAccion = idMetodoClase;
       this.identEstadoSiguiente=idEstadoSiguiente;
       if (idAccion == null )
           this.tipoTransicion =NombresPredefinidos.AUTOMATA_EF_TIPO_TRANSICION_SIN_ACCION;
       else if (modalidadAccion.equals(NombresPredefinidos.AUTOMATA_EF_NOMBRE_MODO_CONCURRENTE))
                this.tipoTransicion =NombresPredefinidos.AUTOMATA_EF_TIPO_TRANSICION_METODO_AS_CONCURR;
            else this.tipoTransicion =NombresPredefinidos.AUTOMATA_EF_TIPO_TRANSICION_METODO_AS_BLOQ;
}
   
    
    /**
     * 
     * @return
     */
    public String  getidentEstadoOrigen ( ){
       return this.identEstadoOrigen;
   }
    public void setidentEstadoOrigen ( String idEstadoOrigen){
       this.identEstadoOrigen= idEstadoOrigen;
   }
    /**
     *
     * @return
     */
    public String  getidentEstadoSiguiente ( ){
       return this.identEstadoSiguiente;
   }
    /**
     *
     * @param tipoEst puede ser estado intermedio o estado final . Habría que controlar el tipo
     */
    public void setTipoEstadoOrigen ( Integer tipoEst){
       this.tipoEstadoOrigen= tipoEst;
   }
    /**
     *
     * @return
     */
    public Integer  getTipoEstadoOrigen ( ){
       return this.tipoEstadoOrigen;
   }
    
   public String  getInput ( ){
       return this.input;
   }
    /**
     *
     * @param input
     */
    public void setIdentMetodoAccion ( String identMetodo){
       this.identMetodoAccion= identMetodo;
   }
    public String  getIdentMetodoAccion ( ){
       return this.identMetodoAccion;
   }
    /**
     *
     * @param input
     */
    public void setInput ( String input){
       this.input= input;
   }
  
//   public void setidentAccion ( String idAccion, String){
//      
//       this.identAccion= idAccion;
//   }
    /**
     *
     * @return
     */
    public Class  getClaseAccion ( ){
       return this.identClaseAccion;
   }
    /**
     *
     * @return Los tipos de transición estan definidos como Integer en NombresPredefinidos
     */
    public Integer  getTipoTransicion ( ){
       return this.tipoTransicion;
   }
}
