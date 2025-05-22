package main.viewPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import main.controllerPackage.PlayerController;
import main.exceptionPackage.*;
import main.modelPackage.PlayerModel;
import main.modelPackage.NonEditableTableModel;

public class CrudPlayer extends JPanel implements ActionListener {
    private MainWindow mainWindow;
    private JTable playerTable;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton readButton;
    private JButton updateButton;
    private JButton deleteButton;
    private PlayerController playerController;

    public CrudPlayer(MainWindow mainWindow) throws ConnectionDataAccessException {
        this.mainWindow = mainWindow;
        this.playerController = new PlayerController();
        
        setLayout(new BorderLayout());
        
        // Create title
        JLabel title = new JLabel("Gestion des Joueurs");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        // Create table
        String[] columnNames = {"ID", "Nom", "Prénom", "Date de naissance", "Genre", "Points ELO", "Téléphone", "Email", "Pro", "Localité", "Instagram"};
        tableModel = new NonEditableTableModel(columnNames, 0);
        playerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(playerTable);
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
        playerTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRows = playerTable.getSelectedRowCount();
            boolean singleSelection = selectedRows == 1;
            boolean multipleSelection = selectedRows > 1;

            createButton.setEnabled(!multipleSelection);
            readButton.setEnabled(singleSelection);
            updateButton.setEnabled(singleSelection);
            deleteButton.setEnabled(selectedRows > 0);
        });

        // Load initial data
        loadPlayers();
    }

    public void loadPlayers() {
        try {
            resetRows();
            List<PlayerModel> players = playerController.getAllPlayers();
            
            for (PlayerModel player : players) {
                Object[] data = {
                    player.getPlayerId(),
                    player.getLastname(),
                    player.getFirstname(),
                    player.getBirthdayDate(),
                    player.getGender(),
                    player.getEloPoints(),
                    player.getPhoneNumber(),
                    player.getEmail(),
                    player.getIsPro() ? "Oui" : "Non",
                    player.getLocalityId(),
                    player.getInstagramProfile()
                };
                tableModel.addRow(data);
            }
        } catch (PlayerSearchException e) {
            mainWindow.displayError(e.toString());
        }
    }

    private void resetRows() {
        tableModel.setRowCount(0);
    }

    private void createPlayer() {
        try {
            CreatePlayerDialog dialog = new CreatePlayerDialog(this);
            dialog.setVisible(true);
        } catch (ConnectionDataAccessException e) {
            mainWindow.displayError("Erreur de connexion: " + e.getMessage());
        }
    }

    private void readPlayer() {
        int selectedRow = playerTable.getSelectedRow();
        if (selectedRow != -1) {
            int playerId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                ReadPlayerDialog dialog = new ReadPlayerDialog((Frame) SwingUtilities.getWindowAncestor(this), playerId);
                dialog.setVisible(true);
            } catch (ConnectionDataAccessException e) {
                mainWindow.displayError("Erreur de connexion: " + e.getMessage());
            }
        }
    }

    private void updatePlayer() {
        int selectedRow = playerTable.getSelectedRow();
        if (selectedRow != -1) {
            int playerId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                UpdatePlayerDialog dialog = new UpdatePlayerDialog(this, playerId);
                dialog.setVisible(true);
            } catch (ConnectionDataAccessException e) {
                mainWindow.displayError("Erreur de connexion: " + e.getMessage());
            }
        }
    }

    private void deletePlayer() {
        int[] selectedRows = playerTable.getSelectedRows();
        if (selectedRows.length > 0) {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Êtes-vous sûr de vouloir supprimer " + selectedRows.length + " joueur(s) ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                boolean allDeleted = true;
                StringBuilder errorMessage = new StringBuilder();

                for (int row : selectedRows) {
                    int playerId = (int) tableModel.getValueAt(row, 0);
                    PlayerModel player = new PlayerModel();
                    player.setPlayerId(playerId);

                    try {
                        if (!playerController.deletePlayer(player)) {
                            allDeleted = false;
                            errorMessage.append("Impossible de supprimer le joueur ID: ").append(playerId).append("\n");
                        }
                    } catch (PlayerDeletionException e) {
                        allDeleted = false;
                        errorMessage.append("Erreur lors de la suppression du joueur ID: ").append(playerId).append(" - ").append(e.getMessage()).append("\n");
                    }
                }

                if (allDeleted) {
                    loadPlayers(); // Recharge la table
                    JOptionPane.showMessageDialog(this, 
                        "Tous les joueurs ont été supprimés avec succès.", 
                        "Suppression réussie", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    loadPlayers(); // Recharge la table même en cas d'erreur partielle
                    JOptionPane.showMessageDialog(this, 
                        "Certains joueurs n'ont pas pu être supprimés :\n" + errorMessage.toString(), 
                        "Erreur de suppression", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            createPlayer();
        } else if (e.getSource() == readButton) {
            readPlayer();
        } else if (e.getSource() == updateButton) {
            updatePlayer();
        } else if (e.getSource() == deleteButton) {
            deletePlayer();
        }
    }
}
