package com.loicproust.zzquiz.quiz.core.beans.score;

/**
 * Class to automatize score management
 *
 * Created by proust on 28/02/2017.
 */

public abstract class UserScore {

    public static final String KEY_SCORE = "user_score";

    protected static final int GOOD_IN_SCORE = 3;
    protected static final int GOOD_OUT_SCORE = 1;
    protected static final int BAD_IN_SCORE = -1;

    private int mScore;
    private int mNbTrueOverTimeAnswers;
    private int mNbTrueAnswers;
    private int mNbBadAnwsers;


    public abstract void manageScore(ScoreConditions scoreConditions);

    public void addInTimeScore() {
        mScore += 3;
        mNbTrueAnswers++;
    }

    public void addOverTimeScore() {
        mScore++;
        mNbTrueAnswers++;
        mNbTrueOverTimeAnswers++;
    }

    public void removeInTimeScore() {
        mScore--;
        mNbBadAnwsers++;
    }


    /* // GETTERS // */
    //TODO Verify uses of all of the following methods -> Maybe only in children class
    public int getScore() {
        return mScore;
    }

    public int getNbTrueAnswers() {
        return mNbTrueAnswers;
    }

    public int getNbTrueOverTimeAnswers() {
        return mNbTrueOverTimeAnswers;
    }

    public int getNbBadAnswers() {
        return mNbBadAnwsers;
    }

}
