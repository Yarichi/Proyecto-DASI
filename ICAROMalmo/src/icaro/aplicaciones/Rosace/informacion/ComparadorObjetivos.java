/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import java.util.Comparator;

/**
 *
 * @author FGarijo
 */
public class ComparadorObjetivos implements Comparator<Objetivo> {

    @Override
    public int compare(Objetivo o1, Objetivo o2) {
        if(o1.getgoalId().equals(o2.getgoalId()))return -1;
        return 0;
    }
    
}
