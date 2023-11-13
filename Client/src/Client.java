import java.io.*;
import java.net.Socket;

public class Client {
    public static void connect() {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connesso al server.");

            // Ricevi il giocatore dal server
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
          

            // Chiudi la connessione
            ois.close();
            //oos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
