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
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Insets;

/* 
 * - VENTANA PRINCIPAL DE PHOTOTDS -
 * */

public class PhotoTDSVentana {

	private CardLayout c;
	private Usuario usuario;

	private JFrame frame;
	private String defaultPanel = "panelPrincipal";
	
	private Image dockPubli1, dockPubli2;
	private Image dockBusqueda1, dockBusqueda2;
	private Image dockHome1, dockHome2;
	private Image dockUser1, dockUser2;
	private Image errImage, glass;
	private Image bordeIzqImagen, bordeDrcImagen;
	
	private int borderWidth;
	
	

	public PhotoTDSVentana() {
		cargarRecursos();
		initialize();
	}

	/* Carga los recursos gráficos asociados a la ventana */
	public void cargarRecursos() {
		try {
			dockPubli1 = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/dock-images/publi1.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			dockPubli2 = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/dock-images/publi2.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			dockBusqueda1 = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/dock-images/busqueda1.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			dockBusqueda2 = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/dock-images/busqueda2.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			dockHome1 = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/dock-images/home1.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			dockHome2 = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/dock-images/home2.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			dockUser1 = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/dock-images/user1.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			dockUser2 = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/dock-images/user2.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			errImage = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/error.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			glass = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/dock-images/glass.png"));
			bordeIzqImagen = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/dock-images/bordeizq.png")).getScaledInstance(260, 80, Image.SCALE_SMOOTH);
			bordeDrcImagen = ImageIO.read(PhotoTDSVentana.class.getResource("/imagenes/dock-images/bordedrc.png")).getScaledInstance(260, 80, Image.SCALE_SMOOTH);

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
		frame.getContentPane().setBackground(new Color(240, 240, 240));
		frame.setTitle("PhotoTDS");
		frame.setBounds(100, 100, 840, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		borderWidth = (frame.getWidth()-(64*4))/2;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{64, 0, 32, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel photoTDSDock = new JPanel();
		GridBagConstraints gbc_photoTDSDock = new GridBagConstraints();
		gbc_photoTDSDock.fill = GridBagConstraints.BOTH;
		gbc_photoTDSDock.gridx = 0;
		gbc_photoTDSDock.gridy = 0;
		frame.getContentPane().add(photoTDSDock, gbc_photoTDSDock);
		GridBagLayout gbl_photoTDSDock = new GridBagLayout();
		gbl_photoTDSDock.columnWidths = new int[]{292, 256, 292, 0};
		gbl_photoTDSDock.rowHeights = new int[]{0, 0};
		gbl_photoTDSDock.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_photoTDSDock.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		photoTDSDock.setLayout(gbl_photoTDSDock);
		
		JPanel bordeIzq = new JPanel();
		GridBagConstraints gbc_bordeIzq = new GridBagConstraints();
		gbc_bordeIzq.fill = GridBagConstraints.BOTH;
		gbc_bordeIzq.gridx = 0;
		gbc_bordeIzq.gridy = 0;
		photoTDSDock.add(bordeIzq, gbc_bordeIzq);
		bordeIzq.setLayout(new BorderLayout(0, 0));
		
		JLabel bordeIzqGlass = new JLabel("");
		bordeIzqGlass.setIcon(new ImageIcon(bordeIzqImagen));
		bordeIzq.add(bordeIzqGlass, BorderLayout.CENTER);
		
		JPanel panelDOCK = new JPanel();
		//panelDOCK.setBackground(Color.WHITE);
		GridBagConstraints gbc_panelDOCK = new GridBagConstraints();
		gbc_panelDOCK.fill = GridBagConstraints.BOTH;
		gbc_panelDOCK.gridx = 1;
		gbc_panelDOCK.gridy = 0;
		photoTDSDock.add(panelDOCK, gbc_panelDOCK);
		GridBagLayout gbl_panelDOCK = new GridBagLayout();
		gbl_panelDOCK.columnWidths = new int[]{64, 64, 64, 64, 0};
		gbl_panelDOCK.rowHeights = new int[]{0, 0};
		gbl_panelDOCK.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelDOCK.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelDOCK.setLayout(gbl_panelDOCK);
		
		JPanel dockBusqueda = new JPanel();
		dockBusqueda.setToolTipText("Búsqueda");
		//dockBusqueda.setBackground(Color.MAGENTA);
		GridBagConstraints gbc_dockBusqueda = new GridBagConstraints();
		gbc_dockBusqueda.fill = GridBagConstraints.BOTH;
		gbc_dockBusqueda.gridx = 0;
		gbc_dockBusqueda.gridy = 0;
		panelDOCK.add(dockBusqueda, gbc_dockBusqueda);
		dockBusqueda.setLayout(new BorderLayout(0, 0));
		
		final JLabel busquedaIcon = new JLabel("");
		busquedaIcon.setIcon(new ImageIcon(dockBusqueda1));
		dockBusqueda.add(busquedaIcon, BorderLayout.CENTER);
		
		JPanel dockPubli = new JPanel();
		dockPubli.setToolTipText("Nueva publicación");
		//dockPubli.setBackground(Color.YELLOW);
		GridBagConstraints gbc_dockPubli = new GridBagConstraints();
		gbc_dockPubli.fill = GridBagConstraints.BOTH;
		gbc_dockPubli.gridx = 1;
		gbc_dockPubli.gridy = 0;
		panelDOCK.add(dockPubli, gbc_dockPubli);
		dockPubli.setLayout(new BorderLayout(0, 0));
		
		final JLabel publiIcon = new JLabel("");
		publiIcon.setIcon(new ImageIcon(dockPubli1));
		dockPubli.add(publiIcon, BorderLayout.CENTER);
		
		JPanel dockPrincipal = new JPanel();
		dockPrincipal.setToolTipText("Inicio");
		//dockPrincipal.setBackground(Color.RED);
		GridBagConstraints gbc_dockPrincipal = new GridBagConstraints();
		gbc_dockPrincipal.fill = GridBagConstraints.BOTH;
		gbc_dockPrincipal.gridx = 2;
		gbc_dockPrincipal.gridy = 0;
		panelDOCK.add(dockPrincipal, gbc_dockPrincipal);
		dockPrincipal.setLayout(new BorderLayout(0, 0));
		
		final JLabel homeIcon = new JLabel("");
		homeIcon.setIcon(new ImageIcon(dockHome1));
		dockPrincipal.add(homeIcon, BorderLayout.CENTER);
		
		JPanel dockUsuario = new JPanel();
		dockUsuario.setToolTipText("Usuario");
		//dockUsuario.setBackground(Color.GREEN);
		GridBagConstraints gbc_dockUsuario = new GridBagConstraints();
		gbc_dockUsuario.fill = GridBagConstraints.BOTH;
		gbc_dockUsuario.gridx = 3;
		gbc_dockUsuario.gridy = 0;
		panelDOCK.add(dockUsuario, gbc_dockUsuario);
		dockUsuario.setLayout(new BorderLayout(0, 0));
		
		final JLabel usuarioIcon = new JLabel("");
		usuarioIcon.setIcon(new ImageIcon(dockUser1));
		dockUsuario.add(usuarioIcon, BorderLayout.CENTER);
		
		JPanel bordeDrc = new JPanel();
		GridBagConstraints gbc_bordeDrc = new GridBagConstraints();
		gbc_bordeDrc.fill = GridBagConstraints.BOTH;
		gbc_bordeDrc.gridx = 2;
		gbc_bordeDrc.gridy = 0;
		photoTDSDock.add(bordeDrc, gbc_bordeDrc);
		bordeDrc.setLayout(new BorderLayout(0, 0));
		
		JLabel bordeDrcGlass = new JLabel("");
		bordeDrcGlass.setIcon(new ImageIcon(bordeDrcImagen));
		bordeDrc.add(bordeDrcGlass, BorderLayout.CENTER);
		
		frame.getContentPane().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				borderWidth = (frame.getWidth()-(64*4))/2;
				bordeIzqGlass.setIcon(new ImageIcon(bordeIzqImagen.getScaledInstance(borderWidth, 64, Image.SCALE_SMOOTH)));
				bordeDrcGlass.setIcon(new ImageIcon(bordeDrcImagen.getScaledInstance(borderWidth, 64, Image.SCALE_SMOOTH)));
			}
		});
		
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
			public void mouseEntered(MouseEvent e) {
				homeIcon.setIcon(new ImageIcon(dockHome2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				homeIcon.setIcon(new ImageIcon(dockHome1));
			}
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
			public void mouseEntered(MouseEvent e) {
				usuarioIcon.setIcon(new ImageIcon(dockUser2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				usuarioIcon.setIcon(new ImageIcon(dockUser1));
			}
			
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
			public void mouseEntered(MouseEvent e) {
				busquedaIcon.setIcon(new ImageIcon(dockBusqueda2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				busquedaIcon.setIcon(new ImageIcon(dockBusqueda1));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				c.show(photoTDSRenderPanel, "panelBusqueda");
			}
		});
		
		JPanel panelPubli = new JPanel();
		panelPubli.setBackground(Color.YELLOW);
		photoTDSRenderPanel.add(panelPubli, "panelPubli");
		
		JPanel photoTDSLogoPanel = new JPanel();
		GridBagConstraints gbc_photoTDSLogoPanel = new GridBagConstraints();
		gbc_photoTDSLogoPanel.fill = GridBagConstraints.BOTH;
		gbc_photoTDSLogoPanel.gridx = 0;
		gbc_photoTDSLogoPanel.gridy = 2;
		frame.getContentPane().add(photoTDSLogoPanel, gbc_photoTDSLogoPanel);
		dockPubli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				publiIcon.setIcon(new ImageIcon(dockPubli2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				publiIcon.setIcon(new ImageIcon(dockPubli1));
			}
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
					//v.mostrar();
					phototdswin.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}