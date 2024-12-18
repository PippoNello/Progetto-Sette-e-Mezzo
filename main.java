package setteemezzo;

import java.net.ServerSocket;
import java.net.Socket;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 1237;
		num_client clients= new num_client();
		Mazzo mazzo = new Mazzo();
		
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			
			while(true) {
                Socket client = serverSocket.accept();
                System.out.println("Nuovo client connesso.");
                clients.inc();
                Thread handler = new Thread(new thread_client(clients, client, mazzo));
                System.out.println("Avvio del thread...");
                handler.start();
			}
		}catch (Exception e) {
            System.err.println("Errore del server: " + e.getMessage());
            e.printStackTrace();
        }
	}
}


