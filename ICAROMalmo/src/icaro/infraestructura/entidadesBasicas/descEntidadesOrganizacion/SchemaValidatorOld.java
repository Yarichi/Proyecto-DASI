package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import java.io.File;
import org.apache.log4j.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
*
* @author damiano
*/
public class SchemaValidatorOld {

   private static final String JAXP_SCHEMA_LANGUAGE =
           "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
   private static final String W3C_XML_SCHEMA =
           "http://www.w3.org/2001/XMLSchema";
   private static final String JAXP_SCHEMA_SOURCE =
           "http://java.sun.com/xml/jaxp/properties/schemaSource";
   
   private File schema;
   private File xmlDoc;
   
   private Boolean valid;
   
   private Log log = LogFactory.getLog(SchemaValidator.class);
   private Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());

   public SchemaValidatorOld(File schema) {
       this.schema = schema;
       this.valid = false;
   }
   
   
   
   public SchemaValidatorOld() {
       valid = false;
   }


   public Boolean validate(File xmlDoc) {
	   this.xmlDoc = xmlDoc;
       if (getSchema() == null || getXmlDoc() == null) {
           return null;
       }

       DocumentBuilderFactory factory =
               DocumentBuilderFactory.newInstance();

       factory.setNamespaceAware(true);
       factory.setValidating(true);

       try {
           factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

           factory.setAttribute(JAXP_SCHEMA_SOURCE,getSchema());
           DocumentBuilder builder = factory.newDocumentBuilder();

           builder.setErrorHandler(new ValidationErrorHandler());
           valid = true;
           builder.parse(getXmlDoc());

       } catch (Exception ex) {
    	   log.error(ex.toString());
           logger.fatal("\n\nEl fichero de descripcion del XML "
							+ xmlDoc
							+ "  no es valido. Compruebe la sintaxis de la descripcion y vuelva a intentarlo",ex);
           valid = false;
       }
       return valid;
   }


   public File getSchema() {
       return schema;
   }

   public void setSchema(File schema) {
       this.schema = schema;
   }

   public File getXmlDoc() {
       return xmlDoc;
   }

   public void setXmlDoc(File xmlDoc) {
       this.xmlDoc = xmlDoc;
   }

   private class ValidationErrorHandler implements ErrorHandler {

       public void warning(SAXParseException arg0) throws SAXException {
           log.warn(arg0+"\n\n");

       }

       public void error(SAXParseException arg0) throws SAXException {
           log.error(arg0+"\n\n");
           logger.fatal("\n\nEl fichero de descripcion del XML "
							+ xmlDoc + arg0.toString()+"\n\n", arg0);
           valid = false;
       }

       public void fatalError(SAXParseException arg0) throws SAXException {
           log.fatal(arg0+"\n\n");
           valid = false;
       }
   }

}
