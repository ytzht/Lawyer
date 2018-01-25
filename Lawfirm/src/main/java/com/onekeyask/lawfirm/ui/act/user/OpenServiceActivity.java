package com.onekeyask.lawfirm.ui.act.user;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.entity.ServiceInfo;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseActivity;
import com.onekeyask.lawfirm.utils.UserService;
import com.onekeyask.lawfirm.utils.dialog.MDEditDialog;

import org.nutz.lang.Strings;

public class OpenServiceActivity extends BaseActivity {

    private ImageView iv_arrow;
    private TextView next_step_two;
    private SwitchButton sb_pic, sb_phone, sb_person;
    private RelativeLayout rl_1, rl_2, rl3_1, rl3_2, rl3_3;
    private LinearLayout ll_3;
    private TextView tv_price_1, tv_price_1_1, tv_price_2, tv_price_2_1,
            tv_price_3_1, tv_price_3_1_, tv_price_3_2, tv_price_3_2_, tv_price_3_3, tv_price_3_3_;
    private ServiceInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_service);

        initView();

        initClick();
    }

    private void initClick() {
        iv_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SkillAreaActivity.class);
                finish();
            }
        });

        next_step_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UploadImgActivity.class);
                finish();
            }
        });

        sb_phone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                OkGo.<String>post(Apis.SaveSwitch)
                        .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                        .params("serviceType", 3)
                        .params("isOn", isChecked)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                                if (data.getCode() == 0) {
                                    sb_phone.setChecked(isChecked);
                                    if (isChecked) {
                                        rl_2.setVisibility(View.VISIBLE);
                                    } else {
                                        rl_2.setVisibility(View.GONE);
                                    }
                                } else {
                                    showShort(data.getMsg());
                                    initIntoSetting();
                                }
                            }
                        });
            }
        });

        sb_person.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                OkGo.<String>post(Apis.SaveSwitch)
                        .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                        .params("serviceType", 1)
                        .params("isOn", isChecked)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                                if (data.getCode() == 0) {
                                    sb_person.setChecked(isChecked);
                                    if (isChecked) {
                                        ll_3.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_3.setVisibility(View.GONE);
                                    }
                                } else {
                                    showShort(data.getMsg());
                                    initIntoSetting();
                                }
                            }
                        });
            }
        });

        sb_pic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                OkGo.<String>post(Apis.SaveSwitch)
                        .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                        .params("serviceType", 2)
                        .params("isOn", isChecked)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                                if (data.getCode() == 0) {
                                    sb_pic.setChecked(isChecked);
                                    if (isChecked) {
                                        rl_1.setVisibility(View.VISIBLE);
                                    } else {
                                        rl_1.setVisibility(View.GONE);
                                    }
                                } else {
                                    showShort(data.getMsg());
                                    initIntoSetting();
                                }
                            }
                        });
            }
        });


    }

    private void initView() {
        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);
        sb_pic = (SwitchButton) findViewById(R.id.sb_pic);
        sb_phone = (SwitchButton) findViewById(R.id.sb_phone);
        sb_person = (SwitchButton) findViewById(R.id.sb_person);
        next_step_two = (TextView) findViewById(R.id.next_step_two);
        tv_price_1 = (TextView) findViewById(R.id.tv_price_1);
        tv_price_1_1 = (TextView) findViewById(R.id.tv_price_1_1);
        tv_price_2 = (TextView) findViewById(R.id.tv_price_2);
        tv_price_2_1 = (TextView) findViewById(R.id.tv_price_2_1);
        tv_price_3_1 = (TextView) findViewById(R.id.tv_price_3_1);
        tv_price_3_1_ = (TextView) findViewById(R.id.tv_price_3_1_);
        tv_price_3_2 = (TextView) findViewById(R.id.tv_price_3_2);
        tv_price_3_2_ = (TextView) findViewById(R.id.tv_price_3_2_);
        tv_price_3_3 = (TextView) findViewById(R.id.tv_price_3_3);
        tv_price_3_3_ = (TextView) findViewById(R.id.tv_price_3_3_);
        rl_1 = (RelativeLayout) findViewById(R.id.rl_1);
        rl_2 = (RelativeLayout) findViewById(R.id.rl_2);
        ll_3 = (LinearLayout) findViewById(R.id.ll_3);
        rl3_1 = (RelativeLayout) findViewById(R.id.rl3_1);
        rl3_2 = (RelativeLayout) findViewById(R.id.rl3_2);
        rl3_3 = (RelativeLayout) findViewById(R.id.rl3_3);

        initIntoSetting();


    }



    private void initIntoSetting() {
        OkGo.<String>get(Apis.ServiceInfo)
                .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        info = (new Gson()).fromJson(response.body(), ServiceInfo.class);
                        if (info.getCode() == 0) {
                            if (info.getData().getServiceList().get(0).isIsOn()) {
                                sb_pic.setChecked(true);
                                rl_1.setVisibility(View.VISIBLE);
                            } else {
                                sb_pic.setChecked(false);
                                rl_1.setVisibility(View.GONE);
                            }

                            if (info.getData().getServiceList().get(1).isIsOn()){
                                sb_phone.setChecked(true);
                                rl_2.setVisibility(View.VISIBLE);
                            }else {
                                sb_phone.setChecked(false);
                                rl_2.setVisibility(View.GONE);
                            }

                            if (info.getData().getServiceList().get(2).isIsOn()){
                                sb_person.setChecked(true);
                                ll_3.setVisibility(View.VISIBLE);
                            }else {
                                sb_person.setChecked(false);
                                ll_3.setVisibility(View.GONE);
                            }


                            tv_price_3_1.setText("元/" + info.getData().getServiceList().get(2).getPriceList().get(0).getCycle());
                            tv_price_3_1_.setText(info.getData().getServiceList().get(2).getPriceList().get(0).getPrice()+"");
                            tv_price_3_2.setText("元/" + info.getData().getServiceList().get(2).getPriceList().get(1).getCycle());
                            tv_price_3_2_.setText(info.getData().getServiceList().get(2).getPriceList().get(1).getPrice()+"");
                            tv_price_3_3.setText("元/" + info.getData().getServiceList().get(2).getPriceList().get(2).getCycle());
                            tv_price_3_3_.setText(info.getData().getServiceList().get(2).getPriceList().get(2).getPrice()+"");

                            rl3_1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showAlert(1, info.getData().getServiceList().get(2).getPriceList().get(0).getPriceId());
                                }
                            });
                            rl3_2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showAlert(1, info.getData().getServiceList().get(2).getPriceList().get(1).getPriceId());
                                }
                            });
                            rl3_3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showAlert(1, info.getData().getServiceList().get(2).getPriceList().get(2).getPriceId());
                                }
                            });

                            tv_price_1.setText("元/" + info.getData().getServiceList().get(0).getPriceList().get(0).getCycle());
                            tv_price_1_1.setText(info.getData().getServiceList().get(0).getPriceList().get(0).getPrice()+"");
                            rl_1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showAlert(2, info.getData().getServiceList().get(0).getPriceList().get(0).getPriceId());
                                }
                            });

                            tv_price_2.setText("元/" + info.getData().getServiceList().get(1).getPriceList().get(0).getCycle());
                            tv_price_2_1.setText(info.getData().getServiceList().get(1).getPriceList().get(0).getPrice()+"");
                            rl_2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showAlert(3, info.getData().getServiceList().get(1).getPriceList().get(0).getPriceId());
                                }
                            });


                        } else {
                            showShort(info.getMsg());
                        }
                    }
                });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(SkillAreaActivity.class);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    private MDEditDialog dialog;
    private void showAlert(final int serviceType, final int priceId) {
        dialog = new MDEditDialog.Builder(OpenServiceActivity.this)
                .setTitleVisible(false)
                .setHintText("请输入自定义价格")
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
                            if (Double.parseDouble(text) < 1) {
                                showShort("不能低于1元");
                            } else if (Double.parseDouble(text) > 100000) {
                                showShort("不能高于十万元");
                            } else {

                                dialog.dismiss();

                                SavePrice(serviceType, priceId, Double.parseDouble(text) + "");

                            }
                        }
                    }
                })
                .setMinHeight(0.1f)
                .setWidth(0.8f)
                .build();
        dialog.show();
    }

    private void SavePrice(int i, int priceId, String l) {
        OkGo.<String>post(Apis.Saveprice).params("priceId", priceId)
                .params("serviceType", i)
                .params("price", l)
                .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                        if (data.getCode() == 0) {
                            showShort("保存成功");
                            initIntoSetting();
                        } else {
                            showShort(data.getMsg());
                        }
                    }
                });

    }

}
