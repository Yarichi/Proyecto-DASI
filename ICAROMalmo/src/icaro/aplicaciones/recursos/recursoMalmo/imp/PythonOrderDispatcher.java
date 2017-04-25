package icaro.aplicaciones.recursos.recursoMalmo.imp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class PythonOrderDispatcher implements OrderDispatcher 
{
    protected Socket socket;
    protected DataOutputStream outFlow;
    protected Process pythonDispatcherThread;
    
	public PythonOrderDispatcher(String pythonPath, String pythonScript, int port) throws IOException
	{
		try 
		{
			//Iniciamos el proceso de inicializacion de la parte de python
			String[] command = {pythonPath, pythonScript};
			//pythonDispatcherThread = Runtime.getRuntime().exec(command);
			//damos tiempo para que se inicie tranquilamente
			Thread.sleep(2000);
            //creamos el socket para comunicarnos con la interfaz de python
			socket = new Socket("localhost", port);
	        //Flujo de datos hacia la interfaz de python
	        outFlow = new DataOutputStream(socket.getOutputStream());
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

	public void sendCommand(String order) 
	{
        try
        {            
            //Se manda la orden a la interfaz de python
            outFlow.writeUTF(order);           
        }
        catch (Exception e)
        {
            System.err.println("Capturada la excepción al mandar la orden a la interfaz python");
        }
	}
	
	public void closeDispatcher()
	{
		try
		{
			//mandamos el mensaje de cierre de conexion
			outFlow.writeUTF("end");
			//cerramos el socket para finalizar la comunicacion
			socket.close();
			//damos tiempo para que se cierre tranquilamente
            Thread.sleep(200);
			//eliminamos los subprocesos si se queda con los ojos para los lados
            //if(pythonDispatcherThread.isAlive())
            //	pythonDispatcherThread.destroyForcibly();
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
