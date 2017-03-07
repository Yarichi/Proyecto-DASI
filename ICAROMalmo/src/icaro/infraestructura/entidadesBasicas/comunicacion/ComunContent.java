/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.comunicacion;

/**
 *
 * @author FGarijo
 * Clase basica par definir contenidos de comunicación que se incluirán en los mensajes entre agentes
 */
public class ComunContent {
        public static final int UNKNOWN = -1;
	public static final int AGREE = 1;
	public static final int CONFIRM = 4;
	public static final int DISCONFIRM = 5;
	public static final int FAILURE = 6;
	public static final int INFORM = 7;
        public static final int NOT_UNDERSTOOD = 10;
        public static final int PROPOSE = 11;
	public static final int REFUSE = 14;
	public static final int REQUEST = 16;
        public static final int REJECT_PROPOSAL = 15;

	private int identPerformativa;
 

//protected String  nombrePerformativa = null;
protected String  nombreOperacion = null;
protected Object parametros = null;

    
  public ComunContent (int performativa, Object parametros ){
  this.identPerformativa = performativa;
  this.parametros= parametros;
}
public ComunContent (int performativa,String operacion, Object parametros ){
  this.identPerformativa = performativa;
  this.nombreOperacion = operacion;
  this.parametros= parametros;
}
 public Object getParametros() {
        return parametros;
    }
public int getnombrePerformativa() {
        return identPerformativa;
    }
public String getnombreOperacion() {
        return nombreOperacion;
    }
public void  nombrePerformativa(int nombre) {
        this.identPerformativa = nombre;
    }
void  setnombreOperacion(String nombreOperacion) {
        this.nombreOperacion = nombreOperacion;
    }
void  setParametros(Object parametros) {
        this.parametros = parametros;
    }
public String getNamePerformative() {
		switch (identPerformativa) {
		case UNKNOWN:
			return "UNKNOWN";
		case AGREE:
			return "AGREE";
		case CONFIRM:
			return "CONFIRM";
		case REFUSE:
			return "REFUSE";
		case FAILURE:
			return "FAILURE";
		case DISCONFIRM:
			return "DISCONFIRM";
		case INFORM:
			return "INFORM";
		case REQUEST:
			return "REQUEST";
		default:
			return "UNKNOWN";
		}
    }
public boolean esPerformativa ( int potencialPerformativa){

    switch (potencialPerformativa) {
		case UNKNOWN:
			return true;
		case AGREE:
			return true;
		case CONFIRM:
			return true;
		case REFUSE:
			return true;
		case FAILURE:
			return true;
		case DISCONFIRM:
			return true;
		case INFORM:
			return true;
		case REQUEST:
			return true;
		default:
			return false;
		}

    }
   

}

