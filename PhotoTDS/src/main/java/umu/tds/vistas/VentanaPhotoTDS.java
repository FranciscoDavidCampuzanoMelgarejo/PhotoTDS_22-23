package umu.tds.vistas;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

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
import java.util.LinkedList;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.Font;

/**@title Ventana principal de PhotoTDS
 * @author Francisco & Diego
 * */

public class VentanaPhotoTDS {

	private JFrame frame;
	private JPanel phototdsrender;
	private PhotoTDSDock dock;
	private CardLayout c;
	private GridBagLayout layout1, layout2;
	
	private Image home1, home2, publi1, publi2, busqu1, busqu2, logo, fondoDock;
	
	private Image fondo;
	
	private void cargarRecursos() {
		try {
			fondo = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/fondos/fondo-3.jpg")).getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
			logo = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-files/logo-phototds-dock.png")).getScaledInstance(140, 64, Image.SCALE_SMOOTH);
			fondoDock = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock-files/dock-bg.png"));
			
		} catch (Exception e1) { e1.printStackTrace(); }
	}
	
	public void mostrar() {
		frame.setVisible(true);
	}
	 
	public void destruir() {
		frame.dispose();
	}
	
	public VentanaPhotoTDS() {
		cargarRecursos();
		
		layout1 = new GridBagLayout();
			layout1.columnWidths = new int[]{140, 0, 700, 0, 0};
			layout1.rowHeights = new int[]{0, 0};
			layout1.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			layout1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			
		layout2 = new GridBagLayout();
			layout2.columnWidths = new int[]{200, 0, 640, 0, 0};
			layout2.rowHeights = new int[]{0, 0};
			layout2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			layout2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			
		dibujar();
	}

	private void dibujar() {
		frame = new JFrame();
		frame.setBounds(100, 100, 840, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				/*
				if(dock!=null) {
					int w = frame.getWidth();	// Recuperamos nueva anchura. El cambio se hace a partir de los 1260 píxeles de ancho
					if(w>=1260) {
						phototdsrender.setLayout(layout2);
						dock.setDock2();
					} else dock.setDock1();
				}
				*/
			}
		});
		
		phototdsrender = new JPanel() {
			private static final long serialVersionUID = 1L;
			{setOpaque(false);} 
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(fondo, 0, 0, null);
			}
		};
		
		frame.setContentPane(phototdsrender);
		phototdsrender.setLayout(layout1);
		
		dock = new PhotoTDSDock();
		phototdsrender.add(dock, dock.getGBC());
		
		JPanel apprender = new JPanel() { {setOpaque(false);} };
		apprender.setBackground(Color.RED);
		GridBagConstraints gbc_apprender = new GridBagConstraints();
		gbc_apprender.insets = new Insets(0, 0, 0, 5);
		gbc_apprender.fill = GridBagConstraints.BOTH;
		gbc_apprender.gridx = 2;
		gbc_apprender.gridy = 0;
		phototdsrender.add(apprender, gbc_apprender);
		apprender.setLayout(new CardLayout(0, 0));
			c = (CardLayout)apprender.getLayout();
		
		JPanel panelInicio = new JPanel();
		apprender.add(panelInicio, "panelInicio");
		
		JPanel panelUsuario = new JPanel();
		apprender.add(panelUsuario, "panelUsuario");
		
		JPanel panelBusqueda = new JPanel();
		apprender.add(panelBusqueda, "panelBusqueda");
			
	}

}
