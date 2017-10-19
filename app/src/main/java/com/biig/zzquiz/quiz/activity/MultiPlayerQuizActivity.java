package com.biig.zzquiz.quiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.biig.zzquiz.MenuActivity;
import com.biig.zzquiz.R;
import com.biig.zzquiz.quiz.dialog.GetReadyDialog;
import com.biig.zzquiz.quiz.core.JsonController;
import com.biig.zzquiz.quiz.core.Question;
import com.biig.zzquiz.quiz.core.QuestionsList;

import java.util.ArrayList;
import java.util.Random;

/** Main activity for split-screen Quiz game
 * Created by Loïc Proust on 27/04/2016.
 */
public class MultiPlayerQuizActivity extends AppCompatActivity implements View.OnClickListener, GetReadyDialog.OnBothUsersReadyListener {

    public static final String QUESTIONS_JSON_FILENAME = "questions.json";

    public static final String KEY_NB_ANSWERED_TRUE_USER_1 = "key_nb_answered_true_usr_1";
    public static final String KEY_NB_ANSWERED_TRUE_USER_2 = "key_nb_answered_usr_2";
    public static final String KEY_NB_ANSWERED_BAD_USER_1 = "key_nb_answered_bad_usr_1";
    public static final String KEY_NB_ANSWERED_BAD_USER_2 = "key_nb_answered_bad_usr_2";
    public static final String KEY_NB_ANSWERED_TRUE_OUT_USER_1 = "key_nb_answered_true_out_1";
    public static final String KEY_NB_ANSWERED_TRUE_OUT_USER_2 = "key_nb_answered_true_out_2";

    public static final String KEY_SCORE_USER_1 = "key_score_user_1";
    public static final String KEY_SCORE_USER_2 = "key_score_user_2";

    private static final int CLICK_KEY_ALLOWED = 0;
    private static final int CLICK_KEY_USR1 = 1;
    private static final int CLICK_KEY_USR2 = 2;

    private int mScoreUsr1;
    private TextView mTextViewScoreUsr1_1;
    private TextView mTextViewScoreUsr1_2;
    private TextView mQuestionTextUsr1;
    private Button mBtnUsr1Answ1;
    private Button mBtnUsr1Answ2;
    private Button mBtnUsr1Answ3;
    private Button mBtnUsr1Answ4;
    private int mNbTrueAnsweredUsr1;
    private int mNbTrueAnsweredOutUsr1;
    private int mNbBadAnsweredUsr1;

    private int mScoreUsr2;
    private TextView mTextViewScoreUsr2_1;
    private TextView mTextViewScoreUsr2_2;
    private TextView mQuestionTextUsr2;
    private Button mBtnUsr2Answ1;
    private Button mBtnUsr2Answ2;
    private Button mBtnUsr2Answ3;
    private Button mBtnUsr2Answ4;
    private int mNbTrueAnsweredUsr2;
    private int mNbTrueAnsweredOutUsr2;
    private int mNbBadAnsweredUsr2;

    private ArrayList<Button> mButtons; /* Contain all buttons to retrieve */
    private Button mBtnClicked;
    private int mClickKey = CLICK_KEY_ALLOWED;

    private QuestionsList mQuestionList;
    private Question mCurrentQuestion;
    private int mNbMaxQuestions;
    private int mNbQuestions;

    private int[] mBtnsPos = new int[4];
    private int mTruePos;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mScoreUsr1 = 0;
        mTextViewScoreUsr1_1 = (TextView)findViewById(R.id.tv_score_usr1_1);
        mTextViewScoreUsr1_2 = (TextView)findViewById(R.id.tv_score_usr1_2);
        mQuestionTextUsr1 = (TextView)findViewById(R.id.tv_question_usr1);
        mBtnUsr1Answ1 = (Button)findViewById(R.id.btn_usr1_answ1);
        mBtnUsr1Answ2 = (Button)findViewById(R.id.btn_usr1_answ2);
        mBtnUsr1Answ3= (Button)findViewById(R.id.btn_usr1_answ3);
        mBtnUsr1Answ4 = (Button)findViewById(R.id.btn_usr1_answ4);
        mNbTrueAnsweredUsr1 = 0;
        mNbTrueAnsweredOutUsr1 = 0;
        mNbBadAnsweredUsr1 = 0;

        mScoreUsr2 = 0;
        mTextViewScoreUsr2_1 = (TextView)findViewById(R.id.tv_score_usr2_1);
        mTextViewScoreUsr2_2 = (TextView)findViewById(R.id.tv_score_usr2_2);
        mQuestionTextUsr2 = (TextView)findViewById(R.id.tv_question_usr2);
        mBtnUsr2Answ1 = (Button)findViewById(R.id.btn_usr2_answ1);
        mBtnUsr2Answ2 = (Button)findViewById(R.id.btn_usr2_answ2);
        mBtnUsr2Answ3 = (Button)findViewById(R.id.btn_usr2_answ3);
        mBtnUsr2Answ4 = (Button)findViewById(R.id.btn_usr2_answ4);
        mNbTrueAnsweredUsr2 = 0;
        mNbTrueAnsweredOutUsr2 = 0;
        mNbBadAnsweredUsr2 = 0;

        mBtnUsr1Answ1.setOnClickListener(this);
        mBtnUsr1Answ2.setOnClickListener(this);
        mBtnUsr1Answ3.setOnClickListener(this);
        mBtnUsr1Answ4.setOnClickListener(this);

        mBtnUsr2Answ1.setOnClickListener(this);
        mBtnUsr2Answ2.setOnClickListener(this);
        mBtnUsr2Answ3.setOnClickListener(this);
        mBtnUsr2Answ4.setOnClickListener(this);

        mButtons = new ArrayList<>();
        mButtons.add(mBtnUsr1Answ1);
        mButtons.add(mBtnUsr1Answ2);
        mButtons.add(mBtnUsr1Answ3);
        mButtons.add(mBtnUsr1Answ4);
        mButtons.add(mBtnUsr2Answ1);
        mButtons.add(mBtnUsr2Answ2);
        mButtons.add(mBtnUsr2Answ3);
        mButtons.add(mBtnUsr2Answ4);

        mNbQuestions = 0;
        mNbMaxQuestions = getIntent().getExtras().getInt(MenuActivity.KEY_NB_QUESTIONS);
        mQuestionList = JsonController.getInstance(this).loadQuestionsListFromAssets();
        mBtnsPos[0] = 0;
        mBtnsPos[1] = 1;
        mBtnsPos[2] = 2;
        mBtnsPos[3] = 3;

        mTimeProgressBar = (ProgressBar)findViewById(R.id.pb_quiz_time_remaining);

        GetReadyDialog readyDialog = new GetReadyDialog(this, this);
        readyDialog.show();
    }


    @Override
    public void onClick(View v) {
        mIsInProgress = false;
        if(mClickKey == CLICK_KEY_ALLOWED) {
            switch (v.getId()) {
                case R.id.btn_usr1_answ1:
                    verifyAnswer(mBtnUsr1Answ1, CLICK_KEY_USR1);
                    break;
                case R.id.btn_usr1_answ2:
                    verifyAnswer(mBtnUsr1Answ2, CLICK_KEY_USR1);
                    break;
                case R.id.btn_usr1_answ3:
                    verifyAnswer(mBtnUsr1Answ3, CLICK_KEY_USR1);
                    break;
                case R.id.btn_usr1_answ4:
                    verifyAnswer(mBtnUsr1Answ4, CLICK_KEY_USR1);
                    break;
                case R.id.btn_usr2_answ1:
                    verifyAnswer(mBtnUsr2Answ1, CLICK_KEY_USR2);
                    break;
                case R.id.btn_usr2_answ2:
                    verifyAnswer(mBtnUsr2Answ2, CLICK_KEY_USR2);
                    break;
                case R.id.btn_usr2_answ3:
                    verifyAnswer(mBtnUsr2Answ3, CLICK_KEY_USR2);
                    break;
                case R.id.btn_usr2_answ4:
                    verifyAnswer(mBtnUsr2Answ4, CLICK_KEY_USR2);
                    break;
            }
        }
    }


    private void verifyAnswer(Button btn, int clickKey) {
        final Handler handler = new Handler();
        mClickKey = clickKey;

        if(btn.getText().equals(mCurrentQuestion.mAnsws[0])) {
            final Runnable blink;
            btn.setBackgroundResource(R.drawable.btn_quiz_true);

            if(btn.equals(mButtons.get(mTruePos)))
                blink = getTrueMissedBlink(mButtons.get(mTruePos + 4), handler);
            else
                blink = getTrueMissedBlink(mButtons.get(mTruePos), handler);

            manageScore(true, (mTimeProgress < PROGRESS_MAX), clickKey);
            handler.postDelayed(blink, 250);
        }
        else {
            manageScore(false, (mTimeProgress < PROGRESS_MAX), clickKey);
            final Runnable blinkBoth = getTrueBothBlink(mButtons.get(mTruePos), mButtons.get(mTruePos+4), handler);
            btn.setBackgroundResource(R.drawable.btn_quiz_false);
            mBtnClicked = btn;
            handler.postDelayed(blinkBoth, 250);
        }
    }

    private void setNextQuestion() {
        mNbQuestions++;
        mButtons.get(mTruePos).setBackgroundResource(R.drawable.btn_quiz);
        mButtons.get(mTruePos + 4).setBackgroundResource(R.drawable.btn_quiz);

        if (mBtnClicked != null) {
            mBtnClicked.setBackgroundResource(R.drawable.btn_quiz);
            mBtnClicked = null;
        }
        mClickKey = CLICK_KEY_ALLOWED;
        mCurrentQuestion = mQuestionList.nextQuestion();

        if(mCurrentQuestion != null && mNbQuestions <= mNbMaxQuestions) {
            mQuestionTextUsr1.setText(mCurrentQuestion.mQuestion);
            mQuestionTextUsr2.setText(mCurrentQuestion.mQuestion);
            randomizeButtons();
            mBtnUsr1Answ1.setText(mCurrentQuestion.mAnsws[mBtnsPos[0]]);
            mBtnUsr2Answ1.setText(mCurrentQuestion.mAnsws[mBtnsPos[0]]);
            mBtnUsr1Answ2.setText(mCurrentQuestion.mAnsws[mBtnsPos[1]]);
            mBtnUsr2Answ2.setText(mCurrentQuestion.mAnsws[mBtnsPos[1]]);
            mBtnUsr1Answ3.setText(mCurrentQuestion.mAnsws[mBtnsPos[2]]);
            mBtnUsr2Answ3.setText(mCurrentQuestion.mAnsws[mBtnsPos[2]]);
            mBtnUsr1Answ4.setText(mCurrentQuestion.mAnsws[mBtnsPos[3]]);
            mBtnUsr2Answ4.setText(mCurrentQuestion.mAnsws[mBtnsPos[3]]);
            mIsInProgress = true;
            mTimeProgress = 0;
            mHandler.postDelayed(mProgressBarRunnable, 125);

        } else {

            Log.i("BoUsLog", "True answer 1 : " + mNbTrueAnsweredUsr1 + " | Bad answer 1 : " + mNbBadAnsweredUsr1);
            Log.i("BoUsLog", "True answer 2 : " + mNbTrueAnsweredUsr2 + " | Bad answer 2 : " + mNbBadAnsweredUsr2);
            Intent intent = new Intent(this, ScoreActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_NB_ANSWERED_TRUE_USER_1, mNbTrueAnsweredUsr1);
            bundle.putInt(KEY_NB_ANSWERED_TRUE_USER_2, mNbTrueAnsweredUsr2);
            bundle.putInt(KEY_NB_ANSWERED_BAD_USER_1, mNbBadAnsweredUsr1);
            bundle.putInt(KEY_NB_ANSWERED_BAD_USER_2, mNbBadAnsweredUsr2);
            bundle.putInt(KEY_NB_ANSWERED_TRUE_OUT_USER_1, mNbTrueAnsweredOutUsr1);
            bundle.putInt(KEY_NB_ANSWERED_TRUE_OUT_USER_2, mNbTrueAnsweredOutUsr2);
            bundle.putInt(KEY_SCORE_USER_1, mScoreUsr1);
            bundle.putInt(KEY_SCORE_USER_2, mScoreUsr2);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

    }

    private void randomizeButtons() {
        Random rnd = new Random();
        mTruePos = 0;
        for (int i = mBtnsPos.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i);
            int a = mBtnsPos[index];
            mBtnsPos[index] = mBtnsPos[i];
            mBtnsPos[i] = a;
            if(a == 0)
                mTruePos = i;
        }
    }

    /**
     * Make the button passed by parameters blink during 2 seconds and then be green (true)
     * @param btnAdvers The button to blink
     * @param handler The handler to handle work and repetition
     * @return The runnable that do the job
     */
    private Runnable getTrueMissedBlink(final Button btnAdvers, final Handler handler) {
        return new Runnable() {
            int i;
            @Override
            public void run() {
                if(i++ % 2 == 0 && i<7)
                    btnAdvers.setBackgroundResource(R.drawable.btn_quiz_false);
                else if(i < 7)
                    btnAdvers.setBackgroundResource(R.drawable.btn_quiz_true);

                if(i < 10)
                    handler.postDelayed(this, 300);
                else {
                    handler.removeCallbacks(this);
                    setNextQuestion();
                }

            }
        };
    }

    /**
     * Make the two buttons passed by parameters blink
     * @param btnUsr1 The first button to blink
     * @param btnUsr2 The second button to blink
     * @param handler The handler to handle work and repetition
     * @return The runnable that do the job
     */
    private Runnable getTrueBothBlink(final Button btnUsr1, final Button btnUsr2, final Handler handler) {
        return new Runnable() {
            int i;
            @Override
            public void run() {
                if(i++ % 2 == 0 && i<7) {
                    btnUsr1.setBackgroundResource(R.drawable.btn_quiz_false);
                    btnUsr2.setBackgroundResource(R.drawable.btn_quiz_false);
                } else if(i < 7) {
                    btnUsr1.setBackgroundResource(R.drawable.btn_quiz_true);
                    btnUsr2.setBackgroundResource(R.drawable.btn_quiz_true);
                }

                if(i < 10)
                    handler.postDelayed(this, 300);
                else {
                    handler.removeCallbacks(this);
                    setNextQuestion();
                }

            }
        };
    }

    private void manageScore(boolean isTrue, boolean isInTime, int clickKey) {
        if(clickKey == CLICK_KEY_USR1) {

            if(isTrue) {
                if(isInTime) {
                    mScoreUsr1 += 3;
                    mScoreUsr2 += -1;
                } else {
                    mScoreUsr1 += 1;
                    mNbTrueAnsweredOutUsr1++;
                }

                mNbTrueAnsweredUsr1++;
            } else {
                if(isInTime)
                    mScoreUsr1 += -1;

                mNbBadAnsweredUsr1++;
            }

//            if (isInTime) {
//                mScoreUsr1 += (isTrue ? 3 : -1);
//                mScoreUsr2--;
//            } else
//                mScoreUsr1 += (isTrue ? 1 : 0);
//
//            if(isTrue)
//                mNbTrueAnsweredUsr1++;
//            else
//                mNbBadAnsweredUsr1++;

        } else {
            if(isTrue) {
                if(isInTime) {
                    mScoreUsr2 += 3;
                    mScoreUsr1 += -1;
                } else {
                    mScoreUsr2 += 1;
                    mNbTrueAnsweredOutUsr2++;
                }

                mNbTrueAnsweredUsr2++;
            } else {
                if(isInTime)
                    mScoreUsr2 += -1;
                mNbBadAnsweredUsr2++;
            }


//            if (isInTime) {
//                mScoreUsr2 += (isTrue ? 3 : -1);
//                mScoreUsr1--;
//            } else
//                mScoreUsr2 += (isTrue ? 1 : 0);
//
//            if(isTrue)
//                mNbTrueAnsweredUsr2++;
//            else
//                mNbBadAnsweredUsr2++;
        }

        String score = ""+ mScoreUsr1;
        mTextViewScoreUsr1_1.setText(score);
        score =" : " + mScoreUsr2;
        mTextViewScoreUsr1_2.setText(score);

        score = ""+ mScoreUsr2;
        mTextViewScoreUsr2_1.setText(score);
        score = " : " + mScoreUsr1;
        mTextViewScoreUsr2_2.setText(score);
    }


    @Override
    public void onBothPlayersReady() {
        setNextQuestion();
    }

    /* ////////////// GESTION DE LA PROGRESSBAR ////////////// */
    private static final int PROGRESS_MAX = 32;

    private ProgressBar mTimeProgressBar;
    private int mTimeProgress = 0;
    private boolean mIsInProgress = false;

    private Handler mHandler = new Handler();
    private Runnable mProgressBarRunnable = new Runnable() {
        @Override
        public void run() {
            if(mIsInProgress) {
                updateProgress();
                mHandler.postDelayed(this, 250);
            }   else
                mHandler.removeCallbacks(this);

            if(!(mTimeProgress < PROGRESS_MAX))
                mIsInProgress = false;
        }
    };

    private void updateProgress() {
        mTimeProgress++;
        mTimeProgressBar.setProgress(mTimeProgress);
    }

}
