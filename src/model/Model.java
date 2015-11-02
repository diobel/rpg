/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;

/**
 * Class which is used to validate input arguments correctness and perform
 * simulation of dice throwing depending on game type chosen
 *
 * @author Michał Szubert
 * @version 1.0
 */
public class Model {

    /**
     * Class member which contains String array of input arguments, last element
     * of array describes game type
     */
    public String[] argumentsArray;
    /**
     * Class member which contains String array of result arguments. All
     * elements of array, but last contain result of specific dice throw, last
     * element of array contains sum of all throws of dices
     */
    public String[] resultArray;

    /**
     * Method used to determine if parameter String is numeric
     *
     * @param str examined string
     * @return true when input String is numeric, false when input String is not
     * numeric
     */
    private boolean stringNumericCheck(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method used to clean array of Strings which contains spare white spaces
     * and empty Strings
     *
     * @param strArray array of Strings which contains arguments with white
     * spaces and empty Strings
     * @return array of Strings free of spare white spaces and empty Strings
     */
    private String[] clearWhiteSpacesEmptyStrings(String[] strArray) {
        int emptyStringCounter = 0;
        for (String examinedString : strArray) {
            examinedString = examinedString.replaceAll("\\s+", "");
            examinedString = examinedString.replaceAll("\t", "");
            if (examinedString.equals("")) {
                emptyStringCounter++;
            }
        }
        String[] cleanedArray = new String[strArray.length - emptyStringCounter];
        emptyStringCounter = 0;
        for (String examinedString : strArray) {
            if (!examinedString.equals("")) {
                cleanedArray[emptyStringCounter] = examinedString;
                emptyStringCounter++;
            }
        }
        return cleanedArray;
    }

    /**
     * Method which determines correctness of input arguments
     *
     * @param arguments String array of input arguments, all arguments but last
     * should by xky where x is integer and y should be one of 4,6,8,10,12,20
     * @throws InvalidArgumentsException Exception is thrown process of
     * validation finds that arguments are invalid
     */
    private void validateArguments(String[] arguments) throws InvalidArgumentsException {
        if ((arguments.length == 0) || (arguments.length == 1)) {
            throw new InvalidArgumentsException("Lack of Arguments Exception");
        }
        if ((!arguments[arguments.length - 1].equals("r")) && (!arguments[arguments.length - 1].equals("s"))) {
            throw new InvalidArgumentsException("Invalid Game Type Exception");
        }
        for (int i = 0; i <= arguments.length - 2; i++) {
            if (!arguments[i].contains("k")) {
                throw new InvalidArgumentsException("Invalid Dice Argument Structure Exception");
            }
            int charCount = 0;
            for (char c : arguments[i].toCharArray()) {
                if (c == 'k') {
                    charCount++;
                }
            }
            if (charCount != 1) {
                throw new InvalidArgumentsException("Invalid Dice Argument Structure Exception");
            }
            String[] examinedString = arguments[i].split("k");
            if ((examinedString.length <= 1) || (examinedString[0].equals("")) || (examinedString[1].equals(""))) {
                throw new InvalidArgumentsException("Invalid Dice Structure Argument Exception");
            }
            if ((!stringNumericCheck(examinedString[0])) || (!stringNumericCheck(examinedString[1]))) {
                throw new InvalidArgumentsException("Invalid Dice Argument Numeric Exception");
            }
            int enteredDiceType = Integer.parseInt(examinedString[1], 10);
            if (!(((enteredDiceType >= 4) && (enteredDiceType <= 12) && (enteredDiceType % 2 == 0)) || (enteredDiceType == 20))) {
                throw new InvalidArgumentsException("Invalid Dice Type Exception");
            }
        }
    }

    /**
     * Method which simulates throwing dices process with sum mode, each kind of dice
     * is thrown as many times as argument in diceArgumentsArray parameter
     * describes
     *
     * @param diceArgumentsArray String array of input arguments, all arguments
     * but last should by xky where x is integer and y should be one of
     * 4,6,8,10,12,20
     * @return String array which contains results of each dice throw, also last
     * element contains sum of all throws
     */
    private String[] rpgModeDraw(String[] diceArgumentsArray) {
        Integer sum = 0;
        String[] examinedString;
        String[] result = new String[diceArgumentsArray.length];
        Random generator = new Random();
        int enteredDiceType;
        int throwsNumber;
        Integer thrownNumber;
        for (int i = 0; i < diceArgumentsArray.length - 1; i++) {
            examinedString = diceArgumentsArray[i].split("k");
            throwsNumber = Integer.parseInt(examinedString[0], 10);
            enteredDiceType = Integer.parseInt(examinedString[1], 10);
            result[i] = "";
            for (int j = 0; j < throwsNumber; j++) {
                do {
                    thrownNumber = (generator.nextInt(enteredDiceType)) + 1;
                    result[i] += thrownNumber.toString() + " ";
                    sum += thrownNumber;
                } while (thrownNumber == enteredDiceType);
            }
        }
        result[diceArgumentsArray.length - 1] = sum.toString();
        return result;
    }

    /**
     * Method which simulates throwing dices process with rpg mode, each kind of dice
     * is thrown as many times as argument (including re rolls when achieving
     * max value on current dice) in diceArgumentsArray parameter describes
     *
     * @param diceArgumentsArray String array of input arguments, all arguments
     * but last should by xky where x is integer and y should be one of
     * 4,6,8,10,12,20
     * @return String array which contains results of each dice throw, also last
     * element contains rpg-sum of all throws
     */
    private String[] summingModeDraw(String[] diceArgumentsArray) {
        Integer sum = 0;
        String[] examinedString;
        String[] result = new String[diceArgumentsArray.length];
        Random generator = new Random();
        int enteredDiceType;
        int throwsNumber;
        Integer thrownNumber;
        for (int i = 0; i < diceArgumentsArray.length - 1; i++) {
            examinedString = diceArgumentsArray[i].split("k");
            throwsNumber = Integer.parseInt(examinedString[0], 10);
            enteredDiceType = Integer.parseInt(examinedString[1], 10);
            result[i] = "";
            for (int j = 0; j < throwsNumber; j++) {
                thrownNumber = (generator.nextInt(enteredDiceType)) + 1;
                result[i] += thrownNumber.toString() + " ";
                sum += thrownNumber;
            }
        }
        result[diceArgumentsArray.length - 1] = sum.toString();
        return result;
    }

    /**
     * Method which manages what kind of dice drawing is selected
     *
     * @param diceArgumentsArray String array of input arguments, all arguments
     * but last should by xky where x is integer and y should be one of
     * 4,6,8,10,12,20
     * @return String array which contains results of each dice throw, also last
     * element contains rpg-sum of all throws
     * @throws InvalidArgumentsException Exception is thrown process of
     * validation finds that arguments are invalid
     */
    public String[] calculateResult(String[] diceArgumentsArray) throws InvalidArgumentsException {
        String[] result;
        diceArgumentsArray = this.clearWhiteSpacesEmptyStrings(diceArgumentsArray);
        validateArguments(diceArgumentsArray);
        if (diceArgumentsArray[diceArgumentsArray.length - 1].equals("s")) {
            result = summingModeDraw(diceArgumentsArray);
        } else {
            result = rpgModeDraw(diceArgumentsArray);

        }
        resultArray = result.clone();
        return result;
    }
}
