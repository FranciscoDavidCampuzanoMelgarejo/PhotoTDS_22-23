package umu.tds.vistas;

import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import umu.tds.modelo.pojos.Comentario;
import umu.tds.modelo.pojos.Usuario;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class VentanaComentario {

	private JFrame frame;
	private Comentario comentario;
	
	private Image noPicture;			// En caso de que el usuario no tenga foto, se coloca una por defecto (NO PUEDE OCURRIR)
	
	private void cargarRecursos() {
		try{
			noPicture = ImageIO.read(VentanaComentario.class.getResource("/imagenes/dock-images/user1.png")).getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public VentanaComentario(Comentario comentario) {
		this.comentario = comentario;
		cargarRecursos();
		initialize();
	}
	
	public void mostrar() { this.frame.setVisible(true); }		// Muestra la ventana.
	public void ocultar() { this.frame.setVisible(false); }		// Oculta la ventana. No la destruye
	public void destruir() { this.frame.dispose(); }			// Destruye la ventana.

	
	private void initialize() {
		frame = new JFrame();
		//frame.setTitle("Comentario de " + comentario.getUsuario().getUsuario());
		frame.setTitle("Comentario de ");
		frame.setResizable(false);
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{100, 155, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panelFotoPerfil = new JPanel();
		GridBagConstraints gbc_panelFotoPerfil = new GridBagConstraints();
		gbc_panelFotoPerfil.insets = new Insets(0, 0, 5, 0);
		gbc_panelFotoPerfil.fill = GridBagConstraints.BOTH;
		gbc_panelFotoPerfil.gridx = 0;
		gbc_panelFotoPerfil.gridy = 0;
		frame.getContentPane().add(panelFotoPerfil, gbc_panelFotoPerfil);
		GridBagLayout gbl_panelFotoPerfil = new GridBagLayout();
		gbl_panelFotoPerfil.columnWidths = new int[]{100, 0, 0};
		gbl_panelFotoPerfil.rowHeights = new int[]{0, 0};
		gbl_panelFotoPerfil.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelFotoPerfil.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelFotoPerfil.setLayout(gbl_panelFotoPerfil);
		
		JPanel panelFoto = new JPanel();
		GridBagConstraints gbc_panelFoto = new GridBagConstraints();
		gbc_panelFoto.insets = new Insets(0, 5, 0, 5);
		gbc_panelFoto.fill = GridBagConstraints.BOTH;
		gbc_panelFoto.gridx = 0;
		gbc_panelFoto.gridy = 0;
		panelFotoPerfil.add(panelFoto, gbc_panelFoto);
		panelFoto.setLayout(new BorderLayout(0, 0));
		
		JLabel foto = new JLabel("");
			foto.setIcon(new ImageIcon(noPicture));
		panelFoto.add(foto, BorderLayout.CENTER);
		
		JPanel panelPerfil = new JPanel();
		GridBagConstraints gbc_panelPerfil = new GridBagConstraints();
		gbc_panelPerfil.fill = GridBagConstraints.BOTH;
		gbc_panelPerfil.gridx = 1;
		gbc_panelPerfil.gridy = 0;
		panelFotoPerfil.add(panelPerfil, gbc_panelPerfil);
		GridBagLayout gbl_panelPerfil = new GridBagLayout();
		gbl_panelPerfil.columnWidths = new int[]{0, 0};
		gbl_panelPerfil.rowHeights = new int[]{50, 0, 0};
		gbl_panelPerfil.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelPerfil.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelPerfil.setLayout(gbl_panelPerfil);
		
		JLabel labelNombre = new JLabel("Nombre");
		labelNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_labelNombre = new GridBagConstraints();
		gbc_labelNombre.anchor = GridBagConstraints.WEST;
		gbc_labelNombre.insets = new Insets(0, 0, 5, 0);
		gbc_labelNombre.gridx = 0;
		gbc_labelNombre.gridy = 0;
		panelPerfil.add(labelNombre, gbc_labelNombre);
		
		JLabel labelUsuario = new JLabel("Nombre de usuario");
		labelUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		labelUsuario.setFont(new Font("Tahoma", Font.ITALIC, 13));
		GridBagConstraints gbc_labelUsuario = new GridBagConstraints();
		gbc_labelUsuario.anchor = GridBagConstraints.WEST;
		gbc_labelUsuario.gridx = 0;
		gbc_labelUsuario.gridy = 1;
		panelPerfil.add(labelUsuario, gbc_labelUsuario);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 5, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		JPanel panelComentario = new JPanel();
		scrollPane.setViewportView(panelComentario);
		panelComentario.setLayout(new BorderLayout(0, 0));
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		panelComentario.add(textArea);
	}
	
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaComentario window = new VentanaComentario();
					window.mostrar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
}
