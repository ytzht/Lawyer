package com.onekeyask.lawyer.ui.act.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseActivity;
import com.onekeyask.lawyer.ui.act.lawyer.FindLawyerActivity;
import com.onekeyask.lawyer.utils.UserService;

import java.util.ArrayList;
import java.util.List;

public class SearchFindActivity extends BaseActivity {

    private TextView searchcancel;
    private ImageView searchiv;
    private EditText searchet;
    private ImageView ivclearhistory;
    private RecyclerView rlvhistory;
    private LinearLayout llhistory;
    private List<String> history_list = new ArrayList<>();
    private UserService service;
    private String sp_history;
    private SearchHistoryAdapter historyAdapter;
    private String keyword = "";
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_find);

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchet.setText(keyword);
    }

    private void initView() {
        this.llhistory = (LinearLayout) findViewById(R.id.ll_history);
        this.rlvhistory = (RecyclerView) findViewById(R.id.rlv_history);
        this.ivclearhistory = (ImageView) findViewById(R.id.iv_clear_history);
        this.searchet = (EditText) findViewById(R.id.search_et);
        this.searchiv = (ImageView) findViewById(R.id.search_iv);
        this.searchcancel = (TextView) findViewById(R.id.search_cancel);

        type = getIntent().getStringExtra("type");

        if (type.equals("lawyer")) {
            searchet.setHint("搜索律师、律所、擅长领域");
        }

        if (type.equals("content")) {
            searchet.setHint("搜索律师、优质解答");
        }
        history_list.clear();
        service = new UserService(getBaseContext());
        sp_history = service.getSearchHistorys();
        String[] split = sp_history.split("&");
        if (split.length > 0) {
            for (int i = split.length; i > 0; i--) {
                if (split.length != 1)
                history_list.add(split[i - 1]);
            }
        }
        rlvhistory.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new SearchHistoryAdapter();
        rlvhistory.setAdapter(historyAdapter);
        searchcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().hasExtra("start")) {
                    startActivity(FindLawyerActivity.class);
                }
                finish();
            }
        });
        ivclearhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.saveSearchHistorys("");
                history_list.clear();
                historyAdapter.notifyDataSetChanged();
            }
        });
        searchet.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) searchet.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //实现自己的搜索逻辑

                    keyword = searchet.getText().toString();
                    searchet.setText(keyword);
                    sp_history = service.getSearchHistorys();
                    if (!keyword.equals("")) {
                        if (TextUtils.isEmpty(sp_history)) {
                            service.saveSearchHistorys(keyword);
                            history_list.add(0, keyword);
                        } else {
                            sp_history = service.getSearchHistorys();
                            String[] split = sp_history.split("&");
                            boolean hasHis = false;
                            for (int i = 0; i < split.length; i++) {
                                if (split[i].equals(keyword)) {
                                    hasHis = true;
                                }
                            }
                            if (!hasHis) {
                                service.saveSearchHistorys(sp_history + "&" + keyword);
                                history_list.add(0, keyword);
                            }
                        }
                        historyAdapter.notifyDataSetChanged();
//                        searchet.setText("");

                        if (type.equals("content"))
                            startActivity(DiscoverSearchActivity.class, "keyword", keyword);
                        if (type.equals("lawyer"))
                            startActivity(FindLawyerActivity.class, "keyword", keyword);

                        finish();
                    }
                    return true;
                }
                return false;
            }
        });

    }


    private class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_search_history, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.history_content.setText(history_list.get(position));
            holder.history_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    history_list.remove(position);
                    String now_history = "";
                    for (int i = 0; i < history_list.size(); i++) {
                        if (i == history_list.size() - 1) {
                            now_history += history_list.get(i);
                        } else {
                            now_history += (history_list.get(i) + "&");
                        }
                    }
                    service.saveSearchHistorys(now_history);
                    notifyDataSetChanged();
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((InputMethodManager) searchet.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    keyword = history_list.get(position);
                    searchet.setText(keyword);
                    if (type.equals("content"))
                        startActivity(DiscoverSearchActivity.class, "keyword", keyword);
                    if (type.equals("lawyer"))
                        startActivity(FindLawyerActivity.class, "keyword", keyword);

                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return history_list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView history_content;
            private ImageView history_close;

            public ViewHolder(View itemView) {
                super(itemView);
                history_close = (ImageView) itemView.findViewById(R.id.history_close);
                history_content = (TextView) itemView.findViewById(R.id.history_content);
            }
        }
    }

}
