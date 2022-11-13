package umu.tds.vistas;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;

import umu.tds.modelo.pojos.Usuario;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/* 
 * - VENTANA PRINCIPAL DE PHOTOTDS -
 * */

public class PhotoTDSVentana {

	private CardLayout c;
	private Usuario usuario;

	private JFrame frame;
	private String defaultPanel = "panelPrincipal";

	private ImageIcon dockBusqueda1;
	private ImageIcon dockBusqueda2;
	private ImageIcon dockPubli1;
	private ImageIcon dockPubli2;
	private ImageIcon dockPrincipal1;
	private ImageIcon dockPrincipal2;
	private ImageIcon dockUsuario1;
	private ImageIcon dockUsuario2;
	private Image bordeImagen;

	public PhotoTDSVentana() {
		cargarRecursos();
		initialize();
	}

	/* Carga los recursos gráficos asociados a la ventana */
	public void cargarRecursos() {
		try {
			dockBusqueda1 = new ImageIcon(PhotoTDSVentana.class.getResource("/imagenes/error.png"));
			dockBusqueda2 = new ImageIcon(PhotoTDSVentana.class.getResource("/imagenes/error.png"));
			dockPubli1 = new ImageIcon(PhotoTDSVentana.class.getResource("/imagenes/error.png"));
			dockPubli2 = new ImageIcon(PhotoTDSVentana.class.getResource("/imagenes/error.png"));
			dockPrincipal1 = new ImageIcon(PhotoTDSVentana.class.getResource("/imagenes/error.png"));
			dockPrincipal2 = new ImageIcon(PhotoTDSVentana.class.getResource("/imagenes/error.png"));
			dockUsuario1 = new ImageIcon(PhotoTDSVentana.class.getResource("/imagenes/error.png"));
			dockUsuario2 = new ImageIcon(PhotoTDSVentana.class.getResource("/imagenes/error.png"));
			bordeImagen = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/bordeImagen.png")).getScaledInstance(260, 80, Image.SCALE_SMOOTH);

		} catch (Exception e) {
			e.printStackTrace();
			terminatePhotoTDS();
		}
	}
	
	public void mostrarVentana() {
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}

	/* Destruye la ventana y libera recursos */
	public void terminatePhotoTDS() {
		this.frame.dispose();		// Cuando movamos todo a un main, destruir la ventana no terminaría la aplicación
		// ¿Añadir más cosas?
	}
	

	/* Inicializa la ventana */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("PhotoTDS");
		frame.setBounds(100, 100, 840, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{80, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel photoTDSDock = new JPanel();
		GridBagConstraints gbc_photoTDSDock = new GridBagConstraints();
		gbc_photoTDSDock.fill = GridBagConstraints.BOTH;
		gbc_photoTDSDock.gridx = 0;
		gbc_photoTDSDock.gridy = 0;
		frame.getContentPane().add(photoTDSDock, gbc_photoTDSDock);
		GridBagLayout gbl_photoTDSDock = new GridBagLayout();
		gbl_photoTDSDock.columnWidths = new int[]{260, 320, 260, 0};
		gbl_photoTDSDock.rowHeights = new int[]{0, 0};
		gbl_photoTDSDock.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_photoTDSDock.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		photoTDSDock.setLayout(gbl_photoTDSDock);
		
		JPanel bordeIzq = new JPanel();
		bordeIzq.setBackground(Color.CYAN);
		GridBagConstraints gbc_bordeIzq = new GridBagConstraints();
		gbc_bordeIzq.fill = GridBagConstraints.BOTH;
		gbc_bordeIzq.gridx = 0;
		gbc_bordeIzq.gridy = 0;
		photoTDSDock.add(bordeIzq, gbc_bordeIzq);
		bordeIzq.setLayout(new BorderLayout(0, 0));
		
		final JLabel bordeIzqIconLabel = new JLabel("");
		bordeIzqIconLabel.setIcon(new ImageIcon(bordeImagen));
		bordeIzqIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bordeIzq.add(bordeIzqIconLabel, BorderLayout.CENTER);
		
		JPanel panelDOCK = new JPanel();
		panelDOCK.setBackground(Color.WHITE);
		GridBagConstraints gbc_panelDOCK = new GridBagConstraints();
		gbc_panelDOCK.fill = GridBagConstraints.BOTH;
		gbc_panelDOCK.gridx = 1;
		gbc_panelDOCK.gridy = 0;
		photoTDSDock.add(panelDOCK, gbc_panelDOCK);
		GridBagLayout gbl_panelDOCK = new GridBagLayout();
		gbl_panelDOCK.columnWidths = new int[]{80, 80, 80, 80, 0};
		gbl_panelDOCK.rowHeights = new int[]{0, 0};
		gbl_panelDOCK.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelDOCK.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelDOCK.setLayout(gbl_panelDOCK);
		
		JPanel dockBusqueda = new JPanel();
		dockBusqueda.setBackground(Color.MAGENTA);
		GridBagConstraints gbc_dockBusqueda = new GridBagConstraints();
		gbc_dockBusqueda.fill = GridBagConstraints.BOTH;
		gbc_dockBusqueda.gridx = 0;
		gbc_dockBusqueda.gridy = 0;
		panelDOCK.add(dockBusqueda, gbc_dockBusqueda);
		dockBusqueda.setLayout(new BorderLayout(0, 0));
		
		final JLabel busquedaIconLabel = new JLabel("");
		busquedaIconLabel.setIcon(dockBusqueda1);
		dockBusqueda.add(busquedaIconLabel, BorderLayout.CENTER);
		
		JPanel dockPubli = new JPanel();
		dockPubli.setBackground(Color.YELLOW);
		GridBagConstraints gbc_dockPubli = new GridBagConstraints();
		gbc_dockPubli.fill = GridBagConstraints.BOTH;
		gbc_dockPubli.gridx = 1;
		gbc_dockPubli.gridy = 0;
		panelDOCK.add(dockPubli, gbc_dockPubli);
		dockPubli.setLayout(new BorderLayout(0, 0));
		
		final JLabel publiIconLabel = new JLabel("");
		publiIconLabel.setIcon(dockPubli1);
		dockPubli.add(publiIconLabel, BorderLayout.CENTER);
		
		JPanel dockPrincipal = new JPanel();
		dockPrincipal.setBackground(Color.RED);
		GridBagConstraints gbc_dockPrincipal = new GridBagConstraints();
		gbc_dockPrincipal.fill = GridBagConstraints.BOTH;
		gbc_dockPrincipal.gridx = 2;
		gbc_dockPrincipal.gridy = 0;
		panelDOCK.add(dockPrincipal, gbc_dockPrincipal);
		dockPrincipal.setLayout(new BorderLayout(0, 0));
		
		final JLabel principalIconLabel = new JLabel("");
		principalIconLabel.setIcon(dockPrincipal1);
		dockPrincipal.add(principalIconLabel, BorderLayout.CENTER);
		
		JPanel dockUsuario = new JPanel();
		dockUsuario.setBackground(Color.GREEN);
		GridBagConstraints gbc_dockUsuario = new GridBagConstraints();
		gbc_dockUsuario.fill = GridBagConstraints.BOTH;
		gbc_dockUsuario.gridx = 3;
		gbc_dockUsuario.gridy = 0;
		panelDOCK.add(dockUsuario, gbc_dockUsuario);
		dockUsuario.setLayout(new BorderLayout(0, 0));
		
		final JLabel usuarioIconLabel = new JLabel("");
		usuarioIconLabel.setIcon(dockUsuario1);
		dockUsuario.add(usuarioIconLabel, BorderLayout.CENTER);
		
		JPanel bordeDrc = new JPanel();
		bordeDrc.setBackground(Color.CYAN);
		GridBagConstraints gbc_bordeDrc = new GridBagConstraints();
		gbc_bordeDrc.fill = GridBagConstraints.BOTH;
		gbc_bordeDrc.gridx = 2;
		gbc_bordeDrc.gridy = 0;
		photoTDSDock.add(bordeDrc, gbc_bordeDrc);
		bordeDrc.setLayout(new BorderLayout(0, 0));
		
		JLabel bordeDrcIconLabel = new JLabel("");
		bordeDrcIconLabel.setIcon(new ImageIcon(bordeImagen));
		bordeDrcIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bordeDrc.add(bordeDrcIconLabel, BorderLayout.CENTER);
		
		JPanel photoTDSRenderPanel = new JPanel();
		GridBagConstraints gbc_photoTDSRenderPanel = new GridBagConstraints();
		gbc_photoTDSRenderPanel.fill = GridBagConstraints.BOTH;
		gbc_photoTDSRenderPanel.gridx = 0;
		gbc_photoTDSRenderPanel.gridy = 1;
		frame.getContentPane().add(photoTDSRenderPanel, gbc_photoTDSRenderPanel);
		photoTDSRenderPanel.setLayout(new CardLayout(0, 0));
		c = (CardLayout)photoTDSRenderPanel.getLayout();
		c.show(photoTDSRenderPanel, defaultPanel);
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBackground(Color.RED);
		photoTDSRenderPanel.add(panelPrincipal, "panelPrincipal");
		dockPrincipal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				c.show(photoTDSRenderPanel, "panelPrincipal");
			}
		});
		
		JPanel panelUsuario = new JPanel();
		panelUsuario.setBackground(Color.GREEN);
		photoTDSRenderPanel.add(panelUsuario, "panelUsuario");
		dockUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				c.show(photoTDSRenderPanel, "panelUsuario");
			}
		});
		
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBackground(Color.MAGENTA);
		photoTDSRenderPanel.add(panelBusqueda, "panelBusqueda");
		dockBusqueda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				c.show(photoTDSRenderPanel, "panelBusqueda");
			}
		});
		
		JPanel panelPubli = new JPanel();
		panelPubli.setBackground(Color.YELLOW);
		photoTDSRenderPanel.add(panelPubli, "panelPubli");
		dockPubli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				c.show(photoTDSRenderPanel, "panelPubli");
			}
		});
	}

	/* Main de la ventana. Borrar cuando se añada a clase main principal de la aplicación */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new FlatDarculaLaf());
					PhotoTDSVentana phototdswin = new PhotoTDSVentana();
					VentanaLoginRegistro loginregistro = new VentanaLoginRegistro();
					VentanaSubirFoto v = new VentanaSubirFoto();
					v.mostrar();
;					phototdswin.frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}