package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

import java.util.StringTokenizer;


/**
 * Define  un objetivo del Agente  Cognitivo 
 */
public  class Objetivo {

    static public final int PENDING = 0;
    static public final int SOLVING = 1;
    static public final int SOLVED = 2;
    static public final int REFINED = 3;
    static public final int FAILED = 4;
    private String goalId;
    private String objectReferenceId ; // identificador del objeto al que se refiere el objetivo p ej el ident de una victima
    private int state;
    private int priority;
    private boolean isfocused;

    public Objetivo() {
        this.state = PENDING;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setRefined() {
        this.setState(REFINED);

    }

    public void setSolved() {
        this.setState(SOLVED);

    }

    public void setPending() {
        this.setState(PENDING);

    }

    public void setSolving() {
        this.setState(SOLVING);

    }

    public void setFailed() {
        this.setState(FAILED);

    }

    public boolean getisFocused (){
        return isfocused;
    }
    public void setisfocused (boolean boolValue){
        this.isfocused = boolValue;
    }

    public int getState() {
        return this.state;
    }

    public synchronized String getgoalId() {
        return this.goalId;
    }

    public synchronized void setgoalId(String id) {
        this.goalId = id;
    }
    public synchronized String getobjectReferenceId() {
        return this.objectReferenceId;
    }

    public synchronized void setobjectReferenceId(String id) {
        this.objectReferenceId = id;
    }

    public String getClassName() {
        return this.getClass().getName();
   }
     public synchronized Integer getPriority() {
        return this.priority;
    }

    public synchronized void setPriority(Integer id) {
        this.priority = id;
    }

//
//    public boolean equals(Object o) {
//        if (o.getClass().equals(this.getClass())) {
//            return ((Objetivo) o).getID().equals(this.getID());
//        }
//        return false;
//    }

    /**
     * Returns the state of this goal
     * @return A string with the name of the state
     */
    public String getStateAsString() {
        String dev = "";

        switch (this.state) {
            case PENDING:
                dev = "PENDING";
                break;
            case REFINED:
                dev = "REFINED";
                break;
            case SOLVING:
                dev = "SOLVING";
                break;
            case SOLVED:
                dev = "SOLVED";
                break;
            case FAILED:
                dev = "FAILED";
                break;
        }
        return dev;
    }

   /**
    * Returns a string with the description of this goal
    */
//    public String toString() {
//        String res = null;
//        StringTokenizer st = new StringTokenizer(this.getClassName(), ".");
//        while (st.hasMoreTokens()) {
//            res = st.nextToken();
//        }
//        return res + " State: " + this.getStateAsString();
//    }

    @Override
    public String toString() {
        String res = null;
        StringTokenizer st = new StringTokenizer(this.getClassName(), ".");
        while (st.hasMoreTokens()) {
            res = st.nextToken();
        }
        return "Objetivo clase: " + res + "("+ this.objectReferenceId + ")" + " State: " + this.getStateAsString();
    }
    
    
}
