package icaro.aplicaciones.recursos.recursoMalmo.imp;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException
	{
		String rutaIcaroMap = new File("src\\icaro\\aplicaciones\\recursos\\recursoMalmo\\imp\\icaro_map2.py").getAbsolutePath();
		//PythonOrderDispatcher dispatcher = new PythonOrderDispatcher("E:\\JDK\\Python27\\python",rutaIcaroMap, 9288);
		//dispatcher.sendCommand("prueba 0");
		//String input;
		ClaseGeneradoraRecursoMalmo2 asd = new ClaseGeneradoraRecursoMalmo2(null);
		asd.updateInformation();
		asd.dispatcher.sendCommand("up MalmoTutorialBot 12.5 56.5");
        /*String entradaTeclado = "";
        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        while(entradaEscaner.hasNext())
        {
        	input = entradaEscaner.nextLine();
        	System.out.println(input);
        	if(!input.equalsIgnoreCase("exit"))
        		asd.dispatcher.sendCommand(input);
        }*/
		
        //System.out.println(asd.getInformacionManzanas());
		//System.out.println(asd.getInformacionAgentes());
		//System.out.println(asd.getInformacionObstaculos());
		
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		String cadena="";
		try 
		{
			while(!cadena.equalsIgnoreCase("end"))
			{
				cadena = new String(entrada.readLine());
				asd.dispatcher.sendCommand(cadena);
				asd.updateInformation();
				System.out.println(asd.getInformacionAgentes());
			}
		}
		catch (IOException e) 
		{
			System.out.println("Error de E/S");
		} 
		
		//dispatcher.closeDispatcher();
		Thread.sleep(20000);
		asd.close();
	}

}
