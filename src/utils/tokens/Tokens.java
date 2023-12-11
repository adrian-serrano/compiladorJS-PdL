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
 * Esta es la interfaz de los tokens
 */
public interface Tokens {
	
	/**
	 * Metodo que devuelve el tipo de token
	 * @return String
	 */
	public String tipoToken();
	
	/**
	 * Metodo que genera el String del token
	 * @return String
	 */
	public String convertToString();

}
