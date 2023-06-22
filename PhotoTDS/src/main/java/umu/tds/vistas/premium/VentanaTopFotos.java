package umu.tds.vistas.premium;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.pojos.Publicacion;
import umu.tds.vistas.Utils;
import umu.tds.vistas.lists.PubliListRenderer;

import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.Color;

public class VentanaTopFotos {
	private static VentanaTopFotos instancia = null;
	private JFrame frame;
	
	private BufferedImage encabezado, winIcon, fondo;
	private JList<Publicacion> publiJList;
	private DefaultListModel<Publicacion> listmodel;
	

	/* Constructor */
	private VentanaTopFotos() {
		listmodel = new DefaultListModel<Publicacion>();
		listmodel.addAll(Controlador.getControlador().getMasMeGusta());
		cargarRecursos();
		dibujar();
	}
	
	/* Precarga de recursos */
	private void cargarRecursos() {
		try {
			encabezado = ImageIO.read(getClass().getResource("/imagenes/premium/topfotosintro.png"));
			winIcon = ImageIO.read(getClass().getResource("/imagenes/premium/topfotos.png"));
			fondo = ImageIO.read(getClass().getResource("/imagenes/fondos/bg.png"));
		} catch (IOException ioe) { ioe.printStackTrace(); }
	}
	
	private void recargarLista() {
		listmodel.clear();
		listmodel.addAll(Controlador.getControlador().getMasMeGusta());
		publiJList.revalidate();
	}

	/* Dibujado de la ventana */
	private void dibujar() {
		frame = new JFrame();
		frame.setBackground(Color.MAGENTA);
		frame.setBounds(100, 100, 512, 640);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setIconImage(winIcon);
		frame.setTitle("Tus 10 mejores fotos");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{150, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panelIntro = new JPanel();
		GridBagConstraints gbc_panelIntro = new GridBagConstraints();
		gbc_panelIntro.insets = new Insets(0, 0, 5, 0);
		gbc_panelIntro.fill = GridBagConstraints.BOTH;
		gbc_panelIntro.gridx = 0;
		gbc_panelIntro.gridy = 0;
		frame.getContentPane().add(panelIntro, gbc_panelIntro);
		panelIntro.setLayout(new BorderLayout(0, 0));
		
		JLabel introIcon = new JLabel();
			introIcon.setIcon(new ImageIcon(Utils.reescalar(195, 127, encabezado)));
			introIcon.setHorizontalAlignment(SwingConstants.CENTER);
		panelIntro.add(introIcon, BorderLayout.CENTER);
		
		JScrollPane scrollFotos = new JScrollPane();
		scrollFotos.setOpaque(false);
		GridBagConstraints gbc_scrollFotos = new GridBagConstraints();
		gbc_scrollFotos.fill = GridBagConstraints.BOTH;
		gbc_scrollFotos.gridx = 0;
		gbc_scrollFotos.gridy = 1;
		frame.getContentPane().add(scrollFotos, gbc_scrollFotos);
		
		JPanel panelLista = new JPanel();
		panelLista.setOpaque(false);
		scrollFotos.setViewportView(panelLista);
		panelLista.setLayout(new BorderLayout(0, 0));
		
		publiJList = new JList<Publicacion>();
			publiJList.setOpaque(false);
			publiJList.setModel(listmodel);
			publiJList.setCellRenderer(new PubliListRenderer(128));
		panelLista.add(publiJList, BorderLayout.CENTER);
	}

	/* Devuelve la instancia única */
	public static VentanaTopFotos getInstancia() {
		if(instancia==null) instancia = new VentanaTopFotos();
		instancia.recargarLista();
		return instancia;
	}
	
	/* Muestra la ventana */
	public void mostrar() {
		frame.setVisible(true);
	}
	
	/* Destruye la ventana */
	public void destruir() {
		frame.dispose();
	}
}
