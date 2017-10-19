package com.loicproust.zzquiz.quiz.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.loicproust.zzquiz.R;
import com.loicproust.zzquiz.quiz.core.beans.Question;
import com.loicproust.zzquiz.quiz.core.beans.score.ScoreConditions;
import com.loicproust.zzquiz.quiz.ui.listeners.OnAnswerGivenListener;
import com.loicproust.zzquiz.quiz.ui.listeners.OnBlinkFinishedListener;
import com.loicproust.zzquiz.quiz.ui.listeners.OnButtonSettedListener;
import com.loicproust.zzquiz.quiz.ui.listeners.OnQuestionSelectedListener;

import java.util.ArrayList;

/**
 * Game Panel that handle user interactions and manage buttons
 *
 * Created by proust on 27/02/2017.
 */

public class GamePanelFragment extends Fragment implements View.OnClickListener, OnQuestionSelectedListener {

    private Question mQuestion;

    private TextView mTextViewQuestionText;
    private int mTruePos;

    private Button mBtnUsrAnsw1;
    private Button mBtnUsrAnsw2;
    private Button mBtnUsrAnsw3;
    private Button mBtnUsrAnsw4;

    private Button mClickedButton;
    private ArrayList<Button> mButtons;

    private OnAnswerGivenListener mOnAnswerGivenListener;
    private OnButtonSettedListener mOnButtonSettedListener;
    private OnBlinkFinishedListener mOnBlinkFinishedListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_game_panel, container);

        mTextViewQuestionText = (TextView) v.findViewById(R.id.tv_game_panel_question_usr);
//        mTextViewQuestionText.setText("Text test");

        mBtnUsrAnsw1 = (Button) v.findViewById(R.id.btn_usr1_answ1);
        mBtnUsrAnsw2 = (Button) v.findViewById(R.id.btn_usr1_answ2);
        mBtnUsrAnsw3 = (Button) v.findViewById(R.id.btn_usr1_answ3);
        mBtnUsrAnsw4 = (Button) v.findViewById(R.id.btn_usr1_answ4);

        mBtnUsrAnsw1.setOnClickListener(this);
        mBtnUsrAnsw2.setOnClickListener(this);
        mBtnUsrAnsw3.setOnClickListener(this);
        mBtnUsrAnsw4.setOnClickListener(this);

        mButtons = new ArrayList<>();
        mButtons.add(mBtnUsrAnsw1);
        mButtons.add(mBtnUsrAnsw2);
        mButtons.add(mBtnUsrAnsw3);
        mButtons.add(mBtnUsrAnsw4);

        return v;
    }

    public void setOnAnswerGivenListener(OnAnswerGivenListener listener) {
        mOnAnswerGivenListener = listener;
    }
    public void setOnButtonSettedListener(OnButtonSettedListener listener) {
        mOnButtonSettedListener = listener;
    }

    public void setOnBlinkFinishedListener(OnBlinkFinishedListener listener) {
        mOnBlinkFinishedListener = listener;
    }

    @Override
    public void onQuestionSelected(Question question, int[] answersPlacement, int truePos) {


        mQuestion = question;
        mTruePos = truePos;

        mTextViewQuestionText.setText(mQuestion.mQuestion);
        mBtnUsrAnsw1.setText(mQuestion.mAnsws[answersPlacement[0]]);
        mBtnUsrAnsw2.setText(mQuestion.mAnsws[answersPlacement[1]]);
        mBtnUsrAnsw3.setText(mQuestion.mAnsws[answersPlacement[2]]);
        mBtnUsrAnsw4.setText(mQuestion.mAnsws[answersPlacement[3]]);

        mBtnUsrAnsw1.setBackgroundResource(R.drawable.btn_quiz);
        mBtnUsrAnsw2.setBackgroundResource(R.drawable.btn_quiz);
        mBtnUsrAnsw3.setBackgroundResource(R.drawable.btn_quiz);
        mBtnUsrAnsw4.setBackgroundResource(R.drawable.btn_quiz);

        /* Not really clean, need to find a cleaner way */
        switch (truePos) {
            case 0 :
                mOnButtonSettedListener.onButtonSetted(mBtnUsrAnsw1.getId());
                break;
            case 1 :
                mOnButtonSettedListener.onButtonSetted(mBtnUsrAnsw2.getId());
                break;
            case 2 :
                mOnButtonSettedListener.onButtonSetted(mBtnUsrAnsw3.getId());
                break;
            case 3 :
                mOnButtonSettedListener.onButtonSetted(mBtnUsrAnsw4.getId());
                break;
        }

    }


    @Override
    public void onClick(View v) {
        Log.d("BoUsLog", "Click View");
        Log.d("BoUsLog", "");

        mClickedButton = (Button) v;
        mOnAnswerGivenListener.onUnknownAnswerGiven(mClickedButton.getId());
    }

    private Runnable getWrongBlink(final Button btn, final Handler handler) {
        return new Runnable() {
            int i;
            @Override
            public void run() {
                if(i++ % 2 == 0 && i<7)
                    btn.setBackgroundResource(R.drawable.btn_quiz_false);
                else if(i < 7)
                    btn.setBackgroundResource(R.drawable.btn_quiz_true);

                if(i < 10)
                    handler.postDelayed(this, 300);
                else {
                    handler.removeCallbacks(this);
                    btn.setBackgroundResource(R.drawable.btn_quiz);
                    mOnBlinkFinishedListener.onBlinkFinished();
                }

            }
        };
    }

    public void trueAnswerGiven() {
        mButtons.get(mTruePos).setBackgroundResource(R.drawable.btn_quiz_true);

        final Handler handler = new Handler();
        int time = 500; // in milliseconds
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                i++;
                if(i < 6)
                    handler.postDelayed(this, 300);
                else {
                    handler.removeCallbacks(this);
                    mOnBlinkFinishedListener.onBlinkFinished();
                }
            }
        },time);
//        mButtons.get(mTruePos).setBackgroundResource(R.drawable.btn_quiz_true);
    }

    public void wrongAnswerGiven() {

        mClickedButton.setBackgroundResource(R.drawable.btn_quiz_false);
        final Handler handler = new Handler();
        final Runnable blink = getWrongBlink(mButtons.get(mTruePos), handler);
        handler.postDelayed(blink, 250);
    }

}
