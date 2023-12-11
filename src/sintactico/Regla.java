package sintactico;

import java.util.Stack;
import semantico.Atributo;
import utils.GestorErrores;
import utils.tabla.AuxTS;

/**
 * Autores: Guillermo Buendia del Campo, Adrian Serrano Lopez-Alvarez, Andres Ramos Colombo, 
 * 
 * Fecha de inicio: 19/10/2020
 * 
 * Fecha de ultima modificacion: 11/1/2020
 * 
 * Proyecto: Practica PDL
 *
 */

public class Regla {

	public int numero; //  el numero de regla que se le asocia
	public String parteIzq; // el antecedente de la regla
	public int nElementosDer; // el consecuente de la regla
	private static Stack<String> param;
	/**
	 * Metodo que inicaliza una regla y guarda su informacion basica
	 * @param numero
	 * @param parteIzq
	 * @param nElementosDer
	 */
	public Regla(int numero, String parteIzq, int nElementosDer){
		this.numero = numero; 
		this.parteIzq = parteIzq;
		this.nElementosDer = nElementosDer;
		Regla.param = new Stack<String>();
	}
	/**
	 * Metodo que ejecuta la siguiente accion mandada por el analizador sintactico y lleva acabo el trabajo del analisis semantico generando los tipos
	 * @param numRegla regla que se va a ejecutar
	 * @param pilaSimbolos la pila que almacena los simbolos leidos	
	 * @return tipo devuelve el tipo de la regla ejecutada
	 */
	public static String ejecutarAccion(int numRegla, Stack<Atributo> pilaSimbolos){
		String tipo = "-";				
		Atributo startsl, main, bloque, bloque2, bloque3, bloque4, funcion, sentencia, sentencia2 , expresion, expresion1, cuerpo,  id, callFun, callFun2, argumentos,
		myReturn, argumentos2, argumentos21, aritmetica, sencillo, type, sencillofun;

		switch(numRegla){
		case 2://START -> STARTSL
			// { if (STARTSL.tipo = "ok") then NO CONTIENE ERRORES; else ERROR }
			startsl = pilaSimbolos.peek();
			tipo = startsl.getTipo();
			if(tipo.equals("ok")) {
				System.out.println("El programa analizado no contiene errores");
			}
			else System.out.println("El programa analizado contiene errores");
			break;
		case 3://STARTSL -> MAIN
			//{STARTSL.tipo = MAIN.tipo }
			main = pilaSimbolos.peek();
			tipo =  main.getTipo();
			break;
		case 4://STARTSL -> lambda;
			//{STARTSL.tipo = “ok”}
			tipo = "ok";
			break;
		case 5://MAIN -> FUNCION
			//{if(FUNCION.tipo = "ok") then MAIN.tipo = MAIN1.tipo else MAIN.tipo = "error"}
			main = pilaSimbolos.peek();
			tipo = main.getTipo();
			break;
		case 6://MAIN -> FUNCION  MAIN
			//{ if(FUNCION.tipo = "ok") then MAIN.tipo = MAIN1.tipo; else MAIN.tipo = "error" }
			main = pilaSimbolos.peek();
			funcion = pilaSimbolos.get(pilaSimbolos.size()-2);
			if(funcion.getTipo().equals("ok"))tipo = main.getTipo();
			else { 
				GestorErrores.addError("Error semantico en la regla 6 no se ha resuleto correctamente la funcion");
				tipo = "error";
			}
			break;
		case 7://MAIN -> BLOQUE
			//{ MAIN.tipo = BLOQUE.tipo }
			bloque = pilaSimbolos.peek();
			tipo = bloque.getTipo();
			break;
		case 8://MAIN -> BLOQUE MAIN
			// { if(BLOQUE.tipo = "ok") then MAIN.tipo = MAIN1.tipo else MAIN.tipo = "error" }
			main = pilaSimbolos.peek();
			bloque = pilaSimbolos.get(pilaSimbolos.size()-2);
			if(bloque.getTipo().equals("ok"))tipo = main.getTipo();
			else { 
				GestorErrores.addError("Error semantico en la regla 7");
				tipo = "error";
			}
			break;
		case 9://FUNCION ->function TIPO id  PARENTabr ARGUMENTOS PARENTcerr  LLAVabr  CUERPO LLAVcerr  
			/* { if(TIPO.tipo = “lambda”){ then if(ARGUMENTOS.tipo = "ok") then FUNCION.tipo = CUERPO.tipo else FUNCION.tipo = "error"}
					else if(TIPO.tipo = “number”||TIPO.tipo = “boolean”||TIPO.tipo = “string”){ then if(ARGUMENTOS.tipo = "ok") 
					then FUNCION.tipo = CUERPO.tipo; else FUNCION.tipo = "error" }
					else FUNCION.tipo = "error"
					if(buscarTipoDevTs = “vacio”) then if(!TIPO.tipo = ”lambda”)  then error; else if(!buscarTipoDevTs = TIPO.tipo && !TIPO.tipo = ”lambda”)
					then error if(buscarTipoDevTs = “-”) then insertarTipoDevTs(“vacio”)
					eliminarTs
					} 
			 */
			cuerpo = pilaSimbolos.get(pilaSimbolos.size()-2);
			argumentos = pilaSimbolos.get(pilaSimbolos.size()-5);
			type = pilaSimbolos.get(pilaSimbolos.size()-8);
			id = pilaSimbolos.get(pilaSimbolos.size()-7);

			if(type.getTipo().equals("lambda")) {
				if(argumentos.getTipo().equals("ok")) {
					AuxTS.insertaTipoTS(id.getLexema(), "vacio", false);
					tipo = cuerpo.getTipo();
				}else{ 
					GestorErrores.addError("Error semantico en la regla 9 los parametros de la funcion son incorrectos");
					tipo = "error";
				}
			}
			else if(type.getTipo().equals("number")|| type.getTipo().equals("string") || type.getTipo().equals("boolean")){
				if(argumentos.getTipo().equals("ok")) {
					AuxTS.insertaTipoTS(id.getLexema(), type.getTipo(), false);
					tipo = cuerpo.getTipo();
				}
				else { 
					GestorErrores.addError("Error semantico en la regla 9 los parametros de la funcion son incorrectos");
					tipo = "error";
				}
			}
			else { 
				GestorErrores.addError("Error semantico en la regla 9 la declaracion del tipo de funcion no es valido");
				tipo = "error";
			}
			if (AuxTS.buscaTipoDevTS(id.getLexema()).equals("vacio") ) {
				if(!type.getTipo().equals("lambda")) {
					GestorErrores.addError("Error semantico en la regla 9 la declaracion del tipo de funcion no se corresponde con el valor retornadov");
					tipo = "error";
				}
			}
			else if (!AuxTS.buscaTipoDevTS(id.getLexema()).equals(type.getTipo())&&!type.getTipo().equals("lambda")) {
				GestorErrores.addError("Error semantico en la regla 9 la declaracion del tipo de funcion no se corresponde con el valor retornado");
				tipo = "error";
			}
			if (AuxTS.buscaTipoDevTS(id.getLexema()).equals("-") ) {					
				AuxTS.insertaTipoDevTS(id.getLexema(), "vacio");
			}

			AuxTS.insertarEtiqueta(id.getLexema());
			AuxTS.eliminarTS();
			break;

		case 10://BLOQUE -> let TIPO id puntoComa
			//{if(TIPO.tipo = “lambda”) then BLOQUE.tipo = “error” else{ BLOQUE.tipo = "ok" insertaTipoTs(id.lexema, TIPO.tipo) }

			type = pilaSimbolos.get(pilaSimbolos.size()-3);
			id = pilaSimbolos.get(pilaSimbolos.size()-2);
			if(type.getTipo().equals("lambda")) {
				tipo = "error";
				GestorErrores.addError("Error semantico en la regla 10 el tipo de la declaracion de funcion no es correcto");
			}else {
				AuxTS.insertaTipoTS(id.getLexema(), type.getTipo(), false);
				int desp = AuxTS.getDesp(false);
				AuxTS.insertaDespTS(id.getLexema(), Integer.toString(desp));
				if(type.getTipo().equals("string")) {
					AuxTS.sumDesp(64,false);
				}
				else{
					AuxTS.sumDesp(1,false);
				}

				tipo = "ok";}
			break;

		case 11://BLOQUE -> SENTENCIA
			//{ BLOQUE.tipo = SENTENCIA.tipo }
			sentencia = pilaSimbolos.peek();
			tipo = sentencia.getTipo();
			break;
		case 12://BLOQUE -> if  PARENTabr  EXPRESION  PARENTcerr BLOQUE2;
			// {if(EXPRESION.tipo = "true"||EXPRESION.tipo = "false"||EXPRESION.tipo = "boolean") then BLOQUE.tipo = BLOQUE2.tipo else BLOQUE.tipo = "error"}
			bloque2 = pilaSimbolos.peek();
			expresion = pilaSimbolos.get(pilaSimbolos.size()-3);
			if(expresion.getTipo().contains("true")||expresion.getTipo().equals("boolean")|| expresion.getTipo().contains("false"))tipo = bloque2.getTipo();
			else {
				tipo = "error";
				GestorErrores.addError("Error semantico en la regla 12 la condicion del if no es de tipo logico");
			}
			break;
		case 13: // BLOQUE2 -> SENTENCIA 
			//{ BLOQUE2.tipo = SENTENCIA.tipo }
			sentencia = pilaSimbolos.peek();
			tipo = sentencia.getTipo();
			break;
		case 14: // BLOQUE2 -> BLOQUE3
			//{ BLOQUE2.tipo = BLOQUE3.tipo }
			bloque3 = pilaSimbolos.peek();
			tipo = bloque3.getTipo();
			break;

		case 15://BLOQUE3 -> LLAVabr  CUERPO  LLAVcerr BLOQUE4 
			//{ if (!BLOQUE4 = “error”) then BLOQUE3.tipo = CUERPO.tipo }
			cuerpo = pilaSimbolos.get(pilaSimbolos.size()-3);
			bloque4 = pilaSimbolos.peek();
			if(!bloque4.getTipo().equals("error"))tipo = cuerpo.getTipo();
			else {
				tipo = "error";
				GestorErrores.addError("Error en el cuerpo del if");
			}
			break;
		case 16: //BLOQUE4 ->  lambda 
			//{BLOQUE4.tipo = “ok”}
			tipo = "ok";
			break;
		case 17://BLOQUE4 ->   else LLAVabr CUERPO  LLAVcerr 
			//{if (!CUERPO = “error”) then BLOQUE4.tipo = CUERPO.tipo }
			cuerpo = pilaSimbolos.get(pilaSimbolos.size()-2);
			if(!cuerpo.getTipo().equals("error")) tipo = cuerpo.getTipo();
			else {
				tipo = "error";

				GestorErrores.addError("Error en el cuerpo del else");
			}
			break;

		case 18: //SENTENCIA -> id  SENTENCIA2
			/*{ if(SENTENCIA2.tipo = “error” || SENTENCIA2.tipo = “-” ) then SENTENCIA.tipo = “error”
					else if(SENTENCIA2.tipo.contains(“igual”)){	if (id.tipo = “number” && ((SENTENCIA2.tipo.contains(”ent”) 
					|| SENTENCIA2.tipo.contains(“number”)) then SENTENCIA.tipo = “ok” if (id.tipo = “string” && ((SENTENCIA2.tipo.contains(”string”) 
					|| SENTENCIA2.tipo.contains(“cadena”)) then SENTENCIA.tipo = “ok”
					if (id.tipo = “boolean” && ((SENTENCIA2.tipo.contains(”true”) || SENTENCIA2.tipo.contains(“false”)|| SENTENCIA2.tipo.contains(“boolean”))
					then SENTENCIA.tipo = “ok” else then SENTENCIA.tipo = “error” }
					else if(SENTENCIA2.tipo.contains(“operacion”)){ if(id.tipo = “number”) then SENTENCIA.tipo = “ok”
					else then SENTENCIA.tipo = “error”} else if(SENTENCIA2.tipo.contains(“dec”)){ if(id.tipo = “number”) then SENTENCIA.tipo = “ok”
					else then SENTENCIA.tipo = “error” } else if(!id.tipo = “-” && SENTENCIA2.tipo.contains(“ok”)){ then SENTENCIA.tipo = “ok” }
					else SENTENCIA.tipo = “error”}
			 */
			id = pilaSimbolos.get(pilaSimbolos.size()-2);
			sentencia2 = pilaSimbolos.peek();	
			if(sentencia2.getTipo().contains("error")||sentencia2.getTipo().contains("-")) {
				GestorErrores.addError("Error semantico en la regla 18 la parte posterior al id no se ha resuelto correctamente");
				tipo = "error";
			}
			else if(sentencia2.getTipo().contains("igual")) {
				if(id.getTipo().equals("number") && (sentencia2.getTipo().contains("ent")||sentencia2.getTipo().contains("number"))) {
					tipo = "ok";
				}
				else if(id.getTipo().equals("string") && (sentencia2.getTipo().contains("cadena")||sentencia2.getTipo().contains("string"))) {
					tipo = "ok";
				}
				else if(id.getTipo().equals("boolean") && (sentencia2.getTipo().contains("boolean")||sentencia2.getTipo().contains("true")||sentencia2.getTipo().contains("false"))) {
					tipo = "ok";
				}
				else {
					GestorErrores.addError("Error semantico en la regla 18 el lado izquierdo de la igualdad es de tipo: " + id.getTipo() +" y el derecho es de tipo: " + sentencia2.getTipo());
					tipo = "error";
				}
			}
			else if(sentencia2.getTipo().contains("operacion")) {
				if(id.getTipo().equals("number")) {
					tipo = "ok";
				}
				else {
					GestorErrores.addError("Error semantico en la regla 18 el id de la operacions es de tipo: " + id.getTipo() +" y deberia ser de tipo number");
					tipo = "error";
				}
			}
			else if(sentencia2.getTipo().contains("dec")) {
				if(id.getTipo().equals("number")) {
					tipo = "ok";
				}
				else {
					GestorErrores.addError("Error semantico en la regla 18 el id del decremento es de tipo: " + id.getTipo() +" y deberia ser de tipo number");
					tipo = "error";
				}
			}
			else if(!id.getTipo().equals("-")&&sentencia2.getTipo().contains("ok"))
				tipo = "ok";
			else {
				GestorErrores.addError("Error semantico en la regla 18 funcion no definida");
				tipo = "error";
			}
			break;
		case 19://SENTENCIA -> alert  PARENTabr  EXPRESION  PARENTcerr puntoComa
			//{if(EXPRESION.tipo = “number” || EXPRESION.tipo = “string”||EXPRESION.tipo = “ent” || EXPRESION.tipo = “cadena”) then SENTENCIA.tipo = “ok”
			//else  SENTENCIA.tipo = “error”}
			expresion = pilaSimbolos.get(pilaSimbolos.size()-3);
			if(expresion.getTipo().equals("number") || expresion.getTipo().equals("string")||
					expresion.getTipo().equals("ent")|| expresion.getTipo().equals("cadena"))tipo = "ok";
			else { 
				GestorErrores.addError("Error semantico en la regla 19 el tipo de la expresion en alert no es un numero o una cadena");
				tipo = "error";
			}
			break;
		case 20://SENTENCIA -> input  PARENTabr  id  PARENTcerr puntoComa 
			//{if(id.tipo = “number” || id.tipo = “string”) then SENTENCIA.tipo = “ok”
			//else  SENTENCIA.tipo = “error”}
			id = pilaSimbolos.get(pilaSimbolos.size()-3);
			if(id.getTipo().equals("number") || id.getTipo().equals("string"))tipo = "ok";
			else {
				tipo = "error";
				GestorErrores.addError("Error semantico en la regla 20 el tipo de la expresion en input no es un numero o una cadena");
			}
			break;

		case 21://SENTENCIA -> return  RETURN puntoComa
			//{if (RETURN.tipo = “ent” || RETURN.tipo = “number”){then insertaTipoDevTs(“number”)SENTENCIA.tipo = “ok”}
			//else if (RETURN.tipo = “string” || RETURN.tipo = “cadena”){then insertaTipoDevTs(“string”)SENTENCIA.tipo = “ok”}
			//else if (RETURN.tipo = “true” || RETURN.tipo = “false”|| RETURN.tipo = “boolean”){then insertaTipoDevTs(“boolena”)SENTENCIA.tipo = “ok”}
			//else if (RETURN.tipo = “vacio”){then insertaTipoDevTs(“vacio”)SENTENCIA.tipo = “ok”}		
			//else SENTENCIA.tipo = “error”}
			myReturn = pilaSimbolos.get(pilaSimbolos.size()-2);
			if(myReturn.getTipo().equals("ent") ||myReturn.getTipo().equals("number")) {
				AuxTS.insertaTipoDevTS(AuxTS.getFuncion(), "number");
				tipo = "ok";
			}
			else if(myReturn.getTipo().equals("string") ||myReturn.getTipo().equals("cadena")) {
				AuxTS.insertaTipoDevTS(AuxTS.getFuncion(), "string");
				tipo = "ok";
			}
			else if(myReturn.getTipo().equals("true")|| myReturn.getTipo().equals("false") ||myReturn.getTipo().equals("boolean")) {
				AuxTS.insertaTipoDevTS(AuxTS.getFuncion(), "boolean");
				tipo = "ok";
			}
			else if( myReturn.getTipo().equals("vacio")) {
				AuxTS.insertaTipoDevTS(AuxTS.getFuncion(), "vacio");
				tipo = "ok";
			}
			else { 
				GestorErrores.addError("Error semantico en la regla 21 la expresion despues del return no es valida");
				tipo = "error";
			}
			break;
		case 22://SENTENCIA2 ->   igual EXPRESION  puntoComa{
			//if(!EXPRESION.tipo = “error”)then EXPRESION.tipo = EXPRESION1.tipo + “/igual” else SENTENCIA2.tipo = “error”}
			expresion =  pilaSimbolos.get(pilaSimbolos.size()-2);
			id =  pilaSimbolos.get(pilaSimbolos.size()-3);
			if(!expresion.getTipo().equals("error")) {
				tipo = expresion.getTipo()+ "/igual";
			}
			else {
				tipo = "error";			
				GestorErrores.addError("Error semantico en la regla 22 la expresion a la izquerda del igual es incorrecta");
			}
			break;
		case 23://SENTENCIA2 ->   mas  EXPRESION  puntoComa{
			//if(EXPRESION.tipo = “number” || EXPRESION.tipo = “ent”)then EXPRESION.tipo = EXPRESION1.tipo + “/operacion” else SENTENCIA2.tipo = “error”}
			expresion =  pilaSimbolos.get(pilaSimbolos.size()-2);
			if(expresion.getTipo().equals("number") || expresion.getTipo().equals("ent")) {
				tipo = expresion.getTipo()+ "/operacion";
			}
			else {
				tipo = "error";			
				GestorErrores.addError("Error semantico en la regla 23 la expresion a la izquerda del mas es incorrecta");
			}
			break;
		case 24://SENTENCIA2 ->   menos  EXPRESION puntoComa {
			//if(EXPRESION.tipo = “number” || EXPRESION.tipo = “ent”) then EXPRESION.tipo = EXPRESION1.tipo + “/operacion” else SENTENCIA2.tipo = “error”}
			expresion =  pilaSimbolos.get(pilaSimbolos.size()-2);
			if(expresion.getTipo().equals("number")|| expresion.getTipo().equals("ent")) {
				tipo = expresion.getTipo()+ "/operacion";
			}
			else {
				tipo = "error";			
				GestorErrores.addError("Error semantico en la regla 24 la expresion a la izquerda del menos es incorrecta");
			}
			break;
		case 25://SENTENCIA2 ->  OPdecr puntoComa
			//{ SENTENCIA2.tipo = “dec” }
			tipo = "dec";
			break;	

		case 26://SENTENCIA2 ->  PARENTabr  CALLFUN  PARENTcerr puntoComa 
			//{ SENTENCIA2.tipo = CALLFUN.tipo}
			callFun = pilaSimbolos.get(pilaSimbolos.size()-3);
			tipo = callFun.getTipo();
			break;

		case 27://CUERPO -> BLOQUE  CUERPO {
			//if(CUERPO.tipo = “ok” && BLOQUE.tipo = “ok”) then CUERPO.tipo = “ok” else CUERPO.tipo = “error”}
			cuerpo = pilaSimbolos.peek();
			bloque = pilaSimbolos.get(pilaSimbolos.size()-2);
			if(cuerpo.getTipo().equals("ok") && bloque.getTipo().equals("ok")) tipo = "ok";
			else { 
				GestorErrores.addError("Error semantico en la regla 27 el bloque o el cuerpo de la expresion no se han resuelto correctamente");
				tipo = "error";
			}
			break;
		case 28://CUERPO -> lambda
			//{ CUERPO.tipo = "ok" }
			tipo="ok";
			break;


		case 29://RETURN -> EXPRESION 
			//{ RETURN.tipo = EXPRESION.tipo }
			expresion = pilaSimbolos.peek();
			tipo = expresion.getTipo();
			break;
		case 30://RETURN -> lambda 
			//{RETURN.tipo = “vacio”}
			tipo = "vacio";
			break;


		case 31://CALLFUN -> EXPRESION  CALLFUN2
			//{if(EXPRESION.tipo = “ok” || EXPRESION.tipo = “true” || EXPRESION.tipo = “false”|| EXPRESION.tipo = “number”|| EXPRESION.tipo = “ent”||
			//EXPRESION.tipo = “string”|| EXPRESION.tipo = “cadena” || EXPRESION.tipo = “boolean”) then CALLFUN.tipo = EXPRESION.tipo + “/” +  CALLFUN2.tipo
			//else CALLFUN.tipo = “error” }
			callFun = pilaSimbolos.peek();
			expresion = pilaSimbolos.get(pilaSimbolos.size()-2);
			if(expresion.getTipo().equals("ok")||expresion.getTipo().equals("ent")||expresion.getTipo().equals("true")||expresion.getTipo().equals("cadena")||
					expresion.getTipo().equals("boolean")||expresion.getTipo().equals("number")||expresion.getTipo().equals("string")|| expresion.getTipo().equals("false"))
				tipo = expresion.getTipo() + "/" +callFun.getTipo();
			else { 
				GestorErrores.addError("Error semantico en la regla 31 el primero de los parametros de la funcion que se va a invocar son incorrecto");
				tipo = "error";
			}
			break;
		case 32://CALLFUN -> lambda
			//{ CALLFUN.tipo = "ok" }
			tipo = "ok";
			break;


		case 33://CALLFUN2 -> coma  EXPRESION  CALLFUN2 
			//{if(EXPRESION.tipo = “ok” || EXPRESION.tipo = “true” || EXPRESION.tipo = “false”|| EXPRESION.tipo = “number”|| EXPRESION.tipo = “ent”||
			//EXPRESION.tipo = “string”|| EXPRESION.tipo = “cadena” || EXPRESION.tipo = “boolean”) then CALLFUN2.tipo = EXPRESION.tipo + “/” +  CALLFUN21.tipo
			//else CALLFUN2.tipo = “error”}
			callFun2 = pilaSimbolos.peek();
			expresion = pilaSimbolos.get(pilaSimbolos.size()-2);
			if(expresion.getTipo().equals("ok")||expresion.getTipo().equals("true")||expresion.getTipo().equals("number")||  expresion.getTipo().equals("ent")|| 
					expresion.getTipo().equals("false")||expresion.getTipo().equals("boolean")||expresion.getTipo().equals("string")||expresion.getTipo().equals("cadena"))
				tipo = expresion.getTipo()+ "/" + callFun2.getTipo();
			else { 
				GestorErrores.addError("Error semantico en la regla 33 uno o mas de los parametros de la funcion que se va a invocar son incorrectos");
				tipo = "error";
			}
			break;
		case 34://CALLFUN2 -> lambda
			//{ CALLFUN2 .tipo = "ok" }
			tipo = "ok";
			break;


		case 35://ARGUMENTOS -> TIPO id ARGUMENTOS2 
			//{if(TIPO.tipo = “lambda”) then ARGUMENTOS.tipo = “error” else if(TIPO.tipo = “number”||TIPO.tipo = “boolean”||TIPO.tipo = “string”){
			//then insertaTipoTs(id.lexema, TIPO.tipo) insertaTipoParam(tabla(id.lexema), TIPO.tipo) ARGUMENTOS.tipo = ARGUMENTOS2.tipo}}
			type = pilaSimbolos.get(pilaSimbolos.size()-3);
			id = pilaSimbolos.get(pilaSimbolos.size()-2);
			if(type.getTipo().equals("lambda")) {
				tipo = "error";
				GestorErrores.addError("Error semantico en la regla 35 el tipo de la declaracion de parametro de funcion no es correcto");
			}else if(type.getTipo().equals("number")|| type.getTipo().equals("string") || type.getTipo().equals("boolean")){
				AuxTS.insertaTipoTS(id.getLexema(), type.getTipo(), false);	
				AuxTS.insertaDespTS(id.getLexema(), "0");
				if(type.getTipo().equals("string")) {
					AuxTS.sumDesp(64,false);
				}
				else{
					AuxTS.sumDesp(1,false);
				}
				for(int i=0;i<param.size();i++) {

					int desp = AuxTS.getDesp(false);				
					if(param.get(i).contains("string")) {
						param.set(i, param.get(i).replace("/string", ""));
						AuxTS.sumDesp(64,false);
					
					}
					else{
						AuxTS.sumDesp(1,false);
					}
					AuxTS.insertaDespTS(param.get(i), Integer.toString(desp));

				}
				param.clear();

				AuxTS.insertarTipoParam(AuxTS.nombreTablaActual(),type.getTipo());
				argumentos2 = pilaSimbolos.peek();
				tipo = argumentos2.getTipo();
			}
			break;
		case 36://ARGUMENTOS -> lambda
			//{ ARGUMENTOS.tipo = "ok" }
			tipo = "ok";
			break;	
		case 37://ARGUMENTOS2 -> coma TIPO  id  ARGUMENTOS2 
			//{if(TIPO.tipo = “lambda”) then ARGUMENTOS2.tipo = “error” else if(TIPO.tipo = “number”||TIPO.tipo = “boolean”||TIPO.tipo = “string”){
			//then insertaTipoTs(id.lexema, TIPO.tipo) insertaTipoParam(tabla(id.lexema), TIPO.tipo) ARGUMENTOS2.tipo = ARGUMENTOS21.tipo}}
			type = pilaSimbolos.get(pilaSimbolos.size()-3);
			id = pilaSimbolos.get(pilaSimbolos.size()-2);
			if(type.getTipo().equals("lambda")) {
				tipo = "error";
				GestorErrores.addError("Error semantico en la regla 37 el tipo de la declaracion de parametro de  funcion no es correcto");
			}else if(type.getTipo().equals("number")|| type.getTipo().equals("string") || type.getTipo().equals("boolean")){
				AuxTS.insertaTipoTS(id.getLexema(), type.getTipo(), false);
				if(type.getTipo().equals("string")) {
					param.add(0, id.getLexema()+"/string");
				}
				else{
					param.add(0, id.getLexema());
				}
				AuxTS.insertarTipoParam(AuxTS.nombreTablaPertenece(id.getLexema()),type.getTipo());
				argumentos21 = pilaSimbolos.peek();
				tipo = argumentos21.getTipo();
			}
			break;
		case 38://ARGUMENTOS2 -> lambda
			//{ ARGUMENTOS2.tipo = "ok" }
			tipo = "ok";
			break;
		case 39://EXPRESION -> EXPRESION  and EXPRESION1 
			//{if((EXPRESION = “number” || EXPRESION1 = “entr” || EXPRESION1 = “entr”)  && (EXPRESION1 = “number” || EXPRESION1 = “entr”|| EXPRESION1 = “entr”))
			//then EXPRESION.tipo = “boolean” else EXPRESION.tipo = “error”}
			expresion = pilaSimbolos.get(pilaSimbolos.size()-3);
			expresion1 = pilaSimbolos.peek();
			if((expresion.getTipo().equals("boolean")|| expresion.getTipo().equals("true")|| expresion.getTipo().equals("false")) && (
					expresion1.getTipo().equals("boolean")||expresion1.getTipo().equals("true")||expresion1.getTipo().equals("false"))) tipo = "boolean";
			else {
				tipo = "error";
				if(!(expresion.getTipo().equals("boolean")||expresion.getTipo().equals("true")|| expresion.getTipo().equals("false")))
					GestorErrores.addError("Error semantico en la regla 39 la parte izquierda de la comparacion no es de tipo booleano "+ expresion.getTipo() + " && " + expresion1.getTipo());
				else GestorErrores.addError("Error semantico en la regla 39 la parte derecha de la comparacion no es de tipo booleano " + expresion.getTipo() + " && " + expresion1.getTipo());

			}
			break;
		case 40://EXPRESION -> EXPRESION1
			//{EXPRESION.tipo = EXPRESION1.tipo }
			expresion1 = pilaSimbolos.peek();
			tipo = expresion1.getTipo();	
			break;
		case 41://EXPRESION1 -> EXPRESION1  menor  ARITMETICA 
			//{if((EXPRESION1 = “number” || EXPRESION1 = “entr”) && (ARITMETICA = “number” || ARITMETICA = “ent” ))
			//then EXPRESION1.tipo = “boolean” else EXPRESION1.tipo = “error”
			expresion = pilaSimbolos.get(pilaSimbolos.size()-3);
			aritmetica = pilaSimbolos.peek();
			if((expresion.getTipo().equals("ent")|| expresion.getTipo().equals("number"))&& 
					(aritmetica.getTipo().equals("ent")||aritmetica.getTipo().equals("number"))) tipo = "boolean";
			else {
				tipo = "error";
				if(!(expresion.getTipo().equals("ent")|| expresion.getTipo().equals("number")))GestorErrores.addError("Error semantico en la regla 39 la parte izquierda de la comparacion no es de tipo entero " +expresion.getTipo()+" < "+aritmetica.getTipo());
				else GestorErrores.addError("Error semantico en la regla 41 la parte derecha de la comparacion no es de tipo entero "  +expresion.getTipo()+" < "+aritmetica.getTipo());	
			}
			break;
		case 42://EXPRESION1 -> EXPRESION1  mayor  ARITMETICA
			//if((EXPRESION1 = “number” || EXPRESION1 = “entr” )&& (ARITMETICA = “number” || ARITMETICA = “ent” ))
			//then EXPRESION1.tipo = “boolean” else EXPRESION1.tipo = “error”
			expresion = pilaSimbolos.get(pilaSimbolos.size()-3);
			aritmetica = pilaSimbolos.peek();
			if((expresion.getTipo().equals("ent")|| expresion.getTipo().equals("number"))&& 
					(aritmetica.getTipo().equals("ent")||aritmetica.getTipo().equals("number"))) tipo = "boolean";
			else {
				tipo = "error";
				if(!(expresion.getTipo().equals("ent")|| expresion.getTipo().equals("number")))GestorErrores.addError("Error semantico en la regla 40 la parte izquierda de la comparacion no es de tipo entero "+ expresion.getTipo() + " > " + aritmetica.getTipo());
				else GestorErrores.addError("Error semantico en la regla 42 la parte derecha de la comparacion no es de tipo entero " + expresion.getTipo() + " > " + aritmetica.getTipo());

			}
			break;	
		case 43://EXPRESION1 -> ARITMETICA  
			//{ EXPRESION1.tipo = ARITMETICA.tipo }			
			aritmetica = pilaSimbolos.peek();
			tipo = aritmetica.getTipo();
			break;	

		case 44://ARITMETICA -> ARITMETICA  mas  SENCILLO
			//{if((ARITMETICA = “number” || ARITMETICA = “ent”) && (SENCILLO = “number” || SENCILLO = ent”))
			//then ARITMETICA.tipo = “number” else ARITMETICA.tipo = “error”}
			aritmetica = pilaSimbolos.get(pilaSimbolos.size()-3);
			sencillo = pilaSimbolos.peek();
			if((aritmetica.getTipo().equals("number")||aritmetica.getTipo().equals("ent")) && (sencillo.getTipo().equals("number")||sencillo.getTipo().equals("ent"))) tipo = "number";
			else {
				tipo = "error";
				if(!(aritmetica.getTipo().equals("number")||aritmetica.getTipo().equals("ent")))GestorErrores.addError("Regla 43:La parte izquierda de la suma no es de tipo entero");
				else GestorErrores.addError("Error semantico en la regla 44 la parte derecha de la suma no es de tipo entero");
			}
			break;
		case 45://ARITMETICA -> ARITMETICA  menos  SENCILLO
			//{ if((ARITMETICA = “number” || ARITMETICA = “ent”) && (SENCILLO = “number” || SENCILLO = ent”)) 
			// then ARITMETICA.tipo = “number” else ARITMETICA.tipo = “error”
			aritmetica = pilaSimbolos.get(pilaSimbolos.size()-3);
			sencillo = pilaSimbolos.peek();
			if((aritmetica.getTipo().equals("number")||aritmetica.getTipo().equals("ent")) && (sencillo.getTipo().equals("number")||sencillo.getTipo().equals("ent"))) tipo = "number";
			else {
				tipo = "error";
				if(!(aritmetica.getTipo().equals("number")||aritmetica.getTipo().equals("ent")))GestorErrores.addError("Regla 44:La parte izquierda de la resta no es de tipo entero");
				else GestorErrores.addError("Error semantico en la regla 45 la parte derecha de la resta no es de tipo entero");
			}
			break;
		case 46://ARITMETICA -> SENCILLO
			//{ ARITMETICA.tipo = SENCILLO.tipo }
			sencillo = pilaSimbolos.peek();
			tipo = sencillo.getTipo();
			break;		
		case 47://TIPO -> number
			//{TIPO.tipo = “number”}
			tipo = "number";
			break;
		case 48://TIPO -> string
			//{TIPO.tipo = “string”}
			tipo = "string";
			break;
		case 49://TIPO -> boolean
			//{TIPO.tipo = “boolean”}
			tipo = "boolean";
			break;
		case 50://TIPO -> lambda
			//{TIPO.tipo = “lambda”}
			tipo = "lambda";
			break;

		case 51://SENCILLO -> abrePar  EXPRESION  cierraPar
			//{ SENCILLO.tipo = EXPRESION.tipo }
			expresion = pilaSimbolos.get(pilaSimbolos.size()-2);
			tipo = expresion.getTipo();
			break;
		case 52://SENCILLO -> id  SENCILLOFUN;
			//{ SENCILLO.tipo = buscaTipoTS(id.ent) }
			id = pilaSimbolos.get(pilaSimbolos.size()-2);
			sencillofun = pilaSimbolos.peek();
			if(sencillofun.getTipo().contains("ok") && AuxTS.buscaTipoTS(id.getLexema()).contains("funcion")){
				String aux = sencillofun.getTipo().replace("/ok", "");

				if(AuxTS.checkParam(id.getLexema(), aux)||aux.equals("ok")){
					tipo = AuxTS.buscaTipoDevTS(id.getLexema());
				}else {
					tipo = "error";
					GestorErrores.addError("Error semantico en la regla 52 los parametros con los que se llama a la funcion no se corresponde con los esperados");
				}
			}else {
				tipo = AuxTS.buscaTipoTS(id.getLexema());
			}

			break;
		case 53://SENCILLO ->  ent
			//{SENCILLO.tipo = “ent”}
			tipo = "ent";
			break;
		case 54://SENCILLO -> cad  
			//{SENCILLO.tipo = “cadena”}
			tipo = "cadena";
			break;
		case 55://SENCILLO -> true
			//{SENCILLO.tipo = “true”}
			tipo = "true";
			break;
		case 56://SENCILLO -> false
			//{SENCILLO.tipo = “false”}
			tipo = "false";
			break;
		case 57: // SENCILLOFUN  -> lambda
			//{SENCILLOFUN.tipo = “ok”}
			tipo = "ok";
			break;
		case 58://SENCILLOFUN -> PARENTabr CALLFUN PARENTcerr
			//{if(CALLFUN.tipo.contains (“ok”)) then SENCILLOFUN.tipo = CALLFUN.tipo else SENCILLOFUN.tipo = “error”}
			callFun = pilaSimbolos.get(pilaSimbolos.size()-2);
			if(callFun.getTipo().contains("ok")){
				tipo = callFun.getTipo();
			}
			else {
				tipo = "error";
				GestorErrores.addError("Error semantico en la regla 58 la llamada a la funcion se ha realizado con parametros invalidos");
			}
			break;
		default:
			System.out.println("Error al ejecutar Accion Semantica, numero de regla incorrecto");
			break;

		}
		return tipo;
	}
}