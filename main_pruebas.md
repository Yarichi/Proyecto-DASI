import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

public class Main {

	public static void main(String[] args) throws IOException
	{
		PythonOrderDispatcher dispatcher = new PythonOrderDispatcher("C:\\Python27\\python", "D:\\codigos_fuente\\jav\\DASI_comunicacion\\src\\icaro_map.py", 9288);
		//dispatcher.sendCommand("prueba 0");

		ClaseGeneradoraRecursoMalmo asd = null;
		try {
			asd = new ClaseGeneradoraRecursoMalmo(null);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(asd.getInformacionManzanas().toString());
		System.out.println(asd.getAgentes().toString());
		System.out.println(asd.getObstaculos().toString());
		
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
