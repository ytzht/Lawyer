package com.onekeyask.lawfirm.ui.act.search;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.SearchLaw;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseActivity;
import com.onekeyask.lawfirm.ui.act.MainActivity;
import com.onekeyask.lawfirm.utils.UserService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchLawActivity extends BaseActivity {


    private DropDownMenu mDropDownMenu;
    private String headers[] = {"发布部门", "效力级别", "时效性"};
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter departmentAdapter;
    private ListDropDownAdapter effAdapter;
    private ListDropDownAdapter agingAdapter;
    private int page = 1;
    private int size = 10;
    private boolean hasMore;
    private RlvAdapter adapter;
    private List<SearchLaw.DataBean.LawsBean> laws = new ArrayList<>();

    private String department[] = {"不限", "全国人民代表大会", "全国人大常委会", "最高人民法院", "最高人民检察院", "国务院", "国务院各机构", "中央其他机构"};
    private String departments[] = {"", "1", "2", "3", "4", "5", "6", "7"};
    private String dep = "";
    private String eff[] = {"不限", "法律", "行政法规", "部门规章", "司法解释", "团体规定", "行业规定", "军事法规规章"};
    private String effs[] = {"", "XA01", "XC02", "XE03", "XG04", "XI05", "XK06", "XQ09"};
    private String ef = "";
    private String aging[] = {"不限", "现行有效", "失效", "已被修改", "尚未生效", "部分失效"};
    private String agings[] = {"", "01", "02", "03", "04", "05"};
    private String ag = "";

    private int constellationPosition = 0;
    KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_law);
//        setToolbarText(getIntent().getStringExtra("content"));

        initView();
        hud = KProgressHUD.create(SearchLawActivity.this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false);

        initData();

    }

    private void initData() {

        hud.show();

        OkGo.<String>post(Apis.SearchLaws)
                .params("keywords", getIntent().getStringExtra("content"))
                .params("department", dep)
                .params("xiaoli", ef)
                .params("lawyerId", new UserService(getBaseContext()).getLawyerId())
                .params("shixiao", ag)
                .params("page", page)
                .params("size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        SearchLaw law = (new Gson()).fromJson(response.body(), SearchLaw.class);
                        if (law.getCode() == 0) {
                            hasMore = law.getData().isHasMore();
                            if (page == 1) {
                                laws.clear();
                                if (!hasMore && law.getData().getLaws().size() == 0) {
                                    showShort("暂时没有内容");
                                }
                            }

                            laws.addAll(law.getData().getLaws());
                            adapter.notifyDataSetChanged();
                        } else {
                            if (page == 1)
                                showShort(law.getMsg());
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (hud.isShowing()) hud.dismiss();

                    }
                });

    }

    private ImageView back_iv, search_main;
    private TextView search_et;
    private void initView() {

        back_iv = (ImageView) findViewById(R.id.back_iv);
        search_et = (TextView) findViewById(R.id.search_et);
        search_main = (ImageView) findViewById(R.id.search_main);
        search_et.setText(getIntent().getStringExtra("content"));
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
            }
        });
        search_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchContentActivity.class);
            }
        });
        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);

        final ListView departmentView = new ListView(this);
        departmentAdapter = new ListDropDownAdapter(this, Arrays.asList(department));
        departmentView.setDividerHeight(0);
        departmentView.setAdapter(departmentAdapter);

        final ListView effView = new ListView(this);
        effAdapter = new ListDropDownAdapter(this, Arrays.asList(eff));
        effView.setDividerHeight(0);
        effView.setAdapter(effAdapter);

        final ListView agingView = new ListView(this);
        agingAdapter = new ListDropDownAdapter(this, Arrays.asList(aging));
        agingView.setDividerHeight(0);
        agingView.setAdapter(agingAdapter);

        popupViews.add(departmentView);
        popupViews.add(effView);
        popupViews.add(agingView);

        departmentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                departmentAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : department[position]);
                mDropDownMenu.closeMenu();
                dep = departments[position];
                initData();
            }
        });

        effView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                effAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : eff[position]);
                mDropDownMenu.closeMenu();
                ef = effs[position];
                initData();
            }
        });

        agingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                agingAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : aging[position]);
                mDropDownMenu.closeMenu();
                ag = agings[position];
                initData();
            }
        });

        //init context view
        SmartRefreshLayout layout = new SmartRefreshLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        RecyclerView contentView = new RecyclerView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setLayoutManager(new LinearLayoutManager(this));
        contentView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        adapter = new RlvAdapter();
        contentView.setAdapter(adapter);

        layout.addView(contentView);

        layout.setRefreshHeader(new ClassicsHeader(SearchLawActivity.this));
        layout.setRefreshFooter(new ClassicsFooter(SearchLawActivity.this));
        layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(200);
                page = 1;
                initData();
            }
        });
        layout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(200);
                if (hasMore) {
                    page += 1;
                    initData();
                }
            }
        });


        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, layout);
    }


    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    private class RlvAdapter extends RecyclerView.Adapter<RlvAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_law_content, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {


            holder.tv_txt.setText(Html.fromHtml(position + 1 + "." + laws.get(position).getTitle()));

            String TimelinessDic = "";

            for (String key : laws.get(position).getTimelinessDic().keySet()) {
                TimelinessDic += (laws.get(position).getTimelinessDic().get(key) + " ");
            }


            holder.tv_time.setText(TimelinessDic + "/" + laws.get(position).getIssueDate() + "发布 / " + laws.get(position).getImplementDate() + "实施");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(LawDetailActivity.class, "id", laws.get(position).getGid());
                }
            });

        }

        @Override
        public int getItemCount() {
            return laws.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_txt, tv_time;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_txt = (TextView) itemView.findViewById(R.id.tv_txt);
                tv_time = (TextView) itemView.findViewById(R.id.tv_time);

            }
        }
    }
}
