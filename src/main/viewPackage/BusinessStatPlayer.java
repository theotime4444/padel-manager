package main.viewPackage;

import main.controllerPackage.PlayerController;
import main.modelPackage.PlayerModel;
import main.exceptionPackage.*;
import main.utilPackage.ValidationUtility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BusinessStatPlayer extends JPanel {
    private PlayerController playerController;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton searchButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public BusinessStatPlayer() {
        playerController = new PlayerController();
        setLayout(new BorderLayout());

        // Panel de recherche
        JPanel searchPanel = new JPanel(new FlowLayout());
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        searchButton = new JButton("Rechercher");

        searchPanel.add(new JLabel("Nom:"));
        searchPanel.add(lastNameField);
        searchPanel.add(new JLabel("Prénom:"));
        searchPanel.add(firstNameField);
        searchPanel.add(searchButton);

        // Table des résultats
        String[] columns = {"ID", "Prénom", "Nom", "Total Matchs", "Matchs Gagnés", "Matchs Perdus", "Score Moyen"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // Ajout des composants
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Action du bouton
        searchButton.addActionListener(e -> searchPlayer());
    }

    private void searchPlayer() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<PlayerModel> players = playerController.getPlayersByFullName(firstName, lastName);
            if (players.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun joueur trouvé", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Vider la table
            tableModel.setRowCount(0);

            // Pour chaque joueur trouvé, récupérer ses statistiques
            for (PlayerModel player : players) {
                PlayerStatsDisplayData stats = playerController.getPlayerStats(player.getPlayerId());
                tableModel.addRow(new Object[]{
                    stats.player.getPlayerId(),
                    stats.player.getFirstname(),
                    stats.player.getLastname(),
                    stats.totalGames,
                    stats.wonGames,
                    stats.lostGames,
                    String.format("%.2f", stats.averageScore)
                });
            }

        } catch (PlayerSearchException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche : " + ex.getMessage(), 
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
} 