package umu.tds.vistas.publicaciones;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import umu.tds.modelo.pojos.Comentario;
import umu.tds.modelo.pojos.Publicacion;

import java.awt.Font;

public class PanelComentarios extends JPanel {

	private final Color foregroundPublicar = new Color(0, 149, 246);
	private final Color foregroundOverPublicar = new Color(0, 55, 107);

	private String fotoPerfil;
	private String nickname;
	private int likes;
	private LocalDateTime fecha;

	private Set<Comentario> comentarios;
	private JList<Comentario> lista;
	private DefaultListModel<Comentario> model;

	private PanelIconos panelIconos;
	private JTextArea areaComentario;
	private JLabel lblPublicar;
	
	private Publicacion publicacion;

	public PanelComentarios(String rutaFotoPerfil, String nickname, Publicacion publicacion) {
		this.publicacion = publicacion;
		this.fotoPerfil = rutaFotoPerfil;
		this.nickname = nickname;

		this.comentarios = publicacion.getComentarios();
		this.model = new DefaultListModel<Comentario>();

		// Añadir los comentarios al modelo
		cambiarModelo();
		this.lista = new JList<Comentario>(model);
		this.lista.setCellRenderer(new ListRendererComentario());

		inicializar();
	}

	public void cambiarFoto(Publicacion publicacion) {
		this.publicacion = publicacion;
		this.comentarios = publicacion.getComentarios();
		cambiarModelo();
		areaComentario.setText("");
		panelIconos.cambiarFoto(publicacion);
		// revalidate();
		// repaint();

	}

	private void inicializar() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 60, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel panelSuperior = new PanelCeldaComentario(fotoPerfil, nickname, null);
		GridBagConstraints gbc_panelSuperior = new GridBagConstraints();
		gbc_panelSuperior.insets = new Insets(0, 0, 5, 0);
		gbc_panelSuperior.fill = GridBagConstraints.BOTH;
		gbc_panelSuperior.gridx = 0;
		gbc_panelSuperior.gridy = 0;
		add(panelSuperior, gbc_panelSuperior);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		add(separator, gbc_separator);

		this.lista.setOpaque(false);
		JScrollPane panelScrollComentarios = new JScrollPane(lista, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// panelScrollComentarios.setOpaque(false);
		GridBagConstraints gbc_panelScrollComentarios = new GridBagConstraints();
		gbc_panelScrollComentarios.insets = new Insets(0, 0, 5, 0);
		gbc_panelScrollComentarios.fill = GridBagConstraints.BOTH;
		gbc_panelScrollComentarios.gridx = 0;
		gbc_panelScrollComentarios.gridy = 2;
		add(panelScrollComentarios, gbc_panelScrollComentarios);

		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.BOTH;
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 3;
		add(separator_1, gbc_separator_1);

		panelIconos = new PanelIconos(publicacion);
		GridBagConstraints gbc_panelIconos = new GridBagConstraints();
		gbc_panelIconos.insets = new Insets(0, 0, 5, 0);
		gbc_panelIconos.fill = GridBagConstraints.BOTH;
		gbc_panelIconos.gridx = 0;
		gbc_panelIconos.gridy = 4;
		add(panelIconos, gbc_panelIconos);

		JSeparator separator_2 = new JSeparator();
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.fill = GridBagConstraints.BOTH;
		gbc_separator_2.insets = new Insets(0, 0, 5, 0);
		gbc_separator_2.gridx = 0;
		gbc_separator_2.gridy = 5;
		add(separator_2, gbc_separator_2);

		JPanel panelAreaComentario = new JPanel();
		GridBagConstraints gbc_panelAreaComentario = new GridBagConstraints();
		gbc_panelAreaComentario.fill = GridBagConstraints.BOTH;
		gbc_panelAreaComentario.gridx = 0;
		gbc_panelAreaComentario.gridy = 6;
		add(panelAreaComentario, gbc_panelAreaComentario);
		panelAreaComentario.setLayout(new BoxLayout(panelAreaComentario, BoxLayout.X_AXIS));

		Component rigidArea = Box.createRigidArea(new Dimension(40, 20));
		panelAreaComentario.add(rigidArea);

		areaComentario = new JTextArea();
		areaComentario.setForeground(Color.WHITE);
		areaComentario.setFont(new Font("Monospaced", Font.PLAIN, 14));
		areaComentario.setLineWrap(true);
		areaComentario.setWrapStyleWord(true);
		areaComentario.setOpaque(false);

		// Comprobar si el JTextArea tiene texto en el
		areaComentario.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkJTextArea();

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				checkJTextArea();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkJTextArea();

			}
		});

		JScrollPane scrollPane = new JScrollPane(areaComentario);
		panelAreaComentario.add(scrollPane);
		scrollPane.setViewportView(areaComentario);

		lblPublicar = new JLabel("  Publicar  ");
		lblPublicar.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPublicar.setForeground(foregroundPublicar);
		lblPublicar.setEnabled(false);
		panelAreaComentario.add(lblPublicar);

		lblPublicar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblPublicar.setForeground(foregroundOverPublicar);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblPublicar.setForeground(foregroundPublicar);
			}
		});
	}

	private void cambiarModelo() {
		if (!model.isEmpty())
			model.clear();

		comentarios.stream().forEach((Comentario c) -> model.addElement(c));
	}

	private void checkJTextArea() {
		if (areaComentario.getText().isEmpty())
			lblPublicar.setEnabled(false);
		else
			lblPublicar.setEnabled(true);
	}

}
