package com.onekeyask.lawfirm.ui.act.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.GetSpecialInfoList;
import com.onekeyask.lawfirm.entity.HeaderPic;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.global.L;
import com.onekeyask.lawfirm.ui.act.Apis;
import com.onekeyask.lawfirm.utils.UserService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tv_introduce)
    TextView tv_intro;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.civ_head)
    CircleImageView civ_head;
    private RecyclerView rlvcan;
    private UserService service;
    private PopupWindow popupWindow = null;
    private View popupView;
    private TextView tv_cancel_popup;
    private TextView tv_yes_popup;
    private EditText et_intro;
    private int lawyerId;
    private ConTagAdapter tagAdapter;
    private List<GetSpecialInfoList.DataBean.SpecialListBean> beanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        setToolbarText("个人信息");

        service = new UserService(this);

        userName.setText(service.getUserName());
        tv_intro.setText(service.getIntroduce());

        userPhone.setText(service.getPhone());
        L.d(service.getHeadURL());
        if (service.getHeadURL().equals("")){
            civ_head.setImageResource(R.drawable.ic_member_avatar);
        }else {
            Glide.with(this).load(service.getHeadURL()).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(civ_head);
        }
//        Picasso.with(this).load(service.getHeadURL()).into(civ_head);

        dialog = new ProgressDialog(this);
        dialog.setMessage("正在上传...");
        dialog.setCancelable(false);

        popupView = LayoutInflater.from(this).inflate(R.layout.popup_change_name, null);
        rlvcan = (RecyclerView) findViewById(R.id.rlv_can);
        rlvcan.setLayoutManager(new GridLayoutManager(this, 4));
        tv_cancel_popup = (TextView) popupView.findViewById(R.id.tv_cancel_popup);
        tv_yes_popup = (TextView) popupView.findViewById(R.id.tv_yes_popup);
        et_intro = (EditText) popupView.findViewById(R.id.et_name);
        et_intro.setText(service.getIntroduce());
        tv_cancel_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        lawyerId = UserService.service(getBaseContext()).getLawyerId();



        tv_yes_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String introduce = et_intro.getText().toString();
                if (introduce.length() > 5 ) {
                    OkGo.<String>get(Apis.SaveIntroduce).params("lawyerId", service.getLawyerId()).params("introduce", introduce).execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                            if (data.getCode() == 0) {
                                service.setIntroduce(introduce);
                                tv_intro.setText(service.getIntroduce());
                                showShort("修改成功");
                                popupWindow.dismiss();

                            } else {
                                showShort(data.getMsg());
                            }
                        }
                    });
                }else {
                    showShort("不能少于五个字符");
                }

            }
        });

        initData();

    }


    @OnClick({R.id.ll_image, R.id.tv_introduce, R.id.ll_phone})
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
            case R.id.tv_introduce:

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
                .params("lawyerId", service.getLawyerId())
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
                                Glide.with(getBaseContext()).load(service.getHeadURL()).skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(civ_head);
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
        popupWindow.showAtLocation(tv_intro, Gravity.BOTTOM, 0, 0);
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
    private class ConTagAdapter extends RecyclerView.Adapter<ConTagAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_con_tag, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tv_tag_text.setText(beanList.get(position).getSpecialName());

            if (beanList.get(position).isIsSelected()) {
                holder.tv_tag_text.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                holder.tv_tag_text.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
            } else {
                holder.tv_tag_text.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_unselect));
                holder.tv_tag_text.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
            }

            holder.tv_tag_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!dialog.isShowing()) dialog.show();
                    OkGo.<String>get(Apis.SaveSpecialService)
                            .params("lawyerId", lawyerId)
                            .params("specialId", beanList.get(position).getSpecialId())
                            .params("isSelected", !beanList.get(position).isIsSelected())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    initData();
                                    if (dialog.isShowing()) dialog.dismiss();

                                    ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                                    if(data.getCode() != 0){
                                        showShort(data.getMsg());
                                    }
                                }
                            });


//                    if (beanList.get(position).isIsSelected()) {
//                        beanList.get(position).setIsSelected(false);
//                        tagAdapter.notifyDataSetChanged();
//                    } else {
//
//                        int j = 0;
//                        for (int i = 0; i < beanList.size(); i++) {
//                            if (beanList.get(i).isIsSelected()) {
//                                j++;
//                            }
//                        }
//
//                        if (j >= 5) {
//                            showShort("最多选择5个");
//                        } else {
//                            beanList.get(position).setIsSelected(true);
//                            tagAdapter.notifyDataSetChanged();
//                        }
//
//                    }
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

    private void initData() {
        OkGo.<String>get(Apis.GetSpecialInfoList)
                .params("lawyerId", service.getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        GetSpecialInfoList list = (new Gson()).fromJson(response.body(), GetSpecialInfoList.class);
                        if (list.getCode() == 0){
                            beanList = list.getData().getSpecialList();
                            tagAdapter = new ConTagAdapter();
                            rlvcan.setAdapter(tagAdapter);
                        }


                    }
                });

    }

}
