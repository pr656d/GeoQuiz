package com.practice.premp.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.practice.premp.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.practice.premp.geoquiz.answer_shown";
    private static final String ANSWER_SHOWN = "shown_answer";
    private static final String ANSWER_IS = "answer_is";

    private boolean isAnswerShown = false;

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private TextView mShowAnswerButton;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // Reference to ids.
        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowAnswerButton = findViewById(R.id.show_answer_button);

        if (savedInstanceState != null) {
            isAnswerShown = savedInstanceState.getBoolean(ANSWER_SHOWN);
            setAnswerShownResult(isAnswerShown);
            mAnswerTextView.setText(savedInstanceState.getString(ANSWER_IS));
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        // Show Answer button listener.
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue)
                    mAnswerTextView.setText(R.string.true_button);
                else
                    mAnswerTextView.setText(R.string.false_button);

                setAnswerShownResult(true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int centerX = mShowAnswerButton.getWidth() / 2;
                    int centerY = mShowAnswerButton.getHeight() / 2;
                    float startRadius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswerButton, centerX, centerY, startRadius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    } // onCreate end.

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ANSWER_SHOWN, isAnswerShown);
        outState.putString(ANSWER_IS, (String) mAnswerTextView.getText());
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        this.isAnswerShown = isAnswerShown;
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
