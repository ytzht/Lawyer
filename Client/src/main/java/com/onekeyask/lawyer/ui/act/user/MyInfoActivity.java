package com.onekeyask.lawyer.ui.act.user;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.HeaderPic;
import com.onekeyask.lawyer.entity.ResultData;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.utils.UserService;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPicker;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class MyInfoActivity extends BaseToolBarActivity {

    @BindView(R.id.ll_image)
    LinearLayout llImage;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.civ_head)
    CircleImageView civ_head;

    private UserService service;
    private PopupWindow popupWindow = null;
    private View popupView;
    private TextView tv_cancel_popup;
    private TextView tv_yes_popup;
    private EditText et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        setToolbarText("个人信息");

        service = new UserService(this);

        userName.setText(service.getUserName());
        userPhone.setText(service.getPhone());
        L.d(service.getHeadURL());
        if (service.getHeadURL().equals("")){
            civ_head.setImageResource(R.drawable.ic_member_avatar);
        }else {
            Picasso.with(this).load(service.getHeadURL())
                    .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar).into(civ_head);
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在上传...");
        dialog.setCancelable(false);

        popupView = LayoutInflater.from(this).inflate(R.layout.popup_change_name, null);

        tv_cancel_popup = (TextView) popupView.findViewById(R.id.tv_cancel_popup);
        tv_yes_popup = (TextView) popupView.findViewById(R.id.tv_yes_popup);
        et_name = (EditText) popupView.findViewById(R.id.et_name);
        et_name.setText(service.getUserName());
        tv_cancel_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        tv_yes_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = et_name.getText().toString();
                if (name.length() > 5 && name.length() < 13) {
                    OkGo.<String>post(Apis.ChangeName).params("userId", service.getUserId()).params("name", name).execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                            if (data.getCode() == 0) {
                                service.setUserName(name);
                                userName.setText(service.getUserName());
                                showShort("修改成功");
                                popupWindow.dismiss();

                            } else {
                                showShort(data.getMsg());
                            }
                        }
                    });
                }else {
                    showShort("长度限制：6-12个字符");
                }

            }
        });
    }


    @OnClick({R.id.ll_image, R.id.ll_name, R.id.ll_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_image:
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(true)
                        .start(MyInfoActivity.this, PhotoPicker.REQUEST_CODE);
                break;
            case R.id.ll_name:

                popupWindow = getPopwindow(popupView);

                break;
            case R.id.ll_phone:
                break;
        }
    }

    private ProgressDialog dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {

                String photoPath = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS).get(0);

                goLuban(photoPath);
            }
        }
    }

    private void goLuban(String photoPath) {

        Luban.get(this)
                .load(new File(photoPath))                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        dialog.show();
                    }

                    @Override
                    public void onSuccess(File file) {
                        goSubmit(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                }).launch();


    }

    private void goSubmit(File file) {
        OkGo.<String>post(Apis.HeadPic)
                .params("userId", service.getUserId())
                .params("headPic", file)
                .params("token", service.getToken())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (dialog.isShowing()) dialog.dismiss();
                        HeaderPic pic = (new Gson()).fromJson(response.body(), HeaderPic.class);
                        if (pic.getCode() == 0) {
                            service.setHeadURL(pic.getData().getHeadUrl());
                            if (service.getHeadURL().equals("")){
                                civ_head.setImageResource(R.drawable.ic_member_avatar);
                            }else {
                                Picasso.with(getBaseContext()).load(service.getHeadURL())
                                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar).into(civ_head);
                            }
                        } else {
                            showShort(pic.getMsg());
                        }
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
        popupWindow.showAtLocation(llName, Gravity.BOTTOM, 0, 0);
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
