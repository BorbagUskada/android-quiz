package com.biig.zzquiz.quiz.dialog;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.biig.zzquiz.R;

/**
 * Created by Loïc Proust on 29/04/2016.
 */
public class GetReadyDialog extends AlertDialog {

    private static final int KEY_BOTH = 3;
    private static final int KEY_USR_1 = 1;
    private static final int KEY_USR_2 = 2;

    private int mToggleReady;

    private Button mBtnUsr1;
    private Button mBtnUsr2;

    private TextView mTextViewMainUsr1;
    private TextView mTextViewMainUsr2;

    private TextView mTextViewCountdownUsr1;
    private TextView mTextViewCountdownUsr2;

    private OnBothUsersReadyListener mListener;

    public GetReadyDialog(Context context, OnBothUsersReadyListener listener) {
        super(context);

        View v = View.inflate(context, R.layout.dialog_quiz_ready, null);
        setView(v);
        setCancelable(false);

        mListener = listener;

        mBtnUsr1 = (Button)v.findViewById(R.id.btn_ready_usr1);
        mBtnUsr2 = (Button)v.findViewById(R.id.btn_ready_usr2);
        mTextViewMainUsr1 = (TextView)v.findViewById(R.id.tv_ready_usr1);
        mTextViewMainUsr2 = (TextView)v.findViewById(R.id.tv_ready_usr2);

        mTextViewCountdownUsr1 = (TextView)v.findViewById(R.id.tv_ready_countdown_usr1);
        mTextViewCountdownUsr2 = (TextView)v.findViewById(R.id.tv_ready_countdown_usr2);

        mBtnUsr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnUsr1.setBackgroundResource(R.drawable.btn_quiz_true);
                modifyToggle(KEY_USR_1);
            }
        });
        mBtnUsr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnUsr2.setBackgroundResource(R.drawable.btn_quiz_true);
                modifyToggle(KEY_USR_2);
            }
        });

    }

    private void modifyToggle(int i) {
        if(mToggleReady == KEY_USR_1 && i == KEY_USR_1)
        {
            mToggleReady -= KEY_USR_1;
            mBtnUsr1.setBackgroundResource(R.drawable.btn_quiz_false);
        }
        else if (mToggleReady == KEY_USR_2 && i == KEY_USR_2) {
            mToggleReady -= KEY_USR_2;
            mBtnUsr2.setBackgroundResource(R.drawable.btn_quiz_false);
        } else
            mToggleReady += i;

        if(mToggleReady == KEY_BOTH) {
            mTextViewMainUsr1.setText("");
            mTextViewMainUsr2.setText("");
            mBtnUsr1.setVisibility(View.INVISIBLE);
            mBtnUsr2.setVisibility(View.INVISIBLE);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                int i = 4;
                String s;
                @Override
                public void run() {
                    if(i-- > 0) {
                        if(i != 0)
                            s = ""+ i;
                        else {
                            s = "Jouez !";
                            mTextViewCountdownUsr1.setTextSize(100);
                            mTextViewCountdownUsr2.setTextSize(100);
                        }
                        mTextViewCountdownUsr1.setText(s);
                        mTextViewCountdownUsr2.setText(s);
                        handler.postDelayed(this, 1000);
                    } else {
                        handler.removeCallbacks(this);
                        mListener.onBothPlayersReady();
                        dismiss();
                    }
                }
            }, 1);
        }

    }

    public interface OnBothUsersReadyListener {
        void onBothPlayersReady();
    }
}
