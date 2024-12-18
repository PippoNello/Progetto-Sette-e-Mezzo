package setteemezzo;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;

public class thread_client implements Runnable {
	private num_client clients;
	private Socket link;
	private BufferedReader in;
    private PrintWriter out;
	Scanner scanner = new Scanner(System.in);
	Mazzo mazzo;
	private int IDclient;
	private int punteggio=0;
	
	private static ArrayList<Integer> punteggiGiocatori = new ArrayList<>(); //lista per memorizzare i punteggi di tutti i giocatori
	
	public thread_client(num_client clients, Socket link, Mazzo mazzo) {
		this.clients = clients;
		this.link = link;
		this.mazzo = mazzo;
	}

	@Override
	public void run() {
			
		IDclient = clients.getCount();
        try {
			in = new BufferedReader(new InputStreamReader(link.getInputStream()));
			out = new PrintWriter(link.getOutputStream(), true);
			System.out.println("Client" + IDclient+ " inizio comunicazione...");
			if(clients.getCount() == 1) {
				//manda richiesta numero giocatori
				String richiesta = "1_giocatore";
				out.println(richiesta);
				//riceve risposta numero giocatori
				String response = in.readLine();
	            int maxPlayers = Integer.parseInt(response);
	            
	            
	            clients.setMaxPlayers(maxPlayers);
	            // Inizializza la lista punteggiGiocatori
	            for (int i = 0; i < maxPlayers; i++) {
	                punteggiGiocatori.add(0); // Aggiungi un punteggio iniziale di 0 per ogni giocatore
	            }
	        }else {
	        	String richiesta = "no_primo";
				out.println(richiesta);
	        }
			
			
			while(clients.getCount() < clients.getMaxPlayers()) {
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Tutti i giocatori si sono connessi! il gioco può iniziare");
			
			// il server manda le carta ai client
			
			 synchronized (mazzo) { // Protezione del mazzo per thread multipli
				 if (mazzo.isMazzoVuoto()) {
					 out.println("Errore: non ci sono più carte nel mazzo.");
	             	return;
				 }	
	                
				 carta carta = mazzo.getCarta();
				 Gson gson = new Gson();
				 String cartaJson = gson.toJson(carta); // Serializza la carta in JSON

				 // Invio della carta al client
				 out.println(cartaJson);
				 System.out.println(IDclient  + " carta: " + carta.toString());
				 punteggio += carta.getValore();
				 punteggiGiocatori.set(IDclient - 1, punteggio); 
			 }        
			 
			 while(!clients.getStato()) {
				 if (clients.getTurn() == IDclient) {
					 out.println("TUO_TURNO");
					 
					 String risposta = in.readLine();
					 if(risposta.equals("CARTA")) {
						 
						 carta carta = mazzo.getCarta();
						 Gson gson = new Gson();
						 String cartaJson = gson.toJson(carta); // Serializza la carta in JSON
						 out.println(cartaJson);
						 
						 punteggio += carta.getValore();
						 punteggiGiocatori.set(IDclient-1, punteggio);
						 if(punteggio > 7.5) {
							 out.println("SBALLATO");
							 punteggio = 0;
							 clients.incFinishPlayer();
							 clients.nextTurn();
						 }else {
							 out.println("VIVO");
						 }
						 punteggiGiocatori.set(IDclient-1, punteggio);
						 
					 }else if(risposta.equals("STO")){
						 clients.incFinishPlayer();
						 clients.nextTurn();
					 }
				 }else {
					 //out.println("ATTESA");
					 
				 }
				 if(clients.getFinishPlayer() == clients.getMaxPlayers()) {
					 	
					    int indiceVincitore = determinaVincitore();
					    if (indiceVincitore != -1) {
					    	if(indiceVincitore+1 == IDclient) {
					    		out.println("VINTO");
					    	}else {
					    		out.println("PERSO");
					    	}
					    }else {
					    	out.println("PAREGGIO");
					    }
					    clients.giocoFinito();
				 }
				
			 }
	            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("GIOCO TERMINATO");
        	
	}
	public static int determinaVincitore() {
        double punteggioMax = -1;
        int indiceVincitore = -1;

        // Trova il punteggio massimo tra i giocatori che non sono sballati
        for (int i = 0; i < punteggiGiocatori.size(); i++) {
            if (punteggiGiocatori.get(i) <= 7.5 && punteggiGiocatori.get(i) > punteggioMax) {
                punteggioMax = punteggiGiocatori.get(i);
                indiceVincitore = i;
            }
        }
        
        return indiceVincitore;
	}

}
