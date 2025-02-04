package umu.tds.vistas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import umu.tds.modelo.pojos.Foto;

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

		BufferedImage mask = new BufferedImage(ancho, ancho, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = mask.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillOval(1, 1, ancho-2, ancho-2);
		g2d.dispose();
		
		
		BufferedImage masked = new BufferedImage(ancho, ancho, BufferedImage.TYPE_INT_ARGB);
		g2d = masked.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(new Color(0x1e, 0x1e, 0x1e));
		g2d.fillRect(0, 0, ancho, ancho);
		g2d.drawImage(((ImageIcon) imagen).getImage(), 1, 1, ancho-2, ancho-2, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
		g2d.drawImage(mask, 0, 0, null);
		g2d.dispose();

		return masked;

	}
	
	/* Redondear imagen pero la entrada es una BufferedImage */
	public static BufferedImage redondear(int ancho, BufferedImage img) {
		BufferedImage mask = new BufferedImage(ancho, ancho, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) mask.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.fillOval(1, 1, ancho-2, ancho-2);
			g2d.dispose();
		
			BufferedImage masked = new BufferedImage(ancho, ancho, BufferedImage.TYPE_INT_ARGB);
				g2d = (Graphics2D) masked.getGraphics();
				g2d.drawImage(img, 1, 1, ancho-2, ancho-2, null);
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
				g2d.drawImage(mask, 0, 0, null);
				g2d.dispose();
			return masked;
	}
	
	/* Reescala una imagen a un tamaño */
	public static BufferedImage reescalar(int ancho, int alto, BufferedImage imagen) {
		BufferedImage img = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) img.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(imagen, 0,0, ancho, alto, null);
			g2d.dispose();
		return img;
	}

	
	/* Método para cambiar el brillo una imagen */
	public static BufferedImage cambiarBrillo(float cantidad, Image img) {
		BufferedImage buff = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			buff.getGraphics().drawImage(img, 0,0, null);
			
		WritableRaster raster = buff.getRaster();
		float[] pixel = new float[4];
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
	
	/* Devuelve la misma imagen pero con un efecto de zoom al centro y cuadrada */
	public static BufferedImage cuadrarZoom(int lado, BufferedImage imagen) {
		float w = imagen.getWidth(), h = imagen.getHeight(), diff;
		BufferedImage zoom = (w>h) ? Utils.reescalar((int)(w * lado/h), lado, imagen) 
								   : Utils.reescalar(lado, (int)(h * lado/w), imagen);
		w = zoom.getWidth(); h = zoom.getHeight();
		diff = (w>h) ? Math.abs(w-lado) : Math.abs(h-lado);
		BufferedImage nueva = new BufferedImage(lado, lado, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) nueva.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		
		if(w>h) g2d.drawImage(zoom, -(int)(diff/2), 0, null);
		else g2d.drawImage(zoom, 0, -(int)(diff/2), null);
		g2d.dispose();
		return nueva;
	}

	/* Método para crear un mosaico de imágenes 
	 * 	w --> ancho de la nueva imagen
	 *  h --> alto de la nueva imagen
	 *  z --> ancho y alto del mosaico; z imagenes x z imagenes
	 *  imgs --> lista de imágenes
	 * */
	public static BufferedImage crearMosaico(int w, int h, int z, List<Foto> fotos) {
		List<BufferedImage> imgs = new LinkedList<BufferedImage>();
		for(Foto f : fotos) {
			try {
				imgs.add(ImageIO.read(new File(f.getRuta())));
			} catch (IOException ioe) { ioe.printStackTrace(); System.exit(-1); }
		}
		
		BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		
		int iw = w/z, ih = h/z;			// Ancho y alto de las imágenes del mosaico.
		Iterator<BufferedImage> it = imgs.iterator();
		Graphics g = buff.getGraphics();
		
		// Recorremos el mosaico y ponemos una imagen en cada casilla
		for(int y=0; y<z; y++) {
			for(int x=0; x<z; x++) {
				if(it.hasNext()) {
					BufferedImage img = it.next();
					if(img.getWidth(null)!=img.getHeight(null)) {
						int imgw = img.getWidth(null), imgh = img.getHeight(null);
						//img = img.getScaledInstance(imgw/3, imgh/3, Image.SCALE_SMOOTH);
						img = Utils.reescalar(imgw/3, imgh/3, img);
						int xori = img.getWidth(null)/2 - (iw/2) - 1;
						int yori = img.getHeight(null)/2 - (ih/2) - 1;
						BufferedImage buff2 = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_ARGB);
						buff2.getGraphics().drawImage(img, -xori, -yori, null);
						g.drawImage(buff2, (iw*x)-1, (ih*y)-1, null);
					} else g.drawImage(Utils.reescalar(iw, ih, img), (iw*x)-1, (ih*y)-1, null);
					// Utils.reescalar(iw, ih, img)
				}
			}
		}
		
		
		return buff;
	}
	
	/* Inserta un pequeño icono en la esquina inferior derecha de una BufferedImage para distinguirla como album */
	public static BufferedImage crearFotoAlbum(BufferedImage img) {
		BufferedImage nueva = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB), icono;
		Graphics2D g2d = (Graphics2D) nueva.getGraphics();
		try{
			icono = ImageIO.read(VentanaPhotoTDS.class.getResource("/imagenes/folder.png"));
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(img, 0,0, null);
			g2d.drawImage(icono, img.getWidth()-icono.getWidth()-8, img.getHeight()-icono.getHeight()-8, null);
			g2d.dispose();
		} catch (IOException ioe) { ioe.printStackTrace(); System.exit(-1); }
		return nueva;
	}
}
