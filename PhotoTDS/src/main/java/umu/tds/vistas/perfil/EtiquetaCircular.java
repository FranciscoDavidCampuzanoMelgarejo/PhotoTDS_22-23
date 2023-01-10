package umu.tds.vistas.perfil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

// ESTA CLASE DEBE IMPLEMENTAR MouseListener para redefinir
//los metodos de mouseClicked, mouseEntered, mouseExited...
public class EtiquetaCircular extends JLabel {
	
	private int radio;
	private Color color;

	public EtiquetaCircular(int radio, Color color) {
		this.radio = radio;
		this.color = color;
		setOpaque(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(color);
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);
		g2d.dispose();
		super.paintComponent(g);
	}
	
	public int getRadio() {
		return radio;
	}
	
	public void setRadio(int radio) {
		this.radio = radio;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

}
