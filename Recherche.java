package iut.sae.algo.simplicite.etu9;


/**
 * Pour rechercher une aiguille dans une botte de foin.
 */
public class Recherche {

    /**
     * Cherche le nombre de fois qu'on peut entourer une aiguille dans une botte de foin.
     */
    public static int chercheMot(String botteDeFoin, String aiguille) {
        Aiguille aig;
        BotteDeFoin botte;
        try {
            aig = new Aiguille(aiguille);
            botte = new BotteDeFoin(botteDeFoin);
        } catch (IllegalArgumentException e) {
            return -1;
        }

        int nbAig = 0;
        nbAig += compteMotHorizontal(botte, aig);
        if (aig.longueurQueue == 0) {
            return nbAig;
        }
        nbAig += compteMotVertical(botte, aig);
        nbAig += compteMotDescenteDiag(botte, aig);
        nbAig += compteMotMonteeDiag(botte, aig);
        return nbAig;
    }

    private static int compteMotHorizontal(BotteDeFoin botte, Aiguille aig) {
        int nb = 0;
        /* Cases de départ (X)  /  Sens de lecture
         *
         * XXXoo
         * XXXoo  /  123
         * XXXoo
         * XXXoo
         */
        for (int li = 0; li < botte.lignes; li++) {
            for (int col = 0; col < botte.colonnes - aig.longueurQueue ; col++) {
                String lu = botte.lire(li, col, aig.longueur, 0, +1);
                if (aig.correspond(lu)) {
                    nb++;
                }
            }
        }
        return nb;
    }

    private static int compteMotVertical(BotteDeFoin botte, Aiguille aig) {
        int nb = 0;
        /* Cases de départ (X)  /  Sens de lecture
         *
         * XXXXX     1
         * XXXXX  /  2
         * ooooo     3
         * ooooo
         */
        for (int li = 0; li < botte.lignes - aig.longueurQueue; li++) {
            for (int col = 0; col < botte.colonnes; col++) {
                String lu = botte.lire(li, col, aig.longueur, +1, 0);
                if (aig.correspond(lu)) {
                    nb++;
                }
            }
        }
        return nb;
    }

    private static int compteMotDescenteDiag(BotteDeFoin botte, Aiguille aig) {
        int nb = 0;
        /* Cases de départ (X)  /  Sens de lecture
         *
         * XXXoo     1
         * XXXoo  /   2
         * ooooo       3
         * ooooo
         */
        for (int li = 0; li < botte.lignes - aig.longueurQueue; li++) {
            for (int col = 0; col < botte.colonnes - aig.longueurQueue; col++) {
                String lu = botte.lire(li, col, aig.longueur, +1, +1);
                if (aig.correspond(lu)) {
                    nb++;
                }
            }
        }
        return nb;
    }

    private static int compteMotMonteeDiag(BotteDeFoin botte, Aiguille aig) {
        int nb = 0;
        /* Cases de départ (X)  /  Sens de lecture
         *
         * ooooo       3
         * ooooo  /   2
         * XXXoo     1
         * XXXoo
         */
        for (int li = aig.longueurQueue; li < botte.lignes; li++) {
            for (int col = 0; col < botte.colonnes - aig.longueurQueue; col++) {
                String lu = botte.lire(li, col, aig.longueur, -1, +1);
                if (aig.correspond(lu)) {
                    nb++;
                }
            }
        }
        return nb;
    }
}

class Aiguille {

    /** Le texte de l'aiguille. */
    public String texte;

    /** Le texte de l'aiguille, mais à l'envers */
    public String texteInverse;

    /** La longueur de l'aiguille */
    public int longueur;

    /** La longueur de l'aiguille sans sa tête (longueur moins 1) */
    public int longueurQueue;

    /**
     * Constructeur.
     */
    public Aiguille(String texte) {
        if (texte == null || texte.isEmpty()) {
            throw new IllegalArgumentException("Aiguille invalide");
        }
        this.texte = texte;
        this.longueur = texte.length();
        this.longueurQueue = this.longueur - 1;
        StringBuilder inverse = new StringBuilder();
        for (int i = texte.length() - 1; i >= 0; i--) {
            inverse.append(texte.charAt(i));
        }
        texteInverse = inverse.toString();
    }

    /**
     * Indique si un texte candidat correspond au texte de l'aiguille, dans un sens ou dans l'autre.
     */
    public boolean correspond(String candidat) {
        return candidat.equals(texte) || candidat.equals(texteInverse);
    }
}

class BotteDeFoin {

    /** La botte de foin, sous la forme d'une matrice de caractères. */
    public char[][] caracteres;

    /** Nombre de lignes dans la botte. */
    public int lignes;

    /** Nombre de colonnes dans la botte. */
    public int colonnes;

    /**
     * Constructeur.
     */
    public BotteDeFoin(String texte) {
        if (texte == null) {
            throw new IllegalArgumentException("Botte de foin invalide");
        }
        if (texte.isEmpty()) {
            return;
        }
        String[] lignes = texte.split("\n");
        this.lignes = lignes.length;
        this.colonnes = lignes[0].length();
        this.caracteres = new char[this.lignes][this.colonnes];
        for (int i = 0; i < lignes.length; i++) {
            char[] carLigne = lignes[i].toCharArray();
            if (carLigne.length != this.colonnes) {
                throw new IllegalArgumentException("Botte de foin irrégulière");
            }
            this.caracteres[i] = carLigne;
        }
    }

    /**
     * Lit un mot dans la botte de foin
     *
     * @param li Ligne de départ
     * @param col Colonne de départ
     * @param n Longueur du mot
     * @param decLi Évolution des lignes
     * @param decCol Évolution des colonnes
     * @return Le texte lu
     */
    public String lire(int li, int col, int n, int decLi, int decCol) {
        StringBuilder mot = new StringBuilder();
        for (int i = 0; i < n; i++) {
            mot.append(caracteres[li][col]);
            li += decLi;
            col += decCol;
        }
        return mot.toString();
    }
}

