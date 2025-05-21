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
    private JMenu menuThread;
    private JMenuItem home;
    private JMenuItem exit;
    private JMenuItem crudPlayer;
    private JMenuItem crudClub;
    private JMenuItem researchPlayer;
    private JMenuItem researchTournamentMatches;
    private JMenuItem researchPlayerRegion;

    private JMenuItem ballAnimation;

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

        // CRUD
        menuPlayers = new JMenu("CRUD");

        crudPlayer = new JMenuItem("Player");
        crudPlayer.addActionListener(this);

        crudClub = new JMenuItem("Club");
        crudClub.addActionListener(this);

        menuPlayers.add(crudPlayer);
        menuPlayers.addSeparator();
        menuPlayers.add(crudClub);

        // Recherche
        menuSearch = new JMenu("Recherche");

        researchPlayer = new JMenuItem("Afficher information de joueurs");
        researchPlayer.addActionListener(this);

        researchTournamentMatches = new JMenuItem("Afficher les matchs d’un tournoi");
        researchTournamentMatches.addActionListener(this);

        researchPlayerRegion = new JMenuItem("Recherche joueurs selon “region” et affichage en ordre décroissant sur la valeur de “elopoint”");
        researchPlayerRegion.addActionListener(this);

        menuSearch.add(researchPlayer);
        menuSearch.addSeparator();
        menuSearch.add(researchTournamentMatches);
        menuSearch.addSeparator();
        menuSearch.add(researchPlayerRegion);

        // Thread
        menuThread = new JMenu("Thread");

        ballAnimation = new JMenuItem("Thread");
        ballAnimation.addActionListener(this);

        menuThread.add(ballAnimation);

        // Bar
        menuBar = new JMenuBar();

        menuBar.add(menuMain);
        menuBar.add(menuPlayers);
        menuBar.add(menuSearch);

        // Ajout de la barre de menu à la fenêtre principale
        this.mainWindow.setJMenuBar(menuBar);
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
        } else if (e.getSource() == exit) {
            mainWindow.exit();
        } else if (e.getSource() == ballAnimation) {
            mainWindow.switchPanel(mainWindow.getThread());
        }
    }
} 