package com.loicproust.zzquiz.quiz.core.beans;

import com.loicproust.zzquiz.quiz.core.beans.Question;

import java.util.ArrayList;

/**
 * Created by Loïc Proust on 27/04/2016.
 */
public class QuestionsList {

    private ArrayList<Question> mQuestionsToAsk;
    private ArrayList<Question> mQuestionsAsked;

    public QuestionsList() {
        mQuestionsToAsk = new ArrayList<>();
        mQuestionsAsked = new ArrayList<>();
    }

    /* Useless am 27/04/2016 */
//    public QuestionsList(ArrayList<Question> mQuestions) {
//    }

    public void addQuestion(Question questionToAdd) {
        mQuestionsToAsk.add(questionToAdd);
    }
    public void addQuestions(ArrayList<Question> questionList) {
        mQuestionsToAsk.addAll(questionList);
    }

    /**
     * Return the next question to be asked and remove it from the remaining questions
     * @return The next question to ask
     */
    public Question nextQuestion() {
        if(mQuestionsToAsk.size() != 0) {
            mQuestionsAsked.add(mQuestionsToAsk.get(mQuestionsToAsk.size()-1));
            Question tmp = mQuestionsToAsk.get(mQuestionsToAsk.size()-1);
            mQuestionsToAsk.remove(mQuestionsToAsk.size()-1);
            return tmp;
        }
        return null;
    }
}
