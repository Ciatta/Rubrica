public class Persona {
    
    private final String id;
    private String nome;
    private String cognome;
    private String indirizzo;
    private String telefono;
    private Integer eta;

    public Persona(String id, String nome, String cognome, String indirizzo, String telefono, int eta) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = eta;
    }
    public Persona(String id, String nome, String cognome, String indirizzo, String telefono, String eta) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = Integer.valueOf(eta);
    }

    public String getId(){
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getEta() {
        return String.valueOf(eta);
    }

    public void setNome(String nome) {
    this.nome = nome;
    }
    public void setCognome(String cognome) {
    this.cognome = cognome;
    }
    public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
    }
     public void setTelefono(String telefono) {
    this.telefono = telefono;
    }
    public void setEta(String eta) {
    this.eta = Integer.valueOf(eta);
    }
    public void setEta(int eta) {
    this.eta = eta;
    }


    @Override
    public String toString() {
        return nome + " " + cognome + " - " + telefono;
    }
}