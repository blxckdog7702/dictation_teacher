package com.blackdog.dictation_teacher.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.LoginSharedPref;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Quiz;
import com.blackdog.dictation_teacher.net.ApiRequester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseDrawerActivity {
    private static String TAG = "MainActivity.java";
    private ListView listView;
    private Button button;
    private ArrayList<Quiz> quizList;
    private Quiz selectedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarTitle.setText("받아쓰기 준비");

        listView = (ListView) findViewById(R.id.listView);
        button = (Button) findViewById(R.id.button);
        button.setClickable(false);

        try {
            requestQuizList();
            Log.d(TAG, "onCreate: 실행");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startQuizClick(View view) {
        try {
            ApiRequester.getInstance().startQuiz(ApiRequester.TEACHER_ID, selectedItem.getNumber());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "startQuizClick: 오류발생!");
        }

        Intent intent = new Intent(getApplicationContext(), QuizControlActivity.class);

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("selectedQuiz", selectedItem);
        intent.putExtra("selectedQuiz", selectedItem);
        startActivity(intent);
    }

    public void requestQuizList() throws IOException {
        ApiRequester.getInstance().getTeachersQuizzes(new ApiRequester.UserCallback<List<Quiz>>() {
            @Override
            public void onSuccess(List<Quiz> result) {
                quizList = (ArrayList) result;

                ArrayAdapter<Quiz> arrayAdapter = new ArrayAdapter<Quiz>(getApplicationContext(),
                        R.layout.quiz_list_item,
                        quizList);

                listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int count = listView.getCheckedItemCount();
                        if (count > 0) {
                            button.setClickable(true);
                            int pos = listView.getCheckedItemPosition();
                            selectedItem = (Quiz) listView.getItemAtPosition(pos);
                            Log.d("TEST", selectedItem.getName());
                            Toast.makeText(getApplicationContext(), "Value selected: " + selectedItem.getName(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onFail() {

            }
        });
    }
}
