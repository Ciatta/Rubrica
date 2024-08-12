import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class Login extends JFrame {
    private Rubrica rubrica;
    private final JTextField usernameField;
    private final JTextField passwordField;
    private final Boolean persistenzaSuCartella = true;
    private final Boolean persistenzaSuDatabase = true;

    public Login() {
        rubrica = new Rubrica();
    
        // Layout dell'interfaccia
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Margini per distanziare i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        usernameField = new JTextField("", 20);
        passwordField = new JTextField("", 20);
        
        aggiungiCampo(inputPanel, gbc, "Username:", usernameField);
        aggiungiCampo(inputPanel, gbc, "Password:", passwordField);
        
        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton confermaButton = new JButton("Login");
        confermaButton.addActionListener((ActionEvent e) -> {
            if (controllaPassword()){
                setVisible(false);
                RubricaApp app = new RubricaApp(rubrica, persistenzaSuCartella, persistenzaSuDatabase);
                app.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(this, "Login Errato", "Errore", JOptionPane.ERROR_MESSAGE);
            }
            
        });

        JButton annullaButton = new JButton("Crea");
        annullaButton.addActionListener((ActionEvent e) -> {
            Utente utente = new Utente(usernameField.getText(), passwordField.getText());
            rubrica.aggiungiUtente(utente);
            try {
                DatabaseUtil.saveUtente(utente);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Errore nel salvataggio dell'utente nel database", "Errore", JOptionPane.ERROR_MESSAGE);
            }
            setVisible(false);
            try {
                if (persistenzaSuCartella) PersistenzaRubrica.salvaSuCartella(rubrica, "informazioni");
                else PersistenzaRubrica.salvaSuFile(rubrica, "informazioni");
            } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Errore nel salvataggio della rubrica", "Errore", JOptionPane.ERROR_MESSAGE);
            }
            RubricaApp app = new RubricaApp(rubrica, persistenzaSuCartella, persistenzaSuDatabase);
            app.setVisible(true);

        });

        buttonPanel.add(confermaButton);
        buttonPanel.add(annullaButton);
        add(buttonPanel, BorderLayout.SOUTH);

          // Caricamento iniziale della rubrica da file
        if (persistenzaSuDatabase){
            try {
                List<Persona> persone = DatabaseUtil.loadPersone();
                for (Persona persona : persone) {
                    rubrica.aggiungiPersona(persona);
                }
                List<Utente> utenti = DatabaseUtil.loadUtenti();
                for (Utente utente : utenti) {
                    rubrica.aggiungiUtente(utente);
                }
            } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Errore nel caricamento della rubrica dal database", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            try {
                if (persistenzaSuCartella) rubrica = PersistenzaRubrica.caricaDaCartella("informazioni");
                else rubrica = PersistenzaRubrica.caricaDaFile("informazioni");
            } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Errore nel caricamento della rubrica", "Errore", JOptionPane.ERROR_MESSAGE);
        }

        }


        
    }

    private void aggiungiCampo(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField) {
        gbc.gridx = 0;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(textField, gbc);
        gbc.gridy++;
    }

   

    private boolean controllaPassword() {
        for (Utente utente : rubrica.getUtenti()){
            if (utente.getUsername().equals(usernameField.getText())){
                return utente.getPassword().equals(passwordField.getText());
            }
        }
        return false;
    }

    

 
    
}
