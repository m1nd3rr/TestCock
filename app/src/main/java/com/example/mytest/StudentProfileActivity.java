package com.example.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytest.adapter.TestAdapter;
import com.example.mytest.auth.Select;
import com.example.mytest.model.Test;
import com.example.mytest.repository.RoomRepository;
import com.example.mytest.repository.TestRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class StudentProfileActivity extends AppCompatActivity {
    private RoomRepository roomRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        Button buttonLogout = findViewById(R.id.buttonLogout);
        textViewWelcome.setText("Добро пожаловать в профиль пользователя!");
        buttonLogout.setOnClickListener(view -> {
            finish();
        });

        roomRepository = new RoomRepository(FirebaseFirestore.getInstance());
    }

    public void ClickMama(View view) {
        EditText editText = findViewById(R.id.mama);
        roomRepository.getTestByRoomNumber(editText.getText().toString())
                .thenAccept(test -> {
                    Select.setTest(test);
                    Intent intent = new Intent(this, PassingTestActivity.class);
                    startActivity(intent);
                    finish();
                });
    }
}