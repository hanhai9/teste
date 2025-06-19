package iut.sae.algo.simplicite.etu72;

import java.util.ArrayList;

public class Recherche {

    /**
     * Cherche le nombre d'occurrences d'un mot (l'aiguille) dans une chaîne multi-lignes (la botte de foin),
     * en parcourant toutes les directions (horizontales, verticales et diagonales).
     * 
     * @param botteDeFoin : la chaine avec des retours ligne
     * @param aiguille : le mot que l'on cherche
     * @return le nombre de fois que l'aiguille est présente
     */

    public static int chercheMot(String botteDeFoin, String aiguille) {
        int compteurNbFoisAiguille = 0; //Compteur du nombre de fois que l'aiguille est trouvée
        Integer casLimite = verifierCasLimites(botteDeFoin, aiguille); //Vérification des cas limites, on stocke la valeur retounée
        if (casLimite != null) { //Si le cas était limite, on renvoi sa valeur et on arête tout
            return casLimite;
        }
        //Sinon on peut continuer
        ArrayList<ArrayList<Character>> matrice = stringEnMatrice(botteDeFoin); // Conversion en matrice de la botte de foin
        if (estValideMatrice(matrice) ) { // On vérifie que la matrice soit correcte (vérification de si elle est réguilière)
            
            if(aiguille.length() == 1){  //Cas où l'aiguille n'a qu'une seule lettre
                compteurNbFoisAiguille = compterLettreMatrice(matrice, aiguille.charAt(0)); //On compte combien de fois cette lettre est présente
            }
            else{ //Sinon, l'aiguille a plusieurs lettres, on peut se balader dans toutes les directions
                //Enregistrement des directions pour créer les déplacements : gauche, droite, bas etc
                int directionsX[] = {1,-1,0,0,1,-1,1,-1}; //Directions les déplacements en X
                int directionsY[] = {0,0,1,-1,1,1,-1,-1}; //Directions pur les déplacements en Y

                for (int y = 0; y < matrice.size(); y++) { // On parcours chaque ligne de la matrice (les Y)
                    for (int x = 0; x < matrice.get(y).size(); x++) {// On parcours chaque colonnes de la matrice (les X)

                        for(int i = 0; i<directionsX.length; i++){ //Pour chacune des lettres, on va dans les 8 directions grace aux directions en X et Y prédéfinies.
                            compteurNbFoisAiguille += deplacement(matrice, aiguille, x, y, directionsX[i], directionsY[i]); //Enregistrement dans le compteur d'aiguille de si l'aiguille est trouvée.
                        }
                    }
                }   
            }
            if(estUnPalindrome(aiguille) && aiguille.length() != 1){ //Dans le cas où c'est l'aiguille est un palindrome et que sa taille n'est pas 1
                                                                    //(Le cas où la taille est 1 à déjà été traité plus haut)
                return compteurNbFoisAiguille/2; //On divise le résultat par 2 pour corriger les doublons vu que c'est un palindrome
            }
            return compteurNbFoisAiguille; //On retourne le resultat (situation normale / taille de l'aiguille vaut 1)
        }
        return -1; //Lorsque l'aiguille est nulle, la botte de foin est nulle ou que la matrice n'est pas valide

    }

    
    /**
     * Fonction dédiée aux cas limites à vérifier avant de lancer les recherches dans le foin
     * @param pfFoin 
     * @param pfAiguille
     * @return null si on doit lancer les recherches dans le foin, 1 si on considère que l'aiguille est trouvée, 0 si l'aiguille n'est pas trouvée, 
     * et -1 si c'est un cas d'erreur
     */
    private static Integer verifierCasLimites(String pfFoin, String pfAiguille) {
        if (pfAiguille == null || pfFoin == null){ //Aiguille et foin null
            return -1;
        } 
        if (pfAiguille.isEmpty() && pfFoin.isEmpty()){ //Aiguille et foin vide
            return 1;
        } 
        if (pfAiguille.isEmpty()){ //Aiguille vide
            return 0;
        }
        if (pfFoin.equals(pfAiguille)){ //Si le foin est égal à l'éguille
            return 1;
        }
        if (pfAiguille.replace("\n", "").isEmpty()){ //On regarde si on une aiguille remplie de retour lignes
            return 0;
        }
        if (pfFoin.replace("\n", "").isEmpty()){ //On regarde si on  un foin rempli de retours lignes
            return 0;
        }
        return null; // aucun cas limite détecté
    }


    /**
     * Fonction qui permet de convertir une string en matrice de Character
     * @param pfString la chaine de caractères
     * @return la matrice (c'est une arraylist qui contient des arrayList de Character)
     */
    public static ArrayList<ArrayList<Character>> stringEnMatrice(String pfString) {

        ArrayList<ArrayList<Character>> matrice = new ArrayList<>(); //Création de la matrice

        String lignes[] = pfString.split("\n"); // A chaque retour chariot, on met la chaine dans une nouvelle case du tableau 
                                                    // (ca va représenter nos lignes de la matrice) 
        for (String motLigne : lignes) { // On parcours toutes les lignes une par une
           
            ArrayList<Character> ligneActuelle = new ArrayList<>(); // initialisation de la ligne actuelle
            // Construction de la ligne actuelle
            for (int j = 0; j < motLigne.length(); j++) { // On parcours chacune des lettres du mot/la ligne
                ligneActuelle.add(motLigne.charAt(j)); // On ajoute chacune des lettres de la ligne actuelle dans l'array list
                                                    
            }
            matrice.add(ligneActuelle); // On ajoute la ligne actuelle dans la matrice
        }
        return matrice; //On renvoit la matrice créée

    }

    /**
     * Permet de savoir si une matrice est valide ou non
     * 
     * @param pfMatrice la matrice
     * @return true si valide, false sinon
     */
    public static boolean estValideMatrice(ArrayList<ArrayList<Character>> pfMatrice) {   
        int taillePremiereLigne = pfMatrice.get(0).size(); //On récupère la taille de la toute première ligne de la matrice
        for (int i = 1; i < pfMatrice.size(); i++) { //On se balade jusqu'à la dernière ligne de la matrice
            if (pfMatrice.get(i).size() != taillePremiereLigne) { //On regarde que toutes les lignes aient la meme taille que la première 
                return false; //Si on trouve une taille de ligne différente de la première, on retourne false
            }
        }
        return true; // La matrice est valide
    }


     /**
     * Permet de vérifier qu'un mot se trouve bien dans une direction choisie dans la matrice
     * @param pfMatrice : la matrice 
     * @param pfMot : le mot à trouver
     * @param pfXDepart : l'ordonnée du point de départ       
     * @param pfYDepart : l'ordonnée du point de départ
     * @param pfXDirection : la direction de l'abscisse : 1 signifie que l'on va vers la droite dans la matrice, -1 que l'on va vers la gauche dans la matrice
     * @param pfYDirection : la direction de l'ordonnée : 1 signifie que l'on vas vers le bas la matrice, -1 signifie que l'on vas vers le haut dans la matrice
     * 
     * Exemple sur les directions :
     * 
     * A, B, C, D
     * E, F, G, H
     * I, J, K, L
     * M, N, O, P
     * 
     * Les directions peuvent etre combinées : par exemple pfXDirection = 1 et pfYDirection = 1 symbolisent un déplacement diagonale vers en bas à droite
     * En partant de pfXDepart = 0 et pfYDepart = 0 avec les directions citées ci dessus on lira la chaine suivante : A,F,K,P
     *    
     * @return 1 si le mot se trouve bien dans la direction choisie, 0 sinon
     */

    public static int deplacement(ArrayList<ArrayList<Character>> pfMatrice, String pfMot, int pfXDepart, int pfYDepart, int pfXDirection, int pfYDirection) {
        
        //On calcule préalablement les coordonnées finales du point que l'on doit atteindre dans la matrice (c'est à dire l'indice de la dernière lettre du mot)
        int xFin = pfXDirection * (pfMot.length()-1) + pfXDepart; //Ajout du X du point de départ pour compenser
        int yFin = pfYDirection * (pfMot.length()-1) + pfYDepart; //Ajout du Y du point de départ pour compenser

        if( !pointEstDansLaMatrice(pfMatrice, xFin, yFin)){ //On teste si le point n'est pas contenu dans la matrice (évite les out of bound exception)
            return 0; //Ca ne sert à rien de continuer donc on renvoit 0
        }

        //Si il est dans la matrice on peut continuer
        int indiceLettreActuelle = 0; //Sauvegarde de l'indice de parcours du mot
        while (pfXDepart != xFin ||  pfYDepart != yFin) { //Tant qu'on a pas atteint les coordonnées de fin calculées avant, on continue
            
            char lettreActuelleMot = pfMot.charAt(indiceLettreActuelle); //On récupère la lettre actuelle du mot dans le tableau
            char lettreActuelleMatrice = pfMatrice.get(pfYDepart).get(pfXDepart); //On récupère la lettre actuelle dans la matrice

            if(lettreActuelleMot != lettreActuelleMatrice){  //Si on remarque que la lettre actuelle du mot (dans le tableau) 
                                                            //est différente de la lettre actuelle de la matrice
                return 0; //On renvoit 0, le mot n'est pas présent dans cette direction choisie
            }
            //On se dirige vers les coordonées finaux grâce aux directions définies
            pfXDepart += pfXDirection; 
            pfYDepart += pfYDirection;

            indiceLettreActuelle ++; //On incrémente l'indice de parcours du mot
        }
        
        //On fait une vérification pour la dernière lettre (n'a pas été traitée par le while)
        char lettreActuelleMot = pfMot.charAt(indiceLettreActuelle); //récupération de la lettre du mot
        char lettreActuelleMatrice = pfMatrice.get(pfYDepart).get(pfXDepart); //récupération de la lettre dans la matrice

        if (lettreActuelleMot != lettreActuelleMatrice) { //On teste si elles sont différentes
            return 0; //L'aiguille n'est pas trouvée
        }
        return 1; //L'aiguille est trouvée
        
    }


    /**
     * Vérifie si un point (x, y) est à l'intérieur des limites de la matrice.
     * @param pfMatrice la matrice
     * @param pfX les coodonnées du point en X
     * @param pfY les coodonnées du point en Y
     * @return true si dedans, false si dehors
     */

    public static boolean pointEstDansLaMatrice(ArrayList<ArrayList<Character>> pfMatrice , int pfX, int pfY){

        //Pour qu'un point puisse etre dans une matrice, il faut que X >= 0
        // X < taille de la ligne
        //Y >= 0 et Y < taille de la matrice

        if((pfX > (pfMatrice.get(0).size() - 1) || pfX < 0) || (pfY > (pfMatrice.size()-1) || pfY <0) ){ //On regarde si le point peut etre placé
            return false; //Le point ne peut pas etre placé
        }
        return true; //Le point peut etre placé
    }


    /**
     * Permet de déterminer si un mot est un palindrome
     * exemple : KAYAK est un palindrome, on peut le lire dans les deux sens
     * @param pfMot
     * @return vrai si palindrome, faux sinon
     */

    public static boolean estUnPalindrome(String pfMot){

        for(int i = 0; i< pfMot.length(); i++){ //Parcours du mot
            if(pfMot.charAt(i) != pfMot.charAt((pfMot.length()-1)-i)){ //On compare la i eme lettre du mot avec la lettre qui est censée etre parallèle
                return false;
            }
        }
        return true;
    }

    /**
     * Permet de compter combien de fois une lettre est présente dans une matrice
     * @param pfMatrice : la matrice
     * @param pfLettre : la lettre à compter
     */
    public static int compterLettreMatrice(ArrayList<ArrayList<Character>> pfMatrice, char pfLettre){
        
        int compteurLettre = 0; //Initialisation du compteur de la lettre recherchée

        for(ArrayList<Character> ligne : pfMatrice){ //On parcourt chacune des lignes de la matrice
            for(char lettre : ligne){ //On parcourt chacune des lettres des lignes
                if(lettre == pfLettre){ //Si la lettre de la ligne actuelle correspond à la lettre passée en paramètre
                    compteurLettre += 1; //On ajoute 1 au compteur
                }
            }
        }
        return compteurLettre; //On retourne le nombre de fois que la lettre à été trouvée
    }

}

