package umu.tds.vistas;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import umu.tds.controlador.Controlador;

public class PanelLogin extends JPanel {

	private JLabel lblErrorUsuario;
	private JTextField campoUsuario;

	private JLabel lblErrorPassword;
	private JPasswordField campoPassword;
	
	private JFrame frmLoginRegistro;


	public PanelLogin(JFrame frame) {
		this.frmLoginRegistro = frame;
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
		gbl_panelFormulario.columnWidths = new int[] { 40, 340, 40, 0 };
		gbl_panelFormulario.rowHeights = new int[] { 50, 0, 0, 0, 0, 0, 0, 20, 0 };
		gbl_panelFormulario.columnWeights = new double[] { 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelFormulario.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelFormulario.setLayout(gbl_panelFormulario);

		JLabel lblNewLabel = new JLabel("PhotoTDS");
		lblNewLabel.setIcon(null);
		lblNewLabel.setForeground(new Color(244, 244, 244));
		lblNewLabel.setFont(new Font("Palatino Linotype", Font.ITALIC, 25));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(10, 0, 25, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panelFormulario.add(lblNewLabel, gbc_lblNewLabel);

		JPanel panelCampoUsuario = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelCampoUsuario.getLayout();
		flowLayout_1.setHgap(0);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelCampoUsuario = new GridBagConstraints();
		gbc_panelCampoUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_panelCampoUsuario.fill = GridBagConstraints.BOTH;
		gbc_panelCampoUsuario.gridx = 1;
		gbc_panelCampoUsuario.gridy = 2;
		panelFormulario.add(panelCampoUsuario, gbc_panelCampoUsuario);

		JLabel lblUsuario = new JLabel("Usuario o Correo Electrónico");
		lblUsuario.setFont(new Font("Georgia", Font.PLAIN, 13));
		panelCampoUsuario.add(lblUsuario);

		lblErrorUsuario = new JLabel("");
		lblErrorUsuario.setForeground(new Color(255, 106, 106));
		lblErrorUsuario.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblErrorUsuario.setVisible(false);
		panelCampoUsuario.add(lblErrorUsuario);

		// Campo del correo o nombre de usuario (primer campo)
		campoUsuario = new JTextField();
		campoUsuario.setFont(new Font("Georgia", Font.PLAIN, 13));
		campoUsuario.setColumns(30);
		GridBagConstraints gbc_loginUser = new GridBagConstraints();
		gbc_loginUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_loginUser.ipady = 4;
		gbc_loginUser.insets = new Insets(0, 0, 15, 5);
		gbc_loginUser.gridx = 1;
		gbc_loginUser.gridy = 3;
		panelFormulario.add(campoUsuario, gbc_loginUser);

		JPanel panelCampoPassword = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelCampoPassword.getLayout();
		flowLayout_2.setHgap(0);
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelCampoPassword = new GridBagConstraints();
		gbc_panelCampoPassword.insets = new Insets(0, 0, 5, 5);
		gbc_panelCampoPassword.fill = GridBagConstraints.BOTH;
		gbc_panelCampoPassword.gridx = 1;
		gbc_panelCampoPassword.gridy = 4;
		panelFormulario.add(panelCampoPassword, gbc_panelCampoPassword);

		JLabel lblPassword = new JLabel("Contraseña");
		lblPassword.setFont(new Font("Georgia", Font.PLAIN, 13));
		panelCampoPassword.add(lblPassword);

		lblErrorPassword = new JLabel("");
		lblErrorPassword.setForeground(new Color(255, 106, 106));
		lblErrorPassword.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblErrorPassword.setVisible(false);
		panelCampoPassword.add(lblErrorPassword);

		// Campo de la contraseña
		campoPassword = new JPasswordField();
		campoPassword.setFont(new Font("Georgia", Font.PLAIN, 13));
		campoPassword.setColumns(30);
		GridBagConstraints gbc_loginPasswd = new GridBagConstraints();
		gbc_loginPasswd.fill = GridBagConstraints.HORIZONTAL;
		gbc_loginPasswd.ipady = 4;
		gbc_loginPasswd.insets = new Insets(0, 0, 15, 5);
		gbc_loginPasswd.gridx = 1;
		gbc_loginPasswd.gridy = 5;
		panelFormulario.add(campoPassword, gbc_loginPasswd);

		// Boton de Inicio de Sesion
		JButton btnIniciarSesion = new JButton("Iniciar Sesión");
		btnIniciarSesion.setFont(new Font("Georgia", Font.PLAIN, 14));
		btnIniciarSesion.setForeground(new Color(200, 200, 200));
		btnIniciarSesion.setBackground(new Color(28, 84, 215));
		GridBagConstraints gbc_btnIniciarSesion = new GridBagConstraints();
		gbc_btnIniciarSesion.ipady = 8;
		gbc_btnIniciarSesion.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnIniciarSesion.insets = new Insets(0, 0, 5, 5);
		gbc_btnIniciarSesion.gridx = 1;
		gbc_btnIniciarSesion.gridy = 6;
		panelFormulario.add(btnIniciarSesion, gbc_btnIniciarSesion);

		btnIniciarSesion.addActionListener((ActionEvent e) -> {
			String usuario = campoUsuario.getText().trim();
			String password = String.valueOf(campoPassword.getPassword()).trim();
			boolean noVacios = true;

			// Comprobar que el campo de login no este vacio
			try {
				Utils.campoVacio(usuario);
				lblErrorUsuario.setText("");
				lblErrorUsuario.setVisible(false);
			} catch (CampoVacioException ex) {
				lblErrorUsuario.setText(" - Este campo es obligatorio");
				lblErrorUsuario.setVisible(true);
				noVacios = false;
			}

			// Comprobar que el campo de la contraseña no este vacio
			try {
				Utils.campoVacio(password);
				lblErrorPassword.setText("");
				lblErrorPassword.setVisible(false);
			} catch (CampoVacioException ex) {
				lblErrorPassword.setText(" - Este campo es obligatorio");
				lblErrorPassword.setVisible(true);
				noVacios = false;
			}

			if (noVacios) {
				// Si no he podido loguearme (no existe el usuario)
				if (!Controlador.getControlador().loginUsuario(usuario, password)) {
					System.out.println("No existe el usuario: " + usuario);
					// Indicar al cliente que no existe ningun usuario con esas credenciales
					lblErrorUsuario.setText(" - El usuario o la contraseña no son válidos ");
					lblErrorUsuario.setVisible(true);
					lblErrorPassword.setText(" - El usuario o la contraseña no son válidos ");
					lblErrorPassword.setVisible(true);
				} else {
					System.out.println("Exito al loguearse");
					limpiarPanel();
					//VentanaPrincipal ventana = new VentanaPrincipal();
						//ventana.mostrar();
					
					VentanaPhotoTDS phototds = new VentanaPhotoTDS();
						phototds.mostrar();
					
					frmLoginRegistro.dispose();
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
		lblRegstrate.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				limpiarPanel();
				CardLayout c = (CardLayout) frmLoginRegistro.getContentPane().getLayout();
				c.show(frmLoginRegistro.getContentPane(), VentanaLoginRegistro.PANEL_REGISTRO);
				
			}
		});
		panelCrearCuenta.add(lblRegstrate);

	}

	private void limpiarPanel() {
		lblErrorUsuario.setText("");
		lblErrorUsuario.setVisible(false);
		lblErrorPassword.setText("");
		lblErrorPassword.setVisible(false);
		campoUsuario.setText("");
		campoPassword.setText("");
	}

}
