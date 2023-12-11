package lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import utils.GestorErrores;
import utils.Patrones;
import utils.tabla.AuxTS;
import utils.tokens.*;

/**
 * Autores: Guillermo Buendia del Campo, Adrian Serrano Lopez-Alvarez, Andres Ramos Colombo, 
 * 
 * Fecha de inicio: 19/10/2020
 * 
 * Fecha de ultima modificacion: 11/01/2021
 * 
 * Proyecto: Practica PDL
 *
 */

public class AnalizadorLexico {

	private ArrayList<Tokens> leidos; // Array List para almacenar los tokens leidos

	private FileReader file; // Fichero que vamos a leer
	private BufferedReader buffer; // Buffer de lectura
	//private PrintWriter log; // Fichero de escritura

	private String caracter; // Caracter leido en cada iteracion

	private boolean eol; // Variable que indica si hemos llegado al final de la linea
	private boolean eof; // Variable que indica si hemos llegado al final del archivo
	public static int linea; // Variable que guarda la linea actual para imprimir errores
	public int columna; // Variable que guarda la columna actual para imprimir errores
	private static final String PARENTABIERTO = "("; 
	private static final String PARENTCERRADO = ")";
	private static final String AND = "&"; 
	private static final String CORABIERTO = "{";
	private static final String CORCERRADO = "}";
	private static final String BARRAIZQUIERDA = "\"";
	private static final String BARRADERECHA = "/";
	private static final String MENOS = "-";
	private static final String PUNTOCOMA = ";";
	private static final String NEWLINE = "newLine";
	private static final String SALTOLINEA = "saltoLinea";
	private static final String IGUAL = "=";
	private static final String COMA = ",";
	private static final String MAS = "+";
	private static final String MAYORQUE = ">";
	private static final String MENORQUE = "<";
	private static final String DOLAR = "$";
	private static final String GUIONBAJO = "_";
	
	
	/**
	 * Este constructor se encarga de inicializar el analizador lexico el cual lee un fichero de prueba
	 * @param fichero fichero de prueba
	 */
	public AnalizadorLexico(String fichero) {
		this.leidos = new ArrayList<Tokens>(); // Inicializamos leidos
		this.caracter = ""; // Inicializamos el caracter
		this.eol = false; // Indicamos que no hemos llegado a fin de linea

		AnalizadorLexico.linea = 1; // Empezamos en la linea 1
		try {
			String filePath = new File("").getAbsolutePath(); // Buscamos el path actual
			filePath = filePath.concat("\\" + fichero); // Le agregamos el nombre del fichero
			this.file = new FileReader(filePath); // Leemos el fichero
			this.buffer = new BufferedReader(file); // Inicalizamos el buffer de lectura
			this.leer(); // LLamamos a la funcion para leer el primer caracter
		} catch (FileNotFoundException e) {
			System.out.println("Error de lectura"); // Si no existe el fichero se avisa al usuario
			e.printStackTrace();
		}
	
	}

	/**
	 * Funcion que a partir de un caracter genera el token correspondientes.
	 * @return El token que le corresponde
	 */
	public Tokens generarTokens() {
		Patrones checker = new Patrones(); // Hacemos uso de esta clase auxiliar que revisa patrones de texto y los
		// clasifica
		Tokens token = null; // Inicializamos el token resultado
		int estadoAF = 0; // Empezamos en el estado 0
		String lex = ""; // Inicializamos el lexema vacio
		boolean acabado = false; // Variable boleana para salir del while

		while (!acabado && !eof) { // El while se ejecutara mientras no se haya leido el lexema o llegado al final del archivo
			if (estadoAF != 0 && estadoAF != 19 && estadoAF != 21  && estadoAF != 13) { // Si estamos en ciertos estados no se llama a leer
				this.leer();
			}

			switch (estadoAF) {
			case 0:
				if (checker.isDel(caracter)) { // Nos quedamos en el estado 0 si es un delimitador el caracter leido
					this.leer(); // Leemos el siguiente caracter
				} else if (caracter.equals(MENOS)) { // Transitamos al estado 11 al encontrar un ya que puede ser un
					lex += caracter;								// OParit o un OPdecr
					estadoAF = 11;
				} else if (caracter.equals(AND)) { // Transitamos al estado 14 para comprobar que sea un OPlogico
					estadoAF = 14;
				} else if (checker.isLetter(caracter)) { // En caso de leer una letra transitamos al estado 18
					estadoAF = 18;
					lex += caracter; // Agregamos al lexema el caracter leido
				} else if (checker.isDigit(caracter)) { // En caso de leer un digito transitamos al estado 20
					estadoAF = 20;
					lex += caracter;
				} else if (caracter.equals(BARRAIZQUIERDA)) { // Transitamos al estado 16 al encotrar el comienzo de una cadena
					estadoAF = 16;
				} else if (caracter.equals(BARRADERECHA)) { // Transitamos al estado 22 para comprobar si se trata de un
					// comentario
					estadoAF = 22;
				}
				
				// Estados finales
				/*
				 * En todos los casos se comprueba que sea un simbolo terminal entonces se emite
				 * un token, la mayoria de timo simbolo y se procede a leer el siguiente
				 * caracter. Cada uno tiene un estado asociado en el automata.
				 */

				else if (caracter.equals(PARENTABIERTO)) { // Estado 1
					token = new Simbolo(PARENTABIERTO);
					lex = PARENTABIERTO;
					acabado = true;
					leer();
				} else if (caracter.equals(PARENTCERRADO)) { // Estado 2
					token = new Simbolo(PARENTCERRADO);
					lex = PARENTCERRADO;
					acabado = true;
					leer();
				} else if (caracter.equals(CORABIERTO)) { // Estado 3
					token = new Simbolo(CORABIERTO);
					lex = CORABIERTO;
					acabado = true;
					leer();
				} else if (caracter.equals(CORCERRADO)) { // Estado 4
					token = new Simbolo(CORCERRADO);
					lex = CORCERRADO;
					acabado = true;
					leer();
				} else if (caracter.equals(PUNTOCOMA)) { // Estado 5
					token = new Simbolo(PUNTOCOMA);
					lex = PUNTOCOMA;
					acabado = true;
					leer();
				}  else if (caracter.equals(IGUAL)) { // Estado 6
					token = new OPasig();
					lex = IGUAL;
					acabado = true;
					leer();
				} else if (caracter.equals(NEWLINE)) { //nueva linea
					leer();
				} else if (caracter.equals(SALTOLINEA)) { //salto de linea
					leer();
				} else if (caracter.equals(COMA)) { // Estado 7
					token = new Simbolo(COMA);
					lex = COMA;
					acabado = true;
					leer();
				} else if (caracter.equals(MENORQUE)) { // Estado 8
					token = new OPrelacion(MENORQUE);
					lex = MENORQUE;
					acabado = true;
					leer();
				} else if (caracter.equals(MAYORQUE)) { // Estado 9
					token = new OPrelacion(MAYORQUE);
					lex = MAYORQUE;
					acabado = true;
					leer();
				} else if (caracter.equals(MAS)) { // Estado 10
					token = new OParit(MAS);
					lex = MAS;
					acabado = true;
					leer();	
				} else if(caracter.equals(DOLAR)){ // Estado 11
					token = new Simbolo(DOLAR);
					lex = DOLAR;
					acabado = true;
					eof = true;
				}
				else { // Si hemos llegado aqui es porque no hemos podido reconocer el simbolo
					System.out.println("Simbolo no reconocido " + "en la linea " + linea + ", columna " + columna); // Imprimos un error
					GestorErrores.addError("Simbolo no reconocido " + "en la linea " + linea + ", columna " + columna); 

					leer(); // Leemos el siguiente caracter y volvemos a ejecutar
					acabado = true;
					return null;
				}
				break;
			case 11:
				if (caracter.equals(MENOS)) { // En el estado 11 podemos transitar a 2 casos
					lex += caracter;
					estadoAF = 12; // Si el - va continuado de otro - es un decremento estado 12
				} else {
					estadoAF = 13; // En otro caso se trata de un operador aritmetico estado 13
				}
				break;
			case 12:
				token = new OPdecr(); // Generamos en token
				acabado = true;
				break;
			case 13:
				token = new OParit(lex); // Generamos el token de aritmetico
				acabado = true;
				break;
			case 14:
				if (caracter.equals(AND)) { // En el estado 14 podemos transitar al 15 o tener un error

					estadoAF = 15;
				} else { // Si no hay otro & se genera un error
					GestorErrores.addError("Simbolo no reconocido " + "en la linea " + linea + ", columna " + columna); 
					System.out.println("Simbolo no reconocido " + "en la linea " + linea + ", columna " + columna); // Imprimos un error
					leer(); // Leemos el siguiente caracter y volvemos a ejecutar
					token = null;
					acabado = true;
				}
				break;
			case 15:
				token = new OPlogico(); // Generamos el token OPlogico &&
				acabado = true;
				break;
			case 16:
				if (!caracter.equals(BARRAIZQUIERDA)) { // En este estado generamos la cadena, nos quedamos en el estado 16 hasta
					// que encontramos otras comillas
					lex += caracter;
					estadoAF = 16;
				} else {
					estadoAF = 17; // Cuando encontramos " pasamos al estado 17
				}
				break;
			case 17:
                if(lex.length() > 64) {//si la cadena es mayor que 64 error
                    GestorErrores.addError("Cadena mayor que 64 caracteres " + "en la linea " + linea + ", columna " + columna); 
                }else {
                token = new Cad(lex); // Generamos la nueva cadena
                }
                acabado = true;
                break;

			case 18:
				if (caracter.equals(GUIONBAJO) || checker.isDigit(caracter) || checker.isLetter(caracter)) {
					// En este estado comprobamos que sea letra digito o guionbajo para volver al mismo estado 18 
					//eoc transitamos al estado 19 
					lex += caracter;
					estadoAF = 18;
				} else {
					estadoAF = 19;
				}
				break;

			case 19:
				/* Si es palabra reservada se genera el token PReservada, lexema */
				if(AuxTS.esReservada(lex)){
					token = new PAreserv(lex);
				}
				/* Si es un id se busca en la TS y si no esta se añade
				 * y se genera el token Id, posTS
				 */
				else{
					int pos = AuxTS.buscarPos(lex);
					if(AuxTS.getFlagDU()){ // DECLARACION
						if(pos==-1){ // lexema no declarado
							if(!AuxTS.getFlagVF()){ // function
								pos = AuxTS.insertarLexema(lex);
								AuxTS.insertaTipoTS(lex,"funcion",false);
								AuxTS.setFuncionM(lex);
								token = new ID(AuxTS.nombreTablaActual(),pos);
								AuxTS.crearTabla(lex); // crear TS para la funcion
								AuxTS.flagUso();
							}
							else{ // var o variable sin declarar
								pos = AuxTS.insertarLexema(lex);
								token = new ID(AuxTS.nombreTablaActual(),pos);
								AuxTS.flagUso();
							}
						}
						else{ // lexema existe
							 GestorErrores.addError("Nombre de la variable ya existente " + "en la linea " + linea + ", columna " + columna); 
						}
					}
					else{//USO
						if(pos == -1){ // variable sin declaracion por tanto es de tipo entero y global
							pos = AuxTS.insertarVarGlobal(lex);
							int desp = AuxTS.getDesp(true);
							AuxTS.insertaDespTS(lex, Integer.toString(desp));
							AuxTS.insertaTipoTS(lex, "number",true);
							AuxTS.sumDesp(1,true);	
						}
						token = new ID(AuxTS.nombreTablaPertenece(lex),pos);
					}
				}
				acabado = true;
				break;
			case 20:
				if(checker.isDigit(caracter)) { // Leemos todos los digitos para generar el token variable entera
					lex += caracter;
					estadoAF = 20;
				} else {
					estadoAF = 21;
				}
				break;
			case 21:
				int resultado = Integer.parseInt(lex); // Convertimos a int el lexema
				if(resultado<32768) {
					token = new ENT(resultado); // Generamos el token
				}
				else {
					GestorErrores.addError("Numero mayor que 32767 " + "en la linea " + linea + ", columna " + columna); 
				}
				acabado = true;
				break;
			case 22:
				if (caracter.equals(BARRADERECHA)) { // Si encontramos una / saltamos al estado 23, en otro caso damos error ya
					// que no se ha declarado el OParitmetico /
					estadoAF = 23;
				} else {
					System.out.println("Simbolo no reconocido " + "en la linea " + linea + ", columna " + columna); // Imprimos un error
					GestorErrores.addError("Simbolo no reconocido " + "en la linea " + linea + ", columna " + columna); 

					leer(); // Leemos el siguiente caracter y volvemos a ejecutar
					token = null;
					acabado = true;
				}
				break;
			case 23:
				if (caracter.equals(NEWLINE)) { // Vamos omitiendo el comentario hasta que llegamos a una nueva linea
					leer();
					estadoAF = 0;
				} else {
					estadoAF = 23;
				}
				break;

			default:
				// Guardamos el error el gestor de errores
				GestorErrores.addError("Simbolo no reconocido " + "en la linea " + linea + ", columna " + columna); 
				System.out.println("Simbolo no reconocido " + "en la linea " + linea + ", columna " + columna); // Imprimos un error
				leer(); // Leemos el siguiente caracter y volvemos a ejecutar
				acabado = true;
				token = null;

			}
			if(token != null)

				if (token != null && !token.convertToString().equals("<$, >")) {
					leidos.add(token); // Añdimos el token generado a la lista
				}


		}

		return token; // Devolvemos el token

	}

	/**
	 * Metodo que lee un caracter del fichero que contiene el codigo y ademas actualiza el valor de linea y columna
	 */
	private void leer() {
		int caracterLeido = 0;
		char aux;
		String caracter = null;

		try {
			caracterLeido = this.buffer.read(); // Leemos el caracter
			if (caracterLeido == 13) { // Si es un retorno de carro
				this.eol = true; // indicamos que hemos llegado al final de la linea
				caracter = SALTOLINEA; // Saltamos de linea
			} else if (caracterLeido == 10 && eol) { // Si teniamos en el caracter anterio un 13 y ahora un 10 (new
				// line)
				caracter = NEWLINE; // Indicamos que estamos en linea nueva
				eol = false; // Dejamos de nuevo la variable a false
			} else if (caracterLeido != -1) { // En otro caso
				aux = (char) caracterLeido; // Transformas el caracter leido a String
				caracter = Character.toString(aux);
			} else {
				caracter = DOLAR;
			}

		} catch (IOException e) {
			System.out.println("Error al leer el fichero"); // Indicamos el error
			e.printStackTrace();
		}
		if (!eof) { // Si no hemos llegado al final del archivo
			this.columna++; // Actualizamos las variables
			if (caracter.equals(NEWLINE)) {
				linea++;
				columna = 0;
			}
			this.caracter = caracter;
		}
	}

	/**
	 * Metodo getter que devuelve la linea
	 * @return linea
	 */
	public int getLinea() { return linea; }

	/**
	 * Metodo getter que devuelve la columna
	 * @return columna
	 */
	public int getColumna() { return columna; }

	/**
	 * Metodo getter que devuelve el fichero
	 * @return file
	 */
	public FileReader getFileReader() { return file; }

	/**
	 * Metodo getter que devuelve los tokens leidos
	 * @return leidos
	 */
	public ArrayList<Tokens> getTokensLeidos() { return leidos; }



}