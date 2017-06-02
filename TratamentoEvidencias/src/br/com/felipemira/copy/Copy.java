package br.com.felipemira.copy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public class Copy {
	public static void copyFile(File origem, File destino) throws IOException{
		InputStream in = new FileInputStream(origem);
		OutputStream out = new FileOutputStream(destino);
		
		byte[] buffer = new byte[1024];
		int lenght;
		while((lenght = in.read(buffer)) > 0){
			out.write(buffer, 0, lenght);
		}
		in.close();
		out.close();
	}
	
	public static File streamTofile (InputStream in, String Prefix, String Suffix) {
		try{
			final File tempFile = File.createTempFile(Prefix, Suffix);
	        tempFile.deleteOnExit();
	        try (FileOutputStream out = new FileOutputStream(tempFile)) {
	            IOUtils.copy(in, out);
	        }
	        return tempFile;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
    }
}
