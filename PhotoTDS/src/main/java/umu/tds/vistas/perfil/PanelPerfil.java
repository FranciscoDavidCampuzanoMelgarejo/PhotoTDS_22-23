package umu.tds.vistas.perfil;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLayeredPane;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;

public class PanelPerfil extends JPanel {

	private Icon imagenPerfil;
	private String presentacion;

	private Icon iconoCamara;
	private JDialog dialogoPadre;
	private JLabel lblFondo;

	public PanelPerfil(JDialog dialogo, Icon imagenPerfil, String presentacion) {
		this.dialogoPadre = dialogo;
		this.imagenPerfil = imagenPerfil;
		this.presentacion = presentacion;
		// Añadir la imagen a la carperta "/src/resources/imagenes"
		this.iconoCamara = new ImageIcon(new ImageIcon(getClass().getResource("/imagenes/camara-fotografica.png"))
				.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

		crearPanel();
	}

	// Metodo privado para crear el panel
	private void crearPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 40, 0, 40, 0 };
		gridBagLayout.rowHeights = new int[] { 40, 200, 0, 15, 0, 40, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLayeredPane panelFotoPerfil = new JLayeredPane();
		GridBagConstraints gbc_panelFotoPerfil = new GridBagConstraints();
		gbc_panelFotoPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_panelFotoPerfil.fill = GridBagConstraints.BOTH;
		gbc_panelFotoPerfil.gridx = 1;
		gbc_panelFotoPerfil.gridy = 1;
		add(panelFotoPerfil, gbc_panelFotoPerfil);

		JLabel lblFotoPerfil = new JLabel(imagenPerfil);
		lblFotoPerfil.setBounds(95, 11, 175, 175);
		panelFotoPerfil.add(lblFotoPerfil);

		lblFondo = new EtiquetaCircular(100, new Color(40, 130, 60)); // Color de prueba
		panelFotoPerfil.setLayer(lblFondo, 1);
		lblFondo.setBounds(193, 123, 65, 65);
		panelFotoPerfil.add(lblFondo);

		JLabel lblIconoCamara = new JLabel(iconoCamara);
		panelFotoPerfil.setLayer(lblIconoCamara, 2);
		lblIconoCamara.setBounds(205, 133, 40, 40);
		panelFotoPerfil.add(lblIconoCamara);

		JScrollPane panelScrollPresentacion = new JScrollPane();
		panelScrollPresentacion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_panelScrollPresentacion = new GridBagConstraints();
		gbc_panelScrollPresentacion.insets = new Insets(10, 0, 0, 5);
		gbc_panelScrollPresentacion.fill = GridBagConstraints.BOTH;
		gbc_panelScrollPresentacion.gridx = 1;
		gbc_panelScrollPresentacion.gridy = 2;
		add(panelScrollPresentacion, gbc_panelScrollPresentacion);

		JTextArea areaPresentacion = new JTextArea();
		areaPresentacion.setLineWrap(true);
		areaPresentacion.setRows(4);
		areaPresentacion.setColumns(30);
		panelScrollPresentacion.setViewportView(areaPresentacion);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnGuardar.insets = new Insets(0, 0, 5, 5);
		gbc_btnGuardar.gridx = 1;
		gbc_btnGuardar.gridy = 4;
		add(btnGuardar, gbc_btnGuardar);
	}
}
