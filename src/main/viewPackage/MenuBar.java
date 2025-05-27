package main.viewPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar implements ActionListener {
    private MainWindow mainWindow;
    private JMenuBar menuBar;
    private JMenu menuMain;
    private JMenu menuPlayers;
    private JMenu menuSearch;
    private JMenu menuBusiness;
    private JMenu menuThread;
    private JMenuItem home;
    private JMenuItem exit;
    private JMenuItem crudPlayer;
    private JMenuItem crudClub;
    private JMenuItem researchPlayer;
    private JMenuItem researchTournamentMatches;
    private JMenuItem researchPlayerRegion;
    private JMenuItem businessStatPlayer;
    private JMenuItem businessFindPlayer;
    private JMenuItem threadBallAnimation;

    public MenuBar(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        // Menu principal
        menuMain = new JMenu("Menu");

        home = new JMenuItem("Retour à l'accueil");
        home.addActionListener(this);

        exit = new JMenuItem("Quitter le programme");
        exit.addActionListener(this);

        menuMain.add(home);
        menuMain.addSeparator();
        menuMain.add(exit);

        // Menu CRUD
        menuPlayers = new JMenu("CRUD");

        crudPlayer = new JMenuItem("Player");
        crudPlayer.addActionListener(this);

        crudClub = new JMenuItem("Club");
        crudClub.addActionListener(this);

        menuPlayers.add(crudPlayer);
        menuPlayers.addSeparator();
        menuPlayers.add(crudClub);

        // Menu recherche
        menuSearch = new JMenu("Recherche");

        researchPlayer = new JMenuItem("Afficher information de joueurs");
        researchPlayer.addActionListener(this);

        researchTournamentMatches = new JMenuItem("Afficher les matchs d'un tournoi");
        researchTournamentMatches.addActionListener(this);

        researchPlayerRegion = new JMenuItem("Recherche joueurs selon \"region\" et affichage en ordre décroissant sur la valeur de \"elopoint\"");
        researchPlayerRegion.addActionListener(this);

        menuSearch.add(researchPlayer);
        menuSearch.addSeparator();
        menuSearch.add(researchTournamentMatches);
        menuSearch.addSeparator();
        menuSearch.add(researchPlayerRegion);

        // Menu métier
        menuBusiness = new JMenu("Business");

        businessStatPlayer = new JMenuItem("Statistiques du joueur");
        businessStatPlayer.addActionListener(this);

        businessFindPlayer = new JMenuItem("Suggestion de partenaires");
        businessFindPlayer.addActionListener(this);

        menuBusiness.add(businessStatPlayer);
        menuBusiness.addSeparator();
        menuBusiness.add(businessFindPlayer);

        // Menu thread
        menuThread = new JMenu("Thread");

        threadBallAnimation = new JMenuItem("Animation de balle");
        threadBallAnimation.addActionListener(this);

        menuThread.add(threadBallAnimation);

        // Barre de menu
        menuBar = new JMenuBar();

        menuBar.add(menuMain);
        menuBar.add(menuPlayers);
        menuBar.add(menuSearch);
        menuBar.add(menuBusiness);
        menuBar.add(menuThread);
        mainWindow.setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == home) {
            mainWindow.switchPanel(mainWindow.getHomePanel());
        } else if (e.getSource() == crudPlayer) {
            mainWindow.switchPanel(mainWindow.getCrudPlayer());
        } else if (e.getSource() == crudClub) {
            mainWindow.switchPanel(mainWindow.getCrudClub());
        } else if (e.getSource() == researchPlayer) {
            mainWindow.switchPanel(mainWindow.getResearchPlayer());
        } else if (e.getSource() == researchTournamentMatches) {
            mainWindow.switchPanel(mainWindow.getResearchTournamentMatches());
        } else if (e.getSource() == researchPlayerRegion) {
            mainWindow.switchPanel(mainWindow.getResearchPlayerRegion());
        } else if (e.getSource() == businessStatPlayer) {
            mainWindow.switchPanel(mainWindow.getBusinessStatPlayer());
        } else if (e.getSource() == businessFindPlayer) {
            mainWindow.switchPanel(mainWindow.getBusinessFindPlayer());
        } else if (e.getSource() == threadBallAnimation) {
            mainWindow.switchPanel(mainWindow.getThreadBallAnimation());
        } else if (e.getSource() == exit) {
            mainWindow.exit();
        }
    }
} 