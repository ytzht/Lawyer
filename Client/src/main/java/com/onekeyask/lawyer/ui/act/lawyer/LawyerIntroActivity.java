package com.onekeyask.lawyer.ui.act.lawyer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.LawyerBasic;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class LawyerIntroActivity extends BaseToolBarActivity {

    @BindView(R.id.lawyer_header)
    CircleImageView lawyerHeader;
    @BindView(R.id.lawyer_name)
    TextView lawyerName;
    @BindView(R.id.lawyer_office)
    TextView lawyerOffice;
    @BindView(R.id.tv_tag1)
    TextView tvTag1;
    @BindView(R.id.tv_tag2)
    TextView tvTag2;
    @BindView(R.id.tv_tag3)
    TextView tvTag3;
    @BindView(R.id.intro_txt)
    TextView introTxt;
    @BindView(R.id.header_more)
    ImageView headerMore;
    @BindView(R.id.rl_lawyer_header)
    RelativeLayout rlLawyerHeader;

    private int lawyerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_intro);
        ButterKnife.bind(this);

        headerMore.setVisibility(View.GONE);
        introTxt.setText(getIntent().getStringExtra("notes"));
        lawyerOffice.setText(getIntent().getStringExtra("office"));
        lawyerId = getIntent().getIntExtra("lawyerId", 0);



        SubscriberOnNextListener<LawyerBasic> listener = new SubscriberOnNextListener<LawyerBasic>() {
            @Override
            public void onNext(LawyerBasic lawyerBasic) {
                lawyerName.setText(lawyerBasic.getLawyer().getName());
                Glide.with(LawyerIntroActivity.this).load(lawyerBasic.getLawyer().getHeadURL())
                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar)
                        .into(lawyerHeader);

                if (lawyerBasic.getLawyer().getSpecial().size() > 0) {
                    tvTag1.setVisibility(View.VISIBLE);
                    tvTag1.setText(lawyerBasic.getLawyer().getSpecial().get(0));
                }

                if (lawyerBasic.getLawyer().getSpecial().size() > 1) {
                    tvTag2.setVisibility(View.VISIBLE);
                    tvTag2.setText(lawyerBasic.getLawyer().getSpecial().get(1));
                }

                if (lawyerBasic.getLawyer().getSpecial().size() > 2) {
                    tvTag3.setVisibility(View.VISIBLE);
                    tvTag3.setText(lawyerBasic.getLawyer().getSpecial().get(2));
                }

            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        retrofitUtil.getLawyerBasic(lawyerId, new ProgressSubscriber<LawyerBasic>(listener, LawyerIntroActivity.this, true));

    }
}
