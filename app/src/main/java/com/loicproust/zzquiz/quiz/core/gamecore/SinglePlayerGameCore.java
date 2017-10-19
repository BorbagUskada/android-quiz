package com.loicproust.zzquiz.quiz.core.gamecore;

import android.content.Context;
import android.widget.Button;

import com.loicproust.zzquiz.quiz.core.beans.score.ScoreConditions;
import com.loicproust.zzquiz.quiz.core.beans.score.SinglePlayerScore;
import com.loicproust.zzquiz.quiz.core.beans.score.UserScore;

/**
 * Created by proust on 22/03/2017.
 */

public class SinglePlayerGameCore extends GameCore {

    private UserScore mUserScore;


    public SinglePlayerGameCore(Context context, int nbMaxQuestions) {
        super(context, nbMaxQuestions);


        mUserScore = new SinglePlayerScore();

    }

    /**
     * @param scoreConditions ScoreConditions Conditions to manage score
     */
    @Override
    public void manageScore(ScoreConditions scoreConditions) {
        mUserScore.manageScore(scoreConditions);

    }


    /**
     * Tiny methods that imply return true if the given answer match the correct answer of the
     * current question
     *
     * @param givenAnswer The given answer
     * @return True if it's the correct answer
     */
    public boolean verifyAnswer(String givenAnswer) {
        return givenAnswer.equals(mCurrentQuestion.mAnsws[0]);
    }

    @Override
    public void onUnknownAnswerGiven(int viewId) {

    }


    public void onUnknownAnswerGiven(Button clickedButton) {

    }
}
