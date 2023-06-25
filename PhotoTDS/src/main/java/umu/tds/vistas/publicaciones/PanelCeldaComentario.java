package umu.tds.vistas.publicaciones;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import umu.tds.vistas.Utils;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class PanelCeldaComentario extends JPanel {

	private static final int ANCHO_FOTO_PERFIL = 20;

	private BufferedImage fotoPerfil;
	private String nickname;
	private String comentario;
	

	public PanelCeldaComentario(String rutaFotoPerfil, String nickname, String comentario) {
		setBorder(new LineBorder(new Color(0x3c-10, 0x3f-10, 0x41-10)));
		this.fotoPerfil = Utils.redondearImagen(ANCHO_FOTO_PERFIL, new ImageIcon(rutaFotoPerfil));
		this.nickname = nickname;
		this.comentario = comentario;

		inicializar();
	}

	private void inicializar() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 15, 0, 10, 0, 0, 15, 0 };
		gridBagLayout.rowHeights = new int[] { 14, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblFoto = new JLabel(new ImageIcon(fotoPerfil));
		GridBagConstraints gbc_lblFoto = new GridBagConstraints();
		gbc_lblFoto.insets = new Insets(0, 0, 5, 5);
		gbc_lblFoto.gridx = 1;
		gbc_lblFoto.gridy = 1;
		add(lblFoto, gbc_lblFoto);

		JLabel lblNickname = new JLabel(nickname);
		GridBagConstraints gbc_lblNickname = new GridBagConstraints();
		gbc_lblNickname.insets = new Insets(0, 0, 5, 5);
		gbc_lblNickname.gridx = 3;
		gbc_lblNickname.gridy = 1;
		add(lblNickname, gbc_lblNickname);

		JTextArea areaComentario = new JTextArea(comentario);
		areaComentario.setEditable(false);
		areaComentario.setWrapStyleWord(true);
		areaComentario.setLineWrap(true);
		areaComentario.setOpaque(false);
		GridBagConstraints gbc_areaComentario = new GridBagConstraints();
		gbc_areaComentario.anchor = GridBagConstraints.WEST;
		gbc_areaComentario.gridwidth = 2;
		gbc_areaComentario.insets = new Insets(0, 0, 0, 5);
		gbc_areaComentario.fill = GridBagConstraints.BOTH;
		gbc_areaComentario.gridx = 3;
		gbc_areaComentario.gridy = 2;
		add(areaComentario, gbc_areaComentario);

		if (comentario == null) {
			areaComentario.setVisible(false);
		}
	}

}
