package com.example.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        Button buttonLogout = findViewById(R.id.buttonLogout);
        textViewWelcome.setText("Добро пожаловать в профиль преподавателя!");
        buttonLogout.setOnClickListener(view -> {
            finish();
        });
    }

    public void ClickOnCreateTest(View view) {
        Intent intent = new Intent(this, CreateTestActivity.class);
        startActivity(intent);
    }
}