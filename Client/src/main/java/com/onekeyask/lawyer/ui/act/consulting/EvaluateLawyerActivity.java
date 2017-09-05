package com.onekeyask.lawyer.ui.act.consulting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.BaseResult;
import com.onekeyask.lawyer.entity.CommonTagList;
import com.onekeyask.lawyer.entity.LawyerBasic;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.L;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.utils.UserService;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EvaluateLawyerActivity extends BaseToolBarActivity {

    @BindView(R.id.civ_eva_lawyer)
    CircleImageView civEvaLawyer;
    @BindView(R.id.tv_name_eva)
    TextView tvNameEva;
    @BindView(R.id.tv_skilled)
    TextView tvSkilled;
    @BindView(R.id.iv_eva_1)
    ImageView ivEva1;
    @BindView(R.id.tv_eva_1)
    TextView tvEva1;
    @BindView(R.id.ll_eva_1)
    LinearLayout llEva1;
    @BindView(R.id.iv_eva_2)
    ImageView ivEva2;
    @BindView(R.id.tv_eva_2)
    TextView tvEva2;
    @BindView(R.id.ll_eva_2)
    LinearLayout llEva2;
    @BindView(R.id.iv_eva_3)
    ImageView ivEva3;
    @BindView(R.id.tv_eva_3)
    TextView tvEva3;
    @BindView(R.id.ll_eva_3)
    LinearLayout llEva3;
    @BindView(R.id.et_tag_eva)
    EditText etTagEva;
    @BindView(R.id.et_eva_to)
    EditText etEvaTo;
    @BindView(R.id.btn_mind_submit)
    TextView btnMindSubmit;
    @BindView(R.id.tag_flow)
    TagFlowLayout tagFlowLayout;

    private CommonTagList tagList;
    private List<CommonTagList.TagListBean> tagListBeen;
    private String Score = "5";
    private String userServiceId = "";
    private String lawyerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_lawyer);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setToolbarText("评价律师");
        userServiceId = getIntent().getStringExtra("userServiceId");
        lawyerId = getIntent().getStringExtra("lawyerId");

        L.d("onCreate: "+userServiceId);
        initTags();

        initName();
    }

    private void initName() {
        SubscriberOnNextListener<LawyerBasic> listener = new SubscriberOnNextListener<LawyerBasic>() {
            @Override
            public void onNext(LawyerBasic lawyerBasic) {
                tvNameEva.setText(lawyerBasic.getLawyer().getName());
                Glide.with(EvaluateLawyerActivity.this).load(lawyerBasic.getLawyer().getHeadURL())
                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                        .into(civEvaLawyer);
                String skilled = "专业领域：";
                for (int i = 0; i < lawyerBasic.getLawyer().getSpecial().size(); i++) {
                    if (lawyerBasic.getLawyer().getSpecial().size()-1 == i){
                        skilled = skilled + lawyerBasic.getLawyer().getSpecial().get(i);
                    }else {
                        skilled = skilled + lawyerBasic.getLawyer().getSpecial().get(i) + "、";
                    }
                }
                tvSkilled.setText(skilled);
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        retrofitUtil.getLawyerBasic(Integer.parseInt(lawyerId), new ProgressSubscriber<LawyerBasic>(listener, EvaluateLawyerActivity.this, true));
    }

    private void initTags() {

        SubscriberOnNextListener<CommonTagList> listener = new SubscriberOnNextListener<CommonTagList>() {
            @Override
            public void onNext(CommonTagList commonTagList) {
                tagList = commonTagList;

                tagListBeen = tagList.getTagList();
                initTagData();
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getCommonTagList(new ProgressSubscriber<CommonTagList>(listener, EvaluateLawyerActivity.this, false));


    }

    private void initTagData() {
        String[] mValues = new String[tagListBeen.size()];
        for (int i = 0; i < tagListBeen.size(); i++) {
            mValues[i] = tagListBeen.get(i).getTag();
        }

        tagFlowLayout.setAdapter(new TagAdapter<String>(mValues) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(EvaluateLawyerActivity.this).inflate(R.layout.tag_tv, tagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (int i = 0; i < tagListBeen.size(); i++) {
                    tagListBeen.get(i).setSelect(false);
                }

                for (Integer str : selectPosSet) {
                    tagListBeen.get(str).setSelect(true);
                }

            }
        });

        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                TextView tv = (TextView) view.findViewById(R.id.tag_tv);
                if (tagListBeen.get(position).isSelect()) {
                    tv.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                } else {
                    tv.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.grey));
                }
                return true;
            }
        });
    }

    @OnClick({R.id.ll_eva_1, R.id.ll_eva_2, R.id.ll_eva_3, R.id.btn_mind_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_eva_1:
                Score = "5";
                llEva1.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                llEva2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                llEva3.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                ivEva1.setImageResource(R.drawable.satisfied_v_c);
                ivEva2.setImageResource(R.drawable.satisfied);
                ivEva3.setImageResource(R.drawable.satisfied_n);
                tvEva1.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tvEva2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.grey));
                tvEva3.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.grey));
                break;
            case R.id.ll_eva_2:
                Score = "3";
                llEva2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                llEva1.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                llEva3.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                ivEva1.setImageResource(R.drawable.satisfied_v);
                ivEva2.setImageResource(R.drawable.satisfied_c);
                ivEva3.setImageResource(R.drawable.satisfied_n);
                tvEva2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tvEva1.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.grey));
                tvEva3.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.grey));
                break;
            case R.id.ll_eva_3:
                Score = "1";
                llEva3.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                llEva2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                llEva1.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                ivEva1.setImageResource(R.drawable.satisfied_v);
                ivEva2.setImageResource(R.drawable.satisfied);
                ivEva3.setImageResource(R.drawable.satisfied_n_c);
                tvEva3.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tvEva2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.grey));
                tvEva1.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.grey));
                break;
            case R.id.btn_mind_submit:

                Map<String, String> map = new HashMap<>();

                String comTag = "";
                if (tagListBeen.size() > 0) {
                    for (int i = 0; i < tagListBeen.size(); i++) {
                        if (tagListBeen.get(i).isSelect()) {
                            comTag += tagListBeen.get(i).getId() + ",";
                        }
                    }
                }

                if (!TextUtils.isEmpty(comTag)) {
                    comTag = comTag.substring(0, comTag.length() - 1);
                    L.d("commonTag " + comTag);
                    map.put("commonTag", comTag);
                }

                if (!TextUtils.isEmpty(etEvaTo.getText().toString())) {
                    L.d("comment " + etEvaTo.getText().toString());
                    map.put("comment", etEvaTo.getText().toString());
                }


                if (!TextUtils.isEmpty(etTagEva.getText().toString())) {
                    L.d("customTag " + etTagEva.getText().toString());
                    map.put("customTag", etTagEva.getText().toString());
                }

                L.d("Score userServiceId " + Score + " " + userServiceId);
                map.put("userId", UserService.service(getBaseContext()).getUserId()+"");
                map.put("lawyerId", lawyerId+"");
                map.put("userServiceId", userServiceId);
//                map.put("orderId", oid);
//                map.put("freeaskId", fid);
                map.put("Score", Score);

                SubscriberOnNextListener<BaseResult> listener = new SubscriberOnNextListener<BaseResult>() {
                    @Override
                    public void onNext(BaseResult result) {
                        if (result.getCode() == 0) {
                            Intent intent = new Intent(EvaluateLawyerActivity.this, EvaluateCompleteActivity.class);
                            intent.putExtra("giveMoney", true);
                            intent.putExtra("lawyerId", lawyerId);
                            intent.putExtra("lawName", tvNameEva.getText().toString());
                            intent.putExtra("userServiceId", userServiceId);

                            startActivity(intent);
                            finish();
                        } else {
                            showShort(result.getMsg());
                        }
                    }

                    @Override
                    public void onError(int code, String message) {
                        showShort(message);
                    }
                };

                retrofitUtil.getCommentLawyer(map, new ProgressSubscriber<BaseResult>(listener, EvaluateLawyerActivity.this, false));
                break;
        }
    }
}
