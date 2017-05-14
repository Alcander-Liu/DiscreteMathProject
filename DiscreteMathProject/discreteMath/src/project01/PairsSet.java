package project01;

import static project01.SimpleMethods.*;

/**
 * Created by AlcanderLiu on 4/14/2017.
 */
public class PairsSet {

    public Pairs [] pairs = new Pairs[10];
    private int amounts = 0;

    public PairsSet()
    {
        int i;
        for(i= 0; i<10; i++)
            pairs[i] = new Pairs();
    }

    // Store the outermost brackets locations in this PairsSet
    public void getBrackets(String formula)
    {
        int i;
        int l = formula.length();
        int leftBracket = 0;
        int rightBracket = 0;

        for(i = 0; i < l; i++)
        {
            if(formula.charAt(i) == '(')
            {
                if(leftBracket == rightBracket)
                {
                    pairs[amounts] = new Pairs();
                    pairs[amounts].start = i;
                }
                leftBracket ++;
            }
            else if(formula.charAt(i) == ')')
            {
                rightBracket ++;
                if(rightBracket == leftBracket)
                {
                    pairs[amounts].end = i + 1;
                    amounts++;
                }
            }
        }
    }

    // Store the outermost elements in this PairsSet.
    // Return the logic operator between those elements.
    public char getElementsLoc(String formula)
    {
        char operator = 0;
        boolean halfPart = false;
        int i;
        int l = formula.length();

        pairs[amounts].end = l;
        halfPart = true;

        for(i = l-1; i >=0; i--)
        {
            if (isOperator(formula.charAt(i)) && !insideBracket(formula, i))
            {
                operator = formula.charAt(i);
                pairs[amounts].start = i+1;
                amounts++;
                pairs[amounts].end = i;
                break;
            }
        }
        pairs[amounts].start = 0;
        amounts++;

        return operator;
    }

    public int amounts()
    {
        return amounts;
    }

}
