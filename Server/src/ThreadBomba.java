import java.util.ArrayList;
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
            ServerSender.inviaPosizioneBomba(game);
            Thread.sleep(5000);

            bomba.explode(game.getPlayers(), gestioneBlocchi);

            game.removeBomba(bomba);

            ServerSender.inviaBlocchiDistruttibili(gestioneBlocchi);
            ServerSender.inviaPosizioneBomba(new Game());
            Thread.sleep(2000);
            ServerSender.inviaBlocchiEsplosione(new Game(), new ArrayList<>());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}