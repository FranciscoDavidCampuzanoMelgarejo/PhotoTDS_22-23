package umu.tds.vistas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.pojos.Descuento;
import umu.tds.modelo.pojos.DescuentoEdad;
import umu.tds.modelo.pojos.DescuentoLikes;
import umu.tds.modelo.pojos.Usuario;
import umu.tds.vistas.premium.VentanaTopFotos;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionListener;

/**
 * @title Ventana de Premium
 * @author diego
 * */
public class VentanaPremium {
	private static VentanaPremium instancia = null;
	private JFrame frame;
	private JLabel precioLabel, fotoPDF, fotoExcel, fotoTopFotos;
	
	private Usuario usuario;
	private List<ActionListener> listeners;
	private List<Descuento> descuentos;
	
	private CardLayout c;
	
	private BufferedImage premiumicon, nopremiumicon, yapremium, haztepremium, pdf, excel, topfotos;
	private float precioBase = 10.0f, precio = precioBase;

	/* Listener para la ventana principal */
	public void addActionListener(ActionListener a) {
		if(listeners==null) listeners = new LinkedList<ActionListener>();
		listeners.add(a);
	}
	
	/* Al aceptar o anular el premium, se tiene que notificar a la ventana principal */
	public void notificacionPremium(ActionEvent a) {
		if(listeners!=null) listeners.stream().forEach(l -> l.actionPerformed(a));
	}

	/* Cargar recursos gráficos */
	private void cargarRecursos() {
		try { 
			premiumicon = ImageIO.read(getClass().getResource("/imagenes/dock/premium.png"));
			nopremiumicon = ImageIO.read(getClass().getResource("/imagenes/dock/nopremium.png"));
			yapremium = ImageIO.read(getClass().getResource("/imagenes/premium/yapremium.png"));
			haztepremium = ImageIO.read(getClass().getResource("/imagenes/premium/haztepremium.png"));
			pdf = ImageIO.read(getClass().getResource("/imagenes/premium/pdf.png"));
			excel = ImageIO.read(getClass().getResource("/imagenes/premium/xlsx.png"));
			topfotos = ImageIO.read(getClass().getResource("/imagenes/premium/topfotos.png"));
		} catch (IOException ioe) { System.err.println("ERR cargando recursos"); System.exit(-1); }
	}
	
	/* Constructor de la instancia */
	private VentanaPremium() {
		usuario = Controlador.getControlador().getUsuarioLogueado();
		descuentos = Controlador.getControlador().getDescuentos();
		cargarRecursos();
		dibujar();
		if(usuario.getPremium()) c.show(frame.getContentPane(), "siPremium");
		else c.show(frame.getContentPane(), "noPremium");
	}

	/* Dibujado de la ventana */
	private void dibujar() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(100, 100, 480, 540);
		if(usuario.getPremium()) {
			frame.setTitle("Premium");
			frame.setIconImage(premiumicon);
		}
		else {
			frame.setTitle("Hazte premium");
			frame.setIconImage(nopremiumicon);
		}
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		c = (CardLayout) frame.getContentPane().getLayout();
		 
		JPanel noPremium = new JPanel() {
			private static final long serialVersionUID = 1L;
			{setOpaque(false);} 
		};
		frame.getContentPane().add(noPremium, "noPremium");
		GridBagLayout gbl_noPremium = new GridBagLayout();
		gbl_noPremium.columnWidths = new int[]{64, 0, 64, 0};
		gbl_noPremium.rowHeights = new int[]{220, 0, 0};
		gbl_noPremium.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_noPremium.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		noPremium.setLayout(gbl_noPremium);
		
		JPanel panelImagen1 = new JPanel();
		GridBagConstraints gbc_panelImagen1 = new GridBagConstraints();
		gbc_panelImagen1.insets = new Insets(0, 0, 5, 5);
		gbc_panelImagen1.fill = GridBagConstraints.BOTH;
		gbc_panelImagen1.gridx = 1;
		gbc_panelImagen1.gridy = 0;
		noPremium.add(panelImagen1, gbc_panelImagen1);
		panelImagen1.setLayout(new BorderLayout(0, 0));
		
		JLabel noPremiumLabel = new JLabel("");
		noPremiumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noPremiumLabel.setIcon(new ImageIcon(haztepremium.getScaledInstance(186, 184, Image.SCALE_SMOOTH)));
		panelImagen1.add(noPremiumLabel, BorderLayout.CENTER);
		
		JPanel panelInfo1 = new JPanel();
		GridBagConstraints gbc_panelInfo1 = new GridBagConstraints();
		gbc_panelInfo1.insets = new Insets(0, 0, 0, 5);
		gbc_panelInfo1.fill = GridBagConstraints.BOTH;
		gbc_panelInfo1.gridx = 1;
		gbc_panelInfo1.gridy = 1;
		noPremium.add(panelInfo1, gbc_panelInfo1);
		GridBagLayout gbl_panelInfo1 = new GridBagLayout();
		gbl_panelInfo1.columnWidths = new int[]{0, 0};
		gbl_panelInfo1.rowHeights = new int[]{0, 144, 48, 32, 0};
		gbl_panelInfo1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelInfo1.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelInfo1.setLayout(gbl_panelInfo1);
		
		JLabel labelDescuento = new JLabel("Elige un descuento:");
		labelDescuento.setHorizontalAlignment(SwingConstants.LEFT);
		labelDescuento.setFont(new Font("Tahoma", Font.ITALIC, 14));
		GridBagConstraints gbc_labelDescuento = new GridBagConstraints();
		gbc_labelDescuento.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelDescuento.insets = new Insets(0, 0, 5, 0);
		gbc_labelDescuento.gridx = 0;
		gbc_labelDescuento.gridy = 0;
		panelInfo1.add(labelDescuento, gbc_labelDescuento);
		
		JPanel panelListaDescuentos = new JPanel();
		GridBagConstraints gbc_panelListaDescuentos = new GridBagConstraints();
		gbc_panelListaDescuentos.insets = new Insets(0, 0, 5, 0);
		gbc_panelListaDescuentos.fill = GridBagConstraints.BOTH;
		gbc_panelListaDescuentos.gridx = 0;
		gbc_panelListaDescuentos.gridy = 1;
		panelInfo1.add(panelListaDescuentos, gbc_panelListaDescuentos);
		panelListaDescuentos.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelListaDescuentos.add(scrollPane);
		
		DefaultListModel<Descuento> descuentoModel = new DefaultListModel<Descuento>();
			descuentoModel.addAll(descuentos);
		JList<Descuento> descuentosJList = new JList<Descuento>(descuentoModel);
		descuentosJList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				Descuento d = descuentosJList.getSelectedValue();
				precio = d.aplicar(precioBase);
				precioLabel.setText(precio + " €");
			}
		});
		
		
		
		scrollPane.setViewportView(descuentosJList);
		
		JPanel panelPrecio = new JPanel();
		GridBagConstraints gbc_panelPrecio = new GridBagConstraints();
		gbc_panelPrecio.insets = new Insets(0, 0, 5, 0);
		gbc_panelPrecio.fill = GridBagConstraints.BOTH;
		gbc_panelPrecio.gridx = 0;
		gbc_panelPrecio.gridy = 2;
		panelInfo1.add(panelPrecio, gbc_panelPrecio);
		panelPrecio.setLayout(new BorderLayout(0, 0));
		
		precioLabel = new JLabel(precio + " €");
		precioLabel.setHorizontalAlignment(SwingConstants.CENTER);
		precioLabel.setForeground(new Color(255, 255, 128));
		precioLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		panelPrecio.add(precioLabel);
		
		JPanel panelAltaPremium = new JPanel();
		GridBagConstraints gbc_panelHacersePremium = new GridBagConstraints();
		gbc_panelHacersePremium.fill = GridBagConstraints.BOTH;
		gbc_panelHacersePremium.gridx = 0;
		gbc_panelHacersePremium.gridy = 3;
		panelInfo1.add(panelAltaPremium, gbc_panelHacersePremium);
		panelAltaPremium.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnAltaPremium = new JButton("Hacerse premium");
		btnAltaPremium.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				notificacionPremium(new ActionEvent(this, 6, "cambioPremium"));
				destruir();
			}
		});
		btnAltaPremium.setBackground(new Color(0xb2, 0x00, 0x9c));
		btnAltaPremium.setForeground(new Color(0xff, 0xff, 0xff));
		btnAltaPremium.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		panelAltaPremium.add(btnAltaPremium);
		
		JPanel siPremium = new JPanel() {
			private static final long serialVersionUID = 1L;
			{setOpaque(false);} 
		};
		frame.getContentPane().add(siPremium, "siPremium");
		GridBagLayout gbl_siPremium = new GridBagLayout();
		gbl_siPremium.columnWidths = new int[]{48, 0, 48, 0};
		gbl_siPremium.rowHeights = new int[]{220, 0, 0};
		gbl_siPremium.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_siPremium.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		siPremium.setLayout(gbl_siPremium);
		
		JPanel panelImagen2 = new JPanel();
		GridBagConstraints gbc_panelImagen2 = new GridBagConstraints();
		gbc_panelImagen2.insets = new Insets(0, 0, 5, 5);
		gbc_panelImagen2.fill = GridBagConstraints.BOTH;
		gbc_panelImagen2.gridx = 1;
		gbc_panelImagen2.gridy = 0;
		siPremium.add(panelImagen2, gbc_panelImagen2);
		panelImagen2.setLayout(new BorderLayout(0, 0));
		
		JLabel premiumLabel = new JLabel("");
		premiumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		premiumLabel.setIcon(new ImageIcon(yapremium.getScaledInstance(190, 184, Image.SCALE_SMOOTH)));
		panelImagen2.add(premiumLabel, BorderLayout.CENTER);
		
		JPanel panelInfo2 = new JPanel();
		GridBagConstraints gbc_panelInfo2 = new GridBagConstraints();
		gbc_panelInfo2.insets = new Insets(0, 0, 0, 5);
		gbc_panelInfo2.fill = GridBagConstraints.BOTH;
		gbc_panelInfo2.gridx = 1;
		gbc_panelInfo2.gridy = 1;
		siPremium.add(panelInfo2, gbc_panelInfo2);
		GridBagLayout gbl_panelInfo2 = new GridBagLayout();
		gbl_panelInfo2.columnWidths = new int[]{0, 0};
		gbl_panelInfo2.rowHeights = new int[]{208, 0, 0};
		gbl_panelInfo2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelInfo2.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panelInfo2.setLayout(gbl_panelInfo2);
		
		JPanel panelFuncionesPremium = new JPanel();
		GridBagConstraints gbc_panelFuncionesPremium = new GridBagConstraints();
		gbc_panelFuncionesPremium.insets = new Insets(0, 0, 5, 0);
		gbc_panelFuncionesPremium.fill = GridBagConstraints.BOTH;
		gbc_panelFuncionesPremium.gridx = 0;
		gbc_panelFuncionesPremium.gridy = 0;
		panelInfo2.add(panelFuncionesPremium, gbc_panelFuncionesPremium);
		GridBagLayout gbl_panelFuncionesPremium = new GridBagLayout();
		gbl_panelFuncionesPremium.columnWidths = new int[]{128, 128, 128, 0};
		gbl_panelFuncionesPremium.rowHeights = new int[]{110, 0, 0};
		gbl_panelFuncionesPremium.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelFuncionesPremium.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelFuncionesPremium.setLayout(gbl_panelFuncionesPremium);
		
		JPanel panelPDF = new JPanel();
		GridBagConstraints gbc_panelPDF = new GridBagConstraints();
		gbc_panelPDF.insets = new Insets(0, 0, 5, 5);
		gbc_panelPDF.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelPDF.gridx = 0;
		gbc_panelPDF.gridy = 0;
		panelFuncionesPremium.add(panelPDF, gbc_panelPDF);
		
		fotoPDF = new JLabel("");
		fotoPDF.setHorizontalAlignment(SwingConstants.CENTER);
		fotoPDF.setToolTipText("Genera un PDF con tus seguidores");
			fotoPDF.setIcon(new ImageIcon(pdf.getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
		fotoPDF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				fotoPDF.setIcon(new ImageIcon(pdf.getScaledInstance(84, 84, Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				fotoPDF.setIcon(new ImageIcon(pdf.getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				fotoPDF.setIcon(new ImageIcon(Utils.cambiarBrillo(0.5f, pdf.getScaledInstance(84, 84, Image.SCALE_SMOOTH))));
			}
			
			@Override 
			public void mouseReleased(MouseEvent e) {
				fotoPDF.setIcon(new ImageIcon(pdf.getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// Creamos el dialogo para obtener la ruta de dónde querermos guardar el archivo
				JFileChooser elegirArchivo = new JFileChooser();
					elegirArchivo.setDialogTitle("Guardar PDF con mis seguidores");
					elegirArchivo.setSelectedFile(new File("seguidores.pdf"));
					
				if(elegirArchivo.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
					Controlador.getControlador().generarPdfSeguidores(elegirArchivo.getSelectedFile().getAbsolutePath());
			}
		});
		panelPDF.setLayout(new BorderLayout(0, 0));
		panelPDF.add(fotoPDF);
		
		JPanel panelExcel = new JPanel();
		GridBagConstraints gbc_panelExcel = new GridBagConstraints();
		gbc_panelExcel.insets = new Insets(0, 0, 5, 5);
		gbc_panelExcel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelExcel.gridx = 1;
		gbc_panelExcel.gridy = 0;
		panelFuncionesPremium.add(panelExcel, gbc_panelExcel);
		
		fotoExcel = new JLabel("");
		fotoExcel.setHorizontalAlignment(SwingConstants.CENTER);
		fotoExcel.setToolTipText("Genera un excel con tus seguidores");
			fotoExcel.setIcon(new ImageIcon(excel.getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
		fotoExcel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				fotoExcel.setIcon(new ImageIcon(excel.getScaledInstance(84, 84, Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				fotoExcel.setIcon(new ImageIcon(excel.getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				fotoExcel.setIcon(new ImageIcon(Utils.cambiarBrillo(0.5f, excel.getScaledInstance(84, 84, Image.SCALE_SMOOTH))));
			}
			
			@Override 
			public void mouseReleased(MouseEvent e) {
				fotoExcel.setIcon(new ImageIcon(excel.getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// Creamos el dialogo para obtener la ruta de dónde querermos guardar el archivo
				JFileChooser elegirArchivo = new JFileChooser();
					elegirArchivo.setDialogTitle("Guardar Excel con mis seguidores");
					elegirArchivo.setSelectedFile(new File("seguidores.xlsx"));
					
				if(elegirArchivo.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
					Controlador.getControlador().generarExcelSeguidores(elegirArchivo.getSelectedFile().getAbsolutePath());
			}
		});
		panelExcel.setLayout(new BorderLayout(0, 0));
		panelExcel.add(fotoExcel, BorderLayout.CENTER);
		
		JPanel panelTopFotos = new JPanel();
		GridBagConstraints gbc_panelTopFotos = new GridBagConstraints();
		gbc_panelTopFotos.insets = new Insets(0, 0, 5, 0);
		gbc_panelTopFotos.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelTopFotos.gridx = 2;
		gbc_panelTopFotos.gridy = 0;
		panelFuncionesPremium.add(panelTopFotos, gbc_panelTopFotos);
		
		fotoTopFotos = new JLabel("");
		fotoTopFotos.setHorizontalAlignment(SwingConstants.CENTER);
		fotoTopFotos.setToolTipText("Genera una lista con tus 10 fotos con más likes");
			fotoTopFotos.setIcon(new ImageIcon(topfotos.getScaledInstance(96, 96, Image.SCALE_SMOOTH)));
		fotoTopFotos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				fotoTopFotos.setIcon(new ImageIcon(topfotos.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				fotoTopFotos.setIcon(new ImageIcon(topfotos.getScaledInstance(96, 96, Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				fotoTopFotos.setIcon(new ImageIcon(Utils.cambiarBrillo(0.5f, topfotos.getScaledInstance(100, 100, Image.SCALE_SMOOTH))));
			}
			
			@Override 
			public void mouseReleased(MouseEvent e) {
				fotoTopFotos.setIcon(new ImageIcon(topfotos.getScaledInstance(96, 96, Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaTopFotos.getInstancia().mostrar();
			}
		});
		panelTopFotos.setLayout(new BorderLayout(0, 0));
		panelTopFotos.add(fotoTopFotos);
		
		JLabel PDFTexto = new JLabel("Generar PDF");
		PDFTexto.setHorizontalAlignment(SwingConstants.CENTER);
		PDFTexto.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		GridBagConstraints gbc_PDFTexto = new GridBagConstraints();
		gbc_PDFTexto.fill = GridBagConstraints.HORIZONTAL;
		gbc_PDFTexto.insets = new Insets(0, 0, 0, 5);
		gbc_PDFTexto.gridx = 0;
		gbc_PDFTexto.gridy = 1;
		panelFuncionesPremium.add(PDFTexto, gbc_PDFTexto);
		
		JLabel ExcelTexto = new JLabel("Generar Excel");
		ExcelTexto.setHorizontalAlignment(SwingConstants.CENTER);
		ExcelTexto.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		GridBagConstraints gbc_ExcelTexto = new GridBagConstraints();
		gbc_ExcelTexto.fill = GridBagConstraints.HORIZONTAL;
		gbc_ExcelTexto.insets = new Insets(0, 0, 0, 5);
		gbc_ExcelTexto.gridx = 1;
		gbc_ExcelTexto.gridy = 1;
		panelFuncionesPremium.add(ExcelTexto, gbc_ExcelTexto);
		
		JLabel lblNewLabel = new JLabel("Top 10 Fotos");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 1;
		panelFuncionesPremium.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panelBajaPremium = new JPanel();
		GridBagConstraints gbc_panelBajaPremium = new GridBagConstraints();
		gbc_panelBajaPremium.fill = GridBagConstraints.BOTH;
		gbc_panelBajaPremium.gridx = 0;
		gbc_panelBajaPremium.gridy = 1;
		panelInfo2.add(panelBajaPremium, gbc_panelBajaPremium);
		
		JButton btnBajaPremium = new JButton("Darse de baja");
		btnBajaPremium.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				notificacionPremium(new ActionEvent(this, 7, "cambioNoPremium"));
				destruir();
			}
		});
		btnBajaPremium.setBackground(new Color(0xb2, 0x00, 0x9c));
		btnBajaPremium.setForeground(new Color(0xff, 0xff, 0xff));
		btnBajaPremium.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		panelBajaPremium.add(btnBajaPremium);
	}
	
	/* Destruye la instancia */
	private void destruir() {
		frame.dispose();
		instancia = null;
	}
	
	/* Devuelve la instancia de la ventana */
	public static VentanaPremium getInstancia() {
		if(instancia==null) instancia = new VentanaPremium();
		return instancia;
	}
	
	/* Muestra la ventana */
	public void mostrar() {
		frame.setVisible(true);
	}
	
}
