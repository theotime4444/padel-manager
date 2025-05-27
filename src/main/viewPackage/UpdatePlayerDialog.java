package main.viewPackage;

import main.controllerPackage.PlayerController;
import main.controllerPackage.LocalityController;
import main.modelPackage.PlayerModel;
import main.modelPackage.LocalityModel;
import main.exceptionPackage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class UpdatePlayerDialog extends JDialog implements ActionListener {
    private CrudPlayer parentPanel;
    private PlayerController playerController;
    private LocalityController localityController;
    private PlayerModel currentPlayer;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField instagramField;
    private JSpinner eloPointsSpinner;
    private JSpinner daySpinner;
    private JSpinner monthSpinner;
    private JSpinner yearSpinner;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private JCheckBox isProCheckBox;
    private JComboBox<LocalityModel> localityComboBox;
    private JButton submitButton;
    private JButton cancelButton;

    public UpdatePlayerDialog(CrudPlayer parent, int playerId) throws ConnectionDataAccessException {
        super((Frame) SwingUtilities.getWindowAncestor(parent), "Modifier le joueur", true);
        this.parentPanel = parent;
        this.playerController = new PlayerController();
        this.localityController = new LocalityController();

        try {
            this.currentPlayer = playerController.getPlayerById(playerId);
            if (currentPlayer == null) {
                JOptionPane.showMessageDialog(this, "Joueur non trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
                dispose();
                return;
            }
        } catch (PlayerSearchException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération du joueur: " + e.getMessage(), 
                "Erreur", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setLayout(new BorderLayout());
        createContent();
        pack();
        setLocationRelativeTo(parent);
    }

    private void createContent() {
        // Panel du formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Last Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nom *:"), gbc);
        lastNameField = new JTextField(currentPlayer.getLastname(), 20);
        gbc.gridx = 1;
        formPanel.add(lastNameField, gbc);

        // First Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Prénom *:"), gbc);
        firstNameField = new JTextField(currentPlayer.getFirstname(), 20);
        gbc.gridx = 1;
        formPanel.add(firstNameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email *:"), gbc);
        emailField = new JTextField(currentPlayer.getEmail(), 20);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Téléphone:"), gbc);
        phoneField = new JTextField(currentPlayer.getPhoneNumber(), 20);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        // Instagram
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Instagram:"), gbc);
        instagramField = new JTextField(currentPlayer.getInstagramProfile(), 20);
        gbc.gridx = 1;
        formPanel.add(instagramField, gbc);

        // Birthday Date
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Date de naissance *:"), gbc);
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentPlayer.getBirthdayDate());
        
        // Day spinner (1-31)
        SpinnerNumberModel dayModel = new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1);
        daySpinner = new JSpinner(dayModel);
        datePanel.add(daySpinner);
        datePanel.add(new JLabel("/"));
        
        // Month spinner (1-12)
        SpinnerNumberModel monthModel = new SpinnerNumberModel(cal.get(Calendar.MONTH) + 1, 1, 12, 1);
        monthSpinner = new JSpinner(monthModel);
        datePanel.add(monthSpinner);
        datePanel.add(new JLabel("/"));
        
        // Year spinner (1900-current year)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        SpinnerNumberModel yearModel = new SpinnerNumberModel(cal.get(Calendar.YEAR), 1900, currentYear, 1);
        yearSpinner = new JSpinner(yearModel);
        datePanel.add(yearSpinner);
        
        gbc.gridx = 1;
        formPanel.add(datePanel, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Genre *:"), gbc);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleRadio = new JRadioButton("M");
        femaleRadio = new JRadioButton("F");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        if (currentPlayer.getGender() == 'M') {
            maleRadio.setSelected(true);
        } else {
            femaleRadio.setSelected(true);
        }
        gbc.gridx = 1;
        formPanel.add(genderPanel, gbc);

        // ELO Points
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Points ELO *:"), gbc);
        SpinnerNumberModel eloModel = new SpinnerNumberModel(currentPlayer.getEloPoints(), 0, 5000, 1);
        eloPointsSpinner = new JSpinner(eloModel);
        gbc.gridx = 1;
        formPanel.add(eloPointsSpinner, gbc);

        // Is Pro
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(new JLabel("Joueur Pro:"), gbc);
        isProCheckBox = new JCheckBox();
        isProCheckBox.setSelected(currentPlayer.getIsPro());
        gbc.gridx = 1;
        formPanel.add(isProCheckBox, gbc);

        // Locality
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(new JLabel("Localité *:"), gbc);
        localityComboBox = new JComboBox<>();
        try {
            List<LocalityModel> localities = localityController.getAllLocalities();
            for (LocalityModel locality : localities) {
                localityComboBox.addItem(locality);
                if (locality.getLocalityId().equals(currentPlayer.getLocalityId())) {
                    localityComboBox.setSelectedItem(locality);
                }
            }
            localityComboBox.setRenderer(new LocalityListCellRenderer());
        } catch (LocalitySearchException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        gbc.gridx = 1;
        formPanel.add(localityComboBox, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Panel des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitButton = new JButton("Modifier");
        cancelButton = new JButton("Annuler");
        submitButton.addActionListener(this);
        cancelButton.addActionListener(this);
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submitButton) {
            try {
                // Mise à jour du joueur
                currentPlayer.setLastname(lastNameField.getText());
                currentPlayer.setFirstname(firstNameField.getText());
                currentPlayer.setEmail(emailField.getText());
                currentPlayer.setPhoneNumber(phoneField.getText());
                currentPlayer.setInstagramProfile(instagramField.getText());
                
                // Création de la date à partir des spinners
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, (Integer) yearSpinner.getValue());
                cal.set(Calendar.MONTH, (Integer) monthSpinner.getValue() - 1);
                cal.set(Calendar.DAY_OF_MONTH, (Integer) daySpinner.getValue());
                currentPlayer.setBirthdayDate(new Date(cal.getTimeInMillis()));
                
                currentPlayer.setGender(maleRadio.isSelected() ? 'M' : 'F');
                currentPlayer.setEloPoints((Integer) eloPointsSpinner.getValue());
                currentPlayer.setIsPro(isProCheckBox.isSelected());
                
                LocalityModel selectedLocality = (LocalityModel) localityComboBox.getSelectedItem();
                if (selectedLocality != null) {
                    currentPlayer.setLocalityId(selectedLocality.getLocalityId());
                }

                // Sauvegarde du joueur
                playerController.updatePlayer(currentPlayer);
                
                // Rafraîchissement du panel parent
                parentPanel.loadPlayers();
                
                // Fermeture de la boîte de dialogue
                dispose();

            } catch (PlayerUpdateException e) {
                JOptionPane.showMessageDialog(this,
                        e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            } catch (ValidationException e) {
                JOptionPane.showMessageDialog(this,
                        e.getMessage(),
                        "Attention",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else if (actionEvent.getSource() == cancelButton) {
            dispose();
        }
    }

    // Rendu personnalisé pour LocalityModel dans ComboBox
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