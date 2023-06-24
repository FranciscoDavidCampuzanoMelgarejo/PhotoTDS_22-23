package umu.tds.vistas;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.CardLayout;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import umu.tds.controlador.Controlador;
import java.awt.event.*;
import java.awt.Insets;
import javax.swing.SwingConstants;

import pulsador.Luz;

/***
 * @title PhotoTDS dock
 * @author diego
 */
public class PhotoTDSDock extends JPanel{
	private static final long serialVersionUID = 1L;
	private int DOCKICONSIZE1 = 38, DOCKICONSIZE2 = 42;
	
	private CardLayout c;
	private JPanel dock1;
	private Image logo1, home, finder, publi, user, nouser, premium, nopremium; 
	private ImageIcon nousericon, usericon;
	
	private JLabel dock1usericon;
	JLabel premiumicon;
	
	private List<ActionListener> listeners;
	
	/* Queremos que todos los recursos gráficos se pinten utilizando la mayor resolución posible */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g; 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		super.paintComponent(g2d);
	}
	
	/* Constructor del panel del dock */
	public PhotoTDSDock() {
		super();
		this.setOpaque(false);
		try {
			cargarRecursos();
			dibujar();
		} catch (Exception edock) { edock.printStackTrace(); }
	}
	
	/* Listeners */
	public void addActionListener(ActionListener a) {
		if(listeners==null) listeners = new LinkedList<ActionListener>();
		listeners.add(a);
	}
	
	/* Notificar de un cambio */
	public void notificacionDock(ActionEvent a) {
		if(listeners!=null) listeners.stream().forEach(l -> l.actionPerformed(a));
	}
	
	/* Cambia el estilo del dock */
	public void cambiarDock(String estilo) {
		c.show(this, estilo);
	}

	/* Recarga la imagen de usuario del dock */
	public void recargarImagenUsuario(){
		String path = Controlador.getControlador().getUserPicture();
		if(path!=null) {
			try {
				Image pic = ImageIO.read(new File(path));
				ImageIcon i = new ImageIcon(pic);
				user = Utils.redondearImagen(DOCKICONSIZE1, i);
				dock1usericon.setIcon(new ImageIcon(user));
			} catch (IOException ioe) { System.err.println("PHOTOTDS !> error cargando '" + path + "'"); }
		} else { user = nouser; dock1usericon.setIcon(nousericon); }
	}
	
	/* Recarga el icono de premium del dock */
	public void recargarPremium() {
		if(Controlador.getControlador().isPremium()) {
			premiumicon.setIcon(new ImageIcon(premium.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			premiumicon.setToolTipText("Usuario premium");
		} else {
			premiumicon.setIcon(new ImageIcon(nopremium.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			premiumicon.setToolTipText("Usuario no premium");
		}
	}
	
	/* Tamaño de iconos al pasar el cursor sobre ellos */
	public void setDockIconSize1(int size) { this.DOCKICONSIZE1 = size; }
	public void setDockIconSize2(int size) { this.DOCKICONSIZE2 = size; }
	
	/* Precarga de recursos gráficos del dock */
	private void cargarRecursos() throws Exception{
		logo1 = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/logo1.png"));
		home = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/home.png"));
		finder = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/finder.png"));
		publi = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/publi.png"));
		
		nouser = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/nouser.png"));
		nousericon = new ImageIcon(nouser.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH));
		
		premium = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/premium.png"));
		nopremium = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/nopremium.png"));

		// Si el usuario no tiene foto, saltará la excepción y simplemente se usará la imagen por defecto
		String userpicpath = Controlador.getControlador().getUserPicture();
		try {
			File f = new File(userpicpath);
			user = ImageIO.read(f);
			user = Utils.redondearImagen(user.getWidth(null), new ImageIcon(user));
		} catch (Exception e) { user = nouser; usericon = nousericon; }
	}
	
	/* Dibujado de componentes del dock */
	private void dibujar() throws Exception{
		c = new CardLayout(0,0);
		setLayout(c);
				
		dock1 = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); }
		};

		add(dock1, "dock1");
		GridBagLayout gbl_dock1 = new GridBagLayout();
		gbl_dock1.columnWidths = new int[]{0, 0};
		gbl_dock1.rowHeights = new int[]{0, 64, 64, 64, 64, 64, 0, 64, 0};
		gbl_dock1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_dock1.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		dock1.setLayout(gbl_dock1);
		
		JPanel dock1logo = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); }
		};
		GridBagConstraints gbc_dock1logo = new GridBagConstraints();
		gbc_dock1logo.insets = new Insets(0, 0, 5, 0);
		gbc_dock1logo.fill = GridBagConstraints.VERTICAL;
		gbc_dock1logo.gridx = 0;
		gbc_dock1logo.gridy = 1;
		dock1.add(dock1logo, gbc_dock1logo);
		dock1logo.setLayout(new BorderLayout(0, 0));
		
		JLabel dock1logoicon = new JLabel("");
		dock1logoicon.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent e) {
				notificacionDock(new ActionEvent(this, 4, "about"));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				dock1logoicon.setIcon(new ImageIcon(logo1.getScaledInstance(DOCKICONSIZE2, 54, Image.SCALE_SMOOTH)));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				dock1logoicon.setIcon(new ImageIcon(logo1.getScaledInstance(DOCKICONSIZE1, 50, Image.SCALE_SMOOTH)));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				dock1logoicon.setIcon(new ImageIcon(Utils.cambiarBrillo(0.7f, logo1.getScaledInstance(DOCKICONSIZE2, 54, Image.SCALE_SMOOTH))));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				dock1logoicon.setIcon(new ImageIcon(logo1.getScaledInstance(DOCKICONSIZE1, 50, Image.SCALE_SMOOTH)));
			}
		});
		dock1logoicon.setToolTipText("PhotoTDS");
			dock1logoicon.setIcon(new ImageIcon(logo1.getScaledInstance(DOCKICONSIZE1, 50, Image.SCALE_SMOOTH)));
		dock1logo.add(dock1logoicon, BorderLayout.CENTER);
		
		JPanel dock1home = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); }
		};
		GridBagConstraints gbc_dock1home = new GridBagConstraints();
		gbc_dock1home.insets = new Insets(0, 0, 5, 0);
		gbc_dock1home.fill = GridBagConstraints.VERTICAL;
		gbc_dock1home.gridx = 0;
		gbc_dock1home.gridy = 2;
		dock1.add(dock1home, gbc_dock1home);
		dock1home.setLayout(new BorderLayout(0, 0));
		
		JLabel dock1homeicon = new JLabel("");
		dock1homeicon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				dock1homeicon.setIcon(new ImageIcon(home.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH)));
				//dock1homeicon.setIcon(new ImageIcon(((ImageIcon)dock1homeicon.getIcon()).getImage().getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH)));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				dock1homeicon.setIcon(new ImageIcon(home.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
				//dock1homeicon.setIcon(new ImageIcon(((ImageIcon)dock1homeicon.getIcon()).getImage().getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				dock1homeicon.setIcon(new ImageIcon(Utils.cambiarBrillo(0.7f, home.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH))));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				dock1homeicon.setIcon(new ImageIcon(home.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				notificacionDock(new ActionEvent(this, 0, "home"));
			}
		});
		dock1homeicon.setToolTipText("Inicio");
			dock1homeicon.setIcon(new ImageIcon(home.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
		dock1home.add(dock1homeicon, BorderLayout.CENTER);
		
		JPanel dock1finder = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); }
		};
		GridBagConstraints gbc_dock1finder = new GridBagConstraints();
		gbc_dock1finder.insets = new Insets(0, 0, 5, 0);
		gbc_dock1finder.fill = GridBagConstraints.VERTICAL;
		gbc_dock1finder.gridx = 0;
		gbc_dock1finder.gridy = 3;
		dock1.add(dock1finder, gbc_dock1finder);
		dock1finder.setLayout(new BorderLayout(0, 0));
		
		JLabel dock1findericon = new JLabel("");
		dock1findericon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				dock1findericon.setIcon(new ImageIcon(finder.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH)));
				//dock1findericon.setIcon(new ImageIcon(((ImageIcon)dock1findericon.getIcon()).getImage().getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH)));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				dock1findericon.setIcon(new ImageIcon(finder.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
				//dock1findericon.setIcon(new ImageIcon(((ImageIcon)dock1findericon.getIcon()).getImage().getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				dock1findericon.setIcon(new ImageIcon(Utils.cambiarBrillo(0.7f, finder.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH))));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				dock1findericon.setIcon(new ImageIcon(finder.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				notificacionDock(new ActionEvent(this, 1, "finder"));
			}
		});
		dock1findericon.setToolTipText("Buscar");
			dock1findericon.setIcon(new ImageIcon(finder.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
		dock1finder.add(dock1findericon, BorderLayout.CENTER);
		
		JPanel dock1publi = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); }
		};
		GridBagConstraints gbc_dock1publi = new GridBagConstraints();
		gbc_dock1publi.insets = new Insets(0, 0, 5, 0);
		gbc_dock1publi.fill = GridBagConstraints.VERTICAL;
		gbc_dock1publi.gridx = 0;
		gbc_dock1publi.gridy = 4;
		dock1.add(dock1publi, gbc_dock1publi);
		dock1publi.setLayout(new BorderLayout(0, 0));
		
		JLabel dock1publiicon = new JLabel("");
		dock1publiicon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				dock1publiicon.setIcon(new ImageIcon(publi.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH)));
				//dock1publiicon.setIcon(new ImageIcon(((ImageIcon)dock1publiicon.getIcon()).getImage().getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH)));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				dock1publiicon.setIcon(new ImageIcon(publi.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
				//dock1publiicon.setIcon(new ImageIcon(((ImageIcon)dock1publiicon.getIcon()).getImage().getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				dock1publiicon.setIcon(new ImageIcon(Utils.cambiarBrillo(0.7f, publi.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH))));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				dock1publiicon.setIcon(new ImageIcon(publi.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				notificacionDock(new ActionEvent(this, 2, "publi"));
			}
		});
			dock1publiicon.setToolTipText("Publicar");
			dock1publiicon.setIcon(new ImageIcon(publi.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
		dock1publi.add(dock1publiicon, BorderLayout.CENTER);
		
		JPanel dock1user = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); }
		};
		GridBagConstraints gbc_dock1user = new GridBagConstraints();
		gbc_dock1user.insets = new Insets(0, 0, 5, 0);
		gbc_dock1user.fill = GridBagConstraints.VERTICAL;
		gbc_dock1user.gridx = 0;
		gbc_dock1user.gridy = 5;
		dock1.add(dock1user, gbc_dock1user);
		dock1user.setLayout(new BorderLayout(0, 0));
		
		dock1usericon = new JLabel("");
		dock1usericon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				dock1usericon.setIcon(new ImageIcon(user.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH)));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				dock1usericon.setIcon(new ImageIcon(user.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				dock1usericon.setIcon(new ImageIcon(Utils.cambiarBrillo(0.7f, user.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH))));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				dock1usericon.setIcon(new ImageIcon(user.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				notificacionDock(new ActionEvent(this, 3, "user"));
			}
		});
			dock1usericon.setToolTipText(Controlador.getControlador().getUsername());
			dock1usericon.setIcon(new ImageIcon(user.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			
		dock1user.add(dock1usericon, BorderLayout.CENTER);
		
		JPanel dockpremium = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); }
		};
		GridBagConstraints gbc_dockpremium = new GridBagConstraints();
		gbc_dockpremium.fill = GridBagConstraints.VERTICAL;
		gbc_dockpremium.gridx = 0;
		gbc_dockpremium.gridy = 7;
		dock1.add(dockpremium, gbc_dockpremium);
		dockpremium.setLayout(new BorderLayout(0, 0));
		
		premiumicon = new JLabel("");
		premiumicon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(Controlador.getControlador().isPremium()) premiumicon.setIcon(new ImageIcon(premium.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH)));
				else premiumicon.setIcon(new ImageIcon(nopremium.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH)));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				if(Controlador.getControlador().isPremium()) premiumicon.setIcon(new ImageIcon(premium.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
				else premiumicon.setIcon(new ImageIcon(nopremium.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(Controlador.getControlador().isPremium()) premiumicon.setIcon(new ImageIcon(Utils.cambiarBrillo(0.5f, premium.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH))));
				premiumicon.setIcon(new ImageIcon(Utils.cambiarBrillo(0.7f, nopremium.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH))));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(Controlador.getControlador().isPremium()) premiumicon.setIcon(new ImageIcon(premium.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
				else premiumicon.setIcon(new ImageIcon(nopremium.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				notificacionDock(new ActionEvent(this, 5, "premium"));
			}
		});
		premiumicon.setIcon(new ImageIcon(nopremium.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
		premiumicon.setHorizontalAlignment(SwingConstants.CENTER);
		dockpremium.add(premiumicon, BorderLayout.CENTER);
		
	}
}