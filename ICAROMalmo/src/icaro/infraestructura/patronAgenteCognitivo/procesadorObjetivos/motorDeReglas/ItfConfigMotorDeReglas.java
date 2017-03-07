/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.patronAgenteCognitivo.procesadorObjetivos.motorDeReglas;

/**
 *
 * @author FGarijo
 */
public interface ItfConfigMotorDeReglas {
  public void setDepuracionActivationRulesDebugging (boolean boolValor);
  public void setDepuracionHechosInsertados (boolean boolValor); // traza los hechos insertados en el motor
  public void setDepuracionHechosModificados (boolean boolValor);// traza los hechos modificados en el motor
  public void setFactHandlesMonitoring_beforeActivationFired_DEBUGGING (boolean boolValor);
  public void setfactHandlesMonitoring_afterActivationFired_DEBUGGING (boolean boolValor);
  public void setFactHandlesMonitoring_DEBUGGING (boolean boolValor);
  public void setFactHandlesMonitoringINSERT_DEBUGGING (boolean boolValor);
  public void setFactHandlesMonitoringRETRACT_DEBUGGING (boolean boolValor);
  public void setFactHandlesMonitoringUPDATE_DEBUGGING (boolean boolValor);
}
