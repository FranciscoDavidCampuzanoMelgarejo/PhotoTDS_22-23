
package umu.tds.vistas;

import java.awt.CardLayout;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JPanel;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.pojos.Descuento;
import umu.tds.modelo.pojos.DescuentoEdad;
import umu.tds.modelo.pojos.DescuentoJo;
import umu.tds.modelo.pojos.DescuentoLikes;
import umu.tds.modelo.pojos.Publicacion;
import umu.tds.modelo.pojos.Usuario;
import umu.tds.vistas.lists.PubliListPanel;
import umu.tds.vistas.lists.PubliListRenderer;
//import umu.tds.vistas.perfil.VentanaPerfilUsuario;
import umu.tds.vistas.perfil.PanelPerfilUsuario;
import umu.tds.vistas.publicaciones.DialogoPublicacion;

import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ListSelectionListener;


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
	
	private PanelPerfilUsuario panelUsuario;
	private PanelBusqueda panelBusqueda;
	
	private VentanaAbout about;
	
	private VentanaPremium premium;
	
	private ArrayList<Publicacion> publiList;
	private DefaultListModel<Publicacion> publiListModel;
	private JList<Publicacion> publiJList;
	private JPanel panelInicio;
	private JScrollPane scrollPubliList;


	private void cargarRecursos() {
		try {
			fondo = ImageIO.read(VentanaPhotoTDS.class.getResource("/imagenes/fondos/bg.png")).getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
			winIcon = ImageIO.read(VentanaPhotoTDS.class.getResource("/imagenes/dock/logo1.png"));
		} catch (Exception e1) { e1.printStackTrace(); }
	}
	
	private void recargarRecientes() {
		publiListModel.clear();
		publiListModel.addAll(Controlador.getControlador().getUltimasFotos());
		publiJList.clearSelection();
		publiJList.revalidate();
	}
	
	public void mostrar() {
		frame.setVisible(true);
	}
	 
	public void destruir() {
		frame.dispose();
	}
	
	public VentanaPhotoTDS(int winX, int winY) {
		cargarRecursos();
		
		Controlador.getControlador().addDescuento(new DescuentoLikes());
		Controlador.getControlador().addDescuento(new DescuentoEdad());
		Controlador.getControlador().addDescuento(new DescuentoJo());
		publiList = (ArrayList<Publicacion>) Controlador.getControlador().getUltimasFotos();
		publiListModel = new DefaultListModel<Publicacion>();
			publiListModel.addAll(publiList);
		
		layout1 = new GridBagLayout();
			layout1.columnWidths = new int[]{64, 776, 0};
			layout1.rowHeights = new int[]{0, 0};
			layout1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			layout1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			
		layout2 = new GridBagLayout();
			layout2.columnWidths = new int[]{200, 0, 640, 0, 0};
			layout2.rowHeights = new int[]{0, 0};
			layout2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			layout2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			
		dibujar(winX, winY);
		dock.recargarImagenUsuario();
		dock.recargarPremium();
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
				float ww = frame.getWidth(), wh = frame.getHeight(), iw = fondo.getWidth(null), ih = fondo.getHeight(null);
				int numX = (int)((ww + iw-1) / iw), numY = (int)((wh + ih-1) / ih);
				for(int y=0; y<numY; y++)
					for(int x=0; x<numX; x++)
						g.drawImage(fondo, x*(int)iw, y*(int)ih, null);
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
		apprender.setBorder(null);
		GridBagConstraints gbc_apprender = new GridBagConstraints();
		gbc_apprender.fill = GridBagConstraints.BOTH;
		gbc_apprender.gridx = 1;
		gbc_apprender.gridy = 0;
		phototdsrender.add(apprender, gbc_apprender);
		apprender.setLayout(new CardLayout(0, 0));
			c = (CardLayout)apprender.getLayout();
			panelUsuario = new PanelPerfilUsuario(frame, Controlador.getControlador().getUsuarioLogueado());
				panelUsuario.addActionListener(this);
			
			panelInicio = new JPanel() { 
				private static final long serialVersionUID = 1L;
				{setOpaque(false);} 
			};
			apprender.add(panelInicio, "panelInicio");
			panelInicio.setLayout(new BorderLayout(0, 0));
			
			scrollPubliList = new JScrollPane();
			scrollPubliList.setOpaque(false);
			scrollPubliList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panelInicio.add(scrollPubliList, BorderLayout.CENTER);
			publiJList = new JList<Publicacion>();
			publiJList.setOpaque(false);
			publiJList.setModel(publiListModel);
			publiJList.clearSelection();
			publiJList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if(!e.getValueIsAdjusting()) {
						Publicacion p = publiJList.getSelectedValue();
						if(p!=null) {
							VentanaPubli v = new VentanaPubli(p);
							v.mostrar();
						}
					}
				}
			});
			publiJList.setCellRenderer(new PubliListRenderer(128));
			publiJList.setSelectedIndex(ListSelectionModel.SINGLE_SELECTION);
			scrollPubliList.setViewportView(publiJList);
			scrollPubliList.getViewport().setOpaque(false);
			scrollPubliList.setViewportBorder(BorderFactory.createEmptyBorder());
			scrollPubliList.setBorder(BorderFactory.createEmptyBorder());
			apprender.add(panelUsuario, "panelUsuario");
			
			
			panelBusqueda = new PanelBusqueda();
			apprender.add(panelBusqueda, "panelBusqueda");			
	}

	/* La ventana principal escucha cambios en el Dock --> selección de distintas pantallas */
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		switch(comando) {
			case "home" :					// Publicaciones recientes
				recargarRecientes();
				c.show(apprender, "panelInicio");
				break; 
			case "finder" : 				// Búsqueda de publicaciones por usuaro y etiqueta
				c.show(apprender, "panelBusqueda"); 
				break; 
			case "publi" : {				// Subir nueva publicación
				VentanaSubirFoto.getInstancia().mostrar();	
				VentanaSubirFoto.getInstancia().addActionListener(this);
				break;
			}
			case "user" : {					// Perfil de usuario
				panelUsuario.actualizarPanel();
				c.show(apprender, "panelUsuario");
				break;
			}
			case "cambioFotoPerfil" : {		// Notificación para actualizar perfil en el dock
				dock.recargarImagenUsuario();
				panelUsuario.cambiarFotoPerfil();
				break;
			}
			case "about" :					// Ventana "Acerca de"
				about = VentanaAbout.getInstancia();
				about.mostrar();
				break;
			case "premium":					// Mostrar ventana de premium
				premium = VentanaPremium.getInstancia();
				premium.mostrar();
				premium.addActionListener(this);
				break;
			case "cambioPremium" :			// Notificación para actualizar premium en el dock
				Controlador.getControlador().setPremium(true);
				dock.recargarPremium();
				break;
			case "cambioNoPremium" :
				Controlador.getControlador().setPremium(false);
				dock.recargarPremium();
				break;
			case "fotoSubida" :
				recargarRecientes();
				break;
		}
		
	}



}