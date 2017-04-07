package icaro.aplicaciones.recursos.recursoMalmo.imp;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Agente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Obstaculo;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;

public class ClaseGeneradoraRecursoMalmo extends ImplRecursoSimple implements ItfUsoRecursoMalmo{
	private static final long serialVersionUID = 8053587275334286680L;
	private ServerSocket serversocket;
	private Socket socket;
	private ArrayList<String> agents, apples, obstacles;
	
	public ClaseGeneradoraRecursoMalmo(String idRecurso) throws RemoteException 
	{
		super(idRecurso);
		BufferedReader input;
		String message;
		agents = new ArrayList<String>();
		apples = new ArrayList<String>();
		obstacles = new ArrayList<String>();
		try
		{
			serversocket = new ServerSocket(9289);
			socket = new Socket();
			socket = serversocket.accept();
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			message = input.readLine();
			do
			{
				setInformation(message);
				message = input.readLine();
			}
			while(!message.equalsIgnoreCase("end"));
			serversocket.close();
		}
		catch(Exception e)
		{
			System.err.println("Error: "+e.getMessage());
		}
		
	}
	
	private void buildInformation(String line, ArrayList<String> data)
	{
		String[] lines;
		line = line.replaceAll("\\[\\[", "\\[");
		line = line.replaceAll("\\]\\]", "\\]");
		line = line.replaceAll("\\], \\[", "\\] \\[");
		line = line.replaceAll(", ", ",");
		lines = line.split(" ");
		for (int i = 0; i < lines.length; i++) 
		{
			data.add(lines[i]);
		}
	}
	
	private void setInformation(String line)
	{
		String[] lines = line.split("_");
		if(lines[0].equalsIgnoreCase("ag"))
			buildInformation(lines[1], agents);
		else if(lines[0].equalsIgnoreCase("ap"))
			buildInformation(lines[1], apples);
		else if(lines[0].equalsIgnoreCase("ob"))
			buildInformation(lines[1], obstacles);
	}

	public ArrayList<String> getObstaculos() {
		return obstacles;
	}

	public ArrayList<String> getAgentes() {
		return agents;
	}

	public String getInformacionAgente(String idAgente) {
		return agents.get(Integer.parseInt(idAgente));
	}

	public ArrayList<String> getInformacionManzanas() {
		return apples;
	}

}
