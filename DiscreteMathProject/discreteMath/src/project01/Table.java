package project01;

import java.util.Scanner;

/**
 * Created by AlcanderLiu on 4/15/2017.
 */
public class Table {

    private boolean [] value; // 在變量不同真值情況下 的 公式值
    private VariableSet[] inputs; // 每個Set為一種個變量的某一解釋
    private char [] variables;
    private int row = 0;
    private int column = 0;

    // Create an empty table with certain rows and columns and known symbols
    public Table(int column, char [] variables)
    {
        this.column = column;
        this.variables = variables;
        row = (int) Math.pow(2,column);
        value = new boolean[row];
        inputs = new VariableSet[row];
    }


    public void print()
    {
        int i,j;

        for(i = 0; i<column; i++)
            System.out.print("| " + variables[i] + " ");
        System.out.println("| value |");

        for(j=0; j<row; j++)
        {
            for (i = 0; i < column; i++)
            {

                if(inputs[j].value[i])
                    System.out.print("| T ");
                else
                    System.out.print("| F ");
            }

            if(value[j])
                System.out.println("|   T   |");
            else
                System.out.println("|   F   |");
        }
    }


    public void scan()
    {
        int i,j;
        char tempValue;
        Scanner sc = new Scanner(System.in);
        VariableSet tempVariableSet;

        for(i = 0; i<column; i++)
            System.out.print("| " + variables[i] + " ");
        System.out.println("| value |");

        for(i = 0; i<row; i++)
        {
            tempVariableSet = new VariableSet(column);
            tempVariableSet.setVariables(variables);// 設定此行的變量名稱
            tempVariableSet.setValues(i);// 設定此行的變量真值組合

            inputs[i] = tempVariableSet;

            for (j = 0; j < column; j++)
            {
                if(inputs[i].value[j])
                    System.out.print("| T ");
                else
                    System.out.print("| F ");
            }
            System.out.print("|   ");
            tempValue = sc.next().charAt(0);

            while(tempValue != 'T' && tempValue != 'F')
            {
                System.out.println("Please enter T or F");
                tempValue = sc.next().charAt(0);
            }

            value[i] = (tempValue == 'T'); // 記下此行(此真值組合下)的公式值
        }
    }


    public String formDNF()
    {
        char [] charDNF = new char[500];
        int i,j;
        int index = 0;

        for(i=0; i<row; i++)
        {
            if(value[i])
            {
                charDNF[index] = '(';
                index++;
                for (j = 0; j < column; j++)
                {
                    if (!inputs[i].value[j])
                    {
                        charDNF[index] = '!';
                        index++;
                    }
                    charDNF[index] = inputs[i].variable[j];
                    index++;
                    charDNF[index] = '^';
                    index++;
                }
                index--;
                charDNF[index] = ')';
                index++;
                charDNF[index] = 'v';
                index++;
            }
        }

        String stringDNF;
        if(index == 0)
            stringDNF = "F";
        else
        {
            stringDNF = new String(charDNF);
            stringDNF = stringDNF.substring(0, index - 1);
        }
        return stringDNF;
    }


    public String formCNF()
    {
        char [] charCNF = new char[500];
        int i,j;
        int index = 0;

        for(i=0; i<row; i++)
        {
            if(!value[i])
            {
                charCNF[index] = '(';
                index++;
                for (j = 0; j < column; j++) {
                    if (inputs[i].value[j]) {
                        charCNF[index] = '!';
                        index++;
                    }
                    charCNF[index] = inputs[i].variable[j];
                    index++;
                    charCNF[index] = 'v';
                    index++;
                }
                index--;
                charCNF[index] = ')';
                index++;
                charCNF[index] = '^';
                index++;
            }
        }

        String stringCNF;
        if(index == 0)
            stringCNF = "T";
        else
        {
            stringCNF = new String(charCNF);
            stringCNF = stringCNF.substring(0, index - 1);
        }
        return stringCNF;
    }

    public int column()
    {
        return column;
    }

    public int row()
    {
        return row;
    }


    public void formTable(Element formula)
    {
        VariableSet variableSet;

        int i;
        for(i = 0; i<row; i++)
        {
            variableSet = new VariableSet(column);
            variableSet.setVariables(variables);
            variableSet.setValues(i);

            inputs[i] = variableSet;
            value[i] = formula.getValue(variableSet);
        }
    }
}
