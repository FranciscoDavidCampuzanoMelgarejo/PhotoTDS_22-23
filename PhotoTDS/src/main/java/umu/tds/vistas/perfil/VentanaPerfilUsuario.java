package umu.tds.vistas.perfil;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import java.awt.BorderLayout;
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
import umu.tds.vistas.Utils;

import javax.swing.JScrollPane;
import java.awt.Cursor;
import javax.swing.ScrollPaneConstants;

public class VentanaPerfilUsuario {

	private static final int ANCHO_FOTO_PERFIL = 150; // Ancho de la foto de perfil en la ventana del perfil del usuario

	private static final int COLUMNAS = 3;
	private static final int MINIMO_FOTOS_CARGAR = 6; // Numero de fotos del usuario que se cargan en su perfil
														// inicialmente
	private static final int ANCHO_FOTO_INICIAL = 198; // Ancho inicial de las fotos que se cargan en el perfil

	private static final int MINIMO_FOTO = 158; // Ancho minimo de las fotos que se cargan
	private static final int MAXIMO_FOTO = 295; // Ancho maximo de las fotos que se cargan

	private static final double ESCALA_FOTO_PERFIL = 0.333; // Al clickar en la foto de perfil, esta debe ocupar 1/3 de
															// la pantalla

	private static final double PORCENTAJE_BARRA = 0.9;
	
	private static VentanaPerfilUsuario instancia = null;

	private JFrame frame;
	private Icon fotoPerfil;
	private String rutaFotoPerfil;
	private JLabel lblFotoPerfil;

	// Cargar las fotos del usuario
	private JScrollPane panelScrollFotos;
	private JPanel panelFotosUsuario;
	private GridBagLayout gbl_panelFotosUsuario;
	private int filasActuales;
	private int anchoFotoActual; // Ancho que debe de tener cada foto
	private double anchoPanelActual; // Ancho del panel (cambia al estirarse y estrecharse)

	private List<String> rutasFotos; // Path de cada foto que se va a cargar
	private Map<JLabel, Image> etiquetasImagenes; // Guarda que imagen corresponde a cada etiqueta
	private int fotosCargadas, fotosRestantes;
	private int col, row;
	
	private List<ActionListener> listeners;

	private VentanaPerfilUsuario() {
		this.rutaFotoPerfil = Controlador.getControlador().getUserPicture();

		System.out.println(rutaFotoPerfil);
		this.fotoPerfil = rutaFotoPerfil == null
				? new ImageIcon(getClass().getResource("/imagenes/noprofilepic.png"))
				: new ImageIcon(rutaFotoPerfil);

		this.filasActuales = 0;
		this.fotosCargadas = 0;
		this.fotosRestantes = Controlador.getControlador().getUsuarioLogueado().getPublicaciones().size();
		row = col = 0;
		this.etiquetasImagenes = new HashMap<JLabel, Image>();
		this.rutasFotos = Controlador.getControlador().getUsuarioLogueado().getRutasFotos();
		initialize();
	}
	
	public static VentanaPerfilUsuario getInstancia() {
		if(instancia==null) instancia = new VentanaPerfilUsuario();
		return instancia;
	}

	public void addActionListener(ActionListener a) {
		if(listeners==null) listeners = new LinkedList<ActionListener>();
		listeners.add(a);
	}

	public void notificacionPerfilUsuario(ActionEvent e) {
		listeners.stream().forEach(l -> l.actionPerformed(e));
	}
	
	public void mostrar() {
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}

	public void destruir() {
		frame.dispose();
		instancia = null;
	}
	
	private void cambiarFotoPerfil() {
		/*
		this.rutaFotoPerfil = Controlador.getControlador().getUserPicture();
		this.fotoPerfil = new ImageIcon(rutaFotoPerfil);

		BufferedImage masked = Utils.redondearImagen(ANCHO_FOTO_PERFIL, fotoPerfil);
		this.lblFotoPerfil.setIcon(new ImageIcon(masked));
		notificacionPerfilUsuario(new ActionEvent(this, 6, "cambioFotoPerfil"));
		*/
		rutaFotoPerfil = Controlador.getControlador().getUserPicture();
		fotoPerfil = (rutaFotoPerfil==null) ? new ImageIcon(getClass().getResource("/imagenes/noprofilepic.png")) : new ImageIcon(rutaFotoPerfil);
		fotoPerfil = new ImageIcon(Utils.redondearImagen(ANCHO_FOTO_PERFIL, fotoPerfil));
		this.lblFotoPerfil.setIcon(fotoPerfil);
		notificacionPerfilUsuario(new ActionEvent(this, 6, "cambioFotoPerfil"));
	}

	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(690, 590));
		frame.setBounds(100, 100, 700, 420);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent w) {
				notificacionPerfilUsuario(new ActionEvent(this, 6, "perfilusuarioCerrando"));
			}
		});

		JPanel panelContenedor = new JPanel();
		frame.getContentPane().add(panelContenedor, BorderLayout.CENTER);
		GridBagLayout gbl_panelContenedor = new GridBagLayout();
		gbl_panelContenedor.columnWidths = new int[] { 30, 0, 30, 0 };
		gbl_panelContenedor.rowHeights = new int[] { 30, 0, 30, 0, 0, 0, 30, 0 };
		gbl_panelContenedor.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelContenedor.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panelContenedor.setLayout(gbl_panelContenedor);

		// Cada vez que cambie el tamaño de la pantalla (panelContenedor), las fotos
		// deben reescalar
		panelContenedor.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				//System.out.println("DENTRO");
				//System.out.println(frame.getSize());
				//System.out.println(panelScrollFotos.getSize());

				int anchoFoto = (int) Math.floor(panelScrollFotos.getSize().getWidth() / COLUMNAS) - 12;
				boolean reescalar = true;
				if ((anchoFoto < MINIMO_FOTO && anchoFotoActual == MINIMO_FOTO)
						|| (anchoFoto > MAXIMO_FOTO && anchoFotoActual == MAXIMO_FOTO))
					reescalar = false;
				else {
					anchoFotoActual = anchoFoto;
					if (anchoFoto <= MINIMO_FOTO)
						anchoFotoActual = MINIMO_FOTO;
					if (anchoFoto >= MAXIMO_FOTO)
						anchoFotoActual = MAXIMO_FOTO;
				}

				if (panelScrollFotos.getSize().getWidth() != anchoPanelActual && reescalar) {
					//System.out.println("RESIZED");
					anchoPanelActual = panelScrollFotos.getSize().getWidth();
					//System.out.println("Ancho foto: " + anchoFotoActual);

					for (JLabel label : etiquetasImagenes.keySet()) {
						ImageIcon icono = new ImageIcon(etiquetasImagenes.get(label).getScaledInstance(anchoFotoActual,
								anchoFotoActual, Image.SCALE_SMOOTH));
						label.setIcon(icono);

					}
					panelScrollFotos.revalidate();

				}
			}
		});

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
				int longitudImagen = (int) (ESCALA_FOTO_PERFIL * frame.getSize().height);
				BufferedImage maskedDialogo = Utils.redondearImagen(longitudImagen, fotoPerfil);
				DialogoFotoPerfil dialogoFoto = new DialogoFotoPerfil(frame, maskedDialogo);
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

		JButton btnEditarPerfil = new JButton("Editar Perfil");
		panelNombreUsuario.add(btnEditarPerfil);

		// Al clickar en el boton, abrir un dialogo para editar el perfil del usuario
		btnEditarPerfil.addActionListener((ActionEvent e) -> {
			BufferedImage fotoDialogoPerfil = Utils.redondearImagen(175, fotoPerfil);
			DialogoEditarPerfil dialogoPerfil = new DialogoEditarPerfil(frame, fotoDialogoPerfil, rutaFotoPerfil);
			dialogoPerfil.mostrarDialogo();

			dialogoPerfil.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					if (dialogoPerfil.cambiarFotoPerfil()) {
						cambiarFotoPerfil();
					}
				}
			});

		});

		JLabel lblPublicaciones = new JLabel(
				Controlador.getControlador().getUsuarioLogueado().numeroPublicaciones() + " Publicaciones");
		lblPublicaciones.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPublicaciones = new GridBagConstraints();
		gbc_lblPublicaciones.insets = new Insets(0, 10, 5, 25);
		gbc_lblPublicaciones.gridx = 0;
		gbc_lblPublicaciones.gridy = 2;
		panelPerfil.add(lblPublicaciones, gbc_lblPublicaciones);

		JLabel lblSeguidores = new JLabel(
				Controlador.getControlador().getUsuarioLogueado().numeroSeguidores() + " Seguidores");
		lblSeguidores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSeguidores.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblSeguidores = new GridBagConstraints();
		gbc_lblSeguidores.insets = new Insets(0, 0, 5, 25);
		gbc_lblSeguidores.gridx = 1;
		gbc_lblSeguidores.gridy = 2;
		panelPerfil.add(lblSeguidores, gbc_lblSeguidores);

		// Al clikar en los seguidores, se debe abrir un dialogo con las lista de
		// seguidores del usuario

		JLabel lblSeguidos = new JLabel(Controlador.getControlador().numeroUsuariosSeguidos() + " Seguidos");
		lblSeguidos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSeguidos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblSeguidos = new GridBagConstraints();
		gbc_lblSeguidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidos.gridx = 2;
		gbc_lblSeguidos.gridy = 2;
		panelPerfil.add(lblSeguidos, gbc_lblSeguidos);

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

		panelScrollFotos = new JScrollPane();
		panelScrollFotos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_panelScrollFotos = new GridBagConstraints();
		gbc_panelScrollFotos.anchor = GridBagConstraints.NORTH;
		gbc_panelScrollFotos.insets = new Insets(0, 0, 5, 5);
		gbc_panelScrollFotos.fill = GridBagConstraints.BOTH;
		gbc_panelScrollFotos.gridx = 1;
		gbc_panelScrollFotos.gridy = 5;
		panelContenedor.add(panelScrollFotos, gbc_panelScrollFotos);

		// Al desplazar la barra vertical hacia abajo, cargar nuevas fotos
		panelScrollFotos.getVerticalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> {
			//System.out.println("BARRA");
			JScrollBar barra = (JScrollBar) e.getAdjustable();
			int posicionCarga = (int) ((barra.getMaximum() - barra.getModel().getExtent()) * PORCENTAJE_BARRA);
			//System.out.println("Maximo: " + barra.getMaximum());
			//System.out.println("Extent: " + barra.getModel().getExtent());
			//System.out.println("Posicion carga: " + posicionCarga + " Posicion actual: " + barra.getValue());

			if ((fotosRestantes > 0) && (barra.getValue() >= posicionCarga)) {
				cargarFotos(Math.min(MINIMO_FOTOS_CARGAR, fotosRestantes));
				//System.out.println("FOTOS CARGADAS");
			}

		});

		panelFotosUsuario = new JPanel();
		panelScrollFotos.setViewportView(panelFotosUsuario);
		gbl_panelFotosUsuario = new GridBagLayout();
		gbl_panelFotosUsuario.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panelFotosUsuario.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		panelFotosUsuario.setLayout(gbl_panelFotosUsuario);

		cargarFotos(Math.min(MINIMO_FOTOS_CARGAR, (fotosRestantes)));
	}

	private void cargarFotos(int numeroFotos) {
		//System.out.println("cargar");
		filasActuales = filasActuales + (int) (Math.ceil(numeroFotos / COLUMNAS));
		gbl_panelFotosUsuario.rowHeights = new int[filasActuales + 1];

		double pesosFilas[] = new double[filasActuales + 1];
		pesosFilas[filasActuales] = Double.MIN_VALUE;
		gbl_panelFotosUsuario.rowWeights = pesosFilas;

		//System.out.println("Ancho actual: " + anchoPanelActual);
		int anchoFoto = anchoFotoActual = ANCHO_FOTO_INICIAL;

		for (int i = 0; i < numeroFotos; i++) {
			Image imagen = new ImageIcon(rutasFotos.get(fotosCargadas++)).getImage();
			JLabel label = new JLabel(new ImageIcon(imagen.getScaledInstance(anchoFoto, anchoFoto, Image.SCALE_FAST)));

			label.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseEntered(e);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseExited(e);
				}
			});

			etiquetasImagenes.put(label, imagen);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = col;
			gbc.gridy = row;
			gbc.fill = GridBagConstraints.NONE;
			gbc.anchor = GridBagConstraints.NORTH;
			gbc.insets = new Insets(0, 0, 5, 5);
			panelFotosUsuario.add(label, gbc);
			col = (col + 1) % 3;
			if (col == 0)
				row++;
		}

		fotosRestantes -= numeroFotos;
		// frame.revalidate();
		anchoPanelActual = panelScrollFotos.getSize().getWidth();
		//System.out.println("Panel Ventana: " + panelScrollFotos.getSize());
		//System.out.println("Panel Fotos: " + panelFotosUsuario.getSize());
		//System.out.println("FIN");
	}

}
