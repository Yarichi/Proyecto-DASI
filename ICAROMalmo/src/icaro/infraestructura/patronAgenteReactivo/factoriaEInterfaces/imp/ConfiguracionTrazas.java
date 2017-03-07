package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;



public class ConfiguracionTrazas {

	/**
	 * Clase que carga la configuracion de las trazas de un archivo de configuracion
	 * @uml.property  name="logger"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Logger logger;
	/**
	 * @uml.property  name="archivoLog"
	 */
	private String archivoLog;
	/**
	 * @uml.property  name="nivelLog"
	 */
	private String nivelLog;
	
	public ConfiguracionTrazas(){
		logger = Logger.getRootLogger();
		archivoLog = "log/organizacion.log";
		nivelLog = "debug";
		this.configura();
	}
	
	public ConfiguracionTrazas(Logger logger){
		this.logger = logger;
		archivoLog = "log/organizacion.log";
		nivelLog = "debug";
		this.configura();
	}
	
	public ConfiguracionTrazas(Logger logger, String archivoLog, String nivelLog){
		this.logger = logger;
		this.archivoLog = archivoLog;
		this.nivelLog = nivelLog;
		this.configura();
	}
	
	public static void main (String[] args){
		new ConfiguracionTrazas();
	}
	private void configura(){
		// Como es un Logger nuevo, no tiene appenders, se los colocamos
		System.out.println("TRAZAS: Configurando logger: " + logger.getName());
		// WARNING: Comprobar que no tiene appenders antes de ponerle mas
		logger.removeAllAppenders();
		// Primero el de consola
		// El layout esta hard-wired
		PatternLayout layout = new PatternLayout("%-4r [%t] %-5p %c %x - %m%n");

		ConsoleAppender appenderConsola = new ConsoleAppender(layout);
		logger.addAppender(appenderConsola);

		
		// Ahora el patron de archivo
		// Primero obtenemos el nombre de archivo de la configuracion
		
		try{
			FileAppender appenderArchivo = new FileAppender(layout, archivoLog, true);
			logger.addAppender(appenderArchivo);
		} catch (IOException e){
			logger.error("Error incializando logging auxiliar sobre archivo");
			logger.error(e);
		}

		logger.setLevel(Level.toLevel(nivelLog));
		//logger.fatal("Nivel Actual: " + logger.getLevel());

	}
}
