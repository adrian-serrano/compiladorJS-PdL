package utils.tabla;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

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



public class TS {

	//private String nombreTabla;
	private int posEntrada = 1;
	private int despl;
	@SuppressWarnings("unused")
	private PrintWriter log; // Fichero de escritura
	private String nombreTabla;
	private ArrayList<String> tablaSim;
	private HashMap<String, Atributos> mapa;


	/**
	 * Constructor que inicializa la tabla de simbolos.
	 * @param nombreTS nombre de la tabla de simbolos
	 */
	public TS(String nombreTS){
		this.nombreTabla = nombreTS;
		this.despl = 0;
		this.mapa = new HashMap<String, Atributos>();
		this.tablaSim = new ArrayList<String>();

	}

	/**
	 * Metodo getter que devuelve el desplazamiento
	 * @return despl
	 */
	public int getDesp(){ return despl; }

	/**
	 * Metodo que devuelve la suma del Desplazamiento
	 * @param x Lo que hay que sumar al desplazamiento. 
	 */
	public void sumDesp(int x){ this.despl += x; }
	/**
	 * Metodo getter que devuelve el nombre de la tabla
	 * @return nombre de la tabla
	 */
	public String getNombreTabla() { return nombreTabla; }


	/*
	 * Metodo que inserta lexema en la TS retorna la posicion 
	 */
	public int insertarLex(String lex) {
		int pos = -1;
		if(!mapa.containsKey(lex)){
			Atributos entrada=new Atributos(posEntrada);
			entrada.setLexema(lex);
			mapa.put(lex, entrada);
			this.tablaSim.add(lex);
			pos = posEntrada;//posicion en la que entra en la tabla
			posEntrada++;//incrementamos para el siguiente elemento de la tabla
		}
		return pos; 
	}

	/**
	 * Metodo que busca el lexema mediante el ID en la TS
	 * @param pos la posicion
	 * @return el lexema (lex)
	 */
	public String buscarLexema(int pos){
		String lex = null;
		boolean encontrado=false;
		if(!mapa.isEmpty()){
			Iterator<Entry<String, Atributos>> it=mapa.entrySet().iterator();
			while(it.hasNext() && !encontrado) {
				Entry<String,  Atributos> entry=it.next();
				if(entry.getValue().getPos()==pos) {
					lex=entry.getKey();
					encontrado=true;
				}
			}

		}
		return lex;
	}

	/**
	 * Funcion que se encarga de buscar el id por medio de el lexema en la Table de Simbolos.
	 * @param lex El lexema.
	 * @return La posicion (pos)
	 */
	public int buscarPos(String lex){
		int pos = -1;
		if(!mapa.isEmpty()&& mapa.containsKey(lex)){
			pos = mapa.get(lex).getPos();
		}
		return pos;
	}

	/**
	 * Metodo que inserta el desplazamiento.
	 * @param lex Lexema al que se le inserta
	 * @param despl El desplazamiento
	 */
	public boolean insertaDesp(String lex, String despl) {
		boolean insertado=false;
		if(!mapa.isEmpty()&& mapa.containsKey(lex)){
			mapa.get(lex).setDespl(despl);
			insertado=true;
		}
		return insertado;


	}
	
	/**
	 * Metodo que inserta el tipo.
	 * @param lex El lexema al que se le inserta el tipo
	 * @param tipo El tipo
	 * @return true si se ha insertado
	 */
	public boolean insertaTipo(String lex, String tipo) {
		boolean res = false;
		if(!mapa.isEmpty() && mapa.containsKey(lex)){
			mapa.get(lex).setTipo(tipo);
			res = true;

		}
		return res;
	}

	/**
	 * Metodo que busca el tipo
	 * @param lex El lexema 
	 * @return El tipo
	 */
	public String buscaTipo(String lex){
		String tipo = "-";
		if(!mapa.isEmpty()&& mapa.containsKey(lex)){
			tipo=mapa.get(lex).getTipo();

		}

		return tipo;
	}



	/**
	 * Metodo que inserta el tipo de parametro en la TS en caso de ser funcion
	 * @param lex El lexema al cual se le inserta el tipo de parametro
	 * @param tipoParam El tipo de parametro
	 * @return true si se ha realiado
	 */
	public boolean insertarTipoParam(String lex, String tipoParam){
		boolean res = false;
		if(!mapa.isEmpty()&& mapa.containsKey(lex)){
			mapa.get(lex).setTipoParam(tipoParam);
			res = true;
		}
		return res;
	}

	/**
	 * Metodo que busca el tipo mediante el ID en la TS
	 * @param pos La posicion
	 * @return El tipo
	 */

	public String buscarTipoParam(String lex,int pos){
		String tipo = null;

		if(!mapa.isEmpty()&& mapa.containsKey(lex)){
			tipo=mapa.get(lex).getTipoParam(pos);
		}
		return tipo;
	}
	
	/**
	 * Metodo que busca el numero de parametros mediante el lexema en la TS en caso de ser funcion
	 * @param lex Lexema sobre el cual se realiza la bsuqueda
	 * @return numParam
	 */
	public int buscarNumParam(String lex){
		int numParam = 0;
		if(!mapa.isEmpty()){
			if(mapa.containsKey(lex)) {
				numParam=mapa.get(lex).getNumParam();
			}

		}

		return numParam;
	}
	

	/**
	 * Metodo que inserta el tipo de devolucion
	 * @param lex El lexema al que se le inserta
	 * @param tipoRet El tipo de devolucion
	 */
	public void insertaTipoRet(String lex, String tipoRet) {

		if(!mapa.isEmpty()&& mapa.containsKey(lex)){
			mapa.get(lex).setTipoRet(tipoRet);
		}

	}
	
	/**
	 * Metodo que busca el tipo de devolucion
	 * @param lex El lexema en el que se hace la busqueda
	 * @return tipoDev
	 */
	public String buscaTipoRet(String lex) {
		String tipoDev = "-";
		if(!mapa.isEmpty()){
			if(mapa.containsKey(lex)) {
				tipoDev = mapa.get(lex).getTipoRet();
			}

		}

		return tipoDev;
	}

	/**
	 * Meotod que inserta la etiqueta que corresponda
	 * @param lexema El lexema al cual se le va a insertar
	 */
	public void insertarEtiqueta(String lexema) {
		if(!mapa.isEmpty()&& mapa.containsKey(lexema)){
			mapa.get(lexema).setEtiqueta("Et"+lexema+"01");;
		}	
	}



	/**
	 * Metodo que imprime la mapa en un archivo de texto.
	 * @param append para saber si nos interesa que se guarde o se borre al volver a escribir en el fichero.
	 * @param number numero de la tabla
	 */
	public void printTablaSimbolos(boolean append,int number){
		PrintWriter writer;
		try {

			File fichero = new File("src\\recursos\\tabla_simbolos.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(fichero, append));
			writer = new PrintWriter(bw);
			String aux="TABLA ";
			if(!getNombreTabla().equals("PRINCIPAL"))
				aux=aux+"de la FUNCION ";
			writer.println(aux + getNombreTabla() + " #" + number + ":");
			writer.println(" ");

			for(int i=0; i<this.tablaSim.size(); i++) {
				String lex=this.tablaSim.get(i);
				Atributos atributos = mapa.get(lex);

				if(!(atributos.getTipo().equals("funcion"))) {

					writer.println("* LEXEMA : " +"'" +atributos.getLexema()+"'");
					writer.println("  ATRIBUTOS:");
					writer.println("  + tipo : "  + "'" + atributos.getTipo() +"'" + "(este es de tipo  *" + atributos.getTipo() + "*)");
					writer.println("  + despl : " + atributos.getDespl());
					writer.println(" ");
				}
				else {
					writer.println("* LEXEMA : " +"'"+ atributos.getLexema()+"'");
					writer.println("  ATRIBUTOS:");
					writer.println("  + tipo : " +  "'" + atributos.getTipo() +"'" +  "(este es de tipo  *" + atributos.getTipo() + "*)");
					writer.println("    + numParam : " + atributos.getNumParam());
					for(int j = 0; j < atributos.getNumParam(); j++) {
						if((j+1) <= 9){
							writer.println("      + TipoParam0" + (j+1) + " : '" + atributos.getTipoParam(j)+"'");
							writer.println("      + ModoParam0" + (j+1) + " : " + atributos.getModoParam());
						}
						else {
							writer.println("      + TipoParam" + (j+1) + " : " + atributos.getTipoParam(j));
							writer.println("      + ModoParam" + (j+1) + " : " + atributos.getModoParam());
						}
					}
					writer.println("    + TipoRetorno : "+"'"  + atributos.getTipoRet()+"'" );
					writer.println("    + EtiqFuncion : '" + atributos.getEtiqueta()+"'");
					writer.println(" ");
				}

			}

			writer.println("  ---------------------------------------------");
			writer.println(" ");
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}



