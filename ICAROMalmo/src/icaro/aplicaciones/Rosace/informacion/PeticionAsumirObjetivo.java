/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import java.io.Serializable;

/**
 *
 * @author FGarijo
 * Se manda como justificacion el estatus del robot que hace la propuesta
 */
public class PeticionAsumirObjetivo extends PeticionAgente implements Serializable{
    private Objetivo objetivoTransferir;
    private Object infoComplementaria;
   public PeticionAsumirObjetivo (String idAgteEmisor, Objetivo objtivoAtransferir, RobotStatus1 justificacion){
       super(idAgteEmisor);
       super.setMensajePeticion(VocabularioRosace.MsgePeticionAsumirObjetivo);
       super.setJustificacion(justificacion);
       super.setidentObjectRefPeticion(objtivoAtransferir.getobjectReferenceId());
       objetivoTransferir = objtivoAtransferir;
   }
   public Objetivo getObjetivoTransferir(){
       return objetivoTransferir;
   }
   public void setinfoComplementaria(Object complInfo){
       infoComplementaria=complInfo;
   }
   public Object getinfoComplementaria(){
       return infoComplementaria;
   }
}
