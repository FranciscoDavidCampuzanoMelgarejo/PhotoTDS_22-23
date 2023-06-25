package umu.tds.vistas.perfil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class EtiquetaFotoPerfil extends JLabel {

	private static final int ANCHO_ICONO = 20;

	private BufferedImage imagenReescalada;
	private BufferedImage imagenOscurecida;

	private int numLikes, numComentarios;
	
	private Image imagenOriginal;
	private Image iconoLike;
	private Image iconoComentario;

	private JLabel lblLike, lblComentario;
	private JLabel lblNumLikes, lblNumComentarios;

	public EtiquetaFotoPerfil(String ruta, Image iconoLike, Image iconoComentario, int numLikes, int numComentarios, int ancho) {
		this.imagenOriginal = new ImageIcon(ruta).getImage();
		this.numLikes = numLikes;
		this.numComentarios = numComentarios;
		this.iconoLike = iconoLike.getScaledInstance(ANCHO_ICONO, ANCHO_ICONO, Image.SCALE_SMOOTH);
		this.iconoComentario = iconoComentario.getScaledInstance(ANCHO_ICONO, ANCHO_ICONO, Image.SCALE_SMOOTH);
		this.imagenReescalada = crearImagenReescalada(ancho);
		this.imagenOscurecida = crearImagenOscura();
		inicializar();

	}

	private void inicializar() {
		GridBagLayout gbl_etiqueta = new GridBagLayout();
		gbl_etiqueta.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_etiqueta.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_etiqueta.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_etiqueta.rowWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gbl_etiqueta);
		setIcon(new ImageIcon(imagenReescalada));
		
		// Etiqueta con el icono de like (corazon)
		lblLike = new JLabel(new ImageIcon(iconoLike));
		lblLike.setVisible(false);
		GridBagConstraints gbc_lblLike = new GridBagConstraints();
		gbc_lblLike.anchor = GridBagConstraints.EAST;
		gbc_lblLike.fill = GridBagConstraints.NONE;
		gbc_lblLike.insets = new Insets(0, 0, 5, 5);
		gbc_lblLike.gridx = 1;
		gbc_lblLike.gridy = 1;
		add(lblLike, gbc_lblLike);
		
		// Etiqueta con el icono de comentario
		lblComentario = new JLabel(new ImageIcon(iconoComentario));
		lblComentario.setVisible(false);
		GridBagConstraints gbc_lblComentario = new GridBagConstraints();
		gbc_lblComentario.anchor = GridBagConstraints.EAST;
		gbc_lblComentario.fill = GridBagConstraints.NONE;
		gbc_lblComentario.insets = new Insets(0, 0, 5, 5);
		gbc_lblComentario.gridx = 3;
		gbc_lblComentario.gridy = 1;
		add(lblComentario, gbc_lblComentario);
		
		lblNumLikes = new JLabel(String.valueOf(numLikes));
		lblNumLikes.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNumLikes.setForeground(Color.WHITE);
		lblNumLikes.setVisible(false);
		GridBagConstraints gbc_lblNumLikes = new GridBagConstraints();
		gbc_lblNumLikes.anchor = GridBagConstraints.WEST;
		gbc_lblNumLikes.fill = GridBagConstraints.NONE;
		gbc_lblNumLikes.insets = new Insets(0, 0, 5, 25);
		gbc_lblNumLikes.gridx = 2;
		gbc_lblNumLikes.gridy = 1;
		add(lblNumLikes, gbc_lblNumLikes);

		

		lblNumComentarios = new JLabel(String.valueOf(numComentarios));
		lblNumComentarios.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNumComentarios.setForeground(Color.WHITE);
		lblNumComentarios.setVisible(false);
		GridBagConstraints gbc_lblNumComentarios = new GridBagConstraints();
		gbc_lblNumComentarios.anchor = GridBagConstraints.WEST;
		gbc_lblNumComentarios.fill = GridBagConstraints.NONE;
		gbc_lblNumComentarios.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumComentarios.gridx = 4;
		gbc_lblNumComentarios.gridy = 1;
		add(lblNumComentarios, gbc_lblNumComentarios);


		// Efecto hover
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				EtiquetaFotoPerfil.this.setIcon(new ImageIcon(imagenOscurecida));
				lblLike.setVisible(true);
				lblComentario.setVisible(true);
				lblNumLikes.setVisible(true);
				lblNumComentarios.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				EtiquetaFotoPerfil.this.setIcon(new ImageIcon(imagenReescalada));
				lblLike.setVisible(false);
				lblComentario.setVisible(false);
				lblNumLikes.setVisible(false);
				lblNumComentarios.setVisible(false);
			}
		});
	}

	public void reescalar(int ancho) {
		this.imagenReescalada = crearImagenReescalada(ancho);
		this.imagenOscurecida = crearImagenOscura();
		setIcon(new ImageIcon(imagenReescalada));
	}

	// Metodos auxiliares privados

	private BufferedImage crearImagenReescalada(int ancho) {

		BufferedImage imagen = new BufferedImage(ancho, ancho, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = imagen.createGraphics();
		g2d.drawImage(imagenOriginal, 0, 0, ancho, ancho, null);
		g2d.dispose();
		return imagen;

	}

	private BufferedImage crearImagenOscura() {
		RescaleOp rescale = new RescaleOp(0.5f, 0, null);
		return rescale.filter(this.imagenReescalada, null);
	}
}
