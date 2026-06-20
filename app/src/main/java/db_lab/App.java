package db_lab;

import db_lab.data.CareerZenDAO;
import db_lab.model.Candidato;
import db_lab.model.Amministratore;
import db_lab.model.Azienda;
import db_lab.model.PosizioneLavorativa;
import db_lab.model.Colloquio;
import db_lab.model.Offerta;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class App {

    private static final CareerZenDAO dao = new CareerZenDAO();
    private static final Font titleFont = new Font("SansSerif", Font.BOLD, 18);
    private static final Font textFont = new Font("SansSerif", Font.PLAIN, 14);
    
    private static final Color BG_COLOR = new Color(250, 247, 242);
    private static final Color TEXT_COLOR = new Color(60, 48, 36);
    private static final Color BTN_COLOR = new Color(74, 47, 31);
    private static final Color TEXT_AREA_BG = new Color(242, 237, 228);

    private static JFrame mainFrame;
    private static JPanel containerPanel;
    private static CardLayout cardLayout;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        mainFrame = new JFrame("CareerZen - Portale Candidati");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(750, 600);
        mainFrame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        JPanel loginPanel = createLoginPanel();
        containerPanel.add(loginPanel, "LOGIN");

        mainFrame.add(containerPanel);
        cardLayout.show(containerPanel, "LOGIN");
        mainFrame.setVisible(true);
    }

    private static void showAnnunciList(String title, List<String> annunci) {
        JDialog dialog = new JDialog(mainFrame, title, true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(mainFrame);
        
        JPanel dPanel = new JPanel(new BorderLayout());
        dPanel.setBackground(BG_COLOR);
        dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(textFont);
        area.setMargin(new Insets(10, 10, 10, 10));
        area.setBackground(TEXT_AREA_BG);
        area.setForeground(TEXT_COLOR);
        
        if (annunci == null || annunci.isEmpty()) {
            area.setText("Nessun annuncio trovato.");
        } else {
            for (String a : annunci) {
                area.append(a + "\n\n");
            }
        }
        
        dPanel.add(new JScrollPane(area), BorderLayout.CENTER);
        dialog.add(dPanel);
        dialog.setVisible(true);
    }

    private static void styleButton(JButton btn) {
        btn.setBackground(BTN_COLOR);
        btn.setOpaque(true);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
    }

    private static JTextField addLabeledField(JPanel panel, String labelText, int row, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(textFont);
        label.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(label, gbc);

        JTextField textField = new JTextField(15);
        textField.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(textField, gbc);
        return textField;
    }

    private static JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(textFont);
        userLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        JTextField userField = new JTextField(15);
        userField.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(userField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(textFont);
        passLabel.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(15);
        passField.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passField, gbc);

        JCheckBox adminCheckBox = new JCheckBox("Accedi come Amministratore");
        adminCheckBox.setFont(textFont);
        adminCheckBox.setForeground(TEXT_COLOR);
        adminCheckBox.setBackground(BG_COLOR);
        adminCheckBox.setHorizontalAlignment(JCheckBox.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(adminCheckBox, gbc);

        JPanel buttonPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BG_COLOR);
        
        JButton loginButton = new JButton("Accedi");
        styleButton(loginButton);
        loginButton.setMargin(new Insets(10, 20, 10, 20));
        buttonPanel.add(loginButton);

        JButton registerButton = new JButton("Registrati");
        styleButton(registerButton);
        registerButton.setMargin(new Insets(10, 20, 10, 20));
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        loginButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Username e Password sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (adminCheckBox.isSelected()) {
                Amministratore admin = dao.authenticateAmministratore(username, password);
                if (admin == null) {
                    JOptionPane.showMessageDialog(mainFrame, "Username o Password amministratore errati.", "Errore di autenticazione", JOptionPane.ERROR_MESSAGE);
                } else {
                    JPanel adminDashboardPanel = createAdminDashboardPanel(admin);
                    containerPanel.add(adminDashboardPanel, "ADMIN_DASHBOARD");
                    cardLayout.show(containerPanel, "ADMIN_DASHBOARD");
                }
            } else {
                Candidato candidato = dao.authenticateCandidato(username, password);

                if (candidato == null) {
                    JOptionPane.showMessageDialog(mainFrame, "Username o Password errati.", "Errore di autenticazione", JOptionPane.ERROR_MESSAGE);
                } else {
                    JPanel dashboardPanel = createDashboardPanel(candidato);
                    containerPanel.add(dashboardPanel, "DASHBOARD");
                    cardLayout.show(containerPanel, "DASHBOARD");
                }
            }
        });

        registerButton.addActionListener(e -> {
            JDialog dialog = new JDialog(mainFrame, "Registrazione Candidato", true);
            dialog.setSize(400, 350);
            dialog.setLocationRelativeTo(mainFrame);
            
            JPanel dPanel = new JPanel(new GridBagLayout());
            dPanel.setBackground(BG_COLOR);
            dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints dGbc = new GridBagConstraints();
            dGbc.insets = new Insets(8, 8, 8, 8);
            dGbc.fill = GridBagConstraints.HORIZONTAL;

            JTextField userFieldReg = addLabeledField(dPanel, "NomeUtente:", 0, dGbc);
            
            dGbc.gridx = 0;
            dGbc.gridy = 1;
            dGbc.gridwidth = 1;
            JLabel passLabelReg = new JLabel("Password:");
            passLabelReg.setFont(textFont);
            passLabelReg.setForeground(TEXT_COLOR);
            dPanel.add(passLabelReg, dGbc);
            
            JPasswordField passFieldReg = new JPasswordField(15);
            passFieldReg.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(passFieldReg, dGbc);

            JTextField nomeField = addLabeledField(dPanel, "Nome:", 2, dGbc);
            JTextField keyCognomeField = addLabeledField(dPanel, "Cognome:", 3, dGbc);
            JTextField emailField = addLabeledField(dPanel, "Email:", 4, dGbc);

            JButton saveButton = new JButton("Salva");
            styleButton(saveButton);
            dGbc.gridx = 0;
            dGbc.gridy = 5;
            dGbc.gridwidth = 2;
            dPanel.add(saveButton, dGbc);
            
            saveButton.addActionListener(ev -> {
                String username = userFieldReg.getText().trim();
                String password = new String(passFieldReg.getPassword()).trim();
                String nome = nomeField.getText().trim();
                String cognome = keyCognomeField.getText().trim();
                String email = emailField.getText().trim();
                
                if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Candidato c = new Candidato(0, username, password, nome, cognome, email);
                boolean success = dao.iscriviCandidato(c);
                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Registrazione avvenuta con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Errore durante la registrazione.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(dPanel);
            dialog.setVisible(true);
        });

        return panel;
    }

    private static void refreshDashboard(JTextArea dashboardArea, int idCandidato) {
        dashboardArea.setText("");
        
        dashboardArea.append("=== LE TUE COMPETENZE ===\n");
        List<String> skillsList = dao.getSkillCandidato(idCandidato);
        if (skillsList.isEmpty()) {
            dashboardArea.append("Nessuna competenza inserita.\n");
        } else {
            for (String skill : skillsList) {
                dashboardArea.append(skill + "\n");
            }
        }
        
        dashboardArea.append("\n=== LE TUE CANDIDATURE ===\n");
        List<String> dashboardList = dao.getDashboardCandidato(idCandidato);
        if (dashboardList.isEmpty()) {
            dashboardArea.append("Nessuna candidatura attiva al momento.\n");
        } else {
            for (String item : dashboardList) {
                dashboardArea.append(item + "\n");
            }
        }
    }

    private static JPanel createDashboardPanel(Candidato candidato) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG_COLOR);

        JLabel welcomeLabel = new JLabel("Bentornato, " + candidato.getNome() + " " + candidato.getCognome());
        welcomeLabel.setFont(titleFont);
        welcomeLabel.setForeground(TEXT_COLOR);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        topPanel.add(welcomeLabel, BorderLayout.CENTER);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(180, 60, 50));
        btnLogout.setOpaque(true);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnLogout.addActionListener(e -> cardLayout.show(containerPanel, "LOGIN"));
        topPanel.add(btnLogout, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        JTextArea dashboardArea = new JTextArea();
        dashboardArea.setEditable(false);
        dashboardArea.setFont(textFont);
        dashboardArea.setMargin(new Insets(10, 10, 10, 10));
        dashboardArea.setBackground(TEXT_AREA_BG);
        dashboardArea.setForeground(TEXT_COLOR);

        refreshDashboard(dashboardArea, candidato.getIdCandidato());

        JScrollPane scrollPane = new JScrollPane(dashboardArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel actionsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        actionsPanel.setBackground(BG_COLOR);
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        

        JButton btnCerca = new JButton("Cerca Annunci");
        styleButton(btnCerca);
        actionsPanel.add(btnCerca);

        JButton btnCandidati = new JButton("Candidati");
        styleButton(btnCandidati);
        actionsPanel.add(btnCandidati);

        JButton btnSkill = new JButton("Aggiungi Skill");
        styleButton(btnSkill);
        actionsPanel.add(btnSkill);

        JButton btnStorico = new JButton("Storico Iter");
        styleButton(btnStorico);
        actionsPanel.add(btnStorico);

        JButton btnAggiornaSkill = new JButton("Aggiorna Livello Skill");
        styleButton(btnAggiornaSkill);
        actionsPanel.add(btnAggiornaSkill);

        JButton btnRispondiOfferta = new JButton("Rispondi a Offerta");
        styleButton(btnRispondiOfferta);
        actionsPanel.add(btnRispondiOfferta);

        JButton btnAvanzaStato = new JButton("Avanza Stato Candidatura");
        styleButton(btnAvanzaStato);
        actionsPanel.add(btnAvanzaStato);

        JButton btnFissaColloquio = new JButton("Fissa Colloquio");
        styleButton(btnFissaColloquio);
        actionsPanel.add(btnFissaColloquio);

        JButton btnEsitoColloquio = new JButton("Esito Colloquio");
        styleButton(btnEsitoColloquio);
        actionsPanel.add(btnEsitoColloquio);

        JButton btnTracciaOfferta = new JButton("Traccia Nuova Offerta");
        styleButton(btnTracciaOfferta);
        actionsPanel.add(btnTracciaOfferta);

        panel.add(actionsPanel, BorderLayout.SOUTH);

        btnCerca.addActionListener(e -> {
            JDialog dialog = new JDialog(mainFrame, "Scegli tipo di ricerca", true);
            dialog.setSize(300, 250);
            dialog.setLocationRelativeTo(mainFrame);
            
            JPanel dPanel = new JPanel(new GridLayout(4, 1, 10, 10));
            dPanel.setBackground(BG_COLOR);
            dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JButton btnAll = new JButton("Tutti gli annunci");
            styleButton(btnAll);
            JButton btnSearchSkill = new JButton("Per Skill Compatibili");
            styleButton(btnSearchSkill);
            JButton btnAzienda = new JButton("Per Azienda");
            styleButton(btnAzienda);
            JButton btnId = new JButton("Per ID");
            styleButton(btnId);
            
            btnAll.addActionListener(ev -> {
                dialog.dispose();
                showAnnunciList("Tutti gli Annunci", dao.getAllPosizioniLavorativeFormatted());
            });
            
            btnSearchSkill.addActionListener(ev -> {
                dialog.dispose();
                showAnnunciList("Annunci Compatibili con le tue Skill", dao.getAnnunciCompatibili(candidato.getIdCandidato()));
            });
            
            btnAzienda.addActionListener(ev -> {
                dialog.dispose();
                List<String> aziende = dao.getElencoAziende();
                if (aziende.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Nessuna azienda trovata.", "Errore", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                String sel = (String) JOptionPane.showInputDialog(mainFrame, "Seleziona Azienda:", "Cerca per Azienda", JOptionPane.QUESTION_MESSAGE, null, aziende.toArray(), aziende.get(0));
                if (sel != null) {
                    int idAz = Integer.parseInt(sel.split(" - ")[0]);
                    showAnnunciList("Annunci per " + sel.split(" - ")[1], dao.getAnnunciByAzienda(idAz));
                }
            });
            
            btnId.addActionListener(ev -> {
                dialog.dispose();
                String input = JOptionPane.showInputDialog(mainFrame, "Inserisci l'ID dell'annuncio:", "Cerca per ID", JOptionPane.QUESTION_MESSAGE);
                if (input != null && !input.trim().isEmpty()) {
                    try {
                        int id = Integer.parseInt(input.trim());
                        showAnnunciList("Risultato Ricerca per ID: " + id, dao.getAnnuncioById(id));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Inserisci un ID numerico valido.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            
            dPanel.add(btnAll);
            dPanel.add(btnSearchSkill);
            dPanel.add(btnAzienda);
            dPanel.add(btnId);
            dialog.add(dPanel);
            dialog.setVisible(true);
        });

        btnCandidati.addActionListener(e -> {
            List<String> posizioni = dao.getAllPosizioniLavorativeFormatted();
            if (posizioni.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Nessuna posizione lavorativa disponibile al momento.", "Nessun Annuncio", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String selectedPos = (String) JOptionPane.showInputDialog(
                    mainFrame,
                    "Seleziona la Posizione a cui candidarsi:",
                    "Candidati",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    posizioni.toArray(new String[0]),
                    posizioni.get(0)
            );

            if (selectedPos == null || selectedPos.trim().isEmpty()) {
                return;
            }
            try {
                int idPosizione = Integer.parseInt(selectedPos.split(" - ")[0]);
                boolean success = dao.inviaCandidatura(candidato.getIdCandidato(), idPosizione);
                if (success) {
                    JOptionPane.showMessageDialog(mainFrame, "Candidatura inviata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    refreshDashboard(dashboardArea, candidato.getIdCandidato());
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Errore durante l'invio della candidatura.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Errore durante la selezione della posizione.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSkill.addActionListener(e -> {
            JDialog dialog = new JDialog(mainFrame, "Aggiungi Skill", true);
            dialog.setSize(400, 220);
            dialog.setLocationRelativeTo(mainFrame);
            
            JPanel dPanel = new JPanel(new GridBagLayout());
            dPanel.setBackground(BG_COLOR);
            dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints dGbc = new GridBagConstraints();
            dGbc.insets = new Insets(8, 8, 8, 8);
            dGbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel skillLabel = new JLabel("Skill:");
            skillLabel.setFont(textFont);
            skillLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 0; dGbc.gridwidth = 1;
            dPanel.add(skillLabel, dGbc);

            List<String> skillList = dao.getElencoSkill();
            JComboBox<String> skillCombo = new JComboBox<>(skillList.toArray(new String[0]));
            skillCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(skillCombo, dGbc);

            JLabel levelLabel = new JLabel("Livello:");
            levelLabel.setFont(textFont);
            levelLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 1;
            dPanel.add(levelLabel, dGbc);

            Integer[] levels = {1, 2, 3, 4, 5};
            JComboBox<Integer> levelCombo = new JComboBox<>(levels);
            levelCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(levelCombo, dGbc);

            JButton saveButton = new JButton("Salva");
            styleButton(saveButton);
            dGbc.gridx = 0;
            dGbc.gridy = 2;
            dGbc.gridwidth = 2;
            dPanel.add(saveButton, dGbc);

            saveButton.addActionListener(ev -> {
                String selectedSkill = (String) skillCombo.getSelectedItem();
                Integer selectedLevel = (Integer) levelCombo.getSelectedItem();

                if (selectedSkill == null || selectedLevel == null) {
                    JOptionPane.showMessageDialog(dialog, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int idSkill = Integer.parseInt(selectedSkill.split(" - ")[0]);
                    boolean success = dao.associaSkill(candidato.getIdCandidato(), idSkill, selectedLevel);
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Skill associata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Errore durante l'associazione della skill.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Errore nell'elaborazione dell'associazione.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(dPanel);
            dialog.setVisible(true);
        });

        btnStorico.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(mainFrame, "Inserisci l'ID della Candidatura da visualizzare:", "Visualizza Storico", JOptionPane.QUESTION_MESSAGE);
            if (input == null || input.trim().isEmpty()) {
                return;
            }
            try {
                int idCandidatura = Integer.parseInt(input.trim());
                List<String> storicoList = dao.getStoricoCompleto(idCandidatura);
                
                JDialog dialog = new JDialog(mainFrame, "Storico Candidatura #" + idCandidatura, true);
                dialog.setSize(500, 400);
                dialog.setLocationRelativeTo(mainFrame);
                
                JPanel dPanel = new JPanel(new BorderLayout());
                dPanel.setBackground(BG_COLOR);
                dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                
                JTextArea area = new JTextArea();
                area.setEditable(false);
                area.setFont(textFont);
                area.setMargin(new Insets(10, 10, 10, 10));
                area.setBackground(TEXT_AREA_BG);
                area.setForeground(TEXT_COLOR);
                
                if (storicoList.isEmpty()) {
                    area.setText("Nessuno storico o candidatura non trovata.");
                } else {
                    for (String line : storicoList) {
                        area.append(line + "\n");
                    }
                }
                
                dPanel.add(new JScrollPane(area), BorderLayout.CENTER);
                dialog.add(dPanel);
                dialog.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Inserisci un ID Candidatura numerico valido.", "Errore Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAggiornaSkill.addActionListener(e -> {
            JDialog dialog = new JDialog(mainFrame, "Aggiorna Livello Skill", true);
            dialog.setSize(400, 220);
            dialog.setLocationRelativeTo(mainFrame);
            
            JPanel dPanel = new JPanel(new GridBagLayout());
            dPanel.setBackground(BG_COLOR);
            dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints dGbc = new GridBagConstraints();
            dGbc.insets = new Insets(8, 8, 8, 8);
            dGbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel skillLabel = new JLabel("Skill:");
            skillLabel.setFont(textFont);
            skillLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 0; dGbc.gridwidth = 1;
            dPanel.add(skillLabel, dGbc);

            List<String> skillList = dao.getElencoSkill();
            JComboBox<String> skillCombo = new JComboBox<>(skillList.toArray(new String[0]));
            skillCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(skillCombo, dGbc);

            JLabel levelLabel = new JLabel("Nuovo Livello:");
            levelLabel.setFont(textFont);
            levelLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 1;
            dPanel.add(levelLabel, dGbc);

            Integer[] levels = {1, 2, 3, 4, 5};
            JComboBox<Integer> levelCombo = new JComboBox<>(levels);
            levelCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(levelCombo, dGbc);

            JButton saveButton = new JButton("Salva");
            styleButton(saveButton);
            dGbc.gridx = 0; dGbc.gridy = 2; dGbc.gridwidth = 2;
            dPanel.add(saveButton, dGbc);

            saveButton.addActionListener(ev -> {
                String selectedSkill = (String) skillCombo.getSelectedItem();
                Integer selectedLevel = (Integer) levelCombo.getSelectedItem();

                if (selectedSkill == null || selectedLevel == null) {
                    JOptionPane.showMessageDialog(dialog, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int idSkill = Integer.parseInt(selectedSkill.split(" - ")[0]);
                    boolean success = dao.aggiornaLivelloSkill(candidato.getIdCandidato(), idSkill, selectedLevel);
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Skill aggiornata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Errore durante l'aggiornamento della skill.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Errore nell'elaborazione dell'aggiornamento.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(dPanel);
            dialog.setVisible(true);
        });

        btnRispondiOfferta.addActionListener(e -> {
            List<String> offerte = dao.getOfferteRicevute(candidato.getIdCandidato());
            if (offerte.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Non hai nessuna offerta a cui rispondere al momento.", "Nessuna Offerta", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String selectedOff = (String) JOptionPane.showInputDialog(
                    mainFrame,
                    "Seleziona l'Offerta a cui rispondere:",
                    "Rispondi a Offerta",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    offerte.toArray(new String[0]),
                    offerte.get(0)
            );

            if (selectedOff == null || selectedOff.trim().isEmpty()) {
                return;
            }

            String[] decisioni = {"Accettata", "Rifiutata"};
            String decisione = (String) JOptionPane.showInputDialog(
                    mainFrame,
                    "Scegli la tua decisione:",
                    "Decisione Offerta",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    decisioni,
                    decisioni[0]
            );

            if (decisione == null || decisione.trim().isEmpty()) {
                return;
            }

            try {
                int idOfferta = Integer.parseInt(selectedOff.split(" - ")[0]);
                boolean success = dao.aggiornaStatoOfferta(idOfferta, decisione);
                if (success) {
                    JOptionPane.showMessageDialog(mainFrame, "Decisione registrata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    refreshDashboard(dashboardArea, candidato.getIdCandidato());
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Errore durante l'aggiornamento dell'offerta.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Errore durante l'elaborazione.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnTracciaOfferta.addActionListener(e -> {
            List<String> candidature = dao.getCandidatureByCandidato(candidato.getIdCandidato());
            if (candidature.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Non hai ancora nessuna candidatura attiva. Candidati prima a un annuncio.", "Nessuna Candidatura", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JDialog dialog = new JDialog(mainFrame, "Traccia Nuova Offerta", true);
            dialog.setSize(450, 320);
            dialog.setLocationRelativeTo(mainFrame);
            
            JPanel dPanel = new JPanel(new GridBagLayout());
            dPanel.setBackground(BG_COLOR);
            dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints dGbc = new GridBagConstraints();
            dGbc.insets = new Insets(8, 8, 8, 8);
            dGbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel candLabel = new JLabel("Candidatura:");
            candLabel.setFont(textFont);
            candLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 0; dGbc.gridwidth = 1;
            dPanel.add(candLabel, dGbc);

            JComboBox<String> candCombo = new JComboBox<>(candidature.toArray(new String[0]));
            candCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(candCombo, dGbc);

            JTextField ralField = addLabeledField(dPanel, "RAL Proposta:", 1, dGbc);
            JTextField benefitField = addLabeledField(dPanel, "Benefit:", 2, dGbc);
            
            JLabel scadenzaLabel = new JLabel("Scadenza:");
            scadenzaLabel.setFont(textFont);
            scadenzaLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 3; dGbc.gridwidth = 1;
            dPanel.add(scadenzaLabel, dGbc);

            JPanel datePanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
            datePanel.setBackground(BG_COLOR);
            String[] years = {"2026", "2027", "2028", "2029", "2030"};
            String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
            String[] days = new String[31];
            for(int i=1; i<=31; i++) days[i-1] = String.format("%02d", i);
            JComboBox<String> yearCombo = new JComboBox<>(years);
            JComboBox<String> monthCombo = new JComboBox<>(months);
            JComboBox<String> dayCombo = new JComboBox<>(days);
            datePanel.add(yearCombo);
            datePanel.add(new JLabel("-"));
            datePanel.add(monthCombo);
            datePanel.add(new JLabel("-"));
            datePanel.add(dayCombo);
            dGbc.gridx = 1; dGbc.gridy = 3;
            dPanel.add(datePanel, dGbc);

            JButton saveButton = new JButton("Salva");
            styleButton(saveButton);
            dGbc.gridx = 0; dGbc.gridy = 4; dGbc.gridwidth = 2;
            dPanel.add(saveButton, dGbc);

            saveButton.addActionListener(ev -> {
                String selectedCand = (String) candCombo.getSelectedItem();
                String ralText = ralField.getText().trim();
                String benefit = benefitField.getText().trim();
                String scadenzaText = yearCombo.getSelectedItem() + "-" + monthCombo.getSelectedItem() + "-" + dayCombo.getSelectedItem();

                if (selectedCand == null || ralText.isEmpty() || benefit.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int idCandidatura = Integer.parseInt(selectedCand.split(" - ")[0]);
                    
                    double ral;
                    try {
                        ral = Double.parseDouble(ralText.replace(",", "."));
                    } catch (NumberFormatException exNum) {
                        JOptionPane.showMessageDialog(dialog, "La RAL deve essere un numero valido.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    java.sql.Date dataScadenza;
                    try {
                        dataScadenza = java.sql.Date.valueOf(scadenzaText);
                    } catch (IllegalArgumentException exDate) {
                        JOptionPane.showMessageDialog(dialog, "Data selezionata non valida (es. 31 Febbraio).", "Errore Data", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Offerta o = new Offerta(0, ral, benefit, dataScadenza, "In Attesa", idCandidatura);
                    boolean success = dao.inserisciOfferta(o);
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Offerta registrata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                        refreshDashboard(dashboardArea, candidato.getIdCandidato());
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Errore durante l'inserimento dell'offerta.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Errore generico durante l'elaborazione.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(dPanel);
            dialog.setVisible(true);
        });

        btnAvanzaStato.addActionListener(e -> {
            JDialog dialog = new JDialog(mainFrame, "Avanza Stato Candidatura", true);
            dialog.setSize(500, 250);
            dialog.setLocationRelativeTo(mainFrame);
            
            JPanel dPanel = new JPanel(new GridBagLayout());
            dPanel.setBackground(BG_COLOR);
            dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints dGbc = new GridBagConstraints();
            dGbc.insets = new Insets(8, 8, 8, 8);
            dGbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel candLabel = new JLabel("Candidatura:");
            candLabel.setFont(textFont);
            candLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 0;
            dPanel.add(candLabel, dGbc);

            List<String> candidature = dao.getCandidatureByCandidato(candidato.getIdCandidato());
            if (candidature.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Nessuna candidatura trovata.", "Avviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JComboBox<String> candCombo = new JComboBox<>(candidature.toArray(new String[0]));
            candCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(candCombo, dGbc);

            JLabel statoLabel = new JLabel("Nuovo Stato:");
            statoLabel.setFont(textFont);
            statoLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 1;
            dPanel.add(statoLabel, dGbc);

            String[] stati = {"Inviata", "In Valutazione", "Colloquio", "Offerta", "Assunto", "Rifiutata"};
            JComboBox<String> statoCombo = new JComboBox<>(stati);
            statoCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(statoCombo, dGbc);

            JButton saveButton = new JButton("Salva");
            styleButton(saveButton);
            dGbc.gridx = 0; dGbc.gridy = 2; dGbc.gridwidth = 2;
            dPanel.add(saveButton, dGbc);

            saveButton.addActionListener(ev -> {
                String selectedCand = (String) candCombo.getSelectedItem();
                String nuovoStato = (String) statoCombo.getSelectedItem();

                if (selectedCand == null || nuovoStato == null) {
                    JOptionPane.showMessageDialog(dialog, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int idCandidatura = Integer.parseInt(selectedCand.split(" - ")[0]);
                    boolean success = dao.avanzaStatoCandidatura(idCandidatura, nuovoStato);
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Stato avanzato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                        refreshDashboard(dashboardArea, candidato.getIdCandidato());
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Errore durante l'avanzamento di stato.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Errore nell'elaborazione della candidatura.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(dPanel);
            dialog.setVisible(true);
        });

        btnFissaColloquio.addActionListener(e -> {
            JDialog dialog = new JDialog(mainFrame, "Fissa Nuovo Colloquio", true);
            dialog.setSize(650, 350);
            dialog.setLocationRelativeTo(mainFrame);
            
            JPanel dPanel = new JPanel(new GridBagLayout());
            dPanel.setBackground(BG_COLOR);
            dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints dGbc = new GridBagConstraints();
            dGbc.insets = new Insets(8, 8, 8, 8);
            dGbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel candLabel = new JLabel("Candidatura:");
            candLabel.setFont(textFont);
            candLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 0;
            dPanel.add(candLabel, dGbc);

            List<String> candidature = dao.getCandidatureByCandidato(candidato.getIdCandidato());
            if (candidature.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Nessuna candidatura trovata.", "Avviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JComboBox<String> candCombo = new JComboBox<>(candidature.toArray(new String[0]));
            candCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(candCombo, dGbc);

            JLabel dataOraLabel = new JLabel("Data e Ora:");
            dataOraLabel.setFont(textFont);
            dataOraLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 1;
            dPanel.add(dataOraLabel, dGbc);

            JPanel datePanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
            datePanel.setBackground(BG_COLOR);
            String[] years = {"2026", "2027", "2028", "2029", "2030"};
            String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
            String[] days = new String[31];
            for(int i=1; i<=31; i++) days[i-1] = String.format("%02d", i);
            String[] hours = new String[13];
            for(int i=0; i<13; i++) hours[i] = String.format("%02d", i+8);
            String[] mins = {"00", "15", "30", "45"};

            JComboBox<String> yearCombo = new JComboBox<>(years);
            JComboBox<String> monthCombo = new JComboBox<>(months);
            JComboBox<String> dayCombo = new JComboBox<>(days);
            JComboBox<String> hourCombo = new JComboBox<>(hours);
            JComboBox<String> minCombo = new JComboBox<>(mins);

            datePanel.add(yearCombo);
            datePanel.add(new JLabel("-"));
            datePanel.add(monthCombo);
            datePanel.add(new JLabel("-"));
            datePanel.add(dayCombo);
            datePanel.add(new JLabel(" "));
            datePanel.add(hourCombo);
            datePanel.add(new JLabel(":"));
            datePanel.add(minCombo);
            
            dGbc.gridx = 1; dGbc.gridy = 1;
            dPanel.add(datePanel, dGbc);

            JLabel tipoLabel = new JLabel("Tipo Colloquio:");
            tipoLabel.setFont(textFont);
            tipoLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 2;
            dPanel.add(tipoLabel, dGbc);

            String[] tipiColloquio = {"Conoscitivo", "Tecnico", "HR", "Finale"};
            JComboBox<String> tipoCombo = new JComboBox<>(tipiColloquio);
            tipoCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(tipoCombo, dGbc);

            JTextField recruiterField = addLabeledField(dPanel, "Nome Recruiter:", 3, dGbc);

            JButton saveButton = new JButton("Salva");
            styleButton(saveButton);
            dGbc.gridx = 0; dGbc.gridy = 4; dGbc.gridwidth = 2;
            dPanel.add(saveButton, dGbc);

            saveButton.addActionListener(ev -> {
                String selectedCand = (String) candCombo.getSelectedItem();
                String dataOraText = yearCombo.getSelectedItem() + "-" + monthCombo.getSelectedItem() + "-" + dayCombo.getSelectedItem() + " " + hourCombo.getSelectedItem() + ":" + minCombo.getSelectedItem() + ":00";
                String tipo = (String) tipoCombo.getSelectedItem();
                String recruiter = recruiterField.getText().trim();

                if (selectedCand == null || recruiter.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int idCandidatura = Integer.parseInt(selectedCand.split(" - ")[0]);
                    java.sql.Timestamp dataOra = java.sql.Timestamp.valueOf(dataOraText);
                    
                    Colloquio c = new Colloquio(0, dataOra, tipo, "", recruiter, "", idCandidatura);
                    boolean success = dao.inserisciColloquio(c);
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Colloquio pianificato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                        refreshDashboard(dashboardArea, candidato.getIdCandidato());
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Errore durante l'inserimento del colloquio.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dialog, "Formato data/ora non valido.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(dPanel);
            dialog.setVisible(true);
        });

        btnEsitoColloquio.addActionListener(e -> {
            JDialog dialog = new JDialog(mainFrame, "Registra Esito Colloquio", true);
            dialog.setSize(600, 250);
            dialog.setLocationRelativeTo(mainFrame);
            
            JPanel dPanel = new JPanel(new GridBagLayout());
            dPanel.setBackground(BG_COLOR);
            dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints dGbc = new GridBagConstraints();
            dGbc.insets = new Insets(8, 8, 8, 8);
            dGbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel collLabel = new JLabel("Colloquio:");
            collLabel.setFont(textFont);
            collLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 0;
            dPanel.add(collLabel, dGbc);

            List<String> colloqui = dao.getColloquiByCandidato(candidato.getIdCandidato());
            if (colloqui.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Nessun colloquio trovato.", "Avviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JComboBox<String> collCombo = new JComboBox<>(colloqui.toArray(new String[0]));
            collCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(collCombo, dGbc);

            JLabel esitoLabel = new JLabel("Esito:");
            esitoLabel.setFont(textFont);
            esitoLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 1;
            dPanel.add(esitoLabel, dGbc);

            String[] esiti = {"Candidato eccellente, da assumere", "Buono, da valutare", "Non idoneo"};
            JComboBox<String> esitoCombo = new JComboBox<>(esiti);
            esitoCombo.setEditable(true);
            esitoCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(esitoCombo, dGbc);

            JTextField noteField = addLabeledField(dPanel, "Note aggiuntive:", 2, dGbc);

            JButton saveButton = new JButton("Salva");
            styleButton(saveButton);
            dGbc.gridx = 0; dGbc.gridy = 3; dGbc.gridwidth = 2;
            dPanel.add(saveButton, dGbc);

            saveButton.addActionListener(ev -> {
                String selectedColl = (String) collCombo.getSelectedItem();
                String esito = (String) esitoCombo.getSelectedItem();
                String note = noteField.getText().trim();

                if (selectedColl == null || esito == null || esito.trim().isEmpty() || note.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int idColloquio = Integer.parseInt(selectedColl.split(" - ")[0]);
                    boolean success = dao.aggiornaEsitoColloquio(idColloquio, esito.trim(), note);
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Esito aggiornato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                        refreshDashboard(dashboardArea, candidato.getIdCandidato());
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Errore durante l'aggiornamento dell'esito.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Errore nell'elaborazione del colloquio.", "Errore Input", JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(dPanel);
            dialog.setVisible(true);
        });

        return panel;
    }

    private static void refreshAdminDashboard(JTextArea reportArea) {
        reportArea.setText("=== ELENCO AZIENDE REGISTRATE ===\n");
        List<String> aziende = dao.getAllAziende();
        if (aziende.isEmpty()) {
            reportArea.append("Nessuna azienda registrata.\n");
        } else {
            for (String az : aziende) {
                reportArea.append(az + "\n");
            }
        }
    }

    private static JPanel createAdminDashboardPanel(Amministratore admin) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG_COLOR);

        JLabel welcomeLabel = new JLabel("Pannello di Controllo Amministratore - Bentornato");
        welcomeLabel.setFont(titleFont);
        welcomeLabel.setForeground(TEXT_COLOR);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        topPanel.add(welcomeLabel, BorderLayout.CENTER);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(180, 60, 50));
        btnLogout.setOpaque(true);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnLogout.addActionListener(e -> cardLayout.show(containerPanel, "LOGIN"));
        topPanel.add(btnLogout, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(textFont);
        reportArea.setMargin(new Insets(10, 10, 10, 10));
        reportArea.setBackground(TEXT_AREA_BG);
        reportArea.setForeground(TEXT_COLOR);
        
        refreshAdminDashboard(reportArea);
        
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);

        JPanel actionsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        actionsPanel.setBackground(BG_COLOR);
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton btnAzienda = new JButton("Aggiungi Azienda");
        styleButton(btnAzienda);
        actionsPanel.add(btnAzienda);

        JButton btnReport = new JButton("Report Aziendale");
        styleButton(btnReport);
        actionsPanel.add(btnReport);



        JButton btnPubblicaAnnuncio = new JButton("Pubblica Annuncio");
        styleButton(btnPubblicaAnnuncio);
        actionsPanel.add(btnPubblicaAnnuncio);

        JButton btnEliminaAnnuncio = new JButton("Elimina Annuncio");
        styleButton(btnEliminaAnnuncio);
        actionsPanel.add(btnEliminaAnnuncio);

        JButton btnAggiungiSkill = new JButton("Aggiungi Skill");
        styleButton(btnAggiungiSkill);
        actionsPanel.add(btnAggiungiSkill);

        JButton btnEliminaAzienda = new JButton("Elimina Azienda");
        styleButton(btnEliminaAzienda);
        actionsPanel.add(btnEliminaAzienda);

        JButton btnVisualizzaAnnunci = new JButton("Visualizza Annunci");
        styleButton(btnVisualizzaAnnunci);
        actionsPanel.add(btnVisualizzaAnnunci);

        panel.add(actionsPanel, BorderLayout.SOUTH);

        btnAzienda.addActionListener(e -> {
            JDialog dialog = new JDialog(mainFrame, "Registra Nuova Azienda", true);
            dialog.setSize(400, 250);
            dialog.setLocationRelativeTo(mainFrame);

            JPanel dPanel = new JPanel(new GridBagLayout());
            dPanel.setBackground(BG_COLOR);
            dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints dGbc = new GridBagConstraints();
            dGbc.insets = new Insets(8, 8, 8, 8);
            dGbc.fill = GridBagConstraints.HORIZONTAL;

            JTextField nomeField = addLabeledField(dPanel, "Nome Azienda:", 0, dGbc);
            JTextField settoreField = addLabeledField(dPanel, "Settore:", 1, dGbc);
            JTextField sedeField = addLabeledField(dPanel, "Sede Principale:", 2, dGbc);

            JButton saveButton = new JButton("Salva");
            styleButton(saveButton);
            dGbc.gridx = 0;
            dGbc.gridy = 3;
            dGbc.gridwidth = 2;
            dPanel.add(saveButton, dGbc);

            saveButton.addActionListener(ev -> {
                String nome = nomeField.getText().trim();
                String settore = settoreField.getText().trim();
                String sede = sedeField.getText().trim();

                if (nome.isEmpty() || settore.isEmpty() || sede.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Azienda a = new Azienda(0, nome, settore, sede);
                boolean success = dao.registraAzienda(a);
                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Azienda registrata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    refreshAdminDashboard(reportArea);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Errore durante la registrazione dell'azienda.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(dPanel);
            dialog.setVisible(true);
        });



        btnReport.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(mainFrame, "Inserisci l'ID dell'Azienda per generare il report:", "Report Aziendale", JOptionPane.QUESTION_MESSAGE);
            if (input == null || input.trim().isEmpty()) {
                return;
            }
            try {
                int idAzienda = Integer.parseInt(input.trim());
                List<String> reportLines = dao.generaReportAziendale(idAzienda);
                reportArea.setText("");
                if (reportLines.isEmpty()) {
                    reportArea.setText("Nessun report generato o azienda non trovata.");
                } else {
                    for (String line : reportLines) {
                        reportArea.append(line + "\n");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Inserisci un ID Azienda numerico valido.", "Errore Input", JOptionPane.ERROR_MESSAGE);
            }
        });



        btnPubblicaAnnuncio.addActionListener(e -> {
            JDialog dialog = new JDialog(mainFrame, "Pubblica Nuovo Annuncio", true);
            dialog.setSize(450, 450);
            dialog.setLocationRelativeTo(mainFrame);

            JPanel dPanel = new JPanel(new GridBagLayout());
            dPanel.setBackground(BG_COLOR);
            dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints dGbc = new GridBagConstraints();
            dGbc.insets = new Insets(8, 8, 8, 8);
            dGbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel azLabel = new JLabel("Azienda:");
            azLabel.setFont(textFont);
            azLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 0; dGbc.gridwidth = 1;
            dPanel.add(azLabel, dGbc);

            List<String> aziende = dao.getElencoAziende();
            JComboBox<String> azCombo = new JComboBox<>(aziende.toArray(new String[0]));
            azCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(azCombo, dGbc);

            JTextField titoloField = addLabeledField(dPanel, "Titolo:", 1, dGbc);
            JTextField modalitaField = addLabeledField(dPanel, "Modalità:", 2, dGbc);
            JTextField ralField = addLabeledField(dPanel, "Range RAL:", 3, dGbc);
            JTextField scadenzaField = addLabeledField(dPanel, "Scadenza (YYYY-MM-DD):", 4, dGbc);

            JLabel skillLabel = new JLabel("Skill principale:");
            skillLabel.setFont(textFont);
            skillLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 5;
            dPanel.add(skillLabel, dGbc);

            List<String> skillList = dao.getElencoSkill();
            JComboBox<String> skillCombo = new JComboBox<>(skillList.toArray(new String[0]));
            skillCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(skillCombo, dGbc);

            JLabel lvlLabel = new JLabel("Livello min:");
            lvlLabel.setFont(textFont);
            lvlLabel.setForeground(TEXT_COLOR);
            dGbc.gridx = 0; dGbc.gridy = 6;
            dPanel.add(lvlLabel, dGbc);

            String[] levelArray = {"-", "1", "2", "3", "4", "5"};
            JComboBox<String> lvlCombo = new JComboBox<>(levelArray);
            lvlCombo.setFont(textFont);
            dGbc.gridx = 1;
            dPanel.add(lvlCombo, dGbc);

            JButton saveButton = new JButton("Salva");
            styleButton(saveButton);
            dGbc.gridx = 0; dGbc.gridy = 7; dGbc.gridwidth = 2;
            dPanel.add(saveButton, dGbc);

            saveButton.addActionListener(ev -> {
                String selectedAz = (String) azCombo.getSelectedItem();
                String titolo = titoloField.getText().trim();
                String modalita = modalitaField.getText().trim();
                String ral = ralField.getText().trim();
                String scadenzaText = scadenzaField.getText().trim();
                String selectedSkill = (String) skillCombo.getSelectedItem();
                String selectedLvlStr = (String) lvlCombo.getSelectedItem();

                if (selectedAz == null || titolo.isEmpty() || modalita.isEmpty() || ral.isEmpty() || scadenzaText.isEmpty() || selectedSkill == null || selectedLvlStr == null) {
                    JOptionPane.showMessageDialog(dialog, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int idAzienda = Integer.parseInt(selectedAz.split(" - ")[0]);
                    int idSkill = Integer.parseInt(selectedSkill.split(" - ")[0]);
                    java.sql.Date dataScadenza = java.sql.Date.valueOf(scadenzaText);
                    
                    int minLvl = selectedLvlStr.equals("-") ? 1 : Integer.parseInt(selectedLvlStr);

                    PosizioneLavorativa p = new PosizioneLavorativa(0, titolo, modalita, ral, dataScadenza, idAzienda);
                    java.util.Map<Integer, Integer> reqs = new java.util.HashMap<>();
                    reqs.put(idSkill, minLvl);

                    boolean success = dao.pubblicaPosizioneConRequisiti(p, reqs);
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Annuncio pubblicato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Errore durante la pubblicazione dell'annuncio.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dialog, "Parametri numerici o data errati (usa YYYY-MM-DD).", "Errore Input", JOptionPane.ERROR_MESSAGE);
                }
            });

            JScrollPane dScrollPane = new JScrollPane(dPanel);
            dScrollPane.setBorder(null);
            dialog.add(dScrollPane);
            dialog.setVisible(true);
        });

        btnEliminaAnnuncio.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(mainFrame, "Inserisci l'ID dell'Annuncio da eliminare:", "Elimina Annuncio", JOptionPane.QUESTION_MESSAGE);
            if (input == null || input.trim().isEmpty()) return;
            try {
                int idAnnuncio = Integer.parseInt(input.trim());
                int confirm = JOptionPane.showConfirmDialog(mainFrame, "Sei sicuro di voler eliminare l'annuncio ID " + idAnnuncio + "?", "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = dao.eliminaPosizione(idAnnuncio);
                    if (success) {
                        JOptionPane.showMessageDialog(mainFrame, "Annuncio eliminato con successo.");
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Impossibile eliminare l'annuncio. Verifica l'ID o le dipendenze.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "ID non valido.", "Errore Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAggiungiSkill.addActionListener(e -> {
            JDialog dialog = new JDialog(mainFrame, "Aggiungi Nuova Skill al Sistema", true);
            dialog.setSize(400, 200);
            dialog.setLocationRelativeTo(mainFrame);
            
            JPanel dPanel = new JPanel(new GridBagLayout());
            dPanel.setBackground(BG_COLOR);
            dPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            GridBagConstraints dGbc = new GridBagConstraints();
            dGbc.insets = new Insets(8, 8, 8, 8);
            dGbc.fill = GridBagConstraints.HORIZONTAL;

            JTextField nomeField = addLabeledField(dPanel, "Nome Skill:", 0, dGbc);
            JTextField areaField = addLabeledField(dPanel, "Area (es. IT, Design):", 1, dGbc);

            JButton saveButton = new JButton("Salva");
            styleButton(saveButton);
            dGbc.gridx = 0; dGbc.gridy = 2; dGbc.gridwidth = 2;
            dPanel.add(saveButton, dGbc);

            saveButton.addActionListener(ev -> {
                String nome = nomeField.getText().trim();
                String area = areaField.getText().trim();

                if (nome.isEmpty() || area.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = dao.inserisciSkill(nome, area);
                if (success) {
                    JOptionPane.showMessageDialog(dialog, "Skill aggiunta con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Errore. Potrebbe essere già esistente.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.add(dPanel);
            dialog.setVisible(true);
        });

        btnVisualizzaAnnunci.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(mainFrame, "Inserisci l'ID dell'Azienda per vedere i suoi annunci:", "Visualizza Annunci", JOptionPane.QUESTION_MESSAGE);
            if (input == null || input.trim().isEmpty()) return;
            try {
                int idAz = Integer.parseInt(input.trim());
                List<String> annunci = dao.getAnnunciByAzienda(idAz);
                if (annunci.isEmpty()) {
                    JOptionPane.showMessageDialog(mainFrame, "Nessun annuncio trovato per questa Azienda.", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JTextArea area = new JTextArea(15, 50);
                    area.setEditable(false);
                    area.setFont(textFont);
                    area.setBackground(TEXT_AREA_BG);
                    area.setForeground(TEXT_COLOR);
                    for (String a : annunci) area.append(a + "\n");
                    JOptionPane.showMessageDialog(mainFrame, new JScrollPane(area), "Annunci Azienda ID " + idAz, JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "ID non valido.", "Errore Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminaAzienda.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(mainFrame, "Inserisci l'ID dell'Azienda da eliminare:", "Elimina Azienda", JOptionPane.WARNING_MESSAGE);
            if (input == null || input.trim().isEmpty()) {
                return;
            }
            try {
                int idAzienda = Integer.parseInt(input.trim());
                int confirm = JOptionPane.showConfirmDialog(mainFrame, 
                    "Sei sicuro? Questa operazione eliminerà permanentemente l'Azienda e tutti gli annunci e le candidature correlate (Cascade).", 
                    "Conferma Eliminazione", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = dao.eliminaAzienda(idAzienda);
                    if (success) {
                        JOptionPane.showMessageDialog(mainFrame, "Azienda eliminata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        refreshAdminDashboard(reportArea);
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Errore durante l'eliminazione o azienda non trovata.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Inserisci un ID Azienda numerico valido.", "Errore Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }
}