/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.pruebas;

import icaro.infraestructura.entidadesBasicas.informes.Informe;

/**
 *
 * @author FGarijo
 */
public class InformeArranqueGestor extends Informe{
//  public  String contenidoInforme;
    public InformeArranqueGestor (String  identEmisor,String contenido){
        super(identEmisor,contenido);
        contenidoInforme = contenido;
        
    }
  @Override
    public String getContenidoInforme(){
    return (String)super.getContenidoInforme();
    }
    
}
