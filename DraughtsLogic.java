// Fara Stringfellow
import java.io.Serializable;
import java.util.ArrayList;


public class DraughtsLogic implements Serializable {

	//ATTRIBUTES 
	//constant IDs of checker types and empty squares
	static final int EMPTY_TILE = 0;
	static final int WHITE_CHECKER = 1;
	static final int WHITE_KING = 10;
	static final int BLACK_CHECKER = 2;
	static final int BLACK_KING = 20;
	//Size of checker board. (number of squares)
	static final int CHECKERBOARD_WIDTH = 8;



	//2D array representing the board
	private int[][] board;
	

	public int[][] getBoard(){
		return board;

	}
	//CONSTRUCTOR
	public DraughtsLogic() {
		board= new int[CHECKERBOARD_WIDTH][CHECKERBOARD_WIDTH];
		startingPositions();

	} 

	// METHODS
	public void startingPositions() {
		for (int row = 0; row < CHECKERBOARD_WIDTH; row++) {
			for (int column = 0; column < CHECKERBOARD_WIDTH; column++) {
				if (row % 2 == column % 2  && row<3 ) { //if both coordinates are either even or odd 
					board[row][column] = BLACK_CHECKER; }
				else if (( row % 2 == column % 2) && (row > 4))
				{	board[row][column] = WHITE_CHECKER;}
				else
				{board[row][column] = EMPTY_TILE;}
			}

		}
	}


	public int getTilesContents(int row, int column) {
		return board[row][column];
	}
	
//method used in game container to pull squares checker from arraylist that is the board
	public  int getTilesContentsArrayList(int row, int column, ArrayList<Integer> arrayList2 ) {
		int y = row* 8+column;
		return arrayList2.get(y);
		
	}
	
	//to pass over the server matrix must be converted to arraylist 
	public  ArrayList<Integer> matrixToArrayList() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		for (int r=0; r<8;r++) {
			for (int c=0; c<8;c++) {
				array.add(board[r][c]);
				System.out.println(board[r][c]);
			}
		}
		return array;
	}


	public void Move(Move move) {	
		Move(move.fromRow, move.fromColumn, move.toRow, move.toColumn);
	}


	//move checker and change states
	public void Move(int r1, int c1, int r2, int c2) {
		board[r2][c2] = board[r1][c1];
		board[r1][c1] = EMPTY_TILE;
		//is a jump 
		if(r1 - r2 == 2 || r1-r2 ==-2) {
			int takenPieceRow = (r1 +r2) /2;
			int takenPieceColumn = (c1 +c2) /2;
			board[takenPieceRow][takenPieceColumn] = EMPTY_TILE;
		}

		//turn into king. if checker reaches opposite row 
		if (r2 ==0 && board[r2][c2] == WHITE_CHECKER){
			board[r2][c2] = WHITE_KING;
		}
		if (r2 ==7 && board[r2][c2] == BLACK_CHECKER){
			board[r2][c2] = BLACK_KING;
		}
	}

	//if checker is off board
	public boolean offBoard(int row, int column) {
		if (row < 0 || row >= CHECKERBOARD_WIDTH || column < 0 || column >= CHECKERBOARD_WIDTH) {
			return true; 
		}
		else {
			return false;
		}
	}
	//if array position has a checker in it
	public boolean isFilled(int row, int column) {
		if (board[row][column] != EMPTY_TILE) {
			return true;
		}
		else {
			return false;
		}
	}

	//returns true if the move input is allowed
	public boolean moveAllowed(int player, int r1, int c1, int r2, int c2) {
		if (offBoard(r2, c2)) {
			return false; 
		}
		if (isFilled(r2, c2)) {
			return false;
		}
		//regular checker can only move forward
		if (player == WHITE_CHECKER) {
			if (board[r1][c1] == WHITE_CHECKER && r2 > r1) {
				return false;  
			}else {
				return true; 
			}
		}else {
			if (board[r1][c1] == BLACK_CHECKER && r2 < r1) {
				return false;  
			}

			else {
				return true;  
			}
		}
	}

	/**
	 * if player is allowed to take oppositions piece return true 
	 * @param player
	 * @param r1
	 * @param c1
	 * @param r2
	 * @param c2
	 * @param r3
	 * @param c3
	 * @return
	 */

	public boolean takePieceAllowed(int player, int r1, int c1, int r2, int c2, int r3, int c3) {

		if (offBoard(r3, c3)) {
			return false; 
		}
		if (isFilled(r3, c3)) {
			return false;
		}

		if (player == WHITE_CHECKER) {
			if (board[r1][c1] == WHITE_CHECKER && r3 > r1)
				return false;  
			if (board[r2][c2] != BLACK_CHECKER && board[r2][c2] != BLACK_KING)
				return false;  
			return true; 
		}
		else {
			if (board[r1][c1] == BLACK_CHECKER && r3 < r1)
				return false; 
			if (board[r2][c2] != WHITE_CHECKER && board[r2][c2] != WHITE_KING)
				return false;  
			return true;  
		}

	} 





	/**
	 * gets all the moves a player can make
	 * @param playersChecker
	 * @return
	 */
	public ArrayList<Move> getMoveOptions(int playersChecker) {
		ArrayList<Move> listOfMoves = new ArrayList<Move>();

		int king;
		if (playersChecker == WHITE_CHECKER)
			king = WHITE_KING;
		else
			king = BLACK_KING; 

		if (playersChecker!= WHITE_CHECKER && playersChecker != BLACK_CHECKER) {
			return null;
		}

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {

				for(int row2 = -1; row2<2; row2 +=2) {
					int r2 = row+row2;
					int row3 = row2*2;
					int r3 = row+row3;
					for(int column2 = -1; column2<2; column2 +=2) {
						int c2 = col+column2;
						int column3 = column2*2;
						int c3 = col+column3;
						if (board[row][col] == playersChecker || board[row][col] == king) {
							if (takePieceAllowed(playersChecker, row, col, r2,c2, r3, c3))
								listOfMoves.add(new Move(row, col, r3, c3, playersChecker));
						}
					}
				}
			}
		}
		if (listOfMoves.size() == 0) {
			for (int row = 0; row < 8; row++) {
				for (int col = 0; col < 8; col++) {

					for(int row2 = -1; row2<2; row2 +=2) {
						int r2 = row+row2;
						for(int column2 = -1; column2<2; column2 +=2) {
							int c2 = col+column2;
							if (board[row][col] == playersChecker || board[row][col] == king) {
								if (moveAllowed(playersChecker,row,col,r2,c2))
									listOfMoves.add(new Move(row,col,r2,c2, playersChecker));
							}
						}
					}
				}
			}
		}
		if (listOfMoves.size() == 0)
			return null;
		else {
			ArrayList<Move> listOfMovesCopy = new ArrayList<Move>();

			for (int i = 0; i < listOfMoves.size(); i++)
				listOfMovesCopy.add((listOfMoves.get(i)));
			return listOfMovesCopy;
		}

	} 


	/**
	 * gets all the moves a player can make that involve a skip over a player
	 * @param playersChecker
	 * @param row
	 * @param column
	 * @return
	 */
	public ArrayList<Move> getSkipOptions(int playersChecker, int row, int column) {
		if (playersChecker != WHITE_CHECKER && playersChecker != BLACK_CHECKER)
			return null;
		int playerKing;  
		if (playersChecker == WHITE_CHECKER)
			playerKing = WHITE_KING;
		else
			playerKing = BLACK_KING;
		ArrayList<Move> listOfMoves = new ArrayList<Move>();  
		for(int row2 = -1; row2<2; row2 +=2) {
			int r2 = row+row2;
			int row3 = row2*2;
			int r3 = row+row3;
			for(int column2 = -1; column2<2; column2 +=2) {
				int c2 = column+column2;
				int column3 = column2*2;
				int c3 = column+column3;

				if (board[row][column] == playersChecker || board[row][column] == playerKing) {
					if (takePieceAllowed(playersChecker, row, column, r2, c2, r3, c3)) 
						listOfMoves.add(new Move(row, column, r3, c3, playersChecker));
				}
			}
		}

		if (listOfMoves.size() == 0)
			return null;
		else {
			ArrayList<Move> listOfMovesCopy = new ArrayList<Move>();
			for (int i = 0; i < listOfMoves.size(); i++)
				listOfMovesCopy.add(listOfMoves.get(i));
			return listOfMovesCopy;
		}

	}  
}

