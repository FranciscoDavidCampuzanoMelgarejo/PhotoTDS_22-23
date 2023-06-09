package umu.tds.vistas.perfil;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
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

	private Image imagenOriginal;
	private Image iconoLike;
	private Image iconoComentario;

	private JLabel lblLike, lblComentario;

	public EtiquetaFotoPerfil(String ruta, Image iconoLike, Image iconoComentario, int ancho) {
		this.imagenOriginal = new ImageIcon(ruta).getImage();
		this.iconoLike = iconoLike.getScaledInstance(ANCHO_ICONO, ANCHO_ICONO, Image.SCALE_SMOOTH);
		this.iconoComentario = iconoComentario.getScaledInstance(ANCHO_ICONO, ANCHO_ICONO, Image.SCALE_SMOOTH);
		this.imagenReescalada = crearImagenReescalada(ancho);
		this.imagenOscurecida = crearImagenOscura();
		inicializar();

	}

	private void inicializar() {
		setLayout(new GridLayout(1, 2, 20, 0));
		setIcon(new ImageIcon(imagenReescalada));

		lblLike = new JLabel(new ImageIcon(iconoLike));
		lblLike.setVisible(false);
		lblComentario = new JLabel(new ImageIcon(iconoComentario));
		lblComentario.setVisible(false);
		add(lblLike);
		add(lblComentario);

		// Efecto hover
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				EtiquetaFotoPerfil.this.setIcon(new ImageIcon(imagenOscurecida));
				lblLike.setVisible(true);
				lblComentario.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				System.out.println("estoy fuera");
				EtiquetaFotoPerfil.this.setIcon(new ImageIcon(imagenReescalada));
				lblLike.setVisible(false);
				lblComentario.setVisible(false);
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
		RescaleOp rescale = new RescaleOp(0.7f, 0, null);
		return rescale.filter(this.imagenReescalada, null);
	}
}
