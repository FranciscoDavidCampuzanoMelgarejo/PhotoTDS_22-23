package umu.tds.vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;

/* 
 * - VENTANA PRINCIPAL DE PHOTOTDS -
 * */

public class PhotoTDSVentana {

	private JFrame frame;
	private LoginRegistroVentana loginRegistroVentana;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatDarculaLaf());
					PhotoTDSVentana window = new PhotoTDSVentana();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PhotoTDSVentana() {
		initialize();
		loginRegistroVentana = new LoginRegistroVentana();
			loginRegistroVentana.mostrar();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
