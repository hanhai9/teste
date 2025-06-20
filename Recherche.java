package iut.sae.algo.sobriete.etu4;

public class Recherche {

    public static int chercheMot(String botteDeFoin, String aiguille) {
        if (botteDeFoin == null || aiguille == null || botteDeFoin.isEmpty()) return -1;

        // Calculer la taille de la matrice sans l'initialiser entièrement
        int cols = 0, rows = 0, tempCols = 0;
        for (int i = 0; i < botteDeFoin.length(); i++) {
            if (botteDeFoin.charAt(i) == '\n') {
                if (cols == 0) cols = tempCols;
                else if (tempCols != cols) return -1; // Matrice non rectangulaire
                rows++;
                tempCols = 0;
            } else {
                tempCols++;
            }
        }
        rows++;

        return compterOccurrences(botteDeFoin, aiguille, rows, cols);
    }

    private static int compterOccurrences(String botteDeFoin, String aiguille, int rows, int cols) {
        int count = 0;
        boolean estPalindrome = estPalindrome(aiguille);
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

        for (int i = 0, x = 0, y = 0; i < botteDeFoin.length(); i++) {
            if (botteDeFoin.charAt(i) == '\n') {
                x++;
                y = 0;
            } else {
                if (botteDeFoin.charAt(i) == aiguille.charAt(0)) {
                    for (int[] dir : directions) {
                        if (trouverMot(botteDeFoin, aiguille, x, y, rows, cols, dir)) {
                            count++;
                        }
                    }
                }
                y++;
            }
        }

        return estPalindrome ? count / 2 : count;
    }

    private static boolean trouverMot(String botteDeFoin, String aiguille, int x, int y, int rows, int cols, int[] direction) {
        int len = aiguille.length();

        for (int i = 0, xi = x, yi = y, index = 0; i < botteDeFoin.length() && index < len; i++) {
            if (botteDeFoin.charAt(i) == '\n') {
                xi++;
                yi = 0;
            } else {
                if (xi == x + i * direction[0] && yi == y + i * direction[1]) {
                    if (xi < 0 || xi >= rows || yi < 0 || yi >= cols || botteDeFoin.charAt(i) != aiguille.charAt(index)) {
                        return false;
                    }
                    index++;
                }
                yi++;
            }
        }

        return true;
    }

    private static boolean estPalindrome(String str) {
        int left = 0, right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left++) != str.charAt(right--)) return false;
        }
        return true;
    }
}

