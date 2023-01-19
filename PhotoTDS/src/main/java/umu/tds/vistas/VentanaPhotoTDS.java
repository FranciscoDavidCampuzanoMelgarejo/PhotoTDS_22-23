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
import java.awt.Dimension;
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

import umu.tds.controlador.Controlador;
import umu.tds.vistas.perfil.PanelPerfilUsuario;

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
	
	private Image fondo, winIcon;
	
	
	
	private void cargarRecursos() {
		try {
			fondo = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/fondos/fondo-3.jpg")).getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
			winIcon = ImageIO.read(VentanaPrincipal.class.getResource("/imagenes/dock/logo1.png"));
		} catch (Exception e1) { e1.printStackTrace(); }
	}
	
	public void mostrar() {
		frame.setVisible(true);
	}
	 
	public void destruir() {
		frame.dispose();
	}
	
	public VentanaPhotoTDS(int winX, int winY) {
		cargarRecursos();
		
		layout1 = new GridBagLayout();
			layout1.columnWidths = new int[]{64, 0, 776, 0, 0};
			layout1.rowHeights = new int[]{0, 0};
			layout1.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			layout1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			
		layout2 = new GridBagLayout();
			layout2.columnWidths = new int[]{200, 0, 640, 0, 0};
			layout2.rowHeights = new int[]{0, 0};
			layout2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			layout2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			
		dibujar(winX, winY);
	}

	private void dibujar(int winX, int winY) {
		frame = new JFrame();
		frame.setBounds(winX, winY, 840, 720);
		frame.setMinimumSize(new Dimension(840, 720));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("PhotoTDS");
		frame.setIconImage(winIcon);
		
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
		
		JPanel panelDock = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelDock = new GridBagConstraints();
		gbc_panelDock.fill = GridBagConstraints.BOTH;
		gbc_panelDock.gridx = 0;
		gbc_panelDock.gridy = 0;
		phototdsrender.add(panelDock, gbc_panelDock);
		panelDock.setLayout(new BorderLayout(0, 0));
		panelDock.add(dock);
		
		JPanel apprender = new JPanel() { 
			private static final long serialVersionUID = 1L;
			{setOpaque(false);} 
		};
		apprender.setBackground(Color.RED);
		GridBagConstraints gbc_apprender = new GridBagConstraints();
		gbc_apprender.fill = GridBagConstraints.BOTH;
		gbc_apprender.gridx = 2;
		gbc_apprender.gridy = 0;
		phototdsrender.add(apprender, gbc_apprender);
		apprender.setLayout(new CardLayout(0, 0));
			c = (CardLayout)apprender.getLayout();
		
		JPanel panelInicio = new JPanel();
		apprender.add(panelInicio, "panelInicio");
		panelInicio.setLayout(new BorderLayout(0, 0));
		
		/*
		JLabel lblNewLabel = new JLabel("");
			LinkedList<Image> imgs = new LinkedList<Image>();
				imgs.add(fondo);
				imgs.add(winIcon);
				imgs.add(fondo);
				
				imgs.add(winIcon);
				imgs.add(fondo);

				
				
			lblNewLabel.setIcon(new ImageIcon(Utils.crearMosaico(512, 512, 4, imgs)));
		panelInicio.add(lblNewLabel, BorderLayout.CENTER);
		*/
		
		JPanel panelUsuario = new PanelPerfilUsuario(frame, Controlador.getControlador().getUsuarioLogueado());
		apprender.add(panelUsuario, "panelUsuario");
		
		JPanel panelBusqueda = new JPanel();
		apprender.add(panelBusqueda, "panelBusqueda");	
	}

}
