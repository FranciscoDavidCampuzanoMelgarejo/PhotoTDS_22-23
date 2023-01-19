package umu.tds.vistas;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import umu.tds.controlador.Controlador;
import umu.tds.vistas.perfil.VentanaPerfilUsuario;

import java.awt.event.*;

public class PhotoTDSDock extends JPanel{
	private static final long serialVersionUID = 1L;
	private int DOCKICONSIZE1 = 38, DOCKICONSIZE2 = 42;
	
	private CardLayout c;
	private JPanel dock1, dock2;
	private Image dockbg, logo1, logo2, home, finder, publi, user;
	
	private List<ActionListener> listeners;
	
	public PhotoTDSDock() {
		super();
		this.setOpaque(false);
		try {
			cargarRecursos();
			dibujar();
		} catch (Exception edock) { edock.printStackTrace(); }
	}
	
	public void addActionListener(ActionListener a) {
		if(listeners==null) listeners = new LinkedList<ActionListener>();
		listeners.add(a);
	}
	
	public void notificacionDock(ActionEvent a) {
		if(listeners!=null) listeners.stream().forEach(l -> l.actionPerformed(a));
	}
	
	/* Cambia el estilo del dock */
	public void cambiarDock(String estilo) {
		c.show(this, estilo);
	}

	/* Recarga la imagen de usuario del dock */
	public void recargarImagenUsuario(){
		File userpic;
		String userpicture = Controlador.getControlador().getUserPicture();
		if(userpicture!=null) {
			userpic = new File(userpicture);
		} else userpic = new File(PhotoTDSDock.class.getResource("/imagenes/dock/nouser.png").getFile());
		try{
			user = ImageIO.read(userpic);
		} catch (IOException ioe) { ioe.printStackTrace(); }
	}
	
	/* Tamaño de iconos al pasar el cursor sobre ellos */
	public void setDockIconSize1(int size) { this.DOCKICONSIZE1 = size; }
	public void setDockIconSize2(int size) { this.DOCKICONSIZE2 = size; }
	
	/* Precarga de recursos gráficos del dock */
	private void cargarRecursos() throws Exception{
		dockbg = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/dockbg1.png"));
		logo1 = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/logo1.png"));
		logo2 = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/logo2.png"));
		home = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/home.png"));
		finder = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/finder.png"));
		publi = ImageIO.read(PhotoTDSDock.class.getResource("/imagenes/dock/publi.png"));
		
		File userpic;
		String userpicture = Controlador.getControlador().getUserPicture();
		if(userpicture!=null) {
			userpic = new File(userpicture);
		} else userpic = new File(PhotoTDSDock.class.getResource("/imagenes/dock/nouser.png").getFile());
		user = ImageIO.read(userpic);
		user = Utils.redondearImagen(user.getWidth(null), new ImageIcon(user));
	}
	
	/* Dibujado de componentes del dock */
	private void dibujar() throws Exception{
		c = new CardLayout(0,0);
		setLayout(c);
				
		/* DOCK1 */
		dock1 = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); }
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(dockbg.getScaledInstance(64, dock1.getHeight(), Image.SCALE_SMOOTH), 0,0, null);
			}
		};

		add(dock1, "dock1");
		GridBagLayout gbl_dock1 = new GridBagLayout();
		gbl_dock1.columnWidths = new int[]{0, 0};
		gbl_dock1.rowHeights = new int[]{64, 64, 64, 64, 64, 0};
		gbl_dock1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_dock1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		dock1.setLayout(gbl_dock1);
		
		JPanel dock1logo = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); }
		};
		GridBagConstraints gbc_dock1logo = new GridBagConstraints();
		gbc_dock1logo.fill = GridBagConstraints.VERTICAL;
		gbc_dock1logo.gridx = 0;
		gbc_dock1logo.gridy = 0;
		dock1.add(dock1logo, gbc_dock1logo);
		dock1logo.setLayout(new BorderLayout(0, 0));
		
		JLabel dock1logoicon = new JLabel("");
		dock1logoicon.setToolTipText("PhotoTDS");
			dock1logoicon.setIcon(new ImageIcon(logo1.getScaledInstance(DOCKICONSIZE1, 50, Image.SCALE_SMOOTH)));
		dock1logo.add(dock1logoicon, BorderLayout.CENTER);
		
		JPanel dock1home = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); }
		};
		GridBagConstraints gbc_dock1home = new GridBagConstraints();
		gbc_dock1home.fill = GridBagConstraints.VERTICAL;
		gbc_dock1home.gridx = 0;
		gbc_dock1home.gridy = 1;
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
				dock1homeicon.setIcon(new ImageIcon(Utils.cambiarBrillo(0.5f, home.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH))));
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
		gbc_dock1finder.fill = GridBagConstraints.VERTICAL;
		gbc_dock1finder.gridx = 0;
		gbc_dock1finder.gridy = 2;
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
				dock1findericon.setIcon(new ImageIcon(Utils.cambiarBrillo(0.5f, finder.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH))));
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
		gbc_dock1publi.fill = GridBagConstraints.VERTICAL;
		gbc_dock1publi.gridx = 0;
		gbc_dock1publi.gridy = 3;
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
				dock1publiicon.setIcon(new ImageIcon(Utils.cambiarBrillo(0.5f, publi.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH))));
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
		gbc_dock1user.fill = GridBagConstraints.VERTICAL;
		gbc_dock1user.gridx = 0;
		gbc_dock1user.gridy = 4;
		dock1.add(dock1user, gbc_dock1user);
		dock1user.setLayout(new BorderLayout(0, 0));
		
		JLabel dock1usericon = new JLabel("");
		dock1usericon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				dock1usericon.setIcon(new ImageIcon(((ImageIcon)dock1usericon.getIcon()).getImage().getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_FAST)));
				//dock1usericon.setIcon(new ImageIcon(((ImageIcon)dock1usericon.getIcon()).getImage().getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH)));
			}
			@Override 
			public void mouseExited(MouseEvent e) {
				dock1usericon.setIcon(new ImageIcon(((ImageIcon)dock1usericon.getIcon()).getImage().getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_FAST)));
				//dock1usericon.setIcon(new ImageIcon(((ImageIcon)dock1usericon.getIcon()).getImage().getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				dock1usericon.setIcon(new ImageIcon(Utils.cambiarBrillo(0.5f, user.getScaledInstance(DOCKICONSIZE2, DOCKICONSIZE2, Image.SCALE_SMOOTH))));
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
			dock1usericon.setIcon(new ImageIcon(Utils.redondearImagen(38, new ImageIcon( user.getScaledInstance(DOCKICONSIZE1, DOCKICONSIZE1, Image.SCALE_SMOOTH)))));
			
		dock1user.add(dock1usericon, BorderLayout.CENTER);
		
		/* DOCK2 */
		/*
		dock2 = new JPanel();
		add(dock2, "dock2");
		GridBagLayout gbl_dock2 = new GridBagLayout();
		gbl_dock2.columnWidths = new int[]{0};
		gbl_dock2.rowHeights = new int[]{0};
		gbl_dock2.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_dock2.rowWeights = new double[]{Double.MIN_VALUE};
		dock2.setLayout(gbl_dock2);
		*/
	}
}
