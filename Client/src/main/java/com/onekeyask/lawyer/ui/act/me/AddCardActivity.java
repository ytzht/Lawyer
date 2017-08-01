package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCardActivity extends BaseToolBarActivity {

    @BindView(R.id.et_no)
    EditText etNo;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_type)
    EditText etType;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.rb_save)
    RadioButton rbSave;
    @BindView(R.id.rb_trust)
    RadioButton rbTrust;
    @BindView(R.id.rg_card)
    RadioGroup rgCard;
    @BindView(R.id.balance_next)
    TextView balanceNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ButterKnife.bind(this);
        setToolbarText("添加银行卡");
    }

    @OnClick({R.id.rb_save, R.id.rb_trust, R.id.rg_card, R.id.balance_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_save:
                break;
            case R.id.rb_trust:
                break;
            case R.id.rg_card:
                break;
            case R.id.balance_next:
                finish();
                break;
        }
    }
}
