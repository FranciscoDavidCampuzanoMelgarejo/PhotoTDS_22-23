package umu.tds.vistas.publicaciones;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import umu.tds.modelo.pojos.Album;
import umu.tds.modelo.pojos.Foto;
import umu.tds.modelo.pojos.Publicacion;

public class DialogoPublicacion extends JDialog {
	private static final long serialVersionUID = 1L;
	// Constantes
	private static final double PORCENTAJE_ANCHO_FOTO = 0.45; // Porcentaje del ancho de la ventana que ocupara la foto
																// del perfil
	private static final int ANCHO_ICONO_CROSS = 25; // Ancho de la imagen 'cross' para cerrar el dialogo
	private static final int ANCHO_ICONO_FLECHA = 35; // Ancho de las imagenes 'prev' y 'next' usadas para pasar entre
														// publicaciones
	
	
	private final Color colorLabelFlecha = new Color(81, 83, 86);
	private final Color colorOverLabelFlecha = new Color(131, 134, 139);
	private final Color colorClickLabelFlecha = new Color(255, 255, 255);
	
	

	// Atributos
	private JFrame framePadre;

	private JLabel lblPrev, lblCross, lblNext;
	private Image prevIcon, crossIcon, nextIcon;
	private boolean overPrev, overCross, overNext;
	private boolean clickPrev, clickCross, clickNext;

	
	private PanelPublicacion panelPublicacion;
	private JLabel lblFoto;
	private final int anchoFoto;
	private Publicacion publicacionActual;
	private int indice;
	private List<Publicacion> publicaciones;

	private String rutaFotoPerfil;
	private String nickname;

	private PanelComentarios panelComentarios;

	public DialogoPublicacion(JFrame frame, List<Publicacion> publicaciones, int indice, String fotoPerfil, String nickname) {
		super(frame, true);
		this.framePadre = frame;
		this.publicaciones = publicaciones;
		this.indice = indice;
		this.rutaFotoPerfil = fotoPerfil;
		this.nickname = nickname;
		this.overCross = this.overPrev = this.overNext = false;
		this.clickCross = this.clickPrev = this.clickNext = false;
		
		this.publicacionActual = publicaciones.get(indice);

		this.anchoFoto = (int) (frame.getSize().width * PORCENTAJE_ANCHO_FOTO);
		this.prevIcon = new ImageIcon(getClass().getResource("/imagenes/iconos-dialogo_publicaciones/prev.png"))
				.getImage().getScaledInstance(ANCHO_ICONO_FLECHA, ANCHO_ICONO_FLECHA, Image.SCALE_SMOOTH);
		this.nextIcon = new ImageIcon(getClass().getResource("/imagenes/iconos-dialogo_publicaciones/next.png"))
				.getImage().getScaledInstance(ANCHO_ICONO_FLECHA, ANCHO_ICONO_FLECHA, Image.SCALE_SMOOTH);
		this.crossIcon = new ImageIcon(getClass().getResource("/imagenes/iconos-dialogo_publicaciones/close.png"))
				.getImage().getScaledInstance(ANCHO_ICONO_CROSS, ANCHO_ICONO_CROSS, Image.SCALE_SMOOTH);

		inicializar();
	}
	
	public void mostrar() {
		setVisible(true);
	}

	private void inicializar() {
		// setSize(600, 600);
		setSize(framePadre.getSize().width, framePadre.getSize().height);
		setLocationRelativeTo(framePadre);
		setUndecorated(true);
		setResizable(false);

		JPanel panelCentral = new JPanel();
		getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelCentral.rowHeights = new int[] { 0, 0, 20, 0 };
		gbl_panelCentral.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelCentral.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panelCentral.setLayout(gbl_panelCentral);

		lblCross = new JLabel(new ImageIcon(crossIcon)) {

			// Dibujar una circulo y circunferencia (parte exterior) sobre el icono de
			// 'cross'. El circulo se dibuja en el fondo y la imagen
			// por encima
			@Override
			protected void paintComponent(Graphics g) {
				if (overCross) {
					Graphics2D g2d = (Graphics2D) g;
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

					g2d.setColor(Color.lightGray);
					g2d.fillOval(0, 0, getWidth(), getHeight());

					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
					g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
				}

				super.paintComponent(g);
			}
		};

		lblCross.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				overCross = true;
				lblCross.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				overCross = false;
				lblCross.repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});

		lblCross.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		GridBagConstraints gbc_lblCross = new GridBagConstraints();
		gbc_lblCross.insets = new Insets(15, 0, 15, 10);
		gbc_lblCross.gridx = 3;
		gbc_lblCross.gridy = 0;
		panelCentral.add(lblCross, gbc_lblCross);

		lblPrev = new JLabel(new ImageIcon(prevIcon)) {
			// Dibujar un circulo sobre el icono de 'prev'. Es lo mismo que con el icono de
			// 'cross'
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

				Color c = !overPrev ? colorLabelFlecha : (clickPrev ? colorClickLabelFlecha : colorOverLabelFlecha);
				g2d.setColor(c);
				// int x = (getWidth() - DIAMETRO) / 2; // Coordenada x del centro del JLabel
				// int y = (getHeight() - DIAMETRO) / 2; // Coordenada y del centro del JLabel
				g2d.fillOval(0, 0, getWidth(), getHeight());

				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
				super.paintComponent(g);
			}
		};

		lblPrev.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (lblPrev.isEnabled()) {
					overPrev = true;
					lblPrev.repaint();
				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (lblPrev.isEnabled()) {
					overPrev = false;
					clickPrev = false;
					lblPrev.repaint();
				}

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (lblPrev.isEnabled()) {
					clickPrev = true;
					indice--;
					checkBotones();
					cargarFoto();
					lblPrev.repaint();
				}
				//clickPrev = false;
				
			}
		});

		
		GridBagConstraints gbc_lblPrev = new GridBagConstraints();
		gbc_lblPrev.insets = new Insets(0, 5, 5, 10);
		gbc_lblPrev.gridx = 0;
		gbc_lblPrev.gridy = 1;
		panelCentral.add(lblPrev, gbc_lblPrev);


		
		panelPublicacion = new PanelPublicacion(anchoFoto, publicacionActual);
		GridBagConstraints gbc_panelPublicacion = new GridBagConstraints();
		gbc_panelPublicacion.fill = GridBagConstraints.BOTH;
		gbc_panelPublicacion.insets = new Insets(0, 0, 5, 5);
		gbc_panelPublicacion.gridx = 1;
		gbc_panelPublicacion.gridy = 1;
		panelCentral.add(panelPublicacion, gbc_panelPublicacion);

		panelComentarios = new PanelComentarios(rutaFotoPerfil, nickname, publicacionActual);
		GridBagConstraints gbc_panelComentarios = new GridBagConstraints();
		gbc_panelComentarios.insets = new Insets(0, 0, 5, 5);
		gbc_panelComentarios.fill = GridBagConstraints.BOTH;

		gbc_panelComentarios.gridx = 2;
		gbc_panelComentarios.gridy = 1;
		panelCentral.add(panelComentarios, gbc_panelComentarios);

		lblNext = new JLabel(new ImageIcon(nextIcon)) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

				Color c = !overNext ? colorLabelFlecha : (clickNext ? colorClickLabelFlecha : colorOverLabelFlecha);
				g2d.setColor(c);
				// int x = (getWidth() - DIAMETRO) / 2; // Coordenada x del centro del JLabel
				// int y = (getHeight() - DIAMETRO) / 2; // Coordenada y del centro del JLabel
				g2d.fillOval(0, 0, getWidth(), getHeight());

				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
				super.paintComponent(g);
			}
		};

		lblNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (lblNext.isEnabled()) {
					overNext = true;
					lblNext.repaint();
				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (lblNext.isEnabled()) {
					overNext = false;
					lblNext.repaint();
				}

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (lblNext.isEnabled()) {
					clickNext = true;
					indice++;
					checkBotones();
					cargarFoto();
					lblNext.repaint();
				}

			}
		});
		
		GridBagConstraints gbc_lblNext = new GridBagConstraints();
		gbc_lblNext.insets = new Insets(0, 10, 5, 5);
		gbc_lblNext.gridx = 3;
		gbc_lblNext.gridy = 1;
		panelCentral.add(lblNext, gbc_lblNext);

		checkBotones();
		setVisible(false);
	}

	private void checkBotones() {
		
		if(publicaciones.size() == 1) {
			lblPrev.setEnabled(false);
			lblNext.setEnabled(false);
		} else {
			if (indice == 0) {
				lblPrev.setEnabled(false);
				lblNext.setEnabled(true);
			} else if (indice > 0 && indice < publicaciones.size() - 1) {
				lblPrev.setEnabled(true);
				lblNext.setEnabled(true);
			} else {
				lblNext.setEnabled(false);
				lblPrev.setEnabled(true);
			}
		}
		
	}

	private void cargarFoto() {
		this.publicacionActual = publicaciones.get(indice);
		panelPublicacion.setPublicacion(publicacionActual);
		panelComentarios.cambiarFoto(publicacionActual);
		revalidate();
		repaint();
	}

}
