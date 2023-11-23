import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ThreadGiocatore extends Thread {
    private Giocatore player;
    private Game game;
    private DataInputStream dis;
    private GestioneBlocchi gb;

    public ThreadGiocatore(Giocatore player, Game game, GestioneBlocchi gb) {
        this.player = player;
        this.game = game;
        this.gb = gb;

        try {
            dis = new DataInputStream(player.getSocket().getInputStream());
            ServerSender.inviaBlocchiFissi(gb);
            ServerSender.inviaBlocchiDistruttibili(gb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                String command = dis.readUTF();
                System.out.println("Coordinate ricevute: " + command);

                String[] coordinates = command.split(";");
                int newX = Integer.parseInt(coordinates[0]);
                int newY = Integer.parseInt(coordinates[1]);
                boolean piazzaBomba = Boolean.parseBoolean(coordinates[2]);

                if (!(gb.isBloccoDistruttibile(newX, newY) || gb.isBloccoFisso(newX, newY))) {
                    // aggiorna la posizione del giocatore
                    game.movePlayer(player, newX, newY);
                }

                if (piazzaBomba) {
                    player.setBombaPiazzata(piazzaBomba);
                    Bomba bomba = new Bomba(player.getPosX(), player.getPosY(), 1, game);
                    game.addBomba(bomba);

                    ThreadBomba thBomba = new ThreadBomba(bomba, game, gb);
                    thBomba.start();
                }
                ServerSender.inviaPosizioniGiocatori(game);
                player.setBombaPiazzata(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            game.removePlayer(player);
            try {
                player.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}