package com.blackdog.dictation_teacher.singleton;

import com.blackdog.dictation_teacher.models.Teacher;

/**
 * Created by DH on 2017-09-26.
 */

public class MyTeacherInfo {

    private static MyTeacherInfo instance;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    private Teacher teacher;

    public static MyTeacherInfo getInstance() {
        if (instance == null) {
            instance = new MyTeacherInfo();
        }
        return instance;
    }


}