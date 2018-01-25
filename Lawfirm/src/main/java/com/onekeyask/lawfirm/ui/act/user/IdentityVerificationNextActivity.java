package com.onekeyask.lawfirm.ui.act.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;

import java.io.File;

import me.iwf.photopicker.PhotoPicker;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class IdentityVerificationNextActivity extends BaseToolBarActivity {

    private String lawyerOfficeName, lawyerOfficeTel, city, district, lawyerName;
    private ImageView ivpic;
    private TextView tvbefore;
    private TextView tvsubmitid;
    private ProgressDialog dialog;
    private String sex = "1";
    private boolean isEdit = false;
    private String LisencePicURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_verification_next);

        setToolbarText("执照证明图片");

        initView();

        initClick();

        Glide.with(getBaseContext()).load(IdentityVerificationActivity.files).into(ivpic);
    }

    private void initClick() {
        tvsubmitid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(true)
                        .start(IdentityVerificationNextActivity.this, PhotoPicker.REQUEST_CODE);

            }
        });

        tvbefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        this.tvsubmitid = (TextView) findViewById(R.id.tv_submit_id);
        this.tvbefore = (TextView) findViewById(R.id.tv_before);
        this.ivpic = (ImageView) findViewById(R.id.iv_pic);

        lawyerOfficeName = getIntent().getStringExtra("lawyerOfficeName");
        lawyerOfficeTel = getIntent().getStringExtra("lawyerOfficeTel");
        city = getIntent().getStringExtra("city");
        sex = getIntent().getStringExtra("sex");
        district = getIntent().getStringExtra("district");
        lawyerName = getIntent().getStringExtra("lawyerName");

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在上传...");
        dialog.setCancelable(false);
//
//        if (getIntent().hasExtra("isEdit")) {
//            isEdit = true;
//            loadPic();
//        }
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
                        IdentityVerificationActivity.files = file;
                        if (dialog.isShowing()) dialog.dismiss();
                        Glide.with(getBaseContext()).load(IdentityVerificationActivity.files).into(ivpic);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                }).launch();


    }
}
