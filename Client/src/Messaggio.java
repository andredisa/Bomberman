import java.util.ArrayList;
import java.util.List;

public class Messaggio {
    private String tipo;
    private List<String> dati;


    public Messaggio() {
        this.tipo = "";
        this.dati = new ArrayList<>();
    }

    public Messaggio(String tipo) {
        this.tipo = tipo;
        this.dati = new ArrayList<>();
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

}