package main.viewPackage;

import main.controllerPackage.ClubController;
import main.modelPackage.ClubModel;
import main.modelPackage.NonEditableTableModel;
import main.exceptionPackage.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CrudClub extends JPanel implements ActionListener {
    private MainWindow mainWindow;
    private ClubController clubController;
    private JTable clubTable;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton readButton;
    private JButton updateButton;
    private JButton deleteButton;

    public CrudClub(MainWindow mainWindow) throws ConnectionDataAccessException {
        this.mainWindow = mainWindow;
        this.clubController = new ClubController();
        
        setLayout(new BorderLayout());
        
        // Titre
        JLabel title = new JLabel("Gestion des Clubs");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Table des clubs
        String[] columnNames = {"ID", "Nom", "Adresse", "Localité", "Téléphone", "Date de création", "Site web", "Débutants acceptés", "Instagram"};
        tableModel = new NonEditableTableModel(columnNames, 0);
        clubTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(clubTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel des boutons
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

        clubTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRows = clubTable.getSelectedRowCount();
            boolean singleSelection = selectedRows == 1;
            boolean multipleSelection = selectedRows > 1;

            createButton.setEnabled(!multipleSelection);
            readButton.setEnabled(singleSelection);
            updateButton.setEnabled(singleSelection);
            deleteButton.setEnabled(selectedRows > 0);
        });

        loadClubs();
    }

    public void loadClubs() {
        try {
            resetRows();
            List<ClubModel> clubs = clubController.getAllClubs();
            
            for (ClubModel club : clubs) {
                Object[] data = {
                    club.getClubId(),
                    club.getName(),
                    club.getStreetAddress(),
                    club.getLocalityId(),
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
                    club.setClubId(clubId);

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
                    loadClubs();
                    JOptionPane.showMessageDialog(this, 
                        "Tous les clubs ont été supprimés avec succès.", 
                        "Suppression réussie", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    loadClubs();
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
