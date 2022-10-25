package umu.tds.vistas;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;

/* 
 * - VENTANA PRINCIPAL DE PHOTOTDS -
 * */

public class PhotoTDSVentana extends JFrame {
	
	public final static String PANEL_LOGIN = "Panel de Loging";
	public final static String PANEL_REGISTRO = "Panel de Registro";

	private CardLayout c;
	private PanelLogin panelLogin;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatDarculaLaf());
					PhotoTDSVentana window = new PhotoTDSVentana();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PhotoTDSVentana() {
		super();
		initialize();
		panelLogin = new PanelLogin(this);
		getContentPane().add(panelLogin, PANEL_LOGIN);
		cambiarPanel(PANEL_LOGIN);
	}

	private void initialize() {
		setBounds(100, 100, 640, 480);
		c = new CardLayout();
		getContentPane().setLayout(c);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void cambiarPanel(String nombrePanel) {
		c.show(getContentPane(), nombrePanel);
	}

}
