package utils.tabla;

import java.util.ArrayList;
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

public class AuxTS {

	private static Stack<TS> localStack = new Stack<TS>();
	private static Stack<TS> tableStack = new Stack<TS>();


	@SuppressWarnings("serial")
	private static ArrayList<String> reservada = new ArrayList<String>() {{
		add("true");
		add("false");
		add("if");
		add("else");
		add("return");
		add("let");
		add("number");
		add("string");
		add("boolean");
		add("input");
		add("alert");
		add("function");
	}};
	private static String funcionM;
	private static boolean flagDU = false;//True = Declaracion, False = Uso
	private static boolean flagVF = false;//True = Var, False = Function

	
	/**
	 * Metodo getter que devuleve FalgDU
	 * @return FlagDU
	 */
	public static boolean getFlagDU(){ return flagDU; }
	
	/**
	 * Metodo que declara el Flag a true
	 */
	public static void flagDeclaracion(){ flagDU = true; }

	/**
	 * Metodo que declara el Flag a false
	 */
	public static void flagUso(){ flagDU = false; }
	
	/**
	 * Metodo getter que devuelve el flagVF
	 * @return flagVF
	 */
	public static boolean getFlagVF(){ return flagVF; }

	/**
	 * Metodo que declara flagVF a true
	 */
	public static void flagVar(){ flagVF = true; }

	/**
	 * Metodo que declara flagVF a false
	 */
	public static void flagFunction(){ flagVF = false; }

	/**
	 * Metodo que retorna la funcion
	 * @return funcionM
	 */
	public static String getFuncion() { return funcionM; }
	
	/**
	 * Metodo setter que estable el valor de funcionM
	 * @param string valor de funcionM
	 */
	public static void setFuncionM(String string) { AuxTS.funcionM = string; }


	/**
	 * Meetodo que ve si es palabra reservada o no
	 * @param palabra la palabra que se va a analizar
	 * @return true si es palabra reservada, false si no lo es.
	 */
	public static boolean esReservada(String palabra) {
		return reservada.contains(palabra);
	}
	
	/**
	 * Metodo que inserta el lexema en la pila. 
	 * @param lex El lexema que va a insertar
	 * @return 
	 */
	public static int insertarLexema(String lex) {
		if(!tableStack.isEmpty()) {
			return tableStack.peek().insertarLex(lex);
		}
		return -1;
	}
	
	/**
	 * Metodo getter que devuelve el lexema.
	 * @param pos La posicion 
	 * @param nombreTabla La tabla
	 * @return El lexema. 
	 */
	public static String getLexema(int pos, String nombreTabla){
		String resultado = "";
		boolean encontrado = false;
		TS tabla;
		for(int i=0; i < tableStack.size() && !encontrado; i++){
			tabla = tableStack.get(i);
			if(tabla.getNombreTabla().contains(nombreTabla)){
				resultado = tabla.buscarLexema(pos);
				encontrado = true;
			}
		}
		return resultado;
	}
	
	/**
	 * Metodo que inserta el lexema de la variable global.
	 * @param lex El lexema que va a insertar
	 * @return 
	 */
	public static int insertarVarGlobal(String lex) {
		if(!tableStack.isEmpty()) {
			return tableStack.get(0).insertarLex(lex);
		}
		return -1;
	}

	/**
	 * Metodo que busca la posicion en la tabla de un lexema. 
	 * @param lex El lexema que buscaremos el ID
	 * @return el ID
	 */
	public static int buscarPos(String lex) {
		int pos = -1;
		boolean encontrado = false;
		if(!tableStack.isEmpty()) {
			for(int i = 0; i<tableStack.size()&&!encontrado;i++) {
				if(tableStack.get(i).buscarPos(lex) > 0) {
					pos = tableStack.get(i).buscarPos(lex);
					encontrado = true;

				}
			}
		}
		return pos;
	}
	
	/**
	 * Metodo que inserta el desplazamiento.
	 * @param lexema Lexema al que se le inserta el desplazamiento
	 * @param desp El desplazamiento
	 */
	public static void insertaDespTS(String lexema, String desp){
		boolean insertado=false;
		for(int i=tableStack.size()-1;i>-1 &&!insertado;i--) {
			insertado=tableStack.get(i).insertaDesp(lexema, desp);
		}
	}
	
	/**
	 * Metodo getter que retorna el desplazamientio
	 * @param esVarGlobal booleano que indica si es var global
	 * @return El desplazamiento
	 */
	public static int getDesp(boolean esVarGlobal){
		if(!esVarGlobal)
			return tableStack.peek().getDesp();
		return tableStack.get(0).getDesp();
	}

	/**
	 * Metodo que suma el desplazamiento
	 * @param x Valor de desplazamiento a sumar
	 * @param esVarGlobal booleano que indica si es var global
	 */
	public static void sumDesp(int x,boolean esVarGlobal){
		if(!esVarGlobal)
			tableStack.peek().sumDesp(x);
		else
			tableStack.get(0).sumDesp(x);
	}
	
	/**
	 * Metodo que inserta el tipo del lexema en la tabla de simbolo
	 * @param lexema El lexema
	 * @param tipo El tipo
	 * @param esVarGlobal booleano que indica si es var global
	 * @return true si se ha realizado la insercion del tipo.
	 */
	public static void insertaTipoTS(String lexema, String tipo,boolean esVarGlobal) {
	
		if(!tableStack.isEmpty()&& !esVarGlobal){
			tableStack.peek().insertaTipo(lexema, tipo);
		}
		else {
			tableStack.get(0).insertaTipo(lexema, tipo);
		}
	
	}
	
	/**
	 * Metodo que busca el tipo en la tabla
	 * @param id ID de lo que se busca
	 * @return El tipo
	 */
	public static String buscaTipoTS(String id){
		String tipo = "-";
		boolean found = false;
		if(!tableStack.isEmpty()){
			for(int i=tableStack.size()-1; i > -1 && !found; i--){
				tipo = tableStack.get(i).buscaTipo(id);
				if(!tipo.equals("-")){
					found = true;
				}
			}
		}
		return tipo;
	}
	
	
	/**
	 * Metodo que inserta el tipo de parametro
	 * @param lexema El lexema en el cual se realiza la busqueda
	 * @param tipoParam El tipo de parametro
	 * @return Booleano que indica si se ha insertado
	 */
	public static void insertarTipoParam(String lexema,String tipoParam){
		 tableStack.get(0).insertarTipoParam(lexema, tipoParam);
	}

	/**
	 * Metodo que busca el tipo DevTS
	 * @param lexema Lexema que se hara la busqueda
	 * @return retorna el tipo DvsTs para el lexema que se 
	 */
	public static String buscaTipoDevTS(String lexema){
		return tableStack.get(0).buscaTipoRet(lexema);
	}

	/**
	 * Metodo que inserta el tipo DevTS
	 * @param lexema El lexema al que se le asigna el tipo
	 * @param tipo El tipo que se le asigna
	 */
	public static void insertaTipoDevTS(String lexema, String tipo){
		tableStack.get(0).insertaTipoRet(lexema, tipo);
	}
	
	/**
	 * Metodo que inserta la etiqueta de la funcion
	 * @param lexema lexema al cual se le inserta
	 */
	public static void insertarEtiqueta(String lexema) {
		tableStack.get(0).insertarEtiqueta(lexema);
	}
	
	/**
	 * Metodo que crea la Tabla
	 * @param lex Nombre de la tabla. 
	 */
	public static void crearTabla(String lex) {
		tableStack.push(new TS(lex));

	}
	
	/**
	 * Metodo que elimina una tabla de la pila.
	 */
	public static void eliminarTS() {
		localStack.push(tableStack.pop());
	}


	
	/**
	 * Metodo getter que devuelve la pila de las tablas.
	 * @return la pila de las tablas.
	 */
	public static Stack<TS> getPilaTablas() { return tableStack; }
	
	/**
	 * Metodo que imprime las tablas.
	 */
	
	public static void printTablas(){
		//Printear la tabla Global creando/sobreescribiendo el fichero tabla_simbolos.txt
		for(int i=0; i <tableStack.size() ; i++){
			tableStack.get(i).printTablaSimbolos(false, 1); 
		}
		int number=2;
		for(int i=0; i <localStack.size() ; i++){
			localStack.get(i).printTablaSimbolos(true, number); 
			number++;
		}
	}
	

	
	/**
	 * Metodo que retorna el nombre de la tabla en la que estamos
	 * @return nombre de la tabla.
	 */
	public static String nombreTablaActual(){ return tableStack.peek().getNombreTabla(); }



	/**
	 * Metodo que retorna el nombre de la tabla que pertenece un lexema.
	 * @param lex El lexema
	 * @return El nombre de la tabla
	 */
	public static String nombreTablaPertenece(String lex) {
		String resultado = null;
		boolean encontrado = false;
		if(!tableStack.isEmpty()) {
			for(int i = 0; i<tableStack.size()&&!encontrado;i++) {
				if(tableStack.get(i).buscarPos(lex) > 0) {
					resultado = tableStack.get(i).getNombreTabla();
					encontrado = true;

				}
			}
		}
		return resultado;
	}



	/**
	 * Metodo que comprueba si los parametros recibidos al llamar una funcion son los que corresponden
	 * @param lex El lexema que se comprueba 
	 * @param tipo El tipo
	 * @return true si son correctos, false en caso contrario
	 */
	public static boolean checkParam(String lex,String tipo) {
		boolean resultado = false;

		String[]aux=tipo.split("/");

		if(tableStack.get(0).buscarNumParam(lex)==aux.length) {

			for(int i=0;i<aux.length;i++) {
				if(aux[i].equals("true") || aux[i].equals("false")) {
					resultado = tableStack.get(0).buscarTipoParam(lex,i).equals("boolean");
				}
				else if(aux[i].equals("ent")){
					resultado=tableStack.get(0).buscarTipoParam(lex,i).equals("number");
				}
				else {
					resultado=aux[i].equals(tableStack.get(0).buscarTipoParam(lex,i));
				}
			}
		}
		return resultado;
	}





}
