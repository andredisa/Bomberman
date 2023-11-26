import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 5000;

    private static Socket socket;
    private static DataInputStream dis;
    private static DataOutputStream dos;

    public static void connect() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("Connected to the server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendCoordinates(int x, int y, boolean bombPlaced) {
        try {
            dos.writeUTF(x + ";" + y + ";" + bombPlaced);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Messaggio receiveMessage() {
        try {
            socket.setSoTimeout(2000);

            // Verifica se ci sono dati disponibili prima di leggere
            if (dis.available() > 0) {
                int dataSize = dis.readInt();
                byte[] data = new byte[dataSize];
                dis.readFully(data);

                try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
                    Messaggio message = (Messaggio) ois.readObject();
                    System.out.println("Messaggio ricevuto: " + message.getTipo());
                    return message;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketTimeoutException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.setSoTimeout(0);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
