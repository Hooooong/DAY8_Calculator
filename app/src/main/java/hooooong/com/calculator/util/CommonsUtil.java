package hooooong.com.calculator.util;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Android Hong on 2017-09-13.
 */

public class CommonsUtil {

    /**
     * 계산식의 마지막이 연산자 인지 구별하는 메소드
     *
     * @param numberArray
     * @return true : 연산자 맞음 , false : 연산자 아님
     */
    public static boolean checkCalculate(ArrayList<String> numberArray) {
        // 가장 마지막값이
        String text = numberArray.get(numberArray.size() - 1);
        if ("+".equals(text) || "-".equals(text) || "/".equals(text) || "*".equals(text)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 계산식의 마지막이 숫자인지 아닌지 검사
     * (정수, 실수인것들만 검사)
     *
     * @param numberArray
     * @return
     */
    public static boolean checkNumber(ArrayList<String> numberArray) {
        // 가장 마지막값이 숫자이면
        String text = numberArray.get(numberArray.size() - 1);
        String pattern = "^\\d*(\\.?\\d*)$";
        if (Pattern.matches(pattern, text)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 계산식의 마지막이 (이면
     *
     * @param numberArray
     * @return
     */
    public static boolean checkParenthesesStart(ArrayList<String> numberArray) {
        // 가장 마지막값이
        String text = numberArray.get(numberArray.size() - 1);
        if ("(".equals(text)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 계산식의 마지막이 ) 이면
     *
     * @param numberArray
     * @return
     */
    public static boolean checkParenthesesEnd(ArrayList<String> numberArray) {
        // 가장 마지막값이
        String text = numberArray.get(numberArray.size() - 1);
        if (")".equals(text)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 마지막 숫자에서 점의 유무를 검사하는 메소드
     *
     * @param numberArray
     * @return true : 있음, false : 없음
     */
    public static boolean checkDot(ArrayList<String> numberArray) {
        int count = 0;
        String number = numberArray.get(numberArray.size() - 1);

        String splitValue[] = number.split("");

        for (String temp : splitValue) {
            if (".".equals(temp)) {
                count = 1;
                break;
            }
        }

        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 숫자의 마지막이 . 인 경우 검사
     *
     * @param numberArray
     * @return
     */
    public static boolean checkLastDot(ArrayList<String> numberArray) {
        boolean check = false;
        String splitValue[] = numberArray.get(numberArray.size() - 1).split("");

        if (".".equals(splitValue[splitValue.length - 1])) {
            check = true;
        }
        return check;
    }

    /**
     * ( 의 갯수를 가져오는 메소드
     *
     * @param numberArray
     * @return
     */
    public static int getParenthesesStartCount(ArrayList<String> numberArray) {
        int count = 0;
        for (String number : numberArray) {
            if ("(".equals(number)) {
                count++;
            }
        }
        return count;
    }

    /**
     * ) 의 갯수를 가져오는 메소드
     *
     * @param numberArray
     * @return
     */
    public static int getParenthesesEndCount(ArrayList<String> numberArray) {
        int count = 0;

        for (String number : numberArray) {
            if (")".equals(number)) {
                count++;
            }
        }
        return count;
    }

    /**
     * ( 의 마지막 Index
     *
     * @return
     */
    public static int getParenthesesStartedLastIndex(ArrayList<String> numberArray) {
        int index = 0;
        for (int i = 0; i < numberArray.size(); i++) {
            if ("(".equals(numberArray.get(i))) {
                index = i;
            }
        }
        return index;
    }

    /**
     * ) 의 첫번째 Index
     *
     * @return
     */
    public static int getParenthesesEndedFirstIndex(ArrayList<String> numberArray) {
        int index = 0;
        for (int i = 0; i < numberArray.size(); i++) {
            if (")".equals(numberArray.get(i))) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * 계산식 출력
     *
     * @param numberArray
     * @return
     */
    public static String printPreviewValue(ArrayList<String> numberArray) {
        String previewValues = "";
        for (String txt : numberArray) {
            previewValues += txt;
        }
        return previewValues;
    }

    public static String typeChange(String value) {
        double type;
        if (value != null && value.length() > 0) {
            try {

                type = Double.parseDouble(value);

            } catch (NumberFormatException e) {
                return value;
            }
            if (type == (long) type) {
                return String.format("%d", (long) type);
            } else {
                return String.format("%s", type);
            }

        } else {
            // 빈 여백일 경우
            return value;
        }
    }
}
