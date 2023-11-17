import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ThreadGiocatore extends Thread {
    private Giocatore player;
    private Game game;

    private DataInputStream dis;
    private DataOutputStream dos;
    private GestioneBlocchi gb;

    public ThreadGiocatore(Giocatore player, Game game, GestioneBlocchi gb) {
        this.player = player;
        this.game = game;
        this.gb = gb;
        try {
            dis = new DataInputStream(this.player.getSocket().getInputStream());
            dos = new DataOutputStream(this.player.getSocket().getOutputStream());

            inviaBlocchiFissi();
            inviaBlocchiDistruttibili();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                inviaBlocchiDistruttibili();

                /*String command = dis.readUTF();
                System.out.println("Coordinate ricevute: " + command);

                String[] coordinates = command.split(";");
                int newX = Integer.parseInt(coordinates[0]);
                int newY = Integer.parseInt(coordinates[1]);
                boolean b = Boolean.parseBoolean(coordinates[2]);

                // aggiorna la posizione del giocatore
                game.movePlayer(player, newX, newY);*/

                // controllo bomba

                // inviaPosizioniAiClient();
            }
        } finally {
            game.removePlayer(player);
            try {
                player.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void inviaBlocchiFissi() {
        try {
            System.out.println("Invio dati blocchi fissi");
            Messaggio m = new Messaggio("blocchiFissi");

            for (BloccoFisso b : gb.getBlockMap()) {
                m.aggiungiDato(b.toString());
            }

            ObjectOutputStream oos = new ObjectOutputStream(dos);
            oos.writeObject(m);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inviaBlocchiDistruttibili() {
        try {
            System.out.println("Invio dati blocchi distruttibili");
            Messaggio m = new Messaggio("blocchiDistruttibili");

            for (BloccoDistruttibile b : gb.getWoodBlock()) {
                m.aggiungiDato(b.toString());
            }

            ObjectOutputStream oos = new ObjectOutputStream(dos);
            oos.writeObject(m);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inviaPosizioniAiClient() {
        try {
            Messaggio m = new Messaggio("datiGiocatori");

            for (Giocatore player : game.getPlayers()) {
                m.aggiungiDato(player.toString());
            }

            ObjectOutputStream oos = new ObjectOutputStream(dos);
            oos.writeObject(m);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}