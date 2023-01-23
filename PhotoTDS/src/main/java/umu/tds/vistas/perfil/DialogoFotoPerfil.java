package umu.tds.vistas.perfil;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import umu.tds.vistas.Utils;

import javax.swing.JLabel;

public class DialogoFotoPerfil extends JDialog implements ActionListener {

	private static final double ESCALA_FOTO_PERFIL = 0.333; // Al clickar en la foto de perfil, esta debe ocupar 1/3 de
															// la pantalla

	private Rectangle bounds;
	private Icon fotoPerfil;
	private Image iconoBoton;

	public DialogoFotoPerfil(Rectangle bounds, Icon fotoPerfil) {
		this.bounds = bounds;
		this.fotoPerfil = fotoPerfil;
		this.iconoBoton = new ImageIcon(getClass().getResource("/imagenes/cross-dialogo.png")).getImage()
				.getScaledInstance(60, 60, Image.SCALE_SMOOTH);

		crearDialogo();
	}

	public void mostrarDialogo() {
		setVisible(true);
	}

	private void crearDialogo() {
		this.setBounds(bounds);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setResizable(false);
		this.getRootPane().setOpaque(false);
		this.setUndecorated(true);
		// this.getContentPane().setBackground(new Color(0, 0, 0, 200));
		this.setBackground(new Color(0, 0, 0, 200));

		JPanel panelNorte = new JPanel();
		getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));

		Component verticalStrut = Box.createVerticalStrut(10);
		panelNorte.add(verticalStrut);

		JButton btnCerrarDialogo = new JButton(new ImageIcon(iconoBoton));
		btnCerrarDialogo.setOpaque(false);
		btnCerrarDialogo.setContentAreaFilled(false);
		btnCerrarDialogo.setBorderPainted(false);
		btnCerrarDialogo.setHorizontalAlignment(SwingConstants.LEFT);
		btnCerrarDialogo.setAlignmentX(0.05f);
		btnCerrarDialogo.addActionListener(this);
		panelNorte.add(btnCerrarDialogo);

		int anchoImagen = (int) (ESCALA_FOTO_PERFIL * bounds.getSize().height);
		BufferedImage masked = Utils.redondearImagen(anchoImagen, fotoPerfil);
		JLabel lblFotoPerfil = new JLabel(new ImageIcon(masked));
		lblFotoPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblFotoPerfil, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}

}
