package com.biig.zzquiz.quiz.core;

/**
 * Created by Loïc Proust on 27/04/2016.
 */
public class Question {

    public static final String JSON_KEY_TEXT = "question_text";
    public static final String JSON_KEY_TRUE_ANSW = "true_answ";
    public static final String JSON_KEY_BAD_ANSW = "bad_answ";

    public String mQuestion;

    public String[] mAnsws = new String[3];

//    TODO : Définir le temps de la progressbar en fonction du temps pour chaque question
//    public int mSecondBeforeOut;


    public Question() {

    }

//    public Question(String questionText, String trueAnsw, String[] badAnsws) {
//        mQuestion = questionText;
//        mTrueAnsw = trueAnsw;
//        mAnsws = badAnsws;
//    }


}
