package main.viewPackage;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    private JLabel welcomeText;

    public HomePanel() {
        setLayout(new BorderLayout());

        welcomeText = new JLabel(
                """
                <html>
                    <div style='text-align: center; font-family: sans-serif;'>
                        <h1 style='color: darkblue;'>Bienvenue sur padel-manager</h1>
                        <p>L'application d'organisation de match de padel.</p>
                        <p><b>Voici les fonctionnalités de base :</b></p>
                        <ul style='list-style-type: disc; text-align: left; margin-left: 30%;'>
                            <li>Opérations CRUD</li>
                            <li>Recherches</li>
                            <li>Tâche métier</li>
                            <li>Animation de like (Thread)</li>
                        </ul>
                        <p><i>Pour commencer, sélectionnez une option dans le menu.</i></p>
                    </div>
                </html>
                """
        );
        welcomeText.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeText, BorderLayout.CENTER);
    }
}

