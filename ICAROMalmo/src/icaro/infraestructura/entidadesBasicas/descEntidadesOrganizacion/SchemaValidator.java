package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
*
* @author damiano
*/
public class SchemaValidator {

   private static final String JAXP_SCHEMA_LANGUAGE =
           "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
   private static final String W3C_XML_SCHEMA =
           "http://www.w3.org/2001/XMLSchema";
   private static final String JAXP_SCHEMA_SOURCE =
           "http://java.sun.com/xml/jaxp/properties/schemaSource";
   
   private File schema;
   private File xmlDoc;
   
   private Boolean valid;
   
   //private Log log = LogFactory.getLog(SchemaValidator.class);
   private Logger log = Logger
			.getLogger(this.getClass().getCanonicalName());

   public SchemaValidator(File schema) {
       this.schema = schema;
       this.valid = false;
   }
   
   
   
   public SchemaValidator() {
       valid = false;
   }


   public Boolean validate(File xmlDoc) throws SAXException {
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

       } catch (ParserConfigurationException ex) {

		} catch (IOException ex) {

		} catch (SAXException ex) {
    	  log.error(ex);
           valid = false;

		   throw ex;
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
           valid = false;
		   throw arg0;
       }

       public void fatalError(SAXParseException arg0) throws SAXException {
           log.fatal("No se puede validar el fichero de configuracion " + arg0+"\n\n");
           valid = false;
		   throw arg0;
       }
   }

}
