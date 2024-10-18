package com.example.mytest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class QuestionActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_question);

        editText = findViewById(R.id.QuestionTitle);

        Intent intent = getIntent();
        question = (Question)intent.getSerializableExtra("QUESTION");
        editText.setText(question.getTitle());
        answerRepository = new AnswerRepository(FirebaseFirestore.getInstance());
        questionRepository = new QuestionRepository(FirebaseFirestore.getInstance());
        testRepository = new TestRepository(FirebaseFirestore.getInstance());

        RecyclerView recyclerView = findViewById(R.id.rvAnswerList);
        answerRepository.getAllAnswerById(question.getId())
                .thenAccept(list -> {
                    answerList.addAll(list);
                    answerAdapter = new AnswerAdapter(answerList,this, question);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(answerAdapter);

                    if (question.getType().equals("true-false") && list.isEmpty()) {
                        Answer answer = new Answer(null,"TRUE",false,question.getId(),-1, null);
                        Answer answer2 = new Answer(null,"FALSE",false,question.getId(),-1, null);
                        answerList.add(answerRepository.addAnswer(answer));
                        answerList.add(answerRepository.addAnswer(answer2));
                        answerAdapter.notifyItemInserted(answerList.size());
                    }
                });
    }
    public void singleChoose(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_answer, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Введите ответ")
                .setView(dialogView)
                .setPositiveButton("Создать", (dialog1, which) -> {
                    EditText editText = dialogView.findViewById(R.id.dialog_answer_editText);
                    CheckBox checkBox = dialogView.findViewById(R.id.dialog_answer_checkBox);
                    answerRepository.checkCorrectAnswer(question.getId())
                            .thenAccept(correct -> {
                                if (correct || !checkBox.isChecked()) {
                                    Answer answer = new Answer(null, editText.getText().toString(), checkBox.isChecked(), question.getId(), -1, null);
                                    answerList.add(answerRepository.addAnswer(answer));
                                    answerAdapter.notifyItemInserted(answerList.size());
                                }
                            });

                })
                .setNegativeButton("Отмена", (dialog12, which) -> dialog12.dismiss())
                .create();



        dialog.show();
    }
    public void ClickOnCreateAnswer(View view) {
     switch (question.getType()){
         case "single-choice":
             singleChoose();
             break;
         case "multi-choice":
             multiChoose();
             break;
         case "sort":
             sort();
             break;
         case "text":
             text();
             break;
         case "connect":
             connect();
             break;
     }
    }


    private void multiChoose() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_answer, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Введите ответ")
                .setView(dialogView)
                .setPositiveButton("Создать", (dialog1, which) -> {
                    EditText editText = dialogView.findViewById(R.id.dialog_answer_editText);
                    CheckBox checkBox = dialogView.findViewById(R.id.dialog_answer_checkBox);

                    Answer answer = new Answer(null, editText.getText().toString(), checkBox.isChecked(), question.getId(), -1, null);
                    answerList.add(answerRepository.addAnswer(answer));
                    answerAdapter.notifyItemInserted(answerList.size());
                })
                .setNegativeButton("Отмена", (dialog12, which) -> dialog12.dismiss())
                .create();

        dialog.show();
    }

    public void sort(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_sort,null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Введите ответ")
                .setView(dialogView)
                .setPositiveButton("Создать", (dialog1, which) -> {
                    EditText editText = dialogView.findViewById(R.id.dialog_answer_editText);
                    EditText editText2 = dialogView.findViewById(R.id.dialog_answer_sort_number);

                    Answer answer = new Answer(null,editText.getText().toString(),true, question.getId(),Integer.parseInt(editText2.getText().toString()), null);
                    answerList.add(answerRepository.addAnswer(answer));
                    answerAdapter.notifyItemInserted(answerList.size());
                })
                .setNegativeButton("Отмена", (dialog12, which) -> dialog12.dismiss())
                .create();
        dialog.show();
    }

    public void text(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.diaolog_text,null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Введите ответ")
                .setView(dialogView)
                .setPositiveButton("Создать", (dialog1, which) -> {
                    EditText editText = dialogView.findViewById(R.id.dialog_answer_editText);

                    Answer answer = new Answer(null,editText.getText().toString(),true,question.getId(),-1, null);
                    answerList.add(answerRepository.addAnswer(answer));
                    answerAdapter.notifyItemInserted(answerList.size());
        })
                .setNegativeButton("Отмена", (dialog12, which) -> dialog12.dismiss())
                .create();


        dialog.show();
    }

    public void connect(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_connect,null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Введите ответ")
                .setView(dialogView)
                .setPositiveButton("Создать", (dialog1, which) -> {
                    EditText editText = dialogView.findViewById(R.id.dialog_answer_editTex);
                    EditText editText1 = dialogView.findViewById(R.id.dialog_answer_editText2);

                    Answer answer = new Answer(null,editText.getText().toString(),true,question.getId(),-1,editText1.getText().toString());
                    answerList.add(answerRepository.addAnswer(answer));
                    answerAdapter.notifyItemInserted(answerList.size());
                })
                .setNegativeButton("Отмена", (dialog12, which) -> dialog12.dismiss())
                .create();
        dialog.show();

    }
    public void onBackButton(View view) {
        Intent intent = new Intent(QuestionActivity.this, QuestionSettingActivity.class);
        startActivity(intent);
        finish();
    }

    public void ClickOnCreateQuestion(View view) {

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