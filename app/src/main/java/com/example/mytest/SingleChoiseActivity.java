package com.example.mytest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.mytest.adapter.AnswerAdapter;
import com.example.mytest.model.Answer;
import com.example.mytest.model.Question;
import com.example.mytest.repository.AnswerRepository;
import com.example.mytest.repository.QuestionRepository;
import com.example.mytest.repository.TestRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SingleChoiseActivity extends AppCompatActivity {
    List<Answer> answerList = new ArrayList<>();
    Question question;
    AnswerAdapter answerAdapter;
    EditText editText;
    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private TestRepository testRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_choise);

        editText = findViewById(R.id.QuestionTitle);

        Intent intent = getIntent();
        question = (Question)intent.getSerializableExtra("QUESTION");
        question.setType(intent.getStringExtra("TYPE"));
        editText.setText(question.getTitle());

        answerRepository = new AnswerRepository(FirebaseFirestore.getInstance());
        questionRepository = new QuestionRepository(FirebaseFirestore.getInstance());
        testRepository = new TestRepository(FirebaseFirestore.getInstance());

        RecyclerView recyclerView = findViewById(R.id.rvAnswerList);
        answerRepository.getAllAnswerById(question.getId())
                .thenAccept(list -> {
                    answerList.addAll(list);
                    answerAdapter = new AnswerAdapter(answerList,this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(answerAdapter);
                });
    }

    public void ClickOnCreateAnswer(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_answer, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Введите ответ")
                .setView(dialogView)
                .setPositiveButton("Создать", (dialog1, which) -> {
                    EditText editText = dialogView.findViewById(R.id.dialog_answer_editText);
                    CheckBox checkBox = dialogView.findViewById(R.id.dialog_answer_checkBox);
                    Answer answer = new Answer(null, editText.getText().toString(), checkBox.isChecked(), question.getId());
                    answerList.add(answerRepository.addAnswer(answer));
                    answerAdapter.notifyItemInserted(answerList.size());
                })
                .setNegativeButton("Отмена", (dialog12, which) -> dialog12.dismiss())
                .create();

        dialog.show();
    }

    public void ClickOnCreateTest(View view) {

        testRepository.getById(question.getTestId())
                        .thenAccept(test -> {
                            question.setTitle(editText.getText().toString());
                            questionRepository.updateQuestion(question);
                            Intent intent = new Intent(this,CreateTestActivity.class);
                            intent.putExtra("TEST", test);
                            startActivity(intent);
                            finish();
                        });
    }
}