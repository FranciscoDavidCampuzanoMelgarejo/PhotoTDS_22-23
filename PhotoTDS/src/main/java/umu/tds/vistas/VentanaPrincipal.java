package umu.tds.vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.EtchedBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Cursor;

public class VentanaPrincipal {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatDarkLaf());
					VentanaPrincipal window = new VentanaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 630, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panelLogin = new JPanel();
		frame.getContentPane().add(panelLogin, "name_2076552014272400");
		panelLogin.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCosasPorPoner = new JLabel("Cosas por poner");
		panelLogin.add(lblCosasPorPoner, BorderLayout.WEST);
		
		JPanel panelCentro = new JPanel();
		panelLogin.add(panelCentro, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[]{0, 0};
		gbl_panelCentro.rowHeights = new int[]{0, 0};
		gbl_panelCentro.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelCentro.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelCentro.setLayout(gbl_panelCentro);
		
		JPanel panelFormulario = new JPanel();
		GridBagConstraints gbc_panelFormulario = new GridBagConstraints();
		gbc_panelFormulario.anchor = GridBagConstraints.NORTH;
		gbc_panelFormulario.insets = new Insets(35, 5, 10, 5);
		gbc_panelFormulario.gridx = 0;
		gbc_panelFormulario.gridy = 0;
		panelCentro.add(panelFormulario, gbc_panelFormulario);
		GridBagLayout gbl_panelFormulario = new GridBagLayout();
		gbl_panelFormulario.columnWidths = new int[]{40, 200, 40, 0};
		gbl_panelFormulario.rowHeights = new int[]{50, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelFormulario.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelFormulario.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelFormulario.setLayout(gbl_panelFormulario);
		
		JLabel lblNewLabel = new JLabel("Photo TDS");
		lblNewLabel.setForeground(new Color(244, 244, 244));
		lblNewLabel.setFont(new Font("Palatino Linotype", Font.ITALIC, 25));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.insets = new Insets(10, 0, 25, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panelFormulario.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNombre = new JLabel("USUARIO O CORREO ELECTRÓNICO");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.ipady = 4;
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 2;
		panelFormulario.add(lblNombre, gbc_lblNombre);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.ipady = 2;
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 15, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 3;
		panelFormulario.add(textField, gbc_textField);
		textField.setColumns(30);
		
		JLabel lblContrasea = new JLabel("CONTRASEÑA");
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.ipady = 4;
		gbc_lblContrasea.anchor = GridBagConstraints.WEST;
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea.gridx = 1;
		gbc_lblContrasea.gridy = 4;
		panelFormulario.add(lblContrasea, gbc_lblContrasea);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(30);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.ipady = 2;
		gbc_passwordField.anchor = GridBagConstraints.WEST;
		gbc_passwordField.insets = new Insets(0, 0, 25, 5);
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 5;
		panelFormulario.add(passwordField, gbc_passwordField);
		
		JButton btnIniciarSesin = new JButton("Iniciar Sesión");
		btnIniciarSesin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnIniciarSesin.setForeground(new Color(233, 233, 233));
		btnIniciarSesin.setBackground(new Color(0, 126, 187));
		GridBagConstraints gbc_btnIniciarSesin = new GridBagConstraints();
		gbc_btnIniciarSesin.ipady = 5;
		gbc_btnIniciarSesin.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnIniciarSesin.insets = new Insets(0, 0, 5, 5);
		gbc_btnIniciarSesin.gridx = 1;
		gbc_btnIniciarSesin.gridy = 6;
		panelFormulario.add(btnIniciarSesin, gbc_btnIniciarSesin);
		
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
		panelCrearCuenta.add(lblRegstrate);
	}

}
