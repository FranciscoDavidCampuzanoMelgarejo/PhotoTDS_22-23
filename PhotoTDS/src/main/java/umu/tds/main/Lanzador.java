package umu.tds.main;

import java.awt.EventQueue;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarculaLaf;
import umu.tds.controlador.Controlador;
//import umu.tds.modelo.catalogos.*;
//import umu.tds.modelo.pojos.*;
import umu.tds.vistas.*;

/**
 * -- CLASE PRINCIPAL DE PHOTOTDS --
 * **/


public class Lanzador {
	
	public static void main(final String[] args) {		
		EventQueue.invokeLater(new Runnable() {	
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatDarculaLaf());
					/* -- La ventana de registro lanza la ventana principal una vez se ha logueado correctamente -- */
					VentanaLoginRegistro vlr = new VentanaLoginRegistro();
					vlr.mostrar();
						
				} catch (Exception e) { e.printStackTrace(); }
			}
		});
		
	}

}
