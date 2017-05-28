package icaro.aplicaciones.recursos.recursoMalmo;

import java.util.ArrayList;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Agente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformePiedra;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Obstaculo;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoRecursoMalmo extends ItfUsoRecursoSimple{
	public abstract ArrayList<Obstaculo> getInformacionObstaculos() throws Exception;
	public abstract ArrayList<Agente> getInformacionAgentes() throws Exception;
	public abstract void getInformacionAgente(String idAgente) throws Exception;
	public abstract void getInformacionManzanas() throws Exception;
	public abstract void calculaCoste(String idAgente, Coordinate coorDestino) throws Exception;
	public abstract void moverAgente(String identAgente, Coordinate coordinate) throws Exception;
	public abstract void construyePuente(String msg) throws Exception;
	public abstract void picaPiedra(String justificacion) throws Exception;
	public abstract void trataMensajeInforme(String msg) throws Exception;
}
