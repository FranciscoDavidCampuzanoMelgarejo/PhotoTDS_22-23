package umu.tds.vistas;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.pojos.*;
import umu.tds.vistas.publicaciones.ListRendererComentario;
import umu.tds.vistas.publicaciones.PanelComentarios;
import umu.tds.vistas.publicaciones.PanelPublicacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @title Ventana publicación
 * @author diego
 * **/
public class VentanaPubli {
	private Publicacion publi;
	private Usuario usuario;
	private JFrame frame;
	private JLabel labelNumLikes, labelCommNum, labelTiempo;
	private BufferedImage winicon, img, like, comment, userpic;
	private JList<Comentario> comentariosJList;
	private DefaultListModel<Comentario> comentariosListModel;

	/* Precargar recursos */
	private void cargarRecursos() {
		try {
			Foto foto = (Foto) publi;
			winicon = ImageIO.read(getClass().getResource("/imagenes/dock/logo1.png"));
			img = ImageIO.read(new File(foto.getRuta()));
			like = ImageIO.read(getClass().getResource("/imagenes/like.png"));
			comment = ImageIO.read(getClass().getResource("/imagenes/comment.png"));
			
			String ruta = usuario.getPerfil().getFoto();
			userpic = (ruta==null) ? ImageIO.read(getClass().getResource("/imagenes/noprofilepic.png")) : ImageIO.read(new File(ruta));
		} catch (IOException ioe) { ioe.printStackTrace(); System.exit(-1); }
	}

	public void mostrar() {
		frame.setVisible(true);
	}
	
	public void destruir() {
		frame.dispose();
	}
	
	public void recargarLikesCommTiempo() {
		int nl = publi.getLikes();
		String nlikes = (nl==0) ? "sin likes" : ((nl==1) ? "un like" : nl+" likes");
		labelNumLikes.setText(nlikes);
		int nc = publi.getComentarios().size();
		String ncom = (nc==0) ? "sin comentarios" : ((nc==1) ? "un comentario" : nc+" comentarios" );
		labelCommNum.setText(ncom);
		
		long min = ChronoUnit.MINUTES.between(publi.getFecha(), LocalDateTime.now()), hrs;
		if(min>59) {
			hrs = min/60;
			if(hrs==1) labelTiempo.setText("Hace una hora ");
			else labelTiempo.setText("Hace " + hrs + " horas ");
		} else {
			if(min==0) labelTiempo.setText("Ahora mismo ");
			else if(min==1) labelTiempo.setText("Hace un minuto ");
			else labelTiempo.setText("Hace " + min + " minutos ");
		}
	}
	
	public void recargarComentarios() {
		comentariosListModel.clear();
		comentariosListModel.addAll(publi.getComentarios());
		comentariosJList.revalidate();
	}
	
	/* Constructor de la ventana */
	public VentanaPubli(Publicacion publi) {
		this.publi = publi;
		this.usuario = publi.getUsuario();
		comentariosListModel = new DefaultListModel<Comentario>();
		cargarRecursos();
		dibujar();
	}

	/* Dibujar */
	private void dibujar() {
		frame = new JFrame();
		frame.setTitle(publi.getTitulo());
		frame.setIconImage(winicon);
		frame.setBounds(100, 100, 800, 640);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{480, 320, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panelPubli = new JPanel();
		GridBagConstraints gbc_panelPubli = new GridBagConstraints();
		gbc_panelPubli.fill = GridBagConstraints.BOTH;
		gbc_panelPubli.gridx = 0;
		gbc_panelPubli.gridy = 0;
		frame.getContentPane().add(panelPubli, gbc_panelPubli);
		GridBagLayout gbl_panelPubli = new GridBagLayout();
		gbl_panelPubli.columnWidths = new int[]{450, 0};
		gbl_panelPubli.rowHeights = new int[]{0, 96, 0, 0};
		gbl_panelPubli.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelPubli.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		panelPubli.setLayout(gbl_panelPubli);
		
		JPanel panelFoto = new JPanel();
		GridBagConstraints gbc_panelFoto = new GridBagConstraints();
		gbc_panelFoto.anchor = GridBagConstraints.WEST;
		gbc_panelFoto.fill = GridBagConstraints.VERTICAL;
		gbc_panelFoto.gridx = 0;
		gbc_panelFoto.gridy = 0;
		panelPubli.add(panelFoto, gbc_panelFoto);
		panelFoto.setLayout(new BorderLayout(0, 0));
		
		JLabel labelFoto = new JLabel();
			labelFoto.setIcon(new ImageIcon(Utils.reescalar(450, 450, img)));
		labelFoto.setHorizontalAlignment(SwingConstants.CENTER);
		panelFoto.add(labelFoto, BorderLayout.CENTER);
		
		JPanel panelFotoInfo = new JPanel();
		GridBagConstraints gbc_panelFotoInfo = new GridBagConstraints();
		gbc_panelFotoInfo.anchor = GridBagConstraints.WEST;
		gbc_panelFotoInfo.insets = new Insets(0, 5, 0, 0);
		gbc_panelFotoInfo.fill = GridBagConstraints.VERTICAL;
		gbc_panelFotoInfo.gridx = 0;
		gbc_panelFotoInfo.gridy = 1;
		panelPubli.add(panelFotoInfo, gbc_panelFotoInfo);
		GridBagLayout gbl_panelFotoInfo = new GridBagLayout();
		gbl_panelFotoInfo.columnWidths = new int[]{445, 0};
		gbl_panelFotoInfo.rowHeights = new int[]{32, 0, 0, 0};
		gbl_panelFotoInfo.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panelFotoInfo.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		panelFotoInfo.setLayout(gbl_panelFotoInfo);
		
		JPanel panelLikedCommTiempo = new JPanel();
		GridBagConstraints gbc_panelLikedCommTiempo = new GridBagConstraints();
		gbc_panelLikedCommTiempo.fill = GridBagConstraints.BOTH;
		gbc_panelLikedCommTiempo.gridx = 0;
		gbc_panelLikedCommTiempo.gridy = 0;
		panelFotoInfo.add(panelLikedCommTiempo, gbc_panelLikedCommTiempo);
		GridBagLayout gbl_panelLikedCommTiempo = new GridBagLayout();
		gbl_panelLikedCommTiempo.columnWidths = new int[]{150, 150, 145, 0};
		gbl_panelLikedCommTiempo.rowHeights = new int[]{32, 0};
		gbl_panelLikedCommTiempo.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelLikedCommTiempo.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelLikedCommTiempo.setLayout(gbl_panelLikedCommTiempo);
		
		JPanel panelLikes = new JPanel();
		GridBagConstraints gbc_panelLikes = new GridBagConstraints();
		gbc_panelLikes.insets = new Insets(0, 5, 0, 5);
		gbc_panelLikes.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelLikes.gridx = 0;
		gbc_panelLikes.gridy = 0;
		panelLikedCommTiempo.add(panelLikes, gbc_panelLikes);
		GridBagLayout gbl_panelLikes = new GridBagLayout();
		gbl_panelLikes.columnWidths = new int[]{40, 107, 0};
		gbl_panelLikes.rowHeights = new int[]{40, 0};
		gbl_panelLikes.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelLikes.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelLikes.setLayout(gbl_panelLikes);
		
		JLabel labelLikesIcon = new JLabel();
		labelLikesIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Controlador.getControlador().darLike(publi.getId());
				recargarLikesCommTiempo();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				labelLikesIcon.setIcon(new ImageIcon(Utils.reescalar(24, 24, like)));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				labelLikesIcon.setIcon(new ImageIcon(Utils.reescalar(22, 22, like)));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				labelLikesIcon.setIcon(new ImageIcon(Utils.reescalar(24, 24, Utils.cambiarBrillo(0.7f, like))));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				labelLikesIcon.setIcon(new ImageIcon(Utils.reescalar(22, 22, like)));
			}
		});
		labelLikesIcon.setIcon(new ImageIcon(Utils.reescalar(22,22,like)));
		GridBagConstraints gbc_labelLikesIcon = new GridBagConstraints();
		gbc_labelLikesIcon.fill = GridBagConstraints.VERTICAL;
		gbc_labelLikesIcon.insets = new Insets(0, 5, 0, 0);
		gbc_labelLikesIcon.gridx = 0;
		gbc_labelLikesIcon.gridy = 0;
		panelLikes.add(labelLikesIcon, gbc_labelLikesIcon);
		
		labelNumLikes = new JLabel();
		labelNumLikes.setToolTipText("Número de likes");
		labelNumLikes.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		GridBagConstraints gbc_labelNumLikes = new GridBagConstraints();
		gbc_labelNumLikes.fill = GridBagConstraints.BOTH;
		gbc_labelNumLikes.gridx = 1;
		gbc_labelNumLikes.gridy = 0;
		panelLikes.add(labelNumLikes, gbc_labelNumLikes);
		
		JPanel panelComm = new JPanel();
		GridBagConstraints gbc_panelComm = new GridBagConstraints();
		gbc_panelComm.insets = new Insets(0, 0, 0, 5);
		gbc_panelComm.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelComm.gridx = 1;
		gbc_panelComm.gridy = 0;
		panelLikedCommTiempo.add(panelComm, gbc_panelComm);
		GridBagLayout gbl_panelComm = new GridBagLayout();
		gbl_panelComm.columnWidths = new int[]{24, 112, 0};
		gbl_panelComm.rowHeights = new int[]{32, 0};
		gbl_panelComm.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelComm.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelComm.setLayout(gbl_panelComm);
		
		JLabel labelCommIcon = new JLabel();
		labelCommIcon.setIcon(new ImageIcon(Utils.reescalar(22,22, comment)));
		GridBagConstraints gbc_labelCommIcon = new GridBagConstraints();
		gbc_labelCommIcon.fill = GridBagConstraints.VERTICAL;
		gbc_labelCommIcon.insets = new Insets(0, 0, 0, 5);
		gbc_labelCommIcon.gridx = 0;
		gbc_labelCommIcon.gridy = 0;
		panelComm.add(labelCommIcon, gbc_labelCommIcon);
		
		
		labelCommNum = new JLabel();
		labelCommNum.setToolTipText("Número de comentarios");
		labelCommNum.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		GridBagConstraints gbc_labelCommNum = new GridBagConstraints();
		gbc_labelCommNum.fill = GridBagConstraints.BOTH;
		gbc_labelCommNum.gridx = 1;
		gbc_labelCommNum.gridy = 0;
		panelComm.add(labelCommNum, gbc_labelCommNum);
		
		JPanel panelTiempo = new JPanel();
		GridBagConstraints gbc_panelTiempo = new GridBagConstraints();
		gbc_panelTiempo.anchor = GridBagConstraints.EAST;
		gbc_panelTiempo.fill = GridBagConstraints.VERTICAL;
		gbc_panelTiempo.gridx = 2;
		gbc_panelTiempo.gridy = 0;
		panelLikedCommTiempo.add(panelTiempo, gbc_panelTiempo);
		panelTiempo.setLayout(new BorderLayout(0, 0));
		
		labelTiempo = new JLabel();
		labelTiempo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTiempo.setFont(new Font("Tahoma", Font.ITALIC, 12));
		panelTiempo.add(labelTiempo, BorderLayout.CENTER);
		
		JPanel panelTitulo = new JPanel();
		GridBagConstraints gbc_panelTitulo = new GridBagConstraints();
		gbc_panelTitulo.insets = new Insets(0, 5, 0, 0);
		gbc_panelTitulo.fill = GridBagConstraints.BOTH;
		gbc_panelTitulo.gridx = 0;
		gbc_panelTitulo.gridy = 1;
		panelFotoInfo.add(panelTitulo, gbc_panelTitulo);
		panelTitulo.setLayout(new BorderLayout(0, 0));
		
		JLabel labelTitulo = new JLabel(publi.getTitulo());
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		panelTitulo.add(labelTitulo, BorderLayout.CENTER);
		
		JPanel panelDescripcion = new JPanel();
		GridBagConstraints gbc_panelDescripcion = new GridBagConstraints();
		gbc_panelDescripcion.insets = new Insets(0, 5, 0, 0);
		gbc_panelDescripcion.fill = GridBagConstraints.BOTH;
		gbc_panelDescripcion.gridx = 0;
		gbc_panelDescripcion.gridy = 2;
		panelFotoInfo.add(panelDescripcion, gbc_panelDescripcion);
		panelDescripcion.setLayout(new BorderLayout(0, 0));
		
		JLabel labelDescripcion = new JLabel(publi.getDescripcion());
		labelDescripcion.setFont(new Font("Tahoma", Font.ITALIC, 12));
		panelDescripcion.add(labelDescripcion, BorderLayout.CENTER);
		
		JPanel panelInfoFoto = new JPanel();
		GridBagConstraints gbc_panelInfoFoto = new GridBagConstraints();
		gbc_panelInfoFoto.fill = GridBagConstraints.BOTH;
		gbc_panelInfoFoto.gridx = 1;
		gbc_panelInfoFoto.gridy = 0;
		frame.getContentPane().add(panelInfoFoto, gbc_panelInfoFoto);
		GridBagLayout gbl_panelInfoFoto = new GridBagLayout();
		gbl_panelInfoFoto.columnWidths = new int[]{0, 0};
		gbl_panelInfoFoto.rowHeights = new int[]{80, 0, 192, 0};
		gbl_panelInfoFoto.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelInfoFoto.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panelInfoFoto.setLayout(gbl_panelInfoFoto);
		
		JPanel panelPerfil = new JPanel();
		GridBagConstraints gbc_panelPerfil = new GridBagConstraints();
		gbc_panelPerfil.insets = new Insets(0, 0, 5, 0);
		gbc_panelPerfil.fill = GridBagConstraints.BOTH;
		gbc_panelPerfil.gridx = 0;
		gbc_panelPerfil.gridy = 0;
		panelInfoFoto.add(panelPerfil, gbc_panelPerfil);
		GridBagLayout gbl_panelPerfil = new GridBagLayout();
		gbl_panelPerfil.columnWidths = new int[]{80, 0, 0};
		gbl_panelPerfil.rowHeights = new int[]{0, 0};
		gbl_panelPerfil.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelPerfil.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelPerfil.setLayout(gbl_panelPerfil);
		
		JPanel panelPerfilFoto = new JPanel();
		GridBagConstraints gbc_panelPerfilFoto = new GridBagConstraints();
		gbc_panelPerfilFoto.insets = new Insets(0, 0, 0, 5);
		gbc_panelPerfilFoto.fill = GridBagConstraints.BOTH;
		gbc_panelPerfilFoto.gridx = 0;
		gbc_panelPerfilFoto.gridy = 0;
		panelPerfil.add(panelPerfilFoto, gbc_panelPerfilFoto);
		panelPerfilFoto.setLayout(new BorderLayout(0, 0));
		
		JLabel labelPerfilFoto = new JLabel();
			labelPerfilFoto.setIcon(new ImageIcon(Utils.redondear(80, userpic)));
		labelPerfilFoto.setHorizontalAlignment(SwingConstants.CENTER);
		panelPerfilFoto.add(labelPerfilFoto, BorderLayout.CENTER);
		
		JPanel panelPerfilInfo = new JPanel();
		GridBagConstraints gbc_panelPerfilInfo = new GridBagConstraints();
		gbc_panelPerfilInfo.fill = GridBagConstraints.BOTH;
		gbc_panelPerfilInfo.gridx = 1;
		gbc_panelPerfilInfo.gridy = 0;
		panelPerfil.add(panelPerfilInfo, gbc_panelPerfilInfo);
		GridBagLayout gbl_panelPerfilInfo = new GridBagLayout();
		gbl_panelPerfilInfo.columnWidths = new int[]{0, 0};
		gbl_panelPerfilInfo.rowHeights = new int[]{40, 40, 0};
		gbl_panelPerfilInfo.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelPerfilInfo.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelPerfilInfo.setLayout(gbl_panelPerfilInfo);
		
		JPanel panelPerfilLogin = new JPanel();
		GridBagConstraints gbc_panelPerfilLogin = new GridBagConstraints();
		gbc_panelPerfilLogin.fill = GridBagConstraints.BOTH;
		gbc_panelPerfilLogin.gridx = 0;
		gbc_panelPerfilLogin.gridy = 0;
		panelPerfilInfo.add(panelPerfilLogin, gbc_panelPerfilLogin);
		panelPerfilLogin.setLayout(new BorderLayout(0, 0));
		
		JLabel labelPerfilNick = new JLabel(usuario.getUsuario());
		labelPerfilNick.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		panelPerfilLogin.add(labelPerfilNick, BorderLayout.CENTER);
		
		JPanel panelPerfilNombre = new JPanel();
		GridBagConstraints gbc_panelPerfilNombre = new GridBagConstraints();
		gbc_panelPerfilNombre.fill = GridBagConstraints.BOTH;
		gbc_panelPerfilNombre.gridx = 0;
		gbc_panelPerfilNombre.gridy = 1;
		panelPerfilInfo.add(panelPerfilNombre, gbc_panelPerfilNombre);
		panelPerfilNombre.setLayout(new BorderLayout(0, 0));
		
		JLabel labelPerfilNombre = new JLabel(usuario.getNombre());
		labelPerfilNombre.setFont(new Font("Tahoma", Font.ITALIC, 12));
		panelPerfilNombre.add(labelPerfilNombre, BorderLayout.CENTER);
		
		JPanel panelComentarios = new JPanel();
		GridBagConstraints gbc_panelComentarios = new GridBagConstraints();
		gbc_panelComentarios.insets = new Insets(5, 5, 5, 5);
		gbc_panelComentarios.fill = GridBagConstraints.BOTH;
		gbc_panelComentarios.gridx = 0;
		gbc_panelComentarios.gridy = 1;
		panelInfoFoto.add(panelComentarios, gbc_panelComentarios);
		panelComentarios.setLayout(new BorderLayout(0, 0));
		panelComentarios.setBorder(BorderFactory.createEmptyBorder());
		
		JScrollPane scrollComentarios = new JScrollPane();
		scrollComentarios.setBorder(BorderFactory.createEmptyBorder());
		scrollComentarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelComentarios.add(scrollComentarios, BorderLayout.CENTER);
		
		comentariosJList = new JList<Comentario>();
		comentariosJList.setCellRenderer(new ListRendererComentario());
		comentariosJList.setModel(comentariosListModel);
		scrollComentarios.setViewportView(comentariosJList);
		scrollComentarios.setViewportBorder(BorderFactory.createEmptyBorder());
		
		JPanel panelComentar = new JPanel();
		GridBagConstraints gbc_panelComentar = new GridBagConstraints();
		gbc_panelComentar.fill = GridBagConstraints.BOTH;
		gbc_panelComentar.gridx = 0;
		gbc_panelComentar.gridy = 2;
		panelInfoFoto.add(panelComentar, gbc_panelComentar);
		GridBagLayout gbl_panelComentar = new GridBagLayout();
		gbl_panelComentar.columnWidths = new int[]{0, 0};
		gbl_panelComentar.rowHeights = new int[]{152, 40, 0};
		gbl_panelComentar.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelComentar.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panelComentar.setLayout(gbl_panelComentar);
		
		JPanel panelTextBox = new JPanel();
		GridBagConstraints gbc_panelTextBox = new GridBagConstraints();
		gbc_panelTextBox.insets = new Insets(5, 5, 5, 5);
		gbc_panelTextBox.fill = GridBagConstraints.BOTH;
		gbc_panelTextBox.gridx = 0;
		gbc_panelTextBox.gridy = 0;
		panelComentar.add(panelTextBox, gbc_panelTextBox);
		panelTextBox.setLayout(new BorderLayout(0, 0));
		panelTextBox.setBorder(BorderFactory.createEmptyBorder());
		
		JScrollPane scrollComentario = new JScrollPane();
		scrollComentario.setBorder(BorderFactory.createEmptyBorder());
		scrollComentario.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollComentario.setViewportBorder(BorderFactory.createEmptyBorder());
		panelTextBox.add(scrollComentario, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String comentario = textArea.getText();
				if(textArea.isFocusOwner() && e.getKeyCode()==KeyEvent.VK_ENTER) {
					Controlador.getControlador().comentar(publi.getId(), comentario);
					recargarComentarios();
					recargarLikesCommTiempo();
					textArea.setText(null);
				}
			}
		});
		textArea.setToolTipText("Escribe un comentario");
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollComentario.setViewportView(textArea);
		
		JPanel panelPublicarComentario = new JPanel();
		GridBagConstraints gbc_panelPublicarComentario = new GridBagConstraints();
		gbc_panelPublicarComentario.fill = GridBagConstraints.BOTH;
		gbc_panelPublicarComentario.gridx = 0;
		gbc_panelPublicarComentario.gridy = 1;
		panelComentar.add(panelPublicarComentario, gbc_panelPublicarComentario);
		panelPublicarComentario.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton botonComentar = new JButton("Comentar");
		botonComentar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String comentario = textArea.getText();
				if(comentario!=null && comentario.length()>0) {
					Controlador.getControlador().comentar(publi.getId(), comentario);
					recargarComentarios();
					recargarLikesCommTiempo();
					textArea.setText(null);
				}
			}
		});
		botonComentar.setBackground(new Color(0xb2, 0x00, 0x9c));
		botonComentar.setForeground(new Color(0xff, 0xff, 0xff));
		botonComentar.setToolTipText("Publica tu comentario");
		botonComentar.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		panelPublicarComentario.add(botonComentar);
		
		recargarComentarios();
		recargarLikesCommTiempo();
	}

}
