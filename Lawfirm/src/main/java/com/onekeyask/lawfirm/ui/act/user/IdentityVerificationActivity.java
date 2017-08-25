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

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.GetSpecialInfoList;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.ui.act.Apis;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_verification);

        setToolbarText("身份审核");

        service = new UserService(getBaseContext());

        initView();

        initData();

        initClick();
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

            }
        });


        nextsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInfo();
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
