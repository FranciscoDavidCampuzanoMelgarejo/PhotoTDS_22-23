package umu.tds.vistas.perfil;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import umu.tds.modelo.pojos.Foto;
import umu.tds.modelo.pojos.Publicacion;
import umu.tds.vistas.publicaciones.DialogoPublicacion;

public class PanelScrollFotos extends JScrollPane {

	private static final int COLUMNAS = 3;
	private static final int NUMERO_FOTOS_CARGAR = 6;
	private static final double PORCENTAJE_BARRA = 0.95;
	private static final double PORCENTAJE_VENTANA = 0.8;

	// Ancho de la ventana maximo permitido para reescalar
	private final int maximoPermitido = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * PORCENTAJE_VENTANA);

	// private List<String> rutas; // Rutas absolutas a cada foto
	private List<Publicacion> fotos;
	private int fotosCargadas, fotosRestantes;
	private int filasActuales = 0;
	int row, col;
	
	// Asociar cada etiqueta con un entero (indice)
	private Map<JLabel, Integer> mapaEtiquetas;

	// Paneles para guardar las fotos
	private JPanel panelFotos;
	private GridBagLayout gbl_panelFotos;

	// Iconos de "like" y comentario
	private Image iconoLike, iconoComentario;

	// Ancho inicial de la ventana (JFrame)
	private int anchoVentana;
	
	private String fotoPerfil;
	private String nickname;

	public PanelScrollFotos(List<Publicacion> fotos, int anchoVentana, String fotoPerfil, String nickname) {
		this.mapaEtiquetas = new HashMap<JLabel, Integer>();
		this.anchoVentana = anchoVentana;
		this.fotos = fotos;
		this.fotoPerfil = fotoPerfil;
		this.nickname = nickname;
		
		this.fotosCargadas = 0;
		this.fotosRestantes = fotos.size();
		this.col = this.row = 0;
		this.iconoLike = new ImageIcon(getClass().getResource("/imagenes/iconos-perfil/heart-solid.png")).getImage();
		this.iconoComentario = new ImageIcon(getClass().getResource("/imagenes/iconos-perfil/comment-solid.png"))
				.getImage();
		inicializar();
		cargar(Math.min(NUMERO_FOTOS_CARGAR, fotosRestantes));
	}

	// Metodo para inicializar el panel (añadir componentes)
	private void inicializar() {
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		panelFotos = new JPanel();
		setViewportView(panelFotos);
		gbl_panelFotos = new GridBagLayout();
		gbl_panelFotos.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panelFotos.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelFotos.setLayout(gbl_panelFotos);
		panelFotos.setBorder(new LineBorder(Color.RED));

		// Al cambiar el tamaño de la ventana principal (cambia el tamaño del panel de
		// scroll), reescalar las imagenes
		this.getViewport().addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("Componente Resized");
				System.out.println("Tamaño maximo del frame: " + Toolkit.getDefaultToolkit().getScreenSize().width);
				System.out.println("Tamaño del viewport: " + PanelScrollFotos.this.getViewport().getWidth());

				int anchoVentanaActual = SwingUtilities.getWindowAncestor(PanelScrollFotos.this).getSize().width;
				int anchoFoto = getAnchoFoto();
				System.out.println("Ancho foto: " + anchoFoto);

				if ((anchoVentanaActual > maximoPermitido && anchoVentana < maximoPermitido)
						|| (anchoVentanaActual <= maximoPermitido)) {
					reescalar(anchoFoto);
				}

				anchoVentana = anchoVentanaActual;
			}
		});

		this.getVerticalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> {
			System.out.println("BARRA");
			JScrollBar barra = (JScrollBar) e.getAdjustable();
			int posicionCarga = (int) ((barra.getMaximum() - barra.getModel().getExtent()) * PORCENTAJE_BARRA);
			System.out.println("Maximo: " + barra.getMaximum());
			System.out.println("Extent: " + barra.getModel().getExtent());
			System.out.println("Posicion carga: " + posicionCarga + " Posicion actual: " + barra.getValue());

			if ((fotosRestantes > 0) && (barra.getValue() >= posicionCarga)) {
				cargar(Math.min(NUMERO_FOTOS_CARGAR, fotosRestantes));
				System.out.println("FOTOS CARGADAS");
			}
		});
	}
	
	
	public void cargarFotos(List<Publicacion> publicaciones) {
		this.fotos = publicaciones;
		this.fotosCargadas = 0;
		this.fotosRestantes = fotos.size();
		this.row = this.col = 0;
		cargar(Math.min(NUMERO_FOTOS_CARGAR, fotosRestantes));
	}

	// Metodos auxiliares (privados)

	// Metodo para cargar las fotos del usuario
	private void cargar(int numeroFotos) {
		System.out.println("Cargar Fotos");
		filasActuales = filasActuales + (int) (Math.ceil(numeroFotos / COLUMNAS));
		gbl_panelFotos.rowHeights = new int[filasActuales + 1];

		double[] pesosFilas = new double[filasActuales + 1];
		pesosFilas[filasActuales] = Double.MIN_VALUE;
		gbl_panelFotos.rowWeights = pesosFilas;

		int anchoFoto = getAnchoFoto();
		System.out.println(anchoFoto);
		for (int i = 0; i < numeroFotos; i++) {

			Foto foto = (Foto) fotos.get(fotosCargadas);
			JLabel lblCelda = new EtiquetaFotoPerfil(foto.getRuta(), iconoLike, iconoComentario, foto.getLikes(), foto.getComentarios().size(), anchoFoto);
			mapaEtiquetas.put(lblCelda, fotosCargadas++);
			
			// Añadir MouseListener a la etiqueta
			lblCelda.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PanelScrollFotos.this);
					DialogoPublicacion dialogoPublicacion = new DialogoPublicacion(frame, fotos, mapaEtiquetas.get(lblCelda), fotoPerfil, nickname);
					dialogoPublicacion.mostrar();
				}
			});
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = col;
			gbc.gridy = row;
			gbc.fill = GridBagConstraints.NONE;
			gbc.anchor = GridBagConstraints.NORTH;
			gbc.insets = new Insets(8, 10, 0, 0);
			panelFotos.add(lblCelda, gbc);

			col = (col + 1) % 3;
			if (col == 0)
				row++;
		}

		fotosRestantes -= numeroFotos;
		reescalar(anchoFoto);
		System.out.println("FIN CARGAR FOTOS");
	}

	// Metodo para calcular el ancho de la foto en funcion del tamaño de la ventana
	// (JFrame)
	private int getAnchoFoto() {
		if (this.getViewport().getSize().width <= 0) {
			return (int) (anchoVentana / 3);
		}

		// Si el ancho de la ventana es mayor que el maximo permitido, entonces para
		// calcular el ancho de la foto hay que caluclar la diferencia
		// entre el ancho de la ventana actual y el ancho del panel.
		// Dividir el resultado entre las columnas y restar 10
		int anchoVentanaActual = SwingUtilities.getWindowAncestor(this).getSize().width;
		if (anchoVentanaActual > maximoPermitido) {
			int diferencia = anchoVentanaActual - this.getViewport().getSize().width;
			return (int) (((maximoPermitido - diferencia) / COLUMNAS) - 15);
		} else {
			return (int) ((this.getViewport().getSize().width / COLUMNAS) - 15);
		}
	}

	// Metodo para reescalar las fotos del panel
	private void reescalar(int anchoFoto) {
		Arrays.stream(panelFotos.getComponents()).forEach((c) -> {
			if (c instanceof JLabel) {
				EtiquetaFotoPerfil label = (EtiquetaFotoPerfil) c;
				// int nuevoAncho =
				// (int)(SwingUtilities.getWindowAncestor(PanelScrollFotos.this).getSize().width
				// / 4);
				label.reescalar(anchoFoto);
			}
		});
	}

}
