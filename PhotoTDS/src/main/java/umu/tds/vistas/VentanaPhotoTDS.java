package umu.tds.vistas;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JPanel;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.pojos.Foto;
import umu.tds.modelo.pojos.Publicacion;
import umu.tds.vistas.lists.PublicacionRenderer;
import umu.tds.vistas.perfil.VentanaPerfilUsuario;

import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;


/**@title Ventana principal de PhotoTDS
 * @author Francisco & Diego
 * */

public class VentanaPhotoTDS implements ActionListener{

	private JFrame frame;
	private JPanel phototdsrender;
	private JPanel apprender;
	private PhotoTDSDock dock;
	private CardLayout c;
	private GridBagLayout layout1, layout2;
	
	private Image fondo, winIcon;
	private VentanaSubirFoto subirFoto;
	private boolean subiendoFoto;
	
	private VentanaPerfilUsuario perfilusuario;
	private boolean viendoperfil;
	private JList list;
	private JList list_1;

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
		
		subiendoFoto = false;
		viendoperfil = false;
		
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
			dock.addActionListener(this);
		
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
		
		apprender = new JPanel() { 
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
		panelInicio.setBackground(Color.RED);
		apprender.add(panelInicio, "panelInicio");
		panelInicio.setLayout(new BorderLayout(0, 0));
		
		
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBackground(Color.PINK);
		apprender.add(panelBusqueda, "panelBusqueda");	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		switch(comando) {
			case "home" : { c.show(apprender, "panelInicio"); break; }
			case "finder" : { c.show(apprender, "panelBusqueda"); break; }
			case "publi" : {
				if(!subiendoFoto) {
					subiendoFoto = true;
					subirFoto = new VentanaSubirFoto();
					subirFoto.addActionListener(this);
					subirFoto.mostrar();
				}
				break;
			}
			case "user" : {
				if(!viendoperfil) {
					viendoperfil = true;
					perfilusuario = new VentanaPerfilUsuario();
					perfilusuario.mostrar();
					perfilusuario.addActionListener(this);
				}
				break;
			}
			case "fotoSubida" : {
				System.out.println("Nueva foto subida");
				subirFoto.destruir();
				subiendoFoto = false;
				break;
			}
			case "subirFotoCerrando" : { subiendoFoto = false; break; }
			case "perfilusuarioCerrando" : {
				viendoperfil = false;
				break;
			}
			case "cambioFotoPerfil" : {
				dock.recargarImagenUsuario();
				break;
			}
		}
		
	}



}
