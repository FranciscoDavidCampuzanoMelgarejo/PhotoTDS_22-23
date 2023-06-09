package umu.tds.modelo.pojos;

/**
 * @title Clase abstracta descuento
 * @author francisco&diego
 * */
public abstract class Descuento {
	public abstract float aplicar(float value);
	public abstract String toString();
}
