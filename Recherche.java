package iut.sae.algo.efficacite.etu18;

public class Recherche {

    /**
     * Recherche le nombre d'occurrences d'une aiguille dans une botte de foin.
     * Algorithme efficace
     *
     * @param botteDeFoin la botte de foin sous forme de chaîne de caractères
     * @param aiguille    l'aiguille à rechercher
     * @return le nombre d'occurrences de l'aiguille dans la botte de foin,
     *         ou -1 en cas d'erreur (aiguille ou botteDeFoin null, ou botteDeFoin
     *         non rectangulaire)
     */
    public static int chercheMot(String botteDeFoin, String aiguille) {
        /* Tests */

        // tests préalables
        if (botteDeFoin == null || aiguille == null) {
            return -1;
        }

        // calcul du nombre de lignes (nbL) et de colonnes (nbC)
        int nbL = 1; // au moins une ligne
        int nbC = 0;
        int tempColonnes = 0;

        for (int i = 0; i < botteDeFoin.length(); i++) {
            char c = botteDeFoin.charAt(i);
            if (c == '\n') {
                nbL++;
                if (tempColonnes > nbC) {
                    nbC = tempColonnes;
                }
                tempColonnes = 0;
            } else {
                tempColonnes++;
            }
        }

        // dernière ligne (sans \n final)
        if (tempColonnes > nbC) {
            nbC = tempColonnes;
        }

        // construction de la matrice et vérification de matrice rectangulaire
        char[][] matrice = new char[nbL][];
        int ligne = 0;
        char[] ligneCourante = new char[nbC];
        int index = 0;
        boolean estRectangulaire = true;

        for (int i = 0; i < botteDeFoin.length(); i++) {
            char c = botteDeFoin.charAt(i);
            if (c == '\n') {
                if (index != nbC) {
                    estRectangulaire = false; // si la matrice n'est pas rectangulaire, on sort
                    break;
                }
                matrice[ligne++] = ligneCourante;
                ligneCourante = new char[nbC];
                index = 0;
            } else {
                if (index < nbC) {
                    ligneCourante[index++] = c;
                } else {
                    estRectangulaire = false; // si la matrice n'est pas rectangulaire, on sort
                    break;
                }
            }
        }

        // si la matrice n’est pas rectangulaire, traiter comme invalide
        if (!estRectangulaire) {
            return -1;
        }

        // ajout de la dernière ligne
        if (estRectangulaire) {
            if (index != nbC) {
                estRectangulaire = false;
            } else {
                matrice[ligne] = ligneCourante;
            }
        }

        /* Début de l'algo */

        int nbOccurences = 0;
        char[] tabAiguille = aiguille.toCharArray();
        int len = tabAiguille.length;

        // cas particulier : 1 lettre
        if (len == 1) {
            for (int l = 0; l < nbL; l++) {
                for (int c = 0; c < nbC; c++) {
                    if (matrice[l][c] == tabAiguille[0]) {
                        nbOccurences++;
                    }
                }
            }
            return nbOccurences;
        }

        // parcours horizontal
        for (int l = 0; l < nbL; l++) {
            for (int c = 0; c <= nbC - len; c++) {
                boolean normal = true, inverse = true;
                for (int i = 0; i < len; i++) {
                    char ch = matrice[l][c + i];
                    if (ch != tabAiguille[i]) // correspondance normale
                        normal = false;
                    if (ch != tabAiguille[len - 1 - i]) // correspondance inverse
                        inverse = false;
                    if (!normal && !inverse) // si la sous chaîne ne correspond dans aucun des sens, on sort
                        break;
                }
                if (normal || inverse)
                    nbOccurences++;
            }
        }

        // parcours vertical
        for (int c = 0; c < nbC; c++) {
            for (int l = 0; l <= nbL - len; l++) {
                boolean normal = true, inverse = true;
                for (int i = 0; i < len; i++) {
                    char ch = matrice[l + i][c];
                    if (ch != tabAiguille[i]) // correspondance normale
                        normal = false;
                    if (ch != tabAiguille[len - 1 - i]) // correspondance inverse
                        inverse = false;
                    if (!normal && !inverse)
                        break;
                }
                if (normal || inverse)
                    nbOccurences++;
            }
        }

        // parcours diagonale droite
        for (int l = 0; l <= nbL - len; l++) {
            for (int c = 0; c <= nbC - len; c++) {
                boolean normal = true, inverse = true;
                for (int i = 0; i < len; i++) {
                    char ch = matrice[l + i][c + i];
                    if (ch != tabAiguille[i])
                        normal = false;
                    if (ch != tabAiguille[len - 1 - i])
                        inverse = false;
                    if (!normal && !inverse)
                        break;
                }
                if (normal || inverse)
                    nbOccurences++;
            }
        }

        // parcours diagonale gauche
        for (int l = 0; l <= nbL - len; l++) {
            for (int c = len - 1; c < nbC; c++) {
                boolean normal = true, inverse = true;
                for (int i = 0; i < len; i++) {
                    char ch = matrice[l + i][c - i];
                    if (ch != tabAiguille[i])
                        normal = false;
                    if (ch != tabAiguille[len - 1 - i])
                        inverse = false;
                    if (!normal && !inverse)
                        break;
                }
                if (normal || inverse)
                    nbOccurences++;
            }
        }

        return nbOccurences;
    }

}
