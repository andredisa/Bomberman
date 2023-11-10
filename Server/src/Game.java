import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Giocatore> players;

    public Game() {
        this.players = new ArrayList<>();
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

}