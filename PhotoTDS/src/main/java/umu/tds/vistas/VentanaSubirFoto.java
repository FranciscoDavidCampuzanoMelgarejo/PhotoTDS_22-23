package umu.tds.vistas;

import java.awt.EventQueue;

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
import java.util.List;

import javax.swing.JTextArea;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class VentanaSubirFoto {

	private JFrame frame;
	private CardLayout c1;				// Cardlayout de toda la ventana
	private CardLayout c2;				// Cardlayout del panel de selección de foto
	private File archivo;				// Archivo seleccionado
	JTextArea comentarioTextArea;		// Comentario de la foto
	
	private ImageIcon nuevapubli;		

	/* Constructor */
	public VentanaSubirFoto() {
		cargarRecursos();
		initialize();
	}
	
	public void mostrar() {
		frame.setVisible(true);
	}
	
	public void ocultar() {
		frame.setVisible(false);
	}
	
	public void cargarRecursos() {
		try {
			Image i = ImageIO.read(VentanaSubirFoto.class.getResource("/imagenes/nuevapubli.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
			nuevapubli = new ImageIcon(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getComentario() {
		return comentarioTextArea.getText();
	}
	
	/* Dibujado de ventana */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Nueva publicación");
		frame.setResizable(false);
		frame.setBounds(100, 100, 512, 480);
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
		
		botonFilechooser.setBackground(new Color(24, 84, 215));
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
					archivo = archivos.get(0);
					fotoElegida.setIcon(new ImageIcon(archivo.toString()));
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
				archivo = chooser.getSelectedFile();
				fotoElegida.setIcon(new ImageIcon(archivo.toString()));
				c2.show(paneldndfoto, "fotoElegida");
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
					archivo = archivos.get(0);
					fotoElegida.setIcon(new ImageIcon(archivo.toString()));
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
				frame.setBounds(frame.getX(), frame.getY(), frame.getWidth(), 640);
				vistaPreviaLabel.setIcon((ImageIcon) fotoElegida.getIcon());
				c1.show(frame.getContentPane(), "panelNuevaPublicacion");
			}
		});
		botonContinuar.setFont(new Font("Tahoma", Font.ITALIC, 10));
		botonContinuar.setBackground(new Color(24, 84, 215));
		panelBotonContinuar.add(botonContinuar);
		
		
		JPanel panelNuevaPublicacion = new JPanel();
		frame.getContentPane().add(panelNuevaPublicacion, "panelNuevaPublicacion");
		panelNuevaPublicacion.setLayout(new BorderLayout(0, 0));
		
		JPanel panelBotonPublicar = new JPanel();
		panelNuevaPublicacion.add(panelBotonPublicar, BorderLayout.SOUTH);
		panelBotonPublicar.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton botonPublicacion = new JButton("Publicar");
		botonPublicacion.setBackground(new Color(24, 84, 215));
		botonPublicacion.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		panelBotonPublicar.add(botonPublicacion);
		
		JPanel panelPublicacion = new JPanel();
		panelNuevaPublicacion.add(panelPublicacion, BorderLayout.CENTER);
		GridBagLayout gbl_panelPublicacion = new GridBagLayout();
		gbl_panelPublicacion.columnWidths = new int[]{0, 0};
		gbl_panelPublicacion.rowHeights = new int[]{1, 0, 0, 0};
		gbl_panelPublicacion.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelPublicacion.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		panelPublicacion.setLayout(gbl_panelPublicacion);
		
		
		GridBagConstraints gbc_vistaPreviaLabel = new GridBagConstraints();
		gbc_vistaPreviaLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_vistaPreviaLabel.insets = new Insets(0, 0, 10, 0);
		gbc_vistaPreviaLabel.gridx = 0;
		gbc_vistaPreviaLabel.gridy = 0;
		panelPublicacion.add(vistaPreviaLabel, gbc_vistaPreviaLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Añade un comentario a tu foto");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 16));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 10, 5, 0);
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panelPublicacion.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 10, 0, 10);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		panelPublicacion.add(scrollPane, gbc_scrollPane);
		
		comentarioTextArea = new JTextArea();
		comentarioTextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPane.setViewportView(comentarioTextArea);
		
	}

}
