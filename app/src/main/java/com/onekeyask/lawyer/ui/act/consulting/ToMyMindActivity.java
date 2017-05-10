package com.onekeyask.lawyer.ui.act.consulting;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.utils.dialog.MDEditDialog;

import org.nutz.lang.Strings;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ToMyMindActivity extends BaseToolBarActivity {

    @BindView(R.id.civ_lawyer_avatar)
    CircleImageView civLawyerAvatar;
    @BindView(R.id.tv_select_money)
    TextView tvSelect;
    @BindView(R.id.tv_law_name)
    TextView tvLawName;
    @BindView(R.id.tv_2_mind)
    TextView tv2Mind;
    @BindView(R.id.tv_2_yuan)
    TextView tv2Yuan;
    @BindView(R.id.rl_circle_2)
    RelativeLayout rlCircle2;
    @BindView(R.id.tv_4_mind)
    TextView tv4Mind;
    @BindView(R.id.tv_4_yuan)
    TextView tv4Yuan;
    @BindView(R.id.rl_circle_4)
    RelativeLayout rlCircle4;
    @BindView(R.id.tv_6_mind)
    TextView tv6Mind;
    @BindView(R.id.tv_6_yuan)
    TextView tv6Yuan;
    @BindView(R.id.rl_circle_6)
    RelativeLayout rlCircle6;
    @BindView(R.id.tv_8_mind)
    TextView tv8Mind;
    @BindView(R.id.tv_8_yuan)
    TextView tv8Yuan;
    @BindView(R.id.rl_circle_8)
    RelativeLayout rlCircle8;
    @BindView(R.id.rl_circle_more)
    RelativeLayout rlCircleMore;
    @BindView(R.id.tv_more_txt)
    TextView tvMoreTxt;
    @BindView(R.id.et_to_lawyer)
    EditText etToLawyer;
    @BindView(R.id.btn_mind_submit)
    Button btnMindSubmit;
    @BindView(R.id.tv_law_mind)
    TextView tvLawMind;
    @BindView(R.id.civ_mind_talk)
    CircleImageView civMindTalk;
    @BindView(R.id.rlv_mind)
    RecyclerView rlvMind;

    private double selectMoney = 2;
    private MDEditDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_my_mind);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setToolbarText("送心意");
        tvSelect.setText("请选择答谢金额");

    }


    @OnClick({R.id.rl_circle_2, R.id.rl_circle_4, R.id.rl_circle_6, R.id.rl_circle_8, R.id.rl_circle_more, R.id.btn_mind_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_circle_2:
                selectMoney = 2;

                sel2();

                break;
            case R.id.rl_circle_4:
                selectMoney = 4;

                sel4();

                break;
            case R.id.rl_circle_6:
                selectMoney = 6;

                sel6();

                break;
            case R.id.rl_circle_8:
                selectMoney = 8;

                sel8();

                break;
            case R.id.rl_circle_more:

                dialog = new MDEditDialog.Builder(this)
                        .setTitleVisible(false)
                        .setHintText("请输入答谢金额")
                        .setMaxLines(1)
                        .setButtonTextSize(16)
                        .setLeftButtonText("取消")
                        .setRightButtonTextColor(R.color.white)
                        .setRightButtonText("确定")
                        .setOnclickListener(new MDEditDialog.OnClickEditDialogListener() {
                            @Override
                            public void clickLeftButton(View view, String text) {
                                dialog.dismiss();
                            }

                            @Override
                            public void clickRightButton(View view, String text) {
                                if (Strings.isBlank(text)) {
                                    showShort("请输入");
                                } else {
                                    selectMoney = Double.parseDouble((new DecimalFormat("#.00")).format(Double.parseDouble(text)));

                                    if (selectMoney == 0) {
                                        showShort("输入金额不能小于0.01元");
                                    } else {
                                        if (selectMoney > 10000) {
                                            showShort("输入金额不能大于10000元");
                                        } else {
                                            dialog.dismiss();
                                            selectMoney = Double.parseDouble(text);

                                            selMore();

                                        }
                                    }
                                }
                            }
                        })
                        .setMinHeight(0.1f)
                        .setWidth(0.8f)
                        .build();
                dialog.show();


                break;
            case R.id.btn_mind_submit:
//                startActivity(PayLawyerActivity.class, "name", "张三", "money", selectMoney + "");
                break;
        }
    }

    private void selMore() {
        rlCircleMore.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.select_money_bg));
        rlCircle4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircle6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircle8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircle2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        tvMoreTxt.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tv2Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv2Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv4Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv4Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv6Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv6Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv8Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv8Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
    }

    private void sel8() {
        rlCircle8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.select_money_bg));
        rlCircle4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircle6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircle2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircleMore.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        tv8Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tv8Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tv4Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv4Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv6Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv6Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv2Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv2Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tvMoreTxt.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
    }

    private void sel6() {
        rlCircle6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.select_money_bg));
        rlCircle4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircle2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircle8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircleMore.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        tv6Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tv6Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tv4Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv4Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv2Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv2Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv8Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv8Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tvMoreTxt.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
    }

    private void sel4() {
        rlCircle4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.select_money_bg));
        rlCircle2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircle6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircle8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircleMore.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        tv4Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tv4Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tv2Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv2Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv6Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv6Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv8Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv8Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tvMoreTxt.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
    }

    private void sel2() {
        rlCircle2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.select_money_bg));
        rlCircle4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircle6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircle8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        rlCircleMore.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.unselect_money_bg));
        tv2Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tv2Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        tv4Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv4Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv6Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv6Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv8Mind.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv8Yuan.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tvMoreTxt.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
    }
}
