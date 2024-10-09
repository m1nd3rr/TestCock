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

    public void ClickOnOneChoise(View view) {
        Intent intent = new Intent(QuestionSettingActivity.this, SingleChoiseActivity.class);
        intent.putExtra("QUESTION", question);
        intent.putExtra("TYPE", "single-choice");
        startActivity(intent);
    }
}