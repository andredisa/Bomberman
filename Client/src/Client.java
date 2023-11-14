import java.io.DataOutputStream;
import java.io.IOException;
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

    public static void inviaCoordinate(int x, int y, boolean b) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(x + ";" + y + ";" + b);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
