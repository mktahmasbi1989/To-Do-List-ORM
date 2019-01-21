package com.example.mohamdkazem.advancetodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mohamdkazem.advancetodolist.Model.Task;
import com.example.mohamdkazem.advancetodolist.Model.TasksRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowTaskDetailDialog extends DialogFragment {
    private static final String ARG_TASK ="com.example.mohamdkazem.advancetodolist.task" ;
    private static final int REQ_DETAIL =10 ;
    private Task mTask;
    private TextView mTextTitle,mTextTime,mTextDetail,mTextDate;
    private Button mBtnEdit;
    private Long taskId;



    public static ShowTaskDetailDialog newInstance(Long taskId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, taskId);
        ShowTaskDetailDialog fragment = new ShowTaskDetailDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskId=getArguments().getLong(ARG_TASK);
        mTask= TasksRepository.getInstance(getActivity()).getTaskORm(taskId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.task_detail_dialog,container,false);
        initDialog(view);

        mTextTitle.setText(mTask.getMTitle());
        mTextDetail.setText(mTask.getMDetail());

//        set Date TextView
        Date date= mTask.getMDate();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formatDate=simpleDateFormat.format(date);
        mTextDate.setText(formatDate);

//      set Time TextView
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat( "h:mm a");
        String formatTime=simpleDateFormat1.format(date);
        mTextTime.setText(formatTime);

        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent=TaskDetailActivity.newIntent(getActivity(),taskId);
//                startActivity(intent);
                startActivityForResult(intent,REQ_DETAIL);
            }
        });



        return view;


    }

    private void initDialog(View view) {
        mTextTitle=view.findViewById(R.id.text_title_dialog);
        mTextDetail=view.findViewById(R.id.text_detail_dialog);
        mTextDate=view.findViewById(R.id.text_date_dialog);
        mTextTime=view.findViewById(R.id.text_time_dialog);
        mBtnEdit=view.findViewById(R.id.btn_edit_dialog);
    }
}