/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Scanner;

/**
 * View class that is responsible for interaction with user
 *
 * @author Michał Szubert
 * @version 1.0
 *
 */
public class View {

    /**
     * Class member which contains input argument strings.
     */
    public String[] argumentsArray;

    /**
     * Class method which displays menu in case of lack of arguments in input
     * arguments. displayMenu() collects input data from user and inserts it 
     * in String array which is member of View class.
     *
     */
    public void displayMenu() {
        String inputStringArguments;
        String inputStringGameType;
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Enter parameters of dices to roll (e.g. 2k6 4k8 etc.): ");
        inputStringArguments = scanIn.nextLine();
        System.out.println("Enter game type (s for summing, r for rpg): ");
        inputStringGameType = scanIn.nextLine();
        inputStringArguments = inputStringArguments.concat(" " + inputStringGameType);
        inputStringArguments= inputStringArguments.replaceAll("\\t", " ");
        this.argumentsArray = inputStringArguments.split(" ");
       
    }

    /**
     * Class method which displays result of thrown dices simulation
     *
     * @param result array of Strings which computed result of thrown dices
     * simulation
     */
    public void displayResult(String[] result) {
        System.out.println("Result of thrown dices is: ");
        for (int i = 0; i < result.length - 1; i++) {
            System.out.println("Throw of dice nr. " + (i + 1) + ": " + result[i]);
        }
        System.out.println("Giving sum of: " + result[result.length - 1]);
    }
}
