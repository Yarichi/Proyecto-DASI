/*
 *  Copyright 2001 Telefnica I+D
 *
 *
 *  All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.comunicacion;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *  Ttulo: Descripcion: Copyright: Copyright (c) 2001 Empresa:
 *
 *@author
 *@created    11 de septiembre de 2001
 *@modified	  20 de junio de 2006
 *@version    2.0
 */

public class SimpleEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private String msg;

	@SuppressWarnings("unchecked")
	private Hashtable properties = new Hashtable();

	private String origen;

	public SimpleEvent(String msg, String origen ) {

		this.msg = msg;
		this.origen = origen;
	}

	public SimpleEvent(String msg, String origen, Hashtable properties) {
		this.msg = msg;
		if (properties != null) {
			String key;
			Object value;
			for (Enumeration e = properties.keys(); e.hasMoreElements(); this.properties
					.put(key, value)) {
				key = (String) e.nextElement();
				value = properties.get(key);
			}

		}
		this.origen = origen;
		this.properties.put("event.sender", this.getOrigen());
	}



    /**
	 * @return  Returns the msg.
	 * @uml.property  name="msg"
	 */
	public String getMsg() {
		return msg;
	}


	/**
	 * @param msg  The msg to set.
	 * @uml.property  name="msg"
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * get the properties.
	 * 
	 * @return the properties.
	 */
	public final Dictionary properties() {
		return properties;
	}

	/**
	 * get a certain property.
	 * 
	 * @param name
	 *            the property name.
	 * @return the value.
	 */
	public final Object getProperty(final String name) {
		return properties.get(name);
	}

	/**
	 * set a certain property
	 * @param eventTime
	 * @param string
	 */
	@SuppressWarnings("unchecked")
	public void setProperty(String name, Object obj) {
		this.properties.put(name, obj);
		
	}
	
	/**
	 * get the property names.
	 * 
	 * @return the property names.
	 */
	public final String[] getPropertyNames() {
		String names[] = new String[properties.size()];
		Enumeration keys = properties.keys();
		for (int i = 0; keys.hasMoreElements(); i++) {
			names[i] = (String) keys.nextElement();
		}

		return names;
	}

	/**
	 * @return
	 * @uml.property  name="origen"
	 */
	public String getOrigen(){
		if (origen!=null)

			return origen;
		else
			return "no especificado";
	}

	/**
	 * @param origen
	 * @uml.property  name="origen"
	 */
	public void setOrigen(String origen){
		this.origen = origen;
	}



	public String toString() {
		StringBuffer sb = new StringBuffer(this.msg);
		sb.append(" [");
		Enumeration keys = properties.keys();
		for (int i = 0; keys.hasMoreElements(); i++) {
			String key = (String) keys.nextElement();
			sb.append(key);
			sb.append("=");
			sb.append( properties.get(key) );
			sb.append(", ");
		}
        return sb.toString();
    }

	public boolean tieneParams() {
		return properties != null && properties.size() != 0;
	}



}
