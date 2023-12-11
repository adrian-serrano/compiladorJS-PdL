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

/* Esta clase genera los tokens de tipo OPasig
 * 
 */


/**
 * En esta clase se implementa el operador de asignacion =
 *
 */
public class OPasig implements Tokens{
	
	/**
	 * Metodo que devuelve el tipo de token
	 * @return String
	 */
	@Override
	public String tipoToken() {	
		return "igual";
	}

	/**
	 * Metodo que genera el String del token
	 * @return String
	 */
	@Override
	public String convertToString() {	
		return "<OPasig, "  + " >";
	}
}
