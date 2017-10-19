package com.loicproust.zzquiz.quiz.core.beans.score;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by proust on 24/03/2017.
 */

public class SinglePlayerScore extends UserScore implements Parcelable {

    private int mUserScore;

    private int mNbTrueAnswers;
    private int mNbTrueOverTimeAnswers;
    private int mNbBadAnswers;

    public SinglePlayerScore() {
        mUserScore = 0;
        mNbTrueAnswers = 0;
        mNbTrueOverTimeAnswers = 0;
        mNbBadAnswers = 0;
    }

    /**
     * Constructor for the CREATOR object
     * @param source
     */
    public SinglePlayerScore(Parcel source) {
        mUserScore = source.readInt();
        mNbTrueAnswers = source.readInt();
        mNbTrueOverTimeAnswers = source.readInt();
        mNbBadAnswers = source.readInt();
    }

    @Override
    public void manageScore(ScoreConditions scoreConditions) {
        if (scoreConditions.isTrue()) {
            mNbTrueAnswers++;
            if (scoreConditions.isInTime())
                mUserScore += UserScore.GOOD_IN_SCORE;
            else {
                mUserScore += UserScore.GOOD_OUT_SCORE;
                mNbTrueOverTimeAnswers++;
            }
            return;
        } else {
            mNbBadAnswers++;
            if (scoreConditions.isInTime())
                mUserScore += UserScore.BAD_IN_SCORE;
        }

    }


    public int getNbBadAnswers() {
        return mNbBadAnswers;
    }
    public int getNbTrueAnswers() {
        return mNbTrueAnswers;
    }
    public int getNbTrueOverTimeAnswers() {
        return mNbTrueOverTimeAnswers;
    }


    /* //////////////////////////////////////////////// */
    /* ////////// Parcelable Implementations ////////// */
    /* //////////////////////////////////////////////// */

    public static final Parcelable.Creator<SinglePlayerScore> CREATOR = new Parcelable.Creator<SinglePlayerScore>() {

        @Override
        public SinglePlayerScore createFromParcel(Parcel source) {
            return new SinglePlayerScore(source);
        }

        @Override
        public SinglePlayerScore[] newArray(int size) {
            return new SinglePlayerScore[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mUserScore);
        dest.writeInt(mNbTrueAnswers);
        dest.writeInt(mNbTrueOverTimeAnswers);
        dest.writeInt(mNbBadAnswers);
    }
}
