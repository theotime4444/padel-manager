package main.viewPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import main.controllerPackage.ClubController;
import main.exceptionPackage.*;
import main.modelPackage.ClubModel;
import main.modelPackage.NonEditableTableModel;

public class CrudClub extends JPanel implements ActionListener {
    private MainWindow mainWindow;
    private JTable clubTable;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton readButton;
    private JButton updateButton;
    private JButton deleteButton;
    private ClubController clubController;

    public CrudClub(MainWindow mainWindow) throws ConnectionDataAccessException {
        this.mainWindow = mainWindow;
        this.clubController = new ClubController();
        
        setLayout(new BorderLayout());
        
        // Create title
        JLabel title = new JLabel("Gestion des Clubs");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Create table
        String[] columnNames = {"ID", "Nom", "Adresse", "Localité", "Téléphone", "Date de création", "Site web", "Débutants acceptés", "Instagram"};
        tableModel = new NonEditableTableModel(columnNames, 0);
        clubTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(clubTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        createButton = new JButton("Créer");
        readButton = new JButton("Voir");
        updateButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");

        createButton.addActionListener(this);
        readButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);

        buttonPanel.add(createButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add selection listener
        clubTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRows = clubTable.getSelectedRowCount();
            boolean singleSelection = selectedRows == 1;
            boolean multipleSelection = selectedRows > 1;

            createButton.setEnabled(!multipleSelection);
            readButton.setEnabled(singleSelection);
            updateButton.setEnabled(singleSelection);
            deleteButton.setEnabled(selectedRows > 0);
        });

        // Load initial data
        loadClubs();
    }

    public void loadClubs() {
        try {
            resetRows();
            List<ClubModel> clubs = clubController.getAllClubs();
            
            for (ClubModel club : clubs) {
                Object[] data = {
                    club.getId(),
                    club.getName(),
                    club.getStreetAddress(),
                    club.getLocalityID(),
                    club.getPhoneNumber(),
                    club.getCreationDate(),
                    club.getWebsite(),
                    club.getIsBeginnersFriendly() ? "Oui" : "Non",
                    club.getInstagramProfile()
                };
                tableModel.addRow(data);
            }
        } catch (ClubSearchException e) {
            mainWindow.displayError(e.toString());
        }
    }

    private void resetRows() {
        tableModel.setRowCount(0);
    }

    private void createClub() {
        try {
            CreateClubDialog dialog = new CreateClubDialog(this);
            dialog.setVisible(true);
        } catch (ConnectionDataAccessException e) {
            mainWindow.displayError("Erreur de connexion: " + e.getMessage());
        }
    }

    private void readClub() {
        int selectedRow = clubTable.getSelectedRow();
        if (selectedRow != -1) {
            int clubId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                ReadClubDialog dialog = new ReadClubDialog((Frame) SwingUtilities.getWindowAncestor(this), clubId);
                dialog.setVisible(true);
            } catch (ConnectionDataAccessException e) {
                mainWindow.displayError("Erreur de connexion: " + e.getMessage());
            }
        }
    }

    private void updateClub() {
        int selectedRow = clubTable.getSelectedRow();
        if (selectedRow != -1) {
            int clubId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                UpdateClubDialog dialog = new UpdateClubDialog(this, clubId);
                dialog.setVisible(true);
            } catch (ConnectionDataAccessException e) {
                mainWindow.displayError("Erreur de connexion: " + e.getMessage());
            }
        }
    }

    private void deleteClub() {
        int[] selectedRows = clubTable.getSelectedRows();
        if (selectedRows.length > 0) {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Êtes-vous sûr de vouloir supprimer " + selectedRows.length + " club(s) ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean allDeleted = true;
                StringBuilder errorMessage = new StringBuilder();

                for (int row : selectedRows) {
                    int clubId = (int) tableModel.getValueAt(row, 0);
                    ClubModel club = new ClubModel();
                    club.setId(clubId);

                    try {
                        if (!clubController.deleteClub(club)) {
                            allDeleted = false;
                            errorMessage.append("Impossible de supprimer le club ID: ").append(clubId).append("\n");
                        }
                    } catch (ClubDeletionException e) {
                        allDeleted = false;
                        errorMessage.append("Erreur lors de la suppression du club ID: ").append(clubId).append(" - ").append(e.getMessage()).append("\n");
                    }
                }

                if (allDeleted) {
                    loadClubs(); // Recharge la table
                    JOptionPane.showMessageDialog(this, 
                        "Tous les clubs ont été supprimés avec succès.", 
                        "Suppression réussie", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    loadClubs(); // Recharge la table même en cas d'erreur partielle
                    JOptionPane.showMessageDialog(this, 
                        "Certains clubs n'ont pas pu être supprimés :\n" + errorMessage.toString(), 
                        "Erreur de suppression", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            createClub();
        } else if (e.getSource() == readButton) {
            readClub();
        } else if (e.getSource() == updateButton) {
            updateClub();
        } else if (e.getSource() == deleteButton) {
            deleteClub();
        }
    }
}


/*
package main.viewPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import main.controllerPackage.PlayerController;
import main.exceptionPackage.ConnectionDataAccessException;
import main.exceptionPackage.PlayerSearchException;
import main.modelPackage.PlayerModel;
import main.modelPackage.NonEditableTableModel;

public class ResearchPlayer extends JPanel implements ActionListener {
    private MainWindow mainWindow;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton submitButton;
    private PlayerController playerController;
    private DefaultTableModel tableModel;

    public ResearchPlayer(MainWindow mainWindow) throws ConnectionDataAccessException {
        this.mainWindow = mainWindow;

        JLabel title = new JLabel("Rechercher un joueur par nom et prénom :");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        playerController = new PlayerController();

        // First name field
        JLabel firstNameLabel = new JLabel("Prénom :");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(firstNameLabel, gbc);

        firstNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(firstNameField, gbc);

        // Last name field
        JLabel lastNameLabel = new JLabel("Nom :");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lastNameLabel, gbc);

        lastNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(lastNameField, gbc);

        submitButton = new JButton("Rechercher");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);
        submitButton.addActionListener(this);

        String[] columnNames = {"Nom", "Prénom", "Points ELO", "Pro"};
        tableModel = new NonEditableTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);
    }

    private void resetRows() {
        tableModel.setRowCount(0);
    }

    private void submit() {
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();

            if (firstName.isEmpty() || lastName.isEmpty()) {
                mainWindow.displayError("Veuillez remplir les deux champs.");
                return;
            }

            resetRows();
            List<PlayerModel> players = playerController.getPlayersByFullName(firstName, lastName);

            for (PlayerModel player : players) {
                Object[] data = {
                    player.getLastname(),
                    player.getFirstname(),
                    player.getEloPoints(),
                    player.getIsPro() ? "Oui" : "Non"
                };
                tableModel.addRow(data);
            }

            if (players.isEmpty()) {
                mainWindow.displayMessage("Aucun joueur trouvé avec ce nom et prénom.", "");
            }

        } catch (PlayerSearchException exception) {
            mainWindow.displayError(exception.toString());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            submit();
        }
    }
}





*/