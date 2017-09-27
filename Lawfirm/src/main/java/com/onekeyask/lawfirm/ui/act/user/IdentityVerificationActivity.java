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
import com.onekeyask.lawfirm.entity.ProvinceBean;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.utils.AssetsUtils;
import com.onekeyask.lawfirm.utils.UserService;

import java.util.ArrayList;
import java.util.List;


public class IdentityVerificationActivity extends BaseToolBarActivity {

    private EditText etname;
    private TextView tvarea;
    private LinearLayout llarea;
    private LinearLayout ll_sex;
    private EditText etoffice;
    private EditText etphone;
    private RecyclerView rlvcan;
    private TextView nextsubmit;
    private TextView tv_sex;
    private ConTagAdapter tagAdapter;
    private int lawyerId;
    private UserService service;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private PopupWindow popupWindowSex = null;
    private View popupViewSex;
    private TextView tv_cancel_popup_sex;
    private TextView tv_yes_popup_sex;
    private TextView tv_boy;
    private TextView tv_girl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_verification);

        setToolbarText("身份审核");

        service = new UserService(getBaseContext());

        initView();

        initData();

        initClick();

        getOptionData();
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
    private String sex = "";
    private String selectSex;
    private void initClick() {
        selectSex = "1";
        tv_boy.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.burro_primary_ext));
        tv_girl.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackModule));

        llarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initOptionPicker();

            }
        });

        ll_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowSex = getPopwindow(popupViewSex);
            }
        });
        nextsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInfo();
            }
        });

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
        tv_cancel_popup_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowSex.dismiss();
            }
        });
        tv_yes_popup_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowSex.dismiss();
                sex = selectSex;
                if (sex.equals("1")){
                    tv_sex.setText("男");
                }else {
                    tv_sex.setText("女");
                }

            }
        });
    }



    private void checkInfo() {

        if (etname.getText().toString().equals("")) {
            showShort("姓名不能为空");
            return;
        }

        if (etoffice.getText().toString().equals("")) {
            showShort("律所名不能为空");
            return;
        }

        if (tvarea.getText().toString().equals("")) {
            showShort("请选择所在地区");
            return;
        }

        if (!etphone.getText().toString().equals("") & etphone.getText().toString().length() != 11) {
            showShort("请输入正确的电话号码");
            return;
        }

        if (tv_sex.getText().toString().equals("")){
            showShort("请选择性别");
            return;
        }


        Intent intent = new Intent(IdentityVerificationActivity.this, IdentityVerificationNextActivity.class);
        intent.putExtra("lawyerOfficeName", etoffice.getText().toString());
        intent.putExtra("lawyerOfficeTel", etphone.getText().toString());
        intent.putExtra("city", city);
        intent.putExtra("sex", sex);
        intent.putExtra("district", district);
        intent.putExtra("lawyerName", etname.getText().toString());
        startActivity(intent);

        finish();

    }

    private void initView() {
        this.nextsubmit = (TextView) findViewById(R.id.next_submit);
        this.rlvcan = (RecyclerView) findViewById(R.id.rlv_can);
        this.etphone = (EditText) findViewById(R.id.et_phone);
        this.etoffice = (EditText) findViewById(R.id.et_office);
        this.llarea = (LinearLayout) findViewById(R.id.ll_area);
        this.ll_sex = (LinearLayout) findViewById(R.id.ll_sex);
        this.tvarea = (TextView) findViewById(R.id.tv_area);
        this.tv_sex = (TextView) findViewById(R.id.tv_sex);
        this.etname = (EditText) findViewById(R.id.et_name);

        rlvcan.setLayoutManager(new GridLayoutManager(this, 4));

        lawyerId = service.getLawyerId();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在设置...");

        popupViewSex = LayoutInflater.from(this).inflate(R.layout.popup_change_sex, null);
        tv_cancel_popup_sex = (TextView) popupViewSex.findViewById(R.id.tv_cancel_popup);
        tv_yes_popup_sex = (TextView) popupViewSex.findViewById(R.id.tv_yes_popup);
        tv_boy = (TextView) popupViewSex.findViewById(R.id.tv_boy);
        tv_girl = (TextView) popupViewSex.findViewById(R.id.tv_girl);


    }

    private ProgressDialog dialog;

    private List<GetSpecialInfoList.DataBean.SpecialListBean> beanList = new ArrayList<>();
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

    private String city = "", district = "";
    private void initOptionPicker() {//条件选择器初始化
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                city = options1Items.get(options1).getPickerViewText();
                district = options2Items.get(options1).get(options2);
                tvarea.setText(city + "-" + district);

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
        popupWindow.showAtLocation(ll_sex, Gravity.BOTTOM, 0, 0);
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
