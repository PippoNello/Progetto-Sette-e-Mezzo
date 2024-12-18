package setteemezzo;

public class carta {
    private String seme;    // Picche, Cuori, Fiori, Denari
	private String id;      // Identificativo (1, 2, ..., Donna, Gobbo, Re)
    private double valore;  // Valore per 7 e mezzo (0.5, 1, ..., 7)
    private boolean matta;  // True se la carta è la matta (Donna di Cuori)
    private boolean estratto;
	
    public carta(String seme, String id, double valore, boolean matta) {
		this.seme = seme;
		this.id = id;
		this.valore = valore;
		this.matta = matta;
		estratto = false;
		
	}

	public String getSeme() {
		return seme;
	}

	public String getId() {
		return id;
	}

	public double getValore() {
		return valore;
	}

	public boolean isMatta() {
		return matta;
	}
	
	public boolean getEstratto(){
		return estratto;
	}
	
	public void setEstratto(boolean estratto) {
		this.estratto = true;
	}
    
	// Valore di 7 e mezzo, con regole speciali per la matta
    public double getValoreSetteMezzo() {
        if (matta) {
            return -1; // Indica che il valore della matta deve essere scelto dal giocatore
        }
        return valore;
    }
    
    @Override
    public String toString() {
        return	id + seme;
    }

	
}
