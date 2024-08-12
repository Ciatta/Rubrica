import java.util.ArrayList;
import java.util.List;

public class Rubrica {
    private final List<Persona> persone;
    private final List<Utente> utenti;

    public Rubrica() {
        this.persone = new ArrayList<>();
        this.utenti = new ArrayList<>();
    }

    public void aggiungiPersona(Persona persona) {
        persone.add(persona);
    }

    public void rimuoviPersona(Persona persona) {
        persone.remove(persona);
    }

    public List<Persona> getPersone() {
        return new ArrayList<>(persone);
    }

    public void aggiungiUtente(Utente utente) {
        utenti.add(utente);
    }

    public void rimuoviUtente(Utente utente) {
        utenti.remove(utente);
    }

    public List<Utente> getUtenti() {
        return new ArrayList<>(utenti);
    }

    
}
