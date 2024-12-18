package setteemezzo;

import java.util.Random;

public class Mazzo {
	private carta[] mazzo;
	Random random = new Random();
	
	public Mazzo() {
        String[] semi = {"\u2660", "\u2665", "\u2663", "\u2666"};
        String[] id = {"1", "2", "3", "4", "5", "6", "7", "Q", "J", "K"};
        double[] valori = {1, 2, 3, 4, 5, 6, 7, 0.5, 0.5, 0.5};
        
        mazzo = new carta[40];
        int index = 0;
        
        for (String seme : semi) {
            for (int i = 0; i < id.length; i++) {
                boolean matta = (seme.equals("Cuori") && id[i].equals("Donna")); // Assegna la matta
                mazzo[index++] = new carta(seme, id[i], valori[i], matta);
            }
        }
        
	}
	
	public carta getCarta() {
		int num = random.nextInt(40);
		carta carta_uscita = mazzo[num];
		
		while(carta_uscita.getEstratto() == true) {
			num = random.nextInt(40);
			carta_uscita = mazzo[num];
		}
		
		carta_uscita.setEstratto(true);
		
		return carta_uscita;
	}
	
	public boolean isMazzoVuoto() {
        for (carta c : mazzo) {
            if (!c.getEstratto()) {
                return false; // Se trova una carta non estratta, il mazzo non è vuoto
            }
        }
        return true; // Tutte le carte sono state estratte
    }
}
