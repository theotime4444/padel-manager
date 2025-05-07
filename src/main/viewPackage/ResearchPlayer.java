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
