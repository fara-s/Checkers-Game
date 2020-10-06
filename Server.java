//Fara Stringfellow
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{

	//ATTRIBUTES
	private ServerSocket server;
	private ArrayList<DraughtsClientRunner> clients = new ArrayList<DraughtsClientRunner>();
	private int numPlayersATM;
	private Game game;
	
	//CONSTRUCTOR
	public Server(Game g) {

		this.game = g;
		numPlayersATM = 0;
		try {
			server = new ServerSocket(8765);
			System.out.println("-----Game Server-------");
		}catch(IOException e) {
			e.printStackTrace();
		}

	}
	
	
	
	//GETTERS
	public Game getGame() {
		return this.game;

	}
	
	

	//METHODS 
	public void run() {
		while(true) {
			Socket clientSocket = null;
			try {
				//accept 2 clients only
				if (clients.size()<2) {
					clientSocket = server.accept();
					numPlayersATM++;
					System.out.println("New client connected");
					//pass player number to the update object
					DraughtsClientRunner client = new DraughtsClientRunner(clientSocket,this, numPlayersATM);
					clients.add(client);
					new Thread(client).start();
				}

			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}



	//send the updated game to the 2 clients 
	public void updateGame() {
		for(DraughtsClientRunner c: clients) {
			if(c != null) {
				c.sendUpdatedGame();
			}
		}
	}



	public static void main(String[] args) {
		Game game = new Game();
		Thread t = new Thread(new Server(game));
		t.start();
		try {
			t.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}

	}
}


