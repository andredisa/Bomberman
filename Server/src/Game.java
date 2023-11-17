import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Giocatore> players;
    private List<Bomba> bombe;

    public Game() {
        this.players = new ArrayList<>();
        this.bombe = new ArrayList<>();
    }

    public int size(){
        return this.players.size();
    }

    public void add(Giocatore player) {
        this.players.add(player);
    }

    public void removePlayer(Giocatore player) {
        this.players.remove(player);
    }

    public void movePlayer(Giocatore player, int newX, int newY) {
        player.setPosX(newX);
        player.setPosY(newY);
    }
    public List<Giocatore> getPlayers() {
        return this.players;
    }
    public void removeBomba(Bomba bomba) {
        this.bombe.remove(bomba);
    }
    public void addBomba(Bomba bomba) {
        this.bombe.add(bomba);
    }

}