package project01;

import static project01.SimpleMethods.insideBracket;
import static project01.SimpleMethods.isOperator;

/**
 * Created by AlcanderLiu on 4/15/2017.
 */

// This class is used in divideElementByDetectingOperator(String formula) in Element.java
// However, since divideElementByDetectingOperator(String formula) is optional in this project,
// I didn't use this class.
public class OperatorSet {

    public int [] loc = new int[10];
    private int amounts = 0;
    public char operator;

    public char getOperatorLoc(String formula)
    {
        int i;
        for(i = 0; i < formula.length(); i++)
        {
            if (isOperator(formula.charAt(i)) && !insideBracket(formula,i))
            {
                loc[amounts] = i;
                amounts++;
                operator = formula.charAt(i);
            }
        }
        return operator;
    }

    public int amounts()
    {
        return amounts;
    }
}
