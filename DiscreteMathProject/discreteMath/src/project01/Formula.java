package project01;

import java.util.Scanner;
import static project01.SimpleMethods.isCapitalChar;
import static project01.SimpleMethods.isOperator;


/**
 * Created by AlcanderLiu on 4/14/2017.
 */
public class Formula {

    private Element inputFormula;
    private String  CNF;
    private String  DNF;
    private String input;
    private Table table;
    private char[] symbol = new char[5];
    private int symbolAmounts = 0;


    public Formula()
    {
        resetSymbols();
    }

    // Reset all the symbols
    private void resetSymbols()
    {
        int i;
        for(i = 0; i<5; i++)
            symbol[i] = 0;
        symbolAmounts = 0;
    }

    public void scanFormula()
    {
        int i;
        Scanner sc = new Scanner(System.in);

        resetSymbols();

        System.out.println("Enter your formula: ");
        input = sc.nextLine();

        // Check if it is legal
        while(!checkLegitimacy())  // If not legal
        {
            System.out.println("Your input is not legal.");
            System.out.println("Enter your formula: ");
            input = sc.nextLine();
        }

        countSymbols();

        // Create element
        inputFormula = new Element(input);
    }


    public void scanTable()
    {
        int i;
        Scanner sc = new Scanner(System.in);

        resetSymbols();
        System.out.println("How many variables (from 1 to 5): ");
        symbolAmounts = sc.nextInt();

        // Check if is legal
        while(symbolAmounts <1 || symbolAmounts >5)
        {
            System.out.println("Invalid number.");
            System.out.println("How many variables (from 1 to 5): ");
        }

        // Set symbols as P, Q, R, S....
        for(i = 0; i<symbolAmounts; i++)
        {
            symbol[i] = 'P';
            symbol[i] += i;
        }

        table = new Table(symbolAmounts, symbol);
        table.scan();
    }


    // Print the formula input
    public void printFormula()
    {
        inputFormula.print();
    }

    public void printTable()
    {
        table.print();
    }

    public void formDNF()
    {
        DNF = table.formDNF();
    }

    public void printDNF()
    {
        System.out.println("Disjunctive normal form: " + DNF);
    }

    public void formCNF()
    {
        CNF = table.formCNF();
    }

    public void printCNF()
    {
        System.out.println("Conjunctive normal form: " + CNF);
    }

    public void formTable()
    {
        table = new Table(symbolAmounts, symbol);
        table.formTable(inputFormula);
    }


    private boolean checkLegitimacy()
    {
        if(input == null)
            return false;
        if(!checkCharacters())
            return false;
        if(!checkBrackets())
            return false;

        return true;
    }


    // Detect illegal chars, sequences, beginning, and end
    private boolean checkCharacters()
    {
        int i;
        int l = input.length();
        char temp;

        // Detect illegal chars
        for(i = 0; i<l; i++)
        {
            temp = input.charAt(i);
            if ( !isCapitalChar(temp) && temp != '(' && temp != ')' &&
                         temp != '!' && !isOperator(temp) )
                return false;
        }

        // Detect illegal sequences of chars
        for( i = 1; i<l; i++)
        {
            temp = input.charAt(i);
            if( isCapitalChar(input.charAt(i-1)) &&
                    ( isCapitalChar(temp) || temp == '(') )
                return false;
            if(isOperator(input.charAt(i-1)) &&
                    ( isOperator(temp) || temp == ')'))
                return false;
            if(input.charAt(i-1) == '(' && isOperator(temp))
                return false;
            if(input.charAt(i-1) == ')' && isCapitalChar(temp))
                return false;
            if(input.charAt(i-1) == '!' && !isCapitalChar(temp) && temp!='(')
                return false;
        }

        // Detect illegal beginning and end
        if(isOperator(input.charAt(0)) || isOperator(input.charAt(l-1)) || input.charAt(l-1) == '!')
            return false;

        return true;
    }


    // Check if brackets are legal pairs
    private boolean checkBrackets()
    {
        int i;
        int l = input.length();
        int leftBracket = 0;
        int rightBracket = 0;

        for(i = 0; i < l; i++)
        {
            if(input.charAt(i) == '(')
                leftBracket ++;
            else if(input.charAt(i) == ')')
            {
                rightBracket ++;
                if(rightBracket > leftBracket)
                    return false;
            }
        }
        if(rightBracket < leftBracket)
            return false;

        return true;
    }

    // Count the amount of different symbols and store it in symbolAmounts.
    // Register every new symbol in symbol[].
    private void countSymbols()
    {
        int i;
        int l = input.length();
        char temp;

        for(i = 0; i<l; i++)
        {
            temp = input.charAt(i);
            if(isCapitalChar(temp) && isNewSymbol(temp))
            {
                symbol[symbolAmounts] = temp;
                symbolAmounts++;
            }
        }
    }

    private boolean isNewSymbol(char a)
    {
        int i;
        for(i = 0; i<symbolAmounts; i++)
            if(symbol[i] == a)
                return false;
        return true;
    }

}