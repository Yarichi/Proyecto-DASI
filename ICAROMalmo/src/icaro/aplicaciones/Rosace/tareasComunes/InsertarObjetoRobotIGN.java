package icaro.aplicaciones.Rosace.tareasComunes;

import icaro.aplicaciones.Rosace.informacion.RobotStatus;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.componentesInternos.movimientoCtrl.InfoCompMovimiento;
import icaro.aplicaciones.recursos.recursoPersistenciaEntornosSimulacion.ItfUsoRecursoPersistenciaEntornosSimulacion;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsertarObjetoRobotIGN extends TareaSincrona {

    String valueid;
    float velocidadPorDefectoRobot = 10000 / 3600; // m por segundo

    @Override
    public void ejecutar(Object... params) {
        try {
            // incorporo la interfaz del componente de movimiento que ha sido generado por la tarea CrearComponentesInternos
            // se le pasa al componente las coordenadas del robot y otros parametros como la velocidad inicial
            InfoCompMovimiento infoCompMovto = (InfoCompMovimiento) params[0];
            ItfUsoRecursoPersistenciaEntornosSimulacion itfPersistenciaES = (ItfUsoRecursoPersistenciaEntornosSimulacion) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfazUso(VocabularioRosace.IdentPersistenciaEntornosSimulacion);
            //     String nombreAgenteEmisor = this.getIdentAgente();

            //        String rutaFicheroRobotsTest = AccesoPropiedadesGlobalesRosace.getRutaFicheroRobotsTest();
            //        String rutaFicheroRobotsTest = AccesoPropiedadesGlobalesRosace.getRutaFicheroRobotsTest();

            //        ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(rutaFicheroRobotsTest);
            //        
            ////    	ReadXMLTestRobots rXMLTRobots = new ReadXMLTestRobots(Constantes.rutasFicheroRobotsIgualitario);
            //
            //    	Document doc = rXMLTRobots.getDocument(rXMLTRobots.gettestFilePaht());
            //		//Obtain all the robots
            //		NodeList nodeLst = rXMLTRobots.getRobotsXMLStructure(doc, "robot");
            //		
            //		int numeroRobotsSimulation = rXMLTRobots.getNumberOfRobots(nodeLst);
            //		
            //		for(int j=0; j<numeroRobotsSimulation;j++){
            //  		    //Obtain info about robot located at the test        	
            //        	Element info = rXMLTRobots.getRobotElement(nodeLst, j);			        	
            //        	valueid = rXMLTRobots.getRobotIDValue(info, "id");
            //        	
            //        	if (this.agente.equals(valueid)){        		
            //        	   int energy = rXMLTRobots.getRobotInitialEnergy(info, "initialenergy");
            //        	   Coordinate initialCoordinate = rXMLTRobots.getRobotCoordinate(info);
            //        	   float healRange = rXMLTRobots.getRobotHealRange(info, "healrange");
            //        		        	           	   
            //        	   RobotStatus robotStatus = new RobotStatus();        	           	   
            //        	   robotStatus.setIdRobot(valueid);
            //        	   robotStatus.setAvailableEnergy(energy);        	   
            //        	   robotStatus.setRobotCoordinate(initialCoordinate);        	   
            //        	   robotStatus.setHealRange(healRange); 
            RobotStatus robotStatus = itfPersistenciaES.getRobotStatus(this.identAgente);
            robotStatus.setInfoCompMovt(infoCompMovto);
            robotStatus.setRobotCoordinate(robotStatus.getRobotCoordinate()); // para que se pase la coordenada inicial leida al InfoMovimiento
            this.getEnvioHechos().insertarHecho(robotStatus);
            //                   ItfUsoMovimientoCtrl itfCompMovimiento = (ItfUsoMovimientoCtrl) infoCompMovto.getitfAccesoComponente();
            //        	   itfCompMovimiento.inicializarInfoMovimiento(initialCoordinate,velocidadPorDefectoRobot );
            //        	   j = numeroRobotsSimulation; //Salir del bucle for. Se ha encontrado la informacion xml asociada al robot/agente que ejecuta esta tarea
        } catch (Exception ex) {
            Logger.getLogger(InsertarObjetoRobotIGN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
