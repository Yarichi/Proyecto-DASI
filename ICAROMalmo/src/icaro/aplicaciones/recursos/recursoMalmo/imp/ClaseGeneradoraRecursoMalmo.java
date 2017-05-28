package icaro.aplicaciones.recursos.recursoMalmo.imp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.Rosace.informacion.VocabularioRosace;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Agente;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformePiedra;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Manzana;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.Obstaculo;
import icaro.aplicaciones.recursos.recursoMalmo.ItfUsoRecursoMalmo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.imp.AgenteCognitivotImp2;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;

public class ClaseGeneradoraRecursoMalmo extends ImplRecursoSimple implements ItfUsoRecursoMalmo{
	private static final long serialVersionUID = 8053587275334286680L;
	private ServerSocket serversocket;
	private Socket inSocket, outSocket;
	private DataOutputStream outFlow;
	BufferedReader inputData;
	private ArrayList<String> agents, apples, obstacles, ids;
	private ArrayList<Manzana> apples_parsed;
	private ArrayList<Agente> agents_parsed;
	private ArrayList<Obstaculo> obstacles_parsed;
	private PythonOrderDispatcher dispatcher;
	private AgenteCognitivotImp2 agente1,agente2;
	public static Integer NUM_AGENTES = 2;
	public ClaseGeneradoraRecursoMalmo(String idRecurso) throws RemoteException 
	{
		super(idRecurso);
		cargaMinecraft minecraftLoader;
		NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.registrarInterfaz(idRecurso, this);
		/*for (int i = 0; i < NUM_AGENTES; i++) 
		{
			minecraftLoader = new cargaMinecraft();
			minecraftLoader.start();
			while(!minecraftLoader.isLoaded());
		}*/
		try {
			String rutaIcaroMap = new File("src\\icaro\\aplicaciones\\recursos\\recursoMalmo\\imp\\icaro_map.py").getAbsolutePath();
			this.dispatcher = new PythonOrderDispatcher("C:\\Python27\\python.exe", rutaIcaroMap, 9288);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		agents = new ArrayList<String>();
		apples = new ArrayList<String>();
		obstacles = new ArrayList<String>();
		ids = new ArrayList<String>();
		/*try
		{
			serversocket = new ServerSocket(9289);
			inSocket = new Socket();
			inSocket = serversocket.accept();
			inputData = new BufferedReader(new InputStreamReader(inSocket.getInputStream()));
			Thread.sleep(1000);
			outSocket = new Socket("localhost", 9290);
	        outFlow = new DataOutputStream(outSocket.getOutputStream());
	        updateInformation();
		}
		catch(Exception e)
		{
			System.err.println("Error: "+e.getMessage());
		}
		 */
	    JOptionPane.showConfirmDialog(null, "Acepte cuando los minecraft esten iniciados", "Espera",
	            JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
		MensajeSimple mensaje = new MensajeSimple(VocabularioRosace.MalmoListo, VocabularioRosace.IdentRecursoMalmo , null);
		
		try {
			
			
			agente1 = (AgenteCognitivotImp2) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz("Itf_Ges_robot1Recolector");
			Thread t = new Thread(){
				public void run(){
					agente1.getControl().insertarHecho(mensaje);
				}
			};
			t.start();
			agente2 = (AgenteCognitivotImp2) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz("Itf_Ges_robot2Recolector");
			Thread t1 = new Thread(){
				public void run(){
					agente2.getControl().insertarHecho(mensaje);
				}
			};
			t1.start();
				
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}


		System.out.println("Enviados mensajes de arranque de Malmo");
	}

	public static void buildInformation(String line, ArrayList<String> data)
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
		else if(lines[0].equalsIgnoreCase("id"))
			buildInformation(lines[1], ids);
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

	public static ArrayList<Manzana> parseManzanas(ArrayList<String> manzanas){
		ArrayList<Manzana> manzanas_return = new ArrayList<Manzana>();
		StringBuilder aux;
		int i = 0;
		for (String s : manzanas){
			aux = new StringBuilder(s);
			aux.deleteCharAt(0);
			aux.deleteCharAt(aux.length()-1);
			s = aux.toString();
			String[] coords = s.split(",");
			manzanas_return.add(new Manzana("Manzana" + i, Float.parseFloat(coords[0]), Float.parseFloat(coords[1]),Float.parseFloat(coords[2])));
			i++;
		}
		return manzanas_return;
	}

	private ArrayList<Obstaculo> parseObstaculos(ArrayList<String> obstaculos){
		ArrayList<Obstaculo> obstaculo_return = new ArrayList<Obstaculo>();
		StringBuilder aux;
		for (String s : obstaculos){
			aux = new StringBuilder(s);
			aux.deleteCharAt(0);
			aux.deleteCharAt(aux.length()-1);
			s = aux.toString();
			String[] coords = s.split(",");
			obstaculo_return.add(new Obstaculo(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]),Integer.parseInt(coords[2])));
	
		}
		return obstaculo_return;
	}

	public ArrayList<Obstaculo> getInformacionObstaculos() {
		return this.obstacles_parsed;
	}

	public ArrayList<Agente> getInformacionAgentes() {
		return this.agents_parsed;
	}

	public void getInformacionManzanas() {
		this.dispatcher.sendCommand("apples");
		/*setInformation(apples);
		return this.parseManzanas(this.apples);*/
	}

	
	public void getInformacionAgente(String idAgente) throws Exception {
		this.dispatcher.sendCommand("agent " + idAgente);
		/*
		if(agent == null){
			JOptionPane.showMessageDialog(null, "El agente " + idAgente + " no consigue su informacion");
			return null;
		}
		else{
			String [] parts = agent.split("\\[");
			parts = parts[1].split(",");
			Agente agente = new Agente(idAgente,
					Double.parseDouble(parts[0]),
					Double.parseDouble(parts[2].split("\\]")[0]),
					Double.parseDouble(parts[1])

					);
			return agente;
		}*/
	}

	public void updateInformation()
	{
		String message;
		try 
		{
			agents.clear();
			apples.clear();
			obstacles.clear();
			ids.clear();
			outFlow.writeUTF("loop");
			message = inputData.readLine();
			do
			{
				setInformation(message);
				System.out.println(message);
				message = inputData.readLine();
				System.out.println(message);
			}
			while(!message.equalsIgnoreCase("end"));
			apples_parsed = parseManzanas(apples);
			agents_parsed = parseAgentes(agents);
			obstacles_parsed = parseObstaculos(obstacles);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	
	public void calculaCoste(String idAgente, Coordinate coorDestino) throws Exception {
		double x =(int)coorDestino.getX() + 0.5, y = (int)coorDestino.getZ() + 0.5;
		String msg = "eval " + idAgente + " " + x + " " + y;
		this.dispatcher.sendCommand(msg);
	}
	
	public void close()
	{
		try
		{
			dispatcher.closeDispatcher();
			outFlow.writeUTF("end");
			inSocket.close();
			outSocket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void moverAgente(String identAgente, Coordinate coorDestino) throws Exception {
		double x =(int)coorDestino.getX() + 0.5, y = (int)coorDestino.getZ() + 0.5;
		String msg = "move " + identAgente + " " + x + " " + y;
		System.out.println(identAgente + ": " + msg);
		new Thread(){
			public void run(){
				try {
					dispatcher.sendCommand(msg);
					//sleep(10000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	@Override
	public void construyePuente(String msg) {
		/*String msg = "buildriver " + informe.getIdAgenteInvolucrado() + " " + informe.getCoordenadaAgente().getX() + " " + informe.getCoordenadaAgente().getZ() + " "  
				                   + informe.getCoordenadaManzana().getX() + " " + informe.getCoordenadaManzana().getZ() + " " + informe.getOrientacionAgente();*/
		
		this.dispatcher.sendCommand(msg);
		
	}

	@Override
	public void picaPiedra(String msg) {
		/*String msg = "pickstone " + informe.getIdAgenteInvolucrado() + " " + informe.getCoordenadaObs().getX() + " " + informe.getCoordenadaObs().getZ() + " "  
                + informe.getCoordenadaObj().getX() + " " + informe.getCoordenadaObj().getZ() + " " + informe.getOrientacionAgente();*/
		this.dispatcher.sendCommand(msg);
		
	}
	
	public void trataMensajeInforme(String msg){
		String[] parts = msg.split(" ");
		if (parts[0].equals("buildriver")){
			this.construyePuente(msg);	
		}
		else if (parts[0].equals("pickstone")){
			this.picaPiedra(msg);	
		}
	}

}
