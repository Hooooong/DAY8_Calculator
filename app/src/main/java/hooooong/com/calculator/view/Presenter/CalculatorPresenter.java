package hooooong.com.calculator.view.Presenter;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import hooooong.com.calculator.model.CalculatorData;
import hooooong.com.calculator.util.CommonsUtil;


/**
 * Created by Android Hong on 2017-09-12.
 */

public class CalculatorPresenter implements ICalculator.Presenter {

    // 연산 후(= 누르고) 더 계산할 것인지 검사하는 flag 값
    private boolean flag = false;

    private CalculatorData calculatorData;
    private ICalculator.View view;

    @Override
    public void attachView(ICalculator.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void setCalculatorData(CalculatorData calculatorData) {
        this.calculatorData = calculatorData;
    }

    /**
     * 숫자를 눌렀을 경우
     *
     * @param value
     */
    @Override
    public void appendValue(View btnView, String value) {
        ArrayList<String> numberArray = calculatorData.getProgressValue();

        // 연산을 한 결과이면
        if (flag) {
            flag = false;
            clearValue(null);
        }

        if(numberArray.size() == 0){
            // Size 가 0이라면
            numberArray.add(value);
            calculatorData.setProgressValue(numberArray);

            view.setPreviewText(CommonsUtil.printPreviewValue(numberArray));
            view.setValueText(value);

            view.viewAnimation(btnView);
        }else{
            // Size 가 0이 아니라면
            if(!CommonsUtil.checkParenthesesEnd(numberArray)){
                // ")" 가 아니라면
                if(CommonsUtil.checkNumber(numberArray)){
                    // 숫자이면
                    if ("0".equals(numberArray.get(numberArray.size() - 1))) {
                        numberArray.set(numberArray.size() - 1, value);
                    } else {
                        value = numberArray.get(numberArray.size() - 1) + value;
                        numberArray.set(numberArray.size() - 1, value);
                    }
                    calculatorData.setProgressValue(numberArray);
                }else{
                    // 숫자가 아니라면

                    numberArray.add(value);
                    calculatorData.setProgressValue(numberArray);
                }

                view.setPreviewText(CommonsUtil.printPreviewValue(numberArray));
                view.setValueText(value);
                // 애니메이션 실행
                view.viewAnimation(btnView);
            }
        }
    }

    /**
     * 점을 눌렀을 경우
     */
    @Override
    public void appendDot(View btnView) {

        ArrayList<String> numberArray = calculatorData.getProgressValue();

        if (flag) {
            flag = false;
            clearValue(null);
        }

        if(numberArray.size() == 0){
            //Size 가 0이라면
            numberArray.add("0.");
            calculatorData.setProgressValue(numberArray);

            view.setPreviewText(CommonsUtil.printPreviewValue(numberArray));
            view.setValueText("0.");
            view.viewAnimation(btnView);
        }else{
            //Size 가 0이 아니라면
            if(CommonsUtil.checkNumber(numberArray)){
                // 정수,실수 숫자이면
                if(!CommonsUtil.checkDot(numberArray)){
                    // 점이 없다면
                    String value = numberArray.get(numberArray.size() - 1);
                    numberArray.set(numberArray.size() - 1, value + ".");
                    calculatorData.setProgressValue(numberArray);

                    view.setPreviewText(CommonsUtil.printPreviewValue(numberArray));
                    view.setValueText(value + ".");
                    view.viewAnimation(btnView);
                }
            }
        }
    }

    /**
     * 연산자를 눌렀을 경우
     *
     * @param calculate
     */
    @Override
    public void appendCalculate(View btnView, String calculate) {
        // 1. 데이터를 가져온다.
        ArrayList<String> numberArray = calculatorData.getProgressValue();

        if (numberArray.size() != 0) {
            // size = 0 이 아닐 경우
            // flag 변경
            flag = false;
            // 2. 연산자를 저장
            if (CommonsUtil.checkCalculate(numberArray)) {
                // 2-1. 가장 끝에 있는 값이 연산자인 경우에는
                //      연산자의 값을 바꿔준다.
                numberArray.set(numberArray.size() - 1, calculate);
                calculatorData.setProgressValue(numberArray);
            } else if (!CommonsUtil.checkParenthesesStart(numberArray)) {
                // 2-2. 가장 끝에 있는 값이 연산자가 아니고, 괄호가 아닌 경우에는
                //      ArrayList 끝에 연산자를 추가한다.
                numberArray.add(calculate);
                calculatorData.setProgressValue(numberArray);
            }
            view.setPreviewText(CommonsUtil.printPreviewValue(numberArray));
            view.viewAnimation(btnView);
        }
    }

    /**
     * 괄호를 눌렀을 경우
     *
     * @param parentheses
     */
    @Override
    public void appendParentheses(View btnView, String parentheses) {
        ArrayList<String> numberArray = calculatorData.getProgressValue();

        if ("(".equals(parentheses)) {
            // 소괄호 시작을 눌렀을 경우
            if (numberArray.size() == 0) {
                // 사이즈가 0일 경우에는
                numberArray.add("(");
                calculatorData.setProgressValue(numberArray);

                view.setPreviewText("(");
                view.viewAnimation(btnView);
            } else if (CommonsUtil.checkCalculate(numberArray)) {
                // 문자일 경우에만 넣어야된다. ( 숫자 일 때 넣으면 안됌 )
                numberArray.add("(");
                calculatorData.setProgressValue(numberArray);

                view.setPreviewText(CommonsUtil.printPreviewValue(numberArray));
                view.viewAnimation(btnView);
            }
        } else {
            // 소괄호 종료를 눌렀을 경우
            if (numberArray.size() != 0) {
                // size != 0이 아닐 때

                // "(" 갯수
                int parenthesesStartCount = CommonsUtil.getParenthesesStartCount(numberArray);
                // ")" 갯수
                int parenthesesEndCount = CommonsUtil.getParenthesesEndCount(numberArray);

                if (!CommonsUtil.checkCalculate(numberArray) && !CommonsUtil.checkLastDot(numberArray)) {
                    // 뒤에 문자가 아닐 때만( 연산자와 . )
                    if (parenthesesStartCount > parenthesesEndCount) {
                        if ("(".equals(numberArray.get(numberArray.size() - 1))) {
                            // 바로 전 문자가 "("인 경우에는 삭제
                            numberArray.remove(numberArray.get(numberArray.size() - 1));
                            calculatorData.setProgressValue(numberArray);
                        } else {
                            // 아니면 ")" 를 붙인다.

                            numberArray.add(")");
                            calculatorData.setProgressValue(numberArray);
                        }
                        view.setPreviewText(CommonsUtil.printPreviewValue(numberArray));
                        view.viewAnimation(btnView);
                    }
                }
            }
        }
    }

    /**
     * C 를 눌렀을 경우
     */
    @Override
    public void clearValue(View btnView) {
        // DB 초기화
        ArrayList<String> numberArray = calculatorData.getProgressValue();
        numberArray.clear();

        calculatorData.setProgressValue(numberArray);
        calculatorData.setProgressValue(numberArray);
        view.setValueText("");
        view.setPreviewText("");

        if (btnView != null) {
            view.viewAnimation(btnView);
        }
    }

    /**
     * = 을 눌렀을 경우
     */
    @Override
    public void resultValue(View btnView) {
        // 연산 하고 그 값을 보내줘야 한다.

        // Model 에 저장되어 있는 계산식
        ArrayList<String> numberArray = calculatorData.getProgressValue();
        double temp;

        String previewValue = CommonsUtil.printPreviewValue(numberArray);

        if (numberArray.size() != 0) {
            if (CommonsUtil.getParenthesesStartCount(numberArray) != CommonsUtil.getParenthesesEndCount(numberArray)) {
                // 괄호의 갯수가 맞지 않으면
                view.showToast();
                return;

            } else {
                // '(' 괄호의 갯수가 0이 아닐때까지 돈다
                while (CommonsUtil.getParenthesesStartCount(numberArray) != 0) {

                    // 마지막 "(" 의 index
                    int firstIndex = CommonsUtil.getParenthesesStartedLastIndex(numberArray);
                    // 첫번째 ")" 의 index
                    int lastIndex = CommonsUtil.getParenthesesEndedFirstIndex(numberArray);

                    // 임시저장소
                    ArrayList<String> tempNumArray = new ArrayList<>();

                    // 제일 마지막에 나오는 괄호 안 문자들을 tempNumberArray 에 넣는다.
                    for (int i = firstIndex + 1; i < lastIndex; i++) {
                        tempNumArray.add(numberArray.get(i));
                    }

                    // 계산
                    for (int i = 1; i < tempNumArray.size(); i = i + 2) {
                        switch (tempNumArray.get(i)) {
                            case "*":
                                temp = Double.parseDouble(tempNumArray.get(i - 1)) * Double.parseDouble(tempNumArray.get(i + 1));
                                tempNumArray.set(i - 1, String.valueOf(temp));
                                tempNumArray.remove(i);
                                tempNumArray.remove(i);
                                i = i - 2;
                                break;
                            case "/":
                                temp = Double.parseDouble(tempNumArray.get(i - 1)) / Double.parseDouble(tempNumArray.get(i + 1));
                                tempNumArray.set(i - 1, String.valueOf(temp));
                                tempNumArray.remove(i);
                                tempNumArray.remove(i);
                                i = i - 2;
                                break;
                        }
                    }

                    for(int i = 1; i<tempNumArray.size(); i = i+2){
                        switch (tempNumArray.get(i)) {
                            case "+":
                                temp = Double.parseDouble(tempNumArray.get(i - 1)) + Double.parseDouble(tempNumArray.get(i + 1));
                                tempNumArray.set(i - 1, String.valueOf(temp));
                                tempNumArray.remove(i);
                                tempNumArray.remove(i);
                                i = i - 2;
                                break;
                            case "-":
                                temp = Double.parseDouble(tempNumArray.get(i - 1)) - Double.parseDouble(tempNumArray.get(i + 1));
                                tempNumArray.set(i - 1, String.valueOf(temp));
                                tempNumArray.remove(i);
                                tempNumArray.remove(i);
                                i = i - 2;
                                break;
                        }
                    }


                    // 계산이 끝난 후 index 에 넣고
                    numberArray.set(firstIndex, tempNumArray.get(0));
                    for (int i = 0; i < lastIndex - firstIndex; i++) {
                        numberArray.remove(firstIndex + 1);
                    }
                }

                if (CommonsUtil.checkCalculate(numberArray)) {
                    // 마지막 문자가 연산자인 경우에는 그 전 결과까지 연산한 후 마지막 문자를 해준다
                    if (numberArray.size() == 1) {
                        // size 가 1인 경우에는
                        // 3 += 3
                        temp = Double.parseDouble(numberArray.get(0));

                        switch (numberArray.get(1)) {
                            case "+":
                                temp += temp;
                                break;
                            case "-":
                                temp -= temp;
                                break;
                            case "*":
                                temp *= temp;
                                break;
                            case "/":
                                temp /= temp;

                                break;
                        }
                        numberArray.set(0, String.valueOf(temp));
                    } else {
                        // size 가 1보다 큰 경우에는
                        for (int i = 1; i < numberArray.size()-1; i = i + 2) {
                            switch (numberArray.get(i)) {
                                case "*":
                                    temp = Double.parseDouble(numberArray.get(i - 1)) * Double.parseDouble(numberArray.get(i + 1));
                                    numberArray.set(i - 1, String.valueOf(temp));
                                    numberArray.remove(i);
                                    numberArray.remove(i);
                                    i = i - 2;
                                    break;
                                case "/":
                                    temp = Double.parseDouble(numberArray.get(i - 1)) / Double.parseDouble(numberArray.get(i + 1));
                                    numberArray.set(i - 1, String.valueOf(temp));
                                    numberArray.remove(i);
                                    numberArray.remove(i);
                                    i = i - 2;
                                    break;
                            }
                        }

                        for(int i = 1; i<numberArray.size()-1; i = i+2){
                            switch (numberArray.get(i)) {
                                case "+":
                                    temp = Double.parseDouble(numberArray.get(i - 1)) + Double.parseDouble(numberArray.get(i + 1));
                                    numberArray.set(i - 1, String.valueOf(temp));
                                    numberArray.remove(i);
                                    numberArray.remove(i);
                                    i = i - 2;
                                    break;
                                case "-":
                                    temp = Double.parseDouble(numberArray.get(i - 1)) - Double.parseDouble(numberArray.get(i + 1));
                                    numberArray.set(i - 1, String.valueOf(temp));
                                    numberArray.remove(i);
                                    numberArray.remove(i);
                                    i = i - 2;
                                    break;
                            }
                        }

                        temp = Double.parseDouble(numberArray.get(0));

                        switch (numberArray.get(1)) {
                            case "+":
                                temp += temp;
                                break;
                            case "-":
                                temp -= temp;
                                break;
                            case "*":
                                temp *= temp;
                                break;
                            case "/":
                                temp /= temp;
                                break;
                        }
                        numberArray.set(0, String.valueOf(temp));
                    }
                } else {
                    // 마지막 문자가 연산자가 아닌 경우
                    for (int i = 1; i < numberArray.size(); i = i + 2) {
                        switch (numberArray.get(i)) {
                            case "*":
                                temp = Double.parseDouble(numberArray.get(i - 1)) * Double.parseDouble(numberArray.get(i + 1));
                                numberArray.set(i - 1, String.valueOf(temp));
                                numberArray.remove(i);
                                numberArray.remove(i);
                                i = i - 2;
                                break;
                            case "/":
                                temp = Double.parseDouble(numberArray.get(i - 1)) / Double.parseDouble(numberArray.get(i + 1));
                                numberArray.set(i - 1, String.valueOf(temp));
                                numberArray.remove(i);
                                numberArray.remove(i);
                                i = i - 2;
                                break;
                        }
                    }

                    for(int i = 1; i<numberArray.size(); i = i+2){
                        switch (numberArray.get(i)) {
                            case "+":
                                temp = Double.parseDouble(numberArray.get(i - 1)) + Double.parseDouble(numberArray.get(i + 1));
                                numberArray.set(i - 1, String.valueOf(temp));
                                numberArray.remove(i);
                                numberArray.remove(i);
                                i = i - 2;
                                break;
                            case "-":
                                temp = Double.parseDouble(numberArray.get(i - 1)) - Double.parseDouble(numberArray.get(i + 1));
                                numberArray.set(i - 1, String.valueOf(temp));
                                numberArray.remove(i);
                                numberArray.remove(i);
                                i = i - 2;
                                break;
                        }
                    }
                }
            }

            flag = true;

            if (Double.isNaN(Double.parseDouble(numberArray.get(0))) || Double.isInfinite(Double.parseDouble(numberArray.get(0)))) {
                numberArray.clear();
                calculatorData.setProgressValue(numberArray);
                view.setValueText("계산오류");
            } else {
                // 초기화
                calculatorData.setProgressValue(numberArray);
                view.setValueText(CommonsUtil.typeChange(numberArray.get(0)));
                view.viewAnimation(btnView);
            }

            view.setPreviewText(previewValue + "=");
        }
    }
}
