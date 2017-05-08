package icaro.aplicaciones.recursos.recursoMalmo.imp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class cargaMinecraft extends Thread
{
	protected final static int trys = 20;
	protected int checkInteger, currentInteger, countInteger;
	protected Process pythonDispatcherThread;
	
	public cargaMinecraft()
	{
		checkInteger = -1;
		currentInteger = 0;
		countInteger = 0;
	}
	
	public boolean isLoaded()
	{
		if(checkInteger == currentInteger)
		{
			try 
			{
				Thread.sleep(500);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			countInteger++;
			if(countInteger == trys)
			{
				
				return true;
			}
			else
				return false;
		}
		else
		{
			checkInteger = currentInteger;
			countInteger = 0;
			return false;
		}
	}
	
	public void release()
	{
		pythonDispatcherThread.destroyForcibly();
	}
	
	public void run() 
	{
		String s;
		try 
		{
			pythonDispatcherThread = Runtime.getRuntime().exec("lanza_minecraft.bat");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(pythonDispatcherThread.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(pythonDispatcherThread.getErrorStream()));

			s = null;
			while ((s = stdInput.readLine()) != null)
			{
			    currentInteger++;
			    System.out.println(s);
			}
			
			System.out.println("Here is the standard error of the command (if any):\n");
			while ((s = stdError.readLine()) != null)
			{
			    currentInteger++;
			    System.out.println(s);
			}
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
