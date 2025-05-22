package main.viewPackage;

import main.controllerPackage.PlayerController;
import main.controllerPackage.LocalityController;
import main.exceptionPackage.*;
import main.modelPackage.PlayerModel;
import main.modelPackage.LocalityModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BusinessFindPlayer extends JPanel {
    private PlayerController playerController;
    private LocalityController localityController;
    private JSpinner eloSpinner;
    private JComboBox<LocalityModel> localityComboBox;
    private JSpinner maxEloDifferenceSpinner;
    private JButton searchButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public BusinessFindPlayer() {
        playerController = new PlayerController();
        localityController = new LocalityController();
        setLayout(new BorderLayout());

        // Panel de recherche
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Points ELO
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(new JLabel("Points ELO:"), gbc);
        SpinnerNumberModel eloModel = new SpinnerNumberModel(1000, 0, 5000, 100);
        eloSpinner = new JSpinner(eloModel);
        gbc.gridx = 1;
        searchPanel.add(eloSpinner, gbc);
        
        // Localité
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(new JLabel("Localité:"), gbc);
        localityComboBox = new JComboBox<>();
        try {
            List<LocalityModel> localities = localityController.getAllLocalities();
            for (LocalityModel locality : localities) {
                localityComboBox.addItem(locality);
            }
            localityComboBox.setRenderer(new LocalityListCellRenderer());
        } catch (LocalitySearchException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des localités : " + e.getMessage(), 
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        gbc.gridx = 1;
        searchPanel.add(localityComboBox, gbc);
        
        // Différence max ELO
        gbc.gridx = 0;
        gbc.gridy = 2;
        searchPanel.add(new JLabel("Différence max ELO:"), gbc);
        SpinnerNumberModel diffModel = new SpinnerNumberModel(100, 0, 1000, 50);
        maxEloDifferenceSpinner = new JSpinner(diffModel);
        gbc.gridx = 1;
        searchPanel.add(maxEloDifferenceSpinner, gbc);
        
        // Bouton de recherche
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        searchButton = new JButton("Rechercher des partenaires");
        searchPanel.add(searchButton, gbc);

        // Table des résultats
        String[] columns = {"ID", "Prénom", "Nom", "Points ELO", "Email", "Téléphone"};
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
        searchButton.addActionListener(e -> searchPartners());
    }

    private void searchPartners() {
        int eloPoints = (Integer) eloSpinner.getValue();
        LocalityModel selectedLocality = (LocalityModel) localityComboBox.getSelectedItem();
        int maxEloDifference = (Integer) maxEloDifferenceSpinner.getValue();

        if (selectedLocality == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une localité", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Récupérer les partenaires potentiels
            List<PlayerModel> partners = playerController.findPotentialPartnersByEloAndCity(
                eloPoints,
                selectedLocality.getCity(),
                maxEloDifference
            );

            // Vider la table
            tableModel.setRowCount(0);

            if (partners.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun partenaire potentiel trouvé", 
                    "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Afficher les partenaires
            for (PlayerModel partner : partners) {
                tableModel.addRow(new Object[]{
                    partner.getPlayerId(),
                    partner.getFirstname(),
                    partner.getLastname(),
                    partner.getEloPoints(),
                    partner.getEmail(),
                    partner.getPhoneNumber()
                });
            }

        } catch (PlayerSearchException | LocalitySearchException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche : " + ex.getMessage(), 
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Custom renderer for LocalityModel in ComboBox
    private class LocalityListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                     boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof LocalityModel) {
                LocalityModel locality = (LocalityModel) value;
                setText(locality.getCity() + " (" + locality.getRegion() + ")");
            }
            return this;
        }
    }
} 