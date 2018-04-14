package assignment4Game;

import java.io.*;

public class Game {
	
	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		int columnPlayed = 3; int player;
		
		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		int nbTurn = 1;
		
		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}
	
	public static int getNextMove(BufferedReader keyboard, Configuration c, int player) {
		try { 
		System.out.println("AI has made its move.");
		c.print(); 
		System.out.println("It's your turn now player 2. Please choose a column number between 0 to 6 (inclusive) to place your disk:");  
		String columnString = keyboard.readLine(); 
		// keep asking until an integer is entered
		while (!columnString.equals("0") && !columnString.equals("1") && !columnString.equals("2") && !columnString.equals("3") && !columnString.equals("4") && !columnString.equals("5") && !columnString.equals("6")) {
			System.out.println("Please enter an integer between 0 to 6 (inclusive):");
			columnString = keyboard.readLine();
		} 
		int column = Integer.parseInt(columnString);
		// if the column is full, ask for another one 
		while (c.available[column] == 6) {
			System.out.println("This column is full. Please choose another column: ");
			columnString = keyboard.readLine(); 
			// make sure the user inputs an integer
			while (!columnString.equals("0") && !columnString.equals("1") && !columnString.equals("2") && !columnString.equals("3") && !columnString.equals("4") && !columnString.equals("5") && !columnString.equals("6")) {
				System.out.println("Please enter an integer between 0 to 6 (inclusive):");
				columnString = keyboard.readLine();
			}
			column = Integer.parseInt(columnString); 
		}
		// return the integer entered by the user
		System.out.println("Your move has been recorded.");
		return column; 
		} catch (IOException e) {
			System.out.println("Oh no! A problem has occurred and your move was not made or recorded!");
			return -1; 
		}
	}
	
	public static int firstMovePlayer1 (){
		return 3;
	}
	
	public static int movePlayer1 (int columnPlayed2, Configuration c){
		// initialize a return value for the column 
		int column = -1; 
		// use conditional statements to determine what column is chosen
		if ((column = c.canWinNextRound(1)) != -1) {
			return column; 
		// if cannot win next round
		} else if ((column = c.canWinTwoTurns(1)) != -1) {
			return column; 
		// if cannot win next round nor in two turns
		} else {
			// choose the same column as the other player (if is not full)
			if (c.available[columnPlayed2] < 6) {
				return columnPlayed2; 
			// if full, choose next available 
			} else { 
				for (int i = 1; i < 7; i++) {
					if (columnPlayed2 - i >= 0 && c.available[columnPlayed2 - i] < 6) {
						return columnPlayed2 - i;
					} else if (columnPlayed2 + i <= 6 && c.available[columnPlayed2 + i] < 6) {
						return columnPlayed2 + i; 
					} 
				}
			}
		}
		return column;
	}
	
}

