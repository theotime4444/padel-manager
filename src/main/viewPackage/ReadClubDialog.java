package main.viewPackage;

import main.controllerPackage.ClubController;
import main.controllerPackage.LocalityController;
import main.exceptionPackage.*;
import main.modelPackage.ClubModel;
import main.modelPackage.LocalityModel;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class ReadClubDialog extends JDialog {
    private final ClubController clubController;
    private final LocalityController localityController;
    private final int clubId;

    public ReadClubDialog(Frame parent, int clubId) throws ConnectionDataAccessException {
        super(parent, "Informations du club", true);
        this.clubId = clubId;
        this.clubController = new ClubController();
        this.localityController = new LocalityController();

        setLayout(new BorderLayout());
        createContent();
        pack();
        setLocationRelativeTo(parent);
    }

    private void createContent() {
        try {
            ClubModel club = clubController.getClubById(clubId);
            if (club == null) {
                JOptionPane.showMessageDialog(this, "Le club n'a pas été trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
                dispose();
                return;
            }

            // Main panel with card-like appearance
            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(new BorderLayout());
            cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));
            cardPanel.setBackground(Color.WHITE);

            // Header with title
            JLabel titleLabel = new JLabel("Carte de Club", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            cardPanel.add(titleLabel, BorderLayout.NORTH);

            // Content panel
            JPanel contentPanel = new JPanel(new GridBagLayout());
            contentPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Format date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = dateFormat.format(club.getCreationDate());

            // Get locality information
            String localityInfo = "Non spécifiée";
            if (club.getLocalityID() != null) {
                try {
                    LocalityModel locality = localityController.getLocalityById(club.getLocalityID());
                    if (locality != null) {
                        localityInfo = locality.getCity() + " (" + locality.getRegion() + ")";
                    } else {
                        localityInfo = "Localité non trouvée";
                    }
                } catch (LocalitySearchException e) {
                    System.err.println("Erreur lors de la récupération de la localité: " + e.getMessage());
                    localityInfo = "Erreur de chargement de la localité";
                }
            }

            // Add club information
            addInfoRow(contentPanel, gbc, "ID:", String.valueOf(club.getId()), 0);
            addInfoRow(contentPanel, gbc, "Nom:", club.getName(), 1);
            addInfoRow(contentPanel, gbc, "Adresse:", club.getStreetAddress(), 2);
            addInfoRow(contentPanel, gbc, "Localité:", localityInfo, 3);
            addInfoRow(contentPanel, gbc, "Téléphone:", club.getPhoneNumber() != null ? club.getPhoneNumber() : "Non spécifié", 4);
            addInfoRow(contentPanel, gbc, "Date de création:", formattedDate, 5);
            addInfoRow(contentPanel, gbc, "Site web:", club.getWebsite() != null ? club.getWebsite() : "Non spécifié", 6);
            addInfoRow(contentPanel, gbc, "Débutants acceptés:", club.getIsBeginnersFriendly() ? "Oui" : "Non", 7);
            addInfoRow(contentPanel, gbc, "Instagram:", club.getInstagramProfile() != null ? club.getInstagramProfile() : "Non spécifié", 8);

            cardPanel.add(contentPanel, BorderLayout.CENTER);

            // Close button
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton closeButton = new JButton("Fermer");
            closeButton.addActionListener(e -> dispose());
            buttonPanel.add(closeButton);
            cardPanel.add(buttonPanel, BorderLayout.SOUTH);

            add(cardPanel);

        } catch (ClubSearchException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void addInfoRow(JPanel panel, GridBagConstraints gbc, String label, String value, int row) {
        // Label
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(labelComponent, gbc);

        // Value
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(valueComponent, gbc);
    }
} 