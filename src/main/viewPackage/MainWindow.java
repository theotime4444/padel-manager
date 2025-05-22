package main.viewPackage;

import main.controllerPackage.ConnectionDataAccessController;
import main.exceptionPackage.*;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static final String WINDOW_TITLE = "Padel manager";
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int X_BOUNDS = (SCREEN_SIZE.width - FRAME_WIDTH) / 2;
    private static final int Y_BOUNDS = (SCREEN_SIZE.height - FRAME_HEIGHT) / 2;

    private JPanel homePanel;
    private JPanel researchPlayer;
    private JPanel crudPlayer;
    private JPanel crudClub;
    private JPanel researchTournamentMatches;
    private JPanel researchPlayerRegion;
    private JPanel threadBallAnimation;
    private JPanel businessStatPlayer;
    private JPanel businessFindPlayer;

    private MenuBar menuBar;
    private ConnectionDataAccessController connectionController;

    public MainWindow() throws ConnectionDataAccessException {
        super(WINDOW_TITLE);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(X_BOUNDS, Y_BOUNDS, FRAME_WIDTH, FRAME_HEIGHT);

        connectionController = new ConnectionDataAccessController();

        homePanel = new HomePanel();
        // CRUD
        crudPlayer = new CrudPlayer(this);
        crudClub = new CrudClub(this);
        // Research
        researchPlayer = new ResearchPlayer(this);
        researchTournamentMatches = new ResearchTournamentMatches(this);
        researchPlayerRegion = new ResearchPlayerRegion(this);
        // Business
        businessStatPlayer = new BusinessStatPlayer();
        businessFindPlayer = new BusinessFindPlayer();
        // Thread
        threadBallAnimation = new ThreadBallAnimation();

        menuBar = new MenuBar(this);

        switchPanel(homePanel);
        setVisible(true);
    }

    public void exit() {
        try {
            connectionController.closeConnection();
            System.exit(0);
        } catch (ConnectionDataAccessException e) {
            displayError(e.toString());
        }
    }

    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public void displayMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void switchPanel(JPanel panel) {
        paintPanel(panel);
    }

    private void paintPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
    }

    public JPanel getHomePanel() {
        return homePanel;
    }


    public JPanel getCrudPlayer() {
        return crudPlayer;
    }


    public JPanel getCrudClub() {
        return crudClub;
    }

    public JPanel getResearchPlayer() {
        return researchPlayer;
    }

    public JPanel getResearchTournamentMatches() {
        return researchTournamentMatches;
    }

    public JPanel getResearchPlayerRegion() {
        return researchPlayerRegion;
    }

    public JPanel getThreadBallAnimation() {
        return threadBallAnimation;
    }

    public JPanel getBusinessStatPlayer() {
        return businessStatPlayer;
    }

    public JPanel getBusinessFindPlayer() {
        return businessFindPlayer;
    }
    
    
}
