package com.practice.premp.geoquiz;

public class Question {
    private int mTextResourceId;
    private boolean mAnswerTrue;

    public Question(int textResourceId, boolean answerTrue) {
        mTextResourceId = textResourceId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResourceId() {
        return mTextResourceId;
    }

    public void setTextResourceId(int textResourceId) {
        mTextResourceId = textResourceId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
