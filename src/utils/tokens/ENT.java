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
 * Esta clase genera los tokens de tipo Entero
 */
public class ENT implements Tokens {
	private int entero;


	/**
	 * Constructor que se encarga de inicilizar el atributo entero
	 * @param entero
	 */
	public ENT(int entero) {
		super();
		this.entero = entero;
	}
	
	/**
	 * Metodo que devuelve el tipo de token
	 * @return el tipo de token entero
	 */
	@Override
	public String tipoToken() {
		return "ent";
	}

	/**
	 * Funcion la cual que genera el String que nos interesa de token entero
	 * @return El String como nos interesa
	 */
	@Override
	public String convertToString() {	
		return "<ENT, " + entero + ">";
	}
}
