import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.UUID;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class RubricaApp extends JFrame {
    private Rubrica rubrica;
    private final JTable tabellaPersone;
    private final DefaultTableModel tableModel;
    private final Boolean toolbar = true;
    //private final Boolean persistenzaSuCartella = false;
 
    public RubricaApp(Rubrica rubrica2, boolean persistenzaSuCartella, boolean persistenzaSuDatabase) {
        //rubrica = new Rubrica();
        rubrica = rubrica2;
        // Layout dell'interfaccia
        setTitle("Rubrica Telefonica");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabella
        String[] colonne = {"Nome", "Cognome", "Telefono"};
        tableModel = new DefaultTableModel(colonne, 0);
        tabellaPersone = new JTable(tableModel);
        add(new JScrollPane(tabellaPersone), BorderLayout.CENTER);
        
        
        // Bottoni
        JButton aggiungiButton = new JButton("Aggiungi");
        try {
            File file = new File("icons/plus.png");
            ImageIcon icon = new ImageIcon(file.toURI().toURL());
            Image image = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            aggiungiButton.setIcon(icon);
        } catch (MalformedURLException e) {
            System.err.println("Invalid path: " + "icons/plus.png");
        }
        aggiungiButton.addActionListener((ActionEvent e) -> {
            aggiungiPersona();
        });

        JButton modificaButton = new JButton("Modifica");
        try {
            File file = new File("icons/editing.png");
            ImageIcon icon = new ImageIcon(file.toURI().toURL());
            Image image = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            modificaButton.setIcon(icon);
        } catch (MalformedURLException e) {
            System.err.println("Invalid path: " + "icons/editing.png");
        }
        modificaButton.addActionListener((ActionEvent e) -> {
            modificaPersona();
        });

        JButton rimuoviButton = new JButton("Rimuovi");
        rimuoviButton.setToolTipText("Rimuovi");
        // Load the image
        try {
            File file = new File("icons/remove.png");
            ImageIcon icon = new ImageIcon(file.toURI().toURL());
            Image image = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            rimuoviButton.setIcon(icon);
        } catch (MalformedURLException e) {
            System.err.println("Invalid path: " + "icons/remove.png");
        }
        rimuoviButton.addActionListener((ActionEvent e) -> {
            rimuoviPersona();
        });
       
        

        
        // Toolbar or Panel
        if (toolbar){
            JToolBar toolBar = new JToolBar("Still draggable");
            toolBar.add(aggiungiButton);
            toolBar.add(modificaButton);
            toolBar.add(rimuoviButton);
            add(toolBar, BorderLayout.NORTH);
        }
        else{
            JPanel panel = new JPanel(new GridLayout(4, 2));
            panel.add(aggiungiButton);
            panel.add(modificaButton);
            panel.add(rimuoviButton);
            add(panel, BorderLayout.SOUTH);
        }
        

        aggiornaTabellaPersone();
        
        // Carica rubrica
        /*
        try {
            if (persistenzaSuCartella) rubrica = PersistenzaRubrica.caricaDaCartella("informazioni");
            else rubrica = PersistenzaRubrica.caricaDaFile("informazioni");
            aggiornaTabellaPersone();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Errore nel caricamento della rubrica", "Errore", JOptionPane.ERROR_MESSAGE);
        }
        */

        // Salva rubrica
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                /*
                if (persistenzaSuDatabase){
                    try {
                    for (Persona persona : rubrica.getPersone()) {
                        DatabaseUtil.savePersona(persona);
                    }
                    for (Utente utente : rubrica.getUtenti()) {
                        DatabaseUtil.saveUtente(utente);
                    }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errore nel salvataggio della rubrica nel database", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    try {
                        if (persistenzaSuCartella) PersistenzaRubrica.salvaSuCartella(rubrica, "informazioni");
                        else PersistenzaRubrica.salvaSuFile(rubrica, "informazioni");
                    } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Errore nel salvataggio della rubrica", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
                */
               if(!persistenzaSuDatabase){
                try {
                    if (persistenzaSuCartella) PersistenzaRubrica.salvaSuCartella(rubrica, "informazioni");
                    else PersistenzaRubrica.salvaSuFile(rubrica, "informazioni");
                } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Errore nel salvataggio della rubrica", "Errore", JOptionPane.ERROR_MESSAGE);
                }
                }
                
            }
            
           
           
        });
    }

    private void aggiungiPersona() {
        EditorPersona dialog = new EditorPersona(this, toolbar);
        dialog.setVisible(true);
        if (dialog.isConfermato()) {
            
            String nome = dialog.getNome();
            String cognome = dialog.getCognome();
            String telefono = dialog.getTelefono();
            String indizzo = dialog.getIndirizzo();
            String eta = dialog.getEta();

            if (!nome.isEmpty() && !cognome.isEmpty() && !telefono.isEmpty() && !indizzo.isEmpty() && !eta.isEmpty()) {
                
                UUID uuid = UUID.randomUUID();
                String id = uuid.toString();
                Persona persona = new Persona(id, nome, cognome, indizzo, telefono, eta);
                try {
                    rubrica.aggiungiPersona(persona);
                    DatabaseUtil.savePersona(persona);
                    tableModel.addRow(new Object[]{nome, cognome, telefono});   
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Errore nel salvataggio del contatto nel database", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Compila tutti i campi", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modificaPersona() {
        int selectedRow = tabellaPersone.getSelectedRow();
        if (selectedRow != -1) {
            Persona persona = rubrica.getPersone().get(selectedRow);

            EditorPersona dialog = new EditorPersona(this, persona, toolbar);
            dialog.setVisible(true);

            if (dialog.isConfermato()) {
                persona.setNome(dialog.getNome());
                persona.setCognome(dialog.getCognome());
                persona.setIndirizzo(dialog.getIndirizzo());
                persona.setTelefono(dialog.getTelefono());
                persona.setEta(dialog.getEta());
                try {
                    DatabaseUtil.updatePersona(persona, persona.getId());  // Assuming the ID corresponds to the row index + 1
                    aggiornaTabellaPersone();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Errore nel salvataggio del contatto nel database", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
            JOptionPane.showMessageDialog(this, "Seleziona una persona da modificare", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void rimuoviPersona() {
        int selectedRow = tabellaPersone.getSelectedRow();
        if (selectedRow != -1) {
            Persona persona = rubrica.getPersone().get(selectedRow);
            String message = "Eliminare la persona "+persona.getNome().toUpperCase()+" "+persona.getCognome().toUpperCase()+"?";
            int val = JOptionPane.showConfirmDialog(this, message , "Conferma", JOptionPane.YES_NO_OPTION);
            if (val==0) {
                try {
                    rubrica.rimuoviPersona(persona);
                    tableModel.removeRow(selectedRow);
                    DatabaseUtil.deletePersona(persona.getId());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Errore nel salvataggio del contatto nel database", "Errore", JOptionPane.ERROR_MESSAGE);
                }
                
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "Seleziona una persona da eliminare", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aggiornaTabellaPersone() {
        tableModel.setRowCount(0); // Cancella tutte le righe esistenti
        for (Persona persona : rubrica.getPersone()) {
            tableModel.addRow(new Object[]{persona.getNome(), persona.getCognome(), persona.getTelefono()});
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
            //RubricaApp app = new RubricaApp();
            //app.setVisible(true);
        });
    }
}
