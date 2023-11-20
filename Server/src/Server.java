import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int WIDTH = 17;
    public static final int HEIGHT = 13;
    public static final int BLOCK_SIZE = 40;
    public static final int NUM_PLAYERS = 2;

    public static void main(String[] args) {
        Game gm = new Game();
        ServerSocket serverSocket = null;
        int connectedPlayers = 0;

        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server avviato. In attesa di connessioni...");
            GestioneBlocchi gb = null;

            while (connectedPlayers < NUM_PLAYERS) {
                Socket socket = serverSocket.accept();
                System.out.println("Connessione accettata da: " + socket);

                int playerId = gm.size() + 1;
                Giocatore player;

                if (playerId == 1) {
                    // primo giocatore
                    player = new Giocatore(1, 1, playerId, socket);
                } else {
                    // secondo giocatore
                    player = new Giocatore(15, 11, playerId, socket);
                }

                gm.add(player);
                connectedPlayers++;

                if (connectedPlayers == NUM_PLAYERS) {
                    gb = new GestioneBlocchi(gm.getPlayers());

                    ThreadGiocatore thPlayer = new ThreadGiocatore(gm.getPlayers().get(0), gm, gb);
                    ThreadGiocatore thPlayer2 = new ThreadGiocatore(gm.getPlayers().get(1), gm, gb);
                    thPlayer.start();
                    thPlayer2.start();
                    inviaPosizioniAiClient(gm, socket);
                }

            }

            System.out.println("Gioco iniziato");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void inviaPosizioniAiClient(Game gm, Socket socket) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            System.out.println("Inviato dati giocatori n giocatori: " + gm.getPlayers().size());
            Messaggio m = new Messaggio("datiGiocatori");

            for (Giocatore p : gm.getPlayers()) {
                m.aggiungiDato(p.toString());
            }

            ObjectOutputStream oos = new ObjectOutputStream(dos);
            oos.writeObject(m);
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}