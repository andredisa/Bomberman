import java.util.List;

public class ThreadBomba extends Thread {
    private Bomba bomba;
    private Game game;
    private GestioneBlocchi gestioneBlocchi;

    public ThreadBomba(Bomba bomba, Game game) {
        this.bomba = bomba;
        this.game = game;
        this.gestioneBlocchi = gestioneBlocchi;
    }

    public void run() {
        try {
            // Dormi per il tempo di esplosione della bomba
            Thread.sleep(2000);

            // Verifica l'esplosione
            bomba.explode(game.getPlayers(), gestioneBlocchi);

            // Rimuovi la bomba dal gioco dopo l'esplosione
            game.removeBomba(bomba);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}