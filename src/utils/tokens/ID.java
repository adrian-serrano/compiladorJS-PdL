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

/* 
 * Esta clase genera los tokens de tipo Id
 */
public class ID implements Tokens {
	private int posicion;
	private String tabla;

	
	/**
	 * Constructor que inicializa los atributos tabla y posicion
	 * @param tabla la tabla
	 * @param pos la posicion
	 */
	public ID(String tabla, int pos){
		this.posicion = pos;
		this.tabla = tabla;
	}
	
	/**
	 * Funcion que se encarga de devolver el tipo de token
	 * @return String que devuelve el tipo de token (id)
	 */
	@Override
	public String tipoToken() {
		return "id";
	}

	/**
	 * Funcion que genera el String del token Id como nos interesa
	 * @return String El string del token id
	 */
	@Override
	public String convertToString() {	
		return "<Id," +  posicion + ">";
	}
	
	/**
	 * Metodo getter que devuelve la posicion
	 * @return la posicion
	 */
	public int getPos(){
		return posicion;
	}
	
	/**
	 * Metodo getter que devuelve la tabla
	 * @return la tabla
	 */
	public String getTabla(){
		return tabla;
	}
}
