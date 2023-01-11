package umu.tds.vistas;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
		//System.out.println("VA");
		int diametro = Math.min(imagen.getIconWidth(), imagen.getIconHeight()); //No sirve para nada
		//System.out.println(diametro);
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

	
	/* Método para cambiar el brillo una imagen */
	public static BufferedImage cambiarBrillo(float cantidad, Image img) {
		BufferedImage buff = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			buff.getGraphics().drawImage(img, 0,0, null);
			
		WritableRaster raster = buff.getRaster();
		int[] pixel = new int[4];
		for(int y=0;y<img.getHeight(null);y++) {
			for(int x=0;x<img.getWidth(null);x++) {
				raster.getPixel(x, y, pixel);
				pixel[0] *= cantidad;
				pixel[1] *= cantidad;
				pixel[2] *= cantidad;
				raster.setPixel(x, y, pixel);
			}
		}
		
		return buff;
	}

	/* Método para crear un mosaico de imágenes 
	 * 	w --> ancho de la nueva imagen
	 *  h --> alto de la nueva imagen
	 *  z --> ancho y alto del mosaico; z imagenes x z imagenes
	 *  imgs --> lista de imágenes
	 * */
	public static BufferedImage crearMosaico(int w, int h, int z, List<Image> imgs) {
		BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		int iw = w/z, ih = h/z;			// Ancho y alto de las imágenes del mosaico.
		Iterator<Image> it = imgs.iterator();
		Graphics g = buff.getGraphics();
		
		// Recorremos el mosaico y ponemos una imagen en cada casilla
		for(int y=0; y<z; y++) {
			for(int x=0; x<z; x++) {
				if(it.hasNext()) {
					Image img = it.next();
					if(img.getWidth(null)!=img.getHeight(null)) {
						int imgw = img.getWidth(null), imgh = img.getHeight(null);
						img = img.getScaledInstance(imgw/3, imgh/3, Image.SCALE_SMOOTH);
						int xori = img.getWidth(null)/2 - (iw/2) - 1;
						int yori = img.getHeight(null)/2 - (ih/2) - 1;
						BufferedImage buff2 = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_ARGB);
						buff2.getGraphics().drawImage(img, -xori, -yori, null);
						g.drawImage(buff2, (iw*x)-1, (ih*y)-1, null);
					} else g.drawImage(img.getScaledInstance(iw, ih, Image.SCALE_SMOOTH), (iw*x)-1, (ih*y)-1, null);
				}
			}
		}
		
		
		return buff;
	}
}
