package com.onekeyask.lawyer.ui.act.consulting;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

public class ComplaintLawyerActivity extends BaseToolBarActivity {

    private EditText etcontent;
    private ImageView addpic;
    private TextView tvaddtxt;
    private RecyclerView rlvaddpic;
    private EditText etphone;
    private TextView submitcomplaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_lawyer);

        initView();


        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void initView() {
        this.submitcomplaint = (TextView) findViewById(R.id.submit_complaint);
        this.etphone = (EditText) findViewById(R.id.et_phone);
        this.rlvaddpic = (RecyclerView) findViewById(R.id.rlv_add_pic);
        this.tvaddtxt = (TextView) findViewById(R.id.tv_add_txt);
        this.addpic = (ImageView) findViewById(R.id.add_pic);
        this.etcontent = (EditText) findViewById(R.id.et_content);
    }


}
