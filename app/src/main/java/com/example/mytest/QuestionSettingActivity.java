package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mytest.model.Question;

public class QuestionSettingActivity extends AppCompatActivity {

    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_setting);

        Intent intent = getIntent();
        question = (Question)intent.getSerializableExtra("QUESTION");
    }

    public void onBack(View view) {
        Intent intent = new Intent(QuestionSettingActivity.this, CreateTestActivity.class);
        startActivity(intent);
        finish();
    }


    public void ClickOnOneChoise(View view) {
        Intent intent = new Intent(QuestionSettingActivity.this, QuestionActivity.class);
        question.setType("single-choice");
        intent.putExtra("QUESTION", question);
        startActivity(intent);
        finish();
    }

    public void ClickOnMultiChoise(View view) {
        Intent intent = new Intent(QuestionSettingActivity.this, QuestionActivity.class);
        question.setType("multi-choice");
        intent.putExtra("QUESTION", question);
        startActivity(intent);
        finish();
    }

    public void ClickOnSort(View view){
        Intent intent = new Intent(QuestionSettingActivity.this,QuestionActivity.class);
        question.setType("sort");
        intent.putExtra("QUESTION",question);
        startActivity(intent);
        finish();
    }
    public  void ClickOnText(View view){
        Intent intent = new Intent(QuestionSettingActivity.this,QuestionActivity.class);
        question.setType("text");
        intent.putExtra("QUESTION",question);
        startActivity(intent);
        finish();
    }
    public  void ClickOnTrueFalse(View view){
        Intent intent = new Intent(QuestionSettingActivity.this,QuestionActivity.class);
        question.setType("true-false");
        intent.putExtra("QUESTION",question);
        startActivity(intent);
        finish();
    }
    public  void ClickOnConnect(View view){
        Intent intent = new Intent(QuestionSettingActivity.this,QuestionActivity.class);
        question.setType("connect");
        intent.putExtra("QUESTION",question);
        startActivity(intent);
        finish();
    }
}