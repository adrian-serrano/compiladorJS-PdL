package utils.tokens;
/**
 * Autores: Guillermo Buendia del Campo, Adrian Serrano Lopez-Alvarez, Andres Ramos Colombo, 
 * 
 * Fecha de inicio: 19/10/2020
 * 
 * Fecha de ultima modificacion: 28/11/2020
 * 
 * Proyecto: Practica PDL
 *
 */

/* Esta clase genera los tokens de tipo PAreserv
 * 
 */
public class PAreserv implements Tokens{
	private String palabra;
	
	/**
	 * Constructor que iniciliza el atributo palabra
	 * @param palabra la plabra
	 */
	public PAreserv(String palabra){
		super();
		this.palabra = palabra;
	}
	

	/**
	 * Metodo que devuelve el tipo de token
	 * @return String
	 */
	@Override
	public String tipoToken() {	
		return palabra;
	}
	
	/**
	 * Metodo que genera el String del token
	 * @return String
	 */
	@Override
	public String convertToString() {
		return "<PAreserv, " + palabra + ">";
	}
}