package umu.tds.vistas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.pojos.Usuario;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @title Ventana de Premium
 * @author diego
 * */
public class VentanaPremium {
	private static VentanaPremium instancia = null;
	private JFrame frame;
	
	private Usuario usuario;
	private List<ActionListener> listeners;
	
	private CardLayout c;
	
	private BufferedImage premiumicon, nopremiumicon, yapremium, haztepremium;

	/* Listener para la ventana principal */
	public void addActionListener(ActionListener a) {
		if(listeners==null) listeners = new LinkedList<ActionListener>();
		listeners.add(a);
	}
	
	/* Al aceptar o anular el premium, se tiene que notificar a la ventana principal */
	public void notificacionPremium(ActionEvent a) {
		if(listeners!=null) listeners.stream().forEach(l -> l.actionPerformed(a));
	}

	/* Cargar recursos gráficos */
	private void cargarRecursos() {
		try {
			premiumicon = ImageIO.read(getClass().getResource("/imagenes/dock/premium.png"));
			nopremiumicon = ImageIO.read(getClass().getResource("/imagenes/dock/nopremium.png"));
			yapremium = ImageIO.read(getClass().getResource("/imagenes/yapremium.png"));
			haztepremium = ImageIO.read(getClass().getResource("/imagenes/haztepremium.png"));
		} catch (IOException ioe) { System.err.println("ERR cargando recursos"); }
	}
	
	/* Constructor de la instancia */
	private VentanaPremium() {
		usuario = Controlador.getControlador().getUsuarioLogueado();
		cargarRecursos();
		dibujar();
		if(usuario.getPremium()) c.show(frame.getContentPane(), "siPremium");
		else c.show(frame.getContentPane(), "noPremium");
	}

	/* Dibujado de la ventana */
	private void dibujar() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(100, 100, 480, 512);
		if(usuario.getPremium()) {
			frame.setTitle("Premium");
			frame.setIconImage(premiumicon);
		}
		else {
			frame.setTitle("Hazte premium");
			frame.setIconImage(nopremiumicon);
		}
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		c = (CardLayout) frame.getContentPane().getLayout();
		 
		JPanel noPremium = new JPanel() {
			private static final long serialVersionUID = 1L;
			{setOpaque(false);} 
		};
		frame.getContentPane().add(noPremium, "noPremium");
		GridBagLayout gbl_noPremium = new GridBagLayout();
		gbl_noPremium.columnWidths = new int[]{0, 0};
		gbl_noPremium.rowHeights = new int[]{220, 0, 0};
		gbl_noPremium.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_noPremium.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		noPremium.setLayout(gbl_noPremium);
		
		JPanel panelImagen1 = new JPanel();
		GridBagConstraints gbc_panelImagen1 = new GridBagConstraints();
		gbc_panelImagen1.insets = new Insets(0, 0, 5, 0);
		gbc_panelImagen1.fill = GridBagConstraints.BOTH;
		gbc_panelImagen1.gridx = 0;
		gbc_panelImagen1.gridy = 0;
		noPremium.add(panelImagen1, gbc_panelImagen1);
		panelImagen1.setLayout(new BorderLayout(0, 0));
		
		JLabel noPremiumLabel = new JLabel("");
		noPremiumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noPremiumLabel.setIcon(new ImageIcon(haztepremium.getScaledInstance(186, 184, Image.SCALE_SMOOTH)));
		panelImagen1.add(noPremiumLabel, BorderLayout.CENTER);
		
		JPanel panelInfo1 = new JPanel();
		GridBagConstraints gbc_panelInfo1 = new GridBagConstraints();
		gbc_panelInfo1.fill = GridBagConstraints.BOTH;
		gbc_panelInfo1.gridx = 0;
		gbc_panelInfo1.gridy = 1;
		noPremium.add(panelInfo1, gbc_panelInfo1);
		GridBagLayout gbl_panelInfo1 = new GridBagLayout();
		gbl_panelInfo1.columnWidths = new int[]{0, 0};
		gbl_panelInfo1.rowHeights = new int[]{144, 64, 32, 0};
		gbl_panelInfo1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelInfo1.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelInfo1.setLayout(gbl_panelInfo1);
		
		JPanel panelListaDescuentos = new JPanel();
		GridBagConstraints gbc_panelListaDescuentos = new GridBagConstraints();
		gbc_panelListaDescuentos.insets = new Insets(0, 0, 5, 0);
		gbc_panelListaDescuentos.fill = GridBagConstraints.BOTH;
		gbc_panelListaDescuentos.gridx = 0;
		gbc_panelListaDescuentos.gridy = 0;
		panelInfo1.add(panelListaDescuentos, gbc_panelListaDescuentos);
		
		JPanel panelPrecio = new JPanel();
		GridBagConstraints gbc_panelPrecio = new GridBagConstraints();
		gbc_panelPrecio.insets = new Insets(0, 0, 5, 0);
		gbc_panelPrecio.fill = GridBagConstraints.BOTH;
		gbc_panelPrecio.gridx = 0;
		gbc_panelPrecio.gridy = 1;
		panelInfo1.add(panelPrecio, gbc_panelPrecio);
		panelPrecio.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Precio");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		panelPrecio.add(lblNewLabel);
		
		JPanel panelHacersePremium = new JPanel();
		GridBagConstraints gbc_panelHacersePremium = new GridBagConstraints();
		gbc_panelHacersePremium.fill = GridBagConstraints.BOTH;
		gbc_panelHacersePremium.gridx = 0;
		gbc_panelHacersePremium.gridy = 2;
		panelInfo1.add(panelHacersePremium, gbc_panelHacersePremium);
		panelHacersePremium.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnHacersePremium = new JButton("Hacerse premium");
		btnHacersePremium.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				notificacionPremium(new ActionEvent(this, 6, "cambioPremium"));
				destruir();
			}
		});
		btnHacersePremium.setBackground(new Color(0xb2, 0x00, 0x9c));
		btnHacersePremium.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		panelHacersePremium.add(btnHacersePremium);
		
		JPanel siPremium = new JPanel() {
			private static final long serialVersionUID = 1L;
			{setOpaque(false);} 
		};
		frame.getContentPane().add(siPremium, "siPremium");
		GridBagLayout gbl_siPremium = new GridBagLayout();
		gbl_siPremium.columnWidths = new int[]{0, 0};
		gbl_siPremium.rowHeights = new int[]{220, 0, 0};
		gbl_siPremium.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_siPremium.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		siPremium.setLayout(gbl_siPremium);
		
		JPanel panelImagen2 = new JPanel();
		GridBagConstraints gbc_panelImagen2 = new GridBagConstraints();
		gbc_panelImagen2.insets = new Insets(0, 0, 5, 0);
		gbc_panelImagen2.fill = GridBagConstraints.BOTH;
		gbc_panelImagen2.gridx = 0;
		gbc_panelImagen2.gridy = 0;
		siPremium.add(panelImagen2, gbc_panelImagen2);
		panelImagen2.setLayout(new BorderLayout(0, 0));
		
		JLabel premiumLabel = new JLabel("");
		premiumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		premiumLabel.setIcon(new ImageIcon(yapremium.getScaledInstance(190, 184, Image.SCALE_SMOOTH)));
		panelImagen2.add(premiumLabel, BorderLayout.CENTER);
		
		JPanel panelInfo2 = new JPanel();
		GridBagConstraints gbc_panelInfo2 = new GridBagConstraints();
		gbc_panelInfo2.fill = GridBagConstraints.BOTH;
		gbc_panelInfo2.gridx = 0;
		gbc_panelInfo2.gridy = 1;
		siPremium.add(panelInfo2, gbc_panelInfo2);
	}
	
	/* Destruye la instancia */
	private void destruir() {
		frame.dispose();
		instancia = null;
	}
	
	/* Devuelve la instancia de la ventana */
	public static VentanaPremium getInstancia() {
		if(instancia==null) instancia = new VentanaPremium();
		return instancia;
	}
	
	/* Muestra la ventana */
	public void mostrar() {
		frame.setVisible(true);
	}
	
}
