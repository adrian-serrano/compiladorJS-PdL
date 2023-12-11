package utils.tokens;

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

/* 
 * Esta clase genera los tokens de tipo cadena
 */
public class Cad implements Tokens {
	private String cadena;
	

	/**
	 * Constructor el cual iniciliza el atributo cadena
	 * @param cadena la cadena
	 */
	public Cad(String cadena) {
		super();
		this.cadena = cadena;
	}
	
	/**
	 * Funcion la cual devuelve el tipo de token
	 * @return String token cadena
	 */
	@Override
	public String tipoToken() {	
		return "cad";
	}

	/**
	 * Funcion la cual se encarga de generar el String del token cadena
	 * @return String a modo que nos interesa
	 */
	@Override
	public String convertToString() {		
		return "<CAD, " + "\"" + cadena + "\"" + ">";
	}
	
}
