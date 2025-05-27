package main.viewPackage;

import main.controllerPackage.PlayerController;
import main.controllerPackage.LocalityController;
import main.modelPackage.*;
import main.modelPackage.NonEditableTableModel;
import main.exceptionPackage.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ResearchPlayerRegion extends JPanel implements ActionListener {
    private MainWindow mainWindow;
    private PlayerController playerController;
    private LocalityController localityController;
    private JComboBox<String> regionComboBox;
    private JButton submitButton;
    private DefaultTableModel tableModel;

    public ResearchPlayerRegion(MainWindow mainWindow) throws ConnectionDataAccessException {
        this.mainWindow = mainWindow;

        JLabel title = new JLabel("Rechercher les joueurs par région :");
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
        localityController = new LocalityController();

        // Liste déroulante des régions
        JLabel regionLabel = new JLabel("Région :");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(regionLabel, gbc);

        try {
            List<String> regions = localityController.getAllRegions();
            regionComboBox = new JComboBox<>(regions.toArray(new String[0]));
            gbc.gridx = 1;
            gbc.gridy = 1;
            add(regionComboBox, gbc);
        } catch (LocalitySearchException e) {
            mainWindow.displayError("Erreur lors du chargement des régions : " + e.getMessage());
        }

        // Bouton de recherche
        submitButton = new JButton("Rechercher");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);
        submitButton.addActionListener(this);

        // Table des résultats
        String[] columnNames = {"Prénom", "Nom", "Points ELO", "Club", "Ville"};
        tableModel = new NonEditableTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 3;
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
            String selectedRegion = (String) regionComboBox.getSelectedItem();
            if (selectedRegion == null || selectedRegion.isEmpty()) {
                mainWindow.displayError("Veuillez sélectionner une région.");
                return;
            }

            resetRows();
            List<PlayerDisplayData> players = playerController.getPlayersWithDetailsByRegion(selectedRegion);

            for (PlayerDisplayData data : players) {
                Object[] row = {
                    data.player.getFirstname(),
                    data.player.getLastname(),
                    data.player.getEloPoints(),
                    data.lastClub != null ? data.lastClub.getName() : "—",
                    data.locality != null ? data.locality.getCity() : "—"
                };
                tableModel.addRow(row);
            }

            if (players.isEmpty()) {
                mainWindow.displayMessage("Aucun joueur trouvé dans cette région.", "Information");
            }

        } catch (PlayerSearchException | ClubSearchException | ValidationException | LocalitySearchException e) {
            mainWindow.displayError(e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            submit();
        }
    }
}
