package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

// Classe principale pour la fenêtre du jeu
public class GameWindow extends JFrame {
    // Variables pour gérer la difficulté, le mot à deviner, les emplacements du mot, etc.
    private String difficulty; // Difficulté sélectionnée par le joueur
    private String wordToGuess; // Mot actuel à deviner
    private JLabel[] wordSlots; // Emplacements pour afficher les lettres du mot
    private JPanel lettersPanel; // Panneau pour les boutons des lettres
    private ArrayList<JButton> letterButtons; // Liste des boutons de lettres
    private int score = 0; // Score du joueur
    private int level = 1; // Niveau actuel
    private final int maxLevels = 10; // Nombre maximum de niveaux
    private JLabel scoreLabel; // Étiquette pour afficher le score
    private JLabel levelLabel; // Étiquette pour afficher le niveau
    private JButton submitButton; // Bouton pour soumettre la réponse
    private JButton removeLetterButton; // Bouton pour supprimer une lettre
    private JButton skipLevelButton; // Bouton pour passer un niveau

    // Constructeur pour initialiser la fenêtre du jeu
    public GameWindow(String difficulty, int level, int score) {
        this.difficulty = difficulty; // Définir la difficulté
        this.level = level; // Définir le niveau
        this.score = score; // Définir le score
        this.wordToGuess = getRandomWord(); // Générer un mot aléatoire selon la difficulté
        this.wordSlots = new JLabel[wordToGuess.length()]; // Initialiser les emplacements pour le mot
        this.letterButtons = new ArrayList<>(); // Initialiser la liste des boutons de lettres

        // Configuration de la fenêtre
        setTitle("Award Guessing Game - " + difficulty); // Titre de la fenêtre
        setSize(500, 600); // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer la fenêtre en quittant
        setLocationRelativeTo(null); // Centrer la fenêtre à l'écran

        // Disposition verticale (empile les éléments de haut en bas)
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Ajouter les différents panneaux
        createTopPanel(); // Panneau pour afficher le niveau et le score
        createButtonPanel(); // Panneau pour les boutons d'action
        createWordSlots(); // Panneau pour afficher les emplacements du mot
        createLettersPanel(); // Panneau pour les boutons de lettres

        setVisible(true); // Afficher la fenêtre
    }

    // Méthode pour obtenir un mot aléatoire selon la difficulté sélectionnée
    private String getRandomWord() {
        String[] easyWords = {"CAT", "DOG", "SUN"}; // Liste des mots faciles
        String[] mediumWords = {"APPLE", "TABLE", "CHAIR"}; // Liste des mots moyens
        String[] hardWords = {"ELEPHANT", "COMPUTER", "BICYCLE"}; // Liste des mots difficiles

        switch (difficulty) {
            case "Easy":
                return easyWords[(int) (Math.random() * easyWords.length)]; // Choisir un mot aléatoire parmi les mots faciles
            case "Medium":
                return mediumWords[(int) (Math.random() * mediumWords.length)]; // Mots moyens
            case "Hard":
                return hardWords[(int) (Math.random() * hardWords.length)]; // Mots difficiles
            default:
                return "ERROR"; // Retourner "ERROR" si la difficulté est invalide
        }
    }

    // Créer le panneau supérieur pour afficher le niveau et le score
    private void createTopPanel() {
        JPanel topPanel = new JPanel(); // Créer un nouveau panneau
        topPanel.setLayout(new GridLayout(1, 2)); // Disposition en grille avec 1 ligne et 2 colonnes

        levelLabel = new JLabel("Level " + level + "/" + maxLevels, SwingConstants.CENTER); // Étiquette du niveau
        levelLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Définir une police en gras

        scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER); // Étiquette du score
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));

        topPanel.add(levelLabel); // Ajouter l'étiquette du niveau au panneau
        topPanel.add(scoreLabel); // Ajouter l'étiquette du score au panneau
        add(topPanel); // Ajouter le panneau à la fenêtre
    }

    // Créer le panneau pour les boutons d'action
    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(); // Créer un panneau pour les boutons
        buttonPanel.setLayout(new FlowLayout()); // Disposition en ligne

        removeLetterButton = new JButton("Remove Letter"); // Bouton pour supprimer une lettre
        removeLetterButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeLetterButton.addActionListener(new RemoveLetterButtonListener()); // Ajouter un écouteur d'événement

        skipLevelButton = new JButton("Skip Level"); // Bouton pour passer un niveau
        skipLevelButton.setFont(new Font("Arial", Font.BOLD, 14));
        skipLevelButton.addActionListener(new SkipLevelButtonListener()); // Ajouter un écouteur d'événement

        submitButton = new JButton("Submit"); // Bouton pour soumettre la réponse
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.addActionListener(e -> checkIfWordIsComplete()); // Vérifier si le mot est complet

        buttonPanel.add(removeLetterButton); // Ajouter le bouton "Remove Letter" au panneau
        buttonPanel.add(skipLevelButton); // Ajouter le bouton "Skip Level" au panneau
        buttonPanel.add(submitButton); // Ajouter le bouton "Submit" au panneau
        add(buttonPanel); // Ajouter le panneau à la fenêtre
    }

    // Créer le panneau pour afficher les emplacements du mot
    private void createWordSlots() {
        JPanel wordPanel = new JPanel(); // Créer un nouveau panneau
        wordPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Centrer les éléments

        for (int i = 0; i < wordToGuess.length(); i++) {
            wordSlots[i] = new JLabel("_"); // Initialiser chaque emplacement avec "_"
            wordSlots[i].setFont(new Font("Arial", Font.BOLD, 30)); // Police en gras et grande taille
            wordSlots[i].setPreferredSize(new Dimension(40, 50)); // Taille fixe pour chaque emplacement
            wordSlots[i].setHorizontalAlignment(SwingConstants.CENTER); // Centrer le texte
            wordPanel.add(wordSlots[i]); // Ajouter l'emplacement au panneau
        }

        add(wordPanel); // Ajouter le panneau à la fenêtre
    }

    // Créer le panneau pour les boutons des lettres
    private void createLettersPanel() {
        lettersPanel = new JPanel(); // Créer un nouveau panneau
        lettersPanel.setLayout(new GridLayout(2, 5, 10, 10)); // 2 lignes, 5 colonnes, avec espacement

        // Préparer une liste de lettres
        ArrayList<Character> letterChoices = new ArrayList<>();
        for (char c : wordToGuess.toCharArray()) {
            letterChoices.add(c); // Ajouter chaque lettre du mot
        }
        while (letterChoices.size() < 10) { // Ajouter des lettres aléatoires jusqu'à atteindre 10 lettres
            letterChoices.add((char) ('A' + (int) (Math.random() * 26)));
        }
        Collections.shuffle(letterChoices); // Mélanger les lettres

        for (char letter : letterChoices) {
            JButton letterButton = new JButton(String.valueOf(letter)); // Créer un bouton pour chaque lettre
            letterButton.setFont(new Font("Arial", Font.BOLD, 20)); // Police en gras
            letterButton.addActionListener(new LetterClickListener(letterButton, letter)); // Ajouter un écouteur d'événement
            letterButtons.add(letterButton); // Ajouter le bouton à la liste
            lettersPanel.add(letterButton); // Ajouter le bouton au panneau
        }

        add(lettersPanel); // Ajouter le panneau à la fenêtre
    }

    // Méthode pour vérifier si le mot est complété
    private void checkIfWordIsComplete() {
        StringBuilder guessedWord = new StringBuilder(); // Construire le mot deviné
        for (JLabel slot : wordSlots) {
            guessedWord.append(slot.getText()); // Ajouter chaque lettre à la chaîne
        }

        if (guessedWord.toString().equals(wordToGuess)) { // Si le mot deviné est correct
            score += 10 * wordToGuess.length(); // Ajouter des points au score
            JOptionPane.showMessageDialog(null, "Correct! The word was: " + wordToGuess); // Message de succès
            nextLevel(); // Passer au niveau suivant
        } else {
            JOptionPane.showMessageDialog(null, "3asba"); // Message d'erreur
        }
    }

    // Méthode pour passer au niveau suivant
    private void nextLevel() {
        if (level >= maxLevels) { // Si le joueur atteint le dernier niveau
            JOptionPane.showMessageDialog(null, "Game Over! Final Score: " + score); // Message de fin de jeu
            dispose(); // Fermer la fenêtre
            new DifficultySelection(); // Retourner au menu de sélection de difficulté
            return;
        }

        level++; // Incrémenter le niveau
        dispose(); // Fermer la fenêtre actuelle
        new GameWindow(difficulty, level, score); // Créer une nouvelle fenêtre pour le niveau suivant
    }

    // Écouteur pour le bouton "Remove Letter"
    private class RemoveLetterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Parcourir les emplacements du mot à l'envers
            for (int i = wordSlots.length - 1; i >= 0; i--) {
                if (!wordSlots[i].getText().equals("_")) { // Si un emplacement est rempli
                    String removedLetter = wordSlots[i].getText(); // Récupérer la lettre
                    wordSlots[i].setText("_"); // Réinitialiser l'emplacement à "_"

                    // Réactiver le bouton correspondant
                    for (JButton button : letterButtons) {
                        if (button.getText().equals(removedLetter) && !button.isEnabled()) {
                            button.setEnabled(true); // Réactiver le bouton
                            break;
                        }
                    }
                    break; // Arrêter après avoir supprimé une lettre
                }
            }
        }
    }

    // Écouteur pour le bouton "Skip Level"
    private class SkipLevelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            score -= 10; // Réduire le score
            scoreLabel.setText("Score: " + score); // Mettre à jour l'affichage du score
            nextLevel(); // Passer au niveau suivant
        }
    }

    // Écouteur pour les boutons des lettres
    private class LetterClickListener implements ActionListener {
        private JButton button; // Bouton cliqué
        private char letter; // Lettre associée au bouton

        public LetterClickListener(JButton button, char letter) {
            this.button = button;
            this.letter = letter;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Parcourir les emplacements pour trouver le premier emplacement vide
            for (int i = 0; i < wordSlots.length; i++) {
                if (wordSlots[i].getText().equals("_")) { // Si l'emplacement est vide
                    wordSlots[i].setText(String.valueOf(letter)); // Ajouter la lettre à l'emplacement
                    button.setEnabled(false); // Désactiver le bouton
                    break; // Arrêter après avoir rempli un emplacement
                }
            }
        }
    }
}
