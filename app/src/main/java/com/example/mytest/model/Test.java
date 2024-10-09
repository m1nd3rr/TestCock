package com.example.mytest.model;

public class Test {
    private String teacherId;
    private String title;

    private String id;

    public Test() {
    }

    public Test(String teacherId, String title, String id) {
        this.teacherId = teacherId;
        this.title = title;
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
