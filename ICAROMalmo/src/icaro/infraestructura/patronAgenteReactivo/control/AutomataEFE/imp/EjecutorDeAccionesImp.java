package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp;

import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.EjecutorDeAccionesAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.ExcepcionEjecucionAcciones;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;
        
        
        /**
         *  Define la clase de objetos que pueden contener acciones semnticas, que se
         *  ejecutarn dinmicamente por el automata
         *
         *	
         *	@modified	22 de junio de 2006
         *	@version	2.0
         */

import java.util.logging.Level;
public class EjecutorDeAccionesImp extends EjecutorDeAccionesAbstracto {

    /**
     * Objeto que almacena las funciones
     * @uml.property  name="accionesSemanticas"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
    protected AccionesSemanticasAgenteReactivo accionesSemanticas;
    /**
     * Prxima accin que ejecutamos
     * @uml.property  name="accionSemanticaAEjecutar"
     */
    protected String accionSemanticaAEjecutar;
    /**
     * Lista de parmetros de la accin semntica a ejecutar
     * @uml.property  name="parametros" multiplicity="(0 -1)" dimension="1"
     */
    protected Object[] parametros;
    private ExecutorService executorService1;
    private Future ejecucionHebra;
    /**
     *  Constructor
     *	@param accionesSemanticas Clase que contiene las acciones ejecutables java
     */
    public EjecutorDeAccionesImp(AccionesSemanticasAgenteReactivo accionesSemanticas) {
        super("Acciones semanticas");
        this.accionesSemanticas = accionesSemanticas;
        executorService1 = Executors.newSingleThreadExecutor();
    }

    /**
     *  Asigna la accin a ejecutar
     *	@param  accion  Nombre del mtodo
     */
    public synchronized void setAccion(String accion) {
        accionSemanticaAEjecutar = accion;
    }

    /**
     * Asigna los parmetros de la accin a ejecutar
     * @param parametros  Lista de parmetros de la accin
     * @uml.property  name="parametros"
     */
    public synchronized void setParametros(Object[] parametros) {
        this.parametros = parametros;
    }

    /**
     * @return  Returns the accionesSemanticas.
     * @uml.property  name="accionesSemanticas"
     */
    public synchronized AccionesSemanticasAgenteReactivo getAccionesSemanticas() {
        return accionesSemanticas;
    }

    /**
     * @param accionesSemanticas  The accionesSemanticas to set.
     * @uml.property  name="accionesSemanticas"
     */
    public synchronized void setAccionesSemanticas(AccionesSemanticasAgenteReactivo accionesSemanticas) {
        this.accionesSemanticas = accionesSemanticas;
    }
    /**
     *  Decide el modo de ejecucin dependiendo de la accin semntica a realizar.
     *  Funciona como dispatcher de funciones. Debe evolucionar hacia una tabla
     *  donde re recoja el tipo de accin semntica de la que se trata => modificar
     *  el autmata para especificar transiciones bloqueantes o no bloqueantes.
     *
     *	@param  accion Nombre del mtodo a ejecutar
     *	@param  modoBloqueante Marca la espera o no por el resultado, en caso de ser
     *      no bloqueante se ejecuta en una nueva hebra
     */
   	public synchronized void ejecutarAccion(String accion, Object[] parametros, boolean modoBloqueante) throws ExcepcionEjecucionAcciones {
		      /**/
		if (accion != null)	{
			if (modoBloqueante){
                    ejecutarAccionBloqueante(accion, parametros); 
           }                
			else this.ejecutarAccionEnNuevaHebra(accion,parametros);
		}
		else System.out.println("AVISO: Accion Ignorada.La accion a ejecutar era null");
	}

	/**
	 *  Mtodo que ejecuta la accin en una nueva hebra
	 */
	public void run() {
        try {
            ejecutarAccionBloqueante(accionSemanticaAEjecutar, parametros);
        } catch (ExcepcionEjecucionAcciones ex) {
            Logger.getLogger(EjecutorDeAccionesImp.class.getName()).log(Level.SEVERE, null, ex);
        }
	}


	/**
	 *  Mtodo que ejecuta la accin dinmicamente esperando su vuelta
	 *
	 *	@param nombre Nombre del mtodo a ejecutar
	 */
	protected synchronized void ejecutarAccionBloqueante(String nombre, Object[] parametros) throws ExcepcionEjecucionAcciones {
		
		Class params[] = {};
		Object paramsObj[] = {};
		
		if (parametros != null && parametros.length > 0) {
			params = new Class[parametros.length];
			paramsObj = new Object[parametros.length];
			for (int i=0; i<parametros.length; i++) {
				params[i] = parametros[i].getClass();
				paramsObj[i] = parametros[i];
			}
		}
		else {
			params = new Class[0];
			paramsObj = new Object[0];
		}

		try	{
			Class thisClass = accionesSemanticas.getClass();
			Object iClass = accionesSemanticas;
			Method thisMethod = thisClass.getMethod(nombre, params);
			thisMethod.invoke(iClass, paramsObj);
		}
		catch (IllegalAccessException iae) {
			System.out.println("ERROR en los privilegios de acceso (no es publico) para en metodo: " + nombre + " de la clase: " + accionesSemanticas.getClass().getName());
			iae.printStackTrace();
            throw new ExcepcionEjecucionAcciones( "AccionesSemanticasImp", "error al ejecutar un metodo"+ nombre + " de la clase: " + accionesSemanticas.getClass().getName(),
                                                          "se ha producido un IlegalAccessIAE");
		}
		catch (NoSuchMethodError nsme) {
			System.out.println("ERROR (NO EXISTE) al invocar el metodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			nsme.printStackTrace();
            throw new ExcepcionEjecucionAcciones( "AccionesSemanticasImp", "error al ejecutar un metodo"+ nombre + " de la clase: " + accionesSemanticas.getClass().getName(),
                                                          "El metodo a invocar no existe. Se ha producido una excepcion NoSuchMethodError");
		}
		catch (NoSuchMethodException nsmee) {
			System.out.println("ERROR (NO EXISTE) al invocar el metodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			nsmee.printStackTrace();
			System.out.println("Invocando metodo con parametros de sus superclases correspondientes");
			throw new ExcepcionEjecucionAcciones( "AccionesSemanticasImp", "error al ejecutar un metodo"+ nombre + " de la clase: " + accionesSemanticas.getClass().getName(),
                                                          "El metodo a invocar no existe. Se ha producido una excepcion NoSuchMethodError");
            //ejecutarAccionBloqueantePolimorfica(nombre, parametros, 1);
		}
		catch (InvocationTargetException ite) {
			System.out.println("ERROR en la ejecucion del metodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			ite.printStackTrace();
			System.out.println("Excepcion producida en el metodo: ");
			ite.getTargetException().printStackTrace();
		throw new ExcepcionEjecucionAcciones( "AccionesSemanticasImp", "error al ejecutar un metodo"+ nombre + " de la clase: " + accionesSemanticas.getClass().getName(),
                                                          "El metodo a invocar no existe. Se ha producido una excepcion InvocationTargetException");
        }
	}

	/**
	 *  Mtodo que ejecuta la accin dinmicamente esperando su vuelta. 
	 *  Con el orden se va evaluando distintos niveles de herencia en los parmetros teniendo en
	 *  cuenta que pueden pasarse infinitos parmetros. El modo de operacin consiste en probar llamando
	 *  a mtodos con las clases finales en la jerarqua de herencia y en caso de no encontrar el
	 *  mtodo solicitado, subir un nivel de herencia para el primer parmetro manteniendo el resto
	 *  hasta llegar a la clase Object (primer nivel de la jeranqua). En ese momento se sube un nivel en
	 *  la jerarqua del segundo parmetro...
	 *
	 *	@param nombre Nombre del mtodo a ejecutar
	 */
	protected void ejecutarAccionBloqueantePolimorfica(String nombre, Object[] parametros, int nivel) {
		
		Class params[] = {};
		Object paramsObj[] = {};
		boolean salir = false;
		
		if (parametros != null && parametros.length > 0) {
			params = new Class[parametros.length];
			paramsObj = new Object[parametros.length];
			for (int i=0; i<parametros.length; i++) {
				params[i] = parametros[i].getClass();
				for (int j=0; j<nivel; j++) {
					if(params[i].getSuperclass() != null) {
						params[i] = params[i].getSuperclass();
						System.out.println("\n\nCLASS: "+params[i].toString()+"\n\n");
						salir = false;
					} else {salir = true;}
				}
				paramsObj[i] = parametros[i];
			}
		}
		else {
			params = new Class[0];
			paramsObj = new Object[0];
		}

		try	{
			Class thisClass = accionesSemanticas.getClass();
			Object iClass = accionesSemanticas;
			Method thisMethod = thisClass.getMethod(nombre, params);
			thisMethod.invoke(iClass, paramsObj);
		}
		catch (IllegalAccessException iae) {
			System.out.println("ERROR en los privilegios de acceso (no es pblico) para en mtodo: " + nombre + " de la clase: " + accionesSemanticas.getClass().getName());
			iae.printStackTrace();
		}
		catch (NoSuchMethodError nsme) {
			System.out.println("ERROR (NO EXISTE) al invocar el mtodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			nsme.printStackTrace();
		}
		catch (NoSuchMethodException nsmee) {
			System.out.println("ERROR (NO EXISTE) al invocar el mtodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			nsmee.printStackTrace();
			System.out.println("Invocando mtodo con parmetros de sus superclases correspondientes");
			if(!salir) ejecutarAccionBloqueantePolimorfica(nombre, parametros, ++nivel);
		}
		catch (InvocationTargetException ite) {
			System.out.println("ERROR en la ejecucin del mtodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			ite.printStackTrace();
			System.out.println("Excepcin producida en el mtodo: ");
			ite.getTargetException().printStackTrace();
		}
	}

	/**
	 *  Ejecutar la accin en una hebra nueva y volver
	 *
	 *@param nombre  Nombre del mtodo a ejecutar
	 *@param parametros Parmetros del mtodo a ejecutar
	 */
	protected void ejecutarAccionEnNuevaHebra(String nombre, Object[] parametros) {
		
		this.setAccion(nombre);
		this.setParametros(parametros);
//		this.setDaemon(true);
                executorService1.submit(this);
//		this.run();
	}
}
