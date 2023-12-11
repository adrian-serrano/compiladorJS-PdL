package utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import lexico.AnalizadorLexico;

/**
 * Autores: Guillermo Buendia del Campo, Adrian Serrano Lopez-Alvarez, Andres Ramos Colombo, 
 * 
 * Fecha de inicio: 19/10/2020
 * 
 * Fecha de ultima modificacion: 06/01/2021
 * 
 * Proyecto: Practica PDL
 *
 */

public class GestorErrores {

	private static ArrayList<String> errores =  new ArrayList<String>();

	/**
	 * Metodo que agraga un error a listaErrores.
	 * @param error El error que se agrega
	 */
	public static void addError(String error){
		String output = "Cerca de la linea: " + AnalizadorLexico.linea + " Descripcion: " + error;
		errores.add(output);
	}

	/**
	 * Metodo que escibe los errores en un fichero llamado errores. 
	 */
	public static void printErrors(){
		PrintWriter writer;
		int errorNum = 0;
		try {
			writer = new PrintWriter("src\\recursos\\errores.txt", "UTF-8");
			Iterator<String> it = errores.iterator();
			String error;
			while(it.hasNext()){
				error = it.next();
				System.out.println("ERROR: " + error);
				// añadir a fichero
				writer.println("ERROR: " + error);
				errorNum++;
			}
			writer.close();
			if(errorNum > 0) System.out.println("Generado errores.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}