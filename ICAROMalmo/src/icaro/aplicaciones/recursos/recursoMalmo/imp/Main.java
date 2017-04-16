package icaro.aplicaciones.recursos.recursoMalmo.imp;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

public class Main {

	public static void main(String[] args) throws IOException
	{
		String rutaIcaroMap = new File("src\\icaro\\aplicaciones\\recursos\\recursoMalmo\\imp\\icaro_map2.py").getAbsolutePath();
		PythonOrderDispatcher dispatcher = new PythonOrderDispatcher("C:\\Python27\\python",rutaIcaroMap, 9288);
		//dispatcher.sendCommand("prueba 0");

		ClaseGeneradoraRecursoMalmo asd = null;
		try {
			asd = new ClaseGeneradoraRecursoMalmo(null);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(asd.getInformacionManzanas());
		System.out.println(asd.getInformacionAgentes());
		System.out.println(asd.getInformacionObstaculos());
		
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		String cadena="";
		try 
		{
			while(!cadena.equalsIgnoreCase("end"))
			{
				cadena = new String(entrada.readLine());
				dispatcher.sendCommand(cadena);
			}
		}
		catch (IOException e) 
		{
			System.out.println("Error de E/S");
		} 
		
		dispatcher.closeDispatcher();
	}

}
