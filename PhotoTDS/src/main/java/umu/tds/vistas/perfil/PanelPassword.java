package umu.tds.vistas.perfil;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PanelPassword extends JPanel {

	private String password;
	private JDialog dialogoPadre;

	private JTextField campoPasswordAntigua;
	private JTextField campoPasswordNueva;
	private JTextField campoPasswordConfirmacion;

	// Imagenes
	private Image iconoQuestion;

	public PanelPassword(JDialog dialogo, String password) {
		this.dialogoPadre = dialogo;
		this.password = password;
		this.iconoQuestion = new ImageIcon(getClass().getResource("/imagenes/question-white.png")).getImage()
				.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 40, 0, 0, 4, 40, 0 };
		gridBagLayout.rowHeights = new int[] { 40, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JButton btnQuestion = new JButton(new ImageIcon(iconoQuestion));
		btnQuestion.setBorderPainted(false);
		btnQuestion.setContentAreaFilled(false);
		GridBagConstraints gbc_btnQuestion = new GridBagConstraints();
		gbc_btnQuestion.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnQuestion.insets = new Insets(0, 0, 5, 5);
		gbc_btnQuestion.gridx = 2;
		gbc_btnQuestion.gridy = 1;
		add(btnQuestion, gbc_btnQuestion);

		// Al pulsar el boton de pregunta, se debe abrir un pequeño dialogo que
		// muestre los requisitos que debe cumplir una contraseña valida
		btnQuestion.addActionListener((ActionEvent e) -> {
			JOptionPane.showMessageDialog(dialogoPadre,
					"Una contraseña valida debe contener una letra minúsucla, /n mayúscula, un número y un caracter especial.",
					"Requisitos para una constraseña válida", JOptionPane.INFORMATION_MESSAGE);
		});

		JLabel lblErrorPassAntigua = new JLabel(" - Formato invalido");
		GridBagConstraints gbc_lblErrorPassAntigua = new GridBagConstraints();
		gbc_lblErrorPassAntigua.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblErrorPassAntigua.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorPassAntigua.gridx = 2;
		gbc_lblErrorPassAntigua.gridy = 2;
		add(lblErrorPassAntigua, gbc_lblErrorPassAntigua);

		JLabel lblPasswordAnt = new JLabel("<html>Contraseña<br/>anterior</html>");
		GridBagConstraints gbc_lblPasswordAnt = new GridBagConstraints();
		gbc_lblPasswordAnt.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPasswordAnt.insets = new Insets(0, 0, 5, 20);
		gbc_lblPasswordAnt.gridx = 1;
		gbc_lblPasswordAnt.gridy = 3;
		add(lblPasswordAnt, gbc_lblPasswordAnt);

		campoPasswordAntigua = new JTextField();
		GridBagConstraints gbc_campoPasswordAntigua = new GridBagConstraints();
		gbc_campoPasswordAntigua.anchor = GridBagConstraints.NORTHWEST;
		gbc_campoPasswordAntigua.insets = new Insets(0, 0, 5, 5);
		gbc_campoPasswordAntigua.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoPasswordAntigua.gridx = 2;
		gbc_campoPasswordAntigua.gridy = 3;
		add(campoPasswordAntigua, gbc_campoPasswordAntigua);
		campoPasswordAntigua.setColumns(25);

		JLabel lblIconoPassAnt = new JLabel("etiqueta");
		GridBagConstraints gbc_lblIconoPassAnt = new GridBagConstraints();
		gbc_lblIconoPassAnt.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblIconoPassAnt.insets = new Insets(0, 0, 5, 5);
		gbc_lblIconoPassAnt.gridx = 3;
		gbc_lblIconoPassAnt.gridy = 3;
		add(lblIconoPassAnt, gbc_lblIconoPassAnt);

		JLabel lblFormatoInvalido = new JLabel(" - Formato invalido");
		GridBagConstraints gbc_lblFormatoInvalido = new GridBagConstraints();
		gbc_lblFormatoInvalido.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblFormatoInvalido.insets = new Insets(10, 0, 5, 5);
		gbc_lblFormatoInvalido.gridx = 2;
		gbc_lblFormatoInvalido.gridy = 4;
		add(lblFormatoInvalido, gbc_lblFormatoInvalido);

		JLabel lblPasswordNueva = new JLabel("<html>Contraseña<br/>nueva</html>");
		GridBagConstraints gbc_lblPasswordNueva = new GridBagConstraints();
		gbc_lblPasswordNueva.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPasswordNueva.insets = new Insets(0, 0, 5, 20);
		gbc_lblPasswordNueva.gridx = 1;
		gbc_lblPasswordNueva.gridy = 5;
		add(lblPasswordNueva, gbc_lblPasswordNueva);

		campoPasswordNueva = new JTextField();
		GridBagConstraints gbc_campoPasswordNueva = new GridBagConstraints();
		gbc_campoPasswordNueva.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoPasswordNueva.anchor = GridBagConstraints.NORTHWEST;
		gbc_campoPasswordNueva.insets = new Insets(0, 0, 5, 5);
		gbc_campoPasswordNueva.gridx = 2;
		gbc_campoPasswordNueva.gridy = 5;
		add(campoPasswordNueva, gbc_campoPasswordNueva);
		campoPasswordNueva.setColumns(25);

		JLabel lblIconoPassNueva = new JLabel("etiqueta");
		GridBagConstraints gbc_lblIconoPassNueva = new GridBagConstraints();
		gbc_lblIconoPassNueva.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblIconoPassNueva.insets = new Insets(0, 0, 5, 5);
		gbc_lblIconoPassNueva.gridx = 3;
		gbc_lblIconoPassNueva.gridy = 5;
		add(lblIconoPassNueva, gbc_lblIconoPassNueva);

		JLabel lblFormatoInvalido_1 = new JLabel(" - Formato invalido");
		GridBagConstraints gbc_lblFormatoInvalido_1 = new GridBagConstraints();
		gbc_lblFormatoInvalido_1.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblFormatoInvalido_1.insets = new Insets(10, 0, 5, 5);
		gbc_lblFormatoInvalido_1.gridx = 2;
		gbc_lblFormatoInvalido_1.gridy = 6;
		add(lblFormatoInvalido_1, gbc_lblFormatoInvalido_1);

		JLabel lblPasswordConf = new JLabel("<html>Confirmar<br/>contraseña</html>");
		GridBagConstraints gbc_lblPasswordConf = new GridBagConstraints();
		gbc_lblPasswordConf.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPasswordConf.insets = new Insets(0, 0, 5, 20);
		gbc_lblPasswordConf.gridx = 1;
		gbc_lblPasswordConf.gridy = 7;
		add(lblPasswordConf, gbc_lblPasswordConf);

		campoPasswordConfirmacion = new JTextField();
		GridBagConstraints gbc_campoPasswordConfirmacion = new GridBagConstraints();
		gbc_campoPasswordConfirmacion.anchor = GridBagConstraints.NORTHWEST;
		gbc_campoPasswordConfirmacion.insets = new Insets(0, 0, 5, 5);
		gbc_campoPasswordConfirmacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoPasswordConfirmacion.gridx = 2;
		gbc_campoPasswordConfirmacion.gridy = 7;
		add(campoPasswordConfirmacion, gbc_campoPasswordConfirmacion);
		campoPasswordConfirmacion.setColumns(25);

		JLabel lblIconoPassConf = new JLabel("etiqueta");
		GridBagConstraints gbc_lblIconoPassConf = new GridBagConstraints();
		gbc_lblIconoPassConf.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblIconoPassConf.insets = new Insets(0, 0, 5, 5);
		gbc_lblIconoPassConf.gridx = 3;
		gbc_lblIconoPassConf.gridy = 7;
		add(lblIconoPassConf, gbc_lblIconoPassConf);

		JButton btnCambiarContrasea = new JButton("Cambiar contraseña");
		GridBagConstraints gbc_btnCambiarContrasea = new GridBagConstraints();
		gbc_btnCambiarContrasea.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCambiarContrasea.insets = new Insets(10, 0, 5, 5);
		gbc_btnCambiarContrasea.gridx = 2;
		gbc_btnCambiarContrasea.gridy = 8;
		add(btnCambiarContrasea, gbc_btnCambiarContrasea);
		crearPanel();
	}

	private void crearPanel() {

	}

}
