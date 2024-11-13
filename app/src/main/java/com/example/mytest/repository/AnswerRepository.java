package com.example.mytest.repository;

import com.example.mytest.model.Answer;
import com.example.mytest.model.Question;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AnswerRepository {
    private final CollectionReference answerCollection;
    public AnswerRepository(FirebaseFirestore db){
        answerCollection = db.collection("answer");
    }
    public Answer addAnswer(Answer answer) {
        String answerId = answerCollection.document().getId();
        answer.setId(answerId);
        answerCollection.document(answerId).set(answer);

        return answer;
    }
    public void deleteAnswer(Answer answer) {
        answerCollection.document(answer.getId()).delete();
    }

    public void updateAnswer(Answer answer) {
        answerCollection.document(answer.getId()).set(answer);
    }
    public CompletableFuture<List<Answer>> getAllAnswer() {
        CompletableFuture<List<Answer>> future = new CompletableFuture<>();
        List<Answer> answersList = new ArrayList<>();

        answerCollection.get().addOnCompleteListener(task -> {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Answer answer = document.toObject(Answer.class);
                answersList.add(answer);
            }
            future.complete(answersList);
        });

        return future;
    }

    public CompletableFuture<List<Answer>> getAllAnswerById(String id) {
        CompletableFuture<List<Answer>> future = new CompletableFuture<>();
        List<Answer> answersList = new ArrayList<>();

        answerCollection.whereEqualTo("questionId", id).get().addOnCompleteListener(task -> {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Answer answer = document.toObject(Answer.class);
                answersList.add(answer);
            }
            future.complete(answersList);
        });

        return future;
    }

    public CompletableFuture<Boolean> checkCorrectAnswer(String id){
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        answerCollection.whereEqualTo("questionId", id).get()
                .addOnCompleteListener(task -> {
                    boolean fl = true;
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Answer answer = document.toObject(Answer.class);
                        if(answer.isCorrect()) fl = false;
                    }
                    future.complete(fl);
                });

        return future;
    }
}