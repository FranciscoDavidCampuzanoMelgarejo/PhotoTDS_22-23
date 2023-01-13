package umu.tds.vistas;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

public class Utils {

	public Utils() {
		// TODO Auto-generated constructor stub
	}

	public static void campoVacio(String texto) {
		if (texto.isEmpty()) {
			throw new CampoVacioException("Excepcion: Campo Vacio");
		}
	}

	public static void formatoErroneo(String texto, String patron) {
		Pattern p = Pattern.compile(patron);
		Matcher matcher = p.matcher(texto);
		if (!matcher.matches()) {
			throw new FormatoNoAdecuadoException("Excepcion: No cumple el formato");
		}

	}

	// Metodo para redondear una imagen
	public static BufferedImage redondearImagen(int ancho, Icon imagen) {
		System.out.println("REDONDEAR IMAGEN");
		System.out.println("VA");
		int diametro = Math.min(imagen.getIconWidth(), imagen.getIconHeight()); //No sirve para nada
		System.out.println(diametro);
		BufferedImage mask = new BufferedImage(ancho, ancho, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = mask.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.fillOval(0, 0, ancho - 1, ancho - 1);
		g2d.dispose();

		BufferedImage masked = new BufferedImage(ancho, ancho, BufferedImage.TYPE_INT_ARGB);
		g2d = masked.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		// int x = (300 - imagen.getIconWidth()) / 2;
		// int y = (300 - imagen.getIconHeight()) / 2;
		// System.out.println("X: " + x + " Y: " + y);

		g2d.drawImage(((ImageIcon) imagen).getImage(), 0, 0, ancho, ancho, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
		g2d.drawImage(mask, 0, 0, null);
		g2d.dispose();

		return masked;

	}

}
