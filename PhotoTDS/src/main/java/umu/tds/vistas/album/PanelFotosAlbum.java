package umu.tds.vistas.album;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PanelFotosAlbum extends JPanel {

	private static final int COLUMNS = 4;
	private final int anchoImagen, altoImagen;
	private int indiceImagen;

	public PanelFotosAlbum(int anchoImagen, int altoImagen) {
		this.anchoImagen = anchoImagen;
		this.altoImagen = altoImagen;
		this.indiceImagen = 0;

		inicializar();
	}

	private void inicializar() {
		System.out.println(getSize());

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 10, 0, 0, 0, 0, 10, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		setBorder(new LineBorder(Color.gray, 1));

	}

	public void setFoto(String ruta) {
		System.out.println("Añadiendo foto");
		Image imagen = new ImageIcon(ruta).getImage().getScaledInstance(anchoImagen, altoImagen, Image.SCALE_SMOOTH);
		JLabel lblImagen = new JLabel(new ImageIcon(imagen));

		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.anchor = GridBagConstraints.CENTER;
		gbc_lblImagen.gridx = (indiceImagen % COLUMNS) + 1;
		
		
		gbc_lblImagen.gridy = indiceImagen <= 3 ? 0 : indiceImagen/COLUMNS;
		gbc_lblImagen.insets = new Insets(5, 0, 0, 5);
		add(lblImagen, gbc_lblImagen);
		indiceImagen++;
		revalidate();
		repaint();
	}

}
