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
			//comportamiento de la cpu segun el turno
			//turno 1 o 2 coloca random
			if(numeroTurno == 1 || numeroTurno == 2) {
				primeraTirada();
			}
			//turno 3 intenta jugada
			
			//turno 4 intenta bloquear
			
			//se pasa el turno
			jugador = true;
		}
		
	}
	//metodo comprobar
	public static boolean comprobarFicha(int i, int j) {
		//si hay espacio true
		if(tablero[i][j] == ' ') {
			return true;
		}else {
			if(jugador) {
				System.out.println("Casilla ocupada:");
				return false;
			}else {
				return false;
			}
			
		}
	}
	
	//primera tirada (turno 1 o 2)
	public static void primeraTirada() {
		int i;
		int j;
		do {
			i = r.nextInt(3);
			j = r.nextInt(3);
		}while(!comprobarFicha(i,j));
		tablero[i][j] = 'O';
	}
	//tirada turno 3 intenta seguir jugada o tirada turno 4 intenta bloquear
	public static void segundaTirada() {
		//si es el turno 3 recorre el array y intenta poner una ficha al lado
		
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				//detectamos la ficha
				if(tablero[i][j] == 'O') {
					//miramos al rededor de la posición
					/*	   j0  j1  j2
					 * i0 [0 0] [0 1] [0 2]
					 * i1 [1 0] [1 1] [1 2]
					 * i2 [2 0] [1 2] [2 2]
					 */
					
				}
			}
		}
		
	}
	
	//generador de estrategias
	
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
