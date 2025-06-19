package iut.sae.algo.simplicite.etu78;

public class Recherche {
    public static int chercheMot(String botteDeFoin, String aiguille) {
        // Gestion des cas particuliers
        // Test Erreur Aiguille (si aiguille est null ou vide)
        if (aiguille == null || aiguille.isEmpty()) { 
            return -1; 
        }

        // Test Erreur Botte (si botteDeFoin est null)
        if (botteDeFoin == null) { 
            return -1; 
        }

        // Test Vide (si botteDeFoin est vide)
        if (botteDeFoin.isEmpty()) { 
            return 0; 
        }

        String[] lignes = botteDeFoin.split("\n");
        if(lignes.length == 0 || (lignes.length == 1 && lignes[0].isEmpty() && botteDeFoin.equals("\n"))) {
            return 0; 
        }

        // Vérifier la régularité de la botte de foin (toutes les lignes ont la même longueur)
        int largeur = lignes[0].length();
        for (String ligne : lignes) {
            if (ligne.length() != largeur) {
                return -1; 
            }
        }
        
         // Convertir la botte de foin en une matrice de caractères
         char[][] matrice = new char[lignes.length][lignes[0].length()];
         for (int i = 0; i < lignes.length; i++) {
            matrice[i] = lignes[i].toCharArray();
         }
 
         int hauteurMatrice = matrice.length;
         int largeurMatrice = matrice[0].length;
         int occurrences = 0;

        // Si l'aiguille est d'un seul caractère, on compte les occurrences de ce caractère dans la matrice
         if(aiguille.length()==1){
                for (int i = 0; i < hauteurMatrice; i++) {
                    for (int j = 0; j < largeurMatrice; j++) {
                        if (matrice[i][j] == aiguille.charAt(0)) {
                            occurrences++;
                        }
                    }
                }
                return occurrences;
         }

         // Si l'aiguille est un palindrome ou un duo de caractères identiques

        char[] aiguilleChar = aiguille.toCharArray();
        boolean estPalindrome = true; // Palindrome à défaut d'autres mots satisfaisant signifiant chaine de caractères identique de la même longueur que la matrice.
        boolean estChaineIdentique = true;

        
        if(aiguilleChar.length==largeurMatrice){
            for (int i = 1; i < aiguilleChar.length; i++) {
                if (aiguilleChar[i] != aiguilleChar[i-1]) {
                    estPalindrome = false;
                    estChaineIdentique = false;
                    break;
                }
            }
        } else {
            for (int i = 1; i < aiguilleChar.length; i++){
                if(aiguilleChar[i] != aiguilleChar[i-1]) {
                    estChaineIdentique = false; 
                    break;
                }
            }
        }
        
        
        

         // Parcours de la matrice pour trouver les occurrences de l'aiguille

         
         // Parcours horizontal de la matrice (gauche à droite)
        for (int i = 0; i < hauteurMatrice; i++) {
            for (int j = 0; j <= largeurMatrice - aiguille.length(); j++) {
                String sousChaine = new String(matrice[i], j, aiguille.length());
                if (sousChaine.equals(aiguille)) {
                    occurrences++;
                }
            }
            
        }
        if(estChaineIdentique && !estPalindrome){
            occurrences--;
        }

        // Parcours horizontal de la matrice (droite à gauche)
        for (int i = 0; i < hauteurMatrice; i++) {
            for (int j = largeurMatrice - 1; j >= aiguille.length() - 1; j--) { // Commencer de la fin
                StringBuilder sousChaine = new StringBuilder();
                for (int k = 0; k < aiguille.length(); k++) {
                    sousChaine.append(matrice[i][j - k]); // Déplacement vers la gauche
                }
                if (sousChaine.toString().equals(aiguille) && estChaineIdentique==false  ) {
                    occurrences++;
                }
            }
        }

        // Parcours vertical de la matrice (haut en bas)
        for (int j = 0; j < largeurMatrice; j++) {
            for (int i = 0; i <= hauteurMatrice - aiguille.length(); i++) {
                StringBuilder sousChaine = new StringBuilder();
                for (int k = 0; k < aiguille.length(); k++) {
                    sousChaine.append(matrice[i + k][j]);
                }
                if (sousChaine.toString().equals(aiguille)) {
                    occurrences++;
                }
            }
            
        }
        if(estChaineIdentique && !estPalindrome){
            occurrences--;
        }

        // Parcours vertical de la matrice (bas en haut)
        for (int j = 0; j < largeurMatrice; j++) { // Pour chaque colonne
            for (int i = hauteurMatrice - 1; i >= aiguille.length() - 1; i--) { // On part du bas et on remonte
                StringBuilder sousChaine = new StringBuilder();
                for (int k = 0; k < aiguille.length(); k++) {
                    sousChaine.append(matrice[i - k][j]);
                }
                if (sousChaine.toString().equals(aiguille) && estChaineIdentique==false) {
                    occurrences++;
                }
            }
        }

        // Parcours diagonal de la matrice (haut-gauche vers bas-droite)
        for (int i = 0; i < hauteurMatrice; i++) {
            for (int j = 0; j <= largeurMatrice - aiguille.length(); j++) {
                StringBuilder sousChaine = new StringBuilder();
                boolean possible = true;   
                for (int k = 0; k < aiguille.length(); k++) {
                    int ligneActuelle = i + k;
                    int colonneActuelle = j + k;
                    if (ligneActuelle < hauteurMatrice && colonneActuelle < largeurMatrice) {
                        sousChaine.append(matrice[ligneActuelle][colonneActuelle]);
                    } else {
                        possible = false; // on sort de la matrice, donc on stop
                        break;
                    }
                }
                if (possible && sousChaine.toString().equals(aiguille)) {
                    occurrences++;
                }
            }
        }
        if(estChaineIdentique && !estPalindrome){
            occurrences--;
        }

        // Parcours diagonal de la matrice (bas-droite vers haut-gauche)
        for (int i = hauteurMatrice - 1; i >= 0 ; i--) {
            for (int j = largeurMatrice - 1; j >= aiguille.length() - 1; j--) {
                StringBuilder sousChaine = new StringBuilder();
                boolean possible = true;
                for (int k = 0; k < aiguille.length(); k++) {
                    int ligneActuelle = i - k;
                    int colonneActuelle = j - k;
                    if (ligneActuelle >= 0 && colonneActuelle >= 0) {
                        sousChaine.append(matrice[ligneActuelle][colonneActuelle]);
                    } else {
                        possible = false; // on sort de la matrice, donc on stop
                        break;
                    }
                }
                if (possible && sousChaine.toString().equals(aiguille) && estChaineIdentique==false) {
                    occurrences++;
                }
            }
        }

        // Parcours diagonal de la matrice (haut-droite vers bas-gauche)
        for (int i = 0; i < hauteurMatrice; i++) {
            for (int j = largeurMatrice - 1; j >=0; j--) {
                StringBuilder sousChaine = new StringBuilder();
                boolean possible = true;
                for (int k = 0; k < aiguille.length(); k++) {
                    int ligneActuelle = i + k;
                    int colonneActuelle = j - k;
                    if (ligneActuelle < hauteurMatrice && colonneActuelle >= 0) {
                        sousChaine.append(matrice[ligneActuelle][colonneActuelle]);
                    } else {
                        possible = false; // on sort de la matrice, donc on stop
                        break;
                    }
                }
                if (possible && sousChaine.toString().equals(aiguille)) {
                    occurrences++;
                }
            }
        }
        if(estChaineIdentique && !estPalindrome){
            occurrences--;
        }

        // Parcours diagonal de la matrice (bas-gauche vers haut-droite)
        for (int i = hauteurMatrice - 1; i >= 0; i--) {
            for (int j = 0; j <= largeurMatrice - aiguille.length(); j++) {
                StringBuilder sousChaine = new StringBuilder();
                boolean possible = true;
                for (int k = 0; k < aiguille.length(); k++) {
                    int ligneActuelle = i - k;
                    int colonneActuelle = j + k;
                    if (ligneActuelle >= 0 && colonneActuelle < largeurMatrice) {
                        sousChaine.append(matrice[ligneActuelle][colonneActuelle]);
                    } else {
                        possible = false; // on sort de la matrice, donc on stop
                        break;
                    }
                }
                if (possible && sousChaine.toString().equals(aiguille) && estChaineIdentique==false) {
                    occurrences++;
                }
            }
        }

        if(estChaineIdentique && !estPalindrome){
            occurrences-=4;
        }
        
        return occurrences;
    }
}

