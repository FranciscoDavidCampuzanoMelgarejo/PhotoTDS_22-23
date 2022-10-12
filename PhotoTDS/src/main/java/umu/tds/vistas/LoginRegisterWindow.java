package umu.tds.vistas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import javax.swing.UIManager;

//import com.toedter.calendar.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class LoginRegisterWindow {

	private JFrame frame;										// Ventana principal
	private JTextField loginUser;							   // Inicio de sesi�n: nombre de usuario / correo
	private JPasswordField loginPasswd;						  // Inicio de sesi�n: contrase�a
	private JTextField registerNombre;						 // Registro: nombre completo
	private JTextField registerUser;						// Registro: nombre de usuario
	private JTextField registerCorreo;					   // Registro: correo 
	private JPasswordField registerPasswd;				  // Registro: contrase�a
	JTextArea presentacionTextArea;					     // Registro: presentaci�n (opcional)
	File profilePic;									// Registro: foto de perfil
	boolean profilePicSelected = false;                  

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginRegisterWindow window = new LoginRegisterWindow();
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
	public LoginRegisterWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("PhotoTDS");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginRegisterWindow.class.getResource("/resources/polaroid.png")));
		frame.setBounds(100, 100, 380, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel LoginPanel = new JPanel();
		LoginPanel.setBackground(new Color(248, 248, 255));
		frame.getContentPane().add(LoginPanel, "LoginPanel");
		LoginPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblNewLabel.setIcon(new ImageIcon(LoginRegisterWindow.class.getResource("/resources/logo-intro-200x200.png")));
		LoginPanel.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel loginData = new JPanel();
		loginData.setBackground(new Color(248, 248, 255));
		LoginPanel.add(loginData, BorderLayout.CENTER);
		loginData.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 248, 255));
		loginData.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 20, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel_1 = new JLabel("Usuario:");
		lblNewLabel_1.setFont(new Font("Apple Garamond", Font.ITALIC, 13));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.insets = new Insets(5, 5, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		loginUser = new JTextField();
		loginUser.setFont(new Font("Apple Garamond", Font.PLAIN, 16));
		GridBagConstraints gbc_loginUser = new GridBagConstraints();
		gbc_loginUser.insets = new Insets(0, 5, 5, 5);
		gbc_loginUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_loginUser.gridx = 0;
		gbc_loginUser.gridy = 1;
		panel.add(loginUser, gbc_loginUser);
		loginUser.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Contrase\u00F1a:");
		lblNewLabel_2.setFont(new Font("Apple Garamond", Font.ITALIC, 13));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 5, 5, 0);
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		loginPasswd = new JPasswordField();
		loginPasswd.setFont(new Font("Apple Garamond", Font.PLAIN, 16));
		GridBagConstraints gbc_loginPasswd = new GridBagConstraints();
		gbc_loginPasswd.insets = new Insets(0, 5, 5, 5);
		gbc_loginPasswd.fill = GridBagConstraints.HORIZONTAL;
		gbc_loginPasswd.gridx = 0;
		gbc_loginPasswd.gridy = 3;
		panel.add(loginPasswd, gbc_loginPasswd);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(248, 248, 255));
		loginData.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 0));
		panel_1.add(separator, BorderLayout.SOUTH);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(248, 248, 255));
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("Iniciar sesi\u00F3n");
		btnNewButton.setForeground(new Color(123, 104, 238));
		btnNewButton.setBackground(UIManager.getColor("Button.background"));
		btnNewButton.setFont(new Font("Apple Garamond", Font.ITALIC, 16));
		panel_2.add(btnNewButton);
		
		JPanel registerOption = new JPanel();
		registerOption.setBackground(new Color(248, 248, 255));
		LoginPanel.add(registerOption, BorderLayout.SOUTH);
		registerOption.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("\u00BFNo tienes cuenta?");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Apple Garamond", Font.ITALIC, 15));
		registerOption.add(lblNewLabel_3, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(248, 248, 255));
		registerOption.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnNewButton_1 = new JButton("Crear cuenta");
		btnNewButton_1.setFont(new Font("Apple Garamond", Font.ITALIC, 16));
		panel_3.add(btnNewButton_1);
		
		JPanel RegisterPanel = new JPanel();
		RegisterPanel.setBackground(new Color(248, 248, 255));
		frame.getContentPane().add(RegisterPanel, "RegisterPanel");
		RegisterPanel.setLayout(new BorderLayout(0, 0));
		
		ImageIcon noProfileState1 = new ImageIcon(LoginRegisterWindow.class.getResource("/resources/noPicture-user-1.png"));
		ImageIcon noProfileState2 = new ImageIcon(LoginRegisterWindow.class.getResource("/resources/noPicture-user-2.png"));
		
		JPanel accountDataPanel = new JPanel();
		accountDataPanel.setBackground(new Color(248, 248, 255));
		RegisterPanel.add(accountDataPanel, BorderLayout.CENTER);
		GridBagLayout gbl_accountDataPanel = new GridBagLayout();
		gbl_accountDataPanel.columnWidths = new int[]{84, 0, 0};
		gbl_accountDataPanel.rowHeights = new int[]{120, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_accountDataPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_accountDataPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		accountDataPanel.setLayout(gbl_accountDataPanel);
		
		JPanel profilePicPanel = new JPanel();
		profilePicPanel.setBackground(new Color(248, 248, 255));
		GridBagConstraints gbc_profilePicPanel = new GridBagConstraints();
		gbc_profilePicPanel.insets = new Insets(0, 0, 5, 0);
		gbc_profilePicPanel.gridwidth = 2;
		gbc_profilePicPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_profilePicPanel.gridx = 0;
		gbc_profilePicPanel.gridy = 0;
		accountDataPanel.add(profilePicPanel, gbc_profilePicPanel);
		profilePicPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel profilePicButton = new JLabel("");
			profilePicButton.setToolTipText("Selecciona una imagen de perfil");
		profilePicButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!profilePicSelected)profilePicButton.setIcon(noProfileState2);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(!profilePicSelected)profilePicButton.setIcon(noProfileState1);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(profilePicButton);
				profilePic = chooser.getSelectedFile();
				if(profilePic!=null) { 
					ImageIcon pp = new ImageIcon(profilePic.toString());
					int h = pp.getIconHeight(), w = pp.getIconWidth();
					if(h>100 || w>100) { 		// Si la imagen es demasiado grande, cancelamos la operaci�n
						JOptionPane.showMessageDialog(frame, "La imagen puede ser de hasta 100x100 p�xeles", "Error al cargar imagen de perfil", JOptionPane.ERROR_MESSAGE);
						profilePicButton.setIcon(noProfileState1); profilePicSelected = false;
					} else {
						profilePicButton.setIcon(pp);
						profilePicButton.setToolTipText("Cambiar imagen");
						profilePicSelected = true;
					}
				}
			}
		});
		profilePicButton.setHorizontalAlignment(SwingConstants.CENTER);
		profilePicButton.setIcon(noProfileState1);
		profilePicPanel.add(profilePicButton, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(248, 248, 255));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.gridwidth = 2;
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 1;
		accountDataPanel.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JSeparator separator_2 = new JSeparator();
		panel_4.add(separator_2, BorderLayout.SOUTH);
		
		JLabel lblNewLabel_4 = new JLabel("Nombre:");
		lblNewLabel_4.setFont(new Font("Apple Garamond", Font.ITALIC, 14));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 2;
		accountDataPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		registerNombre = new JTextField();
		registerNombre.setToolTipText("Introduce tu nombre completo");
		registerNombre.setFont(new Font("Apple Garamond", Font.PLAIN, 14));
		GridBagConstraints gbc_registerNombre = new GridBagConstraints();
		gbc_registerNombre.anchor = GridBagConstraints.WEST;
		gbc_registerNombre.insets = new Insets(0, 0, 5, 0);
		gbc_registerNombre.gridx = 1;
		gbc_registerNombre.gridy = 2;
		accountDataPanel.add(registerNombre, gbc_registerNombre);
		registerNombre.setColumns(20);
		
		JLabel lblNewLabel_5 = new JLabel("Usuario:");
		lblNewLabel_5.setFont(new Font("Apple Garamond", Font.ITALIC, 14));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(5, 5, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 3;
		accountDataPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		registerUser = new JTextField();
		registerUser.setToolTipText("Introduce el nombre de usuario que quieres usar");
		registerUser.setFont(new Font("Apple Garamond", Font.PLAIN, 14));
		GridBagConstraints gbc_registerUser = new GridBagConstraints();
		gbc_registerUser.anchor = GridBagConstraints.WEST;
		gbc_registerUser.insets = new Insets(5, 0, 5, 0);
		gbc_registerUser.gridx = 1;
		gbc_registerUser.gridy = 3;
		accountDataPanel.add(registerUser, gbc_registerUser);
		registerUser.setColumns(20);
		
		JLabel lblNewLabel_6 = new JLabel("Correo:");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_6.setFont(new Font("Apple Garamond", Font.ITALIC, 14));
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(5, 5, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 4;
		accountDataPanel.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		registerCorreo = new JTextField();
		registerCorreo.setToolTipText("Introduce la cuenta de correo asociada a la cuenta");
		registerCorreo.setFont(new Font("Apple Garamond", Font.PLAIN, 14));
		GridBagConstraints gbc_registerCorreo = new GridBagConstraints();
		gbc_registerCorreo.insets = new Insets(5, 0, 5, 0);
		gbc_registerCorreo.anchor = GridBagConstraints.WEST;
		gbc_registerCorreo.gridx = 1;
		gbc_registerCorreo.gridy = 4;
		accountDataPanel.add(registerCorreo, gbc_registerCorreo);
		registerCorreo.setColumns(20);
		
		JLabel lblNewLabel_7 = new JLabel("Contrase\u00F1a:");
		lblNewLabel_7.setFont(new Font("Apple Garamond", Font.ITALIC, 14));
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(5, 5, 5, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 5;
		accountDataPanel.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		registerPasswd = new JPasswordField();
		registerPasswd.setToolTipText("Introduce una contrase\u00F1a que proteger\u00E1 tu cuenta");
		registerPasswd.setFont(new Font("Apple Garamond", Font.PLAIN, 14));
		registerPasswd.setColumns(15);
		GridBagConstraints gbc_registerPasswd = new GridBagConstraints();
		gbc_registerPasswd.anchor = GridBagConstraints.WEST;
		gbc_registerPasswd.insets = new Insets(5, 0, 5, 0);
		gbc_registerPasswd.gridx = 1;
		gbc_registerPasswd.gridy = 5;
		accountDataPanel.add(registerPasswd, gbc_registerPasswd);
		
		JLabel lblNewLabel_8 = new JLabel("Fecha:");
		lblNewLabel_8.setFont(new Font("Apple Garamond", Font.ITALIC, 14));
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_8.insets = new Insets(5, 5, 5, 5);
		gbc_lblNewLabel_8.gridx = 0;
		gbc_lblNewLabel_8.gridy = 6;
		accountDataPanel.add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_9.setIcon(new ImageIcon(LoginRegisterWindow.class.getResource("/com/toedter/calendar/images/JCalendarColor32.gif")));
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_9.insets = new Insets(5, 0, 5, 120);
		gbc_lblNewLabel_9.gridx = 1;
		gbc_lblNewLabel_9.gridy = 6;
		JDateChooser fecha = new JDateChooser();
			fecha.setToolTipText("Introduce tu fecha de nacimiento");
		accountDataPanel.add(fecha, gbc_lblNewLabel_9);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(248, 248, 255));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 0);
		gbc_panel_5.gridwidth = 2;
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 7;
		accountDataPanel.add(panel_5, gbc_panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JSeparator separator_3 = new JSeparator();
		panel_5.add(separator_3, BorderLayout.NORTH);
		
		JLabel lblNewLabel_10 = new JLabel("Presentacion:");
		lblNewLabel_10.setForeground(new Color(255, 0, 0));
		lblNewLabel_10.setFont(new Font("Apple Garamond", Font.ITALIC, 14));
		GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
		gbc_lblNewLabel_10.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_10.gridx = 0;
		gbc_lblNewLabel_10.gridy = 8;
		accountDataPanel.add(lblNewLabel_10, gbc_lblNewLabel_10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 5, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 9;
		accountDataPanel.add(scrollPane, gbc_scrollPane);
		
		presentacionTextArea = new JTextArea();
		presentacionTextArea.setLineWrap(true);
		scrollPane.setViewportView(presentacionTextArea);
		presentacionTextArea.setToolTipText("A\u00F1ade una presentaci\u00F3n de t\u00ED mismo (opcional)");
		presentacionTextArea.setFont(new Font("Apple Garamond", Font.PLAIN, 14));
		
		JPanel createAccountPanel = new JPanel();
		createAccountPanel.setBackground(new Color(248, 248, 255));
		RegisterPanel.add(createAccountPanel, BorderLayout.SOUTH);
		createAccountPanel.setLayout(new BorderLayout(0, 0));
		
		JSeparator separator_1 = new JSeparator();
		createAccountPanel.add(separator_1, BorderLayout.NORTH);
		
		JPanel createAccountButtonPanel = new JPanel();
		createAccountButtonPanel.setBackground(new Color(248, 248, 255));
		createAccountPanel.add(createAccountButtonPanel, BorderLayout.SOUTH);
		
		JButton createAccountButton = new JButton("Crear cuenta");
		createAccountButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {			// Comprobamos la validez de los datos proporcionados por el usuario
				
			}
		});
		createAccountButton.setForeground(new Color(123, 104, 238));
		createAccountButton.setFont(new Font("Apple Garamond", Font.ITALIC, 16));
		createAccountButtonPanel.add(createAccountButton);
		
		
		/* Menajadores de eventos */
		
		// Bot�n "Crear cuenta"
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CardLayout c = (CardLayout)frame.getContentPane().getLayout();
				c.show(frame.getContentPane(), "RegisterPanel");
			}
		});
	}

}
