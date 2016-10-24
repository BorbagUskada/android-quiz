package com.loicproust.zzquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.loicproust.zzquiz.quiz.dialog.ChooseNbQuestionsDialog;
import com.loicproust.zzquiz.quiz.activity.multiplayer.MultiPlayerQuizActivity;

/** Activity to handle menu
 * Created by Loïc Proust on 26/04/2016.
 */
public class MenuActivity extends AppCompatActivity implements ChooseNbQuestionsDialog.NumberQuestionsChoosedListener{

    public static final String KEY_NB_QUESTIONS = "nb_questions";

    private Intent mIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void clickQuiz(View v) {
        mIntent = new Intent(this, MultiPlayerQuizActivity.class);
        ChooseNbQuestionsDialog dialog = new ChooseNbQuestionsDialog(this, this);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void nbQuestionsChoosed(int nb) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_NB_QUESTIONS, nb);
        mIntent.putExtras(bundle);

        startActivity(mIntent);
    }

}
