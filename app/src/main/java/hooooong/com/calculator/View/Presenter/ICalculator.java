package hooooong.com.calculator.View.Presenter;


import hooooong.com.calculator.Model.CalculatorData;

/**
 * Created by Android Hong on 2017-09-12.
 */

public interface ICalculator {

    interface View{
        // 연산식을 보여주기 위한 메소드
        void setPreviewText(String processText);
        // 현재 누른 값과 결과값을 보여주기 위한 메소드
        void setValueText(String value);
        // 메세지를 보여준다.
        void showToast();
        // 애니메이션 실행
        void viewAnimation(android.view.View view);
    }

    interface Presenter{

        void attachView(View view);
        void detachView();

        void setCalculatorData(CalculatorData calculatorData);

        // 값을 눌렀을 때
        void appendValue(android.view.View view, String value);
        // Dot(.) 을 눌렀을 경우
        void appendDot(android.view.View view);
        // 연산자를 눌렀을 때
        void appendCalculate(android.view.View view, String calculate);
        // 괄호를 눌렀을 경우
        void appendParentheses(android.view.View view, String parentheses);
        // C 를 눌렀을 때
        void clearValue(android.view.View view);
        // = 을 눌렀을 때
        void resultValue(android.view.View view);
    }
}
