package Abarrotes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Buscador {

	FileInputStream entrada;
	FileOutputStream salida;
	File archivo;
	
	public Buscador() {
		try {
			salida = new FileOutputStream(archivo);
		}catch(Exception e) {}
	}

	//Esta madre guarda
	public String AbrirTexto(File archivo) {
		String contenido = "";
		try {
			entrada = new FileInputStream(archivo);
			int ascci;
			while((ascci = entrada.read()) != -1) {
				char caracter = (char) ascci;
				contenido += caracter;
			}
		}catch(Exception e) {	
			e.printStackTrace();
		}	
		return contenido;
	}
	
}