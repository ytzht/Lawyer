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

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.CityList;
import com.onekeyask.lawfirm.entity.GetSpecialInfoList;
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
    @BindView(R.id.tv_introduce)
    TextView tv_intro;
    @BindView(R.id.ll_sex)
    LinearLayout ll_sex;
    @BindView(R.id.ll_area)
    LinearLayout ll_area;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_sex)
    TextView user_sex;
    @BindView(R.id.user_area)
    TextView user_area;
    @BindView(R.id.user_office)
    TextView user_office;
    @BindView(R.id.civ_head)
    CircleImageView civ_head;
    private RecyclerView rlvcan;
    private UserService service;
    private PopupWindow popupWindow = null;
    private PopupWindow popupWindowSex = null;
    private View popupView;
    private View popupViewSex;
    private TextView tv_cancel_popup;
    private TextView tv_yes_popup;
    private TextView tv_cancel_popup_sex;
    private TextView tv_yes_popup_sex;
    private TextView tv_boy;
    private TextView tv_girl;
    private EditText et_intro;
    private int lawyerId;
    private ConTagAdapter tagAdapter;
    private List<GetSpecialInfoList.DataBean.SpecialListBean> beanList = new ArrayList<>();
    private String sex = "1";
    private String selectSex;

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

        popupView = LayoutInflater.from(this).inflate(R.layout.popup_change_name, null);
        popupViewSex = LayoutInflater.from(this).inflate(R.layout.popup_change_sex, null);
        rlvcan = (RecyclerView) findViewById(R.id.rlv_can);
        rlvcan.setLayoutManager(new GridLayoutManager(this, 4));
        tv_cancel_popup = (TextView) popupView.findViewById(R.id.tv_cancel_popup);
        tv_yes_popup = (TextView) popupView.findViewById(R.id.tv_yes_popup);
        tv_cancel_popup_sex = (TextView) popupViewSex.findViewById(R.id.tv_cancel_popup);
        tv_yes_popup_sex = (TextView) popupViewSex.findViewById(R.id.tv_yes_popup);
        tv_boy = (TextView) popupViewSex.findViewById(R.id.tv_boy);
        tv_girl = (TextView) popupViewSex.findViewById(R.id.tv_girl);
        et_intro = (EditText) popupView.findViewById(R.id.et_name);
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
        tv_cancel_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
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
        lawyerId = UserService.service(getBaseContext()).getLawyerId();


        tv_yes_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String introduce = et_intro.getText().toString();
                if (introduce.length() > 5) {
                    OkGo.<String>post(Apis.SaveIntroduce).params("lawyerId", service.getLawyerId()).params("introduce", introduce).execute(new StringCallback() {
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
                } else {
                    showShort("不能少于五个字符");
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
                        user_sex.setText("男");
                        tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                        tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
                    } else {
                        user_sex.setText("女");
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


    @OnClick({R.id.ll_image, R.id.tv_introduce, R.id.ll_sex, R.id.ll_area})
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
            case R.id.ll_area:
                initOptionPicker();
                break;

            case R.id.ll_sex:
                popupWindowSex = getPopwindow(popupViewSex);
                break;
        }
    }

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private String city = "", district = "";
    private void getOptionData() {

        String json = AssetsUtils.readText(getBaseContext(), "city.json");
        List<CityList> cityList = new Gson().fromJson(json, new TypeToken<ArrayList<CityList>>(){}.getType());
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
                                    if (data.getCode() != 0) {
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
                        if (list.getCode() == 0) {
                            beanList = list.getData().getSpecialList();
                            tagAdapter = new ConTagAdapter();
                            rlvcan.setAdapter(tagAdapter);
                        }


                    }
                });

        OkGo.<String>get(Apis.getPersonalInfo).params("lawyerId", service.getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        PersonalInfo info = (new Gson()).fromJson(response.body(), PersonalInfo.class);
                        if (info.getCode() == 0) {

                            userName.setText(info.getData().getLawyerName());
                            Picasso.with(getBaseContext()).load(info.getData().getHeadURL())
                                    .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                                    .into(civ_head);
                            if (info.getData().getSex() == 1) {
                                sex = "1";
                                user_sex.setText("男");
                                tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                                tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
                            } else {
                                sex = "2";
                                user_sex.setText("女");
                                tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));
                                tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
                            }
                            selectSex = sex;
                            user_area.setText(info.getData().getCity() + "-" + info.getData().getDistrict());
                            tv_intro.setText(info.getData().getIntroduce());
                            et_intro.setText(info.getData().getIntroduce());
                            user_office.setText(info.getData().getOfficeName());
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
