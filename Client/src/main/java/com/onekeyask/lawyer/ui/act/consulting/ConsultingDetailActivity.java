package com.onekeyask.lawyer.ui.act.consulting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.FreeAskCategory;
import com.onekeyask.lawyer.global.BaseEvent;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.utils.HideUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

public class ConsultingDetailActivity extends BaseToolBarActivity {


    @BindView(R.id.et_con_desc)
    EditText etConDesc;
    @BindView(R.id.rlv_con_tag)
    RecyclerView rlvConTag;
    @BindView(R.id.rlv_photos_free)
    RecyclerView rlv_photos_free;
    @BindView(R.id.iv_input_bottom)
    ImageView ivInputBottom;
    @BindView(R.id.iv_voice_bottom)
    ImageView ivVoiceBottom;
    @BindView(R.id.iv_photo_bottom)
    ImageView ivPhotoBottom;

    private ArrayList<String> photos;
    private ConPhotosAdapter photosAdapter;
    private ConTagAdapter tagAdapter;
    private int categoryId = -1;
    //    private String payType, money;
    private int maxPicNum = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_quick_consulting);
        HideUtil.init(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setToolbarText("快速咨询");

//        payType = getIntent().getStringExtra("payType");
//        money = getIntent().getStringExtra("money");

        photos = new ArrayList<>();

        rlvConTag.setLayoutManager(new GridLayoutManager(this, 4));
        rlv_photos_free.setLayoutManager(new GridLayoutManager(this, 4));
        photosAdapter = new ConPhotosAdapter();
        rlv_photos_free.setAdapter(photosAdapter);


        initData();
    }

    private void initData() {

        SubscriberOnNextListener<FreeAskCategory> listener = new SubscriberOnNextListener<FreeAskCategory>() {
            @Override
            public void onNext(FreeAskCategory category) {
                List<FreeAskCategory.AllSpecialBean> beanList = category.getAllSpecial();
                beanList.get(0).setCheck(true);
                tagAdapter = new ConTagAdapter(beanList);
                categoryId = beanList.get(0).getId();
                rlvConTag.setAdapter(tagAdapter);
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };


        retrofitUtil.getTags(new ProgressSubscriber<FreeAskCategory>(listener, ConsultingDetailActivity.this, false));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.next_step) {
            Intent intent = new Intent(ConsultingDetailActivity.this, QuickConsultingActivity.class);


            if (etConDesc.getText().toString().equals("")) {
                showShort("请输入您想要咨询的问题");

            } else if (etConDesc.getText().toString().length() < 10) {

                showShort("最少输入十个字符");
            } else {
                intent.putExtra("content", etConDesc.getText().toString());
                intent.putExtra("category", categoryId);
                intent.putStringArrayListExtra("photos", photos);

                startActivity(intent);
            }
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);

        if (event.getCode() == BaseEvent.GO_SERVICE) {
            finish();
        }
    }

    private AlertDialog dialog;
    @OnClick({R.id.iv_input_bottom, R.id.iv_voice_bottom, R.id.iv_photo_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_input_bottom:
                etConDesc.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.iv_voice_bottom:

//                View view1 = LayoutInflater.from(this).inflate(R.layout.custom_dialog_share, null, false);
//                dialog = new AlertDialog.Builder(this).setView(view1).setCancelable(false).show();
//
//                view1.findViewById(R.id.tv_share_con).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });

                break;
            case R.id.iv_photo_bottom:
                if (photos.size() < maxPicNum) {
                    PhotoPicker.builder()
                            .setPhotoCount(maxPicNum - photos.size())
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(true)
                            .start(ConsultingDetailActivity.this, PhotoPicker.REQUEST_CODE);

                } else {
                    showShort("最多上传" + maxPicNum + "张图片");
                }
                break;
        }
    }


    private class ConTagAdapter extends RecyclerView.Adapter<ConTagAdapter.ViewHolder> {

        private List<FreeAskCategory.AllSpecialBean> beanList = new ArrayList<>();

        public ConTagAdapter(List<FreeAskCategory.AllSpecialBean> beanList) {
            this.beanList = beanList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_con_tag, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tv_tag_text.setText(beanList.get(position).getName());

            if (beanList.get(position).isCheck()) {
                holder.tv_tag_text.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                holder.tv_tag_text.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
            } else {
                holder.tv_tag_text.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_unselect));
                holder.tv_tag_text.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
            }

            holder.tv_tag_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (beanList.get(position).isCheck()) {
//                        beanList.get(position).setCheck(false);
//                        categoryId = -1;
//                        tagAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < beanList.size(); i++) {
                            beanList.get(i).setCheck(false);
                        }
                        categoryId = beanList.get(position).getId();
                        beanList.get(position).setCheck(true);
                        tagAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return beanList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_tag_text;

            ViewHolder(View itemView) {
                super(itemView);
                tv_tag_text = (TextView) itemView.findViewById(R.id.tv_tag_text);
            }
        }

    }


    private class ConPhotosAdapter extends RecyclerView.Adapter<PhotosViewHolder> {


        @Override
        public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ConsultingDetailActivity.this).inflate(R.layout.item_con_photos, parent, false);
            return new PhotosViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotosViewHolder holder, final int position) {
            Glide.with(ConsultingDetailActivity.this).load(new File(photos.get(position))).into(holder.photo_iv);
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

        @Override
        public int getItemCount() {
            return photos.size();
        }

        private void removeData(int position) {
            if (position != photos.size()) {
                photos.remove(position);
                L.d("removeData: photos.size() " + photos.size());
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, photos.size() - position + 1);
            }

        }


    }

    private class PhotosViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo_iv, close;

        public PhotosViewHolder(View itemView) {
            super(itemView);
            photo_iv = (ImageView) itemView.findViewById(R.id.photo_iv);
            close = (ImageView) itemView.findViewById(R.id.close);
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
