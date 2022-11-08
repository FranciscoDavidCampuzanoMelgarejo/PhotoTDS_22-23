package umu.tds.main;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;

import umu.tds.vistas.VentanaLoginRegistro;

public class Lanzador {

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatDarculaLaf());
					VentanaLoginRegistro ventana = new VentanaLoginRegistro();
					ventana.mostrar();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}

}
