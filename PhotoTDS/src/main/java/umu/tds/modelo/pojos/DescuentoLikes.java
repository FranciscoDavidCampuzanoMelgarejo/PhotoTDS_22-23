package umu.tds.modelo.pojos;
import umu.tds.controlador.Controlador;

/* Este descuento quita 1€ por cada 20 likes */
public class DescuentoLikes extends Descuento{
	
	@Override
	public float aplicar(float value) {
		long nlikes = Controlador.getControlador().numeroLikes();
		return value - (nlikes/20);
	}
	
	@Override
	public String toString() {
		return "Descuento Likes";
	}
}
