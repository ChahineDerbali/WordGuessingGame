package ui;

// Importation des bibliothèques nécessaires pour créer l'interface utilisateur
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Classe pour la fenêtre de sélection de difficulté
public class DifficultySelection extends JFrame {

    // Constructeur pour initialiser la fenêtre de sélection de difficulté
    public DifficultySelection() {
        setTitle("Select Difficulty"); // Définir le titre de la fenêtre
        setSize(400, 300); // Définir la taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer l'application à la fermeture de la fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre à l'écran
        setLayout(new GridLayout(4, 1, 10, 10)); // Disposition en grille (4 lignes, 1 colonne)

        // Création et configuration de l'étiquette pour le titre
        JLabel titleLabel = new JLabel("Select Difficulty Level", SwingConstants.CENTER); 
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Définir la police et le style
        add(titleLabel); // Ajouter l'étiquette à la fenêtre

        // Création des boutons pour chaque niveau de difficulté
        JButton easyButton = new JButton("Easy"); // Bouton "Facile"
        JButton mediumButton = new JButton("Medium"); // Bouton "Moyen"
        JButton hardButton = new JButton("Hard"); // Bouton "Difficile"

        // Configuration de la police des boutons
        easyButton.setFont(new Font("Arial", Font.BOLD, 18));
        mediumButton.setFont(new Font("Arial", Font.BOLD, 18));
        hardButton.setFont(new Font("Arial", Font.BOLD, 18));

        // Ajout d'écouteurs d'événements pour les boutons
        easyButton.addActionListener(new DifficultyButtonListener("Easy")); // Écouteur pour le bouton "Facile"
        mediumButton.addActionListener(new DifficultyButtonListener("Medium")); // Écouteur pour le bouton "Moyen"
        hardButton.addActionListener(new DifficultyButtonListener("Hard")); // Écouteur pour le bouton "Difficile"

        // Ajouter les boutons à la fenêtre
        add(easyButton);
        add(mediumButton);
        add(hardButton);

        // Rendre la fenêtre visible
        setVisible(true);
    }

    // Classe interne pour gérer les événements des boutons de difficulté
    private class DifficultyButtonListener implements ActionListener {
        private String difficulty; // Stocker la difficulté sélectionnée

        // Constructeur qui initialise la difficulté sélectionnée
        public DifficultyButtonListener(String difficulty) {
            this.difficulty = difficulty;
        }

        // Méthode appelée lorsque le bouton est cliqué
        @Override
        public void actionPerformed(ActionEvent e) {
            // Créer une nouvelle fenêtre de jeu avec la difficulté sélectionnée
            new GameWindow(difficulty, 1, 0); // Commence au niveau 1 avec un score de 0
            dispose(); // Fermer la fenêtre actuelle (sélection de difficulté)
        }
    }

    // Méthode principale pour lancer l'application
    public static void main(String[] args) {
        new DifficultySelection(); // Lancer la fenêtre de sélection de difficulté
    }
}
