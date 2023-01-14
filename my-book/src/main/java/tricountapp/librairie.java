package tricountapp;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Scanner;

public class librairie {
    public static String[] createFirstNamesArray(int numPeople, Scanner scanner) {
        // Array firstNames qui contient les noms
        String[] firstNames;

        firstNames = new String[numPeople];
        int index = 0;
        // Prompt for first names
        while (index < numPeople) {
            System.out.print("Entrez le prénom de la personne " + (index + 1) + " : ");
            String firstName = scanner.next();
            firstNames[index] = firstName;

            if (index > 0 && searchStringArray(firstNames, firstName, index)) {
                System.out.println(
                        "Le nom " + firstName + " est déjà présent dans la liste. Entrez un autre prénom.");
                firstNames[index] = "";
            } else {
                index++;
            }
        }

        return firstNames;
    }

    public static boolean searchStringArray(String[] array, String id, int index) {
        id = id.toLowerCase().trim();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                if (i != index && array[i].toLowerCase().trim().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int sommeTest(int a, int b) {

        return a + b;
    }

    // Créer char pour un choix
    public static char choice(Scanner scanner) {

        char choice = scanner.next().charAt(0);
        return choice;
    }

    // Calcule combien d'élément il y a dans un tableau 2d
    public static int numberElements(float[][] array2D) {
        int elements = 0;
        for (int i = 0; i < array2D.length; i++) {
            if (array2D[i][0] == 0)
                break;

            elements++;
        }
        return elements;
    }

    // Méthode qui vérifie si int deja présent dans tableau int
    public static boolean isIntInArray(int x, int[] arr) {
        for (int n : arr) {
            if (n == x) {
                return true;
            }
        }
        return false;
    }

    // Méthode pour formater les nombres en gardant max de précision
    public static double formatDecimal(double number) {
        int fractionDigits = 2;
        // Créer un objet BigDecimal à partir du nombre double
        BigDecimal bigDecimal = BigDecimal.valueOf(number);

        // Créer un objet NumberFormat pour formater le nombre
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(fractionDigits);

        String formattedNumber = numberFormat.format(bigDecimal);

        // Remplacer le séparateur de milliers par un point
        formattedNumber = formattedNumber.replace(",", ".");
        formattedNumber = formattedNumber.replace("'", "");

        // Convertir la chaîne de caractères en double

        double result = Double.parseDouble(formattedNumber);
        return result;
    }

    // Méthodes pour afficher résultat
    public static void printArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println((i + 1) + ": " + arr[i]);
        }
    }

    public static void printBool2DArray(boolean[][] array2D, int indice) {

        for (int i = 0; i < array2D.length; i++) {
            for (int j = 0; j < indice; j++) {
                System.out.print(array2D[i][j] + " ");

            }
            System.out.println();
        }
    }

    public static void printDouble2DArray(double[][] array) {
        for (double[] row : array) {
            for (double element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }

    }

    public static void testPrint(double[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < 2; j++) {

                System.out.print(array[i][j]);

            }
            System.out.print(" | ");

        }
        System.out.println();

    }
}
