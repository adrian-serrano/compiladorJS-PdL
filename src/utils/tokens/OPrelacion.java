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

/* Esta clase genera los tokens de tipo OPrelacion
 * 
 */
/**
 * En esta clase se implementan los operadores de relacion < / >
 *
 */
public class OPrelacion implements Tokens {
    private String operador;

	/**
	 * <i><b>OPrelacion(String operador)</b></i> <br>
	 * Metodo constructor que iniciliza el atributo operador
	 * 
	 */
    public OPrelacion(String operador){
        this.operador = operador;
    }

	/**
	 * <i><b>tipoToken()</b></i> <br>
	 * Metodo que devuelve el tipo de token
	 * 
	 * @return String
	 */
    @Override
    public String convertToString() {
        return "<OpRel," + tipoToken() + ">";
    }

	/**
	 * <i><b>convertToString()</b></i> <br>
	 * Metodo que genera el String del token
	 * 
	 * @return String
	 */
    @Override
    public String tipoToken() {
        if( operador.equals("<"))
        return  "menor";
        if( operador.equals(">"))
        return  "mayor";
        else {    				// En caso de que no sea ninguno de los anteriores devolvemos error
        	System.out.println("Error, se ha generado un token erroneo: OP.RELACIONAL + "+ operador);
			return null;
        }
    }
}
