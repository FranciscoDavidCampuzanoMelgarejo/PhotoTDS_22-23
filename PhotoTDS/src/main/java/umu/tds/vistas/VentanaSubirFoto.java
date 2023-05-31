package umu.tds.vistas;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;	
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextArea;
import java.awt.CardLayout;
import javax.swing.SwingConstants;

import umu.tds.controlador.Controlador;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.util.regex.*;
import javax.swing.JTextField;

public class VentanaSubirFoto {
	
	private static VentanaSubirFoto instancia = null;
	
	private JFrame frame;
	private CardLayout c1;				// Cardlayout de toda la ventana
	private CardLayout c2;				// Cardlayout del panel de selección de foto
	private Image fotoPubli;			// Imagen tal cual el archivo
	private File filePubli;
	
	JLabel labelTitulo;
	JLabel labelDescripcion;
	JLabel labelComentario;
	
	JTextArea textoTitulo;
	JTextArea textoDescripcion;
	JTextArea textoComentario;
	
	private ImageIcon nuevapubli;	
	
	private Pattern patronEtiqueta;
	
	private List<ActionListener> listeners;

	/* Constructor */
	private VentanaSubirFoto() {
		cargarRecursos();
		initialize();
	}
	
	/* En este caso, queremos que solo haya una instancia pero que cada vez que se pida se cree una nueva */
	private void destruir() {
		frame.dispose();
		instancia = null;
	}
	
	public static VentanaSubirFoto getInstancia() {
		if(instancia==null) instancia = new VentanaSubirFoto();
		return instancia;
	}
	
	public void addActionListener(ActionListener a) {
		if(listeners==null) listeners = new LinkedList<ActionListener>();
		listeners.add(a);
	}

	public void notificacionSubirFoto(ActionEvent e) {
		if(listeners!=null) listeners.stream().forEach(l -> l.actionPerformed(e));
	}
	
	
	/* Mostrar ventana */
	public void mostrar() {
		frame.setVisible(true);
	}
	
	/* Ocultar ventana */
	public void ocultar() {
		frame.setVisible(false);
	}
	
	
	/* Cargar recursos de la ventana */
	public void cargarRecursos() {
		try {
			Image i = ImageIO.read(VentanaSubirFoto.class.getResource("/imagenes/nuevapubli.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
			nuevapubli = new ImageIcon(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Getter del comentario de la publicación */
	public String getComentario() {
		return textoComentario.getText();
	}

	
	/* Dibujado de ventana */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Nueva publicación");
		frame.setResizable(false);
		frame.setBounds(100, 100, 512, 512);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		c1 = (CardLayout) frame.getContentPane().getLayout();
		
		JPanel panelDropFoto = new JPanel();
		frame.getContentPane().add(panelDropFoto, "panelDropFoto");
		panelDropFoto.setLayout(new BorderLayout(0, 0));
		
		JPanel iconoDropFoto = new JPanel();
		panelDropFoto.add(iconoDropFoto, BorderLayout.NORTH);
		GridBagLayout gbl_iconoDropFoto = new GridBagLayout();
		gbl_iconoDropFoto.columnWidths = new int[]{0, 0};
		gbl_iconoDropFoto.rowHeights = new int[]{80, 10, 10, 0};
		gbl_iconoDropFoto.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_iconoDropFoto.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		iconoDropFoto.setLayout(gbl_iconoDropFoto);
		
		JLabel nuevapubliicon = new JLabel("");
		GridBagConstraints gbc_nuevapubliicon = new GridBagConstraints();
		gbc_nuevapubliicon.insets = new Insets(0, 0, 10, 0);
		gbc_nuevapubliicon.gridx = 0;
		gbc_nuevapubliicon.gridy = 0;
		nuevapubliicon.setIcon(nuevapubli);
		iconoDropFoto.add(nuevapubliicon, gbc_nuevapubliicon);
		
		JButton botonFilechooser = new JButton("Elegir foto");
		
		botonFilechooser.setBackground(new Color(0xb2, 0x00, 0x9c));
		botonFilechooser.setFont(new Font("Tahoma", Font.ITALIC, 17));
		GridBagConstraints gbc_botonFilechooser = new GridBagConstraints();
		gbc_botonFilechooser.insets = new Insets(0, 0, 5, 0);
		gbc_botonFilechooser.gridx = 0;
		gbc_botonFilechooser.gridy = 1;
		iconoDropFoto.add(botonFilechooser, gbc_botonFilechooser);
		
		JLabel lblNewLabel = new JLabel("O bien arrástrala aquí abajo");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		iconoDropFoto.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel paneldndfoto = new JPanel();
		panelDropFoto.add(paneldndfoto, BorderLayout.CENTER);
		paneldndfoto.setLayout(new CardLayout(0, 0));
		c2 = (CardLayout) paneldndfoto.getLayout();
		
		JPanel dndFoto = new JPanel();
		paneldndfoto.add(dndFoto, "dndFoto");
		dndFoto.setLayout(new BorderLayout(0, 0));
		
		// Al seleccionar una nueva foto, se actualiza la vista previa
		JLabel fotoElegida = new JLabel();
		fotoElegida.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Podemos obtener la foto por FileChooser o por el drag and drop
		dndFoto.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent devent) {
				try {
					devent.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> archivos = (List<File>) devent.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					filePubli = archivos.get(0);
					fotoPubli = ImageIO.read(archivos.get(0));
					
					float iw = fotoPubli.getWidth(null), ih = fotoPubli.getHeight(null), s = 1.0f;
					s = (iw>ih) ? (384/iw) : (384/ih);
					fotoElegida.setIcon(new ImageIcon(fotoPubli.getScaledInstance((int)(iw*s), (int)(ih*s), Image.SCALE_SMOOTH)));
					c2.show(paneldndfoto, "fotoElegida");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		botonFilechooser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.showOpenDialog(botonFilechooser);
				
				try {
					filePubli = chooser.getSelectedFile();
					fotoPubli = ImageIO.read(filePubli);
				} catch (Exception e1) { e1.printStackTrace(); }
				 
				if(fotoPubli!=null) { 
					float iw = fotoPubli.getWidth(null), ih = fotoPubli.getHeight(null), s = 1.0f;
					s = (iw>ih) ? (384/iw) : (384/ih);
					fotoElegida.setIcon(new ImageIcon(fotoPubli.getScaledInstance((int)(iw*s), (int)(ih*s), Image.SCALE_SMOOTH)));
					c2.show(paneldndfoto, "fotoElegida");
				}
				
			}
		});
		
		JPanel panelfotoElegida = new JPanel();
		paneldndfoto.add(panelfotoElegida, "fotoElegida");
		panelfotoElegida.setLayout(new BorderLayout(0, 0));
		panelfotoElegida.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent devent) {
				try {
					devent.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> archivos = (List<File>) devent.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					filePubli = archivos.get(0);
					fotoPubli = ImageIO.read(filePubli);
					
					float iw = fotoPubli.getWidth(null), ih = fotoPubli.getHeight(null), s = 1.0f;
					s = (iw>ih) ? (384/iw) : (384/ih);
					if(fotoPubli!=null) fotoElegida.setIcon(new ImageIcon(fotoPubli.getScaledInstance((int)(iw*s), (int)(ih*s), Image.SCALE_SMOOTH)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// Añadimos la foto una vez ya se ha elegido
		panelfotoElegida.add(fotoElegida, BorderLayout.CENTER);
			
		JPanel panelBotonContinuar = new JPanel();
		panelfotoElegida.add(panelBotonContinuar, BorderLayout.SOUTH);
		
		// Donde se muestra la vista previa
		JLabel vistaPreviaLabel = new JLabel("");
		vistaPreviaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton botonContinuar = new JButton("Continuar con esta foto");
		botonContinuar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setBounds(frame.getX(), frame.getY(), frame.getWidth(), 768);
				
				/*
				float nw = fotoPubli.getWidth(null), nh = fotoPubli.getHeight(null);
				
				if(fotoPubli.getWidth(null)>=480) nw = (float) (fotoPubli.getWidth(null) * 0.5);
				if(fotoPubli.getHeight(null)>=360) nh = (float) (fotoPubli.getHeight(null) * 0.5);
				
				vistaPreviaLabel.setIcon(new ImageIcon(fotoPubli.getScaledInstance((int)nw, (int)nh, Image.SCALE_SMOOTH)));
				c1.show(frame.getContentPane(), "panelNuevaPublicacion");
				*/
				
				// Escalamos hasta que la dimensión coincida con el tamaño de la imagen de preview
				float iw = fotoPubli.getWidth(null), ih = fotoPubli.getHeight(null), s = 1.0f;
				s = (iw>ih) ? (512/iw) : (512/ih);
				
				vistaPreviaLabel.setIcon(new ImageIcon(fotoPubli.getScaledInstance((int)(iw*s), (int)(ih*s), Image.SCALE_SMOOTH)));
				
				c1.show(frame.getContentPane(), "panelNuevaPublicacion");
			}
		});
		botonContinuar.setFont(new Font("Tahoma", Font.ITALIC, 10));
		botonContinuar.setBackground(new Color(0xb2, 0x00, 0x9c));
		panelBotonContinuar.add(botonContinuar);
		
					
		JPanel panelNuevaPublicacion = new JPanel();
		frame.getContentPane().add(panelNuevaPublicacion, "panelNuevaPublicacion");
		panelNuevaPublicacion.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBotonPublicar = new JPanel();
		panelNuevaPublicacion.add(panelBotonPublicar, BorderLayout.SOUTH);
		panelBotonPublicar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton botonPublicacion = new JButton("Publicar");
		botonPublicacion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textoTitulo.getText().isEmpty() || textoDescripcion.getText().isEmpty()) {	// No dejamos que se haga la publicación
					if(textoTitulo.getText().isEmpty()) { labelTitulo.setText("Titulo - campo obligatorio"); labelTitulo.setForeground(Color.red); }
					if(textoDescripcion.getText().isEmpty()) { labelDescripcion.setText("Descripcion - campo obligatorio"); labelDescripcion.setForeground(Color.red); }
				} else {																		// Hacemos la publicación
					Controlador.getControlador().publicarFoto(filePubli.getAbsolutePath(), textoTitulo.getText(), textoDescripcion.getText(), textoComentario.getText(), null);
					//notificacionSubirFoto(new ActionEvent(this, 4, "fotoSubida"));
					destruir();
				}
			}
		});
		botonPublicacion.setBackground(new Color(0xb2, 0x00, 0x9c));
		botonPublicacion.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		panelBotonPublicar.add(botonPublicacion);
		
		
		// PANEL PUBLICACION
		JPanel panelPublicacion = new JPanel();
		panelNuevaPublicacion.add(panelPublicacion, BorderLayout.CENTER);
		GridBagLayout gbl_panelPublicacion = new GridBagLayout();
		gbl_panelPublicacion.columnWidths = new int[]{0, 0};
		gbl_panelPublicacion.rowHeights = new int[]{512, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelPublicacion.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelPublicacion.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		panelPublicacion.setLayout(gbl_panelPublicacion);
		
		
		GridBagConstraints gbc_vistaPreviaLabel = new GridBagConstraints();
		gbc_vistaPreviaLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_vistaPreviaLabel.insets = new Insets(0, 0, 10, 0);
		gbc_vistaPreviaLabel.gridx = 0;
		gbc_vistaPreviaLabel.gridy = 0;
		panelPublicacion.add(vistaPreviaLabel, gbc_vistaPreviaLabel);
		
		labelTitulo = new JLabel("Título");
		labelTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		labelTitulo.setFont(new Font("Tahoma", Font.ITALIC, 14));
		GridBagConstraints gbc_labelTitulo = new GridBagConstraints();
		gbc_labelTitulo.insets = new Insets(0, 10, 5, 0);
		gbc_labelTitulo.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelTitulo.gridx = 0;
		gbc_labelTitulo.gridy = 1;
		panelPublicacion.add(labelTitulo, gbc_labelTitulo);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 10, 5, 10);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 2;
		panelPublicacion.add(scrollPane_1, gbc_scrollPane_1);
		
		textoTitulo = new JTextArea();
		textoTitulo.setLineWrap(true);
		scrollPane_1.setViewportView(textoTitulo);
		
		labelDescripcion = new JLabel("Descripción");
		labelDescripcion.setFont(new Font("Tahoma", Font.ITALIC, 14));
		GridBagConstraints gbc_labelDescripcion = new GridBagConstraints();
		gbc_labelDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelDescripcion.insets = new Insets(0, 10, 5, 0);
		gbc_labelDescripcion.anchor = GridBagConstraints.WEST;
		gbc_labelDescripcion.gridx = 0;
		gbc_labelDescripcion.gridy = 3;
		panelPublicacion.add(labelDescripcion, gbc_labelDescripcion);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.insets = new Insets(0, 10, 5, 10);
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 4;
		panelPublicacion.add(scrollPane_2, gbc_scrollPane_2);
		
		textoDescripcion = new JTextArea();
		textoDescripcion.setLineWrap(true);
		scrollPane_2.setViewportView(textoDescripcion);
		
		labelComentario = new JLabel("Comentario (opcional)");
		labelComentario.setFont(new Font("Tahoma", Font.ITALIC, 14));
		GridBagConstraints gbc_labelComentario = new GridBagConstraints();
		gbc_labelComentario.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelComentario.anchor = GridBagConstraints.WEST;
		gbc_labelComentario.insets = new Insets(0, 10, 5, 0);
		gbc_labelComentario.gridx = 0;
		gbc_labelComentario.gridy = 5;
		panelPublicacion.add(labelComentario, gbc_labelComentario);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 10, 0, 10);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 6;
		panelPublicacion.add(scrollPane, gbc_scrollPane);
		
		textoComentario = new JTextArea();
		textoComentario.setLineWrap(true);
		textoComentario.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane.setViewportView(textoComentario);
		
	}

}
