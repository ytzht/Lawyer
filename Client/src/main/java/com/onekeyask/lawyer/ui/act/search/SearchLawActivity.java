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

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.ui.act.lawyer.FindLawyerActivity;
import com.onekeyask.lawyer.utils.RecyclerSpace;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchLawActivity extends BaseToolBarActivity {

    @BindView(R.id.search_view)
    ImageView searchView;
    @BindView(R.id.rlv_icon)
    RecyclerView rlvIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_law);
        ButterKnife.bind(this);
        setToolbarText("找律师");

        rlvIcon.setLayoutManager(new GridLayoutManager(getBaseContext(), 4));
        rlvIcon.addItemDecoration(new RecyclerSpace(2, ContextCompat.getColor(getBaseContext(), R.color.divider)));
        rlvIcon.setAdapter(new IconRlvAdapter());

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchFindActivity.class, "type", "lawyer");
            }
        });
    }

    private class IconRlvAdapter extends RecyclerView.Adapter<IconRlvAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_icon_search, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            switch (position){
                case 0:
                    holder.icon_iv.setImageResource(R.drawable.icon_consumption);
                    holder.icon_name.setText("合同纠纷");
                    break;
                case 1:
                    holder.icon_iv.setImageResource(R.drawable.icon_contract);
                    holder.icon_name.setText("房屋纠纷");
                    break;
                case 2:
                    holder.icon_iv.setImageResource(R.drawable.icon_house);
                    holder.icon_name.setText("婚姻继承");
                    break;
                case 3:
                    holder.icon_iv.setImageResource(R.drawable.icon_investment);
                    holder.icon_name.setText("人身安全");
                    break;
                case 4:
                    holder.icon_iv.setImageResource(R.drawable.icon_labour);
                    holder.icon_name.setText("劳动纠纷");
                    break;
                case 5:
                    holder.icon_iv.setImageResource(R.drawable.icon_marriage);
                    holder.icon_name.setText("债权债务");
                    break;
                case 6:
                    holder.icon_iv.setImageResource(R.drawable.icon_penal);
                    holder.icon_name.setText("侵权纠纷");
                    break;
                default:
                    holder.icon_iv.setImageResource(R.drawable.icon_tort);
                    holder.icon_name.setText("消费维权");
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(FindLawyerActivity.class, "position", position+"");
                }
            });
        }

        @Override
        public int getItemCount() {
            return 12;
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            private ImageView icon_iv;
            private TextView icon_name;
            public ViewHolder(View itemView) {
                super(itemView);
                icon_name = (TextView)itemView.findViewById(R.id.icon_name);
                icon_iv = (ImageView) itemView.findViewById(R.id.icon_iv);
            }
        }
    }
}
