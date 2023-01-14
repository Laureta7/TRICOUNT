package tricountapp;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class tricount {

    public static void afficherMenu() {
        System.out.printf("%10s\n", "===============");
        System.out.printf("%1s %1s\n", "=", "Menu");

        System.out.printf("\033[33m %s \033[0m\n", "1. Dépenses");
        System.out.printf("\033[32m %s \033[0m\n", "2. moyennes et paiements");
        System.out.printf("\033[32m %s \033[0m\n", "3. Equilibre final");
        System.out.printf("\033[31m %s \033[0m\n", "4. Quitter");

        System.out.println("Votre choix (1, 2, 3 ou 4) ?");
        System.out.printf("%10s\n", "===============");
    }

    public static float[][] saisirDepense(float[][] payePar, String[] firstNames, Scanner scanner) {
        // Variables pour la méthode
        int quiPaye = 0, nbParticipants = firstNames.length;
        float montantPaye = 0;
        boolean correct;

        do {
            correct = true;

            try {

                System.out.println(
                        "Saisir le numéro de la personne qui paie (entier entre 1 et " + nbParticipants + ") : ");
                librairie.printArray(firstNames);
                quiPaye = scanner.nextInt();
                // Vérifie si le nombre saisi est compris entre 1 et le nombre de personne
                if (quiPaye >= 1 && quiPaye <= nbParticipants) {
                    System.out.println("Saisir le montant payé par " + firstNames[quiPaye - 1] + " (float) : ");
                    montantPaye = scanner.nextFloat();
                    // Remplir le tableau des que "0" est présent, (aucun contributeurs assigné ==
                    // place libre )
                    for (int i = 0; i < payePar.length; i++) {
                        if ((int) payePar[i][0] == 0) {
                            payePar[i][0] = quiPaye;
                            payePar[i][1] = montantPaye;
                            break;
                        }

                    }

                } else {
                    System.out.println(
                            "Utilisateur non valide. Veuillez entrer un nombre entier entre 1 et " + nbParticipants);
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur d'entrée. Veuillez entrer un entier valide.");
                scanner.next();
                correct = false;
            }
        } while (quiPaye < 1 || quiPaye > nbParticipants || !correct);

        return payePar;
    }

    // Contributions de chaque participant pour chaque paiement :
    public static boolean[] contribution(boolean[] arrayContribution, float montantPaye, String[] firstNames,
            Scanner scanner) {
        char termine = 'n';
        int choixPersonnes = 0, indice = 0, nbParticipants = firstNames.length;
        int[] contributeurs = new int[nbParticipants];
        do {

            // Do-while pour rentrer un participant qui participe au paiement avec test
            // d'erreur (try-catch pour exeptions)

            System.out.println("Qui participe à ce paiement (entier entre 1 et " + nbParticipants + ") : ");
            librairie.printArray(firstNames);

            do {
                try {

                    choixPersonnes = scanner.nextInt();

                    // Itérer à travers l'array contributeurs pour voir si nous avons déja choisi
                    // cette personne.

                    boolean isAlreadyChosen = librairie.isIntInArray(choixPersonnes, contributeurs);

                    if (choixPersonnes >= 1 && choixPersonnes <= nbParticipants && !isAlreadyChosen) {
                        indice = choixPersonnes - 1;
                        arrayContribution[indice] = true;
                        for (int i = 0; i < contributeurs.length; i++) {
                            if (contributeurs[i] == 0) {
                                contributeurs[i] = choixPersonnes;
                                break;
                            }

                        }
                    } else {
                        if (isAlreadyChosen)
                            System.out.printf("\033[31m %s \033[0m\n",
                                    "Vous avez déjà choisi cet utilisateur, veuillez rentrer un autre utilisateur");

                        else
                            System.out.println(
                                    "Utilisateur non valide. Veuillez entrer un nombre entier entre 1 et "
                                            + nbParticipants);

                    }
                } catch (InputMismatchException e) {
                    System.out.println("Erreur d'entrée. Veuillez entrer un entier valide.");
                    scanner.next();
                }

            } while (choixPersonnes < 1 || choixPersonnes > nbParticipants);

            // Afficher les contributeurs choisit !

            System.out.println("+------+");
            System.out.print("|contributeurs: ");
            Arrays.sort(contributeurs);
            for (int i = 0; i < contributeurs.length; i++) {
                if (contributeurs[i] != 0) {
                    System.out.println();
                    System.out.print(contributeurs[i] + " : " + firstNames[contributeurs[i] - 1]);

                }

            }

            System.out.println("|");
            System.out.println("+------+");
            if (termine != 'y') {
                System.out.println("Avez-vous désigné tout les contributeurs ('y' pour 'oui' ou any key pour 'non')? ");
                termine = scanner.next().charAt(0);

            }

        } while (termine != 'y');

        return arrayContribution;
    }

    // La méthode suivante stock tout les array contibutions que nous créons à
    // chaque nouvel dépense dans un tabelau à 2 dimension (nombre de transaction
    // max et nbParticipants)

    public static boolean[][] enregistrerContributions(boolean[][] stockContribution, boolean[] arrayContribution,
            int nbPaiements) {
        int nbParticipants = arrayContribution.length;

        for (int j = 0; j < nbParticipants; j++) {
            stockContribution[nbPaiements - 1][j] = arrayContribution[j];
        }
        return stockContribution;
    }

    public static double[][] calculerDifference(float[][] payePar, boolean[][] stockContribution,
            int nbParticipants) {
        int nbPaiements = librairie.numberElements(payePar);
        double[][] diff = new double[nbParticipants][2];
        double[] totalPay = new double[nbParticipants];
        double[] totalSpend = new double[nbParticipants];

        boolean[] keepTrack = { false, false, false, false, false };
        // Calculer sommes totales payé par chaque utilisateur
        for (int i = 0; i < nbPaiements; i++) {
            int indice = (int) payePar[i][0];
            totalPay[indice - 1] += librairie.formatDecimal(payePar[i][1]);
        }

        // Calculer sommes totales dépensés par chaque utilisateur
        for (int i = 0; i < nbPaiements; i++) {

            int count = 0;

            for (int j = 0; j < nbParticipants; j++) {
                if (stockContribution[i][j]) {
                    count++;
                    keepTrack[j] = true;
                }
            }
            double result = librairie.formatDecimal(payePar[i][1] / count);

            for (int z = 0; z < keepTrack.length; z++) {
                if (keepTrack[z]) {
                    totalSpend[z] += result;
                }

                keepTrack[z] = false;
            }
        }

        // Calculer les différence de chaqu'un: qui doit combien ou qui doit recevoir
        // combien
        for (int i = 0; i < nbParticipants; i++) {
            diff[i][0] = i + 1;
            diff[i][1] = librairie.formatDecimal(totalPay[i] - totalSpend[i]);

        }

        return diff;
    }

    public static void afficherMoyennesEtPaiements(float[][] payePar, String[] firstNames, int nbPaiements) {
        int nbParticipants = firstNames.length;
        // Calculer la somme de toutes les dépenses de chaque utilisateur
        float[][] sommeDepenses = new float[nbParticipants][2];
        for (int i = 0; i < nbParticipants; i++) {
            for (int j = 0; j < nbPaiements; j++) {
                int indice = i + 1;
                if ((int) payePar[j][0] == indice) {
                    sommeDepenses[i][0] += payePar[j][1];
                    sommeDepenses[i][1] += 1;

                }

            }

        }

        // Calculer la moyenne de dépense par utilisateur
        float[] moyenneDepenses = new float[nbParticipants];
        for (int i = 0; i < nbParticipants; i++) {
            moyenneDepenses[i] = sommeDepenses[i][0] / sommeDepenses[i][1];
        }

        // Afficher les résultats
        System.out.println("Moyennes de dépense par utilisateur :");
        for (int i = 0; i < nbParticipants; i++) {
            System.out.println(firstNames[i] + " : " + moyenneDepenses[i] + " CHF");
        }

        System.out.println("\nPaiements de chaque utilisateur :");
        for (int i = 0; i < nbParticipants; i++) {
            System.out.println(firstNames[i] + " :");
            for (int j = 0; j < payePar.length; j++) {
                if (payePar[j][0] == i + 1) {
                    System.out.println("  " + payePar[j][1] + " CHF");
                }
            }
        }

        System.out.println("\nSommes total dépensé de chaque utilisateur :");
        for (int i = 0; i < nbParticipants; i++) {
            System.out.println(firstNames[i] + " :");

            System.out.println("  " + sommeDepenses[i][0] + " CHF");

        }
    }

    // Cette méthode stock dans un tableau à 2 dimension pour chaque dépense
    public static double[][] equilibreFinal(double[][] equilibreFinal, double[][] diff) {
        double reste;
        int cpt = 0;
        for (int i = 0; i < diff.length; i++) {

            if (diff[i][1] < 0) {
                reste = diff[i][1];

                for (int j = 0; j < diff.length; j++) {

                    if (diff[j][1] > 0) {
                        if (Math.abs(reste) > diff[j][1]) {
                            reste = reste + diff[j][1];
                            equilibreFinal[cpt][0] = (diff[i][0]);
                            equilibreFinal[cpt][1] = diff[j][1];
                            equilibreFinal[cpt][2] = (diff[j][0]);

                            // changer valeur du tableau diff

                            diff[i][1] += diff[j][1];
                            diff[j][1] -= diff[j][1];
                            cpt += 1;
                            // TESTE :
                            // System.out.println(" DIFF boucle 1 ");
                            // librairie.printDouble2DArray(diff);

                        } else {

                            equilibreFinal[cpt][0] = (diff[i][0]);
                            equilibreFinal[cpt][1] = Math.abs(reste);
                            equilibreFinal[cpt][2] = (diff[j][0]);

                            // changer valeur du tableau diff

                            diff[i][1] -= reste;
                            diff[j][1] += reste;
                            cpt += 1;
                            // TESTE:

                            // System.out.println(" DIFF boucle 2 ");
                            // librairie.printDouble2DArray(diff);

                            break;

                        }

                    }
                }

            }
        }
        for (int i = 0; i < equilibreFinal.length; i++) {
            if (equilibreFinal[i][0] == 0)
                break;
            equilibreFinal[i][1] = librairie.formatDecimal(equilibreFinal[i][1]);

        }

        return equilibreFinal;
    }

    public static void afficherEquilibreFinal(double[][] equilibreFinal, String[] firstNames) {
        for (int i = 0; i < equilibreFinal.length; i++) {
            if (equilibreFinal[i][0] == 0)
                break;
            System.out.println(
                    firstNames[(int) equilibreFinal[i][0] - 1] + " doit " + equilibreFinal[i][1] + " \u00e0 "
                            + firstNames[(int) equilibreFinal[i][2] - 1]);

        }
    }

}
