package com.loicproust.zzquiz.quiz.core.beans.score;

/**
 * Created by proust on 22/03/2017.
 */

public class ScoreConditions {

    private boolean mIsTrue;

    private boolean mIsInTime;

    private int mClickKey;

    public ScoreConditions(boolean isTrue, boolean isInTime) {
        mIsTrue = isTrue;
        mIsInTime = isInTime;
        mClickKey = 0;
    }

    public ScoreConditions(boolean isTrue, boolean isInTime, int clickKey) {
        mIsTrue = isTrue;
        mIsInTime = isInTime;
        mClickKey = clickKey;
    }

    public boolean isTrue() {
        return mIsTrue;
    }

    public boolean isInTime() {
        return mIsInTime;
    }

    public int getClickKey() {
        return mClickKey;
    }
}
