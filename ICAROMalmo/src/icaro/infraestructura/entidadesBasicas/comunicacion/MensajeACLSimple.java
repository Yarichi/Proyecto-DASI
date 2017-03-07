/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.entidadesBasicas.comunicacion;
/*
**
 * @author Damiano Spina
 * @version 1.0
 * @created 21-ago-2008 10:16:59
 */
public class MensajeACLSimple extends MensajeSimple {

	public static final int UNKNOWN = -1;
	public static final int AGREE = 1;
	public static final int CONFIRM = 4;
	public static final int DISCONFIRM = 5;
	public static final int FAILURE = 6;
	public static final int INFORM = 7;
	public static final int REFUSE = 14;
	public static final int REQUEST = 16;

	private int performative;
	private String source;
	private String dest;
	private Object content;
	private String conversation_id;

	public MensajeACLSimple(){

	}

	public MensajeACLSimple(int performative) {
		this.performative = performative;
	}

	public int getPerformative() {
		return performative;
	}

	public void setPerformative(int performative) {
		this.performative = performative;
	}

	public String getNamePerformative() {
		switch (performative) {
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public String getConversation_id() {
		return conversation_id;
	}

	public void setConversation_id(String conversation_id) {
		this.conversation_id = conversation_id;
	}

}