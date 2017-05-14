package project01;

import static project01.SimpleMethods.*;

/**
 * Created by AlcanderLiu on 4/15/2017.
 */

// A set of variables and their values
public class VariableSet {

    public char []variable;
    public boolean [] value;
    private int amounts = 0;


    public VariableSet(int amounts)
    {
        this.amounts = amounts;
        variable = new char[amounts];
        value = new boolean[amounts];
    }

    public void setVariables(char [] symbol)
    {
        int i;
        for(i = 0; i< amounts; i++)
        {
            variable[i] = symbol[i];
        }
    }

    // 輸入十進制數，來設置此組變量的各個真值
    // 例如有變量PQR，輸入十進制0，則 P=0, Q=0, R=0.
    // 輸入十進制3 則P=0, Q=1, R=1.
    // 輸入十進制7 則P=1, Q=1, R=1.
    public void setValues(int decimalValue)
    {
        int i;
        for(i = amounts-1; i>=0; i--)
        {
            value[i] = toBoolean(decimalValue%2);
            decimalValue /= 2;
        }
    }

    public int amounts()
    {
        return amounts;
    }

}
