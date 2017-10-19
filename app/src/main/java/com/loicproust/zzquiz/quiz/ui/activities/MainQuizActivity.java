package com.loicproust.zzquiz.quiz.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loicproust.zzquiz.R;
import com.loicproust.zzquiz.quiz.core.gamecore.GameCore;
import com.loicproust.zzquiz.quiz.ui.listeners.OnAnswerGivenListener;
import com.loicproust.zzquiz.quiz.ui.listeners.OnBlinkFinishedListener;
import com.loicproust.zzquiz.quiz.ui.listeners.OnButtonSettedListener;

/**
 * Created by proust on 13/03/2017.
 */

public abstract class MainQuizActivity extends AppCompatActivity implements View.OnClickListener,
        OnAnswerGivenListener,
        OnButtonSettedListener,
        OnBlinkFinishedListener {

    protected GameCore mGameCore;

    protected int[] mAnswersPlacement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setNextQuestion() {
        mAnswersPlacement = mGameCore.setNextQuestion();

//        if(null == mAnswersPlacement)
//            return;
            //TODO Stop game
    }


    protected abstract void verifyAnswer(Button btn);


    /**
     * Make the button passed by parameters blink during 2 seconds and then be green (true)
     * @param btnTrueAnsw The button to blink
     * @param handler The handler to handle work and repetition
     * @return The runnable that do the job
     */
    protected Runnable getTrueMissedBlink(final Button btnTrueAnsw, final Handler handler) {
        return new Runnable() {
            int i;
            @Override
            public void run() {
                if(i++ % 2 == 0 && i<7)
                    btnTrueAnsw.setBackgroundResource(R.drawable.btn_quiz_false);
                else if(i < 7)
                    btnTrueAnsw.setBackgroundResource(R.drawable.btn_quiz_true);

                if(i < 10)
                    handler.postDelayed(this, 300);
                else {
                    handler.removeCallbacks(this);
                    setNextQuestion();
                }

            }
        };
    }

    @Override
    public void onClick(View v) {
        Log.d("BoUsLog", "Listener from activity");
//        switch (v.getId()) {
//            case R.id.btn_usr1_answ1:
//                verifyAnswer(mBtnUsr1Answ1, CLICK_KEY_USR1);
//                break;
//            case R.id.btn_usr1_answ2:
//                verifyAnswer(mBtnUsr1Answ2, CLICK_KEY_USR1);
//                break;
//            case R.id.btn_usr1_answ3:
//                verifyAnswer(mBtnUsr1Answ3, CLICK_KEY_USR1);
//                break;
//            case R.id.btn_usr1_answ4:
//                verifyAnswer(mBtnUsr1Answ4, CLICK_KEY_USR1);
//                break;
//            case R.id.btn_usr2_answ1:
//                verifyAnswer(mBtnUsr2Answ1, CLICK_KEY_USR2);
//                break;
//            case R.id.btn_usr2_answ2:
//                verifyAnswer(mBtnUsr2Answ2, CLICK_KEY_USR2);
//                break;
//            case R.id.btn_usr2_answ3:
//                verifyAnswer(mBtnUsr2Answ3, CLICK_KEY_USR2);
//                break;
//            case R.id.btn_usr2_answ4:
//                verifyAnswer(mBtnUsr2Answ4, CLICK_KEY_USR2);
//                break;
//        }
        mGameCore.onUnknownAnswerGiven(v.getId());

    }

    @Override
    public void onBlinkFinished() {
        setNextQuestion();
    }
}
