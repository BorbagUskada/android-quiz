package com.biig.zzquiz.quiz.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.biig.zzquiz.R;

/** Activity to display final score after a game
 * Created by Loïc Proust on 02/05/2016.
 */
public class ScoreActivity extends Activity {

    private static final String USER_1 = "Joueur 1";
    private static final String USER_2 = "Joueur 2";

    private Bundle mBundle;

    private TextView mTextViewMainRes_1;
    private TextView mTextViewPlayer_1_1;
    private TextView mTextViewPlayer_2_1;
    private TextView mTextViewTrue_1_1;
    private TextView mTextViewTrueOut_1_1;
    private TextView mTextViewBad_1_1;
    private TextView mTextViewTrue_1_2;
    private TextView mTextViewTrueOut_1_2;
    private TextView mTextViewBad_1_2;

    private TextView mTextViewMainRes_2;
    private TextView mTextViewPlayer_1_2;
    private TextView mTextViewPlayer_2_2;
    private TextView mTextViewTrue_2_1;
    private TextView mTextViewTrueOut_2_1;
    private TextView mTextViewBad_2_1;
    private TextView mTextViewTrue_2_2;
    private TextView mTextViewTrueOut_2_2;
    private TextView mTextViewBad_2_2;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_score);



        View v = findViewById(R.id.rl_main_quiz_score);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBundle = getIntent().getExtras();


        mTextViewTrue_1_1 = (TextView)findViewById(R.id.tv_good_1_1_nb);
        mTextViewTrueOut_1_1 = (TextView)findViewById(R.id.tv_true_out_1_1_nb);
        mTextViewBad_1_1 = (TextView)findViewById(R.id.tv_bad_1_1_nb);
        mTextViewTrue_1_2 = (TextView)findViewById(R.id.tv_good_1_2_nb);
        mTextViewTrueOut_1_2 = (TextView)findViewById(R.id.tv_true_out_1_2_nb);
        mTextViewBad_1_2 = (TextView)findViewById(R.id.tv_bad_1_2_nb);


        mTextViewTrue_2_1 = (TextView)findViewById(R.id.tv_good_2_1_nb);
        mTextViewTrueOut_2_1 = (TextView)findViewById(R.id.tv_true_out_2_1_nb);
        mTextViewBad_2_1 = (TextView)findViewById(R.id.tv_bad_2_1_nb);
        mTextViewTrue_2_2= (TextView)findViewById(R.id.tv_good_2_2_nb);
        mTextViewTrueOut_2_2 = (TextView)findViewById(R.id.tv_true_out_2_2_nb);
        mTextViewBad_2_2 = (TextView)findViewById(R.id.tv_bad_2_2_nb);

        mTextViewPlayer_1_1 = (TextView)findViewById(R.id.tv_usr1_main_res);
        mTextViewPlayer_2_1 = (TextView)findViewById(R.id.tv_usr2_main_res);

        mTextViewPlayer_1_2 = (TextView)findViewById(R.id.tv_player_1_2);
        mTextViewPlayer_2_2 = (TextView)findViewById(R.id.tv_player_2_2);





        if(mBundle.getInt(MultiPlayerQuizActivity.KEY_SCORE_USER_1) > mBundle.getInt(MultiPlayerQuizActivity.KEY_SCORE_USER_2)) {
            setWinnerIsUser1();
        }
        else if(mBundle.getInt(MultiPlayerQuizActivity.KEY_SCORE_USER_2) > mBundle.getInt(MultiPlayerQuizActivity.KEY_SCORE_USER_1)) {
            setWinnerIsUser2();
        }
        else {
            if(mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_USER_1) > mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_USER_2)) {
                setWinnerIsUser1();
            } else if(mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_USER_2) > mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_USER_1)) {
                setWinnerIsUser2();
            } else
                if(mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_BAD_USER_1) < mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_BAD_USER_2)) {
                    setWinnerIsUser1();
                } else if(mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_BAD_USER_2) < mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_BAD_USER_1)) {
                    setWinnerIsUser2();
                } else {
                    Log.i("BiigLogScore", "Égalité parfaite");
                    //TODO Égalité parfaite
                }

        }


    }


    private void setWinnerIsUser1() {
        mTextViewPlayer_1_1.setText(USER_1);
        mTextViewPlayer_2_1.setText(USER_1);
        mTextViewPlayer_1_2.setText(USER_2);
        mTextViewPlayer_2_2.setText(USER_2);

        String score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_USER_1);
        mTextViewTrue_1_1.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_OUT_USER_1);
        mTextViewTrueOut_1_1.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_BAD_USER_1);
        mTextViewBad_1_1.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_USER_2);
        mTextViewTrue_1_2.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_OUT_USER_2);
        mTextViewTrueOut_1_2.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_BAD_USER_2);
        mTextViewBad_1_2.setText(score);

        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_USER_1);
        mTextViewTrue_2_1.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_OUT_USER_1);
        mTextViewTrueOut_2_1.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_BAD_USER_1);
        mTextViewBad_2_1.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_USER_2);
        mTextViewTrue_2_2.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_OUT_USER_2);
        mTextViewTrueOut_2_2.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_BAD_USER_2);
        mTextViewBad_2_2.setText(score);

    }

    private void setWinnerIsUser2() {
        mTextViewPlayer_1_1.setText(USER_2);
        mTextViewPlayer_2_1.setText(USER_2);
        mTextViewPlayer_1_2.setText(USER_1);
        mTextViewPlayer_2_2.setText(USER_1);


        String score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_USER_2);
        mTextViewTrue_1_1.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_OUT_USER_2);
        mTextViewTrueOut_1_1.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_BAD_USER_2);
        mTextViewBad_1_1.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_USER_1);
        mTextViewTrue_1_2.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_OUT_USER_1);
        mTextViewTrueOut_1_2.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_BAD_USER_1);
        mTextViewBad_1_2.setText(score);

        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_USER_2);
        mTextViewTrue_2_1.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_OUT_USER_2);
        mTextViewTrueOut_2_1.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_BAD_USER_2);
        mTextViewBad_2_1.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_USER_1);
        mTextViewTrue_2_2.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_TRUE_OUT_USER_1);
        mTextViewTrueOut_2_2.setText(score);
        score = ""+ mBundle.getInt(MultiPlayerQuizActivity.KEY_NB_ANSWERED_BAD_USER_1);
        mTextViewBad_2_2.setText(score);
    }

    private void setEquality() {

    }

}
