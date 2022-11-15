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

public class VentanaSubirFoto {

	private JFrame frame;
	private CardLayout c1;				// Cardlayout de toda la ventana
	private CardLayout c2;				// Cardlayout del panel de selección de foto
	private File archivo;
	private ImageIcon vistaPrevia;		// Vista previa de la foto de la publicación
	private boolean fotoSubida;
	
	private ImageIcon nuevapubli;

	/* ELIMINAR AL TERMINAR VENTANA */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaSubirFoto window = new VentanaSubirFoto();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/* Constructor */
	public VentanaSubirFoto() {
		fotoSubida = false;
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
	
	public void recargaVistaPrevia(JLabel j, Image i) {
		try{
			j.setIcon(new ImageIcon(i));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				vistaPrevia = new ImageIcon(archivo.toString());
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
		
		JButton btnNewButton = new JButton("Continuar con esta foto");
		btnNewButton.setFont(new Font("Tahoma", Font.ITALIC, 10));
		btnNewButton.setBackground(new Color(24, 84, 215));
		panelBotonContinuar.add(btnNewButton);
		
		
		JPanel panelNuevaPublicacion = new JPanel();
		frame.getContentPane().add(panelNuevaPublicacion, "panelNuevaPublicacion");
		
	}

}
