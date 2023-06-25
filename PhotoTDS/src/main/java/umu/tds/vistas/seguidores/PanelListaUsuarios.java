package umu.tds.vistas.seguidores;

import java.awt.Color;

import javax.swing.JPanel;

import umu.tds.modelo.pojos.Usuario;
import umu.tds.vistas.Utils;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

public class PanelListaUsuarios extends JPanel {

	private static final int SIZE_FOTO = 50;

	private Usuario usuario;
	private final BufferedImage fotoPerfil;
	private final Color colorEncima = new Color(90, 91, 93);
	private final Color colorClickado = new Color(149, 153, 159);

	private JPanel panelNombres;

	public PanelListaUsuarios(Usuario usuario) {
		this.usuario = usuario;
		String ruta = usuario.getPerfil().getFoto() == null
				? getClass().getResource("/imagenes/noPerfilPicture.jpg").getPath()
				: usuario.getPerfil().getFoto();
		this.fotoPerfil = Utils.redondearImagen(SIZE_FOTO, new ImageIcon(ruta));
		crearPanel();
	}

	public void setColorEncima() {
		setBackground(colorEncima);
		panelNombres.setBackground(colorEncima);
	}

	public void setColorClickado() {
		setBackground(colorClickado);
		panelNombres.setBackground(colorClickado);
	}

	private void crearPanel() {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);

		JLabel lblFotoPerfil = new JLabel(new ImageIcon(fotoPerfil));
		lblFotoPerfil.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblFotoPerfil);

		Component horizontalStrut = Box.createHorizontalStrut(30);
		add(horizontalStrut);

		panelNombres = new JPanel();
		add(panelNombres);
		panelNombres.setLayout(new BoxLayout(panelNombres, BoxLayout.Y_AXIS));

		JLabel lblNombreUsuario = new JLabel(usuario.getUsuario());
		panelNombres.add(lblNombreUsuario);

		Component verticalStrut = Box.createVerticalStrut(10);
		panelNombres.add(verticalStrut);

		JLabel lblNombre = new JLabel(usuario.getNombre());
		panelNombres.add(lblNombre);
	}

}
