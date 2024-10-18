package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mytest.adapter.AnswerAdapter;
import com.example.mytest.adapter.QuestionAdapter;
import com.example.mytest.auth.Authentication;
import com.example.mytest.model.Question;
import com.example.mytest.model.Test;
import com.example.mytest.repository.QuestionRepository;
import com.example.mytest.repository.TestRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CreateTestActivity extends AppCompatActivity {

    QuestionRepository questionRepository;
    TestRepository testRepository;
    QuestionAdapter questionAdapter;
    List<Question> questionList = new ArrayList<>();
    EditText editText;
    Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        Intent intent = getIntent();
        test = (Test)intent.getSerializableExtra("TEST");

        questionRepository = new QuestionRepository(FirebaseFirestore.getInstance());
        testRepository = new TestRepository(FirebaseFirestore.getInstance());
        RecyclerView recyclerView = findViewById(R.id.rvQuestionsList);
        editText = findViewById(R.id.etTestTitle);

        editText.setText(test.getTitle());

        questionRepository.getAllQuestionByTestId(test.getId())
                .thenAccept(list -> {
                    questionList.addAll(list);
                    questionAdapter = new QuestionAdapter(questionList,this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(questionAdapter);
                    questionAdapter.notifyItemInserted(list.size());
                });

        findViewById(R.id.btnCreateQuestion).setOnClickListener(view -> {
            Question question = new Question();
            question.setTestId(test.getId());

            Intent intentQuestion = new Intent(CreateTestActivity.this, QuestionSettingActivity.class);
            intentQuestion.putExtra("QUESTION", questionRepository.addQuestion(question));
            startActivity(intentQuestion);
            finish();
        });

        findViewById(R.id.btnCreateTest).setOnClickListener(view -> {
            editText = findViewById(R.id.etTestTitle);
            test.setTitle(editText.getText().toString());
            testRepository.updateTest(test);

            Intent intentProfile = new Intent(this, TeacherProfileActivity.class);
            startActivity(intentProfile);
            finish();
        });
    }
    public void onBackButtonClick(View view) {
        Intent intent = new Intent(CreateTestActivity.this, CreateTestActivity.class);
        startActivity(intent);
        finish();
    }
}