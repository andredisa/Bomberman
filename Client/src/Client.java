import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Client {
    private static Socket socket;
    private static DataInputStream dis;
    private static DataOutputStream dos;

    public static void connect() {
        try {
            //ip = 172.16.102.115
            socket = new Socket("localhost", 5000);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("Connected to the server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inviaCoordinate(int x, int y, boolean bombaPiazzata) {
        try {
            dos.writeUTF(x + ";" + y + ";" + bombaPiazzata);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Messaggio riceviMessaggio() throws SocketException {
        try {
            socket.setSoTimeout(1000);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Messaggio messaggio = (Messaggio) ois.readObject();

            System.out.println("messaggio ricevuto: " + messaggio.getTipo());
            return messaggio;
        } catch (SocketTimeoutException e) {
            System.out.println("Nessun messaggio disponibile al momento.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            socket.setSoTimeout(0);
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