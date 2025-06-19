package iut.sae.algo.simplicite.etu43;

public class Recherche {
    public static int chercheMot(String botteDeFoin, String aiguille) {
        int         nbMotTrouve         = 0;
        int         nbLettreValide      = 0;
        int         lignes              = 0;
        int         colonnes            = 0;
        int         longueur            = 0;
        String[]    lignesBotteDeFoin;
        

//=============== vérification des paramètres ===============

        // parametres nuls
        if (botteDeFoin == null || aiguille == null || aiguille.equals("")) {
            return -1;
        }

        if (botteDeFoin.equals("")) {
            return 0;
        }

       
        // On met chaque lingne dans une case du tableau
        lignesBotteDeFoin = botteDeFoin.split("\n");
        
        // Vérification pour si la botte est irrégulière
        for (int i = 0; i < lignesBotteDeFoin.length; i++) {
            if (lignesBotteDeFoin[i].length() != lignesBotteDeFoin[0].length()) {
                return -1;
            }
        }


//=============== Recherches dans la botte ===============

        // Horizontal
        for (int i = 0; i < lignesBotteDeFoin.length; i++) {
            nbLettreValide = 0;

            for (int j = 0; j < lignesBotteDeFoin[i].length(); j++) {
                if (lignesBotteDeFoin[i].charAt(j) == aiguille.charAt(nbLettreValide)) {
                    nbLettreValide++;

                    // Verification pour si on trouve l'aiguille
                    if (nbLettreValide == aiguille.length()) {
                        nbMotTrouve++;
                        nbLettreValide = 0;
                    }

                } else {
                    nbLettreValide = 0;
                }
            }
        }

        if (aiguille.length() != 1) {
            // Horizontal inverse
            for (int i = 0; i < lignesBotteDeFoin.length; i++) {
                nbLettreValide = 0;

                for (int j = lignesBotteDeFoin[i].length() - 1; j >= 0; j--) {
                    if (lignesBotteDeFoin[i].charAt(j) == aiguille.charAt(nbLettreValide)) {
                        nbLettreValide++;

                        // Verification pour si on trouve l'aiguille
                        if (nbLettreValide == aiguille.length()) {
                            nbMotTrouve++;
                            nbLettreValide = 0;
                        }

                    } else {
                        nbLettreValide = 0;
                    }
                }
            }


            // Vertical
            for (int i = 0; i < lignesBotteDeFoin[0].length(); i++) {
                nbLettreValide = 0;

                for (int j = 0; j < lignesBotteDeFoin.length; j++) {
                    if (lignesBotteDeFoin[j].charAt(i) == aiguille.charAt(nbLettreValide)) {
                        nbLettreValide++;

                        // Pareil on verifie si on trouve l'aiguille
                        if (nbLettreValide == aiguille.length()) {
                            nbMotTrouve++;
                            nbLettreValide = 0;
                        }

                    } else {
                        nbLettreValide = 0;
                    }

                }
            }

            // Vertical inverse
            for (int i = 0; i < lignesBotteDeFoin[0].length(); i++) {
                nbLettreValide = 0;

                for (int j = lignesBotteDeFoin.length - 1; j >= 0; j--) {
                    if (lignesBotteDeFoin[j].charAt(i) == aiguille.charAt(nbLettreValide)) {
                        nbLettreValide++;
                    }


                    if (nbLettreValide == aiguille.length()) {
                        nbMotTrouve++;
                        nbLettreValide = 0;
                    }
                }
            }


            if (aiguille.length() > 2) {
                lignes = lignesBotteDeFoin.length;
                colonnes = lignesBotteDeFoin[0].length();
                longueur = aiguille.length();

                // Diagonale ↘ (haut-gauche vers bas-droite)
                for (int i = 0; i <= lignes - longueur; i++) {
                    for (int j = 0; j <= colonnes - longueur; j++) {
                        boolean trouve = true;
                        for (int k = 0; k < longueur; k++) {
                            if (lignesBotteDeFoin[i + k].charAt(j + k) != aiguille.charAt(k)) {
                                trouve = false;
                                break;
                            }
                        }
                        if (trouve) nbMotTrouve++;
                    }
                }
                // Diagonale ↖ (bas-droite vers haut-gauche)
                for (int i = longueur - 1; i < lignes; i++) {
                    for (int j = longueur - 1; j < colonnes; j++) {
                        boolean trouve = true;
                        for (int k = 0; k < longueur; k++) {
                            if (lignesBotteDeFoin[i - k].charAt(j - k) != aiguille.charAt(k)) {
                                trouve = false;
                                break;
                            }
                        }
                        if (trouve) nbMotTrouve++;
                    }
                }
            }
                // Diagonale ↙ (haut-droite vers bas-gauche)
                for (int i = 0; i <= lignes - longueur; i++) {
                    for (int j = longueur - 1; j < colonnes; j++) {
                        boolean trouve = true;
                        for (int k = 0; k < longueur; k++) {
                            if (lignesBotteDeFoin[i + k].charAt(j - k) != aiguille.charAt(k)) {
                                trouve = false;
                                break;
                            }
                        }
                        if (trouve) nbMotTrouve++;
                    }
                }
                // Diagonale ↗ (bas-gauche vers haut-droite)
                for (int i = longueur - 1; i < lignes; i++) {
                    for (int j = 0; j <= colonnes - longueur; j++) {
                        boolean trouve = true;
                        for (int k = 0; k < longueur; k++) {
                            if (lignesBotteDeFoin[i - k].charAt(j + k) != aiguille.charAt(k)) {
                                trouve = false;
                                break;
                            }
                        }
                        if (trouve) nbMotTrouve++;
                    }
                }
        }

        return nbMotTrouve;
    } 
}


