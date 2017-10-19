package com.loicproust.zzquiz.quiz.core.gamecore;

import android.content.Context;

import com.loicproust.zzquiz.quiz.core.JsonController;
import com.loicproust.zzquiz.quiz.core.beans.Question;
import com.loicproust.zzquiz.quiz.core.beans.QuestionsList;
import com.loicproust.zzquiz.quiz.core.beans.score.ScoreConditions;
import com.loicproust.zzquiz.quiz.ui.listeners.OnAnswerGivenListener;

import java.util.Random;


/**
 * Created by proust on 24/02/2017.
 */

public abstract class GameCore implements OnAnswerGivenListener {

    public final int CLICK_KEY_ALLOWED = 0;
    public static final int CLICK_KEY_USER = 1;

    protected Context mContext;


    public static final String QUESTIONS_JSON_FILENAME = "questions.json";


    private OnAnswerGivenListener mTrueMissedListener;


    protected Question mCurrentQuestion;
    protected QuestionsList mQuestionsList;


    /* Game mechanics properties */
    private int mClickKey = CLICK_KEY_ALLOWED;

    private int mNbMaxQuestions;
    private int mNbQuestions;


    //Questions' randomization
    private int mTruePos; //Position of the true answer
    /* Array which contain buttons positions. */
    /* How it works ? It's directly linked with mBtnPos :
     *      mButtonsPosition[0] contain the position in the list mBtnPos of the true answer
     *      mButtonsPosition[1] contain the position in the list mBtnPos of the original 2nd answer, etc
      * */
    private int[] mButtonsPosition = new int[4];



    /* Protected constructor to prevent wild instanciation */
    protected GameCore(Context context, int nbMaxQuestions) {
        mContext = context;

        mNbMaxQuestions = nbMaxQuestions;
        mNbQuestions = 0;

        mQuestionsList = JsonController.getInstance(mContext).loadQuestionsListFromAssets();

        /* Initialize the buttons positions array */
        for (int i = 0; i < 4; i++)
            mButtonsPosition[i] = i;

    }


    /* ////////// Game common methods ////////// */

    protected abstract void manageScore(ScoreConditions scoreConditions);


    public int[] setNextQuestion() {

        mNbQuestions++;
        mCurrentQuestion = mQuestionsList.nextQuestion();

        if(mCurrentQuestion != null && mNbQuestions <= mNbMaxQuestions) {
            randomizeAnswersPlacement();
            return mButtonsPosition;
        }
        return null;
    }

    public String getQuestionText() {
        return mCurrentQuestion.mQuestion;
    }


    // Verify this method, maybe it break OOP rules
    public Question getCurrentQuestion() {
        return mCurrentQuestion;
    }

    public boolean verifyAnswer(String answer) {
        return answer.equals(mCurrentQuestion.getTrueAnswer());
    }


    /**
     *
     * @return Text of the true answer of the current question
     */
    public String getTrueAnswerText() {
        return mCurrentQuestion.getTrueAnswer();
    }


    /**
     * Randomize the buttons placement by moving answer id
     * */
    private void randomizeAnswersPlacement() {
        Random rnd = new Random();
        mTruePos = 0;
        for (int i = mButtonsPosition.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i);
            int a = mButtonsPosition[index];
            mButtonsPosition[index] = mButtonsPosition[i];
            mButtonsPosition[i] = a;
            if(a == 0)
                mTruePos = i;
        }
    }

    public int getTruePos() {
        return mTruePos;
    }


    /* ////////// Progress bar method and properties ////////// */
    private static final int PROGRESS_MAX = 32;

    private int mTimeProgress = 0;
    private boolean mIsInProgress = false;

    private void updateProgress() {
        mTimeProgress++;
    }
}
