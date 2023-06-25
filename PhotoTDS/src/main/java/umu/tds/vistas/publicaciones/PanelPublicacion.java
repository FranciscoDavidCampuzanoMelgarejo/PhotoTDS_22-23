package umu.tds.vistas.publicaciones;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import umu.tds.modelo.pojos.Album;
import umu.tds.modelo.pojos.Foto;
import umu.tds.modelo.pojos.Publicacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class PanelPublicacion extends JPanel {

	private static final int ANCHO_ICONO = 30;

	private Publicacion publicacion; // Puede ser una foto o un album

	private final Color colorIcono = new Color(81, 83, 86);
	private final Color colorOverIcono = new Color(131, 134, 139);
	private final Color colorClickIcono = new Color(255, 255, 255);
	private boolean overPrev, overNext;
	private boolean clickPrev, clickNext;
	private Image imagenPrev, imagenNext;
	private JLabel lblNext, lblPrev;

	private JLabel lblTitulo;
	private JLabel lblFoto;
	private int anchoFoto;

	private int indice;
	private List<Foto> fotos = null;
	private JPanel panelDescripcion;
	private Component rigidArea_1;
	private Component rigidArea_2;
	private JScrollPane scrollDescripcion;
	private JTextArea textArea;

	public PanelPublicacion(int anchoFoto, Publicacion publicacion) {
		this.publicacion = publicacion;
		this.anchoFoto = anchoFoto;
		this.overPrev = this.overNext = false;
		this.clickPrev = this.clickNext = false;
		this.imagenPrev = new ImageIcon(getClass().getResource("/imagenes/iconos-dialogo_publicaciones/prev.png"))
				.getImage().getScaledInstance(ANCHO_ICONO, ANCHO_ICONO, Image.SCALE_SMOOTH);
		this.imagenNext = new ImageIcon(getClass().getResource("/imagenes/iconos-dialogo_publicaciones/next.png"))
				.getImage().getScaledInstance(ANCHO_ICONO, ANCHO_ICONO, Image.SCALE_SMOOTH);

		this.indice = 0;
		if (publicacion instanceof Album) {
			this.fotos = new ArrayList<Foto>(((Album) publicacion).getFotos());
		}
		inicializar();
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
		this.indice = 0;
		if (publicacion instanceof Album) {
			this.fotos = new ArrayList<Foto>(((Album) publicacion).getFotos());
		}
		lblTitulo.setText(publicacion.getTitulo());
		textArea.setText(publicacion.getDescripcion());
		cargarPublicacion();
	}

	private void inicializar() {
		setLayout(new BorderLayout(0, 0));

		lblPrev = new JLabel(new ImageIcon(imagenPrev)) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

				Color c = !overPrev ? colorIcono : (clickPrev ? colorClickIcono : colorOverIcono);
				g2d.setColor(c);
				g2d.fillOval(0, 0, getWidth(), getHeight());

				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
				super.paintComponent(g);
			}
		};
		lblPrev.setVisible(false);

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
					checkPrevNext();
					cargarPublicacion();
					lblPrev.repaint();
				}
			}
		});

		lblNext = new JLabel(new ImageIcon(imagenNext)) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

				Color c = !overNext ? colorIcono : (clickNext ? colorClickIcono : colorOverIcono);
				g2d.setColor(c);

				g2d.fillOval(0, 0, getWidth(), getHeight());

				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
				super.paintComponent(g);
			}
		};
		lblNext.setVisible(false);

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
					clickNext = false;
					lblNext.repaint();
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (lblNext.isEnabled()) {
					clickNext = true;
					indice++;
					checkPrevNext();
					cargarPublicacion();
					lblNext.repaint();
				}
			}
		});

		lblFoto = new JLabel();
		lblFoto.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		lblFoto.setLayout(new BorderLayout());

		// Añadir las etiquetas prev y next a la etiqueta de la foto
		JPanel panelPrev = new JPanel();
		panelPrev.setLayout(new BoxLayout(panelPrev, BoxLayout.Y_AXIS));
		panelPrev.setOpaque(false);
		panelPrev.add(Box.createVerticalGlue());
		panelPrev.add(lblPrev);
		panelPrev.add(Box.createVerticalGlue());
		lblFoto.add(panelPrev, BorderLayout.WEST);

		JPanel panelNext = new JPanel();
		panelNext.setLayout(new BoxLayout(panelNext, BoxLayout.Y_AXIS));
		panelNext.setOpaque(false);
		panelNext.add(Box.createVerticalGlue());
		panelNext.add(lblNext);
		panelNext.add(Box.createVerticalGlue());
		lblFoto.add(panelNext, BorderLayout.EAST);

		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblFoto, BorderLayout.CENTER);

		JPanel panelTitulo = new JPanel();
		add(panelTitulo, BorderLayout.NORTH);
		panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));

		Component rigidArea = Box.createRigidArea(new Dimension(20, 25));
		panelTitulo.add(rigidArea);

		lblTitulo = new JLabel(publicacion.getTitulo());
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
		panelTitulo.add(lblTitulo);

		rigidArea_2 = Box.createRigidArea(new Dimension(20, 5));
		panelTitulo.add(rigidArea_2);

		panelDescripcion = new JPanel();
		add(panelDescripcion, BorderLayout.SOUTH);
		panelDescripcion.setLayout(new BoxLayout(panelDescripcion, BoxLayout.Y_AXIS));

		

		textArea = new JTextArea();
		textArea.setRows(2);
		textArea.setColumns(20);
		textArea.setOpaque(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setText(publicacion.getDescripcion());
		
		scrollDescripcion = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelDescripcion.add(scrollDescripcion);
		

		rigidArea_1 = Box.createRigidArea(new Dimension(20, 25));
		panelDescripcion.add(rigidArea_1);

		if (fotos == null) {
			cargarPublicacion();
		} else {
			checkPrevNext();
			cargarPublicacion();
		}
	}

	// Metodos auxiliares

	private void checkPrevNext() {
		lblPrev.setVisible(true);
		lblNext.setVisible(true);

		if (fotos.size() == 1) {
			lblPrev.setEnabled(false);
			lblNext.setEnabled(false);
		} else {
			if (indice == 0) {
				lblPrev.setEnabled(false);
				lblNext.setEnabled(true);
			} else if (indice == fotos.size() - 1) {
				lblPrev.setEnabled(true);
				lblNext.setEnabled(false);
			} else {
				lblPrev.setEnabled(true);
				lblNext.setEnabled(true);
			}
		}
	}

	private void cargarPublicacion() {

		String ruta;
		if (this.fotos == null) {
			lblPrev.setVisible(false);
			lblNext.setVisible(false);
			ruta = ((Foto) publicacion).getRuta();
		} else {
			lblPrev.setVisible(true);
			lblNext.setVisible(true);
			checkPrevNext();
			ruta = fotos.get(indice).getRuta();
		}

		Image imagen = new ImageIcon(ruta).getImage().getScaledInstance(anchoFoto, anchoFoto, Image.SCALE_SMOOTH);
		lblFoto.setIcon(new ImageIcon(imagen));

	}

}
