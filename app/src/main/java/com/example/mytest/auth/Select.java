package com.example.mytest.auth;

import com.example.mytest.model.Question;
import com.example.mytest.model.Test;

public class Select {
    private static Question question;
    private static Test test;

    public static Question getQuestion() {
        return question;
    }

    public static void setQuestion(Question question) {
        Select.question = question;
    }

    public static Test getTest() {
        return test;
    }

    public static void setTest(Test test) {
        Select.test = test;
    }
}
