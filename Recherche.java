package iut.sae.algo.efficacite.etu6;

public class Recherche {

    public static int chercheMot(String botteDeFoin, String aiguille) {
        // Vérification des paramètres nulls
        if (botteDeFoin == null || aiguille == null) {
            return -1;
        }

        // Cas de la chaîne vide
        if (botteDeFoin.isEmpty()) {
            return 0;
        }

        // Cas de l'aiguille vide
        if (aiguille.isEmpty()) {
            return 0;
        }

        // Calcul des dimensions de la matrice
        int largeur = calculerLargeurPremiereLigne(botteDeFoin);
        int hauteur = calculerHauteur(botteDeFoin);
        
        // Vérification que la matrice est rectangulaire
        if (!estMatriceRectangulaire(botteDeFoin, largeur, hauteur)) {
            return -1;
        }

        // Vérification si l'aiguille est un palindrome
        boolean estPalindrome = estPalindrome(aiguille);
        
        int compteur = 0;
        
        // Les 8 directions possibles : deltaLigne, deltaColonne
        int[] deltaLignes = {0, 0, 1, -1, 1, -1, 1, -1};
        int[] deltaColonnes = {1, -1, 0, 0, 1, -1, -1, 1};

        // Parcourir chaque position de départ
        for (int ligne = 0; ligne < hauteur; ligne++) {
            for (int colonne = 0; colonne < largeur; colonne++) {
                
                // Tester chaque direction
                for (int dir = 0; dir < 8; dir++) {
                    if (rechercheMotDansDirection(botteDeFoin, aiguille, ligne, colonne, 
                                                deltaLignes[dir], deltaColonnes[dir], largeur)) {
                        compteur++;
                    }
                }
            }
        }

        // Pour les palindromes et caractères uniques, diviser par 2
        if (estPalindrome || aiguille.length() == 1) {
            return compteur / 2;
        }

        return compteur;
    }

    /**
     * Calcule la largeur de la première ligne
     */
    private static int calculerLargeurPremiereLigne(String texte) {
        int largeur = 0;
        for (int i = 0; i < texte.length(); i++) {
            if (texte.charAt(i) == '\n') {
                break;
            }
            largeur++;
        }
        return largeur;
    }

    /**
     * Calcule le nombre de lignes en comptant les '\n'
     */
    private static int calculerHauteur(String texte) {
        int hauteur = 1;
        for (int i = 0; i < texte.length(); i++) {
            if (texte.charAt(i) == '\n') {
                hauteur++;
            }
        }
        return hauteur;
    }

    /**
     * Vérifie si la matrice est rectangulaire
     */
    private static boolean estMatriceRectangulaire(String texte, int largeurAttendue, int hauteur) {
        int ligneActuelle = 0;
        int colonneActuelle = 0;
        
        for (int i = 0; i < texte.length(); i++) {
            char c = texte.charAt(i);
            
            if (c == '\n') {
                if (colonneActuelle != largeurAttendue) {
                    return false;
                }
                ligneActuelle++;
                colonneActuelle = 0;
            } else {
                colonneActuelle++;
                if (colonneActuelle > largeurAttendue) {
                    return false;
                }
            }
        }
        
        // Vérifier la dernière ligne
        return colonneActuelle == largeurAttendue;
    }

    /**
     * Obtient le caractère à la position (ligne, colonne) dans la chaîne
     */
    private static char obtenirCaractere(String texte, int ligne, int colonne, int largeur) {
        // Position dans la chaîne = ligne * (largeur + 1) + colonne
        // Le +1 est pour le caractère '\n'
        int position = ligne * (largeur + 1) + colonne;
        return texte.charAt(position);
    }

    /**
     * Recherche un mot dans une direction spécifique à partir d'une position donnée
     */
    private static boolean rechercheMotDansDirection(String matrice, String mot, 
                                                   int ligneDepart, int colonneDepart, 
                                                   int deltaLigne, int deltaColonne, int largeur) {
        int hauteur = calculerHauteur(matrice);
        
        // Vérifier si le mot peut tenir dans cette direction
        int ligneFin = ligneDepart + (mot.length() - 1) * deltaLigne;
        int colonneFin = colonneDepart + (mot.length() - 1) * deltaColonne;
        
        if (ligneFin < 0 || ligneFin >= hauteur || colonneFin < 0 || colonneFin >= largeur) {
            return false;
        }

        // Vérifier chaque caractère du mot
        for (int i = 0; i < mot.length(); i++) {
            int ligneActuelle = ligneDepart + i * deltaLigne;
            int colonneActuelle = colonneDepart + i * deltaColonne;
            
            char caractereMatrice = obtenirCaractere(matrice, ligneActuelle, colonneActuelle, largeur);
            
            if (caractereMatrice != mot.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Vérifie si une chaîne est un palindrome
     */
    private static boolean estPalindrome(String chaine) {
        int longueur = chaine.length();
        for (int i = 0; i < longueur / 2; i++) {
            if (chaine.charAt(i) != chaine.charAt(longueur - 1 - i)) {
                return false;
            }
        }
        return true;
    }
}
