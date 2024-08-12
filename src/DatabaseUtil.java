import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class DatabaseUtil {
    //private static final String URL = "jdbc:mysql://localhost:3306/schema_database";
    //private static final String USER = "testuser"; // Replace with your MySQL username
    //private static final String PASSWORD = "pass1234"; // Replace with your MySQL password
    
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = new FileInputStream("credenziali_database.properties")) {
        // Load the properties file into the Properties object
        properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load properties", ex);
        }

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String IP = properties.getProperty("db.ip-server-mysql");
        String PORTA = properties.getProperty("db.porta");
        String USER = properties.getProperty("db.username");
        String PASSWORD = properties.getProperty("db.password");
        String URL =  String.format("jdbc:mysql://%s:%s/schema_database", IP, PORTA);
    
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<Persona> loadPersone() throws SQLException {
        List<Persona> persone = new ArrayList<>();
        String query = "SELECT * FROM persone";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("idPersone");
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                String indirizzo = resultSet.getString("indirizzo");
                String telefono = resultSet.getString("telefono");
                String eta = resultSet.getString("eta");
                Persona persona = new Persona(id, nome, cognome, indirizzo, telefono, eta);
                persone.add(persona);
            }
        }
        return persone;
    }

    public static List<Utente> loadUtenti() throws SQLException {
        List<Utente> utenti = new ArrayList<>();
        String query = "SELECT * FROM utenti";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                
                Utente utente = new Utente(username, password);
                utenti.add(utente);
            }
        }
        return utenti;
    }

    public static void savePersona(Persona persona) throws SQLException {
        String query = "INSERT INTO persone (idPersone, nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, persona.getId());
            statement.setString(2, persona.getNome());
            statement.setString(3, persona.getCognome());
            statement.setString(4, persona.getIndirizzo());
            statement.setString(5, persona.getTelefono());
            statement.setString(6, persona.getEta());
            statement.executeUpdate();
        }
    }

    public static void saveUtente(Utente utente) throws SQLException {
        String query = "INSERT INTO utenti (username, password) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, utente.getUsername());
            statement.setString(2, utente.getPassword());
            statement.executeUpdate();
        }
    }

    public static void updatePersona(Persona persona, String id) throws SQLException {
        String query = "UPDATE persone SET nome = ?, cognome = ?, indirizzo = ?, telefono = ?, eta = ? WHERE idPersone = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, persona.getNome());
            statement.setString(2, persona.getCognome());
            statement.setString(3, persona.getIndirizzo());
            statement.setString(4, persona.getTelefono());
            statement.setString(5, persona.getEta());
            statement.setString(6, id);
            statement.executeUpdate();
        }
    }

    public static void deletePersona(String id) throws SQLException {
        String query = "DELETE FROM persone WHERE idPersone = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, id);
            statement.executeUpdate();
        }
    }
}
