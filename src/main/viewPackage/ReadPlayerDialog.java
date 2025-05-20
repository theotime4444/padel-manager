package main.viewPackage;

import main.controllerPackage.PlayerController;
import main.controllerPackage.LocalityController;
import main.exceptionPackage.*;
import main.modelPackage.PlayerModel;
import main.modelPackage.LocalityModel;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class ReadPlayerDialog extends JDialog {
    private final PlayerController playerController;
    private final LocalityController localityController;
    private final int playerId;

    public ReadPlayerDialog(Frame parent, int playerId) throws ConnectionDataAccessException {
        super(parent, "Informations du joueur", true);
        this.playerId = playerId;
        this.playerController = new PlayerController();
        this.localityController = new LocalityController();

        setLayout(new BorderLayout());
        createContent();
        pack();
        setLocationRelativeTo(parent);
    }

    private void createContent() {
        try {
            PlayerModel player = playerController.getPlayerById(playerId);
            if (player == null) {
                JOptionPane.showMessageDialog(this, "Le joueur n'a pas été trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
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
            JLabel titleLabel = new JLabel("Carte de Joueur", SwingConstants.CENTER);
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
            String formattedDate = dateFormat.format(player.getBirthdayDate());

            // Get locality information
            String localityInfo = "Non spécifiée";
            if (player.getLocality() != null) {
                try {
                    LocalityModel locality = localityController.getLocalityById(player.getLocality());
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

            // Add player information
            addInfoRow(contentPanel, gbc, "ID:", String.valueOf(player.getPlayerID()), 0);
            addInfoRow(contentPanel, gbc, "Nom:", player.getLastname(), 1);
            addInfoRow(contentPanel, gbc, "Prénom:", player.getFirstname(), 2);
            addInfoRow(contentPanel, gbc, "Date de naissance:", formattedDate, 3);
            addInfoRow(contentPanel, gbc, "Genre:", String.valueOf(player.getGender()), 4);
            addInfoRow(contentPanel, gbc, "Points ELO:", String.valueOf(player.getEloPoints()), 5);
            addInfoRow(contentPanel, gbc, "Téléphone:", player.getPhoneNumber() != null ? player.getPhoneNumber() : "Non spécifié", 6);
            addInfoRow(contentPanel, gbc, "Email:", player.getEmail(), 7);
            addInfoRow(contentPanel, gbc, "Instagram:", player.getInstagramProfile() != null ? player.getInstagramProfile() : "Non spécifié", 8);
            addInfoRow(contentPanel, gbc, "Statut Pro:", player.getIsPro() ? "Oui" : "Non", 9);
            addInfoRow(contentPanel, gbc, "Localité:", localityInfo, 10);

            cardPanel.add(contentPanel, BorderLayout.CENTER);

            // Close button
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton closeButton = new JButton("Fermer");
            closeButton.addActionListener(e -> dispose());
            buttonPanel.add(closeButton);
            cardPanel.add(buttonPanel, BorderLayout.SOUTH);

            add(cardPanel);

        } catch (PlayerSearchException e) {
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