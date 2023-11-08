import java.util.ArrayList;
import java.util.List;

public class Bomba {
    private int x;
    private int y;
    private int power;

    public Bomba(int x, int y, int power) {
        this.x = x;
        this.y = y;
        this.power = power;
    }

    public void explode(List<Giocatore> giocatori) {
        try {
            Thread.sleep(2000);

            List<Giocatore> playersInRange = getPlayersInRange(giocatori);
            for (Giocatore g : playersInRange) {
                g.togliVita();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        return (g.getPosX() == this.x && Math.abs(g.getPosY() - this.y) <= this.power) ||
               (g.getPosY() == this.y && Math.abs(g.getPosX() - this.x) <= this.power);
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
}