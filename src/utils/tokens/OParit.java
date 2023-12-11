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

/* Esta clase genera los tokens de tipo Oparit
 * 
 */


/**
 * En esta clase se implementan el + y el - de los operadores aritmeticos
 * @author Andres Ramos Colombo
 *
 */
public class OParit implements Tokens{
	private String operador;
	

	/**
	 * Metodo constructor que iniciliza el atributo operador
	 * @param operador
	 */
	public OParit(String operador){
		super();
		this.operador = operador;
	}
	
	/**
	 * Metodo que devuelve el tipo de token
	 * @return String
	 */
	@Override
	public String tipoToken() {
		
		//Puede ser  + y -
		if(operador.equals("+")) {
			return "mas";
		}
		
		else if(operador.equals("-")) {
			return "menos";
		}
		else{ //Si no es ninguno de los anteriores devolvemos error
			System.out.println("Error, se ha generado un token erroneo: OP.ARITMETICO + "+ operador);
			return null;
		}
	}

	/**
	 * Metodo que genera el String del token
	 * @return String
	 */
	@Override
	public String convertToString() {
		return "<OParit, " + tipoToken() + ">";
	}
}