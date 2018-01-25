package com.onekeyask.lawyer.ui.act.consulting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

public class ComplaintFinishActivity extends BaseToolBarActivity {

    private android.widget.TextView complaintover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_finish);
        this.complaintover = (TextView) findViewById(R.id.complaint_over);
        setToolbarText("提交成功");

        complaintover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
