package com.loicproust.zzquiz.quiz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loicproust.zzquiz.R;
import com.loicproust.zzquiz.quiz.core.beans.score.SinglePlayerScore;

/**
 * Created by proust on 01/06/2017.
 */

public class ScorePanelFragment extends Fragment {

    private TextView mTextViewTrueAnswer;
    private TextView mTextViewTrueOverTimeAnswer;
    private TextView mTextViewWrongAnswer;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_score_singleplayer, container);

        mTextViewTrueAnswer = (TextView)v.findViewById(R.id.tv_good_1_1_nb);
        mTextViewTrueOverTimeAnswer = (TextView)v.findViewById(R.id.tv_true_out_1_1_nb);
        mTextViewWrongAnswer = (TextView)v.findViewById(R.id.tv_bad_1_1_nb);

        return v;
    }

    /**
     * Update UI with new score
     * @param score SinglePlayerScore object which contain updated score
     */
    public void updateScore(SinglePlayerScore score) {

        Log.d("BoUsLogScore", "Update score in progress : " + score.getNbTrueAnswers());
        mTextViewTrueAnswer.setText(""+score.getNbTrueAnswers());
        mTextViewWrongAnswer.setText(""+score.getNbBadAnswers());
        mTextViewTrueOverTimeAnswer.setText(""+score.getNbTrueOverTimeAnswers());
    }

}
