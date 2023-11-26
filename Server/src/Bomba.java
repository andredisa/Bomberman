import java.util.ArrayList;
import java.util.List;

public class Bomba {
    private int x;
    private int y;
    private int power;
    private Game game;
    private String image;

    public Bomba(int x, int y, int power, Game game) {
        this.x = x;
        this.y = y;
        this.power = power;
        this.game = game;
        this.image = "img/bomb.png";
    }

    public List<String> explode(List<Giocatore> giocatori, GestioneBlocchi gestioneBlocchi) {
        List<String> blocchiDistrutti = new ArrayList<>();

        try {
            Thread.sleep(1000);

            List<Giocatore> playersInRange = getPlayersInRange(giocatori);
            for (Giocatore g : playersInRange) {
                g.togliVita();
                if (g.isMorto()) {
                    game.removePlayer(g);
                }
            }
            blocchiDistrutti.addAll(removeDistruttibiliInRange(gestioneBlocchi));
            ServerSender.inviaBlocchiEsplosione(game, blocchiDistrutti);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return blocchiDistrutti;
    }

    private List<String> removeDistruttibiliInRange(GestioneBlocchi gestioneBlocchi) {
        List<String> distruttibiliInRange = new ArrayList<>();

        for (int i = x - power; i <= x + power; i++) {
            for (int j = y - power; j <= y + power; j++) {
                if (isInRange(i, j, x, y, power) && gestioneBlocchi.isBloccoDistruttibile(i, j)) {
                    distruttibiliInRange.add(i + ";" + j);
                }
            }
        }

        gestioneBlocchi.removeBlocchiDistruttibili(distruttibiliInRange);

        return distruttibiliInRange;
    }

    private boolean isInRange(int x, int y, int bombX, int bombY, int power) {
        return Math.abs(x - bombX) + Math.abs(y - bombY) <= power;
    }

    private List<Giocatore> getPlayersInRange(List<Giocatore> giocatori) {
        List<Giocatore> playersInRange = new ArrayList<>();
        for (Giocatore g : giocatori) {
            if (isPlayerInRange(g)) {
                playersInRange.add(g);
            }
        }
        return playersInRange;
    }

    private boolean isPlayerInRange(Giocatore g) {
        return (Math.abs(g.getPosX() - this.x) <= this.power && g.getPosY() == this.y) ||
                (Math.abs(g.getPosY() - this.y) <= this.power && g.getPosX() == this.x);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String toString() {
        return this.x + ";" + this.y + ";" + this.image;
    }
}