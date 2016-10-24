package com.biig.zzquiz.quiz.core;

import android.content.Context;

import com.biig.zzquiz.quiz.activity.MultiPlayerQuizActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Loïc Proust on 27/04/2016.
 */
public class JsonController {

    private Context mContext;

    private static final Object lock = new Object();

    private static JsonController mInstance;
    public static JsonController getInstance(Context context) {
        synchronized (lock) {
            if(mInstance == null)
                mInstance = new JsonController(context);

            return mInstance;
        }
    }
    private JsonController(Context context) {
        mContext = context;
    }

    private String loadJsonFromAsset() {
        String json;
        try {
            InputStream inputStream = mContext.getAssets().open(MultiPlayerQuizActivity.QUESTIONS_JSON_FILENAME);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Convert a JSONObject into a valid Question Object
     * @param jsonObject The JSONObject to parse
     * @return question A Question object
     * @throws JSONException If the JSONObject is not correct or does not correspond to a Question
     */
    private Question getQuestionFromJson(JSONObject jsonObject) throws JSONException {
        Question question = new Question();
        question.mQuestion = jsonObject.getString(Question.JSON_KEY_TEXT);
//        question.mTrueAnsw = jsonObject.getString(Question.JSON_KEY_TRUE_ANSW);

        String[] answs = new String[4];
        answs[0] = jsonObject.getString(Question.JSON_KEY_TRUE_ANSW);
        answs[1] = jsonObject.getString(Question.JSON_KEY_BAD_ANSW);
        answs[2] = jsonObject.getString(Question.JSON_KEY_BAD_ANSW + "_2");
        answs[3] = jsonObject.getString(Question.JSON_KEY_BAD_ANSW + "_3");
        question.mAnsws = answs;

        return question;
    }

    public QuestionsList loadQuestionsListFromAssets() {
        QuestionsList questionsList = new QuestionsList();
        try {
            JSONArray jsonArray = new JSONArray(loadJsonFromAsset());
            JSONObject jsonObj;
            ArrayList<Question> questions = new ArrayList<>();//Useful to sort the questions list without to make QuestionsList.list public
            for(int i = 0; i < jsonArray.length(); i++) {
                jsonObj = jsonArray.getJSONObject(i);
                questions.add(getQuestionFromJson(jsonObj));
            }
            Collections.shuffle(questions);
            questionsList.addQuestions(questions);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        return questionsList;
    }
}
