import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int WIDTH = 17;
    public static final int HEIGHT = 13;
    public static final int BLOCK_SIZE = 40;

    public static void main(String[] args) {
        Game gm = new Game();
        GestioneBlocchi gb = new GestioneBlocchi();

        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server avviato. In attesa di connessioni...");

            for (int i = 0; i < 2; i++) {
                Socket socket = serverSocket.accept();
                System.out.println("Connessione accettata da: " + socket);

                int playerId = gm.size() + 1;
                Giocatore player;

                if (playerId == 1) {
                    player = new Giocatore(1, 1, playerId, socket);
                } else {
                    player = new Giocatore(15, 11, playerId, socket);
                }

                gm.add(player);
                inviaPosizioniAiClient(gm, socket);
                ThreadGiocatore thPlayer = new ThreadGiocatore(player, gm, gb);
                thPlayer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void inviaPosizioniAiClient(Game gm, Socket socket) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            Messaggio m = new Messaggio("datiGiocatori");

            for (Giocatore player : gm.getPlayers()) {
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
