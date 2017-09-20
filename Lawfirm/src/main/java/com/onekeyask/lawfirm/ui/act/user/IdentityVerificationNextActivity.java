package com.onekeyask.lawfirm.ui.act.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.http.ProgressSubscriber;
import com.onekeyask.lawfirm.http.SubscriberOnNextListener;
import com.onekeyask.lawfirm.utils.UserService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class IdentityVerificationNextActivity extends BaseToolBarActivity {

    private String lawyerOfficeName, lawyerOfficeTel, city, district, lawyerName;
    private ImageView ivpic;
    private TextView tvbefore;
    private TextView tvsubmitid;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_verification_next);

        setToolbarText("身份审核");

        initView();

        initClick();

    }

    private void initClick() {
        ivpic.setOnClickListener(new View.OnClickListener() {
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

        tvsubmitid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSubmit();
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
        district = getIntent().getStringExtra("district");
        lawyerName = getIntent().getStringExtra("lawyerName");

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在上传...");
        dialog.setCancelable(false);

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

    File files;
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
                        Glide.with(getBaseContext()).load(files).into(ivpic);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                }).launch();


    }

    private void goSubmit() {
        if (files == null){
            showShort("请添加律师资格证照片");
            return;
        }

        Map<String, RequestBody> map = new HashMap<>();
        map.clear();
        map.put("lawyerId", RequestBody.create(null, UserService.service(getBaseContext()).getLawyerId()+""));
        map.put("lawyerOfficeName", RequestBody.create(null, lawyerOfficeName));
        map.put("lawyerOfficeTel", RequestBody.create(null, lawyerOfficeTel));
        map.put("city", RequestBody.create(null, city));
        map.put("district", RequestBody.create(null, district));
        map.put("token", RequestBody.create(null, UserService.service(getBaseContext()).getToken()));
        map.put("lawyerName", RequestBody.create(null, lawyerName));
        String key = "lisencePic\"; filename=\"lisencePic";
        map.put(key, RequestBody.create(MediaType.parse("multipart/form-data"), files));

        SubscriberOnNextListener<ResultData> listener = new SubscriberOnNextListener<ResultData>() {
            @Override
            public void onNext(ResultData result) {

                UserService.service(getBaseContext()).setOfficeName(lawyerOfficeName);
                if (result.getCode() == 0){
                    startActivity(GotoVerifyActivity.class);
                }else if (result.getCode() == -101){
                    showShort("已经提交审核");
                }else {
                    showShort(result.getMsg());
                }


            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        retrofitUtil.gotoVerify(map, new ProgressSubscriber<ResultData>(listener, IdentityVerificationNextActivity.this, true));
    }
}
