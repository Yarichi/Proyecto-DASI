package icaro.aplicaciones.recursos.recursoVisualizadorEntornosSimulacion.imp;

public class utilsCadenaComponente {

	//El string s finaliza en un numero
	//El metodo devuelve la posicion en la que empieza el numero
    public static int getNumberStartIndex(String s){
    	
    	int index=0;
    	
    	for (int x=s.length()-1;x>=0;x--){
    		char ch = s.charAt(x);
    		String sch = "" + ch;
    		int chint = (int)ch;    		
    		int numberchint = chint - 48; //48 es el valor ascii del 0

    		if ((numberchint<0) || (numberchint >= 10)) //no es un numero
    		   {   
    			  return x+1;
    		}                   		
    	}
    	return index;
    }

    //El string finaliza en un numero
    //El metodo devuelve el substring que contiene el numero
    public static String getNumber(String s, int index){
    	String stringNumber;
    	stringNumber = s.substring(index);    	    	
    	return stringNumber;
    }

    
	//El string s es un numero
	//El metodo devuelve la posicion en la que empieza el primer digito distinto de cero
    public static int getNumberStartIndexPrimerDigitoDistintoCero(String s){    	
    	int index=0;    	
    	//System.out.println("Cadena->" + s + " , longitud->" + s.length());    	
       	for (int x=0;x<=s.length()-1;x++){       		
    		char ch = s.charAt(x);
    		String sch = "" + ch;
    		int chint = (int)ch;    		
    		int numberchint = chint - 48; //48 es el valor ascii del 0    		
        	//System.out.println("numberchint->" + numberchint);       	
    		if ((numberchint<0) || (numberchint >= 10)) //no es un numero
 		    {
    			;
 		    }
    		else //es un numero   		
    			if (numberchint!=0){    			
    				index = x;
    				break;
    			}    			
    	}       	       	
    	//System.out.println("Pos Cadena->" + index);       	
    	return index;
    }


    //El string finaliza en un numero
    //El metodo devuelve el substring que contiene el numero sin ceros a la izquierda
    public static String getNumberSinCerosAlaIzquierda(String s, int index){
    	String stringNumber;
    	stringNumber = s.substring(index);    	    	
    	return stringNumber;
    }
            	
}
