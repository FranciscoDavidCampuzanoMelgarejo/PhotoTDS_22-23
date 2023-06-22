package umu.tds.vistas.premium;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import umu.tds.modelo.pojos.*;

public class DocumentoPDF implements IDocumento{
	
	@Override
	public void crearDocumento(String ruta, List<Usuario> usuarios) {
		try {
			FileOutputStream archivo = new FileOutputStream(ruta);
			Document pdf = new Document();
			PdfWriter.getInstance(pdf, archivo);
			pdf.open();
			pdf.add(new Paragraph("Odio mi vida"));
			pdf.close();
		} catch (FileNotFoundException e1) { e1.printStackTrace(); } catch (DocumentException e2) { e2.printStackTrace(); }
		
	}
}
