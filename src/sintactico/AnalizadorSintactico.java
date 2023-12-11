package sintactico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import semantico.Atributo;
import utils.GestorErrores;
import utils.tabla.AuxTS;
import utils.tokens.Tokens;
import utils.tokens.ID;
import lexico.AnalizadorLexico;

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


public class AnalizadorSintactico {

	private Stack<String> pilaEstados;
	private Stack<Atributo> pilaSimbolos;
	private Tokens tokenEntrada;
	private Tokens tokenAnterior;
	private AnalizadorLexico analizadorLexico;
	private ArrayList<String> tokens;
	private ArrayList<String> noTerminales;
	private boolean tipo1 = false;
	private Map<String, Acciones> mapaAcciones;
	private Map<String, Acciones> mapaDistribucionGOTO;	
	private Map<Integer, Regla> mapaReglas;
	private List<Integer> listaReglasUtilizadas;
	private Workbook workbook;



	/**
	 * Constructor del Analizador Sintactico
	 * Inicaliza los atributos y recive en los parametros el fichero de las tanlas y el fichero de las reglas
	 * @param analizadorLexico
	 * @param fileTablas
	 * @param fileReglas
	 */
	public AnalizadorSintactico(AnalizadorLexico analizadorLexico, String fileTablas, String fileReglas){//Constructor
		this.analizadorLexico = analizadorLexico; //cambio nombre de anLex a analizadorLexico
		this.tokenEntrada = analizadorLexico.generarTokens();
		this.mapaAcciones = new HashMap<String, Acciones>();
		this.mapaDistribucionGOTO = new HashMap<String, Acciones>();
		this.mapaReglas = new HashMap<Integer, Regla>();
		this.tokens=new ArrayList<String>();
		this.noTerminales=new ArrayList<String>();
		this.listaReglasUtilizadas = new ArrayList<Integer>();

		//Inicializar pila de estados
		pilaEstados = new Stack<String>();
		pilaEstados.push("0");

		//Inicializar pila de simbolos
		pilaSimbolos = new Stack<Atributo>();

		lecturaTablasExcel(fileTablas);
		lecturaReglas(fileReglas);

	}//Constructor

	/**
	 * Funcion que lee la tabla excel de acciones y goTo y llama a las funciones acciones y distribuidorGOTO para almacenar la informacion
	 * en sus respectivos mapas
	 * @param ficheroTablas fichero que se va a leer.
	 */
	private void lecturaTablasExcel(String fileTablas) {

		//Leer las tablas accion y goTo
		try {

			FileInputStream excelFile = new FileInputStream(new File(fileTablas));
			workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);//hoja 1 tabla accion
			Sheet datatypeSheet2 = workbook.getSheetAt(1);//hoja 2 tabla goto
			Iterator<Row> iteradorAccion = datatypeSheet.iterator();//iterador filas tabla accion
			Iterator<Row> iteradorGOTO = datatypeSheet2.iterator();//iterador filas tabla GOTO
			int estado=-1;

			while (iteradorAccion.hasNext()) {
				String accion = ""; //Lo inicio porque me salta error al llamar acciones en la linea 155
				String token;
				String noTerminal;
				boolean salir=false;
				int i=0;//variable auxiliar para saber en que columna estamos y obtener el token correspondiente
				int j=0;//variable auxiliar para saber en que columna estamos y obtener el simbolo no terminal correspondiente
				Row currentRowAccion = iteradorAccion.next();//fila tabla accion
				Row currentRowGOTO = iteradorGOTO.next();//fila tabla GOTO
				Iterator<Cell> cellIteratorAccion = currentRowAccion.iterator();//iterador columnas tabla accion
				Iterator<Cell> cellIteratorGOTO = currentRowGOTO.iterator();//iterador columna tabla GOTO
				while (cellIteratorAccion.hasNext() && !salir) {
					Cell currentCell = cellIteratorAccion.next();
					//Si es la primera fila guarda los tokens en una arraylist
					if(currentRowAccion.getRowNum()==0) {
						tokens.add(currentCell.getStringCellValue());
					}
					else if(i==0&&currentRowAccion.getRowNum()==1) {
						estado=0;
					}
					//si estamos en la primera columna obtenemos el estado
					else if(i==0) {
						estado=(int)currentCell.getNumericCellValue();
						if(estado==0) {
							salir=true;
						}
					}
					//Si hay un string en la celda
					else if (currentCell.getCellType() == CellType.STRING) {
						accion=currentCell.getStringCellValue();
					}
					//Si hay un numero en la celda
					else if (currentCell.getCellType() == CellType.NUMERIC) {
						accion=Integer.toString((int)currentCell.getNumericCellValue());
					}
					//Si la celda está vacía
					else {
						accion="";
					}
					token=tokens.get(i);
					if(i>0) 
						acciones(token, String.valueOf(estado), accion); //Almacena tabla Acciones.
					i++;
				}//while 1 tabla


				while (cellIteratorGOTO.hasNext()&&!salir) {
					Cell currentCell = cellIteratorGOTO.next();
					//Si es la primera fila guarda los tokens en una arraylist
					if(currentRowGOTO.getRowNum()==0) {
						noTerminales.add(currentCell.getStringCellValue());
					}
					//si estamos en la primera columna obtenemos el estado
					else if(i==0 && currentCell.getCellType() == CellType.NUMERIC) {
						estado=(int)currentCell.getNumericCellValue();
					}
					//Si hay un string en la celda
					else if (currentCell.getCellType() == CellType.STRING) {
						accion=currentCell.getStringCellValue();
					} 
					//Si hay un numero en la celda
					else if (currentCell.getCellType() == CellType.NUMERIC) {
						accion=Integer.toString((int)currentCell.getNumericCellValue());
					}
					//Si la celda esta vacia
					else {
						accion="";
					}
					noTerminal=noTerminales.get(j);
					j++; 
					distribuidorGOTO(noTerminal, String.valueOf(estado), accion); //Almacena tabla GoTo 
				}//while 2 tabla
				i=0;
				j=0;

			}//while
		} catch (FileNotFoundException e) {
			System.out.println("Error al leer el fichero: "+ fileTablas);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error al leer el fichero: "+ fileTablas);
			e.printStackTrace();
		}
	}


	/**
	 * Este metodo lee el fichero reglas y lo almacena en un mapa de reglas.
	 * @param fileReglas fichero que se lee
	 */
	private void lecturaReglas(String fileReglas) {
		Scanner scan;
		try {
			scan = new Scanner(new File(fileReglas));
			String linea = "";
			int numeroRegla = 2;
			while(scan.hasNext()) {
				linea = scan.nextLine();
				String[] lineArray = linea.split(",", 2);
				Regla regla = new Regla(numeroRegla, lineArray[0].trim(), (Integer.parseInt(lineArray[1].trim())));
				mapaReglas.put(numeroRegla, regla);
				numeroRegla++;
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que establece el valor de las acciones.
	 * @param token
	 * @param estado
	 * @param accion
	 */
	private void acciones(String token, String estado, String accion) {
		if(mapaAcciones.get(token) == null){
			Acciones acciones = new Acciones(); 
			mapaAcciones.put(token, acciones);
		}
		mapaAcciones.get(token).setAccion(estado, accion);
	}

	/**
	 * Metodo que establece el valor de distribucion GOTO
	 * @param noTerminal
	 * @param estado
	 * @param accion
	 */
	private void distribuidorGOTO(String noTerminal, String estado, String accion) {
		if(mapaDistribucionGOTO.get(noTerminal) == null){
			Acciones acciones = new Acciones(); 
			mapaDistribucionGOTO.put(noTerminal, acciones);
		}
		mapaDistribucionGOTO.get(noTerminal).setAccion(estado, accion);

	}


	/**
	 * Funcion que selecciona la accion correspondiente dependiendo del token o noTerminal que se le pase y del estado. 
	 * @param token token o noTerminal
	 * @param mapa mapa de acciones o de goTo
	 * @param estado estado que se le pase
	 * @return la accion que corresponde
	 */
	private String seleccionAccion(String token, Map<String, Acciones> mapa, String estado) {//buscarTabla
		return (mapa.get(token) == null || mapa.get(token).getAccion(estado) == null) ? "" : mapa.get(token).getAccion(estado);
	}


	/**
	 * Metodo que se encarga de seleccionar el caso a tratar y devolver el valor correspondiente
	 * @return 1 si la terminacion es correcta
	 */
	public int tratamiento() {
		String casoTratar;
		String estado = pilaEstados.peek();
		String accion = seleccionAccion(tokenEntrada.tipoToken(), mapaAcciones, estado);
		if(accion.equals("")) {
			casoTratar = "error";
		}else {
		casoTratar = accion.substring(0,1);
		}
		switch(casoTratar) {
		case "d":
			return dCase(accion);
		case "r":
			return rCase(accion, estado);
		case "a":
			return aCase(accion);
		default:
			GestorErrores.addError("Error Sintactico en la linea: "+ this.analizadorLexico.getLinea() + " columna:"+ this.analizadorLexico.getColumna());
			return -1;
		}
		
	}
	/**
	 * Metodo que trabaja el caso dCase de tratamiento. 
	 * @param accion
	 * @return el valor entero (1 correcto) y (-1 incorrecto) 
	 */
	public int dCase(String accion) {
		String tipo = "-";
		if(tokenEntrada.tipoToken().equals("id")) {
			String lexema = AuxTS.getLexema(((ID)tokenEntrada).getPos(),((ID)tokenEntrada).getTabla());
			tipo=AuxTS.buscaTipoTS(lexema);
			if(tipo.equals("-")) {
				AuxTS.insertaTipoTS(lexema, "number", false);
				tipo = "number";
			}
			pilaSimbolos.push(new Atributo(tokenEntrada.tipoToken(), tipo, lexema));
		}
		else {
			pilaSimbolos.push(new Atributo(tokenEntrada.tipoToken(), tipo));
			
		}
		String aux = accion.substring(1,accion.length()).trim();
		aux = aux.replace("(", ""); 
		aux = aux.replace(")", ""); 
		pilaEstados.push(aux);
		
		if(tokenEntrada.tipoToken().equals("function")){ //Si vemos que se va a declarar una funcion activamos los flags
			AuxTS.flagDeclaracion();
			AuxTS.flagFunction();
		}
		if(tokenEntrada.tipoToken().equals("let")){ //Si vemos que se va a declarar una variable activamos los flags
			AuxTS.flagDeclaracion();
			AuxTS.flagVar();
		}
		if(tipo1) {
			tipo1 = false;
			String lexema = AuxTS.getLexema(((ID)tokenEntrada).getPos(),((ID)tokenEntrada).getTabla());	
			AuxTS.insertaTipoDevTS(lexema, tokenAnterior.tipoToken());
		}
		if((tokenEntrada.tipoToken().equals("boolean")||tokenEntrada.tipoToken().equals("number")||tokenEntrada.tipoToken().equals("string"))
				&& (tokenAnterior.tipoToken().equals("function"))){
				tipo1 = true;
			}
		if(((tokenEntrada.tipoToken().equals("boolean")||tokenEntrada.tipoToken().equals("number")||tokenEntrada.tipoToken().equals("string"))
			&& !(tokenAnterior.tipoToken().equals("let") || tokenAnterior.tipoToken().equals("function")))){
			AuxTS.flagDeclaracion();
			AuxTS.flagVar();
		}
		tokenAnterior = tokenEntrada;
		tokenEntrada = analizadorLexico.generarTokens();
		if(tokenEntrada == null) return -1;
		return 1;

	}
	
	/**
	 * Metodo que trabaja el caso rCase de tratamiento
	 * @param accion la accion que se va a ejecutar
	 * @param estado el estado en el que se esta trabajando
	 * @return el valor entero (1 correcto) y (-1 incorrecto)
	 */
	public int rCase(String accion, String estado) {
		String aux = accion.substring(1,accion.length()).trim();
		aux = aux.replace("(", ""); 
		aux = aux.replace(")", ""); 
		int numRegla = Integer.valueOf(aux);
		listaReglasUtilizadas.add(numRegla);
		Regla regla = mapaReglas.get(numRegla);
		if(pilaEstados.size() < regla.nElementosDer ||
				pilaSimbolos.size() < regla.nElementosDer){
			return -1;
		}
		else{
			String tipo = Regla.ejecutarAccion(numRegla, pilaSimbolos);
			for(int i=0; i<regla.nElementosDer; i++){
				pilaEstados.pop();
				pilaSimbolos.pop();
			}
			estado = seleccionAccion(regla.parteIzq, mapaDistribucionGOTO, pilaEstados.peek());
			pilaEstados.push(estado);
			pilaSimbolos.push(new Atributo(regla.parteIzq,tipo));
			return 1;
		}
	}
	/**
	 * Metodo que devuleve el caso aCase de tratamiento
	 * @param accion
	 * @return
	 */
	public int aCase(String accion) {
		return 0;
	}
	
	/**
	 * Metodo getter que devuelve la lista de reglas utilizadas. 
	 * @return la lista de reglas utilizadas
	 */
	public List<Integer> getListaReglasUtilizadas() { return listaReglasUtilizadas; }
}
