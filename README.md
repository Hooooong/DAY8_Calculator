# MVP 패턴을 이용한 계산기 예제

### 설명
____________________________________________________

![계산기]()

- MVP 패턴을 이용한 계산기 예제

- 각종 예외처리와 Animation 을 추가한 예제

### KeyPoint
____________________________________________________

0. MVP 패턴

  > `MVP` 패턴이란 __M__ odel-__V__ iew-__P__ resenter 의 약자로 어플리케이션을 세가지의 역할로 구분한 소프트웨어 디자인 패턴 중 하나이다. `MVC` 패턴을 기반으로 둔 GUI를 처리하기 위한 패턴으로, `MVP`는 `MVC` 의 `View-Model` 간의 결합도를 낮추기 위해 설계되었다.

  - Model

      - MVP 의 Model 과 동일하게 Data의 전반적인 부분과 처리를 담당한다. 네트워크, 로컬 데이터 등을 포함한다.

  - View

      - UI(화면), Envet를 담당한다. 사용자의 실질적인 Event 가 발생하고, 이를 처리 담당자인 Presenter 에 전달하는 부분이기 때문에 완전한 View의 형태를 가지도록 설졔한다.

  - Presenter

      - View에서 전달받은 Event를 처리한다. 계산을 담당하거나 데이터를 가져오는 행위. 즉, 전반적인 로직을 처리하고, Model 에 요청한 데이터를 가공하여 View 에게 전달한다.  

  - 참고 : [디자인 패턴 : MVC](https://github.com/Hooooong/DAY4_MVC)

1. 사칙연산

  > `+`, `-`, `*`, `/` 를 우선순위를 통해 `*` 와 `/`를 먼저 계산해야 한다.

  0. 모든 숫자와 연산자는 `ArrayList` 에 연산 순으로 담겨 있다.

  1. `ArrayList` 의 홀수 번째에만 연산자가 있으므로 `for` 문을 통해 1, 3, 5... 번째를 찾는다.

  2. `*` 과 `/` 일 때의 index를 통해 index-1, index+1 의 값들을 계산한다.

  3. 연산을 한 숫자를 `ArrayList` 에 index-1 에 넣고, index 번째 값을 2번 지운다.

  4. 2 ~ 4 번을 `+`와 `-` 일 때로 변경한 `for` 문을 반복한다.

  ```java
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
  ```

2. 소수점 추가

  > 숫자에 소수점을 붙일 수 있다. 소수점은 단 하나만 있어야 한다.

  0. 숫자인지 아닌지 검사한다. 숫자가 아니면 점을 붙일 수 없다.

  1. 점이 있는지 없는지 검사한다.

  2. 점이 없으면 숫자에 점을 붙인다.

  ```java
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
  ```

3. 괄호 추가

  > `(`, `)` 의 갯수가 일치해야 하고, 괄호 안에 있는 연산자가 우선적으로 이뤄저야 한다. 가장 오른쪽 `(`의 index 와 가장 왼쪽 `)`의 index 를 통하여 계산을 반복적으로 실행한다.

  1. `while` 문을 통해 괄호가 있을 때 계산을 반복적으로 이룬다.

  2. `(` 의 index 와 `)` 의 index 사이의 숫자와 연산자를 `for`을 통해 별도 ArrayList 에 담는다.

  3. 별도 ArrayList 에 담은 숫자와 연산자들을 `사칙연산`의 계산식을 통해 계산한다.

  4. 모든 괄호의 계산이 끝난 후 기존 ArrayList `(` 의  index에 값을 넣어준다.

  5. `)`의  index - `(` 의 index 크기 만큼 기존 ArrayList에 값들을 지워준다.

  6. 그 이후 `사칙연산` 을 추가적으로 한다.

  ```java
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
  ```

4. 애니메이션 추가

  - `Button` 을 눌렀을 경우 `Dummy`를 생성하여 날라가는 애니메이션이다.

  - 참조 : [Animation](https://github.com/Hooooong/DAY9_Animation), [DummyAnimation](https://github.com/Hooooong/DAY10_DummyAnimation)

### Code Review
____________________________________________________

- 프로젝트 구조

  ![프로젝트 이미지]()

- CalculatorActivity.java

  - iCalculator.View 를 상속받는 Activity이다.

  - `MVP` 패턴의 View 를 담당하는 구조이다. View 에 대한 처리와 Listener 처리가 있다.

  ```java
  public class CalculatorActivity extends AppCompatActivity implements ICalculator.View {

      private ConstraintLayout parentPanel;

      private CalculatorPresenter presenter;

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
  ```

- CalculatorPresenter.java

  - iCalculator.preseneter 를 상속받는 Preseneter 이다.

  - `MVP` 패턴의 Presenter 를 담당하는 구조이다. Data 에 대한 로직 처리 및 View 에 넘어온 이벤트 처리가 있다.

  ```java
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
                          switch (numberArray.get(i)) {
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
                          switch (numberArray.get(i)) {
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
  ```

- calculatorData.java

  - `MVP` 패턴의 Model 을 담당하는 구조이다. 계산기에 필요한 Data 는 사실 없지만 `MVP` 패턴을 구현하기 위해 임시적으로 만든 클래스이다.

  ```java
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
  ```

- iCalculator.java

  - `View` 와 `Presenter` 를 이어주는 Interface 이다.

  ```java
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
  ```

- CommonsUtil.javva

  - 각종 연산에 필요한 작업들의 메소드를 모아둔 클래스이다.

  ```java
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
  ```
