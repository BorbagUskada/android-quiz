package com.loicproust.zzquiz.quiz.core.beans.score;

/**
 * Created by proust on 24/03/2017.
 */

public class MultiPlayerScore extends UserScore {

    private SinglePlayerScore mUsr1Score;

    private SinglePlayerScore mUsr2Score;

    public MultiPlayerScore() {

        mUsr1Score = new SinglePlayerScore();
        mUsr2Score = new SinglePlayerScore();
    }

    @Override
    public void manageScore(ScoreConditions scoreConditions) {
        //TODO Implement method
    }

}
