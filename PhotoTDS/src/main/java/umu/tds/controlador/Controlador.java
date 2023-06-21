package umu.tds.controlador;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;

import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.regex.*;

import javax.swing.ImageIcon;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfChunk;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import umu.tds.componente.CargadorFotos;
import umu.tds.componente.FotosEvent;
import umu.tds.componente.FotosListener;
import umu.tds.fotos.HashTag;
import umu.tds.modelo.catalogos.CatalogoPublicaciones;
import umu.tds.modelo.catalogos.CatalogoUsuarios;
import umu.tds.modelo.pojos.Album;
import umu.tds.modelo.pojos.Comentario;
import umu.tds.modelo.pojos.Descuento;
import umu.tds.modelo.pojos.Foto;
import umu.tds.modelo.pojos.PerfilUsuario;
import umu.tds.modelo.pojos.Publicacion;
import umu.tds.modelo.pojos.Usuario;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IComentarioDAO;
import umu.tds.persistencia.IPublicacionDAO;
import umu.tds.persistencia.IUsuarioDAO;

public class Controlador implements FotosListener {

	private static Controlador controlador;

	// Catalogos del sistema
	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoPublicaciones catalogoPublicaciones;

	// DAO de las clases del sistema
	private IUsuarioDAO usuarioDAO;
	private IPublicacionDAO publicacionDAO;
	private IComentarioDAO comentarioDAO;

	// Usuario logueado
	private Usuario usuario;

	// Lista de usuarios a los que sigo
	List<Usuario> usuariosSeguidos;
	
	// Lista de descuentos
	List<Descuento> descuentos;
	
	// Componente Java Bean para cargar las fotos a partir de un fichero XML
	private CargadorFotos cargador;

	private Controlador() {
		this.catalogoUsuarios = CatalogoUsuarios.getCatalogoUsuarios();
		this.catalogoPublicaciones = CatalogoPublicaciones.getCatalogoPublicaciones();

		FactoriaDAO factoria = FactoriaDAO.getFactoriaDAO(); // Obtenemos la factoria que crea DAO que usan JPA para la
																// persistencia
		this.usuarioDAO = factoria.getUsuarioDAO();
		this.publicacionDAO = factoria.getPublicacionDAO();
		this.comentarioDAO = factoria.getComentarioDAO();

		this.usuario = null;
		this.usuariosSeguidos = new LinkedList<Usuario>();
		this.cargador = new CargadorFotos();
		this.cargador.addFotosListener(this);
		this.descuentos = new LinkedList<Descuento>();
	}

	private void publicar(Publicacion publicacion) {
		publicacion.setUsuario(this.usuario);

		publicacionDAO.save(publicacion);
		catalogoPublicaciones.add(publicacion);
		this.usuario.addPublicacion(publicacion);
	}

	public static Controlador getControlador() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}

	public String getUsername() {
		return usuario.getUsuario();
	}

	public String getUserNombre() {
		return usuario.getNombre();
	}
	
	public LocalDate getFechaNacimiento() {
		return usuario.getFechaNacimiento();
	}

	public String getUserPresentacion() {
		return usuario.getPerfil().getPresentacion();
	}

	public String getUserPicture() {
		System.out.println(usuario.getPerfil());
		return usuario.getPerfil().getFoto();
	}
	
	/* Devuelve la ruta de la foto de perfil de un usuario */
	public String getUserPicture(String username) {
		Usuario u = catalogoUsuarios.get(username);
		return (u==null) ? null : u.getPerfil().getFoto();
	}
	
	// Devuelve los IDS de los seguidores
	public List<Integer> getIDSeguidos(){
		return getUsuariosSeguidos(usuario).stream()
							   			   .map(s -> s.getId())
							   			   .collect(Collectors.toList());
	}
	
	public boolean isPremium() {
		return usuario.getPremium();
	}

	@Override
	public void enteradoCambio(EventObject e) {
		if (e instanceof FotosEvent) {
			FotosEvent evento = (FotosEvent) e;
			for (umu.tds.fotos.Foto foto : evento.getFotosNuevas().getFoto()) {

				// Si la foto no esta guardada en el catalogo
				if (!catalogoPublicaciones.checkPath(foto.getPath())) {
					List<String> hashtags = null;
					String textoComentario = null;
					if (!foto.getHashTags().isEmpty()) {
						hashtags = foto.getHashTags().stream().flatMap(h -> h.getHashTag().stream())
								.collect(Collectors.toList());

						StringBuilder sb = new StringBuilder();
						for (String h : hashtags) {
							sb.append("#" + h);
						}
						sb.append("\n");
						textoComentario = sb.toString();

						System.out.println("HASHTAGS");
						hashtags.stream().forEach(h -> System.out.println(h));

						System.out.println("COMENTARIO");
						System.out.println(textoComentario);
					}

					publicarFoto(foto.getPath(), foto.getTitulo(), foto.getDescripcion(), textoComentario, hashtags);

				}
			}
		}

	}

	public void cargarFotos(String ruta) {
		this.cargador.setFicheroFotos(ruta);
	}

	public boolean registrarUsuario(String nombre, String email, String nombreUsuario, String password,
			LocalDate fechaNacimiento, String presentacion, String foto) {

		//System.out.println(foto);

		if (!catalogoUsuarios
				.existeUsuario((Usuario u) -> u.getEmail().equals(email) || u.getUsuario().equals(nombreUsuario))) {

			PerfilUsuario perfil = new PerfilUsuario(presentacion, foto);
			Usuario usuario = new Usuario(nombre, email, nombreUsuario, password, fechaNacimiento, perfil);

			/*
			 * IMPORTANTE: Guardar primero el usuario en la base de datos. De esta forma, se
			 * genera el id de la entidad automaticamente y podemos guardarlo en el
			 * catalogo.
			 */
			usuarioDAO.save(usuario);
			catalogoUsuarios.add(usuario);
			return true;
		}
		return false;
	}

	public boolean loginUsuario(String nombreUsuario, String password) {
		Predicate<Usuario> predicado = ((
				Usuario u) -> (u.getEmail().equals(nombreUsuario) || u.getUsuario().equals(nombreUsuario))
						&& (u.getPassword().equals(password)));

		Usuario usuarioLogueado = catalogoUsuarios.get(predicado);
		if (usuarioLogueado != null) {
			// Necesito obtener el usuario de la base de datos para que este persistido
			// this.usuario = usuarioDAO.findBy(usuarioLogueado.getId());
			this.usuario = usuarioLogueado;
			return true;
		}
		return false;
	}

	public void editarPerfil(String foto, String presentacion) {
		usuario.cambiarPerfil(foto, presentacion);

		usuarioDAO.update(usuario);
	}

	public void cambiarPassword(String nuevaPassword) {
		usuario.setPassword(nuevaPassword);
		usuarioDAO.update(usuario);
	}

	public void seguir(String nombreUsuario) {

		// Obtener el usuario al que se quiere seguir
		Usuario usuarioSeguido = catalogoUsuarios.get(nombreUsuario);

		// Nos añadimos a la lista de seguidores del usuario seguido
		usuarioSeguido.addSeguidor(this.usuario);
		usuarioDAO.update(usuarioSeguido);
	}

	public void dejarSeguir(String nombreUsuario) {

		// Obtener el usuario al que se quiere dejar de seguir
		Usuario usuarioSeguido = catalogoUsuarios.get(nombreUsuario);

		// Nos elmininamos de la lista de seguidores del usuario seguido
		usuarioSeguido.removeSeguidor(this.usuario);
		usuarioDAO.update(usuarioSeguido);
	}

	public void publicarFoto(String ruta, String titulo, String descripcion, String comentario, List<String> hashtags) {
		
		Foto foto = new Foto(titulo, descripcion, ruta, new Comentario(comentario, usuario), hashtags);
		publicar(foto);

	}

	public void publicarAlbum(String titulo, String descripcion, String comentario, List<String> hashtags,
			Set<Foto> fotos) {

		Album album = new Album(titulo, descripcion, new Comentario(comentario, usuario), hashtags, fotos);
		publicar(album);
	}

	public void elminarPublicacion(Integer id) {
		Publicacion publicacion = catalogoPublicaciones.get(id);
		catalogoPublicaciones.remove(publicacion);
		publicacionDAO.delete(publicacion);
	}

	public void darLike(Integer id) {
		Publicacion publicacion = catalogoPublicaciones.get(id);
		publicacion.darLike();
		publicacionDAO.update(publicacion);
	}

	// Metodo que devuelve una lista de los Usuarios seguidos por el Usuario usuario
	public Set<Usuario> getUsuariosSeguidos(Usuario usuario) {
		return catalogoUsuarios.getAll().stream()
				.filter((Usuario u) -> u.isSeguido(usuario))
				.collect(Collectors.toSet());
	}
	
	// Metodo que devuelve el numero de Usuarios seguidos por el Usuario usuario
	public int getNumeroUsuariosSeguidos(Usuario usuario) {
		return (int) catalogoUsuarios.getAll().stream()
				.filter((Usuario u) -> u.isSeguido(usuario))
				.count();
	}


	public int getSeguidores() {
		return usuario.numeroSeguidores();
	}
	
	// Devuelve el número de likes en total de todas las fotos del usuario
	public long numeroLikes() {
		return catalogoPublicaciones.getAll().stream() 
											 .filter(p -> p.getClass()==Foto.class)
											 .filter(p -> p.getUsuario().getId()==usuario.getId())
											 .map(p -> p.getLikes())
											 .count();
	}


	// Devuelve las últimas 20 publicaciones de usuarios que seguimos, desde la fecha actual
	public List<Publicacion> getUltimasPublicaciones(){
		return catalogoPublicaciones.getAll().stream()
											 .sorted(Comparator.comparing(Publicacion::getFecha))
											 .limit(20)
											 .collect(Collectors.toList());
	}
	
	// Devuelve las últimas 20 fotos de todos los usuarios que seguimos y nosotros mismos
	public List<Publicacion> getUltimasFotos(){
		return catalogoPublicaciones.getAll().stream()
											 .filter(p -> p.getClass()==Foto.class)
											 .filter(p -> getIDSeguidos().contains(p.getUsuario().getId()) || p.getUsuario().getId()==usuario.getId())
											 .sorted(Comparator.comparing(Publicacion::getFecha).reversed())
											 .limit(20)
											 .collect(Collectors.toList());
		
	}
	
	/* Devuelve una lista de usuarios cuyo nombre o correo casan con la expresión regular */
	public List<Usuario> getUsersByER(Pattern pat){
		return catalogoUsuarios.getAll().stream()
										.filter(u -> pat.matcher(u.getNombre()).find() | pat.matcher(u.getEmail()).find())
										.collect(Collectors.toList());
	}
	
	private List<String> getHashtags(){
		return catalogoPublicaciones.getAll().stream()
											 .flatMap(p -> p.getHashtags().stream())
											 .collect(Collectors.toList());
	}
	
	/* Devuelve la lista de hashtags que casan con los hashtags de patron */
	public List<String> getHashtagList(String patron){
		List<String> lista = new LinkedList<String>(), hs = getHashtags();
		Pattern p1 = Pattern.compile("#[^ ]*");
		Matcher m1 = p1.matcher(patron);
		while(m1.find()) {
			Pattern p2 = Pattern.compile(m1.group());
			lista.addAll(hs.stream().filter(h -> p2.matcher(h).find()).collect(Collectors.toList()));
		}
		return lista.stream().distinct().collect(Collectors.toList());
	}
	
	/* Devuelve una lista de fotos con ese hashtag */
	public List<Publicacion> getPublisHashtag(String hashtag){
		return catalogoPublicaciones.getAll().stream()
											 .filter(p -> p.getClass()==Foto.class)
											 .filter(p -> p.getHashtags().contains(hashtag))
											 .sorted(Comparator.comparing(Publicacion::getFecha).reversed())
											 .collect(Collectors.toList());
	}
	
	
	// PREMIUM: el controlador almacena los descuentos que hay hasta ahora implementados
	public void addDescuento(Descuento d) {
		descuentos.add(d);
	}
	
	public List<Descuento> getDescuentos(){
		return descuentos;
	}
	
	// PREMIUM: activa o desactiva el status de premium del usuario
	public void setPremium(boolean value) {
		usuario.setPremium(value);
		usuarioDAO.update(usuario);
	}
	
	// PREMIUM: devuelve las 10 fotos con más me gusta del usuario actual
	public List<Publicacion> getMasMeGusta(){
		return catalogoPublicaciones.getAll().stream()
											 .filter(publi -> publi.getUsuario().getId()==usuario.getId())
											 .filter(publi -> publi.getClass()==Foto.class)
										     .sorted(Comparator.comparing(Publicacion::getLikes).reversed())
										     .limit(10)
										     .collect(Collectors.toList());
	}
	
	// PREMIUM: genera un fichero EXCEL con la lista de seguidores (nombre, email, presentacion)
	public void generarExcelSeguidores(String ruta) {
		Workbook wb = new XSSFWorkbook();
		CellStyle intros = wb.createCellStyle();
		org.apache.poi.ss.usermodel.Font f = wb.createFont();
			f.setBold(true);
			intros.setFont(f);
		
		String sseg = (getSeguidores()==0) ? "No tienes seguidores ;-;" : ((getSeguidores()==1) ? "¡Tienes un seguidor!" : "¡Tienes " + getSeguidores() + " seguidores!" );
			
		Sheet s = wb.createSheet("MisSeguidores");
		Row seg = s.createRow(0);
			seg.createCell(0).setCellValue(sseg);
			seg.getCell(0).setCellStyle(intros);
		
		if(getSeguidores()>0) {
			Row intror = s.createRow(1);
				intror.createCell(0).setCellValue("Nombre");
				intror.getCell(0).setCellStyle(intros);
				intror.createCell(1).setCellValue("Correo");
				intror.getCell(1).setCellStyle(intros);
				intror.createCell(2).setCellValue("Presentación");
				intror.getCell(2).setCellStyle(intros);
			int i = 2;
			for(Usuario u : usuario.getSeguidores()) {
				Row r = s.createRow(i++);
					r.createCell(0).setCellValue(u.getNombre());
					r.createCell(1).setCellValue(u.getEmail());
					r.createCell(2).setCellValue(u.getPerfil().getPresentacion());
			}
		}
		
		try {
			FileOutputStream archivo = new FileOutputStream(ruta);
			wb.write(archivo);
			wb.close();
		} catch (IOException ioe) { ioe.printStackTrace(); System.exit(-1); }
		
	}
	
	// PREMIM: genera un fichero PDF con la lista de seguidores (foto, nombre, email, presentacion)
	public void generarPdfSeguidores(String ruta) {
		try {
			Rectangle ps = new Rectangle(PageSize.A4);
			ps.setBackgroundColor(new BaseColor(0x3c, 0x3f, 0x41));
			Document pdf = new Document(ps);
			FileOutputStream archivo = new FileOutputStream(ruta);
			PdfWriter.getInstance(pdf, archivo);
			Font fuente1 = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);
				fuente1.setColor(BaseColor.WHITE);
			Font fuente2 = new Font(Font.FontFamily.COURIER, 11);
				fuente2.setColor(BaseColor.WHITE);
			Font fuente3 = new Font(Font.FontFamily.COURIER, 11, Font.BOLD);
				fuente3.setColor(BaseColor.WHITE);
			
			String ns = (getSeguidores()==0) ? "No tienes seguidores ;-;\n" : ((getSeguidores()==1) ? "¡Tienes un seguidor!\n" : "¡Tienes " + getSeguidores() + " seguidores!\n");
			pdf.open();
			Image logo = Image.getInstance(getClass().getResource("/imagenes/premium/tusseguidores.png"));
				logo.scaleToFit(128,128);
				logo.setAlignment(Element.ALIGN_CENTER);
			pdf.add(logo);
			Paragraph intro = new Paragraph(ns, fuente1);
				intro.setAlignment(Element.ALIGN_CENTER);
			pdf.add(intro);
			pdf.add(new Paragraph("\n"));
			
			PdfPTable tabla = new PdfPTable(3);
				PdfPCell c1 = new PdfPCell(new Paragraph("Nombre", fuente3));
					c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					c1.setVerticalAlignment(Element.ALIGN_MIDDLE);

					PdfPCell c2 = new PdfPCell(new Paragraph("Correo", fuente3));
						c2.setHorizontalAlignment(Element.ALIGN_CENTER);
						c2.setVerticalAlignment(Element.ALIGN_MIDDLE);
					PdfPCell c3 = new PdfPCell(new Paragraph("Presentación", fuente3));
						c3.setHorizontalAlignment(Element.ALIGN_CENTER);
						c3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				tabla.addCell(c1);
				tabla.addCell(c2);
				tabla.addCell(c3);
			
			// Para cada seguidor, ponemos su foto de perfil, nombre, email y descripción
			for(Usuario u : usuario.getSeguidores()) {
				PdfPCell cnombre = new PdfPCell(new Paragraph(u.getNombre(), fuente2));
					cnombre.setHorizontalAlignment(Element.ALIGN_CENTER);
					cnombre.setVerticalAlignment(Element.ALIGN_MIDDLE);
				PdfPCell cemail = new PdfPCell(new Paragraph(u.getEmail(), fuente2));
					cemail.setHorizontalAlignment(Element.ALIGN_CENTER);
					cemail.setVerticalAlignment(Element.ALIGN_MIDDLE);
				PdfPCell cpresentacion = (u.getPerfil().getPresentacion()==null) ? new PdfPCell(new Paragraph("-", fuente2))
											: new PdfPCell(new Paragraph(u.getPerfil().getPresentacion(), fuente2));
					cpresentacion.setHorizontalAlignment(Element.ALIGN_CENTER);
					cpresentacion.setVerticalAlignment(Element.ALIGN_MIDDLE);
				tabla.addCell(cnombre);
				tabla.addCell(cemail);
				tabla.addCell(cpresentacion);
			}
			
			pdf.add(tabla);
			pdf.close();
		} catch (IOException ioe) { ioe.printStackTrace(); 
		} catch (DocumentException dex) { dex.printStackTrace(); }
	}
	
	// Getters y Setters
	public Usuario getUsuarioLogueado() {
		return this.usuario;
	}

	public void setUsuarioLogueado(Usuario usuario) {
		this.usuario = usuario;
	}

}
