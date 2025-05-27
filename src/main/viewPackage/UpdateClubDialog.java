package main.viewPackage;

import main.controllerPackage.ClubController;
import main.controllerPackage.LocalityController;
import main.modelPackage.ClubModel;
import main.modelPackage.LocalityModel;
import main.exceptionPackage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class UpdateClubDialog extends JDialog implements ActionListener {
    private JTextField nameField;
    private JTextField streetAddressField;
    private JTextField phoneField;
    private JTextField websiteField;
    private JTextField instagramField;
    private JSpinner daySpinner;
    private JSpinner monthSpinner;
    private JSpinner yearSpinner;
    private JCheckBox isBeginnersFriendlyCheckBox;
    private JComboBox<LocalityModel> localityComboBox;
    private JButton submitButton;
    private JButton cancelButton;
    private ClubController clubController;
    private LocalityController localityController;
    private CrudClub parentPanel;
    private ClubModel currentClub;

    public UpdateClubDialog(CrudClub parent, int clubId) throws ConnectionDataAccessException {
        super((Frame) SwingUtilities.getWindowAncestor(parent), "Modifier le club", true);
        this.parentPanel = parent;
        this.clubController = new ClubController();
        this.localityController = new LocalityController();

        try {
            this.currentClub = clubController.getClubById(clubId);
            if (currentClub == null) {
                JOptionPane.showMessageDialog(this, "Club non trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
                dispose();
                return;
            }
        } catch (ClubSearchException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération du club: " + e.getMessage(), 
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

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nom *:"), gbc);
        nameField = new JTextField(currentClub.getName(), 20);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        // Street Address
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Adresse *:"), gbc);
        streetAddressField = new JTextField(currentClub.getStreetAddress(), 20);
        gbc.gridx = 1;
        formPanel.add(streetAddressField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Téléphone:"), gbc);
        phoneField = new JTextField(currentClub.getPhoneNumber(), 20);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        // Website
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Site web:"), gbc);
        websiteField = new JTextField(currentClub.getWebsite(), 20);
        gbc.gridx = 1;
        formPanel.add(websiteField, gbc);

        // Instagram
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Instagram:"), gbc);
        instagramField = new JTextField(currentClub.getInstagramProfile(), 20);
        gbc.gridx = 1;
        formPanel.add(instagramField, gbc);

        // Creation Date
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Date de création *:"), gbc);
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentClub.getCreationDate());
        
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

        // Is Beginners Friendly
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Débutants acceptés:"), gbc);
        isBeginnersFriendlyCheckBox = new JCheckBox();
        isBeginnersFriendlyCheckBox.setSelected(currentClub.getIsBeginnersFriendly());
        gbc.gridx = 1;
        formPanel.add(isBeginnersFriendlyCheckBox, gbc);

        // Locality
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Localité *:"), gbc);
        localityComboBox = new JComboBox<>();
        try {
            List<LocalityModel> localities = localityController.getAllLocalities();
            for (LocalityModel locality : localities) {
                localityComboBox.addItem(locality);
                if (locality.getLocalityId().equals(currentClub.getLocalityId())) {
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
                // Mise à jour du club
                currentClub.setName(nameField.getText());
                currentClub.setStreetAddress(streetAddressField.getText());
                currentClub.setPhoneNumber(phoneField.getText());
                currentClub.setWebsite(websiteField.getText());
                currentClub.setInstagramProfile(instagramField.getText());
                
                // Création de la date à partir des spinners
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, (Integer) yearSpinner.getValue());
                cal.set(Calendar.MONTH, (Integer) monthSpinner.getValue() - 1);
                cal.set(Calendar.DAY_OF_MONTH, (Integer) daySpinner.getValue());
                currentClub.setCreationDate(new Date(cal.getTimeInMillis()));
                
                currentClub.setIsBeginnersFriendly(isBeginnersFriendlyCheckBox.isSelected());
                
                LocalityModel selectedLocality = (LocalityModel) localityComboBox.getSelectedItem();
                if (selectedLocality != null) {
                    currentClub.setLocalityId(selectedLocality.getLocalityId());
                }

                // Sauvegarde du club
                clubController.updateClub(currentClub);
                
                // Rafraîchissement du panel parent
                parentPanel.loadClubs();
                
                // Fermeture de la boîte de dialogue
                dispose();

            } catch (ClubUpdateException e) {
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
