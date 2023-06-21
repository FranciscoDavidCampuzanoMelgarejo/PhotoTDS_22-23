package umu.tds.vistas.lists;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import umu.tds.controlador.Controlador;
import umu.tds.vistas.Utils;

import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 * @title Panel para ListCellRenderer de Hashtags
 * @author diego
 * */
public class HashListPanel  extends JPanel{
	private static final long serialVersionUID = 1L;
	private BufferedImage icon;

	private void cargarRecursos() {
		try {
			icon = ImageIO.read(getClass().getResource("/imagenes/hashtag.png"));
		} catch (IOException ioe) { ioe.printStackTrace(); System.exit(-1); }
	}
	
	public HashListPanel(String hashtag) {
		cargarRecursos();
		setBorder(new LineBorder(new Color(0x3c-10, 0x3f-10, 0x41-10)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{48, 0, 0};
		gridBagLayout.rowHeights = new int[]{32, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panelIcon = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelIcon = new GridBagConstraints();
		gbc_panelIcon.fill = GridBagConstraints.BOTH;
		gbc_panelIcon.gridx = 0;
		gbc_panelIcon.gridy = 0;
		add(panelIcon, gbc_panelIcon);
		panelIcon.setLayout(new BorderLayout(0, 0));
		
		JLabel labelIcon = new JLabel();
			labelIcon.setIcon(new ImageIcon(icon));
		labelIcon.setHorizontalAlignment(SwingConstants.CENTER);
		panelIcon.add(labelIcon, BorderLayout.CENTER);
		
		JPanel panelInfo = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelInfo = new GridBagConstraints();
		gbc_panelInfo.fill = GridBagConstraints.BOTH;
		gbc_panelInfo.gridx = 1;
		gbc_panelInfo.gridy = 0;
		add(panelInfo, gbc_panelInfo);
		GridBagLayout gbl_panelInfo = new GridBagLayout();
		gbl_panelInfo.columnWidths = new int[]{0, 0};
		gbl_panelInfo.rowHeights = new int[]{0, 0, 0};
		gbl_panelInfo.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelInfo.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panelInfo.setLayout(gbl_panelInfo);
		
		JPanel panelHashName = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelHashName = new GridBagConstraints();
		gbc_panelHashName.fill = GridBagConstraints.BOTH;
		gbc_panelHashName.gridx = 0;
		gbc_panelHashName.gridy = 0;
		panelInfo.add(panelHashName, gbc_panelHashName);
		panelHashName.setLayout(new BorderLayout(0, 0));
		
		JLabel labelHashName = new JLabel(hashtag);
		labelHashName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		panelHashName.add(labelHashName, BorderLayout.CENTER);
		
		JPanel panelHashPublis = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelHashPublis = new GridBagConstraints();
		gbc_panelHashPublis.fill = GridBagConstraints.BOTH;
		gbc_panelHashPublis.gridx = 0;
		gbc_panelHashPublis.gridy = 1;
		panelInfo.add(panelHashPublis, gbc_panelHashPublis);
		panelHashPublis.setLayout(new BorderLayout(0, 0));
		
		int nPublis = Controlador.getControlador().getPublisHashtag(hashtag).size();
		JLabel labelHashPublis = new JLabel();
		if(nPublis==0) labelHashPublis.setText("No hay publicaciones");	// Este caso no se puede dar
		else if(nPublis==1) labelHashPublis.setText("Una publicacion");
		else labelHashPublis.setText(nPublis + " publicaciones");
		labelHashPublis.setFont(new Font("Tahoma", Font.ITALIC, 10));
		panelHashPublis.add(labelHashPublis, BorderLayout.CENTER);
	}
	
}
