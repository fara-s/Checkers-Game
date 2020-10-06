// Fara Stringfellow
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.SwingWorker;


/**
 * 
 * class that reads the update and updates GUI
 *
 */
public class ReadMove extends SwingWorker<Void,Void> {
	
        private Socket socket = null;
        private ObjectInputStream inputStream = null;
        private Client client;
        public ReadMove(Socket s, Client parent) {
           
        	this.socket = s;
            this.client = parent;
            try {
                inputStream = new ObjectInputStream(this.socket.getInputStream());
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        
      
		public Void doInBackground() {
            System.out.println("Started read move");
            UpdateModel update=null;
            	
            try {
            	//input coming from client runner class sendUpdatedGame method 
            	//sends  model data to client 
            	//then updates each GUI
                while((update = (UpdateModel)inputStream.readObject())!= null) {
                   
       
                    client.updateGUI(update);
                }
            }catch(ClassNotFoundException e) {
                e.printStackTrace();
            }catch(IOException e) {
                e.printStackTrace();
            }finally {
                return null;
            }
        }
    }