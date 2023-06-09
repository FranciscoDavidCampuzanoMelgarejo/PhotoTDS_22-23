package umu.tds.vistas;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JSeparator;

/**
 * @title About application
 * @author diego
 * */
public class VentanaAbout {
	private static VentanaAbout instancia = null;		// Singleton: solo puede haber una ventana about
	private JFrame frame;
	
	Image logo;

	private VentanaAbout() {
		cargarRecursos();
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("About PhotoTDS");
		frame.setIconImage(logo);
		frame.setResizable(false);
		frame.setBounds(100, 100, 256, 288);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel logoPanel = new JPanel();
		GridBagConstraints gbc_logoPanel = new GridBagConstraints();
		gbc_logoPanel.insets = new Insets(0, 0, 5, 0);
		gbc_logoPanel.fill = GridBagConstraints.VERTICAL;
		gbc_logoPanel.gridx = 0;
		gbc_logoPanel.gridy = 0;
		frame.getContentPane().add(logoPanel, gbc_logoPanel);
		logoPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel logoIcon = new JLabel("");
		logoIcon.setIcon(new ImageIcon(logo.getScaledInstance(74, 88, Image.SCALE_SMOOTH)));
		logoIcon.setHorizontalAlignment(SwingConstants.CENTER);
		logoPanel.add(logoIcon);
		
		JLabel lblNewLabel = new JLabel("PhotoTDS v1");
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		logoPanel.add(lblNewLabel, BorderLayout.SOUTH);
		
		JPanel infoPanel = new JPanel();
		GridBagConstraints gbc_infoPanel = new GridBagConstraints();
		gbc_infoPanel.fill = GridBagConstraints.VERTICAL;
		gbc_infoPanel.gridx = 0;
		gbc_infoPanel.gridy = 1;
		frame.getContentPane().add(infoPanel, gbc_infoPanel);
		infoPanel.setLayout(new BorderLayout(0, 0));
		
		JSeparator separator = new JSeparator();
		infoPanel.add(separator, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		infoPanel.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{272, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel_1 = new JLabel("Francisco David Campuzano Melgarejo");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Diego José Párraga Nicolás");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.ITALIC, 13));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
	}
	
	private void cargarRecursos() {
		try {
			logo = ImageIO.read(VentanaAbout.class.getResource("/imagenes/dock/logo1.png"));
		} catch (IOException ioe) { System.err.println("ERR loading resource"); }
	}
	
	/* Si queremos que cada vez se cree una instancia nueva y que sea única, la destruimos */
	private void destruir() {
		frame.dispose();
		instancia = null;
	}
	
	public static VentanaAbout getInstancia() {
		if(instancia==null) instancia = new VentanaAbout();
		return instancia;
	}
	
	
	public void mostrar() {
		frame.setVisible(true);
	}
	
	public void ocultar() {
		frame.setVisible(false);
	}
	
	

}
