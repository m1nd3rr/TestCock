package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mytest.adapter.AnswerAdapter;
import com.example.mytest.adapter.QuestionAdapter;
import com.example.mytest.model.Question;
import com.example.mytest.repository.QuestionRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CreateTestActivity extends AppCompatActivity {

    QuestionRepository questionRepository;
    QuestionAdapter questionAdapter;
    List<Question> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        questionRepository = new QuestionRepository(FirebaseFirestore.getInstance());
        RecyclerView recyclerView = findViewById(R.id.rvQuestionsList);
        questionRepository.getAllQuestion()
                .thenAccept(list -> {
                    questionList.addAll(list);
                    questionAdapter = new QuestionAdapter(questionList,this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(questionAdapter);
                    questionAdapter.notifyItemInserted(list.size());
                });

        findViewById(R.id.btnCreateQuestion).setOnClickListener(view -> {
            Question question = new Question();

            Intent intent = new Intent(CreateTestActivity.this, QuestionSettingActivity.class);
            intent.putExtra("QUESTION", questionRepository.addQuestion(question));
            startActivity(intent);
            finish();
        });
    }


}