import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;
import javax.swing.*;

public class EditorPersona extends JDialog {
    private final JTextField nomeField;
    private final JTextField cognomeField;
    private final JTextField indirizzoField;
    private final JTextField telefonoField;
    private final JTextField etaField;
    private boolean confermato;

    public EditorPersona(Frame owner, Boolean toolbar) {
        super(owner, "Aggiungi Contatto", true);
        setLayout(new BorderLayout());
        

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Margini per distanziare i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        nomeField = new JTextField("", 20);
        cognomeField = new JTextField("", 20);
        indirizzoField = new JTextField("", 20);
        telefonoField = new JTextField("", 20);
        etaField = new JTextField("", 20);
        
        aggiungiCampo(inputPanel, gbc, "Nome:", nomeField);
        aggiungiCampo(inputPanel, gbc, "Cognome:", cognomeField);
        aggiungiCampo(inputPanel, gbc, "Indirizzo:", indirizzoField);
        aggiungiCampo(inputPanel, gbc, "Telefono:", telefonoField);
        aggiungiCampo(inputPanel, gbc, "Età:", etaField);
        add(inputPanel, BorderLayout.CENTER);


        JButton confermaButton = new JButton("Salva");
        try {
            var file = new File("icons/check.png");
            ImageIcon icon = new ImageIcon(file.toURI().toURL());
            Image image = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            confermaButton.setIcon(icon);
        } catch (MalformedURLException e) {
            System.err.println("Invalid path: " + "icons/check.png");
        }
        confermaButton.addActionListener((ActionEvent e) -> {
            confermato = true;
            setVisible(false);
        });

        JButton annullaButton = new JButton("Annulla");
        try {
            var file = new File("icons/cross.png");
            ImageIcon icon = new ImageIcon(file.toURI().toURL());
            Image image = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            annullaButton.setIcon(icon);
        } catch (MalformedURLException e) {
            System.err.println("Invalid path: " + "icons/cross.png");
        }
        annullaButton.addActionListener((ActionEvent e) -> {
            confermato = false;
            setVisible(false);
        });

        if (toolbar){
            JToolBar toolBar = new JToolBar("Still draggable");
            toolBar.add(confermaButton);
            toolBar.add(annullaButton);
            add(toolBar, BorderLayout.NORTH);
        }
        else{
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(confermaButton);
            buttonPanel.add(annullaButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        pack();
        setLocationRelativeTo(owner);
    }

    public EditorPersona(Frame owner, Persona persona, Boolean toolbar) {
        super(owner, "Modifica Contatto", true);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Margini per distanziare i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        nomeField = new JTextField(persona.getNome(), 20);
        cognomeField = new JTextField(persona.getCognome(), 20);
        indirizzoField = new JTextField(persona.getIndirizzo(), 20);
        telefonoField = new JTextField(persona.getTelefono(), 20);
        etaField = new JTextField(persona.getEta(), 20);
        
        aggiungiCampo(inputPanel, gbc, "Nome:", nomeField);
        aggiungiCampo(inputPanel, gbc, "Cognome:", cognomeField);
        aggiungiCampo(inputPanel, gbc, "Indirizzo:", indirizzoField);
        aggiungiCampo(inputPanel, gbc, "Telefono:", telefonoField);
        aggiungiCampo(inputPanel, gbc, "Età:", etaField);
        add(inputPanel, BorderLayout.CENTER);

        
        JButton confermaButton = new JButton("Salva");
        try {
            var file = new File("icons/check.png");
            ImageIcon icon = new ImageIcon(file.toURI().toURL());
            Image image = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            confermaButton.setIcon(icon);
        } catch (MalformedURLException e) {
            System.err.println("Invalid path: " + "icons/check.png");
        }
        confermaButton.addActionListener((ActionEvent e) -> {
            confermato = true;
            setVisible(false);
        });

        JButton annullaButton = new JButton("Annulla");
        try {
            var file = new File("icons/cross.png");
            ImageIcon icon = new ImageIcon(file.toURI().toURL());
            Image image = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            annullaButton.setIcon(icon);
        } catch (MalformedURLException e) {
            System.err.println("Invalid path: " + "icons/cross.png");
        }
        annullaButton.addActionListener((ActionEvent e) -> {
            confermato = false;
            setVisible(false);
        });

        

        if (toolbar){
            JToolBar toolBar = new JToolBar("Still draggable");
            toolBar.add(confermaButton);
            toolBar.add(annullaButton);
            add(toolBar, BorderLayout.NORTH);
        }
        else{
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(confermaButton);
            buttonPanel.add(annullaButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        pack();
        setLocationRelativeTo(owner);
    }

    private void aggiungiCampo(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField) {
        gbc.gridx = 0;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(textField, gbc);
        gbc.gridy++;
    }

    public boolean isConfermato() {
        return confermato;
    }

    public String getNome() {
        return nomeField.getText();
    }

    public String getCognome() {
        return cognomeField.getText();
    }

    public String getTelefono() {
        return telefonoField.getText();
    }

    public String getIndirizzo() {
        return indirizzoField.getText();
    }

    public String getEta() {
        return etaField.getText();
    }
}
