package icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp;


import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.AdaptadorRegRMI;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;




public class RepositorioInterfacesImpGen extends ClaseGeneradoraRepositorioInterfaces {

	  // Tabla con todos los interfaces
	  private Map<String,Object> repositorio;

	  // Depuracion del componente
	  public final static boolean DEBUG=true;
          public boolean recursoConfiguracionCreado= false;
          public ItfUsoConfiguracion configuracion;
	  /**
	   * Constructor. Registra el repositorio y lo deja accesible de forma remota.
	 */ 
	  public RepositorioInterfacesImpGen() throws RemoteException {
		  super("RepositorioInterfaces");
		  repositorio = new HashMap<String, Object>();
                  trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
	  }
	  


	  /**
	   * Almacena una interfaz en el repositorio
	   * En caso de existir el nombre previamente, se actualiza la referencia
	   * @param nombre
	   * @param interfaz
	   */
    @Override
	  public synchronized void registrarInterfaz(String nombre, Object interfaz) 
	  {
	    this.repositorio.put(nombre,interfaz);
	    if (DEBUG) System.out.println("Registrado en repositorio nombre="+nombre+", interfaz="+interfaz);
	  }

	  /**
	   * Recupera una interfaz del repositorio
	   * @param nombre  Nombre de la interfaz a recuperar
	   * @return  Interfaz asociada a ese nombre o null si no se ha encontrado ese nombre
	   */
    @Override
	  public synchronized Object obtenerInterfaz(String nombre) {
	    if ( !this.repositorio.containsKey(nombre) ){
                // Extraer el identificador de la entidad 
            //    String identEntity;
                Object itfRemota = null;
                if (nombre.startsWith(NombresPredefinidos.ITF_USO)){
                  itfRemota = obtenerItfEntidadRemota(nombre,NombresPredefinidos.ITF_USO );
                }else if (nombre.startsWith(NombresPredefinidos.ITF_GESTION)){
                        itfRemota = obtenerItfEntidadRemota(nombre,NombresPredefinidos.ITF_GESTION ); 
                      }
                if (itfRemota != null){
                    registrarInterfaz( nombre, itfRemota);
                    return itfRemota;
                }
                else {
                  if ( trazas !=null) 
                    this.trazas.trazar(this.getId(), " No se pudo recuperar "+nombre+" porque no existe ningn objeto con ese nombre", InfoTraza.NivelTraza.error);
	      System.err.println("RepositorioInterfaces: No se pudo recuperar "+nombre+" porque no existe ningn objeto con ese nombre");
	      System.err.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> "+this.listarNombresInterfacesRegistradas());
	      System.out.println("RepositorioInterfaces: No se pudo recuperar "+nombre+" porque no existe ningn objeto con ese nombre");
	      System.out.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> "+this.listarNombresInterfacesRegistradas());
              return null;
                }
            }
	    return this.repositorio.get(nombre);
	  }

    @Override
	public synchronized Object obtenerInterfazUso(String nombre) throws Exception {
            String identInterfaz =  NombresPredefinidos.ITF_USO + nombre;
              if ( !this.repositorio.containsKey(identInterfaz) ){
               Object itfRemota = null;
               itfRemota = obtenerItfEntidadRemota(nombre,NombresPredefinidos.ITF_USO );
               if (itfRemota != null){
                    registrarInterfaz( nombre, itfRemota);
                    return itfRemota;
               }
                else {
                this.trazas.trazar(this.getId(), " No se pudo recuperar "+nombre+" porque no existe ningn objeto con ese nombre", InfoTraza.NivelTraza.error);
                System.err.println("RepositorioInterfaces: No se pudo recuperar "+identInterfaz+" porque no existe ningn objeto con ese nombre");
                System.err.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> "+this.listarNombresInterfacesRegistradas());
                System.out.println("RepositorioInterfaces: No se pudo recuperar "+identInterfaz+" porque no existe ningn objeto con ese nombre");
                System.out.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> "+this.listarNombresInterfacesRegistradas());
                return null;
                }
              }
	    return this.repositorio.get(identInterfaz);
         }


    @Override
        public synchronized Object obtenerInterfazGestion(String nombre) throws Exception {
            String identInterfaz =  NombresPredefinidos.ITF_GESTION + nombre;
              if ( !this.repositorio.containsKey(identInterfaz) ){
               Object itfRemota = null;
               itfRemota = obtenerItfEntidadRemota(nombre,NombresPredefinidos.ITF_GESTION );
               if (itfRemota != null){
                    registrarInterfaz( nombre, itfRemota);
                    return itfRemota;
               }
                else {
                this.trazas.trazar(this.getId(), " No se pudo recuperar "+nombre+" porque no existe ningn objeto con ese nombre", InfoTraza.NivelTraza.error);
                System.err.println("RepositorioInterfaces: No se pudo recuperar "+identInterfaz+" porque no existe ningn objeto con ese nombre");
                System.err.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> "+this.listarNombresInterfacesRegistradas());
                System.out.println("RepositorioInterfaces: No se pudo recuperar "+identInterfaz+" porque no existe ningn objeto con ese nombre");
                System.out.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> "+this.listarNombresInterfacesRegistradas());
                return null;
                }
              }
	    return this.repositorio.get(identInterfaz);
         }
	  /**
	   * Cancela el registro de una interfaz en el repositorio
	   * @param nombre Nombre de la interfaz a eliminar del repositorio
	   * @throws RemoteException
	   */
    @Override
	  public synchronized void eliminarRegistroInterfaz(String nombre)
	  {

	    if (this.repositorio.containsKey(nombre))
	    {
	      this.repositorio.remove(nombre);
	      if(DEBUG) System.out.println("Se elimino la referencia a "+nombre+" del repositorio de interfaces.");
	    }
	    else
	      if(DEBUG) System.out.println("Se intento eliminar la referencia "+nombre+" del repositorio, pero no estaba definida.");
	  }
	  
	  
	  /**
	   * Devuelve una lista con los nombres de todos los interfaces registrados
	   * @return
	   * @throws RemoteException
	   */
    @Override
	  public String listarNombresInterfacesRegistradas()
	  {
	    String ret="";
	    Set<String> enume = this.repositorio.keySet();
            Iterator<String> iter = enume.iterator();
	    while (iter.hasNext()) {
	      Object item = iter.next();
	      ret += item+" ";
	    }
	    return ret;
	  }

          /**
	   * Devuelve un ArrayList con los nombres de todos los interfaces registrados
	   * @return
	   * @throws RemoteException
	   */
    @Override
          public ArrayList nombresInterfacesRegistradas() throws Exception {
            ArrayList ret = new ArrayList();
	    Set<String> enume = this.repositorio.keySet();

	   
	    return new ArrayList(enume);
          }

    @Override
       public Boolean estaRegistradoEsteNombre(String nombreEntidad)
	  {
         return  this.repositorio.containsKey(nombreEntidad);
      }

    @Override
      public ArrayList nombresAgentesAplicacionRegistrados() throws Exception {
            ArrayList ret = new ArrayList();
	    Set<String> enume = this.repositorio.keySet();
            String identTipoAgenteReActivo = NombresPredefinidos.NOMBRE_ENTIDAD_AGENTE + NombresPredefinidos.TIPO_REACTIVO;
            String identTipoAgenteCognitivo = NombresPredefinidos.NOMBRE_ENTIDAD_AGENTE + NombresPredefinidos.TIPO_COGNITIVO;
            Iterator<String> iter = enume.iterator();
	    while (iter.hasNext()) {
	     Object item = iter.next();
             String itf = this.repositorio.get(item).toString();
             String identItf = item.toString();
             if (((itf.startsWith(identTipoAgenteReActivo))||(itf.startsWith(identTipoAgenteCognitivo))) && identItf.contains(NombresPredefinidos.ITF_USO)  ){
                String identAgente =  identItf.replaceFirst (NombresPredefinidos.ITF_USO, "");
               if (!( identAgente.contains("Gestor")) )
                ret.add(identAgente);     
                }
                }
            return ret;
          }
    @Override
public ArrayList nombresRecursosRegistrados() throws Exception {
            ArrayList ret = new ArrayList();
	    Set<String> enume = this.repositorio.keySet();
            Iterator<String> iter = enume.iterator();
	    while (iter.hasNext()) {
	      String item = iter.next().toString();
              if (item.contains("Recurso") ){
	      ret.add(item);
                }
              }
	    return ret;
          }


    @Override
       public Boolean estaRegistradoEsteAgente(String nombreAgente)
// Verificamos que la entidad registrada es un agente
      {
               if (! this.repositorio.containsKey(nombreAgente)) {
                                                return false;
                  }else
                    {
                    String repoContenido = this.repositorio.get(nombreAgente).toString();
                    if (repoContenido.matches("patronAgente") ){
                            return true;
                        }else
                             {
                                return false;
                         }
                }
}
    @Override
public Boolean estaRegistradoEsteRecurso(String nombreRecurso)
// Verificamos que la entidad registrada es un recurso
// La verificacion es bastante ligera y se hace comprabando que el nombre tiene una interfaz asociada y
// que en el nombre de la interfaz aparece la cadena recurso. Esto es equivalente a que debe haber sido generado
// por patron que verifica la ruta de la clase generadora
      {
               if (! this.repositorio.containsKey(nombreRecurso)) {
                                                return false;
                  }else
                    {
                    String repoContenido = this.repositorio.get(nombreRecurso).toString();
                    if (repoContenido.matches("recurso") ){
                            return true;
                        }else
                             {
                                return false;
                         }
                }
}

	  public String toString()
	  {
	    StringBuffer str= new StringBuffer("Listado de interfaces registrados Nombre/Interfaz");
	    Set<String> enume = this.repositorio.keySet();
            Iterator<String> iter = enume.iterator();
	    while (iter.hasNext()) {
	      Object key = iter.next();
	      str.append("\n");
	      str.append((String)key);
	      str.append("->");
	      str.append(this.repositorio.get(key));
	    }
	    return str.toString();
	  }

	private  Object obtenerItfEntidadRemota(String identEntity, String tipoItf){
            // para busqueda de entidades remotas debe estar creado el recurso de configuracion 
            if (!recursoConfiguracionCreado) {// verificamos si esta creado
               configuracion= (ItfUsoConfiguracion) this.repositorio.get(NombresPredefinidos.NOMBRE_ITF_USO_CONFIGURACION);
               if (configuracion ==null) return null;
               else recursoConfiguracionCreado = true;
            }   
            if (tipoItf != NombresPredefinidos.ITF_USO && tipoItf != NombresPredefinidos.ITF_GESTION)  {
            return null;
            }  
            Object itfRemota = null;
                if (identEntity.startsWith(tipoItf)) identEntity= identEntity.replaceFirst(tipoItf, "");
                // si no empieza por el prefijo itfUso o itf Gestion consideramos que es el identificador de la entidad
                try { 
//                    if (AdaptadorRegRMI.esRecursoRemoto(identEntity)) 
                    if (configuracion.esComponenteRemoto(identEntity))
                        itfRemota = AdaptadorRegRMI.getItfComponenteRemoto(identEntity,tipoItf);
         //           else if (configuracion.esAgenteRemoto(identEntity))  
         //               itfRemota = AdaptadorRegRMI.getItfAgenteRemoto(identEntity,tipoItf);
         //           if (itfRemota != null){
         //                   registrarInterfaz(identEntity, itfRemota);
         //               }
                    } catch (ExcepcionEnComponente ex) {
            Logger.getLogger(RepositorioInterfacesImpGen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
                    Logger.getLogger(RepositorioInterfacesImpGen.class.getName()).log(Level.SEVERE, null, ex);
                    this.trazas.trazar(this.getId(),"Error al intentar obtener una interfaz remota para la entidad:"+identEntity, InfoTraza.NivelTraza.error);
                }  
                 return itfRemota;
        }
	  /**
	   *  Pruebas
	   
	  public static void main(String[] args) {
	    Object obj1 = new Object();
	    Object obj2 = new Object();
	    Object obj3 = new Object();


	    try {
	      ItfUsoRepositorioInterfaces rep = RepositorioInterfaces.instance();
	    	
	      rep.registrarInterfaz("Objeto UNO",obj1);
	      rep.registrarInterfaz("Objeto DOS",obj2);
	      rep.registrarInterfaz("Objeto tRES",obj3);

	      System.out.println("El objeto uno es "+rep.obtenerInterfaz("Objeto UNO"));
	      System.out.println(""+rep.toString());
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }

	  }
		*/
}
