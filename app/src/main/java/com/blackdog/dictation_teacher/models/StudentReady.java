package com.blackdog.dictation_teacher.models;

/**
 * Created by DH on 2017-10-18.
 */

public class StudentReady {
    String id;
    String name;

    boolean ready;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

}
