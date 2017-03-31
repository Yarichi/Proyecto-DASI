package comunicacionPython.java;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PythonOrderDispatcher implements OrderDispatcher 
{
    protected Socket socket;
    protected DataOutputStream outFlow;
    
	public PythonOrderDispatcher(int port)
	{
		try 
		{
			//creamos el socket para comunicarnos con la interfaz de python
			this.socket = new Socket("127.0.0.1", port);
	        //Flujo de datos hacia la interfaz de python
	        this.outFlow = new DataOutputStream(socket.getOutputStream());
		} 
		catch (UnknownHostException e)
		{
			System.err.println("Capturada la excepción al crear el socket ");
		}
		catch (IOException e)
		{
			System.err.println("Capturada la excepción al crear el socket ");
		}
	}

	public void sendCommand(String order) 
	{
        try
        {            
            //Se manda la orden a la interfaz de python
            this.outFlow.writeUTF(order);           
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
			this.outFlow.writeUTF("end");
			//cerramos el socket para finalizar la comunicacion
			socket.close();
		}
		catch (IOException e) 
		{
			System.err.println("Capturada la excepción al cerrar el socket ");
		}
	}
	
	
}
