package com.onekeyask.lawfirm.ui.act.search;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.LawDoc;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;

public class LawDetailActivity extends BaseToolBarActivity {

    private TextView tv_law_doc, law_tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_detail);


        law_tv_title = (TextView) findViewById(R.id.law_tv_title);
        tv_law_doc = (TextView) findViewById(R.id.tv_law_doc);


        setToolbarText("法规详情");
        OkGo.<String>get(Apis.Lawdoc).params("lawid", getIntent().getStringExtra("id")).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                LawDoc doc = (new Gson()).fromJson(response.body(), LawDoc.class);
                if (doc.getCode() == 0) {
                    law_tv_title.setText(doc.getData().getLaw().getTitle());
                    if (doc.getData().getLaw().getTitle().length() > 8) {
                        setToolbarText(doc.getData().getLaw().getTitle().substring(0, 8) + "...");
                    } else {
                        setToolbarText(doc.getData().getLaw().getTitle());
                    }
                    tv_law_doc.setText(Html.fromHtml(doc.getData().getLaw().getFullText()));
                } else {
                    showShort(doc.getMsg());
                    finish();
                }


            }
        });
    }
}
