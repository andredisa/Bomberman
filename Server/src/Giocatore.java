import java.net.Socket;

public class Giocatore {
    private int posX;
    private int posY;
    private int id;

    private boolean bombaPiazzata;
    private boolean morto;
    private boolean inGioco;
    private int numVite;
    private int numBombe;

    private Socket socket;

    public Giocatore(int posX, int posY, int playerId, Socket socket) {
        this.posX = posX;
        this.posY = posY;
        this.id = playerId;
        this.bombaPiazzata = false;
        this.morto = false;
        this.inGioco = false;
        this.numVite = 0;
        this.numBombe = 0;
        this.socket = socket;
    }

    public void togliVita() {
        if (!this.morto) {
            this.numVite--;
            if (this.numVite <= 0) {
                this.morto = true;
            }
        }
    }

    public String toString() {
        return this.posX + ";" + this.posY + ";" + this.bombaPiazzata;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBombaPiazzata() {
        return bombaPiazzata;
    }

    public void setBombaPiazzata(boolean bombaPiazzata) {
        this.bombaPiazzata = bombaPiazzata;
    }

    public boolean isMorto() {
        return morto;
    }

    public void setMorto(boolean morto) {
        this.morto = morto;
    }

    public boolean isInGioco() {
        return inGioco;
    }

    public void setInGioco(boolean inGioco) {
        this.inGioco = inGioco;
    }

    public int getNumVite() {
        return numVite;
    }

    public void setNumVite(int numVite) {
        this.numVite = numVite;
    }

    public int getNumBombe() {
        return numBombe;
    }

    public void setNumBombe(int numBombe) {
        this.numBombe = numBombe;
    }
}