package iut.sae.algo.sobriete.etu82;

/**
 * Classe proposant une méthode de recherche d'un mot (aiguille) dans une grille
 * de caractères (botte de foin).
 * La recherche s'effectue dans toutes les directions (horizontale, verticale,
 * diagonales et inverses).
 * Version efficacité.
 */
public class Recherche {

    /**
     * Recherche le nombre d'occurrences de l'aiguille dans la botte de foin.
     * 
     * @param botteDeFoin La grille de caractères, sous forme de chaîne avec '\n'
     *                    pour séparer les lignes.
     * @param aiguille    Le mot à rechercher.
     * @return Le nombre d'occurrences trouvées, ou -1 en cas d'erreur.
     */
    public static int chercheMot(String botteDeFoin, String aiguille) {
        // si la botte de foin est null ou n'est pas rectangle
        // ou bien si l'aiguille est vide ou null
        if (botteDeFoin == null || aiguille == null || aiguille.equals("")) {
            // il y a une erreur
            return -1;
        }

        // Calcul du nombre de colonnes (avant le premier '\n')
        int nbColonnes = 0;
        while (nbColonnes < botteDeFoin.length() && botteDeFoin.charAt(nbColonnes) != '\n') {
            nbColonnes++;
        }
        // Calcul du nombre de lignes
        int nbLignes = 1;
        for (int i = nbColonnes; i < botteDeFoin.length(); i += nbColonnes + 1) {
            // si on est à la fin de la botte ou que le caractere est un retour à la ligne
            // on incrémente
            if (i == botteDeFoin.length() || botteDeFoin.charAt(i) == '\n')
                nbLignes++;
            else
                return -1; // sinon il y a une erreur (nombre de colonnes incorrect)
        }

        int nbAiguilles = 0;
        boolean estPalindrome = estPalindrome(aiguille);
        // Parcours de chaque case (ligne, colonne)
        for (int ligne = 0; ligne < nbLignes; ligne++) {
            for (int colonne = 0; colonne < nbColonnes; colonne++) {
                // +1 pour le '\n' à la fin de chaque ligne sauf la dernière
                int index = ligne * (nbColonnes + 1) + colonne;
                if (index >= botteDeFoin.length() || botteDeFoin.charAt(index) == '\n')
                    continue;
                if (botteDeFoin.charAt(index) == aiguille.charAt(0)) {
                    // Pour chaque direction, vérifie si l'aiguille est présente
                    if (verifDirection(botteDeFoin, aiguille, ligne, colonne, nbLignes, nbColonnes, 0, 1))
                        nbAiguilles++; // droite
                    if (aiguille.length() == 1)
                        continue;
                    for (int i = -1; i < 2; i++) {
                        // bas, puis diagonale bas droite, puis diagonale bas gauche
                        if (verifDirection(botteDeFoin, aiguille, ligne, colonne, nbLignes, nbColonnes, 1, i))
                            nbAiguilles++;
                    }
                    if (!estPalindrome) {
                        if (verifDirection(botteDeFoin, aiguille, ligne, colonne, nbLignes, nbColonnes, 0, -1))
                            nbAiguilles++; // gauche
                        for (int i = -1; i < 2; i++) {
                            // diagonale haut gauche, puis gauche, puis diagonale haut droite
                            if (verifDirection(botteDeFoin, aiguille, ligne, colonne, nbLignes, nbColonnes, -1, i))
                                nbAiguilles++;
                        }
                    }
                }
            }
        }
        return nbAiguilles;
    }

    /**
     * Vérifie si l'aiguille est présente à partir de (ligne, colonne) dans la
     * direction (dL, dC).
     */
    private static boolean verifDirection(String botte, String aiguille, int ligne, int colonne, int nbLignes,
            int nbColonnes, int dL, int dC) {
        for (int i = 0; i < aiguille.length(); i++) {
            int l = ligne + i * dL;
            int c = colonne + i * dC;
            if (l < 0 || l >= nbLignes || c < 0 || c >= nbColonnes)
                return false;
            int idx = l * (nbColonnes + 1) + c;
            if (idx >= botte.length() || botte.charAt(idx) == '\n' || botte.charAt(idx) != aiguille.charAt(i))
                return false;
        }
        return true;
    }

    /**
     * Vérifie si l'aiguille est un palindrome.
     *
     * @param aiguille Le mot à tester.
     * @return true si l'aiguille est un palindrome, false sinon.
     */
    private static boolean estPalindrome(String aiguille) {
        for (int i = 0; i < aiguille.length(); i++) {
            if (aiguille.charAt(i) != aiguille.charAt(aiguille.length() - i - 1))
                return false;
        }
        return true;
    }
}


