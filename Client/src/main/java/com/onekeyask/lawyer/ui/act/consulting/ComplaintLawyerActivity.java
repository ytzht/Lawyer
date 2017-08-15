package com.onekeyask.lawyer.ui.act.consulting;

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
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.L;

import java.io.File;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;

public class ComplaintLawyerActivity extends BaseToolBarActivity {

    private EditText etcontent;
    private ImageView addpic;
    private TextView tvaddtxt;
    private RecyclerView rlvaddpic;
    private EditText etphone;
    private TextView submitcomplaint;

    private int maxPicNum = 3;
    private ConPhotosAdapter photosAdapter;
    private ArrayList<String> photos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_lawyer);

        initView();


        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photos.size() < maxPicNum) {
                    PhotoPicker.builder()
                            .setPhotoCount(maxPicNum - photos.size())
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(true)
                            .start(ComplaintLawyerActivity.this, PhotoPicker.REQUEST_CODE);

                } else {
                    showShort("最多上传" + maxPicNum + "张图片");
                }
            }
        });


    }

    private void initView() {
        this.submitcomplaint = (TextView) findViewById(R.id.submit_complaint);
        this.etphone = (EditText) findViewById(R.id.et_phone);
        this.rlvaddpic = (RecyclerView) findViewById(R.id.rlv_add_pic);
        this.tvaddtxt = (TextView) findViewById(R.id.tv_add_txt);
        this.addpic = (ImageView) findViewById(R.id.add_pic);
        this.etcontent = (EditText) findViewById(R.id.et_content);

        rlvaddpic.setLayoutManager(new GridLayoutManager(this, 4));

        photosAdapter = new ConPhotosAdapter();
        rlvaddpic.setAdapter(photosAdapter);


    }


    private class ConPhotosAdapter extends RecyclerView.Adapter<ConPhotosAdapter.PhotosViewHolder> {


        @Override
        public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ComplaintLawyerActivity.this).inflate(R.layout.item_con_photos, parent, false);
            return new PhotosViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotosViewHolder holder, final int position) {


            if (position == photos.size()){
                holder.photo_iv.setImageResource(R.drawable.add_pic);
                holder.close.setVisibility(View.GONE);
            }else {
                Glide.with(ComplaintLawyerActivity.this).load(new File(photos.get(position))).into(holder.photo_iv);
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
                if (photos.size() > 0) {
                    addpic.setVisibility(View.GONE);
                    tvaddtxt.setVisibility(View.GONE);
                    for (int i = 0; i < photos.size(); i++) {
                        L.d(photos.get(i));
                    }
                } else {
                    L.d("null");
                }
            }
        }
    }
}
