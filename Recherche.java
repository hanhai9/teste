package iut.sae.algo.efficacite.etu45;

public class Recherche {
    public static int chercheMot(String botteDeFoin, String aiguille) {
        //vérification de la nullité des paramètres
        if (botteDeFoin == null || aiguille == null) {
            return -1;
        } else if (botteDeFoin == "" || aiguille == ""){
            return 0;
        }

        //On commence à initialiser des variables utiles, le reste sera initialisé après
        int tailleAiguille = aiguille.length();
        int tailleBotte = botteDeFoin.length();
        int i = 0 ;
        int y;
        boolean estPossible = true;
        int nbLignes = 0;
        boolean nbFrequencePair = false; // Sert à déterminer laquelle des deux fréquences suivantes doit changer de fréquence, false -> 1ère / true -> 2ème
        int premiereFrequenceLigne = 0;
        int deuxiemeFrequenceLigne = 0;
        boolean nbFrequenceValide = true;

        // Ici on teste si toute les lettres de l'aiguille sont bien présente au moins une fois dans la botte
        // Dans la même boucle, on compte les nombres de fréquences sur la première boucle
        while(i < tailleAiguille && estPossible && nbFrequenceValide){
            y = 0;
            if (i == 0) {
                for (y = 0; y < tailleBotte; y++) {
                    if (aiguille.charAt(0) != botteDeFoin.charAt(y)) {
                        if (botteDeFoin.charAt(y) == '\n') {
                            // On alterne entre la première fréquence et la deuxième et on regarde si elle sont différente à un moment, si c'est le cas alors la botte n'est pas régulière
                            if (!nbFrequencePair) {
                                premiereFrequenceLigne = y;
                                nbFrequencePair = true;
                            } else {
                                deuxiemeFrequenceLigne = y - premiereFrequenceLigne - 1;
                                nbFrequencePair = false;
                            }
                            nbLignes++;
                        }
                        if (premiereFrequenceLigne != deuxiemeFrequenceLigne && premiereFrequenceLigne != 0 && deuxiemeFrequenceLigne != 0) {
                            nbFrequenceValide = false;
                        }
                    } else {
                        estPossible = true;
                    }
                }
                i++;
            } else {
                while(aiguille.charAt(i) != botteDeFoin.charAt(y) && y < tailleBotte-2 ){
                    y++;
                }
                y++;
                if (y == tailleBotte - 1 && aiguille.charAt(i) != botteDeFoin.charAt(y) && aiguille.charAt(i) != botteDeFoin.charAt(y-1)){
                    estPossible = false;
                }
                i++;
             }
        }

        nbLignes++; // La dernière ligne non compté, soit la seule ligne pour des bottes sans \n

        if (!estPossible){
            return 0;
        }
        if (!nbFrequenceValide) {
            return -1;
        }

        // On verifie si le mot est un palindrome, s'il en est un, on divisera le nombre trouvé à la fin par deux
        i = 0;
        boolean estPalindrome = false;

        if (tailleAiguille >= 2){
            //On traite le problème différement si le mot est pair au départ
            if ((tailleAiguille % 2) == 0) {
                while (i < tailleAiguille / 2 && aiguille.charAt(i) == aiguille.charAt(tailleAiguille - 1)) {
                    i++;
                }

                if (i == ((tailleAiguille / 2))) {
                    estPalindrome = true;
                }
            }else{
                while (i < ((tailleAiguille / 2 ) ) && aiguille.charAt(i) == aiguille.charAt(tailleAiguille - 1)) {
                    i++;
                }

                if (i == ((tailleAiguille / 2) )) {
                    estPalindrome = true;
                }
            }
        }

        // initialisation du compteur final
        int compteur = 0;



        // Recherche de l'aiguille dans la botte de foin
        // S'il l'aiguille n'a qu'un caractère, on traite différemment
        if (tailleAiguille >= 2) {


            i = 0;
            boolean stopBoucle = false;

            // On traite différemment si on n'a qu'une seule ligne (on évite de chercher les diagonales et les verticales)
            if (nbLignes == 1) {

                while (i < tailleBotte) {
                    if (botteDeFoin.charAt(i) == aiguille.charAt(0)) {

                        boolean testH = (i + tailleAiguille) <= tailleBotte ;
                        boolean testHI = (i - tailleAiguille + 1) >= 0;

                        if (testH) {

                            y = i + 1;

                            while (botteDeFoin.charAt(y) == aiguille.charAt(y - i) && !stopBoucle) {
                                if (y-i == tailleAiguille - 1 ) {
                                    compteur++;
                                    stopBoucle = true;
                                    y--;
                                }
                                y++;
                            }

                            stopBoucle = false;

                        }
                        if (testHI) {

                            y = i-1;

                            while (botteDeFoin.charAt(y) == aiguille.charAt(i - y) && !stopBoucle) {
                                if (i - y  == tailleAiguille - 1) {
                                    compteur++;
                                    y++;
                                    stopBoucle=true;
                                }
                                y--;
                            }

                        }

                    }
                    i++;
                }
                if (estPalindrome) {
                    return compteur / 2;
                }
            } else {

                int indice;
                int ligneActuelle = 1;

                while (i < tailleBotte) {
                    if (botteDeFoin.charAt(i) == '\n') {
                        ligneActuelle++;
                    } else if (botteDeFoin.charAt(i) == aiguille.charAt(0)) {

                        // Test de la possibilité que l'aiguille soit à l'horizontale à ce moment précis de la boucle
                        boolean testH = (i + tailleAiguille) <= tailleBotte && (i + tailleAiguille - 1) < (premiereFrequenceLigne * ligneActuelle) + (ligneActuelle - 1);

                        // Test de la possibilité que l'aiguille soit à l'horizontale inversée à ce moment précis de la boucle
                        boolean testHI = (i - tailleAiguille + 1) >= 0 && (i - tailleAiguille + 1) > (premiereFrequenceLigne * (ligneActuelle - 1)) + (ligneActuelle - 2);

                        // Test de la possibilité que l'aiguille soit à la verticale à ce moment précis de la boucle
                        boolean testV = ligneActuelle + tailleAiguille - 1 <= nbLignes;

                        // Test de la possibilité que l'aiguille soit à la verticale inversée à ce moment précis de la boucle
                        boolean testVI = ligneActuelle - tailleAiguille >= 0;


                        // A partir de ces tests, on peut faire l'ensemble des tests
                        if (testH) {

                            y = i + 1;

                            while (botteDeFoin.charAt(y) == aiguille.charAt(y - i) && !stopBoucle) {
                                if (y-i == tailleAiguille - 1 ) {
                                    compteur++;
                                    stopBoucle = true;
                                    y--;
                                }
                                y++;
                            }

                            stopBoucle = false;

                        }
                        if (testHI) {

                            y = i-1;

                            while (botteDeFoin.charAt(y) == aiguille.charAt(i - y) && !stopBoucle) {
                                if (i - y  == tailleAiguille - 1) {
                                    compteur++;
                                    y++;
                                    stopBoucle=true;
                                }
                                y--;
                            }

                            stopBoucle = false;

                        }
                        if (testV) {

                            y = i +premiereFrequenceLigne +1;
                            indice = 1;

                            while (botteDeFoin.charAt(y) == aiguille.charAt(indice) && !stopBoucle) {
                                if (indice == tailleAiguille - 1) {
                                    compteur++;
                                    stopBoucle = true;
                                    y -= premiereFrequenceLigne +1;
                                    indice--;
                                }
                                y += premiereFrequenceLigne +1;
                                indice++;
                            }

                            stopBoucle = false;

                        }
                        if (testVI) {

                            y = i -premiereFrequenceLigne -1;
                            indice = 1;

                            while (botteDeFoin.charAt(y) == aiguille.charAt(indice) && !stopBoucle) {
                                if (indice == tailleAiguille - 1) {
                                    compteur++;
                                    indice--;
                                    stopBoucle = true;
                                    y += premiereFrequenceLigne +1;
                                }
                                y -= premiereFrequenceLigne +1;
                                indice++;
                            }

                            stopBoucle = false;

                        }
                        if (testH && testV) {
                            y = i +premiereFrequenceLigne + 2 ;
                            indice = 1;

                            while (botteDeFoin.charAt(y) == aiguille.charAt(indice) && !stopBoucle) {
                                if (indice  == tailleAiguille - 1) {
                                    compteur++;
                                    y -= premiereFrequenceLigne +2;
                                    indice--;
                                    stopBoucle = true;
                                }
                                y += premiereFrequenceLigne +2;
                                indice++;
                            }

                            stopBoucle = false;

                        }
                        if (testHI && testVI) {

                            y = i -premiereFrequenceLigne -2;
                            indice = 1;

                            while (botteDeFoin.charAt(y) == aiguille.charAt(indice) && !stopBoucle) {
                                if (indice  == tailleAiguille - 1) {
                                    compteur++;
                                    stopBoucle = true;
                                    y+= premiereFrequenceLigne +2;
                                    indice--;
                                }
                                y -= premiereFrequenceLigne +2;
                                indice++;
                            }

                            stopBoucle = false;
                        }
                        if (testHI && testV) {
                            y = i +premiereFrequenceLigne  ;
                            indice = 1;

                            while (botteDeFoin.charAt(y) == aiguille.charAt(indice) && !stopBoucle) {
                                if (indice == tailleAiguille - 1) {
                                    compteur ++;
                                    y -= premiereFrequenceLigne;
                                    indice--;
                                    stopBoucle = true;
                                }
                                y += premiereFrequenceLigne;
                                indice++;
                            }

                            stopBoucle = false;

                        }
                        if (testH && testVI) {

                            y = i -premiereFrequenceLigne;
                            indice = 1;

                            while (botteDeFoin.charAt(y) == aiguille.charAt(indice) && !stopBoucle) {
                                if (indice  == tailleAiguille - 1) {
                                    compteur++;
                                    stopBoucle = true;
                                    y += premiereFrequenceLigne;
                                    indice--;
                                }
                                y -= premiereFrequenceLigne;
                                indice++;
                            }

                            stopBoucle = false;

                        }
                    }
                    i++;
                }
                if (estPalindrome) {
                    return compteur / 2;
                }
            }
        } else {
            for (i = 0; i < tailleBotte; i++) {
                if (botteDeFoin.charAt(i) == aiguille.charAt(0)) {
                    compteur++;
                }
            }
        }


        // On retourne le résultat
        return compteur;


    }
}

