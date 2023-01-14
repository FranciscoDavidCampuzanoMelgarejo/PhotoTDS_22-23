package umu.tds.vistas.perfil;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingConstants;

import umu.tds.controlador.Controlador;
import umu.tds.vistas.CampoVacioException;
import umu.tds.vistas.FormatoNoAdecuadoException;
import umu.tds.vistas.Utils;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;

public class PanelPassword extends JPanel {

	private String password;
	private JDialog dialogoPadre;

	private JPasswordField campoPasswordAntigua;
	private JPasswordField campoPasswordNueva;
	private JPasswordField campoPasswordConfirmacion;

	private JLabel lblErrorPassAntigua;
	private JLabel lblErrorPassNueva;
	private JLabel lblErrorPassConf;

	private List<JPasswordField> campos;
	private List<JLabel> etiquetas;

	// Imagenes
	private Image iconoQuestion;

	public PanelPassword(JDialog dialogo, String password) {
		this.dialogoPadre = dialogo;
		this.password = password;
		this.iconoQuestion = new ImageIcon(getClass().getResource("/imagenes/question-white.png")).getImage()
				.getScaledInstance(35, 35, Image.SCALE_SMOOTH);

		// Campos contraseña
		this.campoPasswordAntigua = new JPasswordField();
		this.campoPasswordNueva = new JPasswordField();
		this.campoPasswordConfirmacion = new JPasswordField();

		// Etiquetas de error
		this.lblErrorPassAntigua = new JLabel();

		this.lblErrorPassNueva = new JLabel();
		this.lblErrorPassConf = new JLabel();

		campos = Arrays.asList(campoPasswordAntigua, campoPasswordNueva, campoPasswordConfirmacion);
		etiquetas = Arrays.asList(lblErrorPassAntigua, lblErrorPassNueva, lblErrorPassConf);
		crearPanel();
	}

	public void limpiar() {
		for (JLabel etiqueta : etiquetas)
			etiqueta.setVisible(false);
		for (JPasswordField campo : campos)
			campo.setText(null);
	}

	private void crearPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 40, 0, 0, 40, 0 };
		gridBagLayout.rowHeights = new int[] { 40, 0, 25, 0, 35, 0, 35, 0, 0, 20, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JButton btnQuestion = new JButton(new ImageIcon(iconoQuestion));
		btnQuestion.setVerticalAlignment(SwingConstants.BOTTOM);
		btnQuestion.setBorderPainted(false);
		btnQuestion.setContentAreaFilled(false);
		GridBagConstraints gbc_btnQuestion = new GridBagConstraints();
		gbc_btnQuestion.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnQuestion.insets = new Insets(0, 0, 0, 5);
		gbc_btnQuestion.gridx = 2;
		gbc_btnQuestion.gridy = 1;
		add(btnQuestion, gbc_btnQuestion);

		// Al pulsar el boton de pregunta, se debe abrir un pequeño dialogo que
		// muestre los requisitos que debe cumplir una contraseña valida
		btnQuestion.addActionListener((ActionEvent e) -> {
			JOptionPane.showMessageDialog(dialogoPadre,
					"Una contraseña válida debe contener una letra minúsucla, una mayúscula, un número y un caracter especial de entre los siguientes [-_$?!%.]",
					"Requisitos para una constraseña válida", JOptionPane.INFORMATION_MESSAGE);
		});

		lblErrorPassAntigua.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblErrorPassAntigua.setForeground(new Color(255, 106, 106));
		lblErrorPassAntigua.setVisible(false);
		GridBagConstraints gbc_lblErrorPassAntigua = new GridBagConstraints();
		gbc_lblErrorPassAntigua.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblErrorPassAntigua.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorPassAntigua.gridx = 2;
		gbc_lblErrorPassAntigua.gridy = 2;
		add(lblErrorPassAntigua, gbc_lblErrorPassAntigua);

		JLabel lblPasswordAnt = new JLabel("<html>Contraseña<br/>anterior</html>");
		lblPasswordAnt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblPasswordAnt = new GridBagConstraints();
		gbc_lblPasswordAnt.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPasswordAnt.insets = new Insets(0, 0, 5, 20);
		gbc_lblPasswordAnt.gridx = 1;
		gbc_lblPasswordAnt.gridy = 3;
		add(lblPasswordAnt, gbc_lblPasswordAnt);

		GridBagConstraints gbc_campoPasswordAntigua = new GridBagConstraints();
		gbc_campoPasswordAntigua.anchor = GridBagConstraints.NORTHWEST;
		gbc_campoPasswordAntigua.insets = new Insets(0, 0, 5, 5);
		gbc_campoPasswordAntigua.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoPasswordAntigua.gridx = 2;
		gbc_campoPasswordAntigua.gridy = 3;
		add(campoPasswordAntigua, gbc_campoPasswordAntigua);
		campoPasswordAntigua.setColumns(20);

		lblErrorPassNueva.setVisible(false);
		lblErrorPassNueva.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblErrorPassNueva.setForeground(new Color(255, 106, 106));
		GridBagConstraints gbc_lblErrorPassNueva = new GridBagConstraints();
		gbc_lblErrorPassNueva.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblErrorPassNueva.insets = new Insets(10, 0, 5, 5);
		gbc_lblErrorPassNueva.gridx = 2;
		gbc_lblErrorPassNueva.gridy = 4;
		add(lblErrorPassNueva, gbc_lblErrorPassNueva);

		JLabel lblPasswordNueva = new JLabel("<html>Contraseña<br/>nueva</html>");
		lblPasswordNueva.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblPasswordNueva = new GridBagConstraints();
		gbc_lblPasswordNueva.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPasswordNueva.insets = new Insets(0, 0, 5, 20);
		gbc_lblPasswordNueva.gridx = 1;
		gbc_lblPasswordNueva.gridy = 5;
		add(lblPasswordNueva, gbc_lblPasswordNueva);

		GridBagConstraints gbc_campoPasswordNueva = new GridBagConstraints();
		gbc_campoPasswordNueva.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoPasswordNueva.anchor = GridBagConstraints.NORTHWEST;
		gbc_campoPasswordNueva.insets = new Insets(0, 0, 5, 5);
		gbc_campoPasswordNueva.gridx = 2;
		gbc_campoPasswordNueva.gridy = 5;
		add(campoPasswordNueva, gbc_campoPasswordNueva);
		campoPasswordNueva.setColumns(20);

		lblErrorPassConf.setVisible(false);
		lblErrorPassConf.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblErrorPassConf.setForeground(new Color(255, 106, 106));
		GridBagConstraints gbc_lblErrorPassConf = new GridBagConstraints();
		gbc_lblErrorPassConf.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblErrorPassConf.insets = new Insets(10, 0, 5, 5);
		gbc_lblErrorPassConf.gridx = 2;
		gbc_lblErrorPassConf.gridy = 6;
		add(lblErrorPassConf, gbc_lblErrorPassConf);

		JLabel lblPasswordConf = new JLabel("<html>Confirmar<br/>contraseña</html>");
		lblPasswordConf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblPasswordConf = new GridBagConstraints();
		gbc_lblPasswordConf.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPasswordConf.insets = new Insets(0, 0, 5, 20);
		gbc_lblPasswordConf.gridx = 1;
		gbc_lblPasswordConf.gridy = 7;
		add(lblPasswordConf, gbc_lblPasswordConf);

		GridBagConstraints gbc_campoPasswordConfirmacion = new GridBagConstraints();
		gbc_campoPasswordConfirmacion.anchor = GridBagConstraints.NORTHWEST;
		gbc_campoPasswordConfirmacion.insets = new Insets(0, 0, 5, 5);
		gbc_campoPasswordConfirmacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoPasswordConfirmacion.gridx = 2;
		gbc_campoPasswordConfirmacion.gridy = 7;
		add(campoPasswordConfirmacion, gbc_campoPasswordConfirmacion);
		campoPasswordConfirmacion.setColumns(20);

		JButton btnCambiarPassword = new JButton("Cambiar contraseña");
		btnCambiarPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCambiarPassword.setBackground(new Color(28, 84, 215));
		btnCambiarPassword.setForeground(new Color(200, 200, 200));
		GridBagConstraints gbc_btnCambiarPassword = new GridBagConstraints();
		gbc_btnCambiarPassword.ipady = 4;
		gbc_btnCambiarPassword.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCambiarPassword.insets = new Insets(20, 0, 5, 5);
		gbc_btnCambiarPassword.gridx = 2;
		gbc_btnCambiarPassword.gridy = 8;
		add(btnCambiarPassword, gbc_btnCambiarPassword);

		// Al clickar en el boton, validar los campos
		btnCambiarPassword.addActionListener((ActionEvent e) -> {

			if (validarCampos()) {
				boolean passwordsValidas = true;
				String passAntigua = String.valueOf(campoPasswordAntigua.getPassword());
				if (!passAntigua.equals(Controlador.getControlador().getUsuarioLogueado().getPassword())) {
					passwordsValidas = false;
					lblErrorPassAntigua.setText(" - Contraseña incorrecta ");
					lblErrorPassAntigua.setVisible(true);
				}

				String passNueva = String.valueOf(campoPasswordNueva.getPassword());
				String passConf = String.valueOf(campoPasswordConfirmacion.getPassword());

				if (!passNueva.equals(passConf)) {
					passwordsValidas = false;
					lblErrorPassConf.setText(" - La contraseña no coincide ");
					lblErrorPassConf.setVisible(true);
				}

				if (passwordsValidas) {
					// Cambiar la contraseña -> Llamar al Controlador
					Controlador.getControlador().cambiarPassword(passNueva);
					Image iconoReescalado = new ImageIcon(getClass().getResource("/imagenes/check-mark.png")).getImage()
							.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
					JOptionPane.showMessageDialog(this, "La contraseña ha sido cambiada exitosamente",
							"Contraseña Guardada", JOptionPane.INFORMATION_MESSAGE,
							new ImageIcon(iconoReescalado));
				}
			}

		});

	}

	private boolean validarCampos() {
		boolean valido = true;
		String texto;

		for (int i = 0; i < 3; i++) {
			JPasswordField campo = campos.get(i);
			JLabel etiqueta = etiquetas.get(i);
			etiqueta.setVisible(false);
			System.out.println(campo.getPassword());
			System.out.println(etiqueta.getText());

			try {
				texto = String.valueOf(campo.getPassword());
				System.out.println(texto);
				Utils.campoVacio(texto);
				Utils.formatoErroneo(texto, "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[-_$?!%.]).*");
			} catch (CampoVacioException ex1) {
				valido = false;
				etiqueta.setText(" - Este campo no puede esta vacío ");
				etiqueta.setVisible(true);
			} catch (FormatoNoAdecuadoException ex2) {
				valido = false;
				etiqueta.setText(" - Formato incorrecto ");
				etiqueta.setVisible(true);
			}

		}

		return valido;

	}

}
