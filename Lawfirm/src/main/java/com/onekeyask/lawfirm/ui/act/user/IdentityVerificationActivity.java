package com.onekeyask.lawfirm.ui.act.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.ui.act.Apis;
import com.onekeyask.lawfirm.utils.AssetsUtils;
import com.onekeyask.lawfirm.utils.UserService;

import java.util.ArrayList;
import java.util.List;


public class IdentityVerificationActivity extends BaseToolBarActivity {

    private EditText etname;
    private TextView tvarea;
    private LinearLayout llarea;
    private EditText etoffice;
    private EditText etphone;
    private RecyclerView rlvcan;
    private TextView nextsubmit;
    private ConTagAdapter tagAdapter;
    private int lawyerId;
    private UserService service;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

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

    private void initClick() {
        llarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initOptionPicker();

            }
        });


        nextsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInfo();
            }
        });
    }

    private OptionsPickerView pvOptions;
    private String city = "", district = "";
    private void initOptionPicker() {//条件选择器初始化

        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() + "-"
                        + options2Items.get(options1).get(options2)
                       /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/;
                tvarea.setText(tx);
            }
        })
                .setTitleText("所在地区选择")
                .setContentTextSize(20)//设置滚轮文字大小
//                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
//                .setSelectOptions(0, 1)//默认选中项
//                .setBgColor(Color.BLACK)
//                .setTitleBgColor(Color.DKGRAY)
//                .setTitleColor(Color.LTGRAY)
//                .setCancelColor(Color.YELLOW)
//                .setSubmitColor(Color.YELLOW)
//                .setTextColorCenter(Color.LTGRAY)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
//                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                .build();

        //pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }

    private void getOptionData() {

        /**
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        String json = AssetsUtils.readText(getBaseContext(), "city.json");
        List<CityList> cityList = new Gson().fromJson(json, new TypeToken<ArrayList<CityList>>(){}.getType());

//        getCardData();
//        getNoLinkData();

        for (int i = 0; i < cityList.size(); i++) {
            options1Items.add(new ProvinceBean(i, cityList.get(i).getAreaName(), "描述部分", "其他数据"));
            ArrayList<String> options2Items_0 = new ArrayList<>();
            for (int j = 0; j < cityList.get(i).getCities().size(); j++) {

                options2Items_0.add(cityList.get(i).getCities().get(j).getAreaName());
            }
            options2Items.add(options2Items_0);
        }

        //选项1
//        options1Items.add(new ProvinceBean(0, "广东", "描述部分", "其他数据"));
//        options1Items.add(new ProvinceBean(1, "湖南", "描述部分", "其他数据"));
//        options1Items.add(new ProvinceBean(2, "广西", "描述部分", "其他数据"));
//
//        //选项2
//        ArrayList<String> options2Items_01 = new ArrayList<>();
//        options2Items_01.add("广州");
//        options2Items_01.add("佛山");
//        options2Items_01.add("东莞");
//        options2Items_01.add("珠海");
//        ArrayList<String> options2Items_02 = new ArrayList<>();
//        options2Items_02.add("长沙");
//        options2Items_02.add("岳阳");
//        options2Items_02.add("株洲");
//        options2Items_02.add("衡阳");
//        ArrayList<String> options2Items_03 = new ArrayList<>();
//        options2Items_03.add("桂林");
//        options2Items_03.add("玉林");
//        options2Items.add(options2Items_01);
//        options2Items.add(options2Items_02);
//        options2Items.add(options2Items_03);

        /*--------数据源添加完毕---------*/
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







    }

    private void initView() {
        this.nextsubmit = (TextView) findViewById(R.id.next_submit);
        this.rlvcan = (RecyclerView) findViewById(R.id.rlv_can);
        this.etphone = (EditText) findViewById(R.id.et_phone);
        this.etoffice = (EditText) findViewById(R.id.et_office);
        this.llarea = (LinearLayout) findViewById(R.id.ll_area);
        this.tvarea = (TextView) findViewById(R.id.tv_area);
        this.etname = (EditText) findViewById(R.id.et_name);

        rlvcan.setLayoutManager(new GridLayoutManager(this, 4));

        lawyerId = service.getLawyerId();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在设置...");
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

}
