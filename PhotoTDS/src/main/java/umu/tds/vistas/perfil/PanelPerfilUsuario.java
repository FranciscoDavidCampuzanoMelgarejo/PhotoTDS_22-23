package umu.tds.vistas.perfil;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JSeparator;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.pojos.Publicacion;
import umu.tds.modelo.pojos.Usuario;
import umu.tds.vistas.Utils;
import umu.tds.vistas.VentanaAbout;
import umu.tds.vistas.VentanaPremium;
import umu.tds.vistas.VentanaSubirFoto;
import umu.tds.vistas.seguidores.DialogoSeguidores;

import javax.swing.JScrollPane;
import java.awt.Cursor;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class PanelPerfilUsuario extends JPanel implements ActionListener{

	private static final int ANCHO_FOTO_PERFIL = 150; // Ancho de la foto de perfil en la ventana del perfil del usuario

	private Usuario usuarioPerfil;

	private JFrame framePadre; // Frame padre
	private Icon fotoPerfil; // Foto de perfil sin retocar
	private String rutaFotoPerfil;
	private JLabel lblFotoPerfil;
	
	private List<ActionListener> listeners;
	
	/* Listener para la ventana principal */
	public void addActionListener(ActionListener a) {
		if(listeners==null) listeners = new LinkedList<ActionListener>();
		listeners.add(a);
	}
	
	/* Notificación de nueva foto de perfil a la ventana principal para que actualice la del Dock */
	public void notificarCambioFotoPerfil(ActionEvent a) {
		listeners.stream().forEach(l -> l.actionPerformed(a));
	}

	// Panel de scroll que contiene las fotos del perfil del usuario
	private JScrollPane panelScrollFotos;

	private List<String> rutasFotos; // Path de cada foto que se va a cargar

	private FactoriaBotonPerfil factoria;

	public PanelPerfilUsuario(JFrame framePadre, Usuario usuario) {
		this.framePadre = framePadre;
		this.usuarioPerfil = usuario;
		this.rutaFotoPerfil = usuarioPerfil.getPerfil().getFoto();
		listeners = new LinkedList<ActionListener>();

		System.out.println(rutaFotoPerfil);
		/*
		this.fotoPerfil = rutaFotoPerfil == null
				? new ImageIcon(getClass().getResource("/imagenes/noprofilepic.png"))
				: new ImageIcon(rutaFotoPerfil);
				*/
		this.fotoPerfil = (rutaFotoPerfil==null) ? new ImageIcon(getClass().getResource("/imagenes/noprofilepic.png")) : new ImageIcon(rutaFotoPerfil);
		this.rutasFotos = usuarioPerfil.getRutasFotos();

		// Factoria para crear un boton -> Si el usuario del perfil es el usuario
		// logueado (Editar Perfil)
		// si el usuario logueado no sigue al usuario del perfil (Seguir)
		// si el usuario logueado siguel al usuario del perfil (Siguiendo)
		EstadoBotonPerfil estado = Controlador.getControlador().getUsuarioLogueado().equals(usuario)
				? EstadoBotonPerfil.EDITAR_PERFIL
				: (usuario.isSeguido(Controlador.getControlador().getUsuarioLogueado()) ? EstadoBotonPerfil.SIGUIENDO
						: EstadoBotonPerfil.SEGUIR);

		if (estado.equals(EstadoBotonPerfil.EDITAR_PERFIL))
			factoria = new FactoriaBotonPerfil.FactoriaBotonPerfilBuilder(estado)
					.dialogo(framePadre, fotoPerfil, rutaFotoPerfil).build();
		else
			factoria = new FactoriaBotonPerfil.FactoriaBotonPerfilBuilder(estado).build();
		factoria.addActionListener(this);
		initialize();
	}

	/*
	private void cambiarFotoPerfil() {
		this.rutaFotoPerfil = Controlador.getControlador().getUserPicture();
		this.fotoPerfil = new ImageIcon(rutaFotoPerfil);

		BufferedImage masked = Utils.redondearImagen(ANCHO_FOTO_PERFIL, fotoPerfil);
		this.lblFotoPerfil.setIcon(new ImageIcon(masked));
		
		notificarCambioFotoPerfil(new ActionEvent(this, 6, "cambioFotoPerfil"));
	}
	*/
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		this.setBorder(new LineBorder(Color.yellow));
		setLayout(new BorderLayout(0, 0));
		JPanel panelContenedor = new JPanel();
		add(panelContenedor, BorderLayout.CENTER);
		GridBagLayout gbl_panelContenedor = new GridBagLayout();
		gbl_panelContenedor.columnWidths = new int[] { 30, 0, 30, 0 };
		gbl_panelContenedor.rowHeights = new int[] { 30, 0, 30, 0, 0, 0, 30, 0 };
		gbl_panelContenedor.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelContenedor.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panelContenedor.setLayout(gbl_panelContenedor);

		JPanel panelContenedorSuperior = new JPanel();
		GridBagConstraints gbc_panelContenedorSuperior = new GridBagConstraints();
		gbc_panelContenedorSuperior.anchor = GridBagConstraints.NORTH;
		gbc_panelContenedorSuperior.insets = new Insets(0, 0, 5, 5);
		gbc_panelContenedorSuperior.fill = GridBagConstraints.BOTH;
		gbc_panelContenedorSuperior.gridx = 1;
		gbc_panelContenedorSuperior.gridy = 1;
		panelContenedor.add(panelContenedorSuperior, gbc_panelContenedorSuperior);
		GridBagLayout gbl_panelContenedorSuperior = new GridBagLayout();
		gbl_panelContenedorSuperior.columnWidths = new int[] { 0, 10, 0, 0 };
		gbl_panelContenedorSuperior.rowHeights = new int[] { 0, 0 };
		gbl_panelContenedorSuperior.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelContenedorSuperior.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelContenedorSuperior.setLayout(gbl_panelContenedorSuperior);

		BufferedImage masked = Utils.redondearImagen(ANCHO_FOTO_PERFIL, fotoPerfil); // Foto redondeada
		lblFotoPerfil = new JLabel(new ImageIcon(masked));
		lblFotoPerfil.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
		gbc_lblFotoPerfil.insets = new Insets(0, 0, 0, 5);
		gbc_lblFotoPerfil.gridx = 0;
		gbc_lblFotoPerfil.gridy = 0;
		panelContenedorSuperior.add(lblFotoPerfil, gbc_lblFotoPerfil);

		// Al clickar en la foto de pefil, se abre un dialogo con la foto en grande
		lblFotoPerfil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DialogoFotoPerfil dialogoFoto = new DialogoFotoPerfil(framePadre.getBounds(), fotoPerfil);
				dialogoFoto.mostrarDialogo();
			}
		});

		JPanel panelPerfil = new JPanel();
		GridBagConstraints gbc_panelPerfil = new GridBagConstraints();
		gbc_panelPerfil.insets = new Insets(0, 0, 0, 5);
		gbc_panelPerfil.fill = GridBagConstraints.BOTH;
		gbc_panelPerfil.gridx = 2;
		gbc_panelPerfil.gridy = 0;
		panelContenedorSuperior.add(panelPerfil, gbc_panelPerfil);
		GridBagLayout gbl_panelPerfil = new GridBagLayout();
		gbl_panelPerfil.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panelPerfil.rowHeights = new int[] { 0, 20, 0, 20, 0, 0, 0 };
		gbl_panelPerfil.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelPerfil.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelPerfil.setLayout(gbl_panelPerfil);

		JPanel panelNombreUsuario = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelNombreUsuario.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelNombreUsuario = new GridBagConstraints();
		gbc_panelNombreUsuario.insets = new Insets(0, 10, 5, 5);
		gbc_panelNombreUsuario.gridwidth = 3;
		gbc_panelNombreUsuario.anchor = GridBagConstraints.NORTHWEST;
		gbc_panelNombreUsuario.gridx = 0;
		gbc_panelNombreUsuario.gridy = 0;
		panelPerfil.add(panelNombreUsuario, gbc_panelNombreUsuario);

		JLabel lblNombreUsuario = new JLabel(Controlador.getControlador().getUsername());
		lblNombreUsuario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelNombreUsuario.add(lblNombreUsuario);

		Component rigidArea = Box.createRigidArea(new Dimension(30, 20));
		panelNombreUsuario.add(rigidArea);

		// Boton para editar perfil, seguir a un usuario, o dejar de seguirlo
		JButton botonPerfil = factoria.crearBoton();
		panelNombreUsuario.add(botonPerfil);

		JLabel lblPublicaciones = new JLabel(
				Controlador.getControlador().getUsuarioLogueado().numeroPublicaciones() + " Publicaciones");
		lblPublicaciones.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPublicaciones = new GridBagConstraints();
		gbc_lblPublicaciones.insets = new Insets(0, 10, 5, 25);
		gbc_lblPublicaciones.gridx = 0;
		gbc_lblPublicaciones.gridy = 2;
		panelPerfil.add(lblPublicaciones, gbc_lblPublicaciones);

		JLabel lblSeguidores = new JLabel(usuarioPerfil.numeroSeguidores() + " Seguidores");
		lblSeguidores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSeguidores.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblSeguidores = new GridBagConstraints();
		gbc_lblSeguidores.insets = new Insets(0, 0, 5, 25);
		gbc_lblSeguidores.gridx = 1;
		gbc_lblSeguidores.gridy = 2;
		panelPerfil.add(lblSeguidores, gbc_lblSeguidores);

		// Al clikar en los seguidores, se debe abrir un dialogo con las lista de
		// seguidores del usuario

		lblSeguidores.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				DialogoSeguidores dialogoSeguidores = new DialogoSeguidores("Seguidores", usuarioPerfil.getSeguidores(),
						framePadre.getSize().height, framePadre.getContentPane());
						dialogoSeguidores.mostrar();
						
			}
		});

		JLabel lblSeguidos = new JLabel(
				Controlador.getControlador().getNumeroUsuariosSeguidos(usuarioPerfil) + " Seguidos");
		lblSeguidos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSeguidos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblSeguidos = new GridBagConstraints();
		gbc_lblSeguidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidos.gridx = 2;
		gbc_lblSeguidos.gridy = 2;
		panelPerfil.add(lblSeguidos, gbc_lblSeguidos);

		// Al clickar en los usuarios seguidos, se debe abrir un dialogo con una lista de
		// los usuarios a los que sigue el usuario logueado

		lblSeguidos.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				DialogoSeguidores dialogoSeguidores = new DialogoSeguidores("Siguiendo",
						Controlador.getControlador().getUsuariosSeguidos(usuarioPerfil), framePadre.getSize().height,
						framePadre.getContentPane());
				dialogoSeguidores.mostrar();
			}
		});

		JLabel lblNombreCompleto = new JLabel(Controlador.getControlador().getUserNombre());
		lblNombreCompleto.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblNombreCompleto = new GridBagConstraints();
		gbc_lblNombreCompleto.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNombreCompleto.gridwidth = 3;
		gbc_lblNombreCompleto.insets = new Insets(0, 10, 5, 5);
		gbc_lblNombreCompleto.gridx = 0;
		gbc_lblNombreCompleto.gridy = 4;
		panelPerfil.add(lblNombreCompleto, gbc_lblNombreCompleto);

		JLabel lblCorreo = new JLabel(Controlador.getControlador().getUsuarioLogueado().getEmail());
		lblCorreo.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.gridwidth = 3;
		gbc_lblCorreo.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblCorreo.insets = new Insets(0, 10, 0, 5);
		gbc_lblCorreo.gridx = 0;
		gbc_lblCorreo.gridy = 5;
		panelPerfil.add(lblCorreo, gbc_lblCorreo);

		JSeparator separadorPerfil = new JSeparator();
		GridBagConstraints gbc_separadorPerfil = new GridBagConstraints();
		gbc_separadorPerfil.fill = GridBagConstraints.BOTH;
		gbc_separadorPerfil.anchor = GridBagConstraints.NORTH;
		gbc_separadorPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_separadorPerfil.gridx = 1;
		gbc_separadorPerfil.gridy = 3;
		panelContenedor.add(separadorPerfil, gbc_separadorPerfil);

		JPanel panelContenedorBotones = new JPanel();
		GridBagConstraints gbc_panelContenedorBotones = new GridBagConstraints();
		gbc_panelContenedorBotones.anchor = GridBagConstraints.NORTH;
		gbc_panelContenedorBotones.insets = new Insets(0, 0, 5, 5);
		gbc_panelContenedorBotones.fill = GridBagConstraints.BOTH;
		gbc_panelContenedorBotones.gridx = 1;
		gbc_panelContenedorBotones.gridy = 4;
		panelContenedor.add(panelContenedorBotones, gbc_panelContenedorBotones);
		GridBagLayout gbl_panelContenedorBotones = new GridBagLayout();
		gbl_panelContenedorBotones.columnWidths = new int[] { 0, 0 };
		gbl_panelContenedorBotones.rowHeights = new int[] { 0, 0 };
		gbl_panelContenedorBotones.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelContenedorBotones.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelContenedorBotones.setLayout(gbl_panelContenedorBotones);

		JPanel panelBotones = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelBotones.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		GridBagConstraints gbc_panelBotones = new GridBagConstraints();
		gbc_panelBotones.anchor = GridBagConstraints.NORTH;
		gbc_panelBotones.gridx = 0;
		gbc_panelBotones.gridy = 0;
		panelContenedorBotones.add(panelBotones, gbc_panelBotones);

		JButton btnFotos = new JButton("FOTOS");
		btnFotos.setOpaque(false);
		btnFotos.setContentAreaFilled(false);
		btnFotos.setBorderPainted(false);
		btnFotos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelBotones.add(btnFotos);

		Component rigidArea_1 = Box.createRigidArea(new Dimension(30, 20));
		panelBotones.add(rigidArea_1);

		JButton btnAlbumes = new JButton("ALBUMES");
		btnAlbumes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAlbumes.setContentAreaFilled(false);
		btnAlbumes.setBorderPainted(false);
		btnAlbumes.setOpaque(false);
		panelBotones.add(btnAlbumes);

		System.out.println("me adentro al abismo");
		System.out.println(framePadre.getSize().width);
		panelScrollFotos = new PanelScrollFotos(usuarioPerfil.getPublicaciones(), framePadre.getSize().width,
				rutaFotoPerfil, usuarioPerfil.getUsuario());
		GridBagConstraints gbc_panelScrollFotos = new GridBagConstraints();
		gbc_panelScrollFotos.insets = new Insets(0, 0, 5, 5);
		gbc_panelScrollFotos.fill = GridBagConstraints.BOTH;
		gbc_panelScrollFotos.gridx = 1;
		gbc_panelScrollFotos.gridy = 5;
		panelContenedor.add(panelScrollFotos, gbc_panelScrollFotos);
		

	}
	
	public void cambiarFotoPerfil() {
		if (!rutaFotoPerfil.equals(usuarioPerfil.getPerfil().getFoto())) {
			this.rutaFotoPerfil = usuarioPerfil.getPerfil().getFoto();
			this.fotoPerfil = new ImageIcon(rutaFotoPerfil);
			
			BufferedImage fotoRedondeada = Utils.redondearImagen(ANCHO_FOTO_PERFIL, fotoPerfil);
			lblFotoPerfil.setIcon(new ImageIcon(fotoRedondeada));
			factoria.setFotoPerfil(fotoPerfil);
			factoria.setRutaFotoPerfil(rutaFotoPerfil);
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		notificarCambioFotoPerfil(e);
	}
	
	// Metodo que se llama cuando un usuario sube una foto -> Se debe actualizar el panel
	public void actualizarPerfil() {
		this.usuarioPerfil = Controlador.getControlador().getUsuarioLogueado();
		((PanelScrollFotos)this.panelScrollFotos).cargarFotos(usuarioPerfil.getPublicaciones());
	}

}
