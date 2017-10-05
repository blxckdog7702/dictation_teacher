package com.blackdog.dictation_teacher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.ExamResultPage;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.QuestionResult;
import com.blackdog.dictation_teacher.models.QuizResult;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.net.ApiRequester;
import com.dd.processbutton.FlatButton;

import java.util.List;


/**
 * Created by JBStat on 2016-11-29.
 */
public class ApplicantListAdapter extends RecyclerView.Adapter<ApplicantListAdapter.ViewHolder> {

    String teacherLoginID;
    List<Student> applicants;
    Context context;


    public ApplicantListAdapter(Context _context,String _teacherLoginID, List<Student> _applicants) {
        context = _context;
        applicants = _applicants;
        teacherLoginID = _teacherLoginID;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;

        TextView tv_name;
        TextView tv_class;

        FlatButton btn_accept;
        FlatButton btn_cancel;

        public ViewHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.layout_item_applicant);

            tv_name = (TextView) view.findViewById(R.id.tv_matching_name);
            tv_class = (TextView) view.findViewById(R.id.tv_matching_class);

            btn_accept = (FlatButton) view.findViewById(R.id.btn_matching_accept);
            btn_cancel = (FlatButton) view.findViewById(R.id.btn_matching_cancel);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_applicant, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Student applicant = applicants.get(position);

        holder.tv_class.setText(applicant.getSchool() + " : " + applicant.getGrade() + "-" + applicant.getClass_());
        holder.tv_name.setText(applicant.getName());

        holder.btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiRequester.getInstance().acceptMatching(teacherLoginID, applicant.getId(), new ApiRequester.UserCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if(result) Toast.makeText(context,"수락 요청 성공",Toast.LENGTH_SHORT).show();
                        else Toast.makeText(context,"수락 요청 실패",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(context,"수락 요청 실패",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiRequester.getInstance().acceptMatching(teacherLoginID, applicant.getId(), new ApiRequester.UserCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if(result) Toast.makeText(context,"거절 요청 성공",Toast.LENGTH_SHORT).show();
                        else Toast.makeText(context,"거절 요청 실패",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(context,"거절 요청 실패",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return applicants.size();
    }
}
