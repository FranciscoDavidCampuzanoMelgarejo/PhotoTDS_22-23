package umu.tds.modelo.pojos;
import java.time.LocalDate;

import umu.tds.controlador.Controlador;

/* Este descuento quita 5€ si eres menor de 18 años */
public class DescuentoEdad extends Descuento{
	
	@Override
	public float aplicar(float value) {
		LocalDate edad = Controlador.getControlador().getFechaNacimiento();
		return (LocalDate.now().getYear()-edad.getYear() >= 18) ? value-5 : value;
	}
	
	@Override
	public String toString() {
		return "Descuento Edad";
	}
}