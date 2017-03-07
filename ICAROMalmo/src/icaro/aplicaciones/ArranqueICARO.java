package icaro.aplicaciones;

import icaro.infraestructura.CreacionOrganizacion;

public class ArranqueICARO {

    /**
     * Arranque de la infraestructura ICARO con un SMA especificado en un fichero de configuración
     * 
     * @param args
     *            Entrada: ruta del fichero de configuración 
     *            (sin la extensión .xml) que debe estar en config.icaro.aplicaciones.descripcionOrganizaciones
     *            (p.ej. rosaceCicloCompleto/descripcionNrobotsNvictimas)
     */
    public static void main(String args[]) {
        // Es necesario especificar un fichero de descripción como argumento
        if (args.length == 0) {
            System.err.println("Error: Hace falta especificar como argumento la ruta del fichero de descripción.\n Ejemplo: directorio/descripcionAcceso");
            System.exit(-1);
        } 

        CreacionOrganizacion.arranca(args[0]);	// arranca la infraestructura pasándole el fichero de descripción de la organización
    }
}
  