package umu.tds.vistas;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.SimpleFormatter;

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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.toedter.calendar.JDateChooser;

import umu.tds.controlador.Controlador;
import umu.tds.filtros.DocumentFilterListener;
import umu.tds.filtros.FiltroPassword;
import umu.tds.filtros.FiltroTextField;

import java.awt.FlowLayout;
import com.toedter.calendar.JCalendar;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import java.awt.Dimension;

public class PanelRegistro extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int MAX_CARACTERES = 200;
	private static final String CAMPO_OBLIGATORIO_VACIO = " Este campo es obligatorio ";
	private static final String USUARIO_EXISTENTE = " Ya existe un usuario con nombre o correo ";
	private boolean noPicture;
	
	private String foto;

	private JFrame frmLoginRegistro;

	// Campos del registro
	private JTextField campoNombreApellidos;
	private JTextField campoUsuario;
	private JTextField campoCorreo;
	private JPasswordField campoPassword;
	private JDateChooser dateChooser;
	private JTextArea textoPresentacion;

	// Etiquetas
	private JLabel lblErrorNombApell, lblIconoNombApell;
	private JLabel lblErrorUsuario, lblIconoUsuario;
	private JLabel lblErrorCorreo, lblIconoCorreo;
	private JLabel lblErrorPassword, lblIconoPassword;
	private JLabel lblErrorFecha;

	// Iconos
	private ImageIcon iconoError; // X
	private ImageIcon iconoAcierto; // V

	// Variables booleanas para comprobar que los campos son correctos (cumple un
	// patron y otras caracteristicas)
	private boolean nombreApellidosValido;
	private boolean usuarioValido;
	private boolean correoValido;
	private boolean passwordValido;
	// private boolean fechaValido;

	private void cambiarEtiquetas(JLabel lblSuperior, JLabel lblLateral, String mensaje, ImageIcon icono) {
		lblSuperior.setText(mensaje);
		lblLateral.setIcon(icono);

		if (mensaje == null)
			lblSuperior.setVisible(false);
		else
			lblSuperior.setVisible(true);

		if (icono == null)
			lblLateral.setVisible(false);
		else
			lblLateral.setVisible(true);
	}

	// Sobrecarga del metodo original
	private void cambiarEtiquetas(JLabel lblSuperior, JLabel lblLateral, ImageIcon icono) {
		cambiarEtiquetas(lblSuperior, lblLateral, null, icono);
	}

	// Sobrecarga del metodo original
	private void cambiarEtiquetas(JLabel lblSuperior, JLabel lblLateral) {
		cambiarEtiquetas(lblSuperior, lblLateral, null, null);
	}

	private void limpiarPanel() {

		this.noPicture = true;

		// Campos
		this.campoNombreApellidos.setText(null);
		this.campoUsuario.setText(null);
		this.campoCorreo.setText(null);
		this.campoPassword.setText(null);
		this.textoPresentacion.setText(null);
		this.dateChooser.setDate(null);

		// Etiquetas de error
		this.lblErrorNombApell.setVisible(false);
		this.lblIconoNombApell.setVisible(false);

		this.lblErrorUsuario.setVisible(false);
		this.lblIconoUsuario.setVisible(false);

		this.lblErrorCorreo.setVisible(false);
		this.lblIconoCorreo.setVisible(false);

		this.lblErrorPassword.setVisible(false);
		this.lblIconoPassword.setVisible(false);

		this.lblErrorFecha.setVisible(false);
	}

	private boolean campoCorrecto(String mensaje, boolean ponerIcono) {
		return (mensaje == null && ponerIcono);
	}

	public PanelRegistro(JFrame frame) {
		this.noPicture = true;
		this.foto = null;			
		this.frmLoginRegistro = frame;
		this.iconoError = new ImageIcon(new ImageIcon(getClass().getResource("/imagenes/cross-icon.png")).getImage()
				.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		this.iconoAcierto = new ImageIcon(new ImageIcon(getClass().getResource("/imagenes/tick-icon.png")).getImage()
				.getScaledInstance(20, 20, Image.SCALE_DEFAULT));

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
		gbl_panelExterno.columnWidths = new int[] { 40, 410, 40, 0 };
		gbl_panelExterno.rowHeights = new int[] { 40, 683, 40, 0 };
		gbl_panelExterno.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelExterno.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
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
		gbl_panelCentro.columnWidths = new int[] { 320, 40, 0 };
		gbl_panelCentro.rowHeights = new int[] { 60, 30, 25, 0, 25, 0, 25, 0, 25, 0, 25, 0, 0, 25, 0, 0, 0, 0, 0 };
		gbl_panelCentro.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelCentro.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelCentro.setLayout(gbl_panelCentro);

		JLabel lblFoto = new JLabel("");
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblFoto.setIcon(noPicture1);
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFoto.insets = new Insets(0, 0, 5, 5);
		gbc_lblFoto.anchor = GridBagConstraints.NORTH;
		gbc_lblFoto.gridx = 0;
		gbc_lblFoto.gridy = 0;
		panelCentro.add(lblFoto, gbc_lblFoto);

		lblFoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (noPicture) {
					// Si no tiene foto, alternamos la imagen de noPicture1 con noPicture2
					lblFoto.setIcon(noPicture2);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (noPicture) {
					// Si no tiene foto, alternamos la imagen de noPicture2 con noPicture1
					lblFoto.setIcon(noPicture1);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e){
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(lblFoto);
				File archivo = chooser.getSelectedFile();
				if (archivo != null) {
					try{
						Image pic = ImageIO.read(archivo);
						float iw = pic.getWidth(null), ih = pic.getHeight(null), s;
						s = (iw>ih) ? 200/iw : 200/ih;
						//ImageIcon i = new ImageIcon(pic.getScaledInstance((int)(iw*s), (int)(ih*s), Image.SCALE_SMOOTH));
						lblFoto.setIcon(new ImageIcon(Utils.redondearImagen((int)(iw*s), new ImageIcon(pic))));
						noPicture = false;
			
						foto = archivo.toString();
					} catch (IOException ioe) {}
				}
			}
		});

		JPanel panelNombreApellidos = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelNombreApellidos.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelNombreApellidos = new GridBagConstraints();
		gbc_panelNombreApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelNombreApellidos.insets = new Insets(0, 0, 5, 8);
		gbc_panelNombreApellidos.gridx = 0;
		gbc_panelNombreApellidos.gridy = 2;
		panelCentro.add(panelNombreApellidos, gbc_panelNombreApellidos);

		JLabel lblNombApell = new JLabel("Nombre y Apellidos");
		lblNombApell.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelNombreApellidos.add(lblNombApell);

		lblErrorNombApell = new JLabel("");
		lblErrorNombApell.setVisible(false);
		lblErrorNombApell.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblErrorNombApell.setForeground(new Color(255, 106, 106));
		panelNombreApellidos.add(lblErrorNombApell);

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

		((AbstractDocument) campoNombreApellidos.getDocument()).setDocumentFilter(
				new FiltroTextField("([A-Z][a-z]+)([\\s-][A-Z][a-z]+)*", new DocumentFilterListener() {

					@Override
					public void notificarRobustezPassword(String mensaje, Color color) {
						// TODO Auto-generated method stub

					}

					@Override
					public void notificarFormatoErroneo(String mensaje) {
						cambiarEtiquetas(lblErrorNombApell, lblIconoNombApell, mensaje, iconoError);

					}

					@Override
					public void notificarFormatoCorrecto() {
						cambiarEtiquetas(lblErrorNombApell, lblIconoNombApell, iconoAcierto);
						nombreApellidosValido = true;

					}

					@Override
					public void notificarCampoVacio() {
						cambiarEtiquetas(lblErrorNombApell, lblIconoNombApell);

					}
				}));

		lblIconoNombApell = new JLabel();
		lblIconoNombApell.setVisible(false);
		GridBagConstraints gbc_lblIconoNombApell = new GridBagConstraints();
		gbc_lblIconoNombApell.ipady = 3;
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
		gbc_panelUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelUsuario.insets = new Insets(0, 0, 5, 8);
		gbc_panelUsuario.gridx = 0;
		gbc_panelUsuario.gridy = 4;
		panelCentro.add(panelUsuario, gbc_panelUsuario);

		JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario");
		lblNombreDeUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelUsuario.add(lblNombreDeUsuario);

		lblErrorUsuario = new JLabel("");
		lblErrorUsuario.setVisible(false);
		lblErrorUsuario.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblErrorUsuario.setForeground(new Color(255, 106, 106));
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

		((AbstractDocument) campoUsuario.getDocument())
				.setDocumentFilter(new FiltroTextField(".+", new DocumentFilterListener() {

					@Override
					public void notificarRobustezPassword(String mensaje, Color color) {
						// TODO Auto-generated method stub

					}

					@Override
					public void notificarFormatoErroneo(String mensaje) {
						cambiarEtiquetas(lblErrorUsuario, lblIconoUsuario, mensaje, iconoError);

					}

					@Override
					public void notificarFormatoCorrecto() {
						cambiarEtiquetas(lblErrorUsuario, lblIconoUsuario, iconoAcierto);
						usuarioValido = true;

					}

					@Override
					public void notificarCampoVacio() {
						cambiarEtiquetas(lblErrorUsuario, lblIconoUsuario);

					}
				}));

		lblIconoUsuario = new JLabel();
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
		gbc_panelCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelCorreo.insets = new Insets(0, 0, 5, 8);
		gbc_panelCorreo.gridx = 0;
		gbc_panelCorreo.gridy = 6;
		panelCentro.add(panelCorreo, gbc_panelCorreo);

		JLabel lblCorreo = new JLabel("Correo Electrónico");
		lblCorreo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelCorreo.add(lblCorreo);

		lblErrorCorreo = new JLabel("");
		lblErrorCorreo.setVisible(false);
		lblErrorCorreo.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblErrorCorreo.setForeground(new Color(255, 106, 106));
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

		((AbstractDocument) campoCorreo.getDocument()).setDocumentFilter(new FiltroTextField(
				"([A-Za-z0-9-_.]+)@([A-Za-z0-9-_.]+[.]([A-Za-z]{2,5}))", new DocumentFilterListener() {

					@Override
					public void notificarRobustezPassword(String mensaje, Color color) {
						// TODO Auto-generated method stub

					}

					@Override
					public void notificarFormatoErroneo(String mensaje) {
						cambiarEtiquetas(lblErrorCorreo, lblIconoCorreo, mensaje, iconoError);

					}

					@Override
					public void notificarFormatoCorrecto() {
						cambiarEtiquetas(lblErrorCorreo, lblIconoCorreo, iconoAcierto);
						correoValido = true;

					}

					@Override
					public void notificarCampoVacio() {
						cambiarEtiquetas(lblErrorCorreo, lblIconoCorreo);

					}
				}));

		lblIconoCorreo = new JLabel();
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
		gbc_panelPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelPassword.insets = new Insets(0, 0, 5, 8);
		gbc_panelPassword.gridx = 0;
		gbc_panelPassword.gridy = 8;
		panelCentro.add(panelPassword, gbc_panelPassword);

		JLabel lblPassword = new JLabel("Contraseña");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelPassword.add(lblPassword);

		lblErrorPassword = new JLabel("");
		lblErrorPassword.setBackground(Color.YELLOW);
		lblErrorPassword.setVisible(false);
		lblErrorPassword.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblErrorPassword.setForeground(new Color(255, 106, 106));
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

		((AbstractDocument) campoPassword.getDocument()).setDocumentFilter(
				new FiltroPassword("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[-_$?!%.]).*", new DocumentFilterListener() {

					@Override
					public void notificarRobustezPassword(String mensaje, Color color) {
						cambiarEtiquetas(lblErrorPassword, lblIconoPassword, mensaje, iconoAcierto);
						lblErrorPassword.setForeground(new Color(0, 0, 0));
						lblErrorPassword.setBackground(color);
						lblErrorPassword.setOpaque(true);
						passwordValido = true;

					}

					@Override
					public void notificarFormatoErroneo(String mensaje) {
						cambiarEtiquetas(lblErrorPassword, lblIconoPassword, mensaje, iconoError);
						lblErrorPassword.setForeground(new Color(255, 106, 106));
						lblErrorPassword.setOpaque(false);

					}

					@Override
					public void notificarFormatoCorrecto() {
						cambiarEtiquetas(lblPassword, lblIconoPassword, iconoAcierto);
						passwordValido = true;

					}

					@Override
					public void notificarCampoVacio() {
						cambiarEtiquetas(lblErrorPassword, lblIconoPassword);
						lblErrorPassword.setOpaque(false);

					}
				}));

		lblIconoPassword = new JLabel();
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
		gbc_panelFecha.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelFecha.insets = new Insets(0, 0, 5, 8);
		gbc_panelFecha.gridx = 0;
		gbc_panelFecha.gridy = 10;
		panelCentro.add(panelFecha, gbc_panelFecha);

		JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento");
		lblFechaNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelFecha.add(lblFechaNacimiento);

		lblErrorFecha = new JLabel("");
		lblErrorFecha.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblErrorFecha.setForeground(new Color(255, 106, 106));
		lblErrorFecha.setVisible(false);
		panelFecha.add(lblErrorFecha);

		dateChooser = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
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
		gbc_panelPresentacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelPresentacion.insets = new Insets(0, 0, 5, 8);
		gbc_panelPresentacion.gridx = 0;
		gbc_panelPresentacion.gridy = 13;
		panelCentro.add(panelPresentacion, gbc_panelPresentacion);

		JLabel lblPresentacion = new JLabel("Presentación (opcional)");
		lblPresentacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelPresentacion.add(lblPresentacion);

		textoPresentacion = new JTextArea();
		textoPresentacion.setLineWrap(true);
		textoPresentacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textoPresentacion.setRows(5);
		textoPresentacion.setColumns(20);

		// Escribir como maximo 200 caracteres
		((AbstractDocument) textoPresentacion.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {

				String subText = text;
				if (text != null) {
					int longitudActual = (fb.getDocument().getLength() - length);
					// System.out.println(longitudActual);
					if (longitudActual + text.length() > MAX_CARACTERES) {
						int caracteresFaltantes = MAX_CARACTERES - longitudActual;
						subText = new String(text.substring(0, caracteresFaltantes));
					}
				}

				super.replace(fb, offset, length, subText, attrs);
			}

			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
				super.remove(fb, offset, length);
			}
		});

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
		//btnCrearCuenta.setBackground(new Color(240, 240, 240));
		btnCrearCuenta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCrearCuenta.setBackground(new Color(0xb2, 0x00, 0x9c));
		btnCrearCuenta.setForeground(new Color(200, 200, 200));

		btnCrearCuenta.addActionListener((ActionEvent e) -> {
			String nombreApellidos = campoNombreApellidos.getText();
			String usuario = campoUsuario.getText();
			String correo = campoCorreo.getText();
			String password = String.valueOf(campoPassword.getPassword());
			String fecha = dateChooser.getDate() == null ? ""
					: new SimpleDateFormat("dd/MM/yyyy").format(dateChooser.getDate());
			String presentacion = textoPresentacion.getText();

			boolean noVacios = true;

			try {
				Utils.campoVacio(nombreApellidos);
			} catch (CampoVacioException ex) {
				noVacios = false;
				cambiarEtiquetas(lblErrorNombApell, lblIconoNombApell, CAMPO_OBLIGATORIO_VACIO, iconoError);
			}

			try {
				Utils.campoVacio(usuario);
			} catch (CampoVacioException ex) {
				noVacios = false;
				cambiarEtiquetas(lblErrorUsuario, lblIconoUsuario, CAMPO_OBLIGATORIO_VACIO, iconoError);
			}

			try {
				Utils.campoVacio(correo);
			} catch (CampoVacioException ex) {
				noVacios = false;
				cambiarEtiquetas(lblErrorCorreo, lblIconoCorreo, CAMPO_OBLIGATORIO_VACIO, iconoError);
			}

			try {
				Utils.campoVacio(password);
			} catch (CampoVacioException ex) {
				noVacios = false;
				cambiarEtiquetas(lblErrorPassword, lblIconoPassword, CAMPO_OBLIGATORIO_VACIO, iconoError);
				lblErrorPassword.setForeground(new Color(255, 106, 106));
			}

			try {
				Utils.campoVacio(fecha);
				lblErrorFecha.setText("");
				lblErrorFecha.setVisible(false);
				// cambiarEtiquetas(lblErrorFecha, null, true, null);
			} catch (CampoVacioException ex) {
				noVacios = false;
				lblErrorFecha.setText(CAMPO_OBLIGATORIO_VACIO);
				lblErrorFecha.setVisible(true);
				// cambiarEtiquetas(lblErrorFecha, null, true, " - Este campo es obligatorio");
			}

			boolean camposCorrectos = nombreApellidosValido && usuarioValido && correoValido && passwordValido;

			if (noVacios && camposCorrectos) {
				System.out.println("DENTRO");

				LocalDate fechaCambiada = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				// Si ya existe un usuario con el mismo nombre de usuario o correo electronico
				if (!Controlador.getControlador().registrarUsuario(nombreApellidos, correo, usuario, password,
						fechaCambiada, presentacion, foto)) {

					cambiarEtiquetas(lblErrorUsuario, lblIconoUsuario, USUARIO_EXISTENTE, iconoError);
					cambiarEtiquetas(lblErrorCorreo, lblIconoCorreo, USUARIO_EXISTENTE, iconoError);

				} else {
					System.out.println("Exito al registrarse");
					limpiarPanel();
					CardLayout c = (CardLayout) frmLoginRegistro.getContentPane().getLayout();
					c.show(frmLoginRegistro.getContentPane(), VentanaLoginRegistro.PANEL_LOGIN);
				}
			}

		});

		GridBagConstraints gbc_btnCrearCuenta = new GridBagConstraints();
		gbc_btnCrearCuenta.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCrearCuenta.ipady = 4;
		gbc_btnCrearCuenta.insets = new Insets(0, 0, 5, 8);
		gbc_btnCrearCuenta.gridx = 0;
		gbc_btnCrearCuenta.gridy = 16;
		panelCentro.add(btnCrearCuenta, gbc_btnCrearCuenta);

		JLabel lblInicioSesion = new JLabel("¿Ya tienes una cuenta?");
		lblInicioSesion.setForeground(new Color(0xb2, 0x00, 0x9c));
		lblInicioSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		lblInicioSesion.addMouseListener(new MouseListener() {

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
				c.show(frmLoginRegistro.getContentPane(), VentanaLoginRegistro.PANEL_LOGIN);

			}
		});

		GridBagConstraints gbc_lblInicioSesion = new GridBagConstraints();
		gbc_lblInicioSesion.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInicioSesion.ipady = 1;
		gbc_lblInicioSesion.insets = new Insets(0, 0, 0, 8);
		gbc_lblInicioSesion.gridx = 0;
		gbc_lblInicioSesion.gridy = 17;
		panelCentro.add(lblInicioSesion, gbc_lblInicioSesion);

	}

}
