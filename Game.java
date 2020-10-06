// Fara Stringfellow
import java.util.ArrayList;



public class Game {



	//ATTRIBUTES
	private boolean gameBeingPlayed; 
	private int turnsmade = 0;
	private DraughtsLogic draughtsLogic;
	private int currentPlayer;      
	private int selectedRow, selectedCol;  



	//CONSTRUCTOR
	public Game() {
		draughtsLogic = new DraughtsLogic();
		newGame();
	}

	//GETTERS + SETTERS
	boolean isGameInProgress() {
		return gameBeingPlayed;
	}
	public DraughtsLogic getDraughtsLogic() {
		return this.draughtsLogic;
	}
	public void setDraughtsLogic(DraughtsLogic draughtsLogic) {
		this.draughtsLogic = draughtsLogic;
	}
	public int getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(int selectedRow) {
		this.selectedRow = selectedRow;
	}
	public int getSelectedCol() {
		return selectedCol;
	}
	public void setSelectedCol(int selectedCol) {
		this.selectedCol = selectedCol;
	}
	private ArrayList<Move> allowedMoves;  

	public ArrayList<Move> getAllowedMoves() {
		return allowedMoves;
	}
	public void setAllowedMoves(ArrayList<Move> allowedMoves) {
		this.allowedMoves = allowedMoves;
	}
	public void setGameInProgress(boolean play) {
		this.gameBeingPlayed = play;
	}
	public int sendCurrentPlayer() {
		return currentPlayer;
	}

	//METHODS 
	public void newGame() {
		draughtsLogic.startingPositions();  
		currentPlayer= DraughtsLogic.WHITE_CHECKER;
		allowedMoves = draughtsLogic.getMoveOptions(DraughtsLogic.WHITE_CHECKER);  
		selectedRow = -1;   
		gameBeingPlayed = true;
		System.out.println("New game");
	}


	public void gameOver() {
		gameBeingPlayed = false;
	}





	public void clickedTile(int row, int col) {

		for (Move m: allowedMoves)
			if (m.fromRow == row && m.fromColumn == col) {
				selectedRow = row;
				selectedCol = col;
			}


		for (Move m: allowedMoves)
			if (m.fromRow == selectedRow && m.fromColumn == selectedCol
			&& m.toRow == row && m.toColumn == col) {
				moveChecker(m);
				return;
			}
		//		System.out.println("do click square game");
		//		System.out.println("game: selected" + "("+selectedRow + ","+selectedCol+")");

	}  


	public boolean moveChecker(Move move) {

		draughtsLogic.Move(move);
		if (move.isJump()) {
			allowedMoves = draughtsLogic.getSkipOptions(currentPlayer,move.toRow,move.toColumn);
			if (allowedMoves != null) {
				selectedCol = move.toColumn;
				//	repaint();

			}

		}

		//SWAP TURN
		turnsmade++;
		currentPlayer = swapPlayers(currentPlayer);

		allowedMoves = draughtsLogic.getMoveOptions(currentPlayer);

		if (allowedMoves == null)
			gameOver();

		selectedRow = -1;


		if (allowedMoves != null) {
			boolean sameStartSquare = true;
			for (int i = 1; i < allowedMoves.size(); i++)
				if (allowedMoves.get(i).fromRow != allowedMoves.get(0).fromRow
				||allowedMoves.get(i).fromColumn != allowedMoves.get(0).fromColumn) {
					sameStartSquare = false;
					break;
				}
			if (sameStartSquare) {
				selectedRow = allowedMoves.get(0).fromRow;
				selectedCol = allowedMoves.get(0).fromColumn;
			}
		}

		return true;
		//		repaint();
	}  


	public int swapPlayers(int currplayer) {
		if (currplayer == DraughtsLogic.WHITE_CHECKER) {

			currplayer = DraughtsLogic.BLACK_CHECKER ;
		}else 
			currplayer = DraughtsLogic.WHITE_CHECKER;
		return currplayer;


	}

}




