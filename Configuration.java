// there will be a choice of easy, medium, or hard soon.

public class Configuration {
	
	public int[][] board;
	public int[] available;
	boolean spaceLeft;
	
	public Configuration(){
		board = new int[7][6];
		available = new int[7];
		spaceLeft = true;
	}
	
	public void print(){
		System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
		System.out.println("+---+---+---+---+---+---+---+");
		for (int i = 0; i < 6; i++){
			System.out.print("|");
			for (int j = 0; j < 7; j++){
				if (board[j][5-i] == 0){
					System.out.print("   |");
				}
				else{
					System.out.print(" "+ board[j][5-i]+" |");
				}
			}
			System.out.println();
		}
	}
	
	public void addDisk (int index, int player){
		// add the disk to the board, in the corresponding column's first available space
		int add = this.available[index]; 
		this.board[index][add] = player;
		
		// update the availability of the column 
		this.available[index] = add + 1;
		
		// updating spaceLeft if needed 
		// spaceLeft is false when all columns are full 
		int numberFilled = 0; 
		for (int i = 0; i < this.available.length; i++) {
			if (this.available[i] == 6) {
				numberFilled++; 
			}
		}
		if (numberFilled == 7) {
			this.spaceLeft = false;
		}
	}
	
	// helper method to remove a disk
	// removes the last inserted disk in the input column 
	public void removeDisk (int column) { 
		// if no disk exists, don't do anything
		if (this.available[column] != 0) {
			// remove the disk from the board
			int remove = this.available[column] - 1; 
			this.board[column][remove] = 0;
			// update the availability of the column
			this.available[column] = remove; 		
		}		
	}
	
	public boolean isWinning (int lastColumnPlayed, int player){
		// conditional statements to find if winning on at least one of row, column, or diagonal
		if (this.winningRow(lastColumnPlayed, player)) {
			return true;
		} else if (this.winningColumn(lastColumnPlayed, player)) {
			return true;
		} else if (this.winningAscendingDiagonal(lastColumnPlayed, player)) {
			return true;
		} else if (this.winningDescendingDiagonal(lastColumnPlayed, player)) {
			return true;
		} else {
			return false;
		}
	}
	
	// helper method 1 for isWinning 
	// checks for winningRow
	public boolean winningRow(int lastColumnPlayed, int player) {
		int numberOfConsecutives = 0; 
		// find largest non-available row in lastColumnPlayed
		// iterate across the board from left to right
		for (int i = 0; i < 7; i++) {
			if (this.board[i][this.available[lastColumnPlayed] - 1] == player) {
				numberOfConsecutives++; 
				if (numberOfConsecutives == 4) {
					return true; 
				}
			} else {
				numberOfConsecutives = 0; 
			}
		}
		return false; 
	}
	
	// helper method 2 for isWinning
	// checks for winningColumn
	public boolean winningColumn(int lastColumnPlayed, int player) {
		int numberOfConsecutives = 0; 
		// iterate downwards from the lastColumnPlayed 
		for (int i = this.available[lastColumnPlayed] - 1; i >= 0; i--) {
			if (this.board[lastColumnPlayed][i] == player) {
				numberOfConsecutives++;
				if (numberOfConsecutives == 4) {
					return true; 
				}
			} else {
				break;
			}
		}
		return false; 
	}
	
	// helper method 3 for isWinning 
	// checks for winningAscendingDiagonal
	public boolean winningAscendingDiagonal(int lastColumnPlayed, int player) {
		// first find all elements on the ascending diagonal and store in an array
		// max number of elements on any diagonal is 6
		int[][] diagonalElements = new int[6][2];
		// first initialize all elements to -1
		for (int i = 0; i < diagonalElements.length; i++) {
			for (int j = 0; j < diagonalElements[i].length; j++) {
				diagonalElements[i][j] = -1; 
			}
		}
		// find the first element on the ascending diagonal
		// i is the row and j is the column
		int i = this.available[lastColumnPlayed] - 1;
		int j = lastColumnPlayed;
		while (i > 0 && j > 0) {
			i--;
			j--; 
		}
		// count number of consecutives
		int numberOfConsecutives = 0; 
		while (i < 6 && j < 7) {
			if (this.board[j][i] == player) {
				numberOfConsecutives++;
				if (numberOfConsecutives == 4) {
					return true; 
				}
			} else {
				numberOfConsecutives = 0; 
			}
			j++;
			i++; 
		}
		return false; 
	}
	
	
	// helper method 4 for isWinning
	// checks for winningDescendingDiagonal
	public boolean winningDescendingDiagonal(int lastColumnPlayed, int player) {
		// descending diagonal is the diagonal with negative slope
		// row decreases by one while column increases by 1
		// find all elements on the descending diagonal 
		// the max number of elements on a diagonal is 6
		int[][] diagonalElements = new int[6][2]; 
		// first initialize all elements to -1
		for (int i = 0; i < diagonalElements.length; i++) {
			for (int j = 0; j < diagonalElements[i].length; j++) {
				diagonalElements[i][j] = -1; 
			}
		}
		// find the first element on the descending diagonal 
		// first element has the smallest row number but largest column number 
		// i is the row and j is the column
		int i = this.available[lastColumnPlayed] - 1;
		int j = lastColumnPlayed;
		while (i > 0 && j < 6) {
			i--;
			j++; 
		}
		// count numberOfConsecutives
		int numberOfConsecutives = 0;
		while (i < 6 && j >= 0) {
			if (this.board[j][i] == player) {
				numberOfConsecutives++;
				if (numberOfConsecutives == 4) {
					return true; 
				}
			} else {
				numberOfConsecutives = 0; 
			}
			i++;
			j--; 
		}
		return false; 
	}
	
	public int canWinNextRound (int player){
		// loop through all possible columns
		// temporarily place in each column to see if it would win or not
		// if can win then return the column
		// make sure to remove the disk before moving on to the next column
		for (int i = 0; i < 7; i++) {
			if (this.available[i] < 6) {
				this.addDisk(i, player);
				if (this.isWinning(i, player) == true) {
					this.removeDisk(i);
					return i;
				} else {
					this.removeDisk(i); 
				}
			}
		}
		return -1; 
	}
	
	public int canWinTwoTurns (int player){
		// loop through all columns
		for (int i = 0; i < 7; i++) {
			if (this.available[i] < 6) {
				// add a temporary disk for the player in each column 
				this.addDisk(i, player);
				// find which numbered disk to add for the other player
				int otherPlayer; 
				if (player == 1) {
					otherPlayer = 2;
				} else {
					otherPlayer = 1; 
				}
				// only consider the possible wins if the other player cannot win in between 
				if (this.canWinNextRound(otherPlayer) == -1) {
					// find the total number possible wins on the player's next turn 
					int[] possibleWinsNextTurn = this.possibleWins(player); 
					//System.out.println(Arrays.toString(possibleWinsNextTurn));
					int numberPossible = 0; 
					for (int j = 0; j < possibleWinsNextTurn.length; j++) {
						if (possibleWinsNextTurn[j] != -1) {
							numberPossible++; 
						} else {
							break;
						}
					}
					//System.out.println("This is numberPossible " + numberPossible);
					// can win in two turns if there are more than 1 possible wins in the next round
					// the other player can place his/her disk wherever and the player would still win
					if (numberPossible > 1) {
						removeDisk(i); 
						return i; 
					// case where if the other player blocks the player's possible win
					// and the player can still win 
					} else if (numberPossible == 1){
						// add the other player's disk in the possible win column
						this.addDisk(possibleWinsNextTurn[0], otherPlayer);
						int j = this.canWinNextRound(player); 
						if (j != -1) {
							removeDisk(possibleWinsNextTurn[0]);
							removeDisk(i);
							return i;
						} else {
							removeDisk(possibleWinsNextTurn[0]); 
							removeDisk(i);
						}
					// if cannot win in two turns on current column
					// removeDisk and go to the next column 
					} else {
						removeDisk(i); 
					}
				// if the other player can win in between, the player cannot win in two turns 
				// on the current column; go to the next column 
				} else {
					removeDisk(i); 
				}
			}
		}
		// if not, return -1 
		return -1; 
	}
	
	// helper method for canWinTwoTurns
	// returns an array of possible wins for the next round
	public int[] possibleWins(int player) {
		// maximum 6 possible wins (since there are 7 columns) 
		int[] possible = new int[7]; 
		// initialize all elements to -1 first
		for (int i = 0; i < possible.length; i++) {
			possible[i] = -1; 
		}
		// loop through all possible columns
		// temporarily place in each column to see if it would win or not
		// if can win then add the column to possible 
		// make sure to remove the disk before moving on to the next column
		int k = 0; 
		for (int i = 0; i < 7; i++) {
			if (this.available[i] < 6) {
				this.addDisk(i, player);
				if (this.isWinning(i, player) == true) {
					this.removeDisk(i);
					possible[k] = i; 
					k++; 
				} else {
					this.removeDisk(i);
				}
			}
		}
		return possible; 	
	}
	
}

