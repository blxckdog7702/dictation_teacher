package com.blackdog.dictation_teacher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.blackdog.dictation_teacher.models.QuizResult;

public class Util {

    private static Util util = null;
    private long backKeyPressedTime = 0;
    private Toast toast;

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

    public void onBackPressed(Activity activity){

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.moveTaskToBack(true);
            activity.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            toast.cancel();
        }
    }
}
