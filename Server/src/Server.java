import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
 
    public static void main(String[] args) {
        Game gm = new Game();
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server avviato. In attesa di connessioni...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connessione accettata da: " + socket);

                int playerId = gm.size() + 1; // Genera un nuovo ID per il giocatore
                Giocatore player = new Giocatore(1, 1 , playerId); // Crea un nuovo giocatore
                gm.add( player); // Aggiungi il giocatore alla mappa dei giocatori

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(player);
                oos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}