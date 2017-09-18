package hooooong.com.calculator.Model;

import java.util.ArrayList;

/**
 * Created by Android Hong on 2017-09-12.
 */

public class CalculatorData {

    // 숫자 와 연산자를 저장하는 ArrayList
    private ArrayList<String> progressValue;

    private static CalculatorData calculator;
    private CalculatorData(){
        progressValue = new ArrayList<>();
    }

    public static CalculatorData getInstance(){
        if (calculator == null)
            calculator = new CalculatorData();
        return calculator;
    }

    public ArrayList<String> getProgressValue() {
        return progressValue;
    }

    public void setProgressValue(ArrayList<String> progressValue) {
        this.progressValue = progressValue;
    }
}
