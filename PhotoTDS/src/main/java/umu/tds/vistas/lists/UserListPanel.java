package umu.tds.vistas.lists;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import umu.tds.modelo.pojos.Usuario;
import umu.tds.vistas.Utils;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class UserListPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private BufferedImage user, publi, seguidores;
	
	/* Precargar recursos */
	private void cargarRecursos() {
		try {
			user = (usuario.getPerfil().getFoto()==null) ? ImageIO.read(getClass().getResource("/imagenes/noprofilepic.png"))
														: ImageIO.read(new File(usuario.getPerfil().getFoto()));
			seguidores = ImageIO.read(getClass().getResource("/imagenes/noprofilepic.png"));
			publi = ImageIO.read(getClass().getResource("/imagenes/dock/publi.png"));
		} catch (IOException ioe) { ioe.printStackTrace(); }
	}
	
	/* Dibujado */
	private void dibujar() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 0, 0};
		gridBagLayout.rowHeights = new int[]{100, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panelFoto = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelFoto = new GridBagConstraints();
		gbc_panelFoto.insets = new Insets(0, 0, 0, 5);
		gbc_panelFoto.fill = GridBagConstraints.BOTH;
		gbc_panelFoto.gridx = 0;
		gbc_panelFoto.gridy = 0;
		add(panelFoto, gbc_panelFoto);
		panelFoto.setLayout(new BorderLayout(0, 0));
		
		JLabel fotoIcon = new JLabel();
			fotoIcon.setIcon(new ImageIcon(Utils.redondear(80, user)));
		fotoIcon.setHorizontalAlignment(SwingConstants.CENTER);
		panelFoto.add(fotoIcon, BorderLayout.CENTER);
		
		JPanel panelData = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelData = new GridBagConstraints();
		gbc_panelData.fill = GridBagConstraints.BOTH;
		gbc_panelData.gridx = 1;
		gbc_panelData.gridy = 0;
		add(panelData, gbc_panelData);
		GridBagLayout gbl_panelData = new GridBagLayout();
		gbl_panelData.columnWidths = new int[]{16, 0, 0};
		gbl_panelData.rowHeights = new int[]{48, 0, 0};
		gbl_panelData.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelData.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panelData.setLayout(gbl_panelData);
		
		JPanel panelUser = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelUser = new GridBagConstraints();
		gbc_panelUser.insets = new Insets(0, 0, 5, 0);
		gbc_panelUser.fill = GridBagConstraints.BOTH;
		gbc_panelUser.gridx = 1;
		gbc_panelUser.gridy = 0;
		panelData.add(panelUser, gbc_panelUser);
		panelUser.setLayout(new BorderLayout(0, 0));
		
		JLabel loginname = new JLabel(usuario.getUsuario());
		loginname.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		panelUser.add(loginname, BorderLayout.CENTER);
		
		JLabel username = new JLabel(usuario.getNombre());
		username.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panelUser.add(username, BorderLayout.SOUTH);
		
		JPanel panelInfo = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelInfo = new GridBagConstraints();
		gbc_panelInfo.fill = GridBagConstraints.BOTH;
		gbc_panelInfo.gridx = 1;
		gbc_panelInfo.gridy = 1;
		panelData.add(panelInfo, gbc_panelInfo);
		GridBagLayout gbl_panelInfo = new GridBagLayout();
		gbl_panelInfo.columnWidths = new int[]{0, 0};
		gbl_panelInfo.rowHeights = new int[]{20, 20, 0};
		gbl_panelInfo.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelInfo.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelInfo.setLayout(gbl_panelInfo);
		
		JPanel panelSeguidores = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelSeguidores = new GridBagConstraints();
		gbc_panelSeguidores.fill = GridBagConstraints.BOTH;
		gbc_panelSeguidores.gridx = 0;
		gbc_panelSeguidores.gridy = 0;
		panelInfo.add(panelSeguidores, gbc_panelSeguidores);
		GridBagLayout gbl_panelSeguidores = new GridBagLayout();
		gbl_panelSeguidores.columnWidths = new int[]{20, 0, 0};
		gbl_panelSeguidores.rowHeights = new int[]{20, 0};
		gbl_panelSeguidores.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelSeguidores.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelSeguidores.setLayout(gbl_panelSeguidores);
		
		JPanel panelSeguidoresIcon = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelSeguidoresIcon = new GridBagConstraints();
		gbc_panelSeguidoresIcon.insets = new Insets(0, 0, 0, 5);
		gbc_panelSeguidoresIcon.fill = GridBagConstraints.BOTH;
		gbc_panelSeguidoresIcon.gridx = 0;
		gbc_panelSeguidoresIcon.gridy = 0;
		panelSeguidores.add(panelSeguidoresIcon, gbc_panelSeguidoresIcon);
		panelSeguidoresIcon.setLayout(new BorderLayout(0, 0));
		
		JLabel seguidoresIcon = new JLabel();
			seguidoresIcon.setIcon(new ImageIcon(Utils.reescalar(20, 20, seguidores)));
		panelSeguidoresIcon.add(seguidoresIcon, BorderLayout.CENTER);
		
		JPanel panelSeguidoresInfo = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelSeguidoresInfo = new GridBagConstraints();
		gbc_panelSeguidoresInfo.fill = GridBagConstraints.BOTH;
		gbc_panelSeguidoresInfo.gridx = 1;
		gbc_panelSeguidoresInfo.gridy = 0;
		panelSeguidores.add(panelSeguidoresInfo, gbc_panelSeguidoresInfo);
		panelSeguidoresInfo.setLayout(new BorderLayout(0, 0));
		
		JLabel numSeguidores = new JLabel();
		numSeguidores.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		panelSeguidoresInfo.add(numSeguidores, BorderLayout.CENTER);
		int numS = usuario.getSeguidores().size();
		if(numS==0) numSeguidores.setText("No tiene seguidores");
		else if(numS==1) numSeguidores.setText("Un seguidor");
		else numSeguidores.setText(numS + " seguidores");
		
		JPanel panelPubli = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelPubli = new GridBagConstraints();
		gbc_panelPubli.fill = GridBagConstraints.BOTH;
		gbc_panelPubli.gridx = 0;
		gbc_panelPubli.gridy = 1;
		panelInfo.add(panelPubli, gbc_panelPubli);
		GridBagLayout gbl_panelPubli = new GridBagLayout();
		gbl_panelPubli.columnWidths = new int[]{20, 0, 0};
		gbl_panelPubli.rowHeights = new int[]{20, 0};
		gbl_panelPubli.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelPubli.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelPubli.setLayout(gbl_panelPubli);
		
		JPanel panelPubliIcon = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelPubliIcon = new GridBagConstraints();
		gbc_panelPubliIcon.insets = new Insets(0, 0, 0, 5);
		gbc_panelPubliIcon.fill = GridBagConstraints.BOTH;
		gbc_panelPubliIcon.gridx = 0;
		gbc_panelPubliIcon.gridy = 0;
		panelPubli.add(panelPubliIcon, gbc_panelPubliIcon);
		panelPubliIcon.setLayout(new BorderLayout(0, 0));
		
		JLabel publiIcon = new JLabel();
			publiIcon.setIcon(new ImageIcon(Utils.reescalar(20, 20, publi)));
		panelPubliIcon.add(publiIcon, BorderLayout.CENTER);
		
		JPanel panelPublisInfo = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelPublisInfo = new GridBagConstraints();
		gbc_panelPublisInfo.fill = GridBagConstraints.BOTH;
		gbc_panelPublisInfo.gridx = 1;
		gbc_panelPublisInfo.gridy = 0;
		panelPubli.add(panelPublisInfo, gbc_panelPublisInfo);
		panelPublisInfo.setLayout(new BorderLayout(0, 0));
		
		int numP = usuario.getPublicaciones().size();
		JLabel numPubli = new JLabel();
		if(numP==0) numPubli.setText("No tiene publicaciones");
		else if(numP==1) numPubli.setText("Una publicacion");
		else numPubli.setText(numP + " publicaciones");
		numPubli.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		panelPublisInfo.add(numPubli, BorderLayout.CENTER);
	}
	
	/* Constructor */
	public UserListPanel(Usuario usuario) {
		super();
		setBorder(new LineBorder(new Color(0x3c-10, 0x3f-10, 0x41-10)));
		this.usuario = usuario;
		cargarRecursos();
		dibujar();
	}
}
