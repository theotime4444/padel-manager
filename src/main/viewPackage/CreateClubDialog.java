package main.viewPackage;

import main.controllerPackage.ClubController;
import main.controllerPackage.LocalityController;
import main.exceptionPackage.*;
import main.modelPackage.ClubModel;
import main.modelPackage.LocalityModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class CreateClubDialog extends JDialog implements ActionListener {
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

    public CreateClubDialog(CrudClub parent) throws ConnectionDataAccessException {
        super((Frame) SwingUtilities.getWindowAncestor(parent), "Créer un nouveau club", true);
        this.parentPanel = parent;
        this.clubController = new ClubController();
        this.localityController = new LocalityController();

        setLayout(new BorderLayout());
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nom *:"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        // Street Address
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Adresse *:"), gbc);
        streetAddressField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(streetAddressField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Téléphone *:"), gbc);
        phoneField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        // Website
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Site web:"), gbc);
        websiteField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(websiteField, gbc);

        // Instagram
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Instagram:"), gbc);
        instagramField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(instagramField, gbc);

        // Creation Date
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Date de création *:"), gbc);
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Day spinner (1-31)
        SpinnerNumberModel dayModel = new SpinnerNumberModel(1, 1, 31, 1);
        daySpinner = new JSpinner(dayModel);
        datePanel.add(daySpinner);
        datePanel.add(new JLabel("/"));
        
        // Month spinner (1-12)
        SpinnerNumberModel monthModel = new SpinnerNumberModel(1, 1, 12, 1);
        monthSpinner = new JSpinner(monthModel);
        datePanel.add(monthSpinner);
        datePanel.add(new JLabel("/"));
        
        // Year spinner (1900-current year)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        SpinnerNumberModel yearModel = new SpinnerNumberModel(currentYear, 1900, currentYear, 1);
        yearSpinner = new JSpinner(yearModel);
        datePanel.add(yearSpinner);
        
        gbc.gridx = 1;
        formPanel.add(datePanel, gbc);

        // Is Beginners Friendly
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Débutants acceptés:"), gbc);
        isBeginnersFriendlyCheckBox = new JCheckBox();
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
            }
            localityComboBox.setRenderer(new LocalityListCellRenderer());
        } catch (LocalitySearchException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        gbc.gridx = 1;
        formPanel.add(localityComboBox, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitButton = new JButton("Créer");
        cancelButton = new JButton("Annuler");
        submitButton.addActionListener(this);
        cancelButton.addActionListener(this);
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submitButton) {
            try {
                // Create new club
                ClubModel club = new ClubModel();
                club.setName(nameField.getText());
                club.setStreetAddress(streetAddressField.getText());
                club.setPhoneNumber(phoneField.getText());
                club.setWebsite(websiteField.getText());
                club.setInstagramProfile(instagramField.getText());
                
                // Date
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, (Integer) yearSpinner.getValue());
                cal.set(Calendar.MONTH, (Integer) monthSpinner.getValue() - 1); // Month is 0-based
                cal.set(Calendar.DAY_OF_MONTH, (Integer) daySpinner.getValue());
                club.setCreationDate(new Date(cal.getTimeInMillis()));
                
                club.setIsBeginnersFriendly(isBeginnersFriendlyCheckBox.isSelected());
                
                LocalityModel selectedLocality = (LocalityModel) localityComboBox.getSelectedItem();
                if (selectedLocality != null) {
                    club.setLocalityID(selectedLocality.getLocalityID());
                }

                // Save club
                clubController.createClub(club);
                
                // Refresh parent panel
                parentPanel.loadClubs();
                
                // Close dialog
                dispose();
                
            } catch (ClubCreationException e) {
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