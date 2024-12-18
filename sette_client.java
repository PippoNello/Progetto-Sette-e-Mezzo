package sette_client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;

import setteemezzo.carta;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;


public class sette_client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		String serverAddress = "127.0.0.1";
		int port = 1237;
		Socket link = null;
		int i = 0;
		boolean check = true;
		boolean fine = false;
		
		while( link == null && i < 3 ) {
			try {
				// Crea una connessione al server
				link = new Socket(serverAddress, port);
			}catch(IOException ex) {
				ex.printStackTrace();
		
			}
			i++;
		}
		
		if( link == null ) {
			System.out.println("Impossibile collegarsi al server");
			return;
		}
		
		try {			
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);
            System.out.println("Connessione stabilita con il server.");
           /*
            String jsonRequets =in.readLine();
            Gson gson = new Gson();
            RichiestaGiocatori richiesta = gson.fromJson(jsonRequets,RichiestaGiocatori.class);
           */
            
            String richiesta = in.readLine();
            System.out.println(richiesta);
            if (richiesta.equals("1_giocatore")) {
	            System.out.println("Inserisci il numero di giocatori (2-4):");
	            
	            int num_players = scanner.nextInt();
	            while(num_players < 1 || num_players > 4) {
	            	 System.out.println("Numero non valido, inserisci un numero da 2 a 4");
	            	 num_players = scanner.nextInt();
	            }
	            
	            out.println(num_players);
	        }
            
            //ricezione e lettura prima carta;
            String jsonRequest =in.readLine();
            Gson gson = new Gson();
            carta carta_ricevuta = gson.fromJson(jsonRequest,carta.class);
           
            System.out.println("carta ricevuta: " + carta_ricevuta.toString());
            
       
            
           // Ciclo principale del gioco
            while(!fine) {
            	String avviso = in.readLine();
	            if(avviso.equals("TUO_TURNO")) {
	            	System.out.println("mio turno");
	            	
	                System.out.println("1. Chiedi una carta");
	                System.out.println("2. Passa il turno");
	                int scelta = scanner.nextInt();
	                
	                if (scelta == 1) {
	                	out.println("CARTA");
	                	  jsonRequest =in.readLine();
	                      gson = new Gson();
	                      carta_ricevuta = gson.fromJson(jsonRequest,carta.class);
	                      System.out.println("carta ricevuta: " + carta_ricevuta.toString());
	                      
	                      String stato = in.readLine();//vede se ho sballato
	                      System.out.println(stato);	
	                      if(stato.equals("SBALLATO")) {
	                    	  fine = true;
	                      }
	                }else {
	                	out.println("STO");
	                	fine = true;
	                }
	            }else {
	            	
	            }
            }
            String risultato = in.readLine();
            System.out.println(risultato);
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
