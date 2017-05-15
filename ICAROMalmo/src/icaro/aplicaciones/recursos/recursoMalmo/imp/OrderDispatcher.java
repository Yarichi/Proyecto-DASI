package icaro.aplicaciones.recursos.recursoMalmo.imp;

public interface OrderDispatcher 
{
	public abstract void sendCommand(String order);
	public  abstract void receiveCommand();
}
