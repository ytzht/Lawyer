package com.onekeyask.lawfirm.ui.act.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.HeaderPic;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseActivity;
import com.onekeyask.lawfirm.ui.act.MainActivity;
import com.onekeyask.lawfirm.utils.UserService;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPicker;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class UploadImgActivity extends BaseActivity {

    private ImageView iv_arrow;
    private CircleImageView upload_img;
    private TextView next_step_three;
    private UserService service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img);

        service = new UserService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在上传...");
        dialog.setCancelable(false);
        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);
        upload_img = (CircleImageView) findViewById(R.id.upload_img);
        next_step_three = (TextView) findViewById(R.id.next_step_three);

        initClick();

    }

    private void initClick() {
        iv_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(OpenServiceActivity.class);
                finish();
            }
        });

        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(true)
                        .start(UploadImgActivity.this, PhotoPicker.REQUEST_CODE);
            }
        });
        next_step_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSubmit();
            }
        });
    }

    private void goSubmit() {


        if (files != null) {
            dialog.show();
            OkGo.<String>get(Apis.firstHome).params("lawyerId", service.getLawyerId()).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);

                    showShort(data.getMsg());
                }
            });

            OkGo.<String>post(Apis.HeadPic)
                    .params("lawyerId", service.getLawyerId())
                    .params("headPic", files)
                    .params("token", service.getToken())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if (dialog.isShowing()) dialog.dismiss();
                            HeaderPic pic = (new Gson()).fromJson(response.body(), HeaderPic.class);
                            if (pic.getCode() == 0) {
                                service.setHeadURL(pic.getData().getHeadUrl());
                                UserService.service(getBaseContext()).setFirstHome(1);
                                startActivity(MainActivity.class);
                                finish();
                            } else {
                                showShort(pic.getMsg());
                            }
                        }
                    });
        }else {
            OkGo.<String>get(Apis.firstHome).params("lawyerId", service.getLawyerId()).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                    startActivity(MainActivity.class);
                    finish();
                    showShort(data.getMsg());
                }
            });
        }

    }

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

    static File files;
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
                        files = file;
                        if (dialog.isShowing()) dialog.dismiss();

                        Picasso.with(getBaseContext()).load(file)
                                .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar).into(upload_img);

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                }).launch();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            startActivity(OpenServiceActivity.class);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

}
