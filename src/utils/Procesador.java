package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Scanner;
import sintactico.AnalizadorSintactico;
import utils.tabla.AuxTS;
import utils.tokens.Tokens;
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

public class Procesador {
	private AnalizadorLexico anLex;
	private AnalizadorSintactico anSin;

	/**
	 * Constructor que inicializa el analizador lexico y sintactico. 
	 * @param fichero el fichero que se va a leer.
	 */
	public Procesador(String fichero){
		anLex = new AnalizadorLexico(fichero);
		anSin = new AnalizadorSintactico(anLex, "src\\recursos\\tablas.xlsx", "src\\recursos\\reglas.txt");
	}
	
	/**
	 * Metodo principal el cual se encarga de generar el procesador. 
	 */
	public static void main(String[] args) {
		Procesador procesador;
		String archivo;
		AuxTS.crearTabla("PRINCIPAL");
		if(args.length < 1){
			procesador = new Procesador("src\\recursos\\prueba.txt");
			archivo = "prueba.txt archivo por defecto";
		}else {
			procesador = new Procesador(args[0]);
			archivo = args[0];
		}
	    Scanner scan = new Scanner(System.in);
		System.out.println("Si no se le pasa ningun fichero como argumento, ejecutara con el fichero por defecto de prueba, localizado en src\\recursos\\prueba.txt\n");
		System.out.println("En caso de querer ejecutarlo con otro fichero, se tendra que utilizar el comando java -jar procesador.jar (nombre del fichero)\n");
	    System.out.print("Presione cualquier tecla para procesar el archivo: " + archivo);
	     scan.nextLine();
	     scan.close();
		
	
		// Crear tabla de símbolos global en la pila
		int codSintactico = 1;
		while(codSintactico==1){
			//codSintactico =  procesador.anSin.analizar();
			codSintactico =  procesador.anSin.tratamiento();
		}
		try {
			procesador.anLex.getFileReader().close();
			if(codSintactico == 0){
				procesador.listarTokens();
				procesador.listarParse();
				procesador.listarTablas();
				procesador.listarErrores();
			}
			else{
				procesador.listarErrores();
			}
			System.out.println("Los ficheros se pueden encontrar en la carpeta recursos.");
		while(!AuxTS.getPilaTablas().isEmpty()){
			AuxTS.eliminarTS();
		}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * Este metodo genera el fichero del parse del fichero que se le ha pasado para analizar
	 */
	private void listarParse(){
		// Listar parse
		PrintWriter writerParse;
		try {
			writerParse = new PrintWriter("src\\recursos\\parse.txt", "UTF-8");
			//Iterator<Integer> itParse = anSin.getParse().iterator();
			Iterator<Integer> iterator = anSin.getListaReglasUtilizadas().iterator();
			String parse = "Ascendente";
			while(iterator.hasNext()){
				int regla = iterator.next();
				parse = parse + " " + (regla-1);
			}
			writerParse.println(parse);
			writerParse.close();
			System.out.println("Generado parse.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Este metodo genera el fichero de tokens del fichero que se le ha pasado para analizar
	 */
	private void listarTokens(){
		// Listar tokens
		PrintWriter writer;
		try {
			writer = new PrintWriter("src\\recursos\\tokens.txt", "UTF-8");
			Iterator<Tokens> it = anLex.getTokensLeidos().iterator();
			while(it.hasNext()){
				Tokens token = it.next();
				writer.println(token.convertToString());
			}
			writer.close();
			System.out.println("Generado tokens.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Este metodo genera el fichero de errores del fichero que se le ha pasado para analizar
	 */
	private void listarErrores(){
		GestorErrores.printErrors();
	}

	/**
	 * Este metodo genera el fichero de tablas del fichero que se le ha pasado para analizar
	 */
	private void listarTablas(){
		AuxTS.printTablas();
		System.out.println("Generado tabla_simbolos.txt");
	}

}
