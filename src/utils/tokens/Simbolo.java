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

/* Esta clase genera los tokens de tipo Simbolo
 * 
 */
public class Simbolo implements Tokens {
	private String lex;
	
	/**
	 * Metodo constructor que iniciliza el atributo Lexema
	 * 
	 */
	public Simbolo(String simbolo) {
		super();
		this.lex = simbolo;
	}
	
	/**
	 * Metodo que devuelve el tipo de token
	 * @return String
	 */
	@Override
	public String tipoToken() {
		
		String resultado = "";
		// ( ) { } . ,
		if(lex.equals("("))resultado="PARENTabr";
		else if(lex.equals(")"))resultado="PARENTcerr";
		else if(lex.equals("{"))resultado="LLAVabr";
		else if(lex.equals("}"))resultado="LLAVcerr";
		else if(lex.equals(";"))resultado="puntoComa";
		else if(lex.equals(","))resultado="coma";
		else if(lex.equals("$"))resultado="$";
		else{
			System.out.println("Error, token erroneo: SIMBOLO + "+ lex);
			return null;
		}
		return resultado;
	}
	

	/**
	 * Metodo que genera el String del token
	 * @return String
	 */
	@Override
	public String convertToString() {
		return "<" + tipoToken() + ", >";

	}
}
