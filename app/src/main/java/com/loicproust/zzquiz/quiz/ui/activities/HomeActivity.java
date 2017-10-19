package com.loicproust.zzquiz.quiz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.loicproust.zzquiz.R;
import com.loicproust.zzquiz.quiz.dialog.ChooseNbQuestionsDialog;
import com.loicproust.zzquiz.quiz.ui.activities.multiplayer.MultiPlayerQuizOldActivity;
import com.loicproust.zzquiz.quiz.ui.activities.singleplayer.SinglePlayerQuizActivity;

/**
 * Created by proust on 06/10/2017.
 */

public class HomeActivity extends AppCompatActivity implements ChooseNbQuestionsDialog.NumberQuestionsChoosedListener {

    public static final String KEY_NB_QUESTIONS = "nb_questions";

    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);


        Button btnMulti = (Button) findViewById(R.id.btn_main_quiz_multi);
        Button btnSolo  = (Button) findViewById(R.id.btn_main_quiz_solo);

        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(HomeActivity.this, MultiPlayerQuizOldActivity.class);
                ChooseNbQuestionsDialog dialog = new ChooseNbQuestionsDialog(HomeActivity.this, HomeActivity.this);
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        btnSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(HomeActivity.this, SinglePlayerQuizActivity.class);
                ChooseNbQuestionsDialog dialog = new ChooseNbQuestionsDialog(HomeActivity.this, HomeActivity.this);
                dialog.setCancelable(false);
                dialog.show();
            }
        });
    }

    @Override
    public void nbQuestionsChoosed(int nb) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_NB_QUESTIONS, nb);
        mIntent.putExtras(bundle);

        startActivity(mIntent);
    }
}
