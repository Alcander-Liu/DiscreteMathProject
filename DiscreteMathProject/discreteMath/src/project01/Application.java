package project01;

import java.util.Scanner;

/**
 * Created by AlcanderLiu on 5/10/2017.
 */
public class Application {

    public static void main(String args[])
    {
        int condition;
        Formula formula;
        Scanner sc = new Scanner(System.in);

        while (true)
        {
            formula = new Formula();
            condition = 0;

            // Choose function
            while (condition < 1 || condition > 3)
            {
                System.out.println("Would you like to enter a 1.Formula / 2.Table / 3.Exit: ");
                try
                {
                    condition = sc.nextInt();
                }
                catch(Exception ex)
                {
                    sc = new Scanner(System.in);
                    continue;
                }
            }

            switch (condition)
            {
                case 1:
                    formula.scanFormula();
                    formula.formTable();
                    formula.formDNF();
                    formula.formCNF();
                    formula.printTable();
                    formula.printCNF();
                    formula.printDNF();
                    break;
                case 2:
                    formula.scanTable();
                    formula.formDNF();
                    formula.formCNF();
                    formula.printCNF();
                    formula.printDNF();
                    break;
                case 3:
                    break;
            }

            if (condition == 3)
                break;
        }
    }
}
