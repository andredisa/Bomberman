import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class ServerSender {
    private static List<Giocatore> giocatori;

    public static void setGiocatori(List<Giocatore> giocatori) {
        ServerSender.giocatori = giocatori;
    }

    public static void inviaBlocchiFissi(GestioneBlocchi gb) {
        try {
            System.out.println("Invio dati blocchi fissi");

            for (int i = 0; i < giocatori.size(); i++) {
                DataOutputStream dos = new DataOutputStream(giocatori.get(i).getSocket().getOutputStream());
                Messaggio m = new Messaggio("blocchiFissi");

                for (BloccoFisso b : gb.getBlockMap()) {
                    m.aggiungiDato(b.toString());
                }

                ObjectOutputStream oos = new ObjectOutputStream(dos);
                oos.writeObject(m);
                oos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inviaBlocchiDistruttibili(GestioneBlocchi gb) {
        try {
            System.out.println("Invio dati blocchi distruttibili");

            for (int i = 0; i < giocatori.size(); i++) {
                DataOutputStream dos = new DataOutputStream(giocatori.get(i).getSocket().getOutputStream());
                Messaggio m = new Messaggio("blocchiDistruttibili");

                for (BloccoDistruttibile b : gb.getWoodBlock()) {
                    m.aggiungiDato(b.toString());
                }

                ObjectOutputStream oos = new ObjectOutputStream(dos);
                oos.writeObject(m);
                oos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inviaPosizioniGiocatori(Game game) {
        try {
            System.out.println("Invio dati giocatori");

            for (int i = 0; i < giocatori.size(); i++) {
                DataOutputStream dos = new DataOutputStream(giocatori.get(i).getSocket().getOutputStream());
                Messaggio m = new Messaggio("datiGiocatori");

                for (Giocatore g : game.getPlayers()) {
                    m.aggiungiDato(g.toString());
                }

                ObjectOutputStream oos = new ObjectOutputStream(dos);
                oos.writeObject(m);
                oos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
