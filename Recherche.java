package iut.sae.algo.sobriete.etu6;

public class Recherche {

    public static int chercheMot(String botteDeFoin, String aiguille) {
        // Vérification des paramètres nulls
        if (botteDeFoin == null || aiguille == null) {
            return -1;
        }

        int longueurBotte = botteDeFoin.length();
        int longueurAiguille = aiguille.length();

        // Cas des chaînes vides
        if (longueurBotte == 0 || longueurAiguille == 0) {
            return 0;
        }

        // Calcul de la largeur (première ligne)
        int largeur = 0;
        while (largeur < longueurBotte && botteDeFoin.charAt(largeur) != '\n') {
            largeur++;
        }

        // Si pas de \n, c'est une seule ligne
        if (largeur == longueurBotte) {
            // Recherche horizontale seulement
            return rechercheHorizontale(botteDeFoin, aiguille, longueurBotte, longueurAiguille);
        }

        // Calcul de la hauteur et vérification de la régularité
        int hauteur = 1;
        int positionLigne = largeur + 1; // Position après le premier \n
        
        while (positionLigne < longueurBotte) {
            int longueurLigneActuelle = 0;
            int debut = positionLigne;
            
            // Compter les caractères de cette ligne
            while (positionLigne < longueurBotte && botteDeFoin.charAt(positionLigne) != '\n') {
                longueurLigneActuelle++;
                positionLigne++;
            }
            
            // Vérifier la régularité
            if (longueurLigneActuelle != largeur) {
                return -1;
            }
            
            hauteur++;
            positionLigne++; // Passer le \n
        }

        // Vérification si l'aiguille est un palindrome (réutilisation de variables)
        boolean palindrome = true;
        int moitie = longueurAiguille / 2;
        for (int i = 0; i < moitie; i++) {
            if (aiguille.charAt(i) != aiguille.charAt(longueurAiguille - 1 - i)) {
                palindrome = false;
                break;
            }
        }

        int compteur = 0;
        
        // Parcourir chaque position (réutilisation des variables de boucle)
        for (int ligne = 0; ligne < hauteur; ligne++) {
            for (int colonne = 0; colonne < largeur; colonne++) {
                
                // Tester les 8 directions directement (sans tableau)
                // Horizontal droite (0, 1)
                if (peutTenir(ligne, colonne, 0, 1, longueurAiguille, hauteur, largeur) &&
                    motTrouve(botteDeFoin, aiguille, ligne, colonne, 0, 1, largeur, longueurAiguille)) {
                    compteur++;
                }
                
                // Horizontal gauche (0, -1)
                if (peutTenir(ligne, colonne, 0, -1, longueurAiguille, hauteur, largeur) &&
                    motTrouve(botteDeFoin, aiguille, ligne, colonne, 0, -1, largeur, longueurAiguille)) {
                    compteur++;
                }
                
                // Vertical bas (1, 0)
                if (peutTenir(ligne, colonne, 1, 0, longueurAiguille, hauteur, largeur) &&
                    motTrouve(botteDeFoin, aiguille, ligne, colonne, 1, 0, largeur, longueurAiguille)) {
                    compteur++;
                }
                
                // Vertical haut (-1, 0)
                if (peutTenir(ligne, colonne, -1, 0, longueurAiguille, hauteur, largeur) &&
                    motTrouve(botteDeFoin, aiguille, ligne, colonne, -1, 0, largeur, longueurAiguille)) {
                    compteur++;
                }
                
                // Diagonale bas-droite (1, 1)
                if (peutTenir(ligne, colonne, 1, 1, longueurAiguille, hauteur, largeur) &&
                    motTrouve(botteDeFoin, aiguille, ligne, colonne, 1, 1, largeur, longueurAiguille)) {
                    compteur++;
                }
                
                // Diagonale haut-gauche (-1, -1)
                if (peutTenir(ligne, colonne, -1, -1, longueurAiguille, hauteur, largeur) &&
                    motTrouve(botteDeFoin, aiguille, ligne, colonne, -1, -1, largeur, longueurAiguille)) {
                    compteur++;
                }
                
                // Diagonale bas-gauche (1, -1)
                if (peutTenir(ligne, colonne, 1, -1, longueurAiguille, hauteur, largeur) &&
                    motTrouve(botteDeFoin, aiguille, ligne, colonne, 1, -1, largeur, longueurAiguille)) {
                    compteur++;
                }
                
                // Diagonale haut-droite (-1, 1)
                if (peutTenir(ligne, colonne, -1, 1, longueurAiguille, hauteur, largeur) &&
                    motTrouve(botteDeFoin, aiguille, ligne, colonne, -1, 1, largeur, longueurAiguille)) {
                    compteur++;
                }
            }
        }

        // Pour les palindromes et caractères uniques, diviser par 2
        return (palindrome || longueurAiguille == 1) ? compteur / 2 : compteur;
    }

    /**
     * Recherche horizontale optimisée pour une seule ligne
     */
    private static int rechercheHorizontale(String texte, String aiguille, int longueurTexte, int longueurAiguille) {
        if (longueurAiguille > longueurTexte) {
            return 0;
        }
        
        // Vérifier si palindrome
        boolean palindrome = true;
        int moitie = longueurAiguille / 2;
        for (int i = 0; i < moitie; i++) {
            if (aiguille.charAt(i) != aiguille.charAt(longueurAiguille - 1 - i)) {
                palindrome = false;
                break;
            }
        }
        
        int compteur = 0;
        
        // Recherche vers la droite
        for (int i = 0; i <= longueurTexte - longueurAiguille; i++) {
            boolean trouve = true;
            for (int j = 0; j < longueurAiguille; j++) {
                if (texte.charAt(i + j) != aiguille.charAt(j)) {
                    trouve = false;
                    break;
                }
            }
            if (trouve) {
                compteur++;
            }
        }
        
        // Recherche vers la gauche (seulement si pas palindrome)
        if (!palindrome && longueurAiguille > 1) {
            for (int i = 0; i <= longueurTexte - longueurAiguille; i++) {
                boolean trouve = true;
                for (int j = 0; j < longueurAiguille; j++) {
                    if (texte.charAt(i + j) != aiguille.charAt(longueurAiguille - 1 - j)) {
                        trouve = false;
                        break;
                    }
                }
                if (trouve) {
                    compteur++;
                }
            }
        }
        
        return compteur;
    }

    /**
     * Vérifie si le mot peut tenir dans la direction donnée
     */
    private static boolean peutTenir(int ligne, int colonne, int deltaLigne, int deltaColonne, 
                                   int longueurMot, int hauteur, int largeur) {
        int ligneFin = ligne + (longueurMot - 1) * deltaLigne;
        int colonneFin = colonne + (longueurMot - 1) * deltaColonne;
        
        return ligneFin >= 0 && ligneFin < hauteur && colonneFin >= 0 && colonneFin < largeur;
    }

    /**
     * Vérifie si le mot est trouvé dans la direction donnée
     */
    private static boolean motTrouve(String texte, String mot, int ligneDepart, int colonneDepart, 
                                   int deltaLigne, int deltaColonne, int largeur, int longueurMot) {
        
        for (int i = 0; i < longueurMot; i++) {
            int ligneActuelle = ligneDepart + i * deltaLigne;
            int colonneActuelle = colonneDepart + i * deltaColonne;
            
            // Calcul direct de la position dans la chaîne
            int position = ligneActuelle * (largeur + 1) + colonneActuelle;
            
            if (texte.charAt(position) != mot.charAt(i)) {
                return false;
            }
        }
        
        return true;
    }
}
