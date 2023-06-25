package umu.tds.vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import umu.tds.modelo.pojos.*;
import umu.tds.vistas.perfil.PanelPerfilUsuario;

public class VentanaPerfil implements ActionListener{
	private Usuario usuario;
	private JFrame frame;

	public void mostrar() {
		frame.setVisible(true);
	}
	
	public void destruir() {
		frame.dispose();
	}
	
	/* Constructor */
	public VentanaPerfil(Usuario usuario) {
		this.usuario = usuario;
		dibujar();
	}

	/* Dibujado */
	private void dibujar() {
		frame = new JFrame();
		frame.setTitle("Perfil de " + usuario.getUsuario());
		frame.setBounds(100, 100, 640, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		PanelPerfilUsuario ppu = new PanelPerfilUsuario(frame,  usuario);
		ppu.addActionListener(this);
		frame.getContentPane().add(ppu, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent arg0) {
		frame.dispose();
	}

}
