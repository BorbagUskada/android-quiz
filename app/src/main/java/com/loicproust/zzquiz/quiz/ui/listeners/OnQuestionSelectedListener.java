package com.loicproust.zzquiz.quiz.ui.listeners;

import com.loicproust.zzquiz.quiz.core.beans.Question;

/**
 * Created by proust on 13/04/2017.
 */

public interface OnQuestionSelectedListener {

    void onQuestionSelected(Question question, int[] answersPlacement, int truePos);
}
