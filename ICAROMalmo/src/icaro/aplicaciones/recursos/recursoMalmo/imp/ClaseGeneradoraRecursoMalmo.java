package icaro.aplicaciones.recursos.recursoMalmo.imp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Agente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Obstaculo;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;

public class ClaseGeneradoraRecursoMalmo extends ImplRecursoSimple implements ItfUsoRecursoMalmo{
	private static final long serialVersionUID = 8053587275334286680L;
	private ServerSocket serversocket;
	private Socket socket;
	private ArrayList<String> agents, apples, obstacles;
	private ArrayList<Manzana> apples_parsed;
	private ArrayList<Agente> agents_parsed;
	private ArrayList<Obstaculo> obstacles_parsed;
	private PythonOrderDispatcher dispatcher;
	public ClaseGeneradoraRecursoMalmo(String idRecurso) throws RemoteException 
	{
		super(idRecurso);
		
		//NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.registrarInterfaz(idRecurso, this);
		BufferedReader input;
		String message;
		try {
			String rutaIcaroMap = new File("src\\icaro\\aplicaciones\\recursos\\recursoMalmo\\imp\\icaro_map2.py").getAbsolutePath();
			//Runtime.getRuntime().exec("C:\\Python27\\python " + rutaIcaroMap);
			this.dispatcher = new PythonOrderDispatcher("C:\\Python27\\python.exe", rutaIcaroMap, 9288);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
				System.out.println(message);
				message = input.readLine();
			}
			while(!message.equalsIgnoreCase("end"));
			serversocket.close();
		}
		catch(Exception e)
		{
			System.err.println("Error: "+e.getMessage());
		}
		apples_parsed = parseManzanas(apples);
		agents_parsed = parseAgentes(agents);
		obstacles_parsed = parseObstaculos(obstacles);
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
	
	private ArrayList<Agente> parseAgentes(ArrayList<String> agentes){
		ArrayList<Agente> agentes_return = new ArrayList<Agente>();
		StringBuilder aux;
		int i = 0;
		for (String s : agentes){
			aux = new StringBuilder(s);
			aux.deleteCharAt(0);
			aux.deleteCharAt(aux.length()-1);
			s = aux.toString();
			String[] coords = s.split(",");
			agentes_return.add(new Agente("Agente" + i, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]),Integer.parseInt(coords[2])));
			i++;
		}
		return agentes_return;
	}
	
	private ArrayList<Manzana> parseManzanas(ArrayList<String> manzanas){
		ArrayList<Manzana> manzanas_return = new ArrayList<Manzana>();
		StringBuilder aux;
		int i = 0;
		for (String s : manzanas){
			aux = new StringBuilder(s);
			aux.deleteCharAt(0);
			aux.deleteCharAt(aux.length()-1);
			s = aux.toString();
			String[] coords = s.split(",");
			manzanas_return.add(new Manzana("Manzana" + i, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]),Integer.parseInt(coords[2])));
			i++;
		}
		return manzanas_return;
	}
	
	private ArrayList<Obstaculo> parseObstaculos(ArrayList<String> obstaculos){
		ArrayList<Obstaculo> obstaculo_return = new ArrayList<Obstaculo>();
		StringBuilder aux;
		int i = 0;
		for (String s : obstaculos){
			aux = new StringBuilder(s);
			aux.deleteCharAt(0);
			aux.deleteCharAt(aux.length()-1);
			s = aux.toString();
			String[] coords = s.split(",");
			obstaculo_return.add(new Obstaculo(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]),Integer.parseInt(coords[2])));
			i++;
		}
		return obstaculo_return;
	}
	

	public ArrayList<Obstaculo> getInformacionObstaculos() {
		return this.obstacles_parsed;
	}

	public ArrayList<Agente> getInformacionAgentes() {
		return this.agents_parsed;
	}

	public ArrayList<Manzana> getInformacionManzanas() {
		return this.apples_parsed;
	}

	@Override
	public Agente getInformacionAgente(String idAgente) throws Exception {
		for(Agente tmp : this.agents_parsed){
			if(tmp.getId() == idAgente) return tmp;
		}
		
		return null;
	}

}
