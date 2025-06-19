package iut.sae.algo.simplicite.etu102;

public class Recherche {

    public static int chercheMot(String botteDeFoin, String aiguille) {
        
        // On valide le test : testErreurBotte
        if (botteDeFoin == null) {
            return -1;
        }

        // On valide le test : testErreurAiguille
        if (aiguille == null) {
            return -1;
        }

        // On valide le test : testVide
        if (botteDeFoin.isEmpty()) {
            return 0;
        }

        String[] lignesTexte = botteDeFoin.split("\n");

        // On valide le test : testErreurBotteIrreguliere
        for (int ligne = 0; ligne < lignesTexte.length - 1; ligne++) {
            if (lignesTexte[ligne].length() != lignesTexte[ligne + 1].length()) {
                return -1;
            }
        }

        char[][] matrice;
        try {
            matrice = conversionMatrice(botteDeFoin);
        } catch (Exception e) {
            return -1;
        }

        int lignes = matrice.length;
        int colonnes = matrice[0].length;
        int tailleMot = aiguille.length();
        int totalOccurrence = 0;

        // Directions : droite, bas, diagonale bas-droite, diagonale bas-gauche
        int[][] directions = { {0, 1}, {1, 0}, {1, 1}, {1, -1} };

        char[] lettresMot = aiguille.toCharArray();
        String motInverse = new StringBuilder(aiguille).reverse().toString();
        boolean verifierInverse = !aiguille.equals(motInverse);
        char[] lettresMotInverse = motInverse.toCharArray();

        // On valide le test : testAiguilleSimple
        if (tailleMot == 1) {
            char caractereRecherche = lettresMot[0];
            for (int i = 0; i < lignes; i++) {
                for (int j = 0; j < colonnes; j++) {
                    if (matrice[i][j] == caractereRecherche) {
                        totalOccurrence = totalOccurrence + 1;
                    }
                }
            }
            return totalOccurrence;
        }

        // On valide les tests : tous ceux avec détection directionnelle (verticale, horizontale, diagonales)
        for (int ligne = 0; ligne < lignes; ligne++) {
            for (int colonne = 0; colonne < colonnes; colonne++) {
                for (int[] direction : directions) {
                    int dx = direction[0];
                    int dy = direction[1];

                    if (motCorrespond(matrice, ligne, colonne, dx, dy, lettresMot)) {
                        totalOccurrence = totalOccurrence + 1;
                    }

                    if (verifierInverse) {
                        if (motCorrespond(matrice, ligne, colonne, dx, dy, lettresMotInverse)) {
                            totalOccurrence = totalOccurrence + 1;
                        }
                    }
                }
            }
        }

        return totalOccurrence;
    }

    // Cette méthode vérifie si le mot correspond dans une direction donnée
    private static boolean motCorrespond(char[][] matrice, int ligneDepart, int colonneDepart, int dx, int dy, char[] mot) {
        int lignes = matrice.length;
        int colonnes = matrice[0].length;

        for (int k = 0; k < mot.length; k++) {
            int x = ligneDepart + k * dx;
            int y = colonneDepart + k * dy;

            if (x < 0) {
                return false;
            }

            if (x >= lignes) {
                return false;
            }

            if (y < 0) {
                return false;
            }

            if (y >= colonnes) {
                return false;
            }

            if (matrice[x][y] != mot[k]) {
                return false;
            }
        }

        return true;
    }



    // Transforme un texte multi-lignes en matrice de caractères
    public static char[][] conversionMatrice(String texte) throws Exception {
        if (texte == null) {
            throw new Exception("Texte null dans conversionMatrice");
        }

        String[] lignes = texte.split("\n");
        int nbLignes = lignes.length;
        int nbColonnes = lignes[0].length();

        char[][] matrice = new char[nbLignes][nbColonnes];

        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                char caractere = lignes[i].charAt(j);
                if (caractere == '.') {
                    matrice[i][j] = '\0';
                } else {
                    matrice[i][j] = caractere;
                }
            }
        }

        return matrice;
    }




}
