package br.com.felipemira.convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;

import br.com.felipemira.arquivos.office.custom.document.CustomXWPFDocument;

public class ConvertToPDF {
	
	public static void convert(String caminhoArquivo, String nomeArquivo){
		
		String caminhoPDF = caminhoArquivo.split(Pattern.quote("_"))[0] + nomeArquivo + ".pdf";
		
		try {
			CustomXWPFDocument document = null;
	        InputStream arquivo = new FileInputStream(caminhoArquivo);
			CustomXWPFDocument copiaDocumento = new CustomXWPFDocument(arquivo);
		    document = copiaDocumento;
			
			File output = new File(caminhoPDF);
			OutputStream outputStream = new FileOutputStream(output);
			
			PdfOptions opcoes = PdfOptions.create();
			PdfConverter.getInstance().convert(document, outputStream, opcoes);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
