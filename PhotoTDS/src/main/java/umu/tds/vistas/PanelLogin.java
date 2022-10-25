package umu.tds.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import umu.tds.controlador.Controlador;

public class PanelLogin extends JPanel {

	private JTextField campoUsuario;
	private JPasswordField campoPassword;
	private PhotoTDSVentana frame;

	public PanelLogin(PhotoTDSVentana frame) {
		this.frame = frame;
		this.crearPanel();
	}

	private void crearPanel() {
		setLayout(new BorderLayout());

		JPanel panelCentro = new JPanel();
		add(panelCentro, BorderLayout.CENTER);

		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[] { 0, 0 };
		gbl_panelCentro.rowHeights = new int[] { 0, 0 };
		gbl_panelCentro.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelCentro.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelCentro.setLayout(gbl_panelCentro);

		JPanel panelFormulario = new JPanel();

		GridBagConstraints gbc_panelFormulario = new GridBagConstraints();
		gbc_panelFormulario.anchor = GridBagConstraints.NORTH;
		gbc_panelFormulario.insets = new Insets(35, 5, 10, 5);
		gbc_panelFormulario.gridx = 0;
		gbc_panelFormulario.gridy = 0;
		panelCentro.add(panelFormulario, gbc_panelFormulario);

		GridBagLayout gbl_panelFormulario = new GridBagLayout();
		gbl_panelFormulario.columnWidths = new int[] { 40, 300, 40, 0 };
		gbl_panelFormulario.rowHeights = new int[] { 50, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelFormulario.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelFormulario.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelFormulario.setLayout(gbl_panelFormulario);

		JLabel lblNewLabel = new JLabel("PhotoTDS");
		lblNewLabel.setIcon(null);
		lblNewLabel.setForeground(new Color(244, 244, 244));
		lblNewLabel.setFont(new Font("Palatino Linotype", Font.ITALIC, 25));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.insets = new Insets(10, 0, 25, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panelFormulario.add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNombre = new JLabel("Usuario o correo electrónico");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNombre.ipady = 4;
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 0);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 2;
		panelFormulario.add(lblNombre, gbc_lblNombre);

		// Campo del correo o nombre de usuario (primer campo)
		campoUsuario = new JTextField();
		campoUsuario.setColumns(30);
		GridBagConstraints gbc_loginUser = new GridBagConstraints();
		gbc_loginUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_loginUser.ipady = 2;
		gbc_loginUser.insets = new Insets(0, 0, 15, 0);
		gbc_loginUser.gridx = 1;
		gbc_loginUser.gridy = 3;
		panelFormulario.add(campoUsuario, gbc_loginUser);

		JLabel lblPassword = new JLabel("Contraseña");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblContrasea.ipady = 4;
		gbc_lblContrasea.anchor = GridBagConstraints.WEST;
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 0);
		gbc_lblContrasea.gridx = 1;
		gbc_lblContrasea.gridy = 4;
		panelFormulario.add(lblPassword, gbc_lblContrasea);

		// Campo de la contraseña
		campoPassword = new JPasswordField();
		campoPassword.setColumns(30);
		GridBagConstraints gbc_loginPasswd = new GridBagConstraints();
		gbc_loginPasswd.fill = GridBagConstraints.HORIZONTAL;
		gbc_loginPasswd.ipady = 2;
		gbc_loginPasswd.insets = new Insets(0, 0, 25, 0);
		gbc_loginPasswd.gridx = 1;
		gbc_loginPasswd.gridy = 5;
		panelFormulario.add(campoPassword, gbc_loginPasswd);

		// Boton de Inicio de Sesion
		JButton btnIniciarSesion = new JButton("Iniciar Sesión");
		btnIniciarSesion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnIniciarSesion.setForeground(new Color(233, 233, 233));
		btnIniciarSesion.setBackground(new Color(0, 126, 187));
		GridBagConstraints gbc_btnIniciarSesion = new GridBagConstraints();
		gbc_btnIniciarSesion.ipady = 5;
		gbc_btnIniciarSesion.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnIniciarSesion.insets = new Insets(0, 0, 5, 0);
		gbc_btnIniciarSesion.gridx = 1;
		gbc_btnIniciarSesion.gridy = 6;
		panelFormulario.add(btnIniciarSesion, gbc_btnIniciarSesion);

		btnIniciarSesion.addActionListener((ActionEvent e) -> {
			String usuario = campoUsuario.getText().trim();
			String password = String.valueOf(campoPassword.getPassword()).trim();
			System.out.println("Usuario: " + usuario);
			System.out.println("Contraseña: " + password);
			boolean noVacios = true;

			if (usuario.isEmpty()) {
				noVacios = false;
				System.out.println("Campo del usuario vacio");
				// Indicar al usuario que este campo no puede estar vacio
			}

			if (password.isEmpty()) {
				noVacios = false;
				System.out.println("Campo de la contraseña vacio");
				// Inciar al usuario que este campo no puede estar vacio
			}

			/*
			 * Incluir otras comprobaciones: -> Que el campo usuario cumpla un patron
			 * (nombres o correos electronicos) -> Que el campo usuario deba estar entre una
			 * longitud minima y maxima -> Que la contraseña deba estrar entre una longitud
			 * minima y maxima -> Que la contraseña deba contener una mayuscula y un numero
			 * .......
			 */

			if (noVacios) {
				// Si no he podido loguearme (no existe el usuario)
				if (!Controlador.getControlador().loginUsuario(usuario, password)) {
					System.out.println("No existe el usuario: " + usuario);
					// Indicar al cliente que no existe ningun usuario con esas credenciales
				} else {
					System.out.println("Exito al loguearse");
					// Indicar al cliente que se ha logueado exitosamente
					// Pasar al panel principal de la aplicacion
				}
			}

		});

		// Panel en el que hay una etiqueta para crear una cuenta
		JPanel panelCrearCuenta = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelCrearCuenta.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelCrearCuenta = new GridBagConstraints();
		gbc_panelCrearCuenta.insets = new Insets(0, 0, 0, 5);
		gbc_panelCrearCuenta.fill = GridBagConstraints.BOTH;
		gbc_panelCrearCuenta.gridx = 1;
		gbc_panelCrearCuenta.gridy = 7;
		panelFormulario.add(panelCrearCuenta, gbc_panelCrearCuenta);

		JLabel lblnoTienesCuenta = new JLabel("¿No tienes cuenta?");
		panelCrearCuenta.add(lblnoTienesCuenta);

		JLabel lblRegstrate = new JLabel("Regístrate");
		lblRegstrate.setForeground(new Color(0, 128, 255));
		lblRegstrate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRegstrate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.cambiarPanel(PhotoTDSVentana.PANEL_REGISTRO);
			}
		});
		panelCrearCuenta.add(lblRegstrate);

	}

}
