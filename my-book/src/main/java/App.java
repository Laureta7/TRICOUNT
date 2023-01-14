import java.util.InputMismatchException;
import java.util.Scanner;

import tricountapp.*;

public class App {

    public static void main(String[] args) throws Exception {
        int nbParticipants = 0, maxPaiements = 10, nbPaiements;
        char choix;
        float[][] payePar = new float[maxPaiements][2];
        double[][] equilibreFinal = new double[25][3];

        Scanner saisie = new Scanner(System.in);
        do {
            try {
                System.out.println("Bienvenue, entrez le nombre de participants ");
                nbParticipants = saisie.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Erreur d'entrée. Veuillez entrer un entier valide. (entre 1 et 5)");
                saisie.next();
            }
        } while (nbParticipants < 1 || nbParticipants > 5);

        String[] firstNames = librairie.createFirstNamesArray(nbParticipants, saisie);
        librairie.printArray(firstNames);

        // initialisation des variables nécissitant "nbParticipants"
        boolean[][] stockContribution = new boolean[maxPaiements][nbParticipants];
        // diff = pour chaque personne, combien ils doivent payé (négatif) ou combien il
        // doivent recevoir (positif) indice[0] = numéro personne
        double[][] diff = new double[nbParticipants][2];

        do {
            tricount.afficherMenu();
            choix = librairie.choice(saisie);
            switch (choix) {
                case '1':
                    boolean[] arrayContribution = new boolean[nbParticipants];

                    payePar = tricount.saisirDepense(payePar, firstNames, saisie);
                    nbPaiements = librairie.numberElements(payePar);
                    arrayContribution = tricount.contribution(arrayContribution, payePar[nbPaiements - 1][1],
                            firstNames,
                            saisie);
                    stockContribution = tricount.enregistrerContributions(stockContribution, arrayContribution,
                            nbPaiements);

                    // CalculateTotalPay stock dans un array double ce que chaque personne a payé au
                    // total(totalPay[indice] indice = numéro personne)
                    diff = tricount.calculerDifference(payePar, stockContribution, nbParticipants);

                    break;
                case '2':
                    nbPaiements = librairie.numberElements(payePar);
                    tricount.afficherMoyennesEtPaiements(payePar, firstNames, nbPaiements);
                    // tricount.printBool2DArray(stockContribution, nbParticipants);
                    break;
                case '3':
                    // Equilibre final , on a besoin de la somme que chaqun a payé, ainsi que ce que
                    // le montant payé total, calculer la différence et créer la méthode final
                    // librairie.printDouble2DArray(diff);
                    equilibreFinal = tricount.equilibreFinal(equilibreFinal, diff);
                    tricount.afficherEquilibreFinal(equilibreFinal, firstNames);

                    break;
                case '4':
                    // nbPaiements = librairie.numberElements(payePar);
                    // tricount.afficherMoyennesEtPaiements(payePar, firstNames, nbPaiements);
                    equilibreFinal = tricount.equilibreFinal(equilibreFinal, diff);
                    tricount.afficherEquilibreFinal(equilibreFinal, firstNames);

                    break;

                default:
                    System.out.println("Saisie incorrecte");
            }

        } while (choix != '4');
        saisie.close();
    }
}
