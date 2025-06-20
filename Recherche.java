package iut.sae.algo.sobriete.etu96;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe pour la recherche de mots dans une grille de caractères.
 * 
 * Ici j'utilise un HashSet qui nous permet de transformer par exemple un String qui pourrait etre : "ligneDepart,colonneDepart->ligneFin,colonneFin" 
 * en une clé (un nombre) et ainsi de chercher s'il est contenu dans le HashSet a la place d'utiliser un tableau 4D par exemple qui consomme beaucoup de ressource
 */
public class Recherche {

    // Constantes conservées pour la compatibilité
    private static final int POSITION_NON_TROUVEE = -1;
    private static final int POSITION_VIDE = 0;
    
    // Réutilisation d'objets pour éviter les allocations répétées
    private static final int[] DIRECTIONS_X = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] DIRECTIONS_Y = {-1, 0, 1, -1, 1, -1, 0, 1};

    /**
     * Recherche le nombre d'occurrences d'un mot dans une grille de caractères.
     * Le mot peut être trouvé dans 8 directions : horizontal, vertical et diagonal.
     * Mais ! on ne compte pas 2 fois les palindromes
     * 
     * @param texteGrille La chaîne représentant la grille (lignes séparées par \n)
     * @param motRecherche Le mot à rechercher
     * @return Le nombre d'occurrences trouvées, -1 en cas d'erreur
     */
    public static int chercheMot(String texteGrille, String motRecherche) {
        // Étape 1 : Validation des paramètres d'entrée
        if (!parametresSontValides(texteGrille, motRecherche)) {
            return POSITION_NON_TROUVEE;
        }

        // Étape 2 : Cas particulier - paramètres vides
        if (texteGrille.length() == 0 || motRecherche.length() == 0) {
            return POSITION_VIDE;
        }

        // Étape 3 : Création de la grille
        String[] lignesGrille = creerGrilleOptimisee(texteGrille);
        if (lignesGrille == null) {
            return POSITION_NON_TROUVEE;
        }

        // Étape 4 : Cas particulier - recherche d'un seul caractère
        if (motRecherche.length() == 1) {
            return compterOccurrencesCaractereUniqueOptimise(texteGrille, motRecherche.charAt(0));
        }

        // Étape 5 : Recherche complète du mot dans toutes les directions
        return rechercherMotDansToutesDirectionsOptimise(lignesGrille, motRecherche);
    }

    /**
     * Valide que les paramètres d'entrée ne sont pas null.
     */
    private static boolean parametresSontValides(String texteGrille, String motRecherche) {
        return texteGrille != null && motRecherche != null;
    }

    /**
     * Création de grille
     */
    private static String[] creerGrilleOptimisee(String texteGrille) {
        // Split direct - plus efficace que le parsing manuel
        String[] lignes = texteGrille.split("\n", -1);
        
        // Cas spécial : pas de \n dans le texte
        if (lignes.length == 1 && !texteGrille.contains("\n")) {
            return lignes;
        }
        
        // Validation : toutes les lignes doivent avoir la même longueur
        int largeurAttendue = lignes[0].length();
        for (int i = 1; i < lignes.length; i++) {
            if (lignes[i].length() != largeurAttendue) {
                return null; // Grille irrégulière
            }
        }
        
        return lignes;
    }

    /**
     * Compte les occurrences d'un caractère unique 
     */
    private static int compterOccurrencesCaractereUniqueOptimise(String texteGrille, char caractereRecherche) {
        int nombreOccurrences = 0;
        for (int i = 0; i < texteGrille.length(); i++) {
            if (texteGrille.charAt(i) == caractereRecherche) {
                nombreOccurrences++;
            }
        }
        return nombreOccurrences;
    }

    /**
     * Recherche optimisée avec HashSet au lieu du tableau 4D
     */
    private static int rechercherMotDansToutesDirectionsOptimise(String[] lignesGrille, String motRecherche) {
        int hauteurGrille = lignesGrille.length;
        int largeurGrille = lignesGrille[0].length();
        int longueurMot = motRecherche.length();
        
        // HashSet plus économe qu'un tableau 4D
        Set<String> motDejaComptabilise = new HashSet<>();
        int nombreOccurrencesTotales = 0;

        // Parcourir chaque position de la grille comme point de départ
        for (int ligneDepart = 0; ligneDepart < hauteurGrille; ligneDepart++) {
            for (int colonneDepart = 0; colonneDepart < largeurGrille; colonneDepart++) {
                
                // Tester les 8 directions possibles depuis cette position
                nombreOccurrencesTotales += rechercherDepuisPositionOptimise(
                    lignesGrille, motRecherche, 
                    ligneDepart, colonneDepart,
                    longueurMot, hauteurGrille, largeurGrille,
                    motDejaComptabilise
                );
            }
        }

        return nombreOccurrencesTotales;
    }

    /**
     * Version optimisée de la recherche depuis une position
     * Utilise des tableaux pré-calculés pour les directions
     */
    private static int rechercherDepuisPositionOptimise(String[] lignesGrille, String motRecherche,
                                                       int ligneDepart, int colonneDepart,
                                                       int longueurMot, int hauteurGrille, int largeurGrille,
                                                       Set<String> motDejaComptabilise) {
        int occurrencesDepuisCettePosition = 0;

        // Utilisation des tableaux pré-calculés (plus efficace que des boucles imbriquées par exemple)
        for (int directionIndex = 0; directionIndex < 8; directionIndex++) {
            int deltaX = DIRECTIONS_X[directionIndex];
            int deltaY = DIRECTIONS_Y[directionIndex];

            // Vérifier si le mot peut tenir dans cette direction
            if (motPeutTenirDansDirection(ligneDepart, colonneDepart, deltaX, deltaY, 
                                        longueurMot, hauteurGrille, largeurGrille)) {
                
                // Vérifier si le mot est présent dans cette direction
                if (motEstPresentDansDirectionOptimise(lignesGrille, ligneDepart, colonneDepart, 
                                                     deltaX, deltaY, motRecherche, longueurMot)) {
                    
                    // Calculer la position de fin du mot
                    int ligneFinMot = ligneDepart + (longueurMot - 1) * deltaY;
                    int colonneFinMot = colonneDepart + (longueurMot - 1) * deltaX;

                    // Créer les clés pour éviter les doublons
                    String cle1 = ligneDepart + "," + colonneDepart + "->" + ligneFinMot + "," + colonneFinMot;
                    String cle2 = ligneFinMot + "," + colonneFinMot + "->" + ligneDepart + "," + colonneDepart;
                    
                    if (!motDejaComptabilise.contains(cle1) && !motDejaComptabilise.contains(cle2)) {
                        motDejaComptabilise.add(cle1);
                        occurrencesDepuisCettePosition++;
                    }
                }
            }
        }

        return occurrencesDepuisCettePosition;
    }

    /**
     * Vérifie si le mot est présent 
     */
    private static boolean motEstPresentDansDirectionOptimise(String[] lignesGrille,
                                                            int ligneDepart, int colonneDepart,
                                                            int deltaX, int deltaY,
                                                            String motRecherche, int longueurMot) {

        for (int positionCaractere = 0; positionCaractere < longueurMot; positionCaractere++) {
            // Calculer la position actuelle dans la grille
            int ligneActuelle = ligneDepart + positionCaractere * deltaY;
            int colonneActuelle = colonneDepart + positionCaractere * deltaX;

            // Comparer les caractères directement
            if (lignesGrille[ligneActuelle].charAt(colonneActuelle) != motRecherche.charAt(positionCaractere)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Vérifie si un mot peut tenir dans une direction donnée sans sortir de la grille.
     */
    private static boolean motPeutTenirDansDirection(int ligneDepart, int colonneDepart,
                                                   int deltaX, int deltaY,
                                                   int longueurMot, int hauteurGrille, int largeurGrille) {

        // Calculer la position finale du mot
        int ligneFinale = ligneDepart + (longueurMot - 1) * deltaY;
        int colonneFinale = colonneDepart + (longueurMot - 1) * deltaX;

        // Vérifier que la position finale est dans les limites de la grille
        return ligneFinale >= 0 && ligneFinale < hauteurGrille && 
               colonneFinale >= 0 && colonneFinale < largeurGrille;
    }

}



