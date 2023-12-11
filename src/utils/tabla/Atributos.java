package utils.tabla;

import java.util.Stack;

/**
 * Autores: Guillermo Buendia del Campo, Adrian Serrano Lopez-Alvarez y Andres Ramos Colombo, 
 * 
 * Fecha de inicio: 19/10/2020
 * 
 * Fecha de ultima modificacion: 06/01/2021
 * 
 * Proyecto: Practica PDL
 *
 */


public class Atributos {
	private int pos;
	private String lexema;//nombre de la variable o funcion
	
	private String tipo;//tipo:cadena, entero, logico, funcion
	private String despl;//desplazamiento TS
	private int numParam;//numero de parametros en la funcion
	private Stack<String> parametros;//parametros en la funcion
	private String modoParam; //Si se para por referencia o por valor
	private String tipoRetorno;//tipo de devolucion de la funcion
	private String etiqFuncion; //La etiqueta que se le asigna a cada funcion

	/**
	 * Constructor que inicializa la Entrada a la tabla de simbolos.
	 * @param pos
	 */
	public Atributos(int pos){
		this.pos = pos;
		this.lexema = "-";
		this.tipo = "-";
		this.despl = "-";
		this.numParam = 0;
		this.parametros = new Stack<String> ();
		this.modoParam = "1";
		this.tipoRetorno = "-";
		this.etiqFuncion = "-";
	}



	/**
	 * Metodo getter que devuelve la posicion. 
	 * @return La posicion
	 */
	public int getPos() { return pos; }
	

	
	/**
	 * Metodo getter que devuelve el lexema
	 * @return El lexema
	 */
	public String getLexema() { return lexema; }
	
	/**
	 * Metodo setter que establece el valor del lexema
	 * @param lexema El lexema
	 */
	public void setLexema(String lexema) { this.lexema = lexema; }
	
	/**
	 * Metodo getter que devuelve el tipo
	 * @return El tipo
	 */
	public String getTipo() { return tipo; }
	
	/**
	 * Metodo setter que establece el valor del tipo
	 * @param tipo
	 */
	public void setTipo(String tipo) { this.tipo = tipo; }
	
	/**
	 * Metodo getter que devuelve el desplazamiento
	 * @return El despl
	 */
	public String getDespl() { return despl; }
	
	/**
	 * Metodo setter que establece el valor del desplazamiento
	 * @param despl El desplazamiento
	 */
	public void setDespl(String despl) { this.despl = despl; }
	
	/**
	 * Metodo getter que devuelve el numero de parametros.
	 * @return numParam 
	 */
	public int getNumParam() { return numParam; }
	
	
	/**
	 * Metodo getter que devuelve el tipo del parametro.
	 * @return Tipo de parametro
	 */
	public String getTipoParam(int pos) { return this.parametros.get(pos); }
	
	/**
	 * Metodo setter que establece el tipo del parametro.
	 * @param tipoParam Tipo de parametro.
	 */
	public void setTipoParam(String tipoParam) {
        this.parametros.add(0, tipoParam);
        this.numParam++;
    }
	
	/**
	 * Metodo getter que devuelve el tipo de retorno.
	 * @return tipoRetorno
	 */
	public String getTipoRet() { return this.tipoRetorno; }
	
	/**
	 * Metodo setter que establece el tipo de retorno.
	 * @param tipoRet Tipo de retorno.
	 */
	public void setTipoRet(String tipoRet) { this.tipoRetorno = tipoRet; }
	
	/**
	 * Metodo getter que devuelve la etiqueta.
	 * @return etiqFuncion
	 */
	public String getEtiqueta() { return etiqFuncion; }
	
	/**
	 * Metodo setter que establece el tipo etiqueta.
	 * @param etiq El tipo etiqueta.
	 */
	public void setEtiqueta(String etiq) { this.etiqFuncion = etiq; }

	/**
	 * Metodo getter que devuelve el modo parametro.
	 * @return modoParam
	 */
	public String getModoParam() { return modoParam; }

}
