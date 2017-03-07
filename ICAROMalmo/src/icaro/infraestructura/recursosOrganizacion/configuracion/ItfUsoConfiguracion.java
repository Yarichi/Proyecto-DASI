package icaro.infraestructura.recursosOrganizacion.configuracion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstancia;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgenteAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescRecursoAplicacion;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author F Garijo
 * @version 1.2
 * @created 19-feb-2008 13:20:44
 */
public interface ItfUsoConfiguracion extends ItfUsoRecursoSimple {

	public String getValorPropiedadGlobal(String atributo) throws ExcepcionEnComponente, RemoteException;
        public int getValorNumericoPropiedadGlobal(String atributo)throws ExcepcionEnComponente, RemoteException;
        public ArrayList<String> getIdentificadoresInstanciasAgentesAplicacion()throws ExcepcionEnComponente, RemoteException;
	public DescInstanciaGestor getDescInstanciaGestor(String id) throws ExcepcionEnComponente,RemoteException;
	public DescInstanciaAgenteAplicacion getDescInstanciaAgenteAplicacion(String id) throws ExcepcionEnComponente,RemoteException;
	public DescInstanciaRecursoAplicacion getDescInstanciaRecursoAplicacion(String id) throws ExcepcionEnComponente,RemoteException;
	public DescRecursoAplicacion getDescRecursoAplicacion(String nombre) throws ExcepcionEnComponente,RemoteException;
	public DescComportamientoAgente getDescComportamientoAgente(String nombre) throws ExcepcionEnComponente,RemoteException;
        public DescInstancia getDescInstancia(String id)throws ExcepcionEnComponente,RemoteException;;
        public Properties getPropiedadesGlobales()throws ExcepcionEnComponente,RemoteException;     
        public Boolean existeDescInstancia(String id)throws ExcepcionEnComponente,RemoteException ;
        public String getIdentGestorInicial ()throws ExcepcionEnComponente,RemoteException;
        public String getHostComponente(String idComponente) throws ExcepcionEnComponente,RemoteException ;
        public String getHostAgente(String idAgente) throws ExcepcionEnComponente,RemoteException ;
        public String getHostRecurso(String idRecurso) throws ExcepcionEnComponente,RemoteException;
        public Boolean despliegueOrgEnUnSoloNodo ()throws ExcepcionEnComponente,RemoteException  ;
        public Boolean esAgenteRemoto (String idAgente)throws ExcepcionEnComponente,RemoteException;
        public Boolean esRecursoRemoto (String idRecurso)throws ExcepcionEnComponente,RemoteException;
        public Boolean esComponenteRemoto (String idRecurso)throws ExcepcionEnComponente,RemoteException;
        public void interpretarDescripOrganizacion(String rutaDescrOrganizacion)throws ExcepcionEnComponente,RemoteException;
        public boolean validarDescripOrganizacion()throws ExcepcionEnComponente,RemoteException;
        


}