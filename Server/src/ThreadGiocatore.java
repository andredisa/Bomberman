import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

class ThreadGiocatore extends Thread {
    private Giocatore player;
    private Socket socket;
    private Game game;

    public ThreadGiocatore(Giocatore player, Socket socket, Game game) {
        this.player = player;
        this.socket = socket;
        this.game = game;
    }

    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            while (true) {
                String command = dis.readUTF();
                System.out.println("Coordinate ricevute: " + command);

                // gestisci l'input del giocatore
                String[] coordinates = command.split(";");
                int newX = Integer.parseInt(coordinates[0]);
                int newY = Integer.parseInt(coordinates[1]);
                boolean b = Boolean.parseBoolean(coordinates[2]);

                // Aggiorna la posizione del giocatore
                game.movePlayer(player, newX, newY);

                // controlli bomba

                // aggiorna tutti i client delle posizioni degli altri giocatori

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Quando il giocatore si disconnette, rimuovilo dal gioco
            game.removePlayer(player);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}