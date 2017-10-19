package com.loicproust.zzquiz.quiz.ui.activities.singleplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.loicproust.zzquiz.MenuActivity;
import com.loicproust.zzquiz.R;
import com.loicproust.zzquiz.quiz.core.beans.score.ScoreConditions;
import com.loicproust.zzquiz.quiz.core.beans.score.SinglePlayerScore;
import com.loicproust.zzquiz.quiz.core.beans.score.UserScore;
import com.loicproust.zzquiz.quiz.core.gamecore.SinglePlayerGameCore;
import com.loicproust.zzquiz.quiz.ui.activities.MainQuizActivity;
import com.loicproust.zzquiz.quiz.ui.fragments.GamePanelFragment;
import com.loicproust.zzquiz.quiz.ui.fragments.ScorePanelFragment;
import com.loicproust.zzquiz.quiz.ui.listeners.OnScoreChangedListener;

/**
 * Created by proust on 28/02/2017.
 */

public class SinglePlayerQuizActivity extends MainQuizActivity implements OnScoreChangedListener {

    private TextView mTextViewQuestionText;

    private GamePanelFragment mGamePanelFragment;
    private ScorePanelFragment mScorePanelFragment;

    private Button mClickedButton;
    private Button mTrueButton;

    private SinglePlayerScore mScore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_singleplayer);

        int nbQuestions = getIntent().getExtras().getInt(MenuActivity.KEY_NB_QUESTIONS);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mGamePanelFragment = (GamePanelFragment) getSupportFragmentManager().findFragmentById(R.id.f_single_game_panel);
        mGamePanelFragment.setOnAnswerGivenListener(this);
        mGamePanelFragment.setOnButtonSettedListener(this);
        mGamePanelFragment.setOnBlinkFinishedListener(this);

        mScorePanelFragment = (ScorePanelFragment) getSupportFragmentManager().findFragmentById(R.id.f_score_panel_fragment);

        mGameCore = new SinglePlayerGameCore(this, nbQuestions);

        mScore = new SinglePlayerScore();


        setNextQuestion();
    }


    @Override
    protected void setNextQuestion() {
        super.setNextQuestion();

        // TODO Stop quiz
        if(null == mAnswersPlacement) {
            finishGame();
            return;
        }

        if(null != mClickedButton)
            mClickedButton.setBackgroundResource(R.drawable.btn_quiz);

        mGamePanelFragment.onQuestionSelected(mGameCore.getCurrentQuestion(), mAnswersPlacement, mGameCore.getTruePos());

    }


    @Override
    protected void verifyAnswer(Button btn) {
        boolean isTrueAnswser = mGameCore.verifyAnswer((String)btn.getText());

        if(isTrueAnswser) {

            mGamePanelFragment.trueAnswerGiven();
            mScore.manageScore(new ScoreConditions(true, true));

        }  else {

            mGamePanelFragment.wrongAnswerGiven();
            // TODO Check if is in time
            mScore.manageScore(new ScoreConditions(false, true));
        }

        mScorePanelFragment.updateScore(mScore);
    }


    /* // Block On Answer Given Listener methods implementation // */
    @Override
    public void onUnknownAnswerGiven(int id) {
        mClickedButton = (Button)findViewById(id);
        verifyAnswer(mClickedButton);
    }

    public void finishGame() {
        Log.d("BoUsLog", "Game is finished");

        Intent intent = new Intent(this, SinglePlayerScoreActivity.class);
        intent.putExtra(UserScore.KEY_SCORE, mScore);
        startActivity(intent);
        finish();
    }

    @Override
    public void onButtonSetted(int trueButtonId) {
        mTrueButton = (Button)findViewById(trueButtonId);
    }

    @Override
    public void onScoreChanged() {
        Log.d("BoUsLogScore", "Score has changed");
    }
    /* // End Block // */
}
