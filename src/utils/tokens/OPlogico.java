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
 * Esta clase genera los tokens de tipo OPlogico, el unico que trataremos sera &&
 */

public class OPlogico implements Tokens{


	/**
	 * Metodo que devuelve el tipo de token
	 * @return String el tipo de token
	 */
	@Override
	public String tipoToken() {
		return "&&";
	}

	/**
	 * Metodo que genera el String del token OpLogico
	 * @return String como nos interesa para este token
	 */
	@Override
	public String convertToString() {
		return "<OPlogico, "   + " >";
	}
}
