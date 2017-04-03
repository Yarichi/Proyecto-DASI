import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PythonOrderDispatcher implements OrderDispatcher 
{
    protected Socket socket;
    protected DataOutputStream outFlow;
    protected Process pythonDispatcherThread;
    
	public PythonOrderDispatcher(String pythonPath, int port)
	{
		try 
		{
			//Iniciamos el proceso de inicializacion de la parte de python
			String[] command = {pythonPath, "src\\icaro\\aplicaciones\\recursos\\recursoMalmo\\imp\\OrderServer.py"};
            pythonDispatcherThread = Runtime.getRuntime().exec(command);
			//damos tiempo para que se inicie tranquilamente
            Thread.sleep(200);
            //creamos el socket para comunicarnos con la interfaz de python
			socket = new Socket("127.0.0.1", port);
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
			if(pythonDispatcherThread.isAlive())
				pythonDispatcherThread.destroyForcibly();
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
