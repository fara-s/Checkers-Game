//Fara Stringfellow
import java.io.Serializable;
import java.util.ArrayList;

public class UpdateModel implements Serializable {
	
	/**
	 * 	Method to send over game info from server to display
	 * in the clients view 
	 */
	
	//ATTRIBUTES
	int selectedRow;
	int selectedColumn;
	ArrayList<Move> allowedMoves;  
	DraughtsLogic draughtsLogic;
	boolean gameInProgress;
	int playernum;
	int currentPlayer;
	ArrayList<Integer> board;
	
	
	
	
	//CONSTRUCTOR
	public UpdateModel(int r, int c, ArrayList<Move> am, DraughtsLogic dl, boolean gip, int pnum, int cp, ArrayList<Integer> board ) {
		
		this.selectedRow = r;
		this.selectedColumn = c;
		this.allowedMoves = am;
		this.draughtsLogic = dl;
		this.gameInProgress = gip;
		this.playernum = pnum;
		this.currentPlayer=cp;
		this.board = board;
		
	}

}
