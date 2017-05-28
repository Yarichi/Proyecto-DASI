package icaro.aplicaciones.recursos.recursoMalmo.imp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import icaro.aplicaciones.Rosace.informacion.Coordinate;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeObjetivo;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformePiedra;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.informacion.InformeRio;
import icaro.aplicaciones.agentes.agenteAplicacionAgteRecolectorCognitivo.tareas.ConseguirInformacionDelEntorno;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.imp.AgenteCognitivotImp2;

public class PythonOrderDispatcher implements OrderDispatcher 
{
	private ServerSocket serversocket, serversocketAck;
	private Socket inSocket, inSocketAck, outSocket;
	BufferedReader inputData, inputDataAck;
	protected DataOutputStream outputData;
	protected Process pythonDispatcherThread;
	protected ArrayList<String> commandAcks;
	public static Hashtable<String,Coordinate> agentes = new Hashtable<String,Coordinate>();
	public static Hashtable<String,Integer> evaluaciones = new Hashtable<String,Integer>();

	public PythonOrderDispatcher(String pythonPath, String pythonScript, int port) throws IOException
	{
		try 
		{
			//Iniciamos el proceso de inicializacion de la parte de python
			String[] command = {pythonPath, pythonScript};
			pythonDispatcherThread = Runtime.getRuntime().exec(command);
			//damos tiempo para que se inicie tranquilamente
			Thread.sleep(10000);
			//creamos el outSocket para comunicarnos con la interfaz de python
			outSocket = new Socket("localhost", port);
			//creamos el inSocket para recibir los mensajes de python
			serversocket = new ServerSocket(port + 1);
			inSocket = new Socket();
			inSocket = serversocket.accept();
			//creamos la clase con la que recibimos los mensajes asociados a inSocket
			inputData = new BufferedReader(new InputStreamReader(inSocket.getInputStream()));
			
			new Thread(){
				public void run(){
					receiveCommand();
				}
			}.start();
			//creamos el inSocket para recibir los mensajes de python
			serversocketAck = new ServerSocket(port + 2);
			inSocketAck = new Socket();
			inSocketAck = serversocketAck.accept();
			//creamos la clase con la que recibimos los mensajes asociados a inSocket
			inputDataAck = new BufferedReader(new InputStreamReader(inSocketAck.getInputStream()));
			//Flujo de datos hacia la interfaz de python
			outputData = new DataOutputStream(outSocket.getOutputStream());
			//iniciamos la estructura de almacenamiento de acks de comandos finalizados
			commandAcks = new ArrayList<>();
		} 
		catch (UnknownHostException e)
		{
			System.err.println("Capturada la excepción al crear el socket ");
		}
		catch (IOException e)
		{
			System.err.println("Capturada la excepción al crear el socket ");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(pythonDispatcherThread.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(pythonDispatcherThread.getErrorStream()));

			// read the output from the command
			//System.out.println("Here is the standard output of the command:\n");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			System.out.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		}
		catch (InterruptedException e) 
		{
			System.err.println("Capturada la excepción al esperar ");
		}
	}

	public synchronized void sendCommand(String order) 
	{
		try
		{            
			//Se manda la orden a la interfaz de python
			outputData.writeUTF(order);
			//Thread.sleep(3);
		}
		catch (Exception e)
		{
			System.err.println("Capturada la excepción al mandar la orden a la interfaz python");
		}
	}

	public ArrayList<String> getAcks()
	{
		return commandAcks;
	}

	public void updateAcks()
	{
		try 
		{
			while(inputDataAck.ready())
			{
				commandAcks.add(inputDataAck.readLine());
			}
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeDispatcher()
	{
		try
		{
			//mandamos el mensaje de cierre de conexion
			outputData.writeUTF("end");
			//cerramos el outSocket para finalizar la comunicacion
			outSocket.close();
			inSocket.close();
			//damos tiempo para que se cierre tranquilamente
			Thread.sleep(200);
			//eliminamos los subprocesos si se queda con los ojos para los lados
			//if(pythonDispatcherThread.isAlive())
			//pythonDispatcherThread.destroyForcibly();
		}
		catch (IOException e) 
		{
			System.err.println("Capturada la excepción al cerrar el socket ");
		}
		catch (InterruptedException e) 
		{
			System.err.println("Capturada la excepción al esperar ");
		}
	}

	@Override
	public void receiveCommand() {
		// TODO Auto-generated method stub
		while(true){
			try {
				String msg = inputData.readLine();
				if(msg == null){
					JOptionPane.showMessageDialog(null, "Fallos en la comunicación...");
				}
				else{
					new Parser(msg).start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private class Parser extends Thread{
		private String msg;
		public Parser(String msg){
			this.msg = msg;
		}
		public void run(){
			String[] lines = msg.split("_");
			if(lines[0].equalsIgnoreCase("ap")){
				ArrayList<String> manzanas = new ArrayList<String>();
				ClaseGeneradoraRecursoMalmo.buildInformation(lines[1], manzanas);
				ConseguirInformacionDelEntorno.manzanas = ClaseGeneradoraRecursoMalmo.parseManzanas(manzanas);
			}
			else if(lines[0].equalsIgnoreCase("ag")){
				String [] parts = msg.split("\\[");
				parts = parts[1].split(",");
				agentes.put(lines[1], 
						new Coordinate(
								Double.parseDouble(parts[0]),
								Double.parseDouble(parts[2].split("\\]")[0]),
								Double.parseDouble(parts[1])));
			}
			else if(lines[0].equalsIgnoreCase("eval")){
				if(lines[1].equals("Failed")){
					
				}
				else{
					while(evaluaciones.containsKey(lines[1])){}
					evaluaciones.put(lines[1], Integer.parseInt(lines[2]));						
				}
			}
			//"river agentId agX agZ yaw appleX appleZ"
			else if(lines[0].equalsIgnoreCase("river")){
				try {
					AgenteCognitivotImp2 agenteCoordinador =  (AgenteCognitivotImp2) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz("Itf_Ges_"+lines[1]);
					agenteCoordinador.getControl().insertarHecho(new InformeRio(lines[1],
							new Coordinate(Double.parseDouble(lines[5]), 227, Double.parseDouble(lines[6])),
							new Coordinate(Double.parseDouble(lines[2]), 227, Double.parseDouble(lines[3])),
							Integer.parseInt(lines[4])));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else if(lines[0].equalsIgnoreCase("stone")){
				try {
					AgenteCognitivotImp2 agenteCoordinador =  (AgenteCognitivotImp2) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz("Itf_Ges_" +lines[1]);
					agenteCoordinador.getControl().insertarHecho(new InformePiedra(lines[1],
							new Coordinate(Double.parseDouble(lines[5]), 227, Double.parseDouble(lines[6])),
							new Coordinate(Double.parseDouble(lines[2]), 227, Double.parseDouble(lines[3])),
							Integer.parseInt(lines[4])));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(lines[0].equalsIgnoreCase("success")){
				try {
					System.out.println(lines[1] + ": " + lines[2] + " " + lines[3]);
					AgenteCognitivotImp2 agente =  (AgenteCognitivotImp2) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz("Itf_Ges_"+lines[1]);
					agente.getControl().insertarHecho(new InformeObjetivo(lines[1]));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
