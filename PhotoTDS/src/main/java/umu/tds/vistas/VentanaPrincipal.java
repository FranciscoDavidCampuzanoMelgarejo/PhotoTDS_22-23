package umu.tds.vistas;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;

import umu.tds.controlador.Controlador;
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
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Insets;

/* 
 * - VENTANA PRINCIPAL DE PHOTOTDS -
 * */

public class VentanaPrincipal {

	private CardLayout c;
	private JFrame frame;
	
	private Image publi1, publi2;
	private Image busqueda1, busqueda2;
	private Image home1, home2;
	private Image user1, user2;
	private Image userDock1, userDock2;
	private Image userPicture;
	private Image errImage, glass;
	private Image dockizq, dockdrc;
	private Image logoizq, logodrc, logoCentro;
	
	private Image fondo;
	
	/* Subir una foto */
	private boolean subiendoFoto;
	

	public VentanaPrincipal() {
		subiendoFoto = false;
		
		cargarRecursos();
		initialize();
	}

	/* Carga los recursos gráficos asociados a la ventana */
	public void cargarRecursos() {
		try {
			publi1 = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-images/publi1.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			publi2 = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-images/publi2.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			busqueda1 = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-images/busqueda1.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			busqueda2 = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-images/busqueda2.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			home1 = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-images/home1.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			home2 = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-images/home2.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			user1 = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-images/user1.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			user2 = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-images/user2.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			errImage = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/error.png")).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
			glass = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-images/glass.png"));
			dockizq = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-images/bordeizq.png")).getScaledInstance(292, 80, Image.SCALE_SMOOTH);
			dockdrc = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-images/bordedrc.png")).getScaledInstance(292, 80, Image.SCALE_SMOOTH);
			logoizq = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/logo-images/logo-bordeizq.png")).getScaledInstance(336, 32, Image.SCALE_SMOOTH);
			logodrc = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/logo-images/logo-bordedrc.png")).getScaledInstance(336, 32, Image.SCALE_SMOOTH);
			logoCentro = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/logo-images/logo-centro.png")).getScaledInstance(168, 32, Image.SCALE_SMOOTH);
			fondo = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/fondos/fondo-3.jpg"));//.getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
			 
			if(Controlador.getControlador().getUserPicture() != null) {
				File archivo = new File(Controlador.getControlador().getUserPicture());
				userPicture = ImageIO.read(archivo);
				
				// Para la imagen sin el cursor encima, colocamos la imagen de usuario sobre el fondo de cristal transparente
				BufferedImage buffer1 = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
				buffer1.getGraphics().drawImage(glass, 0, 0, null);
				buffer1.getGraphics().drawImage(ImageIO.read(archivo).getScaledInstance(32, 32, Image.SCALE_SMOOTH), 16, 16, null);
				userDock1 = (Image) buffer1;
				
				// Para la imagen con el cursor encima, primero rasterizamos la imagen del usuario con menos brillo y luego la colocamos encima del fondo de cristal
				BufferedImage buffer2 = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
					buffer2.getGraphics().drawImage(ImageIO.read(archivo).getScaledInstance(32, 32, Image.SCALE_SMOOTH), 0, 0, null);
				
				WritableRaster raster = buffer2.getRaster();
						int[] pixel = new int[4];
						for(int y = 0; y<raster.getHeight(); y++) {
							for(int x = 0; x<raster.getWidth(); x++) {
								raster.getPixel(x, y, pixel);
								pixel[0] *= 0.75;
								pixel[1] *= 0.75;
								pixel[2] *= 0.75;
								raster.setPixel(x, y, pixel);
							}
						}
						
				BufferedImage buffer3 = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
					buffer3.getGraphics().drawImage(glass, 0, 0, null);
					buffer3.getGraphics().drawImage(buffer2, 16, 16, null);
				userDock2 = (Image) buffer3;
				
			} else { userDock1 = user1; userDock2 = user2; }
			

		} catch (Exception e) {
			e.printStackTrace();
			destruir();
		}
	}
	
	public void mostrar() {
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}

	public void ocultar() {
		frame.setVisible(false);
	}
	
	/* Destruye la ventana y libera recursos */
	public void destruir() {
		this.frame.dispose();
	}

	/* Inicializa la ventana */
	private void initialize() {
		frame = new JFrame();
		frame.setContentPane(new JPanel() {
			{setOpaque(false);}
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(fondo, 0, 0, null);
			}
		});
		
		frame.setTitle("PhotoTDS");
		frame.setBounds(100, 100, 840, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{64, 0, 32, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel photoTDSDock = new JPanel() { {setOpaque(false);} };
		GridBagConstraints gbc_photoTDSDock = new GridBagConstraints();
		gbc_photoTDSDock.fill = GridBagConstraints.BOTH;
		gbc_photoTDSDock.gridx = 0;
		gbc_photoTDSDock.gridy = 0;
		frame.getContentPane().add(photoTDSDock, gbc_photoTDSDock);
		GridBagLayout gbl_photoTDSDock = new GridBagLayout();
		gbl_photoTDSDock.columnWidths = new int[]{0, 256, 0, 0};
		gbl_photoTDSDock.rowHeights = new int[]{0, 0};
		gbl_photoTDSDock.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_photoTDSDock.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		photoTDSDock.setLayout(gbl_photoTDSDock);
		
		JPanel bordeIzq = new JPanel() { {setOpaque(false);} };
		GridBagConstraints gbc_bordeIzq = new GridBagConstraints();
		gbc_bordeIzq.fill = GridBagConstraints.BOTH;
		gbc_bordeIzq.gridx = 0;
		gbc_bordeIzq.gridy = 0;
		photoTDSDock.add(bordeIzq, gbc_bordeIzq);
		bordeIzq.setLayout(new BorderLayout(0, 0));
		
		JLabel bordeIzqGlass = new JLabel("");
		bordeIzqGlass.setIcon(new ImageIcon(dockizq));
		bordeIzq.add(bordeIzqGlass, BorderLayout.CENTER);
		
		JPanel panelDOCK = new JPanel() { {setOpaque(false);} };
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
		
		JPanel dockBusqueda = new JPanel() { {setOpaque(false);} };
		dockBusqueda.setToolTipText("Búsqueda");
		//dockBusqueda.setBackground(Color.MAGENTA);
		GridBagConstraints gbc_dockBusqueda = new GridBagConstraints();
		gbc_dockBusqueda.fill = GridBagConstraints.BOTH;
		gbc_dockBusqueda.gridx = 0;
		gbc_dockBusqueda.gridy = 0;
		panelDOCK.add(dockBusqueda, gbc_dockBusqueda);
		dockBusqueda.setLayout(new BorderLayout(0, 0));
		
		final JLabel busquedaIcon = new JLabel("");
		busquedaIcon.setIcon(new ImageIcon(busqueda1));
		dockBusqueda.add(busquedaIcon, BorderLayout.CENTER);
		
		JPanel dockPubli = new JPanel() { {setOpaque(false);} };
		dockPubli.setToolTipText("Nueva publicación");
		//dockPubli.setBackground(Color.YELLOW);
		GridBagConstraints gbc_dockPubli = new GridBagConstraints();
		gbc_dockPubli.fill = GridBagConstraints.BOTH;
		gbc_dockPubli.gridx = 1;
		gbc_dockPubli.gridy = 0;
		panelDOCK.add(dockPubli, gbc_dockPubli);
		dockPubli.setLayout(new BorderLayout(0, 0));
		
		final JLabel publiIcon = new JLabel("");
		publiIcon.setIcon(new ImageIcon(publi1));
		dockPubli.add(publiIcon, BorderLayout.CENTER);
		
		JPanel dockPrincipal = new JPanel() { {setOpaque(false);} };
		dockPrincipal.setToolTipText("Inicio");
		//dockPrincipal.setBackground(Color.RED);
		GridBagConstraints gbc_dockPrincipal = new GridBagConstraints();
		gbc_dockPrincipal.fill = GridBagConstraints.BOTH;
		gbc_dockPrincipal.gridx = 2;
		gbc_dockPrincipal.gridy = 0;
		panelDOCK.add(dockPrincipal, gbc_dockPrincipal);
		dockPrincipal.setLayout(new BorderLayout(0, 0));
		
		final JLabel homeIcon = new JLabel("");
		homeIcon.setIcon(new ImageIcon(home1));
		dockPrincipal.add(homeIcon, BorderLayout.CENTER);
		
		JPanel dockUsuario = new JPanel() { {setOpaque(false);} };
		dockUsuario.setToolTipText(Controlador.getControlador().getUsername());
		//dockUsuario.setBackground(Color.GREEN);
		GridBagConstraints gbc_dockUsuario = new GridBagConstraints();
		gbc_dockUsuario.fill = GridBagConstraints.BOTH;
		gbc_dockUsuario.gridx = 3;
		gbc_dockUsuario.gridy = 0;
		panelDOCK.add(dockUsuario, gbc_dockUsuario);
		dockUsuario.setLayout(new BorderLayout(0, 0));
		
		final JLabel usuarioIcon = new JLabel("");
		usuarioIcon.setIcon(new ImageIcon(userPicture));
			usuarioIcon.setIcon(new ImageIcon(userDock1));
		dockUsuario.add(usuarioIcon, BorderLayout.CENTER);
		
		JPanel bordeDrc = new JPanel() { {setOpaque(false);} };
		GridBagConstraints gbc_bordeDrc = new GridBagConstraints();
		gbc_bordeDrc.fill = GridBagConstraints.BOTH;
		gbc_bordeDrc.gridx = 2;
		gbc_bordeDrc.gridy = 0;
		photoTDSDock.add(bordeDrc, gbc_bordeDrc);
		bordeDrc.setLayout(new BorderLayout(0, 0));
		
		JLabel bordeDrcGlass = new JLabel("");
		bordeDrcGlass.setIcon(new ImageIcon(dockdrc));
		bordeDrc.add(bordeDrcGlass, BorderLayout.CENTER);
		
		
		JPanel photoTDSRenderPanel = new JPanel() { {setOpaque(false);} };
		GridBagConstraints gbc_photoTDSRenderPanel = new GridBagConstraints();
		gbc_photoTDSRenderPanel.fill = GridBagConstraints.BOTH;
		gbc_photoTDSRenderPanel.gridx = 0;
		gbc_photoTDSRenderPanel.gridy = 1;
		frame.getContentPane().add(photoTDSRenderPanel, gbc_photoTDSRenderPanel);
		photoTDSRenderPanel.setLayout(new CardLayout(0, 0));
		c = (CardLayout)photoTDSRenderPanel.getLayout();
		//c.show(photoTDSRenderPanel, "panelPrincipal");
		
		JPanel panelPrincipal = new JPanel() { {setOpaque(false);} };
		panelPrincipal.setBackground(new Color(0, 255, 255));
		//panelPrincipal.setBackground(Color.RED);
		photoTDSRenderPanel.add(panelPrincipal, "panelPrincipal");
		c.show(photoTDSRenderPanel, "panelPrincipal");
		dockPrincipal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				homeIcon.setIcon(new ImageIcon(home2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				homeIcon.setIcon(new ImageIcon(home1));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				c.show(photoTDSRenderPanel, "panelPrincipal");
			}
		});
		
		JPanel panelUsuario = new JPanel() { {setOpaque(false);} };
		panelUsuario.setBackground(Color.GREEN);
		photoTDSRenderPanel.add(panelUsuario, "panelUsuario");
		GridBagLayout gbl_panelUsuario = new GridBagLayout();
		gbl_panelUsuario.columnWidths = new int[]{0, 640, 0, 0};
		gbl_panelUsuario.rowHeights = new int[]{0, 0};
		gbl_panelUsuario.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelUsuario.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelUsuario.setLayout(gbl_panelUsuario);
		
		JPanel panelUsuarioIzq = new JPanel() { { setOpaque(false); }};
		GridBagConstraints gbc_panelUsuarioIzq = new GridBagConstraints();
		gbc_panelUsuarioIzq.fill = GridBagConstraints.BOTH;
		gbc_panelUsuarioIzq.gridx = 0;
		gbc_panelUsuarioIzq.gridy = 0;
		panelUsuario.add(panelUsuarioIzq, gbc_panelUsuarioIzq);
		
		JPanel panelUsuarioCentro = new JPanel();
		//panelUsuarioCentro.setBackground(Color.GREEN);
		GridBagConstraints gbc_panelUsuarioCentro = new GridBagConstraints();
		gbc_panelUsuarioCentro.fill = GridBagConstraints.BOTH;
		gbc_panelUsuarioCentro.gridx = 1;
		gbc_panelUsuarioCentro.gridy = 0;
		panelUsuario.add(panelUsuarioCentro, gbc_panelUsuarioCentro);
		GridBagLayout gbl_panelUsuarioCentro = new GridBagLayout();
		gbl_panelUsuarioCentro.columnWidths = new int[]{0, 0};
		gbl_panelUsuarioCentro.rowHeights = new int[]{0, 0};
		gbl_panelUsuarioCentro.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelUsuarioCentro.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelUsuarioCentro.setLayout(gbl_panelUsuarioCentro);
		
		JPanel panelUsuarioCentroFoto = new JPanel();
		GridBagConstraints gbc_panelUsuarioCentroFoto = new GridBagConstraints();
		gbc_panelUsuarioCentroFoto.fill = GridBagConstraints.BOTH;
		gbc_panelUsuarioCentroFoto.gridx = 0;
		gbc_panelUsuarioCentroFoto.gridy = 0;
		panelUsuarioCentro.add(panelUsuarioCentroFoto, gbc_panelUsuarioCentroFoto);
		panelUsuarioCentroFoto.setLayout(new BorderLayout(0, 0));
		
		JLabel panelUsuarioFoto = new JLabel("");
			float uwidth = userPicture.getWidth(null);
			float uheight = userPicture.getHeight(null);
			
			
			System.out.println(uwidth + ", " + uheight);
			panelUsuarioFoto.setIcon(new ImageIcon(userPicture));
		panelUsuarioCentroFoto.add(panelUsuarioFoto, BorderLayout.CENTER);
		
		JPanel panelUsuarioDrc = new JPanel(){ { setOpaque(false); }};
		GridBagConstraints gbc_panelUsuarioDrc = new GridBagConstraints();
		gbc_panelUsuarioDrc.fill = GridBagConstraints.BOTH;
		gbc_panelUsuarioDrc.gridx = 2;
		gbc_panelUsuarioDrc.gridy = 0;
		panelUsuario.add(panelUsuarioDrc, gbc_panelUsuarioDrc);
		
		dockUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				usuarioIcon.setIcon(new ImageIcon(userDock2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				usuarioIcon.setIcon(new ImageIcon(userDock1));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				c.show(photoTDSRenderPanel, "panelUsuario");
			}
		});
		
		JPanel panelBusqueda = new JPanel() { {setOpaque(false);} };
		panelBusqueda.setBackground(Color.MAGENTA);
		photoTDSRenderPanel.add(panelBusqueda, "panelBusqueda");
		dockBusqueda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				busquedaIcon.setIcon(new ImageIcon(busqueda2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				busquedaIcon.setIcon(new ImageIcon(busqueda1));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				c.show(photoTDSRenderPanel, "panelBusqueda");
			}
		});
		
		//JPanel panelPubli = new JPanel() { {setOpaque(false);} };
		//panelPubli.setBackground(Color.YELLOW);
		//photoTDSRenderPanel.add(panelPubli, "panelPubli");
		
		JPanel photoTDSLogoPanel = new JPanel() { {setOpaque(false);} };
		GridBagConstraints gbc_photoTDSLogoPanel = new GridBagConstraints();
		gbc_photoTDSLogoPanel.fill = GridBagConstraints.BOTH;
		gbc_photoTDSLogoPanel.gridx = 0;
		gbc_photoTDSLogoPanel.gridy = 2;
		frame.getContentPane().add(photoTDSLogoPanel, gbc_photoTDSLogoPanel);
		GridBagLayout gbl_photoTDSLogoPanel = new GridBagLayout();
		gbl_photoTDSLogoPanel.columnWidths = new int[]{0, 168, -128, 0};
		gbl_photoTDSLogoPanel.rowHeights = new int[]{0, 0};
		gbl_photoTDSLogoPanel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_photoTDSLogoPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		photoTDSLogoPanel.setLayout(gbl_photoTDSLogoPanel);
		
		JPanel logobordeizq = new JPanel() { {setOpaque(false);} };
		GridBagConstraints gbc_logobordeizq = new GridBagConstraints();
		gbc_logobordeizq.fill = GridBagConstraints.BOTH;
		gbc_logobordeizq.gridx = 0;
		gbc_logobordeizq.gridy = 0;
		photoTDSLogoPanel.add(logobordeizq, gbc_logobordeizq);
		logobordeizq.setLayout(new BorderLayout(0, 0));
		
		JLabel logobordeizqglass = new JLabel("");
			logobordeizqglass.setIcon(new ImageIcon(logoizq));
		logobordeizq.add(logobordeizqglass);
		
		JPanel logocentro = new JPanel() { {setOpaque(false);} };
		GridBagConstraints gbc_logocentro = new GridBagConstraints();
		gbc_logocentro.fill = GridBagConstraints.VERTICAL;
		gbc_logocentro.gridx = 1;
		gbc_logocentro.gridy = 0;
		photoTDSLogoPanel.add(logocentro, gbc_logocentro);
		logocentro.setLayout(new BorderLayout(0, 0));
		
		JLabel logocentroglass = new JLabel("");
			logocentroglass.setIcon(new ImageIcon(logoCentro));
		logocentro.add(logocentroglass, BorderLayout.CENTER);
		
		JPanel logobordedrc = new JPanel() { {setOpaque(false);} };
		GridBagConstraints gbc_logobordedrc = new GridBagConstraints();
		gbc_logobordedrc.fill = GridBagConstraints.BOTH;
		gbc_logobordedrc.gridx = 2;
		gbc_logobordedrc.gridy = 0;
		photoTDSLogoPanel.add(logobordedrc, gbc_logobordedrc);
		logobordedrc.setLayout(new BorderLayout(0, 0));
		
		JLabel logobordedrcglass = new JLabel("");
			logobordedrcglass.setIcon(new ImageIcon(logodrc));
		logobordedrc.add(logobordedrcglass, BorderLayout.CENTER);
		
		
		dockPubli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				publiIcon.setIcon(new ImageIcon(publi2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				publiIcon.setIcon(new ImageIcon(publi1));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				//c.show(photoTDSRenderPanel, "panelPubli");
				if(!subiendoFoto) {
					VentanaSubirFoto vsf = new VentanaSubirFoto();
						subiendoFoto = true;
						vsf.mostrar();
				}
			}
		});
		
		
		frame.getContentPane().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				bordeIzqGlass.setIcon(new ImageIcon(dockizq.getScaledInstance((frame.getWidth()-(64*4))/2, 64, Image.SCALE_FAST)));
				bordeDrcGlass.setIcon(new ImageIcon(dockdrc.getScaledInstance((frame.getWidth()-(64*4))/2, 64, Image.SCALE_FAST)));
				logobordeizqglass.setIcon(new ImageIcon(logoizq.getScaledInstance((frame.getWidth()-168)/2, 32, Image.SCALE_FAST)));
				logobordedrcglass.setIcon(new ImageIcon(logodrc.getScaledInstance((frame.getWidth()-168)/2, 32, Image.SCALE_FAST)));
				//fondo = fondo.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_FAST);
					//frame.getContentPane().repaint();
			}
		});
	}
}