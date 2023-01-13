import java.util.Scanner;

import tricountapp.*;

public class App {
    public static void main(String[] args) {
        Scanner saisie = new Scanner(System.in);
        System.out.println("Hello World!");
        int result = librairie.somme(1, 2);
        System.out.println(result);

        System.out.println("Rentrer un int : ");
        int number = librairie.test(saisie);

        System.out.println("Number : " + number);

        tricount.names();
    }
}
