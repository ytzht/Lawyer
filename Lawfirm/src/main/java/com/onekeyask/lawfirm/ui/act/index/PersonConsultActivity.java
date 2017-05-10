package com.onekeyask.lawfirm.ui.act.index;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.SwitchButton;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonConsultActivity extends BaseToolBarActivity {

    @BindView(R.id.sb_phone)
    SwitchButton sbPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_consult);
        ButterKnife.bind(this);
        setToolbarText("私人律师");

        if (getIntent().getStringExtra("switch").equals("true")) {
            sbPhone.setChecked(true);
        }else {
            sbPhone.setChecked(false);
        }
        sbPhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showShort(isChecked+"");
            }
        });

    }
}
