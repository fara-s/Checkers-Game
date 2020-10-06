// Fara Stringfellow
import java.io.Serializable;

public class Move implements Serializable{

	//move needs to be object as will be stored in arrays 
	//ATTRIBUTES
	int fromRow;
	int fromColumn; 
	int toRow;
	int toColumn;
	int player;

	//CONSTRUCTOR
	public Move(int row1, int column1, int row2, int column2, int player) {
		this.player = player;
		this.fromRow = row1;
		this.fromColumn = column1;
		this.toRow = row2;
		this.toColumn = column2;
	}

	//METHODS 
	boolean isJump() {
		if (fromRow - toRow == 2 || fromRow - toRow == -2) {
			return true;
		}else {
			return false;
		}
	}


	public void print() {
	System.out.println("old coordinates : (" + fromRow +","+ fromColumn + ")");
	System.out.println("new coordinates : (" + toRow +","+ toColumn + ")");

		
	}

} 

