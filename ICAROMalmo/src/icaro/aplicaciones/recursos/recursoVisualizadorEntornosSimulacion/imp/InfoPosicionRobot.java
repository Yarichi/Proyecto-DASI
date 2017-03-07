/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import icaro.aplicaciones.Rosace.informacion.Coordinate;

/**
 *
 * @author FGarijo
 */
public class InfoPosicionRobot {
    String identRobot;
    Coordinate robotPosicion;
    InfoPosicionRobot (){
        identRobot = null;
        robotPosicion=null;
    }
    InfoPosicionRobot (String idRobot, Coordinate robotPos){
        identRobot = idRobot;
        robotPosicion=robotPos;
    }
    public String getidentRobot(){
        return identRobot;
    }
    public Coordinate getrobotPosicion(){
        return robotPosicion;
    }
    public int getrobotPosicionX(){
        return (int)robotPosicion.getX();
    }
    public int getrobotPosicionY(){
        return (int)robotPosicion.getY();
    }
    public void modificarInfoPosicion(String idRobot, Coordinate robotPos){
        identRobot=idRobot;
        robotPosicion=robotPos;
    }
}
