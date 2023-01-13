package umu.tds.vistas.perfil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

// ESTA CLASE DEBE IMPLEMENTAR MouseListener para redefinir
//los metodos de mouseClicked, mouseEntered, mouseExited...
public class EtiquetaCircular extends JLabel {

	private int radio;
	private Color color;
	private Color colorRatonEncima;
	private Color colorRatonClickado;
	private boolean dentro;

	public EtiquetaCircular(int radio, Color color, Color colorRatonEncima, Color colorRatonClickado) {
		this.radio = radio;
		this.color = color;
		this.colorRatonEncima = colorRatonEncima;
		this.colorRatonClickado = colorRatonClickado;
		this.dentro = false;
		setOpaque(false);
		setBackground(color);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				dentro = true;
				setBackground(colorRatonEncima);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				dentro = false;
				setBackground(color);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(colorRatonClickado);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (dentro)
					setBackground(colorRatonEncima);
				else
					setBackground(color);
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(getBackground());
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

	public Color getColorRatonEncima() {
		return colorRatonEncima;
	}

	public void setColorRatonEncima(Color colorRatonEncima) {
		this.colorRatonEncima = colorRatonEncima;
	}

	public Color getColorRatonClickado() {
		return colorRatonClickado;
	}

	public void setColorRatonClickado(Color colorRatonClickado) {
		this.colorRatonClickado = colorRatonClickado;
	}

}
