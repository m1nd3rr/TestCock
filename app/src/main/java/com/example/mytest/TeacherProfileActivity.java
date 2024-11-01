package com.example.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytest.adapter.TestAdapter;
import com.example.mytest.auth.Authentication;
import com.example.mytest.auth.Select;
import com.example.mytest.model.Test;
import com.example.mytest.repository.TestRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TeacherProfileActivity extends AppCompatActivity {

    TestRepository testRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        Button buttonLogout = findViewById(R.id.buttonLogout);
        RecyclerView recyclerView = findViewById(R.id.rvTestList);

        testRepository = new TestRepository(FirebaseFirestore.getInstance());
        List<Test> testList = new ArrayList<>();

        testRepository.getAllTestByTeacherId(Authentication.getTeacher().getId())
                .thenAccept(list -> {
                    testList.addAll(list);
                    TestAdapter testAdapter = new TestAdapter(testList, this);
                    recyclerView.setAdapter(testAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                });

        textViewWelcome.setText("Добро пожаловать в профиль преподавателя!");
        buttonLogout.setOnClickListener(view -> {
            finish();
        });
    }

    public void ClickOnCreateTest(View view) {
        Test test = new Test();
        test.setTeacherId(Authentication.getTeacher().getId());

        Intent intent = new Intent(this, CreateTestActivity.class);
        Select.setTest(testRepository.addTest(test));
        startActivity(intent);
        finish();
    }
}