package main.viewPackage;

import main.controllerPackage.PlayerController;
import main.modelPackage.PlayerModel;
import main.modelPackage.NonEditableTableModel;
import main.modelPackage.ClubModel;
import main.modelPackage.LocalityModel;
import main.exceptionPackage.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ResearchPlayer extends JPanel implements ActionListener {
    private MainWindow mainWindow;
    private PlayerController playerController;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton submitButton;
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

        // Champ nom
        JLabel lastNameLabel = new JLabel("Nom :");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lastNameLabel, gbc);

        lastNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(lastNameField, gbc);

        // Champ prénom
        JLabel firstNameLabel = new JLabel("Prénom :");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(firstNameLabel, gbc);

        firstNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(firstNameField, gbc);

        // Bouton de recherche
        submitButton = new JButton("Rechercher");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);
        submitButton.addActionListener(this);

        // Table des résultats
        String[] columnNames = {"Nom", "Prénom", "Points ELO", "Pro", "Club", "Ville", "Région", "Pays"};
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
                JOptionPane.showMessageDialog(this, "Veuillez remplir les deux champs.", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }

            resetRows();
            List<PlayerDisplayData> playerInfos = playerController.getPlayersWithDetailsByFullName(firstName, lastName);

            for (PlayerDisplayData info : playerInfos) {
                PlayerModel p = info.player;
                ClubModel club = info.lastClub;
                LocalityModel loc = info.locality;

                Object[] data = {
                    p.getLastname(),
                    p.getFirstname(),
                    p.getEloPoints(),
                    p.getIsPro() ? "Oui" : "Non",
                    (club != null ? club.getName() : "—"),
                    (loc != null ? loc.getCity() : "—"),
                    (loc != null ? loc.getRegion() : "—"),
                    (loc != null ? loc.getCountry() : "—")
                };
                tableModel.addRow(data);
            }

            if (playerInfos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun joueur trouvé avec ce nom et prénom.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (PlayerSearchException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur de recherche joueur", JOptionPane.ERROR_MESSAGE);
        } catch (ClubSearchException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur de recherche club", JOptionPane.ERROR_MESSAGE);
        } catch (ValidationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Attention", JOptionPane.WARNING_MESSAGE);
        } catch (LocalitySearchException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur de recherche localité", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            submit();
        }
    }
}
