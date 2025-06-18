package iut.sae.algo.efficacite.etu41;

public class Recherche{
    public static int chercheMot(String botteDeFoin, String aiguille) {

        if(aiguille == null || botteDeFoin == null ) {
            return -1;
        }
        if (botteDeFoin.length( )==0 || aiguille.length() == 0) {
            return 0;
        }
        
        
        int tailleTotale=botteDeFoin.length();
        int nbColonnes = 0;
        
        while(nbColonnes < tailleTotale && botteDeFoin.charAt( nbColonnes) != '\n') {
            nbColonnes = nbColonnes + 1;
        }
        
        // grille est régulière
        int nbLignes = 1;
        int tailleLigneActuelle = 0;
        for(int i = 0; i < tailleTotale; i++) {
            if(botteDeFoin.charAt(i) == '\n' ) {
                if(tailleLigneActuelle !=nbColonnes) return -1;
                nbLignes = nbLignes + 1;
                tailleLigneActuelle = 0;
            } else {
                tailleLigneActuelle = tailleLigneActuelle+1;
            }
        }
        if(tailleLigneActuelle !=nbColonnes)return -1;
        
        int cpt=0;
        int tailleMot=aiguille.length();
        
        

 
        if(tailleMot == 1) {
            char cible=aiguille.charAt( 0);
            for(int ligne = 0; ligne < nbLignes; ligne++) {
                for(int colonne = 0; colonne < nbColonnes; colonne++) {
                    if(recupCaractere(botteDeFoin, ligne, colonne, nbColonnes) == cible) {
                        cpt = cpt + 1;
                    }
                }
            }
            return cpt;
        }
        

        boolean estLuDouble=estLuDouble(aiguille);
        
        // Pour les mots lus en double
        int nbDirections;
        if (estLuDouble) {
            nbDirections = 4;
        } else {
            nbDirections = 8;
        }
        
        // Recherche depuis chaque position
        for(int ligne = 0; ligne< nbLignes; ligne++) {
            for(int colonne= 0; colonne<nbColonnes; colonne++) {
                for(int direction = 0; direction < nbDirections; direction++) {
                    int dirX, dirY;
                    
                    if (direction == 0) {// N
                        dirX = -1; dirY = 0;
                    } else if (direction == 1) {// NE
                        dirX = -1; dirY = 1;
                    } else if (direction == 2) {// E
                        dirX = 0; dirY = 1;
                    } else if (direction == 3) {// SE
                        dirX = 1; dirY = 1;
                    } else if (direction == 4) {// S
                        dirX = 1; dirY = 0;
                    } else if (direction == 5) { // SW
                        dirX = 1; dirY = -1;
                    } else if (direction == 6) {// W
                        dirX = 0; dirY = -1;
                    } else {// NW
                        dirX = -1; dirY = -1;
                    }
                    
                    // limites
                    int lArrivée =ligne + (tailleMot -1) * dirX;
                    int cArrivee = colonne +(tailleMot - 1) * dirY;
                    
                    if (lArrivée >= 0 &&lArrivée< nbLignes&& cArrivee >= 0 && cArrivee <nbColonnes) {

                        boolean trouve = true;
                        for (int i = 0; i <tailleMot; i++) {
                            int ligneActuelle = ligne+ i * dirX;
                            int colonneActuelle = colonne +i*dirY;
                            
                            if (recupCaractere(botteDeFoin,ligneActuelle, colonneActuelle, nbColonnes) != aiguille.charAt(i)) {
                                trouve = false;
                                break;  // caractère faux
                            }
                        }
                        if (trouve) {
                            cpt++;
                        }
                    }
                }
            }
        }
        return cpt;
    }
    
    private static boolean estLuDouble(String mot) {
        int longueur=mot.length();
        for(int i = 0; i < longueur / 2 ; i++) {
            if(mot.charAt(i)!= mot.charAt(longueur - 1 - i )) {
                return false;
            }
        }
        return true;
    }
    

    private static char recupCaractere(String texte, int ligne, int colonne, int nbColonnes) {
        return texte.charAt(ligne*(nbColonnes + 1 ) + colonne);
    }
    
}
