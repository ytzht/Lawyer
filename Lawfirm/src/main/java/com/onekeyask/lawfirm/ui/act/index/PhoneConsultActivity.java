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

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneConsultActivity extends BaseToolBarActivity {

    @BindView(R.id.sb_phone)
    SwitchButton sbPhone;
    private RelativeLayout rl;
    private android.widget.TextView pricecycle;
    private android.widget.TextView tvprice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_consult);
        rl = (RelativeLayout) findViewById(R.id.rl_price);
        this.tvprice = (TextView) findViewById(R.id.tv_price);
        this.pricecycle = (TextView) findViewById(R.id.price_cycle);

        ButterKnife.bind(this);
        setToolbarText("电话咨询");

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
                        .params("serviceType", 3)
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
                .params("serviceType", 3)
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
                            pricecycle.setText("费用("+setting.getData().getServiceInfo().getPriceList().get(0).getCycle()+")");
                            tvprice.setText(setting.getData().getServiceInfo().getPriceList().get(0).getPrice()+"元/"+setting.getData().getServiceInfo().getPriceList().get(0).getCycle());
                            rl.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showAlert();
                                }
                            });
                        } else {
                            showShort(setting.getMsg());
                        }


                    }
                });
    }


    private MDEditDialog dialog;
    private void showAlert(){
        dialog = new MDEditDialog.Builder(PhoneConsultActivity.this)
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
                            if (money < 1) {
                                showShort("不能低于1元");
                            } else if (money > 100000) {
                                showShort("不能高于十万元");
                            } else {

                                dialog.dismiss();

                                SavePrice(3, setting.getData().getServiceInfo().getPriceList().get(0).getPriceId(), money+"");

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
