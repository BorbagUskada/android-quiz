package com.loicproust.zzquiz.quiz.core.gamecore;

import android.content.Context;

import com.loicproust.zzquiz.quiz.core.beans.score.ScoreConditions;

/**
 * Created by proust on 22/03/2017.
 */

public class MultiPlayerGameCore extends GameCore {




    private MultiPlayerGameCore(Context context, int nbMaxQuestions) {
        super(context, nbMaxQuestions);
    }

    @Override
    protected void manageScore(ScoreConditions scoreConditions) {

    }

    @Override
    public void onUnknownAnswerGiven(int viewId) {

    }
}
