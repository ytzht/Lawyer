package com.onekeyask.lawfirm.ui.act.user;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.entity.ReviewInfo;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.utils.UserService;
import com.squareup.picasso.Picasso;

import java.io.File;

import me.iwf.photopicker.PhotoPicker;

public class MyInfoSubmitActivity extends BaseToolBarActivity {

    private LinearLayout llphone, llsex, llname;
    private TextView useroffice, usersex, username;
    private UserService service;

    private PopupWindow popupWindowSex = null;

    private KProgressHUD hud;
    private String lawyerOfficeTel;
    private View popupViewSex;
    private TextView tv_cancel_popup_sex;
    private TextView tv_yes_popup_sex;
    private TextView tv_boy;
    private TextView tv_girl;
    private TextView complaint_over;
    private String sex = "1";
    private String selectSex;
    private LinearLayout ll_lis;
    private ImageView iv_lis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_submit);

        initView();

    }

    private void initView() {
        ll_lis = (LinearLayout) findViewById(R.id.ll_lis);
        iv_lis = (ImageView) findViewById(R.id.iv_lis);
        llphone = (LinearLayout) findViewById(R.id.ll_phone);
        useroffice = (TextView) findViewById(R.id.user_office);
        llsex = (LinearLayout) findViewById(R.id.ll_sex);
        usersex = (TextView) findViewById(R.id.user_sex);
        llname = (LinearLayout) findViewById(R.id.ll_name);
        username = (TextView) findViewById(R.id.user_name);
        complaint_over = (TextView) findViewById(R.id.complaint_over);
        setToolbarText("更改审核信息");
        service = new UserService(this);
        popupViewSex = LayoutInflater.from(this).inflate(R.layout.popup_change_sex, null);

        hud = KProgressHUD.create(MyInfoSubmitActivity.this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false);
        tv_cancel_popup_sex = (TextView) popupViewSex.findViewById(R.id.tv_cancel_popup);
        tv_yes_popup_sex = (TextView) popupViewSex.findViewById(R.id.tv_yes_popup);
        tv_boy = (TextView) popupViewSex.findViewById(R.id.tv_boy);
        tv_girl = (TextView) popupViewSex.findViewById(R.id.tv_girl);
        tv_boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSex = "1";
                tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
            }
        });
        tv_girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSex = "2";
                tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
                tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
            }
        });
        tv_cancel_popup_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowSex.dismiss();
            }
        });
        tv_yes_popup_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeSex();
            }
        });

        llsex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowSex = getPopwindow(popupViewSex);
            }
        });

        ll_lis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(true)
                        .start(MyInfoSubmitActivity.this, PhotoPicker.REQUEST_CODE);

            }
        });

        complaint_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (photoPath.equals("")) {
                    showShort("请选择照片再点击提交审核");
                } else {
                        hud.show();
                    OkGo.<String>post(Apis.ModifyReviewInfo)
                            .params("lawyerId", service.getLawyerId())
                            .params("lawyerName", username.getText().toString())
                            .params("sex", sex)
                            .params("lisencePic", new File(photoPath))
                            .params("lawyerOfficeName", useroffice.getText().toString())
                            .params("lawyerOfficeTel", lawyerOfficeTel)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    if (hud.isShowing()) hud.dismiss();
                                    ResultData pic = (new Gson()).fromJson(response.body(), ResultData.class);
                                    if (pic.getCode() == 0) {
                                        showShort("提交成功");
                                        finish();
                                    } else {
                                        showShort(pic.getMsg());
                                    }
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    if (hud.isShowing()) hud.dismiss();
                                }
                            });
                }
            }
        });
        initData();
    }

    private void changeSex() {

        OkGo.<String>post(Apis.ChangeSex).params("lawyerId", service.getLawyerId()).params("sex", selectSex).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                popupWindowSex.dismiss();
                ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                if (data.getCode() == 0) {
                    sex = selectSex;
                    if (sex.equals("1")) {
                        usersex.setText("男");
                        tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                        tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
                    } else {
                        usersex.setText("女");
                        tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
                        tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                    }
                } else {
                    showShort(data.getMsg());
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                popupWindowSex.dismiss();
            }
        });
    }


    private void initData() {

        OkGo.<String>get(Apis.GotoModifyReviewInfo).params("lawyerId", service.getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ReviewInfo info = (new Gson()).fromJson(response.body(), ReviewInfo.class);
                        if (info.getCode() == 0) {

                            Picasso.with(getBaseContext()).load(info.getData().getLawyerReviewInfo().getLisencePicURL()).into(iv_lis);
                            username.setText(info.getData().getLawyerReviewInfo().getName());
                            if (info.getData().getLawyerReviewInfo().getSex() == 1) {
                                sex = "1";
                                usersex.setText("男");
                                tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                                tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
                            } else {
                                sex = "2";
                                usersex.setText("女");
                                tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
                                tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                            }
                            selectSex = sex;
                            lawyerOfficeTel = info.getData().getLawyerReviewInfo().getLawyerOfficeTel();
                            useroffice.setText(info.getData().getLawyerReviewInfo().getLawyerOfficeName());
                        } else {
                            startActivity(LoginActivity.class);
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
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
        popupWindow.showAtLocation(llsex, Gravity.BOTTOM, 0, 0);
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

    String photoPath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {

                photoPath = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS).get(0);

                Glide.with(getBaseContext()).load(photoPath).into(iv_lis);
            }
        }
    }

}
