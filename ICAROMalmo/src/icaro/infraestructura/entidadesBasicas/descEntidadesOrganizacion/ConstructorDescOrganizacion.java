package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

//import icaro.herramientas.descripcionorganizacion.modelo.DescComportamientoAgente;
//import icaro.herramientas.descripcionorganizacion.modelo.DescOrganizacion;
//import icaro.herramientas.descripcionorganizacion.modelo.DescRecursoAplicacion;
//import icaro.infraestructura.recursosOrganizacion.configuracion.imp.*;
//import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.ConstructorProperties;
//import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstancia;
//import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgenteAplicacion;
//import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
//import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.ComponenteGestionado;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescOrganizacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Instancia;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.InstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Nodo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.excepciones.UsoRecursoException;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import java.util.logging.Level;



public  class ConstructorDescOrganizacion implements Serializable{
	private transient DescOrganizacion descOrganizacion;
        private HashMap <String, Object> tablaInstanciasDefinidas;
        private HashMap<String,Object> tablaDescripcionComponentes;
        private HashMap<String,Object> tablaComportamientosDefinidos;
        private HashMap<String,Object> tablaNodosDefinidos;
        private HashMap<String,TreeSet<String>> tablaEntidadesEnNodosDefinidos;
        private ArrayList<String> identificadoresGestores,identificadoresInstanciasAgentesAplicacion,identificadoresInstanciasRecursos ;
        private Properties properties;
        private Nodo nodocomunDefinido;
        private String identNodoComunDefinido, thisHost ;
        private transient Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());
        private String  valorPanelTrazas;
        private Boolean descripcionOrganizacionCorrecta;
        private ItfUsoRecursoTrazas trazas;
	public ConstructorDescOrganizacion(DescOrganizacion desOrgInterpretada) throws ExcepcionEnComponente {
                trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;	
                descripcionOrganizacionCorrecta = true;
                descOrganizacion = desOrgInterpretada;
                tablaInstanciasDefinidas = new HashMap <String,Object>();
                tablaComportamientosDefinidos = new HashMap <String,Object>();
                tablaDescripcionComponentes = new HashMap <String,Object>();
                tablaNodosDefinidos = new HashMap <String,Object>();
                identificadoresGestores= new  ArrayList<String>();
                identificadoresInstanciasAgentesAplicacion = new ArrayList<String>();
                identificadoresInstanciasRecursos = new ArrayList<String>();
                getValoresInicialesTablaNodos ();
                getInstanciasDefinidasGestores();
                obtenerTablaComportamientosDefinidosAgentes();
                obtenerTablaComportamientosDefinidosRecursos();
                getInstanciasDefinidasAgenteAplicacion();
                getInstanciasDefinidasRecursoAplicacion();
                
	}
         public void getValoresInicialesTablaNodos () throws ExcepcionEnComponente{
         // Se obtiene el nodo comun de ejecucion y se valida su definicion. Si hay inconsistencias se toma como identificador
         // localhost. El resto de los nodos se obtienen al construir
         // las instancias de agentes y de recursos. Se da preferencia a los nombres de los nodos frente a los nombre completos
         // excepto en el nodo comun donde  si hay  inconsistencias se advierte de la inconsistenci pero toma el del localhost
            try {
            thisHost = InetAddress.getLocalHost().getHostName();

            } catch (UnknownHostException ex) {
            java.util.logging.Logger.getLogger(ConstructorDescOrganizacion.class.getName()).log(Level.SEVERE, null, ex);
            }
             nodocomunDefinido = descOrganizacion.getDescInstancias().getNodoComun();
             if (nodocomunDefinido != null){
//             identNodoComunDefinido = nodocomunDefinido.getNombreUso();
//             String nombrecompletoHost = nodocomunDefinido.getNombreCompletoHost();
             TreeSet<String> identificadoresInstanciasEnNodo = new TreeSet<String>();
             tablaEntidadesEnNodosDefinidos = new HashMap<String, TreeSet<String>>();
             String nodoVerificado = getIdentNodo(nodocomunDefinido);
             
             if (nodoVerificado != null)
//                if (nombrecompletoHost.equalsIgnoreCase("localhost") )
                    tablaEntidadesEnNodosDefinidos.put(nodoVerificado, identificadoresInstanciasEnNodo);
             else{
                     trazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "Los identificadores definidos para el nodo localHost NO coinciden.  Se ha definido: nombreUso = " +nodocomunDefinido.getNombreUso()
                                +",nombrecompletoHost =  "+ nodocomunDefinido.getNombreCompletoHost() ,
                        InfoTraza.NivelTraza.error));
                      throw new ExcepcionEnComponente("Componente:configuracion","causa:Erores en el fichero de descripcion de la organizacion ", "Recurso de configuracion", "interpretacion del fichero de descripcion" );
                }
             }
        }
         
 public String getIdentNodo (Nodo nodoDefinido) {
     // A partir de la definicion de un nodo se deberia verficar la conectividad con el host definido
     // Solo se verifica que si el nombrecompletoHost es localhost se tomara como identificador de nodo el identificador real
     // y se ignora el que haya definido el usuario
     String identHost = nodoDefinido.getNombreUso();
     String nombrecompletoHost = nodoDefinido.getNombreCompletoHost();
     try {
            if ( nombrecompletoHost.equalsIgnoreCase("localhost")){
//                String    nuevoIdentHost = InetAddress.getLocalHost().getHostName();
                    if (identHost.equalsIgnoreCase(thisHost)) return identHost;
                    else{
                        // Se avisa de que son distintos y que se tomará el identifador del host
                        trazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "Los identificadores definidos para el nodo localHost NO coinciden.  Se ha definido: nombreUso = " +identHost
                                +",nombrecompletoHost =  "+ nombrecompletoHost + " Se toma como identHost = " + thisHost ,
                        InfoTraza.NivelTraza.debug));
                        return thisHost;
                    }
            }
            else {
                if ( !(identHost.isEmpty())  ) return identHost;
                else
                    if (!nombrecompletoHost.isEmpty()) return nombrecompletoHost;
                    else {// se avisa que no se han definido valores validos para los nombres del host
                         trazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "Los identificadores definidos para el nodo localHost SON NULL " +identHost
                                +",nombrecompletoHost :  "+ nombrecompletoHost ,InfoTraza.NivelTraza.error));
                
//                 java.util.logging.Logger.getLogger(ConstructorDescOrganizacion.class.getName()).log(Level.SEVERE, null, "Error");
                    return null;
                    }
            }
            } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConstructorDescOrganizacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
            }
     
 }       
        public void actualizarTablaNodosDefinidos(Instancia instanciaComponente){
            Nodo nodoInstancia = instanciaComponente.getNodoEspecifico();
            if (nodoInstancia == null){ // definimos el nodo de la instancia como el nodo comun
                nodoInstancia = nodocomunDefinido;
             }
                TreeSet<String> identificadoresInstanciasEnNodo;
                String identNodoInstancia = getIdentNodo(nodoInstancia);
//                String identCompletoHost = nodoInstancia.getNombreCompletoHost();
//                if ( identCompletoHost.equalsIgnoreCase("localhost"))
//                    identNodoInstancia = thisHost;
                if ( tablaEntidadesEnNodosDefinidos.containsKey(identNodoInstancia) ) // El nodo esta en la tabla de nodos
                        identificadoresInstanciasEnNodo =(TreeSet<String>) tablaEntidadesEnNodosDefinidos.get(identNodoInstancia);
                    else { // El nodo no esta en la tabla de nodos : Se crea una entrada con la lista de instancias
                        identificadoresInstanciasEnNodo = new TreeSet<String>();  
                    }
                if (identificadoresInstanciasEnNodo.add(instanciaComponente.getId()))
                    tablaEntidadesEnNodosDefinidos.put(identNodoInstancia, identificadoresInstanciasEnNodo);
        }
        public void obtenerTablaComportamientosDefinidosAgentes()
			throws ExcepcionEnComponente {
		// buscar entre los gestores
		Iterator<DescComportamientoAgente> iter = this.descOrganizacion
				.getDescripcionComponentes().getDescComportamientoAgentes()
				.getDescComportamientoGestores().getDescComportamientoAgente()
				.iterator();
//		boolean encontrado = false;
		DescComportamientoAgente desc;
		while (iter.hasNext()) {
			desc = iter.next();
                        String identComportamiento = desc.getNombreComportamiento();
                        // verificamos que el comportamiento no esta repetido
                        if (tablaComportamientosDefinidos.containsKey(identComportamiento) ){
                           descripcionOrganizacionCorrecta = false;

//                           throw new UsoRecursoException(
//				"El comportamiento de nombre "
//						+ identComportamiento
//						+ " esta duplicado : Comprobar la descripcion de la organizacion");
                            } else
                                    tablaComportamientosDefinidos.put(identComportamiento, desc);

		}
		iter = this.descOrganizacion.getDescripcionComponentes()
				.getDescComportamientoAgentes()
				.getDescComportamientoAgentesAplicacion()
				.getDescComportamientoAgente().iterator();
		// buscar entre los agentes de aplicacion

		while (iter.hasNext()) {
			desc = iter.next();
                        String identComportamiento = desc.getNombreComportamiento();
                        // verificamos que el comportamiento no esta repetido
                        if (tablaComportamientosDefinidos.containsKey(identComportamiento) ){
                            throw new ExcepcionEnComponente(
				"El comportamiento de nombre "
						+ identComportamiento
						+ " esta duplicado : Comprobar la descripcion de la organizacion");
                            } else
                                tablaComportamientosDefinidos.put(identComportamiento, desc);
		}
	}
//        public DescComportamientoAgente getDescComportamientoAgente(String nombre)
//			throws ExcepcionEnComponente {
//               return (DescComportamientoAgente) tablaComportamientosDefinidos.get(nombre);
     public DescComportamientoAgente getDescComportamientoAgente(String nombre){
         DescComportamientoAgente desCompAgte = null;
         if (!nombre.isEmpty()){
             desCompAgte = (DescComportamientoAgente) tablaComportamientosDefinidos.get(nombre);
            }
            return desCompAgte;

}
        public void obtenerTablaComportamientosDefinidosRecursos()
			throws ExcepcionEnComponente {
                        DescRecursoAplicacion desc;
            Iterator<DescRecursoAplicacion> iter = this.descOrganizacion.getDescripcionComponentes()
                                                       .getDescRecursosAplicacion().getDescRecursoAplicacion().iterator();
		while (iter.hasNext()) {
			desc = iter.next();
                        String identComportamiento = desc.getNombre();
                        // verificamos que el comportamiento no esta repetido
                        if (tablaComportamientosDefinidos.containsKey(identComportamiento) ){
                            throw new ExcepcionEnComponente(
				"El comportamiento de nombre "
						+ identComportamiento
						+ " esta duplicado : Comprobar la descripcion de la organizacion");
                            } else
                                tablaComportamientosDefinidos.put(identComportamiento, desc);
                    }
         }
	public DescRecursoAplicacion getDescRecursoAplicacion(String nombre)
			throws ExcepcionEnComponente {
		return (DescRecursoAplicacion) tablaComportamientosDefinidos.get(nombre);
        }

        public Boolean existeDescInstancia(String id)  {
           if ((tablaDescripcionComponentes == null) ) return false;
            else  return tablaDescripcionComponentes.containsKey(id);
	}

	public DescInstanciaAgenteAplicacion getDescInstanciaAgenteAplicacion(String id) throws ExcepcionEnComponente {

           DescInstanciaAgenteAplicacion descInstancia = (DescInstanciaAgenteAplicacion) tablaDescripcionComponentes.get(id);
            if (descInstancia == null){
                descInstancia = construirDescInstanciaAgenteAplicacion(id);
                tablaDescripcionComponentes.put(id, descInstancia);
                }
           return descInstancia;
	}

	public DescInstanciaGestor getDescInstanciaGestor(String id) throws ExcepcionEnComponente {
            DescInstanciaGestor descInstancia;
            if (tablaDescripcionComponentes.isEmpty()){
                        descInstancia= construirDescInstanciaGestor(id);
            }else{
                    descInstancia = (DescInstanciaGestor) tablaDescripcionComponentes.get(id);
                    if (descInstancia == null){
                        descInstancia = construirDescInstanciaGestor(id);
                        }
                    }
              tablaDescripcionComponentes.put(id, descInstancia);
              return descInstancia;
	}

	public DescInstanciaRecursoAplicacion getDescInstanciaRecursoAplicacion(String id) throws ExcepcionEnComponente {
		DescInstanciaRecursoAplicacion descInstancia = (DescInstanciaRecursoAplicacion) tablaDescripcionComponentes.get(id);
                if (descInstancia == null){
                    descInstancia = construirDescInstanciaRecursoAplicacion(id);
                    tablaDescripcionComponentes.put(id, descInstancia);
                    }
                 return descInstancia;
	}

	public String getValorPropiedadGlobal(String atributo)
			throws ExcepcionEnComponente {
		return properties.getProperty(atributo);

	}

	public DescOrganizacion getDescOrganizacion() {
		return descOrganizacion;
	}


        public ArrayList<String> getIdentificadoresGestoresDefinidos(){
            return identificadoresGestores;

        }
        public ArrayList<String> getIdentificadoresInstanciasAgentesAplicacion(){
            return identificadoresInstanciasAgentesAplicacion;
        }
        public ArrayList<String> getIdentificadoresInstanciasRecursoAplicacion(){
            return identificadoresInstanciasRecursos;
        }
//
//        public static ArrayList getIpNodos () {
//        config = getConfig();
//        if (config == null) return null;
//        else {
//            ArrayList listaNodos = new ArrayList();
//            Properties p = config.getPropiedadesGlobales();
//            Enumeration e = p.keys();
//            while (e.hasMoreElements()) {
//                String key = e.nextElement().toString();
//                if (key.startsWith("nodo", 0)) listaNodos.add(p.get(key));
//            }
//            return listaNodos;
//        }
//    }
  private void getInstanciasDefinidasGestores(){
	// Se obtienen las  instancias definidas y se añaden a la tabla
        Iterator<InstanciaGestor> iter = this.descOrganizacion.getDescInstancias()
                                        .getGestores().getInstanciaGestor().iterator();

			InstanciaGestor instancia = null;
			while (iter.hasNext() ) {
				instancia = iter.next();
                                String identInstancia = instancia.getId();
				actualizarInfoInstancias( instancia);
//                                tablaInstanciasDefinidas.put(identInstancia, instancia);
                                identificadoresGestores.add(identInstancia);
                                actualizarTablaNodosDefinidos(instancia);
                            }
        }
 private void getInstanciasDefinidasAgenteAplicacion(){
	// Se obtienen las  instancias definidas y se añaden a la tabla
                List<Instancia> instanciasAgentesAplicacion = descOrganizacion
					.getDescInstancias().getAgentesAplicacion().getInstancia();
			Iterator<Instancia> iter = instanciasAgentesAplicacion.iterator();
			Instancia instancia = null;
			while (iter.hasNext() ) {
				instancia = iter.next();
                                String identInstancia = instancia.getId();
//				tablaInstanciasDefinidas.put(identInstancia, instancia);
                                actualizarInfoInstancias( instancia);
                                identificadoresInstanciasAgentesAplicacion.add(identInstancia);
                                actualizarTablaNodosDefinidos(instancia);
                            }
        }
 private void getInstanciasDefinidasRecursoAplicacion(){
	// Se obtienen las  instancias definidas y se añaden a la tabla
                List<Instancia> instanciasAgentesAplicacion = descOrganizacion
					.getDescInstancias().getRecursosAplicacion().getInstancia();
			Iterator<Instancia> iter = instanciasAgentesAplicacion.iterator();
			Instancia instancia = null;
			while (iter.hasNext() ) {
				instancia = iter.next();
                                String identInstancia = instancia.getId();
//				tablaInstanciasDefinidas.put(instancia.getId(), instancia);
                                actualizarInfoInstancias( instancia);
                                identificadoresInstanciasRecursos.add(identInstancia);
                                actualizarTablaNodosDefinidos(instancia);
                            }
        }
  private void actualizarInfoInstancias(Instancia instanciaComponente) {
      // Si la informacion del nodo es null la completamos  con el  nodo comun
      // y actualizamos la tabla de instancias
      Nodo nodoDefinidoInstancia = instanciaComponente.getNodoEspecifico();
      if (nodoDefinidoInstancia == null)
          instanciaComponente.setNodoEspecifico(nodocomunDefinido);
      tablaInstanciasDefinidas.put(instanciaComponente.getId(), instanciaComponente);
  }

////        private Instancia getInstanciaAgenteAplicacion(String id){
////			// Obtener la instancia
////                List<Instancia> instanciasAgentesAplicacion = descOrganizacion
////					.getInstancias().getAgentesAplicacion().getInstancia();
////			Iterator<Instancia> iter = instanciasAgentesAplicacion.iterator();
////			Instancia instancia = null;
////			boolean encontrado = false;
////			while (iter.hasNext() && !encontrado) {
////				instancia = iter.next();
////				if (instancia.getId().equals(id)) {
////					encontrado = true;
////				}
////                            }
////                        return instancia;
//        }
	public DescInstanciaAgenteAplicacion construirDescInstanciaAgenteAplicacion(
			String id) throws ExcepcionEnComponente {

		try {
                        Instancia instanciaDefinida = (Instancia)tablaInstanciasDefinidas.get(id);
			if (instanciaDefinida != null) {
				// Obtener el comportamiento
                            DescInstanciaAgenteAplicacion descAgente = new DescInstanciaAgenteAplicacion();
                            descAgente.setId(id);
                            descAgente.setCategoriaComponente(NombresPredefinidos.NOMBRE_ENTIDAD_AGENTE);
                            descAgente.setDescComportamiento(getDescComportamientoAgente
                                                                    (instanciaDefinida.getRefDescripcion()));
                            descAgente.setTipoComponente(getDescComportamientoAgente(instanciaDefinida.getRefDescripcion()).getTipo().value());
                            descAgente.setRolComponente(getDescComportamientoAgente(instanciaDefinida.getRefDescripcion()).getRol().value());
				// Obtener lista de propiedades
                            descAgente.setPropiedades(ConstructorProperties
						.obtenerProperties(instanciaDefinida.getListaPropiedades()));
				// obtener el nodo especifico
				Nodo nodo = instanciaDefinida.getNodoEspecifico();
				if (nodo == null) {
					// obtener el nodo comun de las instancias de agentes
					nodo = descOrganizacion.getDescInstancias()
							.getAgentesAplicacion().getNodoComun();
					if (nodo == null) {
						// obtener el nodo comun de todas las instancias
						nodo = descOrganizacion.getDescInstancias()
								.getNodoComun();
					}
				}
				descAgente.setNodo(nodo);
				if (nodo == null) {
					throw new ExcepcionEnComponente(
							"Error al leer el nodo de la instancia de agente con id "
									+ id);
				}else
//                                   identificadoresInstanciasAgentesAplicacion.add(id);
                                    // añadimos el nodo a la tabla de nodos
                                    construirDescNodos(nodo);
                                    return descAgente;
			} else
                           logger.fatal("No se ha podido encontrar la descripcion de la instancia de agente de aplicacion  : "+ id);
                           descripcionOrganizacionCorrecta = false;
//                            throw new ExcepcionEnComponente(
//							"No se ha podido encontrar la descripcion de la instancia de agente de aplicacion  : "
//                                                          + id);
                            return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcepcionEnComponente(
					"Error al interpretar la descripcion de la instancia de agente con id "
							+ id);
		}

	}

//    private InstanciaGestor getInstanciaGestor(String id){
//			// Obtener la instancia
//                        List<InstanciaGestor> instanciasGestores = descOrganizacion.getInstancias()
//                                                                        .getGestores().getInstanciaGestor();
//			Iterator<InstanciaGestor> iter = instanciasGestores.iterator();
//			InstanciaGestor instancia = null;
//			boolean encontrado = false;
//			while (iter.hasNext() && !encontrado) {
//				instancia = iter.next();
//				if (instancia.getId().equals(id)) {
//					encontrado = true;
//				}
//                            }
//                        return instancia;
//        }
// TODO revisar creacion de  las instancias, Se debe permitir que se analicen todas y no que se pare el proceso cuando se detecta un error
        public DescInstanciaGestor construirDescInstanciaGestor(String id)throws ExcepcionEnComponente {
                try {
//			InstanciaGestor instancia = getInstanciaGestor(id);
			if (tablaInstanciasDefinidas == null){
                            getInstanciasDefinidasGestores();
                        }
                        InstanciaGestor instancia =(InstanciaGestor)tablaInstanciasDefinidas.get(id);
			if (instancia != null) {
				// Obtener el comportamiento
                        DescInstanciaGestor descGestor = new DescInstanciaGestor();
                            descGestor.setId(id);
                            descGestor.setCategoriaComponente(NombresPredefinidos.NOMBRE_ENTIDAD_AGENTE);
                            descGestor.setDescComportamiento(getDescComportamientoAgente (instancia.getRefDescripcion()));
                            descGestor.setTipoComponente(getDescComportamientoAgente(instancia.getRefDescripcion()).getTipo().value());
                            descGestor.setRolComponente(getDescComportamientoAgente(instancia.getRefDescripcion()).getRol().value());
				// Obtener lista de propiedades
                            descGestor.setPropiedades(ConstructorProperties
						.obtenerProperties(instancia.getListaPropiedades()));
				// obtener el nodo especfico
				Nodo nodo = instancia.getNodoEspecifico();
				if (nodo == null) {
					// obtener el nodo comn de las instancias de gestores
					nodo = descOrganizacion.getDescInstancias()
							.getGestores().getNodoComun();
					if (nodo == null) {
						// obtener el nodo comn de todas las instancias
						nodo = descOrganizacion.getDescInstancias()
								.getNodoComun();
					}
				}
				descGestor.setNodo(nodo);
				if (nodo == null) {
					throw new ExcepcionEnComponente(
							"Error al leer el nodo de la instancia de agente con id "
									+ id);
				}else
                                   construirDescNodos(nodo);
				// obtener lista de componentes gestionados
//				List<ComponenteGestionado> componentes = instancia
//						.getComponentesGestionados().getComponenteGestionado();
//				Iterator<ComponenteGestionado> iterator = componentes
//						.iterator();
//				ComponenteGestionado componente = null;
//				List<DescInstancia> componentesGestionados = new ArrayList<DescInstancia>();
//				while (iterator.hasNext()) {
//					componente = iterator.next();
//					DescInstancia inst = null;
//					switch (componente.getTipoComponente()) {
//					case GESTOR:
//                                                inst = getDescInstanciaGestor(componente.getRefId());
//						break;
//					case AGENTE_APLICACION:
//						inst = getDescInstanciaAgenteAplicacion(componente.getRefId());
//
//						break;
//					case RECURSO_APLICACION:
//						inst = getDescInstanciaRecursoAplicacion(componente.getRefId());
//
//					}
//					componentesGestionados.add(inst);
//				}
                                descGestor.setComponentesGestionados(obtenerComponentesGestionados(instancia));
//				descGestor.setComponentesGestionados(componentesGestionados);
 //                               identificadoresGestores.add(id);
                                return descGestor;

			} else
				throw new ExcepcionEnComponente(
						"Error al interpretar la descripcion de la instancia de gestor con id "
								+ id + " No existe la instancia del gestor");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcepcionEnComponente(
					"Error No se ha podido encontrar la  descripcion de la instancia de gestor con id "
							+ id);
		}
	}

  public String getNodoInstanciaComponente ( String identComponente){

//        Object descInst =  tablaDescripcionComponentes.get(identComponente);
          Object descInst =  tablaInstanciasDefinidas.get(identComponente);
          String identNodo = null;
          if (descInst != null){
               Instancia desComp = (Instancia ) descInst;
                     if (desComp.getNodoEspecifico()!= null)
                         identNodo = desComp.getNodoEspecifico().getNombreUso();
                         }
            return identNodo;
    }
  public HashMap<String,Object> getTablaNodosDefinidos (){
       return this.tablaNodosDefinidos;
  }
  public Boolean despliegueOrgEnUnSoloNodo (){
       return (this.tablaEntidadesEnNodosDefinidos.size() < 2);
  }
//
//    private Instancia getInstanciaRecursoAplicacion(String id){
//			// Obtener la instancia
//		 Object intanciaDefinida =  tablaDescripcionComponentes.get(id);
//
//                 if (intanciaDefinida.getClass().){
//                    descInstancia = construirDescInstanciaRecursoAplicacion(id);
//                    tablaDescripcionComponentes.put(id, descInstancia);
//                    }
//                 return descInstancia;
//                        return instancia;
//        }
	public DescInstanciaRecursoAplicacion construirDescInstanciaRecursoAplicacion(
			String id) throws ExcepcionEnComponente {
		try {
			
			Instancia instanciaDefinida = (Instancia)tablaInstanciasDefinidas.get(id);
			if (instanciaDefinida != null) {
                            DescInstanciaRecursoAplicacion descInstanciaRecursoAplicacion = new DescInstanciaRecursoAplicacion();
                            descInstanciaRecursoAplicacion.setId(id);
                            descInstanciaRecursoAplicacion.setCategoriaComponente(NombresPredefinidos.NOMBRE_ENTIDAD_RECURSO);
                            // Obtener descripcion
                            descInstanciaRecursoAplicacion.setDescRecurso(this.getDescRecursoAplicacion
                                                                                (instanciaDefinida.getRefDescripcion()));
				// Obtener lista de propiedades
                            descInstanciaRecursoAplicacion.setPropiedades(ConstructorProperties
						.obtenerProperties(instanciaDefinida.getListaPropiedades()));
				// obtener el nodo especfico
				Nodo nodo = instanciaDefinida.getNodoEspecifico();
				if (nodo == null) {
					// obtener el nodo comun de las instancias de recursos de aplicacion
					nodo = descOrganizacion.getDescInstancias()
							.getRecursosAplicacion().getNodoComun();
					if (nodo == null) {
						// obtener el nodo comun de todas las instancias
						nodo = descOrganizacion.getDescInstancias()
								.getNodoComun();
					}
				}
				descInstanciaRecursoAplicacion.setNodo(nodo);
				if (nodo == null) {
					throw new ExcepcionEnComponente(
							"Error al leer el nodo de la instancia de agente con id "
									+ id);
				} else
                                    construirDescNodos(nodo);
                                    identificadoresInstanciasRecursos.add(id);
                                    return descInstanciaRecursoAplicacion;
			} else
                            throw new ExcepcionEnComponente(
							"Error No se ha encontrado la descripcion de la  instancia de recurso con id "
									+ id);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcepcionEnComponente(
					"Error al interpretar la descripcion de la instancia de recurso con id "
							+ id);
		}
	}
    public DescInstancia getDescInstancia(String id)  {
            Object resultado =tablaDescripcionComponentes.get(id);
            if (resultado == null ) return null;
            else return (DescInstancia)resultado;
	}
    public void construirDescNodos( Nodo nodoDefinido)  {
		if (nodoDefinido == null){
                    logger.fatal("El nodo que se intenta construir es NULO Verifique la definicion del nodo");
                    descripcionOrganizacionCorrecta = false;
                    }
                    String identNodo = nodoDefinido.getNombreUso();
                    if (identNodo == null){
                    logger.fatal("El identificador del nodo que se intenta construir es NULO Verifique la definicion del nodo");
                    descripcionOrganizacionCorrecta = false;
                    }
                        if(!tablaNodosDefinidos.containsKey(identNodo)){
                            // Es un nodo nuevo . Validamos la direccion IP / o de forma mas general el nombre del host
                            String identifHost = nodoDefinido.getNombreCompletoHost();
       //                   if (validarDireccionIPDefinida (direccionIP)){
                            DescNodo descNuevoNodo = new DescNodo();
                            descNuevoNodo.setNombreUso(identNodo);
                            descNuevoNodo.setNombreCompletoHost(identifHost);
                            if (identifHost.equals("localhost")){
                                tablaNodosDefinidos.put (identNodo,descNuevoNodo);
                                }
                                else{
                                if (descNuevoNodo.setDireccionIP(identifHost))
                                    tablaNodosDefinidos.put (identNodo,descNuevoNodo);
                                    else   {
                                            descripcionOrganizacionCorrecta = false ;
                                            logger.fatal("La direccion IP definida  :" + identifHost
                                            + "No es correcta  "
                                            + ".\n Compruebe la direccion IP .");
                                            }
                            }
                }
}
private List<DescInstancia> obtenerComponentesGestionados(InstanciaGestor instancia) throws ExcepcionEnComponente{
   // TODO tratar el caso en que la lista es vacia. Esto ocurre cuando el gestor no tiene elementos que gestional
   // Esto lo deberia tener en cuenta el gestor de la organizacion que no debe crear un gestor vacio
   // aunque esta situacion puede tener sentido si el gestor crea instancias mas tarde
        List<ComponenteGestionado> componentes = instancia
						.getComponentesGestionados().getComponenteGestionado();
				Iterator<ComponenteGestionado> iterator = componentes
						.iterator();
				ComponenteGestionado componente = null;
				List<DescInstancia> componentesGestionados = new ArrayList<DescInstancia>();
				while (iterator.hasNext()) {
					componente = iterator.next();
					DescInstancia inst = null;
					switch (componente.getTipoComponente()) {
					case GESTOR:
                                                inst = getDescInstanciaGestor(componente.getRefId());
						break;
					case AGENTE_APLICACION:
						inst = getDescInstanciaAgenteAplicacion(componente.getRefId());

						break;
					case RECURSO_APLICACION:
						inst = getDescInstanciaRecursoAplicacion(componente.getRefId());

					}
					componentesGestionados.add(inst);
				}
                              return  componentesGestionados;
}

private boolean validarDireccionIPDefinida(String iPaddress){
//        final Pattern IP_PATTERN =
//              Pattern.compile("b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).)"
//                                    + "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)b");
//    return IP_PATTERN.matcher(iPaddress).matches();
    String ipPattern = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})(:(\\d+))?";
//  String ipV6Pattern = "\\[([a-zA-Z0-9:]+)\\]:(\\d+)";
//  String hostPattern = "([\\w\\.\\-]+):(\\d+)";  // note will allow _ in host name
//    Pattern p = Pattern.compile( ipPattern + "|" + ipV6Pattern + "|" + hostPattern );
    Pattern p = Pattern.compile( ipPattern );
    Matcher m = p.matcher( iPaddress );
    return m.matches ();

//    if( m.matches() )
//    {
//    if( m.group(1) != null ) {
//        // group(1) IP address, group(2) is port
//    } else if( m.group(3) != null ) {
//        // group(3) is IPv6 address, group(4) is port
//    } else if( m.group(5) != null ) {
//        // group(5) is hostname, group(6) is address
//    } else {
//        // Not a valid address
//    }
}

}
