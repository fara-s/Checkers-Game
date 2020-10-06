//2131725s Fara Stringfellow
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/** 	1. starts up JFrame
 * 		2. receives player and game info from server 
 * 		3. uses info to displays game
 * 		4. sends player input info back to server
 */

public class Client extends JFrame implements ActionListener, MouseListener{

	//ATTRIBUTES
	private GameContainer gameContainer;
	private Socket server = null;
	private ObjectOutputStream outputStream;
	private Container contentPane;
	private UpdateModel update;
	//	private UpdateModel start;
	//	private String playernum;
	private JLabel text;  
	private boolean newGame;
	private JButton newGameBtn; 
	JFrame jFrame;

	//CONSTRUCTOR
	public Client() {

		contentPane = this.getContentPane();

		//Make connection 
		connect();
		try {
			outputStream = new ObjectOutputStream(server.getOutputStream());
		}catch(IOException e) {
			e.printStackTrace();
		}
		//Initiate readMove
		ReadMove rm = new ReadMove(server,this);
		rm.execute();


		// set up JFrame
		jFrame = new JFrame("Draughts");
		jFrame.setSize(250,400);
		jFrame.setContentPane(contentPane);
		newGameBtn = new JButton("New Game");
		newGame = false;
		text = new JLabel("",JLabel.CENTER);
		text.setFont(new  Font("Ariel", Font.BOLD, 16));
		text.setForeground(Color.BLACK);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		jFrame.setResizable(true);  
		jFrame.setVisible(true);



	}

	
	/**
	 * updates JFrame with updated game info
	 * @param update
	 */
	public void updateGUI(UpdateModel update) {
		this.update = update;
		jFrame.setTitle("Draughts: Player "+ update.playernum);
		newGameBtn.addActionListener(this);
		gameContainer = new GameContainer(update);
		gameContainer.addMouseListener(this);
		contentPane.add(gameContainer);
		contentPane.add(newGameBtn);
		contentPane.add(text);
		contentPane.setLayout(null);
		gameContainer.setBounds(45,40,164,164);
		newGameBtn.setBounds(70, 250, 120, 30);
		text.setBounds(0, 300, 250, 30);
		if (update.playernum == 1) {
			text.setBackground(Color.orange);
			contentPane.setBackground(Color.orange); 
		}else {
			text.setBackground(Color.pink);
			contentPane.setBackground(Color.pink); 
		}

		if(update.gameInProgress==false) {
			text.setText("Game Over");
		}

	}


	/**
	 * Method passes new game button boolean to server
	 * to start a new game
	 */
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == this.newGameBtn) {
			newGame = true;
			System.out.println("new game");
			try {
				outputStream.writeObject(new ClickedCoordinate(0, 0, 1, newGame));
				newGame = false;
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}

	}



	/**
	 *  if mouse is pressed on the board then send the coordinates,
	 *  player number and new game button boolean to server to be processed 
	 *  in the game class
	 */
	public void mousePressed(MouseEvent evt) {
		int col = (evt.getX() - 2) / 20;
		int row = (evt.getY() - 2) / 20;
		if (col >= 0 && col < 8 && row >= 0 && row < 8)

			text.setText(" ");
		try {
			//disable board for player if its not their turn
			if (update.currentPlayer == update.playernum) {
				text.setText("Your Move");
				outputStream.writeObject(new ClickedCoordinate(row, col, update.playernum, newGame));
			}else {
				text.setText("Wait for Player "+ update.currentPlayer + " to move"); 
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		updateGUI(update);	
	}

	public void setModel(UpdateModel um) {
		System.out.println("update model recieved");
		this.update = um;
	}



	private void connect() {
		try {
			server = new Socket("127.0.0.1",8765);
			System.out.println("Connected");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}


	public void mouseClicked(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}




	public static void main(String[] args) {
		Client  client = new Client();

	}


}




