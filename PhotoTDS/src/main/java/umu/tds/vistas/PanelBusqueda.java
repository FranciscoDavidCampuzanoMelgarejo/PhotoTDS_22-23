package umu.tds.vistas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle.Control;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.pojos.Publicacion;
import umu.tds.modelo.pojos.Usuario;
import umu.tds.vistas.lists.HashListRenderer;
import umu.tds.vistas.lists.PubliListRenderer;
import umu.tds.vistas.lists.UserListPanel;
import umu.tds.vistas.lists.UserListRenderer;

import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Color;

/**
 * @title Panel de búsqueda
 * @author diego
 * */
public class PanelBusqueda extends JPanel{
	private static final long serialVersionUID = 1L;
	private CardLayout layout1, layout2;
	private JTextField barraBusqueda;
	private BufferedImage finder, jo;
	private JList<Usuario> userJList;
	private JList<String> hashJList;
	private JList<Publicacion> hashPubliJList;
	private DefaultListModel<Usuario> userListModel;
	private DefaultListModel<String> hashListModel;
	private DefaultListModel<Publicacion> hashPubliListModel;
	private JPanel panelResultado, panelHashPublis;
	
	/* Constructor del panel */
	public PanelBusqueda() {
		super();
		userListModel = new DefaultListModel<Usuario>();
		hashListModel = new DefaultListModel<String>();
		hashPubliListModel = new DefaultListModel<Publicacion>();
		setOpaque(false);
		cargarRecursos();
		dibujar();
	}
	
	private void recargarListaUsuarios(Pattern pat) {
		List<Usuario> l = Controlador.getControlador().getUsersByER(pat);
		if(!l.isEmpty()) {
			layout1.show(panelResultado, "panelUsuarios");
			userListModel.clear();
			userListModel.addAll(l);
			userJList.revalidate();
		}
		else layout1.show(panelResultado, "panelNoResultado");
	}

	
	private void recargarListaHashtags(String hashtags) {
		List<String> hs = Controlador.getControlador().getHashtagList(hashtags);
		System.err.println(hs);
		if(!hs.isEmpty()) {
			layout1.show(panelResultado, "panelHashtags");
			hashListModel.clear();
			hashListModel.addAll(hs);
			hashJList.revalidate();
		} else layout1.show(panelResultado, "panelNoResultado");
	}
	
	private void recargarListPublis(String hashtag) {
		List<Publicacion> ps = Controlador.getControlador().getPublisHashtag(hashtag);
		System.err.println(ps);
		if(!ps.isEmpty()) {
			layout2.show(panelHashPublis, "panelHashPubliList");
			hashPubliListModel.clear();
			hashPubliListModel.addAll(ps);
			hashPubliJList.revalidate();
		} else layout2.show(panelHashPublis, "panelNoHashtag");
	}
	
	/* Precargar recursos gráficos */
	private void cargarRecursos() {
		try {
			finder = ImageIO.read(getClass().getResource("/imagenes/dock/finder.png"));
			jo = ImageIO.read(getClass().getResource("/imagenes/pc-jo.png"));
		} catch (IOException ioe) { ioe.printStackTrace(); }
	}
	
	/* Dibujado del panel */
	private void dibujar() {
		GridBagLayout g = new GridBagLayout();
		g.columnWidths = new int[]{0, 0};
		g.rowHeights = new int[]{80, 0, 0};
		g.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		g.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(g);
		
		JPanel panelFinder = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelFinder = new GridBagConstraints();
		gbc_panelFinder.fill = GridBagConstraints.BOTH;
		gbc_panelFinder.gridx = 0;
		gbc_panelFinder.gridy = 0;
		add(panelFinder, gbc_panelFinder);
		GridBagLayout gbl_panelFinder = new GridBagLayout();
		gbl_panelFinder.columnWidths = new int[]{0, 256, 48, 0, 0};
		gbl_panelFinder.rowHeights = new int[]{0, 48, 0, 0};
		gbl_panelFinder.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelFinder.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		panelFinder.setLayout(gbl_panelFinder);
		
		JPanel panelBarraBusqueda = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelBarraBusqueda = new GridBagConstraints();
		gbc_panelBarraBusqueda.insets = new Insets(10, 0, 10, 0);
		gbc_panelBarraBusqueda.fill = GridBagConstraints.BOTH;
		gbc_panelBarraBusqueda.gridx = 1;
		gbc_panelBarraBusqueda.gridy = 1;
		panelFinder.add(panelBarraBusqueda, gbc_panelBarraBusqueda);
		panelBarraBusqueda.setLayout(new BorderLayout(0, 0));
		
		barraBusqueda = new JTextField();
		barraBusqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(barraBusqueda.isFocusOwner() && e.getKeyCode()==KeyEvent.VK_ENTER) {
					Pattern pat = Pattern.compile(barraBusqueda.getText());
					Pattern h = Pattern.compile("#[^ ]*");
					if(h.matcher(pat.pattern()).find()) {
						recargarListaHashtags(barraBusqueda.getText());
					} else {
						recargarListaUsuarios(pat);
					}
				}
			}
		});
		barraBusqueda.setToolTipText("Busca por hashtag, por nombre de usuario o correo");
		barraBusqueda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelBarraBusqueda.add(barraBusqueda, BorderLayout.CENTER);
		barraBusqueda.setColumns(10);
		
		JLabel searchIcon = new JLabel();
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				searchIcon.setIcon(new ImageIcon(Utils.reescalar(44, 44, finder)));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				searchIcon.setIcon(new ImageIcon(Utils.reescalar(40, 40, finder)));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				searchIcon.setIcon(new ImageIcon(Utils.cambiarBrillo(0.7f, Utils.reescalar(44, 44, finder))));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				searchIcon.setIcon(new ImageIcon(Utils.reescalar(40, 40, finder)));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Pattern pat = Pattern.compile(barraBusqueda.getText());
				Pattern h = Pattern.compile("#[^ ]*");
				if(h.matcher(pat.pattern()).find()) {
					recargarListaHashtags(barraBusqueda.getText());
				} else {
					recargarListaUsuarios(pat);
				}
			}
		});
		searchIcon.setIcon(new ImageIcon(Utils.reescalar(40, 40, finder)));
		searchIcon.setToolTipText("Buscar");
		searchIcon.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_searchIcon = new GridBagConstraints();
		gbc_searchIcon.gridx = 2;
		gbc_searchIcon.gridy = 1;
		panelFinder.add(searchIcon, gbc_searchIcon);
		
		panelResultado = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelResultado = new GridBagConstraints();
		gbc_panelResultado.fill = GridBagConstraints.BOTH;
		gbc_panelResultado.gridx = 0;
		gbc_panelResultado.gridy = 1;
		add(panelResultado, gbc_panelResultado);
		panelResultado.setLayout(new CardLayout(0, 0));
		layout1 = (CardLayout) panelResultado.getLayout();
		
		userJList = new JList<Usuario>() {
			private static final long serialVersionUID = 1L;
			{setOpaque(false);}
		};
			userJList.setCellRenderer(new UserListRenderer());
			userJList.setModel(userListModel);
		
		JPanel panelUsuarios = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		panelResultado.add(panelUsuarios, "panelUsuarios");
		panelUsuarios.setLayout(new BorderLayout(0, 0));
		panelUsuarios.add(userJList, BorderLayout.CENTER);

		
		JPanel panelHashtags = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		panelResultado.add(panelHashtags, "panelHashtags");
		GridBagLayout gbl_panelHashtags = new GridBagLayout();
		gbl_panelHashtags.columnWidths = new int[]{0, 0};
		gbl_panelHashtags.rowHeights = new int[]{150, 0, 0};
		gbl_panelHashtags.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelHashtags.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panelHashtags.setLayout(gbl_panelHashtags);
		
		JPanel panelListaHashtags = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelListaHashtags = new GridBagConstraints();
		gbc_panelListaHashtags.insets = new Insets(0, 0, 5, 0);
		gbc_panelListaHashtags.fill = GridBagConstraints.BOTH;
		gbc_panelListaHashtags.gridx = 0;
		gbc_panelListaHashtags.gridy = 0;
		panelHashtags.add(panelListaHashtags, gbc_panelListaHashtags);
		GridBagLayout gbl_panelListaHashtags = new GridBagLayout();
		gbl_panelListaHashtags.columnWidths = new int[]{0, 304, 0, 0};
		gbl_panelListaHashtags.rowHeights = new int[]{150, 0};
		gbl_panelListaHashtags.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelListaHashtags.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelListaHashtags.setLayout(gbl_panelListaHashtags);
		
		JScrollPane scrollListaHash = new JScrollPane();
		scrollListaHash.setOpaque(false);
		scrollListaHash.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollListaHash = new GridBagConstraints();
		gbc_scrollListaHash.fill = GridBagConstraints.BOTH;
		gbc_scrollListaHash.gridx = 1;
		gbc_scrollListaHash.gridy = 0;
		panelListaHashtags.add(scrollListaHash, gbc_scrollListaHash);
		
		hashJList = new JList<String>();
		hashJList.setOpaque(false);
		hashJList.setModel(hashListModel);
		hashJList.setCellRenderer(new HashListRenderer());
		hashJList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				recargarListPublis(hashJList.getSelectedValue());
			}
		});
		scrollListaHash.setViewportView(hashJList);
		scrollListaHash.getViewport().setOpaque(false);
			
		panelHashPublis = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelHashPublis = new GridBagConstraints();
		gbc_panelHashPublis.fill = GridBagConstraints.BOTH;
		gbc_panelHashPublis.gridx = 0;
		gbc_panelHashPublis.gridy = 1;
		panelHashtags.add(panelHashPublis, gbc_panelHashPublis);
		panelHashPublis.setLayout(new CardLayout(0, 0));
		layout2 = (CardLayout) panelHashPublis.getLayout();
		
		JPanel panelNoHashtag = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		panelHashPublis.add(panelNoHashtag, "panelNoHashtag");
		GridBagLayout gbl_panelNoPublis = new GridBagLayout();
		gbl_panelNoPublis.columnWidths = new int[]{0, 0};
		gbl_panelNoPublis.rowHeights = new int[]{250, 48, 0};
		gbl_panelNoPublis.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelNoPublis.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panelNoHashtag.setLayout(gbl_panelNoPublis);
		
		JPanel panelJoo = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelJoo = new GridBagConstraints();
		gbc_panelJoo.insets = new Insets(0, 0, 5, 0);
		gbc_panelJoo.fill = GridBagConstraints.BOTH;
		gbc_panelJoo.gridx = 0;
		gbc_panelJoo.gridy = 0;
		panelNoHashtag.add(panelJoo, gbc_panelJoo);
		panelJoo.setLayout(new BorderLayout(0, 0));
		
		float w = jo.getWidth(), h = jo.getHeight();
		JLabel jooIcon = new JLabel();
			jooIcon.setIcon(new ImageIcon(Utils.reescalar((int)(w * 200/w), (int)(h * 200/h), jo)));
		jooIcon.setHorizontalAlignment(SwingConstants.CENTER);
		panelJoo.add(jooIcon, BorderLayout.CENTER);
		
		JPanel panelJooInfo = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelJooInfo = new GridBagConstraints();
		gbc_panelJooInfo.fill = GridBagConstraints.BOTH;
		gbc_panelJooInfo.gridx = 0;
		gbc_panelJooInfo.gridy = 1;
		panelNoHashtag.add(panelJooInfo, gbc_panelJooInfo);
		panelJooInfo.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Selecciona un hashtag de la lista");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		panelJooInfo.add(lblNewLabel, BorderLayout.NORTH);
		
		hashPubliJList = new JList<Publicacion>();
		hashPubliJList.setCellRenderer(new PubliListRenderer(128));
		hashPubliJList.setModel(hashPubliListModel);
		hashPubliJList.setOpaque(false);
		
		JScrollPane panelHashPubliList = new JScrollPane();
		panelHashPubliList.setOpaque(false);
		panelHashPubliList.setViewportBorder(null);
		panelHashPubliList.setViewportView(hashPubliJList);
		panelHashPubliList.getViewport().setOpaque(false);
		panelHashPubliList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelHashPublis.add(panelHashPubliList, "panelHashPubliList");
		
		JPanel panelNoResultados = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		panelResultado.add(panelNoResultados, "panelNoResultado");
		GridBagLayout gbl_panelNoResultados = new GridBagLayout();
		gbl_panelNoResultados.columnWidths = new int[]{0, 0};
		gbl_panelNoResultados.rowHeights = new int[]{300, 0, 0};
		gbl_panelNoResultados.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelNoResultados.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panelNoResultados.setLayout(gbl_panelNoResultados);
		
		JPanel panelJo = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelJo = new GridBagConstraints();
		gbc_panelJo.insets = new Insets(0, 0, 5, 0);
		gbc_panelJo.fill = GridBagConstraints.BOTH;
		gbc_panelJo.gridx = 0;
		gbc_panelJo.gridy = 0;
		panelNoResultados.add(panelJo, gbc_panelJo);
		GridBagLayout gbl_panelJo = new GridBagLayout();
		gbl_panelJo.columnWidths = new int[]{0, 0};
		gbl_panelJo.rowHeights = new int[]{256, 0, 0};
		gbl_panelJo.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelJo.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panelJo.setLayout(gbl_panelJo);
		
		JPanel panelJoIcon = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelJoIcon = new GridBagConstraints();
		gbc_panelJoIcon.insets = new Insets(0, 0, 5, 0);
		gbc_panelJoIcon.fill = GridBagConstraints.BOTH;
		gbc_panelJoIcon.gridx = 0;
		gbc_panelJoIcon.gridy = 0;
		panelJo.add(panelJoIcon, gbc_panelJoIcon);
		panelJoIcon.setLayout(new BorderLayout(0, 0));
		
		JLabel joIcon = new JLabel();
			joIcon.setIcon(new ImageIcon(Utils.reescalar((int)(w * 200/w), (int)(h * 200/h), jo)));
		joIcon.setHorizontalAlignment(SwingConstants.CENTER);
		panelJoIcon.add(joIcon, BorderLayout.CENTER);
		
		JPanel panelJoInfo = new JPanel() {
			private static final long serialVersionUID = 1L;
			{ setOpaque(false); } 
		};
		GridBagConstraints gbc_panelJoInfo = new GridBagConstraints();
		gbc_panelJoInfo.fill = GridBagConstraints.BOTH;
		gbc_panelJoInfo.gridx = 0;
		gbc_panelJoInfo.gridy = 1;
		panelJo.add(panelJoInfo, gbc_panelJoInfo);
		panelJoInfo.setLayout(new BorderLayout(0, 0));
		
		JLabel joInfo = new JLabel("No se han encontrado resultados");
		joInfo.setHorizontalAlignment(SwingConstants.CENTER);
		joInfo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		panelJoInfo.add(joInfo, BorderLayout.CENTER);
	}
}
