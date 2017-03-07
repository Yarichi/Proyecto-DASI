/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.Rosace.informacion;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author FGarijo
 */
@Root
 public class VictimasSalvadas {

//   @Element
//   private String victimasSalvadas;
   private String idVictima;
   private int ultimoIndice=0;
   @ElementList (entry= "victima",inline=true)
   private List<String> listaVictimas;
    
//   public String getlistaVictimas() {
//      return victimasSalvadas;
//   }
   VictimasSalvadas (){
    listaVictimas = new ArrayList<String>();
   }
  
   public List<String> getVictimas() {
      return listaVictimas;
   }
    public void addVictima(String idVictima) {
        if (!listaVictimas.contains(idVictima)){
            listaVictimas.add(ultimoIndice,idVictima);
            ultimoIndice++;
        }
    }
    public String getVictima(int posicion) {
      
        return listaVictimas.get(posicion);
   }
    public void reiniciarVictimas(){
        listaVictimas = new ArrayList<String>();
        ultimoIndice=0;
    }
    }