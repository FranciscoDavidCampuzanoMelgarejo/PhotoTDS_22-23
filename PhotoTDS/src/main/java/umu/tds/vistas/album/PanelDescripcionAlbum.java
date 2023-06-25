package umu.tds.vistas.album;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import umu.tds.controlador.Controlador;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;

public class PanelDescripcionAlbum extends JPanel {

	private static final int MAXIMO_FOTOS = 16;
	private static final int ANCHO_ICONO = 30;

	private Image iconoError, iconoAcierto;
	private JTextField campoTitulo;
	private JTextArea areaDescripcion, areaComentario;
	private JLabel lblTituloError;

	private int contadorFotos;
	private PanelFotosAlbum panelFotos; // Panel de la derecha del dialogo

	private List<String> rutasFotos;
	private List<String> hashtags;
	

	public PanelDescripcionAlbum(JPanel panelFotos) {
		this.panelFotos = (PanelFotosAlbum) panelFotos;
		this.contadorFotos = 0;
		this.iconoError = new ImageIcon(getClass().getResource("/imagenes/cross-icon.png")).getImage()
				.getScaledInstance(ANCHO_ICONO, ANCHO_ICONO, Image.SCALE_SMOOTH);
		this.iconoAcierto = new ImageIcon(getClass().getResource("/imagenes/check-mark.png")).getImage()
				.getScaledInstance(ANCHO_ICONO, ANCHO_ICONO, Image.SCALE_SMOOTH);
		this.hashtags = new ArrayList<String>(4);
		this.rutasFotos = new ArrayList<String>(MAXIMO_FOTOS);
		inicializar();
	}

	private void inicializar() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 40, 0, 0, 40, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		setBorder(new LineBorder(Color.gray, 1));

		JPanel panelTitulo = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelTitulo.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		GridBagConstraints gbc_panelTitulo = new GridBagConstraints();
		gbc_panelTitulo.anchor = GridBagConstraints.WEST;
		gbc_panelTitulo.gridwidth = 2;
		gbc_panelTitulo.insets = new Insets(0, 0, 5, 5);
		gbc_panelTitulo.gridx = 1;
		gbc_panelTitulo.gridy = 1;
		add(panelTitulo, gbc_panelTitulo);

		JLabel lblTitulo = new JLabel("Título");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		panelTitulo.add(lblTitulo);

		lblTituloError = new JLabel("  -  Este campo es obligatorio ");
		lblTituloError.setForeground(new Color(255, 106, 106));
		lblTituloError.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblTituloError.setVisible(false);
		panelTitulo.add(lblTituloError);

		campoTitulo = new JTextField();
		GridBagConstraints gbc_campoTitulo = new GridBagConstraints();
		gbc_campoTitulo.gridwidth = 2;
		gbc_campoTitulo.anchor = GridBagConstraints.WEST;
		gbc_campoTitulo.insets = new Insets(0, 0, 25, 5);
		gbc_campoTitulo.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoTitulo.gridx = 1;
		gbc_campoTitulo.gridy = 2;
		add(campoTitulo, gbc_campoTitulo);
		campoTitulo.setColumns(30);

		JPanel panelDescripcion = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelDescripcion.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelDescripcion = new GridBagConstraints();
		gbc_panelDescripcion.gridwidth = 2;
		gbc_panelDescripcion.anchor = GridBagConstraints.WEST;
		gbc_panelDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_panelDescripcion.gridx = 1;
		gbc_panelDescripcion.gridy = 3;
		add(panelDescripcion, gbc_panelDescripcion);

		JLabel lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelDescripcion.add(lblDescripcion);

		JLabel lblDescripcionError = new JLabel("  -  Este campo es obligatorio ");
		lblDescripcionError.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblDescripcionError.setForeground(new Color(255, 106, 106));
		lblDescripcionError.setVisible(false);
		panelDescripcion.add(lblDescripcionError);

		JScrollPane scrollDescripcion = new JScrollPane();
		scrollDescripcion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollDescripcion = new GridBagConstraints();
		gbc_scrollDescripcion.gridwidth = 2;
		gbc_scrollDescripcion.insets = new Insets(0, 0, 25, 5);
		gbc_scrollDescripcion.fill = GridBagConstraints.BOTH;
		gbc_scrollDescripcion.gridx = 1;
		gbc_scrollDescripcion.gridy = 4;
		add(scrollDescripcion, gbc_scrollDescripcion);

		areaDescripcion = new JTextArea();
		areaDescripcion.setFont(new Font("Monospaced", Font.PLAIN, 14));
		areaDescripcion.setLineWrap(true);
		areaDescripcion.setWrapStyleWord(true);
		areaDescripcion.setRows(3);
		areaDescripcion.setColumns(30);
		scrollDescripcion.setViewportView(areaDescripcion);
		
		JPanel panelComentario = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelComentario.getLayout();
		flowLayout_2.setVgap(0);
		flowLayout_2.setHgap(0);
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelComentario = new GridBagConstraints();
		gbc_panelComentario.gridwidth = 2;
		gbc_panelComentario.anchor = GridBagConstraints.WEST;
		gbc_panelComentario.insets = new Insets(0, 0, 5, 5);
		gbc_panelComentario.fill = GridBagConstraints.BOTH;
		gbc_panelComentario.gridx = 1;
		gbc_panelComentario.gridy = 5;
		add(panelComentario, gbc_panelComentario);
		
		JLabel lblComentario = new JLabel("Comentario");
		lblComentario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelComentario.add(lblComentario);
		
		JLabel lblComentarioError = new JLabel("  -  Los hashtags introducidos son erróneos ");
		lblComentarioError.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblComentarioError.setForeground(new Color(255, 106, 106));
		lblComentarioError.setVisible(false);
		panelComentario.add(lblComentarioError);

		JScrollPane scrollComentario = new JScrollPane();
		GridBagConstraints gbc_scrollComentario = new GridBagConstraints();
		gbc_scrollComentario.gridwidth = 2;
		gbc_scrollComentario.insets = new Insets(0, 0, 20, 5);
		gbc_scrollComentario.fill = GridBagConstraints.BOTH;
		gbc_scrollComentario.gridx = 1;
		gbc_scrollComentario.gridy = 6;
		add(scrollComentario, gbc_scrollComentario);

		areaComentario = new JTextArea();
		areaComentario.setFont(new Font("Monospaced", Font.PLAIN, 14));
		areaComentario.setWrapStyleWord(true);
		areaComentario.setLineWrap(true);
		areaComentario.setRows(3);
		areaComentario.setColumns(30);
		scrollComentario.setViewportView(areaComentario);

		JButton btnAddFoto = new JButton("Añadir Foto");
		btnAddFoto.setBackground(new Color(0xb2, 0x00, 0x9c));
		btnAddFoto.setFont(new Font("Tahoma", Font.ITALIC, 16));
		GridBagConstraints gbc_btnAddFoto = new GridBagConstraints();
		gbc_btnAddFoto.anchor = GridBagConstraints.WEST;
		gbc_btnAddFoto.ipadx = 4;
		gbc_btnAddFoto.ipady = 4;
		gbc_btnAddFoto.insets = new Insets(0, 0, 10, 5);
		gbc_btnAddFoto.gridx = 1;
		gbc_btnAddFoto.gridy = 7;
		add(btnAddFoto, gbc_btnAddFoto);

		btnAddFoto.addActionListener((ActionEvent e) -> {
			System.out.println("Añadir Foto");
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// fileChooser.setFileFilter(new FileNameExtensionFilter("Imagenes", "png, jpg,
			// jpeg"));

			int result = fileChooser.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) {
				File fichero = fileChooser.getSelectedFile();
				panelFotos.setFoto(fichero.getAbsolutePath());
				contadorFotos++;
				rutasFotos.add(fichero.getAbsolutePath());
			}

			if (contadorFotos == MAXIMO_FOTOS) {
				btnAddFoto.setEnabled(false);
			}
		});

		JButton btnCrearAlbum = new JButton("Crear Album");
		btnCrearAlbum.setBackground(new Color(0xb2, 0x00, 0x9c));
		btnCrearAlbum.setFont(new Font("Tahoma", Font.ITALIC, 16));
		GridBagConstraints gbc_btnCrearAlbum = new GridBagConstraints();
		gbc_btnCrearAlbum.anchor = GridBagConstraints.EAST;
		gbc_btnCrearAlbum.ipady = 4;
		gbc_btnCrearAlbum.ipadx = 4;
		gbc_btnCrearAlbum.insets = new Insets(0, 0, 10, 5);
		gbc_btnCrearAlbum.gridx = 2;
		gbc_btnCrearAlbum.gridy = 7;
		add(btnCrearAlbum, gbc_btnCrearAlbum);

		btnCrearAlbum.addActionListener((ActionEvent e) -> {
			lblTituloError.setVisible(false);
			lblDescripcionError.setVisible(false);
			lblComentarioError.setVisible(false);

			boolean camposCorrectos = true;
			String titulo = campoTitulo.getText();
			String descripcion = areaDescripcion.getText();
			String comentario = areaComentario.getText();

			if (titulo == null || titulo.equals("")) {
				camposCorrectos = false;
				lblTituloError.setVisible(true);
			}

			if (descripcion == null || descripcion.equals("")) {
				camposCorrectos = false;
				lblDescripcionError.setVisible(true);
			}
			
			if(comentario != null && !validarHashtags(comentario)) {
				System.out.println("Error hashtags");
				camposCorrectos = false;
				lblComentarioError.setVisible(true);
			}

			if (contadorFotos == 0) {
				JOptionPane.showMessageDialog(null, "No se ha añadido ninguna foto al album", "Album sin fotos",
						JOptionPane.ERROR_MESSAGE, new ImageIcon(iconoError));
			}

			if (contadorFotos > 0 && camposCorrectos) {
				
				Controlador.getControlador().publicarAlbum(titulo, descripcion, comentario, rutasFotos, hashtags);
				String mensaje = "Album creado con " + contadorFotos + " fotos";
				JOptionPane.showMessageDialog(null, mensaje, "Album creado", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(iconoAcierto));

				JDialog dialogoPadre = (JDialog) SwingUtilities.getWindowAncestor(PanelDescripcionAlbum.this);
				dialogoPadre.dispose();

			}

		});

	}

	// Metodos privados auxiliares

	// Metodo que comprueba los hashtags del comentario y si son validos. Si hay
	// algun hashtag invalido devuelve false
	private boolean validarHashtags(String texto) {
		System.out.println("Valida hashtagas");
		int contador = 0;
		//texto = texto.replaceAll("\\s+", "");
		System.out.println(texto);
		for(int i = 0; i < texto.length(); i++) {
			char car = texto.charAt(i);
			
			if(car == '#') {
				contador++;
				int longitud = obtenerLongitudHashtag(texto, i);
				
				if(contador > 4 || longitud > 15) {
					return false;
				}
				
				hashtags.add(texto.substring(i, (i + 1) + longitud));
				i += longitud;
			}
		}
		System.out.println(hashtags);
		return true;
	}
	
	private int obtenerLongitudHashtag(String texto, int indice) {
		int longitud = 0;
		for(int i = indice + 1; i < texto.length(); i++) {
			char car = texto.charAt(i);
			if(Character.isWhitespace(car) || car == '#')
				break;
			longitud++;
		}
		return longitud;
	}

}
