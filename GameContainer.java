//Fara Stringfellow
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GameContainer extends JPanel {


/**
 * class that displays the updated board 
 */
	
	
	//ATTRIBUTES
	//JLabel message;  
	boolean gameInProgress; 
	private UpdateModel update;
	


	//CONSTRUCTOR
	public GameContainer(UpdateModel um) {
	
		this.update = um;
		setBackground(Color.GRAY);
		repaint();
		
		
	}
	public static int[][] arrayListToMatrix(ArrayList<Integer> array, int sizeOfMatrix){

		int[][] matrixOut= new int[sizeOfMatrix][sizeOfMatrix];
		int y = 0;
		for (int r=0; r<sizeOfMatrix;r++) {
			for (int c=0; c<sizeOfMatrix;c++) {
				matrixOut[r][c] = array.get(y);
				//System.out.println(r+","+c+" OUT "+matrixOut[r][c]);
				y++;
			}
		}
		return matrixOut;

	}


	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.black);
		g.drawRect(0,0,getSize().width-1,getSize().height-1);
		g.drawRect(1,1,getSize().width-3,getSize().height-3);

		for (int r = 0; r < DraughtsLogic.CHECKERBOARD_WIDTH; r++) {
			for (int c = 0; c < DraughtsLogic.CHECKERBOARD_WIDTH; c++) {
				if ( r % 2 == c % 2 )

					g.setColor(Color.LIGHT_GRAY);
				else
					g.setColor(Color.WHITE);
				g.fillRect(2 + c*20, 2 + r*20, 20, 20);
//				switch (update.draughtsLogic.getTilesContents(r,c)) {
				switch (update.draughtsLogic.getTilesContentsArrayList(r, c, update.board)) {
				case DraughtsLogic.WHITE_CHECKER:
					g.setColor(Color.WHITE);
					g.fillOval(4 + c*20, 4 + r*20, 15, 15);
					break;
				case DraughtsLogic.BLACK_CHECKER:
					g.setColor(Color.BLACK);
					g.fillOval(4 + c*20, 4 + r*20, 15, 15);
					break;
				case DraughtsLogic.WHITE_KING:
					g.setColor(Color.WHITE);
					g.fillOval(4 + c*20, 4 + r*20, 15, 15);
					g.setColor(Color.BLACK);
					g.drawString("K", 7 + c*20, 16 + r*20);
					break;
				case DraughtsLogic.BLACK_KING:
					g.setColor(Color.BLACK);
					g.fillOval(4 + c*20, 4 + r*20, 15, 15);
					g.setColor(Color.WHITE);
					g.drawString("K", 7 + c*20, 16 + r*20);
					break;
				}
			}
		}

		if (update.gameInProgress) {
			//show each players move options
			g.setColor(Color.green);
			for (int i = 0; i < update.allowedMoves.size(); i++) {
				g.drawRect(2 + update.allowedMoves.get(i).fromColumn*20, 2 + update.allowedMoves.get(i).fromRow*20, 19, 19);
				g.drawRect(3 + update.allowedMoves.get(i).fromColumn*20, 3 + update.allowedMoves.get(i).fromRow*20, 17, 17);
			}
			//Selected piece is yellow
			if (update.selectedRow >= 0) {
				g.setColor(Color.blue);
				g.drawRect(2 + update.selectedColumn *20, 2 + update.selectedRow*20, 19, 19);
				g.drawRect(3 + update.selectedColumn *20, 3 + update.selectedRow*20, 17, 17);
				
				//if clicked on movable piece
				g.setColor(Color.green);
				for (int i = 0; i < update.allowedMoves.size(); i++) {
					if (update.allowedMoves.get(i).fromColumn == update.selectedColumn && update.allowedMoves.get(i).fromRow == update.selectedRow) {
						g.drawRect(2 + update.allowedMoves.get(i).toColumn*20, 2 + update.allowedMoves.get(i).toRow*20, 19, 19);
						g.drawRect(3 + update.allowedMoves.get(i).toColumn*20, 3 + update.allowedMoves.get(i).toRow*20, 17, 17);
					}
				}
			}
		}

	} 


}  



