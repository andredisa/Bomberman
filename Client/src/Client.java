import java.io.*;
import java.net.Socket;

public class Client {
    public static void connect() {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connesso al server.");

            // Ricevi il giocatore dal server
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Giocatore player = (Giocatore) ois.readObject();
            System.out.println("Ricevuto il giocatore: " + player.toString());

            // Chiudi la connessione
            ois.close();
            //oos.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
