package umu.tds.vistas;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

import com.toedter.calendar.JDateChooser;
import java.awt.FlowLayout;
import com.toedter.calendar.JCalendar;

public class PanelRegistro extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean noPicture;
	private File profilePic;
	
	private JFrame frmLoginRegistro;
	private JTextField campoNombreApellidos;
	private JTextField campoUsuario;
	private JTextField campoCorreo;
	private JPasswordField campoPassword;


	public PanelRegistro(JFrame frame) {
		this.noPicture = true;
		this.frmLoginRegistro = frame;
		this.crearPanel();
	}

	private void crearPanel() {
		setViewportBorder(null);
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		final ImageIcon noPicture1 = new ImageIcon(
				VentanaLoginRegistro.class.getResource("/imagenes/noPicture-user-1.png"));
		final ImageIcon noPicture2 = new ImageIcon(
				VentanaLoginRegistro.class.getResource("/imagenes/noPicture-user-2.png"));
		
		JPanel panelExterno = new JPanel();
		setViewportView(panelExterno);
		GridBagLayout gbl_panelExterno = new GridBagLayout();
		gbl_panelExterno.columnWidths = new int[]{40, 410, 40, 0};
		gbl_panelExterno.rowHeights = new int[]{40, 650, 40, 0};
		gbl_panelExterno.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelExterno.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panelExterno.setLayout(gbl_panelExterno);
		
		JPanel panelRegistro = new JPanel();
		GridBagConstraints gbc_panelRegistro = new GridBagConstraints();
		gbc_panelRegistro.gridheight = 2;
		gbc_panelRegistro.anchor = GridBagConstraints.NORTH;
		gbc_panelRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_panelRegistro.gridx = 1;
		gbc_panelRegistro.gridy = 1;
		panelExterno.add(panelRegistro, gbc_panelRegistro);
		panelRegistro.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCentro = new JPanel();
		panelRegistro.add(panelCentro, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[]{320, 40, 0};
		gbl_panelCentro.rowHeights = new int[]{60, 30, 25, 0, 25, 0, 25, 0, 25, 0, 25, 0, 0, 25, 0, 0, 0, 0, 0};
		gbl_panelCentro.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelCentro.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelCentro.setLayout(gbl_panelCentro);
		
		JLabel lblFoto = new JLabel("");
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblFoto.setIcon(noPicture1);
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.insets = new Insets(0, 0, 5, 5);
		gbc_lblFoto.anchor = GridBagConstraints.NORTH;
		gbc_lblFoto.gridx = 0;
		gbc_lblFoto.gridy = 0;
		panelCentro.add(lblFoto, gbc_lblFoto);
		
		lblFoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(noPicture) {
					// Si no tiene foto, alternamos la imagen de noPicture1 con noPicture2
					lblFoto.setIcon(noPicture2);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(noPicture) {
					//Si no tiene foto, alternamos la imagen de noPicture2 con noPicture1
					lblFoto.setIcon(noPicture1);
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(lblFoto);
				profilePic = chooser.getSelectedFile();
				if(profilePic != null) {
					ImageIcon pp = new ImageIcon(profilePic.toString());
					int h = pp.getIconHeight();
					int w = pp.getIconWidth();
					if(h > 100 || w > 100) {
						// Si la imagen es demasiado grande, cancelamos la operacion
						//ventanaPadre.mostrarErrorTamaño()
						lblFoto.setIcon(noPicture1);
						noPicture = true;
					} else {
						lblFoto.setIcon(pp);
						lblFoto.setToolTipText("Cambiar imagen");
						noPicture = false;
					}
				}
			}
		});
		
		
		
		JPanel panelNombreApellidos = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelNombreApellidos.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelNombreApellidos = new GridBagConstraints();
		gbc_panelNombreApellidos.insets = new Insets(0, 0, 5, 8);
		gbc_panelNombreApellidos.fill = GridBagConstraints.BOTH;
		gbc_panelNombreApellidos.gridx = 0;
		gbc_panelNombreApellidos.gridy = 2;
		panelCentro.add(panelNombreApellidos, gbc_panelNombreApellidos);
		
		JLabel lblNombApell = new JLabel("Nombre y Apellidos");
		lblNombApell.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelNombreApellidos.add(lblNombApell);
		
		JLabel lblErrorNombrApell = new JLabel("");
		lblErrorNombrApell.setVisible(false);
		lblErrorNombrApell.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelNombreApellidos.add(lblErrorNombrApell);
		
		campoNombreApellidos = new JTextField();
		campoNombreApellidos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_campoNombreApellidos = new GridBagConstraints();
		gbc_campoNombreApellidos.ipady = 3;
		gbc_campoNombreApellidos.insets = new Insets(0, 0, 20, 8);
		gbc_campoNombreApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoNombreApellidos.gridx = 0;
		gbc_campoNombreApellidos.gridy = 3;
		panelCentro.add(campoNombreApellidos, gbc_campoNombreApellidos);
		campoNombreApellidos.setColumns(20);
		
		JLabel lblIconoNombApell = new JLabel("X");
		lblIconoNombApell.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblIconoNombApell = new GridBagConstraints();
		gbc_lblIconoNombApell.insets = new Insets(0, 0, 20, 0);
		gbc_lblIconoNombApell.anchor = GridBagConstraints.WEST;
		gbc_lblIconoNombApell.gridx = 1;
		gbc_lblIconoNombApell.gridy = 3;
		panelCentro.add(lblIconoNombApell, gbc_lblIconoNombApell);
		
		JPanel panelUsuario = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelUsuario.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelUsuario = new GridBagConstraints();
		gbc_panelUsuario.insets = new Insets(0, 0, 5, 8);
		gbc_panelUsuario.fill = GridBagConstraints.BOTH;
		gbc_panelUsuario.gridx = 0;
		gbc_panelUsuario.gridy = 4;
		panelCentro.add(panelUsuario, gbc_panelUsuario);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario");
		lblNombreDeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelUsuario.add(lblNombreDeUsuario);
		
		JLabel lblErrorUsuario = new JLabel("");
		lblErrorUsuario.setVisible(false);
		lblErrorUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelUsuario.add(lblErrorUsuario);
		
		campoUsuario = new JTextField();
		campoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_campoUsuario = new GridBagConstraints();
		gbc_campoUsuario.ipady = 3;
		gbc_campoUsuario.insets = new Insets(0, 0, 20, 8);
		gbc_campoUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoUsuario.gridx = 0;
		gbc_campoUsuario.gridy = 5;
		panelCentro.add(campoUsuario, gbc_campoUsuario);
		campoUsuario.setColumns(20);
		
		JLabel lblIconoUsuario = new JLabel("X");
		lblIconoUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblIconoUsuario = new GridBagConstraints();
		gbc_lblIconoUsuario.insets = new Insets(0, 0, 20, 0);
		gbc_lblIconoUsuario.anchor = GridBagConstraints.WEST;
		gbc_lblIconoUsuario.gridx = 1;
		gbc_lblIconoUsuario.gridy = 5;
		panelCentro.add(lblIconoUsuario, gbc_lblIconoUsuario);
		
		JPanel panelCorreo = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelCorreo.getLayout();
		flowLayout_2.setVgap(0);
		flowLayout_2.setHgap(0);
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelCorreo = new GridBagConstraints();
		gbc_panelCorreo.insets = new Insets(0, 0, 5, 8);
		gbc_panelCorreo.fill = GridBagConstraints.BOTH;
		gbc_panelCorreo.gridx = 0;
		gbc_panelCorreo.gridy = 6;
		panelCentro.add(panelCorreo, gbc_panelCorreo);
		
		JLabel lblCorreo = new JLabel("Correo Electrónico");
		lblCorreo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelCorreo.add(lblCorreo);
		
		JLabel lblErrorCorreo = new JLabel("");
		lblErrorCorreo.setVisible(false);
		lblErrorCorreo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelCorreo.add(lblErrorCorreo);
		
		campoCorreo = new JTextField();
		campoCorreo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_campoCorreo = new GridBagConstraints();
		gbc_campoCorreo.ipady = 3;
		gbc_campoCorreo.insets = new Insets(0, 0, 20, 8);
		gbc_campoCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoCorreo.gridx = 0;
		gbc_campoCorreo.gridy = 7;
		panelCentro.add(campoCorreo, gbc_campoCorreo);
		campoCorreo.setColumns(20);
		
		JLabel lblIconoCorreo = new JLabel("X");
		lblIconoCorreo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblIconoCorreo = new GridBagConstraints();
		gbc_lblIconoCorreo.anchor = GridBagConstraints.WEST;
		gbc_lblIconoCorreo.insets = new Insets(0, 0, 20, 0);
		gbc_lblIconoCorreo.gridx = 1;
		gbc_lblIconoCorreo.gridy = 7;
		panelCentro.add(lblIconoCorreo, gbc_lblIconoCorreo);
		
		JPanel panelPassword = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panelPassword.getLayout();
		flowLayout_3.setVgap(0);
		flowLayout_3.setHgap(0);
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelPassword = new GridBagConstraints();
		gbc_panelPassword.insets = new Insets(0, 0, 5, 8);
		gbc_panelPassword.fill = GridBagConstraints.BOTH;
		gbc_panelPassword.gridx = 0;
		gbc_panelPassword.gridy = 8;
		panelCentro.add(panelPassword, gbc_panelPassword);
		
		JLabel lblPassword = new JLabel("Contraseña");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelPassword.add(lblPassword);
		
		JLabel lblErrorPassword = new JLabel("");
		lblErrorPassword.setVisible(false);
		lblErrorPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelPassword.add(lblErrorPassword);
		
		campoPassword = new JPasswordField();
		campoPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_campoPassword = new GridBagConstraints();
		gbc_campoPassword.ipady = 3;
		gbc_campoPassword.insets = new Insets(0, 0, 20, 8);
		gbc_campoPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoPassword.gridx = 0;
		gbc_campoPassword.gridy = 9;
		panelCentro.add(campoPassword, gbc_campoPassword);
		
		JLabel lblIconoPassword = new JLabel("X");
		lblIconoPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblIconoPassword = new GridBagConstraints();
		gbc_lblIconoPassword.insets = new Insets(0, 0, 20, 0);
		gbc_lblIconoPassword.anchor = GridBagConstraints.WEST;
		gbc_lblIconoPassword.gridx = 1;
		gbc_lblIconoPassword.gridy = 9;
		panelCentro.add(lblIconoPassword, gbc_lblIconoPassword);
		
		JPanel panelFecha = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panelFecha.getLayout();
		flowLayout_4.setVgap(0);
		flowLayout_4.setHgap(0);
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelFecha = new GridBagConstraints();
		gbc_panelFecha.insets = new Insets(0, 0, 5, 8);
		gbc_panelFecha.fill = GridBagConstraints.BOTH;
		gbc_panelFecha.gridx = 0;
		gbc_panelFecha.gridy = 10;
		panelCentro.add(panelFecha, gbc_panelFecha);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento");
		lblFechaNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelFecha.add(lblFechaNacimiento);
		
		JLabel lblErrorFecha = new JLabel("");
		lblErrorFecha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblErrorFecha.setVisible(false);
		panelFecha.add(lblErrorFecha);
		
		JDateChooser dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 10, 8);
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.gridx = 0;
		gbc_dateChooser.gridy = 11;
		panelCentro.add(dateChooser, gbc_dateChooser);
		
		JSeparator seperadorPresentacion = new JSeparator();
		GridBagConstraints gbc_seperadorPresentacion = new GridBagConstraints();
		gbc_seperadorPresentacion.ipady = 1;
		gbc_seperadorPresentacion.fill = GridBagConstraints.BOTH;
		gbc_seperadorPresentacion.insets = new Insets(0, 0, 10, 8);
		gbc_seperadorPresentacion.gridx = 0;
		gbc_seperadorPresentacion.gridy = 12;
		panelCentro.add(seperadorPresentacion, gbc_seperadorPresentacion);
		
		JPanel panelPresentacion = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) panelPresentacion.getLayout();
		flowLayout_5.setVgap(0);
		flowLayout_5.setHgap(0);
		flowLayout_5.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelPresentacion = new GridBagConstraints();
		gbc_panelPresentacion.insets = new Insets(0, 0, 5, 8);
		gbc_panelPresentacion.fill = GridBagConstraints.BOTH;
		gbc_panelPresentacion.gridx = 0;
		gbc_panelPresentacion.gridy = 13;
		panelCentro.add(panelPresentacion, gbc_panelPresentacion);
		
		JLabel lblPresentacion = new JLabel("Presentación (opcional)");
		lblPresentacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelPresentacion.add(lblPresentacion);
		
		JTextArea textoPresentacion = new JTextArea();
		textoPresentacion.setLineWrap(true);
		textoPresentacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textoPresentacion.setRows(5);
		textoPresentacion.setColumns(20);
		GridBagConstraints gbc_textoPresentacion = new GridBagConstraints();
		gbc_textoPresentacion.insets = new Insets(0, 0, 10, 8);
		gbc_textoPresentacion.fill = GridBagConstraints.BOTH;
		gbc_textoPresentacion.gridx = 0;
		gbc_textoPresentacion.gridy = 14;
		panelCentro.add(textoPresentacion, gbc_textoPresentacion);
		
		JSeparator seperadorCrearCuenta = new JSeparator();
		GridBagConstraints gbc_seperadorCrearCuenta = new GridBagConstraints();
		gbc_seperadorCrearCuenta.fill = GridBagConstraints.BOTH;
		gbc_seperadorCrearCuenta.ipady = 1;
		gbc_seperadorCrearCuenta.insets = new Insets(0, 0, 10, 8);
		gbc_seperadorCrearCuenta.gridx = 0;
		gbc_seperadorCrearCuenta.gridy = 15;
		panelCentro.add(seperadorCrearCuenta, gbc_seperadorCrearCuenta);
		
		JButton btnCrearCuenta = new JButton("Crear Cuenta");
		btnCrearCuenta.setBackground(new Color(240, 240, 240));
		btnCrearCuenta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCrearCuenta.setBackground(new Color(28, 84, 215));
		btnCrearCuenta.setForeground(new Color(200, 200, 200));
		GridBagConstraints gbc_btnCrearCuenta = new GridBagConstraints();
		gbc_btnCrearCuenta.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCrearCuenta.ipady = 4;
		gbc_btnCrearCuenta.insets = new Insets(0, 0, 5, 8);
		gbc_btnCrearCuenta.gridx = 0;
		gbc_btnCrearCuenta.gridy = 16;
		panelCentro.add(btnCrearCuenta, gbc_btnCrearCuenta);
		
		JLabel lblInicioSesion = new JLabel("¿Ya tienes una cuenta?");
		lblInicioSesion.setForeground(new Color(0, 128, 255));
		lblInicioSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_lblInicioSesion = new GridBagConstraints();
		gbc_lblInicioSesion.ipady = 1;
		gbc_lblInicioSesion.anchor = GridBagConstraints.WEST;
		gbc_lblInicioSesion.insets = new Insets(0, 0, 0, 8);
		gbc_lblInicioSesion.gridx = 0;
		gbc_lblInicioSesion.gridy = 17;
		panelCentro.add(lblInicioSesion, gbc_lblInicioSesion);

	}

	/*
	public void limpiarPanel() {
		noPicture = true;
		registroNombreApellidos.setText("");
		registroUserName.setText("");
		registroCorreo.setText("");
		registroPasswd.setText("");
		presentacion.setText("");

		fecha.setDate(null);
	}
	*/

}
