package com.blackdog.dictation_teacher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.blackdog.dictation_teacher.models.QuizResult;

public class Util {

    private static Util util = null;

    public static synchronized Util getInstance()
    {
        if(util == null){}
        try{
            if(util==null)
                util = new Util();
            return util;
        }
        finally {}
    }

    public void moveAcitivity(Context context, final Class<? extends Activity> ActivityToOpen) {
        Intent intent = new Intent(context, ActivityToOpen);
        context.startActivity(intent);
    }
    public void moveAcitivity(Context context, final Class<? extends Activity> ActivityToOpen, QuizResult quizResult) {
        Intent intent = new Intent(context, ActivityToOpen);
        intent.putExtra("OBJECT", quizResult);
        context.startActivity(intent);
    }

    public void moveAcitivity(Context context, final Class<? extends Activity> ActivityToOpen, QuizResult quizResult, int questionNumber) {
        Intent intent = new Intent(context, ActivityToOpen);
        intent.putExtra("OBJECT", quizResult);
        intent.putExtra("questionNumber", questionNumber);
        context.startActivity(intent);
    }

}
