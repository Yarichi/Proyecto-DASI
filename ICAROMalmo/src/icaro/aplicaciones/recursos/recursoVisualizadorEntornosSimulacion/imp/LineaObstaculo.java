package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
@Root
public class LineaObstaculo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Element
	private Coordinate ini;
	@Element
	private Coordinate fin;
	@Element
	private String valueid;
	public LineaObstaculo(){
		
	}
	public LineaObstaculo(Coordinate ini, Coordinate fin, String valueid)
	{
		this.ini = ini;
		this.fin = fin;
		this.valueid = valueid;
	}
	public String getId(){
		return valueid;
	}
	public Coordinate getCoordenadaIni(){
		return this.ini;
	}
	
	public Coordinate getCoordenadaFin(){
		return this.fin;
	}
//(350,40)                      (352,43)
	public boolean compruebaCoordenada(Coordinate coor) {
		int iniX=(int)this.ini.getX(),
			finX=(int)this.fin.getX(),
			iniY=(int)this.ini.getY(),
			finY=(int)this.fin.getY();
		if(iniX<=(int)coor.getX() && finX>=(int)coor.getX()){
			if (iniY <= (int)coor.getY() && finY >= (int)coor.getY())return true;
		}
		return false;
	}
}
