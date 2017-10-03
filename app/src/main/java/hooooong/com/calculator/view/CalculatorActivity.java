package hooooong.com.calculator.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import hooooong.com.calculator.model.CalculatorData;
import hooooong.com.calculator.R;
import hooooong.com.calculator.view.Presenter.CalculatorPresenter;
import hooooong.com.calculator.view.Presenter.ICalculator;

/**
 * Created by Android Hong on 2017-09-12.
 */

public class CalculatorActivity extends AppCompatActivity implements ICalculator.View {

    private ConstraintLayout parentPanel;

    private ICalculator.Presenter presenter;

    TextView valueTxt;
    TextView previewTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * ActionBar 와 상태 창 없애기
         */
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().getDecorView();
        // Hide the status bar.
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_calculator);

        initPresenter();
        initView();
        initListener();
    }

    /**
     * Presenter 초기화
     */
    private void initPresenter() {
        presenter = new CalculatorPresenter();
        presenter.attachView(this);
        presenter.setCalculatorData(CalculatorData.getInstance());
    }


    /**
     * View 초기화
     */
    public void initView() {
        valueTxt = (TextView) findViewById(R.id.valueTxt);
        previewTxt = (TextView) findViewById(R.id.previewTxt);
        parentPanel = (ConstraintLayout) findViewById(R.id.parentPanel);
    }

    /**
     * Listener 초기화
     */
    public void initListener() {
        findViewById(R.id.number1).setOnClickListener(onClickListener);
        findViewById(R.id.number2).setOnClickListener(onClickListener);
        findViewById(R.id.number3).setOnClickListener(onClickListener);
        findViewById(R.id.number4).setOnClickListener(onClickListener);
        findViewById(R.id.number5).setOnClickListener(onClickListener);
        findViewById(R.id.number6).setOnClickListener(onClickListener);
        findViewById(R.id.number7).setOnClickListener(onClickListener);
        findViewById(R.id.number8).setOnClickListener(onClickListener);
        findViewById(R.id.number9).setOnClickListener(onClickListener);
        findViewById(R.id.number0).setOnClickListener(onClickListener);
        findViewById(R.id.plus).setOnClickListener(onClickListener);
        findViewById(R.id.minus).setOnClickListener(onClickListener);
        findViewById(R.id.division).setOnClickListener(onClickListener);
        findViewById(R.id.multiple).setOnClickListener(onClickListener);
        findViewById(R.id.eqaul).setOnClickListener(onClickListener);
        findViewById(R.id.reset).setOnClickListener(onClickListener);
        findViewById(R.id.dot).setOnClickListener(onClickListener);
        findViewById(R.id.parenthesesStart).setOnClickListener(onClickListener);
        findViewById(R.id.parenthesesEnd).setOnClickListener(onClickListener);
    }

    /**
     * 다시 시작했을 시
     */
    @Override
    protected void onResume() {
        super.onResume();
        presenter.clearValue(null);
    }

    /**
     * 버튼 클릭 이벤트 처리
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int getId = view.getId();
            switch (getId) {
                case R.id.number1:
                    presenter.appendValue(view, "1");
                    break;
                case R.id.number2:
                    presenter.appendValue(view, "2");
                    break;
                case R.id.number3:
                    presenter.appendValue(view, "3");
                    break;
                case R.id.number4:
                    presenter.appendValue(view, "4");
                    break;
                case R.id.number5:
                    presenter.appendValue(view, "5");
                    break;
                case R.id.number6:
                    presenter.appendValue(view, "6");
                    break;
                case R.id.number7:
                    presenter.appendValue(view, "7");
                    break;
                case R.id.number8:
                    presenter.appendValue(view, "8");
                    break;
                case R.id.number9:
                    presenter.appendValue(view, "9");
                    break;
                case R.id.number0:
                    presenter.appendValue(view, "0");
                    break;
                case R.id.reset:
                    presenter.clearValue(view);
                    break;
                case R.id.dot:
                    presenter.appendDot(view);
                    break;
                case R.id.plus:
                    presenter.appendCalculate(view, "+");
                    break;
                case R.id.minus:
                    presenter.appendCalculate(view, "-");
                    break;
                case R.id.multiple:
                    presenter.appendCalculate(view, "*");
                    break;
                case R.id.division:
                    presenter.appendCalculate(view, "/");
                    break;
                case R.id.eqaul:
                    presenter.resultValue(view);
                    break;
                case R.id.parenthesesStart:
                    presenter.appendParentheses(view, "(");
                    break;
                case R.id.parenthesesEnd:
                    presenter.appendParentheses(view, ")");
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    /**
     * 계산 식 보여주는 메소드
     *
     * @param processText
     */
    @Override
    public void setPreviewText(String processText) {
        previewTxt.setText(processText);
    }

    /**
     * 숫자 현황 보여주는 메소드
     *
     * @param value
     */
    @Override
    public void setValueText(String value) {
        valueTxt.setText(value);
    }

    @Override
    public void showToast() {
        Toast.makeText(this, "괄호가 맞지 않습니다. \n식을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
    }

    /**
     * View 의 애니메이션을 설정
     *
     * @param view
     */
    @Override
    public void viewAnimation(View view) {
        Button original = (Button) view;

        // 실제 날아갈 더미를 생성해서 상위 레이아웃에 담은 후에 처리한다.
        final Button btnDummy = new Button(this);
        // 생성된 더미에 클릭한 버튼의 속성값을 적용한다.

        btnDummy.setText(original.getText().toString());
        btnDummy.setWidth(10);
        btnDummy.setHeight(30);

        btnDummy.setBackgroundColor(Color.argb(0, 0, 0, 0));
        btnDummy.setTextColor(((Button) view).getCurrentTextColor());
        btnDummy.setTextSize(25);

        // original.getParent() 는 좌표 속성이 없기 때문에
        // 부모 레이아웃을 가져와서 원래 클래스로 캐스팅
        LinearLayout parent = (LinearLayout) original.getParent().getParent();
        // 중첩Linear 이기 때문에 y축을 추가로 구할 수 있다.
        LinearLayout parent2 = (LinearLayout) original.getParent();

        // 부모 레이아웃의 위치값과 원래 버튼의 위치값을 더한다.
        btnDummy.setX(original.getX() + parent.getX());
        btnDummy.setY(parent2.getY() + parent.getY());

        // 더미를 상위 레이아웃에 담는다.
        parentPanel.addView(btnDummy);

        Random random = new Random();
        // Random 함수를 사용하여 X 좌표에 한하여 Random 으로 이동한다.
        int randomWidth = random.nextInt(parentPanel.getWidth());

        ObjectAnimator aniY = ObjectAnimator.ofFloat(
                btnDummy, "translationY", -(parent2.getY() + parent.getY())
                    /* y 로 하면 좌표를 y로 이동한다. */
        );
        ObjectAnimator aniX = ObjectAnimator.ofFloat(
                btnDummy, "translationX", randomWidth
                    /* x 로 하면 좌표를 x로 이동한다. */
        );

        ObjectAnimator aniRotate = ObjectAnimator.ofFloat(
                btnDummy, "rotation", 720F
                    /* x 로 하면 좌표를 x로 이동한다. */
        );

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(aniY, aniX, aniRotate);
        animatorSet.setDuration(3000);

        // 애니메이션 종료를 체크하기 위한 리스너를 달아준다.

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                parentPanel.removeView(btnDummy);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }
}
