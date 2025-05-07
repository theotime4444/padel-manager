package main.viewPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar implements ActionListener {
    private MainWindow mainWindow;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenu menuPlayers;
    private JMenuItem home;
    private JMenuItem exit;
    private JMenuItem researchPlayer;
    private JMenuItem playerList;

    public MenuBar(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuPlayers = new JMenu("Joueurs");

        // Menu principal
        home = new JMenuItem("Retour à l'accueil");
        home.addActionListener(this);

        exit = new JMenuItem("Quitter le programme");
        exit.addActionListener(this);

        // Menu Joueurs
        researchPlayer = new JMenuItem("Rechercher un joueur");
        researchPlayer.addActionListener(this);

        playerList = new JMenuItem("Liste des joueurs");
        playerList.addActionListener(this);

        // Ajout des éléments au menu principal
        menu.add(home);
        menu.addSeparator();
        menu.add(exit);

        // Ajout des éléments au menu Joueurs
        menuPlayers.add(researchPlayer);
        menuPlayers.addSeparator();
        menuPlayers.add(playerList);

        // Ajout des menus à la barre de menu
        menuBar.add(menu);
        menuBar.add(menuPlayers);

        // Ajout de la barre de menu à la fenêtre principale
        this.mainWindow.setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == home) {
            mainWindow.switchPanel(mainWindow.getHomePanel());
        } else if (e.getSource() == researchPlayer) {
            mainWindow.switchPanel(mainWindow.getResearchPlayer());
        } else if (e.getSource() == exit) {
            mainWindow.exit();
        }
        // Le playerList sera implémenté plus tard
    }
} 