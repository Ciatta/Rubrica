import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.UUID;

public class PersistenzaRubrica {

    public static Rubrica caricaDaFile(String nomeFile) throws IOException {
        Rubrica rubrica = new Rubrica();
        File file_persone = new File(nomeFile+"_persone.txt");
        if (file_persone.exists()) {
            try (Scanner scanner = new Scanner(file_persone)) {
                while (scanner.hasNextLine()) {
                    String linea = scanner.nextLine();
                    String[] campi = linea.split(";");  // Supponiamo che i campi siano separati da punto e virgola
                    if (campi.length == 5 ) {
                        String nome = campi[0];
                        String cognome = campi[1];
                        String indirizzo = campi[2];
                        String numero = campi[3];
                        String eta = campi[4];
                        UUID uuid = UUID.randomUUID();
                        String id = uuid.toString();
                        Persona persona = new Persona(id, nome, cognome, indirizzo, numero, eta);
                        rubrica.aggiungiPersona(persona);
                    }
                }
            }
        }
        File file_utenti = new File(nomeFile+"_utenti.txt");
        if (file_utenti.exists()) {
            try (Scanner scanner = new Scanner(file_utenti)) {
                while (scanner.hasNextLine()) {
                    String linea = scanner.nextLine();
                    String[] campi = linea.split(";");  // Supponiamo che i campi siano separati da punto e virgola
                    if (campi.length == 2 ) {
                        String username = campi[0];
                        String password = campi[1];
                        Utente utente = new Utente(username, password);
                        rubrica.aggiungiUtente(utente);
                    }
                }
            }
        }


        return rubrica;
    }

    public static Rubrica caricaDaCartella(String nomeCartella) throws IOException {
        Rubrica rubrica = new Rubrica();
        File cartella = new File(nomeCartella);
        if (!cartella.exists() && !cartella.isDirectory()) {
            return rubrica;  // Ritorna una rubrica vuota se il file non esiste
        }
        File[] files_persone = cartella.listFiles((dir, name) -> name.startsWith("persona_") && name.endsWith(".txt"));
        if (files_persone != null) {
            for (File file : files_persone){
                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNextLine()) {
                        String linea = scanner.nextLine();
                        String[] campi = linea.split(";");  // Supponiamo che i campi siano separati da punto e virgola
                        if (campi.length >= 5 ) {
                            String nome = campi[0];
                            String cognome = campi[1];
                            String indirizzo = campi[2];
                            String numero = campi[3];
                            String eta = campi[4];
                            UUID uuid = UUID.randomUUID();
                            String id = uuid.toString();
                            Persona persona = new Persona(id, nome, cognome, indirizzo, numero, eta);
                            rubrica.aggiungiPersona(persona);
                        }
                    }
                }
            }
        }

        File[] files_utenti = cartella.listFiles((dir, name) -> name.startsWith("utente_") && name.endsWith(".txt"));
        if (files_utenti != null) {
            for (File file : files_utenti){
                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNextLine()) {
                        String linea = scanner.nextLine();
                        String[] campi = linea.split(";");  // Supponiamo che i campi siano separati da punto e virgola
                        if (campi.length == 2 ) {
                            String username = campi[0];
                            String password = campi[1];
                            Utente utente = new Utente(username, password);
                            rubrica.aggiungiUtente(utente);
                        }
                    }
                }
            }
        }
        
        
        return rubrica;
    }

    public static void salvaSuFile(Rubrica rubrica, String nomeFile) throws IOException {
        File file_persone = new File(nomeFile+"_persone.txt");
        try (PrintStream ps = new PrintStream(file_persone)) {
            for (Persona persona : rubrica.getPersone()) {
                ps.println(persona.getNome() + ";" + persona.getCognome() + ";" + persona.getIndirizzo()+ ";" + persona.getTelefono()+ ";" + persona.getEta());
            }
        }
        File file_utenti = new File(nomeFile+"_utenti.txt");
        try (PrintStream ps = new PrintStream(file_utenti)) {
            for (Utente utente : rubrica.getUtenti()) {
                ps.println(utente.getUsername() + ";" + utente.getPassword());
            }
        }
    }

    public static void salvaSuCartella(Rubrica rubrica, String nomeCartella) throws IOException {
        File cartella = new File(nomeCartella);
        if (!cartella.exists()) {
                cartella.mkdirs();}

        int id_persona=1;
        for (Persona persona : rubrica.getPersone()) {
            File file = new File(cartella, "persona_" + id_persona++ + ".txt");
            try (PrintStream ps = new PrintStream(file)) {
                ps.println(persona.getNome() + ";" + persona.getCognome() + ";" + persona.getIndirizzo()+ ";" + persona.getTelefono()+ ";" + persona.getEta());
            }
        }
        int id_utente=1;
        for (Utente utente : rubrica.getUtenti()) {
            File file = new File(cartella, "utente_" + id_utente++ + ".txt");
            try (PrintStream ps = new PrintStream(file)) {
                ps.println(utente.getUsername() + ";" + utente.getPassword());
            }
        }
    }
}