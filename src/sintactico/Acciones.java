package sintactico;

import java.util.HashMap;
import java.util.Map;

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

public class Acciones {
	
	private Map<String, String> estados;
	
	/**
	 * Constructor que inicializa el mapa de estados.
	 */
	public Acciones() {
		estados = new HashMap<String, String>();
		
	}

	/**
	 * Metodo Setter que estable la accion de un determinado estado.
	 * @param estado el estado que queremos cambiar la accion
	 * @param accion la accion que queremos establecer.
	 */
	public void setAccion(String estado, String accion) {
		estados.put(estado, accion);
	}

	/**
	 * Metodo getter que devuelve la accion de un estado
	 * @param estado el estado que queremos obtener la accion
	 * @return la accion del estado.
	 */
	public String getAccion(String estado) {
		return estados.get(estado);
	}

}