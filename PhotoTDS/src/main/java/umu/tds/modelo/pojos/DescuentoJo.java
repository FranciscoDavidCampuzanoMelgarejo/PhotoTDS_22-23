package umu.tds.modelo.pojos;
import umu.tds.controlador.Controlador;

/* Este descuento quita 1€ por cada 20 likes */
public class DescuentoJo extends Descuento{
	
	@Override
	public float aplicar(float value) {
		int seguidores = Controlador.getControlador().getSeguidores();
		return (seguidores == 0) ? value * 0.27f : value; 
	}
	
	@Override
	public String toString() {
		return "Descuento por no tener amigos ;-;";
	}
}