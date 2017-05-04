package icaro.aplicaciones.recursos.recursoMalmo.imp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PythonOrderDispatcher implements OrderDispatcher 
{
	private ServerSocket serversocket, serversocketAck;
	private Socket inSocket, inSocketAck, outSocket;
	BufferedReader inputData, inputDataAck;
    protected DataOutputStream outputData;
    protected Process pythonDispatcherThread;
    protected ArrayList<String> commandAcks;
    
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

	public synchronized String sendCommand(String order) 
	{
		String returnValue = "";
        try
        {            
            //Se manda la orden a la interfaz de python
            outputData.writeUTF(order);
            returnValue = inputData.readLine();
        }
        catch (Exception e)
        {
            System.err.println("Capturada la excepción al mandar la orden a la interfaz python");
            returnValue = "error";
        }
        return returnValue;
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
	
	
}
