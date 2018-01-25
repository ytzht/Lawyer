package com.onekeyask.lawfirm.ui.act.user;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.PersonalInfo;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.utils.UserService;

public class MyIntroduceActivity extends BaseToolBarActivity {
    private TextView intro;
    private PopupWindow popupWindow = null;
    private View popupView;
    private TextView tv_cancel_popup;
    private TextView tv_yes_popup;
    private EditText et_intro;
    private UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_introduce);
        setToolbarText("简介");

        service = UserService.service(getBaseContext());

        intro = (TextView) findViewById(R.id.tv_introduce);
        popupView = LayoutInflater.from(this).inflate(R.layout.popup_change_name, null);

        tv_cancel_popup = (TextView) popupView.findViewById(R.id.tv_cancel_popup);
        tv_yes_popup = (TextView) popupView.findViewById(R.id.tv_yes_popup);
        et_intro = (EditText) popupView.findViewById(R.id.et_name);


        tv_cancel_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        tv_yes_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String introduce = et_intro.getText().toString();
                if (introduce.length() > 5) {
                    OkGo.<String>post(Apis.SaveIntroduce).params("lawyerId", service.getLawyerId()).params("introduce", introduce).execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                            if (data.getCode() == 0) {
                                service.setIntroduce(introduce);
                                intro.setText(service.getIntroduce());
                                showShort("修改成功");
                                popupWindow.dismiss();

                            } else {
                                showShort(data.getMsg());
                            }
                        }
                    });
                } else {
                    showShort("不能少于五个字符");
                }

            }
        });


        OkGo.<String>get(Apis.getPersonalInfo).params("lawyerId", service.getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        PersonalInfo info = (new Gson()).fromJson(response.body(), PersonalInfo.class);
                        if (info.getCode() == 0) {

                            intro.setText(info.getData().getIntroduce());
                            et_intro.setText(info.getData().getIntroduce());
                        } else {
                            startActivity(LoginActivity.class);
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow = getPopwindow(popupView);
            }
        });
    }




    //跳出选项框
    public PopupWindow getPopwindow(View view) {
        PopupWindow popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.6f;
        getWindow().setAttributes(layoutParams);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(intro, Gravity.BOTTOM, 0, 0);
//        popupWindow.showAsDropDown(rlGiveMoney);
//        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.update();
        popupWindow.setTouchable(true);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.alpha = 1f;
                getWindow().setAttributes(layoutParams);
            }
        });
        return popupWindow;
    }

}
