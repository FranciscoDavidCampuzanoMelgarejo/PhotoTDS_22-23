package umu.tds.vistas.seguidores;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;

import umu.tds.modelo.pojos.Usuario;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.Dialog.ModalityType;

import javax.swing.border.LineBorder;
import java.awt.Color;

public class DialogoSeguidores extends JDialog {

	private static final int ANCHO_DIALOGO = 400;
	private static final double PORCENTAJE_ALTURA = 0.6;

	private String textoSuperior;
	private Image iconoCierre;
	private JFrame framePadre;

	private JList<Usuario> lista;
	private List<Usuario> usuarios;

	public DialogoSeguidores(String texto, Set<Usuario> usuarios, JFrame frame) {
		this.textoSuperior = texto;
		this.usuarios = new ArrayList<Usuario>(usuarios);
		this.framePadre = frame;
		this.iconoCierre = new ImageIcon(getClass().getResource("/imagenes/cross-dialogo.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

		crearDialogo();
	}

	public void mostrar() {
		this.setVisible(true);
	}

	private void crearDialogo() {
		System.out.println("CREANDO DIALOGO");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.getRootPane().setOpaque(false);
		this.setUndecorated(true);

		int altura = (int) Math.ceil(framePadre.getContentPane().getSize().height * PORCENTAJE_ALTURA);
		setSize(ANCHO_DIALOGO, altura);
		setLocationRelativeTo(framePadre.getContentPane());

		lista = new JList<Usuario>(usuarios.toArray(new Usuario[usuarios.size()]));
		lista.setCellRenderer(new RendererLista());
		JScrollPane panelScroll = new JScrollPane(lista);
		getContentPane().add(panelScroll, BorderLayout.CENTER);

		JPanel panelNorte = new JPanel();
		panelNorte.setBorder(new LineBorder(Color.LIGHT_GRAY));
		getContentPane().add(panelNorte, BorderLayout.NORTH);
		panelNorte.setLayout(new BorderLayout(0, 0));

		JLabel lblSeguidores = new JLabel(textoSuperior);
		lblSeguidores.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSeguidores.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSeguidores.setHorizontalAlignment(SwingConstants.CENTER);
		panelNorte.add(lblSeguidores, BorderLayout.CENTER);

		JLabel lblBoton = new JLabel(new ImageIcon(iconoCierre));
		lblBoton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBoton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panelNorte.add(lblBoton, BorderLayout.EAST);

		lblBoton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});

		lista.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				RendererLista render = (RendererLista) lista.getCellRenderer();
				int index = lista.locationToIndex(e.getPoint());
				render.setHoverIndex(index);
				if (index == render.getHoverIndex())
					lista.repaint();

			}
		});

		lista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				RendererLista render = (RendererLista) lista.getCellRenderer();
				render.setHoverIndex(-1);
				lista.repaint();
			}
		});

		System.out.println("FIN CREAR DIALOGO");

	}

}
