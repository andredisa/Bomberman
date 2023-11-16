import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadGiocatore extends Thread {
    private Giocatore player;
    private Game game;

    private DataInputStream dis;
    private DataOutputStream dos;

    public ThreadGiocatore(Giocatore player, Game game) {
        this.player = player;
        this.game = game;
        try {
            dis = new DataInputStream(this.player.getSocket().getInputStream());
            dos = new DataOutputStream(this.player.getSocket().getOutputStream());
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
                boolean b = Boolean.parseBoolean(coordinates[2]);

                // aggiorna la posizione del giocatore
                game.movePlayer(player, newX, newY);

                inviaPosizioniAiClient();
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

    public void inviaPosizioniAiClient() {
        try {
            Messaggio m = new Messaggio("datiGiocatori");

            for (Giocatore player : game.getPlayers()) {
                m.aggiungiDato(player.toString());
            }

            ObjectOutputStream oos = new ObjectOutputStream(dos);
            oos.writeObject(m);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}