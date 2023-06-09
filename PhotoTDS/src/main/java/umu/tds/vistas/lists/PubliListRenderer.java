package umu.tds.vistas.lists;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.pojos.*;

import java.awt.Component;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

/**
 * @title Rendering for publication list
 * @author diego
 * */
public class PubliListRenderer extends JPanel implements ListCellRenderer<Publicacion>, Serializable{
	private static final long serialVersionUID = 1L;
	private final int PUBLISIZE = 192;
	
	private Publicacion publi;
	private Foto foto;
	private BufferedImage img, userpic;
	
	private JLabel usuarioPic, publiIcon, usuarioName;
	
	private void cargarRecursos() {
		
		try {
			foto = (Foto) publi;
			img = ImageIO.read(new File(foto.getRuta()));
			String ruta = Controlador.getControlador().getUserPicture(publi.getUsuario().getUsuario());
			userpic = (ruta==null) ? ImageIO.read(getClass().getResource("/imagenes/noprofilepic.png")) : ImageIO.read(new File(ruta));
		} catch (IOException ioe) { System.err.println("ERR cargando publicación '" + publi.toString() + "'"); }
	}
		
	/* Constructor del panel */
	public PubliListRenderer() {
		setOpaque(false);
	}
	
	/* Dibujado del panel */
	private void dibujar() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{256, 0, 0};
		gridBagLayout.rowHeights = new int[]{192, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel fotoPubli = new JPanel();
		GridBagConstraints gbc_fotoPubli = new GridBagConstraints();
		gbc_fotoPubli.insets = new Insets(0, 0, 0, 5);
		gbc_fotoPubli.fill = GridBagConstraints.BOTH;
		gbc_fotoPubli.gridx = 0;
		gbc_fotoPubli.gridy = 0;
		add(fotoPubli, gbc_fotoPubli);
		fotoPubli.setLayout(new BorderLayout(0, 0));
		
		publiIcon = new JLabel("");
		publiIcon.setHorizontalAlignment(SwingConstants.CENTER);
		fotoPubli.add(publiIcon);
		
		JPanel infoPubli = new JPanel();
		GridBagConstraints gbc_infoPubli = new GridBagConstraints();
		gbc_infoPubli.fill = GridBagConstraints.BOTH;
		gbc_infoPubli.gridx = 1;
		gbc_infoPubli.gridy = 0;
		add(infoPubli, gbc_infoPubli);
		GridBagLayout gbl_infoPubli = new GridBagLayout();
		gbl_infoPubli.columnWidths = new int[]{0, 0};
		gbl_infoPubli.rowHeights = new int[]{0, 0, 0};
		gbl_infoPubli.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_infoPubli.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		infoPubli.setLayout(gbl_infoPubli); 
		
		JPanel panelUsuario = new JPanel();
		GridBagConstraints gbc_panelUsuario = new GridBagConstraints();
		gbc_panelUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_panelUsuario.fill = GridBagConstraints.BOTH;
		gbc_panelUsuario.gridx = 0;
		gbc_panelUsuario.gridy = 0;
		infoPubli.add(panelUsuario, gbc_panelUsuario);
		panelUsuario.setLayout(new BorderLayout(0, 0));
		
		usuarioPic = new JLabel("");
		usuarioPic.setHorizontalAlignment(SwingConstants.CENTER);
		panelUsuario.add(usuarioPic, BorderLayout.WEST);
		
		usuarioName = new JLabel("");
		usuarioName.setHorizontalAlignment(SwingConstants.LEFT);
		panelUsuario.add(usuarioName, BorderLayout.CENTER);
		
		JPanel likesComents = new JPanel();
		GridBagConstraints gbc_likesComents = new GridBagConstraints();
		gbc_likesComents.fill = GridBagConstraints.BOTH;
		gbc_likesComents.gridx = 0;
		gbc_likesComents.gridy = 1;
		infoPubli.add(likesComents, gbc_likesComents);
		
		publiIcon.setIcon(new ImageIcon(img.getScaledInstance(PUBLISIZE, PUBLISIZE, Image.SCALE_FAST)));
		usuarioPic.setIcon(new ImageIcon(userpic.getScaledInstance(32, 32, Image.SCALE_FAST)));
		usuarioName.setText(foto.getUsuario().getUsuario());
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Publicacion> list, Publicacion publi, int index, boolean isSelected, boolean cellHasFocus) {
		PubliListRenderer cell = new PubliListRenderer();
		cell.publi = publi;
		cell.cargarRecursos();
		cell.dibujar();
		
		return cell;
	}
	
}
