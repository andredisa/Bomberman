import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Messaggio implements Serializable {
    private String tipo;
    private List<String> dati;
    private int idGiocatore;

    public Messaggio(String tipo) {
        this.tipo = tipo;
        this.dati = new ArrayList<>();
        this.idGiocatore = 0;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void aggiungiDato(String d) {
        this.dati.add(d);
    }

    public List<String> getDati() {
        return dati;
    }

    public void setDati(List<String> dati) {
        this.dati = dati;
    }

    public int getIdGiocatore() {
        return idGiocatore;
    }

    public void setIdGiocatore(int idGiocatore) {
        this.idGiocatore = idGiocatore;
    }

}