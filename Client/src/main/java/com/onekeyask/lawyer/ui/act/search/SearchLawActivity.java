package com.onekeyask.lawyer.ui.act.search;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.AllSpecial;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.ui.act.lawyer.FindLawyerActivity;
import com.onekeyask.lawyer.utils.RecyclerSpace;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchLawActivity extends BaseToolBarActivity {

    @BindView(R.id.search_view)
    ImageView searchView;
    @BindView(R.id.rlv_icon)
    RecyclerView rlvIcon;
    @BindView(R.id.law_pic_1)
    ImageView law_pic_1;
    @BindView(R.id.law_pic_2)
    ImageView law_pic_2;
    @BindView(R.id.law_pic_3)
    ImageView law_pic_3;
    @BindView(R.id.law_pic_4)
    ImageView law_pic_4;
    String tags = "{\"allSpecial\":[" +
            "{\"id\":1,\"name\":\"合同纠纷\"}," +
            "{\"id\":2,\"name\":\"房产纠纷\"}," +
            "{\"id\":3,\"name\":\"婚姻继承\"}," +
            "{\"id\":4,\"name\":\"人身损害\"}," +
            "{\"id\":5,\"name\":\"劳动争议\"}," +
            "{\"id\":6,\"name\":\"债权债务\"}," +
            "{\"id\":7,\"name\":\"侵权纠纷\"}," +
            "{\"id\":8,\"name\":\"消费维权\"}," +
            "{\"id\":9,\"name\":\"交通事故\"}," +
            "{\"id\":10,\"name\":\"刑事辩护\"}," +
            "{\"id\":12,\"name\":\"投资\"}," +
            "{\"id\":13,\"name\":\"融资\"}," +
            "{\"id\":14,\"name\":\"兼并收购\"}," +
            "{\"id\":15,\"name\":\"上市\"}," +
            "{\"id\":16,\"name\":\"知识产权\"}," +
            "{\"id\":17,\"name\":\"新三板\"}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_law);
        ButterKnife.bind(this);
        setToolbarText("找律师");

        rlvIcon.setLayoutManager(new GridLayoutManager(getBaseContext(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rlvIcon.addItemDecoration(new RecyclerSpace(2, ContextCompat.getColor(getBaseContext(), R.color.divider)));
        AllSpecial special = (new Gson()).fromJson(tags, AllSpecial.class);
        beanList = special.getAllSpecial();
        rlvIcon.setAdapter(new IconRlvAdapter());

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchFindActivity.class, "type", "lawyer");
            }
        });

        law_pic_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FindLawyerActivity.class, "keyword", "中伦律师事务所");
            }
        });

        law_pic_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FindLawyerActivity.class, "keyword", "京都律师事务所");
            }
        });

        law_pic_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FindLawyerActivity.class, "keyword", "炜衡律师事务所");
            }
        });

        law_pic_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FindLawyerActivity.class, "keyword", "金杜律师事务所");
            }
        });
    }

    List<AllSpecial.AllSpecialBean> beanList;

    private void initData() {


    }

    private class IconRlvAdapter extends RecyclerView.Adapter<IconRlvAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_icon_search, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            switch (position) {
                case 0:
                    holder.icon_iv.setImageResource(R.drawable.icon_contract);
                    break;
                case 1:
                    holder.icon_iv.setImageResource(R.drawable.icon_house);
                    break;
                case 2:
                    holder.icon_iv.setImageResource(R.drawable.icon_marriage);
                    break;
                case 3:
                    holder.icon_iv.setImageResource(R.drawable.icon_personal);
                    break;
                case 4:
                    holder.icon_iv.setImageResource(R.drawable.icon_labour);
                    break;
                case 5:
                    holder.icon_iv.setImageResource(R.drawable.icon_bond);
                    break;
                case 6:
                    holder.icon_iv.setImageResource(R.drawable.icon_tort);
                    break;
                case 7:
                    holder.icon_iv.setImageResource(R.drawable.icon_consumption);
                    break;
                case 8:
                    holder.icon_iv.setImageResource(R.drawable.icon_traffic);
                    break;
                case 9:
                    holder.icon_iv.setImageResource(R.drawable.icon_penal);
                    break;
                case 10:
                    holder.icon_iv.setImageResource(R.drawable.icon_investment);
                    break;
                case 11:
                    holder.icon_iv.setImageResource(R.drawable.icon_finacning);
                    break;
                case 12:
                    holder.icon_iv.setImageResource(R.drawable.icon_acquisition);
                    break;
                case 13:
                    holder.icon_iv.setImageResource(R.drawable.icon_list);
                    break;
                case 14:
                    holder.icon_iv.setImageResource(R.drawable.icon_knowledge);
                    break;
                case 15:
                    holder.icon_iv.setImageResource(R.drawable.icon_three_board);
                    break;
                default:
                    holder.icon_iv.setImageResource(R.drawable.icon_bond);
            }

            holder.icon_name.setText(beanList.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(FindLawyerActivity.class, "position", (position + 1) + "", "special", beanList.get(position).getName());
                }
            });
        }

        @Override
        public int getItemCount() {
            return 16;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView icon_iv;
            private TextView icon_name;

            public ViewHolder(View itemView) {
                super(itemView);
                icon_name = (TextView) itemView.findViewById(R.id.icon_name);
                icon_iv = (ImageView) itemView.findViewById(R.id.icon_iv);
            }
        }
    }
}
