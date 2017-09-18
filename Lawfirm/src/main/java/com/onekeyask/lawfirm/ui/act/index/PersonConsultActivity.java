package com.onekeyask.lawfirm.ui.act.index;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.IntoSetting;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.utils.UserService;
import com.onekeyask.lawfirm.utils.dialog.MDEditDialog;

import org.nutz.lang.Strings;

import java.text.DecimalFormat;

public class PersonConsultActivity extends BaseToolBarActivity {


    private SwitchButton sbPhone;
    private android.widget.TextView pricecycle1;
    private android.widget.TextView tvprice1;
    private android.widget.TextView pricecycle2;
    private android.widget.TextView tvprice2;
    private android.widget.TextView pricecycle3;
    private android.widget.TextView tvprice3;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_consult);
        rl1 = (RelativeLayout) findViewById(R.id.rl_price1);
        rl2 = (RelativeLayout) findViewById(R.id.rl_price2);
        rl3 = (RelativeLayout) findViewById(R.id.rl_price3);

        this.tvprice3 = (TextView) findViewById(R.id.tv_price3);
        this.pricecycle3 = (TextView) findViewById(R.id.price_cycle3);
        this.tvprice2 = (TextView) findViewById(R.id.tv_price2);
        this.pricecycle2 = (TextView) findViewById(R.id.price_cycle2);
        this.tvprice1 = (TextView) findViewById(R.id.tv_price1);
        this.pricecycle1 = (TextView) findViewById(R.id.price_cycle1);
        this.sbPhone = (SwitchButton) findViewById(R.id.sb_phone);
        setToolbarText("私人律师");


        if (getIntent().getStringExtra("switch").equals("true")) {
            sbPhone.setChecked(true);
        }else {
            sbPhone.setChecked(false);
        }

        initIntoSetting();


        sbPhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                                    sbPhone.setChecked(isChecked);
                                } else {
                                    showShort(data.getMsg());
                                    initIntoSetting();
                                }
                            }
                        });
            }
        });

    }

    IntoSetting setting;

    private void initIntoSetting() {
        OkGo.<String>get(Apis.IntoSetting)
                .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .params("serviceType", 1)
                .execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                setting = (new Gson()).fromJson(response.body(), IntoSetting.class);
                if (setting.getCode() == 0) {
                    if (setting.getData().getServiceInfo().isIsOn()) {
                        sbPhone.setChecked(true);
                    } else {
                        sbPhone.setChecked(false);
                    }

                    pricecycle1.setText("费用("+setting.getData().getServiceInfo().getPriceList().get(0).getCycle()+")");
                    pricecycle2.setText("费用("+setting.getData().getServiceInfo().getPriceList().get(1).getCycle()+")");
                    pricecycle3.setText("费用("+setting.getData().getServiceInfo().getPriceList().get(2).getCycle()+")");

                    tvprice1.setText(setting.getData().getServiceInfo().getPriceList().get(0).getPrice()+"元/"+setting.getData().getServiceInfo().getPriceList().get(0).getCycle());
                    tvprice2.setText(setting.getData().getServiceInfo().getPriceList().get(1).getPrice()+"元/"+setting.getData().getServiceInfo().getPriceList().get(1).getCycle());
                    tvprice3.setText(setting.getData().getServiceInfo().getPriceList().get(2).getPrice()+"元/"+setting.getData().getServiceInfo().getPriceList().get(2).getCycle());


                    rl1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showAlert(0);
                        }
                    });
                    rl2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showAlert(1);
                        }
                    });
                    rl3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showAlert(2);
                        }
                    });



                } else {
                    showShort(setting.getMsg());
                }


            }
        });
    }

    private MDEditDialog dialog;
    private void showAlert(final int i){
        dialog = new MDEditDialog.Builder(PersonConsultActivity.this)
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
                            double money = Double.parseDouble((new DecimalFormat("#.00")).format(Double.parseDouble(text)));
                            if (money == 0) {
                                showShort("请输入金额");
                            } else {

                                dialog.dismiss();

                                SavePrice(1, setting.getData().getServiceInfo().getPriceList().get(i).getPriceId(), money+"");

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
                        if (data.getCode() == 0){
                            showShort("保存成功");
                            initIntoSetting();
                        }else {
                            showShort(data.getMsg());
                        }
                    }
                });

    }


}
