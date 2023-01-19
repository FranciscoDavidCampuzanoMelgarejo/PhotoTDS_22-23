package umu.tds.vistas;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.Image;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.LinkedList;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.border.EtchedBorder;

import umu.tds.vistas.perfil.PanelPerfilUsuario;

import java.awt.Font;

public class OLD_PhotoTDSDock extends JPanel{
	private static final long serialVersionUID = 1L;

	private JLabel dock_inicio_icon, dock_publi_icon, dock_usuario_icon, dock_logo;
	private GridBagConstraints gbc_dock;
	
	private Image logo1, logo2, finder1, finder2, publi1, publi2, home1, home2;

	
	private void cargarRecursos() {
		try {
			logo1 = ImageIO.read(OLD_PhotoTDSDock.class.getResource("/imagenes/dock-files/logo-phototds-dock.png")).getScaledInstance(140, 64, Image.SCALE_SMOOTH);
			logo2 = ImageIO.read(OLD_PhotoTDSDock.class.getResource("/imagenes/dock-files/logo-phototds-dock.png")).getScaledInstance(140, 64, Image.SCALE_SMOOTH);
			finder1 = ImageIO.read(OLD_PhotoTDSDock.class.getResource("/imagenes/dock-files/busqueda.png")).getScaledInstance(38, 38, Image.SCALE_SMOOTH);
			finder2 = ImageIO.read(OLD_PhotoTDSDock.class.getResource("/imagenes/dock-files/busqueda.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			publi1 = ImageIO.read(OLD_PhotoTDSDock.class.getResource("/imagenes/dock-files/publi.png")).getScaledInstance(38, 38, Image.SCALE_SMOOTH);
			publi2 = ImageIO.read(OLD_PhotoTDSDock.class.getResource("/imagenes/dock-files/publi.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			home1 = ImageIO.read(OLD_PhotoTDSDock.class.getResource("/imagenes/dock-files/home.png")).getScaledInstance(38, 38, Image.SCALE_SMOOTH);
			home2 = ImageIO.read(OLD_PhotoTDSDock.class.getResource("/imagenes/dock-files/home.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			
			dock_inicio_icon = new JLabel("");
			dock_publi_icon = new JLabel("");
			dock_usuario_icon = new JLabel("");
			dock_logo = new JLabel("");
			
			
		} catch (NullPointerException edock1) {  System.err.println("Error inesperado en PhotoTDSDock.cargarRecursos()"); edock1.printStackTrace(); }
		  catch(Exception edock2) { edock2.printStackTrace(); }
	}

	public GridBagConstraints getGBC() {
		return gbc_dock;
	}
	
	/* Dock estilo 1: estrecho, solo imagenes */
	public void setDock1() {
		dock_inicio_icon.setText("");
		dock_publi_icon.setText("");
		dock_usuario_icon.setText("");
		dock_logo.setIcon(new ImageIcon(logo1));
	}
	
	/* Dock estilo 2: ancho, imagenes + texto */
	public void setDock2() {
		dock_inicio_icon.setText("Inicio");
		dock_publi_icon.setText("Crear");
		dock_usuario_icon.setText("Usuario");
		dock_logo.setIcon(new ImageIcon(logo2));	// CAMBIAR POR FOTO DE USUARIO REDONDEADA
	}
	
	public OLD_PhotoTDSDock() {
		super();
		cargarRecursos();
		setDock1();
		
		gbc_dock = new GridBagConstraints();
		gbc_dock.insets = new Insets(0, 0, 0, 5);
		gbc_dock.fill = GridBagConstraints.BOTH;
		gbc_dock.gridx = 0;
		gbc_dock.gridy = 0;
		//phototdsrender.add(dock, gbc_dock);
		GridBagLayout gbl_dock = new GridBagLayout();
		gbl_dock.columnWidths = new int[]{64, 0, 0};
		gbl_dock.rowHeights = new int[]{64, 64, 64, 64, 0, 0};
		gbl_dock.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_dock.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		this.setLayout(gbl_dock);
		
		JPanel dock_panelLogo = new JPanel(){ {setOpaque(false);} };
		GridBagConstraints gbc_dock_panelLogo = new GridBagConstraints();
		gbc_dock_panelLogo.gridwidth = 2;
		gbc_dock_panelLogo.insets = new Insets(0, 0, 5, 0);
		gbc_dock_panelLogo.fill = GridBagConstraints.BOTH;
		gbc_dock_panelLogo.gridx = 0;
		gbc_dock_panelLogo.gridy = 0;
		this.add(dock_panelLogo, gbc_dock_panelLogo);
		dock_panelLogo.setLayout(new BorderLayout(0, 0));
		
		dock_logo.setIcon(new ImageIcon(logo1));
		dock_logo.setHorizontalAlignment(SwingConstants.CENTER);
		dock_panelLogo.add(dock_logo, BorderLayout.CENTER);
		
		JPanel dock_inicio1 = new JPanel(){ {setOpaque(false);} };
		dock_inicio1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				dock_inicio_icon.setIcon(new ImageIcon(home2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				dock_inicio_icon.setIcon(new ImageIcon(home1));
			}
		});
		GridBagConstraints gbc_dock_inicio = new GridBagConstraints();
		gbc_dock_inicio.insets = new Insets(0, 0, 5, 5);
		gbc_dock_inicio.fill = GridBagConstraints.BOTH;
		gbc_dock_inicio.gridx = 0;
		gbc_dock_inicio.gridy = 1;
		this.add(dock_inicio1, gbc_dock_inicio);
		dock_inicio1.setLayout(new BorderLayout(0, 0));
		
		dock_inicio_icon.setFont(new Font("Berlin Sans FB", Font.ITALIC, 16));
		dock_inicio_icon.setIcon(new ImageIcon(home1));
		dock_inicio_icon.setHorizontalAlignment(SwingConstants.CENTER);
		dock_inicio1.add(dock_inicio_icon);
		
		JPanel dock_inicio2 = new JPanel();
		GridBagConstraints gbc_dock_inicio2 = new GridBagConstraints();
		gbc_dock_inicio2.insets = new Insets(0, 0, 5, 0);
		gbc_dock_inicio2.fill = GridBagConstraints.BOTH;
		gbc_dock_inicio2.gridx = 1;
		gbc_dock_inicio2.gridy = 1;
		add(dock_inicio2, gbc_dock_inicio2);
		dock_inicio2.setLayout(new BorderLayout(0, 0));
		
		JPanel dock_publi1 = new JPanel(){ {setOpaque(false);} };
		GridBagConstraints gbc_dock_publi = new GridBagConstraints();
		gbc_dock_publi.insets = new Insets(0, 0, 5, 5);
		gbc_dock_publi.fill = GridBagConstraints.BOTH;
		gbc_dock_publi.gridx = 0;
		gbc_dock_publi.gridy = 2;
		this.add(dock_publi1, gbc_dock_publi);
		dock_publi1.setLayout(new BorderLayout(0, 0));
		
		dock_publi_icon.setFont(new Font("Berlin Sans FB", Font.ITALIC, 16));
		dock_publi_icon.setIcon(new ImageIcon(publi1));
		dock_publi_icon.setHorizontalAlignment(SwingConstants.CENTER);
		dock_publi1.add(dock_publi_icon);
		
		JPanel dock_publi2 = new JPanel();
		GridBagConstraints gbc_dock_publi2 = new GridBagConstraints();
		gbc_dock_publi2.insets = new Insets(0, 0, 5, 0);
		gbc_dock_publi2.fill = GridBagConstraints.BOTH;
		gbc_dock_publi2.gridx = 1;
		gbc_dock_publi2.gridy = 2;
		add(dock_publi2, gbc_dock_publi2);
		dock_publi2.setLayout(new BorderLayout(0, 0));
		
		JPanel dock_usuario1 = new JPanel(){ {setOpaque(false);} };
		GridBagConstraints gbc_dock_usuario = new GridBagConstraints();
		gbc_dock_usuario.insets = new Insets(0, 0, 5, 5);
		gbc_dock_usuario.fill = GridBagConstraints.BOTH;
		gbc_dock_usuario.gridx = 0;
		gbc_dock_usuario.gridy = 3;
		this.add(dock_usuario1, gbc_dock_usuario);
		dock_usuario1.setLayout(new BorderLayout(0, 0));
		
		dock_usuario_icon.setFont(new Font("Berlin Sans FB", Font.ITALIC, 16));
		dock_usuario_icon.setIcon(new ImageIcon(home1));
		dock_usuario_icon.setHorizontalAlignment(SwingConstants.CENTER);
		dock_usuario1.add(dock_usuario_icon, BorderLayout.CENTER);
		
		JPanel dock_usuario2 = new JPanel();
		GridBagConstraints gbc_dock_usuario2 = new GridBagConstraints();
		gbc_dock_usuario2.insets = new Insets(0, 0, 5, 0);
		gbc_dock_usuario2.fill = GridBagConstraints.BOTH;
		gbc_dock_usuario2.gridx = 1;
		gbc_dock_usuario2.gridy = 3;
		add(dock_usuario2, gbc_dock_usuario2);
		dock_usuario2.setLayout(new BorderLayout(0, 0));
		
		// Al clickar en la etiqueta del del usuario (perfil), 
		// abrir la ventana de perfil del usuario
		
		dock_usuario_icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PanelPerfilUsuario ventanaPerfil = new PanelPerfilUsuario();
				ventanaPerfil.mostrar();
			}
		});
	}
	
	
	
	
}
