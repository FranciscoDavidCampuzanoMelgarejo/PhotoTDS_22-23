package umu.tds.vistas.perfil;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import umu.tds.controlador.Controlador;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.ScrollPaneConstants;
import java.awt.GridBagLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class DialogoEditarPerfil extends JDialog {

	private static final int ANCHO_DIALOGO = 690;
	private static final String PANEL_PERFIL = "Panel Perfil";
	private static final String PANEL_PASSWORD = "Panel Password";

	private int altura;

	// Representa el ContentPane del Frame padre
	private Component componente;

	private PanelPerfil panelPerfil;
	private PanelPassword panelPassword;
	private JPanel panelContenido;
	private CardLayout cardLayout;

	public DialogoEditarPerfil(int altura, Component componente, Icon fotoPerfil, String rutaFotoPerfil) {
		this.altura = altura - 40;
		this.componente = componente;
		this.panelPerfil = new PanelPerfil(this, fotoPerfil, rutaFotoPerfil);

		// Viola el patron experto
		this.panelPassword = new PanelPassword(this, Controlador.getControlador().getUsuarioLogueado().getPassword());
		crearDialogo();
	}

	public void mostrarDialogo() {
		setVisible(true);
	}

	public boolean cambiarFotoPerfil() {
		return panelPerfil.cambiarFotoPerfil();
	}

	private void crearDialogo() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setSize(new Dimension(ANCHO_DIALOGO, altura));
		setLocationRelativeTo(componente);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel panelExterior = new JPanel();
		scrollPane.setViewportView(panelExterior);
		GridBagLayout gbl_panelExterior = new GridBagLayout();
		gbl_panelExterior.columnWidths = new int[] { 40, 0, 0, 40, 0 };
		gbl_panelExterior.rowHeights = new int[] { 20, 0, 40, 0 };
		gbl_panelExterior.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelExterior.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panelExterior.setLayout(gbl_panelExterior);

		JPanel panelMenuVertical = new JPanel();
		panelMenuVertical.setBorder(new LineBorder(Color.LIGHT_GRAY));
		GridBagConstraints gbc_panelMenuVertical = new GridBagConstraints();
		gbc_panelMenuVertical.anchor = GridBagConstraints.NORTHWEST;
		gbc_panelMenuVertical.insets = new Insets(0, 0, 5, 0);
		gbc_panelMenuVertical.fill = GridBagConstraints.BOTH;
		gbc_panelMenuVertical.gridx = 1;
		gbc_panelMenuVertical.gridy = 1;
		panelExterior.add(panelMenuVertical, gbc_panelMenuVertical);
		panelMenuVertical.setLayout(new BoxLayout(panelMenuVertical, BoxLayout.Y_AXIS));

		JButton btnCambiarPerfil = new JButton("Cambiar Perfil");
		btnCambiarPerfil.setPreferredSize(new Dimension(160, 40));
		btnCambiarPerfil.setMinimumSize(new Dimension(140, 40));
		btnCambiarPerfil.setMaximumSize(new Dimension(160, 40));
		btnCambiarPerfil.setHorizontalAlignment(SwingConstants.LEFT);
		panelMenuVertical.add(btnCambiarPerfil);

		JButton btnCambiarPassword = new JButton("Cambiar Contrase\u00F1a");
		btnCambiarPassword.setPreferredSize(new Dimension(160, 40));
		btnCambiarPassword.setMinimumSize(new Dimension(140, 40));
		btnCambiarPassword.setMaximumSize(new Dimension(160, 40));
		btnCambiarPassword.setHorizontalAlignment(SwingConstants.LEFT);
		panelMenuVertical.add(btnCambiarPassword);

		panelContenido = new JPanel();
		panelContenido.setBorder(new LineBorder(Color.LIGHT_GRAY));
		GridBagConstraints gbc_panelContenido = new GridBagConstraints();
		gbc_panelContenido.insets = new Insets(0, 0, 5, 0);
		gbc_panelContenido.fill = GridBagConstraints.BOTH;
		gbc_panelContenido.gridx = 2;
		gbc_panelContenido.gridy = 1;
		panelExterior.add(panelContenido, gbc_panelContenido);

		cardLayout = new CardLayout(0, 0);
		panelContenido.setLayout(cardLayout);
		panelContenido.add(panelPerfil, PANEL_PERFIL);
		panelContenido.add(panelPassword, PANEL_PASSWORD);

		btnCambiarPerfil.addActionListener((ActionEvent e) -> {
			cardLayout.show(panelContenido, PANEL_PERFIL);
			panelPassword.limpiar();
		});

		btnCambiarPassword.addActionListener((ActionEvent e) -> {
			cardLayout.show(panelContenido, PANEL_PASSWORD);
			panelPerfil.limpiar();
		});

	}

}
