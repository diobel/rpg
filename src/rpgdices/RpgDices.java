/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgdices;

import model.InvalidArgumentsException;
import model.Model;
import view.View;

/**
 * Class which combines funcionality of Model and View class for rpg dices
 * simulation
 *
 * @author Michał Szubert
 * @version 1.0
 */
public class RpgDices {

    /**
     * Main method creates objects of View and Model classes and calls their
     * adequate methods to compute and display results of simulation of dice
     * throwing action
     *
     * @param args the command line arguments, last parameter describes type of
     * game s - for summing mode, r - for rpg mode (with re-rolling dices)
     *
     */
    public static void main(String[] args) {
        View view = new View();
        Model model = new Model();
        boolean correctArgumentsPattern = false;
        while (!correctArgumentsPattern) {
            if (args.length == 0) {
                view.displayMenu();
            } else {
                view.argumentsArray = args.clone();
            }
            try {
                model.argumentsArray = view.argumentsArray.clone();
                view.displayResult(model.calculateResult(model.argumentsArray));
                correctArgumentsPattern = true;
            } catch (InvalidArgumentsException ex) {
                System.err.println(ex.getMessage());
                if (args.length != 0) {
                    correctArgumentsPattern = true;
                }
            }
        }
    }
}
