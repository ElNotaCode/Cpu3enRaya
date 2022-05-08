package cpu;

import java.util.Random;
import java.util.Scanner;

public class Cpu {
	
	// Iniciar tablero
	static char[][] tablero = { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };
	//bolean jugador
	static boolean jugador;
	//int numero turno
	static int numeroTurno = 0;
	//variable random y scanner inicializada para usarse en los metodos
	static Random r = new Random();
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		jugador = r.nextBoolean();
		do {
			turno();
		}while(!comprobarVictoria());
		
		sc.close();
	}
	
	//imprimir tablero
	public static void imprimirTablero() {
		
		if(jugador) {
			System.out.println("TRUNO DEL JUGADOR");
		}else {
			System.out.println("TURNO DE LA CPU");
		}
		
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				
				if(tablero[i][j] == ' ') {
					System.out.print("-");
				}else {
					System.out.print(tablero[i][j]);
				}
			}
			System.out.println();
		}
		
		/*	   j0  j1  j2
		 * i0 [ ] [ ] [ ]
		 * i1 [ ] [ ] [ ]
		 * i2 [ ] [ ] [ ]
		 */
	}
	
	//tirada jugador, de el jugador no se comprueba si se pasa de rango ya que en el juego principal es por inteficie
	public static void tiradaJugador() {
		int i;
		int j;
		do {
			System.out.println("Introduce I:");
			i = sc.nextInt();
			System.out.println("Introduce J:");
			j = sc.nextInt();
		}while(!comprobarFicha(i,j));
		tablero[i][j] = 'X';
	}
	//turno
	public static void turno() {
		
		//se suma el turno
		numeroTurno++;
		//imprimimos tablero
		imprimirTablero();
		//fase 1
		//if jugador else cpu
		if(jugador) {

			//tiramos
			tiradaJugador();
			//pasamos turno
			jugador = false;
		}else {
			//tira la cpu con su estrategia
			comportamientoCpu();
			//se pasa el turno
			jugador = true;
		}
		
	}
	
	//metodo comprobar
	public static boolean comprobarFicha(int i, int j) {
		
		//controlar el out of bounds
		if((i<0 || i>2) || (j<0 || j>2)) {
			return false;
			//si hay espacio true
		}else if(tablero[i][j] == ' ') {
			return true;
		}else {
			if(jugador) {
				System.out.println("Casilla ocupada:");
			}
			return false;
		}
	}
	
	public static void comportamientoCpu() {
		//comportamiento de la cpu segun el turno
		//turno 1 o 2 coloca random
		if(numeroTurno == 1 || numeroTurno == 2) {
			primeraTiradaCpu();
		}else if(numeroTurno == 3) { //turno 3 intenta jugada
			segundaTiradaCpu();
		}else{
			demasTiradasCpu(); //turno 4 o mas intenta bloquear
		}
	}
	
	//primera tirada (turno 1 o 2)
	public static void primeraTiradaCpu() {
		int i;
		int j;
		do {
			i = r.nextInt(3);
			j = r.nextInt(3);
		}while(!comprobarFicha(i,j));
		tablero[i][j] = 'O';
	}

	//tirada turno 3 intenta seguir jugada o tirada turno 4 intenta bloquear
	public static void segundaTiradaCpu() {
		//si es el turno 3 recorre el array y intenta poner una ficha al lado
			for (int i = 0; i < tablero.length; i++) {
				for (int j = 0; j < tablero[i].length; j++) {
					//detectamos la ficha
					if(tablero[i][j] == 'O') {
						colocarAlrededor(i,j);
						
					}
				}
			}
	}
	
	public static void demasTiradasCpu() {
		//si es el turno 4 o mas va a bloquear
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				//detectamos las fichas a bloquear
				if(tablero[i][j] == 'X') {
				//llamamos a un metodo que calcule donde falta para que se cumpla la condición de victoria y bloquee
				bloquearJugada(i, j);
				break;
				}
			}
		}
	}
	
	//generador de estrategias
	
	//metodo para bloquear jugada
	public static void bloquearJugada(int i, int j) {
		//miramos al rededor de la posición y buscamos la otra para bloquear el 3 en raya
		/*	    j0    j1   j2
		 * i0 [0 0] [0 1] [0 2]
		 * i1 [1 0] [1 1] [1 2]
		 * i2 [2 0] [1 2] [2 2]
		 */
		int ii = i;
		int jj = j;
		
		//top and block down
		--ii;
		if(comprobarFicha(ii, jj)) {
			if(tablero[ii][jj] == 'X') {
				//si coincide miramos de colocarla en la parte contraria
				if(comprobarFicha(ii+2, jj)){
					tablero[ii+2][jj] = 'O';
					return;
				}
			}
		}
		
		//top left block bot right
		--jj;
		if(comprobarFicha(ii, jj)) {
			if(tablero[ii][jj] == 'X') {
				//si coincide miramos de colocarla en la parte contraria
				if(comprobarFicha(ii-2, jj-2)){
					tablero[ii+2][jj+2] = 'O';
					return;
				}
			}
		}
		
		//top right to block bot left
		jj =+2;
		if(comprobarFicha(ii, jj)) {
			if(tablero[ii][jj] == 'X') {
				//si coincide miramos de colocarla en la parte contraria
				if(comprobarFicha(ii+2, jj-2)){
					tablero[ii+2][jj+2] = 'O';
					return;
				}
			}
		}
		
		//left to right
		ii= i;
		jj = j-1;
		if(comprobarFicha(ii, jj)) {
			if(tablero[ii][jj] == 'X') {
				//si coincide miramos de colocarla en la parte contraria
				if(comprobarFicha(ii, jj+2)){
					tablero[ii][jj+2] = 'O';
					return;
				}
			}
		}
		//right to left
		jj = j+1;
		if(comprobarFicha(ii, jj)) {
			if(comprobarFicha(ii, jj)) {
				if(tablero[ii][jj] == 'X') {
					//si coincide miramos de colocarla en la parte contraria
					if(comprobarFicha(ii, jj-2)){
						tablero[ii][jj-2] = 'O';
						return;
					}
				}
			}
		}
		//down to top
		--ii;
		jj = j;
		if(comprobarFicha(ii, jj)) {
			if(comprobarFicha(ii, jj)) {
				if(tablero[ii][jj] == 'X') {
					//si coincide miramos de colocarla en la parte contraria
					if(comprobarFicha(ii+2, jj)){
						tablero[ii+2][jj] = 'O';
						return;
					}
				}
			}
		}
		
		//down left to top right
		jj--;
		if(comprobarFicha(ii, jj)) {
			if(comprobarFicha(ii, jj)) {
				if(tablero[ii][jj] == 'X') {
					//si coincide miramos de colocarla en la parte contraria
					if(comprobarFicha(ii+2, jj+2)){
						tablero[ii+2][jj] = 'O';
						return;
					}
				}
			}
		}
		
		//down right
		jj += 2;
		if(comprobarFicha(ii, jj)) {
			if(comprobarFicha(ii, jj)) {
				if(tablero[ii][jj] == 'X') {
					//si coincide miramos de colocarla en la parte contraria
					if(comprobarFicha(ii+2, jj-2)){
						tablero[ii+2][jj] = 'O';
						return;
					}
				}
			}
		}
		
		//si no va a intentar colocar una para si misma
		colocarAlrededor(ii, jj);
		
		//TODO: mirar forma de randomizar el patrón
		
	}
	
	//metodo que mira alrededor de la ficha y intenta colocar otra
	public static void colocarAlrededor(int i, int j) {
		//miramos al rededor de la posición
		/*	    j0    j1   j2
		 * i0 [0 0] [0 1] [0 2]
		 * i1 [1 0] [1 1] [1 2]
		 * i2 [2 0] [1 2] [2 2]
		 */
		int ii = i;
		int jj = j;
		
		//top
		--ii;
		if(comprobarFicha(ii, jj)) {
			tablero[ii][jj] = 'O';
			return;
		}
		
		//top right
		--jj;
		if(comprobarFicha(ii, jj)) {
			tablero[ii][jj] = 'O';
			return;
		}
		
		//top left
		jj =+2;
		if(comprobarFicha(ii, jj)) {
			tablero[ii][jj] = 'O';
			return;
		}
		
		//left
		ii= i;
		jj = j-1;
		if(comprobarFicha(ii, jj)) {
			tablero[ii][jj] = 'O';
			return;
		}
		//right
		jj = j+1;
		if(comprobarFicha(ii, jj)) {
			tablero[ii][jj] = 'O';
			return;
		}
		//down
		--ii;
		jj = j;
		if(comprobarFicha(ii, jj)) {
			tablero[ii][jj] = 'O';
			return;
		}
		
		//down left
		jj--;
		if(comprobarFicha(ii, jj)) {
			tablero[ii][jj] = 'O';
			return;
		}
		
		//down right
		jj += 2;
		if(comprobarFicha(ii, jj)) {
			tablero[ii][jj] = 'O';
			return;
		}
		
		//TODO: mirar forma de randomizar el patrón
		
	}
	
	//metodo comprobar victoria
	public static boolean comprobarVictoria() {
		// mientras no tengamos ganador, comprobaremos todas la combinaciones ganadoras:
		boolean ganaX = false;
		boolean ganaO = false;
		// ganaX = true;
		// ganaO = true;
		
		// comprobaremos el array con cada una de las combinaciones ganadoras
		
		//X o O
		if(jugador) {
			//turno X
			if(tablero[0][0] == 'X' && tablero[1][0] == 'X' && tablero[2][0] == 'X') {
				
				/*	  0   1   2
				 * 0 [1] [ ] [ ]
				 * 1 [1] [ ] [ ]
				 * 2 [1] [ ] [ ]
				 */
				ganaX = true;
				
			}
			if(tablero[0][1] == 'X' && tablero[1][1] == 'X' && tablero[2][1] == 'X') {
				/*	  0   1   2
				 * 0 [ ] [1] [ ]
				 * 1 [ ] [1] [ ]
				 * 2 [ ] [1] [ ]
				 */
				ganaX = true;
			}
			if(tablero[0][2] == 'X' && tablero[1][2] == 'X' && tablero[2][2] == 'X') {
				/*	  0   1   2
				 * 0 [ ] [ ] [1]
				 * 1 [ ] [ ] [1]
				 * 2 [ ] [ ] [1]
				 */
				ganaX = true;
			}
			if(tablero[0][0] == 'X' && tablero[0][1] == 'X' && tablero[0][2] == 'X') {
				/*	  0   1   2
				 * 0 [1] [1] [1]
				 * 1 [ ] [ ] [ ]
				 * 2 [ ] [ ] [ ]
				 */
				ganaX = true;
			}
			if(tablero[0][0] == 'X' && tablero[0][1] == 'X' && tablero[0][2] == 'X') {
				/*	  0   1   2
				 * 0 [1] [1] [1]
				 * 1 [ ] [ ] [ ]
				 * 2 [ ] [ ] [ ]
				 */
				ganaX = true;
			}
			if(tablero[1][0] == 'X' && tablero[1][1] == 'X' && tablero[1][2] == 'X') {
				/*	  0   1   2
				 * 0 [ ] [ ] [ ]
				 * 1 [1] [1] [1]
				 * 2 [ ] [ ] [ ]
				 */
				ganaX = true;
			}
			if(tablero[2][0] == 'X' && tablero[2][1] == 'X' && tablero[2][2] == 'X') {
				/*	  0   1   2
				 * 0 [ ] [ ] [ ]
				 * 1 [ ] [ ] [ ]
				 * 2 [1] [1] [1]
				 */
				ganaX = true;
			}
			
			// diagonal1
			if(tablero[0][0] == 'X' && tablero[1][1] == 'X' && tablero[2][2] == 'X') {
				/*	  0   1   2
				 * 0 [1] [ ] [ ]
				 * 1 [ ] [1] [ ]
				 * 2 [ ] [ ] [1]
				 */
				ganaX = true;
			}
			
			// diagonal2
			if(tablero[0][2] == 'X' && tablero[1][1] == 'X' && tablero[2][0] == 'X') {
				/*	  0   1   2
				 * 0 [ ] [ ] [1]
				 * 1 [ ] [1] [ ]
				 * 2 [1] [ ] [ ]
				 */
				ganaX = true;
			}
			
			if (ganaX == true) {
				System.out.println("HA GANADO EL JUGADOR");
				return true;
			}
			
		}else {
			//turno O
			if(tablero[0][0] == 'O' && tablero[1][0] == 'O' && tablero[2][0] == 'O') {
				/*	  0   1   2
				 * 0 [1] [ ] [ ]
				 * 1 [1] [ ] [ ]
				 * 2 [1] [ ] [ ]
				 */
				ganaO = true;
			}
			if(tablero[0][1] == 'O' && tablero[1][1] == 'O' && tablero[2][1] == 'O') {
				/*	  0   1   2
				 * 0 [ ] [1] [ ]
				 * 1 [ ] [1] [ ]
				 * 2 [ ] [1] [ ]
				 */
				ganaO = true;
			}
			if(tablero[0][2] == 'O' && tablero[1][2] == 'O' && tablero[2][2] == 'O') {
				/*	  0   1   2
				 * 0 [ ] [ ] [1]
				 * 1 [ ] [ ] [1]
				 * 2 [ ] [ ] [1]
				 */
				ganaO = true;
			}
			if(tablero[0][0] == 'O' && tablero[0][1] == 'O' && tablero[0][2] == 'O') {
				/*	  0   1   2
				 * 0 [1] [1] [1]
				 * 1 [ ] [ ] [ ]
				 * 2 [ ] [ ] [ ]
				 */
				ganaO = true;
			}
			if(tablero[0][0] == 'O' && tablero[0][1] == 'O' && tablero[0][2] == 'O') {
				/*	  0   1   2
				 * 0 [1] [1] [1]
				 * 1 [ ] [ ] [ ]
				 * 2 [ ] [ ] [ ]
				 */
				ganaO = true;
			}
			if(tablero[1][0] == 'O' && tablero[1][1] == 'O' && tablero[1][2] == 'O') {
				/*	  0   1   2
				 * 0 [ ] [ ] [ ]
				 * 1 [1] [1] [1]
				 * 2 [ ] [ ] [ ]
				 */
				ganaO = true;
			}
			if(tablero[2][0] == 'O' && tablero[2][1] == 'O' && tablero[2][2] == 'O') {
				/*	  0   1   2
				 * 0 [ ] [ ] [ ]
				 * 1 [ ] [ ] [ ]
				 * 2 [1] [1] [1]
				 */
				ganaO = true;
			}
			// diagonal1
			if(tablero[0][0] == 'O' && tablero[1][1] == 'O' && tablero[2][2] == 'O') {
				/*	  0   1   2
				 * 0 [1] [ ] [ ]
				 * 1 [ ] [1] [ ]
				 * 2 [ ] [ ] [1]
				 */
				ganaO = true;
			}
			// diagonal2
			if(tablero[0][2] == 'O' && tablero[1][1] == 'O' && tablero[2][0] == 'O') {
				/*	  0   1   2
				 * 0 [ ] [ ] [1]
				 * 1 [ ] [1] [ ]
				 * 2 [1] [ ] [ ]
				 */
				ganaO = true;
			}
			
			if (ganaO == true) {
				System.out.println("HA GANADO LA CPU");
				return true;
			}
			
			}
		return false;
		}
	

}
