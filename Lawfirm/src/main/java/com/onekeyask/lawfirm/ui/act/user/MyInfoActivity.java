package com.onekeyask.lawfirm.ui.act.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.CityList;
import com.onekeyask.lawfirm.entity.HeaderPic;
import com.onekeyask.lawfirm.entity.PersonalInfo;
import com.onekeyask.lawfirm.entity.ProvinceBean;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.global.L;
import com.onekeyask.lawfirm.utils.AssetsUtils;
import com.onekeyask.lawfirm.utils.UserService;
import com.squareup.picasso.Picasso;

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
    @BindView(R.id.ll_area)
    LinearLayout ll_area;
    @BindView(R.id.ll_intro)
    LinearLayout ll_intro;
    @BindView(R.id.ll_special)
    LinearLayout ll_special;
    @BindView(R.id.ll_info)
    LinearLayout ll_info;
    @BindView(R.id.user_area)
    TextView user_area;
    @BindView(R.id.civ_head)
    CircleImageView civ_head;
    private UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        setToolbarText("个人信息");

        service = new UserService(this);
        getOptionData();
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在上传...");
        dialog.setCancelable(false);
        initData();
    }


    @OnClick({R.id.ll_image, R.id.ll_area, R.id.ll_special, R.id.ll_intro, R.id.ll_info})
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
            case R.id.ll_special:
                startActivity(MySpecialActivity.class);
                break;
            case R.id.ll_area:
                initOptionPicker();
                break;
            case R.id.ll_intro:
                startActivity(MyIntroduceActivity.class);
                break;
            case R.id.ll_info:
                startActivity(MyInfoSubmitActivity.class);
                break;
        }
    }

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private String city = "", district = "";

    private void getOptionData() {

        String json = AssetsUtils.readText(getBaseContext(), "city.json");
        List<CityList> cityList = new Gson().fromJson(json, new TypeToken<ArrayList<CityList>>() {
        }.getType());
        for (int i = 0; i < cityList.size(); i++) {
            options1Items.add(new ProvinceBean(i, cityList.get(i).getAreaName(), "描述部分", "其他数据"));
            ArrayList<String> options2Items_0 = new ArrayList<>();
            for (int j = 0; j < cityList.get(i).getCities().size(); j++) {

                options2Items_0.add(cityList.get(i).getCities().get(j).getAreaName());
            }
            options2Items.add(options2Items_0);
        }
    }

    private void initOptionPicker() {//条件选择器初始化
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                city = options1Items.get(options1).getPickerViewText();
                district = options2Items.get(options1).get(options2);
                changeArea(city, district);

            }
        })
                .setTitleText("所在地区选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .build();

        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }

    private void changeArea(final String city, final String district) {
        OkGo.<String>post(Apis.changeCityDistrict).params("lawyerId", service.getLawyerId())
                .params("city", city)
                .params("district", district)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ResultData data = (new Gson()).fromJson(response.body(), ResultData.class);
                        if (data.getCode() == 0) {
                            user_area.setText(city + "-" + district);
                        } else {
                            showShort(data.getMsg());
                        }
                    }
                });
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

    private void goSubmit(final File file) {
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
                            if (service.getHeadURL().equals("")) {
                                L.d("onSuccess: getHeadURL");
                                civ_head.setImageResource(R.drawable.ic_member_avatar);
                            } else {
                                L.d("onSuccess: getHeadUrl " + pic.getData().getHeadUrl());
                                Picasso.with(getBaseContext()).load(file)
                                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar).into(civ_head);
                            }
                        } else {
                            showShort(pic.getMsg());
                        }
                    }
                });
    }


    private void initData() {

        OkGo.<String>get(Apis.getPersonalInfo).params("lawyerId", service.getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        PersonalInfo info = (new Gson()).fromJson(response.body(), PersonalInfo.class);
                        if (info.getCode() == 0) {
                            if (info.getData().getHeadURL().equals("")) {
                                Glide.with(getBaseContext()).load(info.getData().getHeadURL())
                                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar).into(civ_head);
                            }else {
                                Picasso.with(getBaseContext()).load(info.getData().getHeadURL())
                                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar).into(civ_head);
                            }
                            user_area.setText(info.getData().getCity() + "-" + info.getData().getDistrict());
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

}
