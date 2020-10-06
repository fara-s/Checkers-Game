// Fara Stringfellow
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/***
 * part of server 
 * gets click from client sends to game class via server 
 * returns update object with values used to reprint the client
 * 
 * 
 */
public class DraughtsClientRunner implements Runnable {
	private Socket clientSocket = null;
	private Server server = null;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;
	private boolean start = true;
	private int numPlayers;


	public DraughtsClientRunner(Socket s,Server parent, int numPlayers) {
		this.numPlayers = numPlayers;
		this.clientSocket = s;
		this.server = parent;
		try {
			outputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
			inputStream = new ObjectInputStream(this.clientSocket.getInputStream());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		// receive coordinates
		try {
			ClickedCoordinate cc = null;
			
			//send over start conditions
			if(start) {
				server.updateGame();
				start = false;
			}
			//Sends game info when a click is made
			while((cc = (ClickedCoordinate)inputStream.readObject())!= null) { //if there is a coordinate then read it 
				server.updateGame();
				//update the game class with the selected tile
				server.getGame().clickedTile(cc.row, cc.column);
				
			
				if (cc.newgame == true){
					server.getGame().newGame();
				}
			}
			inputStream.close();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	//sends game to read move which updates client view 
	public void sendUpdatedGame() {
		try {
			//send update model with game info
			outputStream.writeObject(new UpdateModel(server.getGame().getSelectedRow(), server.getGame().getSelectedCol(),
					server.getGame().getAllowedMoves(), server.getGame().getDraughtsLogic(), 
					server.getGame().isGameInProgress(), numPlayers,server.getGame().sendCurrentPlayer(),
					server.getGame().getDraughtsLogic().matrixToArrayList()));

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
