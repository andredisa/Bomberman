import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int WIDTH = 17;
    public static final int HEIGTH = 13;
    public static final int BLOCK_SIZE = 40;

    public static void main(String[] args) {
        Game gm = new Game();

        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server avviato. In attesa di connessioni...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connessione accettata da: " + socket);

                // id=1 x=1 y=1
                // id=2 x=15 y=11
                int playerId = gm.size() + 1;
                Giocatore player = new Giocatore(1, 1, playerId, socket);
                gm.add(player);

                // thread per gestire il giocatore
                ThreadGiocatore thPlayer = new ThreadGiocatore(player, gm);
                thPlayer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}