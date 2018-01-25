package com.onekeyask.lawfirm.ui.act.me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.BaseResult;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.global.L;
import com.onekeyask.lawfirm.http.ProgressSubscriber;
import com.onekeyask.lawfirm.http.SubscriberOnNextListener;
import com.onekeyask.lawfirm.utils.Forms;
import com.onekeyask.lawfirm.utils.UserService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class OpinionActivity extends BaseToolBarActivity {

    private EditText etopinion;
    private android.widget.ImageView addpic;
    private TextView tvaddtxt;
    private android.support.v7.widget.RecyclerView rlvaddpic;
    private EditText etphone;
    private TextView submitopinion;

    private int maxPicNum = 3;
    private ConPhotosAdapter photosAdapter;
    private ArrayList<String> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);

        setToolbarText("意见反馈");

        initView();

    }

    private void initView() {
        this.submitopinion = (TextView) findViewById(R.id.submit_opinion);
        this.etphone = (EditText) findViewById(R.id.et_phone);
        this.rlvaddpic = (RecyclerView) findViewById(R.id.rlv_add_pic);
        this.tvaddtxt = (TextView) findViewById(R.id.tv_add_txt);
        this.addpic = (ImageView) findViewById(R.id.add_pic);
        this.etopinion = (EditText) findViewById(R.id.et_opinion);


        rlvaddpic.setLayoutManager(new GridLayoutManager(this, 4));

        photosAdapter = new ConPhotosAdapter();
        rlvaddpic.setAdapter(photosAdapter);

        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photos.size() < maxPicNum) {
                    PhotoPicker.builder()
                            .setPhotoCount(maxPicNum - photos.size())
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(true)
                            .start(OpinionActivity.this, PhotoPicker.REQUEST_CODE);

                } else {
                    showShort("最多上传" + maxPicNum + "张图片");
                }
            }
        });

        submitopinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMsg();
            }
        });

    }


    private ProgressDialog progressDialog;
    private void checkMsg() {
        photoMap.clear();
        i = 0;
        k = 0;
        if (etopinion.getText().toString().equals("")) {
            showShort("请输入投诉内容");
            return;
        } else if (etopinion.getText().toString().length() < 10) {
            showShort("最少输入十个字符");
            return;
        }

        if (photos.size() > 0) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在压缩图片...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            i = 0;
            for (int j = 0; j < photos.size(); j++) {

                goLuban();

            }
        }else {
            goSubmit();
        }
    }

    private Map<String, RequestBody> photoMap = new HashMap<>();
    private int i = 0;
    private int k = 0;
    private void goLuban() {
        i += 1;
        File file = new File(photos.get(i - 1));
        Luban.get(this)
                .load(file)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        k += 1;
                        L.d(k+"");
                        String key = "pic" + k + "\"; filename=\"pic" + k + photos.get(k - 1).substring(photos.get(k - 1).lastIndexOf("."));
                        photoMap.put(key, RequestBody.create(MediaType.parse("multipart/form-data"), file));
                        L.d(key);

                        L.d("photoMap.size() " + photoMap.size() + " photos.size() " + photos.size());
                        if (photoMap.size() == photos.size()) {
                            if (progressDialog.isShowing()) progressDialog.dismiss();
                            goSubmit();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e(e.toString());
                        if (progressDialog.isShowing()) progressDialog.dismiss();
                        showShort("第" + i + "张图片压缩出错");
                    }
                }).launch();    //启动压缩
    }
    private void goSubmit() {

        if (!etphone.getText().toString().equals("")) {
            if (Forms.disValid(etphone.getText().toString(), Forms.PHONENUM)) {
                showShort("手机号码格式有误，请检查");
                return;
            } else {
                //手机号对了
                photoMap.put("phoneNum", RequestBody.create(null, etphone.getText().toString()));
            }
        }

        photoMap.put("lawyerId", RequestBody.create(null, UserService.service(getBaseContext()).getLawyerId()+""));
        photoMap.put("advise", RequestBody.create(null, etopinion.getText().toString()));

        getResultOnNext = new SubscriberOnNextListener<BaseResult>() {
            @Override
            public void onNext(BaseResult baseResult) {
                if (baseResult.getCode() == 0){

                    Intent intent = new Intent(OpinionActivity.this, SubmitOpinionActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    showShort(baseResult.getMsg());
                }


            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getSubmitadvice(photoMap, new ProgressSubscriber<BaseResult>(getResultOnNext, OpinionActivity.this, true));

    }
    private class ConPhotosAdapter extends RecyclerView.Adapter<ConPhotosAdapter.PhotosViewHolder> {


        @Override
        public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(OpinionActivity.this).inflate(R.layout.item_con_photos, parent, false);
            return new PhotosViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotosViewHolder holder, final int position) {


            if (position == photos.size()) {
                holder.photo_iv.setImageResource(R.drawable.add_pic);
                holder.close.setVisibility(View.GONE);
            } else {
                Glide.with(OpinionActivity.this).load(new File(photos.get(position))).into(holder.photo_iv);
                holder.close.setVisibility(View.VISIBLE);

                holder.photo_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
                holder.close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != photos.size()) {
                            removeData(position);
                        }
                    }
                });
            }


            holder.photo_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    L.d("这是图片" + position);
                    if (position == photos.size()) {
                        if (photos.size() < maxPicNum) {
                            PhotoPicker.builder()
                                    .setPhotoCount(maxPicNum - photos.size())
                                    .setShowCamera(true)
                                    .setShowGif(true)
                                    .setPreviewEnabled(true)
                                    .start(OpinionActivity.this, PhotoPicker.REQUEST_CODE);

                        } else {
                            showShort("最多上传" + maxPicNum + "张图片");
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return photos.size() + 1;
        }

        private void removeData(int position) {
            if (position != photos.size()) {
                photos.remove(position);
                L.d("removeData: photos.size() " + photos.size());
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, photos.size() - position + 1);
            }

        }

        class PhotosViewHolder extends RecyclerView.ViewHolder {

            private ImageView photo_iv, close;

            public PhotosViewHolder(View itemView) {
                super(itemView);
                photo_iv = (ImageView) itemView.findViewById(R.id.photo_iv);
                close = (ImageView) itemView.findViewById(R.id.close);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                photos.addAll(data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS));
                photosAdapter.notifyDataSetChanged();
            }
            if (photos.size() > 0) {
                addpic.setVisibility(View.GONE);
                tvaddtxt.setVisibility(View.GONE);
                rlvaddpic.setVisibility(View.VISIBLE);
                for (int i = 0; i < photos.size(); i++) {
                    L.d(photos.get(i));
                }
            } else {
                L.d("null");
            }
        }
    }
}
