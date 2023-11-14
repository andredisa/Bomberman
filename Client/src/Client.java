import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket socket;

    public static void connect() {
        try {
            socket = new Socket("localhost", 5000);
            System.out.println("Connesso al server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inviaCoordinate(int x, int y) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(x + ";" + y);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
