package com.loicproust.zzquiz.quiz.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loicproust.zzquiz.R;

/**
 * Created by Loïc Proust on 28/04/2016.
 */
public class ChooseNbQuestionsDialog extends AlertDialog {

    public int mNbQuestions = 0;

    public ChooseNbQuestionsDialog(final Context context, final NumberQuestionsChoosedListener listener) {
        super(context);

        View dialogView = View.inflate(context, R.layout.dialog_choose_nb_questions, null);
        setView(dialogView);
        setCancelable(false);



        Button btnClose = (Button)dialogView.findViewById(R.id.btn_dialog_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btnPlay = (Button)dialogView.findViewById(R.id.btn_dialog_quiz_play);
        final EditText etNbQuestions = (EditText)dialogView.findViewById(R.id.et_nb_questions);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNbQuestions.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Selectionner le nombre de questions à jouer", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(ChooseNbQuestionsDialog.this.getCurrentFocus() != null) {
                    InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(ChooseNbQuestionsDialog.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                mNbQuestions = Integer.parseInt(etNbQuestions.getText().toString());
                listener.nbQuestionsChoosed(mNbQuestions);
                dismiss();
            }
        });
    }

    public interface NumberQuestionsChoosedListener {
        void nbQuestionsChoosed(int nb);
    }
}
