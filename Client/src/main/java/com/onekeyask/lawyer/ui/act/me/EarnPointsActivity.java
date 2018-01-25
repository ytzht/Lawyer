package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.PointsInfo;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.utils.UserService;

public class EarnPointsActivity extends BaseToolBarActivity {

    private android.widget.TextView myScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_points);
        this.myScore = (TextView) findViewById(R.id.score);
        setToolbarText("赚取积分");


        getResultOnNext = new SubscriberOnNextListener<PointsInfo>() {
            @Override
            public void onNext(PointsInfo info) {
                myScore.setText(String.valueOf(info.getPoints()));
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        retrofitUtil.getPointsInfo(UserService.service(getBaseContext()).getUserId(),
                new ProgressSubscriber<PointsInfo>(getResultOnNext, getBaseContext(), false));



    }
}
