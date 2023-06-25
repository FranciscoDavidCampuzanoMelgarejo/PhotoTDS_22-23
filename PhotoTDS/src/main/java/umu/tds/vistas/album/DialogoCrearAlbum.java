package umu.tds.vistas.album;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DialogoCrearAlbum extends JDialog {
	
	private final int anchoDialogo, altoDialogo;
	private JPanel panelIzquierdo, panelDerecho;
	
	public DialogoCrearAlbum(JFrame frame) {
		super(frame, true);
		setLocationRelativeTo(frame);
		this.anchoDialogo = (int) Toolkit.getDefaultToolkit().getScreenSize().width - 300;
		this.altoDialogo = (int) Toolkit.getDefaultToolkit().getScreenSize().height - 200;
		
		inicializar();
	}
	
	private void inicializar() {
		setResizable(false);
		setSize(anchoDialogo, altoDialogo);
		getContentPane().setLayout(new GridLayout(1, 2, 5, 0));
		panelDerecho = new PanelFotosAlbum((int)anchoDialogo/8 - 10, (int)altoDialogo/4);
		panelIzquierdo = new PanelDescripcionAlbum(panelDerecho);
		
		
		getContentPane().add(panelIzquierdo);
		getContentPane().add(panelDerecho);
		
		setVisible(false);
	}
	
	public void mostrar() {
		setVisible(true);
	}

}
