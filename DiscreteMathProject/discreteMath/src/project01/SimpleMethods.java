package project01;

/**
 * Created by AlcanderLiu on 4/15/2017.
 */
public class SimpleMethods {

    // Check if char c is an logic operator
    public static boolean isOperator(char c)
    {
        if(c == '^' || c == 'v'
                || c == '>' || c == '<'
                || c == '=')
            return true;
        else
            return false;
    }


    public static boolean isCapitalChar(char c)
    {
        if(c >=65 && c <= 90)
            return true;
        else
            return false;
    }

    // Check if index i of the string formula is inside one of its outermost brackets.
    public static boolean insideBracket(String formula, int i)
    {
        int k;
        PairsSet bracketsSet = new PairsSet();
        bracketsSet.getBrackets(formula);
        int l = bracketsSet.amounts();

        for(k = 0; k < l; k++)
        {
            if( i > bracketsSet.pairs[k].start && i<bracketsSet.pairs[k].end-1)
                return true;
        }

        return false;
    }

    // Turn int type (0/1) into boolean type (false/true).
    public static boolean toBoolean(int input)
    {
        if(input == 1)
            return true;
        else return false;
    }

}
