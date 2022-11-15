package umu.tds.filtros;

import java.awt.Color;

public interface DocumentFilterListener {
	
	// Notifica que el formato del campo cumple un determinado patron (expresion regular)
	public void notificarFormatoCorrecto();
	
	//Notifica que el formato del campo no cumple un determinado patron (expresion regular)
	public void notificarFormatoErroneo(String mensaje);
	
	//Notifica que el campo esta vacio (se han borrado el contenido del campo)
	public void notificarCampoVacio();
	
	public void notificarRobustezPassword(String mensaje, Color color);

}
