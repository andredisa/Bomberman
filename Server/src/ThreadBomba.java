import java.util.List;

public class ThreadBomba extends Thread {
    private Bomba bomba;
    private Game game;
    private GestioneBlocchi gestioneBlocchi;

    public ThreadBomba(Bomba bomba, Game game, GestioneBlocchi gestioneBlocchi) {
        this.bomba = bomba;
        this.game = game;
        this.gestioneBlocchi = gestioneBlocchi;
    }

    public void run() {
        try {
            Thread.sleep(100);

            bomba.explode(game.getPlayers(), gestioneBlocchi);

            game.removeBomba(bomba);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}