package umu.tds.vistas.publicaciones;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import umu.tds.modelo.pojos.Album;
import umu.tds.modelo.pojos.Publicacion;
import umu.tds.vistas.Utils;

import java.awt.Font;
import java.awt.Cursor;

public class PanelIconos extends JPanel {

	private static final int ANCHO_ICONO = 26;
	private static final int ANCHO_ICONO_REESCALADO = 30;

	private Image iconoLike, iconoComentario, iconoBasura, iconoAddAlbum;
	private Publicacion publicacion;
	private int numLikes;
	private final DateTimeFormatter formato;
	private String fechaPublicacion;

	private JLabel lblNumLikes;
	private JLabel lblFecha;

	private JLabel lblLike, lblComentario, lblBasura, lblAddAlbum;

	public PanelIconos(Publicacion publicacion) {
		this.publicacion = publicacion;
		this.numLikes = publicacion.getLikes();
		// Convertir la fecha de publicacion (LocalDate) a Strign con el formato de
		// "dd/MM/yyyy"
		this.formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		this.fechaPublicacion = formato.format(publicacion.getFecha());

		this.iconoLike = new ImageIcon(getClass().getResource("/imagenes/iconos-dialogo_publicaciones/corazon.png"))
				.getImage().getScaledInstance(ANCHO_ICONO, ANCHO_ICONO, Image.SCALE_SMOOTH);

		this.iconoComentario = new ImageIcon(
				getClass().getResource("/imagenes/iconos-dialogo_publicaciones/comentario.png")).getImage()
						.getScaledInstance(ANCHO_ICONO, ANCHO_ICONO, Image.SCALE_SMOOTH);

		this.iconoBasura = new ImageIcon(getClass().getResource("/imagenes/iconos-dialogo_publicaciones/trash.png"))
				.getImage().getScaledInstance(ANCHO_ICONO, ANCHO_ICONO, Image.SCALE_SMOOTH);

		this.iconoAddAlbum = new ImageIcon(getClass().getResource("/imagenes/iconos-dialogo_publicaciones/plus.png"))
				.getImage().getScaledInstance(ANCHO_ICONO, ANCHO_ICONO, Image.SCALE_SMOOTH);

		inicializar();
	}

	public void cambiarFoto(Publicacion publicacion) {
		this.publicacion = publicacion;
		this.numLikes = publicacion.getLikes();
		this.fechaPublicacion = formato.format(publicacion.getFecha());
		String s = String.valueOf(numLikes) + " Me gusta";
		lblNumLikes.setText(s);
		lblFecha.setText(fechaPublicacion);
		// revalidate();
		// repaint();
	}

	private void inicializar() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 15, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel panelIconos = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelIconos.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelIconos = new GridBagConstraints();
		gbc_panelIconos.insets = new Insets(0, 0, 5, 0);
		gbc_panelIconos.fill = GridBagConstraints.BOTH;
		gbc_panelIconos.gridx = 1;
		gbc_panelIconos.gridy = 0;
		add(panelIconos, gbc_panelIconos);

		lblLike = new JLabel(new ImageIcon(iconoLike));
		lblLike.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelIconos.add(lblLike);

		lblLike.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Image imagen = new ImageIcon(
						getClass().getResource("/imagenes/iconos-dialogo_publicaciones/corazon.png")).getImage()
								.getScaledInstance(ANCHO_ICONO_REESCALADO, ANCHO_ICONO_REESCALADO, Image.SCALE_SMOOTH);
				lblLike.setIcon(new ImageIcon(imagen));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblLike.setIcon(new ImageIcon(iconoLike));
			}
		});

		Component horizontalStrut = Box.createHorizontalStrut(15);
		panelIconos.add(horizontalStrut);

		lblComentario = new JLabel(new ImageIcon(iconoComentario));
		lblComentario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelIconos.add(lblComentario);
		
		lblComentario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Image imagen = new ImageIcon(
						getClass().getResource("/imagenes/iconos-dialogo_publicaciones/comentario.png")).getImage()
								.getScaledInstance(ANCHO_ICONO_REESCALADO, ANCHO_ICONO_REESCALADO, Image.SCALE_SMOOTH);
				lblComentario.setIcon(new ImageIcon(imagen));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblComentario.setIcon(new ImageIcon(iconoComentario));
			}
		});

		Component horizontalStrut_1 = Box.createHorizontalStrut(15);
		panelIconos.add(horizontalStrut_1);

		lblBasura = new JLabel(new ImageIcon(iconoBasura));
		lblBasura.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelIconos.add(lblBasura);
		
		lblBasura.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Image imagen = new ImageIcon(
						getClass().getResource("/imagenes/iconos-dialogo_publicaciones/trash.png")).getImage()
								.getScaledInstance(ANCHO_ICONO_REESCALADO, ANCHO_ICONO_REESCALADO, Image.SCALE_SMOOTH);
				lblBasura.setIcon(new ImageIcon(imagen));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblBasura.setIcon(new ImageIcon(iconoBasura));
			}
		});

		Component horizontalStrut_2 = Box.createHorizontalStrut(15);
		panelIconos.add(horizontalStrut_2);

		lblAddAlbum = new JLabel(new ImageIcon(iconoAddAlbum));
		lblAddAlbum.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelIconos.add(lblAddAlbum);
		
		lblAddAlbum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Image imagen = new ImageIcon(
						getClass().getResource("/imagenes/iconos-dialogo_publicaciones/plus.png")).getImage()
								.getScaledInstance(ANCHO_ICONO_REESCALADO, ANCHO_ICONO_REESCALADO, Image.SCALE_SMOOTH);
				lblAddAlbum.setIcon(new ImageIcon(imagen));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblAddAlbum.setIcon(new ImageIcon(iconoAddAlbum));
			}
		});

		if (!(publicacion instanceof Album))
			lblAddAlbum.setVisible(false);

		/*
		 * lblComentario.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseEntered(MouseEvent e) { BufferedImage
		 * imagenBrillante = Utils.cambiarBrillo(0.7f, iconoComentario);
		 * lblComentario.setIcon(new ImageIcon(imagenBrillante)); }
		 * 
		 * @Override public void mouseExited(MouseEvent e) { lblComentario.setIcon(new
		 * ImageIcon(iconoComentario)); } });
		 */

		String s = String.valueOf(numLikes) + " Me gusta";
		lblNumLikes = new JLabel(s);
		lblNumLikes.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblNumLikes = new GridBagConstraints();
		gbc_lblNumLikes.anchor = GridBagConstraints.WEST;
		gbc_lblNumLikes.gridx = 1;
		gbc_lblNumLikes.gridy = 1;
		add(lblNumLikes, gbc_lblNumLikes);

		lblFecha = new JLabel(fechaPublicacion);
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblFecha = new GridBagConstraints();
		gbc_lblFecha.anchor = GridBagConstraints.WEST;
		gbc_lblFecha.gridx = 1;
		gbc_lblFecha.gridy = 2;
		add(lblFecha, gbc_lblFecha);

	}

}
