package com.example.mytest.repository;

import com.example.mytest.model.Student;
import com.example.mytest.model.Test;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TestRepository {
    private final CollectionReference testCollection;

    public TestRepository(FirebaseFirestore db){
        testCollection = db.collection("tests");
    }
    public void addTest(Test test) {
        String testId = testCollection.document().getId();
        test.setId(testId);
        testCollection.document(testId).set(test);
    }
    public void deleteTest(Test test) {
        testCollection.document(test.getId()).delete();
    }

    public void updateTest(Test test, Test newTest) {
        testCollection.document(test.getId()).set(newTest);
    }
    public CompletableFuture<List<Test>> getAllTest() {
        CompletableFuture<List<Test>> future = new CompletableFuture<>();
        List<Test> testList = new ArrayList<>();

        testCollection.get().addOnCompleteListener(task -> {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Test test = document.toObject(Test.class);
                testList.add(test);
            }
            future.complete(testList);
        });

        return future;
    }
}
