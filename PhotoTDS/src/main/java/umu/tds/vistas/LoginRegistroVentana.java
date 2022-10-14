package umu.tds.vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.EtchedBorder;

import com.formdev.flatlaf.FlatDarculaLaf;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.SwingConstants;

public class LoginRegistroVentana {

	private JFrame frame;
	private JTextField loginUser;
	private JPasswordField loginPasswd;
	private boolean noPicture;
	private File profilePic; 
	private JTextField registroNombreApellidos;
	private JTextField registroUserName;
	private JTextField registroCorreo;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatDarculaLaf());
					LoginRegistroVentana window = new LoginRegistroVentana();
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
	public LoginRegistroVentana() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		noPicture = true;			
		
		frame = new JFrame();
		frame.setBounds(100, 100, 643, 688);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panelLogin = new JPanel();
		frame.getContentPane().add(panelLogin, "name_2076552014272400");
		panelLogin.setLayout(new BorderLayout(0, 0));
		
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
		gbc_lblNombre.ipady = 4;
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 2;
		panelFormulario.add(lblNombre, gbc_lblNombre);
		
		loginUser = new JTextField();
		GridBagConstraints gbc_loginUser = new GridBagConstraints();
		gbc_loginUser.ipady = 2;
		gbc_loginUser.anchor = GridBagConstraints.WEST;
		gbc_loginUser.insets = new Insets(0, 0, 15, 5);
		gbc_loginUser.gridx = 1;
		gbc_loginUser.gridy = 3;
		panelFormulario.add(loginUser, gbc_loginUser);
		loginUser.setColumns(30);
		
		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.ipady = 4;
		gbc_lblContrasea.anchor = GridBagConstraints.WEST;
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea.gridx = 1;
		gbc_lblContrasea.gridy = 4;
		panelFormulario.add(lblContrasea, gbc_lblContrasea);
		
		loginPasswd = new JPasswordField();
		loginPasswd.setColumns(30);
		GridBagConstraints gbc_loginPasswd = new GridBagConstraints();
		gbc_loginPasswd.ipady = 2;
		gbc_loginPasswd.anchor = GridBagConstraints.WEST;
		gbc_loginPasswd.insets = new Insets(0, 0, 25, 5);
		gbc_loginPasswd.gridx = 1;
		gbc_loginPasswd.gridy = 5;
		panelFormulario.add(loginPasswd, gbc_loginPasswd);
		
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
		lblRegstrate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout c = (CardLayout)frame.getContentPane().getLayout();
				c.show(frame.getContentPane(), "panelRegistro");
			}
		});
		lblRegstrate.setForeground(new Color(0, 128, 255));
		lblRegstrate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelCrearCuenta.add(lblRegstrate);
		
		JPanel panelRegistro = new JPanel();
		frame.getContentPane().add(panelRegistro, "panelRegistro");
		GridBagLayout gbl_panelRegistro = new GridBagLayout();
		gbl_panelRegistro.columnWidths = new int[]{0, 300, 0, 0};
		gbl_panelRegistro.rowHeights = new int[]{150, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelRegistro.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelRegistro.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelRegistro.setLayout(gbl_panelRegistro);
		
		final ImageIcon noPicture1 = new ImageIcon(LoginRegistroVentana.class.getResource("/resources/noPicture-user-1.png"));
		final ImageIcon noPicture2 = new ImageIcon(LoginRegistroVentana.class.getResource("/resources/noPicture-user-2.png"));
		
		JPanel panelInfoRegistro = new JPanel();
		GridBagConstraints gbc_panelInfoRegistro = new GridBagConstraints();
		gbc_panelInfoRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_panelInfoRegistro.fill = GridBagConstraints.BOTH;
		gbc_panelInfoRegistro.gridx = 1;
		gbc_panelInfoRegistro.gridy = 0;
		panelRegistro.add(panelInfoRegistro, gbc_panelInfoRegistro);
		GridBagLayout gbl_panelInfoRegistro = new GridBagLayout();
		gbl_panelInfoRegistro.columnWidths = new int[]{0, 0};
		gbl_panelInfoRegistro.rowHeights = new int[]{0, 0, 0};
		gbl_panelInfoRegistro.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelInfoRegistro.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panelInfoRegistro.setLayout(gbl_panelInfoRegistro);
		
		JPanel penalProfilePic = new JPanel();
		GridBagConstraints gbc_penalProfilePic = new GridBagConstraints();
		gbc_penalProfilePic.insets = new Insets(0, 0, 5, 0);
		gbc_penalProfilePic.fill = GridBagConstraints.BOTH;
		gbc_penalProfilePic.gridx = 0;
		gbc_penalProfilePic.gridy = 0;
		panelInfoRegistro.add(penalProfilePic, gbc_penalProfilePic);
		penalProfilePic.setLayout(new BorderLayout(0, 0));
		
		final JLabel profilePicLabel = new JLabel("");
		profilePicLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(noPicture) {		// Si no tiene foto, alternamos la imagen de noPicture1 con noPicture2
					profilePicLabel.setIcon(noPicture2);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(noPicture) {		// Si no tiene foto, alternamos la imagen de noPicture2 con noPicture1
					profilePicLabel.setIcon(noPicture1);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(profilePicLabel);
				profilePic = chooser.getSelectedFile();
				if(profilePic!=null) { 
					ImageIcon pp = new ImageIcon(profilePic.toString());
					int h = pp.getIconHeight(), w = pp.getIconWidth();
					if(h>100 || w>100) { 		// Si la imagen es demasiado grande, cancelamos la operación
						JOptionPane.showMessageDialog(frame, "Solo se admiten imágenes de hasta 100x100 píxeles", "Error al cargar imagen de perfil", JOptionPane.ERROR_MESSAGE);
						profilePicLabel.setIcon(noPicture1); noPicture = true;
					} else {
						profilePicLabel.setIcon(pp);
						profilePicLabel.setToolTipText("Cambiar imagen");
						noPicture = false;
					}
				}
			}
		});
		profilePicLabel.setHorizontalAlignment(SwingConstants.CENTER);
			profilePicLabel.setIcon(noPicture1);
		penalProfilePic.add(profilePicLabel, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre y apellidos");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		panelRegistro.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		registroNombreApellidos = new JTextField();
		registroNombreApellidos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_registroNombreApellidos = new GridBagConstraints();
		gbc_registroNombreApellidos.insets = new Insets(0, 0, 15, 0);
		gbc_registroNombreApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_registroNombreApellidos.gridx = 1;
		gbc_registroNombreApellidos.gridy = 2;
		panelRegistro.add(registroNombreApellidos, gbc_registroNombreApellidos);
		registroNombreApellidos.setColumns(30);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre de usuario");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 3;
		panelRegistro.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		registroUserName = new JTextField();
		registroUserName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_registroUserName = new GridBagConstraints();
		gbc_registroUserName.insets = new Insets(0, 0, 15, 0);
		gbc_registroUserName.fill = GridBagConstraints.HORIZONTAL;
		gbc_registroUserName.gridx = 1;
		gbc_registroUserName.gridy = 4;
		panelRegistro.add(registroUserName, gbc_registroUserName);
		registroUserName.setColumns(30);
		
		JLabel lblNewLabel_3 = new JLabel("Correo electrónico");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 5;
		panelRegistro.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		registroCorreo = new JTextField();
		registroCorreo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_registroCorreo = new GridBagConstraints();
		gbc_registroCorreo.insets = new Insets(0, 0, 15, 0);
		gbc_registroCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_registroCorreo.gridx = 1;
		gbc_registroCorreo.gridy = 6;
		panelRegistro.add(registroCorreo, gbc_registroCorreo);
		registroCorreo.setColumns(30);
		
		JLabel lblNewLabel_4 = new JLabel("Contraseña");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 7;
		panelRegistro.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 15, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 8;
		panelRegistro.add(passwordField, gbc_passwordField);
		
		
		
	}

}
