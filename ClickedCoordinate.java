//Fara Stringfellow
import java.io.Serializable;

public class ClickedCoordinate implements Serializable{

	/**
	 * 	Method to send over mouse clicks from client and implement them in the game class via the server 
	 */
	//ATTRIBUTES
	int column; 
	int row;
	int playerNo;
	boolean newgame;
	

	//CONSTRUCTOR
	public ClickedCoordinate(int r, int c, int playerNo, boolean ng) {

		this.row = r;
		this.column = c;
		this.playerNo=playerNo;
		this.newgame = ng;
	
	}

}
