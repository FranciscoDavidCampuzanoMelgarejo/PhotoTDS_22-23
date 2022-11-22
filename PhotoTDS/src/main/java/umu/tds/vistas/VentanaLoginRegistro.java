package umu.tds.vistas;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.toedter.calendar.*;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class VentanaLoginRegistro {
	
	public final static String PANEL_LOGIN = "Panel de Login";
	public final static String PANEL_REGISTRO = "Panel de Registro";

	private JFrame frame;
	private CardLayout c;
	private PanelLogin panelLogin;
	private PanelRegistro panelRegistro;

	/* Constructor de ventana */
	public VentanaLoginRegistro() {
		initialize();
	}

	/* Mostrar la ventana */
	public void mostrar() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/* Ocultar la ventana */
	public void ocultar() {
		frame.setVisible(false);
	}

	/* Destruye la ventanica y libera recursos manualmente */
	public void destruir() {
		this.frame.dispose();
	}

	/* Hacemos la ventanica */
	private void initialize() {

		frame = new JFrame();
		frame.setTitle("PhotoTDS: Inicio de sesión");
		frame.setBounds(100, 100, 500, 740);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		c = new CardLayout(0,0);
		frame.getContentPane().setLayout(c);

		panelLogin = new PanelLogin(frame);
		panelRegistro = new PanelRegistro(frame);

		frame.getContentPane().add(panelLogin, PANEL_LOGIN);
		frame.getContentPane().add(panelRegistro, PANEL_REGISTRO);
	}

	/* Cambio de panel del cardlayout */
	public void cambiarPanel(String panel) {
		c.show(frame, panel);
	}

	/* Mostrar popup de error de tamaño de fichero */
	public void mostrarErrorTamaño() {
		JOptionPane.showMessageDialog(frame, "Solo se admiten imágenes de hasta 100x100 píxeles",
				"Error al cargar imagen de perfil", JOptionPane.ERROR_MESSAGE);
	}

	/* Mostrar popup de error de */
}
