package umu.tds.vistas.perfil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class FactoriaBotonPerfil {

	private EstadoBotonPerfil estado; // requerido
	private JFrame frame;
	private Icon fotoPerfil;
	private String rutaFotoPerfil;
	
	private List<ActionListener> listeners;

	private FactoriaBotonPerfil(FactoriaBotonPerfilBuilder builder) {
		this.estado = builder.estado;
		this.frame = builder.frame;
		this.fotoPerfil = builder.fotoPerfil;
		this.rutaFotoPerfil = builder.rutaFotoPerfil;
		listeners = new LinkedList<ActionListener>();
	}
	
	/* Listener del botón */
	public void addActionListener(ActionListener a) {
		if(listeners==null) listeners = new LinkedList<ActionListener>();
		listeners.add(a);
	}
	
	/* Acción */
	public void notificacionActualizarFotoPerfil(ActionEvent a) {
		if(listeners!=null) listeners.stream().forEach(l -> l.actionPerformed(a));
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
				dialogo.addWindowListener(new WindowAdapter() {
						@Override 
						public void windowClosed(WindowEvent e) {
							notificacionActualizarFotoPerfil(new ActionEvent(this, 6, "cambioFotoPerfil"));
							
						}
				});
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
