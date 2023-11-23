import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class ServerSender {
    private static List<Giocatore> giocatori;

    public static void setGiocatori(List<Giocatore> giocatori) {
        ServerSender.giocatori = giocatori;
    }

    private static void inviaDati(List<Giocatore> giocatori, String tipoMessaggio, List<String> dati) {
        try {
            System.out.println("Invio dati " + tipoMessaggio);

            for (Giocatore giocatore : giocatori) {
                DataOutputStream dos = new DataOutputStream(giocatore.getSocket().getOutputStream());
                Messaggio m = new Messaggio(tipoMessaggio);

                m.setDati(dati);

                ObjectOutputStream oos = new ObjectOutputStream(dos);
                oos.writeObject(m);
                oos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inviaBlocchiFissi(GestioneBlocchi gb) {
        inviaDati(giocatori, "blocchiFissi", GestioneBlocchi.blockMap_toString(gb.getBlockMap()));
    }

    public static void inviaBlocchiDistruttibili(GestioneBlocchi gb) {
        inviaDati(giocatori, "blocchiDistruttibili", GestioneBlocchi.woodBlock_toString(gb.getWoodBlock()));
    }

    public static void inviaPosizioniGiocatori(Game game) {
        inviaDati(giocatori, "datiGiocatori", Game.giocatoriToString(game.getPlayers()));
    }

    public static void inviaPosizioneBomba(Game game) {
        inviaDati(giocatori, "datiBomba", Game.bombeToString(game.getBombs()));
    }

    public static void inviaBlocchiEsplosione(Game game, List<String> blocchi) {
        inviaDati(giocatori, "datiEsplosione", blocchi);
    }
}
