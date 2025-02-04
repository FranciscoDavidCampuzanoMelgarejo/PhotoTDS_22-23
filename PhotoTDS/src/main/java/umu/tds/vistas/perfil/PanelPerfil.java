package umu.tds.vistas.perfil;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import umu.tds.controlador.Controlador;
import umu.tds.vistas.Utils;

import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.SwingConstants;

public class PanelPerfil extends JPanel {

	private static final int MAX_CARACTERES = 200;
	private static final int SIZE_ICONO_OPTION_PANE = 40;
	private static final int ANCHO_FOTO_PERFIL = 175;

	private Icon fotoPerfil; 
	private String rutaFotoPerfil;
	private String presentacion;

	private Icon iconoCamara;
	private JDialog dialogoPadre;
	private JLabel lblFondo;

	private JFileChooser fileChooser; // Cambiar la foto de perfil

	private JLabel lblFotoPerfil;
	private JTextArea areaPresentacion;
	private JLabel lblContadorLetras;
	private boolean cambioFoto = false; // Para saber si se ha cambiado la foto antes de guardarla
	
	private String rutaImagenPerfilInicial = Controlador.getControlador().getUserPicture();

	public PanelPerfil(JDialog dialogo, Icon fotoPerfil, String rutaFotoPerfil) {
		this.dialogoPadre = dialogo;
		this.fotoPerfil = new ImageIcon(Utils.redondearImagen(ANCHO_FOTO_PERFIL, fotoPerfil));
		this.rutaFotoPerfil = rutaFotoPerfil;
		this.presentacion = Controlador.getControlador().getUserPresentacion();
		// Añadir la imagen a la carperta "/src/resources/imagenes"
		this.iconoCamara = new ImageIcon(new ImageIcon(getClass().getResource("/imagenes/camara-fotografica.png"))
				.getImage().getScaledInstance(SIZE_ICONO_OPTION_PANE, SIZE_ICONO_OPTION_PANE, Image.SCALE_SMOOTH));
		this.fileChooser = new JFileChooser();
		rutaImagenPerfilInicial = (rutaImagenPerfilInicial==null) ? " " : rutaImagenPerfilInicial;
		crearPanel();
	}

	public void limpiar() {
		this.lblFotoPerfil.setIcon(fotoPerfil);
		this.areaPresentacion.setText(presentacion);
		cambioFoto = false;
	}

	// Metodo privado para crear el panel
	private void crearPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 40, 0, 40, 0 };
		gridBagLayout.rowHeights = new int[] { 40, 212, 0, 0, 10, 0, 10, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLayeredPane panelFotoPerfil = new JLayeredPane();
		GridBagConstraints gbc_panelFotoPerfil = new GridBagConstraints();
		gbc_panelFotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_panelFotoPerfil.fill = GridBagConstraints.BOTH;
		gbc_panelFotoPerfil.gridx = 1;
		gbc_panelFotoPerfil.gridy = 1;
		add(panelFotoPerfil, gbc_panelFotoPerfil);

		lblFotoPerfil = new JLabel(fotoPerfil);
		lblFotoPerfil.setBounds(95, 11, ANCHO_FOTO_PERFIL, ANCHO_FOTO_PERFIL);
		panelFotoPerfil.add(lblFotoPerfil);

		lblFondo = new EtiquetaCircular(100, new Color(102, 193, 224), new Color(70, 150, 210), new Color(43, 87, 208));
		panelFotoPerfil.setLayer(lblFondo, 1);
		lblFondo.setBounds(195, 138, 65, 65);
		lblFondo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelFotoPerfil.add(lblFondo);

		// Al clickar en la etiqueta, abrir el FileChooser
		lblFondo.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int valorChooser = fileChooser.showOpenDialog(lblFondo);
				if (valorChooser == JFileChooser.APPROVE_OPTION) {
					// CUIDADO: HAY QUE SELCCIONAR UNA FOTO
					File fichero = fileChooser.getSelectedFile();
					rutaFotoPerfil = fichero.toString();
					BufferedImage masked = Utils.redondearImagen(ANCHO_FOTO_PERFIL, new ImageIcon(rutaFotoPerfil));
					lblFotoPerfil.setIcon(new ImageIcon(masked));
					// lblFotoPerfil.setBounds(95, 11, 175, 175);
					cambioFoto = true;
				}
			}
		});

		JLabel lblIconoCamara = new JLabel(iconoCamara);
		panelFotoPerfil.setLayer(lblIconoCamara, 2);
		lblIconoCamara.setBounds(208, 150, 40, 40);
		lblIconoCamara.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelFotoPerfil.add(lblIconoCamara);

		JScrollPane panelScrollPresentacion = new JScrollPane();
		panelScrollPresentacion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_panelScrollPresentacion = new GridBagConstraints();
		gbc_panelScrollPresentacion.insets = new Insets(10, 0, 5, 5);
		gbc_panelScrollPresentacion.fill = GridBagConstraints.BOTH;
		gbc_panelScrollPresentacion.gridx = 1;
		gbc_panelScrollPresentacion.gridy = 2;
		add(panelScrollPresentacion, gbc_panelScrollPresentacion);

		areaPresentacion = new JTextArea();
		areaPresentacion.setFont(new Font("Monospaced", Font.PLAIN, 14));
		areaPresentacion.setLineWrap(true);
		areaPresentacion.setRows(4);
		areaPresentacion.setColumns(30);
		panelScrollPresentacion.setViewportView(areaPresentacion);

		// Modificar la presentacion del usuario
		if (presentacion != null) {
			areaPresentacion.setText(presentacion);
		}

		((AbstractDocument) areaPresentacion.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {

				String subText = text;
				if (text != null) {
					int longitudActual = (fb.getDocument().getLength() - length);
					if (longitudActual + text.length() > MAX_CARACTERES) {
						int caracteresFaltantes = MAX_CARACTERES - longitudActual;
						subText = new String(text.substring(0, caracteresFaltantes));
					}
				}

				super.replace(fb, offset, length, subText, attrs);
				lblContadorLetras.setText(fb.getDocument().getLength() + " / 200");
			}

			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
				// TODO Auto-generated method stub
				super.remove(fb, offset, length);
				lblContadorLetras.setText(fb.getDocument().getLength() + " / 200");
			}
		});

		//lblContadorLetras = new JLabel(presentacion.length() + " / 200");
		lblContadorLetras = (presentacion==null) ? new JLabel("0 / 200") : new JLabel(presentacion.length() + " / 200");
		lblContadorLetras.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblContadorLetras = new GridBagConstraints();
		gbc_lblContadorLetras.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblContadorLetras.insets = new Insets(0, 0, 5, 5);
		gbc_lblContadorLetras.gridx = 1;
		gbc_lblContadorLetras.gridy = 3;
		add(lblContadorLetras, gbc_lblContadorLetras);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnGuardar.setBackground(new Color(28, 84, 215));
		btnGuardar.setForeground(new Color(200, 200, 200));
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.ipadx = 4;
		gbc_btnGuardar.ipady = 4;
		gbc_btnGuardar.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnGuardar.insets = new Insets(0, 0, 5, 5);
		gbc_btnGuardar.gridx = 1;
		gbc_btnGuardar.gridy = 5;
		add(btnGuardar, gbc_btnGuardar);

		// Al clickar en el boton, indicar que los cambios se han guardado 
		btnGuardar.addActionListener((ActionEvent e) -> {
			String nuevaPresentacion = areaPresentacion.getText();
			boolean presentacionModificada = true;//(nuevaPresentacion!=null) ? true : false;
			
			if(cambioFoto || presentacionModificada) {
				Controlador.getControlador().editarPerfil(rutaFotoPerfil, nuevaPresentacion);
				Image iconoReescalado = new ImageIcon(getClass().getResource("/imagenes/check-mark.png")).getImage()
						.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				JOptionPane.showMessageDialog(this, "Los cambios han sido guardados exitosamente", "Cambios Guardados",
						JOptionPane.INFORMATION_MESSAGE, new ImageIcon(iconoReescalado));
				cambioFoto = false;
			}
			
		});
	}

	public boolean cambiarFotoPerfil() {
		return (!rutaImagenPerfilInicial.equals(rutaFotoPerfil));
	}
}
