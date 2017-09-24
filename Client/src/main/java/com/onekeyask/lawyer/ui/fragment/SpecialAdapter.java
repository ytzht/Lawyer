package com.onekeyask.lawyer.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.AllSpecial;
import com.onekeyask.lawyer.global.Constant;
import com.onekeyask.lawyer.ui.act.lawyer.FindLawyerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytzht on 2017/09/23 下午9:41
 */

public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.ViewHolder> {


    List<AllSpecial.AllSpecialBean> beanList = new ArrayList<>();
    Context context;

    public SpecialAdapter(Context context) {
        this.context = context;
        AllSpecial special = (new Gson()).fromJson(Constant.tags, AllSpecial.class);

        beanList = special.getAllSpecial();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_special, parent, false));
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
                Intent intent = new Intent(context, FindLawyerActivity.class);
                intent.putExtra("position", (position + 1) + "");
                intent.putExtra("special", beanList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 16;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView icon_iv;
        private TextView icon_name;

        public ViewHolder(View itemView) {
            super(itemView);
            icon_name = (TextView) itemView.findViewById(R.id.icon_name);
            icon_iv = (ImageView) itemView.findViewById(R.id.icon_iv);
        }
    }
}
