package project01;

import static project01.SimpleMethods.insideBracket;

/**
 * Created by AlcanderLiu on 4/14/2017.
 */
public class Element {

    private Element [] mb_member;
    private int elementAmounts;
    private String smallestElement = null;
    private char mb_operator = 0;
    private boolean mb_sign = true;
    private boolean mb_value = true;


    public Element(String formula)
    {
        divideElementByDetectingPairs(formula);
    }

    // Originally used but not in this newest version.
    private boolean checkImplyOperator(String formula)
    {
        int i;
        int l = formula.length();
        boolean isDone = false;
        for(i = l-1; i >= 0; i--)
        {
            if(formula.charAt(i) == '=' && !insideBracket(formula, i))
            {
                mb_operator = '=';
                mb_member = new Element[2];
                mb_member[0] = new Element(formula.substring(0, i));
                mb_member[1] = new Element(formula.substring(i+1));
                elementAmounts = 2;
                isDone = true;
                break;
            }
        }
        if(!isDone)
        {
            for (i = l - 1; i >= 0; i--)
            {
                if ((formula.charAt(i) == '>' || formula.charAt(i) == '<' ) && !insideBracket(formula, i))
                {
                    mb_operator = formula.charAt(i);
                    mb_member = new Element[2];
                    mb_member[0] = new Element(formula.substring(0, i));
                    mb_member[1] = new Element(formula.substring(i + 1));
                    elementAmounts = 2;
                    isDone = true;
                    break;
                }
            }
        }

        return isDone;
    }

    private boolean divideElementByDetectingPairs(String formula)
    {
        boolean isDone = false;
        PairsSet smallElements = new PairsSet();
        mb_operator = smallElements.getElementsLoc(formula);
        elementAmounts = smallElements.amounts();
        mb_member = new Element[elementAmounts];

        // If only one child element, check if it starts with '!' or is surround by a pair of brackets
        if(smallElements.amounts() == 1)
        {
            if(formula.startsWith("!"))
            {
                mb_sign = false;
                mb_member[0] = new Element(formula.substring(1));
            }
            else if(formula.startsWith("("))
            {
                mb_member[0] = new Element(formula.substring(1,formula.length()-1));
            }
            else
            {
                smallestElement = formula;
            }
        }
        else // Create members (child elements)
        {
            int i;

            for (i = smallElements.amounts()-1; i >= 0; i--)
            {
                mb_member[i] = new Element(formula.substring(smallElements.pairs[i].start, smallElements.pairs[i].end));
            }

            mb_member[0] = new Element(formula.substring(smallElements.pairs[0].start));
        }

        isDone = true; // Successfully divided
        return isDone;
    }

    private boolean divideElementByDetectingOperator(String formula)
    {
        boolean isDone = false;
        OperatorSet operatorset = new OperatorSet();
        mb_operator = operatorset.getOperatorLoc(formula);
        if(operatorset.amounts() == 0)
        {
            if(formula.startsWith("!"))
            {
                mb_sign = false;
                mb_member[0] = new Element(formula.substring(1));
            }
            else if(formula.startsWith("("))
            {
                mb_member[0] = new Element(formula.substring(1,formula.length()-1));
            }
            else
                smallestElement = formula;
            elementAmounts = 1;
        }
        else
        {
            mb_member[0] = new Element(formula.substring(0, operatorset.loc[0]));
            int i;
            for (i = 1; i < operatorset.amounts(); i++)
            {
                mb_member[i] = new Element(formula.substring(operatorset.loc[i-1], operatorset.loc[i]));
            }
            mb_member[i] = new Element(formula.substring(operatorset.loc[i-1]));
            elementAmounts = operatorset.amounts() + 1;
        }
        isDone = true;
        return isDone;
    }

    // Return the value of this formula(element) according to the given variableSet
    // 回傳此公式 在此給定的 變數的真值組合下 的值
    public boolean getValue( VariableSet variableSet)
    {
        if(smallestElement != null)
        {
            int i;
            for(i = 0; i<variableSet.amounts(); i++)
            {
                if(smallestElement.charAt(0) == variableSet.variable[i])
                {
                    mb_value = variableSet.value[i];
                    //System.out.println();
                    //System.out.println(smallestElement + " is " + variableSet.value[i]);
                    break;
                }
            }
        }
        else if(mb_operator == 0)
        {
            mb_value = mb_member[0].getValue(variableSet);
        }
        else if(mb_operator == '=')
        {
            if(mb_member[0].getValue(variableSet) == mb_member[1].getValue(variableSet))
                mb_value = true;
            else
                mb_value = false;
        }
        else if(mb_operator == '<')
        {
            if(mb_member[0].getValue(variableSet) == true && mb_member[1].getValue(variableSet) == false)
                mb_value = false;
            else
                mb_value = true;
        }
        else if(mb_operator == '>')
        {
            if(mb_member[0].getValue(variableSet) == false && mb_member[1].getValue(variableSet) == true)
                mb_value = false;
            else
                mb_value = true;
        }
        else if(mb_operator == 'v')
        {
            int i;
            mb_value = false;
            for(i = 0; i<elementAmounts; i++)
            {
                if(mb_member[i].getValue(variableSet) == true)
                {
                    mb_value = true;
                    break;
                }
            }
        }
        else if(mb_operator == '^')
        {
            int i;
            mb_value = true;
            for(i = 0; i<elementAmounts; i++)
            {
                if(mb_member[i].getValue(variableSet) == false)
                {
                    mb_value = false;
                    break;
                }
            }
        }
        //return ( (mb_value || mb_sign ) && ! ( mb_value && mb_sign ) );

       return !(mb_value ^ mb_sign);
    }


    public void print()
    {
        if(smallestElement != null)
            System.out.print(smallestElement);
        else
        {
            if(elementAmounts == 1)
            {
                if (mb_sign == false)
                {
                    System.out.print("!");
                    mb_member[0].print();
                }
                else
                {
                    System.out.print("(");
                    mb_member[0].print();
                    System.out.print(")");
                }
            }
            else
            {
                int i;
                mb_member[0].print();
                for (i = 1; i < elementAmounts; i++)
                {
                    System.out.print(mb_operator);
                    mb_member[i].print();
                }
            }
        }
    }

}
