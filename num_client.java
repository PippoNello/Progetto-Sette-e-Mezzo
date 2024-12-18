package setteemezzo;

public class num_client {
	private int count = 0;
    private int maxPlayers = 0;
    private int currentTurn = 1;
    private int finishPlayer = 0;
    private boolean giocoTerminato = false;
    

    public synchronized void inc() {
        count++;
        System.out.println(count);
    }

    public synchronized int getCount() {
        return count;
    }

    public synchronized boolean IfPlayer() {
        return maxPlayers > 0;
    }

	public synchronized void setMaxPlayers(int maxPlayers) {
        if (this.maxPlayers == 0) { // Solo il primo client può impostarlo
            this.maxPlayers = maxPlayers;
        }
    }
	
	 public synchronized int getTurn() {
		 return currentTurn;
	 }

	public synchronized void nextTurn() {
		currentTurn = currentTurn + 1;
	
	}
    public synchronized int getMaxPlayers() {
        return maxPlayers;
    }
    
    public synchronized int incFinishPlayer() {
    	return finishPlayer++;
    }
    public synchronized int getFinishPlayer() {
    	return finishPlayer;
    }
    
    public synchronized boolean getStato() {
    	return giocoTerminato;
    }
    
    public synchronized void giocoFinito() {
    	giocoTerminato = true;
    }
    
    
}
