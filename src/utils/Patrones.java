package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Autores: Guillermo Buendia del Campo, Adrian Serrano Lopez-Alvarez, Andres Ramos Colombo, 
 * 
 * Fecha de inicio: 19/10/2020
 * 
 * Fecha de ultima modificacion: 2/11/2020
 * 
 * Proyecto: Practica PDL
 *
 */

/* Easta clase sirve para saber si es un digito un delimitador o un digito
 * 
 */

public class Patrones {

	private Pattern digit = Pattern.compile("[0-9]");                // Patron de digitos
	private Pattern letter = Pattern.compile("[a-zA-z]");			 // Patron de caracters letras
	private Pattern del = Pattern.compile("["+" "+"\\t"+"\\f"+"]");	 // Patron de delimitadores
	
	
	/**
	 * isLetter(String caracter)
	 * Metodo que devuelve true si es una letra mayucula o minuscula y falso e.o.c.
	 * 
	 * @return boolean
	 */
	public boolean isLetter(String caracter){
		Matcher isLetter = this.letter.matcher(caracter);
		return isLetter.matches();
	}
	
	/**
	 * isDigit(String caracter)
	 * Metodo que devuelve true si es un numero y falso e.o.c.
	 * 
	 * @return boolean
	 */
	public boolean isDigit(String caracter){
		Matcher isDigit = this.digit.matcher(caracter);
		return isDigit.matches();
	}
	
	/**
	 * isDel(String caracter)
	 * Metodo que devuelve true si es un delimitador y falso e.o.c.
	 * 
	 * @return boolean
	 */
	public boolean isDel(String caracter){
		Matcher isDelimiter = this.del.matcher(caracter);
		return isDelimiter.matches();
	}
}