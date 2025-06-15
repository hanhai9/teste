package iut.sae.algo.efficacite.etu4;

public class Recherche {

    public static int chercheMot(String botteDeFoin, String aiguille) {
        if (botteDeFoin == null || aiguille == null || botteDeFoin.isEmpty()) return -1;

        // Vérification de la régularité de la matrice
        int cols = 0, rows = 0;
        for (int i = 0, tempCols = 0; i < botteDeFoin.length(); i++) {
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

        char[][] matrice = new char[rows][cols];
        int r = 0, c = 0;
        for (int i = 0; i < botteDeFoin.length(); i++) {
            if (botteDeFoin.charAt(i) == '\n') {
                r++;
                c = 0;
            } else {
                matrice[r][c++] = botteDeFoin.charAt(i);
            }
        }

        return compterOccurrences(matrice, aiguille);
    }

    private static int compterOccurrences(char[][] matrice, String aiguille) {
        int rows = matrice.length, cols = matrice[0].length, count = 0;
        boolean estPalindrome = estPalindrome(aiguille);
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrice[i][j] == aiguille.charAt(0)) {
                    for (int[] dir : directions) {
                        if (trouverMot(matrice, aiguille, i, j, dir)) {
                            count++;
                        }
                    }
                }
            }
        }

        return estPalindrome ? count / 2 : count;
    }

    private static boolean trouverMot(char[][] matrice, String aiguille, int x, int y, int[] direction) {
        int rows = matrice.length, cols = matrice[0].length, len = aiguille.length();

        for (int i = 0; i < len; i++) {
            int newX = x + i * direction[0], newY = y + i * direction[1];
            if (newX < 0 || newX >= rows || newY < 0 || newY >= cols || matrice[newX][newY] != aiguille.charAt(i)) {
                return false;
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
