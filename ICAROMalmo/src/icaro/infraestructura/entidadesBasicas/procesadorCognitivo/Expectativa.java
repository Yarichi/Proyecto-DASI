package icaro.infraestructura.entidadesBasicas.procesadorCognitivo;

public abstract class Expectativa {
	private String idAgtePropietario;
	private Object objetosEsperados;
	private Tarea tareaGeneradora;
	private String descripcion;
        private boolean estatusSatisfaccion;	
	
	public void setidAgtePropietario (String identAgte){
            idAgtePropietario = identAgte;
        }
        public String  getidAgtePropietario (){
           return idAgtePropietario ;
        }
        public Object getobjetosEsperados() {
		return objetosEsperados;
	}
	public void setobjetosEsperados(Object objetInfo) {
		this.objetosEsperados = objetInfo;
	}
	public Tarea getTarea() {
		return tareaGeneradora;
	}
	public void setTarea(Tarea tarea) {
		this.tareaGeneradora = tarea;
	}
	public String getDescripcion() {
		return descripcion;
	}
        public void setestatusSatisfaccion(boolean satisfStatus) {
		this.estatusSatisfaccion = satisfStatus;
	}
	public boolean getestatusSatisfaccion() {
		return estatusSatisfaccion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
        public abstract void validarExpectativa(Object... params) ;
        
  //     public void  notificarCambiosAlMotorDeReglas(){
           
  //    }
	
	
}
