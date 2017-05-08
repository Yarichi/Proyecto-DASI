package icaro.aplicaciones.recursos.recursoMalmo.imp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

public class prueba {

	public static void main(String[] args) throws InterruptedException, RemoteException 
	{
		ClaseGeneradoraRecursoMalmo asd = new ClaseGeneradoraRecursoMalmo("");
		cargaMinecraft nv;
		for(int i = 0; i < 1; i++)
		{
			nv = new cargaMinecraft();
			nv.start();
			while(!nv.isLoaded());
			System.err.println("termino");
			nv.interrupt();
		}
	}

}
