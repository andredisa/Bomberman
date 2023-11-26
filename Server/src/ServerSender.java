import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class ServerSender {
    private static List<Giocatore> giocatori;

    public static void setGiocatori(List<Giocatore> giocatori) {
        ServerSender.giocatori = giocatori;
    }

    private static void inviaDati(String tipoMessaggio, List<String> dati, int id) {
        try {
            System.out.println("Invio dati " + tipoMessaggio);

            for (Giocatore giocatore : giocatori) {
                Socket socket = giocatore.getSocket();
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);

                Messaggio m = new Messaggio(tipoMessaggio);
                m.setDati(dati);
                m.setId(id); 

                oos.writeObject(m);
                oos.flush();

                byte[] data = baos.toByteArray();
                int dataSize = data.length;

                dos.writeInt(dataSize); // Invia la lunghezza dei dati
                dos.write(data); // Invia i dati effettivi
                dos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inviaBlocchiFissi(GestioneBlocchi gb) {
        inviaDati("blocchiFissi", GestioneBlocchi.blockMap_toString(gb.getBlockMap()), 0);
    }

    public static void inviaBlocchiDistruttibili(GestioneBlocchi gb) {
        inviaDati("blocchiDistruttibili", GestioneBlocchi.woodBlock_toString(gb.getWoodBlock()), 0);
    }

    public static void inviaPosizioniGiocatori(Game game) {
        inviaDati("datiGiocatori", Game.giocatoriToString(game.getPlayers()), 0);
    }

    public static void inviaPosizioneBomba(Game game) {
        inviaDati("datiBomba", Game.bombeToString(game.getBombs()), 0);
    }

    public static void inviaBlocchiEsplosione(Game game, List<String> blocchi) {
        inviaDati("datiEsplosione", blocchi, 0);
    }

    public static void inviaFineGioco(int idVincitore) {
        inviaDati("fineGioco", new ArrayList<>(), idVincitore);
    }
}