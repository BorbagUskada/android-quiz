package com.loicproust.zzquiz.quiz.ui.activities.singleplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.loicproust.zzquiz.R;
import com.loicproust.zzquiz.quiz.core.beans.score.SinglePlayerScore;
import com.loicproust.zzquiz.quiz.core.beans.score.UserScore;
import com.loicproust.zzquiz.quiz.ui.activities.HomeActivity;
import com.loicproust.zzquiz.quiz.ui.fragments.ScorePanelFragment;

/**
 *
 * Created by proust on 16/03/2017.
 */

public class SinglePlayerScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_singleplayer);

        SinglePlayerScore userScore = getIntent().getParcelableExtra(UserScore.KEY_SCORE);

        ScorePanelFragment scorePanelFragment = (ScorePanelFragment) getSupportFragmentManager().findFragmentById(R.id.f_single_score_panel);
        scorePanelFragment.updateScore(userScore);

        Button btnMenu = (Button)findViewById(R.id.btn_single_score_back_to_menu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SinglePlayerScoreActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


    }
}
