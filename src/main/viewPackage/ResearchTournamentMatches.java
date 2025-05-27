package main.viewPackage;

import main.controllerPackage.*;
import main.modelPackage.*;
import main.utilPackage.ValidationUtility;
import main.exceptionPackage.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.time.ZoneId;

public class ResearchTournamentMatches extends JPanel implements ActionListener {
    private MainWindow mainWindow;
    private TournamentController tournamentController;
    private JTextField tournamentNameField;
    private JButton submitButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public ResearchTournamentMatches(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.tournamentController = new TournamentController();

        JLabel title = new JLabel("Rechercher les matchs d'un tournoi :");
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

        // Champ nom du tournoi
        JLabel tournamentNameLabel = new JLabel("Nom du tournoi :");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(tournamentNameLabel, gbc);

        tournamentNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(tournamentNameField, gbc);

        // Bouton de recherche
        submitButton = new JButton("Rechercher");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);
        submitButton.addActionListener(this);

        // Table des résultats
        String[] columns = {"Tournoi", "Club ID", "Prix", "Début", "Fin", "Court ID", "Joueur ID", "Score", "Équipe"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
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

    private void searchTournamentMatches() {
        String tournamentName = tournamentNameField.getText().trim();
        if (tournamentName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nom de tournoi", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            resetRows();
            List<TournamentDisplayData> matches = tournamentController.getTournamentMatchesWithDetails(tournamentName);
            
            if (matches.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun match trouvé pour ce tournoi", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            for (TournamentDisplayData data : matches) {
                Object[] row = {
                    data.tournament.getName(),
                    data.tournament.getClubId(),
                    data.tournament.getPrize(),
                    dateFormat.format(Date.from(data.tournament.getStartingDateHour().atZone(ZoneId.systemDefault()).toInstant())),
                    dateFormat.format(Date.from(data.game.getEndingDateHour().atZone(ZoneId.systemDefault()).toInstant())),
                    data.game.getCourtId(),
                    data.participation.getPlayerId(),
                    data.participation.getScore(),
                    data.participation.getTeamNbr()
                };
                tableModel.addRow(row);
            }

        } catch (TournamentSearchException | GameSearchException | ParticipationSearchException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur de recherche", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            searchTournamentMatches();
        }
    }
}
