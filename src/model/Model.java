/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.*;

    interface checkArgumentsInterface {

        void checkArguments(List<String> arguments) throws InvalidArgumentsException;
    }   
/**
 * Class which is used to validate input arguments correctness and perform
 * simulation of dice throwing depending on game type chosen
 *
 * @author Michał Szubert
 * @version 1.1
 */
public class Model {

    public Model() {
        resultList = new ArrayList<>();
        argumentsList = new ArrayList<>();
    }

    /**
     * Class member which contains String list of result arguments. All elements
     * of array, but last contain result of specific dice throw, last element of
     * array contains sum of all throws of dices
     */
    private List<Integer> resultList;
    /**
     * Class member which contains String list of input arguments, last element
     * of array describes game type
     */
    private List<String> argumentsList;

    /**
     * Method which sets list of Strings input arguments within model class
     *
     * @param inputArgumentsList List of Strings input Arguments
     */
    public void setArgumentsList(List<String> inputArgumentsList) {
        this.argumentsList.addAll(inputArgumentsList);
    }

    /**
     * Method whichs gets input argument list of Strings from within model class
     *
     * @return argumentsList list of Strings input arguments from within model
     * class
     */
    public List<String> getArgumentsList() {
        return this.argumentsList;
    }

    /**
     * Method whichs gets result list of Integers from within model class
     *
     * @return resultList list of Integers results from within model class
     */
    public List<Integer> getResultList() {
        return this.resultList;
    }



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
     * Method used to clean list of Strings which contains spare white spaces
     * and empty Strings
     *
     * @param strList list of Strings which contains arguments with white spaces
     * and empty Strings
     * @return cleanedList list of Strings free of spare white spaces and empty
     * Strings
     */
    private List<String> clearWhiteSpacesEmptyStrings(List<String> strList) {
        for (String examinedString : strList) {
            examinedString = examinedString.replaceAll("\\s+", "");
            examinedString = examinedString.replaceAll("\t", "");
        }
        List<String> cleanedList = new ArrayList<>();

        for (String examinedString : strList) {
            if (!examinedString.equals("")) {
                cleanedList.add(examinedString);
            }
        }
        return cleanedList;
    }

    /**
     * Method which determines correctness of input arguments
     *
     * @param arguments String list of input arguments, all arguments but last
     * should by xky where x is integer and y should be one of 4,6,8,10,12,20
     * @throws InvalidArgumentsException Exception is thrown process of
     * validation finds that arguments are invalid
     */
    private void validateArguments(List<String> argList) throws InvalidArgumentsException {

        checkArgumentsInterface checkArgs = (n) -> {
            if ((n.isEmpty()) || (n.size() == 1)) {
                throw new InvalidArgumentsException("Lack of Arguments Exception");
            }
            if ((!n.get(n.size() - 1).equals("r")) && (!n.get(n.size() - 1).equals("s"))) {
                throw new InvalidArgumentsException("Invalid Game Type Exception");
            }
            for (int i = 0; i <= n.size() - 2; i++) {
                if (!n.get(i).contains("k")) {
                    throw new InvalidArgumentsException("Invalid Dice Argument Lack of k Exception");
                }
                int charCount = 0;
                for (char c : n.get(i).toCharArray()) {
                    if (c == 'k') {
                        charCount++;
                    }
                }
                if (charCount != 1) {
                    throw new InvalidArgumentsException("Invalid Dice Argument Structure Exception");
                }
                String[] examinedString = n.get(i).split("k");
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

        };
        checkArgs.checkArguments(argList);
    }

    /**
     * Method which simulates throwing dices process with sum mode, each kind of
     * dice is thrown as many times as argument in diceArgumentsArray parameter
     * describes
     *
     * @param diceArgumentsArray String list of input arguments, all arguments
     * but last should by xky where x is integer and y should be one of
     * 4,6,8,10,12,20
     * @return result Integer list which contains results of each dice throw,
     * also last element contains sum of all throws
     */
    private List<Integer> rpgModeDraw(List<String> diceArgumentsList) {
        Integer sum = 0;
        String[] examinedString;
        List<Integer> result = new ArrayList<>();// = new String[diceArgumentsList.size()];
        Random generator = new Random();
        Integer throwSum = 0;
        Integer enteredDiceType;
        Integer throwsNumber;
        Integer thrownNumber;
        for (int i = 0; i < diceArgumentsList.size() - 1; i++) {
            examinedString = diceArgumentsList.get(i).split("k");
            throwsNumber = Integer.parseInt(examinedString[0], 10);
            enteredDiceType = Integer.parseInt(examinedString[1], 10);

            for (int j = 0; j < throwsNumber; j++) {
                do {
                    thrownNumber = (generator.nextInt(enteredDiceType)) + 1;
                    throwSum += thrownNumber;
                    sum += thrownNumber;
                } while (Objects.equals(thrownNumber, enteredDiceType));
                result.add(throwSum);
                throwSum = 0;
            }
        }
        for (Integer element : result) {
            throwSum += element;
        }
        result.add(throwSum);
        return result;
    }

    /**
     * Method which simulates throwing dices process with rpg mode, each kind of
     * dice is thrown as many times as argument (including re rolls when
     * achieving max value on current dice) in diceArgumentsArray parameter
     * describes
     *
     * @param diceArgumentsArray String list of input arguments, all arguments
     * but last should by xky where x is integer and y should be one of
     * 4,6,8,10,12,20
     * @return result Integer list which contains results of each dice throw,
     * also last element contains rpg-sum of all throws
     */
    private List<Integer> summingModeDraw(List<String> diceArgumentsList) {
        Integer sum = 0;
        String[] examinedString;
        List<Integer> result = new ArrayList<>();
        Random generator = new Random();
        int enteredDiceType;
        Integer throwsNumber;
        Integer thrownNumber;
        for (int i = 0; i < diceArgumentsList.size() - 1; i++) {
            examinedString = diceArgumentsList.get(i).split("k");
            throwsNumber = Integer.parseInt(examinedString[0], 10);
            enteredDiceType = Integer.parseInt(examinedString[1], 10);

            for (int j = 0; j < throwsNumber; j++) {
                thrownNumber = (generator.nextInt(enteredDiceType)) + 1;
                result.add(thrownNumber);
                sum += thrownNumber;
            }
        }
        result.add(sum);
        return result;
    }

    /**
     * Method which manages what kind of dice drawing is selected
     *
     * @param diceArgumentsList String list of input arguments, all arguments
     * but last should by xky where x is integer and y should be one of
     * 4,6,8,10,12,20
     * @return result Integer List which contains results of each dice throw,
     * also last element containssum of all throws
     * @throws InvalidArgumentsException Exception is thrown process of
     * validation finds that arguments are invalid
     */
    public List<Integer> calculateResult(List<String> diceArgumentsList) throws InvalidArgumentsException {
        List<Integer> result;// = new ArrayList<>();
        diceArgumentsList = this.clearWhiteSpacesEmptyStrings(diceArgumentsList);
        validateArguments(diceArgumentsList);
        if ("s".equals(diceArgumentsList.get(diceArgumentsList.size() - 1))) {
            result = summingModeDraw(diceArgumentsList);
        } else {
            result = rpgModeDraw(diceArgumentsList);
        }
        for (Integer element : result) {
            this.resultList.add(element);
        }
        return result;
    }
}
