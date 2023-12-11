package semantico;

/**
 * Autores: Guillermo Buendia del Campo, Adrian Serrano Lopez-Alvarez y Andres Ramos Colombo, 
 * 
 * Fecha de inicio: 19/10/2020
 * 
 * Fecha de ultima modificacion: 11/01/2021
 * 
 * Proyecto: Practica PDL
 *
 */

public class Atributo {
	private String simbolo;
	private String tipo;
	private String lexema;
	
	/**
	 * Constructor que inicializa el Atributo
	 * @param simbolo
	 * @param tipo
	 * @param lexema 
	 */
	public Atributo(String simbolo, String tipo, String lexema){
		this.simbolo = simbolo;
		this.tipo = tipo;
		this.lexema = lexema;
	}
	
	/**
	 * Constructor que inicializa el Atributo
	 * @param simbolo 
	 * @param tipo
	 */
	public Atributo(String simbolo, String tipo){
		this.simbolo = simbolo;
		this.tipo = tipo;
		this.lexema = "-";
	}
	/**
	 * Metodo getter que devuelve el simbolo
	 * @return el simbolo
	 */
	public String getSimbolo() {
		return simbolo;
	}
	/**
	 * Metodo getter que devuelve el tipo
	 * @return el tipo
	 */
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * Metodo getter que devuelve el lexema.
	 * @return lexema
	 */
	public String getLexema() {
		return lexema;
	}
	
}
