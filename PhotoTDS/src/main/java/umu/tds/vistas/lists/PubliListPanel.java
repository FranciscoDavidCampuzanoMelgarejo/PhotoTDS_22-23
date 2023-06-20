package umu.tds.vistas.lists; 
import umu.tds.modelo.pojos.*;
import umu.tds.vistas.Utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;

/** @title JPanel para la lista de publicaciones
 *  @author diego
 * */
public class PubliListPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private Publicacion publi;
	private BufferedImage img, userpic, like, comment;
	private JLabel usericon, imgicon, username, likesData, commData;
	
	private GridBagLayout layout1;
	
	/* Recargar número de likes y de comentarios */
	private void recargarNumLikes() {
		if(publi.getLikes()==1) likesData.setText("1 like");
		else likesData.setText(publi.getLikes() + " likes");
	}
	
	/* Recargar el número de comentarios */
	private void recargarNumComentarios() {
		if(publi.getComentarios().size()==1) commData.setText("1 comentario");
		else commData.setText(publi.getComentarios().size() + " comentarios");
	}
	
	/* Constructor */
	public PubliListPanel(Publicacion publi, int alto) {
		super();
		setToolTipText(publi.getTitulo());
		setBorder(new LineBorder(new Color(0x3c-10, 0x3f-10, 0x41-10)));
		this.publi = publi;
		
		try {
			Foto foto = (Foto)publi;
			img = ImageIO.read(new File(foto.getRuta()));
			String ruta = publi.getUsuario().getPerfil().getFoto();
			userpic = (ruta==null) ? ImageIO.read(getClass().getResource("/imagenes/noprofilepic.png")) : ImageIO.read(new File(ruta));
			like = ImageIO.read(getClass().getResource("/imagenes/like.png"));
			comment = ImageIO.read(getClass().getResource("/imagenes/comment.png"));
		} catch (IOException ioe) { System.err.print("PHOTOTDS !> Error cargando publi '" + publi.toString() + "'\n"); }
		
		layout1 = new GridBagLayout();
		layout1.columnWidths = new int[]{alto+8, 0, 0};
		layout1.rowHeights = new int[]{alto+8, 0};
		layout1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		layout1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(layout1);
		
		JPanel panelIzq = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelIzq = new GridBagConstraints();
		gbc_panelIzq.fill = GridBagConstraints.BOTH;
		gbc_panelIzq.gridx = 0;
		gbc_panelIzq.gridy = 0;
		add(panelIzq, gbc_panelIzq);
		panelIzq.setLayout(new BorderLayout(0, 0));
		
		
		// Para la vista previa hacemos zoom a la imagen para que cuadre en 192x192
		imgicon = new JLabel();
		imgicon.setIcon(new ImageIcon(Utils.cuadrarZoom(alto, img)));
		panelIzq.add(imgicon, BorderLayout.CENTER);
		
		JPanel panelDrc = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelDrc = new GridBagConstraints();
		gbc_panelDrc.fill = GridBagConstraints.BOTH;
		gbc_panelDrc.gridx = 1;
		gbc_panelDrc.gridy = 0;
		add(panelDrc, gbc_panelDrc);
		GridBagLayout gbl_panelDrc = new GridBagLayout();
		gbl_panelDrc.columnWidths = new int[]{0, 0};
		gbl_panelDrc.rowHeights = new int[]{0, 20, 20, 0, 20, 0};
		gbl_panelDrc.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelDrc.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panelDrc.setLayout(gbl_panelDrc);
		
		JPanel panelUsuario = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelUsuario = new GridBagConstraints();
		gbc_panelUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_panelUsuario.fill = GridBagConstraints.BOTH;
		gbc_panelUsuario.gridx = 0;
		gbc_panelUsuario.gridy = 0;
		panelDrc.add(panelUsuario, gbc_panelUsuario);
		GridBagLayout gbl_panelUsuario = new GridBagLayout();
		gbl_panelUsuario.columnWidths = new int[]{alto/4 + 16, 0, 0};
		gbl_panelUsuario.rowHeights = new int[]{alto/4 + 16, 0, 0};
		gbl_panelUsuario.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelUsuario.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panelUsuario.setLayout(gbl_panelUsuario);
		
		JPanel panelPic = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelPic = new GridBagConstraints();
		gbc_panelPic.insets = new Insets(0, 0, 5, 5);
		gbc_panelPic.fill = GridBagConstraints.BOTH;
		gbc_panelPic.gridx = 0;
		gbc_panelPic.gridy = 0;
		panelUsuario.add(panelPic, gbc_panelPic);
		panelPic.setLayout(new BorderLayout(0, 0));
		
		JLabel userPicLabel = new JLabel("");
			userPicLabel.setIcon(new ImageIcon((Utils.redondear(alto/4, userpic))));
			userPicLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelPic.add(userPicLabel, BorderLayout.CENTER);
		
		JPanel panelInfo = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelInfo = new GridBagConstraints();
		gbc_panelInfo.insets = new Insets(0, 0, 5, 0);
		gbc_panelInfo.fill = GridBagConstraints.BOTH;
		gbc_panelInfo.gridx = 1;
		gbc_panelInfo.gridy = 0;
		panelUsuario.add(panelInfo, gbc_panelInfo);
		panelInfo.setLayout(new BorderLayout(0, 0));
		
		JLabel usernameLabel = new JLabel(publi.getUsuario().getUsuario());
		usernameLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		panelInfo.add(usernameLabel);
		
		JPanel panelLikes = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelLikes = new GridBagConstraints();
		gbc_panelLikes.insets = new Insets(0, 0, 5, 0);
		gbc_panelLikes.fill = GridBagConstraints.BOTH;
		gbc_panelLikes.gridx = 0;
		gbc_panelLikes.gridy = 1;
		panelDrc.add(panelLikes, gbc_panelLikes);
		GridBagLayout gbl_panelLikes = new GridBagLayout();
		gbl_panelLikes.columnWidths = new int[]{18, 56, 0};
		gbl_panelLikes.rowHeights = new int[]{0, 0};
		gbl_panelLikes.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelLikes.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelLikes.setLayout(gbl_panelLikes);
		
		JPanel panelLikesIcon = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelLikesIcon = new GridBagConstraints();
		gbc_panelLikesIcon.insets = new Insets(0, 0, 0, 5);
		gbc_panelLikesIcon.fill = GridBagConstraints.BOTH;
		gbc_panelLikesIcon.gridx = 0;
		gbc_panelLikesIcon.gridy = 0;
		panelLikes.add(panelLikesIcon, gbc_panelLikesIcon);
		panelLikesIcon.setLayout(new BorderLayout(0, 0));
		
		JLabel likesIcon = new JLabel();
			likesIcon.setIcon(new ImageIcon(like));
		likesIcon.setHorizontalAlignment(SwingConstants.CENTER);
		panelLikesIcon.add(likesIcon, BorderLayout.CENTER);
		
		JPanel panelLikesData = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelLikesData = new GridBagConstraints();
		gbc_panelLikesData.fill = GridBagConstraints.BOTH;
		gbc_panelLikesData.gridx = 1;
		gbc_panelLikesData.gridy = 0;
		panelLikes.add(panelLikesData, gbc_panelLikesData);
		panelLikesData.setLayout(new BorderLayout(0, 0));
		
		likesData = new JLabel();
		likesData.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		likesData.setHorizontalAlignment(SwingConstants.LEFT);
		if(publi.getLikes()==1) likesData.setText("1 like");
		else likesData.setText(publi.getLikes() + " likes");
		panelLikesData.add(likesData, BorderLayout.CENTER);
		
		JPanel panelComments = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelComments = new GridBagConstraints();
		gbc_panelComments.insets = new Insets(0, 0, 5, 0);
		gbc_panelComments.fill = GridBagConstraints.BOTH;
		gbc_panelComments.gridx = 0;
		gbc_panelComments.gridy = 2;
		panelDrc.add(panelComments, gbc_panelComments);
		GridBagLayout gbl_panelComments = new GridBagLayout();
		gbl_panelComments.columnWidths = new int[]{18, 0, 0};
		gbl_panelComments.rowHeights = new int[]{0, 0};
		gbl_panelComments.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelComments.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelComments.setLayout(gbl_panelComments);
		
		JPanel panelCommIcon = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelCommIcon = new GridBagConstraints();
		gbc_panelCommIcon.insets = new Insets(0, 0, 0, 5);
		gbc_panelCommIcon.fill = GridBagConstraints.BOTH;
		gbc_panelCommIcon.gridx = 0;
		gbc_panelCommIcon.gridy = 0;
		panelComments.add(panelCommIcon, gbc_panelCommIcon);
		panelCommIcon.setLayout(new BorderLayout(0, 0));
		
		JLabel commIcon = new JLabel("");
			commIcon.setIcon(new ImageIcon(comment));
		panelCommIcon.add(commIcon, BorderLayout.CENTER);
		
		JPanel panelCommData = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelCommData = new GridBagConstraints();
		gbc_panelCommData.fill = GridBagConstraints.BOTH;
		gbc_panelCommData.gridx = 1;
		gbc_panelCommData.gridy = 0;
		panelComments.add(panelCommData, gbc_panelCommData);
		panelCommData.setLayout(new BorderLayout(0, 0));
		
		commData = new JLabel();
		commData.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		if(publi.getComentarios().size()==1) commData.setText("1 comentario");
		else commData.setText(publi.getComentarios().size() + " comentarios");
		panelCommData.add(commData, BorderLayout.CENTER);
		
		JPanel panelTiempo = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelTiempo = new GridBagConstraints();
		gbc_panelTiempo.fill = GridBagConstraints.BOTH;
		gbc_panelTiempo.gridx = 0;
		gbc_panelTiempo.gridy = 4;
		panelDrc.add(panelTiempo, gbc_panelTiempo);
		panelTiempo.setLayout(new BorderLayout(0, 0));
		
		long min = ChronoUnit.MINUTES.between(publi.getFecha(), LocalDateTime.now()), hrs;
		JLabel timeLabel = new JLabel();
		if(min>59) {
			hrs = min/60;
			if(hrs==1) timeLabel.setText("Hace una hora ");
			else timeLabel.setText("Hace " + hrs + " horas ");
		} else {
			if(min==0) timeLabel.setText("Ahora mismo ");
			else if(min==1) timeLabel.setText("Hace un minuto ");
			else timeLabel.setText("Hace " + min + " minutos ");
		}
		timeLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		timeLabel.setFont(new Font("Tahoma", Font.ITALIC, 12));
		panelTiempo.add(timeLabel, BorderLayout.CENTER);
	}
	
}
