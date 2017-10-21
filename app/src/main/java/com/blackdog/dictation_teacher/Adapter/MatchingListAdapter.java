package com.blackdog.dictation_teacher.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.singleton.MyTeacherInfo;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.net.ApiRequester;

import java.util.List;

/**
 * Created by DH on 2017-10-13.
 */

public class MatchingListAdapter extends RecyclerView.Adapter<MatchingListAdapter.ViewHolder> {
    private List<Student> mMatchingList;
    private Context mContext;

    public MatchingListAdapter(Context _context, List<Student> _matchingList) {
        mMatchingList = _matchingList;
        mContext = _context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mSchoolName;
        public TextView mStudentName;
        public TextView mGrade;
        public TextView mClassNumber;
        public TextView mStudentNumber;
        public ImageView mMatchBt;
//        public Button mAccept;
//        public Button mRefuse;

        public ViewHolder(View v) {
            super(v);
            mSchoolName = (TextView) v.findViewById(R.id.tv_matching_school_name);
            mStudentName = (TextView) v.findViewById(R.id.tv_matching_student_name);
            mGrade = (TextView) v.findViewById(R.id.tv_matching_student_grade);
            mClassNumber = (TextView) v.findViewById(R.id.tv_matching_student_class_number);
            mStudentNumber = (TextView) v.findViewById(R.id.tv_matching_student_student_number);
            mMatchBt = (ImageView) v.findViewById(R.id.mMatchBt);

//            mAccept = (Button) v.findViewById(R.id.bt_matching_student_accept);
//            mRefuse = (Button) v.findViewById(R.id.bt_matching_student_refuse);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_matching_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mSchoolName.setText(mMatchingList.get(position).getSchool());
        holder.mGrade.setText(mMatchingList.get(position).getGrade());
        holder.mClassNumber.setText(mMatchingList.get(position).getClass_());
        holder.mStudentNumber.setText(mMatchingList.get(position).getStudentId().toString());
        holder.mStudentName.setText(mMatchingList.get(position).getName());


                holder.mMatchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                alertDialogBuilder.setTitle("매칭신청알림").setMessage("매칭신청을 수락하시겠습니까?").setPositiveButton("수락", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int selectedPosition = holder.getAdapterPosition();
                        Toast.makeText(mContext, "매칭이 성사되었습니다.", Toast.LENGTH_SHORT).show();
                        matchingAccept(mMatchingList.get(selectedPosition).getId(), selectedPosition);

                    }
                }).setNegativeButton("거절", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        int selectedPosition = holder.getAdapterPosition();
                        Toast.makeText(mContext, "매칭을 거절했습니다.", Toast.LENGTH_SHORT).show();
                        matchingCancel(mMatchingList.get(selectedPosition).getId(), selectedPosition);
                    }
                }).setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                    }
                }).show();



            }
        });

//        holder.mAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int selectedPosition = holder.getAdapterPosition();
//                Toast.makeText(mContext, "수락", Toast.LENGTH_SHORT).show();
//                matchingAccept(mMatchingList.get(selectedPosition).getId(), selectedPosition);
//            }
//        });

//        holder.mRefuse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int selectedPosition = holder.getAdapterPosition();
//                Toast.makeText(mContext, "거절", Toast.LENGTH_SHORT).show();
//                matchingCancel(mMatchingList.get(selectedPosition).getId(), selectedPosition);
//            }
//        });
    }

    private void matchingCancel(String studentId, final int position) {
        ApiRequester.getInstance().cancelMatching(
                MyTeacherInfo.getInstance().getTeacher().getLoginId(),
                studentId,
                new ApiRequester.UserCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if(result) {
                            //삭제되었습니다.
                            mMatchingList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,mMatchingList.size());
                        } else {
                            //삭제 실패?
                        }
                    }
                    @Override
                    public void onFail() {
                        //삭제 실패
                    }
                }
        );
    }

    private void matchingAccept(String studentId, final int position) {
        ApiRequester.getInstance().acceptMatching(
                MyTeacherInfo.getInstance().getTeacher().getLoginId(),
                studentId,
                new ApiRequester.UserCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if(result) {
                    //삭제되었습니다.
                    mMatchingList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,mMatchingList.size());
                } else {
                    //삭제 실패?
                }
            }

            @Override
            public void onFail() {
                //삭제 실패
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMatchingList.size();
    }
}
