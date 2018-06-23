package com.practice.premp.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    // CONSTANTS
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "mCurrentIndex";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    // index to point for a question from array.
    private int mCurrentIndex = 0;

    // Array to store questions and answers.
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_india, true),
            new Question(R.string.question_ocean, true),
            new Question(R.string.question_android, false),
            new Question(R.string.question_americans, true),
            new Question(R.string.question_ahmedabad, false),
            new Question(R.string.question_asia, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, ".onCreate() called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            Log.d(TAG, ".savedInstanceState is not empty.");
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
        }

        // References to ids.
        mQuestionTextView = findViewById(R.id.question_text_view);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.previous_button);
        mCheatButton = findViewById(R.id.cheat_button);

        updateQuestion();

        // True button listener.
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                disableButtons(true);
            }
        });

        // False button listener.
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                disableButtons(true);
            }
        });

        // Next button listener.
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons(false);
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // Previous button listener.
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons(false);
                if (mCurrentIndex == 0) {
                    mCurrentIndex = mQuestionBank.length;
                }
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // Cheat button listener.
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start cheat activity.
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivity(intent);
            }
        });

    } // onCreate end.

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, ".onSaveInstanceState() called.");
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentIndex);
    } // onSaveInstanceState end.

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, ".onStart() called.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, ".onResume() called.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, ".onPause() called.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, ".onStop() called.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, ".onDestroy() called.");
    }

    // Methods.

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    } // updateQuestion end.

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;

        if (answerIsTrue) {
            mTrueButton.setBackgroundColor(getResources().getColor(R.color.color_green));
        } else {
            mFalseButton.setBackgroundColor(getResources().getColor(R.color.color_green));
        }

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct;
        } else {
            messageResId = R.string.incorrect;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    } // checkAnswer end.

    private void disableButtons(boolean isClickable) {
        if (isClickable) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        } else {
            mTrueButton.setEnabled(true);
            mTrueButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            mFalseButton.setEnabled(true);
            mFalseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}
