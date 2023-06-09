package umu.tds.vistas.perfil;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class FactoriaBotonPerfil {

	private EstadoBotonPerfil estado; // requerido
	private JFrame frame;
	private Icon fotoPerfil;
	private String rutaFotoPerfil;

	private FactoriaBotonPerfil(FactoriaBotonPerfilBuilder builder) {
		this.estado = builder.estado;
		this.frame = builder.frame;
		this.fotoPerfil = builder.fotoPerfil;
		this.rutaFotoPerfil = builder.rutaFotoPerfil;
	}

	public JButton crearBoton() {
		JButton boton = new JButton();
		switch (estado) {
		case EDITAR_PERFIL:
			boton.setText("Editar Perfil");
			boton.addActionListener((ActionEvent e) -> {
				DialogoEditarPerfil dialogo = new DialogoEditarPerfil(frame.getSize().height, frame.getContentPane(),
						fotoPerfil, rutaFotoPerfil);
				dialogo.mostrarDialogo();
			});
			break;
		case SEGUIR:
			boton.setText("Seguir");
			// Añadir ActionListener al boton
			break;

		case SIGUIENDO:
			boton.setText("Siguiendo");
			// Añadir ActionListener al boton
			break;
		default:
			break;
		}
		return boton;
	}

	public static class FactoriaBotonPerfilBuilder {
		private final EstadoBotonPerfil estado;
		private JFrame frame;
		private Icon fotoPerfil;
		private String rutaFotoPerfil;

		public FactoriaBotonPerfilBuilder(EstadoBotonPerfil estado) {
			this.estado = estado;
		}

		public FactoriaBotonPerfilBuilder dialogo(JFrame frame, Icon fotoPerfil, String rutaFotoPerfil) {
			this.frame = frame;
			this.fotoPerfil = fotoPerfil;
			this.rutaFotoPerfil = rutaFotoPerfil;
			return this;
		}

		public FactoriaBotonPerfil build() {
			FactoriaBotonPerfil factoria = new FactoriaBotonPerfil(this);
			return factoria;
		}

	}

}
