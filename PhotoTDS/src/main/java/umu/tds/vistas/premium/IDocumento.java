package umu.tds.vistas.premium;

import java.util.List;

import com.itextpdf.text.DocumentException;

import umu.tds.modelo.pojos.Usuario;

public interface IDocumento{
	public void crearDocumento(String ruta, List<Usuario> usuarios);
}
